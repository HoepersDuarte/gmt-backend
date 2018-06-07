package br.com.academiadev.reembolsoazul.converter;

import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.dto.CompanyDTO;
import br.com.academiadev.reembolsoazul.model.Company;

@Component
public class CompanyConverter implements Converter<Company, CompanyDTO> {

	@Override
	public CompanyDTO toDTO(Company entity) {
		// TODO implementar
		return null;
	}

	@Override
	public Company toEntity(CompanyDTO dto) {
		Company company = new Company();
		company.setName(dto.getName());
		company.setCompanyAdminCode(dto.getCompanyAdminCode());
		company.setCompanyUserCode(dto.getCompanyUserCode());
		return company;
	}

}
