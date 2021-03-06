package com.papteco.web.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.beans.ProjectShortcutBean;
import com.papteco.web.beans.SearchShortcutBean;
import com.papteco.web.dbs.ProjectBackupDAO;
import com.papteco.web.dbs.ProjectCacheDAO;
import com.papteco.web.dbs.ProjectShortcutDAO;
import com.papteco.web.dbs.SearchShortcutDAO;
import com.papteco.web.utils.FSUtils;

@Service
public class ProjectServiceImpl extends BaseService {

	public ProjectServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);
	}

	public ProjectBean getProject(String prjId) throws Exception {
		return ProjectCacheDAO.getProjectTree(prjId);
	}

	public void updateFileBy(ProjectBean project, String fileid,
			String username, Date date) {
		boolean flag = true;
		for (int i = 0; i < project.getFolderTree().size(); i++) {
			for (int j = 0; j < project.getFolderTree().get(i).getFileTree()
					.size(); j++) {
				if (project.getFolderTree().get(i).getFileTree().get(j)
						.getFileId().equals(fileid)) {
					project.getFolderTree().get(i).getFileTree().get(j)
							.setLastModifiedAt(date);
					project.getFolderTree().get(i).getFileTree().get(j)
							.setLastModifiedBy(username);
					ProjectCacheDAO.saveProjectTree(project);
					flag = false;
					break;
				}
			}
			if (!flag)
				break;
		}
	}

	public void createProject(ProjectBean project, List<FolderBean> folderList)
			throws Exception {
		foldersUtils.createProjectFolders(
				foldersUtils.prepareProjectPath(project.getProjectCde()),
				folderList);
		ProjectCacheDAO.saveProjectTree(project);
	}

	public String deleteProject(String prjId) throws Exception {
		ProjectBean delPrj = ProjectCacheDAO.getProjectTree(prjId);
		if (delPrj == null) {
			return "NO_PROJECT";
		}
		ProjectBackupDAO.saveProjectTree(delPrj);
		ProjectCacheDAO.deleteProjectTree(prjId);
		foldersUtils.deletePrjAndRenameFolder(foldersUtils
				.prepareProjectPath(delPrj.getProjectCde()));
		return "SUCCESS";
	}

	public void saveProjectShortcut(String usracct, String prjSavName,
			String prjId) throws Exception {
		ProjectShortcutBean prjshortcut = ProjectShortcutDAO
				.getProjectShortcut(usracct);
		if (prjshortcut == null) {
			prjshortcut = new ProjectShortcutBean();
			prjshortcut.setUsracct(usracct);
		}
		prjshortcut.getPrjShortcuts().put(prjSavName, prjId);
		ProjectShortcutDAO.saveProjectShortcut(prjshortcut);
	}

	public void saveSearchShortcut(String usracct, String searchSavName,
			String searchClinetno, String searchAnykey) throws Exception {
		SearchShortcutBean searchShortcut = SearchShortcutDAO
				.getSearchShortcut(usracct);
		if (searchShortcut == null) {
			searchShortcut = new SearchShortcutBean();
			searchShortcut.setUsracct(usracct);
		}
		searchShortcut.getSearchShortcuts().put(searchSavName,
				new String[] { searchClinetno, searchAnykey });
		SearchShortcutDAO.saveSearchShortcut(searchShortcut);
	}

	public SearchShortcutBean getSearchShortcut(String usracct)
			throws Exception {
		return SearchShortcutDAO.getSearchShortcut(usracct);
	}

	public void deleteSearchShortcut(String usracct, String searchSavName)
			throws Exception {
		SearchShortcutBean shortcuts = SearchShortcutDAO
				.getSearchShortcut(usracct);
		shortcuts.getSearchShortcuts().remove(searchSavName);
		SearchShortcutDAO.saveSearchShortcut(shortcuts);
	}

	public ProjectShortcutBean getPrjShortcut(String usracct) throws Exception {
		return ProjectShortcutDAO.getProjectShortcut(usracct);
	}

	public void deletePrjShortcut(String usracct, String searchSavName)
			throws Exception {
		ProjectShortcutBean prjshortcut = ProjectShortcutDAO
				.getProjectShortcut(usracct);
		prjshortcut.getPrjShortcuts().remove(searchSavName);
		ProjectShortcutDAO.saveProjectShortcut(prjshortcut);
	}

	public boolean isPrjIdExisting(String prjId) {
		ProjectBean prjBean = ProjectCacheDAO.getProjectTree(prjId);
		if (prjBean == null) {
			return false;
		} else {
			return true;
		}
	}

	/* mandatory constructor method */
	public ProjectServiceImpl() {
	}
}
