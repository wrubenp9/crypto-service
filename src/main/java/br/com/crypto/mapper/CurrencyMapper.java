package br.com.crypto.mapper;

import br.com.crypto.controller.dto.CurrencyDTO;
import br.com.crypto.controller.request.CurrencyRequest;
import br.com.crypto.model.Currency;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Deixar metodos statico
@Component
public class CurrencyMapper {

    public static CurrencyDTO currencyToCurrencyDTO(Currency currency){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(currency, CurrencyDTO.class);
    }

    public static List<CurrencyDTO> listCurrencyModelToListCurrencyDTO(List<Currency> currencies) {
        return currencies.stream()
                .map(currency -> currencyToCurrencyDTO(currency))
                .collect(Collectors.toList());
    }

    public static Currency currencyRequestToCurrency(CurrencyRequest currencyRequest) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(currencyRequest, Currency.class);
    }

}
