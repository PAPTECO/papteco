package com.papteco.web.filters;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.papteco.web.utils.WebUtils;

public class SecurityFilter extends HandlerInterceptorAdapter {
	protected static final Logger log = Logger.getLogger(SecurityFilter.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		List functionList = (List) session.getAttribute("allowFunctions");
		functionList = (functionList == null) ? new ArrayList() : functionList;

		// log.info("### Filtering ### Current functionList: " + functionList);

		String[] curPath = request.getRequestURL().toString().split("/");
		String actionName = curPath[curPath.length - 1];

		log.info("### Filtering ### Action: " + actionName);

		// special handle
		if (actionName.equals("submitUploadFile")) {

			// find out which type it processing
			String doc_type = request.getParameter("upload_doctype");
			log.info("### Filtering ### uploadFile: " + doc_type);
			if (!WebUtils.isDocTypeInEditFunctionList(doc_type, functionList)) {
				// post
				request.getRequestDispatcher("/forbid").forward(request,
						response);
				return false;
			}
		} else if (actionName.equals("editFile")) {
			// find out which type it processing
			String doc_type = request.getParameter("docType");
			log.info("### Filtering ### editFile: " + doc_type);
			if (!WebUtils.isDocTypeInEditFunctionList(doc_type, functionList)) {
				request.getRequestDispatcher("/forbid").forward(request,
						response);
				return false;
			}

		} else if (actionName.equals("viewDocs")) {

			// find out which type it processing
			String filename = request.getParameter("filename");
			log.info("### Filtering ### ViewingFile: " + filename);

			if (!WebUtils.isFilenameInViewFunctionList(filename, functionList)) {
				// get
				request.getRequestDispatcher("/forbidPlan").forward(request,
						response);
				return false;
			}
		} else if (!WebUtils.isActionInFunctionList(actionName, functionList)) {
			request.getRequestDispatcher("/forbid").forward(request, response);
			return false;
		}

		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

}