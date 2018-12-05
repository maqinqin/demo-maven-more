package com.git.cloud.handler.automation.pv.impl;

import com.git.cloud.handler.automation.pv.GetPowerVcServerHandler;

/** 
 * 验证计算实例状态，是否删除成功
 * @author SunHailong 
 * @version 版本号 2017-4-2
 */
public class CheckServerDeleteStatusHandler extends GetPowerVcServerHandler {
	
	protected void commonInitParam() {
		this.isCheckDelete = true;
	}
	
	protected boolean isOperateVmSuccess(String status) {
		return status.equals("");
	}
}
