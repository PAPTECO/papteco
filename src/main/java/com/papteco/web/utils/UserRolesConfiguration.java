package com.papteco.web.utils;

import java.util.Map;


public class UserRolesConfiguration extends BaseUtils {
	
	private Map<String, String> userRolesSetting;

	public Map<String, String> getUserRolesSetting() {
		return userRolesSetting;
	}

	public void setUserRolesSetting(Map<String, String> userRolesSetting) {
		this.userRolesSetting = userRolesSetting;
	}

	public String getRoleDesc(String key){
		return userRolesSetting.get(key);
	}
	
	public Map<String, String> getAllRoles(){
		return userRolesSetting;
	}
}
