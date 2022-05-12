package br.com.crypto.controller.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyRequest {
    private String nameCrypto;
    private String code;
}
