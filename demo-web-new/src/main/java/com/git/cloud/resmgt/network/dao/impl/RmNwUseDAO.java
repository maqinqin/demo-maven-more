package com.git.cloud.resmgt.network.dao.impl;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.network.dao.IRmNwUseDAO;
import com.git.cloud.resmgt.network.model.po.RmNwUsePo;
import com.git.cloud.resmgt.network.model.po.RmNwUseRelPo;
import com.git.cloud.resmgt.network.model.vo.RmNwUseRelVo;
import com.git.cloud.resmgt.network.model.vo.RmNwUseVo;
/**
 * @Title 		RmNwUseDAO.java 
 * @Package 	com.git.cloud.resmgt.network.dao.impl
 * @author 		make
 * @date 		2015-3-6上午10:55:21
 * @version 	1.0.0
 * @Description  
 */
public class RmNwUseDAO extends CommonDAOImpl implements IRmNwUseDAO {
	@Override
	public Pagination<RmNwUseVo> queryRmNwUsePoPagination(
			PaginationParam pagination) {
		return super.pageQuery( "findRmNwUsePoCount","findRmNwUseList", pagination);
	}
	
	@Override
	public List<Map<String, Object>> queryUseNameList() throws RollbackableBizException {
		return findForList("queryUseNameList");
	}
	
	@Override
	public List<Map<String, Object>> queryDatacenterList() throws RollbackableBizException {
		return findForList("queryDatacenterList");
	}
	
	@Override
	public Integer findRmNwUsePosByUse(RmNwUsePo rmNwUsePo)
			throws RollbackableBizException {

		return (Integer) getSqlMapClientTemplate().queryForObject("findRmNwUsePosByUse", rmNwUsePo);
	}
	
	@Override
	public Integer findRmNwUseRelPosByUseRel(RmNwUseRelPo rmNwUseRelPo)
			throws RollbackableBizException {

		return (Integer) getSqlMapClientTemplate().queryForObject("findRmNwUseRelPosByUseRel", rmNwUseRelPo);
	}
	
	@Override
	public  void saveRmNwUsePo(RmNwUsePo rmNwUsePo) throws RollbackableBizException {
		super.save("saveRmNwUsePoAct", rmNwUsePo);
	}
	
	@Override
	public  void saveRmNwUseRelPo(RmNwUseRelPo rmNwUseRelPo) throws RollbackableBizException {
		super.save("saveRmNwUseRelPoAct", rmNwUseRelPo);
	}
	
	@Override
	public  void updateRmNwUsePo(RmNwUsePo rmNwUsePo) throws RollbackableBizException {
		super.save("updateRmNwUsePoAct", rmNwUsePo);
	}
	
	@Override
	public  void updateRmNwUseRelPo(RmNwUseRelPo rmNwUseRelPo) throws RollbackableBizException {
		super.save("updateRmNwUseRelPoAct", rmNwUseRelPo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RmNwUsePo findRmNwUsePoById(String id) throws RollbackableBizException {

		RmNwUsePo rmNwUsePo = super.findObjectByID("findRmNwUsePoById", id);
		
		return rmNwUsePo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RmNwUseRelPo findRmNwUseRelPoById(String id) throws RollbackableBizException {

		RmNwUseRelPo rmNwUseRelPo = super.findObjectByID("findRmNwUseRelPoById", id);
		
		return rmNwUseRelPo;
	}
	
	@Override
	public List<RmNwUseRelPo> findRmNwUseRelsByUseId(String useId) throws RollbackableBizException
	{
		return super.findByID("findRmNwUseRelsByUseId", useId);
	}
	
	@Override
	public void deleteRmNwUsePoById(String id) throws RollbackableBizException {
		super.deleteForIsActive("deleteRmNwUsePoById", id);
	}
	
	@Override
	public void deleteRmNwUseRelPoById(String id) throws RollbackableBizException {
		super.deleteForIsActive("deleteRmNwUseRelPoById", id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String findOccupiedUseNamePoById(String id) throws RollbackableBizException {
		String rmNwUseName = (String)getSqlMapClientTemplate().queryForObject("findOccupiedUseNamePoById", id);
		return rmNwUseName;
	}
	
	@Override
	public Pagination<RmNwUseRelVo> queryRmNwUseRelPoPagination(
			PaginationParam pagination) {
		return super.pageQuery( "findRmNwUseRelPoCount","findRmNwUseRelList", pagination);
	}
	
	@Override
	public List<Map<String, Object>> selectRmHostTypeSel() throws RollbackableBizException {
		return super.findForList("selectRmHostTypeSel");
	}
}
