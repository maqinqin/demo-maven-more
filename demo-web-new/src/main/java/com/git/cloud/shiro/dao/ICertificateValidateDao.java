package com.git.cloud.shiro.dao;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.shiro.model.CertificatePo;

public interface ICertificateValidateDao {

public	CertificatePo findCertificateName() throws RollbackableBizException;

public void saveTime(CertificatePo certificate)throws RollbackableBizException;

}
