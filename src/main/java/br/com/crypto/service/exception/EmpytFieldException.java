package br.com.crypto.service.exception;

public class EmpytFieldException extends Exception {
    private static final long serialVersionUID = 1L;

    public EmpytFieldException(String msg) {
        super(msg);
    }
}
