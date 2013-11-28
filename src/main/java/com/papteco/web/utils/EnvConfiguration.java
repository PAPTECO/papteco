package com.papteco.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvConfiguration {

	private static Properties envsetting;

	public static Properties getEnvSetting() {
		if (envsetting != null)
			return envsetting;
		else
			return loadEnvProperties();
	}

	public static Properties loadEnvProperties() {
		envsetting = new Properties();
		InputStream in = EnvConfiguration.class
				.getResourceAsStream("/environment.properties");
		try {
			envsetting.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return envsetting;
	}

}
