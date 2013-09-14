package com.papteco.web.services;

import java.io.IOException;
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
public class FileServiceImpl extends BaseService {
	
	public FileServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);
	}

	public void downloadFile(String fromFile, String toFile) throws IOException{
		filesUtils.downloadFile(fromFile, toFile);
	}
	
	public void localOpenFile(String filepath) throws IOException{
		filesUtils.localOpenFile(filepath);
	}
	
	/* mandatory constructor method */
	public FileServiceImpl() {
	}
}
