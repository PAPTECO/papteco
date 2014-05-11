package com.papteco.web.dbs;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.papteco.web.beans.ProjectShortcutBean;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

@Component
public class ProjectShortcutDAO {

	@Value("#{settings[datapath]}")
	protected String datapath;

	private static PrimaryIndex<String, ProjectShortcutBean> projectShortcutIndex;

	@PostConstruct
	public void init() {
		File f = new File(datapath);
		if (!f.exists()) {
			f.mkdirs();
		}
		new ProjectShortcutDAO(datapath);
	}

	public ProjectShortcutDAO(String databasePath) {

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

		projectShortcutIndex = store.getPrimaryIndex(String.class,
				ProjectShortcutBean.class);
	}

	// this is retry function
	public static void saveProjectShortcut(ProjectShortcutBean prjShortcut) {
		projectShortcutIndex.put(prjShortcut);
	}

	public static ProjectShortcutBean getProjectShortcut(String usracct) {
		return projectShortcutIndex.get(usracct);
	}

	/* mandatory constructor method */
	public ProjectShortcutDAO() {

	}
}
