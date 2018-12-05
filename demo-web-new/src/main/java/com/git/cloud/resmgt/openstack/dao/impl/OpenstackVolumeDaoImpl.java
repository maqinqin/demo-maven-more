package com.git.cloud.resmgt.openstack.dao.impl;

import org.springframework.stereotype.Service;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.compute.model.po.CmVmGroupPo;
import com.git.cloud.resmgt.openstack.dao.OpenstackVolumeDao;
import com.git.cloud.resmgt.openstack.model.po.CloudVolumePo;

@Service
public class OpenstackVolumeDaoImpl extends CommonDAOImpl implements OpenstackVolumeDao {
	
	/**
	 * 删除openstack卷
	 * @param volumeId
	 * @throws RollbackableBizException
	 */
	@Override
	public void deleteVolume(String volumeId) throws RollbackableBizException{ 
		delete("deleteOpenstackVolume",volumeId);
	}

	@Override
	public CloudVolumePo selectByPrimaryKey(String id) throws RollbackableBizException {
		return (CloudVolumePo) super.findObjectByID("findCloudVolumeById", id);
	}

	@Override
	public CloudVolumePo selectCloudVolumeIdByIaasUuid(String iaasUuid) throws RollbackableBizException {
		return (CloudVolumePo) super.findObjectByID("selectCloudVolumeIdByIaasUuid", iaasUuid);
	}

	@Override
	public void updateCloudVolumeIaasUuidById(CloudVolumePo CloudVolumePo) throws RollbackableBizException {
		this.update("updateCloudVolumeIaasUuidById", CloudVolumePo);
	}
	
}
