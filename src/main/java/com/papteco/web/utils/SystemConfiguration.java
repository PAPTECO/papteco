package com.papteco.web.utils;

import java.util.List;

import com.papteco.web.beans.FolderBean;

public class SystemConfiguration {
	public List<FolderBean> preDefineFolderStructur;
	
	public void setPreDefineFolderStructur(
			List<FolderBean> preDefineFolderStructur) {
		this.preDefineFolderStructur = preDefineFolderStructur;
	}

	public List<FolderBean> prepareFolderStructure(){
		return preDefineFolderStructur;
	}

}
