package com.adicionatec.venx3.exceptions;

public class ClienteNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClienteNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
