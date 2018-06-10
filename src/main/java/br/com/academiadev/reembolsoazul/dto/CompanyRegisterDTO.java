package br.com.academiadev.reembolsoazul.dto;

import io.swagger.annotations.ApiModelProperty;

public class CompanyRegisterDTO {

	@ApiModelProperty(value = "Nome da empresa", example = "Empresa 1")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
