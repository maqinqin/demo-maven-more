package com.git.cloud.cloudservice.service.impl;

import com.git.cloud.cloudservice.dao.ICloudServiceFlowRefDao;
import com.git.cloud.cloudservice.model.po.CloudServiceFlowRefPo;
import com.git.cloud.cloudservice.service.ICloudServiceFlowRefService;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;

public class CloudServiceFlowRefServiceImpl implements ICloudServiceFlowRefService {

	private ICloudServiceFlowRefDao cloudServiceFlowRefDao;

	@Override
	public CloudServiceFlowRefPo save(CloudServiceFlowRefPo cloudService) throws RollbackableBizException {
		cloudService.setServiceFlowId(UUIDGenerator.getUUID());
		cloudService.setIsActive(IsActiveEnum.YES.getValue());
		cloudServiceFlowRefDao.save(cloudService);
		return cloudService;
	}

	@Override
	public void update(CloudServiceFlowRefPo cloudService) throws RollbackableBizException {
		cloudServiceFlowRefDao.update(cloudService);

	}

	@Override
	public CloudServiceFlowRefPo findById(String id) throws RollbackableBizException {
		return cloudServiceFlowRefDao.findById(id);
	}

	@Override
	public Pagination<CloudServiceFlowRefPo> queryPagination(PaginationParam paginationParam) {
		return cloudServiceFlowRefDao.queryPagination(paginationParam);
	}

	@Override
	public void deleteById(String[] ids) throws RollbackableBizException {
		cloudServiceFlowRefDao.deleteById(ids);
	}

	public ICloudServiceFlowRefDao getCloudServiceFlowRefDao() {
		return cloudServiceFlowRefDao;
	}

	public void setCloudServiceFlowRefDao(ICloudServiceFlowRefDao cloudServiceFlowRefDao) {
		this.cloudServiceFlowRefDao = cloudServiceFlowRefDao;
	}

	@Override
	public boolean checkCloudServiceFlowRefs(
			CloudServiceFlowRefPo cloudServiceFlowRefPo)
			throws RollbackableBizException {
		int count = cloudServiceFlowRefDao.findCloudServiceFlowRefPoCount(cloudServiceFlowRefPo);
		if(count > 0){
			return false;
		}else{
			return true;
		}
	}


}
