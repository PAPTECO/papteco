package com.papteco.web.controllers;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.beanutils.BeanUtils;
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
		if (bean.getDateWith4digs() != null) {
			trgFileName.append(bean.getDateWith4digs());
		} else if (bean.getDateWith6digs() != null) {
			trgFileName.append(bean.getDateWith6digs());
		}
		trgFileName.append("-");
		trgFileName.append(bean.getRef());
		trgFileName.append("-");
		trgFileName.append(bean.getDescription());
		trgFileName.append("-");
		trgFileName.append("Rev");
		trgFileName.append(bean.getRev());
		trgFileName.append(bean
				.getUploadfile()
				.getOriginalFilename()
				.substring(
						bean.getUploadfile().getOriginalFilename()
								.lastIndexOf(".")));
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

		WebUtils.saveUploadFile(bean.getProjectId(), bean.getUpload_doctype(),
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

		return WebUtils.toDocsSummaries(Integer.valueOf(projectId),this.sysConfig);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "deleteDocs")
	@ResponseBody
	public Map deleteDocs(@RequestParam String projectId,
			@RequestParam String filename) throws Exception {
//		System.out.println("deleting project id:" + projectId + " document name is "+filename);
		String fileFolder = combineFolderPath(
				combineFolderPath(rootpath, "1000-1309-1"),
				this.sysConfig.getFolderNameByFolderCde(filename.substring(0, 1)));
		File file = new File(fileFolder, "E1000-130916-1-first pp - Rev 1.ppt");
		if (file.exists()) {
			file.delete();
		}
		
		fileService.deleteFile(1, "E", "E1000-130916-1-first pp - Rev 1.ppt");
		return WebUtils.responseWithStatusCode();
	}

	@RequestMapping(method = RequestMethod.GET, value = "viewDocs")
	@ResponseBody
	public Map viewDocs(@RequestParam String projectId,
			@RequestParam String filename,
			HttpServletResponse response) throws Exception {
		String prjCde = "1000-1309-1";
		String docType = "E";
		String fileName = "E1000-130913-1-first memo - Rev 0.1.ppt";

		String fileFolder = combineFolderPath(
				combineFolderPath(rootpath, prjCde),
				this.sysConfig.getFolderNameByFolderCde(docType));
		
		response.setContentType("application/x-download");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		IOUtils.copy(fileService.getFileIs("C:/water.php.jpg"), response.getOutputStream());
	    response.flushBuffer();

//		fileService.localOpenFile("C:/cony/tmpfile/abc.ppt");
		System.out.println("viewDocs: " + "C:/cony/tmpfile/abc.ppt");

		return WebUtils.responseWithStatusCode();

	}
}
