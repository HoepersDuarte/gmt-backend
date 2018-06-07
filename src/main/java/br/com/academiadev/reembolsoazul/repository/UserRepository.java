package br.com.academiadev.reembolsoazul.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.reembolsoazul.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
}