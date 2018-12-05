package com.git.cloud.resmgt.network.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.network.dao.IRmNwRuleDAO;
import com.git.cloud.resmgt.network.model.po.RmNwRuleListPo;
import com.git.cloud.resmgt.network.model.po.RmNwRulePo;

public class RmNwRuleDAO extends CommonDAOImpl implements IRmNwRuleDAO{

	@Override
	public void saveRmNwRule(RmNwRulePo rmNwRulePo) throws RollbackableBizException {
         super.save("saveRmNwRule", rmNwRulePo);	
	}

	@Override
	public RmNwRulePo selectRmNwRuleById(String rmNwRuleId) throws RollbackableBizException {
		return super.findObjectByID("selectRmNwRuleById", rmNwRuleId);
	}

	@Override
	public void updateRmNwRule(RmNwRulePo rmNwRulePo) throws RollbackableBizException {
		
		super.update("updateRmNwRule", rmNwRulePo);
		
	}

	@Override
	public List<RmNwRuleListPo> selectRmNwRuleListByrmNwRuleId(String id) throws RollbackableBizException {
		return super.findByID("selectRmNwRuleListByrmNwRuleId", id);
	}

	@Override
	public void deleteRmNwRule(String id) throws RollbackableBizException {
       super.deleteForIsActive("deleteRmNwRule", id);		
	}

	@Override
	public void saveRmNwRuleList(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException {
		super.save("saveRmNwRuleList", rmNwRuleListPo);
		
	}

	@Override
	public RmNwRuleListPo selectRmNwRuleListById(String rmNwRuleListId) throws RollbackableBizException {
		return super.findObjectByID("selectRmNwRuleListById", rmNwRuleListId);
	}

	@Override
	public void updateRmNwRuleList(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException {
		super.update("updateRmNwRuleList", rmNwRuleListPo);
	}

	@Override
	public void deleteRmNwRuleList(String rmNwRuleListId) throws RollbackableBizException {
		super.deleteForIsActive("deleteRmNwRuleList", rmNwRuleListId);
	}

	@Override
	public RmNwRuleListPo selectRmNwRuleListByUseCode(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException {
		Map<String,String> map=new HashMap<String,String>();
		map.put("useCode", rmNwRuleListPo.getUseCode());
		map.put("rmNwRuleId", rmNwRuleListPo.getRmNwRuleId());
		return super.findObjectByMap("selectRmNwRuleListByUseCode", map);
	}

	@Override
	public RmNwRulePo selectRmNwRuleByRuleName(String platFormId, String virtualTypeId, String hostTypeId,String haType) throws RollbackableBizException {
		Map<String,String> map=new HashMap<String,String>();
		map.put("platFormId", platFormId);
		map.put("virtualTypeId", virtualTypeId);
		map.put("hostTypeId", hostTypeId);
		map.put("haType", haType);
		return super.findObjectByMap("selectRmNwRuleByRuleName", map);
	}

}
