package com.git.cloud.log.action;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.log.model.po.LogPo;
import com.git.cloud.log.service.ILogService;

/**
 * 日志管理
 * @description: 
 * @author: wangdy
 * @Date: Dec 29, 2014
 * @modify：
 * @version: 1.0
 * @Company: 高伟达软件股份有限公司
 */
public class LogAction extends BaseAction<Object>{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(LogAction.class);
	private ILogService logService;
	private LogPo logPo = new LogPo();
	
	/**
	 * 查询日志
	 * @Title: findLogPage
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void findLogPage() throws Exception {
		this.jsonOut(logService.findLogPage(this.getPaginationParam()));
	}
	
	/**
	 * 按logId查询日志
	 * @Title: findLogById
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void findLogById() throws Exception {
	    LogPo  po = logService.findLogById(logPo.getId());
		this.jsonOut(po);
    }
/*****************************************setter&&getter**************************************************/

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}
	
}
