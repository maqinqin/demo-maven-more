package com.git.cloud.cloudservice.service;

import com.git.cloud.cloudservice.model.po.CloudServiceFlowRefPo;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public interface ICloudServiceFlowRefService {
	public CloudServiceFlowRefPo save(CloudServiceFlowRefPo cloudService) throws RollbackableBizException;

	public void update(CloudServiceFlowRefPo cloudService) throws RollbackableBizException;

	public CloudServiceFlowRefPo findById(String id) throws RollbackableBizException;

	public Pagination<CloudServiceFlowRefPo> queryPagination(PaginationParam pagination);

	public void deleteById(String[] ids) throws RollbackableBizException;

	public boolean checkCloudServiceFlowRefs(CloudServiceFlowRefPo cloudServiceFlowRefPo) throws RollbackableBizException;
}
