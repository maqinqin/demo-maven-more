package com.git.cloud.cloudservice.service.impl;

import java.util.List;

import com.git.cloud.cloudservice.dao.ISoftWareDao;
import com.git.cloud.cloudservice.dao.ISoftWareVerDao;
import com.git.cloud.cloudservice.model.po.CloudSoftware;
import com.git.cloud.cloudservice.model.po.CloudSoftwareVer;
import com.git.cloud.cloudservice.model.vo.CloudSoftwareVersVo;
import com.git.cloud.cloudservice.service.ISoftWareService;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;

public class SoftWareServiceImpl implements ISoftWareService {

	private ISoftWareDao softWareDao;
	
	private ISoftWareVerDao softWareVerDao;
	
	
	
	
	public void setSoftWareVerDao(ISoftWareVerDao softWareVerDao) {
		this.softWareVerDao = softWareVerDao;
	}



	public void setSoftWareDao(ISoftWareDao softWareDao) {
		this.softWareDao = softWareDao;
	}



	@Override
	public List<CloudSoftware> showCloudSoftwareAll() throws Exception {
		List<CloudSoftware> cloudSoftwares = softWareDao.showCloudSoftwareAll();
		return cloudSoftwares;
	}



	@Override
	public void insertCloudSoftware(CloudSoftware cloudSoftware)
			throws Exception {
		softWareDao.insertCloudSoftware(cloudSoftware);
		
	}



	@Override
	public void updateCloudSoftware(CloudSoftware cloudSoftware)
			throws Exception {
		softWareDao.updateCloudSoftware(cloudSoftware);
		
	}



	@Override
	public void deleteCloudSoftware(String[] ids) throws Exception {
		
		softWareDao.deleteCloudSoftware(ids);
	}


	@Override
	public List<CloudSoftwareVer> showSoftwareVerAll(String softWareId)
			throws Exception {
		List<CloudSoftwareVer> cloudSoftwareVers = softWareVerDao.showSoftwareVerAll(softWareId);
		return cloudSoftwareVers;
	}



	@Override
	public void insertSoftWareVer(CloudSoftwareVer cloudSoftwareVer)
			throws Exception {

		softWareVerDao.insertSoftWareVer(cloudSoftwareVer);
		
	}



	@Override
	public void updateSoftWareVer(CloudSoftwareVer cloudSoftwareVer)
			throws Exception {
		softWareVerDao.updateSoftWareVer(cloudSoftwareVer);
		
		
	}



	@Override
	public void deleteSoftWareVer(String[] ids) throws Exception {
		softWareVerDao.deleteSoftWareVer(ids);
		
	}

	@Override
	public Pagination<CloudSoftware> showSoftwareAll(PaginationParam pagination) throws Exception {
		Pagination<CloudSoftware> paginations = softWareDao.showSoftwareAll(pagination);
		return paginations;
	}



	@Override
	public CloudSoftware showSoftWareBysoftWareId(String cloudSoftware)
			throws Exception {
		CloudSoftware software = softWareDao.showSoftWareBysoftWareId(cloudSoftware);
		return software;
	}



	@Override
	public Pagination<CloudSoftwareVer> findSoftwareVerAllBySoftId(
			PaginationParam pagination) throws Exception {
		Pagination<CloudSoftwareVer> paginations = softWareVerDao.findSoftwareVerAllBySoftId(pagination);
		return paginations;
	}



	@Override
	public CloudSoftwareVer findSoftWareVerByVerId(
			String cloudSoftwareVer) throws Exception {
		CloudSoftwareVer ver = softWareVerDao.findSoftWareVerByVerId(cloudSoftwareVer);
		return ver;
	}

	@Override
	public  Pagination<CloudSoftwareVersVo> findSoftsByImageId(
			PaginationParam pagination) throws RollbackableBizException {
		return softWareVerDao.findSoftsByImageId(pagination);
	}



	/* (non-Javadoc)
	 * <p>Title:findCloudSoftwaresByParam</p>
	 * <p>Description:</p>
	 * @param cloudSoftware
	 * @return
	 * @throws RollbackableBizException
	 * @see com.git.cloud.cloudservice.service.ISoftWareService#findCloudSoftwaresByParam(com.git.cloud.cloudservice.model.po.CloudSoftware)
	 */
	@Override
	public boolean findCloudSoftwaresByParam(
			CloudSoftware cloudSoftware) throws RollbackableBizException {
		int count = softWareDao.findCloudSoftwaresByParam(cloudSoftware);
		if(count > 0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean findCloudSoftwareVersByParam(
			CloudSoftwareVer cloudSoftwareVer) throws RollbackableBizException {

		int count = softWareVerDao.findCloudSoftwareVers(cloudSoftwareVer);
		if(count > 0){
			return false;
		}else{
			return true;
		}
	}

}
