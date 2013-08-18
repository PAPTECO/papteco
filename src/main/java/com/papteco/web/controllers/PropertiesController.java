package com.papteco.web.controllers;

import java.util.List;

import com.papteco.web.beans.ClientBean;
import com.papteco.web.beans.FolderBean;

public class PropertiesController extends BaseController {

	protected List<FolderBean> folders;
	protected List<ClientBean> clients;

	public PropertiesController(List<FolderBean> folders,
			List<ClientBean> clients) {
		super();
		this.folders = folders;
		this.clients = clients;

	}

	public int getIncreaseNumber() {

		return 0;
	}

	public List<FolderBean> getPredefineStructureFolders() {

		return this.folders;
	}

	public List<ClientBean> getClientInfo() {

		return this.clients;

	}
}
