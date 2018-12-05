package com.git.cloud.tenant.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.model.vo.VirtualSupplyVo;
import com.git.cloud.tenant.model.po.QuotaPo;
import com.git.cloud.tenant.model.vo.AllQuotaCountVo;
import com.git.cloud.tenant.model.vo.QuotaCountVo;
import com.git.cloud.tenant.model.vo.QuotaVo;
import com.git.cloud.tenant.model.vo.RequestInfo;


public interface ITenantQuotaService {
	
	/**
	 *获取配额指标
	 * @param tenantId 
	 * @param 
	 * @return
	 * @throws RollbackableBizException 
	 */
	public Map<String, List> getQuotaConfigInfo(String tenantId) throws RollbackableBizException;
	/**
	 *根据租户ID验证旗下是否有资源池
	 * @param tenantId 
	 * @param 
	 * @return
	 * @throws RollbackableBizException 
	 */
	public String getResPoolByTenantId(String tenantId) throws RollbackableBizException;
	/**
	 *根据租户id获取租户配额
	 * @param String tenantId
	 * @return
	 * @throws RollbackableBizException 
	 */
	public List<QuotaPo> selectQuotaByTenantId(String tenantId) throws RollbackableBizException;
	/**
	 * 添加租户配额
	 * @param String tenantId,List<QuotaPo> list
	 * @throws RollbackableBizException
	 */
	void addQuota(String tenantId,List<QuotaPo> list) throws Exception;
	/**
	 * 修改租户配额
	 * @param tenantId 
	 * @param List<QuotaPo> list
	 * @throws RollbackableBizException
	 */
	void updateQuota(String tenantId, List<QuotaPo> list) throws Exception;

	/**
	 * 单独验证cpu、内存、磁盘、虚拟机数是否符合配额
	 * @param tenantId
	 * @param platformTypeCode
	 * @param reqValue
	 * @param code
	 * @return
	 * @throws RollbackableBizException
	 */
	public boolean validateQuota(String tenantId,String platformTypeCode,int reqValue,String code)throws RollbackableBizException;
	
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
	 * 统计Vmware已用cpu、已用内存、已用存储、虚机数
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
}
