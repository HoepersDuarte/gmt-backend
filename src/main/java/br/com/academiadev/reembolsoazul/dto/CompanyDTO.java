package br.com.academiadev.reembolsoazul.dto;

public class CompanyDTO {
	// colocar codigos aqui?
	private String name;
	
	private String companyAdminCode;

	private String companyUserCode;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyAdminCode() {
		return companyAdminCode;
	}

	public void setCompanyAdminCode(String companyAdminCode) {
		this.companyAdminCode = companyAdminCode;
	}

	public String getCompanyUserCode() {
		return companyUserCode;
	}

	public void setCompanyUserCode(String companyUserCode) {
		this.companyUserCode = companyUserCode;
	}

}
