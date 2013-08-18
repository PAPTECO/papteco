package com.papteco.web.services;

import com.papteco.web.dbs.CacheDB;
import com.papteco.web.utils.FSUtils;

public class ProjectServiceImpl implements ProjectService {

	public ProjectServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);

	}

	public void createProject() throws Exception {
		
		FSUtils.putFile(null, null);
		CacheDB.saveProjectTree(null);

	}

}
