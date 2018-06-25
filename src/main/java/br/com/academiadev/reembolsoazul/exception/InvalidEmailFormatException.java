package br.com.academiadev.reembolsoazul.exception;

public class InvalidEmailFormatException extends Exception {
	private final String message = "Formato invalido de email";

	public String getMessage() {
		return message;
	};
}
