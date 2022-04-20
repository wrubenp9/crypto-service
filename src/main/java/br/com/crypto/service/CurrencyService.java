package br.com.crypto.service;

import br.com.crypto.controller.dto.CurrencyDTO;
import br.com.crypto.controller.request.CurrencyRequest;
import br.com.crypto.mapper.CurrencyMapper;
import br.com.crypto.model.Currency;
import br.com.crypto.repository.CurrencyRepository;
import br.com.crypto.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

    public List<CurrencyDTO> findCurrency(String nameCrypto, String code) {
        return verifyCurrencyNameNullCodeNull(nameCrypto, code);
    }

    private List<CurrencyDTO> verifyCurrencyNameNullCodeNull(String nameCrypto, String code) {
        String message = null;

        List<Currency> currency;
        if (nameCrypto != null && code != null) {
            currency = currencyRepository.findCurrencyByNameCryptoAndCode(nameCrypto, code);

        } else if (nameCrypto != null) {
            currency = currencyRepository.findCurrencyByNameCrypto(nameCrypto);
            message = nameCrypto;
        } else if (code != null) {
            currency = currencyRepository.findCurrencyByCode(code);
            message = code;
        } else {
            currency = currencyRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }

        //Verifica se a lista está vazia
        if (currency.isEmpty()) {//
            throw new ResourceNotFoundException(message);
        } else {
            return currencyMapper.fromListCurrencyModelToListCurrencyDTO(currency);
        }
    }

    @Transactional
    public void save(CurrencyRequest currencyRequest) {
        Currency currency = currencyMapper.fromCurrencyRequestToCurrency(currencyRequest);
        currencyRepository.save(currency);
    }

    public void update(UUID id, CurrencyRequest currencyRequest) {
        var op = currencyRepository.findById(id);
        if (op.isPresent()) {
            var currency = op.get();
            currency.setNameCrypto(currencyRequest.getNameCrypto());
            currency.setCode(currencyRequest.getCode());
            currencyRepository.save(currency);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    public void deleteById(UUID id) {
        try {
            currencyRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }
    }

}
