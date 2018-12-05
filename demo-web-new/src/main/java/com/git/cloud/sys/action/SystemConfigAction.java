package com.git.cloud.sys.action;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.interceptor.SysOperLogResolve;

public class SystemConfigAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	

    public   void  refreshSysOperLogCfg() throws Exception{
		SysOperLogResolve.initSysOperLogMap();
		
	}

   public String init(){
		return SUCCESS;
	}
	

}
