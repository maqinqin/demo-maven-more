/**
 * @Title:ResourceAssignHandler.java
 * @Package:com.git.cloud.request.api
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-16 下午3:48:10
 * @version V1.0
 */
package com.git.cloud.request.api;

import java.util.HashMap;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.request.model.SrStatusCodeEnum;
import com.git.cloud.request.service.IRequestBaseService;
import com.git.cloud.request.service.IVirtualSupplyService;
import com.git.support.constants.PubConstants;

/**
 * 虚机供给自动预分配接口
 * @ClassName:ResourceAssignHandler
 * @Description:TODO
 * @author sunhailong
 * @date 2018-6-7 下午3:48:10
 */
public class ResourceAssignNewHandler {
	
	public String execute(HashMap<String, Object> contenxtParams) {
		IRequestBaseService requestBaseService = (IRequestBaseService) WebApplicationManager.getBean("requestBaseServiceImpl");
		String srId = (String) contenxtParams.get("srvReqId");
		String eStr = "";
		try {	
	        IVirtualSupplyService virtualSupplyServiceImpl = (IVirtualSupplyService) WebApplicationManager.getBean("virtualSupplyServiceImpl");
			eStr = virtualSupplyServiceImpl.doUpdateResourceAssignNew(srId);
			if("".equals(eStr)){
			    requestBaseService.updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_ASSIGN_SUCCESS.getValue());
		    } else{
				    requestBaseService.updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_ASSIGN_FAILURE.getValue());
					if(eStr == null) {
						requestBaseService.updateAssignResult(srId, "未知");
					} else {
						requestBaseService.updateAssignResult(srId, eStr);
					}
			  return PubConstants.EXEC_RESULT_FAIL;
		    }
		}catch (Exception e1) {
		    e1.printStackTrace();
		    return PubConstants.EXEC_RESULT_FAIL;
	    }
		return PubConstants.EXEC_RESULT_SUCC;
	}
}
