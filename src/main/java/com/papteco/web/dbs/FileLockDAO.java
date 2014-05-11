package com.papteco.web.dbs;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.papteco.web.beans.FileLockBean;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

@Component
public class FileLockDAO {

	public static final String FILE_LOCK = "FILELOCK";

	@Value("#{settings[datapath]}")
	protected String datapath;

	private static PrimaryIndex<String, FileLockBean> filelockIndex;

	@PostConstruct
	public void init() {
		File f = new File(datapath);
		if (!f.exists()) {
			f.mkdirs();
		}
		new FileLockDAO(datapath);
	}

	public FileLockDAO(String databasePath) {

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

		filelockIndex = store.getPrimaryIndex(String.class, FileLockBean.class);
	}

	// this is retry function
	public static void saveFileLockBean(FileLockBean filelock) {
		filelockIndex.put(filelock);
	}

	public static FileLockBean getFileLockBean(String fileid) {
		return filelockIndex.get(fileid);
	}

	public static void deleteFileLockBean(String fileid) {
		filelockIndex.delete(fileid);
	}

	/* mandatory constructor method */
	public FileLockDAO() {

	}
}
