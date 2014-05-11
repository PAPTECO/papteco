package com.papteco.web.dbs;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.papteco.web.beans.ClientBean;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

@Component
public class ClientDAO {

	public static final String USER_IP = "USERIP";

	@Value("#{settings[datapath]}")
	protected String datapath;

	private static PrimaryIndex<String, ClientBean> clientsIndex;

	@PostConstruct
	public void init() {
		File f = new File(datapath);
		if (!f.exists()) {
			f.mkdirs();
		}
		new ClientDAO(datapath);
	}

	public ClientDAO(String databasePath) {

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

		clientsIndex = store.getPrimaryIndex(String.class, ClientBean.class);
	}

	// this is retry function
	public static void saveClientBean(ClientBean client) {
		clientsIndex.put(client);
	}

	public static ClientBean getClientBean(String clientNo) {
		return clientsIndex.get(clientNo);
	}

	public static void deleteClient(ClientBean client) {
		clientsIndex.delete(client.getClientNo());
	}

	public static List<ClientBean> getClientsByFilter(String clientNo,
			String clientName) {
		EntityCursor<ClientBean> allClients = clientsIndex.entities();
		List<ClientBean> result = new LinkedList<ClientBean>();
		for (ClientBean bean : allClients) {
			if (StringUtils.isBlank(clientNo)
					&& StringUtils.isBlank(clientName)) {
				result.add(bean);
			} else if (StringUtils.isBlank(clientNo)
					&& StringUtils.isNotBlank(clientName)) {
				if (bean.getClientName().contains(clientName)) {
					result.add(bean);
				}
			} else if (StringUtils.isNotBlank(clientNo)
					&& StringUtils.isBlank(clientName)) {
				if (bean.getClientNo().equals(clientNo)) {
					result.add(bean);
				}
			} else {
				if (bean.getClientNo().equals(clientNo)
						&& bean.getClientName().contains(clientName)) {
					result.add(bean);
				}
			}
		}
		allClients.close();
		return result;
	}

	/* mandatory constructor method */
	public ClientDAO() {

	}
}
