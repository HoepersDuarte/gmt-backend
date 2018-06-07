package br.com.academiadev.reembolsoazul.reembolsoazul;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.academiadev.reembolsoazul.controller.CompanyController;
import br.com.academiadev.reembolsoazul.dto.CompanyDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReembolsoazulApplicationTests {

	@Autowired
	private CompanyController companyController;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void registerCompany() {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setName("Empresa 100");
		companyController.register(companyDTO);
		ResponseEntity<List<CompanyDTO>> companies = companyController.getAll();
		Assert.assertEquals("Empresa 100", companies.getBody().get(0).getName());
	}
	
}
