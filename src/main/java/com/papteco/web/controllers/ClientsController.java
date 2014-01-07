package com.papteco.web.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;
import com.papteco.web.beans.ClientBean;
import com.papteco.web.beans.UsersBean;
import com.papteco.web.beans.UsersFormBean;
import com.papteco.web.services.UserServiceImpl;
import com.papteco.web.utils.Roles2RightsConfiguration;
import com.papteco.web.utils.WebUtils;

@Controller
public class ClientsController extends BaseController {
	@Autowired
	private UserServiceImpl userService;

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
//		UsersBean user = preUserBean(bean);

		try {
//			if (userService.getUser(bean.getCreateUserName()) != null) {
//				return this.failMessage("User already exists");
//			
//			} else {
////				userService.saveUser(user);
//				return this.successMessage();
//			}
			return this.successMessage();
		} catch (Exception e) {
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
//			UsersBean user = preUserBean(bean);
//			UsersBean dbuser = userService.getUser(user.getUserName());
//
//			if (dbuser == null) {
//				throw new Exception("User not exits.");
//			} else {
//				if (StringUtils.isBlank(user.getPassword())) {
//					user.setPassword(dbuser.getPassword());
//				}
//			}
//			userService.saveUser(user);
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
//			UsersBean dbuser = userService.getUser(bean.getCreateUserName());
//
//			if (dbuser == null) {
//				throw new Exception("User not exits.");
//			}
//
//			userService.deleteUser(dbuser);
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

		if (StringUtils.isBlank(clientBean.getClientNo())) {

			return this.successMessage(of("clientno", "", "clientname", ""));
		} else {
			if(true)
				return this.successMessage(of("clientno", "200", "clientname", "200.22"));
			else
				return this
						.failMessage("Client does not exists or has been deleted.Please refresh!");
			
		}
	}
}
