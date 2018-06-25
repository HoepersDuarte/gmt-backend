package br.com.academiadev.reembolsoazul.exception;

public class EmailAlreadyUsedException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String message = "Email ja utilizado";

	public String getMessage() {
		return message;
	};
}
