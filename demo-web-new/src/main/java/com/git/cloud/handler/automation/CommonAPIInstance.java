package com.git.cloud.handler.automation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.common.Utils;
import com.git.support.common.MesgFlds;
import com.git.support.sdo.inf.IDataObject;


/**
 * 通用调用API的instance
 * @author zhuzhaoyong.co
 * 修改：
 * 		2015-11-06 增加操作bean参数支持，如果存在operationBean则operationCode无效，需要在服务策略中增加参数：OPERATION_BEAN
 *
 */
public  class CommonAPIInstance extends BaseInstance {
	
	private static Logger log = LoggerFactory.getLogger(CommonAPIHandler.class);
	
	final static String OPERATION_CODE = "OPERATION_CODE";
	final static String PARAMETER_KEY = "PARAMETER_KEY";
	final static String RESOURCE_CLASS = "RESOURCE_CLASS";
	final static String RESOURCE_TYPE = "RESOURCE_TYPE";
	int timeOutMs = 0;

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contextParams)
			throws Exception {
		if (contextParams.get(TIME_OUT_STR) == null || StringUtils.isEmpty(contextParams.get(TIME_OUT_STR).toString())) {
			log.info("TIME_OUT is null. Use default timeout:" + super.getTimeOut());
		} else {
			timeOutMs = Integer.parseInt(contextParams.get(TIME_OUT_STR).toString()) * 1000;
			log.info("Command TIME_OUT is :" + timeOutMs);
			contextParams.put(TIME_OUT_STR, timeOutMs);
		}
		int operation = 0;
		String operationBean = getContextStringPara(contextParams, MesgFlds.OPERATION_BEAN);
		if (operationBean == null || operationBean.trim().equals("")) {
			operation = getContextLongPara(contextParams, OPERATION_CODE).intValue();
		}
		String resourceClass = getContextStringPara(contextParams, RESOURCE_CLASS);
		String resourceType = getContextStringPara(contextParams, RESOURCE_TYPE);
		String paraKeys = getContextStringPara(contextParams, PARAMETER_KEY);
		List<String> keys = new ArrayList<String>();
		keys = Utils.stringSplitBySymbol2List(paraKeys, ",");
		String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
		// 增加数据中心路由标识
		String queueIdentify = this.getQueueIdent(rrinfoId);
		IDataObject requestObject = LinkDataObjectUtil.buildRequestData(keys, contextParams, 
				resourceClass, resourceType, operation, queueIdentify, operationBean);
		return requestObject;
	}

	@Override
	protected int getTimeOut() {
		if (timeOutMs == 0) {
			return super.getTimeOut();
		} else {
			return timeOutMs;
		}
	}
	
	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) {
		// TODO Auto-generated method stub
		
	}
	
}
