package com.papteco.web.beans;

import java.io.Serializable;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class PreserveNosBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private String presNoCde;
	
	private int presNoFrom;
	
	private int presNoTo;

	public PreserveNosBean(){}
	
	public PreserveNosBean(int presNoFrom, int presNoTo){
		this.presNoFrom = presNoFrom;
		this.presNoTo = presNoTo;
	}
	
	public PreserveNosBean(String presNoCde, int presNoFrom, int presNoTo){
		this.presNoCde = presNoCde;
		this.presNoFrom = presNoFrom;
		this.presNoTo = presNoTo;
	}
	
	public String getPresNoCde() {
		return presNoCde;
	}

	public void setPresNoCde(String presNoCde) {
		this.presNoCde = presNoCde;
	}

	public int getPresNoFrom() {
		return presNoFrom;
	}

	public void setPresNoFrom(int presNoFrom) {
		this.presNoFrom = presNoFrom;
	}

	public int getPresNoTo() {
		return presNoTo;
	}

	public void setPresNoTo(int presNoTo) {
		this.presNoTo = presNoTo;
	}
	
}
