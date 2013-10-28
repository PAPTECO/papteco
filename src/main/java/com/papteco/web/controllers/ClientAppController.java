package com.papteco.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.papteco.web.beans.QueueItem;
import com.papteco.web.dbs.UserIPDAO;
import com.papteco.web.netty.NettyAppServerBuilder;
import com.papteco.web.netty.OpenFileClientBuilder;

@Controller
public class ClientAppController extends BaseController {
	private final int port = 8081;
	
	public ClientAppController() throws Exception{
		new Thread(new NettyAppServerBuilder(port)).start();
//		new Thread(new SustainableAppConnClientBuilder()).start();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "openfile")
	public void handleUploadProcess() throws Exception {
		QueueItem openfile = new QueueItem();
		openfile.setActionType("OPENFILE");
		openfile.setParam(combineFolderPath("1000-1310-001",combineFolderPath("Memo","E1000-131021-001-xfdxx-Rev001.jpg")));
		new Thread(new OpenFileClientBuilder(UserIPDAO.getUserIPBean("conygychen").getPCIP(), openfile)).start();
		System.out.println("::::"+UserIPDAO.getUserIPBean("conygychen").getPCIP());
	}
}