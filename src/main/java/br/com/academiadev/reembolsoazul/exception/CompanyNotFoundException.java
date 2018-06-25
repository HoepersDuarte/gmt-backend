package br.com.academiadev.reembolsoazul.exception;

public class CompanyNotFoundException extends Exception {
	private final String message = "Empresa nao encontrada";

	public String getMessage() {
		return message;
	};
	
}
