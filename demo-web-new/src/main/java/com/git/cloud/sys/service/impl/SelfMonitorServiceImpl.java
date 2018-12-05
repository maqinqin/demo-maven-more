package com.git.cloud.sys.service.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.cloudservice.dao.IScriptDao;
import com.git.cloud.cloudservice.dao.ImageDao;
import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.parame.model.po.ParameterPo;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.resmgt.common.CloudClusterConstants;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.dao.impl.RmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.git.cloud.resmgt.common.service.IRmGeneralServerService;
import com.git.cloud.sys.model.vo.SelfMonitorVo;
import com.git.cloud.sys.service.ISelfMonitorService;
import com.git.cloud.taglib.util.Internation;
import com.git.cloud.utils.SSHExecutor;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;

public class SelfMonitorServiceImpl implements ISelfMonitorService{
	private static Logger logger = LoggerFactory.getLogger(RmVmManageServerDAO.class);

	private ImageDao iSelfMonitorDaoImpl;
	private ResAdptInvokerFactory resInvokerFactory;
	private IRmDatacenterDAO rmDCDAO;
	private IScriptDao iScriptDaoImpl;
	@Autowired
	private ConnectionFactory connectionFactory;
	private Connection connection = null;
	private IRmVmManageServerDAO iRmVmManageServerDAO;
	private static String NORMAL=Internation.language("java_self_normal");
	private static String ABNORMAL = Internation.language("java_self_abnormal");
	private IRmGeneralServerService rmGeneralServerService;
	private ParameterService  parameterServiceImpl;
	
	public ParameterService getParameterServiceImpl() {
		return parameterServiceImpl;
	}

	public void setParameterServiceImpl(ParameterService parameterServiceImpl) {
		this.parameterServiceImpl = parameterServiceImpl;
	}

	public IRmGeneralServerService getRmGeneralServerService() {
		return rmGeneralServerService;
	}

	public void setRmGeneralServerService(
			IRmGeneralServerService rmGeneralServerService) {
		this.rmGeneralServerService = rmGeneralServerService;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public IScriptDao getiScriptDaoImpl() {
		return iScriptDaoImpl;
	}

	public IRmVmManageServerDAO getiRmVmManageServerDAO() {
		return iRmVmManageServerDAO;
	}

	public void setiRmVmManageServerDAO(IRmVmManageServerDAO iRmVmManageServerDAO) {
		this.iRmVmManageServerDAO = iRmVmManageServerDAO;
	}

	public void setiScriptDaoImpl(IScriptDao iScriptDaoImpl) {
		this.iScriptDaoImpl = iScriptDaoImpl;
	}

	public ImageDao getiSelfMonitorDaoImpl() {
		return iSelfMonitorDaoImpl;
	}

	public void setiSelfMonitorDaoImpl(ImageDao iSelfMonitorDaoImpl) {
		this.iSelfMonitorDaoImpl = iSelfMonitorDaoImpl;
	}

	public ResAdptInvokerFactory getResInvokerFactory() {
		return resInvokerFactory;
	}

	public void setResInvokerFactory(ResAdptInvokerFactory resInvokerFactory) {
		this.resInvokerFactory = resInvokerFactory;
	}

	public IRmDatacenterDAO getRmDCDAO() {
		return rmDCDAO;
	}

	public void setRmDCDAO(IRmDatacenterDAO rmDCDAO) {
		this.rmDCDAO = rmDCDAO;
	}

	/** 
     * image服务器
     */ 
	@Override
	public List<CloudImage> getImageServiceList() throws Exception {
		List<CloudImage> list = iSelfMonitorDaoImpl.findList();
		return list;
	}
	
    /** 
     * 发送 get请求 
     */  
	@SuppressWarnings("finally")
	public SelfMonitorVo getImageStatusByUrl (String url) throws Exception{
		SelfMonitorVo monitorVo = new SelfMonitorVo();
    	//创建一个DefaultHttpClient的实例
        HttpClient httpClient = new DefaultHttpClient();
        //请求超时
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); 
        //读取超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
        HttpGet httpGet = new HttpGet(url);  
        HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
	        if(200 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_normal"));
	        	monitorVo.setMessage(Internation.language("self_running_normal"));
	        }else if(404 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_abnormal"));
	        	monitorVo.setMessage("无法找到指定位置的资源");
	        }else if(414 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_abnormal"));
	        	monitorVo.setMessage("URL长度超出限制");
	        }else if(500 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_abnormal"));
	        	monitorVo.setMessage("服务器遇到了意料不到的情况，不能完成客户的请求");
	        }else if(503 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_abnormal"));
	        	monitorVo.setMessage("服务器由于维护或者负载过重未能应答");
	        }else if(504 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_abnormal"));
	        	monitorVo.setMessage("无法及时地从远程服务器获得应答");
	        }
		} catch (HttpHostConnectException e){
			logger.error("异常exception",e);
			monitorVo.setStatus(Internation.language("java_self_abnormal"));
        	monitorVo.setMessage("连接被拒绝，请检查端口号是否正确");
		} catch(SSLPeerUnverifiedException e){
			logger.error("异常exception",e);
			monitorVo.setStatus(Internation.language("java_self_abnormal"));
        	monitorVo.setMessage("服务器没有一个有效的SSL证书，请检查协议类型是否正确");
		} catch(ClientProtocolException e){
			logger.error("异常exception",e);
			monitorVo.setStatus(Internation.language("java_self_abnormal"));
			if(e.getMessage().startsWith("URI does not specify a valid host name")){
				monitorVo.setMessage("主机名无效，请检查URL");
			}else{
				monitorVo.setMessage(e.getMessage());
			}
		} catch (Exception e) {
			logger.error("异常exception",e);
			monitorVo.setStatus(Internation.language("java_self_abnormal"));
        	monitorVo.setMessage(e.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
			return monitorVo;
		}
	}

