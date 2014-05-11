package com.papteco.web.dbs;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.papteco.web.beans.IPItem;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

@Component
public class UserIPDAO {

	public static final String USER_IP = "USERIP";

	@Value("#{settings[datapath]}")
	protected String datapath;

	private static PrimaryIndex<String, IPItem> useripIndex;

	@PostConstruct
	public void init() {
		File f = new File(datapath);
		if (!f.exists()) {
			f.mkdirs();
		}
		new UserIPDAO(datapath);
	}

	public UserIPDAO(String databasePath) {

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

		useripIndex = store.getPrimaryIndex(String.class, IPItem.class);
	}

	// this is retry function
	public static void saveUserIPBean(IPItem ipItem) {
		useripIndex.put(ipItem);
	}

	public static IPItem getUserIPBean(String username) {
		return useripIndex.get(username);
	}

	/* mandatory constructor method */
	public UserIPDAO() {

	}
}
