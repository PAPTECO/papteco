/**
 * 
 */
package com.papteco.web.controllers;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;

import com.papteco.web.utils.SystemConfiguration;


/**
 * @author Administrator
 *
 */
public class BaseController {
	@Autowired
	SystemConfiguration sysConfig;

	public static Logger logger = Logger.getLogger("Logger");
	
	/*static{
		PropertyConfigurator.configure("log4j.xml");
	}*/
}
