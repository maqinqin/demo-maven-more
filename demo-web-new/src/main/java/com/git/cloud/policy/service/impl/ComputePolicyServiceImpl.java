package com.git.cloud.policy.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.cloudservice.dao.ICloudServiceDao;
import com.git.cloud.cloudservice.model.HaTypeEnum;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.CollectionUtil;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.policy.dao.IComputePolicyDAO;
import com.git.cloud.policy.model.PolicyInfoVoComparator;
import com.git.cloud.policy.model.vo.CloudSortObjectEnum;
import com.git.cloud.policy.model.vo.CurrentTypeEnum;
import com.git.cloud.policy.model.vo.PolicyInfoVo;
import com.git.cloud.policy.model.vo.PolicyResultVo;
import com.git.cloud.policy.model.vo.RequsetResInfoVo;
import com.git.cloud.policy.service.IComputePolicyService;
import com.git.cloud.policy.service.IRmVmParamService;
import com.git.cloud.policy.service.IRmVmRulesService;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.dao.ICmHostDatastoreRefDAO;
import com.git.cloud.resmgt.common.model.DeviceTypeEnum;
import com.git.cloud.resmgt.common.model.VmDistriTypeEnum;
import com.git.cloud.resmgt.common.model.po.CmHostDatastorePo;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-23
 */
public class ComputePolicyServiceImpl implements IComputePolicyService {
	
	private static Logger log = LoggerFactory.getLogger(ComputePolicyServiceImpl.class);

	private IComputePolicyDAO computePolicyDAO;
	
	private ICloudServiceDao cloudServiceDao;
	
	private IRmVmParamService rmVmParamService;
	
	private IRmVmRulesService rmVmRulesService;
	
	@Autowired
	private ICmHostDatastoreRefDAO cmHostDatastoreRefDAO; 
	Map<String, Boolean> sortRulesMap = new HashMap<String, Boolean>();
	
	
	public IRmVmParamService getRmVmParamService() {
		return rmVmParamService;
	}

	/**
	 * @return the rmVmRulesService
	 */
	public IRmVmRulesService getRmVmRulesService() {
		return rmVmRulesService;
	}

	/**
	 * @param rmVmRulesService the rmVmRulesService to set
	 */
	public void setRmVmRulesService(IRmVmRulesService rmVmRulesService) {
		this.rmVmRulesService = rmVmRulesService;
	}

	public void setRmVmParamService(IRmVmParamService rmVmParamService) {
		this.rmVmParamService = rmVmParamService;
	}

	public IComputePolicyDAO getComputePolicyDAO() {
		return computePolicyDAO;
	}

	public void setComputePolicyDAO(IComputePolicyDAO computePolicyDAO) {
		this.computePolicyDAO = computePolicyDAO;
	}

	public ICloudServiceDao getCloudServiceDao() {
		return cloudServiceDao;
	}

	public void setCloudServiceDao(ICloudServiceDao cloudServiceDao) {
		this.cloudServiceDao = cloudServiceDao;
	}
	/**
	 * @Description:distribHostForVm(根据服务申请分配虚机)
	 * @param bmSrRrinfoPo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@Override
	public List<PolicyResultVo> distribHostForVm(BmSrRrinfoPo bmSrRrinfoPo)
			throws Exception {
		if(bmSrRrinfoPo != null ){
			if(bmSrRrinfoPo != null){
				//查找服务定义
				CloudServicePo servicePo = null;
				JSONObject json = JSONObject.parseObject(bmSrRrinfoPo.getParametersJson());
				List<CloudServicePo> list = cloudServiceDao.findByID("CloudService.load",json.getString("serviceId"));
				if (!CollectionUtil.hasContent(list)) {
					throw new Exception("服务定义为空");
				}
				servicePo = list.get(0);
				//1：虚拟机，2：物理机
				if(servicePo !=null && "2".equals(servicePo.getHostType())){
					return distribHostForHost(bmSrRrinfoPo,servicePo);
				}
				return distribHostForVm(bmSrRrinfoPo,servicePo);
			}else{
				throw new Exception();
			}
		}else{
			throw new Exception("服务请求信息为空");
		}
	}
	
	/**
	 * @Description:distribHostForTy(根据服务申请，服务定义，服务套餐分配物理机)
	 * @param bmSrRrinfoPo
	 * @param servicePo
	 * @return
	 * @throws Exception
	 */
	private List<PolicyResultVo> distribHostForHost(BmSrRrinfoPo bmSrRrinfoPo, CloudServicePo servicePo) throws Exception {
		String validateHost = this.validation(bmSrRrinfoPo, servicePo);
		if(validateHost != null){
			throw new Exception(validateHost);
		}
		return this.findHostBybmSrRrinfoPo(bmSrRrinfoPo, servicePo);
	}

	/**
	 * @Description:findHostBybmSrRrinfoPo(根据服务申请，查询所有符合条件的物理机)
	 * @param bmSrRrinfoPo
	 * @param servicePo
	 * @return
	 * @throws Exception
	 */
	protected List<PolicyResultVo> findHostBybmSrRrinfoPo(BmSrRrinfoPo bmSrRrinfoPo,CloudServicePo servicePo) throws Exception{
		JSONObject json = JSONObject.parseObject(bmSrRrinfoPo.getParametersJson());
		List<PolicyResultVo> resultList = null;
		List<PolicyInfoVo> list = new ArrayList<PolicyInfoVo>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hostType", RmHostType.PHYSICAL.getValue());
		map.put("platFormType", servicePo.getPlatformType());
		map.put("cpu", json.getIntValue("cpu"));
		map.put("mem", json.getIntValue("mem"));
		
		String duId = json.get("duId") == null ? null : json.getString("duId");
		if(StringUtils.hasText(duId)){
			//通过应用系统服务器角色，查询资源池信息
			map.put("duId", duId);
			// 查询所有可提供分配的物理机
			log.info("通过服务器角色查询，传递的参数信息："+map.toString());
			list = computePolicyDAO.findHostResPoolsForHost(map);
		}else{
			String rmHostResPoolId = json.get("rmHostRespoolId") == null ? null : json.getString("rmHostRespoolId");
			//传递计算资源池id，通过资源池id，查询资源池信息
			map.put("rmHostResPoolId", rmHostResPoolId);
			log.info("指定资源池查询，传递的参数信息："+map.toString());
			list = computePolicyDAO.findHostResPoolsForHostByMap(map);
		}
		
		if(list == null || list.size() == 0){
			throw new Exception("未查找到资源信息.");
		}
		
		int vmNum = json.getIntValue("vmNum");
		HashMap<String, List<PolicyResultVo>> clusterMap = new HashMap<String, List<PolicyResultVo>> (); // key 集群ID
		String clusterId;
		List<PolicyResultVo> prList;
		for(PolicyInfoVo currentPolicyInfo : list){
			clusterId = currentPolicyInfo.getClusterId();
			if(clusterMap.get(clusterId) == null) {
				prList = new ArrayList<PolicyResultVo> ();
				clusterMap.put(clusterId, prList);
			}
			PolicyResultVo resultVo = new PolicyResultVo();
			resultVo.setPoolId(currentPolicyInfo.getPoolId());
			resultVo.setClusterId(clusterId);
			resultVo.setHostId(currentPolicyInfo.getHostId());
			clusterMap.get(clusterId).add(resultVo);
			if(clusterMap.get(clusterId).size() == vmNum) {
				resultList = clusterMap.get(clusterId);
				break;
			}
		}
		if(resultList == null) {
			throw new Exception("未查找到足够的资源信息.");
		}
		return resultList;
	}

