package com.papteco.web.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.papteco.web.utils.Roles2RightsConfiguration;
import com.papteco.web.utils.SystemConfiguration;

/**
 * @author Cony
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class BaseController {
	protected static final Logger logger = Logger
			.getLogger(BaseController.class.getName());
	@Autowired
	SystemConfiguration sysConfig;

	@Value("#{settings['rootpath']}")
	protected String rootpath;

	protected Properties rolessetting = Roles2RightsConfiguration
			.getRolesSetting();
	protected Properties rightssetting = Roles2RightsConfiguration
			.getRightsSetting();

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

	protected String formatedNumber(String num, int digs) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < digs - num.length(); i++) {
			result.append("0");
		}
		result.append(num);
		return result.toString();
	}

	protected Map successMessage(Map messageMap) {
		if (messageMap == null)
			messageMap = new HashMap();
		messageMap.put("type", "success");
		return messageMap;
	}

	protected Map successMessage() {
		return this.successMessage(null);
	}

	protected Map failMessage(String message) {
		Map messageMap = new HashMap();
		messageMap.put("type", "fail");
		messageMap.put("message", message);
		return messageMap;
	}

	public static Map of(Object... keyval) {
		Map map = new HashMap();
		for (int i = 0; i < keyval.length; i = i + 2) {
			map.put(keyval[i], keyval[i + 1]);
		}
		return map;
	}

	protected Map successFormatMessage(Map messageMap) {
		if (messageMap == null)
			messageMap = new HashMap();
		messageMap.put("\"type\"", "\"success\"");
		return messageMap;
	}

	protected Map successFormatMessage() {
		return this.successMessage(null);
	}

	protected Map failFormatMessage(String message) {
		Map messageMap = new HashMap();
		messageMap.put("\"type\"", "\"fail\"");
		messageMap.put("\"message\"", "\"" + message + "\"");
		return messageMap;
	}

	public static Map ofFormat(Object... keyval) {
		Map map = new HashMap();
		for (int i = 0; i < keyval.length; i = i + 2) {
			map.put("\"" + keyval[i] + "\"", "\"" + keyval[i + 1] + "\"");
		}
		return map;
	}

	protected String formatJSONToHTML(Map message) {
		return "<textarea>" + message.toString().replaceAll("=", ":")
				+ "</textarea>";
	}
}
