package com.git.cloud.request.api;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.request.model.SrStatusCodeEnum;
import com.git.cloud.request.service.IRequestBaseService;
import com.git.cloud.request.service.IVirtualExtendService;
import com.git.support.constants.PubConstants;

/**
 * 虚机扩容自动预分配接口
 * @ClassName:ExtendAssignHandler
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class ExtendAssignHandler {
	
	private static Logger logger = LoggerFactory.getLogger(ExtendAssignHandler.class);
	
	public String execute(HashMap<String, Object> contenxtParams) {
		IRequestBaseService requestBaseService = (IRequestBaseService) WebApplicationManager.getBean("requestBaseServiceImpl");
		String srId = (String) contenxtParams.get("srvReqId");
		try {
			IVirtualExtendService virtualExtendServiceImpl = (IVirtualExtendService) WebApplicationManager.getBean("virtualExtendServiceImpl");
			virtualExtendServiceImpl.updateResourceAssign(srId);
			requestBaseService.updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_ASSIGN_SUCCESS.getValue());
		} catch (Exception e) {
			logger.error("异常exception",e);
			logger.error("Extend assign handler error :" + e.getLocalizedMessage());
			try {
				requestBaseService.updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_ASSIGN_FAILURE.getValue());
				if(e.getMessage() == null) {
					requestBaseService.updateAssignResult(srId, e + "发生异常，异常信息为空");
				} else {
					requestBaseService.updateAssignResult(srId, e.getMessage());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("Extend assign handler updateAssignResult error :" + e.getLocalizedMessage());
			}
			return PubConstants.EXEC_RESULT_FAIL;
		}
		return PubConstants.EXEC_RESULT_SUCC;
	}
}
