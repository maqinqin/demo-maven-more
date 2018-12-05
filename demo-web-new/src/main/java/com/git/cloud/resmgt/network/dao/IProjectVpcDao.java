package com.git.cloud.resmgt.network.dao;

import org.springframework.stereotype.Service;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.resmgt.network.model.po.CloudProjectPo;
@Service
public interface IProjectVpcDao extends ICommonDAO{
	
	public CloudProjectPo findProjectInfoByVmId(String vmId)throws Exception;
	CloudProjectPo findProjectByProjectId(String projectId)throws Exception;
	CloudProjectPo selectProjectIdByIaasUuid(String iaasUuid)throws Exception;
	
}