	/**
	 * scriptServiceMonitor
	 * 
	 * */
	@Override
	public List<RmGeneralServerVo> getScriptServiceList() throws Exception{
		List<RmGeneralServerVo> scriptServers = iScriptDaoImpl.findAllScriptServer();
		for(RmGeneralServerVo servicePo : scriptServers){
			if (StringUtils.isBlank(servicePo.getPassword())) {
				servicePo.setPassword("");
			} else {
				// 先对密码解密
				servicePo.setPassword(PwdUtil.decryption(servicePo.getPassword()));
			}
		}
		return scriptServers;
	}
	
	public SelfMonitorVo scriptServiceMonitor(RmGeneralServerVo server) throws Exception{
		String result = null;
		SelfMonitorVo monitorVo = new SelfMonitorVo();
		monitorVo.setId(server.getId());
		monitorVo.setIp(server.getServerIp());
		monitorVo.setUserName(server.getUserName());
		monitorVo.setServerName(server.getServerName());
		monitorVo.setPwd(server.getPassword());
		try {
			result = SSHExecutor.execute("date;", server.getServerIp(), server.getUserName(), server.getPassword(), 22);
			if (result != null && !result.equalsIgnoreCase("")) {
				monitorVo.setStatus(Internation.language("java_self_normal"));
				monitorVo.setMessage(Internation.language("self_running_normal"));
			} else {
				monitorVo.setStatus(Internation.language("java_self_abnormal"));
				monitorVo.setMessage("错误");
			}
		} catch (Exception e) {
			monitorVo.setStatus(Internation.language("java_self_abnormal"));
			monitorVo.setMessage(result+e.getMessage());
			logger.error("异常exception",e);
		}
		return monitorVo;
	}
	
	/**
	 * hmcServiceMonitor
	 * 
	 * */
	@Override
	public List<RmVmManageServerPo> getHmcServiceList() {
		List<RmVmManageServerPo> hmcServers = iRmVmManageServerDAO.findAllHmcServer();
		for(RmVmManageServerPo servicePo : hmcServers){
			if (StringUtils.isBlank(servicePo.getPassword())) {
				servicePo.setPassword("");
			} else {
				// 先对密码解密
				servicePo.setPassword(PwdUtil.decryption(servicePo.getPassword()));
			}
		}
		return hmcServers;
	}
	
