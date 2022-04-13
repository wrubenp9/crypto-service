package br.com.crypto.service;

import br.com.crypto.controller.dto.CurrencyDTO;
import br.com.crypto.mapper.CurrencyMapper;
import br.com.crypto.model.Currency;
import br.com.crypto.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

    public List<CurrencyDTO> findAll(){
        List<Currency> currency = currencyRepository.findAll();
        return currencyMapper.fromListCurrencyModelToListCurrencyDTO(currency);
    }

}
