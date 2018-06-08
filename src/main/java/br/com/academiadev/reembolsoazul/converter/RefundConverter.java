package br.com.academiadev.reembolsoazul.converter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.dto.RefundDTO;
import br.com.academiadev.reembolsoazul.model.Refund;
import br.com.academiadev.reembolsoazul.model.RefundCategory;

@Component
public class RefundConverter implements Converter<Refund, RefundDTO> {

	@Override
	public RefundDTO toDTO(Refund entity) {
		RefundDTO refundDTO = new RefundDTO();
		refundDTO.setName(entity.getName());
		refundDTO.setFile(entity.getFile());
		refundDTO.setUser(entity.getUser().getId());// coloca o id aqui?
		refundDTO.setValue(entity.getValue().toString());
		refundDTO.setRefundCategory(entity.getRefundCategory().toString());
		refundDTO.setDate(entity.getDate().toString());

		return refundDTO;
	}

	@Override
	public Refund toEntity(RefundDTO dto) {
		Refund refund = new Refund();
		refund.setName(dto.getName());
		refund.setFile(dto.getFile());
		refund.setRefundCategory(RefundCategory.valueOf(dto.getRefundCategory()));// TODO Exceptions
		refund.setDate(LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		refund.setValue(new BigDecimal(dto.getValue()));
		// user adicionado no service

		return refund;
	}

}
