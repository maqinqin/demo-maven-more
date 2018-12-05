package com.git.cloud.policy.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.policy.model.po.RmVmRulesPo;
import com.git.cloud.policy.model.vo.RmVmRulesVo;

/**
 * @Title 		IRmVmRulesDao.java 
 * @Package 	com.git.cloud.policy.dao 
 * @author 		yangzhenhai
 * @date 		2014-9-16上午10:04:02
 * @version 	1.0.0
 * @Description 
 *
 */
public interface IRmVmRulesDao extends ICommonDAO {
	
	public void saveRmVmRulesPo(RmVmRulesPo rmVmRulesPo) throws RollbackableBizException;
	
	public void updateRmVmRulesPo(RmVmRulesPo rmVmRulesPo) throws RollbackableBizException;
	
	public RmVmRulesPo findRmVmRulesPoById(String id) throws RollbackableBizException;
	
	public Pagination<RmVmRulesVo> queryRmVmRulesPoPagination(PaginationParam pagination);
	
	public void deleteRmVmRulesPoById(String id) throws RollbackableBizException;
	
	public List<RmVmRulesPo> findAllRmvmRulesPo() throws RollbackableBizException;
}
