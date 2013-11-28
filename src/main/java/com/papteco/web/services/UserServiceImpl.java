package com.papteco.web.services;

import org.springframework.stereotype.Service;

import com.papteco.web.beans.UsersBean;
import com.papteco.web.beans.UsersFormBean;
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
	
	public String validateUser(String username, String password){
		UsersBean user = UserDAO.getUser(username);
		if(user == null)
			return "NOUSER";
		else if(user.getPassword().equals(password))
			return "SUCC";
		else
			return "PWDINC";
	}
	
	/* mandatory constructor method */
	public UserServiceImpl() {
	}
}
