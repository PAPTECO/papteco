package com.papteco.web.services;

import java.util.List;

import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.beans.ProjectShortcutBean;
import com.papteco.web.beans.SearchShortcutBean;

public interface ProjectService {
	
	public void createProject(ProjectBean project, List<FolderBean> folderList) throws Exception;
	
	public void saveProjectShortcut(String usracct, String prjSavName, String prjId) throws Exception;
	
	public void saveSearchShortcut(String usracct, String searchSavName, String searchClinetno, String searchAnykey) throws Exception;
	
	public SearchShortcutBean getSearchShortcut(String usracct) throws Exception;
	
	public void deleteSearchShortcut(String usracct, String searchSavName) throws Exception;
	
	public ProjectShortcutBean getPrjShortcut(String usracct) throws Exception;
	
	public void deletePrjShortcut(String usracct, String searchSavName) throws Exception;
	
}
