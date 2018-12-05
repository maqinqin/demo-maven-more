package com.git.cloud.common.tools;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.interceptor.SysOperLogResolve;
import com.git.cloud.common.tools.init.SysCacheInit;

/**
 * 系统启动初始化
 * @author Sunhailong
 *
 */
public class SysStartInit {
	public void init() throws RollbackableBizException {
		// 加载系统操作日志配置
		SysOperLogResolve.initSysOperLogMap();
		// 加载全局参数缓存(ADMIN_PARAM表)
		SysCacheInit sysCacheInit = new SysCacheInit();
		sysCacheInit.adminParamInit();
	}
}
