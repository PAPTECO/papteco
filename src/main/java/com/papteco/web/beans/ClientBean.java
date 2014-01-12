package com.papteco.web.beans;

import java.io.Serializable;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class ClientBean implements Comparable<ClientBean>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4148753755140742448L;
	@PrimaryKey
	private String clientNo;
	private String clientName;
	private String additional1;
	private String additional2;
	private String additional3;
	private String additional4;
	private String additional5;
	
	public ClientBean(){
		
	}
	
	public ClientBean(String clientNo, String clientName) {
		super();
		this.clientNo = clientNo;
		this.clientName = clientName;
	}


	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public String getAdditional4() {
		return additional4;
	}

	public void setAdditional4(String additional4) {
		this.additional4 = additional4;
	}

	public String getAdditional5() {
		return additional5;
	}

	public void setAdditional5(String additional5) {
		this.additional5 = additional5;
	}

	public int compareTo(ClientBean o) {
		
		return this.clientNo.compareTo(o.clientNo)>0 ? 1 :0;
	}

}
