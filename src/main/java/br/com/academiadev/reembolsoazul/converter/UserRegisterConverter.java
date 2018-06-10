package br.com.academiadev.reembolsoazul.converter;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.dto.UserRegisterDTO;
import br.com.academiadev.reembolsoazul.model.User;

@Component
public class UserRegisterConverter implements Converter<User, UserRegisterDTO> {

	@Override
	public UserRegisterDTO toDTO(User entity) {
		throw new NotYetImplementedException();
	}

	@Override
	public User toEntity(UserRegisterDTO dto) {
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		return user;
	}

}
