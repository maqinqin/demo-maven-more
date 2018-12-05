package com.git.cloud.handler.automation.sa.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.IAutomationHandler;


/**
 * 
 * <p>
 * 
 * @author zhuzhaoyong
 * @version 1.0 2013-7-23 
 * @see
 */
public class HandlerThread implements Callable<String> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	IAutomationHandler handler = null;
	Map<String, StringBuffer> results = null;
	HashMap<String, Object> contextParams = null;
	
	@Override
	public String call() {
		try {
			String result = handler.execute(contextParams);
			return result;
		} catch (Exception e) {
			logger.error("handle exec error:",e);
		} finally {
			if (contextParams != null) {
				contextParams.clear();
			}
		}
		return null;
	}

	public HandlerThread(IAutomationHandler handler, HashMap<String, Object> contextParams, Map<String, StringBuffer> results) {
		super();
		this.handler = handler;
		this.contextParams = contextParams;
		this.results = results;
	}

	public void setHandler(IAutomationHandler handler) {
		this.handler = handler;
	}

	public void setResults(Map<String, StringBuffer> results) {
		this.results = results;
	}

	public void setContextParams(HashMap<String, Object> contextParams) {
		this.contextParams = contextParams;
	}
}
