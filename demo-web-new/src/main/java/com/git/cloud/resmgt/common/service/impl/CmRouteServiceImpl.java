package com.git.cloud.resmgt.common.service.impl;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.dao.ICmRouteDAO;
import com.git.cloud.resmgt.common.model.po.CmRoutePo;

import com.git.cloud.resmgt.common.service.ICmRouteService;

public class CmRouteServiceImpl implements ICmRouteService{
	private ICmRouteDAO  cmRouteDAOImpl;

	public ICmRouteDAO getCmRouteDAOImpl() {
		return cmRouteDAOImpl;
	}

	public void setCmRouteDAOImpl(ICmRouteDAO cmRouteDAOImpl) {
		this.cmRouteDAOImpl = cmRouteDAOImpl;
	}

	@Override
	public Pagination<CmRoutePo> queryPagination(PaginationParam pagination) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return cmRouteDAOImpl.queryPagination(pagination);
	}

	@Override
	public void saveCmRoute(CmRoutePo cmRoutePo) throws RollbackableBizException {
		cmRouteDAOImpl.insertCmRoute(cmRoutePo);
		
	}

	@Override
	public void updateCmRoute(CmRoutePo cmRoutePo) throws RollbackableBizException {
		// TODO Auto-generated method stub
		cmRouteDAOImpl.updateCmRoute(cmRoutePo)	;	
	}

	@Override
	public CmRoutePo getCmRouteById(String id) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return cmRouteDAOImpl.getCmRouteById(id);
	}

	@Override
	public void deleteCmRoute(String[] ids) throws RollbackableBizException {
		// TODO Auto-generated method stub
		cmRouteDAOImpl.update(ids);
		
	}

	
	


}
