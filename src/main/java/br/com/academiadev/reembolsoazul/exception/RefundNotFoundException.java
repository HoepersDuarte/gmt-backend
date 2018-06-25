package br.com.academiadev.reembolsoazul.exception;

public class RefundNotFoundException extends Exception {
	private final String message = "Reembolso nao encontrado";

	public String getMessage() {
		return message;
	};
}
