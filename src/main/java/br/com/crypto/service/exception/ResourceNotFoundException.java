package br.com.crypto.service.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super("Resource not found " + message);
    }

    public ResourceNotFoundException(Object id){
        super("Resource not found. Id: " + id);
    }
}
