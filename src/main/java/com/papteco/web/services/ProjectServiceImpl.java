package com.papteco.web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.dbs.ProjectCacheDAO;
import com.papteco.web.utils.FSUtils;
import com.sleepycat.persist.EntityCursor;

@Service
public class ProjectServiceImpl extends BaseService implements ProjectService {
	
	public ProjectServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);
	}

	public void createProject(ProjectBean project, List<FolderBean> folderList) throws Exception {
//		FSUtils.putFile(null, null);
		project.setProjectId(ProjectCacheDAO.getMaxProjectId());
		foldersUtils.createProjectFolders(foldersUtils.prepareProjectPath(project.getProjectCde()), folderList);
		ProjectCacheDAO.saveProjectTree(project);
		
//		System.out.println(DBCacheDAO.getMaxProjectId());
//		System.out.println(project.getProjectCde());
//		System.out.println(DBCacheDAO.getProjectTree(project.getProjectId()));
//		EntityCursor<ProjectBean> list = DBCacheDAO.getAllProjectBeans();
//		for (ProjectBean bean : list){
//			System.out.println(bean.getClientNo());
//		}
	}

	/* mandatory constructor method */
	public ProjectServiceImpl() {
	}
}
