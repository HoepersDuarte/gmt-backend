package br.com.academiadev.reembolsoazul.exception;

public class EmailAlreadyUsedException extends Exception {
	private final String message = "Email ja utilizado";

	public String getMessage() {
		return message;
	};
}
