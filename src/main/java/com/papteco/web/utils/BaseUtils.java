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

}
