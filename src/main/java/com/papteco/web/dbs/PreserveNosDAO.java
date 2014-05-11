package com.papteco.web.dbs;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.papteco.web.beans.PreserveNosBean;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

@Component
public class PreserveNosDAO {

	public static final String PRES_NO_CDE = "PRESERVENOS";

	@Value("#{settings[datapath]}")
	protected String datapath;

	private static PrimaryIndex<String, PreserveNosBean> presNosIndex;

	@PostConstruct
	public void init() {
		File f = new File(datapath);
		if (!f.exists()) {
			f.mkdirs();
		}
		new PreserveNosDAO(datapath);
	}

	public PreserveNosDAO(String databasePath) {

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

		presNosIndex = store.getPrimaryIndex(String.class,
				PreserveNosBean.class);
		if (presNosIndex.get(PRES_NO_CDE) == null) {
			presNosIndex.put(new PreserveNosBean(PRES_NO_CDE, 0, 0));
		}
	}

	// this is retry function
	public static void savePresNosBean(PreserveNosBean presNoBean) {
		presNosIndex.put(presNoBean);
	}

	public static PreserveNosBean getPresNosBean(String presNoCde) {
		return presNosIndex.get(presNoCde);
	}

	/* mandatory constructor method */
	public PreserveNosDAO() {

	}
}
