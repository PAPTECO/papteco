package com.papteco.web.services;

import com.papteco.web.beans.ProjectBean;
import com.papteco.web.dbs.CacheDB;
import com.papteco.web.utils.FSUtils;

public class ProjectServiceImpl implements ProjectService {

	public ProjectServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);
	}

	public void createProject(ProjectBean project) throws Exception {
		FSUtils.putFile(null, null);
		CacheDB.saveProjectTree(project);
		System.out.println(project.getProjectId());
		System.out.println(CacheDB.getProjectTree(project.getProjectId()));
	}

	/* mandatory constructor method */
	public ProjectServiceImpl() {
	}
}
