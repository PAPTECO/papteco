package com.papteco.web.services;

import org.springframework.stereotype.Service;

import com.papteco.web.beans.ClientBean;
import com.papteco.web.dbs.ClientDAO;
import com.papteco.web.utils.FSUtils;

@Service
public class ClientServiceImpl extends BaseService {
	
	public ClientServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);
	}

	public String saveClient(ClientBean client){
		if(ClientDAO.getClientBean(client.getClientNo()) != null){
			return "CLIENT_EXIST";
		}else{
			ClientDAO.saveClientBean(client);
			return "SUCC";
		}
	}
	
	public void updateClient(ClientBean client){
		ClientBean bean = ClientDAO.getClientBean(client.getClientNo());
		if(bean != null){
			bean.setClientName(client.getClientName());
			ClientDAO.saveClientBean(bean);
		}
	}
	
	public void deleteClient(ClientBean client) {
		ClientDAO.deleteClient(client);
	}
	
	public ClientBean getClient(String clientNo){
		return ClientDAO.getClientBean(clientNo);
	}
	
	/* mandatory constructor method */
	public ClientServiceImpl() {
	}
}
