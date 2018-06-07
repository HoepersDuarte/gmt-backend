package br.com.academiadev.reembolsoazul.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.reembolsoazul.converter.UserConverter;
import br.com.academiadev.reembolsoazul.dto.UserDTO;
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
	private UserConverter userConverter;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public void register(UserDTO userDTO) throws CompanyNotFoundException {
		//o que fica no service e o que fica no converter?
		User user = userConverter.toEntity(userDTO);
		
		getUserTypeAndCompany(user, userDTO);
		
		userRepository.save(user);
	}
	
	public List<UserDTO> findAll() {
		return Util.toList(userRepository.findAll()).stream().map(e -> {
			return userConverter.toDTO(e);
		}).collect(Collectors.toList());
	}

	private void getUserTypeAndCompany(User user, UserDTO userDTO) throws CompanyNotFoundException {
		
		//Find by admCode
		List<Company> companies = companyRepository.findByCompanyAdminCode(userDTO.getCompanyCode());
		if(companies.size() == 1) {
			user.setCompany(companies.get(0));
			user.setUserType(UserType.ADMIN);
			return;
		}
		
		//Find by userCode
		companies = companyRepository.findByCompanyUserCode(userDTO.getCompanyCode());
		if(companies.size() == 1) {
			user.setCompany(companies.get(0));
			user.setUserType(UserType.COMMONUSER);
			return;
		}
		
		throw new CompanyNotFoundException();
	}
	
}
