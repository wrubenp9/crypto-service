package br.com.crypto.mapper;

import br.com.crypto.controller.dto.CurrencyDTO;
import br.com.crypto.controller.request.CurrencyRequest;
import br.com.crypto.model.Currency;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CurrencyMapper {

    @Autowired
    private ModelMapper mapper;

    public CurrencyDTO fromCurrencyToCurrencyDTO(Currency currency){
        return mapper.map(currency, CurrencyDTO.class);
    }

    public List<CurrencyDTO> fromListCurrencyModelToListCurrencyDTO(List<Currency> currencies) {
        return currencies.stream()
                .map(currency -> fromCurrencyToCurrencyDTO(currency))
                .collect(Collectors.toList());
    }
    public Currency fromCurrencyRequestToCurrency(CurrencyRequest currencyRequest) {
        return mapper.map(currencyRequest, Currency.class);
    }

}
