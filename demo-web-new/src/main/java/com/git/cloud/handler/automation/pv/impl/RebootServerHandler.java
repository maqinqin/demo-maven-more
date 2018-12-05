package com.git.cloud.handler.automation.pv.impl;

import com.git.cloud.handler.automation.pv.OperatePowerVcServerHandler;
import com.git.support.common.PVOperation;

/** 
 * 计算实例重启
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public class RebootServerHandler extends OperatePowerVcServerHandler {

	@Override
	protected String getOperateCode() {
		return PVOperation.REBOOT_VM;
	}

	@Override
	protected int getResultCode() {
		return 202;
	}
}