	@Override
	public SelfMonitorVo hmcServiceMonitor(RmVmManageServerPo server) {
		String result = null;
		SelfMonitorVo monitorVo = new SelfMonitorVo();
		monitorVo.setId(server.getId());
		monitorVo.setIp(server.getManageIp());
		monitorVo.setUserName(server.getUserName());
		monitorVo.setServerName(server.getServerName());
		monitorVo.setPwd(server.getPassword());
		try {
			result = SSHExecutor.execute("date;", server.getManageIp(), server.getUserName(), server.getPassword(), 22);
			if (result != null && !result.equalsIgnoreCase("")) {
				monitorVo.setStatus(Internation.language("java_self_normal"));
				monitorVo.setMessage(Internation.language("self_running_normal"));
			} else {
				monitorVo.setStatus(Internation.language("java_self_abnormal"));
				monitorVo.setMessage("错误");
			}
		} catch (Exception e) {
			monitorVo.setStatus(Internation.language("java_self_abnormal"));
			monitorVo.setMessage(result+e.getMessage());
			logger.error("异常exception",e);
		}
		return monitorVo;
	}
	
	/**
	 * mqServiceMonitor
	 * 
	 * */

	@Override
	public SelfMonitorVo mqServiceMonitor() {
		SelfMonitorVo mqVo = new SelfMonitorVo();
		if (connectionFactory == null) {
			mqVo.setStatus(Internation.language("java_self_abnormal"));
			mqVo.setMessage("ConnectionFactory is null");
		}else{
			try {
				connection = connectionFactory.createConnection();	//创建connection
			} catch (JMSException e1) {
				mqVo.setStatus(Internation.language("java_self_abnormal"));
				mqVo.setMessage("创建connection失败");
				logger.error("创建connection失败:"+e1.getMessage());
				e1.printStackTrace();
			}
			
			int timeout = 10; //超时时间：  xx秒.  
	        ExecutorService executor = Executors.newSingleThreadExecutor();  
	        
	        Future<?> future = executor.submit(new Runnable(){	// 将任务提交到线程池中    
				@Override
				public void run() {
					try {
						connection.start();
					} catch (JMSException e) {
						logger.error("JMSException:"+e.getMessage());
						logger.error("异常exception",e);
					}
				}
	        }); 
	        try {
	            future.get(timeout*1000, TimeUnit.MILLISECONDS);// 设定在10000毫秒的时间内完成   
	            mqVo.setStatus(Internation.language("java_self_normal"));
				mqVo.setMessage(Internation.language("self_running_normal"));
	        } catch (InterruptedException e) {  
	            future.cancel(true);// 中断执行此任务的线程  
	            mqVo.setStatus(Internation.language("java_self_abnormal"));
				mqVo.setMessage("线程中断出错");
	        } catch (ExecutionException e) {     
	            future.cancel(true);// 中断执行此任务的线程   
	            mqVo.setStatus(Internation.language("java_self_abnormal"));
				mqVo.setMessage("线程服务出错");
	        } catch (TimeoutException e) {// 超时异常     
	            future.cancel(true);// 中断执行此任务的线程     
	            mqVo.setStatus(Internation.language("java_self_abnormal"));
				mqVo.setMessage("创建链接超时");
	        }finally{
	        	if (connection != null) {
	        		try {
						connection.stop();
						connection.close();
						logger.info("关闭connection成功");
					} catch (JMSException e) {
						logger.error("关闭connection失败:"+e.getMessage());
						logger.error("异常exception",e);
					}
					connection = null;
	        	}
	        	executor.shutdown();
	        	logger.info("线程服务关闭");
	        }
		}
        return mqVo;
	}



