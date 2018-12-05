package com.git.cloud.handler.automation.se.db;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.automation.se.common.StorageDBHAEnum;
import com.git.cloud.handler.automation.se.common.StorageDBRACEnum;
import com.git.cloud.handler.automation.se.common.StorageDBSingleEnum;
import com.git.cloud.resmgt.common.dao.impl.CmDeviceDAO;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.handler.automation.se.po.StorageAssignResPo;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.storage.model.vo.StorageDeviceVo;
import com.git.support.common.MesgRetCode;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class AssignConfirmHandler extends RemoteAbstractAutomationHandler  {
	private static Logger log = LoggerFactory
			.getLogger(AssignConfirmHandler.class);

	private StringBuffer return_sb = new StringBuffer();
	private static final String checkCode = "checkCode";
	private static final String exeMsg = "exeMsg";

	private CmDeviceDAO deviceDAO = (CmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
	private StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public String execute(HashMap<String, Object> contextParams) {
		String flowInstId, rrinfoId, nodeId;

		Map<String, String> rtn_map = Maps.newHashMap();

		// 流程实例Id
		flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
		// 节点ID
		nodeId = getContextStringPara(contextParams, NODE_ID);
		// 资源请求ID
		rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
		
		long startTime = System.currentTimeMillis();
		log.debug("执行自动化存储分配结果比对操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);

		try {
			log.info("Bejin To Get Contenxt Parameter ......");
			// 获取全局业务参数
			Map<String, Map<String, String>> handleParams = this
					.getHandleParams(flowInstId);

			// 将工作流相关参数和业务参数合并
			contextParams.putAll(handleParams);
			// 扩展业务参数
			Map<String, Object> extHandleParams = getExtHandleParams(contextParams);

			if (extHandleParams != null) {
				contextParams.putAll(extHandleParams);
			}
			List<String> deviceList = getDeviceIdList(rrinfoId,"","","");
			Map<String, Object> devMap = (Map<String, Object>) contextParams
					.get(deviceList.get(0));

			// 获取资源池服务级别信息
//			String resPoolLevel = (String) devMap.get(String.valueOf(deviceList
//					.get(0)));

			String flag = (String)devMap.get(StorageConstants.ASSIGN_LUN_CONFIRM_FINISHED);
			if(StringUtils.isNotBlank(flag)){
				throw new Exception("已完成存储分配确认操作！");
			}
			// 获取集群类型
			String cluster_type = (String) devMap.get(StorageConstants.CLUSTER_TYPE);
			
			// 获取OS_NAME
			String os_name = (String)devMap.get(StorageConstants.OS_NAME);
			// 获取存储sn信息
			String storage_sn_map_str = (String) devMap
					.get(StorageConstants.STORAGE_SN_MAP);

			JSONObject storage_sn_map = JSONObject
					.parseObject(storage_sn_map_str);

			// 获取存储设备类型
			String storage_dev_type = (String) devMap
					.get(StorageConstants.STORAGE_DEV_TYPE);
			if(StringUtils.isBlank(storage_dev_type)){
				throw new Exception("获取参数[STORAGE_DEV_TYPE]信息失败！");
			}
			Map<String, Map<String, List<String>>> sn_lun_type_map = getSnLunAndTypeList(
					devMap, cluster_type);
			if (storage_dev_type.equals(StorageConstants.STORAGE_DEV_TYPE_EMC)) {
				Map<String, List<Map<String, Object>>> sn_lun_disk_map = getEMCDiskInfo(
						contextParams, deviceList);
				saveEMCLunAssignInfo(sn_lun_type_map,sn_lun_disk_map,deviceList,storage_sn_map,
						devMap);
				saveEMCStorageResult(sn_lun_type_map, sn_lun_disk_map, deviceList, devMap);
			} else if (storage_dev_type
					.equals(StorageConstants.STORAGE_DEV_TYPE_HITACHI)) {

				if(os_name.contains(StorageConstants.OS_NAME_HP_UX)){
					Map<String, List<Map<String, Object>>> sn_lun_disk_map = getHDSDiskInfoHP(
							contextParams, deviceList);
					saveHDSLunAssignInfoHP(sn_lun_type_map,sn_lun_disk_map,deviceList,storage_sn_map,
							devMap);
					saveHDSStorageResultHP(sn_lun_type_map, sn_lun_disk_map, deviceList,  devMap);
				}else{
					Map<String, List<Map<String, Object>>> sn_lun_disk_map = getHDSDiskInfo(
							contextParams, deviceList);
					saveHDSLunAssignInfo(sn_lun_type_map,sn_lun_disk_map,deviceList,storage_sn_map,
							devMap);
					saveHDSStorageResule(sn_lun_type_map, sn_lun_disk_map, deviceList, devMap);
				}
			}
			for(String deviceId:deviceList){
				setHandleResultParam(deviceId,StorageConstants.ASSIGN_LUN_CONFIRM_FINISHED, "FINISHED");
			}
			saveParamInsts(flowInstId, nodeId);

			log.info("End To Save Assign Result To Contenxt Table OK");

			log.info("End To Analysis Assigned Storages OK");
		} catch (Exception e) {
			log.error("异常exception",e);
			String errorMsg = "执行自动化存储分配结果比对操作失败,流程实例ID:" + flowInstId
					+ ",节点ID:" + nodeId + e.getMessage();
			log.error(errorMsg, e);
			rtn_map.put(checkCode, MesgRetCode.ERR_PROCESS_FAILED);
			rtn_map.put(exeMsg, e.getMessage());
			return JSON.toJSONString(rtn_map);
		} finally {

			// 尽快的释放内存
			if (contextParams != null)
				contextParams.clear();
		}

		log.debug("执行自动化存储分配结果比对操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
				flowInstId, nodeId,
				new Long((System.currentTimeMillis() - startTime)) });

		rtn_map.put(checkCode, MesgRetCode.SUCCESS);
		rtn_map.put(exeMsg, return_sb.toString());
		return JSON.toJSONString(rtn_map);
	}

	public Map<String, Map<String, List<String>>> getSnLunAndTypeList(
			Map<String, Object> devMapInfo, String cluster_type) throws Exception {
		String storage_sn_map_str = (String) devMapInfo
				.get(StorageConstants.STORAGE_SN_MAP);
		JSONObject storage_sn_map = JSONObject.parseObject(storage_sn_map_str);
		List<String> lun_type_list = getLunTypeList(devMapInfo, cluster_type);
		// 获取存储对应的lun——type信息
		Iterator<Entry<String, Object>> itr = storage_sn_map.entrySet()
				.iterator();
		Map<String, Map<String, List<String>>> sn_lun_type_map = Maps
				.newHashMap();
		Map<String, List<String>> lun_type_map = Maps.newHashMap();
		while (itr.hasNext()) {
			Entry<String, Object> entry = itr.next();
			String sn = (String) entry.getValue();
			for (String type : lun_type_list) {
				String lunIds_str = (String) devMapInfo.get(sn + "_TYPE_"
						+ type);
				JSONArray lunIds_array = JSONObject.parseArray(lunIds_str);
				if(lunIds_array==null||lunIds_array.size()==0){
					throw new Exception("获取["+sn+ "_TYPE_"+ type+"]类型的lun_id信息失败！");
				}
				List<String> list = Lists.newArrayList();
				for (Object obj : lunIds_array) {
					list.add(String.valueOf(obj));
				}
				if (lun_type_map.containsKey(type)) {
					List<String> _list = lun_type_map.get(type);
					if (list.equals(_list)) {
						continue;
					} else {
						throw new Exception("两天存储类型:" + type
								+ "lun id 信息不一致");
					}
				} else {
					lun_type_map.put(type, list);
				}
			}
			sn_lun_type_map.put(sn, lun_type_map);
		}
		return sn_lun_type_map;
	}

	public List<String> getLunTypeList(Map<String, Object> devMapInfo,
			String cluster_type) {
		List<String> lun_type = Lists.newArrayList();
		if (cluster_type.equals(StorageConstants.CLUSTER_TYPE_RAC)) {
			lun_type.add((String) devMapInfo
					.get(StorageDBRACEnum.HEARTBEAT_NAME.name()));
			lun_type.add((String) devMapInfo
					.get(StorageDBRACEnum.DATA_DISK_NAME.name()));
			lun_type.add((String) devMapInfo
					.get(StorageDBRACEnum.ARCH_DISK_NAME.name()));
		} else if (cluster_type.equals(StorageConstants.CLUSTER_TYPE_HA)) {
			lun_type.add((String) devMapInfo
					.get(StorageDBHAEnum.HEARTBEAT_NAME.name()));
			lun_type.add((String) devMapInfo
					.get(StorageDBHAEnum.DATA_DISK_NAME.name()));
			lun_type.add((String) devMapInfo
					.get(StorageDBHAEnum.ARCH_DISK_NAME.name()));
		} else if (cluster_type.equals(StorageConstants.CLUSTER_TYPE_SINGLE)) {
			lun_type.add((String) devMapInfo
					.get(StorageDBSingleEnum.DATA_DISK_NAME.name()));
			lun_type.add((String) devMapInfo
					.get(StorageDBSingleEnum.ARCH_DISK_NAME.name()));
		}
		return lun_type;
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<Map<String, Object>>> getEMCDiskInfo(
			Map<String, Object> contextParams, List<String> deviceList) {
		try {
			Map<String, List<Map<String, Object>>> scan_sn_lun_disk_map = Maps
					.newHashMap();
			if (deviceList.size() == 2) {
				Map<String, Object> devInfoMap1 = (Map<String, Object>) contextParams
						.get(String.valueOf(deviceList.get(0)));
				String storageInfo1 = getBigParamValue(devInfoMap1,
						StorageConstants.STORAGE_ASSING_INFO);
				Map<String, Object> devInfoMap2 = (Map<String, Object>) contextParams
						.get(String.valueOf(deviceList.get(1)));
				String storageInfo2 = getBigParamValue(devInfoMap2,
						StorageConstants.STORAGE_ASSING_INFO);
				if (!storageInfo1.equals(storageInfo2)) {
					throw new Exception("deviceIdList:["
							+ deviceList.get(0) + "," + deviceList.get(1)
							+ "]扫描分盘结果过不一致");
				}
			}
			Map<String, Object> devInfoMap = (Map<String, Object>) contextParams
					.get(String.valueOf(deviceList.get(0)));
			String storageInfo = getBigParamValue(devInfoMap,
					StorageConstants.STORAGE_ASSING_INFO);
			if (StringUtils.isBlank(storageInfo) || storageInfo.equals("null")) {
				throw new Exception(
						"获取主机/存储分盘对应信息参数[STORAGE_ASSING_INFO]错误！");
			}
			JSONObject jsonObject = JSONObject.parseObject(storageInfo);
			JSONArray jsonArray = jsonObject
					.getJSONArray(StorageConstants.EMC_HDISK_POWER);
			String storage_sn = "";
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				storage_sn = (String) obj.get("storage_sn");
				if (scan_sn_lun_disk_map.containsKey(storage_sn)) {
					scan_sn_lun_disk_map.get(storage_sn).add(obj);
				} else {
					List<Map<String, Object>> l = Lists.newArrayList();
					l.add(obj);
					scan_sn_lun_disk_map.put(storage_sn, l);
				}
			}
			return scan_sn_lun_disk_map;
		} catch (Exception e) {
			log.error("异常exception",e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, List<Map<String, Object>>> getHDSDiskInfo(
			Map<String, Object> contextParams, List<String> deviceList) {
		try {
			Map<String, List<Map<String, Object>>> scan_sn_lun_disk_map = Maps
					.newHashMap();
			if (deviceList.size() == 2) {
				Map<String, Object> devInfoMap1 = (Map<String, Object>) contextParams
						.get(String.valueOf(deviceList.get(0)));
				String storageInfo1 = getBigParamValue(devInfoMap1,
						StorageConstants.STORAGE_ASSING_INFO);
				Map<String, Object> devInfoMap2 = (Map<String, Object>) contextParams
						.get(String.valueOf(deviceList.get(1)));
				String storageInfo2 = getBigParamValue(devInfoMap2,
						StorageConstants.STORAGE_ASSING_INFO);
				if (!storageInfo1.equals(storageInfo2)) {
					throw new Exception("deviceIdList:["
							+ deviceList.get(0) + "," + deviceList.get(1)
							+ "]扫描分盘结果过不一致");
				}
			}
			Map<String, Object> devInfoMap = (Map<String, Object>) contextParams
					.get(String.valueOf(deviceList.get(0)));
			String storageInfo = getBigParamValue(devInfoMap,
					StorageConstants.STORAGE_ASSING_INFO);
			if (StringUtils.isBlank(storageInfo) || storageInfo.equals("null")) {
				throw new Exception(
						"获取主机/存储分盘对应信息参数[STORAGE_ASSING_INFO]错误！");
			}
			JSONObject jsonObject = JSONObject.parseObject(storageInfo);
			JSONArray jsonArray = jsonObject
					.getJSONArray(StorageConstants.HDS_HDISK_POWER);
			String storage_sn = "";
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				storage_sn = (String) obj.get("storage_sn");
				if (scan_sn_lun_disk_map.containsKey(storage_sn)) {
					scan_sn_lun_disk_map.get(storage_sn).add(obj);
				} else {
					List<Map<String, Object>> l = Lists.newArrayList();
					l.add(obj);
					scan_sn_lun_disk_map.put(storage_sn, l);
				}
			}
			String os_name = (String)devInfoMap.get(StorageConstants.OS_NAME);

			if(os_name.equals(StorageConstants.OS_NAME_AIX)){
				String json_obj = JSONObject.toJSONString(scan_sn_lun_disk_map);
				Map  _scan_sn_lun_disk_map = Maps.newHashMap();
				for(String key : scan_sn_lun_disk_map.keySet()){
					if(key.startsWith("00")){
						String _key = key.replaceFirst("00", "");
						json_obj = json_obj.replace(key, _key);
					}
				}
				
				_scan_sn_lun_disk_map = JSONObject.parseObject(json_obj);
				return _scan_sn_lun_disk_map;
			}
			return scan_sn_lun_disk_map;
		} catch (Exception e) {
			log.error("异常exception",e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, List<Map<String, Object>>> getHDSDiskInfoHP(
			Map<String, Object> contextParams, List<String> deviceList) {
		try {
			Map<String, List<Map<String, Object>>> scan_sn_lun_disk_map = Maps
					.newHashMap();
			for (String deviceId : deviceList) {
				Map<String, Object> devInfoMap = (Map<String, Object>) contextParams
						.get(String.valueOf(deviceId));
				List<String> fa_wwn = Lists.newArrayList();
				String fa_wwn1 = (String)devInfoMap.get(StorageConstants.FA_WWNONE);
				String fa_wwn2 = (String)devInfoMap.get(StorageConstants.FA_WWNTWO);
				if(StringUtils.isNotBlank(fa_wwn1)){
					fa_wwn.add(fa_wwn1.replace(":", ""));
				}
				if(StringUtils.isNotBlank(fa_wwn2)){
					fa_wwn.add(fa_wwn2.replace(":", ""));
				}
				String storageInfo = getBigParamValue(devInfoMap,
						StorageConstants.STORAGE_ASSING_INFO);
				JSONObject jsonObject = JSONObject.parseObject(storageInfo);
				JSONArray jsonArray = jsonObject
						.getJSONArray(StorageConstants.HDS_HDISK_POWER);
				List<Map<String, Object>> list = Lists.newArrayList();
				String storage_sn = "";
				String fc_wwn = "";
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					fc_wwn = (String) obj.get("storage_fc");
					fc_wwn = fc_wwn.substring(2,fc_wwn.length());
					storage_sn = getSNbyFcWWN(fa_wwn,fc_wwn);
					if(StringUtils.isBlank(storage_sn)){
						continue;
					}
					list.add(obj);
				}
				scan_sn_lun_disk_map.put(storage_sn, list);
			}
			return scan_sn_lun_disk_map;
		} catch (Exception e) {
			log.error("异常exception",e);
		}
		return null;
	}
	
	public String getSNbyFcWWN(List<String> fc_wwn_list,String fc_wwn){
		log.info("扫描出storage_fc:"+fc_wwn);
		for(String str : fc_wwn_list){
			if(str.contains(fc_wwn)){
				String[] obj_array = str.split(",");
				for(String obj : obj_array){
					if(obj.contains("STORAGE_SN")){
						String sn = obj.replace("STORAGE_SN=", "").trim();
						log.info("storage_fc:["+fc_wwn+"] 所属sn为["+sn+"]");
						return sn;
					}
				}
			}
		}
		log.info("未查找出fc_wwn所属存储sn信息");
		return null;
	}

	/**
	 * @throws Exception 
	 * @Title: saveEMCLunAssignInfo
	 * @Description: 验证需要划分的LUNid是否在扫盘结果中，保存分配信息到cm_storage_assgin_res表中
	 * @field: @param sn_lun_type_map
	 * @field: @param sn_lun_disk_map
	 * @field: @param deviceIdList
	 * @field: @param sn_map
	 * @field: @param devMapInfo
	 * @return void
	 * @throws
	 */
	public void saveEMCLunAssignInfo(
			Map<String, Map<String, List<String>>> sn_lun_type_map,
			Map<String, List<Map<String, Object>>> sn_lun_disk_map,
			List<String> deviceIdList, JSONObject _sn_map,
			Map<String, Object> devMapInfo) throws Exception {
		if (sn_lun_type_map.size() != sn_lun_disk_map.size()) {
			throw new Exception("进行分盘操作的存储数量信息错误！");
		}
		List<StorageAssignResPo> list = Lists.newArrayList();
		List<Object> sn_list = Lists.newArrayList();
		sn_list.addAll(_sn_map.values());
		
		for (String deviceId : deviceIdList) {

			String hostName = deviceDAO.findCmDeviceById(deviceId).getDeviceName();
			return_sb.append("主机名称："+hostName).append("\n");
			for (Object _storage_sn : sn_list) {
				String storage_sn = String.valueOf(_storage_sn);
				return_sb.append("存储SN："+storage_sn).append("\n");
				Date create_time = getCreateLunTime(devMapInfo,storage_sn);
				StorageDeviceVo storage_dev_vo = storageDao.findStorageDevBySn(storage_sn);
				Map<String, List<String>> lun_type_map = sn_lun_type_map
						.get(storage_sn);
				List<Map<String, Object>> lun_disk_map = sn_lun_disk_map
						.get(storage_sn);

				Iterator<Entry<String, List<String>>> lun_type_itr = lun_type_map
						.entrySet().iterator();
				while (lun_type_itr.hasNext()) {
					Entry<String, List<String>> lun_type_entry = lun_type_itr
							.next();
					String type = lun_type_entry.getKey();
					List<String> lunId_list = lun_type_entry.getValue();
					return_sb.append("磁盘类型："+type).append("\n");
					for (String lunId : lunId_list) {
						boolean flag = true;
						for (Map<String, Object> map : lun_disk_map) {
							String _lunId = "0" + (String) map.get("lun_id");
							if (lunId.equals(_lunId)) {
								StorageAssignResPo po = new StorageAssignResPo();					
								po.setStorageAssignResId(UUIDGenerator.getUUID());
								po.setHostId(deviceId);
								po.setHdisk(map.get(StorageConstants.HDISKPOWER_NUMBER).toString());
								po.setStorageId(storage_dev_vo.getId());
								po.setLunId(lunId);
								po.setPurpose(type);
								po.setIsActive("Y");
								po.setLunCreateTime(create_time);
								po.setLunUpdateTime(create_time);
								list.add(po);
								return_sb.append(lunId).append("-----");
								return_sb.append(map.get(StorageConstants.HDISKPOWER_NUMBER).toString()).append("\n");
								flag = false;
							}else {
								continue;
							}
						}
						if(flag){
							throw new Exception("扫盘操作未获取LUN_ID:"+lunId+"信息！");
						}
					}
				}
			}
		}
		//service.batchPersistList(list);
	}
	
	public void saveEMCStorageResult(
			Map<String, Map<String, List<String>>> sn_lun_type_map,
			Map<String, List<Map<String, Object>>> sn_lun_disk_map,
			List<String> deviceIdList,
			Map<String, Object> devMapInfo) throws Exception{
		String sn_map_str = (String)devMapInfo.get(StorageConstants.STORAGE_SN_MAP);
		JSONObject sn_map = JSONObject.parseObject(sn_map_str);
		String sn1 = (String)sn_map.get("STORAGE_SN1");
		String sn2 = (String)sn_map.get("STORAGE_SN2");
		Map<String,List<String>> storage_result_map = Maps.newHashMap();
		Map<String,List<String>> lun_type_map1 = sn_lun_type_map.get(sn1);
		List<Map<String,Object>> lun_disk_map1 = sn_lun_disk_map.get(sn1);
		if(lun_type_map1==null|| lun_type_map1.isEmpty()){
			try {
				throw new Exception("获取主存储SN:["+sn1+"]磁盘类型错误！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("异常exception",e);
			}
		}else{
			for(String type_key: lun_type_map1.keySet()){
				List<String> lunId_list = lun_type_map1.get(type_key);
				for (String lunId : lunId_list) {
					for (Map<String, Object> map : lun_disk_map1) {
						String _lunId = "0" + (String) map.get("lun_id");
						if (lunId.equals(_lunId)) {
							String disk_value = map.get(StorageConstants.HDISKPOWER_NUMBER).toString();
							String disk_key = StorageConstants.lun_type.get(type_key);
							if(storage_result_map.containsKey(disk_key)){
								storage_result_map.get(disk_key).add(disk_value);
							}else{
								List<String> value_list = Lists.newArrayList();
								value_list.add(disk_value);
								storage_result_map.put(disk_key, value_list);
							}
						}else {
							continue;
						}
					}
				}
			}
		}
		
		if(StringUtils.isNotBlank(sn2)){
			Map<String,List<String>> lun_type_map2 = sn_lun_type_map.get(sn2);
			List<Map<String,Object>> lun_disk_map2 = sn_lun_disk_map.get(sn2);
			if(lun_type_map2==null|| lun_type_map2.isEmpty()){
				throw new Exception("获取镜像存储SN:["+sn2+"]磁盘类型错误！");
			}
			for(String type_key: lun_type_map2.keySet()){
				List<String> lunId_list = lun_type_map2.get(type_key);
				for (String lunId : lunId_list) {
					for (Map<String, Object> map : lun_disk_map2) {
						String _lunId = "0" + (String) map.get("lun_id");
						if (lunId.equals(_lunId)) {
							String disk_value = map.get(StorageConstants.HDISKPOWER_NUMBER).toString();
							String disk_key = StorageConstants.lun_type.get(type_key+"M");
							if(storage_result_map.containsKey(disk_key)){
								storage_result_map.get(disk_key).add(disk_value);
							}else{
								List<String> value_list = Lists.newArrayList();
								value_list.add(disk_value);
								storage_result_map.put(disk_key, value_list);
							}
						}else {
							continue;
						}
					}
				}
			}
		}
		Map<String,String> storage_result = Maps.newHashMap();
		for(String key : storage_result_map.keySet()){
			List<String> list = storage_result_map.get(key);
			String values = new String("");
			for(String value:list){
				values+=","+value;
			}
			values = values.replaceFirst(",", "");
			storage_result.put(key, values);
		}
		for(String deviceId:deviceIdList){
			setHandleResultParam(deviceId,
					StorageConstants.STORAGE_RESULT,
					JSONObject.toJSONString(storage_result));
		}
	}
	
	
	/**
	 * @throws Exception 
	 * @Title: saveHDSLunAssignInfo
	 * @Description: 验证需要划分的LUNid是否在扫盘结果中，保存分配信息到cm_storage_assgin_res表中
	 * @field: @param sn_lun_type_map
	 * @field: @param sn_lun_disk_map
	 * @field: @param deviceIdList
	 * @field: @param sn_map
	 * @field: @param devMapInfo
	 * @return void
	 * @throws
	 */
	public void saveHDSLunAssignInfo(
			Map<String, Map<String, List<String>>> sn_lun_type_map,
			Map<String, List<Map<String, Object>>> sn_lun_disk_map,
			List<String> deviceIdList, JSONObject _sn_map,
			Map<String, Object> devMapInfo) throws Exception {
		if (sn_lun_type_map.size() != sn_lun_disk_map.size()) {
			throw new Exception("进行分盘操作的存储数量信息错误！");
		}
		List<StorageAssignResPo> list = Lists.newArrayList();
		List<Object> sn_list = Lists.newArrayList();
		sn_list.addAll(_sn_map.values());
		
		for (String deviceId : deviceIdList) {
			String hostName = deviceDAO.findCmDeviceById(deviceId).getDeviceName();
			return_sb.append("主机名称："+hostName).append("\n");
			for (Object _storage_sn : sn_list) {
				String storage_sn = String.valueOf(_storage_sn);
				return_sb.append("存储SN："+storage_sn).append("\n");
				Date create_time = getCreateLunTime(devMapInfo,storage_sn);
				StorageDeviceVo storage_dev_vo = storageDao.findStorageDevBySn(storage_sn);
				Map<String, List<String>> lun_type_map = sn_lun_type_map
						.get(storage_sn);
				List<Map<String, Object>> lun_disk_map = sn_lun_disk_map
						.get(storage_sn);

				Iterator<Entry<String, List<String>>> lun_type_itr = lun_type_map
						.entrySet().iterator();
				while (lun_type_itr.hasNext()) {
					Entry<String, List<String>> lun_type_entry = lun_type_itr
							.next();
					String type = lun_type_entry.getKey();
					List<String> lunId_list = lun_type_entry.getValue();
					return_sb.append("磁盘类型："+type).append("\n");
					for (String lunId : lunId_list) {
						boolean flag = true;
						for (Map<String, Object> map : lun_disk_map) {
							String _lunId = (String) map.get("lun_id");
							if (lunId.replace(":", "").equals(_lunId)) {
								StorageAssignResPo po = new StorageAssignResPo();					
								po.setStorageAssignResId(UUIDGenerator.getUUID());
								po.setHostId(deviceId);
								po.setHdisk(map.get(StorageConstants.HDISK_NUMBER).toString());
								po.setStorageId(storage_dev_vo.getId());
								po.setLunId(lunId);
								po.setPurpose(type);
								po.setIsActive("Y");
								po.setLunCreateTime(create_time);
								po.setLunUpdateTime(create_time);
								list.add(po);
								return_sb.append(lunId).append("-----");
								return_sb.append(map.get(StorageConstants.HDISK_NUMBER).toString()).append("\n");
								flag = false;
							}else {
								continue;
							}
						}
						if(flag){
							throw new Exception("扫盘操作未获取LUN_ID:"+lunId+"信息！");
						}
					}
				}
			}
		}
		//service.batchPersistList(list);
	}
	
	public void saveHDSStorageResule(
			Map<String, Map<String, List<String>>> sn_lun_type_map,
			Map<String, List<Map<String, Object>>> sn_lun_disk_map,
			List<String> deviceIdList,
			Map<String, Object> devMapInfo) throws Exception{

		String sn_map_str = (String)devMapInfo.get(StorageConstants.STORAGE_SN_MAP);
		JSONObject sn_map = JSONObject.parseObject(sn_map_str);
		String sn1 = (String)sn_map.get("STORAGE_SN1");
		String sn2 = (String)sn_map.get("STORAGE_SN2");
		Map<String,List<String>> storage_result_map = Maps.newHashMap();
		Map<String,List<String>> lun_type_map1 = sn_lun_type_map.get(sn1);
		List<Map<String,Object>> lun_disk_map1 = sn_lun_disk_map.get(sn1);
		if(lun_type_map1==null|| lun_type_map1.isEmpty()){
			try {
				throw new Exception("获取主存储SN:["+sn1+"]磁盘类型错误！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("异常exception",e);
			}
		}else{
			for(String type_key: lun_type_map1.keySet()){
				List<String> lunId_list = lun_type_map1.get(type_key);
				for (String lunId : lunId_list) {
					for (Map<String, Object> map : lun_disk_map1) {
						String _lunId = (String) map.get("lun_id");
						if (lunId.replace(":", "").equals(_lunId)){
							String disk_value = map.get(StorageConstants.HDISK_NUMBER).toString();
							String disk_key = StorageConstants.lun_type.get(type_key);
							if(storage_result_map.containsKey(disk_key)){
								storage_result_map.get(disk_key).add(disk_value);
							}else{
								List<String> value_list = Lists.newArrayList();
								value_list.add(disk_value);
								storage_result_map.put(disk_key, value_list);
							}
						}else {
							continue;
						}
					}
				}
			}
		}
		
		if(StringUtils.isNotBlank(sn2)){
			Map<String,List<String>> lun_type_map2 = sn_lun_type_map.get(sn2);
			List<Map<String,Object>> lun_disk_map2 = sn_lun_disk_map.get(sn2);
			if(lun_type_map2==null|| lun_type_map2.isEmpty()){
				throw new Exception("获取镜像存储SN:["+sn2+"]磁盘类型错误！");
			}
			for(String type_key: lun_type_map2.keySet()){
				List<String> lunId_list = lun_type_map2.get(type_key);
				for (String lunId : lunId_list) {
					for (Map<String, Object> map : lun_disk_map2) {
						String _lunId = (String) map.get("lun_id");
						if (lunId.replace(":", "").equals(_lunId)){
							String disk_value = map.get(StorageConstants.HDISK_NUMBER).toString();
							String disk_key = StorageConstants.lun_type.get(type_key+"M");
							if(storage_result_map.containsKey(disk_key)){
								storage_result_map.get(disk_key).add(disk_value);
							}else{
								List<String> value_list = Lists.newArrayList();
								value_list.add(disk_value);
								storage_result_map.put(disk_key, value_list);
							}
						}else {
							continue;
						}
					}
				}
			}
		}

		Map<String,String> storage_result = Maps.newHashMap();
		for(String key : storage_result_map.keySet()){
			List<String> list = storage_result_map.get(key);
			String values = new String("");
			for(String value:list){
				values+=","+value;
			}
			values = values.replaceFirst(",", "");
			storage_result.put(key, values);
		}
		for(String deviceId:deviceIdList){
			setHandleResultParam(deviceId,
					StorageConstants.STORAGE_RESULT,
					JSONObject.toJSONString(storage_result));
		}
	}
	/**
	 * @throws Exception 
	 * @Title: saveHDSLunAssignInfoHP
	 * @Description: 验证需要划分的LUNid是否在扫盘结果中，保存分配信息到cm_storage_assgin_res表中
	 * @field: @param sn_lun_type_map
	 * @field: @param sn_lun_disk_map
	 * @field: @param deviceIdList
	 * @field: @param sn_map
	 * @field: @param devMapInfo
	 * @return void
	 * @throws
	 */
	public void saveHDSLunAssignInfoHP(
			Map<String, Map<String, List<String>>> sn_lun_type_map,
			Map<String, List<Map<String, Object>>> sn_lun_disk_map,
			List<String> deviceIdList, JSONObject _sn_map,
			Map<String, Object> devMapInfo) throws Exception {
		if (sn_lun_type_map.size() != sn_lun_disk_map.size()) {
			throw new Exception("进行分盘操作的存储数量信息错误！");
		}
		List<StorageAssignResPo> list = Lists.newArrayList();
		List<Object> sn_list = Lists.newArrayList();
		sn_list.addAll(_sn_map.values());
		
		for (String deviceId : deviceIdList) {
			String hostName = deviceDAO.findCmDeviceById(deviceId).getDeviceName();
			return_sb.append("主机名称："+hostName).append("\n");
			for (Object _storage_sn : sn_list) {
				String storage_sn = String.valueOf(_storage_sn);
				return_sb.append("存储SN："+storage_sn).append("\n");
				Date create_time = getCreateLunTime(devMapInfo,storage_sn);
				StorageDeviceVo storage_dev_vo = storageDao.findStorageDevBySn(storage_sn);
				Map<String, List<String>> lun_type_map = sn_lun_type_map
						.get(storage_sn);
				List<Map<String, Object>> lun_disk_map = sn_lun_disk_map
						.get(storage_sn);
				if(lun_disk_map==null||lun_disk_map.size()==0){
					continue;
				}

				Iterator<Entry<String, List<String>>> lun_type_itr = lun_type_map
						.entrySet().iterator();
				while (lun_type_itr.hasNext()) {
					Entry<String, List<String>> lun_type_entry = lun_type_itr
							.next();
					String type = lun_type_entry.getKey();
					List<String> lunId_list = lun_type_entry.getValue();
					return_sb.append("磁盘类型："+type).append("\n");
					for (String lunId : lunId_list) {
						boolean flag = true;
						for (Map<String, Object> map : lun_disk_map) {
							String _lunId = (String) map.get("lun_id");
							if (_lunId.endsWith(lunId.replace(":", ""))) {
								StorageAssignResPo po = new StorageAssignResPo();					
								po.setStorageAssignResId(UUIDGenerator.getUUID());
								po.setHostId(deviceId);
								po.setHdisk(map.get(StorageConstants.HDISK_NUMBER).toString());
								po.setStorageId(storage_dev_vo.getId());
								po.setLunId(lunId);
								po.setPurpose(type);
								po.setIsActive("Y");
								po.setLunCreateTime(create_time);
								po.setLunUpdateTime(create_time);
								list.add(po);
								return_sb.append(lunId).append("-----");
								return_sb.append(map.get(StorageConstants.HDISK_NUMBER).toString()).append("\n");
								flag = false;
							}else {
								continue;
							}
						}
						if(flag){
							throw new Exception("扫盘操作未获取LUN_ID:"+lunId+"信息！");
						}
					}
				}
			}
		}
		//service.batchPersistList(list);
	}
	
	public void saveHDSStorageResultHP(
			Map<String, Map<String, List<String>>> sn_lun_type_map,
			Map<String, List<Map<String, Object>>> sn_lun_disk_map,
			List<String> deviceIdList, 
			Map<String, Object> devMapInfo) throws Exception{
		String sn_map_str = (String)devMapInfo.get(StorageConstants.STORAGE_SN_MAP);
		JSONObject sn_map = JSONObject.parseObject(sn_map_str);
		String sn1 = (String)sn_map.get("STORAGE_SN1");
		String sn2 = (String)sn_map.get("STORAGE_SN2");
		Map<String,List<String>> storage_result_map = Maps.newHashMap();
		Map<String,List<String>> lun_type_map1 = sn_lun_type_map.get(sn1);
		List<Map<String,Object>> lun_disk_map1 = sn_lun_disk_map.get(sn1);
		if(lun_type_map1==null|| lun_type_map1.isEmpty()){
			try {
				throw new Exception("获取主存储SN:["+sn1+"]磁盘类型错误！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("异常exception",e);
			}
		}else{
			for(String type_key: lun_type_map1.keySet()){
				List<String> lunId_list = lun_type_map1.get(type_key);
				for (String lunId : lunId_list) {
					for (Map<String, Object> map : lun_disk_map1) {
						String _lunId = (String) map.get("lun_id");
						if (_lunId.endsWith(lunId.replace(":", ""))) {
							String disk_value = map.get(StorageConstants.HDISK_NUMBER).toString();
							String disk_key = StorageConstants.lun_type.get(type_key);
							if(storage_result_map.containsKey(disk_key)){
								storage_result_map.get(disk_key).add(disk_value);
							}else{
								List<String> value_list = Lists.newArrayList();
								value_list.add(disk_value);
								storage_result_map.put(disk_key, value_list);
							}
						}else {
							continue;
						}
					}
				}
			}
		}
		if(StringUtils.isNotBlank(sn2)){
			Map<String,List<String>> lun_type_map2 = sn_lun_type_map.get(sn2);
			List<Map<String,Object>> lun_disk_map2 = sn_lun_disk_map.get(sn2);
			if(lun_type_map2==null|| lun_type_map2.isEmpty()){
				throw new Exception("获取主存储SN:["+sn2+"]磁盘类型错误！");
			}
			for(String type_key: lun_type_map2.keySet()){
				List<String> lunId_list = lun_type_map2.get(type_key);
				for (String lunId : lunId_list) {
					for (Map<String, Object> map : lun_disk_map2) {
						String _lunId = (String) map.get("lun_id");
						if (_lunId.endsWith(lunId.replace(":", ""))) {
							String disk_value = map.get(StorageConstants.HDISK_NUMBER).toString();
							String disk_key = StorageConstants.lun_type.get(type_key+"M");
							if(storage_result_map.containsKey(disk_key)){
								storage_result_map.get(disk_key).add(disk_value);
							}else{
								List<String> value_list = Lists.newArrayList();
								value_list.add(disk_value);
								storage_result_map.put(disk_key, value_list);
							}
						}else {
							continue;
						}
					}
				}
			}
		}
		Map<String,String> storage_result = Maps.newHashMap();
		for(String key : storage_result_map.keySet()){
			List<String> list = storage_result_map.get(key);
			String values = new String("");
			for(String value:list){
				values+=","+value;
			}
			values = values.replaceFirst(",", "");
			storage_result.put(key, values);
		}
		for(String deviceId:deviceIdList){
			setHandleResultParam(deviceId,
					StorageConstants.STORAGE_RESULT,
					JSONObject.toJSONString(storage_result));
		}
	}
	public Date getCreateLunTime(Map<String,Object> devMapInfo,String sn){
		try{
		String create_time_str = (String) devMapInfo.get(sn+"_LUN_CREATE_TIME");
		JSONObject obj = JSONObject.parseObject(create_time_str);
		String create_time = (String)obj.get(sn);
		if(StringUtils.isBlank(create_time)){
			throw new Exception("获取sn:["+sn+"] lun create time 错误！");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = sdf.parse(create_time);
		return d;
		}catch(Exception e){
			log.error("异常exception",e);
		}
		return null;
	}
}
