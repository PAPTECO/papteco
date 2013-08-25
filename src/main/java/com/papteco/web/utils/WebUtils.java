package com.papteco.web.utils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.papteco.web.beans.ClientBean;
import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.FolderTreeBean;

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

	public static Map toUniqueJson(int maxno) {
		return ImmutableMap.of("max", maxno);
	}

	public static List toSearchGrid() {

		List datalist = Lists.newArrayList();
		Map data = ImmutableMap
				.of("col1",
						"E9970-130310-136",
						"col2",
						"7 May 2013 13:25:59",
						"col3",
						"Short description",
						"col4",
						"E9970-130310-136-Carbide knife grinder - Specifications.doc\n E9970-130310-136-Project2-Specifications.doc",
						"col5", "James");

		Map testdata = Maps.newHashMap();
		testdata.put("id", "1");
		testdata.putAll(data);
		datalist.add(testdata);

		testdata = Maps.newHashMap();
		testdata.put("id", "2");
		testdata.putAll(data);
		datalist.add(testdata);

		testdata = Maps.newHashMap();
		testdata.put("id", "3");
		testdata.putAll(data);
		datalist.add(testdata);

		return datalist;
	}

	public static Map toProjectSummaries() {
		return ImmutableMap.of("projectIndentify", "xxx-xxx-sample", "createdBy",
				"John", "createdAt", "7 May 2013 13:25:59", "description",
				"description");
		
	}
}
