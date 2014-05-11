package com.papteco.web.controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.papteco.web.netty.LoginServerBuilder;
import com.papteco.web.netty.NettyAppServerBuilder;
import com.papteco.web.netty.QuartzMailBackupServerBuilder;

@Controller
public class ClientAppController extends BaseController {

	@PostConstruct
	public void ClientAppController() throws Exception {
		new Thread(new NettyAppServerBuilder(rootpath)).start();
		new Thread(new LoginServerBuilder()).start();
		new Thread(new QuartzMailBackupServerBuilder()).start();

	}

	@RequestMapping(method = RequestMethod.GET, value = "openfile")
	public void handleUploadProcess() throws Exception {
	}
}
