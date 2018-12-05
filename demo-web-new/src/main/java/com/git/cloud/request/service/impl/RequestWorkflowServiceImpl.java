package com.git.cloud.request.service.impl;

import java.util.HashMap;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.dao.IBmSrTypeWfRefDao;
import com.git.cloud.request.dao.IBmToDoDao;
import com.git.cloud.request.model.DriveWfTypeEnum;
import com.git.cloud.request.model.TodoStatusCodeEnum;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.request.model.po.BmSrTypeWfRefPo;
import com.git.cloud.request.model.po.BmToDoPo;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.service.IRequestWorkflowService;
import com.git.cloud.workflow.service.IRequestService;
import com.gitcloud.tankflow.dao.IBpmModelDao;
import com.gitcloud.tankflow.model.po.BpmModelPo;
import com.gitcloud.tankflow.util.Constants;

/**
 * 云服务申请的工作流统一接口
 * @ClassName:RequestWorkflowServiceImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class RequestWorkflowServiceImpl implements IRequestWorkflowService {
	
	private IRequestService requestService;
	private IBmSrDao bmSrDao;
	private IBmSrRrinfoDao bmSrRrinfoDao;
	private IBmToDoDao bmToDoDao;
	private IBmSrTypeWfRefDao bmSrTypeWfRefDao;
	private IBpmModelDao bpmModelDao;

	public String createMainWorkflowInstance(String srId, String srCode, HashMap<String, String> wfParam) throws Exception {
		if(wfParam == null) {
			wfParam = new HashMap<String, String> ();
		}
		wfParam.put(Constants.SRV_REQ_ID, srId);
		BmSrVo bmSr = bmSrDao.findBmSrVoById(srId);
		if(bmSr == null) {
			throw new RollbackableBizException("创建流程失败，无效的服务请求单号" + srCode);
		}
		BmSrTypeWfRefPo bmSrTypeWfRef = bmSrTypeWfRefDao.findBmSrTypeWfRefBySrTypeMark(bmSr.getSrTypeMark());
		if(bmSrTypeWfRef == null) {
			throw new RollbackableBizException("创建流程失败，没有配置当前申请流程");
		}
		BpmModelPo model = bpmModelDao.findBpmModelByTempleId(bmSrTypeWfRef.getTemplateId());
		if(model == null) {
			throw new RollbackableBizException("创建流程失败，获取不到流程模版信息");
		}
		return requestService.newProcessInstanceFromReq(model.getModelId(), "", wfParam);
	}
	
	public void startMainWorkflowInstance(String instanceId) throws Exception {
		requestService.startProcessInstanceFromReq(instanceId);
	}

	public String createSubWorkflowInstance(String rrinfoId, HashMap<String, String> wfParam) throws Exception {
		if(wfParam == null) {
			wfParam = new HashMap<String, String> ();
		}
		BmSrRrinfoPo rrinfo = bmSrRrinfoDao.findBmSrRrinfoById(rrinfoId);
		if(rrinfo == null) {
			throw new RollbackableBizException("创建子流失败，查询不到资源请求");
		}
		return this.createSubWorkflowInstance(rrinfo.getSrId(), rrinfoId, rrinfo.getFlowId(), wfParam);
	}
	
	public String createSubWorkflowInstance(String srId, String rrinfoId, String flowId, HashMap<String, String> wfParam) throws Exception {
		if(wfParam == null) {
			wfParam = new HashMap<String, String> ();
		}
		wfParam.put(Constants.SRV_REQ_ID, srId);
		wfParam.put(Constants.SRV_RES_REQ_ID, rrinfoId);
		return requestService.newProcessInstanceFromReq(flowId, "", wfParam);
	}
	
	public void startSubWorkflowInstance(String instanceId) throws Exception {
		requestService.startProcessInstanceFromReq(instanceId);
	}
	
	public void driveWorkflow(String todoId, String driveWfType) throws Exception {
		BmToDoPo bmToDo = bmToDoDao.findBmToDoById(todoId);
		if(bmToDo == null) {
			throw new RollbackableBizException("获取不到待办信息");
		}
		if(bmToDo.getTodoStatus().equals(TodoStatusCodeEnum.TODO_STATUS_DEAL.getValue())) {
			throw new RollbackableBizException("此待办已处理");
		}
		if(driveWfType == null || "".equals(driveWfType)) {
			throw new RollbackableBizException("传入的驱动类型为空");
		} else if(driveWfType.equals(DriveWfTypeEnum.DRIVE_WF_SUBMIT.getValue())) {
			requestService.commitTask(bmToDo.getTaskId(), bmToDo.getNodeId());
		} else if(driveWfType.equals(DriveWfTypeEnum.DRIVE_WF_AGREE.getValue())) {
			requestService.agreeTask(bmToDo.getTaskId(), bmToDo.getNodeId());
		} else if(driveWfType.equals(DriveWfTypeEnum.DRIVE_WF_DISAGREE.getValue())) {
			requestService.disagreeTask(bmToDo.getTaskId(), bmToDo.getNodeId());
		}
		bmToDoDao.updateBmToDoStatus(todoId, TodoStatusCodeEnum.TODO_STATUS_DEAL.getValue());
	}

	public void setRequestService(IRequestService requestService) {
		this.requestService = requestService;
	}
	public void setBmSrDao(IBmSrDao bmSrDao) {
		this.bmSrDao = bmSrDao;
	}
	public void setBmSrRrinfoDao(IBmSrRrinfoDao bmSrRrinfoDao) {
		this.bmSrRrinfoDao = bmSrRrinfoDao;
	}
	public void setBmToDoDao(IBmToDoDao bmToDoDao) {
		this.bmToDoDao = bmToDoDao;
	}
	public void setBmSrTypeWfRefDao(IBmSrTypeWfRefDao bmSrTypeWfRefDao) {
		this.bmSrTypeWfRefDao = bmSrTypeWfRefDao;
	}
	public void setBpmModelDao(IBpmModelDao bpmModelDao) {
		this.bpmModelDao = bpmModelDao;
	}
}
