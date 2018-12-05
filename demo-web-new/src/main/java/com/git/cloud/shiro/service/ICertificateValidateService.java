package com.git.cloud.shiro.service;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.shiro.model.CertificatePo;

public interface ICertificateValidateService {

public  CertificatePo findCertificateName()throws RollbackableBizException;

public void saveTime(CertificatePo certificate)throws RollbackableBizException;

}

