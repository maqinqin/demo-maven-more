package com.git.cloud.workflow.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.model.po.BmToDoPo;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.service.IRequestBaseService;
import com.git.cloud.sys.dao.ISysRoleDAO;
import com.git.cloud.sys.model.po.SysUserPo;
import com.gitcloud.tankflow.dao.IBpmInstanceDao;
import com.gitcloud.tankflow.dao.IBpmInstanceOataskDao;
import com.gitcloud.tankflow.model.po.BpmInstanceOataskPo;
import com.gitcloud.tankflow.model.po.BpmInstancePo;
import com.gitcloud.tankflow.service.IWebBpmBusinessService;
import com.google.common.collect.Maps;

public class BpmBusinessTankflowServiceImpl implements IWebBpmBusinessService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private IBpmInstanceOataskDao bpmInstanceOataskDao;
	private IBpmInstanceDao bpmInstanceDao;
	private IBmSrDao bmSrDao;
	private IRequestBaseService requestBaseService;
	private ISysRoleDAO sysRoleDAO;

	@Override
	public String newOaTask(String instanceId, long wfNodeId, String taskInstanceId, String sourceList, long tokenId) throws com.gitcloud.tankflow.common.exception.RollbackableBizException {
		/** 获取人工任务节点信息 */
		//		List listNode = bpmModelOanodeDAO.getTaskNode(instanceId,wfNodeId);
		List listUser = new ArrayList();
		Map<String,Object> paramsNode = Maps.newHashMap();
		paramsNode.put("instanceId", instanceId);
		paramsNode.put("wfNodeId", wfNodeId);
		List<BpmInstanceOataskPo> oaTaskNodeL = bpmInstanceOataskDao.findInstanceOataskListByParam(paramsNode);
		BpmInstanceOataskPo oaTaskNode = null;
		if((oaTaskNodeL!=null)&&!oaTaskNodeL.isEmpty()){
			oaTaskNode = oaTaskNodeL.get(0);
		}
		/** 获取流程实例信息 */
		BpmInstancePo instancePo = null;
		instancePo = bpmInstanceDao.findInstancePoInfoById(instanceId);
		
		/** 上级处理人意见 */
		StringBuffer checkMsg = new StringBuffer();
		if(oaTaskNode != null){
			String key_word =String.valueOf(oaTaskNode.getKeyWord());
			String node_name = String.valueOf(oaTaskNode.getOanodeName());
			
			BmToDoPo workItem = new BmToDoPo();
			workItem.setTodoId(UUIDGenerator.getUUID());
			workItem.setSrId(instancePo.getServiceReqId());
			
			//如果特殊角色为-1，则需要处理用户组和处理人，否则选择特殊角色为处理人
			String[] strArr = oaTaskNode.getKeyWord().split(",");
			if(oaTaskNode.getKeyWord().startsWith("-1")){
				workItem.setCurrentGroupId(strArr[1]);
				//如果strArr[2] == "-1"说明没选择操作人，当前操作人设置为""
				workItem.setCurrentUserId(strArr[2].equals("-1") ? "" : strArr[2]);
			}else{
				//选择特殊角色为处理人:目前只处理创建人
				workItem.setCurrentGroupId("");
				//取出创建人
				BmSrVo srVo = null;
				try {
					srVo = bmSrDao.findBmSrVoById(instancePo.getServiceReqId());
				} catch (RollbackableBizException e) {
					// TODO Auto-generated catch block
					logger.error("异常exception",e);
				}
				workItem.setCurrentUserId(srVo.getCreatorId());
			}
			workItem.setTodoStatus("0");
			workItem.setTaskId(taskInstanceId);
			workItem.setNodeId(String.valueOf(wfNodeId));
			workItem.setInstanceId(instanceId);
			workItem.setCurrentStep(node_name);
			workItem.setPageUrl(oaTaskNode.getUrl());
			workItem.setCreateTime((new Timestamp(System.currentTimeMillis())));
			try {
				requestBaseService.insertBmToDo(workItem);
			} catch (RollbackableBizException e) {
				// TODO Auto-generated catch block
				logger.error("异常exception",e);
			}
		}
		return JSON.toJSONString(listUser);
	}
	
	/**
	 * 根据角色得到用户列表
	 * @return
	 * @throws RollbackableBizException
	 */
	@Override
	public List<SysUserPo> findSysUserByRoleId(String roleId) {
		List<SysUserPo> list = null;
		try {
			list = sysRoleDAO.findSysUserByRoleId(roleId);
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
		}
		return list;
	}
	
	public IBpmInstanceOataskDao getBpmInstanceOataskDao() {
		return bpmInstanceOataskDao;
	}
	public void setBpmInstanceOataskDao(IBpmInstanceOataskDao bpmInstanceOataskDao) {
		this.bpmInstanceOataskDao = bpmInstanceOataskDao;
	}
	public IBpmInstanceDao getBpmInstanceDao() {
		return bpmInstanceDao;
	}
	public void setBpmInstanceDao(IBpmInstanceDao bpmInstanceDao) {
		this.bpmInstanceDao = bpmInstanceDao;
	}
	public IBmSrDao getBmSrDao() {
		return bmSrDao;
	}
	public void setBmSrDao(IBmSrDao bmSrDao) {
		this.bmSrDao = bmSrDao;
	}
	public IRequestBaseService getRequestBaseService() {
		return requestBaseService;
	}
	public void setRequestBaseService(IRequestBaseService requestBaseService) {
		this.requestBaseService = requestBaseService;
	}

	public ISysRoleDAO getSysRoleDAO() {
		return sysRoleDAO;
	}

	public void setSysRoleDAO(ISysRoleDAO sysRoleDAO) {
		this.sysRoleDAO = sysRoleDAO;
	}

}
