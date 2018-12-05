package com.git.cloud.rest.client;

import java.util.HashMap;
import java.util.Map;

public class RestModel {
	private String targetURL; // 请求URL
	private String requestMethod; // 请求方式
	private String requestJosnData; // 请求参数JSON串
	private Map<String, String> header; // 请求的Header
	private String sych;
	
	
	public String getSych() {
		return sych;
	}
	public void setSych(String sych) {
		this.sych = sych;
	}
	public String getTargetURL() {
		return targetURL;
	}
	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getRequestJosnData() {
		return requestJosnData;
	}
	public void setRequestJosnData(String requestJosnData) {
		this.requestJosnData = requestJosnData;
	}
	public Map<String, String> getHeader() {
		return header;
	}
	public void setHeader(Map<String, String> header) {
		this.header = header;
	}
	public void appendHeader(String key, String value) {
		if(this.header == null) {
			this.setHeader(new HashMap<String, String> ());
		}
		this.header.put(key, value);
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\ntargetURL:" + this.targetURL);
		sb.append("\nrequestMethod:" + this.requestMethod);
		sb.append("\nheader:" + this.header.toString());
		sb.append("\nrequestJosnData:" + this.requestJosnData);
		return sb.toString();
	}
}
