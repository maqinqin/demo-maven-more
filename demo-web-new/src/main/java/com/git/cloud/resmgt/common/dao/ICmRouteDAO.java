package com.git.cloud.resmgt.common.dao;


import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.po.CmRoutePo;


public interface ICmRouteDAO extends ICommonDAO{

public	Pagination<CmRoutePo> queryPagination(PaginationParam pagination)throws RollbackableBizException;
public void insertCmRoute(CmRoutePo cmRoutePo)throws RollbackableBizException;
public void updateCmRoute(CmRoutePo cmRoutePo)throws RollbackableBizException;
public CmRoutePo getCmRouteById(String id)throws RollbackableBizException;
public void update(String[] ids)throws RollbackableBizException;


}
