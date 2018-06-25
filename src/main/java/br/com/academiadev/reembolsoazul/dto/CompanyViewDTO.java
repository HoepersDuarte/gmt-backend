package br.com.academiadev.reembolsoazul.dto;

import io.swagger.annotations.ApiModelProperty;

public class CompanyViewDTO {

	@ApiModelProperty(value = "Nome da empresa", example = "Empresa 1")
	private String name;

	@ApiModelProperty(value = "Codigo para cadastro de novos admins", example = "123456789")
	private String companyAdminCode;

	@ApiModelProperty(value = "Codigo para cadastro de novos usuarios comuns", example = "123456789")
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
