package com.git.cloud.resmgt.common.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.CmDatastorePo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmResPoolPo;
import com.git.cloud.resmgt.compute.model.po.ScanHmcHostPo;

public interface IRmDatacenterDAO extends ICommonDAO {
	
	public RmDatacenterPo getDataCenterById(String dcId) throws RollbackableBizException;
	
	public List<RmDatacenterPo> getDataCenters() throws RollbackableBizException;
	
	public RmDatacenterPo getStorageTreeSpecified(String clusterId) throws RollbackableBizException;
	
	public List<RmDatacenterPo> getDataCenterByLikeForName(String dataCenterName) throws RollbackableBizException;

	public List<CmDatastorePo> queryStorageDataStoresPagination(String storage_id) throws RollbackableBizException;

	public void saveRmDatacenter(RmDatacenterPo rmDatacenterPo) throws RollbackableBizException;

	public void updateRmDatacenter(RmDatacenterPo rmDatacenterPo) throws RollbackableBizException;

	public String selectPoolByDatacenterId(String dataCenterId) throws RollbackableBizException;

	public void deleteDatacenter(String[] split) throws RollbackableBizException;

	public RmDatacenterPo selectQueueIdenfortrim(String queueIden) throws RollbackableBizException;

	public RmDatacenterPo selectDCenamefortrim(String ename) throws RollbackableBizException;

	public RmDatacenterPo getDataCenterByHostId(String hostId) throws RollbackableBizException;
	
	public ScanHmcHostPo findHmcHostInfoById(String hostId) throws RollbackableBizException;

	/**
	 * 获取Datacenter的url,username,password
	  *
	  * @throws RollbackableBizException
	  * @return RmDatacenterPo
	 */
	public List<RmDatacenterPo> getDataCenterAccessData() throws RollbackableBizException;

}
