package com.git.cloud.resmgt.openstack.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class FloatingIpPo extends BaseBO implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String vmMsId;
	private String projectId;
	private String networkId;
	private String floatingIp;
	private String deviceId;
	private String isActive;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVmMsId() {
		return vmMsId;
	}
	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	public String getFloatingIp() {
		return floatingIp;
	}
	public void setFloatingIp(String floatingIp) {
		this.floatingIp = floatingIp;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
