package com.git.cloud.tenant.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.network.model.po.CloudProjectPo;
import com.git.cloud.tenant.model.po.QuotaConfigPo;
import com.git.cloud.tenant.model.po.QuotaPo;
import com.git.cloud.tenant.model.vo.AllQuotaCountVo;
import com.git.cloud.tenant.model.vo.QuotaVo;

/**
 * @author ly
 *
 */
public interface ITenantQuotaDao {

	/**
	 * 根据平台类型获取定额指标
	 * @param typeCode
	 * @return
	 * @throws RollbackableBizException 
	 */
	public List<QuotaConfigPo> getQuotaConfigInfoByPlaTypeCode(String typeCode) throws RollbackableBizException;
	/**
	 * 获取平台类型
	 * @param typeCode
	 * @return
	 * @throws RollbackableBizException 
	 */
	public List<String> selectPlaTypeCode() throws RollbackableBizException;
	/**
	 *根据租户id获取租户配额
	 * @param typeCode
	 * @return
	 * @throws RollbackableBizException 
	 */
	public List<QuotaPo> selectQuotaByTenantId(String tenantId) throws RollbackableBizException;
	/**
	 * 添加租户配额
	 * @param quotaPo
	 * @throws RollbackableBizException
	 */
	public void addQuota(List<QuotaPo> list)throws RollbackableBizException;
	/**
	 * 删除租户配额
	 * @param denantId
	 * @throws RollbackableBizException
	 */
	public void deleteQuota(String denantId)throws RollbackableBizException;
	
	/**
	 * 根据租户id回去project列表
	 * @param tenantId
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<CloudProjectPo> getProjectsByTenantId(String tenantId)throws RollbackableBizException;
	/**
	 * 更新配额flag
	 * @param tenantId
	 * @throws RollbackableBizException
	 */
	void updateTenantQuotaFlag(String tenantId) throws RollbackableBizException;

	/*配额验证相关*/
	/**
	 * 统计Power已用cpu、已用内存、已用存储、
	 * @param tenantId
	 * @return
	 */
	public AllQuotaCountVo countPowerUsedQuota(String tenantId) throws RollbackableBizException;
	/**
	 * 统计Openstack已用cpu、已用内存、已用存储、虚机数
	 * @param tenantId
	 * @return
	 */
	
	public AllQuotaCountVo countOpenstackUsedQuota(String tenantId,String projectId) throws RollbackableBizException;
	
	
	/**
	 * 统计VMware已用cpu、已用内存、已用存储、虚机数
	 * @param tenantId
	 * @return
	 */
	
	public AllQuotaCountVo countVmwareUsedQuota(String tenantId) throws RollbackableBizException;
	
	
	
	
	/**
	 * 通过租户id查询所有平台的配额
	 * @param tenantId
	 * @return
	 */
	public List<QuotaVo> getQuotaList(String tenantId) throws RollbackableBizException;
	/**
	 * 通过map获取平台配额
	 * @param tenantId
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<QuotaVo> getQuotaListByMap(Map<String,String> map) throws RollbackableBizException;
	/**
	 * 获取已用cpu、内存、虚机数
	 * @param map
	 * @return
	 * @throws RollbackableBizException
	 */
	public AllQuotaCountVo getUsedResByMap(Map<String,String> map)throws RollbackableBizException;

	/**
	 * 查询租户在一个资源池下的资源数
	 * @param tenantId
	 * @param resPoolId
	 * @return
	 * @throws RollbackableBizException
	 */
	AllQuotaCountVo selectOpenstackUsedResourceByResPoolId(String tenantId, String resPoolId) throws RollbackableBizException;

	/**
	 * 查询租户的用户下的资源数
	 * @param tenantId
	 * @param userId
	 * @return
	 * @throws RollbackableBizException
	 */
	AllQuotaCountVo selectOpenstackUsedResourceByUserId(String tenantId, String userId) throws RollbackableBizException;

	/**
	 * 查询租户关联资源池的资源总数
	 * @param tenantId
	 * @return
	 * @throws RollbackableBizException
	 */
	AllQuotaCountVo selectResPoolResourceByTenantId(String tenantId) throws RollbackableBizException;
	/**
	 * 根据租户ID验证旗下是否有资源池
	 * @param tenantId
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<String> selectResPoolByTenantId(String tenantId) throws RollbackableBizException;
}
