package br.com.crypto.repository;

import br.com.crypto.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
    List<Currency> findCurrencyByNameCrypto(String nameCrypto);
    List<Currency> findCurrencyByCode(String code);
    List<Currency> findCurrencyByNameCryptoAndCode(String nameCrypto, String code);
}
