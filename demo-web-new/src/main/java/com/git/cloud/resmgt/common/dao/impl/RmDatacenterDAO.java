package com.git.cloud.resmgt.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.model.po.CmDatastorePo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.compute.model.po.ScanHmcHostPo;

@Service
public class RmDatacenterDAO extends CommonDAOImpl implements IRmDatacenterDAO {

	@Override
	public RmDatacenterPo getDataCenterById(String dcId)
			throws RollbackableBizException {
		return super.findObjectByID("selectDataCenterById", dcId);
	}
	
	public ScanHmcHostPo findHmcHostInfoById(String hostId) throws RollbackableBizException{
		return super.findObjectByID("selectHmcHostInfoById", hostId);
	}
	@Override
	public RmDatacenterPo getDataCenterByHostId(String hostId)
			throws RollbackableBizException {
		return super.findObjectByID("selectDataCenterByHostId", hostId);
	}

	@Override
	public List<RmDatacenterPo> getDataCenters()
			throws RollbackableBizException {
		return super.findAll("selectDataCenter");
	}

	@Override
	public List<RmDatacenterPo> getDataCenterByLikeForName(String dataCenterName)
			throws RollbackableBizException {
		return super.findByID("selectDataCenterByLike", dataCenterName);		
	}

	@Override
	public RmDatacenterPo getStorageTreeSpecified(String clusterId) throws RollbackableBizException {
		return super.findObjectByID("getStorageTreeSpecified", clusterId);
	}

	@Override
	public List<CmDatastorePo> queryStorageDataStoresPagination(String storage_id) throws RollbackableBizException {
		return super.findByID("queryStorageDataStoresPagination", storage_id);
	}

	@Override
	public void saveRmDatacenter(RmDatacenterPo rmDatacenterPo) throws RollbackableBizException {
		super.save("insertRmDatacenter", rmDatacenterPo);
		
	}

	@Override
	public void updateRmDatacenter(RmDatacenterPo rmDatacenterPo) throws RollbackableBizException {
		super.update("updateRmDatacenter", rmDatacenterPo);
		
	}

	@Override
	public String selectPoolByDatacenterId(String dataCenterId) throws RollbackableBizException {
		int count = (Integer)getSqlMapClientTemplate().queryForObject("selectPoolByDatacenterId", dataCenterId);
		return count+"";
	}

	@Override
	public void deleteDatacenter(String[] split) throws RollbackableBizException {
		for (int i = 0; i < split.length; i++) {
			super.deleteForIsActive("deleteDatacenter", split[i]);
		}
		
		
	}

	@Override
	public RmDatacenterPo selectQueueIdenfortrim(String queueIden) throws RollbackableBizException {
		
		return super.findObjectByID("selectQueueIdenfortrim", queueIden);
	}

	@Override
	public RmDatacenterPo selectDCenamefortrim(String ename) throws RollbackableBizException {
		return super.findObjectByID("selectDCenamefortrim", ename);
	}

	@Override
	public List<RmDatacenterPo> getDataCenterAccessData() throws RollbackableBizException {
		return super.findAll("selectVCenterAccessData");
	}

}
