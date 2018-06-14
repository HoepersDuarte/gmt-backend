package br.com.academiadev.reembolsoazul.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.academiadev.reembolsoazul.config.jwt.TokenHelper;
import br.com.academiadev.reembolsoazul.dto.LoginDTO;
import br.com.academiadev.reembolsoazul.dto.ChangePasswordDTO;
import br.com.academiadev.reembolsoazul.model.TokenDTO;
import br.com.academiadev.reembolsoazul.model.User;
//import br.com.academiadev.reembolsoazul.service.impl.CustomUserDetailsService;
import br.com.academiadev.reembolsoazul.common.DeviceProvider;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

	@Autowired
	private TokenHelper tokenHelper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private DeviceProvider deviceProvider;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody LoginDTO authRequest, HttpServletResponse response, Device device) throws AuthenticationException, IOException {
		final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = (User) authentication.getPrincipal();
		String token = tokenHelper.gerarToken(user.getEmail(), device);
		int expiresIn = tokenHelper.getExpiredIn(device);
		return ResponseEntity.ok(new TokenDTO(token, Long.valueOf(expiresIn)));
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAuth(HttpServletRequest request, HttpServletResponse response, Principal principal) {
		String token = tokenHelper.getToken(request);
		Device device = deviceProvider.getDispositivo(request);
		if (token != null && principal != null) {
			String updatedToken = tokenHelper.updateToken(token, device);
			int expires_in = tokenHelper.getExpiredIn(device);
			return ResponseEntity.ok(new TokenDTO(updatedToken, Long.valueOf(expires_in)));
		} else {
			TokenDTO tokenDTO = new TokenDTO();
			return ResponseEntity.accepted().body(tokenDTO);
		}
	}

	@RequestMapping(value = "/isauth", method = RequestMethod.POST)
	public ResponseEntity<?> isAuthenticated(HttpServletRequest request) {
		String token = tokenHelper.getToken(request);

		if (token != null) {
			String tokenUser = tokenHelper.getUser(token);
			if (tokenUser != null) {
				UserDetails usuario = userDetailsService.loadUserByUsername(usuarioDoToken);
				if (tokenHelper.validarToken(token, usuario)) {
					Map<String, String> result = new HashMap<>();
					result.put("isAuth", "true");
					return ResponseEntity.ok().body(result);
				}
			}
		}

		Map<String, String> result = new HashMap<>();
		result.put("isAuth", "false");
		return ResponseEntity.ok().body(result);
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> trocarSenha(@RequestBody ChangePasswordDTO trocaSenhaDTO) {
		userDetailsService.trocarSenha(trocaSenhaDTO.oldPassword, trocaSenhaDTO.newPassword);
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

}