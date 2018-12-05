package com.git.cloud.rest.common;

import com.git.cloud.rest.model.ServiceRequestParam;
import com.git.cloud.rest.service.ServiceRequest;
import com.git.cloud.rest.service.impl.ServiceRequestImpl;


public class ServiceRequestInstance {
	public static ServiceRequest getServiceRequestInstance(ServiceRequestParam jsonData) {
		return new ServiceRequestImpl();
	}
}
