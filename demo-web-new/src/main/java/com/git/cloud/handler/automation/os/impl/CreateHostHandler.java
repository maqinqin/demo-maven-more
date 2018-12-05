
package com.git.cloud.handler.automation.os.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.VmRestModel;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.network.dao.IProjectVpcDao;
import com.git.cloud.resmgt.openstack.dao.impl.SecurityGroupsDaoImpl;
import com.git.cloud.resmgt.openstack.model.vo.SecurityGroupsDeviceRefVo;
import com.git.cloud.resmgt.openstack.model.vo.SecurityGroupsVo;
import com.git.support.common.OSOperation_bak;

import net.sf.json.JSONObject;

/** 
 * 物理机计算实例创建
 * @author SunHailong 
 * @version 版本号 2017-3-30
 */
public class CreateHostHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(CreateHostHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "创建第" + (i+1) + "台机器";
			logger.debug(logCommon + "开始...");
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String domainName = deviceParams.get("DOMAIN_NAME");
			String openstackIp = deviceParams.get("OPENSTACK_IP");
			String openstackId = deviceParams.get("OPENSTACK_ID");
			String projectId = deviceParams.get("PROJECT_ID");
			String ipData = deviceParams.get("IP_DATAS");
			String version = deviceParams.get("VERSION");
			if(version == null || "".equals(version)) {
				throw new Exception("VERSION为空，请检查参数[VERSION].");
			}
			String manageOneIp = deviceParams.get("MANAGE_ONE_IP");
			if(manageOneIp == null || "".equals(manageOneIp)) {
				throw new Exception("MANAGE_ONE_IP为空，请检查参数[MANAGE_ONE_IP].");
			}
			
			VmRestModel vmRestModel = new VmRestModel();
			vmRestModel.setAzName(deviceParams.get("AVAILABILITY_ZONE"));
			vmRestModel.setFlavorId(deviceParams.get("FLAVOR_ID"));
			vmRestModel.setNetworkId(deviceParams.get("NETWORK_ID"));
			vmRestModel.setServerIp(deviceParams.get("SERVER_IP"));
			vmRestModel.setServerName(deviceParams.get("SERVER_NAME"));
			vmRestModel.setImageId(deviceParams.get("IMAGE_ID"));
			vmRestModel.setSubnetId(deviceParams.get("SUBNET_ID"));
			vmRestModel.setRouterId(deviceParams.get("ROUTER_ID"));
			
			OpenstackIdentityModel model = new OpenstackIdentityModel();
			model.setVersion(version);
			model.setProjectId(projectId);
			model.setOpenstackIp(openstackIp);
			model.setDomainName(domainName);
			model.setManageOneIp(manageOneIp);
			
			IaasInstanceFactory.computeInstance(version).createHost(model, vmRestModel, ipData);
			try {
				// 休眠
				Thread.sleep(30000);
			} catch(Exception e) {
				e.printStackTrace();
			}
			String serverList = IaasInstanceFactory.computeInstance(version).getBuildBareMetal(model, vmRestModel.getServerName());
			JSONObject jsonObj = JSONObject.fromObject(serverList);
			net.sf.json.JSONArray jsonArr = jsonObj.getJSONArray("servers");
			String serverId = "";
			for (Object object : jsonArr) {
				JSONObject obj = (JSONObject) object;
				String name = obj.getString("name");
				if(name.equals(deviceParams.get("SERVER_NAME"))) {
					serverId = obj.getString("id");
				}
			}
			logger.debug(logCommon + "完成，开始进行数据处理...");
			//添加默认安全组
			SecurityGroupsDaoImpl securityGroupsDao = (SecurityGroupsDaoImpl) WebApplicationManager.getBean("securityGroupsDao");
			IProjectVpcDao projectVpcDao = (IProjectVpcDao) WebApplicationManager.getBean("projectVpcDao");
			String myProjectId = projectVpcDao.selectProjectIdByIaasUuid(projectId).getVpcId();
			SecurityGroupsVo vo = securityGroupsDao.getSecurityGroupsVoByProjectIdAndName(myProjectId);
			if(vo != null){
				SecurityGroupsDeviceRefVo v = new SecurityGroupsDeviceRefVo();
				v.setId(UUIDGenerator.getUUID());
				v.setDeviceId(deviceIdList.get(i));
				v.setSecurityGroupId(vo.getId());
				securityGroupsDao.saveVmRef(v);
			}
			try {
				// 休眠30秒
				Thread.sleep(15000);
			} catch(Exception e) {
				e.printStackTrace();
			}
			String serverDetail = IaasInstanceFactory.computeInstance(version).getServerDetail(model, serverId, false);
			JSONObject json = JSONObject.fromObject(serverDetail);
			String status = json.getJSONObject("server").getString("status");
			if("error".equals(status)||"ERROR".equals(status)){
				IaasInstanceFactory.computeInstance(version).operateVm(model, serverId, OSOperation_bak.DELETE_VM, 204);
				throw new RollbackableBizException("虚机创建失败,状态："+status);
			}
			setHandleResultParam(deviceIdList.get(i), "TARGET_SERVER_ID", serverId);
			ICmVmDAO cmVmDAO = (ICmVmDAO) WebApplicationManager.getBean("cmVmDAO");
			CmVmPo cmvm = new CmVmPo();
			cmvm.setId(deviceIdList.get(i));
			cmvm.setIaasUuid(serverId);
			cmVmDAO.updateCmVm(cmvm);
			logger.debug(logCommon + "结束...");
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}
}
