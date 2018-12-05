package com.git.cloud.handler.automation.os.impl;

import com.git.cloud.handler.automation.os.GetServerHandler;

/** 
 * 验证计算实例状态，是否创建成功
 * @author SunHailong 
 * @version 版本号 2017-4-2
 */
public class CheckServerCreateStatusHandler extends GetServerHandler {

	protected void commonInitParam() {
		this.updateVmHost = true;
		this.relateVmSysDisk = true;
		this.executeTimes = 50;
	}
	
	protected boolean isOperateVmSuccess(String status) {
		return status.equals("ACTIVE");
	}
}

