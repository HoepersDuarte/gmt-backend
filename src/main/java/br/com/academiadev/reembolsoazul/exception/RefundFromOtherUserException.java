package br.com.academiadev.reembolsoazul.exception;

public class RefundFromOtherUserException extends APIException {

	private static final long serialVersionUID = 1L;

	public RefundFromOtherUserException() {
		super("O reembolso pertence a outro usuario", "");
	}

}
