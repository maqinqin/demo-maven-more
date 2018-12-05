package com.git.cloud.rest.client;

import java.util.List;
import java.util.Map;

public class RestResult {
	private int responseCode;
	private String message;
	private String jsonData;
	private Map<String, List<String>> headerMap;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public Map<String, List<String>> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, List<String>> headerMap) {
		this.headerMap = headerMap;
	}

	@Override
	public String toString() {
		return "RestResult [responseCode=" + responseCode + ", message=" + message + ", jsonData=" + jsonData
				+ ", headerMap=" + headerMap + "]";
	}
	
}
