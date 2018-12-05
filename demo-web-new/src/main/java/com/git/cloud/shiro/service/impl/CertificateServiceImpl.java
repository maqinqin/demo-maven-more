package com.git.cloud.shiro.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.shiro.dao.ICertificateValidateDao;
import com.git.cloud.shiro.model.CertificatePo;
import com.git.cloud.shiro.service.ICertificateValidateService;

public class CertificateServiceImpl implements ICertificateValidateService{
	private ICertificateValidateDao  certificateValidateDao;

	public ICertificateValidateDao getCertificateValidateDao() {
		return certificateValidateDao;
	}

	public void setCertificateValidateDao(
			ICertificateValidateDao certificateValidateDao) {
		this.certificateValidateDao = certificateValidateDao;
	}

	@Override
	public CertificatePo findCertificateName()throws RollbackableBizException  {
		// TODO Auto-generated method stub
		return certificateValidateDao.findCertificateName();
	}

	@Override
	public void saveTime(CertificatePo certificate)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		certificateValidateDao.saveTime(certificate);
	}




	



}
