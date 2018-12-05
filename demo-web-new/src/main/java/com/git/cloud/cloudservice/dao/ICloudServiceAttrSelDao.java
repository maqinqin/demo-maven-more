package com.git.cloud.cloudservice.dao;

import java.util.ArrayList;
import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudServiceAttrSelPo;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public interface ICloudServiceAttrSelDao extends ICommonDAO {

	public CloudServiceAttrSelPo save(CloudServiceAttrSelPo cloudServiceAttrSelPo) throws RollbackableBizException;

	public void update(CloudServiceAttrSelPo cloudServiceAttrSelPo) throws RollbackableBizException;

	public CloudServiceAttrSelPo findById(String id) throws RollbackableBizException;

	public Pagination<CloudServiceAttrSelPo> queryPagination(PaginationParam pagination);

	public void deleteById(String[] ids) throws RollbackableBizException;
	
	public List<CloudServiceAttrSelPo> cloudLeading(ArrayList<String> list) throws RollbackableBizException;
}
