package com.papteco.web.beans;

import java.io.Serializable;
import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class FileLockBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3121423802004911951L;
	@PrimaryKey
	private String fileId;
	
	private boolean isLocked = false;
	
	private String lockByUser;
	
	private Date lockByDT;
	
	public FileLockBean(){}
	
	public FileLockBean(String fileId, boolean isLocked){
		this.fileId = fileId;
		this.isLocked = isLocked;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public String getLockByUser() {
		return lockByUser;
	}

	public void setLockByUser(String lockByUser) {
		this.lockByUser = lockByUser;
	}

	public Date getLockByDT() {
		return lockByDT;
	}

	public void setLockByDT(Date lockByDT) {
		this.lockByDT = lockByDT;
	}
	
}
