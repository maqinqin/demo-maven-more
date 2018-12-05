package com.git.cloud.request.oa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.request.service.IRequestBaseService;
import com.git.support.constants.PubConstants;
import com.gitcloud.tankflow.model.po.BpmInstancePo;
import com.gitcloud.tankflow.service.IBpmInstanceService;
/**
 * 自动启动子流的handler
 * 先创建子流程，再启动子流程	
 * @author wangmingyue
 *
 */
public class AutorunSubWorkflowHandler {
	
	private static Logger logger = LoggerFactory.getLogger(AutorunSubWorkflowHandler.class);
	public String execute(HashMap<String, Object> contenxtParams) throws Exception {
		IRequestBaseService requestBaseService = (IRequestBaseService) WebApplicationManager.getBean("requestBaseServiceImpl");
		IBpmInstanceService bpmInstanceService =  (IBpmInstanceService) WebApplicationManager.getBean("bpmInstanceServiceImpl");
		String srId = (String) contenxtParams.get("srvReqId");
		String instanceId = "";
		if(!CommonUtil.isEmpty(srId)){
			HashMap paramMap = new HashMap<String,String>();
			paramMap.put("srId", srId);
			List subworkFlowList = requestBaseService.queryWorkflowLinkList( paramMap);
			if(subworkFlowList == null || subworkFlowList.isEmpty()){
				logger.error("queryWorkflowLinkList 查询子流程列表失败，srId："+srId);
				return PubConstants.EXEC_RESULT_FAIL;
			}
			List<BpmInstancePo> subInstanceList = bpmInstanceService.findSubInstanceListBySrId(srId);
			if(subInstanceList == null || subInstanceList.isEmpty()){
				logger.warn("findSubInstanceListBySrId 未查询到子流程实例列表，srId："+srId);
			}
			//未创建未启动过子流程，直接创建并启动流程即可
			if(subInstanceList == null || subInstanceList.size() == 0){
				for(int i=0;i<subworkFlowList.size();i++){
					Map map = (Map)subworkFlowList.get(i);
					String flowIdStr = map.get("flowId") + ":" + map.get("rrinfoId");
					try {
						logger.info("服务申请srId："+srId+",flowIdStr:"+flowIdStr);
						instanceId = requestBaseService.createInstanceAuto(srId, flowIdStr);
						if(instanceId !=null){
							requestBaseService.startInstance(instanceId);
						}else{
							 return PubConstants.EXEC_RESULT_FAIL;
						}
					} catch (Exception e) {
						logger.error("自启动流程失败：{}",e);
						return PubConstants.EXEC_RESULT_FAIL;
					}
				}
			}else {
				//需要启动的子流程数和子流程真正创建的流程数相等，就不用再创建子流程，只需判断流程状态是否启动
				if(subworkFlowList.size() == subInstanceList.size()){
					for(int i=0;i<subworkFlowList.size();i++){
						Map map = (Map)subworkFlowList.get(i);
						Integer instanceStateId = (Integer) map.get("instanceStateId");
						String instanceid = (String) map.get("instanceId");
						try {
							//instanceStateId 为0 ，标识子流程创建完成
							if(instanceStateId == 0){
								if(instanceid !=null){
									requestBaseService.startInstance(instanceId);
								}else{
									 return PubConstants.EXEC_RESULT_FAIL;
								}
							}
						} catch (Exception e) {
							logger.error("自启动流程失败：{}",e);
							return PubConstants.EXEC_RESULT_FAIL;
						}
					}
					
				}else if(subworkFlowList.size() > subInstanceList.size()){
				/**
				 * 需要启动的子流数大于真正创建的流程数，
				 * 说明还有子流程未被创建，需要找到未被创建的子流进行创建，再进行启动
				 * 判断已存在的子流是否已经启动，未启动要启动
				 */
					List<String> succInstancedIds = new ArrayList<String>();
					for(int i=0;i<subworkFlowList.size();i++){
						Map map = (Map)subworkFlowList.get(i);
						String flowIdStr = map.get("flowId") + ":" + map.get("rrinfoId");
						String rrinfoId = (String) map.get("rrinfoId");
						for(BpmInstancePo subInstance : subInstanceList){
							if(subInstance.getServiceResReqId().equals(rrinfoId)){
								succInstancedIds.add(rrinfoId);
								int subInstanceStateId = Integer.parseInt(subInstance.getInstanceStateId().toString());
								//流程创建，未启动，需要启动子流程
								if(subInstanceStateId == 0){
									String subInstanceId = subInstance.getInstanceId();
									if(subInstanceId !=null){
										requestBaseService.startInstance(subInstanceId);
									}else{
										logger.error("自启动子流程失败,InstanceId:"+subInstanceId);
										 return PubConstants.EXEC_RESULT_FAIL;
									}
								}
							}
						}
						//成功创建的子流程id中不包含该资源申请，直接创建并启动
						if(!succInstancedIds.contains(rrinfoId)){
							try {
								logger.info("服务申请srId："+srId+",flowIdStr:"+flowIdStr);
								instanceId = requestBaseService.createInstanceAuto(srId, flowIdStr);
								if(instanceId !=null){
									requestBaseService.startInstance(instanceId);
								}else{
									logger.error("自启动子流程失败,InstanceId:"+instanceId);
									 return PubConstants.EXEC_RESULT_FAIL;
								}
							} catch (Exception e) {
								logger.error("自启动流程失败：{}",e);
								return PubConstants.EXEC_RESULT_FAIL;
							}
						}
					}
				}
			}
		}else{
			logger.error("srId为空");
			return PubConstants.EXEC_RESULT_FAIL;
		}
		return PubConstants.EXEC_RESULT_SUCC;
	}

}
