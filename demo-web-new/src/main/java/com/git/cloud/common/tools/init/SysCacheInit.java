package com.git.cloud.common.tools.init;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.CacheTypeEnum;
import com.git.cloud.common.tools.SysCacheManager;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.parame.model.po.ParameterPo;
import com.git.cloud.parame.service.ParameterService;

public class SysCacheInit {
	
	public void adminParamInit() throws RollbackableBizException {
		ParameterService parameterService = (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
		if(parameterService != null) {
			// 查询系统参数，并放入缓存中
			List<ParameterPo> list = parameterService.getAdminParamList();
			for(ParameterPo param:list){
				SysCacheManager.put(CacheTypeEnum.SYSTEM_PARAMETER.getValue(), param.getParamName(), param.getParamValue());
			}
		}
	}
}
