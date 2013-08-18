package com.papteco.web.dbs;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.papteco.web.beans.FileTreeBean;
import com.papteco.web.beans.FolderTreeBean;
import com.papteco.web.beans.ProjectTreeBean;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

public class CacheDB {

	private static final String DATAPATH = "tmp/data";
	private static PrimaryIndex<Integer, ProjectTreeBean> projectIndex;

	static{
		File f = new File(DATAPATH);
		if(!f.exists()){
			f.mkdirs();
		}
		new CacheDB(DATAPATH);
	}
	
	public CacheDB(String databasePath) {

		// Open a transactional Berkeley DB engine environment.
		//
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(true);
		Environment env = new Environment(new File(databasePath), envConfig);

		// Open a transactional entity store.
		//
		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(true);
		storeConfig.setTransactional(true);
		EntityStore store = new EntityStore(env, "ProjectStore", storeConfig);

		projectIndex = store.getPrimaryIndex(Integer.class,
				ProjectTreeBean.class);
	}

	// this is retry function
	public static void saveProjectTree(ProjectTreeBean tree) {
		projectIndex.put(tree);
	}
	
	public static ProjectTreeBean getProjectTree(int id) {
		return projectIndex.get(id);
	}

	public static void main(String[] args) {

		ProjectTreeBean projectexm = new ProjectTreeBean();
		
		projectexm.setCreatedAt(new Date());
		projectexm.setCreatedBy("Simple");
		projectexm.setDescription("This is new project");
		projectexm.setProjectId(203);
		
		FolderTreeBean folderexm1 = new FolderTreeBean();
		
		
		List<FileTreeBean> arr = new ArrayList<FileTreeBean>();
		
		
		FileTreeBean fileexm1 = new FileTreeBean();
		fileexm1.setDescription("this is a new fileA");
		fileexm1.setFileName("FileA");
		
		FileTreeBean fileexm2 = new FileTreeBean();
		fileexm2.setDescription("this is a new fileB");
		fileexm2.setFileName("FileB");
		
		FileTreeBean fileexm3 = new FileTreeBean();
		fileexm3.setDescription("this is a new fileC");
		fileexm3.setFileName("FileC");
		
		arr.add(fileexm1);
		arr.add(fileexm2);
		arr.add(fileexm3);
		
		folderexm1.setFileTree(arr);
		
		List<FolderTreeBean> arrfolder = new ArrayList<FolderTreeBean>();
		arrfolder.add(folderexm1);
		
		projectexm.setFolderTree(arrfolder);
		
		saveProjectTree(projectexm);
		System.out.println("Project saved");
		
		
		System.out.println("Project print:"+getProjectTree(203));
	}
}
