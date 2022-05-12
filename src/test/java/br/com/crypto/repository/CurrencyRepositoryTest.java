package br.com.crypto.repository;

import br.com.crypto.model.Currency;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@Sql(scripts = {"classpath:db/insert_data.sql"})
@Sql(scripts = "classpath:db/clean_schema.sql", executionPhase = AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
public class CurrencyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    @DisplayName("Should find currency by nameCrypt")
    public void shouldFindCurrencyByNameCrypto() {
        // When
        List<Currency> currencyList = currencyRepository.findCurrencyByNameCrypto("Bitcoin");

        System.out.println("\n\n\n\n" + currencyList + "\n\n\n\n");
        // Then
        assertThat(currencyList.get(0).getNameCrypto()).isEqualTo("Bitcoin");
        assertThat(currencyList.get(0).getCode()).isEqualTo("BTC");
    }

    @DisplayName("Should find currency by code")
    @Test
    public void shouldFindCurrencyByCode() {
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
        // When - quando
        List<Currency> currencyList = currencyRepository.findCurrencyByNameCryptoAndCode("Solana", "SOL");

        // Then - então
        assertThat(currencyList.get(0).getNameCrypto()).isEqualTo("Solana");
        assertThat(currencyList.get(0).getCode()).isEqualTo("SOL");
    }
}
