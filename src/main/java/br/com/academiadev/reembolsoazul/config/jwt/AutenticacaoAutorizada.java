package br.com.academiadev.reembolsoazul.config.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AutenticacaoAutorizada extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;
	private String token;
	private final UserDetails usuario;

	public AutenticacaoAutorizada(UserDetails principle, String token) {
		super(principle.getAuthorities());
		this.usuario = principle;
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public UserDetails getPrincipal() {
		return usuario;
	}

}
