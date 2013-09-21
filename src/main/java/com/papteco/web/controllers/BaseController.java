/**
 * 
 */
package com.papteco.web.controllers;

import java.io.File;

import org.apache.log4j.Logger;
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

	protected String combineFolderPath(String path1, String path2) {
		File f = new File(path1, path2);
		if (!f.exists()) {
			f.mkdirs();
			f.setExecutable(true, false);
			f.setReadable(true, false);
			f.setWritable(true, false);
		}
		return f.getPath();
	}
	
	protected String formatedNumber(String num, int digs){
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < digs-num.length(); i++){
			result.append("0");
		}
		result.append(num);
		return result.toString();
	}
}
