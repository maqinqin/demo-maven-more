package com.git.cloud.handler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.po.StorageSuPo;
import com.git.cloud.handler.dao.AutomationStorageDAO;
import com.git.cloud.handler.vo.NasInfoVo;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmSeatPo;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.model.vo.CmVmVo;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.cloud.resmgt.storage.model.po.SePoolLevelRulePo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class StorageServiceImpl implements StorageService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private AutomationStorageDAO autoSeDAOImpl;
	public AutomationStorageDAO getAutoSeDAOImpl() {
		return autoSeDAOImpl;
	}

	public void setAutoSeDAOImpl(AutomationStorageDAO autoSeDAOImpl) {
		this.autoSeDAOImpl = autoSeDAOImpl;
	}

	private StorageDAO storageDAOImpl;

	public StorageDAO getStorageDAOImpl() {
		return storageDAOImpl;
	}

	public void setStorageDAOImpl(StorageDAO storageDAOImpl) {
		this.storageDAOImpl = storageDAOImpl;
	}
	
	@Override
	public Map<String, String> getNASMgrLoginInfo(String nasSn) {
		NasInfoVo nas = autoSeDAOImpl.getNASMgrLoginInfo(nasSn);
		Map<String, String> nasMap = new HashMap<String, String> ();
		if(nas != null) {
			nasMap.put("SERVER_IP", nas.getServerIp());
			nasMap.put("USER_NAME", nas.getUserName());
			nasMap.put("USER_PASSWD", nas.getPwd());
		}
		return nasMap;
	}

	@Override
	public String getStorageModelBySN(String sn) {
		return autoSeDAOImpl.findStorageModelBySN(sn);
	}

	@Override
	public List<String> getNasProcIPsBySN(String sn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResPoolLevel(String availability_level, String perf_level,
			String rule_type) {
		return autoSeDAOImpl.findResPoolLevel(availability_level, perf_level, rule_type);
		
	}

	@Override
	public List<SePoolLevelRulePo> getPerformanceInfo(String performanceType,
			String ruleType) {
		return autoSeDAOImpl.findPerformanceInfo(performanceType,ruleType);
	}

	@Override
	public List<SePoolLevelRulePo> getAvailabilityInfo(String appLevelObj,String dataTypeObj,String ruleType) {
		return autoSeDAOImpl.findAvailabilityInfo(appLevelObj,dataTypeObj,ruleType);
	}

	@Override
	public List<String> getDeviceIPs(String deviceId) {
		List<String> ip_list = Lists.newArrayList();
		IIpAllocToDeviceNewService ipAllocToDeviceImpl = (IIpAllocToDeviceNewService) WebApplicationManager.getBean("ipAllocToDeviceImpl");
		ICmVmDAO cmVmDAO = (ICmVmDAO) WebApplicationManager.getBean("cmVmDAO");
		IRmVmManageServerDAO rmVmManageServerDAO = (IRmVmManageServerDAO) WebApplicationManager.getBean("rmVmManageServerDAO");
		try {
			CmVmVo vm = cmVmDAO.findPlatformTypeAndVmTypeByVmId(deviceId);
//			if(vm != null){
//				RmPlatformPo platform = rmVmManageServerDAO.getPlatformInfoById(vm.getPlatformType());
//				RmVirtualTypePo rmVirtualType = cmVmDAO.findObjectByID("selectRmVirtualTypePoById",vm.getVmType());
//				String netTypeVp = platform.getPlatformCode() + rmVirtualType.getVirtualTypeCode() + "VP";
//				List<String> deviceList = new ArrayList<String> ();
//				deviceList.add(deviceId);
//				List<DeviceNetIP> deviceNetIPs = ipAllocToDeviceImpl.qryAllocedIP(deviceList);
//				List<NetIPInfo> vpIp = deviceNetIPs.get(0).getNetIPs().get(netTypeVp);
//				if (vpIp != null && vpIp.size() > 0) {
//					ip_list.add(vpIp.get(0).getIp());
//				}
//			}
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return ip_list;
	}

	@Override
	public String getSeatCodeByDeviceId(String deviceId, String deviceType) {
		List<CmDevicePo> list = autoSeDAOImpl.findSeatIdByDeviceId(deviceId);
		if(list.isEmpty()||list.size()==0)
			return null;
		else
			return list.get(0).getSeatId();
	}

	@Override
	public List<CmDevicePo> getDeviceByChildPoolId(String childPoolId) {
		return autoSeDAOImpl.findDeviceByChildPoolId(childPoolId);
	}

	@Override
	public CmSeatPo getCmSeat(String seatId) {
		List<CmSeatPo> list = Lists.newArrayList();
		try {
			list = autoSeDAOImpl.findByID("findSeatById", seatId);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		if(list.isEmpty()||list.size()==0)
			return null;
		else
			return list.get(0);
	}

	@Override
	public Map<String, Object> getAppAndDuIdBySrIdRrInfoId(String srId,
			String rrinfoId) {
		Map<String,String> params = Maps.newHashMap();
		params.put("srId", srId);
		params.put("rrinfoId", rrinfoId);
		System.out.println("srId"+ srId);
		System.out.println("rrinfoId"+ rrinfoId);
		try {
			List<Map<String,Object>> list = autoSeDAOImpl.findAppDuInfoBySrRrinfoId(params);
			if(list.isEmpty()||list.size()<1){
				return null;
			}else{
				return list.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return null;
	}
	public Map<String,String> getSeTypeAttrByRrinfoId(String rrinfo_id,String data_app_type) throws Exception{

		Map<String,String> map = Maps.newHashMap();
		Map<String,String> params = Maps.newHashMap();
		params.put("rrinfoId", rrinfo_id);
		List<Map<String,Object>> list = autoSeDAOImpl.findHAPlatTypeInfoBySrRrinfoId(params);
		
		Map<String,Object> sr_type = list.get(0);
		String cluster_type = "";
		if(data_app_type.equals("DB")){
			cluster_type = String.valueOf(sr_type.get("HA_TYPE_CODE"));
			if(cluster_type.equals("NULL")){
				cluster_type = "SINGLE";
			}
		}
		map.put("CLUSTER_TYPE", cluster_type);
		
		String platform_type = (String)sr_type.get("PLATFORM_TYPE_CODE");
		String storage_system_type = "";
		if(StringUtils.isNotBlank(platform_type)){
			if(platform_type.equals("X")){
				storage_system_type = "LINUX";
			}else if(platform_type.equals("H")){
				storage_system_type = "HP";
			}else if(platform_type.equals("P")){
				storage_system_type = "AIX";
			}
		}else{
			throw new Exception("获取OS_NAME参数错误！");
		}
		map.put("CLOUD_SERVICE_TYPE", "VM_INSTALL");
		map.put("OS_NAME",storage_system_type);
		map.put("DATA_APP_TYPE",data_app_type);
		//map.put("NAS_AVAILABLE_RATE_THRESHOLD", "30.0");
		//根据CLUSTER_TYPE 和 OS_NAME,DATA_APP_TYPE
		//获取指定类型的默认参数信息
		
		List<Map<String,Object>> list_setype = autoSeDAOImpl.findSeTypeInfo(map);
		for(Map<String,Object> m : list_setype){
			map.put(m.get("PARAM_KEY").toString(), m.get("PARAM_VALUE").toString());
		}
		return map;
	}

	@Override
	public Map<String, String> getDuInfo(String rrinfoId) throws Exception {
		Map<String,String> params = Maps.newHashMap();
		params.put("rrinfoId", rrinfoId);
		List<Map<String,Object>> list = autoSeDAOImpl.findForList("findDuInfo", params);
		
		Map<String, ?> duMap = list.get(0);
		if (duMap == null || duMap.get("DU_TYPE_CODE") == null) {
			throw new Exception("Not found du info.");
		}
		String serverFunction = duMap.get("DU_TYPE_CODE").toString();
		String duName = duMap.get("DU_NAME").toString();// 从字典表中读取编码
		
		Map<String, String> duInfoMap = Maps.newHashMap();
		duInfoMap.put("PROJECTABBR", duName);
		duInfoMap.put("SERVERFUNCTION", serverFunction);
		
		return duInfoMap;
	}

	@Override
	public String getApplicationLevel(String rrinfoId) throws Exception {
		Map<String,String> params = Maps.newHashMap();
		params.put("rrinfoId", rrinfoId);
		List<Map<String,Object>> list = autoSeDAOImpl.findForList("findApplicationLevel", params);
		Map<String,Object> map = list.get(0);
		
		return map.get("APP_LEVEL_CODESYS_LEVEL_CODE").toString();
	}

	@Override
	public long findSuIdByAppIdAndDutype(long appId, String duType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Long> findSuIdsByAppIdAndDutype(long appId, String duType) {
		
		return null;
	}

	@Override
	public List<StorageSuPo> getStorageSuByPoolId(String poolId) {
		return this.storageDAOImpl.findStorageSuByPoolId(poolId);
	}

	public List<NasInfoVo> getUsedNasDeviceSN() {
		return null;//autoSeDAOImpl.getUsedNasDeviceSN();
	}
}
