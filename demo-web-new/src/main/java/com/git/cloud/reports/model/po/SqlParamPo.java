package com.git.cloud.reports.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class SqlParamPo extends BaseBO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8581296369465496282L;
	//报表SQL实体类
	private String id;//ID
	private String sqlKey;//sqlKEY
	private String sqlValue;//sqlVALUE
	private String reportId;//表示这个sql从属于哪个报表
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSqlKey() {
		return sqlKey;
	}
	public void setSqlKey(String sqlKey) {
		this.sqlKey = sqlKey;
	}
	public String getSqlValue() {
		return sqlValue;
	}
	public void setSqlValue(String sqlValue) {
		this.sqlValue = sqlValue;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
