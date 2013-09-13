package com.papteco.web.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class SearchShortcutBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private String usracct;
	
	private Map<String, String[]> searchShortcuts = new LinkedHashMap<String, String[]>();
	
	public String getUsracct() {
		return usracct;
	}

	public void setUsracct(String usracct) {
		this.usracct = usracct;
	}

	public Map<String, String[]> getSearchShortcuts() {
		return searchShortcuts;
	}

	public void setSearchShortcuts(Map<String, String[]> searchShortcuts) {
		this.searchShortcuts = searchShortcuts;
	}
	
}
