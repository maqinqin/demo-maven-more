package com.git.cloud.cloudservice.service.impl;

import com.git.cloud.cloudservice.dao.ICloudServiceAttrSelDao;
import com.git.cloud.cloudservice.model.po.CloudServiceAttrSelPo;
import com.git.cloud.cloudservice.service.ICloudServiceAttrSelService;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public class CloudServiceAttrSelServiceImpl implements ICloudServiceAttrSelService {
	private ICloudServiceAttrSelDao cloudServiceAttrSelDao;

	@Override
	public CloudServiceAttrSelPo save(CloudServiceAttrSelPo cloudServiceAttrSelPo) throws RollbackableBizException {
		return cloudServiceAttrSelDao.save(cloudServiceAttrSelPo);
	}

	@Override
	public void update(CloudServiceAttrSelPo cloudServiceAttrSelPo) throws RollbackableBizException {
		cloudServiceAttrSelDao.update(cloudServiceAttrSelPo);
	}

	@Override
	public CloudServiceAttrSelPo findById(String id) throws RollbackableBizException {
		return cloudServiceAttrSelDao.findById(id);
	}

	@Override
	public Pagination<CloudServiceAttrSelPo> queryPagination(PaginationParam pagination) {
		return cloudServiceAttrSelDao.queryPagination(pagination);
	}

	@Override
	public void deleteById(String[] ids) throws RollbackableBizException {
		cloudServiceAttrSelDao.deleteById(ids);
	}

	public ICloudServiceAttrSelDao getCloudServiceAttrSelDao() {
		return cloudServiceAttrSelDao;
	}

	public void setCloudServiceAttrSelDao(ICloudServiceAttrSelDao cloudServiceAttrSelDao) {
		this.cloudServiceAttrSelDao = cloudServiceAttrSelDao;
	}

}
