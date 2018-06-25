package br.com.academiadev.reembolsoazul.converter;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.dto.RefundViewDTO;
import br.com.academiadev.reembolsoazul.model.Refund;

@Component
public class RefundViewConverter implements Converter<Refund, RefundViewDTO> {

	@Override
	public RefundViewDTO toDTO(Refund entity) {
		RefundViewDTO refundDTO = new RefundViewDTO();
		refundDTO.setName(entity.getName());
		refundDTO.setFile(entity.getFile());
		refundDTO.setUser(entity.getUser().getName());
		refundDTO.setValue(entity.getValue().toString());
		refundDTO.setId(entity.getId());
		refundDTO.setRefundCategory(entity.getRefundCategory().toString());
		refundDTO.setRefundStatus(entity.getRefundStatus().toString());
		refundDTO.setDate(entity.getDate().toString());
		

		return refundDTO;
	}

	@Override
	public Refund toEntity(RefundViewDTO dto) {
		throw new NotYetImplementedException();
	}


}
