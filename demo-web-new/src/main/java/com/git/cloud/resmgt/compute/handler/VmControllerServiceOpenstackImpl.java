package com.git.cloud.resmgt.compute.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.common.enums.KeyTypeEnum;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.ScanResult;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.network.dao.IProjectVpcDao;
import com.git.cloud.resmgt.network.model.po.CloudProjectPo;

public class VmControllerServiceOpenstackImpl implements VmControllerService{
	private static Logger logger = LoggerFactory.getLogger(VmControllerServiceOpenstackImpl.class);
	@Autowired
	private IProjectVpcDao projectVpcDao;
	@Autowired
	private ICmDeviceService iCmDeviceService;
	
	
	@Override
	public String vmRunningState(String vmId) throws Exception {
		String runningState = "";
		CloudProjectPo projectVpcPo =  projectVpcDao.findProjectInfoByVmId(vmId); 
		String projectId = projectVpcPo.getProjectId();
		String projectName = projectVpcPo.getProjectName();
		String openstackId = projectVpcPo.getVmMsId();
		String openstackIp = projectVpcPo.getManageIp();
		String domainName = projectVpcPo.getDomainName();
		String version = "";
		logger.info("ProjectId:"+projectId+",ProjectName:"+projectName+",OpenstackId:"+openstackId+",OpenstackIp:"+openstackIp);
		String vmTypeCode = "";
		try {
			vmTypeCode = iCmDeviceService.findVmTypeCodeByVmId(vmId);
		} catch(Exception e) {
			logger.error("异常exception",e);
			vmTypeCode = "";
		}
		try {
			String vmKeyType = KeyTypeEnum.COMPUTE_VM.getValue();
			if(vmTypeCode.equals(RmVirtualType.OPENSTACKIRONIC.getValue())) {
				vmKeyType = KeyTypeEnum.COMPUTE_BAREMETAL.getValue();
			}
			String targetProjectId = projectVpcPo.getIaasUuid();
			CmDeviceVMShowBo vmInfo = iCmDeviceService.getCmDeviceVMInfo(vmId);
			String targetVmId = vmInfo.getIaasUuid();
			OpenstackIdentityModel model = new OpenstackIdentityModel();
			model.setVersion(version);
            model.setOpenstackIp(openstackIp);
            model.setDomainName(domainName);
            model.setProjectId(targetProjectId);
            model.setProjectName(projectName);
			runningState = IaasInstanceFactory.computeInstance(version).getServerDetail(model, targetVmId, true);
			if(runningState.equals("ACTIVE")){
				runningState = "poweron";
			}else if(runningState.equals("SHUTOFF")){
				runningState = "poweroff";
			}
			logger.info("虚拟机Id:"+vmId+",设备当前状态为："+runningState);
			//更新虚拟机状态
			CmDevicePo vmVo = new CmDevicePo();
			vmVo.setId(vmId);
			vmVo.setRunningState(runningState);
			iCmDeviceService.updateCmdeviceRunningState(vmVo);
		} catch (Exception e) {
			logger.debug("获取虚拟机状态出现异常"+e);
			runningState =  "获取虚拟机状态出现异常,"+e.getMessage();
		}
		return runningState;
	
	}
	
	@Override
	public List<ScanResult> scanAndUpdateVmIndicator(List<CmDevicePo> vmPoList,
			String virtualTypeCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
