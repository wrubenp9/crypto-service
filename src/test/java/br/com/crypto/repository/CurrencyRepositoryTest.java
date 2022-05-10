package br.com.crypto.repository;

import br.com.crypto.model.Currency;
import br.com.crypto.service.exception.DatabaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CurrencyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CurrencyRepository currencyRepository;


    @DisplayName("Should find currency by nameCrypt")
    @Test
    public void shouldFindCurrencyByNameCrypto() {
        // Given
        Currency currency = new Currency("Bitcoin", "BTC");
        currencyRepository.save(currency);
        // When
        List<Currency> currencyList = currencyRepository.findCurrencyByNameCrypto("Bitcoin");

        // Then
        assertThat(currencyList.get(0).getNameCrypto()).isEqualTo("Bitcoin");
        assertThat(currencyList.get(0).getCode()).isEqualTo("BTC");
    }

    @DisplayName("Should find currency by code")
    @Test
    public void shouldFindCurrencyByCode() {
        // Given
        Currency currency = new Currency("Ethereum", "ETH");
        currencyRepository.save(currency);

        // When
        List<Currency> currencyList = currencyRepository.findCurrencyByCode("ETH");

        // Then
        assertThat(currencyList.get(0).getNameCrypto()).isEqualTo("Ethereum");
        assertThat(currencyList.get(0).getCode()).isEqualTo("ETH");
    }

    @DisplayName("Should find currency by nameCrypt and code")
    @Test
    public void shouldFindCurrencyByNameCryptoAndCode() {
        // Given - dado tal cenario
        Currency currency1 = new Currency("Bitcoin", "BTC");
        entityManager.persist(currency1);
        Currency currency2 = new Currency("Ethereum", "ETH");
        entityManager.persist(currency2);
        Currency currency3 = new Currency("Tether", "USDT");
        entityManager.persist(currency3);
        Currency currency4 = new Currency("Solana", "SOL");
        entityManager.persist(currency4);

        // When - quando
        List<Currency> currencyList = currencyRepository.findCurrencyByNameCryptoAndCode("Solana", "SOL");

        // Then - então
        assertThat(currencyList.get(0).getNameCrypto()).isEqualTo(currency4.getNameCrypto());
        assertThat(currencyList.get(0).getCode()).isEqualTo(currency4.getCode());
    }
}
