package com.git.cloud.handler.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.handler.vo.NasInfoVo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.storage.model.po.SePoolLevelRulePo;

public interface AutomationStorageDAO extends ICommonDAO {

	public String findStorageModelBySN(String sn);
	
	public String findResPoolLevel(String availability_level,String performance_level,String ruleType);

	public List<SePoolLevelRulePo> findPerformanceInfo(String performanceType,
			String ruleType);

	public List<SePoolLevelRulePo> findAvailabilityInfo(String appLevelObj,
			String dataTypeObj, String ruleType);

	public List<CmDevicePo> findDeviceByChildPoolId(String childPoolId);

	public List<CmDevicePo> findSeatIdByDeviceId(String deviceId);
	public List<Map<String,Object>> findAppDuInfoBySrRrinfoId(Map<String,String> params);
	
	public List<Map<String, Object>> findHAPlatTypeInfoBySrRrinfoId(Map<String,String> params);

	public List<Map<String, Object>> findSeTypeInfo(Map<String,String> params);

	public NasInfoVo getNASMgrLoginInfo(String nasSn);
}
