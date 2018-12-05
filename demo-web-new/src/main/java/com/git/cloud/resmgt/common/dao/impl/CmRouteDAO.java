package com.git.cloud.resmgt.common.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.dao.ICmRouteDAO;
import com.git.cloud.resmgt.common.model.po.CmRoutePo;


public class CmRouteDAO extends CommonDAOImpl implements ICmRouteDAO{

	@Override
	public Pagination<CmRoutePo> queryPagination(PaginationParam pagination)throws RollbackableBizException {
		// TODO Auto-generated method stub
		 return this.pageQuery("selectRouteTotal", "selectRoutePage", pagination);
	}

	@Override
	public void insertCmRoute(CmRoutePo cmRoutePo)
			throws RollbackableBizException {
		this.save("insertCmRoute", cmRoutePo);
		
	}

	@Override
	public void updateCmRoute(CmRoutePo cmRoutePo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		this.update("updateCmRoute", cmRoutePo);
	}

	@Override
	public CmRoutePo getCmRouteById(String id) throws RollbackableBizException {
		// TODO Auto-generated method stub
		Map<String,String>  map=new HashMap<String,String>();
		map.put("id", id);
		CmRoutePo po=super.findObjectByMap("getObjectById", map);
		return po;
	}

	@Override
	public void update(String[] ids) throws RollbackableBizException {
		// TODO Auto-generated method stub
		 CmRoutePo cmRoute=new CmRoutePo();
		for(String id:ids){
			  
			   cmRoute.setId(id);
			   cmRoute.setIsActive(IsActiveEnum.NO.getValue());
			   this.update("updateCmRoutePo", cmRoute);
		}
		
		
	}

	/*@Override
	public void update(CmRoutePo cmRoute) throws RollbackableBizException {
		   update("updateCmRoutePo",cmRoute);
		
	}*/


	


}

