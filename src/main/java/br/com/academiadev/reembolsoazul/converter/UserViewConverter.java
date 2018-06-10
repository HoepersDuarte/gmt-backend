package br.com.academiadev.reembolsoazul.converter;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.dto.UserViewDTO;
import br.com.academiadev.reembolsoazul.model.User;

@Component
public class UserViewConverter implements Converter<User, UserViewDTO> {
	@Override
	public UserViewDTO toDTO(User entity) {
		UserViewDTO userViewDTO = new UserViewDTO();
		userViewDTO.setName(entity.getName());
		userViewDTO.setEmail(entity.getEmail());
		userViewDTO.setPassword(entity.getPassword());
		userViewDTO.setCompanyId(entity.getCompany().getId());
		userViewDTO.setUserType(entity.getUserType().toString());
		return userViewDTO;
	}

	@Override
	public User toEntity(UserViewDTO dto) {
		throw new NotYetImplementedException();
	}
}
