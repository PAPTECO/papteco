package com.papteco.web.netty;

import java.util.Properties;

import com.papteco.web.utils.EnvConfiguration;

public class BasicBuilder {

	protected Properties envsetting = EnvConfiguration.getEnvSetting();

	protected Integer PortTranslater(String port) {
		return Integer.valueOf(port);
	}
}
