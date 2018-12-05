package com.git.cloud.workbench.action;

import java.util.ArrayList;
import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudSoftware;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.workbench.service.IDateCenterService;
import com.git.cloud.workbench.service.IResourcePoolService;
import com.git.cloud.workbench.vo.DateCenterVo;
import com.git.cloud.workbench.vo.ResourcePoolVo;

public class ResourcePoolAction extends BaseAction {


	private IResourcePoolService iResourcePoolService;
	
	private String id;
	
	
	




	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public void setiResourcePoolService(IResourcePoolService iResourcePoolService) {
		this.iResourcePoolService = iResourcePoolService;
	}



	/**
	 * 资源池计算
	 * @throws Exception
	 */
	public void getResourcePool()throws Exception{
		//System.out.println("进入到了ResourcePoolACTION");
		ArrayList<ResourcePoolVo> list = (ArrayList<ResourcePoolVo>) iResourcePoolService.showResourcePoolById(id);
//		Pagination<CloudSoftware> pagination = softWareService.showSoftwareAll(this.getPaginationParam());
//		this.jsonOut(pagination);
		this.arrayOut(list);
	}
}
