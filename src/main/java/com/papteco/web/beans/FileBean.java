package com.papteco.web.beans;

import java.io.Serializable;
import java.util.Date;

import com.sleepycat.persist.model.Persistent;

@Persistent
public class FileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3007977589740195080L;

	private String fileId;
	
	private String fileName;

	private Date lastModifiedAt;

	private String lastModifiedBy;

	private Date initUploadAt;

	private String initUploadBy;

	private String description;

	private String projectCde;
	private String upload_doctype;
	private String clientNo;
	private String dateWith4digs;
	private String dateWith6digs;
	private String dateCreated;
	private String ref;
	private String certDate;
	private String drawnBy;
	private String uploadedBy;
	private String amount;
	private String paymentDueDate;
	private String requestedBy;
	private String orderedDate;
	private String completedDate;
	private String suppliedBy;
	private String note;
	private String typeOfConfirmation;
	private String formalPrice;
	private String estimatedCost;
	private String rev;

	private String additional1;
	private String additional2;
	private String additional3;
	private String additional4;
	private String additional5;
	private Object additional6;
	private Object additional7;
	private Object additional8;
	private Object additional9;
	private Object additional10;
	
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getProjectCde() {
		return projectCde;
	}

	public void setProjectCde(String projectCde) {
		this.projectCde = projectCde;
	}

	public String getUpload_doctype() {
		return upload_doctype;
	}

	public void setUpload_doctype(String upload_doctype) {
		this.upload_doctype = upload_doctype;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getDateWith4digs() {
		return dateWith4digs;
	}

	public void setDateWith4digs(String dateWith4digs) {
		this.dateWith4digs = dateWith4digs;
	}

	public String getDateWith6digs() {
		return dateWith6digs;
	}

	public void setDateWith6digs(String dateWith6digs) {
		this.dateWith6digs = dateWith6digs;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getCertDate() {
		return certDate;
	}

	public void setCertDate(String certDate) {
		this.certDate = certDate;
	}

	public String getDrawnBy() {
		return drawnBy;
	}

	public void setDrawnBy(String drawnBy) {
		this.drawnBy = drawnBy;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(String orderedDate) {
		this.orderedDate = orderedDate;
	}

	public String getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}

	public String getSuppliedBy() {
		return suppliedBy;
	}

	public void setSuppliedBy(String suppliedBy) {
		this.suppliedBy = suppliedBy;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTypeOfConfirmation() {
		return typeOfConfirmation;
	}

	public void setTypeOfConfirmation(String typeOfConfirmation) {
		this.typeOfConfirmation = typeOfConfirmation;
	}

	public String getFormalPrice() {
		return formalPrice;
	}

	public void setFormalPrice(String formalPrice) {
		this.formalPrice = formalPrice;
	}

	public String getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(String estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getInitUploadAt() {
		return initUploadAt;
	}

	public void setInitUploadAt(Date initUploadAt) {
		this.initUploadAt = initUploadAt;
	}

	public String getInitUploadBy() {
		return initUploadBy;
	}

	public void setInitUploadBy(String initUploadBy) {
		this.initUploadBy = initUploadBy;
	}

	public String getAdditional1() {
		return additional1;
	}

	public void setAdditional1(String additional1) {
		this.additional1 = additional1;
	}

	public String getAdditional2() {
		return additional2;
	}

	public void setAdditional2(String additional2) {
		this.additional2 = additional2;
	}

	public String getAdditional3() {
		return additional3;
	}

	public void setAdditional3(String additional3) {
		this.additional3 = additional3;
	}

	public String getAdditional4() {
		return additional4;
	}

	public void setAdditional4(String additional4) {
		this.additional4 = additional4;
	}

	public String getAdditional5() {
		return additional5;
	}

	public void setAdditional5(String additional5) {
		this.additional5 = additional5;
	}

	public Object getAdditional6() {
		return additional6;
	}

	public void setAdditional6(Object additional6) {
		this.additional6 = additional6;
	}

	public Object getAdditional7() {
		return additional7;
	}

	public void setAdditional7(Object additional7) {
		this.additional7 = additional7;
	}

	public Object getAdditional8() {
		return additional8;
	}

	public void setAdditional8(Object additional8) {
		this.additional8 = additional8;
	}

	public Object getAdditional9() {
		return additional9;
	}

	public void setAdditional9(Object additional9) {
		this.additional9 = additional9;
	}

	public Object getAdditional10() {
		return additional10;
	}

	public void setAdditional10(Object additional10) {
		this.additional10 = additional10;
	}

	@Override
	public String toString() {
		return "FileBean [fileName=" + fileName + ", lastModifiedAt="
				+ lastModifiedAt + ", lastModifiedBy=" + lastModifiedBy
				+ ", initUploadAt=" + initUploadAt + ", initUploadBy="
				+ initUploadBy + ", description=" + description
				+ ", projectCde=" + projectCde + ", upload_doctype="
				+ upload_doctype + ", clientNo=" + clientNo
				+ ", dateWith4digs=" + dateWith4digs + ", dateWith6digs="
				+ dateWith6digs + ", dateCreated=" + dateCreated + ", ref="
				+ ref + ", certDate=" + certDate + ", drawnBy=" + drawnBy
				+ ", uploadedBy=" + uploadedBy + ", amount=" + amount
				+ ", paymentDueDate=" + paymentDueDate + ", requestedBy="
				+ requestedBy + ", orderedDate=" + orderedDate
				+ ", completedDate=" + completedDate + ", suppliedBy="
				+ suppliedBy + ", note=" + note + ", typeOfConfirmation="
				+ typeOfConfirmation + ", formalPrice=" + formalPrice
				+ ", estimatedCost=" + estimatedCost + ", rev=" + rev + "]";
	}

}
