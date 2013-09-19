package com.papteco.web.services;

import java.io.IOException;
import java.util.List;

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
	
	public boolean isPresNoValidationPassed(PreserveNosBean presNoBean){
		for(ProjectBean prj : ProjectCacheDAO.getAllProjectBeans()){
			int prjId = Integer.valueOf(prj.getProjectId());
			if(prjId >= presNoBean.getPresNoFrom() && prjId <= presNoBean.getPresNoTo()){
				return false;
			}
		}
		return true;
	}
	
	/* mandatory constructor method */
	public PresNoServiceImpl() {
	}
}
