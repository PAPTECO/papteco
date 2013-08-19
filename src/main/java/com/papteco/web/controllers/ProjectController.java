package com.papteco.web.controllers;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.papteco.web.beans.ProjectBean;
import com.papteco.web.services.ProjectService;

@Controller
public class ProjectController extends BaseController {
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(method = RequestMethod.GET, value = "createProject")
	@ResponseBody
	public String createProject() {
//		public String createProject(@RequestBody final ProjectBean project) {
		//for testing
		ProjectBean project2 = new ProjectBean();
		project2.setProjectId(110);
		project2.setCreatedAt(new Date());
		project2.setCreatedBy("ConyGA");
		project2.setDescription("testing project");
		project2.setFolderTree(this.sysConfig.prepareFolderStructure());
		project2.setProjectCde("9910-1301-112");
		try {
			projectService.createProject(project2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		return "SUCCESS";
	}
}
