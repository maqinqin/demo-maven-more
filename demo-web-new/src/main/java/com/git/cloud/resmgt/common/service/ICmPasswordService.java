package com.git.cloud.resmgt.common.service;

import com.git.cloud.common.service.IService;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;

public interface ICmPasswordService extends IService{

	/**
	 * 读取资源的密码信息，密码为加密的
	 * @param resourceId	资源id
	 * @param userName		用户名
	 * @return
	 * @throws Exception
	 */
	public CmPasswordPo getPassword(String resourceId, String userName) throws Exception;
	
}
