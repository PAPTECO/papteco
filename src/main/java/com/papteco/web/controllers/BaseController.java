/**
 * 
 */
package com.papteco.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.papteco.web.utils.SystemConfiguration;


/**
 * @author Administrator
 *
 */
public class BaseController {
	@Autowired
	SystemConfiguration sysConfig;

}
