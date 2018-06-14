package br.com.academiadev.reembolsoazul.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenFilter extends OncePerRequestFilter {

	private TokenHelper tokenHelper;
	private UserDetailsService userDetailsService;

	public TokenFilter(TokenHelper tokenHelper, UserDetailsService userDetailsService) {
		this.tokenHelper = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String token = tokenHelper.getToken(request);
		if (token != null) {
			String usuarioDoToken = tokenHelper.getUsuario(token);
			if (usuarioDoToken != null) {
				UserDetails usuario = userDetailsService.loadUserByUsername(usuarioDoToken);
				if (tokenHelper.validarToken(token, usuario)) {
					SecurityContextHolder.getContext().setAuthentication(new AutenticacaoAutorizada(usuario, token));
				}
			}
		}
		chain.doFilter(request, response);
	}

}