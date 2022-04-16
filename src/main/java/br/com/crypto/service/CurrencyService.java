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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

//    public List<CurrencyDTO> findAll() {
//        List<Currency> currency = currencyRepository.findAll();
//        return currencyMapper.fromListCurrencyModelToListCurrencyDTO(currency);
//    }

    public List<CurrencyDTO> findById(Long id) {
        var op = currencyRepository.findById(id);
        return currencyMapper.fromListCurrencyModelToListCurrencyDTO(op.stream().collect(Collectors.toList()));
    }

    public List<CurrencyDTO> findCurrency(String nameCrypto, String code) {
        return verifyCurrencyNameNullCodeNull(nameCrypto, code);
    }

    public List<CurrencyDTO> verifyCurrencyNameNullCodeNull(String nameCrypto, String code) {

        var order = Sort.by(Sort.Direction.ASC, "id");

        List<Currency> currency;
        //reduzir fluxo de if/else | deixar mais legivel
        if (nameCrypto != null && code != null) {
            currency = currencyRepository.findCurrencyByNameCryptoAndCode(nameCrypto, code);
        } else if (nameCrypto != null) {
            currency = currencyRepository.findCurrencyByNameCrypto(nameCrypto);
        } else if (code != null) {
            currency = currencyRepository.findCurrencyByCode(code);
        } else {
            currency = currencyRepository.findAll(order);
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
