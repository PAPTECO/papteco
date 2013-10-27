package com.papteco.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

@Persistent
public class FolderBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5090631420214980480L;
	private String docType;
	private String folderName;
	private String nuberformat;
	private ArrayList<FileBean> fileTree = new ArrayList<FileBean>();

	public ArrayList<FileBean> getFileTree() {
		return fileTree;
	}

	public void setFileTree(ArrayList<FileBean> fileTree) {
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
