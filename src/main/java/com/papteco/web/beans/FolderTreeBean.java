package com.papteco.web.beans;

import java.util.List;

import com.sleepycat.persist.model.Persistent;

@Persistent
public class FolderTreeBean {

	private List<FileTreeBean> fileTree;

	public List<FileTreeBean> getFileTree() {
		return fileTree;
	}

	public void setFileTree(List<FileTreeBean> fileTree) {
		this.fileTree = fileTree;
	}

}
