package com.git.cloud.handler.automation.os.impl;

import com.git.cloud.handler.automation.os.GetServerHandler;

/** 
 * 验证计算实例状态，是否为活动状态
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public class CheckServerActiveStatusHandler extends GetServerHandler {

	protected void commonInitParam() {
		this.executeTimes = 1; // 只查询一次
	}
	
	protected boolean isOperateVmSuccess(String status) {
		return status.equals("ACTIVE");
	}
}

