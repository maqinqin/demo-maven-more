package com.git.cloud.handler.automation.pv.impl;

import com.git.cloud.handler.automation.pv.GetPowerVcServerHandler;

/** 
 * 验证计算实例状态，是否已调整规格
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public class CheckServerResizeStatusHandler extends GetPowerVcServerHandler {

	protected void commonInitParam() {
	}
	
	protected boolean isOperateVmSuccess(String status) {
		return status.equals("VERIFY_RESIZE");
	}
}

