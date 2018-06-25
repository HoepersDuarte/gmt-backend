package br.com.academiadev.reembolsoazul.dto;

import io.swagger.annotations.ApiModelProperty;

public class EmailVerificationDTO {
	@ApiModelProperty(value = "Email a ser testado", example = "email@example.com.br")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
