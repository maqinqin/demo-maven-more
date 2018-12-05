/**
 * Copyright (c) 2014, Git Co., Ltd. All rights reserved.
 *
 * 审核人：
 */
package com.git.cloud.handler.automation;

import java.util.HashMap;

/**
 * 云平台自动化操作接口
 * <p>
 * 
 * @author mengzx
 * @version 1.0 2013-4-25
 * @see
 */
public interface IAutomationHandler {

	/**
	 * 自动化操作服务,发送操作指令给消息队列,由底层适配层对应的适配器执行具体的逻辑
	 * 
	 * @param contenxt
	 *            上下文参数
	 * @return
	 */
	public String execute(HashMap<String, Object> contenxtParmas) throws Exception;
}
