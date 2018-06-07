package br.com.academiadev.reembolsoazul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.reembolsoazul.dto.CompanyDTO;
import br.com.academiadev.reembolsoazul.dto.UserDTO;
import br.com.academiadev.reembolsoazul.exception.CompanyNotFoundException;
import br.com.academiadev.reembolsoazul.service.UserService;
import io.swagger.annotations.Api;
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
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) throws CompanyNotFoundException {
		userService.register(userDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna todos os usuarios cadastrados", response = UserDTO[].class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lista recebida com sucesso")})
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAll() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}
	
}
