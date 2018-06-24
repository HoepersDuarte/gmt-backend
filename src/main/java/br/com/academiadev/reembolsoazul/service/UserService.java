package br.com.academiadev.reembolsoazul.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.academiadev.reembolsoazul.converter.UserCompanyToUserConverter;
import br.com.academiadev.reembolsoazul.converter.UserRegisterConverter;
import br.com.academiadev.reembolsoazul.converter.UserViewConverter;
import br.com.academiadev.reembolsoazul.dto.CompanyRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserCompanyRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserViewDTO;
import br.com.academiadev.reembolsoazul.exception.CompanyNotFoundException;
import br.com.academiadev.reembolsoazul.exception.InvalidEmailFormatException;
import br.com.academiadev.reembolsoazul.exception.InvalidPasswordFormatException;
import br.com.academiadev.reembolsoazul.model.Company;
import br.com.academiadev.reembolsoazul.model.User;
import br.com.academiadev.reembolsoazul.model.UserType;
import br.com.academiadev.reembolsoazul.repository.CompanyRepository;
import br.com.academiadev.reembolsoazul.repository.UserRepository;
import br.com.academiadev.reembolsoazul.util.Util;
import br.com.academiadev.reembolsoazul.util.ValidationsHelper;

@Service
public class UserService {

	@Autowired
	private UserRegisterConverter userRegisterConverter;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserViewConverter userViewConverter;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserCompanyToUserConverter userCompanyToUserConverter;

	public void save(UserRegisterDTO userRegisterDTO) throws CompanyNotFoundException, InvalidPasswordFormatException, InvalidEmailFormatException {
		User user = userRegisterConverter.toEntity(userRegisterDTO);

		getUserTypeAndCompany(user, userRegisterDTO.getCompany());
		
		if(!ValidationsHelper.passwordFormatValidation(userRegisterDTO.getPassword())) {
			throw new InvalidPasswordFormatException();
		}
		
		if(!ValidationsHelper.emailValidation(userRegisterDTO.getEmail())) {
			throw new InvalidEmailFormatException();
		}
		user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
		user.setLastPasswordChange(LocalDateTime.now());

		userRepository.save(user);
	}

	@Transactional
	public void saveUserCompany(UserCompanyRegisterDTO userCompanyRegisterDTO) throws CompanyNotFoundException, InvalidPasswordFormatException, InvalidEmailFormatException {

		CompanyRegisterDTO companyRegisterDTO = new CompanyRegisterDTO();
		companyRegisterDTO.setName(userCompanyRegisterDTO.getCompany());
		companyService.register(companyRegisterDTO);

		UserRegisterDTO userRegisterDTO = userCompanyToUserConverter.toDTO(userCompanyRegisterDTO);
		userRegisterDTO.setCompany(this.findCompanyAdminCodeByName(userCompanyRegisterDTO.getCompany()));

		this.save(userRegisterDTO);
	}

	private String findCompanyAdminCodeByName(String name) throws CompanyNotFoundException {
		List<Company> companies = companyRepository.findByName(name);
		if (companies.size() > 0) {
			return companies.get(companies.size() - 1).getCompanyAdminCode();
		} else {
			throw new CompanyNotFoundException();
		}
	}

	public User findById(Long id) throws AccessDeniedException {
		return userRepository.findOne(id);
	}

	public User findByUsername(String email) throws UsernameNotFoundException {
		return this.findByEmail(email);
	}

	public User findByEmail(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email);
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
			user.setUserType(UserType.ROLE_ADMIN);
			return;
		}

		// Find by userCode
		companies = companyRepository.findByCompanyUserCode(companyCode);
		if (companies.size() == 1) {
			user.setCompany(companies.get(0));
			user.setUserType(UserType.ROLE_COMMONUSER);
			return;
		}

		throw new CompanyNotFoundException();
	}

}
