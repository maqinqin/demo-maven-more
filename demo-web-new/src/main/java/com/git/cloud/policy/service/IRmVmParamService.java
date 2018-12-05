package com.git.cloud.policy.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.policy.model.po.RmVmParamPo;
import com.git.cloud.policy.model.vo.RmVmParamVo;

/**
 * @Title 		IRmVmParamService.java 
 * @Package 	com.git.cloud.policy.service 
 * @author 		yangzhenhai
 * @date 		2014-9-11下午4:41:02
 * @version 	1.0.0
 * @Description 
 *
 */
public interface IRmVmParamService {
	
	public void saveRmVmParamPo(RmVmParamPo rmVmParamPo) throws RollbackableBizException, Exception;
	
	public void updateRmVmParamPo(RmVmParamPo rmVmParamPo) throws RollbackableBizException, Exception;
	
	public RmVmParamPo findRmVmParamPoById(String id) throws RollbackableBizException;
	
	public Pagination<RmVmParamVo> queryRmVmParamPoPagination(PaginationParam pagination);
	
	public void deleteRmVmParamPoById(String[] ids) throws RollbackableBizException;

	public String findParamValue(String poolId, String cdpId, String paramType);
	
	public boolean checkRmVmParamPo(RmVmParamPo rmVmParamPo) throws RollbackableBizException;
	
	public List<Map<String, Object>> queryPoolList() throws RollbackableBizException;
	
}
