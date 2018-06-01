package br.com.academiadev.reembolsoazul.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "company")
@Entity
public class Company implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String companyAdminCode;
	
	@Column
	private String companyUserCode;
	
	@OneToMany(mappedBy = "company")
	private List<User> users;

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

	public String getCompanyAdminCode() {
		return companyAdminCode;
	}

	public void setCompanyAdminCode(String companyAdminCode) {
		this.companyAdminCode = companyAdminCode;
	}

	public String getCompanyUserCode() {
		return companyUserCode;
	}

	public void setCompanyUserCode(String companyUserCode) {
		this.companyUserCode = companyUserCode;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
