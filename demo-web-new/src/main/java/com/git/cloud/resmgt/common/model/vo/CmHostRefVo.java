package com.git.cloud.resmgt.common.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class CmHostRefVo extends BaseBO implements java.io.Serializable{

	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 1158937840203072268L;

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	private String deviceId;
	private String deviceName;
	private String vmTypeCode;
	private String clusterId;
	private String cdpId;
	private String managerServer;
	private String isInvc;
	

	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getVmTypeCode() {
		return vmTypeCode;
	}
	public void setVmTypeCode(String vmTypeCode) {
		this.vmTypeCode = vmTypeCode;
	}
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	public String getCdpId() {
		return cdpId;
	}
	public void setCdpId(String cdpId) {
		this.cdpId = cdpId;
	}
	public String getManagerServer() {
		return managerServer;
	}
	public void setManagerServer(String managerServer) {
		this.managerServer = managerServer;
	}
	public String getIsInvc() {
		return isInvc;
	}
	public void setIsInvc(String isInvc) {
		this.isInvc = isInvc;
	}
}
