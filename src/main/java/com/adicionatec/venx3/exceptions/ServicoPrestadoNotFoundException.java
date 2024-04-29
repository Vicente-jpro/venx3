package com.adicionatec.venx3.exceptions;

public class ServicoPrestadoNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ServicoPrestadoNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
