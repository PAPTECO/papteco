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
	private String additional1;
	private String additional2;
	private String additional3;
	private Object additional4;
	private Object additional5;
	private Object additional6;
	
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

	public Object getAdditional6() {
		return additional6;
	}

	public void setAdditional6(Object additional6) {
		this.additional6 = additional6;
	}
	
}
