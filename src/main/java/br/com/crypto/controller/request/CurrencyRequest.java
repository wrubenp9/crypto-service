package br.com.crypto.controller.request;

import lombok.Data;

@Data
public class CurrencyRequest {
    private String nameCrypto;
    private String code;
}
