package com.git.cloud.iaas.openstack.service;

import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.VolumeRestModel;

public interface OpenstackStorageService {
	
	 String getVolumeTypes(OpenstackIdentityModel model) throws Exception;
	
	 String createVolume(OpenstackIdentityModel model, VolumeRestModel volumeModel, String type) throws Exception;
	
	 void deleteVolume(OpenstackIdentityModel model, String volumeId) throws Exception;
	
	 String getVolumeStatus(OpenstackIdentityModel model, String volumeId) throws Exception;
	
	 String updateVolume(OpenstackIdentityModel model, String volumeId, String volumeName) throws Exception;
	
	 String getVolumeList(OpenstackIdentityModel model) throws Exception;
	
	 String getVolumeDetail(OpenstackIdentityModel model,String volumeId) throws Exception;
	
	 void putVolumeQuota(OpenstackIdentityModel model, String setProjectId) throws Exception;
	
	 String  createVolumeSnapshot(OpenstackIdentityModel model, String snapshotName,String volumeId) throws Exception;
	
	 String  snapshotCreateVolume(OpenstackIdentityModel model, String volumeName, String snapshotId) throws Exception;
	
	 String  snapshotCreateVolume(OpenstackIdentityModel model, String snapshotId) throws Exception;
	
	 String  getVolumeSnapshotList(OpenstackIdentityModel model) throws Exception;
	
	 String  getVolumeSnapshot(OpenstackIdentityModel model, String snapshotId) throws Exception;
	
	 void  deleteVolumeSnapshot(OpenstackIdentityModel model, String snapshotId) throws Exception;
	
	 String  getStoragePool(OpenstackIdentityModel model) throws Exception;
	
	 String  getStorageVirtualPool(OpenstackIdentityModel model) throws Exception;

}
