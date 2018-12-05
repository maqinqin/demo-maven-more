package com.git.cloud.cloudservice.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class QueryDataVo extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	private String keyParam;
	private String valueParam;
	
	public String getKeyParam() {
		return keyParam;
	}
	public void setKeyParam(String keyParam) {
		this.keyParam = keyParam;
	}
	public String getValueParam() {
		return valueParam;
	}
	public void setValueParam(String valueParam) {
		this.valueParam = valueParam;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
