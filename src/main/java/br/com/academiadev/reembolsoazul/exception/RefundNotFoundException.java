package br.com.academiadev.reembolsoazul.exception;

public class RefundNotFoundException extends APIException {

	private static final long serialVersionUID = 1L;

	public RefundNotFoundException() {
		super("Reembolso nao encontrado", "");
	}

}
