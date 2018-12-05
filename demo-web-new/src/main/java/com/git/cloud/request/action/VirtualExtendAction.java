package com.git.cloud.request.action;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.model.vo.VirtualExtendVo;
import com.git.cloud.request.model.vo.VirtualSupplyVo;
import com.git.cloud.request.service.IVirtualExtendService;

/**
 * 云服务扩容Action
 * @ClassName:VirtualExtendAction
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class VirtualExtendAction extends BaseAction<Object> {

	private static Logger logger = LoggerFactory.getLogger(VirtualExtendAction.class);
	private static final long serialVersionUID = 1L;
	
	private IVirtualExtendService virtualExtendService;
	private VirtualSupplyVo virtualSupplyVo;
	private IBmSrDao bmSrDao;
	
	/**
	 * 查找扩容服务申请
	 * @author huangrongsheng
	 */
	public void findVirtualExtendById(){
		try {
			String srId = this.getRequest().getParameter("srId");
			VirtualExtendVo virtualExtendVo = virtualExtendService.findVirtualExtendById(srId);
			this.jsonOut(virtualExtendVo);
		} catch (Exception e) {
			logger.error("查找扩容服务申请时发生异常:"+e);
		}
	}

	public void queryVEBmSrRrinfoList() throws Exception {
		this.jsonOut(virtualExtendService.queryVEBmSrRrinfoList(this.getPaginationParam()));
	}
	
	public void queryVmSrDeviceinfoList() throws Exception {
		this.jsonOut(virtualExtendService.queryVmSrDeviceinfoList(this.getPaginationParam()));
	}
	
	public void getHostName() throws Exception {
		HttpServletResponse response  = this.getResponse();
		response.setContentType("text/json;charset=utf-8");
		String deviceId = this.getRequest().getParameter("paramName");
		String hostName = virtualExtendService.getDeviceName(deviceId);
		response.getWriter().print(hostName);
	}
	
	public VirtualSupplyVo getVirtualSupplyVo() {
		return virtualSupplyVo;
	}
	public void setVirtualSupplyVo(VirtualSupplyVo virtualSupplyVo) {
		this.virtualSupplyVo = virtualSupplyVo;
	}
	public void setVirtualExtendService(IVirtualExtendService virtualExtendService) {
		this.virtualExtendService = virtualExtendService;
	}

	public IBmSrDao getBmSrDao() {
		return bmSrDao;
	}

	public void setBmSrDao(IBmSrDao bmSrDao) {
		this.bmSrDao = bmSrDao;
	}
	
}
