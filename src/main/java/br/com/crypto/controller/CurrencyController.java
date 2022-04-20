package br.com.crypto.controller;

import br.com.crypto.controller.dto.CurrencyDTO;
import br.com.crypto.controller.request.CurrencyRequest;
import br.com.crypto.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CurrencyDTO> findCurrencyByName(
            @RequestParam(value = "nameCrypto", required = false) String nameCrypto,
            @RequestParam(value = "code", required = false) String code) {
        return currencyService.findCurrency(nameCrypto, code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCrypto(@RequestBody CurrencyRequest currencyRequest) {
        currencyService.save(currencyRequest);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable(value = "id") UUID id, @RequestBody CurrencyRequest currencyRequest) {
        currencyService.update(id, currencyRequest);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(value = "id") UUID id) {
        currencyService.deleteById(id);
    }
}
