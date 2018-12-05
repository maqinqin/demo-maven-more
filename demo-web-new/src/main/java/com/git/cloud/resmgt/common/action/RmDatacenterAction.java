package com.git.cloud.resmgt.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmResPoolPo;
import com.git.cloud.resmgt.common.service.IRmDatacenterService;
import com.google.common.collect.Maps;

/**
 * @ClassName:RmDatacenterAction
 * @Description:TODO
 * @author mijia
 * @date 2014-12-17 上午11:45:07
 *
 *
 */
public class RmDatacenterAction extends BaseAction {

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private static final long serialVersionUID = 4969374643498441741L;
	
	private String dataCenterId;
	
	private IRmDatacenterService rmDataCenterService;
	
	private String id;
	
	private RmDatacenterPo rmDatacenterPo;

	public void setRmDataCenterService(IRmDatacenterService rmDataCenterService) {
		this.rmDataCenterService = rmDataCenterService;
	}

	public String getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}
	
	public RmDatacenterPo getRmDatacenterPo() {
		return rmDatacenterPo;
	}

	public void setRmDatacenterPo(RmDatacenterPo rmDatacenterPo) {
		this.rmDatacenterPo = rmDatacenterPo;
	}
    /**
     *  
     * @throws Exception
     * @Description查询出要修改的数据
     */
	public void getDataCenterById() throws Exception{
		RmDatacenterPo dc = rmDataCenterService.getDataCenterById(getDataCenterId());
		jsonOut(dc);
	}
	
	public void getDataCenters() throws Exception{
		List<RmDatacenterPo> dcs = rmDataCenterService.getDataCenters();
		arrayOut(dcs);
	}
	public String cmDatacenterView() {
		return "success";
	}
	/**
	 * 
	 * @throws Exception
	 * @Description获取数据中心列表数据
	 */
	public void getDatacenterList() throws Exception {
		this.jsonOut(rmDataCenterService.getDevicePagination(this.getPaginationParam()));
	}
	/**
	 * 
	 * @throws RollbackableBizException
	 * @Description添加数据
	 */
    public void saveRmDatacenter() throws RollbackableBizException{
    	rmDataCenterService.saveRmDatacenter(rmDatacenterPo);
    	
    }
	/**
	 * 
	 * @throws RollbackableBizException
	 * @Description修改数据
	 */
    public void updateRmDatacenter() throws RollbackableBizException{
    	rmDataCenterService.updateRmDatacenter(rmDatacenterPo);
    	
    }
    /**
     * 
     * @throws Exception
     * @Description查询数据中心下资源池数量和数据中心的名称
     */
    public void selectPoolByDatacenterId() throws Exception{
    	Map<String, String> map = Maps.newHashMap();
    	map=rmDataCenterService.selectPoolByDatacenterId(dataCenterId);
    	jsonOut(map);
    }
    /**
	 * @throws RollbackableBizException 
     * @Description删除数据中心 
	 */
	public void deleteDatacenter() throws RollbackableBizException {
		rmDataCenterService.deleteDatacenter(rmDatacenterPo.getId().split(","));
	}
	  /**
		 * @throws Exception 
	 * @Description验证数据中心标识不重复 
		 */
		public void selectQueueIdenfortrim() throws Exception {
			RmDatacenterPo datacenter=rmDataCenterService.selectQueueIdenfortrim(rmDatacenterPo.getQueueIden());
			jsonOut(datacenter);
		}
		 /**
		 * @throws Exception 
	 * @Description验证数据中心英文名称不重复 
		 */
		public void selectDCenamefortrim() throws Exception {
			RmDatacenterPo datacenter=rmDataCenterService.selectDCenamefortrim(rmDatacenterPo.getEname());
			jsonOut(datacenter);
		}
		
		/**
		 * @throws Exception
		 * @Description卷查询
		 */
		public void getDiskList() throws Exception {
			String jsonobj = rmDataCenterService.getDiskList();
			this.jsonOut(jsonobj);
		}
		
		public void getDiskDetailed() throws Exception {
			String jsonobj = rmDataCenterService.getDiskDetailed(id);
			this.jsonOut(jsonobj);
		}
}
