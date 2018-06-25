package br.com.academiadev.reembolsoazul.dto;

import io.swagger.annotations.ApiModelProperty;

public class LoginDTO {

	@ApiModelProperty(value = "Email para o login", example = "admin@example.com")
	private String email;

	@ApiModelProperty(value = "Senha para o login", example = "12345aA+")
	private String password;

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

}
