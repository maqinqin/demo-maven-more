package com.git.cloud.resmgt.network.dao.impl;


import org.springframework.stereotype.Repository;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.resmgt.network.dao.IProjectVpcDao;
import com.git.cloud.resmgt.network.model.po.CloudProjectPo;
//@Service
@Repository
public class ProjectVpcDaoImpl extends CommonDAOImpl implements IProjectVpcDao{

	@Override
	public CloudProjectPo findProjectInfoByVmId(String vmId) throws Exception {
		return (CloudProjectPo) getSqlMapClientTemplate().queryForObject("findProjectInfoByVmId", vmId);
	}
	
	@Override
	public CloudProjectPo findProjectByProjectId(String projectId) throws Exception {
		return super.findObjectByID("getProjectVpcPoById", projectId);
	}

	@Override
	public CloudProjectPo selectProjectIdByIaasUuid(String iaasUuid) throws Exception {
		return super.findObjectByID("selectProjectIdByIaasUuid", iaasUuid);
	}
	
}
