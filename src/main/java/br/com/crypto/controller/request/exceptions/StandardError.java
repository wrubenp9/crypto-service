//Erro de requisição
package br.com.crypto.controller.request.exceptions;

import lombok.Data;

import java.time.Instant;

@Data
public class StandardError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError(){
    }

    public StandardError(Instant timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

}
