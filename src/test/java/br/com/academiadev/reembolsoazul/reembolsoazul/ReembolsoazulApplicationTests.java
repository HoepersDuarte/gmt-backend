package br.com.academiadev.reembolsoazul.reembolsoazul;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import br.com.academiadev.reembolsoazul.dto.RefundDTO;
import br.com.academiadev.reembolsoazul.dto.UserCompanyRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserRegisterDTO;
import br.com.academiadev.reembolsoazul.exception.CompanyNotFoundException;
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
		userRegisterDTO.setEmail("email1");
		userRegisterDTO.setPassword("password1");
		userRegisterDTO.setCompanyCode(admCodeCompany1);
		try {
			userController.register(userRegisterDTO);
		} catch (CompanyNotFoundException e) {
			e.printStackTrace();
		}

		// get the user registered
		User user = userRepository.findByEmail("email1");
		Assert.assertTrue(user.getName().equals("name1") && user.getEmail().equals("email1")
				&& passwordEncoder.matches("password1", user.getPassword())
				&& user.getUserType().equals(UserType.ROLE_ADMIN) && user.getCompany().getName().equals("Empresa 1"));
	}

	@Test
	public void registerRefund() {
		// register the company
		CompanyRegisterDTO companyRegisterDTO1 = new CompanyRegisterDTO();
		companyRegisterDTO1.setName("Empresa 1");
		companyController.register(companyRegisterDTO1);

		// get the admin code from the first company
		List<Company> companies = (List<Company>) companyRepository.findByName("Empresa 1");
		String userCodeCompany1 = companies.get(0).getCompanyUserCode();

		// register the user
		UserRegisterDTO userDTO = new UserRegisterDTO();
		userDTO.setName("name1");
		userDTO.setEmail("email2");
		userDTO.setPassword("password1");
		userDTO.setCompanyCode(userCodeCompany1);
		try {
			userController.register(userDTO);
		} catch (CompanyNotFoundException e) {
			e.printStackTrace();
		}

		// get the user registered
		User user = userRepository.findByEmail("email2");

		// register the refund
		RefundDTO refundDTO = new RefundDTO();
		refundDTO.setDate("10/10/2010");
		refundDTO.setFile("file");
		refundDTO.setName("RefundName");
		refundDTO.setRefundCategory("ALIMENTACAO");
		refundDTO.setUser(user.getId());
		refundDTO.setValue("1000");

		try {
			refundService.register(refundDTO);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}

		// get the refund registered
		Refund refund = ((List<Refund>) refundRepository.findByName("RefundName")).get(0);

		Assert.assertTrue(refund.getName().equals("RefundName")
				&& refund.getValue().compareTo(new BigDecimal("1000")) == 0 && refund.getFile().equals("file")
				&& refund.getDate().equals(LocalDate.parse("10/10/2010", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				&& refund.getUser().getName().equals(user.getName())
				&& refund.getRefundCategory().equals(RefundCategory.ALIMENTACAO)
				&& refund.getRefundStatus().equals(RefundStatus.WAITING));
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
		userCompanyRegisterDTO.setPassword("123456");
		userCompanyRegisterDTO.setCompanyName("Empresa legal");

		try {
			userController.registerUserCompany(userCompanyRegisterDTO);
		} catch (CompanyNotFoundException e) {
			e.printStackTrace();
		}

		// Find the company and user registered
		Company company = companyRepository.findByName("Empresa legal").get(0);
		User user = userRepository.findByEmail("email@pessoa.com");

		Assert.assertTrue(user.getName().equals("Pessoa 1") && user.getEmail().equals("email@pessoa.com")
				&& passwordEncoder.matches("123456", user.getPassword())
				&& user.getCompany().getName().equals("Empresa legal") && user.getUserType() == UserType.ROLE_ADMIN
				&& company.getName().equals("Empresa legal"));
	}

}
