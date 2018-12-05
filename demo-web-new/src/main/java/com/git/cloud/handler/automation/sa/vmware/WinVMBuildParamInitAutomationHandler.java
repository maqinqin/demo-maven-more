package com.git.cloud.handler.automation.sa.vmware;

import java.util.Map;

import com.git.cloud.common.exception.BizException;
import com.git.support.common.VMFlds;
import com.git.support.common.VmGlobalConstants;
import com.git.support.constants.SAConstants;
import com.google.common.collect.Maps;

/**
 * Windows虚拟机构建所需要的参数
 * <p>
 * 
 * @author mengzx
 * @version 1.0 2013-5-7
 * @see
 */
public class WinVMBuildParamInitAutomationHandler extends VMBuildParamInitAutomationHandler {

	/**
	 * 获取操作系统类型
	 * 
	 * @return
	 */
	@Override
	public String getVmType() {

		return VmGlobalConstants.VM_TYPE_WIN;
	}

	/**
	 * 获取操作系统的默认用户名称
	 * 
	 * @return
	 * @throws Exception
	 * @throws BizException
	 */
	@Override
	public String getVmUserName() throws BizException, Exception {
		return getAutomationService().getAppParameter("VM.WIN_DEFAULT_USER_NAME");
	}

	/**
	 * 不同类型的虚拟的差异性参数
	 * 
	 * @return
	 * @throws Exception 
	 * @throws BizException 
	 */
	public Map<String,String> getExtDeviceParam() throws BizException, Exception{
		
		Map<String,String> extDeviceParam = Maps.newHashMap();
		extDeviceParam.put(SAConstants.V_CHG_USER_NAME,
				getAutomationService().getAppParameter("VM.WIN_DEFAULT_USER_NAME"));// 需修改密码的用户名称
		extDeviceParam.put(SAConstants.V_CHG_USER_PASSWD,
				getAutomationService().getAppParameter("VM.WIN_DEFAULT_NEW_PASSWD"));// 新的密码
		extDeviceParam.put(VMFlds.V_CONNECTION_NAME, getAutomationService().getAppParameter("VM.V_CONNECTION_NAME"));
		
		return extDeviceParam;
	}
}
