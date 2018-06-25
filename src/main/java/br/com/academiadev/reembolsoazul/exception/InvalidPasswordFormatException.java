package br.com.academiadev.reembolsoazul.exception;

public class InvalidPasswordFormatException extends Exception {
	private final String message = "Formato invalido de senha";

	public String getMessage() {
		return message;
	};
}
