package com.git.cloud.handler.automation.os.impl;

import com.git.cloud.handler.automation.os.OperateServerHandler;
import com.git.support.common.OSOperation_bak;

/** 
 * 计算实例关机
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public class StopServerHandler extends OperateServerHandler {

	@Override
	protected String getOperateCode() {
		return OSOperation_bak.STOP_VM;
	}

	@Override
	protected int getResultCode() {
		return 202;
	}
}
