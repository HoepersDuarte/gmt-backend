package br.com.academiadev.reembolsoazul.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String message = "Usuario nao encontrado";

	public String getMessage() {
		return message;
	};
}
