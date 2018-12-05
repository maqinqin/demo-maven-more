package com.git.cloud.resmgt.common.action;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.foundation.util.UUIDGenerator;

import com.git.cloud.resmgt.common.model.po.CmRoutePo;
import com.git.cloud.resmgt.common.service.ICmRouteService;

public class CmRouteAction extends BaseAction<Object>{
	
	private static final long serialVersionUID = 1L;
	private CmRoutePo  cmRoutePo=new CmRoutePo();
	private  ICmRouteService  cmRouteServiceImpl;
	@SuppressWarnings("rawtypes")
	private Pagination pagination;
	public CmRoutePo getCmRoutePo() {
		return cmRoutePo;
	}
	public void setCmRoutePo(CmRoutePo cmRoutePo) {
		this.cmRoutePo = cmRoutePo;
	}
	
	public ICmRouteService getCmRouteServiceImpl() {
		return cmRouteServiceImpl;
	}
	public void setCmRouteServiceImpl(ICmRouteService cmRouteServiceImpl) {
		this.cmRouteServiceImpl = cmRouteServiceImpl;
	}
	
	
	@SuppressWarnings("rawtypes")
	public Pagination getPagination() {
		return pagination;
	}
	@SuppressWarnings("rawtypes")
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public void getDeviceList() throws Exception{
		pagination = cmRouteServiceImpl.queryPagination(this.getPaginationParam());
		this.jsonOut(pagination);
	}
    public void saveCmRoute() throws Exception{
		
		String id=cmRoutePo.getId();
		
		if(id==null||"".equals(id)){
			cmRoutePo.setId(UUIDGenerator.getUUID());
			cmRoutePo.setIsActive(IsActiveEnum.YES.getValue());
			
			cmRouteServiceImpl.saveCmRoute(cmRoutePo);
			
		}else{
			cmRouteServiceImpl.updateCmRoute(cmRoutePo);
		}
    }
		
   public void viewCmRoute()throws Exception{
		    	 String id=cmRoutePo.getId();//获得前台传入的ID
		    	 CmRoutePo  po=cmRouteServiceImpl.getCmRouteById(id);
		    	  this.jsonOut(po);
		   } 
	
	
   public void  deleteCmRoute() throws Exception{
	 
	   cmRouteServiceImpl.deleteCmRoute(cmRoutePo.getId().split(","));
   }
	
	
	   public  String   init(){
			return "success";
		}
		
}
