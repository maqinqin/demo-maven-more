package com.git.cloud.resmgt.storage.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.resmgt.storage.model.po.SePoolLevelRulePo;
import com.git.cloud.resmgt.storage.model.vo.SePoolRuleCellVo;
import com.git.cloud.resmgt.storage.service.IStoragePoolRuleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName:StoragePoolRuleAction
 * @Description: Storage resource pool service-level rule action
 * @author chengbin
 * @date 2014-9-15 上午10:20:51
 *
 *
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class StoragePoolRuleAction extends BaseAction {
	private IStoragePoolRuleService sePoolRuleService;
	private List<SePoolLevelRulePo> sePoolRulePoList;
	private String sePoolRuleCells;
	public String getSePoolRuleCells() {
		return sePoolRuleCells;
	}

	public void setSePoolRuleCells(String sePoolRuleCells) {
		this.sePoolRuleCells = sePoolRuleCells;
	}

	private String ruleType;


	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public List<SePoolLevelRulePo> getSePoolRulePoList() {
		return sePoolRulePoList;
	}

	public void setSePoolRulePoList(List<SePoolLevelRulePo> sePoolRulePoList) {
		this.sePoolRulePoList = sePoolRulePoList;
	}

	public IStoragePoolRuleService getSePoolRuleService() {
		return sePoolRuleService;
	}

	public void setSePoolRuleService(IStoragePoolRuleService sePoolRuleService) {
		this.sePoolRuleService = sePoolRuleService;
	}
	
	
	/**
	 * @Title: findSePoolRuleByType
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void findSePoolRuleByType() throws Exception{
		Map<String ,List<SePoolLevelRulePo>>  map = sePoolRuleService.getSePoolRuleByType(ruleType);
		arrayOut(map);
	}
	
	/**
	 * @Title: saveSePoolRuleCell
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void saveSePoolRuleCell() throws Exception{
		
		List<SePoolRuleCellVo> sePoolRuleVoList = new ArrayList<SePoolRuleCellVo>();
		JSONArray array = JSONArray.fromObject(sePoolRuleCells);
		JSONObject jsonObj = array.getJSONObject(0);
		for(Object key : jsonObj.keySet()){
			SePoolRuleCellVo vo = new SePoolRuleCellVo();
			vo.setCellId(String.valueOf(key));
			vo.setCellValue(String.valueOf(jsonObj.get(key)));
			sePoolRuleVoList.add(vo);
		}
		sePoolRuleService.saveSePoolRule(sePoolRuleVoList, ruleType);
	}
	
	
	public String init(){
		return SUCCESS;
	}
}
