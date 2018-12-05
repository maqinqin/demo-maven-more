package com.git.cloud.resmgt.compute.model.vo;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class OpenstackSynckVo extends BaseBO implements Serializable {
	private String datacenterId;
	private String datacenterName;
	private String vmSmId;
	private String ServerName;
	private String manageIp; //openstackIp
	private String domainName;
	private String platForm;
	private String resPoolId;
	

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getPlatForm() {
		return platForm;
	}

	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	public String getDatacenterName() {
		return datacenterName;
	}

	public void setDatacenterName(String datacenterName) {
		this.datacenterName = datacenterName;
	}

	public String getVmSmId() {
		return vmSmId;
	}

	public void setVmSmId(String vmSmId) {
		this.vmSmId = vmSmId;
	}

	public String getServerName() {
		return ServerName;
	}

	public void setServerName(String serverName) {
		ServerName = serverName;
	}

	public String getManageIp() {
		return manageIp;
	}

	public void setManageIp(String manageIp) {
		this.manageIp = manageIp;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
