package br.com.crypto.repository;

import br.com.crypto.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findCurrencyByNameCrypto(String nameCrypto);
    List<Currency> findCurrencyByCode(String code);
    List<Currency> findCurrencyByNameCryptoAndCode(String nameCrypto, String code);


}
