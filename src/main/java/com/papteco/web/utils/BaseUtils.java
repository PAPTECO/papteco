package com.papteco.web.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseUtils {

	/* properties value; */
	@Value("#{settings['rootpath']}")
	protected String rootpath;

	@Value("#{settings[datapath]}")
	protected String datapath;

	public boolean isWindowsPlatform() {
		String os = System.getProperty("os.name");
		if (os != null && os.startsWith("Windows"))
			return true;
		else
			return false;
	}

	public boolean isMacPlatform() {
		String os = System.getProperty("os.name");

		if (os != null && os.startsWith("Mac"))
			return true;
		else
			return false;
	}
}
