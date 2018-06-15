package br.com.academiadev.reembolsoazul.exception;

public abstract class APIException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String errorKey;

	protected APIException(String message, String errorKey) {
		super(message);
		this.errorKey = errorKey;
	}

	public String getErrorKey() {
		return errorKey;
	}
}
