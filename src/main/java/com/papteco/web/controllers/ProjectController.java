package com.papteco.web.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;
import com.papteco.web.beans.CreateProjectFormBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.services.ProjectService;
import com.papteco.web.utils.WebUtils;

@Controller
public class ProjectController extends BaseController {

	private ProjectService projectService;

	@RequestMapping(method = RequestMethod.POST, value = "createProject")
	@ResponseBody
	public Map createProject(@RequestBody CreateProjectFormBean bean)
			throws Exception {

		System.out.println(bean);

		ProjectBean project2 = new ProjectBean();
		project2.setProjectId(110);
		project2.setCreatedAt(new Date());
		project2.setCreatedBy("ConyGA");
		project2.setDescription("testing project");
		project2.setFolderTree(this.sysConfig.prepareFolderStructure());
		project2.setProjectCde("9910-1301-112");
		try {
			projectService.createProject(project2,
					this.sysConfig.prepareFolderStructure());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ImmutableMap.of("type", "success");

	}

	@RequestMapping(method = RequestMethod.GET, value = "doSearch")
	@ResponseBody
	public List doSearch(@RequestParam String searchClinetno,
			@RequestParam String searchAnykey) throws Exception {

		System.out.println(searchClinetno);
		System.out.println(searchAnykey);

		return WebUtils.toSearchGrid();
 
	}

	@RequestMapping(method = RequestMethod.GET, value = "getProject")
	@ResponseBody
	public Map getProject(@RequestParam String projectId) throws Exception {

		System.out.println(projectId);

		return WebUtils.toProjectSummaries();

	}
}
