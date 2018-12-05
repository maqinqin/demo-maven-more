package com.git.cloud.cloudservice.dao;

import java.sql.SQLException;
import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudServiceAttrPo;
import com.git.cloud.cloudservice.model.vo.CloudServiceAttrVo;
import com.git.cloud.cloudservice.model.vo.QueryDataVo;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public interface ICloudServiceAttrDao extends ICommonDAO {

	public CloudServiceAttrPo save(CloudServiceAttrPo cloudServiceAttrPo) throws RollbackableBizException;

	public void update(CloudServiceAttrPo cloudServiceAttrPo) throws RollbackableBizException;

	public CloudServiceAttrPo findById(String id) throws RollbackableBizException;

	public Pagination<CloudServiceAttrPo> queryPagination(PaginationParam pagination);

	public void deleteById(String[] ids) throws RollbackableBizException;
	
	public Integer findCloudServiceAttrCount(CloudServiceAttrPo cloudServiceAttrPo) throws RollbackableBizException ;

	List<QueryDataVo> queryDynamicSQL(String listSql) throws SQLException;

	public String queryListSql(String listSqlId) throws SQLException;
	
	public List<CloudServiceAttrPo> cloudLeading(String serviceId) throws RollbackableBizException;
}
