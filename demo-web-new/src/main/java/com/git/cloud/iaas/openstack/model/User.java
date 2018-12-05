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
public class User {

    private String userName;
    private String password;
    public void setUserName(String userName) {
         this.userName = userName;
     }
     public String getUserName() {
         return userName;
     }

    public void setPassword(String password) {
         this.password = password;
     }
     public String getPassword() {
         return password;
     }
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + "]";
	}
     

}