	/**
	 * 
	 * @Description:distribHostForVm(根据服务申请，服务定义，服务套餐分配虚机)
	 * @param bmSrRrinfoPo
	 * @param servicePo
	 * @return
	 * @throws Exception
	 */
	private List<PolicyResultVo> distribHostForVm(BmSrRrinfoPo bmSrRrinfoPo, CloudServicePo servicePo) throws Exception {
		String validateResult = this.validation(bmSrRrinfoPo, servicePo);
		if (validateResult != null) {
			throw new Exception(validateResult);
		}
		StringBuffer message = new StringBuffer();
		List<PolicyInfoVo> respools = this.findHostResPoolsBybmSrRrinfoPo(bmSrRrinfoPo, servicePo, message);
		if (!CollectionUtil.hasContent(respools)) {
			throw new Exception("无满足条件的资源");
		}
		
		JSONObject json = JSONObject.parseObject(bmSrRrinfoPo.getParametersJson());
		
		return this.distribHostForVm(respools, bmSrRrinfoPo, (json.getIntValue("vmNum")), servicePo.getPlatformType(), servicePo.getHaType(), message.toString());
	}
	

	/**
	 * 
	 * @Description:validation(验证虚机分配条件)
	 * @param bmSrRrinfoPo
	 * @param serviceSetPo
	 * @param servicePo
	 * @return
	 */
	protected String validation(BmSrRrinfoPo bmSrRrinfoPo,CloudServicePo servicePo) {
		if(servicePo==null){
			return "云服务不存在!";
		}
		if(servicePo.getVmBase()==null) {
			return "服务套餐中虚机基数不能为空!";
		}
		
		if(servicePo.getVmBase()== null || servicePo.getVmBase() < 1) {
			return "服务套餐中虚机基数不能小于1!";
		}
		JSONObject json = JSONObject.parseObject(bmSrRrinfoPo.getParametersJson());
		if(json.getIntValue("vmNum") <1) {
			return "创建的虚机数量不能小于1!";
		}
		return null;
	}
	
