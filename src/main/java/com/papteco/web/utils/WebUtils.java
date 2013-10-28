package com.papteco.web.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.papteco.web.beans.ActionEnum;
import com.papteco.web.beans.ClientBean;
import com.papteco.web.beans.FieldDef;
import com.papteco.web.beans.FileBean;
import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.FormatItem;
import com.papteco.web.beans.PreserveNosBean;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.dbs.PreserveNosDAO;
import com.papteco.web.dbs.ProjectCacheDAO;

public class WebUtils {

	private static Map<String, String> drawingType = new LinkedHashMap<String, String>();
	private static String drawingtypeSelect = "";
	static {
		drawingType.put("A", "HVAC");
		drawingType.put("B", "BOM");
		drawingType.put("C", "Civil");
		drawingType.put("D", "P&ID");
		drawingType.put("E", "Electrical Schemes");
		drawingType.put("F", "Fire Protection Documentation");
		drawingType.put("G", "Overall Plans");
		drawingType.put("H", "Hydraulic Drawing");
		drawingType.put("M", "Mechanical");
		drawingType.put("N", "Pneumatic Drawing");
		drawingType.put("P", "Piping");
		drawingType.put("W", "Plumbing");

		StringBuilder sb = new StringBuilder();
		sb.append("<td><select style='width:45px;' class='uploadfileqryonly' id='drawintType' name='drawintType'>");
		for (String key : drawingType.keySet()) {
			sb.append("<option value='" + key + "'>" + key + "-");
			sb.append(drawingType.get(key));
			sb.append("</option>");
		}
		sb.append("</select></td>");
		drawingtypeSelect = sb.toString();
	}

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
			resultList.add(ImmutableMap.of("id", bean.getDocType(), "name",
					bean.getFolderName()));
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

	public static Map toUniqueJson() {
		System.out.println(ProjectCacheDAO.getMaxProjectId());
		PreserveNosBean presNo = PreserveNosDAO
				.getPresNosBean(PreserveNosDAO.PRES_NO_CDE);
		return ImmutableMap.of("max", ProjectCacheDAO.getMaxProjectId(),
				"preserve", "Preserved from " + presNo.getPresNoFrom() + " to "
						+ presNo.getPresNoTo() + "");
	}

	public static List toSearchGrid(String searchClinetno, String searchAnykey) {

		List<ProjectBean> searchResult = ProjectCacheDAO
				.getProjectBeansByFilter(searchClinetno, searchAnykey);
		List datalist = Lists.newArrayList();

		for (int i = 0; i < searchResult.size(); i++) {
			ProjectBean bean = searchResult.get(i);
			int countFiles = bean.getTotalFileList().size();
			StringBuffer files = new StringBuffer();
			;

			if (StringUtils.isBlank(searchAnykey)) {
				if (countFiles >= 2) {
					files.append(bean.getTotalFileList().get(countFiles - 1)
							+ "; "
							+ bean.getTotalFileList().get(countFiles - 2) + ";");
				} else if (countFiles == 1) {
					files.append(bean.getTotalFileList().get(countFiles - 1)
							+ "; ");
				} else {
					files.append("");
				}
			} else {
				List<String> tolfiles = bean.getTotalFileList();
				int index = 0;
				for (String file : tolfiles) {
					if (index >= 2) {
						break;
					}
					if (file.contains(searchAnykey)) {
						files.append(file + ";");
						index++;
					}
				}
			}

			Map data = ImmutableMap.of("col1", bean.getProjectCde(), "col2",
					bean.getCreatedAt().toLocaleString(), "col3",
					bean.getShortDesc(), "col4", files, "col5",
					bean.getCreatedBy());
			Map testdata = Maps.newHashMap();
			testdata.put("id", bean.getProjectId());
			testdata.putAll(data);
			datalist.add(testdata);
		}
		return datalist;
	}

	public static Map toProjectSummaries(String projectId) {
		ProjectBean bean = ProjectCacheDAO.getProjectTree(projectId);

		return of("projectIndentify", bean.getProjectCde(), "createdBy",
				bean.getCreatedBy(), "createdAt", bean.getCreatedAt()
						.toString(), "description", bean.getLongDesc(),
						"templates",getProjectTemplate());

	}
	
