package br.com.academiadev.reembolsoazul.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.reembolsoazul.model.Authorization;

@Repository
public interface AuthorizationRepository extends CrudRepository<Authorization, Long> {

	public Authorization findByNome(String nome);

}
