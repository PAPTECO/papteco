package com.papteco.web.utils;

import java.util.List;

import com.papteco.web.beans.ClientBean;
import com.papteco.web.beans.FolderBean;

public class SystemConfiguration extends BaseUtils{
	
	/*pre defined folders structure*/
	private List<FolderBean> preDefineFolderStructure;
	
	public void setPreDefineFolderStructure(
			List<FolderBean> preDefineFolderStructure) {
		this.preDefineFolderStructure = preDefineFolderStructure;
	}

	public List<FolderBean> prepareFolderStructure(){
		return preDefineFolderStructure;
	}

	/*pre defined clients information*/
	private List<ClientBean> preDefineClientsInfo;

	public void setPreDefineClientsInfo(List<ClientBean> preDefineClientsInfo) {
		this.preDefineClientsInfo = preDefineClientsInfo;
	}

	public List<ClientBean> prepareClientsInfo(){
		return preDefineClientsInfo;
	}
}