	//TODO Cony
	public static Map getProjectTemplate(){
		
		List dummy = Lists.newArrayList();
		dummy.add("dummy1.xls");
		dummy.add("dummy2.doc");
		dummy.add("dummy3.csv");
		dummy.add("dummy4.ppt");
		
		
		return of("A",dummy,
				"B",dummy,
				"C",dummy);
	}

	public static ActionEnum getValueByFieldName(String fieldName,
			FormatItem obj) {

		Field field;
		try {
			field = FormatItem.class.getDeclaredField(fieldName);
			field.setAccessible(true);

			return (ActionEnum) field.get(obj);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String getValueByFieldName(String fieldName, FileBean obj) {

		Field field;
		try {
			field = FileBean.class.getDeclaredField(fieldName);
			field.setAccessible(true);

			return (String) field.get(obj);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static Map toNumberingFormat(String prjId, String docType,
			FormatItem item, List<FieldDef> seqAndDesc, String clientno,
			String ref) {
		ProjectBean bean = ProjectCacheDAO.getProjectTree(prjId);
		StringBuilder sb = new StringBuilder();

		sb.append("<table class='dijitdialog_index'>");
		sb.append("<tr>");
		for (FieldDef col : seqAndDesc) {
			if (!col.isAdditional()
					&& getValueByFieldName(col.getFieldName(), item) != ActionEnum.notApplicable)
				sb.append(headertd(col, "th"));
		}
		sb.append("</tr>");
		// details
		sb.append("<tr>");
		for (FieldDef col : seqAndDesc) {
			if (!col.isAdditional()
					&& getValueByFieldName(col.getFieldName(), item) != ActionEnum.notApplicable)
				sb.append(detailtd(col, docType, bean.getClientNo(),
						bean.getUniqueNo()));
		}
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("<table class='dijitdialog_index'>");

		for (FieldDef col : seqAndDesc) {

			if (col.isAdditional()
					&& getValueByFieldName(col.getFieldName(), item) != ActionEnum.notApplicable) {
				sb.append("<tr>");
				sb.append(additionalfieldtd(col));
				sb.append("</tr>");
			}
		}

		// copy from field
		
//		sb.append("<tr><td class='normalcolor'>Copy from</td>");
//		sb.append("<td><input class='uploadfileqryonly' id='uploadedCopyForm' name='uploadedCopyForm' disabled='disabled' size='30' maxlength='30'/></td>");
//		sb.append("</tr>");
		
		sb.append("</table>");
		sb.append("<input type='hidden' value='" + prjId
				+ "' name='projectId'/>");
		sb.append("<input type='hidden' value='" + bean.getClientNo()
				+ "' name='clientNo'/>");
		sb.append("<input type='hidden' value='" + bean.getProjectCde()
				+ "' name='projectCde'/>");
		return ImmutableMap.of("data", sb.toString());

	}

	private static Object additionalfieldtd(FieldDef col) {
		return headertd(col, "td") + detailtd(col, null, null, null);
	}

	public static String headertd(FieldDef col, String tag) {
		return new StringBuilder().append("<").append(tag).append(" id='")
				.append(col.getFieldName())
				.append("_header' class='normalcolor'>")
				.append(col.getFieldDesc()).append("</").append(tag)
				.append(">").toString();

	}

	private static final SimpleDateFormat sfyymm = new SimpleDateFormat("yyMM");
	private static final SimpleDateFormat sfyymmdd = new SimpleDateFormat(
			"yyMMdd");

	private static String getDateYYMM() {
		return sfyymm.format(new Date());
	}

	private static String getDateYYMMDD() {
		return sfyymmdd.format(new Date());
	}

	public static String detailtd(FieldDef col, String docType,
			String clientno, String ref) {

		String result = "";
		String defaultValue = "";
		if ("code".equals(col.getFieldName())) {
			result = "<td>" + docType + "</td>";
		} else if ("clientNo".equals(col.getFieldName())) {
			result = "<td>" + clientno + "</td>";
		} else if ("note".equals(col.getFieldName())) {
			result = "<td><textarea class='uploadfileqryonly' id='note' name='note' cols ='10' rows = '2' onkeyup='chkvaldpty(this)'></textarea></td>";
		} else if ("drawintType".equals(col.getFieldName())) {
			result = drawingtypeSelect;
		} else {

			if ("ref".equals(col.getFieldName())) {
				defaultValue = ref;
			} else if ("dateWith4digs".equals(col.getFieldName())) {
				defaultValue = getDateYYMM();
			} else if ("dateWith6digs".equals(col.getFieldName())) {
				defaultValue = getDateYYMMDD();
			} else if ("rev".equals(col.getFieldName())) {
				defaultValue = "001";
			} else if ("l1".equals(col.getFieldName())) {
				defaultValue = "00";
			} else if ("l2".equals(col.getFieldName())) {
				defaultValue = "00";
			} else if ("l3".equals(col.getFieldName())) {
				defaultValue = "000";
			}

			result = new StringBuilder()
					.append("<td><input class='uploadfileqryonly' id='")
					.append(col.getFieldName())
					.append("' name='")
					.append(col.getFieldName())
					.append("' ")
					.append(col.getMaxlength() > 0 ? "size="
							+ col.getMaxlength() + " maxlength= "
							+ col.getMaxlength() : "")
					.append(StringUtils.isNotBlank(col.getUivalidatescript()) ? " onkeyup='"
							+ col.getUivalidatescript() + "(this)' "
							: " onkeyup='chkvaldpty(this)' ")
					.append(" value='" + defaultValue + "'")
					.append(col.getFieldName()).append("></td>").toString();
		}
		return result;

	}

	public static Map of(Object... keyval) {
		Builder b = ImmutableMap.builder();

		for (int i = 0; i < keyval.length; i = i + 2) {
			b.put(keyval[i], keyval[i + 1]);
		}
		return b.build();

	}

	public static Map toDocsSummaries(String projectId,
			SystemConfiguration sysConfig) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("identifier", "id");
		result.put("label", "name");

		List<Map> resultList = Lists.newArrayList();
		ProjectBean project = ProjectCacheDAO.getProjectTree(projectId);
		for (FolderBean folder : project.getFolderTree()) {
			List<FileBean> files = folder.getFileTree();
			if (files == null || files.size() == 0) {
				resultList.add(of("id", folder.getDocType(), "name",
						folder.getFolderName(), "type", "continent",
						"numformat", folder.getNuberformat(), 
						"ftype","folder",
						"docType",folder.getDocType(),
						"children",
						Lists.newArrayList()));
			} else {
				List<Map> subList = Lists.newArrayList();
				for (FileBean file : files) {
					subList.add(of("id", file.getFileName(), "name",
							file.getFileName(), "type", "continent",
							"projectId", projectId, "field_details",
							displayUploadFileFields(file, sysConfig),
							"docType",folder.getDocType(),
							"ftype","file"));

				}
				resultList.add(of("id", folder.getDocType(), "name",
						folder.getFolderName() + "(" + subList.size() + ")",
						"type", "continent", 
						"ftype","folder",
						"docType",folder.getDocType(),
						"numformat",
						folder.getNuberformat(), "children", subList));
			}

		}
		result.put("items", resultList);
		return result;

	}

	public static String displayUploadFileFields(FileBean bean,
			SystemConfiguration sysConfig) {

		FormatItem item = sysConfig.getFormatSetting().get(
				bean.getUpload_doctype());

		StringBuilder sb = new StringBuilder();
		for (FieldDef col : sysConfig.getSeqAndDesc()) {
			if (col.isAdditional()
					&& getValueByFieldName(col.getFieldName(), item) != ActionEnum.notApplicable) {
				sb.append("<p><label for='sf'>");
				sb.append(col.getFieldDesc());
				sb.append("</label><span class='field_desc'>");
				sb.append(getValueByFieldName(col.getFieldName(), bean));
				sb.append("</span></p>");
			}
		}
		return sb.toString();
	}

	public static Map responseWithStatusCode() {
		return responseWithStatusCode(true, "None");

	}

	public static Map responseWithStatusCode(boolean status, String errmsg) {
		return ImmutableMap.of("status", status, "err",
				StringUtils.isEmpty(errmsg) ? "None" : errmsg);

	}
}
