package com.git.cloud.handler.automation.os;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.support.common.MesgRetCode;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Maps;

/** 
 * Openstack公共服务策略
 * @author SunHailong 
 * @version 版本号 2017-3-24
 */
public class OpenstackCommonHandler extends RemoteAbstractAutomationHandler {

	private static Logger logger = LoggerFactory.getLogger(OpenstackCommonHandler.class);
	private final String CLASS_NAME = "CLASS_NAME";
	
	@Override
	public String execute(HashMap<String, Object> contenxtParams)
			throws Exception {
		Map<String, Object> rtn_map = Maps.newHashMap();
		if(contenxtParams!=null){
			// 流程实例Id
			String flowInstId = (String) contenxtParams.get(SeConstants.FLOW_INST_ID);
			// 流程节点Id
			String nodeId = (String) contenxtParams.get(SeConstants.NODE_ID);
			// 服务请求Id
			String srvReqId = (String) contenxtParams.get(SeConstants.SRV_REQ_ID);
			// 资源请求ID
			String rrinfoId = (String) contenxtParams.get(SeConstants.RRINFO_ID);
			// 执行类
			String className = (String) contenxtParams.get(CLASS_NAME);
			String commonInfo = ",服务请求ID:" + srvReqId + ",资源请求ID:" + rrinfoId + ",流程实例ID:" + flowInstId + ",流程节点ID:" + nodeId;
			logger.info("[OpenstackCommonHandler]执行类["+className+"]过程开始" + commonInfo);
			try {
				Map<String, Map<String, String>> handleParams = this.getHandleParams(flowInstId);
				contenxtParams.putAll(handleParams);
				Map<String, Object> extHandleParams = getExtHandleParams(contenxtParams);
				if (extHandleParams != null) {
					contenxtParams.putAll(extHandleParams);
				}
				Class<?> clazz = Class.forName("com.git.cloud.handler.automation.os.impl." + className);
				Method method = clazz.getMethod("executeOperate", new Class[] {contenxtParams.getClass()});
				method.invoke(clazz.newInstance(), new Object[] {contenxtParams});
				rtn_map.put("checkCode", MesgRetCode.SUCCESS);
				logger.info("[OpenstackCommonHandler]执行类["+className+"]正常结束" + commonInfo);
			} catch(Exception e) {
				rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
				rtn_map.put("exeMsg", e.getCause().getMessage());
				logger.error("[OpenstackCommonHandler]执行类["+className+"]异常结束" + e.getCause().getMessage() + commonInfo, e);
				e.printStackTrace();
			} finally {
				if (contenxtParams != null)
					contenxtParams.clear();
			}
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null!");	
		}

		return JSON.toJSONString(rtn_map);
	}

	/**
	 * 此方法为普通方法，需要被子类重写，但不能定义为抽象方法
	 * @param reqMap
	 * @throws Exception
	 */
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		throw new Exception("请重写executeOperate方法.");
	};
	
	public void saveParam(HashMap<String, Object> contenxtParams) throws Exception {
		// 流程实例Id
		String flowInstId = (String) contenxtParams.get(SeConstants.FLOW_INST_ID);
		// 流程节点Id
		String nodeId = (String) contenxtParams.get(SeConstants.NODE_ID);
		saveParamInsts(flowInstId, nodeId);
	}
	
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {
		return null;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas, IDataObject responseDataObject) throws Exception {
	}
}
