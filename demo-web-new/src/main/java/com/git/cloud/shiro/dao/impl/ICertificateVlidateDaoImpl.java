package com.git.cloud.shiro.dao.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.shiro.dao.ICertificateValidateDao;
import com.git.cloud.shiro.model.CertificatePo;

public class ICertificateVlidateDaoImpl extends CommonDAOImpl implements ICertificateValidateDao {

	@Override
	public CertificatePo findCertificateName()
			throws RollbackableBizException {
		return this.findObjectByID("selectCertificate", "1");
	}

	@Override
	public void saveTime(CertificatePo certificate)
			throws RollbackableBizException {
		this.save("saveTime", certificate);
	}

}
