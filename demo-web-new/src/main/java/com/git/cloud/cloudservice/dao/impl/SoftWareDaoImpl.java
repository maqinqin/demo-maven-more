package com.git.cloud.cloudservice.dao.impl;

import java.util.List;

import com.git.cloud.cloudservice.dao.ISoftWareDao;
import com.git.cloud.cloudservice.model.po.CloudSoftware;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;

public class SoftWareDaoImpl extends CommonDAOImpl implements ISoftWareDao {

	@Override
	public List<CloudSoftware> showCloudSoftwareAll() throws Exception {
		List<CloudSoftware> cloudSoftwares = this.getSqlMapClient().queryForList("selectSoftWareAll");
		return cloudSoftwares;
	}

	@Override
	public void deleteCloudSoftware(String[] ids) throws Exception {
		for(String id : ids){
			super.deleteForIsActive("deleteSoftWareVerBySoftId", id);
			super.deleteForIsActive("deleteSoftWare", id);
		}
		
	}

	@Override
	public void insertCloudSoftware(CloudSoftware cloudSoftware)
			throws Exception {
		cloudSoftware.setSoftwareId(UUIDGenerator.getUUID());
		cloudSoftware.setIsActive("Y");
		save("insertSoftWare", cloudSoftware);
		
	}

	@Override
	public CloudSoftware showSoftWareBysoftWareId(String cloudSoftware)
			throws Exception {
		CloudSoftware software = findObjectByID("selectSoftWareById", cloudSoftware);
		return software;
	}

	@Override
	public Pagination<CloudSoftware> showSoftwareAll(PaginationParam pagination)
			throws Exception {
		Pagination<CloudSoftware> paginations = pageQuery("selectAllSoftCount", "findSoftList", pagination);
		return paginations;
	}

	@Override
	public void updateCloudSoftware(CloudSoftware cloudSoftware)
			throws Exception {
		update("updateSoftWare", cloudSoftware);
		
	}

	@Override
	public Integer findCloudSoftwaresByParam(
			CloudSoftware cloudSoftware) throws RollbackableBizException {
		
		return (Integer) getSqlMapClientTemplate().queryForObject("findCloudSoftwaresByParam", cloudSoftware);
	}



}
