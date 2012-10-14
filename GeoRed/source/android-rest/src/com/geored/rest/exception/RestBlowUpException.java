package com.geored.rest.exception;

public class RestBlowUpException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RestBlowUpException(String message) {
		super(message);
	}
	
	public RestBlowUpException() {
		super();
	}

}
