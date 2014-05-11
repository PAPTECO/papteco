package com.papteco.web.dbs;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.papteco.web.beans.SearchShortcutBean;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

@Component
public class SearchShortcutDAO {

	@Value("#{settings[datapath]}")
	protected String datapath;

	private static PrimaryIndex<String, SearchShortcutBean> searchShortcutIndex;

	@PostConstruct
	public void init() {
		File f = new File(datapath);
		if (!f.exists()) {
			f.mkdirs();
		}
		new SearchShortcutDAO(datapath);
	}

	public SearchShortcutDAO(String databasePath) {

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

		searchShortcutIndex = store.getPrimaryIndex(String.class,
				SearchShortcutBean.class);
	}

	// this is retry function
	public static void saveSearchShortcut(SearchShortcutBean searchShortcut) {
		searchShortcutIndex.put(searchShortcut);
	}

	public static SearchShortcutBean getSearchShortcut(String usracct) {
		return searchShortcutIndex.get(usracct);
	}

	/* mandatory constructor method */
	public SearchShortcutDAO() {

	}
}
