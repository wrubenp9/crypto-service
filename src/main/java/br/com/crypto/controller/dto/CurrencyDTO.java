package br.com.crypto.controller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CurrencyDTO {
    private UUID id;
    private String nameCrypto;
    private String code;
}
