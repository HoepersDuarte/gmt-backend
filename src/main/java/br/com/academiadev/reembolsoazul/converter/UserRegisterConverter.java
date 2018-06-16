package br.com.academiadev.reembolsoazul.converter;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.dto.UserRegisterDTO;
import br.com.academiadev.reembolsoazul.model.User;

@Component
public class UserRegisterConverter implements Converter<User, UserRegisterDTO> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserRegisterDTO toDTO(User entity) {
		throw new NotYetImplementedException();
	}

	@Override
	public User toEntity(UserRegisterDTO dto) {
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		return user;
	}

}
