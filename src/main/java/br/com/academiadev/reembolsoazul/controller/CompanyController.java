package br.com.academiadev.reembolsoazul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.reembolsoazul.dto.CompanyDTO;
import br.com.academiadev.reembolsoazul.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	public ResponseEntity<CompanyDTO> register(@RequestBody CompanyDTO companyDTO) {
		companyService.register(companyDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
