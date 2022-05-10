package br.com.crypto.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class CurrencyDTO {
    private UUID id;
    private String nameCrypto;
    private String code;
    private OffsetDateTime createdAt;
}
