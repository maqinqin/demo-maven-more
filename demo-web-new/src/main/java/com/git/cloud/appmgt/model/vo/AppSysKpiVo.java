/**
 * @Title:AppSysKpiVo.java
 * @Package:com.git.cloud.appmgt.model.vo
 * @Description:TODO
 * @author sunhailong
 * @date 2014-11-12 下午3:01:43
 * @version V1.0
 */
package com.git.cloud.appmgt.model.vo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 关于应用系统的指标
 * @ClassName:AppSysKpiVo
 * @Description:TODO
 * @author sunhailong
 * @date 2014-11-12 下午3:01:43
 */
public class AppSysKpiVo extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	private String appId; // 应用系统Id
	private String appCName; // 应用系统中文名
	private String appEName; // 应用系统英文名
	private String deviceNums; // 应用系统虚机数量
	private String requestNums; // 应用系统月申请数
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppCName() {
		return appCName;
	}
	public void setAppCName(String appCName) {
		this.appCName = appCName;
	}
	public String getAppEName() {
		return appEName;
	}
	public void setAppEName(String appEName) {
		this.appEName = appEName;
	}
	public String getDeviceNums() {
		return deviceNums;
	}
	public void setDeviceNums(String deviceNums) {
		this.deviceNums = deviceNums == null ? "0" : deviceNums;
	}
	public String getRequestNums() {
		return requestNums;
	}
	public void setRequestNums(String requestNums) {
		this.requestNums = requestNums == null ? "0" : requestNums;
	}
	
	@Override
	public String getBizId() {
		return null;
	}
}
