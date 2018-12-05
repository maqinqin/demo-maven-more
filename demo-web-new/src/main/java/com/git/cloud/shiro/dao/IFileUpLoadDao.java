package com.git.cloud.shiro.dao;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.shiro.model.CertificatePo;

public interface IFileUpLoadDao {
	public void saveCertificate(CertificatePo certificatePo) throws RollbackableBizException;
}
