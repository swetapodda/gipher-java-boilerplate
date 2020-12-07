package com.stackroute.accountmanager.exception;

public class GipherUserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public GipherUserAlreadyExistsException(String errorMessage) {
		super(errorMessage);
	}

}
