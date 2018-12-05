/**
 * @Title:RmNwIpAddressVo.java
 * @Package:com.git.cloud.resmgt.network.model.vo
 * @Description:TODO
 * @author LINZI
 * @date 2015-4-16 下午04:38:54
 * @version V1.0
 */
package com.git.cloud.resmgt.network.model.vo;

import com.git.cloud.resmgt.network.model.po.RmNwIpAddressPo;

/**
 * @ClassName:RmNwIpAddressVo
 * @Description:TODO
 * @author LINZI
 * @date 2015-4-16 下午04:38:54
 *
 *
 */
public class RmNwIpAddressVo extends RmNwIpAddressPo {

	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 2375502501940293158L;
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getAllocedStatusName() {
		return allocedStatusName;
	}
	public void setAllocedStatusName(String allocedStatusName) {
		this.allocedStatusName = allocedStatusName;
	}
	private String deviceName;
	private String updateUser;
	private String allocedStatusName;
	private String allocedDateString;
	private String runningState;
	
	
	public String getRunningState() {
		return runningState;
	}
	public void setRunningState(String runningState) {
		this.runningState = runningState;
	}
	public String getAllocedDateString() {
		return allocedDateString;
	}
	public void setAllocedDateString(String allocedDateString) {
		this.allocedDateString = allocedDateString;
	}
}
