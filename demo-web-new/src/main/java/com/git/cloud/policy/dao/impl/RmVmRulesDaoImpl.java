package com.git.cloud.policy.dao.impl;

import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.policy.dao.IRmVmRulesDao;
import com.git.cloud.policy.model.po.RmVmRulesPo;
import com.git.cloud.policy.model.vo.RmVmRulesVo;

/**
 * @Title 		RmVmRulesDaoImpl.java 
 * @Package 	com.git.cloud.policy.dao.impl 
 * @author 		yangzhenhai
 * @date 		2014-9-16上午10:04:47
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmVmRulesDaoImpl extends CommonDAOImpl implements IRmVmRulesDao {
	@Override
	public void saveRmVmRulesPo(RmVmRulesPo rmVmRulesPo) throws RollbackableBizException {

		rmVmRulesPo.setRulesId(UUIDGenerator.getUUID());
		rmVmRulesPo.setIsActive(IsActiveEnum.YES.getValue());
		super.save("saveRmVmRulesPo", rmVmRulesPo);
	}

	@Override
	public void updateRmVmRulesPo(RmVmRulesPo rmVmRulesPo) throws RollbackableBizException {

		super.save("updateRmVmRulesPo", rmVmRulesPo);
	}

	@Override
	public RmVmRulesPo findRmVmRulesPoById(String id) throws RollbackableBizException {

		return super.findObjectByID("findRmVmRulesPoById", id);
	}

	@Override
	public Pagination<RmVmRulesVo> queryRmVmRulesPoPagination(
			PaginationParam pagination) {

		return super.pageQuery("findRmVmRulesPoCount", "findRmVmRulesPoList", pagination);
	}

	@Override
	public void deleteRmVmRulesPoById(String id) throws RollbackableBizException {
		
		super.deleteForIsActive("deleteRmVmRulesPoById", id);
	}

	@Override
	public List<RmVmRulesPo> findAllRmvmRulesPo() throws RollbackableBizException {

		return super.findAll("findRmVmRulesPos");
	}

}
