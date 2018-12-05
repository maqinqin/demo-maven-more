package com.git.cloud.resmgt.common.service;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.po.CmHostUsernamePasswordPo;
import com.git.cloud.resmgt.common.model.vo.CmHostVo;

public interface ICmHostService extends IService{

	public void updateCmHostUsed() throws RollbackableBizException ;

	public Pagination<CmHostVo> getHostList(PaginationParam paginationParam) throws RollbackableBizException;

	public CmHostUsernamePasswordPo getUserInfo(String resourceId) throws RollbackableBizException;

	public String updateUserNamePasswd(CmHostUsernamePasswordPo cupp) throws RollbackableBizException;

}
