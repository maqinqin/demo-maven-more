package com.git.cloud.workflow.dao.impl;

import org.springframework.stereotype.Repository;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.rest.model.InstanceInfoVo;
import com.git.cloud.workflow.dao.IWorkFlowDao;
@Repository
public class WorkFlowDaoImpl extends CommonDAOImpl implements IWorkFlowDao{

	@Override
	public InstanceInfoVo getInstanceInfoById(String instanceId) throws RollbackableBizException{
		return  super.findObjectByID("getInstanceInfoById", instanceId);
	}
	
}
