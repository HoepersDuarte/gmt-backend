package br.com.academiadev.reembolsoazul.reembolsoazul;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.academiadev.reembolsoazul.config.jwt.TokenHelper;
import br.com.academiadev.reembolsoazul.controller.CompanyController;
import br.com.academiadev.reembolsoazul.controller.UserController;
import br.com.academiadev.reembolsoazul.dto.CompanyRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.LoginDTO;
import br.com.academiadev.reembolsoazul.dto.RefundRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserCompanyRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserRegisterDTO;
import br.com.academiadev.reembolsoazul.exception.CompanyNotFoundException;
import br.com.academiadev.reembolsoazul.exception.EmailAlreadyUsedException;
import br.com.academiadev.reembolsoazul.exception.InvalidEmailFormatException;
import br.com.academiadev.reembolsoazul.exception.InvalidPasswordFormatException;
import br.com.academiadev.reembolsoazul.model.Company;
import br.com.academiadev.reembolsoazul.model.Refund;
import br.com.academiadev.reembolsoazul.model.RefundCategory;
import br.com.academiadev.reembolsoazul.model.RefundStatus;
import br.com.academiadev.reembolsoazul.model.User;
import br.com.academiadev.reembolsoazul.repository.CompanyRepository;
import br.com.academiadev.reembolsoazul.repository.RefundRepository;
import br.com.academiadev.reembolsoazul.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestsWithToken {

	@Autowired
	private CompanyController companyController;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private UserController userController;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RefundRepository refundRepository;

	@Autowired
	private TokenHelper tokenHelper;

	@Autowired
	private MockMvc mockMvc;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
	
	@Test
	public void registerRefund() throws IOException, Exception {
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
		userDTO.setEmail("email2@example.com");
		userDTO.setPassword("1aA+1234");
		userDTO.setCompany(userCodeCompany1);
		try {
			userController.register(userDTO);
		} catch (CompanyNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidPasswordFormatException e) {
			e.printStackTrace();
		} catch (InvalidEmailFormatException e) {
			e.printStackTrace();
		}

		// get the user registered
		User user = userRepository.findByEmail("email2@example.com");
		Thread.sleep(1000);// o tempo de criacao do token e ultima modificacao de senha estavam em
							// conflito, provavelmente por um deles n pegar os milisegundos

		// register the refund
		RefundRegisterDTO refundDTO = new RefundRegisterDTO();
		refundDTO.setDate("10/10/2010");
		refundDTO.setFile("file");
		refundDTO.setName("RefundName");
		refundDTO.setRefundCategory("ALIMENTACAO");
		refundDTO.setValue("1000");

		String token = tokenHelper.generateToken(user.getEmail(), user.getUserType().toString(),
				user.getCompany().getName(), null);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + token);
		ResultActions result = mockMvc.perform(post("/refund/").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)
				.content(convertObjectToJsonBytes(refundDTO)));
		result.andExpect(status().isOk());

		// get the refund registered
		Refund refund = ((List<Refund>) refundRepository.findByName("RefundName")).get(0);

		Assert.assertTrue(refund.getName().equals("RefundName"));
		Assert.assertTrue(refund.getValue().compareTo(new BigDecimal("1000")) == 0);
		Assert.assertTrue(refund.getFile().equals("file"));
		Assert.assertTrue(
				refund.getDate().equals(LocalDate.parse("10/10/2010", DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		Assert.assertTrue(refund.getUser().getName().equals(user.getName()));
		Assert.assertTrue(refund.getRefundCategory().equals(RefundCategory.ALIMENTACAO));
		Assert.assertTrue(refund.getRefundStatus().equals(RefundStatus.WAITING));
	}
	
	@Test
	public void returnCompanyCodes() throws InterruptedException {
		UserCompanyRegisterDTO userCompanyRegisterDTO = new UserCompanyRegisterDTO();
		userCompanyRegisterDTO.setName("name1");
		userCompanyRegisterDTO.setEmail("email@test.com");
		userCompanyRegisterDTO.setPassword("1aA+1234");
		userCompanyRegisterDTO.setCompany("Empresa Teste 1");
		try {
			userController.registerUserCompany(userCompanyRegisterDTO);
		} catch (CompanyNotFoundException e1) {
			e1.printStackTrace();
		} catch (InvalidPasswordFormatException e1) {
			e1.printStackTrace();
		} catch (InvalidEmailFormatException e1) {
			e1.printStackTrace();
		} catch (EmailAlreadyUsedException e1) {
			e1.printStackTrace();
		}

		
		User user = userRepository.findByEmail("email@test.com");
		Thread.sleep(1000);
		
		String token = tokenHelper.generateToken(user.getEmail(), user.getUserType().toString(),
				user.getCompany().getName(), null);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + token);
		MvcResult result;
		try {
			result = mockMvc.perform(get("/company/").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)).andReturn();
			Assert.assertTrue(result.getResponse().getContentAsString().length() > 0);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}

		Assert.assertTrue(false);
	}
	
	@Test
	public void loginTest() {
		UserCompanyRegisterDTO userCompanyRegisterDTO = new UserCompanyRegisterDTO();
		userCompanyRegisterDTO.setName("name2");
		userCompanyRegisterDTO.setEmail("email2@test.com");
		userCompanyRegisterDTO.setPassword("1aA+1234");
		userCompanyRegisterDTO.setCompany("Empresa Teste 2");
		try {
			userController.registerUserCompany(userCompanyRegisterDTO);
		} catch (CompanyNotFoundException e1) {
			e1.printStackTrace();
		} catch (InvalidPasswordFormatException e1) {
			e1.printStackTrace();
		} catch (InvalidEmailFormatException e1) {
			e1.printStackTrace();
		} catch (EmailAlreadyUsedException e1) {
			e1.printStackTrace();
		}
		
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setEmail("email2@test.com");
		loginDTO.setPassword("1aA+1234");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		ResultActions result;
		try {
			result = mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8).headers(httpHeaders)
					.content(convertObjectToJsonBytes(loginDTO)));
			result.andExpect(status().isOk());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(true);
	}
}
