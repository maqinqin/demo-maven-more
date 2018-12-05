package com.git.cloud.handler.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.handler.automation.se.po.StorageSuPo;
import com.git.cloud.handler.vo.NasInfoVo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmSeatPo;
import com.git.cloud.resmgt.storage.model.po.SePoolLevelRulePo;

public interface StorageService {
	
	/**
	 * @Title: getNASMgrLoginInfo
	 * @Description: 获取NAS管理机登陆信息
	 * @field: @return
	 * @return Map<String,String>
	 * @throws
	 */
	public Map<String,String> getNASMgrLoginInfo(String nasSn);
	
	/**
	 * @Title: getStorageModelBySN
	 * @Description: 根据存储sn信息获取存储型号
	 * @field: @param sn
	 * @field: @return
	 * @return String
	 * @throws
	 */
	public String getStorageModelBySN(String sn);
	
	/**
	 * @Title: getNasProcIPsBySN
	 * @Description: 根据存储SN信息获取存储生成ip
	 * @field: @param sn
	 * @field: @return
	 * @return List<String>
	 * @throws
	 */
	public List<String> getNasProcIPsBySN(String sn);

	/**
	 * @Title: getResPoolLevel
	 * @Description: 根据可用性，性能参数以及规则类型获取存储资源池服务级别
	 * @field: @param availability_level
	 * @field: @param perf_level
	 * @field: @param rule_type
	 * @field: @return
	 * @return String
	 * @throws
	 */
	public String getResPoolLevel(String availability_level, String perf_level,
			String rule_type);

	/**
	 * @Title: getPerformanceInfo
	 * @Description: 获取性能级别参数
	 * @field: @param performanceType
	 * @field: @param ruleType
	 * @field: @return
	 * @return List<SePoolLevelRulePo>
	 * @throws
	 */
	public List<SePoolLevelRulePo> getPerformanceInfo(String performanceType,
			String ruleType);

	/**
	 * @Title: getAvailabilityInfo
	 * @Description: 获取可用性级别参数
	 * @field: @param appLevelObj
	 * @field: @param dataTypeObj
	 * @field: @param availabilityType
	 * @field: @return
	 * @return List<SePoolLevelRulePo>
	 * @throws
	 */
	public List<SePoolLevelRulePo> getAvailabilityInfo(String appLevelObj,
			String dataTypeObj, String availabilityType);

	/**
	 * @Title: getDeviceIPs
	 * @Description: 获取设备ip地址
	 * @field: @param os_name
	 * @field: @param dev_type
	 * @field: @param pm_type
	 * @field: @param deviceId
	 * @field: @return
	 * @return List<String>
	 * @throws
	 */
	public List<String> getDeviceIPs(String deviceId);
	
	/**
	 * @Title: getSeatCodeByDeviceId
	 * @Description: 获取设备位置信息
	 * @field: @param deviceId
	 * @field: @param deviceType 物理机HM/虚拟机VM
	 * @field: @return
	 * @return String
	 * @throws
	 */
	public String getSeatCodeByDeviceId(String deviceId,String deviceType);
	
	
	/**
	 * @Title: getSeSNByChildPoolId
	 * @Description: 根据资源子池ID获取存储SN和位置信息
	 * @field: @param childPoolId
	 * @field: @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	public List<CmDevicePo> getDeviceByChildPoolId(String childPoolId);
	
	
	/**
	 * @Title: getParentSeatCode
	 * @Description: 根据seatCode获取parentCode
	 * @field: @param seatCode
	 * @field: @return
	 * @return String
	 * @throws
	 */
	public CmSeatPo getCmSeat(String seatId);
	
	
	/**
	 * @Title: getAppAndDuIdBySrIdRrInfoId
	 * @Description: TODO
	 * @field: @param srId
	 * @field: @param rrinfoId
	 * @field: @return
	 * @return Map<String,String>
	 * @throws
	 */
	public Map<String,Object> getAppAndDuIdBySrIdRrInfoId(String srId,String rrinfoId);

	public Map<String,String> getSeTypeAttrByRrinfoId(String rrinfo_id,String data_app_type) throws Exception;

	public Map<String, String> getDuInfo(String rrinfoId) throws Exception;

	public String getApplicationLevel(String rrinfoId) throws Exception;
	
	public long findSuIdByAppIdAndDutype(long appId, String duType);
	
	public List<Long> findSuIdsByAppIdAndDutype(long appId, String duType);
	
	public List<StorageSuPo> getStorageSuByPoolId(String poolId);
	
	/**
	 * 获取可用的NAS设备信息
	 * @return
	 */
	public List<NasInfoVo> getUsedNasDeviceSN();
}
