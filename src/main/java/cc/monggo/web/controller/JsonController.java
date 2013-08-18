package cc.monggo.web.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.monggo.domain.LoginForm;

@Controller
public class JsonController {
    @RequestMapping(value="JsonController")
    public ModelAndView login(HttpServletRequest request,HttpServletResponse response,LoginForm command ){
        Map uidata = new HashMap();
        uidata.put("username", "cony");
        JSONObject json = JSONObject.fromObject(uidata);
        System.out.println(json);
        response.setContentType("text;charset=UTF-8");
        try {
        	 
        	response.getWriter().write(json.toString());
        	 
        	} catch (IOException e) {
        	 
        	// TODO Auto-generated catch block
        	 
        	e.printStackTrace();
        	 
        	}

        return null;
    }
}