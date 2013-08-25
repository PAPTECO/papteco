package com.papteco.web.services;

import java.util.List;

import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.ProjectBean;

public interface ProjectService {
	
	public void createProject(ProjectBean project, List<FolderBean> folderList) throws Exception;
}
