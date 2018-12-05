/**
  * Copyright 2018 bejson.com 
  */
package com.git.cloud.iaas.openstack.model;

/**
 * Auto-generated: 2018-03-14 10:26:42
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TokenBody {

    private String openstackApiIp;
    private String projectName;
    private User user;
    public void setOpenstackApiIp(String openstackApiIp) {
         this.openstackApiIp = openstackApiIp;
     }
     public String getOpenstackApiIp() {
         return openstackApiIp;
     }

    public void setProjectName(String projectName) {
         this.projectName = projectName;
     }
     public String getProjectName() {
         return projectName;
     }

    public void setUser(User user) {
         this.user = user;
     }
     public User getUser() {
         return user;
     }
	@Override
	public String toString() {
		return "TokenBody [openstackApiIp=" + openstackApiIp + ", projectName=" + projectName + ", user=" + user + "]";
	}
     
     

}