package com.papteco.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class ProjectBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8155353323885191907L;

	@PrimaryKey
	private int projectId;
	
	private String projectCde;

	private List<FolderBean> folderTree;

	private String createdBy;

	private Date createdAt;

	private String description;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public List<FolderBean> getFolderTree() {
		return folderTree;
	}

	public void setFolderTree(List<FolderBean> folderTree) {
		this.folderTree = folderTree;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProjectCde() {
		return projectCde;
	}

	public void setProjectCde(String projectCde) {
		this.projectCde = projectCde;
	}

}
