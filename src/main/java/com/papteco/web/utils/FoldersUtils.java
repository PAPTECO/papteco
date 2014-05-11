package com.papteco.web.utils;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.papteco.web.beans.FolderBean;

@Component
public class FoldersUtils extends BaseUtils{
	
	// this is retry function
	public void createProjectFolders(String projectPath, List<FolderBean> folderList){
		File f = new File(projectPath);
		if(!f.exists()){
			f.mkdirs();
			f.setExecutable(true, false);
			f.setReadable(true, false);
			f.setWritable(true, false);
//			log.info("Project \""+ f.getName() + "\" created!");
		}else{
//			log.info("Project \""+ f.getName() + "\" existing already!");
		}
		
		for(FolderBean folder : folderList){
			File sf = new File(projectPath,folder.getFolderName());
			if(!sf.exists()){
				sf.mkdirs();
				sf.setExecutable(true, false);
				sf.setReadable(true, false);
				sf.setWritable(true, false);
//				log.info("(execable, readable, writeable) - ("+sf.canExecute()+", "+sf.canRead()+", "+sf.canWrite()+") - "+ projectPath+"/"+folder.getFolderName());
			}else{
//				log.info("(execable, readable, writeable) - ("+sf.canExecute()+", "+sf.canRead()+", "+sf.canWrite()+") - "+ projectPath+"/"+folder.getFolderName()+" [existing already]");
			}
		}
		log.info("Folders creation finish.");
	}
	
	public void deletePrjAndRenameFolder(String projectPath){
		File f = new File(projectPath);
		if(f.exists()){
			f.renameTo(new File(f.getPath()+"("+ new java.text.SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) +")"));
		}
		
	}
	
	public String prepareProjectPath(String projectCode){
		File prjPath = new File(rootpath, projectCode);
		return prjPath.getPath();
	}
	
}