	/**
	 * 
	 * @Description:findHostResPoolsBybmSrRrinfoPo(查找所有的计算资源信息)
	 * @param bmSrRrinfoPo
	 * @param serviceSetPo
	 * @param servicePo
	 * @return
	 * @throws Exception 
	 */
	protected List<PolicyInfoVo> findHostResPoolsBybmSrRrinfoPo(BmSrRrinfoPo bmSrRrinfoPo,CloudServicePo servicePo,StringBuffer message) throws Exception{
		List<PolicyInfoVo> list = new ArrayList<PolicyInfoVo>();
		// 虚拟机分配时，定位资源的排序规则
		sortRulesMap = rmVmRulesService.findAllRmvmRulesPo();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("playFormType", servicePo.getPlatformType());
		String vmDistriType = null;
		if(servicePo.getHaType().equals(HaTypeEnum.SINGLE.toString())){
			vmDistriType = VmDistriTypeEnum.VM_DISTRI_TYPE_SINGLE.getValue();
		}else{
			vmDistriType = VmDistriTypeEnum.VM_DISTRI_TYPE_CLUSTER.getValue();
		}
		map.put("vmDistriType", vmDistriType);
		map.put("mixType", VmDistriTypeEnum.VM_DISTRI_TYPE_MIX.getValue());
		map.put("vmType", servicePo.getVmType());
		map.put("deviceType", DeviceTypeEnum.DEVICE_TYPE_SERVER.getValue());
		//传递应用系统和服务器角色
		JSONObject json = JSONObject.parseObject(bmSrRrinfoPo.getParametersJson());
		String duId = json.get("duId") == null ? null:json.getString("duId");
		String datacenterId = json.get("datacenterId") == null ? null:json.getString("datacenterId");
		map.put("datacenterId", datacenterId);
		if(!CommonUtil.isEmpty(duId)){
			map.put("duId", duId);
		}else{
			//传递计算资源池id
			String hostResPoolId = json.get("rmHostRespoolId") == null ? null:json.getString("rmHostRespoolId");
			map.put("rmHostResPoolId",hostResPoolId);
		}
		//Openstack平台platformType：4
		String platformType = servicePo.getPlatformType();
		if(platformType != null && !platformType.equals("")){
			if(platformType.equals("4")){
				map.put("tenantId",bmSrRrinfoPo.getTenantId());
			}
		}
		// 查询所有可提供分配的物理机
		//有应用系统和服务器角色的资源池查询
		if( !CommonUtil.isEmpty(duId)){
			list = computePolicyDAO.findHostResPoolsBybmSrRrinfoPo(map);
		}else{
			//无应用系统和服务器角色，传递参数为计算资源池id
			list = computePolicyDAO.findHostResPoolsBybmSrRrinfo(map);
		}
		if(list == null || list.size() ==0){
			throw new Exception("未查找到资源信息.");
		}
		int hostCount = list.size();
		List<PolicyInfoVo> resPoolVos = new ArrayList<PolicyInfoVo>();	//保存资源池对象
		/*
			以键值对形式保存资源池下的层级结构，键是各个层级的ID(poolid对应pool对象，cdpid对应cdp对象...)，
			形如：   {2:02672CAD19144CADBB742E8FF4A430C7=com.git.cloud.policy.model.vo.PolicyInfoVo@fb1493, 
					0:73261B4E629E495CA9F7064FD04A7FC1=com.git.cloud.policy.model.vo.PolicyInfoVo@8ede42, 
					1:D939900F4D03469F993C9FE21213772F=com.git.cloud.policy.model.vo.PolicyInfoVo@6bc081}
		*/
		HashMap<String, PolicyInfoVo> resPoolMap = new HashMap<String, PolicyInfoVo>();
		// 重复使用的资源池对象 // 资源池对象，这个是根节点，资源池下是CDP，CDP下是cluster，cluster下是host
		PolicyInfoVo rootPolicyInfoVo = null; 
		for (PolicyInfoVo currentPolicyInfo : list) { // 整合成资源树结构
			currentPolicyInfo.setCurrentType(CurrentTypeEnum.HOST); // 填充物理机层
			if ((rootPolicyInfoVo == null) || (!rootPolicyInfoVo.getPoolId().equals(currentPolicyInfo.getPoolId()))) {
				String id = (CurrentTypeEnum.POOL.ordinal()) + ":" + currentPolicyInfo.getPoolId();
				PolicyInfoVo newPoolVo = this.createPolicyInfoVo(CurrentTypeEnum.POOL, currentPolicyInfo);
				resPoolMap.put(id, newPoolVo);
				resPoolVos.add(resPoolMap.get(id));
				rootPolicyInfoVo = resPoolMap.get(id);
			}
			this.getParentPolicyInfoVo(resPoolMap, currentPolicyInfo);
			// resPoolMap.clear();
		}
		resPoolMap = null;
		for (PolicyInfoVo pool : resPoolVos) {// 遍历资源池,计算CPU相关信息
			pool.computeCPU();
			pool.calculateMem();
			pool.computeVmNum();
		}
		// 按照预先定义的资源匹配次序排序资源池
		Collections.sort(resPoolVos, new PolicyInfoVoComparator(true, sortRulesMap.get(CloudSortObjectEnum.POOL.toString()), "vmDistriType", "vmNum"));
		message.append("资源池数:" + resPoolVos.size() + ";物理机数:" + hostCount);
		return resPoolVos;
	}
	/**
	 * 
	 * @Title: createPolicyInfoVo
	 * @Description: 给资源池下的各个层级赋值
	 * @field: @param currentType
	 * @field: @param policyInfoVo
	 * @field: @return
	 * @return PolicyInfoVo
	 * @throws
	 */
	protected PolicyInfoVo createPolicyInfoVo(CurrentTypeEnum currentType, PolicyInfoVo policyInfoVo) {
		PolicyInfoVo policyInfo = new PolicyInfoVo();
		policyInfo.setCurrentType(currentType);

		switch (currentType) {
		case HOST:
			policyInfo.setHostId(policyInfoVo.getHostId());
		case CLUSTER:
			policyInfo.setClusterId(policyInfoVo.getClusterId());
		case CDP:
			policyInfo.setCdpId(policyInfoVo.getCdpId());
			policyInfo.setDatastoreType(policyInfoVo.getDatastoreType());
			policyInfo.setVmDistriType(policyInfoVo.getVmDistriType());
		case POOL:
			policyInfo.setPoolId(policyInfoVo.getPoolId());
		default:
			break;
		}
		return policyInfo;
	}
	/**
	 * 
	 * @Title: getParentPolicyInfoVo
	 * @Description: 给资源池的单层附上父子结构
	 * @field: @param map
	 * @field: @param policyInfoVo
	 * @field: @return
	 * @return PolicyInfoVo
	 * @throws
	 */
	protected PolicyInfoVo getParentPolicyInfoVo(HashMap<String, PolicyInfoVo> map, PolicyInfoVo policyInfoVo) {
		PolicyInfoVo policyInfo = null;
		String pid = null;
		switch (policyInfoVo.getCurrentType()) {
		case VM:
			pid = policyInfoVo.getHostId();
			break;
		case HOST:
			pid = policyInfoVo.getClusterId();
			break;
		case CLUSTER:
			pid = policyInfoVo.getCdpId();
			if (org.apache.commons.lang3.StringUtils.isBlank(pid)) {
				pid = policyInfoVo.getPoolId() + ":0";
			}
			break;
		case CDP:
			pid = policyInfoVo.getPoolId();
			break;
		default:
			return null;
		}
		String spid = (policyInfoVo.getCurrentType().ordinal() - 1) + ":" + pid;
		policyInfo = map.get(spid);
		if (policyInfo == null) {
			policyInfo = this.createPolicyInfoVo(CurrentTypeEnum.values()[policyInfoVo.getCurrentType().ordinal() - 1], policyInfoVo);
			map.put(spid, policyInfo);
			policyInfo.addChild(policyInfoVo);
			PolicyInfoVo info = policyInfo;
			this.getParentPolicyInfoVo(map, info);
		} else if ((policyInfo.getChilds() == null) || !policyInfo.getChilds().contains(policyInfoVo)) { // 可能有问题
			policyInfo.addChild(policyInfoVo);
		}
		return policyInfo;
	}
	
