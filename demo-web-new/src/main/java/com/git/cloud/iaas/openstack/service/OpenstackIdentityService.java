package com.git.cloud.iaas.openstack.service;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.ProjectRestModel;

public interface OpenstackIdentityService {
	
	 String getToken(OpenstackIdentityModel model) throws Exception;
	
	 String getToken(String openstackIp,String domainName,String manegeOneIp) throws Exception;
	
	 String createProjectForManageUser(OpenstackIdentityModel model,ProjectRestModel project) throws Exception;
	
	 void deleteProject(OpenstackIdentityModel model) throws Exception;
	
	 void createHAStack(OpenstackIdentityModel model) throws Exception;
	
	 String createUser(OpenstackIdentityModel model,String domainId, String username, String password) throws Exception;
	
	 void deleteUser(OpenstackIdentityModel model,String userId) throws Exception;
	
	 String createProjectForUser(OpenstackIdentityModel model,String projectName, String userId) throws Exception;
	
	 String getManageProject(OpenstackIdentityModel model) throws Exception;
	
	 String getRegion(OpenstackIdentityModel model) throws Exception;
	 
	 JSONObject getTokenForFlavor(String manegeOneIp) throws Exception;
}
