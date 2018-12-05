package com.git.cloud.resmgt.common.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.RmHostResPo;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.compute.model.po.RmCdpPo;
import com.git.cloud.resmgt.network.model.po.RmNwCclassPo;

public interface IRmPlatformDAO extends ICommonDAO{

	public void savaRmPlatform(RmPlatformPo rmPlatformPo) throws RollbackableBizException;

	public void updateRmPlatform(RmPlatformPo rmPlatformPo) throws RollbackableBizException;

	public RmPlatformPo selectRmPlatform(String platformId) throws RollbackableBizException;
	
	public List<Map<String, Object>> selectRmPlatformSel() throws RollbackableBizException;

	public void deleteRmPlatform(String string) throws RollbackableBizException;

	public List<RmHostResPo> selectRmHostResByPlatformId(String id) throws RollbackableBizException;

	public List<RmCdpPo> selectRmCdpByPlatformId(String id) throws RollbackableBizException;

	public List<RmNwCclassPo> selectRmNwCclassByPlatformId(String id) throws RollbackableBizException;

	public List<CloudServicePo> selectRmCloudServiceByPlatformId(String id) throws RollbackableBizException;

	public RmPlatformPo selectRmPlatformForTrim(String platformId) throws RollbackableBizException;

	public void savaVirtualType(RmVirtualTypePo rmVirtualTypePo) throws RollbackableBizException;

	public RmVirtualTypePo selectRmVirtualTypeForTrim(String virtualTypeCode) throws RollbackableBizException;

	public RmVirtualTypePo selectRmVirtualType(String virtualTypeId) throws RollbackableBizException;
	
	public List<Map<String, Object>> selectRmVirtualTypeSel(String platformId) throws RollbackableBizException;

	public void updateRmVirtualType(RmVirtualTypePo rmVirtualTypePo) throws RollbackableBizException;

	//public List<RmHostResPo> selectRmHostResByvirtualTypeId(String virtualTypeId) throws RollbackableBizException;

	public List<RmCdpPo> selectRmCdpByvirtualTypeId(String virtualTypeId) throws RollbackableBizException;

	public List<CloudServicePo> selectRmCloudServiceByvirtualTypeId(String virtualTypeId) throws RollbackableBizException;

	public void deleteVirtualType(String virtualTypeId) throws RollbackableBizException;

	public List<RmVirtualTypePo> selectRmVirtualTypeByPlatformId(String id) throws RollbackableBizException;

	public RmPlatformPo selectRmPlatformNameForTrim(String platformName) throws RollbackableBizException;

}
