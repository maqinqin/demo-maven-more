package com.git.cloud.resmgt.openstack.dao.impl;

import org.springframework.stereotype.Service;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.openstack.dao.ISecurityGroupsDao;
import com.git.cloud.resmgt.openstack.model.vo.SecurityGroupsDeviceRefVo;
import com.git.cloud.resmgt.openstack.model.vo.SecurityGroupsVo;
@Service
public class SecurityGroupsDaoImpl extends CommonDAOImpl implements ISecurityGroupsDao{
	
	@Override
	public SecurityGroupsVo getSecurityGroupsVoByProjectIdAndName(String projectId) throws RollbackableBizException {
		return (SecurityGroupsVo) getSqlMapClientTemplate().queryForObject("getSecurityGroupsVoByProjectIdAndName", projectId);
	}

	@Override
	public void saveVmRef(SecurityGroupsDeviceRefVo v) throws RollbackableBizException {
		this.save("saveVmRef", v);
	}
	
}
