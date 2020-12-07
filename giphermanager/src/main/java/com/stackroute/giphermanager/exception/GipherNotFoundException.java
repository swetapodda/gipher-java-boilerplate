package com.stackroute.giphermanager.exception;

public class GipherNotFoundException extends Exception {


	private static final long serialVersionUID = 1L;

	public GipherNotFoundException(String errorMessage) {
		super(errorMessage);
	}

	public GipherNotFoundException(String errorMessage, Exception e) {
		super(errorMessage, e);
	}

}
