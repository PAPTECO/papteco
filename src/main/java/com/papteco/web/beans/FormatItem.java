/**
 * 
 */
package com.papteco.web.beans;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 */
public class FormatItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1628828347300655771L;
	private ActionEnum code = ActionEnum.autoFillReadOnly;
	private ActionEnum clientNo = ActionEnum.autoFillReadOnly;
	private ActionEnum drawintType = ActionEnum.notApplicable;
	private ActionEnum dateWith4digs = ActionEnum.notApplicable;
	private ActionEnum dateWith6digs = ActionEnum.notApplicable;
	private ActionEnum l1 = ActionEnum.notApplicable;
	private ActionEnum l2 = ActionEnum.notApplicable;
	private ActionEnum l3 = ActionEnum.notApplicable;
	private ActionEnum description = ActionEnum.keyedInByUser;
	private ActionEnum dateCreated = ActionEnum.autoFillCanOverwrite;
	private ActionEnum ref = ActionEnum.autoFillCanOverwrite;
	private ActionEnum certDate = ActionEnum.notApplicable;
	private ActionEnum drawnBy = ActionEnum.notApplicable;
	private ActionEnum uploadedBy = ActionEnum.autoFillCanOverwrite;
	private ActionEnum amount = ActionEnum.notApplicable;
	private ActionEnum paymentDueDate = ActionEnum.notApplicable;
	private ActionEnum requestedBy = ActionEnum.notApplicable;
	private ActionEnum orderedDate = ActionEnum.notApplicable;
	private ActionEnum completedDate = ActionEnum.notApplicable;
	private ActionEnum suppliedBy = ActionEnum.notApplicable;
	private ActionEnum note = ActionEnum.keyedInByUser;
	private ActionEnum typeOfConfirmation = ActionEnum.notApplicable;
	private ActionEnum formalPrice = ActionEnum.notApplicable;
	private ActionEnum estimatedCost = ActionEnum.notApplicable;
	private ActionEnum rev = ActionEnum.autoFillCanOverwrite;
	private ActionEnum additionnal1;
	private ActionEnum additionnal2;
	private ActionEnum additionnal3;
	private ActionEnum additionnal4;
	private ActionEnum additionnal5;
	private ActionEnum additionnal6;
	private ActionEnum additionnal7;
	private ActionEnum additionnal8;
	private ActionEnum additionnal9;
	public ActionEnum getL1() {
		return l1;
	}

	public void setL1(ActionEnum l1) {
		this.l1 = l1;
	}

	public ActionEnum getL2() {
		return l2;
	}

	public void setL2(ActionEnum l2) {
		this.l2 = l2;
	}

	public ActionEnum getL3() {
		return l3;
	}

	public void setL3(ActionEnum l3) {
		this.l3 = l3;
	}

	public ActionEnum getDrawintType() {
		return drawintType;
	}

	public void setDrawintType(ActionEnum drawintType) {
		this.drawintType = drawintType;
	}

	public ActionEnum getDescription() {
		return description;
	}

	public void setDescription(ActionEnum description) {
		this.description = description;
	}

	public ActionEnum getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(ActionEnum dateCreated) {
		this.dateCreated = dateCreated;
	}

	public ActionEnum getRef() {
		return ref;
	}

	public void setRef(ActionEnum ref) {
		this.ref = ref;
	}

	public ActionEnum getCertDate() {
		return certDate;
	}

	public void setCertDate(ActionEnum certDate) {
		this.certDate = certDate;
	}

	public ActionEnum getDrawnBy() {
		return drawnBy;
	}

	public void setDrawnBy(ActionEnum drawnBy) {
		this.drawnBy = drawnBy;
	}

	public ActionEnum getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(ActionEnum uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public ActionEnum getAmount() {
		return amount;
	}

	public void setAmount(ActionEnum amount) {
		this.amount = amount;
	}

	public ActionEnum getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(ActionEnum paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public ActionEnum getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(ActionEnum requestedBy) {
		this.requestedBy = requestedBy;
	}

	public ActionEnum getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(ActionEnum orderedDate) {
		this.orderedDate = orderedDate;
	}

	public ActionEnum getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(ActionEnum completedDate) {
		this.completedDate = completedDate;
	}

	public ActionEnum getSuppliedBy() {
		return suppliedBy;
	}

	public void setSuppliedBy(ActionEnum suppliedBy) {
		this.suppliedBy = suppliedBy;
	}

	public ActionEnum getNote() {
		return note;
	}

	public void setNote(ActionEnum note) {
		this.note = note;
	}

	public ActionEnum getTypeOfConfirmation() {
		return typeOfConfirmation;
	}

	public void setTypeOfConfirmation(ActionEnum typeOfConfirmation) {
		this.typeOfConfirmation = typeOfConfirmation;
	}

	public ActionEnum getFormalPrice() {
		return formalPrice;
	}

	public void setFormalPrice(ActionEnum formalPrice) {
		this.formalPrice = formalPrice;
	}

	public ActionEnum getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(ActionEnum estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	public ActionEnum getCode() {
		return code;
	}

	public void setCode(ActionEnum code) {
		this.code = code;
	}

	public ActionEnum getClientNo() {
		return clientNo;
	}

	public void setClientNo(ActionEnum clientNo) {
		this.clientNo = clientNo;
	}

	public ActionEnum getDateWith4digs() {
		return dateWith4digs;
	}

	public void setDateWith4digs(ActionEnum dateWith4digs) {
		this.dateWith4digs = dateWith4digs;
	}

	public ActionEnum getDateWith6digs() {
		return dateWith6digs;
	}

	public void setDateWith6digs(ActionEnum dateWith6digs) {
		this.dateWith6digs = dateWith6digs;
	}

	public ActionEnum getRev() {
		return rev;
	}

	public void setRev(ActionEnum rev) {
		this.rev = rev;
	}

	public ActionEnum getAdditionnal1() {
		return additionnal1;
	}

	public void setAdditionnal1(ActionEnum additionnal1) {
		this.additionnal1 = additionnal1;
	}

	public ActionEnum getAdditionnal2() {
		return additionnal2;
	}

	public void setAdditionnal2(ActionEnum additionnal2) {
		this.additionnal2 = additionnal2;
	}

	public ActionEnum getAdditionnal3() {
		return additionnal3;
	}

	public void setAdditionnal3(ActionEnum additionnal3) {
		this.additionnal3 = additionnal3;
	}

	public ActionEnum getAdditionnal4() {
		return additionnal4;
	}

	public void setAdditionnal4(ActionEnum additionnal4) {
		this.additionnal4 = additionnal4;
	}

	public ActionEnum getAdditionnal5() {
		return additionnal5;
	}

	public void setAdditionnal5(ActionEnum additionnal5) {
		this.additionnal5 = additionnal5;
	}

	public ActionEnum getAdditionnal6() {
		return additionnal6;
	}

	public void setAdditionnal6(ActionEnum additionnal6) {
		this.additionnal6 = additionnal6;
	}

	public ActionEnum getAdditionnal7() {
		return additionnal7;
	}

	public void setAdditionnal7(ActionEnum additionnal7) {
		this.additionnal7 = additionnal7;
	}

	public ActionEnum getAdditionnal8() {
		return additionnal8;
	}

	public void setAdditionnal8(ActionEnum additionnal8) {
		this.additionnal8 = additionnal8;
	}

	public ActionEnum getAdditionnal9() {
		return additionnal9;
	}

	public void setAdditionnal9(ActionEnum additionnal9) {
		this.additionnal9 = additionnal9;
	}

	@Override
	public String toString() {
		return "FormatItem [code=" + code + ", clientNo=" + clientNo
				+ ", dateWith4digs=" + dateWith4digs + ", dateWith6digs="
				+ dateWith6digs + ", description=" + description
				+ ", dateCreated=" + dateCreated + ", ref=" + ref
				+ ", certDate=" + certDate + ", drawnBy=" + drawnBy
				+ ", uploadedBy=" + uploadedBy + ", amount=" + amount
				+ ", paymentDueDate=" + paymentDueDate + ", requestedBy="
				+ requestedBy + ", orderedDate=" + orderedDate
				+ ", completedDate=" + completedDate + ", suppliedBy="
				+ suppliedBy + ", note=" + note + ", typeOfConfirmation="
				+ typeOfConfirmation + ", formalPrice=" + formalPrice
				+ ", estimatedCost=" + estimatedCost + ", rev=" + rev + "]";
	}

}
