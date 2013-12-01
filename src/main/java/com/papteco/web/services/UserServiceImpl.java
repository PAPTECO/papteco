package com.papteco.web.services;

import org.springframework.stereotype.Service;

import com.papteco.web.beans.UsersBean;
import com.papteco.web.dbs.UserDAO;
import com.papteco.web.utils.FSUtils;

@Service
public class UserServiceImpl extends BaseService {
	
	public UserServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);
	}

	public void saveUser(UsersBean user){
		UserDAO.saveUser(user);
	}
	
	public UsersBean getUser(String username){
		return UserDAO.getUser(username);
	}
	
	public UsersBean validateUser(String username){
		UsersBean user = UserDAO.getUser(username);
		return user;
	}
	
	/* mandatory constructor method */
	public UserServiceImpl() {
	}
}
