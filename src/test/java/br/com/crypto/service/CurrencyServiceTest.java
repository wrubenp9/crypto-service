package br.com.crypto.service;


import br.com.crypto.controller.dto.CurrencyDTO;
import br.com.crypto.controller.request.CurrencyRequest;
import br.com.crypto.mapper.CurrencyMapper;
import br.com.crypto.model.Currency;
import br.com.crypto.repository.CurrencyRepository;
import br.com.crypto.service.exception.DatabaseException;
import br.com.crypto.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;


import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceTest {

    @Mock  // cria uma simulação
    CurrencyRepository currencyRepository;

    @Mock
    CurrencyMapper currencyMapper;

    @InjectMocks // cria uma instancia da classe e injeta o mock que são criados
    CurrencyService currencyService;

    @BeforeEach  //o metodo inicializa os mocks e o injeta para cada metodo de teste ao ser chamado
    public void setUp() {
        MockitoAnnotations.openMocks(this); //não entendi pq fica riscado
    }

    @Test
    @DisplayName("Should find currency by nameCrypt and Code")
    public void shouldFindCurrencyByNameCryptoAndCode() {
        // Given
        Currency currency = Currency.builder()
                .id(UUID.randomUUID())
                .nameCrypto("Bitcoin")
                .code("BTC")
                .createdAt(OffsetDateTime.now())
                .build();

        List<Currency> currencyListMock = Collections.singletonList(currency);
        when(currencyRepository.findCurrencyByNameCryptoAndCode(eq("Bitcoin"), eq("BTC"))).thenReturn(currencyListMock);

        // When
        List<CurrencyDTO> listFindCurrencyByNameCryptoAndCode = currencyService.findCurrency("Bitcoin", "BTC");

        // Then
        assertThat(listFindCurrencyByNameCryptoAndCode.size()).isEqualTo(1);
        assertThat(listFindCurrencyByNameCryptoAndCode.get(0).getNameCrypto()).isEqualTo(currency.getNameCrypto());
    }

    @Test
    @DisplayName("Should find currency by nameCrypt")
    public void shouldFindCurrencyByNameCrypto() {
        // Given
        //Objeto que vai ser retornado pelo mock
        Currency currency = Currency.builder()
                .id(UUID.randomUUID())
                .nameCrypto("Bitcoin")
                .code("BTC")
                .createdAt(OffsetDateTime.now())
                .build();

        List<Currency> currencyListMock = Collections.singletonList(currency);
        Mockito.when(currencyRepository.findCurrencyByNameCrypto(eq("Bitcoin"))).thenReturn(currencyListMock);

        // When
        var listFindCurrencyByNameCrypto = currencyService.findCurrency("Bitcoin", null);

        // Then
        assertThat(listFindCurrencyByNameCrypto.size()).isEqualTo(1); //tamanho
        assertThat(listFindCurrencyByNameCrypto.get(0).getNameCrypto()).isEqualTo(currency.getNameCrypto()); //comapara nameCrypto
    }

    @Test
    @DisplayName("Should find currency by code")
    public void shouldFindCurrencyByCode() {
        // Given
        Currency currency = Currency.builder()
                .id(UUID.randomUUID())
                .nameCrypto("Bitcoin")
                .code("BTC")
                .createdAt(OffsetDateTime.now())
                .build();

        List<Currency> currencyListMock = Collections.singletonList(currency);
        Mockito.when(currencyRepository.findCurrencyByCode(eq("BTC"))).thenReturn(currencyListMock);

        // When
        List<CurrencyDTO> listFindCurrencyByNameCrypto = currencyService.findCurrency(null, "BTC");

        // Then
        assertEquals(1, listFindCurrencyByNameCrypto.size());
        assertEquals(currency.getNameCrypto(), listFindCurrencyByNameCrypto.get(0).getNameCrypto());

    }

    @Test
    @DisplayName("Should find all")
    public void shouldFindAll() {
        // Given
        Currency currency1 = Currency.builder()
                .id(UUID.randomUUID())
                .nameCrypto("Bitcoin")
                .code("BTC")
                .createdAt(OffsetDateTime.now())
                .build();

        Currency currency2 = Currency.builder()
                .id(UUID.randomUUID())
                .nameCrypto("Solana")
                .code("SOL")
                .createdAt(OffsetDateTime.now())
                .build();

        Sort order = Sort.by(Sort.Direction.ASC, "nameCrypto");
        List<Currency> currencyListMock = new ArrayList<>();
        currencyListMock.add(currency1);
        currencyListMock.add(currency2);

        Mockito.when(currencyRepository.findAll(eq(order))).thenReturn(currencyListMock);

        // When
        List<CurrencyDTO> listFindAllCurrency = currencyService.findCurrency(null, null);

        // Then
        assertEquals(2, listFindAllCurrency.size());
        assertEquals(listFindAllCurrency.get(0).getNameCrypto(), "Bitcoin");
        assertEquals(listFindAllCurrency.get(1).getNameCrypto(), "Solana");
    }

    @Test
    @DisplayName("Should save currency")
    public void shouldSave() {
        // Given
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .nameCrypto("Bitcoin")
                .code("BTC")
                .build();

        Currency currency = new Currency("Bitcoin", "BTC");

        // Testando comportanmento do mock - Manipulando save do repository
        Mockito.when(currencyRepository.save(any())).thenReturn(currency);

        // When
        currencyService.save(currencyRequest);

        // Then
        // verificar se o mock chamou apenas uma vez o metodo save com qualquer parametro
        verify(currencyRepository, times(1)).save(any());  // quantas vezes o metodo foi chamado
    }

    @Test
    @DisplayName("Should return exception save")
    public void shouldReturnExceptionSave() {
        // Given
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .nameCrypto("Bitcoin")
                .code("BTC")
                .build();

        DataIntegrityViolationException exception = new DataIntegrityViolationException("Database error");

        when(currencyRepository.save(any())).thenThrow(exception);

        //When
        Throwable thrown = catchThrowable(() -> currencyService.save(currencyRequest));

        //Then
        assertThat(thrown).isInstanceOf(DatabaseException.class);
    }

    @Test
    @DisplayName("Should update")
    public void shouldUpdate() {
        // Given
        UUID id = UUID.randomUUID();
        Currency currency = Currency.builder()
                .id(id)
                .nameCrypto("Bitcoin")
                .code("BTC")
                .createdAt(OffsetDateTime.now())
                .build();

        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .nameCrypto("Ethereum")
                .code("ETH")
                .build();

        //mock chama o metodo getByID com id igual e retorna currency
        when(currencyRepository.getById(eq(id))).thenReturn(currency);

        // WHen
        currencyService.update(id, currencyRequest);

        // Then
        assertEquals(currency.getNameCrypto(), "Ethereum");
    }

    @Test
    @DisplayName("Should return resource not found exception update")
    public void shouldReturnResourceNotFoundExceptionUpdate() {
        UUID id = UUID.randomUUID();
        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .nameCrypto("Ethereum")
                .code("ETH")
                .build();

        EntityNotFoundException exception = new EntityNotFoundException("Not found");
        doThrow(exception).when(currencyRepository).getById(id);

        // When
        Throwable thrown = catchThrowable(() -> currencyService.update(id, currencyRequest));

        // Then
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class);
        assertEquals(thrown.getMessage(), "Resource not found. Id: " + id);
    }

    @Test
    @DisplayName("Should delete by id")
    public void shouldDeleteById() {
        // Given
        Currency currency = Currency.builder()
                .id(UUID.randomUUID())
                .nameCrypto("Bitcoin")
                .code("BTC")
                .createdAt(OffsetDateTime.now())
                .build();

        currencyRepository.save(currency);
        when(currencyRepository.findById(currency.getId())).thenReturn(Optional.of(currency));

        // When
        currencyService.deleteById(currency.getId());

        // Then
        verify(currencyRepository).deleteById(currency.getId());  //Veririficando se o id existe
    }

    @Test
    @DisplayName("Should return exception delete by id")
    public void shouldReturnExceptionDeleteById() {
        // Given
        UUID id = UUID.randomUUID();

        EmptyResultDataAccessException exception = new EmptyResultDataAccessException(1);

        doThrow(exception).when(currencyRepository).deleteById(eq(id));

        // When
        Throwable thrown = catchThrowable(() -> currencyService.deleteById(id));

        // Then
        verify(currencyRepository).deleteById(id);
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class);
        assertEquals(thrown.getMessage(), "Resource not found. Id: " + id);
    }
}