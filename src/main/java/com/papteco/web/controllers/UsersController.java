package com.papteco.web.controllers;

import java.util.ArrayList;
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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.papteco.web.beans.RoleBean;
import com.papteco.web.beans.UsersBean;
import com.papteco.web.beans.UsersFormBean;
import com.papteco.web.services.UserServiceImpl;
import com.papteco.web.utils.WebUtils;

@Controller
public class UsersController extends BaseController {
	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(method = RequestMethod.GET, value = "doSearchUser")
	@ResponseBody
	public List doSearchUser(@RequestParam String searchRoles,
			@RequestParam String searchUserName) throws Exception {
		System.out.println("searchRoles:" + searchRoles + " searchUserName:"
				+ searchUserName);
		return WebUtils.toSearchUserGrid(searchRoles, searchUserName);
	}

	@RequestMapping(method = RequestMethod.GET, value = "loadingRoles")
	@ResponseBody
	public Map loadingRoles() {
		return WebUtils.toRolesJson(userrolesSetting.getAllRoles());
	}

	@RequestMapping(method = RequestMethod.POST, value = "createUserRequest")
	@ResponseBody
	public Map createUserRequest(@RequestBody UsersFormBean bean)
			throws Exception {

		System.out.println("createUserRequest UsersFormBean:" + bean);
		UsersBean user = preUserBean(bean, userrolesSetting.getAllRoles());

		try {
			if (userService.getUser(bean.getCreateUserName()) != null) {
				return ImmutableMap.of("type", "fail", "message",
						"User already exists");
			} else {
				userService.saveUser(user);
				return ImmutableMap.of("type", "success");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ImmutableMap.of("type", "fail", "message", e.getMessage());

		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "updateUserRequest")
	@ResponseBody
	public Map updateUserRequest(@RequestBody UsersFormBean bean)
			throws Exception {

		System.out.println("updateUserRequest UsersFormBean:" + bean);

		try {
			UsersBean user = preUserBean(bean, userrolesSetting.getAllRoles());

			userService.saveUser(user);
			return ImmutableMap.of("type", "success");
		} catch (Exception e) {
			e.printStackTrace();
			return ImmutableMap.of("type", "fail", "message", e.getMessage());

		}
	}

	private UsersBean preUserBean(UsersFormBean bean,
			Map<String, String> allroles) {
		UsersBean user = new UsersBean();
		if (StringUtils.isNotBlank(bean.getCreateUserName()))
			user.setUserName(bean.getCreateUserName());
		if (StringUtils.isNotBlank(bean.getCreatePassword()))
			user.setPassword(bean.getCreatePassword());
		user.setEmail(bean.getCreateEmail());

		List<RoleBean> roles = new ArrayList<RoleBean>();
		for (String role : bean.getCreateRoles()) {
			roles.add(new RoleBean(role, allroles.get(role)));
		}
		user.setRoles(roles);
		return user;
	}

	@RequestMapping(method = RequestMethod.POST, value = "getRoleDisplay")
	@ResponseBody
	public Map getRoleDisplay() throws Exception {

		System.out.println("getRoleDisplay()");

		StringBuffer sb = new StringBuffer();

		sb.append("<table border='0' cellspacing='0' cellpadding='0'>");
		sb.append("<tr><td>General manager</td><td>Create searches,Create project</td></tr>");
		sb.append("<tr><td>Assistant to the general manager</td><td>Create searches,Create project</td></tr>");
		sb.append("</table>");

		return ImmutableMap.of("type", "success", 
				"data",sb.toString());

	}

	@RequestMapping(method = RequestMethod.POST, value = "getUsersRoleList")
	@ResponseBody
	public Map getUsersRoleList(@RequestBody UsersFormBean userid)
			throws Exception {

		System.out.println("getUsersRoleList() userid:"
				+ userid.getCreateUserName());

		if (StringUtils.isBlank(userid.getCreateUserName())) {
			return ImmutableMap.of("type", "success", "username", "", "email",
					"", "roles", this.populateUserRolsesInputs(userrolesSetting
							.getAllRoles()));
		} else {

			UsersBean user = userService.getUser(userid.getCreateUserName());
			if (user != null) {
				return ImmutableMap.of("type", "success", "username", user
						.getUserName(), "email", user.getEmail(), "roles", this
						.populateUserRolsesInputs(userrolesSetting
								.getAllRoles()));
			} else {
				return ImmutableMap.of("type", "success", "username",
						"username1", "email", "email1", "roles", this
								.populateUserRolsesInputs(userrolesSetting
										.getAllRoles()));
			}
		}
	}

	private String populateUserRolsesInputs(Map<String, String> userroles) {
		StringBuffer sb = new StringBuffer();
		if (userroles != null && userroles.size() != 0) {
			sb.append("<table border='0' cellspacing='0' cellpadding='0'>");
			for (String key : userroles.keySet()) {
				sb.append("<tr><td><input type='checkbox' class='rolecls' name='roles' value ='");
				sb.append(key);
				sb.append("' ></td><td>");
				sb.append(userroles.get(key));
				sb.append("</td></tr>");
			}
			sb.append("</table>");
		}
		return sb.toString();
	}
}
