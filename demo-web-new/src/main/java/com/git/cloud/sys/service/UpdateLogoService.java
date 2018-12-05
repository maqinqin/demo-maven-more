package com.git.cloud.sys.service;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.common.model.AdminParamPo;
import com.git.cloud.parame.model.po.ParameterPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.git.cloud.sys.model.vo.SelfMonitorVo;

public interface UpdateLogoService {
	
	/**
	 * 查询Logo图片
	 * @return 
	 */
	public List<ParameterPo> getLogo()throws Exception;
	
	/**
	 * 修改Logo图片
	 * @return
	 * @throws Exception
	 */
	public String insertLogo(ParameterPo parameterPo) throws Exception;
	
}
