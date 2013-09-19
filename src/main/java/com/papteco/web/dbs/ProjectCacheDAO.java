package com.papteco.web.dbs;

import java.io.File;
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
	
	private static PrimaryIndex<String, ProjectBean> projectIdIndex;

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

		projectIdIndex = store.getPrimaryIndex(String.class,
				ProjectBean.class);
	}

	// this is retry function
	public static void saveProjectTree(ProjectBean project) {
		projectIdIndex.put(project);
	}
	
	public static ProjectBean getProjectTree(String id) {
		return projectIdIndex.get(id);
	}

	public static String getMaxProjectId(){
		if(projectIdIndex != null && projectIdIndex.count() == 0){
			return "001";
		}else{
			return getIncreaseNumberBylatestKey(projectIdIndex.sortedMap().lastKey());
		}
	}
	
	public static String getIncreaseNumberBylatestKey(String key){
		Integer intKey = Integer.valueOf(key);
		return formatIncreaseNumber(String.valueOf(intKey+1), 3);
	}
	
	public static String formatIncreaseNumber(String num, int digs){
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < digs-num.length(); i++){
			result.append("0");
		}
		result.append(num);
		return result.toString();
	}
	
	public static List<ProjectBean> getAllProjectBeans(){
		EntityCursor<ProjectBean> allProjects = projectIdIndex.entities();
		List<ProjectBean> result = new LinkedList<ProjectBean>();
		for (ProjectBean bean : allProjects){
			result.add(bean);
		}
		allProjects.close();
		return result;
	}
	
	public static List<ProjectBean> getProjectBeansByFilter(String clientNo, String anyKey){
		EntityCursor<ProjectBean> allProjects = projectIdIndex.entities();
		List<ProjectBean> result = new LinkedList<ProjectBean>();
		for (ProjectBean bean : allProjects){
			if(StringUtils.isBlank(clientNo) && StringUtils.isBlank(anyKey)){
				result.add(bean);
			}else if(StringUtils.isBlank(clientNo) && StringUtils.isNotBlank(anyKey)){
				List<String> files = bean.getTotalFileList();
				for(String file : files){
					if(file.contains(anyKey)){
						result.add(bean);
						break;
					}
				}
			}else if(StringUtils.isNotBlank(clientNo) && StringUtils.isBlank(anyKey)){
				if(clientNo.trim().equals(bean.getClientNo().trim())){
					result.add(bean);
				}
			}else{
				if(clientNo.trim().equals(bean.getClientNo().trim())){
					List<String> files = bean.getTotalFileList();
					for(String file : files){
						if(file.contains(anyKey)){
							result.add(bean);
							break;
						}
					}
				}
			}
		}
		allProjects.close();
		return result;
	}
	
	public static void saveFileBean(String projectId,String docType, FileBean fileBean){
		ProjectBean project = projectIdIndex.get(projectId);
		for(FolderBean folder : project.getFolderTree()){
			if(docType.equals(folder.getDocType())){
				List<FileBean> fileList = folder.getFileTree();
				fileList.add(fileBean);
				folder.setFileTree(fileList);
				break;
			}
		}
		List<String> totalFileList = project.getTotalFileList();
		totalFileList.add(fileBean.getFileName());
		project.setTotalFileList(totalFileList);
		saveProjectTree(project);
	}
	
	public static void deleteFileBean(String projectId,String docType, String fileName){
		ProjectBean project = projectIdIndex.get(projectId);
		for(FolderBean folder : project.getFolderTree()){
			if(docType.equals(folder.getDocType())){
				List<FileBean> fileList = folder.getFileTree();
				FileBean removingfile = null;
				for(FileBean file : fileList){
					if(fileName.equals(file.getFileName())){
						removingfile = file;
						break;
					}
				}
				fileList.remove(removingfile);
				folder.setFileTree(fileList);
				break;
			}
		}
		List<String> totalFileList = project.getTotalFileList();
		totalFileList.remove(fileName);
		project.setTotalFileList(totalFileList);
		saveProjectTree(project);
	}
	
	/* mandatory constructor method */
	public ProjectCacheDAO() {
		
	}
}
