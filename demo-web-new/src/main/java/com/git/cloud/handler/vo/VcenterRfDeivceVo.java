package com.git.cloud.handler.vo;

import java.util.List;
public class VcenterRfDeivceVo {
	
	private String vcenterUrl;//虚拟机所在vcenterUrl
	private String vcenterName;//虚拟机所在vcenter Name
	private String vcenterPwd;//虚拟机所在vcenter pwd
	private String  datacenterId;//数据中心id
	private List <String> vmNames;//虚拟机集合
	
	public String getVcenterUrl() {
		return vcenterUrl;
	}
	public void setVcenterUrl(String vcenterUrl) {
		this.vcenterUrl = vcenterUrl;
	}
	public String getVcenterName() {
		return vcenterName;
	}
	public void setVcenterName(String vcenterName) {
		this.vcenterName = vcenterName;
	}
	public String getVcenterPwd() {
		return vcenterPwd;
	}
	public void setVcenterPwd(String vcenterPwd) {
		this.vcenterPwd = vcenterPwd;
	}
	public List<String> getVmNames() {
		return vmNames;
	}
	public void setVmNames(List<String> vmNames) {
		this.vmNames = vmNames;
	}
	public String getDatacenterId() {
		return datacenterId;
	}
	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}
}
