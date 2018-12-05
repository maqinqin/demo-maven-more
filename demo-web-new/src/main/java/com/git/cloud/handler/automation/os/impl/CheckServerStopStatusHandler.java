package com.git.cloud.handler.automation.os.impl;

import com.git.cloud.handler.automation.os.GetServerHandler;

/** 
 * 验证计算实例状态，是否关机成功
 * @author SunHailong 
 * @version 版本号 2017-4-2
 */
public class CheckServerStopStatusHandler extends GetServerHandler {
	
	protected void commonInitParam() {
	}
	
	protected boolean isOperateVmSuccess(String status) {
		return status.equals("SHUTOFF") || status.equals("STOPPED");
	}
}
