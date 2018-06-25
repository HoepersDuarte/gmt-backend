package br.com.academiadev.reembolsoazul.exception;

public class InvalidEmailFormatException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private final String message = "Formato invalido de email";

	public String getMessage() {
		return message;
	};
}
