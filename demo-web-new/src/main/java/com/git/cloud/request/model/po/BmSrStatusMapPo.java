package com.git.cloud.request.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class BmSrStatusMapPo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String srTypeMark;
	private String nodeName;
	private String srStatusCode;

	public BmSrStatusMapPo() {
	}

	public BmSrStatusMapPo(String id) {
		this.id = id;
	}

	public BmSrStatusMapPo(String id, String srTypeMark, String nodeName, String srStatusCode) {
		this.id = id;
		this.srTypeMark = srTypeMark;
		this.nodeName = nodeName;
		this.srStatusCode = srStatusCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSrTypeMark() {
		return srTypeMark;
	}

	public void setSrTypeMark(String srTypeMark) {
		this.srTypeMark = srTypeMark;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getSrStatusCode() {
		return srStatusCode;
	}

	public void setSrStatusCode(String srStatusCode) {
		this.srStatusCode = srStatusCode;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}