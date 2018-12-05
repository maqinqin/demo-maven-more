package com.git.cloud.reports.model.po;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;

@SuppressWarnings("rawtypes")
public class CreateReportParamPo extends BaseBO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1817900304617690641L;
	//报表类
	private String id;//ID
	private String reportNameKey;//报表名称KEY
	private String reportNameValue;//报表名称VALUE
	private String reportDecKey;//报表描述KEY
	private String reportDecValue;//报表名称VALUE
	private List<ConProParamPo> conProList ;//报表条件实体类 （包含条件和条件的从属条件）
	private List<SqlParamPo> sqlList ;//SQL KEY VALUE
	private String jasperPath;//JASPER地址
	
	
	public List<ConProParamPo> getConProList() {
		return conProList;
	}
	public void setConProList(List<ConProParamPo> conProList) {
		this.conProList = conProList;
	}
	public List<SqlParamPo> getSqlList() {
		return sqlList;
	}
	public void setSqlList(List<SqlParamPo> sqlList) {
		this.sqlList = sqlList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportNameKey() {
		return reportNameKey;
	}
	public void setReportNameKey(String reportNameKey) {
		this.reportNameKey = reportNameKey;
	}
	public String getReportNameValue() {
		return reportNameValue;
	}
	public void setReportNameValue(String reportNameValue) {
		this.reportNameValue = reportNameValue;
	}
	public String getReportDecKey() {
		return reportDecKey;
	}
	public void setReportDecKey(String reportDecKey) {
		this.reportDecKey = reportDecKey;
	}
	public String getReportDecValue() {
		return reportDecValue;
	}
	public void setReportDecValue(String reportDecValue) {
		this.reportDecValue = reportDecValue;
	}
	public String getJasperPath() {
		return jasperPath;
	}
	public void setJasperPath(String jasperPath) {
		this.jasperPath = jasperPath;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
