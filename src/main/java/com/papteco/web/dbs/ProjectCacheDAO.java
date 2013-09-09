package com.papteco.web.dbs;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.papteco.web.beans.FileBean;
import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.ProjectBean;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

@Component
public class ProjectCacheDAO {

	@Value("#{settings[datapath]}")
	protected String datapath;
	
	private static PrimaryIndex<Integer, ProjectBean> projectIdIndex;

	@PostConstruct
	public void init() {
		File f = new File(datapath);
		if(!f.exists()){
			f.mkdirs();
		}
		new ProjectCacheDAO(datapath);
	}  

	public ProjectCacheDAO(String databasePath) {

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

		projectIdIndex = store.getPrimaryIndex(Integer.class,
				ProjectBean.class);
	}

	// this is retry function
	public static void saveProjectTree(ProjectBean project) {
		projectIdIndex.put(project);
	}
	
	public static ProjectBean getProjectTree(int id) {
		return projectIdIndex.get(id);
	}

	public static int getMaxProjectId(){
		if(projectIdIndex != null && projectIdIndex.count() == 0){
			return 1;
		}else{
			return projectIdIndex.sortedMap().lastKey()+1;
		}
	}
	
	public static EntityCursor<ProjectBean> getAllProjectBeans(){
		return projectIdIndex.entities();
	}
	
	public static List<ProjectBean> getProjectBeansByFilter(String clientNo, String anyKey){
		EntityCursor<ProjectBean> allProjects = projectIdIndex.entities();
		List<ProjectBean> result = new LinkedList<ProjectBean>();
		for (ProjectBean bean : allProjects){
			if(StringUtils.isBlank(clientNo) && StringUtils.isBlank(anyKey)){
				result.add(bean);
			}else if(clientNo.trim().equals(bean.getClientNo().trim())){
				result.add(bean);
			}
		}
		return result;
	}
	
	public static void saveFileBean(int projectId,String docType, FileBean fileBean){
		ProjectBean project = projectIdIndex.get(projectId);
		for(int i = 0; i < project.getFolderTree().size(); i++){
			if(docType.equals(project.getFolderTree().get(i).getDocType())){
				FolderBean folder = project.getFolderTree().get(i);
				if(folder.getFileTree() == null){
					folder.setFileTree(new ArrayList());
				}
				List<FileBean> fileList = project.getFolderTree().get(i).getFileTree();
				fileList.add(fileBean);
				project.getFolderTree().get(i).setFileTree(fileList);
				break;
			};
		}
		saveProjectTree(project);
	}
	/* mandatory constructor method */
	public ProjectCacheDAO() {
		
	}
}
