package com.papteco.web.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class ProjectShortcutBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private String usracct;
	
	private Map<String, Integer> prjShortcuts = new LinkedHashMap<String, Integer>();
	
	public String getUsracct() {
		return usracct;
	}

	public void setUsracct(String usracct) {
		this.usracct = usracct;
	}

	public Map<String, Integer> getPrjShortcuts() {
		return prjShortcuts;
	}

	public void setPrjShortcuts(Map<String, Integer> prjShortcuts) {
		this.prjShortcuts = prjShortcuts;
	}
	
}
