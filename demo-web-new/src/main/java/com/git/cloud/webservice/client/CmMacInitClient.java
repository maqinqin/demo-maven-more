package com.git.cloud.webservice.client;
import java.io.IOException;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.resmgt.common.CloudClusterConstants;
import com.git.cloud.resmgt.common.util.mapxml.MyHashMapObject;



public class CmMacInitClient extends WebApplicationObjectSupport{
	
	private static Logger logger = LoggerFactory.getLogger(CmMacInitClient.class);

	private WebClient initClient() {
		//取得要转发的目的地址
		ParameterService parameterService = (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
		String path = "";
		try {
			path = parameterService.getParamValueByName(CloudClusterConstants.COBBLER_FORWARD);
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
		}
		//"http://localhost:8080/cobbler-forward/rest/forwardServlet"
		return WebClient.create(path);
    }
    
    public void destory(){
    }
    

    public RtnMessage PostData(Map map) throws IOException {
    	WebClient client = initClient();
    	//生成可表示为XML的MAP
		MyHashMapObject myMap = new MyHashMapObject();
    	myMap.setMapProperty(map);

    	//调用远程
    	RtnMessage rtnMessage = client.path("/").accept(MediaType.APPLICATION_XML).post(myMap,RtnMessage.class);
    	
    	logger.info("CmMacInitClient PostData() RtnErrNo " + rtnMessage.getErrNo());
    	logger.info("CmMacInitClient PostData() RtnErrMsg " + rtnMessage.getErrMsg());
    	logger.info("CmMacInitClient PostData() RtnMap " + rtnMessage.getMap());
        return rtnMessage ;
    }
    
    public Object PutData(String paraMap){
    	WebClient client = initClient();
        return client.path("/").accept(MediaType.APPLICATION_XML).put(paraMap).getEntity();
    }


}
