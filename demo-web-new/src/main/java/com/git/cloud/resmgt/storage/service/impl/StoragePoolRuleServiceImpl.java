package com.git.cloud.resmgt.storage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.resmgt.storage.dao.ISePoolLevelRuleDAO;
import com.git.cloud.resmgt.storage.model.po.SePoolLevelRulePo;
import com.git.cloud.resmgt.storage.model.vo.SePoolRuleCellVo;
import com.git.cloud.resmgt.storage.service.IStoragePoolRuleService;

/**
 * @ClassName:StoragePoolRuleServiceImpl
 * @Description:TODO
 * @author chengbin
 * @date 2014-9-18 上午11:32:05
 * 
 * 
 */
public class StoragePoolRuleServiceImpl implements IStoragePoolRuleService {
	private ISePoolLevelRuleDAO sePoolRuleDao;

	public ISePoolLevelRuleDAO getSePoolRuleDao() {
		return sePoolRuleDao;
	}

	public void setSePoolRuleDao(ISePoolLevelRuleDAO sePoolRuleDao) {
		this.sePoolRuleDao = sePoolRuleDao;
	}

	/**
	 * @Title: getSePoolRuleByType
	 * @Description: get storage resource pool service-level rule info by
	 *               ruleType
	 * @field: @param ruleType
	 * @field: @return
	 * @return List<SePoolLevelRulePo>
	 * @throws
	 */
	public Map<String, List<SePoolLevelRulePo>> getSePoolRuleByType(
			String ruleType) {
		List<SePoolLevelRulePo> _ruleList = sePoolRuleDao
				.getPoolRuleByRuleType(ruleType);
		if (ruleType.equals("AVAILABILITY_TYPE")) {
			return getAvailabilityInfo(_ruleList);
		}else if(ruleType.equals("PERFORMANCE_TYPE")){
			return getPerformanceInfo(_ruleList);
		}else if(ruleType.equals("NAS_SERVICE_LEVEL")){
			return getNasServiceLevelInfo(_ruleList);
		}
		return null;
	}

	/**
	 * @Title: getAvailabilityInfo
	 * @Description: 获取可用性级别页面所需数据
	 * @field: @param list
	 * @field: @return
	 * @return Map<String,List<SePoolLevelRulePo>>
	 * @throws
	 */
	public Map<String, List<SePoolLevelRulePo>> getAvailabilityInfo(
			List<SePoolLevelRulePo> list) {

		List<SePoolLevelRulePo> ruleList_A = new ArrayList<SePoolLevelRulePo>();
		List<SePoolLevelRulePo> ruleList_a = new ArrayList<SePoolLevelRulePo>();
		List<SePoolLevelRulePo> ruleList_b = new ArrayList<SePoolLevelRulePo>();
		List<SePoolLevelRulePo> ruleList_c = new ArrayList<SePoolLevelRulePo>();
		for (SePoolLevelRulePo po : list) {
			if (po.getConditionOne().equals("A+")) {
				ruleList_A.add(po);
			} else if (po.getConditionOne().equals("A")) {
				ruleList_a.add(po);
			} else if (po.getConditionOne().equals("B")) {
				ruleList_b.add(po);
			} else {
				ruleList_c.add(po);
			}
		}
		Map<String, List<SePoolLevelRulePo>> map = new HashMap<String, List<SePoolLevelRulePo>>();
		map.put("row1", ruleList_A);
		map.put("row2", ruleList_a);
		map.put("row3", ruleList_b);
		map.put("row4", ruleList_c);
		return map;
	}

	public Map<String, List<SePoolLevelRulePo>> getPerformanceInfo(
			List<SePoolLevelRulePo> list) {
		List<SePoolLevelRulePo> ruleList_rspTime = new ArrayList<SePoolLevelRulePo>();
		List<SePoolLevelRulePo> ruleList_iops = new ArrayList<SePoolLevelRulePo>();
		List<SePoolLevelRulePo> ruleList_mbps = new ArrayList<SePoolLevelRulePo>();
		
		for (SePoolLevelRulePo po : list) {
			if(po.getConditionOne().equals("RESPONSE_TIME")){
				ruleList_rspTime.add(po);
			}else if(po.getConditionOne().equals("MBPS")){
				ruleList_iops.add(po);
			}else if(po.getConditionOne().equals("IOPS")){
				ruleList_mbps.add(po);
			}
		}
		Map<String, List<SePoolLevelRulePo>> map = new HashMap<String, List<SePoolLevelRulePo>>();
		map.put("row1", ruleList_rspTime);
		map.put("row2", ruleList_iops);
		map.put("row3", ruleList_mbps);
		return map;
	}
	
	public Map<String, List<SePoolLevelRulePo>> getNasServiceLevelInfo(
			List<SePoolLevelRulePo> list) {
		List<SePoolLevelRulePo> platinum = new ArrayList<SePoolLevelRulePo>();
		List<SePoolLevelRulePo> glod = new ArrayList<SePoolLevelRulePo>();
		List<SePoolLevelRulePo> silver = new ArrayList<SePoolLevelRulePo>();
		for (SePoolLevelRulePo po : list) {
			if(po.getCellValue().equals("PLATINUM")){
				platinum.add(po);
			}else if(po.getCellValue().equals("GLOD")){
				glod.add(po);
			}else if(po.getCellValue().equals("SILVER")){
				silver.add(po);
			}
		}
		Map<String, List<SePoolLevelRulePo>> map = new HashMap<String, List<SePoolLevelRulePo>>();
		map.put("row1", platinum);
		map.put("row2", glod);
		map.put("row3", silver);
		return map;
	}
	/**
	 * @Title: getSePoolRuleByType
	 * @Description: 保存规则数据
	 * @field: @param sePoolRuleVoList
	 * @field: @param ruleType
	 * @field: @return
	 * @return void
	 * @throws
	 */
	@Override
	public void saveSePoolRule(List<SePoolRuleCellVo> sePoolRuleVoList,
			String ruleType) throws Exception {
		List<SePoolLevelRulePo> list = new ArrayList<SePoolLevelRulePo>();
		Map<String,SePoolLevelRulePo> map = new HashMap<String ,SePoolLevelRulePo>();
		if(ruleType.equals("NAS_SERVICE_LEVEL")){
			for(SePoolRuleCellVo vo :sePoolRuleVoList){
				if(vo.getCellId().contains("undefined"))
					continue;
				String[] cell_array = vo.getCellId().split("_");
				String cellId = cell_array[0];
				String condition = cell_array[1];
				if(map.containsKey(cellId)){
					SePoolLevelRulePo po = map.get(cellId);
					if(condition.equals("CONDITIONONE")){
						po.setConditionOne(vo.getCellValue());
					}else if(condition.equals("CONDITIONTWO")){
						po.setConditionTwo(vo.getCellValue());
					}
				}else {
					SePoolLevelRulePo po = new SePoolLevelRulePo();
					po.setCellId(cellId);
					if(condition.equals("CONDITIONONE")){
						po.setConditionOne(vo.getCellValue());
					}else if(condition.equals("CONDITIONTWO")){
						po.setConditionTwo(vo.getCellValue());
					}
					map.put(cellId, po);
				}
			}
			for(String key:map.keySet()){
				list.add(map.get(key));
			}
			sePoolRuleDao.savePoolRuleNasValues(list, ruleType);
		}else{
			sePoolRuleDao.savePoolRuleValues(sePoolRuleVoList, ruleType);
		}

	}

}
