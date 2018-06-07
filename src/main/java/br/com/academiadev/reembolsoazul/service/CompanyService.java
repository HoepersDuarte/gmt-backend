package br.com.academiadev.reembolsoazul.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.reembolsoazul.converter.CompanyConverter;
import br.com.academiadev.reembolsoazul.dto.CompanyDTO;
import br.com.academiadev.reembolsoazul.model.Company;
import br.com.academiadev.reembolsoazul.repository.CompanyRepository;
import br.com.academiadev.reembolsoazul.util.Util;

@Service
public class CompanyService {
	@Autowired
	private CompanyConverter companyConverter;

	@Autowired
	private CompanyRepository companyRepository;

	public void register(CompanyDTO companyDTO) {
		Company company = companyConverter.toEntity(companyDTO);

		// gerar codigos de admin e user aqui ou no converter?
		// TODO gerar codigos de admin de user
		company.setCompanyAdminCode("adminCode");
		company.setCompanyUserCode("userCode");
		companyRepository.save(company);
	}

	public List<CompanyDTO> findAll() {
		return Util.toList(companyRepository.findAll()).stream().map(e -> {
			return companyConverter.toDTO(e);
		}).collect(Collectors.toList());
	}
}
