package com.git.cloud.shiro.service;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.shiro.model.CertificatePo;

public interface IFileUpLoadService {
	public void saveCertificate(CertificatePo certificatePo)throws RollbackableBizException;
}
