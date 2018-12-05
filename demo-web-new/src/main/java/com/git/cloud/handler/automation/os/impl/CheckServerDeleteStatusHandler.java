package com.git.cloud.handler.automation.os.impl;

import com.git.cloud.handler.automation.os.GetServerHandler;

/** 
 * 验证计算实例状态，是否删除成功
 * @author SunHailong 
 * @version 版本号 2017-4-2
 */
public class CheckServerDeleteStatusHandler extends GetServerHandler {
	
	protected void commonInitParam() {
		this.isCheckDelete = true;
	}
	
	protected boolean isOperateVmSuccess(String status) {
		return status.equals("");
	}
}
