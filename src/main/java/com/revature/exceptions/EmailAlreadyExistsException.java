package com.revature.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -6384238840094678157L;

	public EmailAlreadyExistsException() {

	}

	public EmailAlreadyExistsException(String message) {
		super(message);
	}
	
	public EmailAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}
