package com.git.cloud.iaas.openstack.service;

import java.util.List;

import com.git.cloud.iaas.openstack.model.FwRuleRestModel;
import com.git.cloud.iaas.openstack.model.NetworkRestModel;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.SecurtyGroupRuleRestModel;
import com.git.cloud.iaas.openstack.model.SubnetRestModel;
import com.git.cloud.iaas.openstack.model.TapFlowsRestModel;
import com.git.cloud.iaas.openstack.model.TapServiceRestModel;
import com.git.cloud.iaas.openstack.model.VlbHeathRestModel;
import com.git.cloud.iaas.openstack.model.VlbListenerRestModel;
import com.git.cloud.iaas.openstack.model.VlbMemberRestModel;
import com.git.cloud.iaas.openstack.model.VlbPoolRestModel;
import com.git.cloud.iaas.openstack.model.VlbRestModel;

public interface OpenstackNetworkService {
	
	 String createNetwork(OpenstackIdentityModel model, NetworkRestModel networkModel) throws Exception;
	
	 String createExtNetwork(OpenstackIdentityModel model, NetworkRestModel networkModel) throws Exception ;
	
	 void putExtNetwork(OpenstackIdentityModel model,String networkId, NetworkRestModel networkModel) throws Exception;
	
	 void deleteExtNetwork(OpenstackIdentityModel model,String networkId) throws Exception;
	
	 String createSubnet(OpenstackIdentityModel model, SubnetRestModel subnetModel) throws Exception;
	
	 void putSubnet( OpenstackIdentityModel model,String subnetId, SubnetRestModel subnetModel) throws Exception;
	
	 void deleteSubnet(OpenstackIdentityModel model,String subnetId) throws Exception;
	
	 String createRouter(OpenstackIdentityModel model,String networkId, String routerName) throws Exception;
	
	 void updateRouter(OpenstackIdentityModel model,String routerId,String enableSnat,String networkId) throws Exception;
	
	 void deleteRouter(OpenstackIdentityModel model,String routerId) throws Exception;
	
	 void putRouterNetwork(OpenstackIdentityModel model,String subnetId, String routerId) throws Exception;
	
	 void removeRouterNetwork(OpenstackIdentityModel model,String subnetId, String routerId) throws Exception;
	
	 String createPort(OpenstackIdentityModel model,String networkId, String subnetId,String ipAddress,String name) throws Exception;
	 /**
	  * 查询PORT列表
	  * 
	  * @param model
	  * @return
	  * @throws Exception
	  */
	 String getPortList(OpenstackIdentityModel model) throws Exception;
	
	 String getPortDetail(OpenstackIdentityModel model,String portId) throws Exception;
	
	 String getPortIp(OpenstackIdentityModel model,String portName) throws Exception;
	
	 String getNetworkPort(OpenstackIdentityModel model,String networkId) throws Exception;
	
	 void deletePort(OpenstackIdentityModel model,String portId) throws Exception;
	
	 void putPort(OpenstackIdentityModel model,String portId,String wsIp,String omIp,String mac) throws Exception;
	
	 String getPhyNet(OpenstackIdentityModel model) throws Exception;
	
	 String getOverSplit(OpenstackIdentityModel model) throws Exception;
	
	 void putNetworkQuota(OpenstackIdentityModel model, String setProjectId) throws Exception;
	
	 String  createFwp(OpenstackIdentityModel model,String policyName) throws Exception;
	
	 void deleteFwp(OpenstackIdentityModel model,String firewallPolicyId) throws Exception;
	
	 String  createFw(OpenstackIdentityModel model,String routerId,String fwName,String fwpId) throws Exception;
	
	 void deleteFw(OpenstackIdentityModel model,String fwId) throws Exception;
	
	 String  putFwr(OpenstackIdentityModel model,FwRuleRestModel fwr,String targetFwrId) throws Exception;
	
	 String createFwr(OpenstackIdentityModel model,FwRuleRestModel fwr) throws Exception;
	
	 void  deleteFwr(OpenstackIdentityModel model,String fwrId) throws Exception;
	
	 String  putFwpInFw(OpenstackIdentityModel model,String fwId,String fwpId) throws Exception;
	
	 String  addFwrInFwp(OpenstackIdentityModel model,String fwpId,String fwrId) throws Exception;
	
	 String  removeFwrInFwp(OpenstackIdentityModel model,String fwpId,String fwrId) throws Exception;
	
	 String createVlbPool(OpenstackIdentityModel model,VlbPoolRestModel vlbPool) throws Exception;
	
	 void deleteVlbPool(OpenstackIdentityModel model,String poolId) throws Exception;
	
	 String  getVLbPoolList(OpenstackIdentityModel model) throws Exception;
	
	 String createPortNoIp(OpenstackIdentityModel model,String networkId,String portName) throws Exception;
	
	 String createVlb(OpenstackIdentityModel model,VlbRestModel vlb) throws Exception;
	
	 String createVlbListener(OpenstackIdentityModel model,VlbListenerRestModel listener) throws Exception;
	
	 void deleteVlbListener(OpenstackIdentityModel model,String listenerId) throws Exception ;
	
	 void deleteVlb(OpenstackIdentityModel model,String vlbId) throws Exception;
	
	 String createVlbMember(OpenstackIdentityModel model,VlbMemberRestModel vlbMember) throws Exception;
	
	 void deleteVlbMember(OpenstackIdentityModel model,String memberId,String poolId) throws Exception;
	
	 String createVlbHealth(OpenstackIdentityModel model,VlbHeathRestModel vlbHealth) throws Exception;
	
	 String getVlbHealthDetail(OpenstackIdentityModel model,String healthId) throws Exception;
	
	 void deleteVlbHealth(OpenstackIdentityModel model,String healthMonitorId) throws Exception;
	
	 String  getSecurityGroupList(OpenstackIdentityModel model) throws Exception;
	
	 String  getSecurityGroup(OpenstackIdentityModel model,String securityId) throws Exception;
	
	 String  getSecurityGroupRuleList(OpenstackIdentityModel model) throws Exception;
	
	 String  getSecurityGroupRule(OpenstackIdentityModel model,String securityRuleId) throws Exception;
	
	 String  createSecurityGroupRule(OpenstackIdentityModel model,SecurtyGroupRuleRestModel sgr) throws Exception;
	
	 void  deleteSecurityGroupRule(OpenstackIdentityModel model,String securityRuleId) throws Exception;
	
	 String  createSecurityGroup(OpenstackIdentityModel model,String securityGroupName) throws Exception;
	
	 String  updateSecurityGroup(OpenstackIdentityModel model,String securityId,String securityGroupName) throws Exception;
	
	 void  deleteSecurityGroup(OpenstackIdentityModel model,String securityId) throws Exception ;
	
	 String createTapServices(OpenstackIdentityModel model,TapServiceRestModel tapServicePo) throws Exception;
	
	 String createTapFlow(OpenstackIdentityModel model,TapFlowsRestModel tapFlows) throws Exception;
	
	 void deleteTapFlow(OpenstackIdentityModel model,String id) throws Exception;
	
	 void deleteTapService(OpenstackIdentityModel model,String id) throws Exception;
	
	 void putPortIp(OpenstackIdentityModel model,String portId,String ip) throws Exception;
	
	 void updatePortS(OpenstackIdentityModel model,String portId,List<String> vipList,String method) throws Exception;

	String getNetwork(OpenstackIdentityModel model) throws Exception;

}
