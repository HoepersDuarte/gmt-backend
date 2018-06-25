package br.com.academiadev.reembolsoazul.exception;

public class InvalidPasswordFormatException extends APIException {

	private static final long serialVersionUID = 1L;

	public InvalidPasswordFormatException() {
		super("Formato invalido de senha", "");
	}

}
