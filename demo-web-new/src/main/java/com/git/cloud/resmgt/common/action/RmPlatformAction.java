package com.git.cloud.resmgt.common.action;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.service.IRmPlatformService;
import com.google.common.collect.Maps;
/**
 * 
 * @author 王成辉
 * @Package com.git.cloud.resmgt.common.action
 * @Description: 平台类型维护
 * @date 2015-3-16
 *
 */
public class RmPlatformAction extends BaseAction<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IRmPlatformService rmPlatformService;
	private RmPlatformPo rmPlatformPo;
	private RmVirtualTypePo rmVirtualTypePo;
	private List<Map<String, Object>> resultLst;
	
	public IRmPlatformService getRmPlatformService() {
		return rmPlatformService;
	}
	public void setRmPlatformService(IRmPlatformService rmPlatformService) {
		this.rmPlatformService = rmPlatformService;
	}
	public RmPlatformPo getRmPlatformPo() {
		return rmPlatformPo;
	}
	public void setRmPlatformPo(RmPlatformPo rmPlatformPo) {
		this.rmPlatformPo = rmPlatformPo;
	}
	public RmVirtualTypePo getRmVirtualTypePo() {
		return rmVirtualTypePo;
	}
	public void setRmVirtualTypePo(RmVirtualTypePo rmVirtualTypePo) {
		this.rmVirtualTypePo = rmVirtualTypePo;
	}
	/**
	 * 
	 * @throws Exception
	 * @Description进入列表页面
	 */
	public String RmPlatformView()  {
		return "success";
	}
	/**
	 * 
	 * @throws Exception
	 * @Description获取平台类型列表数据
	 */
	public void getPlatformList() throws Exception {
		this.jsonOut(rmPlatformService.getRmPlatformPagination(this.getPaginationParam()));
	}
    /**
     * 
     * @throws RollbackableBizException
     * @Description 添加平台类型记录
     */
	public void savaRmPlatform() throws RollbackableBizException{
		rmPlatformService.saveRmPlatform(rmPlatformPo);
	}
	/**
	 * 
	 * @throws RollbackableBizException
	 * @Description 修改平台烈性
	 */
	public void updateRmPlatform() throws RollbackableBizException{
		rmPlatformService.updateRmPlatform(rmPlatformPo);
	}
	/**
	 * 
	 * @throws Exception
	 * @Description查询平台类型
	 */
	public void selectRmPlatform() throws Exception{
		RmPlatformPo rmPlatformPo1=rmPlatformService.selectRmPlatform(rmPlatformPo.getPlatformId());
		jsonOut(rmPlatformPo1);
	}
	
	/**
	 * 
	 * @throws Exception
	 * @Description查询平台下拉框
	 */
	public void selectRmPlatformSel() throws Exception{
		resultLst = rmPlatformService.selectRmPlatformSel();
		this.arrayOut(resultLst);
	}
	
	 /**
		 * @throws Exception 
	     * @Description删除 
		 */
	public void deleteRmPlatform() throws Exception {
		String meg="";
		meg=rmPlatformService.isDeleteRmPlatform(rmPlatformPo.getPlatformId().split(","));
		if("".equals(meg)){
			meg=rmPlatformService.deleteRmPlatform(rmPlatformPo.getPlatformId().split(","));
		}
			Map<String, String> map = Maps.newHashMap();
			map.put("meg", meg);
			jsonOut(map);
		}
		
		/**
		 * 
		 * @throws Exception
		 * @Description查询平台类型
		 */
	public void selectRmPlatformForTrim() throws Exception{
			RmPlatformPo rmPlatformPoTrim=rmPlatformService.selectRmPlatformForTrim(rmPlatformPo.getPlatformCode());
			jsonOut(rmPlatformPoTrim);
			
		}
		/**
		 * 
		 * @throws Exception
		 * @Description 虚机类型列表
		 */
	public void getRmVirtualTypeList() throws Exception {
			this.jsonOut(rmPlatformService.getRmVirtualTypePagination(this.getPaginationParam()));
		} 
	/**
	 * 	
	 * @throws RollbackableBizException
	 * @Description 虚机类型添加
	 */
	public void savaVirtualType() throws RollbackableBizException{
		rmPlatformService.saveVirtualType(rmVirtualTypePo);
	}	
	/**
	 * 	
	 * @throws Exception
	 * @Description 虚机类型查询
	 */
	public void selectRmVirtualTypeForTrim() throws Exception{
		RmVirtualTypePo rmVirtualTypePo1=rmPlatformService.selectRmVirtualTypeForTrim(rmVirtualTypePo.getVirtualTypeCode());
		jsonOut(rmVirtualTypePo1);
	}	
	/**
	 * 
	 * @throws Exception
	 * @Description 虚机类型查询
	 */
    public void selectRmVirtualType() throws Exception{
    	RmVirtualTypePo rmVirtualTypePo2=rmPlatformService.selectRmVirtualType(rmVirtualTypePo.getVirtualTypeId());
    	jsonOut(rmVirtualTypePo2);
    }
    
	/**
	 * 
	 * @throws Exception
	 * @Description 虚机类型查询
	 */
    public void selectRmVirtualTypeSel() throws Exception{
    	resultLst = rmPlatformService.selectRmVirtualTypeSel(rmPlatformPo.getPlatformId());
    	arrayOut(resultLst);
    }
		
	public void updateRmVirtualType() throws RollbackableBizException{
		rmPlatformService.updateRmVirtualType(rmVirtualTypePo);
		
	}	
		
	public void deleteVirtualType() throws Exception{
		String meg="";
		meg=rmPlatformService.isDeleteVirtualType(rmVirtualTypePo.getVirtualTypeId());
		if("".equals(meg)){
			meg=rmPlatformService.deleteVirtualType(rmVirtualTypePo.getVirtualTypeId());
		}
		Map<String, String> map = Maps.newHashMap();
		map.put("meg", meg);
		jsonOut(map);
	}
	
	public void selectRmPlatformNameForTrim() throws Exception{
		RmPlatformPo rmPlatformPoTrim=rmPlatformService.selectRmPlatformNameForTrim(rmPlatformPo.getPlatformName());
		jsonOut(rmPlatformPoTrim);
		
	}
}
