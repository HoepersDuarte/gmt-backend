package br.com.academiadev.reembolsoazul.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.academiadev.reembolsoazul.converter.RefundRegisterConverter;
import br.com.academiadev.reembolsoazul.converter.RefundViewConverter;
import br.com.academiadev.reembolsoazul.dto.RefundRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.RefundViewDTO;
import br.com.academiadev.reembolsoazul.exception.UserNotFoundException;
import br.com.academiadev.reembolsoazul.model.Refund;
import br.com.academiadev.reembolsoazul.model.RefundStatus;
import br.com.academiadev.reembolsoazul.model.User;
import br.com.academiadev.reembolsoazul.repository.RefundRepository;
import br.com.academiadev.reembolsoazul.repository.UserRepository;
import br.com.academiadev.reembolsoazul.util.Util;

@Service
public class RefundService {

	@Autowired
	private RefundRegisterConverter refundRegisterConverter;
	
	@Autowired
	private RefundViewConverter refundViewConverter;

	@Autowired
	private RefundRepository refundRepository;

	@Autowired
	private UserRepository userRepository;

	public void register(RefundRegisterDTO refundDTO) throws UserNotFoundException {
		Refund refund = refundRegisterConverter.toEntity(refundDTO);
		refund.setRefundStatus(RefundStatus.WAITING);
		refund.setUser(findUserByToken());
		

		refundRepository.save(refund);
	}

	public List<RefundViewDTO> findAll() {
		return Util.toList(refundRepository.findAll()).stream().map(e -> {
			return refundViewConverter.toDTO(e);
		}).collect(Collectors.toList());
	}

	private User findUserByToken() throws UserNotFoundException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) authentication.getPrincipal();
		User user = userRepository.findByEmail(userDetail.getUsername());
		if(user != null) {
			return user;
		}
		throw new UserNotFoundException();
	}

}
