package com.papteco.web.beans;

import java.io.Serializable;

public class FieldDef implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6204285762681960938L;
	private String fieldName;
	private String fieldDesc;
	private int maxlength;
	private String dataType;
	private String uivalidatescript;
	private boolean additional;

	public boolean isAdditional() {
		return additional;
	}

	public void setAdditional(boolean additional) {
		this.additional = additional;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public int getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getUivalidatescript() {
		return uivalidatescript;
	}

	public void setUivalidatescript(String uivalidatescript) {
		this.uivalidatescript = uivalidatescript;
	}

}
