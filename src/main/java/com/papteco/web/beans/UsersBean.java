package com.papteco.web.beans;

import java.io.Serializable;
import java.util.List;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class UsersBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5735835369129164658L;
	@PrimaryKey
	private String userName;
	private String password;
	private String email;
	private List<RoleBean> roles;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<RoleBean> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleBean> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "UsersBean [userName=" + userName + ", password=" + password
				+ ", email=" + email + ", roles=" + roles + "]";
	}

}
