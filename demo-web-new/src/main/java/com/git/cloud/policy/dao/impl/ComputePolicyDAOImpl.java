package com.git.cloud.policy.dao.impl;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.policy.dao.IComputePolicyDAO;
import com.git.cloud.policy.model.vo.PolicyInfoVo;
import com.git.cloud.policy.model.vo.RequsetResInfoVo;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-23
 */
public class ComputePolicyDAOImpl extends CommonDAOImpl implements
		IComputePolicyDAO {

	/* (non-Javadoc)
	 * <p>Title:findHostResPoolsBybmSrRrinfoPo</p>
	 * <p>Description:</p>
	 * @param map
	 * @return
	 * @see com.git.cloud.policy.dao.IComputePolicyDAO#findHostResPoolsBybmSrRrinfoPo(java.util.Map)
	 */
	@Override
	public List<PolicyInfoVo> findHostResPoolsBybmSrRrinfoPo(
			Map<String, Object> map) {
		return super.findListByParam("findHostResPoolsBybmSrRrinfoPo", map);
	}

	/* (non-Javadoc)
	 * <p>Title:findResByReqRes</p>
	 * <p>Description:</p>
	 * @param requsetResInfoVo
	 * @return
	 * @see com.git.cloud.policy.dao.IComputePolicyDAO#findResByReqRes(com.git.cloud.policy.model.vo.RequsetResInfoVo)
	 */
	@Override
	public List<PolicyInfoVo> findResByReqRes(RequsetResInfoVo requsetResInfoVo) throws RollbackableBizException {


		return super.findListByParam("findResByReqRes", requsetResInfoVo);
	}

	@Override
	public List<PolicyInfoVo> findHostResPoolsForHost(Map<String, Object> map) {
		return super.findListByParam("findHostResPoolsForHost", map);
	}

	@Override
	public List<PolicyInfoVo> findHostResPoolsBybmSrRrinfo(Map<String, Object> map) {
		return super.findListByParam("findHostResPoolsBybmSrRrinfo", map);
	}

	@Override
	public List<PolicyInfoVo> findHostResPoolsForHostByMap(Map<String, Object> map) {
		return super.findListByParam("findHostResPoolsForHostByMap", map);
	}

}
