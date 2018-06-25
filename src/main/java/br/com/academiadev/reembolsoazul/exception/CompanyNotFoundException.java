package br.com.academiadev.reembolsoazul.exception;

public class CompanyNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private final String message = "Empresa nao encontrada";
	
	public String getMessage() {
		return message;
	};
	
}
