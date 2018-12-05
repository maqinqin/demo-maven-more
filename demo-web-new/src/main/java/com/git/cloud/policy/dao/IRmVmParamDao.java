package com.git.cloud.policy.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.policy.model.po.RmVmParamPo;
import com.git.cloud.policy.model.vo.RmVmParamVo;

/**
 * @Title 		RmVmParamDao.java 
 * @Package 	com.git.cloud.policy.dao 
 * @author 		yangzhenhai
 * @date 		2014-9-11下午4:38:50
 * @version 	1.0.0
 * @Description 
 *
 */
public interface IRmVmParamDao extends ICommonDAO {
	public String findParamValue(Map<String, Object> map);
	
	public void saveRmVmParamPo(RmVmParamPo rmVmParamPo) throws RollbackableBizException;
	
	public void updateRmVmParamPo(RmVmParamPo rmVmParamPo) throws RollbackableBizException;
	
	public RmVmParamPo findRmVmParamPoById(String id) throws RollbackableBizException;
	
	public Pagination<RmVmParamVo> queryRmVmParamPoPagination(PaginationParam pagination);
	
	public void deleteRmVmParamPoById(String id) throws RollbackableBizException;
	
	public Integer findRmVmParamPosByParam(RmVmParamPo rmVmParamPo) throws RollbackableBizException;
	
	public List<Map<String, Object>> queryPoolList() throws RollbackableBizException;
}
