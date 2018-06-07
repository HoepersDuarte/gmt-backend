package br.com.academiadev.reembolsoazul.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.academiadev.reembolsoazul.model.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
	public List<Company> findByCompanyAdminCode(String companyAdminCode);
	
	public List<Company> findByCompanyUserCode(String companyUserCode);
}
