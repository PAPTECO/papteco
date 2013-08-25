package com.papteco.web.dbs;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.papteco.web.beans.FileBean;
import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.utils.BaseUtils;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

public class CacheDB extends BaseUtils{

//	private static final String DATAPATH = "";
	private static PrimaryIndex<Integer, ProjectBean> projectIndex;

	static{
		File f = new File(props.getProperty("datapath"));
		if(!f.exists()){
			f.mkdirs();
		}
		new CacheDB(props.getProperty("datapath"));
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
				ProjectBean.class);
	}

	// this is retry function
	public static void saveProjectTree(ProjectBean project) {
		projectIndex.put(project);
	}
	
	public static ProjectBean getProjectTree(int id) {
		return projectIndex.get(id);
	}

	public static void main(String[] args) {

		ProjectBean projectexm = new ProjectBean();
		
		projectexm.setCreatedAt(new Date());
		projectexm.setCreatedBy("Cony");
		projectexm.setDescription("This is new project");
		projectexm.setProjectId(203);
		
		FolderBean folderexm1 = new FolderBean();
		
		
		List<FileBean> arr = new ArrayList<FileBean>();
		
		
		FileBean fileexm1 = new FileBean();
		fileexm1.setDescription("this is a new fileA");
		fileexm1.setFileName("FileA");
		
		FileBean fileexm2 = new FileBean();
		fileexm2.setDescription("this is a new fileB");
		fileexm2.setFileName("FileB");
		
		FileBean fileexm3 = new FileBean();
		fileexm3.setDescription("this is a new fileC");
		fileexm3.setFileName("FileC");
		
		arr.add(fileexm1);
		arr.add(fileexm2);
		arr.add(fileexm3);
		
		folderexm1.setFileTree(arr);
		
		List<FolderBean> arrfolder = new ArrayList<FolderBean>();
		arrfolder.add(folderexm1);
		
		projectexm.setFolderTree(arrfolder);
		
//		saveProjectTree(projectexm);
		System.out.println("Project saved");
		
		
		System.out.println("Project print:"+getProjectTree(110));
	}
}
