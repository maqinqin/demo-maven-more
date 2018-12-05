package com.git.cloud.resmgt.common.service;


import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.po.CmRoutePo;


public interface ICmRouteService extends IService{

	public  Pagination<CmRoutePo> queryPagination(PaginationParam pagination)throws RollbackableBizException;

	public void saveCmRoute(CmRoutePo cmRoutePo)throws RollbackableBizException;

	public void updateCmRoute(CmRoutePo cmRoutePo)throws RollbackableBizException;

	public CmRoutePo getCmRouteById(String id)throws RollbackableBizException;

	public void deleteCmRoute(String[] ids)throws RollbackableBizException;

	
	





}
