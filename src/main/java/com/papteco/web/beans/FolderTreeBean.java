package com.papteco.web.beans;

import java.util.List;

import com.sleepycat.persist.model.Persistent;

@Persistent
public class FolderTreeBean {

	private List<FolderBean> folderTree;

	public List<FolderBean> getFolderTree() {
		return folderTree;
	}

	public void setFolderTree(List<FolderBean> folderTree) {
		this.folderTree = folderTree;
	}

}
