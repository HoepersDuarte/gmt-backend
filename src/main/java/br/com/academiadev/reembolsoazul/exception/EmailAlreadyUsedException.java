package br.com.academiadev.reembolsoazul.exception;

public class EmailAlreadyUsedException extends APIException {

	private static final long serialVersionUID = 1L;

	public EmailAlreadyUsedException() {
		super("Email ja utilizado", "");
	}

}
