package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class RmHostVmInfoPo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 7876346929079832369L;
	
	private String id;
	private String deviceName;
	private String hostIp;
	private String vmHostId;
	private String vmHostIp;
	private String runningState;
	private int isHost;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getVmHostId() {
		return vmHostId;
	}

	public void setVmHostId(String vmHostId) {
		this.vmHostId = vmHostId;
	}

	public String getVmHostIp() {
		return vmHostIp;
	}

	public void setVmHostIp(String vmHostIp) {
		this.vmHostIp = vmHostIp;
	}

	public String getRunningState() {
		return runningState;
	}

	public void setRunningState(String runningState) {
		this.runningState = runningState;
	}

	public int getIsHost() {
		return isHost;
	}

	public void setIsHost(int isHost) {
		this.isHost = isHost;
	}

	@Override
	public String getBizId() {
		return null;
	}

}
