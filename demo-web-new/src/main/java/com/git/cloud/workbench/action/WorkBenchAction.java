package com.git.cloud.workbench.action;

import java.util.ArrayList;
import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudSoftware;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.workbench.service.IDateCenterService;
import com.git.cloud.workbench.vo.DateCenterVo;

public class WorkBenchAction extends BaseAction {

	private IDateCenterService iDateCenterService;
	
	
	public void setiDateCenterService(IDateCenterService iDateCenterService) {
		this.iDateCenterService = iDateCenterService;
	}


	/**
	 * 数据中心计算
	 * @throws Exception
	 */
	public void getDateCenter() throws Exception{
		//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//		ArrayList<DateCenterVo> list = (ArrayList<DateCenterVo>) iDateCenterService.showDataCenter();
//		Pagination<CloudSoftware> pagination = softWareService.showSoftwareAll(this.getPaginationParam());
//		this.jsonOut(pagination);
		this.arrayOut(iDateCenterService.showDataCenter());
	}

}
