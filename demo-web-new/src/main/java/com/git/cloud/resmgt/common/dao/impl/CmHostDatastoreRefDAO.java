package com.git.cloud.resmgt.common.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.dao.ICmHostDatastoreRefDAO;
import com.git.cloud.resmgt.common.model.po.CmDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastoreRefPo;


public class CmHostDatastoreRefDAO extends CommonDAOImpl implements ICmHostDatastoreRefDAO{

	@Override
	public String findDefaultDatastoreIdByHostId(String hostId) throws RollbackableBizException {
		String datastoreId = "";
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("hostId", hostId);
//		List<CmHostDatastoreRefPo> hdList = this.findByID("findHostDatastoreIdByHostId", hostId);
//		if(hdList != null && hdList.size() > 0) {
//			datastoreId = hdList.get(0).getDatastoreId();
//		}
		List<CmHostDatastorePo> diskList = this.findListByParam("findDefaultDatastoreIdByHostId", paramMap);
		if(diskList != null && diskList.size() > 0) {
			datastoreId = diskList.get(0).getDatastoreId();
		}
		return datastoreId;
	}
	public List<CmHostDatastorePo> findDatastoreIdByHosts(List<String> hostIdList) throws RollbackableBizException {
		String hostIdsStr;
		StringBuffer stringBuffer = new StringBuffer(1000);
		for(String hostId:hostIdList){
			stringBuffer.append(",'").append(hostId).append("'");
		}
		hostIdsStr = stringBuffer.toString().replaceFirst(",","");
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("hostId", hostIdsStr);
		List<CmHostDatastorePo> diskList = this.findListByParam("findDatastoreIdByHostIds", paramMap);
		return diskList;
	}
	@Override
	public List<CmHostDatastoreRefPo> getAllDatastoreRef(String datastoreId) throws Exception {
		return this.getSqlMapClientTemplate().queryForList("getAllDatastoreRef",datastoreId);
	}
	@Override
	public CmDatastorePo findDataStoreById(String datastoreId) throws Exception {
		List<CmDatastorePo> cmDatastorePos =  this.getSqlMapClientTemplate().queryForList("getDatastoreById",datastoreId);
		if (cmDatastorePos == null || cmDatastorePos.size() < 1) {
			return null;
		}else{
			return cmDatastorePos.get(0);
		}
	}
}
