package br.com.academiadev.reembolsoazul.dto;

import io.swagger.annotations.ApiModelProperty;

public class RefundRegisterDTO {

	@ApiModelProperty(value = "Nome do reembolso", example = "Reembolso exemplo")
	private String name;

	@ApiModelProperty(value = "Valor do reembolso", example = "1550")
	private String value;

	@ApiModelProperty(value = "Data do reembolso", example = "01/01/2018")
	private String date;

	@ApiModelProperty(value = "Nota fiscal do reembolso", example = "notafiscal.txt")
	private String file;

	@ApiModelProperty(value = "Categoria do reembolso", example = "ALIMENTACAO")
	private String refundCategory;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getRefundCategory() {
		return refundCategory;
	}

	public void setRefundCategory(String refundCategory) {
		this.refundCategory = refundCategory;
	}

}
