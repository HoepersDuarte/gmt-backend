package br.com.academiadev.reembolsoazul.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class RefundStatusAssignDTO {
	@ApiModelProperty(value = "Status a ser atribuido para os reembolsos", example = "ACCEPTED")
	private String status;
	
	@ApiModelProperty(value = "Ids dos reembolsos que terão o status alterado", example = "[1, 2, 3]")
	private List<Long> refunds;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Long> getRefunds() {
		return refunds;
	}

	public void setRefunds(List<Long> refundIds) {
		this.refunds = refundIds;
	}
	
}
