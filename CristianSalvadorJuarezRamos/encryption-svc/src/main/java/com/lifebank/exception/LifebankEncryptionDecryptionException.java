package com.lifebank.exception;

public class LifebankEncryptionDecryptionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String errorCode;
	private final String process;
	private final String method;
	
	public LifebankEncryptionDecryptionException(String errorCode, String process, String method) {
		super();
		this.errorCode = errorCode;
		this.process = process;
		this.method = method;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getProcess() {
		return process;
	}

	public String getMethod() {
		return method;
	}
}
