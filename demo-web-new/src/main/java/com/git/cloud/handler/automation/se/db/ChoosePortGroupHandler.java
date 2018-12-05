package com.git.cloud.handler.automation.se.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.po.StorageFCPortGroupPo;
import com.git.cloud.handler.automation.se.po.StorageMgrPo;
import com.git.cloud.handler.automation.se.po.StoragePo;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.handler.service.StorageService;
import com.git.cloud.resmgt.common.dao.impl.CmPasswordDAO;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SEOpration;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Maps;

public class ChoosePortGroupHandler  extends RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(ChoosePortGroupHandler.class);

	private String errorMsg = new String();
	protected StringBuffer result_sb = new StringBuffer();

	protected static final String STORAGEBASE = "STORAGE_SN";

	protected static final String STORAGEONE = STORAGEBASE + 1;

	protected static final String STORAGETWO = STORAGEBASE + 2;
	private String deviceId ;
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	protected static CmPasswordDAO adminPwDao = (CmPasswordDAO) WebApplicationManager.getBean("cmPasswordDAO");
	private StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");

	@SuppressWarnings("unchecked")
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {
		AutomationService automationService = (AutomationService) WebApplicationManager.getBean("automationServiceImpl");

		IDataObject requestData = DataObject.CreateDataObject();

		HeaderDO header = HeaderDO.CreateHeaderDO();
		String rrinfoId = (String) contenxtParmas.get(RRINFO_ID);
		try {
			// 增加数据中心路由标识
			String queueIdentify = this.getQueueIdent(rrinfoId);
			header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(),
					queueIdentify);
		} catch (Exception e) {
			throw new Exception("get LinkRouteType error!", e);
		}

		String deviceId = String.valueOf(getDeviceId());
		Map<String, Object> deviceParams = (HashMap<String, Object>) contenxtParmas
				.get(deviceId);
		String resource_type = (String) deviceParams
				.get(StorageConstants.RESOURCE_TYPE_STORAGE);
		if (StringUtils.isBlank(resource_type)) {
			throw new Exception("获取[RESOURCE_TYPE_STORAGE]参数错误！");
		}
		header.setResourceClass("SE");
		header.setResourceType(resource_type);
		header.setOperation(SEOpration.GETPORTGROUP);
		requestData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();

		String portgroup_finished_flag = (String)deviceParams.get(StorageConstants.DB_CHOOSE_PORTGROUP_FINISHED);
		if(StringUtils.isNotBlank(portgroup_finished_flag)){
			throw new Exception("已完成选择端口节点操作!");
		}
		// 获取数据中心
		String dcenter_ename = (String) deviceParams
				.get(StorageConstants.DATACENTER_ENAME);
		if (StringUtils.isBlank(dcenter_ename)) {
			throw new Exception("获取DATACENTER_ENAME参数错误!");
		}
		body.setString(StorageConstants.DATACENTER_ENAME, dcenter_ename);

		// 获取STORAGE_MODEL
		String storage_model = (String) deviceParams
				.get(StorageConstants.STORAGE_MODEL);
		if (StringUtils.isBlank(storage_model)) {
			throw new Exception("获取STORAGE_MODEL参数错误!");
		}
		body.setString(StorageConstants.STORAGE_MODEL, storage_model);

		String su_id = (String) deviceParams.get(StorageConstants.SU_ID);

		StorageMgrVo mgrVo = storageDao.findStorageMgrInfoBySuId(su_id);
		body.setString(StorageConstants.SMISISERVERIP, mgrVo.getManagerIp()); // ip come
																	// from
																	// contextParams
		body.setString(StorageConstants.USERNAME, mgrVo.getUserName());
		body.setString(StorageConstants.USERPASSWD, mgrVo.getPassword());
		body.setString(StorageConstants.NAMESPACE, mgrVo.getNamespace());

		String resPoolLevel = (String) deviceParams
				.get(StorageConstants.RES_POOL_LEVEL);
		int storage_size = 0;
		if (resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_PLATINUM)) {
			storage_size = StorageConstants.PLATINUM_STORAGE_SIZE;
		} else if (resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_GLOD)) {
			storage_size = StorageConstants.GLOD_STORAGE_SIZE;
		} else if (resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_SILVER)) {
			storage_size = StorageConstants.SILVER_STORAGE_SIZE;
		} else if (resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_COPPER)) {
			storage_size = StorageConstants.COPPER_STORAGE_SIZE;
		}
		String storage_sn_str = (String) deviceParams
				.get(StorageConstants.STORAGE_SN_MAP);
		JSONObject storage_sn_map = JSONObject.parseObject(storage_sn_str);
