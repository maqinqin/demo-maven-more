package com.git.cloud.resmgt.common.action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.po.CmHostUsernamePasswordPo;
import com.git.cloud.resmgt.common.model.vo.CmHostVo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.common.service.ICmHostService;
import com.git.cloud.resmgt.compute.handler.PmHandlerService;

/**
 * @ClassName:RmVmManageServerAction
 * @Description:虚拟管理机信息
 * @author lizhizhong
 * @date 2014-12-17 下午1:33:36
 *
 *
 */
public class RmPhysicalMacManageServerAction extends BaseAction<Object> {

	
	private static Logger logger = LoggerFactory.getLogger(RmPhysicalMacManageServerAction.class);
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = 1L;
	
	private ICmHostService cmHostServiceImpl;
	
	private String resourceId;
	private String hostId;
	
	private CmHostVo hostVo = new CmHostVo();
	
	private CmHostUsernamePasswordPo cupp = new CmHostUsernamePasswordPo();
	
	
	private ICmDeviceService cmDeviceServiceImpl ;
	
	private PmHandlerService pmHandlerServiceImpl ;
	
	
	public ICmDeviceService getCmDeviceServiceImpl() {
		return cmDeviceServiceImpl;
	}

	public void setCmDeviceServiceImpl(ICmDeviceService cmDeviceServiceImpl) {
		this.cmDeviceServiceImpl = cmDeviceServiceImpl;
	}

	public PmHandlerService getPmHandlerServiceImpl() {
		return pmHandlerServiceImpl;
	}

	public void setPmHandlerServiceImpl(PmHandlerService pmHandlerServiceImpl) {
		this.pmHandlerServiceImpl = pmHandlerServiceImpl;
	}

	public CmHostUsernamePasswordPo getCupp() {
		return cupp;
	}
	
	public void setCupp(CmHostUsernamePasswordPo cupp) {
		this.cupp = cupp;
	}
	
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public ICmHostService getCmHostServiceImpl() {
		return cmHostServiceImpl;
	}

	public void setCmHostServiceImpl(ICmHostService cmHostServiceImpl) {
		this.cmHostServiceImpl = cmHostServiceImpl;
	}

	public CmHostVo getHostVo() {
		return hostVo;
	}

	public void setHostVo(CmHostVo hostVo) {
		this.hostVo = hostVo;
	}
	
	public String getHostId() {
		return hostId;
	}
	
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	/**
	 * 物理机管理页面
	 * @return
	 */

	
	public String hostManageView() {
		return SUCCESS;
	}
	
	/**
	 * 得到物理主机列表
	 */
	public void getHostList() throws Exception{
		this.jsonOut(cmHostServiceImpl.getHostList(this.getPaginationParam()));
	}
	
	/**
	 * 得到用户名密码信息
	 */
	public void getUserNamePasswdInfo() throws Exception{
		
		this.jsonOut(cmHostServiceImpl.getUserInfo(this.getResourceId()));
		

	}
	
	/**
	 * 修改用户名密码
	 */
	public void updateHostPasswd() throws Exception{
		try {
			String result = cmHostServiceImpl.updateUserNamePasswd(cupp);
			this.stringOut(result);
		} catch (Exception e) {
			logger.error("删除失败:"+e);
			
		}
	}
}
