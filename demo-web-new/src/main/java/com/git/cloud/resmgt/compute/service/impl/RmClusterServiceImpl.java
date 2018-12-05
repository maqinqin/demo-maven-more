package com.git.cloud.resmgt.compute.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.resmgt.common.CloudClusterConstants;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmResPoolDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.dao.IRmVmTypeDAO;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.RmResPoolVo;
import com.git.cloud.resmgt.compute.dao.IRmCdpDAO;
import com.git.cloud.resmgt.compute.dao.impl.RmClusterDAO;
import com.git.cloud.resmgt.compute.model.po.RmCdpPo;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
import com.git.cloud.resmgt.compute.model.vo.RmCdpVo;
import com.git.cloud.resmgt.compute.service.IRmClusterService;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.common.VMOpration;
import com.git.support.common.VmGlobalConstants;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.emory.mathcs.backport.java.util.Arrays;
/**
 * 
 * @author 王成辉
 * @Description 集群维护
 * @date 2014-12-17
 *
 */
public class RmClusterServiceImpl implements IRmClusterService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
private RmClusterDAO rmClusterDAO;
private IRmCdpDAO rmCdpDAO; 
private ResAdptInvokerFactory resInvokerFactory;
private IRmVmManageServerDAO rmVmMgServerDAO;
private ICmPasswordDAO cmPasswordDAO;
private IRmDatacenterDAO rmDCDAO;
private IRmVmTypeDAO rmVmTypeDAO;
private IRmResPoolDAO rmResPoolDAO;

