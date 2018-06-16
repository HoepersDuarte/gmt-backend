package br.com.academiadev.reembolsoazul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.reembolsoazul.model.Authorization;
import br.com.academiadev.reembolsoazul.repository.AuthorizationRepository;

@Service
public class AuthService {

	@Autowired
	private AuthorizationRepository authorizationRepository;

	public Authorization findByNome(String nome) {
		return authorizationRepository.findByName(nome);
	}

}
