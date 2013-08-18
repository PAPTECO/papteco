package com.papteco.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.papteco.web.beans.ClientBean;
import com.papteco.web.beans.FolderBean;
import com.papteco.web.services.ClientService;

@Controller
public class PropertiesController extends BaseController {

	// protected List<FolderBean> folders;
	// protected List<ClientBean> clients;
//	@Autowired
	protected ClientService clientService;

	public int getIncreaseNumber() {

		return 0;
	}

	@RequestMapping(method = RequestMethod.GET, value = "getPredefineStructureFolders")
	@ResponseBody
	public List<FolderBean> getPredefineStructureFolders() {
		System.out.println(this.sysConfig.prepareFolderStructure().size());
		return this.sysConfig.prepareFolderStructure();
	}

	@RequestMapping(method = RequestMethod.GET, value = "getClientInfo")
	@ResponseBody
	public List<ClientBean> getClientInfo() {
		return clientService.returnAllClientInfo();
	}

	/* mandatory constructor method */
	public PropertiesController() {
	}

	public PropertiesController(List<FolderBean> folders,
			List<ClientBean> clients) {
		super();
		// this.folders = folders;
		// this.clients = clients;
	}
}
