package com.papteco.web.beans;

import org.springframework.web.multipart.MultipartFile;

public class DocTypeFieldSet {

	private String upload_doctype;
	private String upload_f1;
	private String upload_f2;
	private String upload_f3;
	private String upload_f4;
	private String upload_f5;
	private String upload_f6;
	private String upload_f7;
	private MultipartFile uploadfile;

	public MultipartFile getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(MultipartFile uploadfile) {
		this.uploadfile = uploadfile;
	}

	public String getUpload_doctype() {
		return upload_doctype;
	}

	public void setUpload_doctype(String upload_doctype) {
		this.upload_doctype = upload_doctype;
	}

	public String getUpload_f1() {
		return upload_f1;
	}

	public void setUpload_f1(String upload_f1) {
		this.upload_f1 = upload_f1;
	}

	public String getUpload_f2() {
		return upload_f2;
	}

	public void setUpload_f2(String upload_f2) {
		this.upload_f2 = upload_f2;
	}

	public String getUpload_f3() {
		return upload_f3;
	}

	public void setUpload_f3(String upload_f3) {
		this.upload_f3 = upload_f3;
	}

	public String getUpload_f4() {
		return upload_f4;
	}

	public void setUpload_f4(String upload_f4) {
		this.upload_f4 = upload_f4;
	}

	public String getUpload_f5() {
		return upload_f5;
	}

	public void setUpload_f5(String upload_f5) {
		this.upload_f5 = upload_f5;
	}

	public String getUpload_f6() {
		return upload_f6;
	}

	public void setUpload_f6(String upload_f6) {
		this.upload_f6 = upload_f6;
	}

	public String getUpload_f7() {
		return upload_f7;
	}

	public void setUpload_f7(String upload_f7) {
		this.upload_f7 = upload_f7;
	}

	@Override
	public String toString() {
		return "DocTypeFieldSet [upload_doctype=" + upload_doctype
				+ ", upload_f1=" + upload_f1 + ", upload_f2=" + upload_f2
				+ ", upload_f3=" + upload_f3 + ", upload_f4=" + upload_f4
				+ ", upload_f5=" + upload_f5 + ", upload_f6=" + upload_f6
				+ ", upload_f7=" + upload_f7 + ", uploadfile=" + uploadfile
				+ "]";
	}

}
