package com.git.cloud.sys.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.parame.model.po.ParameterPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.git.cloud.resmgt.common.service.IRmGeneralServerService;
import com.git.cloud.resmgt.common.service.IRmVmManageServerService;
import com.git.cloud.sys.model.vo.SelfMonitorVo;
import com.git.cloud.sys.service.ISelfMonitorService;
import com.git.cloud.taglib.util.Internation;

@SuppressWarnings("rawtypes")
public class SelfMonitorAction extends BaseAction{
	private static Logger logger = LoggerFactory.getLogger(SelfMonitorAction.class);
	private static final long serialVersionUID = 1L;
	private IRmVmManageServerService iRmVmManageServerService;
	private ISelfMonitorService iSelfMonitorService;
	private SelfMonitorVo selefMonitorVo;
	private IRmGeneralServerService rmGeneralServerService;
	private static String CHECKING = Internation.language("jcz");
	

	public SelfMonitorVo getSelefMonitorVo() {
		return selefMonitorVo;
	}

	public void setSelefMonitorVo(SelfMonitorVo selefMonitorVo) {
		this.selefMonitorVo = selefMonitorVo;
	}

	public void setiRmVmManageServerService(
			IRmVmManageServerService iRmVmManageServerService) {
		this.iRmVmManageServerService = iRmVmManageServerService;
	}

	public void setiSelfMonitorService(ISelfMonitorService iSelfMonitorService) {
		this.iSelfMonitorService = iSelfMonitorService;
	}

	public void setRmGeneralServerService(
			IRmGeneralServerService rmGeneralServerService) {
		this.rmGeneralServerService = rmGeneralServerService;
	}

	/**
	 * 镜像服务器列表
	 */
	public void getImageServiceList() throws Exception{
		List<SelfMonitorVo> imageListVo = new ArrayList<SelfMonitorVo>();
		List<CloudImage> images = iSelfMonitorService.getImageServiceList();
		for(CloudImage image : images){
			if(image.getImageUrl().startsWith("http://")){
				 SelfMonitorVo c = new SelfMonitorVo();
				 c.setStatus(Internation.language("jcz"));
	        	 c.setMessage(Internation.language("jcz"));
				 c.setId(image.getImageId());
				 c.setServerName(image.getImageName());
				 c.setUrl(image.getImageUrl());
				 imageListVo.add(c);
			}
		}
		arrayOut(imageListVo);
	}
	
	/**
	 * 镜像服务器检测
	 * @throws Exception
	 */
	public void imageServiceMonitor() throws Exception{
		SelfMonitorVo c = new SelfMonitorVo();
		try {
			c = iSelfMonitorService.getImageStatusByUrl(selefMonitorVo.getUrl());
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
			c.setStatus(Internation.language("yichang"));
			c.setMessage(e.getMessage());
		}
		jsonOut(c);
	}
	
