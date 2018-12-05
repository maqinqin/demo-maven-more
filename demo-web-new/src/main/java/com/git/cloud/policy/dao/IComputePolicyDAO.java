package com.git.cloud.policy.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.policy.model.vo.PolicyInfoVo;
import com.git.cloud.policy.model.vo.RequsetResInfoVo;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-23
 */
public interface IComputePolicyDAO extends ICommonDAO {

	public List<PolicyInfoVo> findHostResPoolsBybmSrRrinfoPo(Map<String, Object> map);
	/**
	 * 不通过服务器角色id，直接查询可用物理机资源信息
	 * @param map
	 * @return
	 */
	public List<PolicyInfoVo> findHostResPoolsBybmSrRrinfo(Map<String, Object> map);
	public List<PolicyInfoVo> findResByReqRes(RequsetResInfoVo requsetResInfoVo) throws RollbackableBizException;
	public List<PolicyInfoVo> findHostResPoolsForHost(Map<String, Object> map);
	public List<PolicyInfoVo> findHostResPoolsForHostByMap(Map<String, Object> map);
}
