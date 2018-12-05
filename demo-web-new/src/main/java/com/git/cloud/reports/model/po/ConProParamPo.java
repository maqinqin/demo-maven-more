package com.git.cloud.reports.model.po;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;

public class ConProParamPo extends BaseBO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2156202887018121564L;
	//报表条件类
	private String id;//ID
	private String conKey;//条件KEY
	private String conValue;//条件VALUE
	private String conType;//条件类型
	private String conType_dec;//类型描述
	private String isSqlParam;//是否sql所需
	
	private List<PropertyParamPo> proList;//从属条件实体类（包含从属条件的key和value）
	private String reportId;//表示这个条件所属于哪个报表
	
	
	
	public String getConType_dec() {
		return conType_dec;
	}
	public void setConType_dec(String conType_dec) {
		this.conType_dec = conType_dec;
	}
	public String getIsSqlParam() {
		return isSqlParam;
	}
	public void setIsSqlParam(String isSqlParam) {
		this.isSqlParam = isSqlParam;
	}
	public String getConType() {
		return conType;
	}
	public void setConType(String conType) {
		this.conType = conType;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getConKey() {
		return conKey;
	}
	public void setConKey(String conKey) {
		this.conKey = conKey;
	}
	public String getConValue() {
		return conValue;
	}
	public void setConValue(String conValue) {
		this.conValue = conValue;
	}
	public List<PropertyParamPo> getProList() {
		return proList;
	}
	public void setProList(List<PropertyParamPo> proList) {
		this.proList = proList;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
