package com.git.cloud.handler.automation;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.support.common.MesgRetCode;

/** 
 * 延迟服务策略
 * @author SunHailong 
 * @version 版本号 2017-4-6
 */
public class DelayHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String TIME_OUT = "TIME_OUT";
	
	public String execute(HashMap<String, Object> contenxtParams) {
		Integer timeOut = Integer.valueOf((String) contenxtParams.get(TIME_OUT));
		try {
			Thread.sleep(timeOut * 1000); // 单位为秒
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return MesgRetCode.SUCCESS;
	}
}
