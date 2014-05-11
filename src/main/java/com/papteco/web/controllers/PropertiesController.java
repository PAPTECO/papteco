package com.papteco.web.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.papteco.web.beans.ClientBean;
import com.papteco.web.beans.FolderBean;
import com.papteco.web.utils.WebUtils;

@SuppressWarnings({ "rawtypes" })
@Controller
public class PropertiesController extends BaseController {

	public int getIncreaseNumber() {
		return 0;
	}

	@RequestMapping(method = RequestMethod.GET, value = "getPredefineStructureFolders")
	@ResponseBody
	public Map getPredefineStructureFolders() {
		return WebUtils.toTreeJson(this.sysConfig.prepareFolderStructure());
	}

	@RequestMapping(method = RequestMethod.GET, value = "getPredefineDocumentTypes")
	@ResponseBody
	public Map getPredefineDocumentTypes() {
		return WebUtils.toDocJson(this.sysConfig.prepareFolderStructure());
	}

	@RequestMapping(method = RequestMethod.GET, value = "getClientInfo")
	@ResponseBody
	public Map getClientInfo() {
		return WebUtils.toClientJson();
	}

	@RequestMapping(method = RequestMethod.GET, value = "getUniqueNo")
	@ResponseBody
	public Map getUniqueNo() {
		return WebUtils.toUniqueJson();
	}

	/* mandatory constructor method */
	public PropertiesController() {
	}

	public PropertiesController(List<FolderBean> folders,
			List<ClientBean> clients) {
		super();
	}
}
