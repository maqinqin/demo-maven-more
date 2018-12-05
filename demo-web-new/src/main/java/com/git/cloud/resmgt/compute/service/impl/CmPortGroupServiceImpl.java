package com.git.cloud.resmgt.compute.service.impl;

import com.git.cloud.resmgt.compute.dao.ICmPortGroupDao;
import com.git.cloud.resmgt.compute.service.ICmPortGroupService;

public class CmPortGroupServiceImpl implements ICmPortGroupService{
	
	private ICmPortGroupDao cmPortGroupDao;
	
	public ICmPortGroupDao getCmPortGroupDao() {
		return cmPortGroupDao;
	}

	public void setCmPortGroupDao(ICmPortGroupDao cmPortGroupDao) {
		this.cmPortGroupDao = cmPortGroupDao;
	}
}
