package com.git.cloud.iaas.openstack.model;

public class VlbHeathRestModel {
	private String tenantId;
	private String delay;
	private String expectedCodes;
	private String maxRetries;
	private String httpMethod;
	private String timeout;
	private String urlPath;
	private String healthType;
	private String poolId;
	
	public String getPoolId() {
		return poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getDelay() {
		return delay;
	}
	public void setDelay(String delay) {
		this.delay = delay;
	}
	public String getExpectedCodes() {
		return expectedCodes;
	}
	public void setExpectedCodes(String expectedCodes) {
		this.expectedCodes = expectedCodes;
	}
	public String getMaxRetries() {
		return maxRetries;
	}
	public void setMaxRetries(String maxRetries) {
		this.maxRetries = maxRetries;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getHealthType() {
		return healthType;
	}
	public void setHealthType(String healthType) {
		this.healthType = healthType;
	}


}