	/**
	 * 脚本服务器列表
	 * @throws Exception
	 */
	public void getScriptServiceList() throws Exception{
		List<SelfMonitorVo> scriptListVo = new ArrayList<SelfMonitorVo>();
		List<RmGeneralServerVo> scriptServerList = iSelfMonitorService.getScriptServiceList();
		try {
			for(RmGeneralServerVo server : scriptServerList){
				SelfMonitorVo monitorVo = new SelfMonitorVo();
				monitorVo.setId(server.getId());
				monitorVo.setIp(server.getServerIp());
				monitorVo.setUserName(server.getUserName());
				monitorVo.setServerName(server.getServerName());
				monitorVo.setPwd(server.getPassword());
				monitorVo.setStatus(Internation.language("jcz"));
				monitorVo.setMessage(Internation.language("jcz"));
				scriptListVo.add(monitorVo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}
		arrayOut(scriptListVo);
	}
	
	/**
	 * 脚本服务器检测
	 */
	public void scriptServiceMonitor() throws Exception{
		RmGeneralServerVo v= new RmGeneralServerVo();
		v.setServerIp(selefMonitorVo.getIp());
		v.setUserName(selefMonitorVo.getUserName());
		v.setPassword(selefMonitorVo.getPwd());
		SelfMonitorVo scriptMonitorVo = iSelfMonitorService.scriptServiceMonitor(v);
		jsonOut(scriptMonitorVo);
	}
	
	/**
	 * HMC服务器列表
	 */
	
	public void getHmcServiceList() throws Exception{
		List<SelfMonitorVo> hmcListVo = new ArrayList<SelfMonitorVo>();
		List<RmVmManageServerPo> hmcServerList = iSelfMonitorService.getHmcServiceList();
		try {
			for(RmVmManageServerPo server : hmcServerList){
				SelfMonitorVo monitorVo = new SelfMonitorVo();
				monitorVo.setId(server.getId());
				monitorVo.setIp(server.getManageIp());
				monitorVo.setUserName(server.getUserName());
				monitorVo.setServerName(server.getServerName());
				monitorVo.setPwd(server.getPassword());
				monitorVo.setStatus(Internation.language("jcz"));
				monitorVo.setMessage(Internation.language("jcz"));
				hmcListVo.add(monitorVo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage()); 
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}
		arrayOut(hmcListVo);
	}
	/**
	 * HMC服务器检测
	 */
	public void hmcServiceMonitor() throws Exception{
		RmVmManageServerPo servicePo = iRmVmManageServerService.getvmManageServerInfo(selefMonitorVo.getId());
		SelfMonitorVo hmcMonitorVo = iSelfMonitorService.hmcServiceMonitor(servicePo);
		jsonOut(hmcMonitorVo);
	}
	
	/**
	 * MQ服务器检测
	 * @throws Exception 
	 */
	public void mqServiceMonitor() throws Exception{
		SelfMonitorVo mqMonitorVo = iSelfMonitorService.mqServiceMonitor();
		mqMonitorVo.setServerName(Internation.language("mqserver"));
		jsonOut(mqMonitorVo);
	}
	
	/**
	 * MQ服务列表
	 * @throws Exception
	 */
	public void getMqServiceList()throws Exception{
		List<SelfMonitorVo> mqListVo = new ArrayList<SelfMonitorVo>();
		SelfMonitorVo mqVo = new SelfMonitorVo();
		mqVo.setStatus(Internation.language("jcz"));
		mqVo.setMessage(Internation.language("jcz"));
		mqVo.setServerName(Internation.language("mqserver"));
		//id是自己构建的
		mqVo.setId("MqServiceId");
		mqListVo.add(mqVo);
		arrayOut(mqListVo);
	}
	
	/**
	 * 获取automation服务
	 * @throws Exception
	 */
	public void getAutoServerList() throws Exception{
		List<SelfMonitorVo> autoListVo = new ArrayList<SelfMonitorVo>();
		RmGeneralServerVo autoServer = rmGeneralServerService.findRmGeneralServerByType("AUTOMATION_SERVER").get(0);
		SelfMonitorVo vmware = new SelfMonitorVo();
		vmware.setIp(autoServer.getServerIp());
		//id自己构建的
		vmware.setServerName("VMWARE");
		vmware.setId(autoServer.getId()+"VMWARE");
		vmware.setStatus(Internation.language("jcz"));
		vmware.setMessage(Internation.language("jcz"));
		autoListVo.add(vmware);
		
		SelfMonitorVo kvm = new SelfMonitorVo();
		kvm.setIp(autoServer.getServerIp());
		//id自己构建的
		kvm.setServerName("KVM");
		kvm.setId(autoServer.getId()+"KVM");
		kvm.setStatus(Internation.language("jcz"));
		kvm.setMessage(Internation.language("jcz"));
		autoListVo.add(kvm);
		
		SelfMonitorVo ssh = new SelfMonitorVo();
		ssh.setIp(autoServer.getServerIp());
		//id自己构建的
		ssh.setServerName("SSH");
		ssh.setId(autoServer.getId()+"SSH");
		ssh.setStatus(Internation.language("jcz"));
		ssh.setMessage(Internation.language("jcz"));
		autoListVo.add(ssh);
		
		arrayOut(autoListVo);
	}
	
	/**
	 * automation服务器检测
	 * @throws Exception 
	 */
	public void autoServiceMonitor() throws Exception{
		SelfMonitorVo autoMonitorVo = iSelfMonitorService.autoServiceMonitor(selefMonitorVo.getServerName());
		jsonOut(autoMonitorVo);
	}
	
	/**
	 * 获取vc列表
	 * @throws Exception
	 */
	public void getVcenterServerList() throws Exception{
		List<RmVmManageServerPo> RmVmManageServerList = iSelfMonitorService.getvmManageServerList();
		List<SelfMonitorVo> vcListVo = new ArrayList<SelfMonitorVo>();
		for(RmVmManageServerPo  vmManagerServerPo: RmVmManageServerList){
			SelfMonitorVo c = new SelfMonitorVo();
			try {
				c.setId(vmManagerServerPo.getId());
				c.setIp(vmManagerServerPo.getManageIp());
				c.setServerName(vmManagerServerPo.getServerName());
				c.setStatus(Internation.language("jcz"));
				c.setMessage(Internation.language("jcz"));
				vcListVo.add(c);
			} catch (Exception e) {
				logger.error(e.getMessage());
				logger.error("异常exception",e);
				Utils.printExceptionStack(e);
			}
		}
		arrayOut(vcListVo);
		}
	
	/**
	 * VC服务器检测
	 * @throws Exception 
	 */
	public void vcenterServerMonitor() throws Exception{
		SelfMonitorVo c = new SelfMonitorVo();
			try {
				RmVmManageServerPo servicePo = iRmVmManageServerService.getvmManageServerInfo(selefMonitorVo.getId());
				c = iSelfMonitorService.vcenterServerMonitor(servicePo);
			} catch (Exception e) {
				logger.error(e.getMessage());
				logger.error("异常exception",e);
				Utils.printExceptionStack(e);
				c.setStatus(Internation.language("yichang"));
				c.setMessage(e.getMessage());
			}
		jsonOut(c);
	}
	
	/**
	 * 获取bpm信息
	 * @throws Exception
	 */
	public void getBpm() throws Exception{
		List<SelfMonitorVo> vcListVo = new ArrayList<SelfMonitorVo>();
		ParameterPo po= iSelfMonitorService.getBpm("bpmWebServiceAddress");
		SelfMonitorVo c = new SelfMonitorVo();
		c.setUrl(po.getParamValue());
		c.setStatus(Internation.language("jcz"));
    	c.setMessage(Internation.language("jcz"));
		c.setServerName("bpmWebServiceAddress");
		c.setId(po.getParamId());
		vcListVo.add(c);
		arrayOut(vcListVo);
	}
	
	/**
	 * 检测bpm
	 * @throws Exception
	 */
	public void bpmServerMonitor() throws Exception{
		SelfMonitorVo c = iSelfMonitorService.bpmServerMonitor(selefMonitorVo.getUrl());
		jsonOut(c);
	}
}
