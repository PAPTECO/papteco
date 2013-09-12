/**
 * 
 */
package com.papteco.web.controllers;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.papteco.web.utils.SystemConfiguration;


/**
 * @author Administrator
 *
 */
@Controller
public class BaseController {
	@Autowired
	SystemConfiguration sysConfig;
	
	@Value("#{settings['rootpath']}")
	protected String rootpath;

	public static Logger logger = Logger.getLogger("Logger");
	
	/*static{
		PropertyConfigurator.configure("log4j.xml");
	}*/
}
