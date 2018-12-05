package com.git.cloud.handler.automation.oa;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.git.support.constants.PubConstants;
import com.git.support.constants.SAConstants;
import com.git.support.util.PwdUtil;
import com.google.common.collect.Maps;

public class OperationAutomationInitParamHandler extends LocalAbstractAutomationHandler {
	
	private static Logger logger = LoggerFactory.getLogger(OperationAutomationInitParamHandler.class);

	@Override
	public String service(Map<String, Object> contenxtParams) throws Exception {
		// 流程实例Id
		String flowInstId = (String) contenxtParams.get(FLOW_INST_ID);

		// 服务请求Id
		String srvReqId = (String) contenxtParams.get(SRV_REQ_ID);

		// 资源请求ID
		String rrinfoId = (String) contenxtParams.get(RRINFO_ID);
		
		String commonInfo = ",服务请求ID:" + srvReqId + ",资源请求ID:" + rrinfoId + ",流程实例ID:" + flowInstId;
		
		logger.info("[OperationAutomationInitParamHandler]构建所需参数实例化开始" + commonInfo);
		
		//参数清理
		getBizParamInstService().deleteParamInstsOfFlow(flowInstId);
		
		// 获取设备ID
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		
		int len = deviceIdList == null ? 0 : deviceIdList.size();
		if(len == 0) {
			logger.error("[OperationAutomationInitParamHandler]获取设备信息失败" + commonInfo);
			throw new Exception("[OperationAutomationInitParamHandler]获取设备信息失败" + commonInfo);
		}
		Map<String, String> srvAttrInfoMap;
		for(int i=0 ; i<len ; i++) {
			// 获取申请填写的参数
			srvAttrInfoMap = getAutomationService().getServiceAttrForDevice(rrinfoId, deviceIdList.get(i));
			// 初始化服务自动化参数信息
			try {
				this.buildOperationAutomationParam(deviceIdList.get(i), contenxtParams, srvAttrInfoMap, commonInfo);
			} catch (Exception e) {
				logger.error(e + commonInfo);
				return PubConstants.EXEC_RESULT_FAIL;
			}
		}
		
		logger.info("[OperationAutomationInitParamHandler]构建所需参数实例化结束" + commonInfo);
		
		return PubConstants.EXEC_RESULT_SUCC;
	}
	
	private void buildOperationAutomationParam(String deviceId, Map<String, Object> contenxtParams, Map<String, String> srvAttrInfoMap, String commonInfo) throws Exception {
		Map<String, String> oaParamMap = Maps.newHashMap();
		contenxtParams.put(deviceId, oaParamMap);
		this.setHandleResultParam(deviceId, oaParamMap);
		// 添加所有云服务属性中定义的参数
		for (String attrName : srvAttrInfoMap.keySet()) {
			oaParamMap.put(attrName, srvAttrInfoMap.get(attrName));
		}
		// 初始化特殊参数
		String deviceName = "";
		String lparName = "";
		String lparId = "";
		String hostName = "";
		try {
			CmVmPo vm = getAutomationService().getVm(deviceId);
			deviceName = vm.getDeviceName();
			lparName = vm.getLparName();
			lparId = vm.getLparId();
			hostName = vm.getHostName();
			if("SVR_ZZ_P780_4CD7".equals(hostName)) {
				oaParamMap.put("VIOS1_IP", "22.188.116.130");
				oaParamMap.put("VIOS2_IP", "22.188.116.131");
			} else {
				oaParamMap.put("VIOS1_IP", "22.188.116.132");
				oaParamMap.put("VIOS2_IP", "22.188.116.133");
			}
		} catch (Exception e) {
			logger.error("[OperationAutomationInitParamHandler]获取设备信息失败,deviceID:" + deviceId);
		}
		oaParamMap.put(SAConstants.SERVER_NAME, deviceName);
		oaParamMap.put("lparname", lparName);
		oaParamMap.put("lparid", lparId);
		RmGeneralServerVo server = null;
		try {
			server = getAutomationService().findDeviceServerInfo(deviceId);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		if(server == null) {
			logger.error("[OperationAutomationInitParamHandler]获取服务器信息失败,deviceID:" + deviceId);
			throw new Exception("[OperationAutomationInitParamHandler]获取服务器信息失败,deviceID:" + deviceId);
		}
		oaParamMap.put(SAConstants.SERVER_IP, server.getServerIp());
		String userName = oaParamMap.get(SAConstants.USER_NAME);
		String pwd = oaParamMap.get(SAConstants.USER_PASSWORD);
		if((userName == null || "".equals(userName)) && (pwd == null || "".equals(pwd))) {
			// 当云服务中的用户名和密码都为空，则用设备原用户密码信息
			oaParamMap.put(SAConstants.USER_NAME, server.getUserName());
			oaParamMap.put(SAConstants.USER_PASSWORD, PwdUtil.decryption(server.getPassword()));
		}
	}
}
