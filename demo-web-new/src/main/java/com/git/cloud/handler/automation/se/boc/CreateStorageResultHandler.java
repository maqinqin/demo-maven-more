package com.git.cloud.handler.automation.se.boc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.support.common.MesgRetCode;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Maps;

public class CreateStorageResultHandler extends RemoteAbstractAutomationHandler {

	private static Logger logger = LoggerFactory.getLogger(CreateStorageResultHandler.class);
	private String errorMsg = new String();
	
	@Override
	public String execute(HashMap<String, Object> contenxtParams) throws Exception {
		Map<String, Object> rtn_map = Maps.newHashMap();
		// 流程实例Id
		String flowInstId = (String) contenxtParams.get(SeConstants.FLOW_INST_ID);
		// 流程几点Id
		String nodeId = (String) contenxtParams.get(SeConstants.NODE_ID);
		// 服务请求Id
		String srvReqId = (String) contenxtParams.get(SeConstants.SRV_REQ_ID);
		// 资源请求ID
		String rrinfoId = (String) contenxtParams.get(SeConstants.RRINFO_ID);
		String commonInfo = ",服务请求ID:" + srvReqId + ",资源请求ID:" + rrinfoId + ",流程实例ID:" + flowInstId + ",流程节点ID:" + nodeId;
		logger.info("[CreateStorageResultHandler]创建STORAGE_RESULT开始" + commonInfo);
		try {
			Map<String, Map<String, String>> handleParams = this.getHandleParams(flowInstId);
			contenxtParams.putAll(handleParams);
			Map<String, Object> extHandleParams = getExtHandleParams(contenxtParams);
			if (extHandleParams != null) {
				contenxtParams.putAll(extHandleParams);
			}
			List<String> deviceIdList = getDeviceIdList("","",rrinfoId,"");
			String storageResult = this.getStorageResult(contenxtParams, deviceIdList.get(0));
			//storageResult
			for(int i=0 ; i<deviceIdList.size() ; i++) {
				setHandleResultParam(deviceIdList.get(i), "STORAGE_RESULT", storageResult);
			}
			saveParamInsts(flowInstId, nodeId);
			rtn_map.put("checkCode", MesgRetCode.SUCCESS);
			rtn_map.put("exeMsg", "success");
			logger.info("[CreateStorageResultHandler]创建STORAGE_RESULT正常结束" + commonInfo);
		} catch(Exception e) {
			rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
			rtn_map.put("exeMsg", e.getMessage());
			logger.error("[CreateStorageResultHandler]创建STORAGE_RESULT异常结束," + e + commonInfo);
		}
		return JSON.toJSONString(rtn_map);
	}

	@SuppressWarnings("unchecked")
	private String getStorageResult(HashMap<String, Object> contenxtParams, String deviceId) throws Exception {
		Map<String, Object> deviceParams = (Map<String, Object>) contenxtParams.get(deviceId);
		String storageAssignInfo = (String) deviceParams.get("STORAGE_ASSING_INFO");
		if(storageAssignInfo == null) {
			logger.error("未获取到参数[STORAGE_ASSING_INFO]");
			throw new Exception("未获取到参数[STORAGE_ASSING_INFO]");
		}
		logger.info("[CreateStorageResultHandler]获取到STORAGE_ASSING_INFO值为：" + storageAssignInfo);
		JSONObject json = JSONObject.parseObject(storageAssignInfo);
		HashMap<String, String> storageAssignMap = null;
		try {
			JSONArray diskJsonArr = json.getJSONArray("hdisks");
			storageAssignMap = this.thansJSONArrayToMap(diskJsonArr);
		} catch(Exception e) {
			logger.error("异常exception",e);
		}
		if(storageAssignMap == null) {
			logger.error("获取到的参数[STORAGE_ASSING_INFO]格式有误");
			throw new Exception("获取到的参数[STORAGE_ASSING_INFO]格式有误");
		}
		String storageSn1 = (String) deviceParams.get("STORAGE_SN1");
		String storageSn2 = (String) deviceParams.get("STORAGE_SN2");
		String assignLunInfoForStorage1 = (String) deviceParams.get(storageSn1 + "_ASSIGN_LUN_INFO");
		String assignLunInfoForStorage2 = (String) deviceParams.get(storageSn2 + "_ASSIGN_LUN_INFO");
		String assignLunInfo = assignLunInfoForStorage1 + "," + assignLunInfoForStorage2;
		JSONObject resultJson = new JSONObject();
		String[] assignLunArr = assignLunInfo.split(",");
		for(int i=0 ; i<assignLunArr.length ; i++) {
			if(assignLunArr[i].equals("")) {
				continue;
			}
			String[] subArr = assignLunArr[i].split(":");
			// 格式为 storageName:lunPath:lunType
			String key = subArr[0] + ":" + subArr[1];
			String diskKey = subArr[2]; // lunType(SYSDISK,DATADISK,ARCHDISK,SYSDISKM,DATADISKM,ARCHDISKM)
			if(storageAssignMap.get(key) == null) {
				throw new Exception("[CreateStorageResultHandler]获取到STORAGE_ASSING_INFO值中缺少数据，存储：" + subArr[0] + "；LunPath：" + subArr[1]);
			}
			String diskValue = resultJson.get(diskKey) == null ? "" : (String) resultJson.get(diskKey);
			resultJson.put(diskKey, diskValue + ("".equals(diskValue) ? "" : ",") + storageAssignMap.get(key));
		}
		return resultJson.toString();
	}

	private HashMap<String, String> thansJSONArrayToMap(JSONArray diskJsonArr) {
		HashMap<String, String> map = new HashMap<String, String> ();
		JSONObject diskJson;
		for(int i=0 ; i<diskJsonArr.size() ; i++) {
			diskJson = diskJsonArr.getJSONObject(i);
			// {"storage":"FAS8060A-2E","lun":"/vol/POC/lun19","hdisk_number":"/dev/rhdisk117"}
			map.put(diskJson.getString("storage") + ":" + diskJson.getString("lun"), diskJson.getString("hdisk_number"));
		}
		return map;
	}
	
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
