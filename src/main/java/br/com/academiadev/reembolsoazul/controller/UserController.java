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

import br.com.academiadev.reembolsoazul.dto.UserCompanyRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserViewDTO;
import br.com.academiadev.reembolsoazul.exception.CompanyNotFoundException;
import br.com.academiadev.reembolsoazul.exception.InvalidEmailFormatException;
import br.com.academiadev.reembolsoazul.exception.InvalidPasswordFormatException;
import br.com.academiadev.reembolsoazul.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("User")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Cadastra um usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Usuario cadastrado com sucesso") })
	@PostMapping("/")
	public ResponseEntity<UserRegisterDTO> register(@RequestBody UserRegisterDTO userRegisterDTO)
			throws CompanyNotFoundException, InvalidPasswordFormatException, InvalidEmailFormatException {
		userService.save(userRegisterDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Cadastra um usuario e uma empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Usuario e empresa cadastrados com sucesso") })
	@PostMapping("/newCompany")
	public ResponseEntity<UserRegisterDTO> registerUserCompany(
			@RequestBody UserCompanyRegisterDTO userCompanyRegisterDTO) throws CompanyNotFoundException, InvalidPasswordFormatException, InvalidEmailFormatException {
		userService.saveUserCompany(userCompanyRegisterDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna todos os usuarios cadastrados", response = UserRegisterDTO[].class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lista recebida com sucesso") })
	@ApiImplicitParams({ //
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") //
	})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/")
	public ResponseEntity<List<UserViewDTO>> getAll() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

}
