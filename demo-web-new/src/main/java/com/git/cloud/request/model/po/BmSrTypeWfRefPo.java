package com.git.cloud.request.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class BmSrTypeWfRefPo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String refId;
	private String srTypeMark;
	private String templateId;

	public BmSrTypeWfRefPo() {
	}

	public BmSrTypeWfRefPo(String refId) {
		this.refId = refId;
	}

	public BmSrTypeWfRefPo(String refId, String srTypeMark, String templateId) {
		this.refId = refId;
		this.srTypeMark = srTypeMark;
		this.templateId = templateId;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getSrTypeMark() {
		return srTypeMark;
	}

	public void setSrTypeMark(String srTypeMark) {
		this.srTypeMark = srTypeMark;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}