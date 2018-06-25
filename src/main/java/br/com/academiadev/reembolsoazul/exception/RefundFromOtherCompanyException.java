package br.com.academiadev.reembolsoazul.exception;

public class RefundFromOtherCompanyException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String message = "O reembolso pertence a outra empresa";

	public String getMessage() {
		return message;
	};
}
