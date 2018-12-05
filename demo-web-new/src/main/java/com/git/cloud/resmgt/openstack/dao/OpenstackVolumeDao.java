package com.git.cloud.resmgt.openstack.dao;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.openstack.model.po.CloudVolumePo;

public interface OpenstackVolumeDao extends ICommonDAO {
	
	void deleteVolume(String volumeId) throws RollbackableBizException;
	
	CloudVolumePo selectByPrimaryKey(String id) throws RollbackableBizException;
	
	CloudVolumePo selectCloudVolumeIdByIaasUuid(String iaasUuid) throws RollbackableBizException;
	
	void updateCloudVolumeIaasUuidById(CloudVolumePo CloudVolumePo) throws RollbackableBizException;
}
