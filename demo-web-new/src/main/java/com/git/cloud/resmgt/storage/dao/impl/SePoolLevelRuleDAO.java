package com.git.cloud.resmgt.storage.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;

import com.git.cloud.resmgt.storage.dao.ISePoolLevelRuleDAO;
import com.git.cloud.resmgt.storage.model.po.SePoolLevelRulePo;
import com.git.cloud.resmgt.storage.model.vo.SePoolRuleCellVo;

/**
 * @ClassName:SePoolLevelRuleDAO
 * @Description:TODO
 * @author chengbin
 * @date 2014-9-12 上午10:55:37
 * 
 * 
 */
public class SePoolLevelRuleDAO  extends CommonDAOImpl implements
		ISePoolLevelRuleDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<SePoolLevelRulePo> getPoolRuleByRuleType(String ruleType) {
		return getSqlMapClientTemplate().queryForList("selectSePoolRule",
				ruleType);

	}


	public void savePoolRuleValues(List<SePoolRuleCellVo> sePoolRuleCellVoList,
			String ruleType) throws SQLException {
		getSqlMapClientTemplate().getSqlMapClient().startBatch();
		for (SePoolRuleCellVo po : sePoolRuleCellVoList) {
			Map<String, Object> param_map = new HashMap<String, Object>();
			param_map.put("cellId", po.getCellId());
			param_map.put("cellValue", po.getCellValue());
			param_map.put("ruleType", ruleType);
			getSqlMapClientTemplate().update("updateSePoolRuleCell", param_map);
		}
		getSqlMapClientTemplate().getSqlMapClient().executeBatch();

	}


	@Override
	public void savePoolRuleNasValues(List<SePoolLevelRulePo> list,
			String ruleType) throws SQLException {

		getSqlMapClientTemplate().getSqlMapClient().startBatch();
		for (SePoolLevelRulePo po : list) {
			Map<String, Object> param_map = new HashMap<String, Object>();
			param_map.put("cellId", po.getCellId());
			param_map.put("conditionOne", po.getConditionOne());
			param_map.put("conditionTwo", po.getConditionTwo());
			param_map.put("ruleType", ruleType);
			getSqlMapClientTemplate().update("updateSePoolRuleCondition", param_map);
		}
		getSqlMapClientTemplate().getSqlMapClient().executeBatch();
		
	}

}
