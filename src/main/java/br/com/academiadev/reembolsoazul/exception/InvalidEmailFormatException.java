package br.com.academiadev.reembolsoazul.exception;

public class InvalidEmailFormatException extends APIException {

	private static final long serialVersionUID = 1L;

	public InvalidEmailFormatException() {
		super("Formato invalido de email", "");
	}
}
