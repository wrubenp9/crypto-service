package br.com.crypto.controller.dto;

import lombok.Data;

@Data
public class CurrencyDTO {
    private Long id;
    private String nameCrypto;
    private String code;
}
