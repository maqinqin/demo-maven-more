package com.git.cloud.resmgt.common.model.po;

public class ScanResult {
	/*设备ID*/
	private String deviceId;
	/*设备名称*/
	private String deviceName;
	/* 是否成功完成扫描和保存 */
	private boolean isSuccess;
	/* 设备状态 */
	private String deviceStatus;
	/*扫描、保存过程中的错误信息*/
	private String errorMsg;
	
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
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
