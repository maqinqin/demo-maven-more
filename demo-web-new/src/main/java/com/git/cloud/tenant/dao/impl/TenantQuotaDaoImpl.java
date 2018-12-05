package com.git.cloud.tenant.dao.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.resmgt.network.model.po.CloudProjectPo;
import com.git.cloud.tenant.dao.ITenantQuotaDao;
import com.git.cloud.tenant.model.po.QuotaConfigPo;
import com.git.cloud.tenant.model.po.QuotaPo;
import com.git.cloud.tenant.model.vo.AllQuotaCountVo;
import com.git.cloud.tenant.model.vo.QuotaVo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TenantQuotaDaoImpl extends CommonDAOImpl implements ITenantQuotaDao {

	@Override
	public List<QuotaConfigPo> getQuotaConfigInfoByPlaTypeCode(String typeCode) throws RollbackableBizException {
		List<QuotaConfigPo> list = super.findByID("selectQuotaConfigByPlaTypeCode", typeCode);
		return list;
	}

	@Override
	public List<String> selectPlaTypeCode() throws RollbackableBizException {
		@SuppressWarnings("unchecked")
		List<String> list = getSqlMapClientTemplate().queryForList("selectPlaTypeCode");
		return list;
	}

	@Override
	public List<QuotaPo> selectQuotaByTenantId(String tenantId) throws RollbackableBizException {
		List<QuotaPo> list = super.findByID("selectQuotaByTenantId", tenantId);
		return list;
	}

	@Override
	public void addQuota(List<QuotaPo> list) throws RollbackableBizException {
		if(list!=null && list.size()>0) {
			for (QuotaPo quotaPo : list) {
				super.save("addQuota", quotaPo);
			}
			
		}
	}
	
	@Override
	public void updateTenantQuotaFlag(String tenantId) throws RollbackableBizException {
		getSqlMapClientTemplate().update("updateTenantQuotaFlag", tenantId);
	}

	@Override
	public void deleteQuota(String denantId) throws RollbackableBizException {
		super.delete("deleteQuota", denantId);
		
	}

	@Override
	public List<CloudProjectPo> getProjectsByTenantId(String tenantId) throws RollbackableBizException {
		List<CloudProjectPo> list = super.findByID("selectProjecteByTenantId", tenantId);
		return list;
	}

	/**/
	@Override
	public AllQuotaCountVo countPowerUsedQuota(String tenantId) throws RollbackableBizException {
		return this.findObjectByID("countPowerUsedQuota", tenantId);
	}

	@Override
	public AllQuotaCountVo countOpenstackUsedQuota(String tenantId, String projectId) throws RollbackableBizException {
		Map<String,String> map=new HashMap<String,String>();
		map.put("tenantId", tenantId);
		map.put("projectId", projectId);
		return (AllQuotaCountVo) super.findObjectByMap("countOpenstackUsedQuota", map);
	}

	@Override
	public AllQuotaCountVo countVmwareUsedQuota(String tenantId) throws RollbackableBizException {
		return this.findObjectByID("countVmwareUsedQuota", tenantId);
	}

	@Override
	public List<QuotaVo> getQuotaList(String tenantId) throws RollbackableBizException {
		return super.findByID("getQuotaList",tenantId);
	}

	@Override
	public List<QuotaVo> getQuotaListByMap(Map<String, String> map) throws RollbackableBizException {
		return super.findListByParam("getQuotaListByMap", map);
	}

	@Override
	public AllQuotaCountVo getUsedResByMap(Map<String, String> map) throws RollbackableBizException {
		return super.findObjectByMap("getUsedResByMap", map);
	}

	@Override
	public AllQuotaCountVo selectOpenstackUsedResourceByResPoolId(String tenantId, String resPoolId) throws RollbackableBizException {
		Map<String,String> map=new HashMap<String,String>();
		map.put("tenantId", tenantId);
		map.put("resPoolId", resPoolId);
		return (AllQuotaCountVo) super.findObjectByMap("selectOpenstackUsedResourceByResPoolId", map);
	}

	@Override
	public AllQuotaCountVo selectOpenstackUsedResourceByUserId(String tenantId, String userId) throws RollbackableBizException {
		Map<String,String> map=new HashMap<>(2);
		map.put("tenantId", tenantId);
		map.put("userId", userId);
		return (AllQuotaCountVo) super.findObjectByMap("selectOpenstackUsedResourceByUserId", map);
	}

	@Override
	public AllQuotaCountVo selectResPoolResourceByTenantId(String tenantId) throws RollbackableBizException {
		return (AllQuotaCountVo) super.findObjectByID("selectResPoolResourceByTenantId", tenantId);
	}
	
	
	@Override
	public List<String> selectResPoolByTenantId(String tenantId) throws RollbackableBizException {	
		return  getSqlMapClientTemplate().queryForList("selectResPoolByTenantId", tenantId);
	}
	
	

}
