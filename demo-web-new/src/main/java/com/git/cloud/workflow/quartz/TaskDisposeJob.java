package com.git.cloud.workflow.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.gitcloud.tankflow.service.IBpmBusinessService;

public class TaskDisposeJob{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private IBpmBusinessService businessService; 
	
	public void execute(){
		try {
			businessService.exeAutoTask();//执行自动任务
			//businessService.exeSingleTask();//执行单步任务
			//businessService.subprocessExecute();//执行子流任务
		} catch (com.gitcloud.tankflow.common.exception.RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
	}

	public IBpmBusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(IBpmBusinessService businessService) {
		this.businessService = businessService;
	}
}
