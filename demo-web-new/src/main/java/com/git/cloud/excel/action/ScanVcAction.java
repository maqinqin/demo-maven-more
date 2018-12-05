package com.git.cloud.excel.action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.service.IScanVcService;

@SuppressWarnings({ "serial", "rawtypes" })
public class ScanVcAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(ScanVcAction.class);
	private IScanVcService scanVcService;

	/**
	 * 读取vc信息，启动扫描vc线程和db读取线程
	 * @throws RollbackableBizException 
	 * @throws Exception
	 */
	public void sync() throws RollbackableBizException {
		logger.info("读取vc信息");
		scanVcService.saveOrUpdateOrDelSyncData();
	}
	

	public void setScanVcService(IScanVcService scanVcService) {
		this.scanVcService = scanVcService;
	}
}