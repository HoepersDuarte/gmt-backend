package br.com.academiadev.reembolsoazul.exception;

public class RefundFromOtherCompanyException extends APIException {

	private static final long serialVersionUID = 1L;

	public RefundFromOtherCompanyException() {
		super("O reembolso pertence a outra empresa", "");
	}

}
