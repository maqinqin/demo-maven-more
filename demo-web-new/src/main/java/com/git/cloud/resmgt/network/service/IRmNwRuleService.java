package com.git.cloud.resmgt.network.service;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.network.model.po.RmNwRuleListPo;
import com.git.cloud.resmgt.network.model.po.RmNwRulePo;

public interface IRmNwRuleService {

	public Object getRmNwRulePagination(PaginationParam paginationParam);

	public void saveRmNwRule(RmNwRulePo rmNwRulePo) throws RollbackableBizException;

	public RmNwRulePo selectRmNwRuleById(String rmNwRuleId) throws RollbackableBizException;

	public void updateRmNwRule(RmNwRulePo rmNwRulePo) throws RollbackableBizException;

	public String deleteRmNwRule(String[] split) throws RollbackableBizException;

	public Object getRmNwRuleListPagination(PaginationParam paginationParam);

	public void saveRmNwRuleList(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException;

	public RmNwRuleListPo selectRmNwRuleListById(String rmNwRuleListId) throws RollbackableBizException;

	public void updateRmNwRuleList(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException;

	public void deleteRmNwRuleList(String rmNwRuleListId) throws RollbackableBizException;

	public RmNwRuleListPo selectRmNwRuleListByUseCode(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException;

	public RmNwRulePo selectRmNwRuleByRuleName(String platFormId, String virtualTypeId, String hostTypeId,String haType) throws RollbackableBizException;
	
	public String isDeleteRmNwRule(String[] split) throws RollbackableBizException;

}
