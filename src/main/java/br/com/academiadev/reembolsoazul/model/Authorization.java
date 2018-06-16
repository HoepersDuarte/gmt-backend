package br.com.academiadev.reembolsoazul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "AUTH")
public class Authorization implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	public Authorization(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return name;
	}

	@JsonIgnore
	public String getName() {
		return name;
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Authorization(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Authorization() {
	}

}
