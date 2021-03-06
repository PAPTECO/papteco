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
import com.papteco.web.beans.UsersBean;
import com.papteco.web.beans.UsersFormBean;
import com.papteco.web.services.UserServiceImpl;
import com.papteco.web.utils.Roles2RightsConfiguration;
import com.papteco.web.utils.WebUtils;

@SuppressWarnings({ "rawtypes" })
@Controller
public class UsersController extends BaseController {
	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(method = RequestMethod.GET, value = "secure/doSearchUser")
	@ResponseBody
	public List doSearchUser(@RequestParam String searchRoles,
			@RequestParam String searchUserName) throws Exception {
		logger.info("searchRoles:" + searchRoles + " searchUserName:"
				+ searchUserName);
		return WebUtils.toSearchUserGrid(searchRoles, searchUserName);
	}

	@RequestMapping(method = RequestMethod.GET, value = "loadingRoles")
	@ResponseBody
	public Map loadingRoles() {
		return WebUtils.toRolesJson(rolessetting.keySet());
	}

	@RequestMapping(method = RequestMethod.POST, value = "secure/createUserRequest")
	@ResponseBody
	public Map createUserRequest(@RequestBody UsersFormBean bean)
			throws Exception {

		logger.info("createUserRequest UsersFormBean:" + bean);
		UsersBean user = preUserBean(bean);

		try {
			if (userService.getUser(bean.getCreateUserName()) != null) {
				return this.failMessage("User already exists");
			} else {
				userService.saveUser(user);
				return this.successMessage();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return this.failMessage(e.getMessage());
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "secure/updateUserRequest")
	@ResponseBody
	public Map updateUserRequest(@RequestBody UsersFormBean bean)
			throws Exception {

		logger.info("updateUserRequest UsersFormBean:" + bean);

		try {
			UsersBean user = preUserBean(bean);
			UsersBean dbuser = userService.getUser(user.getUserName());

			if (dbuser == null) {
				throw new Exception("User not exits.");
			} else {
				if (StringUtils.isBlank(user.getPassword())) {
					user.setPassword(dbuser.getPassword());
				}
			}
			userService.saveUser(user);
			return this.successMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return this.failMessage(e.getMessage());

		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "updateMyUserRequest")
	@ResponseBody
	public Map updateMyUserRequest(@RequestBody UsersFormBean bean)
			throws Exception {

		logger.info("updateMyUserRequest UsersFormBean:" + bean);

		try {

			UsersBean dbuser = userService.getUser(bean.getCreateUserName());

			if (dbuser == null) {
				throw new Exception("User not exits.");
			} else {
				if (StringUtils.isNotBlank(bean.getCreatePassword())) {
					dbuser.setPassword(bean.getCreatePassword());
				}
				dbuser.setEmail(bean.getCreateEmail());
			}
			userService.saveUser(dbuser);
			return this.successMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return this.failMessage(e.getMessage());

		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "secure/deleteUserRequest")
	@ResponseBody
	public Map deleteUserRequest(@RequestBody UsersFormBean bean)
			throws Exception {

		logger.info("deleteUserRequest UsersFormBean:" + bean);
		if (bean.getCreateUserName().equals("sysadmin"))
			return this.failMessage("Cannot delete this user!");
		try {
			UsersBean dbuser = userService.getUser(bean.getCreateUserName());

			if (dbuser == null) {
				throw new Exception("User not exits.");
			}

			userService.deleteUser(dbuser);
			return this.successMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return this.failMessage(e.getMessage());

		}
	}

	private UsersBean preUserBean(UsersFormBean bean) {
		UsersBean user = new UsersBean();
		if (StringUtils.isNotBlank(bean.getCreateUserName()))
			user.setUserName(bean.getCreateUserName());
		if (StringUtils.isNotBlank(bean.getCreatePassword()))
			user.setPassword(bean.getCreatePassword());
		user.setEmail(bean.getCreateEmail());

		List<String> roles = new ArrayList<String>();
		for (String role : bean.getCreateRoles()) {
			roles.add(role);
		}
		user.setRoles(roles);
		return user;
	}

	@RequestMapping(method = RequestMethod.POST, value = "getRoleDisplay")
	@ResponseBody
	public Map getRoleDisplay() throws Exception {

		logger.info("getRoleDisplay()");

		StringBuffer sb = new StringBuffer();

		sb.append("<table border='1' cellspacing='4' cellpadding='4'>");

		Enumeration keys = Roles2RightsConfiguration.getRolesSetting().keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			sb.append("<tr><td style='padding:3px'>");
			sb.append(key);
			sb.append("</td><td style='padding:3px'>");

			String value = Roles2RightsConfiguration.getRolesSetting()
					.getProperty(key);
			for (String right : value.split(",")) {
				sb.append(Roles2RightsConfiguration.getRightsSetting()
						.getProperty(right));
				sb.append("<br>");
			}
			sb.append("</td></tr>");
		}
		sb.append("</table>");

		return this.successMessage(of("data", sb.toString()));
	}

	@RequestMapping(method = RequestMethod.POST, value = "secure/getUsersRoleList")
	@ResponseBody
	public Map getUsersRoleList(@RequestBody UsersFormBean userid)
			throws Exception {

		logger.info("getUsersRoleList() userid:" + userid.getCreateUserName());

		if (StringUtils.isBlank(userid.getCreateUserName())) {

			return this.successMessage(of("username", "", "password", "",
					"email", "", "roles",
					this.populateUserRolsesCreate(rolessetting.keySet())));
		} else {
			UsersBean user = userService.getUser(userid.getCreateUserName());
			if (user != null) {
				return this.successMessage(of("username", user.getUserName(),
						"password", "", "email", user.getEmail(), "roles", this
								.populateUserRolsesModify(
										rolessetting.keySet(), user)));
			} else {
				return this
						.failMessage("User does not exists or has been deleted.Please refresh!");

			}
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "getMyUsersRoleList")
	@ResponseBody
	public Map getMyUsersRoleList(@RequestBody UsersFormBean userid)
			throws Exception {

		logger.info("getMyUsersRoleList() userid:" + userid.getCreateUserName());

		UsersBean user = userService.getUser(userid.getCreateUserName());
		if (user != null) {
			return this.successMessage(of("username", user.getUserName(),
					"password", "", "email", user.getEmail()));
		} else {
			return this
					.failMessage("User does not exists or has been deleted.Please refresh!");

		}

	}

	private String populateUserRolsesCreate(Set<Object> userroles) {
		StringBuffer sb = new StringBuffer();
		if (userroles != null && userroles.size() != 0) {
			sb.append("<table border='0' cellspacing='0' cellpadding='0'>");
			for (Object key : userroles) {
				sb.append("<tr><td><input type='checkbox' class='rolecls' name='roles' value ='");
				sb.append(key.toString());
				sb.append("' ></td><td>");
				sb.append(key.toString());
				sb.append("</td></tr>");
			}
			sb.append("</table>");
		}
		return sb.toString();
	}

	private String populateUserRolsesModify(Set<Object> userroles,
			UsersBean user) {
		StringBuffer sb = new StringBuffer();
		List<String> roles = user.getRoles();
		if (userroles != null && userroles.size() != 0) {
			sb.append("<table border='0' cellspacing='0' cellpadding='0'>");
			for (Object key : userroles) {
				sb.append("<tr><td><input type='checkbox' class='rolecls' name='roles'");
				for (String role : roles) {
					if (role.equals(key.toString()))
						sb.append(" checked = true");
				}
				sb.append(" value ='");
				sb.append(key.toString());
				sb.append("' ></td><td>");
				sb.append(key.toString());
				sb.append("</td></tr>");
			}
			sb.append("</table>");
		}
		return sb.toString();
	}

	@RequestMapping(method = RequestMethod.GET, value = "userlogout")
	@ResponseBody
	public Map userlogout(HttpSession session) throws Exception {
		logger.info("userlogout:");

		session.removeAttribute("LOGIN_USER");
		session.removeAttribute("allowFunctions");
		return this.successMessage();
	}

	@RequestMapping(method = RequestMethod.POST, value = "userlogin")
	@ResponseBody
	public Map userlogin(@RequestBody UsersFormBean bean, HttpSession session)
			throws Exception {
		logger.info("UserLogin : " + bean.getCreateUserName());
		logger.info("createUserRequest UsersFormBean:" + bean);

		try {
			UsersBean user = userService.validateUser(bean.getCreateUserName());
			if (user == null)
				return this.failMessage("User not exists");
			else if (user.getPassword().equals(bean.getCreatePassword())) {
				Set<String> tempAllowFunctions = new HashSet<String>();
				for (String role : user.getRoles()) {
					List<String> rights = Arrays.asList(rolessetting.get(role)
							.toString().split(","));
					tempAllowFunctions.addAll(rights);
				}
				List<String> allowFunctions = new ArrayList<String>();
				allowFunctions.addAll(tempAllowFunctions);
				session.setAttribute("allowFunctions", allowFunctions);
				session.setAttribute("LOGIN_USER", user.getUserName());
				return this.successMessage();
			} else
				return this.failMessage("Incorrect Password.");

		} catch (Exception e) {
			e.printStackTrace();
			return this.failMessage(e.getMessage());

		}

	}

	// common
	@RequestMapping(method = RequestMethod.GET, value = "forbid")
	@ResponseBody
	public Map forbid_get() throws Exception {

		return ImmutableMap.of("type", "fail", "message",
				"You have no permission !");

	}

	// common
	@RequestMapping(method = RequestMethod.POST, value = "forbid")
	@ResponseBody
	public Map forbid_post() throws Exception {

		return ImmutableMap.of("type", "fail", "message",
				"You have no permission !");

	}

	@RequestMapping(method = RequestMethod.GET, value = "forbidPlan")
	@ResponseBody
	public String forbid_plan_get() throws Exception {

		return "You have no permission !";

	}

	@RequestMapping(method = RequestMethod.POST, value = "forbidPlan")
	@ResponseBody
	public String forbid_plan_post() throws Exception {

		return "You have no permission !";

	}
}
