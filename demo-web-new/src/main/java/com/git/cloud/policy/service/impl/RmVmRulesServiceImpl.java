package com.git.cloud.policy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.model.SortTypeEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.policy.dao.IRmVmRulesDao;
import com.git.cloud.policy.model.po.RmVmRulesPo;
import com.git.cloud.policy.model.vo.RmVmRulesVo;
import com.git.cloud.policy.service.IRmVmRulesService;

/**
 * @Title 		RmVmRulesServiceImpl.java 
 * @Package 	com.git.cloud.policy.service.impl 
 * @author 		yangzhenhai
 * @date 		2014-9-16上午10:02:36
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmVmRulesServiceImpl implements IRmVmRulesService {

	private IRmVmRulesDao rmVmRulesDao;
	
	
	public IRmVmRulesDao getRmVmRulesDao() {
		return rmVmRulesDao;
	}

	public void setRmVmRulesDao(IRmVmRulesDao rmVmRulesDao) {
		this.rmVmRulesDao = rmVmRulesDao;
	}

	@Override
	public void saveRmVmRulesPo(RmVmRulesPo rmVmRulesPo) throws RollbackableBizException {

		rmVmRulesPo.setRulesId(UUIDGenerator.getUUID());
		rmVmRulesPo.setIsActive(IsActiveEnum.YES.getValue());
		rmVmRulesDao.saveRmVmRulesPo( rmVmRulesPo);
	}

	@Override
	public void updateRmVmRulesPo(RmVmRulesPo rmVmRulesPo) throws RollbackableBizException {

		rmVmRulesDao.updateRmVmRulesPo( rmVmRulesPo);
	}

	@Override
	public RmVmRulesPo findRmVmRulesPoById(String id) throws RollbackableBizException {

		return rmVmRulesDao.findRmVmRulesPoById( id);
	}

	@Override
	public Pagination<RmVmRulesVo> queryRmVmRulesPoPagination(
			PaginationParam pagination) {

		return rmVmRulesDao.queryRmVmRulesPoPagination( pagination);
	}

	@Override
	public void deleteRmVmRulesPoById(String[] ids) throws RollbackableBizException {
		
		for(String id:ids){
			rmVmRulesDao.deleteRmVmRulesPoById( id);
		}
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.policy.service.IRmVmRulesService#findAllRmvmRulesPo()
	 */
	@Override
	public Map<String, Boolean> findAllRmvmRulesPo()
			throws RollbackableBizException {
		List<RmVmRulesPo> rmVmRulesPos = rmVmRulesDao.findAllRmvmRulesPo();
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for(RmVmRulesPo po : rmVmRulesPos){
			map.put(po.getSortObject(), po.getSortType().equals(SortTypeEnum.ASC.getValue()));
		}
		return map;
	}
}
