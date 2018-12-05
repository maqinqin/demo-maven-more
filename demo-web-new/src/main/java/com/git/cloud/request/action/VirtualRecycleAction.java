package com.git.cloud.request.action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.request.model.vo.VirtualRecycleVo;
import com.git.cloud.request.service.IVirtualRecycleService;

/**
 * 云服务回收Action
 * @ClassName:VirtualRecycleAction
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class VirtualRecycleAction extends BaseAction<Object> {

	private static Logger logger = LoggerFactory.getLogger(VirtualRecycleAction.class);
	private static final long serialVersionUID = 1L;
	
	private IVirtualRecycleService virtualRecycleService;
	
	
	/**
	 * 查找回收服务申请
	 * @author huangrongsheng
	 */
	public void getVirtualRecycleVoBySrId(){
		try {
			String srId = this.getRequest().getParameter("srId");
			VirtualRecycleVo virtualRecycleVo = virtualRecycleService.getVirtualRecycleVoBySrId(srId);
			this.jsonOut(virtualRecycleVo);
		} catch (Exception e) {
			logger.error("查找回收服务申请时发生异常:"+e);
		}
	}
	public void setVirtualRecycleService(
			IVirtualRecycleService virtualRecycleService) {
		this.virtualRecycleService = virtualRecycleService;
	}
}
