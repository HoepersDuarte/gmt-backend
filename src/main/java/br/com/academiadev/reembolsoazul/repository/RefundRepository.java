package br.com.academiadev.reembolsoazul.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.reembolsoazul.model.Company;
import br.com.academiadev.reembolsoazul.model.Refund;
import br.com.academiadev.reembolsoazul.model.User;

@Repository
public interface RefundRepository extends CrudRepository<Refund, Long> {
	public List<Refund> findByName(String name);

	public List<Refund> findByUser(User user);

	public List<Refund> findByUser_Company(Company company);

	public Refund findById(Long id);
}
