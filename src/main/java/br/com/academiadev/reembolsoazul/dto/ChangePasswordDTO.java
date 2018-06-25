package br.com.academiadev.reembolsoazul.dto;

import io.swagger.annotations.ApiModelProperty;

public class ChangePasswordDTO {
	@ApiModelProperty(value = "Senha antiga", example = "123456")
	public String oldPassword;

	@ApiModelProperty(value = "Nova senha", example = "12345aA+")
	public String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
