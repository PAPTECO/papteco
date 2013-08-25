package com.papteco.web.beans;

import java.util.Date;

import com.sleepycat.persist.model.Persistent;

@Persistent
public class FileBean {

	private String fileName;

	private Date lastModifiedAt;

	private String lastModifiedBy;

	private Date initUploadAt;

	private String initUploadBy;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getInitUploadAt() {
		return initUploadAt;
	}

	public void setInitUploadAt(Date initUploadAt) {
		this.initUploadAt = initUploadAt;
	}

	public String getInitUploadBy() {
		return initUploadBy;
	}

	public void setInitUploadBy(String initUploadBy) {
		this.initUploadBy = initUploadBy;
	}

}
