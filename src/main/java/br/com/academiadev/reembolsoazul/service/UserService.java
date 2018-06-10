package br.com.academiadev.reembolsoazul.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.reembolsoazul.converter.UserRegisterConverter;
import br.com.academiadev.reembolsoazul.converter.UserViewConverter;
import br.com.academiadev.reembolsoazul.dto.UserRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserViewDTO;
import br.com.academiadev.reembolsoazul.exception.CompanyNotFoundException;
import br.com.academiadev.reembolsoazul.model.Company;
import br.com.academiadev.reembolsoazul.model.User;
import br.com.academiadev.reembolsoazul.model.UserType;
import br.com.academiadev.reembolsoazul.repository.CompanyRepository;
import br.com.academiadev.reembolsoazul.repository.UserRepository;
import br.com.academiadev.reembolsoazul.util.Util;

@Service
public class UserService {

	@Autowired
	private UserRegisterConverter userRegisterConverter;

	@Autowired
	private UserViewConverter userViewConverter;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompanyRepository companyRepository;

	public void register(UserRegisterDTO userRegisterDTO) throws CompanyNotFoundException {
		User user = userRegisterConverter.toEntity(userRegisterDTO);

		getUserTypeAndCompany(user, userRegisterDTO.getCompanyCode());

		userRepository.save(user);
	}

	public List<UserViewDTO> findAll() {
		return Util.toList(userRepository.findAll()).stream().map(e -> {
			return userViewConverter.toDTO(e);
		}).collect(Collectors.toList());
	}

	private void getUserTypeAndCompany(User user, String companyCode) throws CompanyNotFoundException {

		// Find by admCode
		List<Company> companies = companyRepository.findByCompanyAdminCode(companyCode);
		if (companies.size() == 1) {
			user.setCompany(companies.get(0));
			user.setUserType(UserType.ADMIN);
			return;
		}

		// Find by userCode
		companies = companyRepository.findByCompanyUserCode(companyCode);
		if (companies.size() == 1) {
			user.setCompany(companies.get(0));
			user.setUserType(UserType.COMMONUSER);
			return;
		}

		throw new CompanyNotFoundException();
	}

}
