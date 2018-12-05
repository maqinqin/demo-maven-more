package com.git.cloud.iaas.hillstone.model;

public class AddrBookModel {
	private String addrbookName;
	private String ipMin;
	private String ipMax;
	
	public String getAddrbookName() {
		return addrbookName;
	}
	public void setAddrbookName(String addrbookName) {
		this.addrbookName = addrbookName;
	}
	public String getIpMin() {
		return ipMin;
	}
	public void setIpMin(String ipMin) {
		this.ipMin = ipMin;
	}
	public String getIpMax() {
		return ipMax;
	}
	public void setIpMax(String ipMax) {
		this.ipMax = ipMax;
	}
}
