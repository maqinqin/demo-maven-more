package com.git.cloud.resmgt.openstack.dao;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.openstack.model.vo.SecurityGroupsDeviceRefVo;
import com.git.cloud.resmgt.openstack.model.vo.SecurityGroupsVo;

public interface ISecurityGroupsDao {
	
	SecurityGroupsVo getSecurityGroupsVoByProjectIdAndName(String projectId) throws RollbackableBizException;
	
	void saveVmRef(SecurityGroupsDeviceRefVo v )throws RollbackableBizException;
	
}
