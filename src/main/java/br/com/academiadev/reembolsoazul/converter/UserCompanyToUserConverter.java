package br.com.academiadev.reembolsoazul.converter;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.dto.UserCompanyRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.UserRegisterDTO;

@Component
public class UserCompanyToUserConverter implements Converter<UserCompanyRegisterDTO, UserRegisterDTO>{

	@Override
	public UserRegisterDTO toDTO(UserCompanyRegisterDTO entity) {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
		userRegisterDTO.setName(entity.getName());
		userRegisterDTO.setEmail(entity.getEmail());
		userRegisterDTO.setPassword(entity.getPassword());
		return userRegisterDTO;
	}

	@Override
	public UserCompanyRegisterDTO toEntity(UserRegisterDTO dto) {
		throw new NotYetImplementedException();
	}

}
