	package com.git.cloud.resmgt.network.action;
	
	import java.util.Map;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.resmgt.network.model.po.RmNwSecurePo;
import com.git.cloud.resmgt.network.model.vo.RmNwSecureVo;
import com.git.cloud.resmgt.network.service.IRmNwSecureService;
	
	/**
	 * @Title 		RmNwCclassAction.java 
	 * @Package 	com.git.cloud.resmgt.network.action 
	 * @author 		SYP
	 * @date 		2014-9-11下午4:42:14
	 * @version 	1.0.0
	 * @Description 
	 *
	 */
	@SuppressWarnings({ "serial", "rawtypes" })
	public class RmNwSecureAction extends BaseAction {
	   private RmNwSecureVo  rmNwSecureVo=new RmNwSecureVo();
	   private RmNwSecurePo   rmNwSecurePo =new RmNwSecurePo();
		private IRmNwSecureService  rmNwSecureService;
		private Pagination pagination;
		public RmNwSecureVo getRmNwSecureVo() {
			return rmNwSecureVo;
		}
		public void setRmNwSecureVo(RmNwSecureVo rmNwSecureVo) {
			this.rmNwSecureVo = rmNwSecureVo;
		}
		
		public RmNwSecurePo getRmNwSecurePo() {
			return rmNwSecurePo;
		}
		public void setRmNwSecurePo(RmNwSecurePo rmNwSecurePo) {
			this.rmNwSecurePo = rmNwSecurePo;
		}
		public IRmNwSecureService getRmNwSecureService() {
			return rmNwSecureService;
		}
		public void setRmNwSecureService(IRmNwSecureService rmNwSecureService) {
			this.rmNwSecureService = rmNwSecureService;
		}
		public Pagination getPagination() {
			return pagination;
		}
		public void setPagination(Pagination pagination) {
			this.pagination = pagination;
		}
		
		
		//查询参数
		public void getSecureAreaList()throws Exception{
			pagination=rmNwSecureService.queryPagination(this.getPaginationParam());
			this.jsonOut(pagination);
		}
		
		public void saveSecureArea()throws Exception{
			
			String secureAreaId=rmNwSecurePo.getSecureAreaId();
			if(secureAreaId==null||"".equals(secureAreaId))
			{
			   rmNwSecurePo.setSecureAreaId(UUIDGenerator.getUUID());//获得主键
			   rmNwSecurePo.setIsActive(IsActiveEnum.YES.getValue());
			   rmNwSecureService.saveSecureArea(rmNwSecurePo);
			}else{
				rmNwSecureService.updateSecureArea(rmNwSecurePo);
			}
		}
		//安全区域删除检查
		public void  deleteCheckArea()throws Exception{
			 String ids=this.getRequest().getParameter("saIds");
			 Map<String,String>  map=rmNwSecureService.deleteCheckArea(ids.split(","));
			 this.jsonOut(map);
		}
		//安全区域验证成功后再进行安全区域的删除
		public void deleteArea()throws Exception{
			String secureAreaId= rmNwSecurePo.getSecureAreaId();
			rmNwSecureService.deleteSecureArea(secureAreaId.split(","));
		}
		
			//修改时先查询
		public void viewSecureArea()throws Exception{
			String secureAreaId=rmNwSecurePo.getSecureAreaId();
			RmNwSecurePo po=rmNwSecureService.getSecureAreaById(secureAreaId);
			this.jsonOut(po);
		}
		//安全层
	   public void 	getSecureTierList()throws Exception{
		   pagination=rmNwSecureService.queryPaginationTier(this.getPaginationParam());
			this.jsonOut(pagination);
	   }
	   //保存安全层名称
	   public void saveSecureTier()throws Exception{
		   String secureTierId=rmNwSecureVo.getSecureTierId();
		   if(secureTierId==null||"".equals(secureTierId)){
		   rmNwSecureVo.setSecureTierId(UUIDGenerator.getUUID());
		
		   rmNwSecureVo.setIsActive(IsActiveEnum.YES.getValue());
		   rmNwSecureService.saveSecureTier(rmNwSecureVo);
		   }else{
			   rmNwSecureService.updateSecureTier(rmNwSecureVo);
		   }
	   }
	  public void  viewSecureTier()throws Exception{
		  String secureTierId=rmNwSecureVo.getSecureTierId();
		  RmNwSecureVo  vo=rmNwSecureService.getSecureTierById(secureTierId);
		  this.jsonOut(vo);
	  }
		//安全层删除前验证
	  public void viewSecureTierCclass()throws Exception{
		    
		 Map<String,String> map=rmNwSecureService.deleteCheckTierById(rmNwSecureVo.getSecureTierId());
		 this.jsonOut(map);
	  
	  }
	  //安全层验证成功后删除安全层
	  public void deleteSecureTier()throws Exception{
		  String secureTierId=rmNwSecureVo.getSecureTierId();
		  rmNwSecureService.deleteSecureTier(secureTierId);
	  }
	}
