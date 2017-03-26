package com.zero.rbac.domain.impl;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.zero.rbac.domain.api.Role;
import com.zero.rbac.domain.api.User;

@Entity(name = "User")
@Table(name = "User", schema = "RBAC_CORE")
public abstract class AbstractUser implements User {

	@Column(name = "UID")
	private String uid;

	@Column(name = "ACCOUNT")
	private String account;

	@Column(name = "USERNAME")
	private String userName;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "FINGERPRINTS")
	private String fingerPrints;

	@Column(name = "PASSWORD")
	private String password;

	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER, mappedBy = "User")
	@Fetch(FetchMode.SUBSELECT)
	Collection<Role> roles;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFingerPrints() {
		return fingerPrints;
	}

	public void setFingerPrints(String fingerPrints) {
		this.fingerPrints = fingerPrints;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

}