	@Override
	public SelfMonitorVo autoServiceMonitor(String serName) throws Exception {
		RmGeneralServerVo autoServer = rmGeneralServerService.findRmGeneralServerByType("AUTOMATION_SERVER").get(0);
		SelfMonitorVo selfMonitor = new SelfMonitorVo();
		selfMonitor.setIp(autoServer.getServerIp());
		//id自己构建的
		//header
		HeaderDO header = HeaderDO.CreateHeaderDO();
		RmDatacenterPo dcPo = new RmDatacenterPo();
		dcPo = rmDCDAO.getDataCenterById(autoServer.getDatacenterId());
		header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
		if(serName.equals("VMWARE")){
			header.setOperationBean("vmwareListenService");
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			selfMonitor.setServerName("VMWARE");
			selfMonitor.setId(autoServer.getId()+"VMWARE");
		} else if(serName.equals("KVM")){
			header.setOperationBean("kvmListenService");
			header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
			selfMonitor.setServerName("KVM");
			selfMonitor.setId(autoServer.getId()+"KVM");
		}else if(serName.equals("SSH")){
			header.setOperationBean("sshListenService");
			header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
			selfMonitor.setServerName("SSH");
			selfMonitor.setId(autoServer.getId()+"SSH");
		}
		//reqData
		IDataObject reqData = DataObject.CreateDataObject();
		reqData.setDataObject(MesgFlds.HEADER, header);

		IDataObject rspData = null;
		IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
		int MonitorAutoTime = Integer.parseInt(parameterServiceImpl.getParamValueByName("MONITOR_AUTO_TIME"));
		logger.debug("MonitorAutoTime:"+MonitorAutoTime);
		rspData = invoker.invoke(reqData, MonitorAutoTime);
		if (rspData == null) {
			selfMonitor.setStatus(Internation.language("java_self_abnormal"));
			selfMonitor.setMessage("请求响应失败");
		} else {
			HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
			if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
				selfMonitor.setStatus(Internation.language("java_self_normal"));
				selfMonitor.setMessage("报文正常");
			} else {
				selfMonitor.setStatus(Internation.language("java_self_abnormal"));
				selfMonitor.setMessage(rspHeader.getRetMesg());
			}
		}
		return selfMonitor;
	}

	@Override
	public SelfMonitorVo vcenterServerMonitor(RmVmManageServerPo vmManagerServerPo) throws Exception {
		SelfMonitorVo c = new SelfMonitorVo();
		c.setId(vmManagerServerPo.getId());
		c.setIp(vmManagerServerPo.getManageIp());
		c.setServerName(vmManagerServerPo.getServerName());
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();		
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperationBean("validateConnectImpl");
		RmDatacenterPo dcPo = rmDCDAO.getDataCenterById(vmManagerServerPo.getDatacenterId());
		header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(GeneralKeyField.VMware.VCENTER_URL,CloudClusterConstants.VCENTER_URL_HTTPS+vmManagerServerPo.getManageIp()+CloudClusterConstants.VCENTER_URL_SDK);
		body.setString(GeneralKeyField.VMware.VCENTER_USERNAME,vmManagerServerPo.getUserName());
		body.setString(GeneralKeyField.VMware.VCENTER_PASSWORD,vmManagerServerPo.getPassword());

		IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");

		IDataObject rspData = null;
		reqData.setDataObject(MesgFlds.HEADER, header);
		reqData.setDataObject(MesgFlds.BODY, body);
		try {
			int MonitorVcTime = Integer.parseInt(parameterServiceImpl.getParamValueByName("MONITOR_VC_TIME"));
			rspData = invoker.invoke(reqData, MonitorVcTime);
			logger.debug("MonitorVcTime:"+MonitorVcTime);
			if(rspData==null){
				c.setStatus(Internation.language("java_self_abnormal"));
				c.setMessage("返回值为空");
			}else{
				HeaderDO header1 = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
				if(MesgRetCode.SUCCESS.equals(header1.getRetCode())){
					c.setStatus(Internation.language("java_self_normal"));
					c.setMessage("报文正常");
				}else{
					c.setStatus(Internation.language("java_self_abnormal"));
					c.setMessage(header1.getRetMesg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return c;
	}

	@Override
	public List<RmVmManageServerPo> getvmManageServerList() {
		List<RmVmManageServerPo> rmVmManageServerInfoList = Lists.newArrayList();
		try {
			//获取vc列表
			List<RmVmManageServerPo> RmVmManageServer = iRmVmManageServerDAO.getvmManageServerList();
				for(RmVmManageServerPo rmSer : RmVmManageServer){
					try {
						//根据id获取详细信息
						RmVmManageServerPo servicePo = iRmVmManageServerDAO.getvmManageServerInfo(rmSer.getId());
						if (StringUtils.isBlank(servicePo.getPassword())) {
							servicePo.setPassword("");
						} else {
							// 先对密码解密
							servicePo.setPassword(PwdUtil.decryption(servicePo.getPassword()));
						}
						rmVmManageServerInfoList.add(servicePo);
					} catch (Exception e) {
						logger.error("获取vcenter列表失败："+e.getMessage());
						logger.error("异常exception",e);
						Utils.printExceptionStack(e);
					}
				}
	
		} catch (RollbackableBizException e) {
			logger.error("获取虚机管理机列表时发生异常:" + e.getMessage());
			logger.error("异常exception",e);
		}
		return rmVmManageServerInfoList;
	}

	@Override
	public List<RmGeneralServerVo> getAutoServerList() throws Exception {
		return  rmGeneralServerService.findRmGeneralServerByType("AUTOMATION_SERVER");
	}

	@Override
	public ParameterPo getBpm(String bpmName) throws Exception {
		//传递的是bpm的名称
		return parameterServiceImpl.getbpm(bpmName);
	}

	@SuppressWarnings("finally")
	@Override
	public SelfMonitorVo bpmServerMonitor(String url) throws Exception {
		SelfMonitorVo monitorVo = new SelfMonitorVo();
		monitorVo.setUrl(url);
    	//创建一个DefaultHttpClient的实例
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);  
        HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
	        if(200 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_normal"));
	        	monitorVo.setMessage(Internation.language("self_running_normal"));
	        }else if(404 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_abnormal"));
	        	monitorVo.setMessage("无法找到指定位置的资源");
	        }else if(414 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_abnormal"));
	        	monitorVo.setMessage("URL长度超出限制");
	        }else if(500 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_abnormal"));
	        	monitorVo.setMessage("服务器遇到了意料不到的情况，不能完成客户的请求");
	        }else if(503 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_abnormal"));
	        	monitorVo.setMessage("服务器由于维护或者负载过重未能应答");
	        }else if(504 == response.getStatusLine().getStatusCode()){
	        	monitorVo.setStatus(Internation.language("java_self_abnormal"));
	        	monitorVo.setMessage("无法及时地从远程服务器获得应答");
	        }
		} catch (HttpHostConnectException e){
			logger.error("异常exception",e);
			monitorVo.setStatus(Internation.language("java_self_abnormal"));
        	monitorVo.setMessage("连接被拒绝，请检查端口号是否正确，bpm是否已经正常启动");
		} catch(SSLPeerUnverifiedException e){
			logger.error("异常exception",e);
			monitorVo.setStatus(Internation.language("java_self_abnormal"));
        	monitorVo.setMessage("服务器没有一个有效的SSL证书，请检查协议类型是否正确");
		} catch(ClientProtocolException e){
			logger.error("异常exception",e);
			monitorVo.setStatus(Internation.language("java_self_abnormal"));
			if(e.getMessage().startsWith("URI does not specify a valid host name")){
				monitorVo.setMessage("主机名无效，请检查URL");
			}else{
				monitorVo.setMessage(e.getMessage());
			}
		} catch (Exception e) {
			logger.error("异常exception",e);
			monitorVo.setStatus(Internation.language("java_self_abnormal"));
        	monitorVo.setMessage(e.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
			return monitorVo;
		}
	}



}