//		if (storage_sn_map.size() != storage_size) {
//			throw new Exception("获取存储数量错误！");
//		}

		Object storageSN1 = storage_sn_map.get(StorageConstants.STORAGEONE);
		Object storageSN2 = storage_sn_map.get(StorageConstants.STORAGETWO);

		List<String> storageSNList = new ArrayList<String>();
		if (storageSN1 == null) {
			errorMsg = "sn1 is null.";
			throw new Exception(errorMsg);
		}
		storageSNList.add(String.valueOf(storageSN1));
		String portGroup1 = null;
		try {
			StoragePo sePo1 = storageDao.getLastSelectedPortGroup(String
					.valueOf(storageSN1));
			if(sePo1==null){
				portGroup1 = "0";
			}else{
				portGroup1 = sePo1.getFcportGroupNum();
			}
			if (storageSN2 != null) {
				storageSNList.add(String.valueOf(storageSN2));
				StoragePo sePo2 = storageDao.getLastSelectedPortGroup(String
						.valueOf(storageSN2));
				String portGroup2 = null;
				if(sePo2==null){
					portGroup2 = "0";
				}else{
					portGroup2 = sePo2.getFcportGroupNum();
				}
				if (!portGroup1.equals(portGroup2)) {
					errorMsg = "The portGroup of storage is not consistent previously";
					throw new Exception(errorMsg);
				}
			}
		} catch (Exception e) {
			errorMsg = e.toString();
			throw new Exception(errorMsg, e);
		}
		body.setString(StorageConstants.SELECTED_PORT_GROUP, portGroup1);
		body.setList(StorageConstants.STORAGE_SN_KEY, storageSNList);

		HashMap<String, List<String>> map = checkTwoStorageInfo(storageSNList);
		if (map == null || map.size() == 0) {
			errorMsg = "Front Port Name in two storage "
					+ storageSNList.toString() + " is not consistent";
			throw new Exception(errorMsg);
		}

		body.setHashMap(StorageConstants.PORT_GROUP, map);
		requestData.setDataObject(MesgFlds.BODY, body);
		log.info("请求选择lun信息：" + JSONObject.toJSONString(requestData));
		return requestData;
	}

	private HashMap<String, List<String>> checkTwoStorageInfo(
			List<String> storageSNList) {
	//	StorageFCPortGroupDAO storageFCPortDao = (StorageFCPortGroupDAO) WebApplicationManager.getBean("storageFCPortGroupDAO");
		HashMap<String, List<String>> comparedMap = null;
		String portGroup = null;

		for (int i = 0; i < storageSNList.size(); i++) {
			Map<String, String> query_param_map = Maps.newHashMap();
			query_param_map.put("SN", storageSNList.get(i));
			query_param_map.put("isActive", "Y");
			List<StorageFCPortGroupPo> fcPortNameList = storageDao.getStorageFcPortListBySN(storageSNList.get(i));
			HashMap<String, List<String>> map = new HashMap<String, List<String>>();
			int size = fcPortNameList.size();
			if (size == 0) {
				errorMsg = "not found the front port for sn :"
						+ storageSNList.get(i);
//				new Exception(errorMsg);
				log.info(errorMsg);
			}

			for (int j = 0; j < size; j++) {
				portGroup = fcPortNameList.get(j).getFcportGroupNum();
				if (map.containsKey(portGroup)) {
					map.get(portGroup).add(
							fcPortNameList.get(j).getFcport());
				} else {
					List<String> portNameGroupList = new ArrayList<String>();
					portNameGroupList
							.add(fcPortNameList.get(j).getFcportGroupNum());
					map.put(portGroup, portNameGroupList);
				}
			}
			log.info("[ SN ] : " + storageSNList.get(i)
					+ " [ The front port ] : " + map.toString());
			if (comparedMap != null) {
				if (!compareMapEntry(map, comparedMap)) {
					return null;
				}
			} else {
				comparedMap = map;
			}
		}
		return comparedMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) throws Exception {

		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER,
				HeaderDO.class);
		log.info(header.getRetMesg());
		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			errorMsg = "get amq response error,";
			throw new Exception(errorMsg);
		}

		BodyDO body = responseDataObject.getDataObject(MesgFlds.BODY,
				BodyDO.class);
		String rrinfoId = (String) contenxtParmas.get(RRINFO_ID);
		String nodeId = (String) contenxtParmas.get(NODE_ID);
		List<String> deviceIdList = getDeviceIdList("",nodeId,rrinfoId,"");

		HashMap<String, Object> deviceParamsList = (HashMap<String, Object>) contenxtParmas
				.get(String.valueOf(getDeviceId()));

		String storage_sn_str = (String) deviceParamsList
				.get(StorageConstants.STORAGE_SN_MAP);
		JSONObject storage_sn_map = JSONObject.parseObject(storage_sn_str);
		
		Object snOne = storage_sn_map.get(StorageConstants.STORAGEONE);
		Object snTwo = storage_sn_map.get(StorageConstants.STORAGETWO);
		HashMap<String, String> snOneMap = null;
		HashMap<String, String> snTwoMap = null;

		try {
			if (snOne != null) {
				String sn1 = null;
				String sn2 = null;
				String group1 = null;
				String group2 = null;
				sn1 = String.valueOf(snOne);
				snOneMap = body.getHashMap(String.valueOf(snOne));
				if(snOneMap!=null){
					group1 = snOneMap.get("GROUP_ID");
				}
				if (snTwo != null) {
					sn2 = String.valueOf(snTwo);
					snTwoMap = body.getHashMap(String.valueOf(snTwo));
					group2 = snTwoMap.get("GROUP_ID");
					if (!(group1.equals(group2))) {
						errorMsg = "sn1 group not equals sn2 group.";
						throw new Exception(errorMsg);
					}
				}
				//service.checkAndSaveLastSelectedPortGroup(sn1, sn2, group1);

				if (snOneMap == null || snOneMap.size() == 0) {
					errorMsg = "storage " + snOne
							+ " front port and pwwn is null";
					throw new Exception(errorMsg);
				}
				snOneMap.put(StorageConstants.STORAGE_SN_KEY,
						String.valueOf(snOne));
				for (int i = 0; i < deviceIdList.size(); i++) {
					setHandleResultParam(String.valueOf(deviceIdList.get(i)),
							StorageConstants.FA_WWNONE, snOneMap.toString());
				}
				// exeMsg_map.put("选择存储-"+snOneMap.get("STORAGE_SN")+"-端口信息:",snOneMap);
				result_sb.append("STORAGE_SN1:" + snOneMap.get("STORAGE_SN"))
						.append("\n");
				result_sb.append("GROUP_ID:" + group1).append("\n");
				for (Iterator<Entry<String, String>> itr = snOneMap.entrySet()
						.iterator(); itr.hasNext();) {
					Entry<String, String> entry = itr.next();
					if (entry.getKey().equals("GROUP_ID")
							|| entry.getKey().equals("STORAGE_SN")) {
						continue;
					} else {
						result_sb.append(
								entry.getKey() + ":" + entry.getValue())
								.append("\n");
					}
				}
				log.debug(" portGroup1: " + snOneMap.toString());
			} else {
				errorMsg = "sn1 is null.";
				throw new Exception(errorMsg);
			}

			if (snTwo != null) {
				snTwoMap = body.getHashMap(String.valueOf(snTwo));
				if (snTwoMap == null || snTwoMap.size() == 0) {
					errorMsg = "storage " + snTwo
							+ " front port and pwwn is null";
					throw new Exception(errorMsg);
				}
				if (!checkFrontPortPwwnConsistent(snOneMap, snTwoMap)) {
					errorMsg = "The storage front port and wwpn map is not consistent";
					throw new Exception(errorMsg);
				}
				snTwoMap.put(STORAGEBASE, String.valueOf(snTwo));
				for (int i = 0; i < deviceIdList.size(); i++) {
					setHandleResultParam(String.valueOf(deviceIdList.get(i)),
							StorageConstants.FA_WWNTWO, snTwoMap.toString());
				}
				// exeMsg_map.put("选择存储-"+snOneMap.get("STORAGE_SN")+"-端口信息:",snTwoMap);
				result_sb.append("STORAGE_SN2:" + snTwoMap.get("STORAGE_SN"))
						.append("\n");
				result_sb.append("GROUP_ID:" + snTwoMap.get("GROUP_ID"))
						.append("\n");
				for (Iterator<Entry<String, String>> itr = snTwoMap.entrySet()
						.iterator(); itr.hasNext();) {
					Entry<String, String> entry = itr.next();
					if (entry.getKey().equals("GROUP_ID")
							|| entry.getKey().equals("STORAGE_SN")) {
						continue;
					} else {
						result_sb.append(
								entry.getKey() + ":" + entry.getValue())
								.append("\n");
					}
				}

				log.info("portGroupId2: " + snTwoMap.toString());
			}
		} catch (Exception e) {
			errorMsg = e.toString();
			throw new Exception(errorMsg, e);
		}

		for (int i = 0; i < deviceIdList.size(); i++) {
			setHandleResultParam(String.valueOf(deviceIdList.get(i)),
							StorageConstants.DB_CHOOSE_PORTGROUP_FINISHED, "FINISHED");
		}
	}

	private boolean checkFrontPortPwwnConsistent(
			HashMap<String, String> storageOneMap,
			HashMap<String, String> storageTwoMap) {
		storageOneMap.remove(StorageConstants.STORAGE_SN_KEY);
		storageTwoMap.remove(StorageConstants.STORAGE_SN_KEY);

		Set<String> keys = storageOneMap.keySet();
		for (String string : keys) {
			if (storageTwoMap.get(string) == null) {
				return false;
			}
		}

		return true;
		// return storageOneMap.keySet().equals(storageTwoMap);
	}

	@Override
	public String execute(HashMap<String, Object> contenxtParmas) {
		Map<String, String> rtn_map = Maps.newHashMap();
		if(contenxtParmas!=null){
			String flowInstId = (String) contenxtParmas.get(FLOW_INST_ID);
			String nodeId = (String) contenxtParmas.get(NODE_ID);
			String rrinfoId = (String) contenxtParmas.get(RRINFO_ID);

			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			try {
				Map<String, Map<String, String>> handleParams = this
						.getHandleParams(flowInstId);

				contenxtParmas.putAll(handleParams);
				Map<String, Object> extHandleParams = getExtHandleParams(contenxtParmas);

				if (extHandleParams != null)
					contenxtParmas.putAll(extHandleParams);

				List<String> deviceIdList = getDeviceIdList("",nodeId,rrinfoId,"");
				if (deviceIdList == null || deviceIdList.size() == 0) {
					String errorMsg = "the device list is null of the resource request id :"
							+ rrinfoId;
					throw new Exception(errorMsg);
				}
				setDeviceId(deviceIdList.get(0));
				IDataObject requestDataObject = buildRequestData(contenxtParmas);

				IDataObject responseDataObject = getResAdpterInvoker().invoke(
						requestDataObject, getTimeOut());

				handleResonpse(contenxtParmas, responseDataObject);

				saveParamInsts(flowInstId, nodeId);
			} catch (Exception e) {
				String errorMsg = "执行自动化操作失败,流程实例ID:" + flowInstId + ",节点ID:"
						+ nodeId + ".错误信息:" + e.getMessage();
				log.error(errorMsg, e);
				rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
				rtn_map.put("exeMsg", e.getMessage());
				return JSON.toJSONString(rtn_map);
			} finally {
				if (contenxtParmas != null)
					contenxtParmas.clear();
			}

			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					flowInstId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });
			rtn_map.put("checkCode", MesgRetCode.SUCCESS);
			rtn_map.put("exeMsg", result_sb.toString());
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null");
		}
		return JSON.toJSONString(rtn_map);
	}
	
	protected boolean compareMapEntry(HashMap<String, List<String>> map,
			HashMap<String, List<String>> comparedMap) {
		int size = map.size();
		if (size == comparedMap.size()) {
			for (Iterator<Entry<String, List<String>>> it = map.entrySet()
					.iterator(); it.hasNext();) {
				Map.Entry<String, List<String>> entry = it.next();
				String key = entry.getKey();
				List<String> list = entry.getValue();
				for (int i = 0; i < list.size(); i++) {
					String temp = list.get(i);
					if (!(comparedMap.containsKey(key) && comparedMap.get(key)
							.contains(temp))) {
						return Boolean.FALSE;
					}
				}
			}
		}
		return Boolean.TRUE;
	}
	
}
