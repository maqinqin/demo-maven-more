package com.git.cloud.handler.automation.os.impl;

import com.git.cloud.handler.automation.os.OperateServerHandler;
import com.git.support.common.OSOperation;
import com.git.support.common.OSOperation_bak;

/** 
 * 计算实例修改规格
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public class ConfirmResizeServerHandler extends OperateServerHandler {

	@Override
	protected String getOperateCode() {
		return OSOperation_bak.CONFIRM_RESIZE;
	}

	@Override
	protected int getResultCode() {
		return 204;
	}
}
