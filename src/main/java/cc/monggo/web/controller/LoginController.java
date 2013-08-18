package cc.monggo.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.monggo.domain.LoginForm;

import com.papteco.web.utils.SystemConfiguration;

@Controller
public class LoginController {
	@Autowired
	SystemConfiguration preDefineUtils;

	public void setPre(SystemConfiguration preDefineUtils) {
		this.preDefineUtils = preDefineUtils;
	}
	@RequestMapping(value="login")
    public ModelAndView login(HttpServletRequest request,HttpServletResponse response,LoginForm command ){
        String username = command.getUsername();
        System.out.println("HI");
        System.out.println(preDefineUtils.prepareFolderStructure().size());
        ModelAndView mv = new ModelAndView("/index/index","command","LOGIN SUCCESS, " + username);
        return mv;
    }
}