package com.git.cloud.resmgt.network.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.network.model.po.RmNwRuleListPo;
import com.git.cloud.resmgt.network.model.po.RmNwRulePo;

public interface IRmNwRuleDAO extends ICommonDAO{

	public void saveRmNwRule(RmNwRulePo rmNwRulePo) throws RollbackableBizException;

	public RmNwRulePo selectRmNwRuleById(String rmNwRuleId) throws RollbackableBizException;

	public void updateRmNwRule(RmNwRulePo rmNwRulePo) throws RollbackableBizException;

	public List<RmNwRuleListPo> selectRmNwRuleListByrmNwRuleId(String id) throws RollbackableBizException;

	public void deleteRmNwRule(String id) throws RollbackableBizException;

	public void saveRmNwRuleList(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException;

	public RmNwRuleListPo selectRmNwRuleListById(String rmNwRuleListId) throws RollbackableBizException;

	public void updateRmNwRuleList(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException;

	public void deleteRmNwRuleList(String rmNwRuleListId) throws RollbackableBizException;

	public RmNwRuleListPo selectRmNwRuleListByUseCode(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException;

	public RmNwRulePo selectRmNwRuleByRuleName(String platFormId, String virtualTypeId, String hostTypeId,String haType) throws RollbackableBizException;

}
