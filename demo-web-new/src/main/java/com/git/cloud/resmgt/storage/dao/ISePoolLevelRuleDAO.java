package com.git.cloud.resmgt.storage.dao;

import java.sql.SQLException;
import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.resmgt.storage.model.po.SePoolLevelRulePo;
import com.git.cloud.resmgt.storage.model.vo.SePoolRuleCellVo;

public interface ISePoolLevelRuleDAO  extends ICommonDAO{
	
	public List<SePoolLevelRulePo> getPoolRuleByRuleType(String ruleType);
	public void savePoolRuleValues(List<SePoolRuleCellVo> list,String ruleType) throws SQLException ;
	
	public void savePoolRuleNasValues(List<SePoolLevelRulePo> list,String ruleType) throws SQLException ;
}
