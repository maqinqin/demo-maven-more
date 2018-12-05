package com.git.cloud.policy.dao.impl;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.policy.dao.IRmVmParamDao;
import com.git.cloud.policy.model.po.RmVmParamPo;
import com.git.cloud.policy.model.vo.RmVmParamVo;

/**
 * @Title 		RmVmParamDaoImpl.java 
 * @Package 	com.git.cloud.policy.dao.impl 
 * @author 		yangzhenhai
 * @date 		2014-9-11下午4:39:53
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmVmParamDaoImpl extends CommonDAOImpl implements IRmVmParamDao {

	@Override
	public String findParamValue(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<String> values = getSqlMapClientTemplate().queryForList("findParamValue", map);
		if(values != null && values.size()>0){
			return values.get(0);
		}
		return null;
	}
	
	@Override
	public  void saveRmVmParamPo(RmVmParamPo rmVmParamPo) throws RollbackableBizException {
		super.save("saveRmVmParamPo", rmVmParamPo);
	}

	@Override
	public void updateRmVmParamPo(RmVmParamPo rmVmParamPo) throws RollbackableBizException {
		super.update("updateRmVmParamPo", rmVmParamPo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RmVmParamPo findRmVmParamPoById(String id) throws RollbackableBizException {

		RmVmParamPo rmVmParamPo = super.findObjectByID("findRmVmParamPoById", id);
		
		return rmVmParamPo;
	}

	@Override
	public void deleteRmVmParamPoById(String id) throws RollbackableBizException {
		
		super.deleteForIsActive("deleteRmVmParamPoById", id);
	}

	@Override
	public Pagination<RmVmParamVo> queryRmVmParamPoPagination(
			PaginationParam pagination) {
		
		return super.pageQuery( "findRmVmParamPoCount","findRmVmParamPoList", pagination);
	}

	@Override
	public Integer findRmVmParamPosByParam(RmVmParamPo rmVmParamPo)
			throws RollbackableBizException {

		return (Integer) getSqlMapClientTemplate().queryForObject("findRmVmParamPosByParam", rmVmParamPo);
	}

	/* (non-Javadoc)
	 * <p>Title:queryPoolList</p>
	 * <p>Description:</p>
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.policy.dao.IRmVmParamDao#queryPoolList()
	 */
	@Override
	public List<Map<String, Object>> queryPoolList() throws RollbackableBizException {
		return findForList("queryPools");
	}

}
