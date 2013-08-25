package com.papteco.web.utils;

import java.io.IOException;
import java.util.Properties;

public class BaseUtils {

	protected static Properties props;

	static {
		props = new Properties();
		try {
			props.load(BaseUtils.class.getClassLoader().getResourceAsStream(
					"environment.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
