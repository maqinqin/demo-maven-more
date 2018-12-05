/**
 * @Title:ResourceAction.java
 * @Package:com.git.cloud.resource.action
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午02:14:40
 * @version V1.0
 */
package com.git.cloud.resource.action;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.resource.model.po.MachineInfoPo;
import com.git.cloud.resource.model.po.VmInfoPo;
import com.git.cloud.resource.service.IResourceService;

/**
 * @ClassName:ResourceAction
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午02:14:40
 *
 */
public class ResourceAction extends BaseAction<Object> {

	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private IResourceService resourceService;
	
	public IResourceService getResourceService() {
		return resourceService;
	}
	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	public String index() {
		return SUCCESS;
	}
	/**
	 * @throws Exception 
	 * 
	 * @Title: queryMachineResource
	 * @Description: 查询物理机资源
	 * @field: 
	 * @return void
	 * @throws
	 */
	public void queryMachineResource() throws Exception {
		Pagination<MachineInfoPo> pagination = resourceService.queryMachineInfoPagination(this.getPaginationParam());
		jsonOut(pagination);
	}
	public void queryMachineInfo() throws Exception {
		String hostId = getRequest().getParameter("hostId");
		jsonOut(resourceService.queryMachineInfo(hostId));
	}
	/**
	 * @throws Exception 
	 * 
	 * @Title: queryVmResource
	 * @Description: 查询虚机资源
	 * @field: @return
	 * @return String
	 * @throws
	 */
	public void queryVmResource() throws Exception {
		Pagination<VmInfoPo> pagination = resourceService.queryVmInfoPagination(getPaginationParam());
		jsonOut(pagination);
	}
	public void queryVmInfo() throws Exception {
		String vmId = getRequest().getParameter("vmId");
		jsonOut(resourceService.queryVmInfo(vmId));
	}
	public void queryPool() throws Exception{
		String id = this.getRequest().getParameter("dataCenterId");
		arrayOut(resourceService.queryPool(id));
	}
	public void queryCluster() throws Exception{
		String id = this.getRequest().getParameter("poolId");
		arrayOut(resourceService.queryCluster(id));
	}
	
	public void queryDeployUnit() throws Exception {
		String id = getRequest().getParameter("appId");
		arrayOut(resourceService.queryDeployUnit(id));
	}
}
