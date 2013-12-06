package com.papteco.web.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.papteco.web.beans.DocTypeFieldSet;

@Controller
public class PageController extends BaseController {

	@RequestMapping("/index")  
	public String indexResolver() {  
	   return "index";  
	}
	
	@RequestMapping("/users")  
	public String usersResolver() {  
	   return "users";  
	}
	
	@RequestMapping("/templates")  
	public String templatesResolver() {  
	   return "templates";  
	}
	
	@RequestMapping("/members")  
	public String membersResolver() {  
	   return "members";  
	}
	
	@RequestMapping("/clients")  
	public String clientResolver() {  
	   return "clients";  
	}
	
	@RequestMapping("/validateUser")
	@ResponseBody
	public Map validateUser(
			HttpSession session) throws Exception {

		if(session.getAttribute("LOGIN_USER") != null){
			return this.successMessage();
		}else{
			return this.failMessage("Your session is logout.");
		}
	}
}
