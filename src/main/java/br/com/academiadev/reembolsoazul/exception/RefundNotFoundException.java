package br.com.academiadev.reembolsoazul.exception;

public class RefundNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String message = "Reembolso nao encontrado";

	public String getMessage() {
		return message;
	};
}