	/**
	 * @Description:distribHostForVm(这里用一句话描述这个方法的作用)
	 * @param respools
	 * @param bmSrRrinfoPo
	 * @param distribCount：本次客户申请需要分配的虚机数
	 * @param platformType
	 * @param haType
	 * @return
	 * @throws Exception 
	*/
	@SuppressWarnings("unchecked")
	private List<PolicyResultVo> distribHostForVm(List<PolicyInfoVo> respools, BmSrRrinfoPo bmSrRrinfoPo, int distribCount, String platformType,
			String haType,String message) throws Exception {
		List<PolicyResultVo> resultList = new ArrayList<PolicyResultVo>();
		int distribedCount = 0;//已分配虚机总套数
		
		for (PolicyInfoVo pool : respools) {	
			//如果当前资源池下所有的CDP都不能分配需要的虚机数分配数，则下个资源池继续分配
			if(distribedCount<distribCount){
				// 获取该资源池下的全部cdp，并按使用率排列
				//修改后：获取该资源池下的全部cdp，并先按虚机分配类型排序，然后按使用率排列，修改为然后按已分配虚机数排序
				List<PolicyInfoVo> cdpList = pool.getChilds("vmDistriType", true, "vmNum", sortRulesMap.get(CloudSortObjectEnum.CDP.toString()));
				for (PolicyInfoVo cdp : cdpList) {
					if(distribedCount<distribCount){
						String cdpMaxCpuStr = rmVmParamService.findParamValue(cdp.getPoolId(), cdp.getCdpId(), "MAX_CPU_UTILIZATION");
						if(cdpMaxCpuStr == null || "".equals(cdpMaxCpuStr.trim())){
							throw new Exception("没有配置CPU最大分配率参数");
						}

						String hostMaxVmStr = rmVmParamService.findParamValue(cdp.getPoolId(), cdp.getCdpId(), "MAX_VM_NUM");
						if(hostMaxVmStr == null || "".equals(hostMaxVmStr.trim())){
							throw new Exception("没有配置单机最大可分配虚机数参数");
						}
						
						String maxMemRate = rmVmParamService.findParamValue(cdp.getPoolId(), cdp.getCdpId(), "MAX_MEM_UTILIZATION");
						if(maxMemRate == null || "".equals(maxMemRate.trim())){
							throw new Exception("没有配置内存最大分配率参数");
						}

						double cdpMaxCPU = Double.parseDouble(cdpMaxCpuStr);
						int hostMaxVm = Integer.parseInt(hostMaxVmStr);
						double maxMemRateDlb = Double.parseDouble(maxMemRate);
						
						// 判断cdp增加待分配资源后利用率是否不超过规定利用率上线
						if(isCdpUsable(cdp, bmSrRrinfoPo, cdpMaxCPU, maxMemRateDlb)) {
							// 获取该CDP下的全部cluster，并按使用率排列
							List<PolicyInfoVo> clusterList = null; // cdp.getChilds(sortRulesMap.get(CloudSortObjectEnum.CLUSTER.toString()));
							clusterList = cdp.getChilds(true, "vmNum", sortRulesMap.get(CloudSortObjectEnum.CLUSTER.toString()));
							//x86资源
//							if(PlayFormTypeEnum.X86.getValue().equals(platformType)) {
							//按当前CDP下的所有cluster分配虚机
								if(HaTypeEnum.SINGLE.toString().equals(haType)) {
//									resultList = this.distribHostForHostVm(clusterList,bmSrRrinfoPo,resultList,distribCount,distribedCount,cdpMaxCPU,hostMaxVm);
									Map<String, Object> resultmap = this.distribHostForHostVm(clusterList,bmSrRrinfoPo,resultList,distribCount,
											distribedCount,cdpMaxCPU,hostMaxVm, maxMemRateDlb);
									resultList = (List<PolicyResultVo>) resultmap.get("resultList");
									distribedCount=Integer.parseInt(resultmap.get("distribedCount")+"");
								}else{
//									resultList = this.distribHostForClusterVm(clusterList,bmSrRrinfoPo,resultList ,distribCount,distribedCount,cdpMaxCPU,hostMaxVm);
									Map<String, Object> resultmap = this.distribForClusterVm(clusterList,bmSrRrinfoPo,resultList ,distribCount,
											distribedCount,cdpMaxCPU,hostMaxVm, maxMemRateDlb);
									resultList = (List<PolicyResultVo>) resultmap.get("resultList");
									distribedCount=Integer.parseInt(resultmap.get("distribedCount")+"");
								}
							//HP资源
//							}else if(PlayFormTypeEnum.HP.getValue().equals(platformType)) {
								
							//power资源
//							}else if(PlayFormTypeEnum.POWEP.getValue().equals(platformType)) {
//								
//							}
						}
					}else{
						break;
					}
					//循环遍历cdp层end
				}
			}else{
				break;
			}
			//循环遍历资源池层end
		}
		//如果所有资源池下的所有CDP下的所有cluster还能分配出去的虚机总数达不到客户申请的虚机数，那么现有的资源池满足不了客户申请需求
		if(distribedCount<distribCount){
			throw new Exception("现有资源无法满足新增虚拟机,"+message);
		}
		return resultList;
	}
	/**
	 * VMware计算资源分配
	 * @param rmHostList
	 * @param bmSrRrinfoPo
	 * @param distribCount
	 * @param platformType
	 * @param haType
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PolicyResultVo> distribHostForVmWare(List<PolicyInfoVo> rmHostList, BmSrRrinfoPo bmSrRrinfoPo, int distribCount) throws Exception {
		if(rmHostList == null || rmHostList.size()==0 ){
			throw new Exception("虚拟机分配算法参数rmHostList为空");
		}
		if(bmSrRrinfoPo == null){
			throw new Exception("虚拟机分配算法参数bmSrRrinfoPo为空");
		}
		//虚拟机分配时，定位资源的排序规则
		sortRulesMap = rmVmRulesService.findAllRmvmRulesPo();
		if (sortRulesMap == null || sortRulesMap.get(CloudSortObjectEnum.WEBLOGIC.toString()) == null) {
			throw new Exception("虚拟机分配算法参数bmSrRrinfoPo为空");
		}
		List<PolicyResultVo> resultList = new ArrayList<PolicyResultVo>();
		int distribedCount = 0;//已分配虚机总套数
		JSONObject json = JSONObject.parseObject(bmSrRrinfoPo.getParametersJson());
		log.info("json {}",new Object[]{json});		
		int cpu = json.getIntValue("cpu");
		//用户需求为内存为M，需要转换M
		int mem = json.getIntValue("mem");
//		mem = mem*1024;
		//用户选择数据为G转换成M
		int dataDisk = json.getIntValue("dataDisk");
		dataDisk = dataDisk*1024;
		//查询超分比
		String cdpMaxCpuStr = rmVmParamService.findParamValue(bmSrRrinfoPo.getRmHostResPoolId(), null, "MAX_CPU_UTILIZATION");
		if(cdpMaxCpuStr == null || "".equals(cdpMaxCpuStr.trim())){
			throw new Exception("没有配置CPU最大分配率参数");
		}
		String hostMaxVmStr = rmVmParamService.findParamValue(bmSrRrinfoPo.getRmHostResPoolId(), null, "MAX_VM_NUM");
		if(hostMaxVmStr == null || "".equals(hostMaxVmStr.trim())){
			throw new Exception("没有配置单机最大可分配虚机数参数");
		}
		
		String maxMemRate = rmVmParamService.findParamValue(bmSrRrinfoPo.getRmHostResPoolId(), null, "MAX_MEM_UTILIZATION");
		if(maxMemRate == null || "".equals(maxMemRate.trim())){
			throw new Exception("没有配置内存最大分配率参数");
		}
		double cdpMaxCPU = Double.parseDouble(cdpMaxCpuStr);
		int hostMaxVm = Integer.parseInt(hostMaxVmStr);
		double maxMemRateDlb = Double.parseDouble(maxMemRate);
		//查询物理机下面所有的datastore信息，并按照可用大小排序
		List<String> listHostIds = new ArrayList<String>();
		for(PolicyInfoVo policyInfoVoTemp :rmHostList){
			listHostIds.add(policyInfoVoTemp.getHostId());
		}
		log.info("cmHostDatastoreRefDAO {}",new Object[]{cmHostDatastoreRefDAO});		
		List<CmHostDatastorePo> cmHostDatastorePos = cmHostDatastoreRefDAO.findDatastoreIdByHosts(listHostIds);
		//按照物理机的形式
		Map<String,List<CmHostDatastorePo>> cmHostDatastorePoMap = new HashMap<String,List<CmHostDatastorePo>>();
		for(CmHostDatastorePo cmHostDatastorePo:cmHostDatastorePos){
			if(cmHostDatastorePoMap.containsKey(cmHostDatastorePo.getHostId())){
				cmHostDatastorePoMap.get(cmHostDatastorePo.getHostId()).add(cmHostDatastorePo);
			}else{
				List<CmHostDatastorePo> cmHostDatastorePoTmps = new ArrayList<CmHostDatastorePo>() ;
				cmHostDatastorePoTmps.add(cmHostDatastorePo);
				cmHostDatastorePoMap.put(cmHostDatastorePo.getHostId(), cmHostDatastorePoTmps);
			}
		}
		//对资源进行排序
		PolicyInfoVo cluster = new PolicyInfoVo();
		ArrayList<PolicyInfoVo> rmHostArrayList = new ArrayList<PolicyInfoVo>();
		rmHostArrayList.addAll(rmHostList);
		cluster.setChilds(rmHostArrayList);
		rmHostList = cluster.getChilds(true, "vmNum",sortRulesMap.get(CloudSortObjectEnum.WEBLOGIC.toString()));
		// 按当前cluster下的所有host分配虚机
		log.info("可分配的物理机数量{}",new Integer[]{rmHostList.size()});
		for (int i = 0; i < rmHostList.size(); i++) {
			if (distribedCount == distribCount) {
				log.info("已经完成" + distribCount + "台虚拟机的分配。");
				break;
			}
			PolicyInfoVo host = rmHostList.get(i);
			double maxCpu = (host.getCpu().doubleValue() * cdpMaxCPU);
			double usedCpu = (host.getUsedCpu().doubleValue());
			// 根据cpu规则，该主机可分配cpu核数(舍掉小数取整)
			int hostCpuDistribableCount = (int) Math.floor(maxCpu - usedCpu);
			// 根据cpu规则，该主机可分配虚机数(舍掉小数取整) 5
			int hostDistribableCountByCpu = (int) Math.floor(hostCpuDistribableCount / Double.parseDouble(cpu + ""));
			// 根据最大虚拟机数规则，该主机还可分配虚机数：最大虚机数-已分配的虚机数 3
			log.info("maxCpu：{} usedCpu:{},hostCpuDistribableCount:{} cpu:{} ", new String[] { String.valueOf(maxCpu),
					String.valueOf(usedCpu), String.valueOf(hostCpuDistribableCount), String.valueOf(cpu) });
			int vmNum = host.getVmNum() != null ? host.getVmNum().intValue() : 0;
			int hostDistribableCountByVmNum = hostMaxVm - vmNum;
			// 根据内存分配规则规则，还可分的虚拟机数
			int hostUsedMen = host.getUsedMem() != null ? host.getUsedMem().intValue() : 0;
			double totalAvaliableMem = host.getTotalMem() * maxMemRateDlb - hostUsedMen;
			log.info("hostUsedMen：{} maxMemRateDlb:{},totalAvaliableMem:{}M", new String[] {
					String.valueOf(hostUsedMen), String.valueOf(maxMemRateDlb), String.valueOf(totalAvaliableMem) });

			int canAddedVmByMem = (int) Math.floor(totalAvaliableMem / Double.parseDouble(mem + ""));
			int hostDistribableCount = hostDistribableCountByCpu > hostDistribableCountByVmNum
					? hostDistribableCountByVmNum : hostDistribableCountByCpu;
			
			log.info("hostDistribableCount：{} ", new int[] { hostDistribableCount });
			
			hostDistribableCount = hostDistribableCount > canAddedVmByMem ? canAddedVmByMem : hostDistribableCount;
			log.info(
					"资源分配 hostDistribableCountByCpu:{} vmNum:{} hostDistribableCountByVmNum:{}canAddedVmByMem:{} hostDistribableCount:{} hostDistribableCount:{} ",
					new Object[] { hostDistribableCountByCpu, vmNum, hostDistribableCountByVmNum, canAddedVmByMem,
							hostDistribableCount, hostDistribableCount });
			
//			maxCpu：72.0 usedCpu:83.0,cpu:-11 getTotalMem:1 >
//			 hostDistribableCountByCpu:-11 vmNum:0 hostDistribableCountByVmNum:30canAddedVmByMem:0 >
			log.info("资源分配判断 ", new boolean[] { hostDistribableCount < 0 });
			if(hostDistribableCount < 0 || hostDistribableCount == 0){
				log.info("CPU MEN 不够分 物理机资源不够分配 getHostId:{} getCpu:{} getTotalMem:{} ", new String[] { host.getHostId(),
						String.valueOf(host.getCpu()), String.valueOf(host.getTotalMem()) });
				rmHostList.remove(i);
				i--;
				continue;
			}
			//判断物理机面所有的datastore信息
			PolicyResultVo result = new PolicyResultVo();
			List<CmHostDatastorePo> cmHostDatastorePoForDiss =  cmHostDatastorePoMap.get(host.getHostId());
			//添加是否存在合适的datastore信息
			log.info("用户需求 dataDisk大小：{} ", new String[] { String.valueOf(dataDisk)});
			if (cmHostDatastorePoForDiss == null || cmHostDatastorePoForDiss.size() < 1) {
				log.error("getHostId:"+host.getHostId()+"物理机下面没有datastore信息");
				rmHostList.remove(i);
				i--;
				continue;
			}
			boolean existDataStore = false;
			for(CmHostDatastorePo cmHostDatastorePo:cmHostDatastorePoForDiss){
				if(cmHostDatastorePo.getFreeSize()>dataDisk){
					//更新列表中
					cmHostDatastorePo.setFreeSize(cmHostDatastorePo.getFreeSize()+dataDisk);
					result.setDataStoreId(cmHostDatastorePo.getDatastoreId());
					existDataStore = true;
					break;
				}
			}
			if(!existDataStore){
				log.info("existDataStore 不够分");
				rmHostList.remove(i);
				i--;
				continue;
			}
			result.setCdpId(host.getCdpId());
			result.setPoolId(host.getPoolId());
			result.setHostId(host.getHostId());
			result.setClusterId(host.getClusterId());
			resultList.add(result);
			distribedCount++;
			// 每次分配一个虚拟机后都修改物理机策略对象的已分配资源值，重新排序物理机
			host.setUsedCpu(host.getUsedCpu() + cpu);
			log.info("寻找下一台getUsedMem{},mem{},host.getUsedMem() + mem{} ",new Integer[]{host.getUsedMem(),mem,host.getUsedMem() + mem});
			host.setUsedMem(host.getUsedMem() + mem);
			host.setVmNum(vmNum + 1);
			// 每次完成一个虚拟机的分配都重置循环，重新排序，重新分配
			i = -1;
			rmHostList = cluster.getChilds(true, "vmNum",sortRulesMap.get(CloudSortObjectEnum.WEBLOGIC.toString()));
			log.info("寻找下一台 ");
		}
		//如果所有资源池下的所有CDP下的所有cluster还能分配出去的虚机总数达不到客户申请的虚机数，那么现有的资源池满足不了客户申请需求
		log.info("分配结果 distribedCount:{} distribCount:{} ", new Integer[] {distribedCount,distribCount});
		if(distribedCount<distribCount){
			throw new Exception("现有资源无法满足新增虚拟机");
		}
		return resultList;
	}
	/**
	 * 判断cdp增加待分配资源后利用率是否不超过规定利用率上线
	 * @param cdp
	 * @param srPo
	 */
	private boolean isCdpUsable(PolicyInfoVo cdp,BmSrRrinfoPo srPo,double cdpMaxCPU, double maxMemRateDlb){
		boolean cpuPass = false, memPass = false;
		JSONObject json = JSONObject.parseObject(srPo.getParametersJson());
		
		int usedCpu = cdp.getUsedCpu() + (json.getIntValue("cpu") * json.getIntValue("vmNum"));
		double maxCpu = cdpMaxCPU * cdp.getCpu(); 
		if ((cdp.getCpu() != null) && (cdp.getCpu() > 0) && ((usedCpu) <= (maxCpu))){
			cpuPass = true;
		}
		double totalAvaliableMem = cdp.getTotalMem() * maxMemRateDlb;
		double totalUsedMem = cdp.getUsedMem() + (json.getIntValue("mem") * json.getIntValue("vmNum"));
		if (totalAvaliableMem >= totalUsedMem) {
			memPass = true;
		}
		if (cpuPass && memPass) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @Title: distribForClusterVm
	 * @Description: 获取申请虚机类型为集群的资源
	 * @field: @param clusterList
	 * @field: @param bmSrRrinfoPo
	 * @field: @param resultList
	 * @field: @param distribCount
	 * @field: @param distribedCount
	 * @field: @param hostMaxCPU
	 * @field: @param hostMaxVm
	 * @field: @return
	 * @field: @throws Exception
	 * @return Map<String,Object>
	 * @throws
	 */
	private Map<String, Object> distribForClusterVm(List<PolicyInfoVo> clusterList, BmSrRrinfoPo bmSrRrinfoPo, List<PolicyResultVo> resultList, 
			int distribCount, int distribedCount, Double hostMaxCPU, int hostMaxVm, Double maxMemRateDlb) throws Exception {
		JSONObject json = JSONObject.parseObject(bmSrRrinfoPo.getParametersJson());
		int cpu = json.getIntValue("cpu");
		int mem = json.getIntValue("mem");
		
		List<PolicyInfoVo> clusterListBak = new ArrayList<PolicyInfoVo>();
		if(clusterList!=null){
			for (PolicyInfoVo cluster : clusterList) {
				if (distribedCount >= distribCount) {
					break;
				}
				List<PolicyInfoVo> hostList = cluster.getChilds(true, "vmNum", sortRulesMap.get(CloudSortObjectEnum.WEBLOGIC.toString()));// 主机按利用率,编号排序
				List<PolicyInfoVo> hostListBak = new ArrayList<PolicyInfoVo>();
				if (!CollectionUtil.hasContent(hostList)) {
					// 移除无法分配虚机的集群
					clusterListBak.add(cluster);
					continue;
				}
				for (PolicyInfoVo host : hostList) {
					// 该主机可分配cpu核数(舍掉小数取整)
					int hostCpuDistribableCount = (int) Math.floor((host.getCpu().doubleValue() * hostMaxCPU) - host.getUsedCpu().doubleValue());
					// 该主机可分配虚机数(舍掉小数取整)
					int hostDistribableCountByCpu = (int) Math.floor(hostCpuDistribableCount / Double.parseDouble(cpu+""));
					// 计算该主机可分配的内存数
					int totalAvaliableMem = (int) Math.floor((host.getTotalMem().doubleValue() * maxMemRateDlb) - host.getUsedMem().doubleValue());
					int canAddVmCountByMem = (int) Math.floor(totalAvaliableMem / Double.parseDouble(mem+""));
					
					int vmNum = host.getVmNum();
					if (hostDistribableCountByCpu > 0 && hostMaxVm - vmNum > 0 && canAddVmCountByMem > 0) {
						PolicyResultVo result = new PolicyResultVo();
						result.setCdpId(host.getCdpId());
						result.setPoolId(host.getPoolId());
						result.setHostId(host.getHostId());
						result.setClusterId(host.getClusterId());
						resultList.add(result);
						distribedCount++;
						host.setUsedCpu(host.getUsedCpu() + cpu);
						host.setUsedMem(host.getUsedMem() + mem);
						host.setVmNum(++vmNum);
						break;
					} else {
						// 移除无法分配虚机的物理机
						hostListBak.add(host);
					}
				}
				hostList.removeAll(hostListBak);
			}
			clusterList.removeAll(clusterListBak);
		}

		if (distribedCount < distribCount && clusterList != null && clusterList.size() > 0) {
			return distribForClusterVm(clusterList, bmSrRrinfoPo, resultList, distribCount, distribedCount, hostMaxCPU, hostMaxVm, maxMemRateDlb);
		} else if (distribedCount < distribCount && (clusterList == null || clusterList.size() < 1)) {
			resultList.clear();
			distribedCount = 0;
			Map<String, Object> resultmap = new HashMap<String, Object>();
			resultmap.put("resultList", resultList);
			resultmap.put("distribedCount", distribedCount);
			return resultmap;
		} else {
			Map<String, Object> resultmap = new HashMap<String, Object>();
			resultmap.put("resultList", resultList);
			resultmap.put("distribedCount", distribedCount);
			return resultmap;
		}
	}
	/**
	 * 
	 * @Description:distribHostForHostVm(获取申请虚机类型为单机的资源)
	 * 按当前CDP下的所有cluster分配虚机
	 * @param clusterList
	 * @param bmSrRrinfoPo
	 * @param resultList
	 * @param vmBaseNum
	 * @param distribCount
	 * @param distribedCount
	 * @param hostMaxCPU
	 * @param hostMaxVm
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> distribHostForHostVm(List<PolicyInfoVo> clusterList,BmSrRrinfoPo bmSrRrinfoPo,List<PolicyResultVo> resultList ,
				int distribCount, int distribedCount, Double hostMaxCPU, int hostMaxVm, Double maxMemRate) throws Exception {
		
		if (distribedCount == distribCount) {
			log.error("参数错误，已经完成" + distribCount + "台虚拟机的分配。");
			return null;
		} else if (distribedCount > distribCount) {
			log.error("参数错误，已经完成的分配数" + distribedCount + "大于需要分配的数量" + distribCount);
			return null;
		}
		JSONObject json = JSONObject.parseObject(bmSrRrinfoPo.getParametersJson());
		int cpu = json.getIntValue("cpu");
		int mem = json.getIntValue("mem");
		// 开始分配
		for (PolicyInfoVo cluster : clusterList) {
			if (distribedCount == distribCount) {
				log.info("已经完成" + distribCount + "台虚拟机的分配。");
				break;
			}
//			if (distribedCount < distribCount) {
			List<PolicyInfoVo> hostList = cluster.getChilds(true, "vmNum", sortRulesMap.get(CloudSortObjectEnum.WEBLOGIC.toString()));// 主机按利用率,编号排序
			if (hostList == null || hostList.size() <= 0) {
				log.info("集群内不存在物理机，集群id：" + cluster.getClusterId());
				break;
			}
			// 按当前cluster下的所有host分配虚机
			for (int i = 0; i < hostList.size(); i++) {
				if (distribedCount == distribCount) {
					log.info("已经完成" + distribCount + "台虚拟机的分配。");
					break;
				}
				PolicyInfoVo host = hostList.get(i);
				double maxCpu = (host.getCpu().doubleValue() * hostMaxCPU);
				double usedCpu = (host.getUsedCpu().doubleValue());
				// 根据cpu规则，该主机可分配cpu核数(舍掉小数取整)
				int hostCpuDistribableCount = (int) Math.floor(maxCpu - usedCpu);
				// 根据cpu规则，该主机可分配虚机数(舍掉小数取整) 5
				int hostDistribableCountByCpu = (int) Math.floor(hostCpuDistribableCount / Double.parseDouble(cpu+""));
				// 根据最大虚拟机数规则，该主机还可分配虚机数：最大虚机数-已分配的虚机数 3
				int hostDistribableCountByVmNum = hostMaxVm - host.getVmNum();
				// 根据内存分配规则规则，还可分的虚拟机数
				double totalAvaliableMem = host.getTotalMem() * maxMemRate.doubleValue() - host.getUsedMem();
				int canAddedVmByMem = (int) Math.floor(totalAvaliableMem / Double.parseDouble(mem+""));
				
				int hostDistribableCount = hostDistribableCountByCpu > hostDistribableCountByVmNum ? hostDistribableCountByVmNum : hostDistribableCountByCpu;
				hostDistribableCount = hostDistribableCount > canAddedVmByMem ? canAddedVmByMem : hostDistribableCount;
				// for (int i = hostDistribableCount; i > 0 && distribedCount < distribCount; i--) {
				if (hostDistribableCount > 0) {
					PolicyResultVo result = new PolicyResultVo();
					result.setCdpId(host.getCdpId());
					result.setPoolId(host.getPoolId());
					result.setHostId(host.getHostId());
					result.setClusterId(host.getClusterId());
					resultList.add(result);
					distribedCount++;
					// 每次分配一个虚拟机后都修改物理机策略对象的已分配资源值，重新排序物理机
					host.setUsedCpu(host.getUsedCpu() + cpu);
					host.setUsedMem(host.getUsedMem() + mem);
					host.setVmNum(host.getVmNum() + 1);
					// 每次完成一个虚拟机的分配都重置循环，重新排序，重新分配
					i = -1;
					hostList = cluster.getChilds(true, "vmNum", sortRulesMap.get(CloudSortObjectEnum.WEBLOGIC.toString()));
				}
			}
		}

		if (distribedCount < distribCount) { // 如果当前CDP下的所有cluster还能分配出去的虚机数总数达不到客户申请的虚机数，那么当前跳过当前CDP
			resultList.clear();
			distribedCount = 0;
			Map<String, Object> resultmap = new HashMap<String, Object>();
			resultmap.put("resultList", resultList);
			resultmap.put("distribedCount", distribedCount);
			return resultmap;
		} else {
			Map<String, Object> resultmap = new HashMap<String, Object>();
			resultmap.put("resultList", resultList);
			resultmap.put("distribedCount", distribedCount);
			return resultmap;
		}

	}

	/* (non-Javadoc)
	 * <p>Title:getHostResPoolInfo</p>
	 * <p>Description:</p>
	 * @param devId
	 * @param extCpu
	 * @return
	 * @see com.git.cloud.policy.service.IComputePolicyService#getHostResPoolInfo(java.lang.Long, int)
	 */
	@Override
	public boolean checkHostResPoolInfo(String devId, int extCpu, int Mem) throws RollbackableBizException {
		List<PolicyInfoVo> list = new ArrayList<PolicyInfoVo>();
		RequsetResInfoVo requsetResInfoVo = new RequsetResInfoVo();
		requsetResInfoVo.setHostId(devId);
		list = computePolicyDAO.findResByReqRes(requsetResInfoVo);
		if (CollectionUtil.hasContent(list) && list.size() == 1) {
			PolicyInfoVo infoVo = list.get(0);
			double cdpMaxCPU = Double.parseDouble(rmVmParamService.findParamValue(infoVo.getPoolId(), infoVo.getCdpId(), "MAX_CPU_UTILIZATION"));
			if (infoVo.getCpu() * cdpMaxCPU >= (infoVo.getUsedCpu() + extCpu)) {
				// 判断内存条件是否满足
				double maxMemRate = Double.parseDouble(rmVmParamService.findParamValue(infoVo.getPoolId(), infoVo.getCdpId(), "MAX_MEM_UTILIZATION"));
				// 总数 * 分配率 >= 已经分配数 + 新增加数
				if (infoVo.getTotalMem() * maxMemRate >= (infoVo.getUsedMem() + Mem)) {
					return true;
				}
			}
		}
		return false;
	}
	/* (non-Javadoc)
	 * <p>Title:getHostResPoolInfo</p>
	 * 获取可供迁移的资源
	 * @param requsetResInfoVo
	 * @param Cpu
	 * @param Mem
	 * @return
	 * @see com.git.cloud.policy.service.IComputePolicyService#getHostResPoolInfo(com.git.cloud.policy.model.vo.RequsetResInfoVo, int, int)
	 */
	@Override
	public List<PolicyResultVo> getHostResPoolInfo(	RequsetResInfoVo requsetResInfoVo, int cpu, int mem) throws RollbackableBizException {
		List<PolicyInfoVo> list = new ArrayList<PolicyInfoVo>();
		List<PolicyResultVo> resultVos = new ArrayList<PolicyResultVo>();
		list = computePolicyDAO.findResByReqRes(requsetResInfoVo);
		sortRulesMap = rmVmRulesService.findAllRmvmRulesPo();
		if(CollectionUtil.hasContent(list)){
			List<PolicyInfoVo> policyInfoVos = new ArrayList<PolicyInfoVo>();
			
			HashMap<String,PolicyInfoVo> poolMap=new HashMap<String,PolicyInfoVo>();
			PolicyInfoVo policyInfoVo = null;
			for(PolicyInfoVo policyInfo : list) {//整合成资源树结构
				policyInfo.setCurrentType(CurrentTypeEnum.HOST);//填充物理机层
				if((policyInfoVo==null)||(!policyInfoVo.getPoolId().equals(policyInfo.getPoolId()))) {
					String id=(CurrentTypeEnum.POOL.ordinal())+":"+policyInfo.getPoolId();
					PolicyInfoVo infoVo=this.createPolicyInfoVo(CurrentTypeEnum.POOL,policyInfo);
					poolMap.put(id,infoVo);
					policyInfoVos.add(poolMap.get(id));
					policyInfoVo=poolMap.get(id);
				}
				this.getParentPolicyInfoVo(poolMap,policyInfo);
			}
			poolMap.clear();
			poolMap=null;
//			for(PolicyInfoVo pool:policyInfoVos) {//遍历资源池,计算CPU相关信息
//				pool.computeCPU();
//			}
//			if(policyInfoVos!=null && policyInfoVos.size()==1){
//				List<PolicyInfoVo> poolList = policyInfoVos;
				if(policyInfoVos !=null && policyInfoVos.size()==1){
					List<PolicyInfoVo> cdpList = policyInfoVos.get(0).getChilds();
					if(cdpList != null && cdpList.size()==1){
						double cdpMaxCPU = Double.parseDouble(rmVmParamService.findParamValue(cdpList.get(0).getPoolId(), cdpList.get(0).getCdpId(), "MAX_CPU_UTILIZATION"));
						int hostMaxVm = Integer.parseInt(rmVmParamService.findParamValue(cdpList.get(0).getPoolId(), cdpList.get(0).getCdpId(), "MAX_VM_NUM"));
						for(PolicyInfoVo cluster : cdpList.get(0).getChilds(true, "vmNum", sortRulesMap.get(CloudSortObjectEnum.CLUSTER.toString()))){
							for(PolicyInfoVo host : cluster.getChilds(true, "vmNum", sortRulesMap.get(CloudSortObjectEnum.WEBLOGIC.toString()))){
								if((host.getUsedCpu()+cpu) <= (host.getCpu()*cdpMaxCPU) && (host.getVmNum()+1)<=hostMaxVm){
									PolicyResultVo result = new PolicyResultVo();
									result.setPoolId(host.getPoolId());
									result.setCdpId(host.getCdpId());
									result.setClusterId(host.getClusterId());
									result.setHostId(host.getHostId());
									resultVos.add(result);
								}
							}
						}
					}
				}
			}
//		}
//		if(CollectionUtil.hasContent(resultVos)){
//			return resultVos;
//		}
		return resultVos;
	}

	public ICmHostDatastoreRefDAO getCmHostDatastoreRefDAO() {
		return cmHostDatastoreRefDAO;
	}

	public void setCmHostDatastoreRefDAO(ICmHostDatastoreRefDAO cmHostDatastoreRefDAO) {
		this.cmHostDatastoreRefDAO = cmHostDatastoreRefDAO;
	}
	
	
}