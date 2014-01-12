package com.papteco.web.controllers;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.papteco.web.beans.ClientBean;
import com.papteco.web.services.ClientServiceImpl;
import com.papteco.web.utils.WebUtils;

@Controller
public class ClientsController extends BaseController {
	@Autowired
	private ClientServiceImpl clientService;

	@RequestMapping(method = RequestMethod.GET, value = "secure/doSearchClient")
	@ResponseBody
	public List doSearchClient(@RequestParam String searchClientNo,
			@RequestParam String searchClientName) throws Exception {
		System.out.println("doSearchClient:" + searchClientNo + " searchUserName:"
				+ searchClientName);
		return WebUtils.toSearchClientGrid(searchClientNo, searchClientName);
	}

	@RequestMapping(method = RequestMethod.POST, value = "secure/createClientRequest")
	@ResponseBody
	public Map createClientRequest(@RequestBody ClientBean bean)
			throws Exception {
		System.out.println("createClientRequest ClientBean:" + bean);
		
		try {
			String feedback = clientService.saveClient(bean);
			if(feedback.equals("CLIENT_EXIST")){
				return this.failMessage("ClientNo [" + bean.getClientNo() + "] was exist already!");
			}else{
				return this.successMessage();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return this.failMessage(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "secure/updateClientRequest")
	@ResponseBody
	public Map updateClientRequest(@RequestBody ClientBean bean)
			throws Exception {
		System.out.println("updateClientRequest ClientBean:" + bean);

		try {
			if(StringUtils.isBlank(bean.getClientNo()))
				return this.failMessage("ClientNo cannot be blank!");
			if(StringUtils.isBlank(bean.getClientName()))
				return this.failMessage("ClientName cannot be blank!");
			clientService.updateClient(bean);
			return this.successMessage();
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.failMessage(e.getMessage());

		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "secure/deleteClientRequest")
	@ResponseBody
	public Map deleteClientRequest(@RequestBody ClientBean bean)
			throws Exception {
		System.out.println("deleteClientRequest ClientBean:" + bean);

		try {
			clientService.deleteClient(bean);
			return this.successMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return this.failMessage(e.getMessage());

		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "secure/getClientMaintList")
	@ResponseBody
	public Map getClientMaintList(@RequestBody ClientBean clientBean)
			throws Exception {
		System.out.println("getClientMaintList() clientId:"
				+ clientBean.getClientNo());

		if(StringUtils.isNotBlank(clientBean.getClientNo())){
			ClientBean client = clientService.getClient(clientBean.getClientNo());
			if(client != null){
				return this.successMessage(of("clientno", client.getClientNo(), "clientname", client.getClientName()));
			}else{
				return this
						.failMessage("Client does not exists or has been deleted.Please refresh!");
			}
		}else{
			return this.successMessage(of("clientno", "", "clientname", ""));
		}
	}
}
