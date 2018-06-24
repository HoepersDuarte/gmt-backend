package br.com.academiadev.reembolsoazul.converter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.dto.RefundRegisterDTO;
import br.com.academiadev.reembolsoazul.model.Refund;
import br.com.academiadev.reembolsoazul.model.RefundCategory;

@Component
public class RefundRegisterConverter implements Converter<Refund, RefundRegisterDTO> {

	@Override
	public RefundRegisterDTO toDTO(Refund entity) {
		throw new NotYetImplementedException();
	}

	@Override
	public Refund toEntity(RefundRegisterDTO dto) {
		Refund refund = new Refund();
		refund.setName(dto.getName());
		refund.setFile(dto.getFile());
		refund.setRefundCategory(RefundCategory.valueOf(dto.getRefundCategory()));// TODO Exceptions
		refund.setDate(LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		refund.setValue(new BigDecimal(dto.getValue()));

		return refund;
	}

}
