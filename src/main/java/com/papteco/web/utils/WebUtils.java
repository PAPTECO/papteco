package com.papteco.web.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.papteco.web.beans.ClientBean;
import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.dbs.DBCacheDAO;

public class WebUtils {

	public static Map toTreeJson(List<FolderBean> beans) {

		Map result = Maps.newHashMap();
		result.put("identifier", "docType");
		result.put("label", "folderName");

		List resultList = Lists.newArrayList();
		for (FolderBean bean : beans) {
			resultList.add(ImmutableMap.of("docType", bean.getDocType(),
					"folderName", bean.getFolderName(), "nuberformat",
					bean.getNuberformat(), "children", Lists.newArrayList(),
					"type", "folder"));
		}

		result.put("items", resultList);

		return result;
	}
	
	public static Map toDocJson(List<FolderBean> beans) {

		Map result = Maps.newHashMap();
		
		List resultList = Lists.newArrayList();
		for (FolderBean bean : beans) {
			resultList.add(ImmutableMap.of("id", bean.getDocType(),
					"name", bean.getFolderName()));
		}
		result.put("data", resultList);
		return result;
	}

	public static Map toClientJson(List<ClientBean> prepareClientsInfo) {
		Map result = Maps.newHashMap();

		List dataList = Lists.newArrayList();

		// sort first
		Collections.sort(prepareClientsInfo);

		for (ClientBean bean : prepareClientsInfo) {
			dataList.add(ImmutableMap.of("id", bean.getClientNo(), "name", bean
					.getClientNo().concat("-").concat(bean.getClientName())));
		}

		result.put("data", dataList);
		return result;
	}

	public static Map toUniqueJson() {
		System.out.println(DBCacheDAO.getMaxProjectId());
		return ImmutableMap.of("max", DBCacheDAO.getMaxProjectId());
	}

	public static List toSearchGrid(String searchClinetno, String searchAnykey) {

		List<ProjectBean> searchResult = DBCacheDAO.getProjectBeansByFilter(searchClinetno, searchAnykey);
		List datalist = Lists.newArrayList();

		for(int i = 0; i< searchResult.size(); i++){
			ProjectBean bean = searchResult.get(i);
			Map data = ImmutableMap
					.of("col1",
							bean.getProjectCde(),
							"col2",
							bean.getCreatedAt().toLocaleString(),
							"col3",
							bean.getShortDesc(),
							"col4",
							"E9970-130310-136-Carbide knife grinder - Specifications.doc\n E9970-130310-136-Project2-Specifications.doc",
							"col5",
							bean.getCreatedBy());
			Map testdata = Maps.newHashMap();
			testdata.put("id", i+1);
			testdata.putAll(data);
			datalist.add(testdata);
		}
		return datalist;
	}

	public static Map toProjectSummaries(int projectId) {
		ProjectBean bean = DBCacheDAO.getProjectTree(projectId);
		
		return ImmutableMap.of("projectIndentify", bean.getProjectCde(), "createdBy",
				bean.getCreatedBy(), "createdAt", bean.getCreatedAt().toLocaleString(), "description",
				bean.getLongDesc());
		
	}
	
	public static Map toDocsSummaries() {
		return ImmutableMap.of("id", "AF", "name",
				"Africa", "type", "continent", "children",
				Lists.newArrayList());
		
	}
	
	public static Map responseWithStatusCode() {
		return responseWithStatusCode(true,"None");
		
	}
	
	public static Map responseWithStatusCode(boolean status,String errmsg) {
		return ImmutableMap.of("status",status,"err",StringUtils.isEmpty(errmsg)?"None":errmsg);
		
	}
}
