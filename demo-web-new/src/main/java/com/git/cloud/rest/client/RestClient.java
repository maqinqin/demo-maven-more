package com.git.cloud.rest.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RestClient {
	
	static Logger logger = LoggerFactory.getLogger(RestClient.class);
//	static MyLogger logger = MyLogger.getMyLoggerInstance(RestClient.class);

	public static RestResult sendRestRequest(RestModel restModel) throws Exception {
		String targetRUL = restModel.getTargetURL();
		RestClient client = null;
		if(targetRUL.startsWith("https")) {
			logger.debug("start https request...");
			//client = new HttpsRestClient();
			logger.debug("end https request...");
		} else if(targetRUL.startsWith("http")) {
			logger.debug("start http request...");
			client = new HttpRestClient();
			logger.debug("end http request...");
		}
		if(client == null) {
			String errorMsg = "the targetRUL[" + targetRUL + "] is not support!";
			logger.error(errorMsg);
			throw new Exception(errorMsg);
		}
		return client.restService(restModel);
	}
	
	protected abstract RestResult restService(RestModel restModel) throws Exception;
	
}
