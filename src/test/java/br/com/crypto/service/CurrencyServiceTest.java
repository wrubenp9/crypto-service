package br.com.crypto.service;


import br.com.crypto.controller.dto.CurrencyDTO;

import br.com.crypto.repository.CurrencyRepository;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class CurrencyServiceTest {

    CurrencyRepository currencyRepository;

    CurrencyService currencyService;

    public void setup(){
        currencyRepository = mock(CurrencyRepository.class);

//        currencyService = new CurrencyService(currencyRepository);
    }

    @Test
    public void testSave(){
        // Given
        CurrencyDTO currencyDTO;

        // When

        // Then
    }


}
