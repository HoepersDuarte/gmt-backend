package br.com.academiadev.reembolsoazul.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "refund")
@Entity
public class Refund implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private BigDecimal value;

	@Column
	private LocalDate date;

	@Column
	private String file;// TODO como armazena arquivos de nota fiscal

	@OneToOne // TODO ver como e o mapeamento
	private User user;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private RefundCategory refundCategory;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private RefundStatus refundStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RefundCategory getRefundCategory() {
		return refundCategory;
	}

	public void setRefundCategory(RefundCategory refundCategory) {
		this.refundCategory = refundCategory;
	}

	public RefundStatus getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(RefundStatus refundStatus) {
		this.refundStatus = refundStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}