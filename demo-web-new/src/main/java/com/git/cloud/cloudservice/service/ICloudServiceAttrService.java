package com.git.cloud.cloudservice.service;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudServiceAttrPo;
import com.git.cloud.cloudservice.model.vo.QueryDataVo;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public interface ICloudServiceAttrService {
	public CloudServiceAttrPo save(CloudServiceAttrPo cloudServiceAttrPo) throws RollbackableBizException;

	public void update(CloudServiceAttrPo cloudServiceAttrPo) throws RollbackableBizException;

	public CloudServiceAttrPo findById(String id) throws RollbackableBizException;

	public Pagination<CloudServiceAttrPo> queryPagination(PaginationParam pagination);

	public void deleteById(String[] ids) throws RollbackableBizException;

	public boolean checkCloudServiceAttrs(CloudServiceAttrPo cloudServiceAttrPo) throws RollbackableBizException;
	
	public List<QueryDataVo> queryDynamicSQL(String listSqlId, String deviceId) throws Exception;
}
