package com.git.cloud.request.oa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.request.service.IRequestBaseService;
import com.git.support.constants.PubConstants;
/**
 * 监控子流程是否都已结束
 * 	
 * @author wangmingyue
 *
 */
public class WaitForSubWorkFlowFinishHandler {
	private static Logger logger = LoggerFactory.getLogger(WaitForSubWorkFlowFinishHandler.class);
	IRequestBaseService requestBaseService = (IRequestBaseService) WebApplicationManager.getBean("requestBaseServiceImpl");
	public static final Long SLEEP_TIME  = 1 * 15 * 1000L; 
	public String execute(HashMap<String, Object> contenxtParams) {
		//服务申请id
		String srId = (String) contenxtParams.get("srvReqId");
		logger.info("WaitForSubWorkFlowFinishHandler srId："+srId);
		int instanceStateId = 0;
		String instanceId = "";
		//记录成功流程的id列表
		List<String> finishInstanceIds = new ArrayList<String>();
		HashMap<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("srId", srId);
		//查询子流程列表数
		List instanceList = requestBaseService.queryWorkflowLinkList( paramMap);
		if(instanceList == null || instanceList.isEmpty()){
			logger.error("查询流程列表失败，srId："+srId);
			return PubConstants.EXEC_RESULT_FAIL;
		}
		int instancesSize = instanceList.size();	
		
		//页面设置的超时时间，页面传递过来的是秒,转化为毫秒
		//Long timeOut =  instancesSize * Long.parseLong(contenxtParams.get("TIME_OUT").toString()) * 1000  ;
		try {
			while(true){
				Thread.sleep(SLEEP_TIME);//睡眠
				instanceList = requestBaseService.queryWorkflowLinkList( paramMap);
				for(int i=0;i<instanceList.size();i++){
					Map map = (Map)instanceList.get(i);
					instanceStateId = (Integer) map.get("instanceStateId");
					instanceId = (String) map.get("instanceId");
					if(!finishInstanceIds.contains(instanceId)){
						//instanceStateId为3（正常结束）或4（强制结束）
						if(instanceStateId == 3 || instanceStateId == 4){
							instanceId = (String) map.get("instanceId");
							finishInstanceIds.add(instanceId);
						}
					}
				}
				//子流程数等于成功流程数
				if(instancesSize == finishInstanceIds.size()){
					break;
				}
			}
		} catch (InterruptedException e) {
			logger.error("循环查询子流程异常",e);
			return PubConstants.EXEC_RESULT_FAIL;
		}
		if(instancesSize == finishInstanceIds.size()){
			return PubConstants.EXEC_RESULT_SUCC;
		}else{
			return PubConstants.EXEC_RESULT_FAIL;
		}
	}
}
