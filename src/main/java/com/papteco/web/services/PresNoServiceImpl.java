package com.papteco.web.services;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.papteco.web.beans.PreserveNosBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.dbs.PreserveNosDAO;
import com.papteco.web.dbs.ProjectCacheDAO;
import com.papteco.web.utils.FSUtils;

@Service
public class PresNoServiceImpl extends BaseService {
	
	public PresNoServiceImpl(String projectPath) {
		super();
		FSUtils.glanceToCache(projectPath);
	}

	public PreserveNosBean getPresNos() throws IOException{
		return PreserveNosDAO.getPresNosBean(PreserveNosDAO.PRES_NO_CDE);
	}
	
	public void savePresNos(PreserveNosBean presNoBean){
		presNoBean.setPresNoCde(PreserveNosDAO.PRES_NO_CDE);
		PreserveNosDAO.savePresNosBean(presNoBean);
	}
	
	public String isPresNoValidationPassed(int presNoFrom, int presNoTo){
		StringBuffer result = new StringBuffer();
		for(ProjectBean prj : ProjectCacheDAO.getAllProjectBeans()){
			int prjId = Integer.valueOf(prj.getProjectId());
			if(prjId >= presNoFrom && prjId <= presNoTo){
				result.append(prjId + ",");
			}
		}
		if(StringUtils.isNotBlank(result.toString()))
			result.replace(result.lastIndexOf(","), result.length(), "");
		return result.toString();
	}
	
	/* mandatory constructor method */
	public PresNoServiceImpl() {
	}
}
