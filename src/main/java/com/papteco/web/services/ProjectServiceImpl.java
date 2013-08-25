package com.papteco.web.services;

import java.util.List;

import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.dbs.CacheDB;
import com.papteco.web.utils.FSUtils;
import com.papteco.web.utils.FoldersUtils;

public class ProjectServiceImpl implements ProjectService {

	public ProjectServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);
	}

	public void createProject(ProjectBean project, List<FolderBean> folderList) throws Exception {
//		FSUtils.putFile(null, null);
		FoldersUtils.createProjectFolders(FoldersUtils.prepareProjectPath(project.getProjectCde()), folderList);
		CacheDB.saveProjectTree(project);
		System.out.println(project.getProjectId());
		System.out.println(CacheDB.getProjectTree(project.getProjectId()));
	}

	/* mandatory constructor method */
	public ProjectServiceImpl() {
	}
}
