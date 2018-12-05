package com.git.cloud.shiro.dao.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.shiro.dao.IFileUpLoadDao;
import com.git.cloud.shiro.model.CertificatePo;

public class IFileUpLoadDaoImpl extends CommonDAOImpl implements IFileUpLoadDao {
	@Override
	public void saveCertificate(CertificatePo certificatePo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		this.save("save.certificate", certificatePo);
	}
}
