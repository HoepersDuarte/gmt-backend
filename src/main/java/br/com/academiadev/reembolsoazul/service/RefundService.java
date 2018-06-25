package br.com.academiadev.reembolsoazul.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.academiadev.reembolsoazul.converter.RefundRegisterConverter;
import br.com.academiadev.reembolsoazul.converter.RefundViewConverter;
import br.com.academiadev.reembolsoazul.dto.RefundRegisterDTO;
import br.com.academiadev.reembolsoazul.dto.RefundStatusAssignDTO;
import br.com.academiadev.reembolsoazul.dto.RefundViewDTO;
import br.com.academiadev.reembolsoazul.exception.RefundFromOtherCompanyException;
import br.com.academiadev.reembolsoazul.exception.RefundNotFoundException;
import br.com.academiadev.reembolsoazul.exception.UserNotFoundException;
import br.com.academiadev.reembolsoazul.model.Refund;
import br.com.academiadev.reembolsoazul.model.RefundCategory;
import br.com.academiadev.reembolsoazul.model.RefundStatus;
import br.com.academiadev.reembolsoazul.model.User;
import br.com.academiadev.reembolsoazul.model.UserType;
import br.com.academiadev.reembolsoazul.repository.RefundRepository;
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
	private UserService userService;

	public void register(RefundRegisterDTO refundDTO) throws UserNotFoundException {
		Refund refund = refundRegisterConverter.toEntity(refundDTO);
		refund.setRefundStatus(RefundStatus.WAITING);
		refund.setUser(userService.findUserByToken());

		refundRepository.save(refund);
	}

	public List<RefundViewDTO> findAll() throws UserNotFoundException {

		User user = userService.findUserByToken();
		UserType userType = user.getUserType();

		if (userType == UserType.ROLE_ADMIN) {
			return Util.toList(refundRepository.findByUser_Company(user.getCompany())).stream().map(e -> {
				return refundViewConverter.toDTO(e);
			}).collect(Collectors.toList());
		} else if (userType == UserType.ROLE_COMMONUSER) {
			return Util.toList(refundRepository.findByUser(user)).stream().map(e -> {
				return refundViewConverter.toDTO(e);
			}).collect(Collectors.toList());
		}
		throw new UserNotFoundException();
	}

	public List<String> findAllCategories() {
		RefundCategory[] values = RefundCategory.values();
		List<String> categories = new ArrayList<String>();

		for (RefundCategory c : values) {
			categories.add(c.toString());
		}

		return categories;
	}

	@Transactional
	public void statusAssign(RefundStatusAssignDTO refundStatusAssignDTO)
			throws UserNotFoundException, RefundFromOtherCompanyException, RefundNotFoundException {
		User companyAdmin = userService.findUserByToken();

		for (Long id : refundStatusAssignDTO.getRefunds()) {
			Refund refund = refundRepository.findById(id);
			if (refund == null) {
				throw new RefundNotFoundException();
			}
			if (refund.getUser().getCompany() != companyAdmin.getCompany()) {
				throw new RefundFromOtherCompanyException();
			}
			refund.setRefundStatus(RefundStatus.valueOf(refundStatusAssignDTO.getStatus()));
			refundRepository.save(refund);
		}
	}

}
