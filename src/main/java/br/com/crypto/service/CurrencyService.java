package br.com.crypto.service;

import br.com.crypto.controller.dto.CurrencyDTO;
import br.com.crypto.controller.request.CurrencyRequest;
import br.com.crypto.mapper.CurrencyMapper;
import br.com.crypto.model.Currency;
import br.com.crypto.repository.CurrencyRepository;
import br.com.crypto.service.exception.DatabaseException;
import br.com.crypto.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.crypto.mapper.CurrencyMapper.*;

@Service
@AllArgsConstructor
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

    public List<CurrencyDTO> findCurrency(String nameCrypto, String code) {
        List<Currency> currency;
        if (nameCrypto != null && code != null) {
            currency = currencyRepository.findCurrencyByNameCryptoAndCode(nameCrypto, code);
        } else if (nameCrypto != null) {
            currency = currencyRepository.findCurrencyByNameCrypto(nameCrypto);
        } else if (code != null) {
            currency = currencyRepository.findCurrencyByCode(code);
        } else {
            currency = currencyRepository.findAll(Sort.by(Sort.Direction.ASC, "nameCrypto"));
        }

        return listCurrencyModelToListCurrencyDTO(currency);
    }

    @Transactional
    public void save(CurrencyRequest currencyRequest) {
        try {
            Currency currency = currencyRequestToCurrency(currencyRequest);
            currencyRepository.save(currency);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void update(UUID id, CurrencyRequest currencyRequest) {
        try {
            Currency entity = currencyRepository.getById(id);
            updateData(entity, currencyRequest);
            currencyRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Currency entity, CurrencyRequest currencyRequest) {
        entity.setNameCrypto(currencyRequest.getNameCrypto());
        entity.setCode(currencyRequest.getCode());
    }

    public void deleteById(UUID id) {
        try {
            currencyRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }
    }



    // Temporario - Exercicio
    public String avaliacao(int nota){
        if(nota > 60){
            return "Aprovado";
        } else if (nota  >= 50 && nota <= 60){
            return "Recuperação";
        } else{
            return "Reprovado";
        }
    }

    public String verificaCrypto(UUID id){
        Optional<Currency> currency = currencyRepository.findById(id);

        if (currency.isPresent()){
            if (currency.get().getNameCrypto().equals("Bitcoin")){
                return "Bitcoin escolhido";
            } else if (currency.get().getNameCrypto().equals("Ethereum")){
                return "Ethereum escolhido";
            } else {
                return "Não trabalhamos com essa crypto";
            }
        } else {
            return "Não existe";
        }
    }
}
