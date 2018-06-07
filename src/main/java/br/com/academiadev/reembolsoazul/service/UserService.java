package br.com.academiadev.reembolsoazul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.reembolsoazul.converter.UserConverter;
import br.com.academiadev.reembolsoazul.dto.UserDTO;
import br.com.academiadev.reembolsoazul.model.Company;
import br.com.academiadev.reembolsoazul.model.User;
import br.com.academiadev.reembolsoazul.model.UserType;
import br.com.academiadev.reembolsoazul.repository.CompanyRepository;
import br.com.academiadev.reembolsoazul.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public void register(UserDTO userDTO) {
		//o que fica no service e o que fica no converter?
		User user = userConverter.toEntity(userDTO);
		
		List<Company> company = findCompanyByCode(userDTO);
		UserType userType = getUserType(company, userDTO);
		
		user.setCompany(company.get(0));
		user.setUserType(userType);
		
		userRepository.save(user);
	}

	private UserType getUserType(List<Company> company, UserDTO userDTO) {
		if(company.get(0).getCompanyAdminCode() == userDTO.getCompanyCode()) {
			return UserType.ADMIN;
		}else if(company.get(0).getCompanyUserCode() == userDTO.getCompanyCode()) {
			return UserType.COMMONUSER;
		}else {
			//TODO criar Exception
			System.out.println("Nao achou o tipo");
		}
		return null;
	}

	private List<Company> findCompanyByCode(UserDTO userDTO) {
		List<Company> company = companyRepository.findByCompanyAdminCode(userDTO.getCompanyCode());
		if(company.isEmpty()) {
			company = companyRepository.findByCompanyUserCode(userDTO.getCompanyCode());
		}
		
		if(company.size() != 1) {
			//TODO criar Exception
			System.out.println("Nao achou empresa");
		}
		return company;
	}
	
}
