package com.papteco.web.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;
import com.papteco.web.beans.CreateProjectFormBean;
import com.papteco.web.beans.FieldDef;
import com.papteco.web.beans.FormatItem;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.services.ProjectService;
import com.papteco.web.utils.WebUtils;

@Controller
public class ProjectController extends BaseController {
	@Autowired
	private ProjectService projectService;

	@RequestMapping(method = RequestMethod.POST, value = "createProject")
	@ResponseBody
	public Map createProject(@RequestBody CreateProjectFormBean bean)
			throws Exception {
		ProjectBean tmpProject = new ProjectBean();
		tmpProject.setProjectCde(bean.getClientno() + "-"
				+ genProjectCreateDate(bean) + "-" + bean.getUniqueno());
		tmpProject.setClientNo(bean.getClientno());
		tmpProject.setCreateDate(genProjectCreateDate(bean));
		tmpProject.setUniqueNo(bean.getUniqueno());
		tmpProject.setCreatedAt(new Date());
		tmpProject.setCreatedBy("wasadmin");
		tmpProject.setShortDesc(bean.getShortdesc());
		tmpProject.setLongDesc(bean.getLongdesc());
		tmpProject.setFolderTree(this.sysConfig.prepareFolderStructure());

		try {
			projectService.createProject(tmpProject,
					this.sysConfig.prepareFolderStructure());

			return ImmutableMap.of("type", "success", "projectcode",
					tmpProject.getProjectCde());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ImmutableMap
					.of("type", "failure", "message", e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "doSearch")
	@ResponseBody
	public List doSearch(@RequestParam String searchClinetno,
			@RequestParam String searchAnykey) throws Exception {
		System.out.println(searchClinetno);
		System.out.println(searchAnykey);
		return WebUtils.toSearchGrid(searchClinetno, searchAnykey);
	}

	@RequestMapping(method = RequestMethod.GET, value = "saveSearch")
	@ResponseBody
	public Map saveSearch(@RequestParam String searchClinetno,
			@RequestParam String searchAnykey,
			@RequestParam String searchSavName) throws Exception {

		System.out.println(searchClinetno);
		System.out.println(searchAnykey);
		System.out.println(searchSavName);
		projectService.saveSearchShortcut("conygychen", searchSavName,
				searchClinetno, searchAnykey);
		return ImmutableMap.of("type", "success");

	}

	@RequestMapping(method = RequestMethod.GET, value = "savePrjshortcut")
	@ResponseBody
	public Map saveSearch(@RequestParam String prjId,
			@RequestParam String prjSavName) throws Exception {

		projectService.saveProjectShortcut("conygychen", prjSavName, prjId);
		return ImmutableMap.of("type", "success");

	}

	@RequestMapping(method = RequestMethod.GET, value = "getProject")
	@ResponseBody
	public Map getProject(@RequestParam String projectId) throws Exception {
		System.out.println(projectId);
		return WebUtils.toProjectSummaries(Integer.valueOf(projectId));

	}

	@RequestMapping(method = RequestMethod.GET, value = "getNumberingFormat")
	@ResponseBody
	public Map getNumberingFormat(@RequestParam String docType,
			@RequestParam String prjId) throws Exception {
		String shortCode = docType + "("
				+ this.sysConfig.getFolderNameByFolderCde(docType) + ")";
		FormatItem formating = this.sysConfig.getFormatSetting().get(docType);
		List<FieldDef> fieldSetting = this.sysConfig.getSeqAndDesc();
		String clientno = "(?)"; // please change it by prjId
		String ref = "(?)"; // please change it by prjId
		return WebUtils.toNumberingFormat(prjId, shortCode, formating,
				fieldSetting, clientno, ref);

	}

	private String genProjectCreateDate(CreateProjectFormBean bean) {
		return bean.getCreateDate().substring(2, 6);
	}
}
