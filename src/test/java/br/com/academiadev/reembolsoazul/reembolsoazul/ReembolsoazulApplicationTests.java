package br.com.academiadev.reembolsoazul.reembolsoazul;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.academiadev.reembolsoazul.controller.CompanyController;
import br.com.academiadev.reembolsoazul.controller.UserController;
import br.com.academiadev.reembolsoazul.dto.CompanyRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.RefundRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserCompanyRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserRegisterDTO;
import br.com.academiadev.reembolsoazul.exception.CompanyNotFoundException;
import br.com.academiadev.reembolsoazul.exception.InvalidEmailFormatException;
import br.com.academiadev.reembolsoazul.exception.InvalidPasswordFormatException;
import br.com.academiadev.reembolsoazul.exception.UserNotFoundException;
import br.com.academiadev.reembolsoazul.model.Company;
import br.com.academiadev.reembolsoazul.model.Refund;
import br.com.academiadev.reembolsoazul.model.RefundCategory;
import br.com.academiadev.reembolsoazul.model.RefundStatus;
import br.com.academiadev.reembolsoazul.model.User;
import br.com.academiadev.reembolsoazul.model.UserType;
import br.com.academiadev.reembolsoazul.repository.CompanyRepository;
import br.com.academiadev.reembolsoazul.repository.RefundRepository;
import br.com.academiadev.reembolsoazul.repository.UserRepository;
import br.com.academiadev.reembolsoazul.service.EmailService;
import br.com.academiadev.reembolsoazul.service.RefundService;
import br.com.academiadev.reembolsoazul.util.CompanyTokenHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReembolsoazulApplicationTests {

	@Autowired
	private CompanyController companyController;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private UserController userController;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RefundService refundService;

	@Autowired
	private RefundRepository refundRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void registerCompany() {
		CompanyRegisterDTO companyRegisterDTO = new CompanyRegisterDTO();
		companyRegisterDTO.setName("Empresa 100");
		companyController.register(companyRegisterDTO);
		List<Company> companies = (List<Company>) companyRepository.findByName("Empresa 100");
		Assert.assertEquals("Empresa 100", companies.get(0).getName());
	}

	@Test
	public void findByAdmCode() {
		// register company 1
		CompanyRegisterDTO companyRegisterDTO1 = new CompanyRegisterDTO();
		companyRegisterDTO1.setName("Empresa 1");
		companyController.register(companyRegisterDTO1);

		// register company 2
		CompanyRegisterDTO companyRegisterDTO2 = new CompanyRegisterDTO();
		companyRegisterDTO2.setName("Empresa 2");
		companyController.register(companyRegisterDTO2);

		// get the admin code from the first company
		List<Company> companies = (List<Company>) companyRepository.findByName("Empresa 1");
		String admCodeCompany1 = companies.get(0).getCompanyAdminCode();

		List<Company> companyByCode = (List<Company>) companyRepository.findByCompanyAdminCode(admCodeCompany1);
		Assert.assertEquals(companyByCode.get(0).getName(), companies.get(0).getName());
	}

	@Test
	public void registerUser() {
		// register company 1
		CompanyRegisterDTO companyRegisterDTO1 = new CompanyRegisterDTO();
		companyRegisterDTO1.setName("Empresa 1");
		companyController.register(companyRegisterDTO1);

		// register company 2
		CompanyRegisterDTO companyRegisterDTO2 = new CompanyRegisterDTO();
		companyRegisterDTO2.setName("Empresa 2");
		companyController.register(companyRegisterDTO2);

		// get the admin code from the first company
		List<Company> companies = (List<Company>) companyRepository.findByName("Empresa 1");
		String admCodeCompany1 = companies.get(0).getCompanyAdminCode();

		// register the user
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
		userRegisterDTO.setName("name1");
		userRegisterDTO.setEmail("email1@example.com");
		userRegisterDTO.setPassword("1aA+1234");
		userRegisterDTO.setCompany(admCodeCompany1);
		try {
			userController.register(userRegisterDTO);
		} catch (CompanyNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidPasswordFormatException e) {
			e.printStackTrace();
		} catch (InvalidEmailFormatException e) {
			e.printStackTrace();
		}

		// get the user registered
		User user = userRepository.findByEmail("email1@example.com");
		Assert.assertTrue(user.getName().equals("name1"));
		Assert.assertTrue(user.getEmail().equals("email1@example.com"));
		Assert.assertTrue(passwordEncoder.matches("1aA+1234", user.getPassword()));
		Assert.assertTrue(user.getUserType().equals(UserType.ROLE_ADMIN));
		Assert.assertTrue(user.getCompany().getName().equals("Empresa 1"));
	}
	
	@Test
	public void companyCodeGeneration() {
		String companyName = "Empresa 1";
		UserType userType = UserType.ROLE_ADMIN;
		String hash1 = CompanyTokenHelper.generateToken(companyName, userType);

		companyName = "Empresa 2";
		userType = UserType.ROLE_COMMONUSER;
		String hash2 = CompanyTokenHelper.generateToken(companyName, userType);

		Assert.assertTrue(!hash1.equals(hash2));
	}

	@Test
	public void registerCompanyAndUser() {
		// register company 1
		UserCompanyRegisterDTO userCompanyRegisterDTO = new UserCompanyRegisterDTO();
		userCompanyRegisterDTO.setName("Pessoa 1");
		userCompanyRegisterDTO.setEmail("email@pessoa.com");
		userCompanyRegisterDTO.setPassword("1aA+1234");
		userCompanyRegisterDTO.setCompany("Empresa legal");

		try {
			userController.registerUserCompany(userCompanyRegisterDTO);
		} catch (CompanyNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidPasswordFormatException e) {
			e.printStackTrace();
		} catch (InvalidEmailFormatException e) {
			e.printStackTrace();
		}

		// Find the company and user registered
		Company company = companyRepository.findByName("Empresa legal").get(0);
		User user = userRepository.findByEmail("email@pessoa.com");

		Assert.assertTrue(user.getName().equals("Pessoa 1"));
		Assert.assertTrue(user.getEmail().equals("email@pessoa.com"));
		Assert.assertTrue(passwordEncoder.matches("1aA+1234", user.getPassword()));
		Assert.assertTrue(user.getCompany().getName().equals("Empresa legal"));
		Assert.assertTrue(user.getUserType() == UserType.ROLE_ADMIN);
		Assert.assertTrue(company.getName().equals("Empresa legal"));
	}

//	@Test
//	public void sendEmailTest() {
//		try {
//			emailService.send("bluerefund@gmail.com", "Subject2", "Text2");
//		} catch (MessagingException e) {
//			e.printStackTrace();
//			Assert.assertTrue(false);
//		}
//		Assert.assertTrue(true);
//	}

}
