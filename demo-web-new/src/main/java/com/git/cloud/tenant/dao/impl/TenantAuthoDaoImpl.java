package com.git.cloud.tenant.dao.impl;


import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.CollectionUtil;
import com.git.cloud.tenant.dao.ITenantAuthoDao;
import com.git.cloud.tenant.model.po.TenantPo;
import com.git.cloud.tenant.model.po.TenantResPoolPo;
import com.git.cloud.tenant.model.po.TenantUsersPo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 */
@Repository
public class TenantAuthoDaoImpl extends CommonDAOImpl implements ITenantAuthoDao {	

	@Override
	public void deleteTenantAndUserRelation(String tenantId) throws RollbackableBizException {
		super.delete("deleteTenantAndUserRelation", tenantId);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTenantAndUserRelation(List<TenantUsersPo> list) throws RollbackableBizException {		
		//super.batchInsert("addTenantAndUserRelation", list);
		if(list!=null && list.size()>0) {
			for (TenantUsersPo tenantUsersPo : list) {
				super.save("addTenantAndUserRelation", tenantUsersPo);
				super.save("updateTenantUserRoleName", tenantUsersPo);
			}			
		}		
	}

	@Override
	public Pagination<TenantUsersPo> selTenAndNotUsersByTenantId(PaginationParam paginationParam) throws RollbackableBizException {		
		Pagination<TenantUsersPo> pageQuery = pageQuery("selTenAndNotUsersTotalByTenantId", "selTenAndNotUsersByTenantId", paginationParam);
		return pageQuery;
	}

	@Override
	public void addTenant(TenantPo tenantPo) throws RollbackableBizException {
		super.save("addTenant", tenantPo);
		
	}

	@Override
	public void updateTenant(TenantPo tenantPo) throws RollbackableBizException {
		super.update("updateTenant", tenantPo);		
	}

	@Override
	public void deleteTenant(String tenantId) throws RollbackableBizException {
		super.delete("deleteTenant", tenantId);		
	}

	@Override
	public TenantPo selectTenant(String tenantId) throws RollbackableBizException {
		TenantPo tenantPo = super.findObjectByID("selectTenant", tenantId);
		return tenantPo;
	}

	@Override
	public Pagination<TenantPo> selectTenants(PaginationParam paginationParam) throws RollbackableBizException {
		Pagination<TenantPo> pagination =pageQuery("selectTenantsCount", "selectTenants", paginationParam);
		return pagination;
	}

	@Override
	public Integer selectTenantUsersCount(String tenantId) throws RollbackableBizException {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("selTenantUsersCount", tenantId);
		return count;
	}

	@Override
	public List<TenantPo> selectTenantWithoutLimit() throws RollbackableBizException {		
		return super.findAll("selectTenantsWithoutLimit");
	}
	@Override
	public String selectTenantByUserId(String userId) throws RollbackableBizException{
		String tenantId = (String) getSqlMapClientTemplate().queryForObject("selectTenantByUserId",userId);
		return tenantId;
	}

	@Override
	public void deleteTenantAndResPoolRelation(String tenantId) throws RollbackableBizException {
		super.delete("deleteTenantAndResPoolRelation", tenantId);	
	}

	@Override
	public void addTenantAndResPoolRelation(List<TenantResPoolPo> list) throws RollbackableBizException {
		if (CollectionUtil.hasContent(list)) {
			this.batchInsert("addTenantAndResPoolRelation", list);
		}
	}

	@Override
	public Pagination<TenantResPoolPo> getResPoolsRelationByTenantId(PaginationParam paginationParam)
			throws RollbackableBizException {
		Pagination<TenantResPoolPo> pagination =pageQuery("selectTenantPoolsCount", "selectTenantPoolsPage", paginationParam);
		return pagination;
	}

	@Override
	public List<TenantResPoolPo> selectTenantPoolList(String tenantId) throws RollbackableBizException {
		return super.findByID("selectTenantPoolList", tenantId);
	}

	@Override
	public List<TenantUsersPo> selectUserListByTenantId(String tenantId) throws RollbackableBizException {
		return super.findByID("selectUserListByTenantId", tenantId);
	}
}
