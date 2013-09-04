package com.papteco.web.controllers;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.ImmutableMap;
import com.papteco.web.beans.CreateProjectFormBean;
import com.papteco.web.beans.DocTypeFieldSet;
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
		logger.info("Create Project!!!!!");
		ProjectBean tmpProject = new ProjectBean();
		tmpProject.setProjectCde(bean.getClientno()+"-"+genProjectCreateDate(bean)+"-"+bean.getUniqueno());
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ImmutableMap.of("type", "success");

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "submitUploadFile")
	@ResponseBody
	public Map createProject(DocTypeFieldSet bean,Model model)
			throws Exception {

		System.out.println(bean);
		
		bean.getUploadfile().transferTo(new File("C:\\"+bean.getUploadfile().getOriginalFilename()));
		return ImmutableMap.of("type", "success");

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "uploadfile.do")  
    public String handleUploadProcess(  
    		MultipartHttpServletRequest request, Model model)  
            throws Exception {  
		System.out.println("Upload success");
		MultipartFile file = request.getFile("uploadedfile");  
        model.addAttribute("success", "true");  
        return "uploadView";  
    } 
	
	@RequestMapping(method = RequestMethod.POST, value = "uploadfile2.do")  
    public String handleUploadProcess2(  
    		@RequestParam("file") MultipartFile file, Model model)  
            throws Exception {  
		System.out.println("Upload success");
		System.out.println(file);
        model.addAttribute("success", "true");  
        return "uploadView";  
    } 
	

	@RequestMapping(method = RequestMethod.GET, value = "doSearch")
	@ResponseBody
	public List doSearch(@RequestParam String searchClinetno,
			@RequestParam String searchAnykey) throws Exception {

		System.out.println(searchClinetno);
		System.out.println(searchAnykey);

		return WebUtils.toSearchGrid(searchClinetno, searchAnykey);
 
	}

	@RequestMapping(method = RequestMethod.GET, value = "getProject")
	@ResponseBody
	public Map getProject(@RequestParam String projectId) throws Exception {

		System.out.println(projectId);
		return WebUtils.toProjectSummaries(Integer.valueOf(projectId));

	}
	
	private String genProjectCreateDate(CreateProjectFormBean bean){
		return bean.getCreateDate().substring(2, 6);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "getDocs")
	@ResponseBody
	public Map getDocs(@RequestParam String projectId) throws Exception {

		System.out.println("getDocs:"+projectId);

		return WebUtils.toDocsSummaries();

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "deleteDocs")
	@ResponseBody
	public Map deleteDocs(@RequestParam String projectId) throws Exception {

		System.out.println("getDocs:"+projectId);

		return WebUtils.responseWithStatusCode();

	}
}
