package com.papteco.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Roles2RightsConfiguration extends BaseUtils {

	private static Properties rolessetting;
	private static Properties rightssetting;

	public static Properties getRolesSetting() {
		if (rolessetting != null)
			return rolessetting;
		else
			return loadRolesProperties();
	}

	public static Properties loadRolesProperties() {
		rolessetting = new Properties();
		InputStream in = Roles2RightsConfiguration.class
				.getResourceAsStream("/userroles.properties");
		try {
			rolessetting.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rolessetting;
	}

	public static Properties getRightsSetting() {
		if (rightssetting != null)
			return rightssetting;
		else
			return loadRightsProperties();
	}

	public static Properties loadRightsProperties() {
		rightssetting = new Properties();
		InputStream in = Roles2RightsConfiguration.class
				.getResourceAsStream("/userrights.properties");
		try {
			rightssetting.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rightssetting;
	}
}
