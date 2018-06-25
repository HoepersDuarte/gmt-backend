package br.com.academiadev.reembolsoazul.exception;

public class CompanyNotFoundException extends APIException {
	private static final long serialVersionUID = 1L;

	public CompanyNotFoundException() {
		super("Empresa nao encontrada", "");
	}

}
