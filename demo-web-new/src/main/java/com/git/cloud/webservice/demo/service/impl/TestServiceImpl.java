package com.git.cloud.webservice.demo.service.impl;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.git.cloud.webservice.demo.service.ITestService;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-24
 */
@Service
@WebService(endpointInterface="com.git.cloud.webservice.demo.service.ITestService",serviceName="TestService")
public class TestServiceImpl implements ITestService {

	@Override
	public String Test(String name) {
		
		return "success";
	}

}
