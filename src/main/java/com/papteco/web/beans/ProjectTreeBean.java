package com.papteco.web.beans;

import java.util.Date;
import java.util.List;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class ProjectTreeBean {

	@PrimaryKey
	private int projectId;

	private List<FolderTreeBean> folderTree;

	private String createdBy;

	private Date createdAt;

	private String description;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public List<FolderTreeBean> getFolderTree() {
		return folderTree;
	}

	public void setFolderTree(List<FolderTreeBean> folderTree) {
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

}
