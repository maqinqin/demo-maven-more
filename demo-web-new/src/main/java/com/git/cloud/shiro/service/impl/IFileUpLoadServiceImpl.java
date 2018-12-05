package com.git.cloud.shiro.service.impl;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.shiro.dao.IFileUpLoadDao;
import com.git.cloud.shiro.model.CertificatePo;
import com.git.cloud.shiro.service.IFileUpLoadService;

public class IFileUpLoadServiceImpl implements IFileUpLoadService{
	private IFileUpLoadDao  fileUpLoadDao;
	
	public IFileUpLoadDao getFileUpLoadDao() {
		return fileUpLoadDao;
	}

	public void setFileUpLoadDao(IFileUpLoadDao fileUpLoadDao) {
		this.fileUpLoadDao = fileUpLoadDao;
	}

	@Override
	public void saveCertificate(CertificatePo certificatePo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		fileUpLoadDao.saveCertificate(certificatePo);
	}
	
}
