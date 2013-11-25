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
	
	@Override
	public String toString() {
		return "RoleBean [roleCde=" + roleCde + ", roleDesc=" + roleDesc + "]";
	}
}
