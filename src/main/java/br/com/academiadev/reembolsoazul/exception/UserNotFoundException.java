package br.com.academiadev.reembolsoazul.exception;

public class UserNotFoundException extends APIException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("Usuario nao encontrado", "");
	}

}
