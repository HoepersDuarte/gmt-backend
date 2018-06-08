package br.com.academiadev.reembolsoazul.dto;

public class RefundDTO {

	private String name;

	private String value;

	private String date;

	private String file;

	private Long user;

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

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public String getRefundCategory() {
		return refundCategory;
	}

	public void setRefundCategory(String refundCategory) {
		this.refundCategory = refundCategory;
	}

}
