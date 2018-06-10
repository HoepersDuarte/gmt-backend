package br.com.academiadev.reembolsoazul.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.reembolsoazul.converter.CompanyViewConverter;
import br.com.academiadev.reembolsoazul.dto.CompanyRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.CompanyViewDTO;
import br.com.academiadev.reembolsoazul.model.Company;
import br.com.academiadev.reembolsoazul.model.UserType;
import br.com.academiadev.reembolsoazul.repository.CompanyRepository;
import br.com.academiadev.reembolsoazul.util.CompanyTokenHelper;
import br.com.academiadev.reembolsoazul.util.Util;

@Service
public class CompanyService {
	@Autowired
	private CompanyViewConverter companyViewConverter;

	@Autowired
	private CompanyRepository companyRepository;

	public void register(CompanyRegisterDTO companyRegisterDTO) {
		Company company = new Company();

		company.setName(companyRegisterDTO.getName());

		setAdminAndUserCodes(companyRegisterDTO.getName(), company);

		companyRepository.save(company);
	}

	public List<CompanyViewDTO> findAll() {
		return Util.toList(companyRepository.findAll()).stream().map(e -> {
			return companyViewConverter.toDTO(e);
		}).collect(Collectors.toList());
	}

	private void setAdminAndUserCodes(String companyName, Company company) {
		company.setCompanyAdminCode(CompanyTokenHelper.generateToken(companyName, UserType.ADMIN));
		List<Company> companies = companyRepository.findByCompanyAdminCode(company.getCompanyAdminCode());
		while (!companies.isEmpty()) {
			company.setCompanyAdminCode(CompanyTokenHelper.generateToken(companyName, UserType.ADMIN));
			companies = companyRepository.findByCompanyAdminCode(company.getCompanyAdminCode());
		}

		company.setCompanyUserCode(CompanyTokenHelper.generateToken(companyName, UserType.COMMONUSER));
		companies = companyRepository.findByCompanyUserCode(company.getCompanyUserCode());
		while (!companies.isEmpty()) {
			company.setCompanyUserCode(CompanyTokenHelper.generateToken(companyName, UserType.COMMONUSER));
			companies = companyRepository.findByCompanyUserCode(company.getCompanyUserCode());
		}
	}
}
