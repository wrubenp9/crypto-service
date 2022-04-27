package br.com.crypto.repository;

import br.com.crypto.model.Currency;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CurrencyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CurrencyRepository currencyRepository;


    @Test
    public void shouldFindCurrencyByNameCrypto() {
        Currency currency = new Currency("Bitcoin", "BTC");
        entityManager.persist(currency); //grava uma entidade JPA no banco de Dados

        List<Currency> currencyList = currencyRepository.findCurrencyByNameCrypto("Bitcoin");

        assertThat(currencyList.get(0).getNameCrypto()).isEqualTo("Bitcoin");
        assertThat(currencyList.get(0).getCode()).isEqualTo("BTC");
    }

    @Test
    public void shouldFindCurrencyByCode() {
        Currency currency = new Currency("Ethereum", "ETH");
        entityManager.persist(currency);

        List<Currency> currencyList = currencyRepository.findCurrencyByCode("ETH");

        assertThat(currencyList.get(0).getNameCrypto()).isEqualTo("Ethereum");
        assertThat(currencyList.get(0).getCode()).isEqualTo("ETH");
    }

    @Test
    public void findCurrencyByNameCryptoAndCode() {
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
