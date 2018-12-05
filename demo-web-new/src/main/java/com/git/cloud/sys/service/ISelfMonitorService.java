package com.git.cloud.sys.service;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.parame.model.po.ParameterPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.git.cloud.sys.model.vo.SelfMonitorVo;

public interface ISelfMonitorService {
	/**
	 * 镜像服务器检测
	 * @return 
	 */
	public SelfMonitorVo getImageStatusByUrl(String url)throws Exception;
	/**
	 * 获取镜像列表
	 * @return
	 * @throws Exception
	 */
	public List<CloudImage> getImageServiceList() throws Exception;
	/**
	 * 获取脚本服务列表
	 * @return 
	 */
	public List<RmGeneralServerVo> getScriptServiceList() throws Exception;
	/**
	 * 脚本服务器检测
	 * @param server
	 * @return
	 * @throws Exception
	 */
	public SelfMonitorVo scriptServiceMonitor(RmGeneralServerVo server) throws Exception;
	/**
	 * MQ服务器检测
	 * @return 
	 */
	public SelfMonitorVo mqServiceMonitor() throws Exception;
	/**
	 * 获取automation服务列表
	 * @throws Exception
	 */
	public List<RmGeneralServerVo> getAutoServerList() throws Exception;
	/**
	 * automation服务器检测
	 */
	public SelfMonitorVo autoServiceMonitor(String serName) throws Exception;
	/**
	 * 获取vcenter服务器列表
	 * @return
	 */
	public List<RmVmManageServerPo> getvmManageServerList() throws Exception;
	/**
	 * VC服务器检测
	 */
	public SelfMonitorVo vcenterServerMonitor(RmVmManageServerPo rmVmManageServerPo) throws Exception;
	/**
	 * 获取bpm信息
	 * @return
	 * @throws Exception
	 */
	public ParameterPo getBpm(String bpmName) throws Exception;
	/**
	 * bpm检测
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public SelfMonitorVo bpmServerMonitor(String url)throws Exception;
	
	/**
	 * 获取hmc服务器列表
	 * @return
	 */
	public List<RmVmManageServerPo> getHmcServiceList() throws Exception;
	/**
	 * hmc检测
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public SelfMonitorVo hmcServiceMonitor(RmVmManageServerPo server) throws Exception;
}
