/**
 * @Title:IpAllocToDeviceNewImpl.java
 * @Package:com.git.cloud.policy.service.impl
 * @Description:TODO
 * @author zhuzy
 * @date 2015-10-8 上午10:56:07
 * @version V1.0
 */
package com.git.cloud.policy.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.git.cloud.common.enums.RmPlatForm;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.handler.dao.AutomationDAO;
import com.git.cloud.policy.dao.IRmNwRuleDao;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.resmgt.common.model.bo.CmHostBo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.compute.dao.ICmPortGroupDao;
import com.git.cloud.resmgt.compute.model.po.CmPortGroupPo;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.google.common.collect.Lists;

/**
 * @ClassName:IpAllocToDeviceNewImpl
 * @Description:TODO
 * @author zhuzy
 * @date 2015-10-8 上午10:56:07
 *
 *
 */
public class IpAllocToDeviceNewImpl implements IIpAllocToDeviceNewService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AutomationDAO automationDao;
	@Autowired
	private ICmPortGroupDao cmPortGroupDao;
	private IRmNwRuleDao rmNwRuleDao;
	
	
	public IRmNwRuleDao getRmNwRuleDao() {
		return rmNwRuleDao;
	}
	public void setRmNwRuleDao(IRmNwRuleDao rmNwRuleDao) {
		this.rmNwRuleDao = rmNwRuleDao;
	}
	/**
	 * 根据设备ID查询为设备配置的设备列表，并按照设备、规则，形式返回IP列表
	 * @throws RollbackableBizException 
	 */
	@Override
	public List<DeviceNetIP> qryAllocedIPForDevices(String deviceId) throws Exception {
		// 根据设备IDs获取设备所有的ip地址
		List<OpenstackIpAddressPo> openstackIpAddressPos = rmNwRuleDao.findTotalIpListByDeviceIDs(Lists.newArrayList(deviceId));
		//根据设备ID查收设备所属的网络资源池信息,及分配物理机信息
		List<CmVmPo> vmInfoList = automationDao.getVms(Arrays.asList(deviceId));
		if (vmInfoList == null || vmInfoList.size() > 1) {
			logger.error("查询设备对应的虚拟机信息为空，虚拟机ＩＤ为" + deviceId);
			throw new Exception("查询设备对应的虚拟机信息为空，虚拟机ＩＤ为" + deviceId);
		}
		String hostId = vmInfoList.get(0).getHostId();
		List<DeviceNetIP> deviceNetIPs = new ArrayList<DeviceNetIP>();
		DeviceNetIP deviceNetIP = new DeviceNetIP();
		deviceNetIP.setDeviceID(deviceId);
		Map<String,Map<String, List<NetIPInfo>>> deviceIdMap = new HashMap<String,Map<String, List<NetIPInfo>>>();
		for(OpenstackIpAddressPo addressPo :openstackIpAddressPos){
			NetIPInfo netIPInfo = new NetIPInfo();
			netIPInfo.setId(addressPo.getId());
			netIPInfo.setIp(addressPo.getIp());
			netIPInfo.setGateWay(addressPo.getGateWay());
			netIPInfo.setIpMask(addressPo.getIpMask());
			netIPInfo.setVlanID(addressPo.getVlanId());
			logger.info("addressPo.getIp :{}",new Object[]{addressPo.getIp()});
			//只有vmware类型的虚拟机才需要网络端口组前缀
			HashMap<String, String> extendInfoMap = new HashMap<String, String>();
			logger.info("RmPlatForm.X86.getValue() :{} deviceId：{} hostId :{} addressPo.getPlatformType():{}",
					new Object[] { RmPlatForm.X86.getValue(), deviceId, hostId, addressPo.getPlatformType() });
			if(RmPlatForm.X86.getValue().equalsIgnoreCase(addressPo.getPlatformType())){
				Map<String ,String> parameMap = new HashMap<String,String>();
				parameMap.put("hostId",hostId);
				//如果端口组名称有值，则直接使用端口组名称去查找，否则使用vlanID
				if (StringUtils.isNotEmpty(addressPo.getNetworkLable())) { 
					parameMap.put("networkTag", addressPo.getNetworkLable());
				}else{
					parameMap.put("vlanId", addressPo.getVlanId());
				}
				logger.info("JSON parameMap:{}",String.valueOf(JSON.toJSON(parameMap)));
				CmPortGroupPo cmPortGroupPo = cmPortGroupDao.getCmPortGroupPoByVmHostId(parameMap);
				if(cmPortGroupPo != null){
					extendInfoMap.put("VMWARE_PGNAM", cmPortGroupPo.getNetworkTag());
					extendInfoMap.put("PORT_GROUP_ID",cmPortGroupPo.getPortGroupId());
				}else{
					throw new RollbackableBizException("请先检查是否已将虚拟交换机信息导入到云平台,或检查该虚拟机IP所在的网络资源池，VLANID是否配置正确");
				}
			}
			netIPInfo.setExtendInfo(extendInfoMap);
			if(deviceIdMap.containsKey(addressPo.getInstanceId())){
				if(deviceIdMap.get(addressPo.getInstanceId()).containsKey(addressPo.getRuleListCode())){
					deviceIdMap.get(addressPo.getInstanceId()).get(addressPo.getRuleListCode()).add(netIPInfo);
				}else{
					List<NetIPInfo> ipTempList = new ArrayList<NetIPInfo>();
					ipTempList.add(netIPInfo);
					deviceIdMap.get(addressPo.getInstanceId()).put(addressPo.getRuleListCode(),ipTempList);
				}
			}else{
				Map<String, List<NetIPInfo>> ruleCodeMap = new HashMap<String, List<NetIPInfo>>();
				List<NetIPInfo> ipTempList = new ArrayList<NetIPInfo>();
				ipTempList.add(netIPInfo);
				ruleCodeMap.put(addressPo.getRuleListCode(), ipTempList);
				deviceIdMap.put(addressPo.getInstanceId(), ruleCodeMap);
			}
		}
		deviceNetIP.setNetIPs(deviceIdMap.get(deviceId));
		deviceNetIPs.add(deviceNetIP);
		return deviceNetIPs;
	}
	@Override
	public DeviceNetIP qryAllocedIPForHost(String deviceId) throws Exception {
		CmHostBo host = rmNwRuleDao.qryIPForHost(deviceId);
		DeviceNetIP deviceNetIP = new DeviceNetIP();
		deviceNetIP.setDeviceID(deviceId);
		deviceNetIP.setHostIp(host.getHostIp());
		return null;
	}
}
