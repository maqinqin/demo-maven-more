package com.git.cloud.workflow.dao;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.rest.model.InstanceInfoVo;

public interface IWorkFlowDao {
	
	/**
	 * 根据子流的流程实例id，查询流程相关信息
	 * @param instanceId
	 * @return
	 */
	public InstanceInfoVo getInstanceInfoById(String instanceId)throws RollbackableBizException;
}