private static Logger log = LoggerFactory
		.getLogger(RmClusterServiceImpl.class);
	public RmClusterDAO getRmClusterDAO() {
		return rmClusterDAO;
	}

	public void setRmClusterDAO(RmClusterDAO rmClusterDAO) {
		this.rmClusterDAO = rmClusterDAO;
	}

	public IRmResPoolDAO getRmResPoolDAO() {
		return rmResPoolDAO;
	}

	public void setRmResPoolDAO(IRmResPoolDAO rmResPoolDAO) {
		this.rmResPoolDAO = rmResPoolDAO;
	}

	@SuppressWarnings("unused")
	@Override
	public void saveCluster(RmClusterPo clusterPo) throws Exception {
		RmCdpPo cdpPo = null;
		String cdpId = clusterPo.getCdpId();
		clusterPo.setCdpId(null);
		//vmware:vcenter创建folder，成功后入库；powervm直接入库
		String result="";
		String resultCdp="";
		//根据获取虚拟机类型信息
		String vmTypeId = clusterPo.getVmType();
		HashMap<String,String> params = Maps.newHashMap();
		params.put("vmTypeId", vmTypeId);
		RmVirtualTypePo rmVmTypePo = rmVmTypeDAO.findRmVirtualTypeInfo("findRmVirtualTypeInfo", params);
		if(rmVmTypePo==null){
			throw new Exception("获取虚拟机类型错误！参数为：["+params.toString()+"]");
		}
		//先判断是不是vmware类型
		if(rmVmTypePo.getVirtualTypeCode().equals(RmVirtualType.VMWARE.getValue())){
			
			if(cdpId != null && !cdpId.equals("")){
				String cdpExit = getCdpInfoByInterface(cdpPo.getEname(),clusterPo.getManageServer());
				//cdp已经存在，可直接创建集群
				if(cdpExit.equals("Y")){
					//集群是创建在CDP下面的
					result=createClusterInterface(clusterPo,cdpPo,"INTERFACE_OPER_TYPE_ADD");
					if(result.equals("Y")){
					}else
					{
						throw new Exception("create cluster failed");
					}
				}else{
					//先创建cdp，再创建集群
					cdpPo.getVmManagedServerIds().add(clusterPo.getManageServer());
					resultCdp=createCdpInterface(cdpPo);
					if(resultCdp.equals("Y")){
					}else
					{
						throw new RuntimeException("create cdp in vcenter failed");
					}
					
					result=createClusterInterface(clusterPo,cdpPo,"INTERFACE_OPER_TYPE_ADD");
					if(result.equals("Y")){
					}else
					{
						throw new Exception("create cluster failed");
					}
				}
			}else{
				result=createClusterInterface(clusterPo,cdpPo,"INTERFACE_OPER_TYPE_ADD");
				if(result.equals("Y")){
				}else
				{
					throw new Exception("create cluster failed");
				}
			}
			
		}
		SysUserPo sysUserPo=(SysUserPo) SecurityUtils.getSubject().getPrincipal();
		String user=sysUserPo.getFirstName()+sysUserPo.getLastName();
		clusterPo.setId(UUIDGenerator.getUUID());
		clusterPo.setIsActive("Y");
		clusterPo.setCreateDateTime(new Date());
		clusterPo.setCreateUser(user);
		RmResPoolVo rmResPoolVo = rmResPoolDAO.findRmResPoolVoById(clusterPo.getResPoolId());
		clusterPo.setPlatformType(rmResPoolVo.getPlatformType());
		rmClusterDAO.saveRmClusterPo(clusterPo);
	}

	@Override
	public RmClusterPo findClusterById(String clusterId) throws RollbackableBizException {
		return rmClusterDAO.findRmClusterPoById(clusterId);
	}

	@Override
	public void updateCluster(RmClusterPo clusterPo) throws Exception {
		String cdpId = clusterPo.getCdpId();
		RmCdpPo cdpPo = null;
			clusterPo.setCdpId(null);
		RmClusterPo _clusterPo = rmClusterDAO.findRmClusterPoById(clusterPo.getId());
		//vmware:vcenter创建folder，成功后入库；powervm直接入库
		String result="";
		//根据获取虚拟机类型信息
		String vmTypeId = clusterPo.getVmType();
		//String platformId = cdpPo.getPlatformType();
		HashMap<String,String> params = Maps.newHashMap();
		params.put("vmTypeId", vmTypeId);
		//params.put("platformId", platformId);
		RmVirtualTypePo rmVmTypePo = rmVmTypeDAO.findRmVirtualTypeInfo("findRmVirtualTypeInfo", params);
		if(rmVmTypePo==null){
			throw new RollbackableBizException("获取虚拟机类型错误！参数为：["+params.toString()+"]");
		}
		
		if(rmVmTypePo.getVirtualTypeCode().equals(RmVirtualType.VMWARE.getValue())){
			
			String newVmType = clusterPo.getVmType();
			String oldVmType = _clusterPo.getVmType();
			String newName = clusterPo.getEname();
			String oldName = _clusterPo.getEname();
			//虚拟机类型发生改变
			if(!newVmType.equals(oldVmType)){
				if(cdpId != null && !cdpId.equals("")){
					//cdp下修改集群
					result=updateClusterInterface(clusterPo,oldName);
					if(result.equals("Y")){
					}else
					{
						throw new RollbackableBizException("update cluster in vcenter failed");
					}
				}else{
					//要在在数据中心下新建集群
					result=createClusterInterface(clusterPo,cdpPo,"INTERFACE_OPER_TYPE_ADD");
					if(result.equals("Y")){
					}else
					{
						throw new Exception("create cluster failed");
					}
				}
				
			}
			if(!newName.equals(oldName)){//原来就是vmware类型的，只是更改了其他的信息
				result=updateClusterInterface(clusterPo,oldName);
				if(result.equals("Y")){
				}else
				{
					throw new RollbackableBizException("update cluster in vcenter failed");
				}
				
			}
		}
		SysUserPo sysUserPo=(SysUserPo) SecurityUtils.getSubject().getPrincipal();
		String user=sysUserPo.getFirstName()+sysUserPo.getLastName();
		clusterPo.setUpdateUser(user);
		rmClusterDAO.updateRmClusterPo(clusterPo);
		
	}

	@Override
	public String deleteCluster(String clusterId) throws RollbackableBizException {
		 int i;
		 i=rmClusterDAO.findCmHostVoByClusterId(clusterId);
		 String msg="";
		 if(i>0){
			 msg="集群下已关联物理机，无法删除！";
		 }else{
			 RmClusterPo clusterPo = rmClusterDAO.findRmClusterPoById(clusterId);
			//根据获取虚拟机类型信息
			String vmTypeId = clusterPo.getVmType();
			HashMap<String,String> params = Maps.newHashMap();
			params.put("vmTypeId", vmTypeId);
			RmVirtualTypePo rmVmTypePo = rmVmTypeDAO.findRmVirtualTypeInfo("findRmVirtualTypeInfo", params);
			if(rmVmTypePo==null){
				throw new RollbackableBizException("获取虚拟机类型错误！参数为：["+params.toString()+"]");
			}
			if(rmVmTypePo.getVirtualTypeCode().equals(RmVirtualType.VMWARE.getValue())){			
				msg=destoryClusterInterface(clusterPo);
				if(msg.equals("Y")){
				}else
				{
					throw new RollbackableBizException("调用AMQ接口异常");
				}
			}
			rmClusterDAO.deleteRmClusterPoById(clusterId);
			msg="删除成功!";
		 }
		 return msg;
	}

	public IRmCdpDAO getRmCdpDAO() {
		return rmCdpDAO;
	}

	public void setRmCdpDAO(IRmCdpDAO rmCdpDAO) {
		this.rmCdpDAO = rmCdpDAO;
	}
	public String createClusterInterface(RmClusterPo clusterFullVo,RmCdpPo cdpPo,String operType) throws RollbackableBizException{
		String result = "";
		String dc_flag = "N";
		String cdpName = "";
		String cdpFlag = "";
		RmVmManageServerPo vmManagerServerPo = new RmVmManageServerPo();
		List<RmVmManageServerPo> list= rmVmMgServerDAO.findByID("findRmVmManagerServerPo", clusterFullVo.getManageServer());
		if(list.isEmpty()||list.size()==0){
			throw new RollbackableBizException("get vm manager info error!");
		}else{
			vmManagerServerPo = list.get(0);
		}
		String hostIp=vmManagerServerPo.getManageIp();
		String userName = vmManagerServerPo.getUserName();
		String password = "";
		try {
			CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceUser(clusterFullVo.getManageServer(), userName);
			password = pwpo.getPassword();
			if(StringUtils.isBlank(password))
				throw new Exception("获取ManagerServer[" + vmManagerServerPo.getId() + "] password is null");
			password = PwdUtil.decryption(password);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		//判断集群创建的位置
		if(cdpPo==null){
			//说明集群是创建在资源池下面的
			cdpFlag = "hide"; 
			RmDatacenterPo dcPo = rmDCDAO.getDataCenterById(vmManagerServerPo.getDatacenterId());
			String vc_url = CloudClusterConstants.VCENTER_URL_HTTPS+vmManagerServerPo.getManageIp()+CloudClusterConstants.VCENTER_URL_SDK;
			//获取VC中数据中心信息判断是否已创建数据中心
			dc_flag = this.getVCInfoInterface(vc_url, userName, password,dcPo);
			if(dc_flag.equals("N")){
				throw new RollbackableBizException("创建数据中心"+dcPo.getEname()+"不存在，请在VC上创建后再试！");
				//需要在VC上创建DC
				/*result = createDCInVCenterInterface(vc_url, userName, password,dcPo);
				if(result.equals("N")){
					throw new RollbackableBizException("创建数据中心"+dcPo.getEname()+"失败！");
				}*/
			}
		}else{
			cdpName=cdpPo.getEname();
			cdpFlag = "show";
		}
		
		IDataObject reqData = DataObject.CreateDataObject();

		HeaderDO header = HeaderDO.CreateHeaderDO();		
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.CREATE_CLUSTER);
		RmDatacenterPo dcPo = rmDCDAO.getDataCenterById(vmManagerServerPo.getDatacenterId());
		header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(VMFlds.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS+hostIp+CloudClusterConstants.VCENTER_URL_SDK);
		body.setString(VMFlds.VCENTER_PASSWORD, password);
		body.setString(VMFlds.VCENTER_USERNAME, userName);
		if(cdpFlag.equals("show")){
			body.setString(VMFlds.PARENT_NAME, cdpName);
			body.setString(VMFlds.PARENT_TYPE, VmGlobalConstants.VC_RESOURCE_TYPE_FOLDER);
		}else{
			body.setString(VMFlds.PARENT_NAME,dcPo.getEname());
			body.setString(VMFlds.PARENT_TYPE, VmGlobalConstants.VC_RESOURCE_TYPE_DATACENTER);
		}
		body.setString(VMFlds.CLUSTER_NAME, clusterFullVo.getEname());
		//主机监控开关--false
		body.setBoolean(VMFlds.DAS_CONFIG_ENABLED,CloudClusterConstants.DAS_CONFIG_ENABLED_CLOSE_FALSE );
		//接入控制开关--false
		body.setBoolean(VMFlds.DAS_ADMISSION_CONTROL_ENABLED,CloudClusterConstants.ADMISSION_CONTROL_ENABLED_CLOSE_FALSE );
		//接入控制级别--1
		body.setInt(VMFlds.DAS_FAILOVER_LEVEL, Integer.valueOf(1));
		//接入控制策略类型--1
		body.setString(VMFlds.DAS_ADMISSION_CONTROL_POLICY_TYPE, VmGlobalConstants.DAS_AC_POLICY_TYPE_HOST_COUNT);
		//群集允许的主机故障数目--1
		body.setInt(VMFlds.DAS_FAILOVER_LEVEL_POLICY, 1);
		//故障切换空间容量保留的群集cpu百分比--25
		body.setInt(VMFlds.DAS_CPU_FAILOVER_RESOURCES_PERCENT,25);
		//故障切换空间容量保留的群集内存百分比--25
		body.setInt(VMFlds.DAS_MEMORY_FAILOVER_RESOURCES_PERCENT, 25);
		//主机隔离响应策略---NONE保持
		body.setString(VMFlds.DAS_VM_SETTINGS_ISOLATION_RESPONSE, VmGlobalConstants.DAS_VM_ISOLATION_RESPONSE_NONE);
		//虚机重新启动优先级--3
		body.setString(VMFlds.DAS_VM_SETTINGS_RESTART_PRIORITY,VmGlobalConstants.DAS_VM_RESTART_PRIORITY_MEDIUM);
		//虚拟机监控状态--false	
		body.setBoolean(VMFlds.DAS_MONITORING_CLUSTER_SETTING_FLAG,false );
		body.setBoolean(VMFlds.DAS_MONITORING_ENABLED,false);
		//虚拟机监控状态--1
		body.setString(VMFlds.DAS_VM_MONITORING, VmGlobalConstants.DAS_VM_MONITORING_STATE_DISABLED);//虚拟机监控策略
		//最大重置次数--10800
		body.setInt(VMFlds.DAS_MONITORING_MAX_FAILURES, Integer.valueOf(CloudClusterConstants.DAS_MONITORING_MAX_FAILURES_MIDDLE));
		//最短正常运行时间--60
		body.setInt(VMFlds.DAS_MONITORING_MIN_UP_TIME, Integer.valueOf(CloudClusterConstants.DAS_MONITORING_MIN_UP_TIME_MIDDLE));
		//虚机故障间隔时间--86400
		body.setInt(VMFlds.DAS_MONITORING_FAILURE_INTERVAL, Integer.valueOf(CloudClusterConstants.DAS_MONITORING_FAILURE_INTERVAL_MIDDLE));
		//电源管理DPM阈值--3
		body.setInt(VMFlds.DPM_HOST_POWER_ACTION_RATE, Integer.valueOf(3));
		//DRS迁移阈值--3
		body.setInt(VMFlds.DRS_VMOTION_RATE, Integer.valueOf(3));
		//电源管理设置开关--false
		body.setBoolean(VMFlds.DPM_CONFIG_ENABLED, CloudClusterConstants.DPM_BEHAVIOR_CLOSE_1);
		//电源管理设置策略级别--1
		body.setString(VMFlds.DPM_BEHAVIOR, CloudClusterConstants.DPM_BEHAVIOR_AUTOMATED);
		//drs设置开关--false
		body.setBoolean(VMFlds.DRS_CONFIG_ENABLED,CloudClusterConstants.IS_DRS_CLOSE );
		//HA开关--false
		body.setBoolean(VMFlds.DAS_CONFIG_ENABLED,false );
		//操作类型：INTERFACE_OPER_TYPE_ADD新建,INTERFACE_OPER_TYPE_UPDATE更新
		body.setString(VMFlds.INTERFACE_OPER_TYPE, operType);
		//自动化级别--FULLY_AUTOMATED
		body.setString(VMFlds.DRS_CONFIG_VM_BEHAVIOR, VmGlobalConstants.DRS_BEHAVIOR_FULLY_AUTOMATED);
		log.info("【"+VMFlds.VCENTER_URL+"】："+body.getString(VMFlds.VCENTER_URL));
		log.info("【"+VMFlds.VCENTER_USERNAME+"】："+body.getString(VMFlds.VCENTER_USERNAME));
		log.info("【"+VMFlds.VCENTER_PASSWORD+"】："+body.getString(VMFlds.VCENTER_PASSWORD));
		log.info("【"+VMFlds.PARENT_TYPE+"】："+body.getString(VMFlds.PARENT_TYPE));
		log.info("【"+VMFlds.PARENT_NAME+"】："+body.getString(VMFlds.PARENT_NAME));
		log.info("【"+VMFlds.CLUSTER_NAME+"】："+body.getString(VMFlds.CLUSTER_NAME));
		log.info("【"+VMFlds.DAS_CONFIG_ENABLED+"】："+body.getBoolean(VMFlds.DAS_CONFIG_ENABLED));
		log.info("【"+VMFlds.DAS_ADMISSION_CONTROL_ENABLED+"】："+body.getBoolean(VMFlds.DAS_ADMISSION_CONTROL_ENABLED));
		log.info("【"+VMFlds.DAS_FAILOVER_LEVEL+"】："+body.getInt(VMFlds.DAS_FAILOVER_LEVEL));
		log.info("【"+VMFlds.DAS_ADMISSION_CONTROL_POLICY_TYPE+"】："+body.getString(VMFlds.DAS_ADMISSION_CONTROL_POLICY_TYPE));
		log.info("【"+VMFlds.DAS_FAILOVER_LEVEL_POLICY+"】："+body.getInt(VMFlds.DAS_FAILOVER_LEVEL_POLICY));
		log.info("【"+VMFlds.DAS_CPU_FAILOVER_RESOURCES_PERCENT+"】："+body.getInt(VMFlds.DAS_CPU_FAILOVER_RESOURCES_PERCENT));
		log.info("【"+VMFlds.DAS_MEMORY_FAILOVER_RESOURCES_PERCENT+"】："+body.getInt(VMFlds.DAS_MEMORY_FAILOVER_RESOURCES_PERCENT));
		log.info("【"+VMFlds.DAS_VM_SETTINGS_ISOLATION_RESPONSE+"】："+body.getString(VMFlds.DAS_VM_SETTINGS_ISOLATION_RESPONSE));
		log.info("【"+VMFlds.DAS_VM_SETTINGS_RESTART_PRIORITY+"】："+body.getString(VMFlds.DAS_VM_SETTINGS_RESTART_PRIORITY));
		log.info("【"+VMFlds.DAS_MONITORING_CLUSTER_SETTING_FLAG+"】："+body.getBoolean(VMFlds.DAS_MONITORING_CLUSTER_SETTING_FLAG));
		log.info("【"+VMFlds.DAS_MONITORING_ENABLED+"】："+body.getBoolean(VMFlds.DAS_MONITORING_ENABLED));
		log.info("【"+VMFlds.DAS_VM_MONITORING+"】："+body.getString(VMFlds.DAS_VM_MONITORING));
		log.info("【"+VMFlds.DAS_MONITORING_FAILURE_INTERVAL+"】："+body.getInt(VMFlds.DAS_MONITORING_FAILURE_INTERVAL));
		log.info("【"+VMFlds.DAS_MONITORING_MAX_FAILURES+"】："+body.getInt(VMFlds.DAS_MONITORING_MAX_FAILURES));
		log.info("【"+VMFlds.DAS_MONITORING_MIN_UP_TIME+"】："+body.getInt(VMFlds.DAS_MONITORING_MIN_UP_TIME));
		log.info("【"+VMFlds.DPM_CONFIG_ENABLED+"】："+body.getBoolean(VMFlds.DPM_CONFIG_ENABLED));
		log.info("【"+VMFlds.DPM_BEHAVIOR+"】："+body.getString(VMFlds.DPM_BEHAVIOR));
		log.info("【"+VMFlds.DRS_CONFIG_ENABLED+"】："+body.getBoolean(VMFlds.DRS_CONFIG_ENABLED));
		log.info("【"+VMFlds.DRS_CONFIG_VM_BEHAVIOR+"】："+body.getString(VMFlds.DRS_CONFIG_VM_BEHAVIOR));
		log.info("【"+VMFlds.INTERFACE_OPER_TYPE+"】："+body.getString(VMFlds.INTERFACE_OPER_TYPE));
		IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");

		IDataObject rspData = null;
		reqData.setDataObject(MesgFlds.HEADER, header);
		reqData.setDataObject(MesgFlds.BODY, body);
		try {
			rspData = invoker.invoke(reqData, 300000);
			if(rspData==null){
				result="N";
			}else{
				HeaderDO header1 = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
				if(MesgRetCode.SUCCESS.equals(header1.getRetCode())){
					result="Y";
				}else{
					result="N";
				}
			}
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return result;
	}
	
	@Override
	public RmClusterPo findRmClusterPoByEname(String ename) throws RollbackableBizException {
		return rmClusterDAO.findRmClusterPoByEname(ename);
	}

	public ResAdptInvokerFactory getResInvokerFactory() {
		return resInvokerFactory;
	}

	public void setResInvokerFactory(ResAdptInvokerFactory resInvokerFactory) {
		this.resInvokerFactory = resInvokerFactory;
	}

	public IRmVmManageServerDAO getRmVmMgServerDAO() {
		return rmVmMgServerDAO;
	}

	public void setRmVmMgServerDAO(IRmVmManageServerDAO rmVmMgServerDAO) {
		this.rmVmMgServerDAO = rmVmMgServerDAO;
	}

	public ICmPasswordDAO getCmPasswordDAO() {
		return cmPasswordDAO;
	}

	public void setCmPasswordDAO(ICmPasswordDAO cmPasswordDAO) {
		this.cmPasswordDAO = cmPasswordDAO;
	}

	public IRmDatacenterDAO getRmDCDAO() {
		return rmDCDAO;
	}

	public void setRmDCDAO(IRmDatacenterDAO rmDCDAO) {
		this.rmDCDAO = rmDCDAO;
	}

	public IRmVmTypeDAO getRmVmTypeDAO() {
		return rmVmTypeDAO;
	}

	public void setRmVmTypeDAO(IRmVmTypeDAO rmVmTypeDAO) {
		this.rmVmTypeDAO = rmVmTypeDAO;
	}

	@Override
	public RmClusterPo findRmClusterPoByName(Map<String, String> map)throws RollbackableBizException {
		return rmClusterDAO.findRmClusterPoByName(map);
	}

	
	public String destoryClusterInterface(RmClusterPo clusterPo) throws RollbackableBizException{
		String result;
		//获取cdp所在资源池ids
		RmVmManageServerPo vmManagerServerPo = null;
		List<RmVmManageServerPo> list= rmVmMgServerDAO.findByID("findRmVmManagerServerPo", clusterPo.getManageServer());
		if(list.isEmpty()||list.size()==0){
			throw new RuntimeException("get vm manager info error!");
		}else{
			vmManagerServerPo = list.get(0);
		}
		String userName = vmManagerServerPo.getUserName();
		String password = "";
		try {
			CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceUser(clusterPo.getManageServer(), userName);
			password = pwpo.getPassword();
			if(StringUtils.isBlank(password))
				throw new Exception("获取ManagerServer["+clusterPo.getManageServer()+"] password is null");
			password = PwdUtil.decryption(password);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		IDataObject reqData = DataObject.CreateDataObject();
		
		HeaderDO header = HeaderDO.CreateHeaderDO();		
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.DESTORY_RESOURCE);
		RmDatacenterPo dcPo = rmDCDAO.getDataCenterById(vmManagerServerPo.getDatacenterId());
		header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(VMFlds.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS+vmManagerServerPo.getManageIp()+CloudClusterConstants.VCENTER_URL_SDK);
		body.setString(VMFlds.VCENTER_USERNAME, userName);
		body.setString(VMFlds.VCENTER_PASSWORD, password);
		
		List<String> clusterName = Lists.newArrayList();
		clusterName.add( clusterPo.getEname());
		body.setList(VMFlds.DESTORY_RESOURCE_NAME, clusterName);//回收cdp名称
		body.setString(VMFlds.DESTORY_TYPE, "CLUSTER");
		IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");

		IDataObject rspData = null;
		reqData.setDataObject(MesgFlds.HEADER, header);
		reqData.setDataObject(MesgFlds.BODY, body);
		try {
			rspData = invoker.invoke(reqData, 300000);
			if(rspData==null){
				result="N";
			}else{
				HeaderDO header1 = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
				if(MesgRetCode.SUCCESS.equals(header1.getRetCode())){
					result="Y";
				}else{
					result="N";
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public String updateClusterInterface(RmClusterPo clusterPo,String oldName) throws RollbackableBizException{
		String result = new String();
		//获取cdp所在资源池ids
		RmVmManageServerPo vmManagerServerPo = null;
		List<RmVmManageServerPo> list= rmVmMgServerDAO.findByID("findRmVmManagerServerPo", clusterPo.getManageServer());
		if(list.isEmpty()||list.size()==0){
			throw new RuntimeException("get vm manager info error!");
		}else{
			vmManagerServerPo = list.get(0);
		}
		String userName = vmManagerServerPo.getUserName();
		String password = "";
		try {
			CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceUser(clusterPo.getManageServer(), userName);
			password = pwpo.getPassword();
			if(StringUtils.isBlank(password))
				throw new Exception("获取ManagerServer["+clusterPo.getManageServer()+"] password is null");
			password = PwdUtil.decryption(password);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		IDataObject reqData = DataObject.CreateDataObject();
		
		HeaderDO header = HeaderDO.CreateHeaderDO();		
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.RESOURCE_RENAME);
		RmDatacenterPo dcPo = rmDCDAO.getDataCenterById(vmManagerServerPo.getDatacenterId());
		header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(VMFlds.URL, CloudClusterConstants.VCENTER_URL_HTTPS+vmManagerServerPo.getManageIp()+CloudClusterConstants.VCENTER_URL_SDK);
		body.setString(VMFlds.USERNAME, userName);
		body.setString(VMFlds.PASSWORD, password);
		
		body.setString(VMFlds.RESOURCE_NAME, oldName);
		body.setString(VMFlds.RESOURCE_NEW_NAME, clusterPo.getEname());
		
		IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");

		IDataObject rspData = null;
		reqData.setDataObject(MesgFlds.HEADER, header);
		reqData.setDataObject(MesgFlds.BODY, body);
		try {
			rspData = invoker.invoke(reqData, 300000);
			if(rspData==null){
				result="N";
			}else{
				HeaderDO header1 = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
				if(MesgRetCode.SUCCESS.equals(header1.getRetCode())){
					result="Y";
				}else{
					result="N";
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	public String createCdpInterface(RmCdpPo cdpPo) throws RollbackableBizException {
		String result = "N";
		String dc_flag = "N";
		
		//获取cdp所在资源池ids
		String manageServerId = cdpPo.getVmManagedServerIds().iterator().next();
		RmVmManageServerPo vmManagerServerPo = null;
		List<RmVmManageServerPo> list = rmVmMgServerDAO.findByID("findRmVmManagerServerPo", manageServerId);
		if(list.isEmpty()||list.size()==0){
			throw new RollbackableBizException("get vm manager info error!");
		}else{
			vmManagerServerPo = list.get(0);
		}
		String userName = vmManagerServerPo.getUserName();
		String password = "";
		try {
			CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceUser(manageServerId, userName);
			password = pwpo.getPassword();
			if(StringUtils.isBlank(password))
				throw new RollbackableBizException("获取ManagerServer["+manageServerId+"] password is null");
			password = PwdUtil.decryption(password);
		} catch (RollbackableBizException e) {
			throw new RuntimeException(e);
		}
		RmDatacenterPo dcPo = rmDCDAO.getDataCenterById(vmManagerServerPo.getDatacenterId());
		String vc_url = CloudClusterConstants.VCENTER_URL_HTTPS+vmManagerServerPo.getManageIp()+CloudClusterConstants.VCENTER_URL_SDK;
		//获取VC中数据中心信息判断是否已创建数据中心
		dc_flag = this.getVCInfoInterface(vc_url, userName, password,dcPo);
		if(dc_flag.equals("N")){
			//需要在VC上创建DC
			result = createDCInVCenterInterface(vc_url, userName, password,dcPo);
			if(result.equals("N")){
				throw new RollbackableBizException("创建数据中心"+dcPo.getEname()+"失败！");
			}
		}
		IDataObject reqData = DataObject.CreateDataObject();
		
		HeaderDO header = HeaderDO.CreateHeaderDO();		
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.CREATE_FOLDER);
		header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(VMFlds.VCENTER_URL, vc_url);
		body.setString(VMFlds.VCENTER_USERNAME, userName);
		body.setString(VMFlds.VCENTER_PASSWORD, password);

		body.setString(VMFlds.PARENT_TYPE, VmGlobalConstants.VC_RESOURCE_TYPE_DATACENTER);
		body.setString(VMFlds.PARENT_NAME, dcPo.getEname());
		body.setString(VMFlds.FOLDER_NAME, cdpPo.getEname());
		
		IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");

		IDataObject rspData = null;
		reqData.setDataObject(MesgFlds.HEADER, header);
		reqData.setDataObject(MesgFlds.BODY, body);
		try {
			rspData = invoker.invoke(reqData, 300000);
			if(rspData==null){
				result="N";
			}else{
				HeaderDO header1 = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
				if(MesgRetCode.SUCCESS.equals(header1.getRetCode())){
					result="Y";
				}else{
					result="N";
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	//判断VCenter中是否已创建DC信息
	public String getVCInfoInterface(String url,String username,String password,RmDatacenterPo dcPo){
		//String result = "N";
		IDataObject reqData = DataObject.CreateDataObject();
		
		HeaderDO header = HeaderDO.CreateHeaderDO();		
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.RESOURCE_EXIST);
		header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(VMFlds.VCENTER_URL, url);
		body.setString(VMFlds.VCENTER_USERNAME, username);
		body.setString(VMFlds.VCENTER_PASSWORD, password);
		body.setList(VMFlds.DESTORY_RESOURCE_NAME, Arrays.asList(new String[]{dcPo.getEname()}));

		IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");

		IDataObject rspData = null;
		reqData.setDataObject(MesgFlds.HEADER, header);
		reqData.setDataObject(MesgFlds.BODY, body);
		try {
			rspData = invoker.invoke(reqData, 300000);
			if(rspData==null){
				throw new Exception("获取VC信息失败！");
			}else{
				HeaderDO header1 = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
				if(MesgRetCode.SUCCESS.equals(header1.getRetCode())){
					//已经在VC中创建DC
					return "Y";
				}else{
					return "N";
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//在VC上创建DC
		public String createDCInVCenterInterface(String url,String username,String password,RmDatacenterPo dcPo){
			IDataObject reqData = DataObject.CreateDataObject();
			
			HeaderDO header = HeaderDO.CreateHeaderDO();		
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperation(VMOpration.CREATE_DATACENTER);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
			reqData.setDataObject(MesgFlds.HEADER, header);
			BodyDO body = BodyDO.CreateBodyDO();
			body.setString(VMFlds.VCENTER_URL, url);
			body.setString(VMFlds.USERNAME, username);
			body.setString(VMFlds.PASSWORD, password);
			body.setString(VMFlds.DATACENTER_NAME, dcPo.getEname());

			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");

			IDataObject rspData = null;
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);
			try {
				rspData = invoker.invoke(reqData, 300000);
				if(rspData==null){
					throw new Exception("获取VC信息失败！");
				}else{
					HeaderDO header1 = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
					if(MesgRetCode.SUCCESS.equals(header1.getRetCode())){
						return "Y";
					}else{
						return "N";
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		
	
		/*public String destoryCdpInterface(RmCdpVo cdpVo) throws RollbackableBizException{
			String result;
			//获取cdp所在资源池ids
			RmVmManageServerPo vmManagerServerPo = null;
			List<RmVmManageServerPo> list= rmVmMgServerDAO.findByID("findRmVmManagerServerPo", cdpVo.getManageServer());
			if(list.isEmpty()||list.size()==0){
				throw new RuntimeException("get vm manager info error!");
			}else{
				vmManagerServerPo = list.get(0);
			}
			String userName = vmManagerServerPo.getUserName();
			String password = "";
			try {
				CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceUser(cdpVo.getManageServer(), userName);
				password = pwpo.getPassword();
				if(StringUtils.isBlank(password))
					throw new Exception("获取ManagerServer["+cdpVo.getManageServer()+"] password is null");
				password = PwdUtil.decryption(password);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			IDataObject reqData = DataObject.CreateDataObject();
			
			HeaderDO header = HeaderDO.CreateHeaderDO();		
			header.setResourceClass(CloudClusterConstants.VM_RES_CLASS);
			header.setResourceType(CloudClusterConstants.VM_RES_TYPE);
			header.setOperation(VMOpration.DESTORY_RESOURCE);
			RmDatacenterPo dcPo = rmDCDAO.getDataCenterById(vmManagerServerPo.getDatacenterId());
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
			reqData.setDataObject(MesgFlds.HEADER, header);
			BodyDO body = BodyDO.CreateBodyDO();
			body.setString(VMFlds.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS+vmManagerServerPo.getManageIp()+CloudClusterConstants.VCENTER_URL_SDK);
			body.setString(VMFlds.VCENTER_USERNAME, userName);
			body.setString(VMFlds.VCENTER_PASSWORD, password);
			
			List<String> cdpName = Lists.newArrayList();
			cdpName.add( cdpVo.getEname());
			body.setList(VMFlds.DESTORY_RESOURCE_NAME, cdpName);//回收cdp名称
			body.setString(VMFlds.DESTORY_TYPE, "CDP");
			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");

			IDataObject rspData = null;
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);
			try {
				rspData = invoker.invoke(reqData, 300000);
				if(rspData==null){
					result="N";
				}else{
					HeaderDO header1 = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
					if(MesgRetCode.SUCCESS.equals(header1.getRetCode())){
						result="Y";
					}else{
						result="N";
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return result;
		}*/
		
		public String getCdpInfoByInterface(String cdpName,String managerServer) throws RollbackableBizException{

			String result = new String();
			//获取cdp所在资源池ids
			RmVmManageServerPo vmManagerServerPo = null;
			List<RmVmManageServerPo> list= rmVmMgServerDAO.findByID("findRmVmManagerServerPo", managerServer);
			if(list.isEmpty()||list.size()==0){
				throw new RuntimeException("get vm manager info error!");
			}else{
				vmManagerServerPo = list.get(0);
			}
			String userName = vmManagerServerPo.getUserName();
			String password = "";
			try {
				CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceUser(managerServer, userName);
				password = pwpo.getPassword();
				if(StringUtils.isBlank(password))
					throw new Exception("获取ManagerServer["+managerServer+"] password is null");
				password = PwdUtil.decryption(password);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			IDataObject reqData = DataObject.CreateDataObject();
			
			HeaderDO header = HeaderDO.CreateHeaderDO();		
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperationBean("queryFolderManagerServiceImpl");
			RmDatacenterPo dcPo = rmDCDAO.getDataCenterById(vmManagerServerPo.getDatacenterId());
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
			reqData.setDataObject(MesgFlds.HEADER, header);
			BodyDO body = BodyDO.CreateBodyDO();
			String vc_url = CloudClusterConstants.VCENTER_URL_HTTPS+vmManagerServerPo.getManageIp()+CloudClusterConstants.VCENTER_URL_SDK;
			body.setString(VMFlds.VCENTER_URL,vc_url);
			body.setString(VMFlds.VCENTER_USERNAME, userName);
			body.setString(VMFlds.VCENTER_PASSWORD, password);
			body.setString(VMFlds.FOLDER_NAME, cdpName);
			body.setString(VMFlds.PARENT_TYPE ,VmGlobalConstants.VC_RESOURCE_TYPE_DATACENTER);
			body.setString(VMFlds.PARENT_NAME, "");
			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");

			IDataObject rspData = null;
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);
			try {
				rspData = invoker.invoke(reqData, 300000);
				if(rspData==null){
					throw new RollbackableBizException("查询失败！");
				}else{
					HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
					if(MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())){
						result=rspHeader.getRetMesg();
					}else{
						throw new RollbackableBizException("查询失败！");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return result;
		
		}

		@Override
		public List<RmClusterPo> findAllCluster()
				throws RollbackableBizException {
			return rmClusterDAO.findAllCluster();
		}

		@Override
		public void saveClusterBySample(RmClusterPo clusterPo) throws RollbackableBizException {
				
		}

		@Override
		public void deleteClusterListByExistIds(List<String> isExistIds) throws RollbackableBizException {
			// TODO Auto-generated method stub
			
		}
}
