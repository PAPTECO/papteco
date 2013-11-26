package com.papteco.web.utils;

import java.util.Map;


public class UserRolesConfiguration extends BaseUtils {
	
	private Map<String, String> rolesRightsMapping;
	private Map<String, String> rolesSetting;
	private Map<String, String> rightsSetting;

	public Map<String, String> getRolesRightsMapping() {
		return rolesRightsMapping;
	}

	public void setRolesRightsMapping(Map<String, String> rolesRightsMapping) {
		this.rolesRightsMapping = rolesRightsMapping;
	}

	public Map<String, String> getRolesSetting() {
		return rolesSetting;
	}

	public void setRolesSetting(Map<String, String> rolesSetting) {
		this.rolesSetting = rolesSetting;
	}

	public Map<String, String> getRightsSetting() {
		return rightsSetting;
	}

	public void setRightsSetting(Map<String, String> rightsSetting) {
		this.rightsSetting = rightsSetting;
	}

	public String getRoleDesc(String key){
		return rolesSetting.get(key);
	}
	
	public Map<String, String> getAllRoles(){
		return rolesSetting;
	}
}
