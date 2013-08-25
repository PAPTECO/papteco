package com.papteco.web.beans;

public class ClientBean implements Comparable<ClientBean>{

	private String clientNo;
	private String clientName;

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

	public int compareTo(ClientBean o) {
		
		return this.clientNo.compareTo(o.clientNo)>0 ? 1 :0;
	}

}
