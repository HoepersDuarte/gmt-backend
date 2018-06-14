package br.com.academiadev.reembolsoazul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.auth.model.Autorizacao;
import br.com.academiadev.auth.repository.AutorizacaoRepository;

@Service
public class AuthService {

	@Autowired
	private AutorizacaoRepository autorizacaoRepository;

	public Authorization findByNome(String nome) {
		return autorizacaoRepository.findByNome(nome);
	}

}
