package com.papteco.web.services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

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
	
	public InputStream getFileIs(String fromFile) throws IOException{
		return filesUtils.getFileInputStream(fromFile);
	}
	
	/* mandatory constructor method */
	public FileServiceImpl() {
	}
}