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
	private List<String> roles;
	
	private String additional1;
	private String additional2;
	private String additional3;
	private String additional4;
	private String additional5;
	private Object additional6;
	private Object additional7;
	private Object additional8;
	private Object additional9;
	private Object additional10;
	
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
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public String getAdditional1() {
		return additional1;
	}
	public void setAdditional1(String additional1) {
		this.additional1 = additional1;
	}
	public String getAdditional2() {
		return additional2;
	}
	public void setAdditional2(String additional2) {
		this.additional2 = additional2;
	}
	public String getAdditional3() {
		return additional3;
	}
	public void setAdditional3(String additional3) {
		this.additional3 = additional3;
	}
	public String getAdditional4() {
		return additional4;
	}
	public void setAdditional4(String additional4) {
		this.additional4 = additional4;
	}
	public String getAdditional5() {
		return additional5;
	}
	public void setAdditional5(String additional5) {
		this.additional5 = additional5;
	}
	public Object getAdditional6() {
		return additional6;
	}
	public void setAdditional6(Object additional6) {
		this.additional6 = additional6;
	}
	public Object getAdditional7() {
		return additional7;
	}
	public void setAdditional7(Object additional7) {
		this.additional7 = additional7;
	}
	public Object getAdditional8() {
		return additional8;
	}
	public void setAdditional8(Object additional8) {
		this.additional8 = additional8;
	}
	public Object getAdditional9() {
		return additional9;
	}
	public void setAdditional9(Object additional9) {
		this.additional9 = additional9;
	}
	public Object getAdditional10() {
		return additional10;
	}
	public void setAdditional10(Object additional10) {
		this.additional10 = additional10;
	}
	@Override
	public String toString() {
		return "UsersBean [userName=" + userName + ", password=" + password
				+ ", email=" + email + ", roles=" + roles + "]";
	}

}
