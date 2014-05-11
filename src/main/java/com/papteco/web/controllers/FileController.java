package com.papteco.web.controllers;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
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
import com.papteco.web.beans.FileLockBean;
import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.IPItem;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.beans.QueueItem;
import com.papteco.web.dbs.FileLockDAO;
import com.papteco.web.dbs.UserIPDAO;
import com.papteco.web.netty.OpenFileClientBuilder;
import com.papteco.web.netty.ReleaseFileClientBuilder;
import com.papteco.web.services.FileServiceImpl;
import com.papteco.web.services.ProjectServiceImpl;
import com.papteco.web.utils.EncoderDecoderUtil;
import com.papteco.web.utils.FilesUtils;
import com.papteco.web.utils.TaskUtils;
import com.papteco.web.utils.WebUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class FileController extends BaseController {
	@Autowired
	private FileServiceImpl fileService;
	@Autowired
	private ProjectServiceImpl projectService;

	private String[] fileStructPath;
	private String serverFilePath;

	private String prepareFileName(DocTypeFieldSet bean) {
		StringBuffer trgFileName = new StringBuffer();

		trgFileName.append(bean.getUpload_doctype());
		trgFileName.append(bean.getClientNo());
		trgFileName.append("-");

		if (StringUtils.isNotBlank(bean.getDrawintType())) {
			trgFileName.append(bean.getDrawintType());
			trgFileName.append(bean.getRef());
			trgFileName.append("-");
			trgFileName.append(bean.getL1() + " ");
			trgFileName.append(bean.getL2() + " ");
			trgFileName.append(bean.getL3());
			trgFileName.append("-");
		}

		if (StringUtils.isNotBlank(bean.getDateWith4digs())) {
			trgFileName.append(bean.getDateWith4digs());
			trgFileName.append("-");
			trgFileName.append(bean.getRef());
			trgFileName.append("-");

		}

		if (StringUtils.isNotBlank(bean.getDateWith6digs())) {
			trgFileName.append(bean.getDateWith6digs());
			trgFileName.append("-");
			trgFileName.append(bean.getRef());
			trgFileName.append("-");
		}

		trgFileName
				.append(StringEscapeUtils.unescapeHtml(bean.getDescription()));
		trgFileName.append("-");
		trgFileName.append("Rev");
		if (bean.getRev().length() < 3)
			trgFileName.append(formatedNumber(bean.getRev(), 3));
		else
			trgFileName.append(bean.getRev());

		if (StringUtils.isNotBlank(bean.getUploadedCopyForm())
				&& bean.getUploadedCopyForm().contains(".")) {
			trgFileName.append(bean.getUploadedCopyForm().substring(
					bean.getUploadedCopyForm().lastIndexOf(".")));
		} else {
			if (bean.getUploadfile().getOriginalFilename().contains(".")) {
				trgFileName.append(bean
						.getUploadfile()
						.getOriginalFilename()
						.substring(
								bean.getUploadfile().getOriginalFilename()
										.lastIndexOf(".")));
			}
		}
		return trgFileName.toString();
	}

	@RequestMapping(method = RequestMethod.GET, value = "getPformRef")
	@ResponseBody
	public Map getPformRef(@RequestParam String prjId, @RequestParam String date)
			throws Exception {
		int ref_i = 0;
		logger.info("getDocs:" + prjId + " date:" + date);

		ProjectBean project = projectService.getProject(prjId);
		if (project != null) {
			String clientno = project.getClientNo();
			if (clientno.startsWith("1")) {
				FolderBean p_folder = new FolderBean();
				for (FolderBean folder : project.getFolderTree()) {
					if (folder.getDocType().equals("P")) {
						p_folder = folder;
						break;
					}
				}
				for (FileBean file : p_folder.getFileTree()) {
					String[] f = file.getFileName().split("-");
					if (f[1].equals(date)) {
						int tmpref = Integer.valueOf(file.getRef());
						if (tmpref > ref_i)
							ref_i = tmpref;
					}
				}
				return this.successMessage(of("no",
						formatedNumber(String.valueOf(ref_i + 1), 3)));
			} else {

				return this.successMessage(of("no", project.getProjectId()));
			}
		}
		return this.successMessage(of("no", "001"));
	}

	@RequestMapping(method = RequestMethod.POST, value = "secure/submitUploadFile")
	@ResponseBody
	public String submitUploadFile(DocTypeFieldSet bean, Model model,
			HttpSession session) throws Exception {

		String username = session.getAttribute("LOGIN_USER") != null ? session
				.getAttribute("LOGIN_USER").toString() : "";

		if (StringUtils.isBlank(bean.getUpload_doctype())) {
			bean.setUpload_doctype(bean.getUpload_doctype_tmp());
		}
		if (StringUtils.isNotBlank(bean.getUploadedCopyForm())
				&& !"undefined".equals(bean.getUploadedCopyForm())) {

			String fromfileFolder;
			String fileFolder;
			if (bean.getUploadedCopyForm().substring(1, 2).equals("0")) {
				fromfileFolder = combineFolderPath(
						combineFolderPath(rootpath, "Templates"),
						this.sysConfig.getFolderNameByFolderCde(bean
								.getUpload_doctype()));
			} else {
				fromfileFolder = combineFolderPath(
						combineFolderPath(rootpath, bean.getProjectCde()),
						this.sysConfig.getFolderNameByFolderCde(bean
								.getUpload_doctype()));
			}
			fileFolder = combineFolderPath(
					combineFolderPath(rootpath, bean.getProjectCde()),
					this.sysConfig.getFolderNameByFolderCde(bean
							.getUpload_doctype()));

			String fileName = prepareFileName(bean);

			File fromfile = new File(fromfileFolder,
					StringEscapeUtils.unescapeHtml(bean.getUploadedCopyForm()));

			File tofile = new File(fileFolder, fileName);
			FileBean fileBean = new FileBean();
			fileBean.setFileId(FilesUtils.genFileId());
			fileBean.setFileName(fileName);
			fileBean.setInitUploadAt(new Date());
			fileBean.setLastModifiedAt(new Date());
			fileBean.setLastModifiedBy(username);
			fileBean.setInitUploadBy(username);
			BeanUtils.copyProperties(fileBean, bean);

			if (fileService.isFileNameExisting(bean.getProjectId(),
					fileBean.getFileName())) {
				return this.formatJSONToHTML(this
						.failFormatMessage("File already exits!"));
			} else {
				if (!fromfile.exists()) {
					fromfile.createNewFile();
				}
				FilesUtils.copyFile(fromfile.getPath(), tofile.getPath());
				fileService.saveUploadFile(bean.getProjectId(),
						bean.getUpload_doctype(), fileBean);

				// TODO Cony
				// Order client to open the file
				// temp solution for upload
				serverFilePath = tofile.getPath();
				fileStructPath = new String[] {
						bean.getProjectCde(),
						this.sysConfig.getFolderNameByFolderCde(bean
								.getUpload_doctype()), fileBean.getFileName() };

				// open add rev file on local
				QueueItem openfile = new QueueItem();
				openfile.setActionType("OPENFILE");
				// openfile.setParam(fileStructPath); // simplechanges
				openfile.setParam(fileStructPath);
				fileService.saveFileLock(new FileLockBean(fileBean.getFileId(),
						username, new Date()));

				// get remote user ip
				IPItem item = UserIPDAO.getUserIPBean(username);

				if (item == null) {
					return this
							.formatJSONToHTML(this
									.successFormatMessage(ofFormat(
											"message",
											"File is created successfully. Program attempts to open file but detected client had not open.")));
				} else {
					OpenFileClientBuilder callback = new OpenFileClientBuilder(
							item.getPCIP(), openfile, serverFilePath,
							fileStructPath);
					FutureTask t = new FutureTask(callback);
					new Thread(t).start();
					try {
						t.get();
						return this.formatJSONToHTML(this
								.successFormatMessage(ofFormat("filename",
										EncoderDecoderUtil
												.encodeURIComponent(fileBean
														.getFileName()))));
					} catch (ExecutionException e) {
						return this
								.formatJSONToHTML(this
										.successFormatMessage(ofFormat(
												"message",
												"File is created successfully. Program attempts to open file but detected client had not open.")));
					} catch (Exception e) {
						return this.formatJSONToHTML(this.failFormatMessage(e
								.getMessage()));
					}

				}
			}

		} else {
			String fileFolder = combineFolderPath(
					combineFolderPath(rootpath, bean.getProjectCde()),
					this.sysConfig.getFolderNameByFolderCde(bean
							.getUpload_doctype()));
			String fileName = prepareFileName(bean);

			File file = new File(fileFolder, fileName);
			FileBean fileBean = new FileBean();
			fileBean.setFileId(FilesUtils.genFileId());
			fileBean.setFileName(fileName);
			fileBean.setInitUploadAt(new Date());
			fileBean.setLastModifiedAt(new Date());
			fileBean.setLastModifiedBy(username);
			fileBean.setInitUploadBy(username);
			BeanUtils.copyProperties(fileBean, bean);

			if (fileService.isFileNameExisting(bean.getProjectId(),
					fileBean.getFileName())) {
				return this.formatJSONToHTML(this
						.failFormatMessage("File already exits!"));
			} else {
				if (!file.exists()) {
					file.createNewFile();
				}
				bean.getUploadfile().transferTo(file);
				fileService.saveUploadFile(bean.getProjectId(),
						bean.getUpload_doctype(), fileBean);
				return this.formatJSONToHTML(this
						.successFormatMessage(ofFormat("filename",
								EncoderDecoderUtil.encodeURIComponent(fileBean
										.getFileName()))));

			}
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "secure/s2ubmitUploadFile")
	@ResponseBody
	public String submitUploadFile2(DocTypeFieldSet bean, Model model,
			HttpSession session) throws Exception {

		return "<textarea name='textarea' id='textarea'>datasss</textarea>";

	}

	@RequestMapping(method = RequestMethod.POST, value = "uploadfile.do")
	public String handleUploadProcess(MultipartHttpServletRequest request,
			Model model) throws Exception {
		logger.info("Upload success");
		MultipartFile file = request.getFile("uploadedfile");
		model.addAttribute("success", "true");
		return "uploadView";
	}

	@RequestMapping(method = RequestMethod.POST, value = "uploadfile2.do")
	public String handleUploadProcess2(
			@RequestParam("file") MultipartFile file, Model model)
			throws Exception {
		logger.info("Upload success");
		logger.info(file);
		model.addAttribute("success", "true");
		return "uploadView";
	}

	@RequestMapping(method = RequestMethod.GET, value = "getDocs")
	@ResponseBody
	public Map getDocs(@RequestParam String projectId) throws Exception {
		logger.info("getDocs:" + projectId);

		return WebUtils.toDocsSummaries(projectId, this.sysConfig);

	}

	@RequestMapping(method = RequestMethod.GET, value = "secure/deleteDocs")
	@ResponseBody
	public Map deleteDocs(@RequestParam String projectId,
			@RequestParam String filename) throws Exception {
		filename = EncoderDecoderUtil.decodeURIComponent(filename);
		ProjectBean project = fileService.getProjectBeanByProjectId(projectId);

		String fileFolder = combineFolderPath(
				combineFolderPath(rootpath, project.getProjectCde()),
				this.sysConfig.getFolderNameByFolderCde(filename
						.substring(0, 1)));
		File file = new File(fileFolder, filename);
		if (file.exists()) {
			file.delete();
			fileService.deleteFile(projectId, filename.substring(0, 1),
					filename);
		}

		return WebUtils.responseWithStatusCode();
	}

	@RequestMapping(method = RequestMethod.GET, value = "secure/viewDocs")
	@ResponseBody
	public Map viewDocs(@RequestParam String projectId,
			@RequestParam String filename, HttpServletResponse response)
			throws Exception {
		filename = StringEscapeUtils.unescapeHtml(filename);
		ProjectBean project = fileService.getProjectBeanByProjectId(projectId);

		String fileFolder = combineFolderPath(
				combineFolderPath(rootpath, project.getProjectCde()),
				this.sysConfig.getFolderNameByFolderCde(filename
						.substring(0, 1)));
		File file = new File(fileFolder, filename);
		if (file.exists()) {
			response.setContentType("application/x-download");
			response.setHeader("Content-disposition", "attachment; filename="
					+ filename);
			IOUtils.copy(fileService.getFileIs(file.getPath()),
					response.getOutputStream());
			response.flushBuffer();
		} else {
			logger.info("no such file.");
		}
		logger.info("viewDocs: " + filename);

		return null;

	}

	@RequestMapping(method = RequestMethod.GET, value = "secure/editFile")
	@ResponseBody
	public Map editFile(@RequestParam String projectId,
			@RequestParam String docType, @RequestParam String filename,
			@RequestParam String fileid, HttpSession session) throws Exception {
		filename = EncoderDecoderUtil.decodeURIComponent(filename);
		String username = session.getAttribute("LOGIN_USER") != null ? session
				.getAttribute("LOGIN_USER").toString() : "";

		logger.info("projectId:" + projectId + " fileid:" + fileid
				+ " doctype:" + docType + " filename:" + filename);

		FileLockBean filelock = fileService.getFileLock(fileid);
		if (filelock != null) {
			if (!filelock.getLockByUser().equals(username)) {
				// locked user not matching with current user
				return this.failMessage("Another user is locking this file!");
			}
		} else {
			fileService.saveFileLock(new FileLockBean(fileid, username,
					new Date()));
		}

		ProjectBean project = projectService.getProject(projectId);

		if (project == null)
			return this.failMessage("Project does not exists.");

		String fileFolder = combineFolderPath(
				combineFolderPath(rootpath, project.getProjectCde()),
				this.sysConfig.getFolderNameByFolderCde(docType));
		File file = new File(fileFolder, filename);

		serverFilePath = file.getPath();
		fileStructPath = new String[] { project.getProjectCde(),
				this.sysConfig.getFolderNameByFolderCde(docType), filename };

		// open add rev file on local
		QueueItem openfile = new QueueItem();
		openfile.setActionType("OPENFILE");
		// openfile.setParam(fileStructPath); //simple changes
		openfile.setParam(fileStructPath);

		// get remote user ip
		IPItem item = UserIPDAO.getUserIPBean(username);

		if (item == null) {
			return this.successMessage(of("message",
					"File is locked successfully. Please open client first."));
		} else {
			OpenFileClientBuilder callback = new OpenFileClientBuilder(
					item.getPCIP(), openfile, serverFilePath, fileStructPath);
			FutureTask t = new FutureTask(callback);
			new Thread(t).start();
			try {
				t.get();
				return this.successMessage();
			} catch (ExecutionException e) {
				return this
						.successMessage(of("message",
								"File is locked successfully. Please open client first."));
			} catch (Exception e) {
				return this.failMessage("File is locked successfully."
						+ e.getMessage());
			}

		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "secure/releaseFile")
	@ResponseBody
	public Map releaseFile(@RequestParam String projectId,
			@RequestParam String docType, @RequestParam String filename,
			@RequestParam String fileid, HttpSession session) throws Exception {
		filename = EncoderDecoderUtil.decodeURIComponent(filename);
		String username = session.getAttribute("LOGIN_USER") != null ? session
				.getAttribute("LOGIN_USER").toString() : "";
		logger.info("projectId:" + projectId + " fileid:" + fileid
				+ " doctype:" + docType + " filename:" + filename);

		FileLockBean filelock = fileService.getFileLock(fileid);
		if (filelock == null) {
			return this.failMessage("File is not locked!");
		} else {
			String taskid = TaskUtils.genTaskId();
			if (!filelock.getLockByUser().equals(username)) {
				// release by user with RL right
				FileLockDAO.deleteFileLockBean(fileid);
				TaskUtils.setTaskStatus(taskid, TaskUtils.STUS_SUCC,
						"Admin Release Successfull.");

			} else {
				TaskUtils.setTaskStatus(taskid, TaskUtils.STUS_START,
						"Starting release.");
				ProjectBean project = projectService.getProject(projectId);
				if (project != null) {
					String fileFolder = combineFolderPath(
							combineFolderPath(rootpath, project.getProjectCde()),
							this.sysConfig.getFolderNameByFolderCde(docType));
					File file = new File(fileFolder, filename);

					serverFilePath = file.getPath();
					fileStructPath = new String[] { project.getProjectCde(),
							this.sysConfig.getFolderNameByFolderCde(docType),
							filename };
					// get remote user ip
					IPItem item = UserIPDAO.getUserIPBean(username);

					if (item == null) {
						return this
								.failMessage("Client is not run, could not edit");
					} else {
						projectService.updateFileBy(project, fileid, username,
								new Date());

						ReleaseFileClientBuilder callback = new ReleaseFileClientBuilder(
								item.getPCIP(), serverFilePath, fileStructPath,
								fileid, taskid);
						FutureTask t = new FutureTask(callback);
						new Thread(t).start();
						try {
							t.get();
						} catch (ExecutionException e) {
							return this.failMessage("Client for user: "
									+ item.getPCID() + " wasn't running!");
						} catch (Exception e) {
							return this.failMessage(e.getMessage());
						}
					}
				}
			}

			// 3 mins timeout
			int i = 0;
			while (i < 90) {
				i++;
				Thread.sleep(2000);

				String[] task = TaskUtils.getTaskStatus(taskid);

				if (task != null && task[0] != "START") {
					return this.successMessage(of("message", task[1]));
				}
			}
			return this.failMessage("Operation timeout, please retry");
		}

	}
}
