package br.com.crypto.service;

import br.com.crypto.controller.dto.CurrencyDTO;
import br.com.crypto.controller.request.CurrencyRequest;
import br.com.crypto.mapper.CurrencyMapper;
import br.com.crypto.model.Currency;
import br.com.crypto.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        List<Currency> currency;
        if (nameCrypto != null && code != null) {
            currency = currencyRepository.findCurrencyByNameCryptoAndCode(nameCrypto, code);
        } else if (nameCrypto != null) {
            currency = currencyRepository.findCurrencyByNameCrypto(nameCrypto);
        } else if (code != null) {
            currency = currencyRepository.findCurrencyByCode(code);
        } else {
            currency = currencyRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }
        return currencyMapper.fromListCurrencyModelToListCurrencyDTO(currency);
    }

    @Transactional
    public void save(CurrencyRequest currencyRequest) {
        Currency currency = currencyMapper.fromCurrencyRequestToCurrency(currencyRequest);
        currencyRepository.save(currency);
    }

    public void update(Long id, CurrencyRequest currencyRequest) {
        var op = currencyRepository.findById(id);
        if (op.isPresent()) {
            var currency = op.get();
            currency.setNameCrypto(currencyRequest.getNameCrypto());
            currency.setCode(currencyRequest.getCode());
            currencyRepository.save(currency);

        } else {
            System.out.println("Crypto id:" + id + ",cannot be found!"); //Temporario
        }
    }

    public void deleteById(Long id) {
        currencyRepository.deleteById(id);
    }

}
