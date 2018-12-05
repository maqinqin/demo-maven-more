package com.git.cloud.handler.automation.pv.impl;

import com.git.cloud.handler.automation.pv.OperatePowerVcServerHandler;
import com.git.support.common.PVOperation;

/** 
 * 删除计算实例
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public class DeleteServerHandler extends OperatePowerVcServerHandler {

	@Override
	protected String getOperateCode() {
		return PVOperation.DELETE_VM;
	}

	@Override
	protected int getResultCode() {
		return 204;
	}
}
