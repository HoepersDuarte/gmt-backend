package br.com.academiadev.reembolsoazul.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.reembolsoazul.model.Company;
import br.com.academiadev.reembolsoazul.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public List<User> findById(Long Id);

	public User findByEmail(String Email);
	
	public List<User> findByCompany(Company company);
}