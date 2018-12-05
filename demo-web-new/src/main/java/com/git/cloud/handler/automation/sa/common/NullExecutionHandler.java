package com.git.cloud.handler.automation.sa.common;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.sa.ssh.CommandExecutionHandler;
import com.git.support.common.MesgRetCode;
import com.git.support.sdo.inf.IDataObject;

public class NullExecutionHandler extends RemoteAbstractAutomationHandler {

	private static Logger logger = LoggerFactory.getLogger(CommandExecutionHandler.class);
	
	@Override
	public String execute(HashMap<String, Object> contenxtParmas) {
		
		logger.debug("This is a null method.");
		try {
			Long timeOut = getContextLongPara(contenxtParmas, "WAIT_TIME");
			logger.debug("This is a null method. wait " + timeOut);
			Thread.sleep(timeOut);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
		}
		return MesgRetCode.SUCCESS;
	}

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas, IDataObject responseDataObject) {
		// TODO Auto-generated method stub

	}

}
