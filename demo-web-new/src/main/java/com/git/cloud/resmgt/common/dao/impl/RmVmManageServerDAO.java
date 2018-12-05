package com.git.cloud.resmgt.common.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.po.CmDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.RmHostVmInfoPo;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
//@Service
@Repository
public class RmVmManageServerDAO extends CommonDAOImpl implements IRmVmManageServerDAO {
	private static Logger logger = LoggerFactory.getLogger(RmVmManageServerDAO.class);
	
	
	@Override
	public RmVmManageServerPo findRmVmManageServerById(String vmMsId) throws RollbackableBizException {
		RmVmManageServerPo  rmVmManageServerPo = (RmVmManageServerPo) this.getSqlMapClientTemplate().queryForObject("findRmVmManageServerById", vmMsId);
		return rmVmManageServerPo;
	}

	@Override
	public List<RmPlatformPo> getPlatformInfo() throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------getPlatformInfo start---------------------");
		List<RmPlatformPo> list = this.findAll("selectPlatformInfo");
		logger.info("RmVmManageServerDAO---------------getPlatformInfo end---------------------");
		return list;
	}

	@Override
	public RmPlatformPo getPlatformInfoById(String platformId) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------getPlatformInfoById start---------------------");
		RmPlatformPo platform = null;
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("platformId", platformId);
		List<RmPlatformPo> list = this.findListByParam("getPlatformInfoById", paramMap);
		if(list != null && list.size() > 0) {
			platform = list.get(0);
		}
		logger.info("RmVmManageServerDAO---------------getPlatformInfoById end---------------------");
		return platform;
	}
	
	@Override
	public List<RmVirtualTypePo> getVmTypeNameInfo(String platformId) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------getVmTypeNameInfo start---------------------");
		List<RmVirtualTypePo> list = this.findByID("selectVmTypeNameInfo", platformId);
		logger.info("RmVmManageServerDAO---------------getVmTypeNameInfo end---------------------");
		return list;
	}

	@Override
	public Pagination<RmVmManageServerPo> getvmManageServerList(PaginationParam paginationParam) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------getvmManageServerList start---------------------");
		Pagination<RmVmManageServerPo> rmVmManageServerList = this.pageQuery("findVMServerTotal", "findVMServerPage",
				paginationParam);
		logger.info("RmVmManageServerDAO---------------getvmManageServerList end---------------------");
		return rmVmManageServerList;
	}

	@Override
	public <T extends BaseBO> T getvmManageServerInfo(String serverId) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------getvmManageServerInfo start---------------------");
		BaseBO rmVmManageServerInfo = (BaseBO) getSqlMapClientTemplate().queryForObject("findVMServerInfo", serverId);
		logger.info("RmVmManageServerDAO---------------getvmManageServerInfo end---------------------");
		return (T) rmVmManageServerInfo;
	}

	@Override
	public int selectVMServerCount(String Bizid) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------selectVMServerCount start---------------------");
		List count = getSqlMapClientTemplate().queryForList("selectVMServerCount", Bizid);
		logger.info("RmVmManageServerDAO---------------selectVMServerCount end---------------------");
		return (Integer) count.get(0);
	}

	@Override
	public int deleteVMServer(String id) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------deleteVMServer start---------------------");
		int flag = this.delete("deleteVMServer", id);
		logger.info("RmVmManageServerDAO---------------deleteVMServer end---------------------");
		return flag;
	}

	@Override
	public int insertVmManageServer(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------updatevmManageServer start---------------------");
		int flag = this.update("insertVmManageServer", rmVmManageServerPo);
		logger.info("RmVmManageServerDAO---------------updatevmManageServer end---------------------");
		return flag;
	}

	@Override
	public int updateVmManageServer(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------updateVmManageServer start---------------------");
		int flag = this.update("updateVmManageServer", rmVmManageServerPo);
		logger.info("RmVmManageServerDAO---------------updateVmManageServer end---------------------");
		return flag;
	}

	@Override
	public int insertCmPasswordPo(CmPasswordPo cmPasswordPo) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------insertCmPasswordPo start---------------------");
		int flag = this.update("insertCmPasswordPo", cmPasswordPo);
		logger.info("RmVmManageServerDAO---------------insertCmPasswordPo end---------------------");
		return flag;
	}

	@Override
	public int updateCmPasswordPo(CmPasswordPo cmPasswordPo) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------updateCmPasswordPo start---------------------");
		int flag = this.update("updateCmPasswordPo", cmPasswordPo);
		logger.info("RmVmManageServerDAO---------------updateCmPasswordPo end---------------------");
		return flag;
	}

	@Override
	public int selectVMServerName(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------selectVMServerName start---------------------");
		List count = getSqlMapClientTemplate().queryForList("selectVMServerName", rmVmManageServerPo);
		logger.info("RmVmManageServerDAO---------------selectVMServerName end---------------------");
		return (Integer) count.get(0);
	}

	@Override
	public int selectVMServerIp(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------selectVMServerIp start---------------------");
		List count = getSqlMapClientTemplate().queryForList("selectVMServerIp", rmVmManageServerPo);
		logger.info("RmVmManageServerDAO---------------selectVMServerIp end---------------------");
		return (Integer) count.get(0);
	}

	@Override
	public int selectVMServer(RmVmManageServerPo rmVmManageServerPo) throws RollbackableBizException {
		logger.info("RmVmManageServerDAO---------------selectVMServer start---------------------");
		List count = getSqlMapClientTemplate().queryForList("selectVMServer", rmVmManageServerPo);
		logger.info("RmVmManageServerDAO---------------selectVMServer end---------------------");
		return (Integer) count.get(0);
	}

	public List<RmVmManageServerPo> findRmVmManageServerListByVmIdList(List<String> vmIdList) throws RollbackableBizException {
		Map<String, String> paramMap = new HashMap<String, String> ();
		String vmIds = "";
		int len = vmIdList == null ? 0 : vmIdList.size();
		if(len == 0) {
			throw new RollbackableBizException("传入的设备Id列表为空");
		}
		List<RmVmManageServerPo> vmms = new ArrayList<RmVmManageServerPo>();
		for(int i=0 ; i<len ; i++) {
			vmIds = "'" + vmIdList.get(i) + "'";
			paramMap.put("vmIds", vmIds);
			List<RmVmManageServerPo> vmmsOne = this.findListByParam("findRmVmManageServerListByVmIdList", paramMap);
			vmms.addAll(vmmsOne);
		}
		return vmms;
	}
	
	public RmVmManageServerPo findRmVmManageServerByVmId(String vmId) throws RollbackableBizException {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("vmId", vmId);
		return this.findObjectByMap("findRmVmManageServerByVmId", paramMap);
	}

	@Override
	public List<RmVmManageServerPo> getvmManageServerList()
			throws RollbackableBizException {
		return   this.getSqlMapClientTemplate().queryForList("selectVMServerList", ""); 
	}
	
	/**
	 * 获取openstack服务器信息
	 * @return
	 * @throws RollbackableBizException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<RmVmManageServerPo> selectOpenstackServerList()
			throws RollbackableBizException {
		return   this.getSqlMapClientTemplate().queryForList("selectOpenstackServerList", ""); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RmVmManageServerPo> findAllHmcServer() {
		return this.getSqlMapClientTemplate().queryForList("selectVMServerListByType", ""); 
	}

	@Override
	public RmHostVmInfoPo findHostVmInfo(Map<String,String> pMap) throws RollbackableBizException {
		List<RmHostVmInfoPo> list = this.getSqlMapClientTemplate().queryForList("queryHostVMInfo", pMap);
		if(list.size() > 0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public CmDatastorePo findDatastoreInfo(Map<String,String> pMap) throws RollbackableBizException {
		List<CmDatastorePo> list = this.getSqlMapClientTemplate().queryForList("queryDatastoreInfo", pMap);
		if(list.size() > 0){
			return list.get(0);
		}
		else{
		    return null;
		}
	}

	@Override
	public <T extends RmVmManageServerPo> List<T> findListByFieldsAndOrder(String method,
			HashMap<String, String> params) throws RollbackableBizException {
		List<T> list = getSqlMapClientTemplate().queryForList(method, params);
		return list;
	}
	
	@Override
	public String getVmMsIdByHostPoolId(String resPoolId){
		return (String) getSqlMapClientTemplate().queryForObject("getVmMsIdByHostPoolId",resPoolId);
	}
}
