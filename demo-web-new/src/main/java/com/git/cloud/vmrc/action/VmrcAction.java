package com.git.cloud.vmrc.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.vmrc.model.po.VmrcPo;
import com.git.cloud.vmrc.model.vo.VmrcVo;
import com.git.cloud.vmrc.service.VmrcService;

/**
* @ClassName: VmrcAction  
* @Description: vmware remote console action
* @author WangJingxin
* @date 2016年6月24日 下午3:40:59  
*
 */
public class VmrcAction extends BaseAction {
	
	/**
	* @Title: showVNC  
	* @Description: 显示noVNC页面
	* @param     设定文件  
	* @return void    返回类型  
	* @throws
	 */
	public void showConsole() throws Exception{
		try {
			// 查询vmrc信息
			VmrcPo po = vmrcServiceHandler.queryVmrcInfoByVmId(vmrcVo.getVmId());
			
			vmrcVo.setModeMask(2);
			vmrcVo.setMsgMode(1);
			vmrcVo.setAdvancedConfig("");
			
			vmrcVo.setConnectVmid("");
			vmrcVo.setConnectAllowSslErrors(true);
			vmrcVo.setConnectDatacenter("");
			vmrcVo.setConnectTicket("");
			
			vmrcVo.setConnectHost(po.getConnectHost());
			vmrcVo.setConnectThumbprint(po.getConnectThumbprint());
			vmrcVo.setConnectUsername(po.getConnectUsername());
			vmrcVo.setConnectPassword(po.getConnectPassword());
			vmrcVo.setConnectVmpath(po.getConnectVmpath());
			vmrcVo.setDevName(po.getDevName());

			this.jsonOut(vmrcVo);
		} catch (Exception e) {
			this.errorOut(e);
		}
		
	}
	
    public String index(){
		return SUCCESS;
	}
    
    private VmrcVo vmrcVo;
    @Autowired
    private VmrcService vmrcServiceHandler;

	public VmrcVo getVmrcVo() {
		return vmrcVo;
	}

	public void setVmrcVo(VmrcVo vmrcVo) {
		this.vmrcVo = vmrcVo;
	}

	public VmrcService getVmrcServiceHandler() {
		return vmrcServiceHandler;
	}

	public void setVmrcServiceHandler(VmrcService vmrcServiceHandler) {
		this.vmrcServiceHandler = vmrcServiceHandler;
	}
     
}
