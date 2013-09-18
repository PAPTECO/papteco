package com.papteco.web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.beans.ProjectShortcutBean;
import com.papteco.web.beans.SearchShortcutBean;
import com.papteco.web.dbs.ProjectCacheDAO;
import com.papteco.web.dbs.ProjectShortcutDAO;
import com.papteco.web.dbs.SearchShortcutDAO;
import com.papteco.web.utils.FSUtils;

@Service
public class ProjectServiceImpl extends BaseService implements ProjectService {
	
	public ProjectServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);
	}

	public void createProject(ProjectBean project, List<FolderBean> folderList) throws Exception {
		project.setProjectId(ProjectCacheDAO.getMaxProjectId());
		foldersUtils.createProjectFolders(foldersUtils.prepareProjectPath(project.getProjectCde()), folderList);
		ProjectCacheDAO.saveProjectTree(project);
	}

	public void saveProjectShortcut(String usracct, String prjSavName, String prjId) throws Exception {
		ProjectShortcutBean prjshortcut = ProjectShortcutDAO.getProjectShortcut(usracct);
		if(prjshortcut == null){
			prjshortcut = new ProjectShortcutBean();
			prjshortcut.setUsracct(usracct);
		}
		prjshortcut.getPrjShortcuts().put(prjSavName, Integer.valueOf(prjId));
		ProjectShortcutDAO.saveProjectShortcut(prjshortcut);
	}
	
	public void saveSearchShortcut(String usracct, String searchSavName, String searchClinetno, String searchAnykey) throws Exception {
		SearchShortcutBean searchShortcut = SearchShortcutDAO.getSearchShortcut(usracct);
		if(searchShortcut == null){
			searchShortcut = new SearchShortcutBean();
			searchShortcut.setUsracct(usracct);
		}
		searchShortcut.getSearchShortcuts().put(searchSavName, new String[]{searchClinetno,searchAnykey});
		SearchShortcutDAO.saveSearchShortcut(searchShortcut);
	}
	
	public SearchShortcutBean getSearchShortcut(String usracct) throws Exception {
		return SearchShortcutDAO.getSearchShortcut(usracct);
	}
	
	public void deleteSearchShortcut(String usracct, String searchSavName) throws Exception {
		 SearchShortcutBean shortcuts = SearchShortcutDAO.getSearchShortcut(usracct);
		 shortcuts.getSearchShortcuts().remove(searchSavName);
		 SearchShortcutDAO.saveSearchShortcut(shortcuts);
	}
	
	public ProjectShortcutBean getPrjShortcut(String usracct) throws Exception {
		return ProjectShortcutDAO.getProjectShortcut(usracct);
	}
	
	public void deletePrjShortcut(String usracct, String searchSavName) throws Exception {
		 ProjectShortcutBean prjshortcut = ProjectShortcutDAO.getProjectShortcut(usracct);
		 prjshortcut.getPrjShortcuts().remove(searchSavName);
		 ProjectShortcutDAO.saveProjectShortcut(prjshortcut);
	}
	
	/* mandatory constructor method */
	public ProjectServiceImpl() {
	}
}
