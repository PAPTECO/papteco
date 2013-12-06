package com.papteco.web.beans;

import java.io.Serializable;

import com.sleepycat.persist.model.Persistent;

@Persistent
public class RoleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2435329262358693644L;
	private String roleCde;
	private String roleDesc;
	private String additional1;
	private String additional2;
	private String additional3;
	private String additional4;
	private String additional5;
	public RoleBean(){
	}
	public RoleBean(String roleCde, String roleDesc){
		this.roleCde = roleCde;
		this.roleDesc = roleDesc;
	}
	public String getRoleCde() {
		return roleCde;
	}
	public void setRoleCde(String roleCde) {
		this.roleCde = roleCde;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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
	@Override
	public String toString() {
		return "RoleBean [roleCde=" + roleCde + ", roleDesc=" + roleDesc + "]";
	}
}
