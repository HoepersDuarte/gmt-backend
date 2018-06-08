package br.com.academiadev.reembolsoazul.converter;

import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.dto.UserDTO;
import br.com.academiadev.reembolsoazul.model.User;

@Component
public class UserConverter implements Converter<User, UserDTO> {

	@Override
	public UserDTO toDTO(User entity) {
		UserDTO userDTO = new UserDTO();
		userDTO.setName(entity.getName());
		userDTO.setEmail(entity.getEmail());
		userDTO.setPassword(entity.getPassword());
		userDTO.setCompanyCode(entity.getCompany().getName());
		// TODO como vai fazer da empresa
		return userDTO;
	}

	@Override
	public User toEntity(UserDTO dto) {
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		// TODO como vai fazer da empresa
		return user;
	}

}
