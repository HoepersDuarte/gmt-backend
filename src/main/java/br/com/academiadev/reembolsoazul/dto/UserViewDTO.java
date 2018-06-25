package br.com.academiadev.reembolsoazul.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserViewDTO {
	@ApiModelProperty(value = "Nome do usuario", example = "Usuario exemplo")
	private String name;

	@ApiModelProperty(value = "Email do usuario", example = "user@example.com")
	private String email;

	@ApiModelProperty(value = "Hash da senha do usuario", example = "123hfd21afs556asd4")
	private String password;

	@ApiModelProperty(value = "Id da empresa do usuario", example = "1")
	private Long companyId;

	@ApiModelProperty(value = "Tipo do usuario", example = "ROLE_ADMIN")
	private String userType;

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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
