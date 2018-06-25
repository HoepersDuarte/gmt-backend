package br.com.academiadev.reembolsoazul.exception;

public class UserNotFoundException extends Exception {
	private final String message = "Usuario nao encontrado";

	public String getMessage() {
		return message;
	};
}
