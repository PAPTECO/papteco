package com.papteco.web.beans;

import java.io.Serializable;

public class CreateProjectFormBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 229980401777078388L;
	private String clientno;
	private String createDate;
	private String uniqueno;
	private String shortdesc;
	private String longdesc;

	public String getClientno() {
		return clientno;
	}

	public void setClientno(String clientno) {
		this.clientno = clientno;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUniqueno() {
		return uniqueno;
	}

	public void setUniqueno(String uniqueno) {
		this.uniqueno = uniqueno;
	}

	public String getShortdesc() {
		return shortdesc;
	}

	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}

	public String getLongdesc() {
		return longdesc;
	}

	public void setLongdesc(String longdesc) {
		this.longdesc = longdesc;
	}

	@Override
	public String toString() {
		return "CreateProjectFormBean [clientno=" + clientno + ", createDate="
				+ createDate + ", uniqueno=" + uniqueno + ", shortdesc="
				+ shortdesc + ", longdesc=" + longdesc + "]";
	}

}
