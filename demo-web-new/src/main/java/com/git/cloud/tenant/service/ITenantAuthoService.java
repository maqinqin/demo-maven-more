package com.git.cloud.tenant.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.tenant.model.po.TenantPo;
import com.git.cloud.tenant.model.po.TenantResPoolPo;
import com.git.cloud.tenant.model.po.TenantUsersPo;

public interface ITenantAuthoService {


	
	
	/**
	 *  保存租户用户信息列表 以及删除无关联的租户信息
	 * @param List<TenantUsersPo> list
	 * @param String tenantId
	 * @return 
	 * @throws RollbackableBizException 
	 */
	public void addTenantAndUserRelation(String tenantId,List<TenantUsersPo> list) throws RollbackableBizException;

	/**
	 * 根据租户Id获取租户用户信息列表
	 * @param tenantId
	 * @return Pagination<TenantUsersPo>
	 * @throws RollbackableBizException
	 */
	public Pagination<TenantUsersPo> selectTenantAndNotTenantUsersByTenantId(PaginationParam paginationParam) throws RollbackableBizException;
	
	/**添加租户
	 * @param tenantVo
	 * @throws RollbackableBizException
	 */
	public void addTenant(TenantPo tenantPo)throws RollbackableBizException;
	/**
	 * 修改租户
	 * @param tenantPo
	 * @throws RollbackableBizException
	 */
	public void updateTenant(TenantPo tenantPo)throws RollbackableBizException;
	/**删除租户
	 * @param tenantId
	 * @throws RollbackableBizException
	 */
	public boolean deleteTenant(String tenantId) throws RollbackableBizException;
	/**
	 * 查询租户
	 * @param tenantId
	 * @return TenantPo
	 * @throws RollbackableBizException
	 */
	public TenantPo selectTenant(String tenantId)throws RollbackableBizException;
	/**
	 * 查询所有租户
	 * @param paginationParam
	 * @return Pagination<TenantPo>
	 * @throws RollbackableBizException
	 */
	public Pagination<TenantPo> selectTenants(PaginationParam paginationParam)throws RollbackableBizException;
	/**查询所有租户
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<TenantPo> selectTenantWithoutLimit()throws RollbackableBizException;

	/**根据用户id获取租户id
	 * @param userId
	 * @return
	 * @throws RollbackableBizException
	 */
	String selectTenantByUserId(String userId) throws RollbackableBizException; 
	/**
	 * 添加租户和资源池关联关系
	 * 先删除之前的，再添加最新的
	 * @param tenantId
	 * @param list
	 * @throws RollbackableBizException
	 */
	public void addTenantAndResPoolRelation(String tenantId,List<TenantResPoolPo> list) throws RollbackableBizException;
	/**
	 * 展示租户和资源池的列表信息
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 */
	public Pagination<TenantResPoolPo> getResPoolsRelationByTenantId(PaginationParam paginationParam) throws RollbackableBizException;
}
