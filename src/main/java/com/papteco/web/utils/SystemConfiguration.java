package com.papteco.web.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.papteco.web.beans.ClientBean;
import com.papteco.web.beans.FieldDef;
import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.FormatItem;

public class SystemConfiguration extends BaseUtils {

	private Map<String, FormatItem> formatSetting;

	public Map<String, FormatItem> getFormatSetting() {
		return formatSetting;
	}

	public void setFormatSetting(Map<String, FormatItem> formatSetting) {
		this.formatSetting = formatSetting;
	}

	private List<FieldDef> seqAndDesc;

	public List<FieldDef> getSeqAndDesc() {
		return seqAndDesc;
	}

	public void setSeqAndDesc(List<FieldDef> seqAndDesc) {
		this.seqAndDesc = seqAndDesc;
	}

	/* pre defined folders structure */
	private List<FolderBean> preDefineFolderStructure;

	public void setPreDefineFolderStructure(
			List<FolderBean> preDefineFolderStructure) {
		this.preDefineFolderStructure = preDefineFolderStructure;
	}

	public List<FolderBean> prepareFolderStructure() {
		return preDefineFolderStructure;
	}

	/* pre defined clients information */
	private List<ClientBean> preDefineClientsInfo;

	public void setPreDefineClientsInfo(List<ClientBean> preDefineClientsInfo) {
		this.preDefineClientsInfo = preDefineClientsInfo;
	}

	public List<ClientBean> prepareClientsInfo() {
		return preDefineClientsInfo;
	}
}
