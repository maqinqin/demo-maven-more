package com.git.cloud.policy.service;

import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.policy.model.po.RmVmRulesPo;
import com.git.cloud.policy.model.vo.RmVmRulesVo;

/**
 * @Title 		IRmVmRulesService.java 
 * @Package 	com.git.cloud.policy.service 
 * @author 		yangzhenhai
 * @date 		2014-9-16上午9:57:24
 * @version 	1.0.0
 * @Description 
 *
 */
public interface IRmVmRulesService {
	
	public void saveRmVmRulesPo(RmVmRulesPo rmVmRulesPo) throws RollbackableBizException;
	
	public void updateRmVmRulesPo(RmVmRulesPo rmVmRulesPo) throws RollbackableBizException;
	
	public RmVmRulesPo findRmVmRulesPoById(String id) throws RollbackableBizException;
	
	public Pagination<RmVmRulesVo> queryRmVmRulesPoPagination(PaginationParam pagination);
	
	public void deleteRmVmRulesPoById(String[] ids) throws RollbackableBizException;
	
	public Map<String ,Boolean> findAllRmvmRulesPo() throws RollbackableBizException;
}
