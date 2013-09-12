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
import com.papteco.web.beans.FieldDef;
import com.papteco.web.beans.FileBean;
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
			
			return ImmutableMap.of("type", "success","projectcode",tmpProject.getProjectCde());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ImmutableMap.of("type", "failure","message",e.getMessage());
		}

		

	}
	
	private String combineFolderPath(String path1, String path2){
		File f = new File(path1, path2);
		if(!f.exists()){
			f.mkdirs();
			f.setExecutable(true, false);
			f.setReadable(true, false);
			f.setWritable(true, false);
		}
		return f.getPath();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "submitUploadFile")
	@ResponseBody
	public String submitUploadFile(DocTypeFieldSet bean,Model model)
			throws Exception {
//		bean.setUpload_doctype("E");
		System.out.println(bean);
		
		String fileFolder = combineFolderPath(combineFolderPath(rootpath, bean.getProjectCde()),this.sysConfig.getFolderNameByFolderCde(bean.getUpload_doctype()));
		String descFileName = bean.getDescription();
		
		StringBuffer trgFileName = new StringBuffer();
		trgFileName.append(bean.getUpload_doctype());
		trgFileName.append(bean.getClientNo());
		trgFileName.append("-");
		if(bean.getDateWith4digs() != null){
			trgFileName.append(bean.getDateWith4digs());
		}else if(bean.getDateWith6digs() != null) {
			trgFileName.append(bean.getDateWith6digs());
		}
		trgFileName.append("-");
		trgFileName.append(bean.getRef());
		trgFileName.append("-");
		trgFileName.append(descFileName);
		trgFileName.append(" - ");
		trgFileName.append("Rev ");
		trgFileName.append(bean.getRev());
		trgFileName.append(bean.getUploadfile().getOriginalFilename().substring(bean.getUploadfile().getOriginalFilename().lastIndexOf(".")));
		
		File file = new File(fileFolder,trgFileName.toString());
		if(!file.exists()){
			file.createNewFile();
		}
		bean.getUploadfile().transferTo(file);
		FileBean fileBean = new FileBean();
		fileBean.setFileName(trgFileName.toString());
		fileBean.setInitUploadAt(new Date());
		fileBean.setLastModifiedAt(new Date());
		fileBean.setLastModifiedBy("wasadmin");
		fileBean.setInitUploadBy("cony");
		fileBean.setDescription(bean.getDescription());
		WebUtils.saveUploadFile(bean.getProjectId(), bean.getUpload_doctype(), fileBean);
		return "success";

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

	@RequestMapping(method = RequestMethod.GET, value = "saveSearch")
	@ResponseBody
	public Map saveSearch(@RequestParam String searchClinetno,
			@RequestParam String searchAnykey,
			@RequestParam String searchSavName) throws Exception {

		System.out.println(searchClinetno);
		System.out.println(searchAnykey);
		System.out.println(searchSavName);

		return ImmutableMap.of("type","success");
 
	}	
	
	@RequestMapping(method = RequestMethod.GET, value = "savePrjshortcut")
	@ResponseBody
	public Map saveSearch(@RequestParam String prjId,
			@RequestParam String prjSavName) throws Exception {

		System.out.println(prjId);
		System.out.println(prjSavName);

		return ImmutableMap.of("type","success");
 
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
		System.out.println("project id is "+prjId);
		System.out.println(this.sysConfig.getSeqAndDesc());
		//#TODO get project information by prjId
		
		String shortCode = docType + "(" +this.sysConfig.getFolderNameByFolderCde(docType)+")"; // hardcode here, please change to D by docType
		FormatItem formating = this.sysConfig.getFormatSetting().get(docType);
		List<FieldDef> fieldSetting = this.sysConfig.getSeqAndDesc();
		String clientno = "(?)"; // please change it by prjId
		String ref = "(?)"; // please change it by prjId
		return WebUtils.toNumberingFormat(prjId, shortCode,formating,fieldSetting,
				clientno,ref);

	}
	
	private String genProjectCreateDate(CreateProjectFormBean bean){
		return bean.getCreateDate().substring(2, 6);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "getDocs")
	@ResponseBody
	public Map getDocs(@RequestParam String projectId) throws Exception {

		System.out.println("getDocs:"+projectId);

		return WebUtils.toDocsSummaries(Integer.valueOf(projectId));

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "deleteDocs")
	@ResponseBody
	public Map deleteDocs(@RequestParam String projectId) throws Exception {

		System.out.println("getDocs:"+projectId);

		return WebUtils.responseWithStatusCode();

	}
}
