package br.com.academiadev.reembolsoazul.converter;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.dto.CompanyViewDTO;
import br.com.academiadev.reembolsoazul.model.Company;

@Component
public class CompanyViewConverter implements Converter<Company, CompanyViewDTO> {

	@Override
	public CompanyViewDTO toDTO(Company entity) {
		CompanyViewDTO companyDTO = new CompanyViewDTO();
		companyDTO.setName(entity.getName());
		companyDTO.setCompanyAdminCode(entity.getCompanyAdminCode());
		companyDTO.setCompanyUserCode(entity.getCompanyUserCode());
		return companyDTO;
	}

	@Override
	public Company toEntity(CompanyViewDTO dto) {
		throw new NotYetImplementedException();
	}

}
