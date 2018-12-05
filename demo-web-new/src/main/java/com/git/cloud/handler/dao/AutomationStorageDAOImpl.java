package com.git.cloud.handler.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.handler.vo.NasInfoVo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.storage.model.po.SePoolLevelRulePo;
import com.google.common.collect.Maps;


public class AutomationStorageDAOImpl extends CommonDAOImpl implements
		AutomationStorageDAO {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String findStorageModelBySN(String sn) {
		return this.getSqlMapClientTemplate().queryForObject("findStorageModelBySN", sn).toString();
		
	}

	@Override
	public String findResPoolLevel(String availability_level,
			String performance_level, String ruleType) {
		Map<String,String> map = Maps.newHashMap();
		map.put("availability_level", availability_level);
		map.put("performance_level", performance_level);
		map.put("ruleType", ruleType);
		return this.getSqlMapClientTemplate().queryForObject("findResPoolLevel", map).toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SePoolLevelRulePo> findPerformanceInfo(String performanceType,
			String ruleType) {

		Map<String,String> map = Maps.newHashMap();
		map.put("conditionOne", performanceType);
		map.put("ruleType", ruleType);
		return this.getSqlMapClientTemplate().queryForList("findPerformanceInfo",map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SePoolLevelRulePo> findAvailabilityInfo(String appLevelObj,
			String dataTypeObj, String ruleType) {
		Map<String,Object> params = Maps.newHashMap();
		params.put("conditionOne", appLevelObj);
		params.put("conditionTwo", dataTypeObj);
		params.put("ruleType", ruleType);
		return this.getSqlMapClientTemplate().queryForList("findAvailabilityInfo",params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CmDevicePo> findDeviceByChildPoolId(String childPoolId) {
		return this.getSqlMapClientTemplate().queryForList("findDeviceByChildPoolId",childPoolId);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CmDevicePo> findSeatIdByDeviceId(String deviceId) {
		return this.getSqlMapClientTemplate().queryForList("findSeatIdByDeviceId",deviceId);
	}

	@Override
	public List<Map<String,Object>> findAppDuInfoBySrRrinfoId(Map<String,String> params){
		try {
			return super.findForList("findAppAndDuIdBySrIdRrInfoId", params);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findHAPlatTypeInfoBySrRrinfoId(
			Map<String, String> params) {
		try {
			return super.findForList("findHAPlatTypeInfoBySrRrinfoId", params);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findSeTypeInfo(Map<String, String> params) {
		try {
			return super.findForList("findSeTypeInfo", params);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return null;
	}
	
	public NasInfoVo getNASMgrLoginInfo(String nasSn) {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("nasSn", nasSn);
		List<NasInfoVo> nasList = this.findListByParam("se.getNASMgrLoginInfo", paramMap);
		NasInfoVo nasInfo = null;
		if(nasList != null && nasList.size() > 0) {
			nasInfo = nasList.get(0);
		}
		return nasInfo;
	}
}
