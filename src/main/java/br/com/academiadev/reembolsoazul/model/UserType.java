package br.com.academiadev.reembolsoazul.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserType implements GrantedAuthority{
	ROLE_ADMIN("ROLE_ADMIN"), ROLE_COMMONUSER("ROLE_COMMONUSER");
	
	
	
	private UserType(String name) {
		this.name = name;
	}

	private String name;

	@Override
	public String getAuthority() {
		return this.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
