package br.com.academiadev.reembolsoazul.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserType implements GrantedAuthority{
	ADMIN, COMMONUSER;

	@Override
	public String getAuthority() {
		return this.toString();
	}
}
