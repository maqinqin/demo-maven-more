package com.git.cloud.workflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.rest.model.InstanceInfoVo;
import com.git.cloud.workflow.dao.IWorkFlowDao;
import com.git.cloud.workflow.service.IWorkFlowService;
@Service
public class WorkFlowServiceImpl implements IWorkFlowService{
	
	@Autowired
	private IWorkFlowDao workFlowDaoImpl;
	@Override
	public InstanceInfoVo getInstanceInfoById(String instanceId) throws RollbackableBizException{
		return workFlowDaoImpl.getInstanceInfoById(instanceId);
	}
	

}
