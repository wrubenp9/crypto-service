package br.com.crypto.repository;

import br.com.crypto.model.CurrencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyModel, Long> {
}
