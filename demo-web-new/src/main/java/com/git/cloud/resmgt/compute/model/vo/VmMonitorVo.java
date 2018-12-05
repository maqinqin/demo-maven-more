package com.git.cloud.resmgt.compute.model.vo;

import java.util.Date;

public class VmMonitorVo {
	
	private String name;
	
	private String value;
	
	private String dateStr;
	
	private Long date;
	
	

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
	

}
