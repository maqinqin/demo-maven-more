package com.git.cloud.cloudservice.dao.impl;

import java.util.List;

import com.git.cloud.cloudservice.dao.ISoftWareVerDao;
import com.git.cloud.cloudservice.model.po.CloudSoftwareVer;
import com.git.cloud.cloudservice.model.vo.CloudSoftwareVersVo;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;

public class SoftWareVerDaoImpl extends CommonDAOImpl implements ISoftWareVerDao {

	@Override
	public List<CloudSoftwareVer> showSoftwareVerAll(String softWareId)
			throws Exception {
		List<CloudSoftwareVer> cloudSoftwareVers = this.getSqlMapClient().queryForList("showVerBySoftWareId", softWareId);
		return cloudSoftwareVers;
	}

	@Override
	public void deleteSoftWareVer(String[] ids) throws Exception {
		for(String id : ids){
			super.deleteForIsActive("deleteSoftWareVer", id);
		}
	}

	@Override
	public CloudSoftwareVer findSoftWareVerByVerId(
			String cloudSoftwareVer) throws Exception {
		CloudSoftwareVer ver = findObjectByID("selectSoftWareVerById", cloudSoftwareVer);
		return ver;
	}

	@Override
	public Pagination<CloudSoftwareVer> findSoftwareVerAllBySoftId(
			PaginationParam pagination) throws Exception {
		Pagination<CloudSoftwareVer> paginations = pageQuery("selectAllVerCount", "findVerListBySoftId", pagination);
		return paginations;
	}

	@Override
	public void insertSoftWareVer(CloudSoftwareVer cloudSoftwareVer)
			throws Exception {
		cloudSoftwareVer.setSoftwareVerId(UUIDGenerator.getUUID());
		cloudSoftwareVer.setIsActive(IsActiveEnum.YES.getValue());
		save("insertSoftWareVer", cloudSoftwareVer);
		
	}

	@Override
	public void updateSoftWareVer(CloudSoftwareVer cloudSoftwareVer)
			throws Exception {
		update("updateSoftWareVer", cloudSoftwareVer);
		
	}

	/* (non-Javadoc)
	 * <p>Title:findSoftsByImageId</p>
	 * <p>Description:</p>
	 * @param pagination
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.dao.ISoftWareVerDao#findSoftsByImageId(com.git.cloud.common.support.PaginationParam)
	 */
	@Override
	public Pagination<CloudSoftwareVersVo> findSoftsByImageId(
			PaginationParam pagination) throws RollbackableBizException {
		
		return super.pageQuery( "findSoftsCount","findSoftsList", pagination);
	}

	@Override
	public Integer findCloudSoftwareVers(CloudSoftwareVer cloudSoftwareVer)
			throws RollbackableBizException {

		return (Integer) getSqlMapClientTemplate().queryForObject("findCloudSoftWareVersByparam", cloudSoftwareVer);
	}


}
