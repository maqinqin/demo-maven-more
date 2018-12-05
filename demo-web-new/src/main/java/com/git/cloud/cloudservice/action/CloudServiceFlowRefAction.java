package com.git.cloud.cloudservice.action;

import java.util.Map;

import com.git.cloud.cloudservice.model.po.CloudServiceFlowRefPo;
import com.git.cloud.cloudservice.service.ICloudServiceFlowRefService;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.support.Pagination;

public class CloudServiceFlowRefAction extends BaseAction<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1461015574729711914L;

	private ICloudServiceFlowRefService cloudServiceFlowRefService;
	private CloudServiceFlowRefPo cloudServiceFlowRefPo;
	private Pagination pagination;
	private Map<String, String> pageParams;
	private Map<String, Object> result;

	public String index() {
		return SUCCESS;
	}

	public String search() {
		pagination = cloudServiceFlowRefService.queryPagination(this.getPaginationParam());
		return SUCCESS;
	}

	public String save() throws Exception {

		if (cloudServiceFlowRefPo != null) {
			if (cloudServiceFlowRefPo.getServiceFlowId() == null || "".equals(cloudServiceFlowRefPo.getServiceFlowId())) {
				cloudServiceFlowRefService.save(cloudServiceFlowRefPo);
			} else {
				cloudServiceFlowRefService.update(cloudServiceFlowRefPo);
			}
		}
		return SUCCESS;
	}

	public String stop() throws Exception {
		if (cloudServiceFlowRefPo != null) {
			if (cloudServiceFlowRefPo.getServiceFlowId() != null && !"".equals(cloudServiceFlowRefPo.getServiceFlowId())) {
				cloudServiceFlowRefPo = cloudServiceFlowRefService.findById(cloudServiceFlowRefPo.getServiceFlowId());
				cloudServiceFlowRefPo.setIsActive("0");
				cloudServiceFlowRefService.update(cloudServiceFlowRefPo);
			}
		}
		return SUCCESS;
	}

	public String delete() throws Exception {
		if (cloudServiceFlowRefPo != null) {
			if (cloudServiceFlowRefPo.getServiceFlowId() != null && !"".equals(cloudServiceFlowRefPo.getServiceFlowId())) {
				String[] ids = new String[] { cloudServiceFlowRefPo.getServiceFlowId() };
				cloudServiceFlowRefService.deleteById(ids);
			}
		}
		return SUCCESS;
	}

	public String load() throws Exception {
		cloudServiceFlowRefPo = cloudServiceFlowRefService.findById(cloudServiceFlowRefPo.getServiceFlowId());
		return SUCCESS;
	}
	
	public void checkCloudServiceFlowRefs()throws Exception{
		super.ObjectOut(cloudServiceFlowRefService.checkCloudServiceFlowRefs(cloudServiceFlowRefPo));
	}

	public ICloudServiceFlowRefService getCloudServiceFlowRefService() {
		return cloudServiceFlowRefService;
	}

	public CloudServiceFlowRefPo getCloudServiceFlowRefPo() {
		return cloudServiceFlowRefPo;
	}

	public void FlowRefCloudServiceFlowRefPo(CloudServiceFlowRefPo cloudServiceFlowRefPo) {
		this.cloudServiceFlowRefPo = cloudServiceFlowRefPo;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void FlowRefPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Map<String, String> getPageParams() {
		return pageParams;
	}

	public void FlowRefPageParams(Map<String, String> pageParams) {
		this.pageParams = pageParams;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void FlowRefResult(Map<String, Object> result) {
		this.result = result;
	}

	public void setCloudServiceFlowRefService(ICloudServiceFlowRefService cloudServiceFlowRefService) {
		this.cloudServiceFlowRefService = cloudServiceFlowRefService;
	}

	public void setCloudServiceFlowRefPo(CloudServiceFlowRefPo cloudServiceFlowRefPo) {
		this.cloudServiceFlowRefPo = cloudServiceFlowRefPo;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setPageParams(Map<String, String> pageParams) {
		this.pageParams = pageParams;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

}
