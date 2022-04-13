package br.com.crypto.controller;

import br.com.crypto.controller.dto.CurrencyDTO;
import br.com.crypto.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CurrencyDTO> findAll() {
        return currencyService.findAll();
    }

/*
    Busca
        Recebe:
            Como parâmetro os filtros: name e code (ambos opcionais)
        Retorna:
            @ResponseStatus(HttpStatus.OK)
*/

/*
    Salvar
        Recebe:
            - Name e code (obrigatorios)
        Retorna:
            @ResponseStatus(HttpStatus.CREATED) //criado
*/

/*
    Editar
        Recebe:
            - Parâmetro via path ID
            - Name e code (opcionais)
        Retorna:
            @ResponseStatus(HttpStatus.OK) //sucesso
*/

/*
    Remover
        Recebe:
            - Parâmetro via path ID
        Retorna:
            @ResponseStatus(HttpStatus.OK) //sucesso
*/


}
