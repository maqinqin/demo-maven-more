package com.git.cloud.resmgt.compute.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwProjectPo extends BaseBO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String projectId;
	private String datacenterId;
	private String vmMsId;
	private String projectName;
	private String isActive;
	private String remark;
	
	
	private String openstackId ;
	private String openstackIp ;
	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getDatacenterId() {
		return datacenterId;
	}


	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}


	public String getVmMsId() {
		return vmMsId;
	}


	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
