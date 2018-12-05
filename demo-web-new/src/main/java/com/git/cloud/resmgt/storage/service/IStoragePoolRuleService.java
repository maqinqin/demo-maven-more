package com.git.cloud.resmgt.storage.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.policy.model.po.RmVmParamPo;
import com.git.cloud.resmgt.storage.model.po.SePoolLevelRulePo;
import com.git.cloud.resmgt.storage.model.vo.SePoolRuleCellVo;

public interface IStoragePoolRuleService {
	
	
	public Map<String ,List<SePoolLevelRulePo>> getSePoolRuleByType(String ruleType);
	
	public void saveSePoolRule(List<SePoolRuleCellVo> sePoolRuleVoList,String ruleType) throws Exception;
}
