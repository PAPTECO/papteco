package com.papteco.web.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.papteco.web.beans.FileBean;
import com.papteco.web.beans.FileLockBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.dbs.FileLockDAO;
import com.papteco.web.dbs.ProjectCacheDAO;
import com.papteco.web.utils.FSUtils;

@Service
public class FileServiceImpl extends BaseService {
	
	public FileServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);
	}
	
	public void saveFileLock(FileLockBean filelock){
		FileLockDAO.saveFileLockBean(filelock);
	}
	
	public Boolean isFileLocked(String fileid){
		FileLockBean filelock = FileLockDAO.getFileLockBean(fileid);
		if(filelock != null){
			return true;
		}else{
			return false;
		}
	}
	
	public void releaseFile(String fileid){
		FileLockDAO.deleteFileLockBean(fileid);
	}
	
	public void saveUploadFile(String projectId, String docType, FileBean fileBean){
		ProjectCacheDAO.saveFileBean(projectId, docType, fileBean);
	}
	
	public void downloadFile(String fromFile, String toFile) throws IOException{
		filesUtils.downloadFile(fromFile, toFile);
	}
	
	public void localOpenFile(String filepath) throws IOException{
		filesUtils.localOpenFile(filepath);
	}
	
	public InputStream getFileIs(String fromFile) throws IOException{
		return filesUtils.getFileInputStream(fromFile);
	}
	
	public void deleteFile(String prjId, String docType, String fileName) throws IOException{
		ProjectCacheDAO.deleteFileBean(prjId, docType, fileName);
	}
	
	public ProjectBean getProjectBeanByProjectId(String prjId){
		return ProjectCacheDAO.getProjectTree(prjId);
	}
	
	public boolean isFileNameExisting(String projectId, String filename){
		List<String> prjFileList =  ProjectCacheDAO.getProjectTree(projectId).getTotalFileList();
		for(String fname : prjFileList){
			if(filename.equals(fname)){
				return true;
			}
		}
		return false;
	}
	
	/* mandatory constructor method */
	public FileServiceImpl() {
	}
}
