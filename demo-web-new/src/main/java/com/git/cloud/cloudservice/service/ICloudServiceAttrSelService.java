package com.git.cloud.cloudservice.service;

import com.git.cloud.cloudservice.model.po.CloudServiceAttrSelPo;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public interface ICloudServiceAttrSelService {
	public CloudServiceAttrSelPo save(CloudServiceAttrSelPo cloudServiceAttrSelPo) throws RollbackableBizException;

	public void update(CloudServiceAttrSelPo cloudServiceAttrSelPo) throws RollbackableBizException;

	public CloudServiceAttrSelPo findById(String id) throws RollbackableBizException;

	public Pagination<CloudServiceAttrSelPo> queryPagination(PaginationParam pagination);

	public void deleteById(String[] ids) throws RollbackableBizException;

}
