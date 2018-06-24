package br.com.academiadev.reembolsoazul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.reembolsoazul.dto.CompanyRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.CompanyViewDTO;
import br.com.academiadev.reembolsoazul.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Company")
@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@ApiOperation(value = "Cadastra uma empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Empresa cadastrada com sucesso") })
	@PostMapping("/")
	public ResponseEntity<CompanyRegisterDTO> register(@RequestBody CompanyRegisterDTO companyRegisterDTO) {
		companyService.register(companyRegisterDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna todas as empresas cadastradas", response = CompanyViewDTO[].class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lista recebida com sucesso") })
	@ApiImplicitParams({ //
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@GetMapping("/")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<CompanyViewDTO>> getAll() {
		return new ResponseEntity<>(companyService.findAll(), HttpStatus.OK);
	}
}
