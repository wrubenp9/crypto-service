package br.com.crypto.repository;

import br.com.crypto.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, UUID> {

//    @Query("SELECT c FROM Currency c WHERE LOWER(c.nameCrypto) LIKE LOWER(CONCAT('%',:nameCrypto,'%'))")
    List<Currency> findCurrencyByNameCrypto(String nameCrypto);

//    @Query("SELECT c FROM Currency c WHERE LOWER(c.code) LIKE LOWER(CONCAT('%',:code,'%'))")
    List<Currency> findCurrencyByCode(String code);

    List<Currency> findCurrencyByNameCryptoAndCode(String nameCrypto, String code);
}