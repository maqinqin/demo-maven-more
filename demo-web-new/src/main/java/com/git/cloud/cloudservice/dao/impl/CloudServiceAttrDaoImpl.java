package com.git.cloud.cloudservice.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.git.cloud.cloudservice.dao.ICloudServiceAttrDao;
import com.git.cloud.cloudservice.model.po.CloudServiceAttrPo;
import com.git.cloud.cloudservice.model.vo.CloudServiceAttrVo;
import com.git.cloud.cloudservice.model.vo.QueryDataVo;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public class CloudServiceAttrDaoImpl extends CommonDAOImpl implements ICloudServiceAttrDao {

	@Override
	public CloudServiceAttrPo save(CloudServiceAttrPo cloudService) throws RollbackableBizException {
		save("CloudServiceAttrPo.insert", cloudService);
		return null;
	}

	@Override
	public void update(CloudServiceAttrPo cloudService) throws RollbackableBizException {
		update("CloudServiceAttrPo.update", cloudService);

	}

	@Override
	public CloudServiceAttrPo findById(String id) throws RollbackableBizException {
		List<CloudServiceAttrPo> list = findByID("CloudServiceAttrPo.load", id);
		if (list != null) {
			for (CloudServiceAttrPo p : list)
				return p;
		}
		return null;
	}

	@Override
	public List<CloudServiceAttrPo> cloudLeading(String serviceId) throws RollbackableBizException {
		List<CloudServiceAttrPo> list = findByID("CloudServiceAttrPo.file",serviceId);
		if(list != null) {
			return list;
		}
		return null;
	}
	
	@Override
	public Pagination<CloudServiceAttrPo> queryPagination(PaginationParam pagination) {
		return pageQuery("CloudServiceAttrPo.count", "CloudServiceAttrPo.search", pagination);
	}

	@Override
	public void deleteById(String[] ids) throws RollbackableBizException {
		if (ids != null) {
			for (String id : ids)
				super.deleteForIsActive("CloudServiceAttrPo.delete", id);
		}

	}

	/* (non-Javadoc)
	 * <p>Title:findCloudServiceAttrCount</p>
	 * <p>Description:</p>
	 * @param cloudServiceAttrPo
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.dao.ICloudServiceAttrDao#findCloudServiceAttrCount(com.git.cloud.cloudservice.model.po.CloudServiceAttrPo)
	 */
	@Override
	public Integer findCloudServiceAttrCount(
			CloudServiceAttrPo cloudServiceAttrPo)
			throws RollbackableBizException {

		return (Integer) getSqlMapClientTemplate().queryForObject("findCloudServiceAttrPosByparam", cloudServiceAttrPo);
	}
	
	@Override
	public List<QueryDataVo> queryDynamicSQL(String listSql) throws SQLException{
		List<QueryDataVo> list = this.getSqlMapClient().queryForList("queryDynamicSQL", listSql);
		return list;
	}

	@Override
	public String queryListSql(String listSqlId) throws SQLException {
		Object object = this.getSqlMapClient().queryForObject("QueryForListSql", listSqlId);
		return object.toString();
	}

}
