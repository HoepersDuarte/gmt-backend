package br.com.academiadev.reembolsoazul.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserRegisterDTO {

	@ApiModelProperty(value = "Nome do usuario", example = "Nome exemplo")
	private String name;

	@ApiModelProperty(value = "Email do usuario", example = "example@example.com.br")
	private String email;

	@ApiModelProperty(value = "Senha do usuario", example = "123456")
	private String password;

	@ApiModelProperty(value = "Codigo fornecido pela empresa", example = "codigoEmpresa")
	private String companyCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
