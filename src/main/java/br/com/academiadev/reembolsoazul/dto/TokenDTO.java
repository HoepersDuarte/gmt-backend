package br.com.academiadev.reembolsoazul.dto;

public class TokenDTO {

	private String access_token;
	private Long expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}

	public TokenDTO(String access_token, Long expires_in) {
		super();
		this.access_token = access_token;
		this.expires_in = expires_in;
	}

	public TokenDTO() {
	}

}
