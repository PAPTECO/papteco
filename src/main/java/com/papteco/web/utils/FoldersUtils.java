package com.papteco.web.utils;

import java.io.File;
import java.util.List;

import com.papteco.web.beans.FolderBean;

public class FoldersUtils extends BaseUtils{
	
	// this is retry function
	public static void createProjectFolders(String projectPath, List<FolderBean> folderList){
		if(!projectPath.endsWith("/") && !projectPath.endsWith("\\")){
			projectPath = projectPath + "/";
		}
		
		File f = new File(projectPath);
		if(!f.exists()){
			f.mkdirs();
			f.setExecutable(true, false);
			f.setReadable(true, false);
			f.setWritable(true, false);
			System.out.println("Project \""+ f.getName() + "\" created!");
		}else{
			System.out.println("Project \""+ f.getName() + "\" existing already!");
		}
		
		for(FolderBean folder : folderList){
			File sf = new File(projectPath+folder.getFolderName());
			if(!sf.exists()){
				sf.mkdirs();
				sf.setExecutable(true, false);
				sf.setReadable(true, false);
				sf.setWritable(true, false);
				System.out.println("(execable, readable, writeable) - ("+sf.canExecute()+", "+sf.canRead()+", "+sf.canWrite()+") - "+ projectPath+folder.getFolderName());
			}else{
				System.out.println("(execable, readable, writeable) - ("+sf.canExecute()+", "+sf.canRead()+", "+sf.canWrite()+") - "+ projectPath+folder.getFolderName()+" [existing already]");
			}
		}
		System.out.println("Folders creation finish.");
	}
	
	public static String prepareProjectPath(String projectCode){
		String rPath = props.getProperty("rootpath");
		if(!rPath.endsWith("/") && !rPath.endsWith("\\")){
			rPath = rPath + "/";
		}
		return rPath+projectCode;
	}
	
}