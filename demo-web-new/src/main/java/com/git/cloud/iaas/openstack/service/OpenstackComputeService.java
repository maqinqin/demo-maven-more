package com.git.cloud.iaas.openstack.service;

import com.alibaba.fastjson.JSONArray;
import com.git.cloud.cloudservice.model.po.CloudFlavorPo;
import com.git.cloud.iaas.openstack.model.FlavorRestModel;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.VmRestModel;

public interface OpenstackComputeService {
	
	 String getGroupHost(OpenstackIdentityModel model) throws Exception;
	
	 String getGroupHostRes(OpenstackIdentityModel model) throws Exception;
	
	 String getGroupHostResDetail(OpenstackIdentityModel model,String hostId) throws Exception;
	
	 String getFlavor(OpenstackIdentityModel model) throws Exception;
	
	 String getFlavorDetail(OpenstackIdentityModel model, String flavorId) throws Exception;
	
	 String createFlavor(OpenstackIdentityModel model, CloudFlavorPo flavor) throws Exception;
	
	 void configFlavorVm(OpenstackIdentityModel model, String flavorId, FlavorRestModel flavor) throws Exception;
	
	 void configFlavorHost(OpenstackIdentityModel model, String flavorId, FlavorRestModel flavor) throws Exception;
	
	 void operateVm(OpenstackIdentityModel model, String serverId, String type) throws Exception;
	 
	 void operateVm(OpenstackIdentityModel model, String serverId, String type, int resultCode) throws Exception;
	
	 void resizeVm(OpenstackIdentityModel model, String serverId, String flavorId) throws Exception;
	
	 void updateVm(OpenstackIdentityModel model, String serverId, String serverName) throws Exception;
	
	 void moveVm(OpenstackIdentityModel model, String serverId, String hostName) throws Exception;
	
	 void operateHost(OpenstackIdentityModel model, String serverId, String type, int resultCode) throws Exception;
	
	 String getVNCConsole(OpenstackIdentityModel model, String serverId) throws Exception;
	
	 String getVNCConsolePhy(OpenstackIdentityModel model, String serverId) throws Exception;
	
	 String createVm(OpenstackIdentityModel model, VmRestModel vmRestModel,String ipData) throws Exception;
	
	 String createHost(OpenstackIdentityModel model, VmRestModel vmRestModel,String ipData) throws Exception;
	
	 String getServerDetail(OpenstackIdentityModel model, String serverId, boolean isVm) throws Exception;
	
	 String getServerState(OpenstackIdentityModel model, String serverId, boolean isVm) throws Exception;
	
	 String getServerList(OpenstackIdentityModel model) throws Exception;
	
	 String mountServerVolume(OpenstackIdentityModel model, String serverId, String volumeId, boolean isVm) throws Exception;
	
	 String getServerVolume(OpenstackIdentityModel model, String serverId, boolean isVm) throws Exception;
	
	 void unmountServerVolume(OpenstackIdentityModel model, String serverId, String volumeId, boolean isVm) throws Exception;
	
	 String getNetworkCard(OpenstackIdentityModel model, String serverId) throws Exception;
	
	 String getNetworkCardStatus(OpenstackIdentityModel model, String serverId,String portId) throws Exception;
	
	 void deleteNetworkCard(OpenstackIdentityModel model, String serverId ,String portId) throws Exception;
	
	 String addNetworkCard(OpenstackIdentityModel model, String serverId ,String portId) throws Exception;
	
	 void putComputeQuota(OpenstackIdentityModel model, String setProjectId) throws Exception;
	
	 void addSecurityGroup(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception;
	
	 void removeSecurityGroup(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception;
	
	 String  createFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception;
	
	 void deleteFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception;
	
	 void addFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception;
	
	 void removeFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception;
	
	 JSONArray getAvailabilityZoneList(OpenstackIdentityModel model) throws Exception;
	
	 String createVmGroup(OpenstackIdentityModel model, String name, String policy) throws Exception;

	String getServerGroupDetails(OpenstackIdentityModel model, String id) throws Exception;

	String deleteVmGroup(OpenstackIdentityModel model, String id) throws Exception;
	
	 String getBaremetal(OpenstackIdentityModel model) throws Exception;
	
	 String getBaremetalDetail(OpenstackIdentityModel model,String hostId) throws Exception;
	 
	 String createVmForGroup(OpenstackIdentityModel model,VmRestModel vmRestModel,String ipData) throws Exception;
	 
	 String getBuildBareMetal(OpenstackIdentityModel model, String serverName) throws Exception;

	String deleteHost(OpenstackIdentityModel model,String serverId)throws Exception ;
}
