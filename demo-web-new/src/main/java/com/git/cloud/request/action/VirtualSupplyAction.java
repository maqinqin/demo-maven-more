package com.git.cloud.request.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.request.model.vo.VirtualSupplyVo;
import com.git.cloud.request.service.IVirtualSupplyService;

/**
 * 云服务供给Action
 * @ClassName:VirtualSupplyAction
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class VirtualSupplyAction extends BaseAction<Object> {

	private static Logger logger = LoggerFactory.getLogger(VirtualSupplyAction.class);
	private static final long serialVersionUID = 1L;
	private IVirtualSupplyService virtualSupplyService;
	private VirtualSupplyVo virtualSupplyVo;
	
	/**
	 * 查找供给服务申请
	 * @author djq
	 */
	public void findVirtualSupplyById(){
		try {
			String srId = this.getRequest().getParameter("srId");
			VirtualSupplyVo virtualSupplyVo = virtualSupplyService.findVirtualSupplyById(srId);
			this.jsonOut(virtualSupplyVo);
		} catch (Exception e) {
			logger.error("查找供给服务申请时发生异常:"+e);
		}
	}
	
	public VirtualSupplyVo getVirtualSupplyVo() {
		return virtualSupplyVo;
	}
	public void setVirtualSupplyVo(VirtualSupplyVo virtualSupplyVo) {
		this.virtualSupplyVo = virtualSupplyVo;
	}
	public void setVirtualSupplyService(IVirtualSupplyService virtualSupplyService) {
		this.virtualSupplyService = virtualSupplyService;
	}
}