package com.git.cloud.resmgt.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.dao.IRmPlatformDAO;
import com.git.cloud.resmgt.common.model.po.RmHostResPo;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.compute.model.po.RmCdpPo;
import com.git.cloud.resmgt.network.model.po.RmNwCclassPo;

public class RmPlatformDAO extends CommonDAOImpl implements IRmPlatformDAO{

	@Override
	public void savaRmPlatform(RmPlatformPo rmPlatformPo) throws RollbackableBizException {
		super.save("insertRmPlatform", rmPlatformPo);
		
	}

	@Override
	public void updateRmPlatform(RmPlatformPo rmPlatformPo) throws RollbackableBizException {
		super.update("updateRmPlatform", rmPlatformPo);
		
	}

	@Override
	public RmPlatformPo selectRmPlatform(String platformId) throws RollbackableBizException {
		return super.findObjectByID("selectRmPlatform", platformId);
	}
	
	@Override
	public List<Map<String, Object>> selectRmPlatformSel() throws RollbackableBizException {
		return super.findForList("selectRmPlatformSel");
	}

	@Override
	public void deleteRmPlatform(String platformId) throws RollbackableBizException {
		super.deleteForIsActive("deleteRmPlatform", platformId);
		
	}

	@Override
	public List<RmHostResPo> selectRmHostResByPlatformId(String id) throws RollbackableBizException {
	
		return super.findByID("selectRmHostResByPlatformId", id);
	}

	@Override
	public List<RmCdpPo> selectRmCdpByPlatformId(String id) throws RollbackableBizException {
		return super.findByID("selectRmHostResByPlatformId", id);
	}

	@Override
	public List<RmNwCclassPo> selectRmNwCclassByPlatformId(String id) throws RollbackableBizException {
		return super.findByID("selectRmHostResByPlatformId", id);
	}

	@Override
	public List<CloudServicePo> selectRmCloudServiceByPlatformId(String id) throws RollbackableBizException {
		return super.findByID("selectRmHostResByPlatformId", id);
	}

	@Override
	public RmPlatformPo selectRmPlatformForTrim(String platformId) throws RollbackableBizException {
		return super.findObjectByID("selectRmPlatformForTrim", platformId);
	}

	@Override
	public void savaVirtualType(RmVirtualTypePo rmVirtualTypePo) throws RollbackableBizException {
          super.save("savaVirtualType", rmVirtualTypePo);
	}

	@Override
	public RmVirtualTypePo selectRmVirtualTypeForTrim(String virtualTypeCode) throws RollbackableBizException {
		return super.findObjectByID("selectRmVirtualTypeForTrim", virtualTypeCode);
	}

	@Override
	public RmVirtualTypePo selectRmVirtualType(String virtualTypeId) throws RollbackableBizException {
		return super.findObjectByID("selectRmVirtualType", virtualTypeId);
	}
	
	@Override
	public List<Map<String, Object>> selectRmVirtualTypeSel(String platformId) throws RollbackableBizException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("platformId", platformId);
		return super.findForList("selectRmVirtualTypeSel", map);
	}

	@Override
	public void updateRmVirtualType(RmVirtualTypePo rmVirtualTypePo) throws RollbackableBizException {
		super.update("updateRmVirtualType", rmVirtualTypePo);
	}

//	@Override
//	public List<RmHostResPo> selectRmHostResByvirtualTypeId(String virtualTypeId) throws RollbackableBizException {
//		return super.findByID("selectRmHostResByvirtualTypeId", virtualTypeId);
//	}

	@Override
	public List<RmCdpPo> selectRmCdpByvirtualTypeId(String virtualTypeId) throws RollbackableBizException {
		return super.findByID("selectRmCdpByvirtualTypeId", virtualTypeId);
	}

	@Override
	public List<CloudServicePo> selectRmCloudServiceByvirtualTypeId(String virtualTypeId) throws RollbackableBizException {
		return super.findByID("selectRmCloudServiceByvirtualTypeId", virtualTypeId);
	}

	@Override
	public void deleteVirtualType(String virtualTypeId) throws RollbackableBizException {
		super.deleteForIsActive("deleteVirtualType", virtualTypeId);
	}

	@Override
	public List<RmVirtualTypePo> selectRmVirtualTypeByPlatformId(String id) throws RollbackableBizException {
		return super.findByID("selectRmVirtualTypeByPlatformId", id);
	}

	@Override
	public RmPlatformPo selectRmPlatformNameForTrim(String platformName) throws RollbackableBizException {
		return super.findObjectByID("selectRmPlatformNameForTrim", platformName);
	}

}
