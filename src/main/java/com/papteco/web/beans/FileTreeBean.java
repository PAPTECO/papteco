package com.papteco.web.beans;

import java.util.List;

import com.sleepycat.persist.model.Persistent;

@Persistent
public class FileTreeBean {

	private List<FileBean> fileTree;

	public List<FileBean> getFileTree() {
		return fileTree;
	}

	public void setFileTree(List<FileBean> fileTree) {
		this.fileTree = fileTree;
	}

}
