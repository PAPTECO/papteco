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
	
	private String lockByUser;
	
	private Date lockByDT;
	private String additional1;
	private String additional2;
	private String additional3;
	private Object additional4;
	private Object additional5;
	public FileLockBean(){}
	
	public FileLockBean(String fileId, String lockByUser, Date lockByDT){
		this.fileId = fileId;
		this.lockByUser = lockByUser;
		this.lockByDT = lockByDT;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
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

	public String getAdditional1() {
		return additional1;
	}

	public void setAdditional1(String additional1) {
		this.additional1 = additional1;
	}

	public String getAdditional2() {
		return additional2;
	}

	public void setAdditional2(String additional2) {
		this.additional2 = additional2;
	}

	public String getAdditional3() {
		return additional3;
	}

	public void setAdditional3(String additional3) {
		this.additional3 = additional3;
	}

	public Object getAdditional4() {
		return additional4;
	}

	public void setAdditional4(Object additional4) {
		this.additional4 = additional4;
	}

	public Object getAdditional5() {
		return additional5;
	}

	public void setAdditional5(Object additional5) {
		this.additional5 = additional5;
	}
	
}
