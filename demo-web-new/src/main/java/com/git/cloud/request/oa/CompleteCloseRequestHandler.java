package com.git.cloud.request.oa;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.service.IRequestBaseService;
import com.git.support.constants.PubConstants;
/**
 * 自动执行实施完成和验证关单逻辑的handler
 * 	
 * @author wangmingyue
 *
 */
public class CompleteCloseRequestHandler {
	private static Logger logger = LoggerFactory.getLogger(CompleteCloseRequestHandler.class);
	IRequestBaseService requestBaseService = (IRequestBaseService) WebApplicationManager.getBean("requestBaseServiceImpl");
	public String execute(HashMap<String, Object> contenxtParams) {
		String srId = (String) contenxtParams.get("srvReqId");
		BmSrVo bmSrVo = null;
		String srType = "";
		try {
			bmSrVo =requestBaseService.findBmSrVoById(srId);
			
			if(bmSrVo != null){
				srType = bmSrVo.getSrTypeMark();
				}
			logger.info("服务申请srId："+srId+",服务类型："+srType);
			requestBaseService.saveOperateEnd(srId,"",srType);
			requestBaseService.closeRequestSr(srId,"");
		} catch (Exception e) {
			logger.error("执行自动实施和验证关单失败：",e);
			return PubConstants.EXEC_RESULT_FAIL;
			
		}
		return PubConstants.EXEC_RESULT_SUCC;
	}
}
