package com.papteco.web.controllers;

import java.util.Date;
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
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.beans.UsersFormBean;
import com.papteco.web.services.PresNoServiceImpl;
import com.papteco.web.services.ProjectServiceImpl;
import com.papteco.web.utils.WebUtils;

@Controller
public class UsersController extends BaseController {
	@Autowired
	private ProjectServiceImpl projectService;
	
	@Autowired
	private PresNoServiceImpl presNoService;

	@RequestMapping(method = RequestMethod.GET, value = "doSearchUser")
	@ResponseBody
	public List doSearchUser(@RequestParam String searchRoles,
			@RequestParam String searchUserName) throws Exception {
		System.out.println("searchRoles:"+searchRoles+" searchUserName:"+searchUserName);
		return WebUtils.toSearchUserGrid(searchRoles, searchUserName);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "loadingRoles")
	@ResponseBody
	public Map loadingRoles() {
		List l = Lists.newArrayList();
		l.add("Administrator");
		l.add("General manager");
		l.add("Assistant to the general manager");
		return WebUtils.toRolesJson(l);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "createUserRequest")
	@ResponseBody
	public Map createUserRequest(@RequestBody UsersFormBean bean)
			throws Exception {
		
		System.out.println("UsersFormBean:"+bean);
//		try {
//			if(projectService.isPrjIdExisting(bean.getUniqueno())){
//				return ImmutableMap.of("type", "failure", "message",
//						"The Unique No. was existing : "+bean.getUniqueno());
//			}else{
//
//				
//				projectService.createProject(tmpProject,
//						this.sysConfig.prepareFolderStructure());
//
//				return ImmutableMap.of("type", "success", "projectcode",
//						tmpProject.getProjectCde());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return ImmutableMap
//					.of("type", "failure", "message", e.getMessage());
//		}
		
		return ImmutableMap.of("type", "success");
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "getUsersRoleList")
	@ResponseBody
	public Map getUsersRoleList(@RequestBody UsersFormBean userid)
			throws Exception {
		
		System.out.println("getUsersRoleList() userid:"+userid.getCreateUserName());

		if(StringUtils.isBlank(userid.getCreateUserName())){
			return ImmutableMap.of("type", "success",
					"username","",
					"email","",
					"roles","<input type='checkbox' class='rolecls' name='fruit' value ='apple' >apple<br><input type='checkbox' name='fruit' class='rolecls' value='orange'>orange<br><input type='checkbox' name='fruit' class='rolecls' value='mango'>mango<br>");
		}else{
			return ImmutableMap.of("type", "success",
					"username","username1",
					"email","email1",
					"roles","<input type='checkbox' class='rolecls' name='fruit' value ='apple' >apple<br><input type='checkbox' name='fruit' class='rolecls' value='orange'>orange<br><input type='checkbox' name='fruit' class='rolecls' value='mango'>mango<br>");	
		}
		
		
	}
}
