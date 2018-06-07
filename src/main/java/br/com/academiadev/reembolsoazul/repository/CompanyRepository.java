package br.com.academiadev.reembolsoazul.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.reembolsoazul.model.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {

}
