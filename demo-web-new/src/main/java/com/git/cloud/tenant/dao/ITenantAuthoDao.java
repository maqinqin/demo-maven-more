package com.git.cloud.tenant.dao;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.tenant.model.po.TenantPo;
import com.git.cloud.tenant.model.po.TenantResPoolPo;
import com.git.cloud.tenant.model.po.TenantUsersPo;

/**
 * @author ly
 *
 */
public interface ITenantAuthoDao {

	
	
	/**
	 * 根据租户Id获取租户用户信息列表
	 * @param PaginationParam paginationParam
	 * @return
	 * @throws RollbackableBizException
	 */
	public Pagination<TenantUsersPo> selTenAndNotUsersByTenantId(PaginationParam paginationParam) throws RollbackableBizException;
	
	/**
	 * 删除租户和用户关联信息
	 * @param 
	 * @return 
	 * @throws RollbackableBizException 
	 */
	public void deleteTenantAndUserRelation(String tenantId) throws RollbackableBizException;
	/**
	 *  添加租户和用户关联信息
	 * @param 
	 * @return 
	 * @throws RollbackableBizException 
	 */
	public void addTenantAndUserRelation(List<TenantUsersPo> list) throws RollbackableBizException;
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
	public void deleteTenant(String tenantId) throws RollbackableBizException;
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
	/**
	 * 获取租户用户的数目
	 * @param tenantId
	 * @return
	 * @throws RollbackableBizException
	 */
	public Integer selectTenantUsersCount(String tenantId)throws RollbackableBizException;
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
	 * 删除租户和资源池关联关系
	 * @param tenantId
	 * @throws RollbackableBizException
	 */
	public void deleteTenantAndResPoolRelation(String tenantId) throws RollbackableBizException;
	/**
	 * 添加租户和资源池关联关系
	 * @param list
	 * @throws RollbackableBizException
	 */
	public void addTenantAndResPoolRelation(List<TenantResPoolPo> list )throws RollbackableBizException;
	/**
	 * 展示租户和资源池的列表信息
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 */
	public Pagination<TenantResPoolPo> getResPoolsRelationByTenantId(PaginationParam paginationParam) throws RollbackableBizException;

	/**
	 * 根据租户ID查询关联的资源池
	 * @param tenantId
	 * @return
	 * @throws RollbackableBizException
	 */
	List<TenantResPoolPo> selectTenantPoolList(String tenantId) throws RollbackableBizException;

	/**
	 * 根据租户ID查询用户列表
	 * @param tenantId 租户ID
	 * @return
	 * @throws RollbackableBizException
	 */
	List<TenantUsersPo> selectUserListByTenantId(String tenantId) throws RollbackableBizException;
}
