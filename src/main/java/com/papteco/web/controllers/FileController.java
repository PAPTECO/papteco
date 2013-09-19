package com.papteco.web.controllers;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.papteco.web.beans.DocTypeFieldSet;
import com.papteco.web.beans.FileBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.services.FileServiceImpl;
import com.papteco.web.utils.WebUtils;

@Controller
public class FileController extends BaseController {
	@Autowired
	private FileServiceImpl fileService;

	private String prepareFileName(DocTypeFieldSet bean){
		StringBuffer trgFileName = new StringBuffer();
		
		trgFileName.append(bean.getUpload_doctype());
		trgFileName.append(bean.getClientNo());
		trgFileName.append("-");
		
		if(StringUtils.isNotBlank(bean.getDrawintType())){
			trgFileName.append(bean.getDrawintType());
			trgFileName.append(bean.getRef());
			trgFileName.append("-");
			trgFileName.append(bean.getL1()+" ");
			trgFileName.append(bean.getL2()+" ");
			trgFileName.append(bean.getL3());
			trgFileName.append("-");
		}
		
		if(StringUtils.isNotBlank(bean.getDateWith4digs())){
			trgFileName.append(bean.getDateWith4digs());
			trgFileName.append("-");
			trgFileName.append(bean.getRef());
			trgFileName.append("-");
			
		}
		
		if(StringUtils.isNotBlank(bean.getDateWith6digs())){
			trgFileName.append(bean.getDateWith6digs());
			trgFileName.append("-");
			trgFileName.append(bean.getRef());
			trgFileName.append("-");
		}
		
		trgFileName.append(bean.getDescription());
		trgFileName.append("-");
		trgFileName.append("Rev");
		trgFileName.append(bean.getRev());
		if(bean.getUploadfile().getOriginalFilename().contains(".")){
			trgFileName.append(bean
					.getUploadfile()
					.getOriginalFilename()
					.substring(
							bean.getUploadfile().getOriginalFilename()
									.lastIndexOf(".")));
		}
		
		return trgFileName.toString();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "submitUploadFile")
	@ResponseBody
	public String submitUploadFile(DocTypeFieldSet bean, Model model)
			throws Exception {

		String fileFolder = combineFolderPath(
				combineFolderPath(rootpath, bean.getProjectCde()),
				this.sysConfig.getFolderNameByFolderCde(bean
						.getUpload_doctype()));
		String fileName = prepareFileName(bean);
		
		File file = new File(fileFolder, fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		bean.getUploadfile().transferTo(file);
		FileBean fileBean = new FileBean();
		fileBean.setFileName(fileName);
		fileBean.setInitUploadAt(new Date());
		fileBean.setLastModifiedAt(new Date());
		fileBean.setLastModifiedBy("admin");
		fileBean.setInitUploadBy("cony");
		
		BeanUtils.copyProperties(fileBean, bean);
		System.out.println(fileBean);

		fileService.saveUploadFile(bean.getProjectId(), bean.getUpload_doctype(),
				fileBean);
		return "success";

	}

	@RequestMapping(method = RequestMethod.POST, value = "uploadfile.do")
	public String handleUploadProcess(MultipartHttpServletRequest request,
			Model model) throws Exception {
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
	
	@RequestMapping(method = RequestMethod.GET, value = "getDocs")
	@ResponseBody
	public Map getDocs(@RequestParam String projectId) throws Exception {
		System.out.println("getDocs:"+projectId);

		return WebUtils.toDocsSummaries(projectId,this.sysConfig);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "deleteDocs")
	@ResponseBody
	public Map deleteDocs(@RequestParam String projectId,
			@RequestParam String filename) throws Exception {
		ProjectBean project = fileService.getProjectBeanByProjectId(projectId);
		
		String fileFolder = combineFolderPath(
				combineFolderPath(rootpath, project.getProjectCde()),
				this.sysConfig.getFolderNameByFolderCde(filename.substring(0, 1)));
		File file = new File(fileFolder, filename);
		if (file.exists()) {
			file.delete();
			fileService.deleteFile(projectId, filename.substring(0, 1), filename);
		}
		
		return WebUtils.responseWithStatusCode();
	}

	@RequestMapping(method = RequestMethod.GET, value = "viewDocs")
	@ResponseBody
	public Map viewDocs(@RequestParam String projectId,
			@RequestParam String filename,
			HttpServletResponse response) throws Exception {

		ProjectBean project = fileService.getProjectBeanByProjectId(projectId);

		
		String fileFolder = combineFolderPath(
				combineFolderPath(rootpath, project.getProjectCde()),
				this.sysConfig.getFolderNameByFolderCde(filename.substring(0, 1)));
		File file = new File(fileFolder, filename);
		if (file.exists()) {
			response.setContentType("application/x-download");
			response.setHeader("Content-disposition", "attachment; filename=" + filename);
			IOUtils.copy(fileService.getFileIs(file.getPath()), response.getOutputStream());
		    response.flushBuffer();
		}else {
			System.out.println("no such file.");
		}
		System.out.println("viewDocs: " + filename);

		return WebUtils.responseWithStatusCode();

	}
}
