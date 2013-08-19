package com.papteco.web.beans;

import java.util.List;

import com.sleepycat.persist.model.Persistent;

@Persistent
public class FolderBean {

	private String docType;
	private String folderName;
	private String nuberformat;
	private List<FileBean> fileTree;

	public List<FileBean> getFileTree() {
		return fileTree;
	}

	public void setFileTree(List<FileBean> fileTree) {
		this.fileTree = fileTree;
	}
	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getNuberformat() {
		return nuberformat;
	}

	public void setNuberformat(String nuberformat) {
		this.nuberformat = nuberformat;
	}

}
