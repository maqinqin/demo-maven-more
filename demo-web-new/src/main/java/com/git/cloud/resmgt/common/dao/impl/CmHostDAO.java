package com.git.cloud.resmgt.common.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.excel.model.vo.DataStoreVo;
import com.git.cloud.excel.model.vo.HostVo;
import com.git.cloud.policy.model.vo.PolicyInfoVo;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmHostUsernamePasswordPo;
import com.git.cloud.resmgt.common.model.vo.CmHostVo;

public class CmHostDAO extends CommonDAOImpl implements ICmHostDAO{
	private static Logger logger = LoggerFactory.getLogger(CmHostDAO.class);
	public void updateCmHostUsed(CmHostPo cmHost) throws RollbackableBizException {
		this.update("updateCmHostUsed", cmHost);
	}
	
	public CmHostPo findCmHostById(String id) throws RollbackableBizException {
		return this.findObjectByID("findCmHostById", id);
	}
	
	public List<?> findHostCpuCdpInfo(String hostId) throws RollbackableBizException{
		return this.getSqlMapClientTemplate().queryForList("selectHostCpuCdpInfo",hostId);
	}
	
	public String findHostIpById(String id) throws SQLException  {
		return (String) this.getSqlMapClient().queryForObject("selectHostIpById", id);
	}
	
	public CmHostUsernamePasswordPo findHostUsernamePassword(String hostId) throws RollbackableBizException{
		return this.findObjectByID("selectHostUsernamePasswordById", hostId);
	}

	@Override
	public DataStoreVo findDiskInfoByHostId(String hostId) throws RollbackableBizException {
		return this.findObjectByID("selectDiskInfoByHostId", hostId);
	}

	@Override
	public void updateCmHostUsed() throws RollbackableBizException {
		logger.info("CmHostDAO---------------updateCmHostUsed start---------------------");
		this.getSqlMapClientTemplate().update("updateHostCpuAndMem");
		logger.info("CmHostDAO---------------updateCmHostUsed end---------------------");
	}

	@Override
	public List<DataStoreVo> findDatastoreByHostIp(String hostIp) throws RollbackableBizException {
		return this.findListByParam("findDatastoreByHostIp", hostIp);
	}

	@Override
	public Pagination<CmHostVo> getHostList(PaginationParam paginationParam) throws RollbackableBizException {

		logger.info("CmHostDao---------------getHostList start---------------------");
		Pagination<CmHostVo> rmVmManageServerList = this.pageQuery("findPhyHostTotal", "findPhyHostPage",
				paginationParam);
		logger.info("CmHostDao---------------getHostList end---------------------");
		return rmVmManageServerList;
	}

	@Override
	public void updateUserNamePasswd(CmHostUsernamePasswordPo cupp)
			throws RollbackableBizException {
		this.update("updateUserNamePasswd", cupp);
	}

	@Override
	public int findExistUserNamePassWd(CmHostUsernamePasswordPo cupp)
			throws RollbackableBizException {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("findExistUserNamePassWd", cupp);
	}

	@Override
	public void insertHostUserNamePasswd(CmHostUsernamePasswordPo cupp)
			throws RollbackableBizException {
		this.save("insertHostUserNamePasswd", cupp);
	}
	@Override
	public String getGYRXHostDatastore(String hostId)throws SQLException {
		return (String) this.getSqlMapClient().queryForObject("getGYRXHostDatastore", hostId);
	}
	@Override
	public List<HostVo> findHostInfoListByParams(Map<String, String> params) {
		return this.findListByParam("findHostInfoListByParams", params);
	}


	@Override
	public void deleteSanDataStoreList(List<DataStoreVo> dataStoreList) {
		this.getSqlMapClientTemplate().delete("deleteSanDataStoreList", dataStoreList);
	}

	@Override
	public void deleteHostDataStoreRefList(List<DataStoreVo> dataStoreList) {
		this.getSqlMapClientTemplate().delete("deleteHostDataStoreRefList", dataStoreList);
	}


	@Override
	public List<DataStoreVo> findSanStorgeListByHostId(String hostId) {
		return this.getSqlMapClientTemplate().queryForList("findSanStorgeListByHostId",hostId);
	}

	public List<PolicyInfoVo> findHostByResPoolId(String resPoolId) throws RollbackableBizException {
		HashMap<String, Object> params = new HashMap<String, Object> ();
		params.put("resPoolId", resPoolId);
		return this.findListByParam("findHostByResPoolId", params);
	}
	
	@Override
	public CmHostVo selectHostIdByIp(String hostIp) {
		return (CmHostVo) this.getSqlMapClientTemplate().queryForObject("selectHostIdByIp", hostIp);
	}
}
