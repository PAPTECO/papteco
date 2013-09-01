package com.papteco.web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.dbs.DBCacheDAO;
import com.papteco.web.utils.FSUtils;

@Service
public class ProjectServiceImpl extends BaseService implements ProjectService {
	
	public ProjectServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);
	}

	public void createProject(ProjectBean project, List<FolderBean> folderList) throws Exception {
//		FSUtils.putFile(null, null);
		foldersUtils.createProjectFolders(foldersUtils.prepareProjectPath(project.getProjectCde()), folderList);
		DBCacheDAO.saveProjectTree(project);
		System.out.println(project.getProjectId());
		System.out.println(DBCacheDAO.getProjectTree(project.getProjectId()));
	}

	/* mandatory constructor method */
	public ProjectServiceImpl() {
	}
}
