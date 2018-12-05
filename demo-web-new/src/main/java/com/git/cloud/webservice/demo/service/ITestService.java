package com.git.cloud.webservice.demo.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-24
 */
@WebService
public interface ITestService {
	
	String Test(@WebParam(name="name")String name);
}
