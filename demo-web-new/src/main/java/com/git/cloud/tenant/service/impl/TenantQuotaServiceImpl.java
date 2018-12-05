package com.git.cloud.tenant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.enums.RmPlatForm;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.QuotaCodeEnum;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.resmgt.common.dao.impl.RmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.network.model.po.CloudProjectPo;
import com.git.cloud.tenant.dao.ITenantAuthoDao;
import com.git.cloud.tenant.dao.ITenantQuotaDao;
import com.git.cloud.tenant.model.po.QuotaConfigPo;
import com.git.cloud.tenant.model.po.QuotaPo;
import com.git.cloud.tenant.model.po.TenantPo;
import com.git.cloud.tenant.model.vo.AllQuotaCountVo;
import com.git.cloud.tenant.model.vo.QuotaVo;
import com.git.cloud.tenant.service.ITenantQuotaService;

@Service
public class TenantQuotaServiceImpl extends CommonDAOImpl implements ITenantQuotaService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ITenantQuotaDao tenantQuotaDao;
	@Autowired
	private RmVmManageServerDAO rmVmManageServerDAO;
	@Override
	public Map<String, List> getQuotaConfigInfo(String tenantId) throws RollbackableBizException {
		List<String> typeCodeList = tenantQuotaDao.selectPlaTypeCode();
		Map<String, List> map = new HashMap<String, List>();
		if(typeCodeList!=null && typeCodeList.size()>0){
			for (String typeCode : typeCodeList) {
				List<QuotaConfigPo> list = tenantQuotaDao.getQuotaConfigInfoByPlaTypeCode(typeCode);
				map.put(typeCode, list);
			}
		}
		List<CloudProjectPo> listO = new ArrayList<>();
		List<CloudProjectPo> listPV = new ArrayList<>();
		List<CloudProjectPo> list = tenantQuotaDao.getProjectsByTenantId(tenantId);
		for (CloudProjectPo projectVpcPo : list) {
			RmVmManageServerPo serverInfo = rmVmManageServerDAO.getvmManageServerInfo(projectVpcPo.getVmMsId());
			if(serverInfo == null){
				continue;
			}
			if("5".equals(serverInfo.getPlatformType())){
				listPV.add(projectVpcPo);
			}else if("4".equals(serverInfo.getPlatformType())){
				listO.add(projectVpcPo);
			}
		}
		map.put("projects", list);
		map.put("projectsO", listO);
		map.put("projectsPV", listPV);
		return map;
	}

	private static final String OPENSTACK = "O";

	@Override
	public List<QuotaPo> selectQuotaByTenantId(String tenantId) throws RollbackableBizException {
		// 返回值
		List<QuotaPo> list = tenantQuotaDao.selectQuotaByTenantId(tenantId);
		if(list.size() == 0) {
			list = new ArrayList<>();
			// 无限配额的情况
			// openstack的配额列表
			List<QuotaConfigPo> configPos = tenantQuotaDao.getQuotaConfigInfoByPlaTypeCode(OPENSTACK);
			// 查询openstack的project列表
			List<CloudProjectPo> projectVpcPos = tenantQuotaDao.getProjectsByTenantId(tenantId);
			for (CloudProjectPo projectVpcPo : projectVpcPos) {
				// 查询虚机管理服务器
				RmVmManageServerPo serverInfo = rmVmManageServerDAO.getvmManageServerInfo(projectVpcPo.getVmMsId());
				if(serverInfo == null){
					continue;
				}
				if(OPENSTACK.equals(serverInfo.getPlatformCode())){
					for(QuotaConfigPo configPo : configPos) {
						QuotaPo quotaPo = new QuotaPo();
						quotaPo.setTenantId(tenantId);
						quotaPo.setQuotaConfigId(configPo.getId());
						quotaPo.setProjectId(projectVpcPo.getProjectId());
						quotaPo.setDataCnterId(projectVpcPo.getDatacenterId());
						quotaPo.setCode(configPo.getCode());
						quotaPo.setUsedValue(getOpenstackUsedValue(tenantId, projectVpcPo.getProjectId(), configPo.getCode()));
						list.add(quotaPo);
					}
				}
			}
		}
		else {
			for (QuotaPo q : list) {
				if(OPENSTACK.equals(q.getPlatformCode())) {
					// 获取OpenStack已使用的值
					q.setUsedValue(getOpenstackUsedValue(tenantId, q.getProjectId(), q.getCode()));
				}
				else {
					// 其他平台暂时不查询
					//q.setUsedValue("0");
					q.setUsedValue(getVMwareUsedValue(tenantId,q.getCode()));
				}
			}
		}
		return list;
	}

	@Override
	public void addQuota(String tenantId,List<QuotaPo> list) throws Exception {
		String id ="";
		if(list!=null &&list.size()>0){
			long cpuRequest = 0;
			long memRequest = 0;
			for (QuotaPo quotaPo : list) {
				id = UUID.randomUUID().toString().replaceAll("-", "");
				quotaPo.setId(id);
				// 校验值是否大于已经使用的值
				AllQuotaCountVo allQuotaCountVo = tenantQuotaDao.countOpenstackUsedQuota(quotaPo.getTenantId(), quotaPo.getProjectId());
				switch (quotaPo.getCode()) {
					case "vm":
						if(allQuotaCountVo.getVmNumUsed() != null) {
							if(Long.parseLong(quotaPo.getValue()) < allQuotaCountVo.getVmNumUsed()) {
								throw new RuntimeException("虚拟机配额不能小于已经使用的值！");
							}
						}
						break;
					case "cpu" :
						if(allQuotaCountVo.getCpuUsed() != null) {
							if(Long.parseLong(quotaPo.getValue()) < allQuotaCountVo.getCpuUsed()) {
								throw new RuntimeException("CPU配额不能小于已经使用的值！");
							}
						}
						cpuRequest += Long.parseLong(quotaPo.getValue());
						break;
					case "mem" :
						if(allQuotaCountVo.getMemUsed() != null) {
							if(Long.parseLong(quotaPo.getValue()) < allQuotaCountVo.getMemUsed()) {
								throw new RuntimeException("内存配额不能小于已经使用的值！");
							}
						}
						memRequest += Long.parseLong(quotaPo.getValue());
						break;
					case "storage" :
						if(allQuotaCountVo.getStorageUsed() != null) {
							if(Long.parseLong(quotaPo.getValue()) < allQuotaCountVo.getStorageUsed()) {
								throw new RuntimeException("存储配额不能小于已经使用的值！");
							}
						}
						break;
					case "ip" :
						if(allQuotaCountVo.getFloatingIpNumUsed() != null) {
							if(Long.parseLong(quotaPo.getValue()) < allQuotaCountVo.getFloatingIpNumUsed()) {
								throw new RuntimeException("弹性IP配额不能小于已经使用的值！");
							}
						}
						break;
					case "vlb" :
						if(allQuotaCountVo.getVlbNumUsed() != null) {
							if(Long.parseLong(quotaPo.getValue()) < allQuotaCountVo.getVlbNumUsed()) {
								throw new RuntimeException("负载均衡配额不能小于已经使用的值！");
							}
						}
						break;
					case "network" :
						if(allQuotaCountVo.getNetworkNumUsed() != null) {
							if(Long.parseLong(quotaPo.getValue()) < allQuotaCountVo.getNetworkNumUsed()) {
								throw new RuntimeException("网络配额不能小于已经使用的值！");
							}
						}
						break;
					case "group" :
						if(allQuotaCountVo.getSecurityGroupNumUsed() != null) {
							if(Long.parseLong(quotaPo.getValue()) < allQuotaCountVo.getSecurityGroupNumUsed()) {
								throw new RuntimeException("安全组配额不能小于已经使用的值！");
							}
						}
						break;
					default:
						break;
				}
			}
			// 查询资源池的资源总数
			AllQuotaCountVo resPoolCount = tenantQuotaDao.selectResPoolResourceByTenantId(tenantId);
			if (cpuRequest > resPoolCount.getCpu()) {
				throw new RuntimeException("租户CPU配额总数" + cpuRequest + "核大于当前租户关联的资源池的CPU资源总量" + resPoolCount.getCpu() + "核！");
			}
			if (memRequest > resPoolCount.getMem()) {
				throw new RuntimeException("租户内存配额总数" + memRequest / 1024 + "GB大于当前租户关联的资源池的内存资源总量" + resPoolCount.getMem() / 1024 + "GB！");
			}
		}
		tenantQuotaDao.deleteQuota(tenantId);
		tenantQuotaDao.addQuota(list);
		tenantQuotaDao.updateTenantQuotaFlag(tenantId);
	}

	@Override
	public void updateQuota(String tenantId,List<QuotaPo> list) throws Exception {
		if(list!=null && list.size()>0){
			tenantQuotaDao.deleteQuota(tenantId);
			addQuota(tenantId,list);
		}
		
	}	
	
	@Override
	public AllQuotaCountVo countPowerUsedQuota(String tenantId) throws RollbackableBizException {
		return tenantQuotaDao.countPowerUsedQuota(tenantId);
	}

	@Override
	public AllQuotaCountVo countOpenstackUsedQuota(String tenantId, String projectId) throws RollbackableBizException {
		return tenantQuotaDao.countOpenstackUsedQuota(tenantId, projectId);
	}

	@Override
	public AllQuotaCountVo countVmwareUsedQuota(String tenantId) throws RollbackableBizException {
		return tenantQuotaDao.countVmwareUsedQuota(tenantId);
	}
	
	@Override
	public List<QuotaVo> getQuotaList(String tenantId) throws RollbackableBizException {
		return tenantQuotaDao.getQuotaList(tenantId);
	}
	
	@Override
	public boolean validateQuota(String tenantId, String platformTypeCode,int reqValue,String code) throws RollbackableBizException {
		Map<String,String> paramMap = new HashMap<String,String>();
		boolean validateQuotaFlag = false;
		StringBuilder errorInfo = new StringBuilder();
		//String errorInfo = "";
		//验证传递参数不能为空
		if(CommonUtil.isEmpty(tenantId)) {
			logger.error("tenantId:{},获取tenantId为空！",tenantId);
			throw new RollbackableBizException("tenantId:"+tenantId+",获取tenantId为空！");
		}
		if(CommonUtil.isEmpty(platformTypeCode)) {
			logger.error("platformTypeCode:{},获取platformTypeCode为空！",platformTypeCode);
			throw new RollbackableBizException("platformTypeCode:"+platformTypeCode+",获取platformTypeCode为空！");
		}
		if(CommonUtil.isEmpty(reqValue)) {
			logger.error("reqValue:{},获取reqValue为空！",reqValue);
			throw new RollbackableBizException("reqValue:"+reqValue+",获取reqValue为空！");
		}
		if(CommonUtil.isEmpty(platformTypeCode)) {
			logger.error("code:{},获取code为空！",code);
			throw new RollbackableBizException("code:"+code+",获取code为空！");
		}
		//组装map，获取具体code的配额列表
		paramMap.put("tenantId", tenantId);
		paramMap.put("platformTypeCode", platformTypeCode);
		paramMap.put("code", code);
		List<QuotaVo> quotaVoList = tenantQuotaDao.getQuotaListByMap(paramMap);
		if(quotaVoList == null || quotaVoList.size() == 0) {
			logger.error("tenantId:{},获取配额为空,编码为code:{}",tenantId,code);
			throw new RollbackableBizException("tenantId:"+tenantId+",获取cpu配额为空！");
		}
		for(QuotaVo vo : quotaVoList) {
			AllQuotaCountVo usedResVo = new AllQuotaCountVo();
			int usedRes = 0;
			//磁盘需要根据类型，调不同的接口进行查询
			if(code.equals("storage")) {
				if(RmPlatForm.OPENSTACKX86.getValue().equals(platformTypeCode)||RmPlatForm.POWERVC.getValue().equals(platformTypeCode)) {
					usedResVo = tenantQuotaDao.countOpenstackUsedQuota(tenantId, vo.getProjectId());
				}else if(RmPlatForm.POWER.getValue().equals(platformTypeCode)){
					usedResVo = tenantQuotaDao.countPowerUsedQuota(tenantId);
				}else if(RmPlatForm.X86.getValue().equals(platformTypeCode)) {
					usedResVo = tenantQuotaDao.countVmwareUsedQuota(tenantId);
				}
			}else {
				//查询openstack类型的已用配额时，map中需要加上projectId
				if(RmPlatForm.OPENSTACKX86.getValue().equals(platformTypeCode)||RmPlatForm.POWERVC.getValue().equals(platformTypeCode)) {
					paramMap.put("projectId", vo.getProjectId());
				}
				usedResVo = tenantQuotaDao.getUsedResByMap(paramMap);
			}
			if(!CommonUtil.isEmpty(usedResVo)) {
				if(QuotaCodeEnum.CPU.getValue().equals(code)) {
					usedRes = usedResVo.getCpuUsed();
				}else if(QuotaCodeEnum.MEM.getValue().equals(code)) {
					usedRes = usedResVo.getMemUsed();
				}else if(QuotaCodeEnum.VM.getValue().equals(code)) {
					usedRes = usedResVo.getVmNumUsed();
				}else if(QuotaCodeEnum.STORAGE.getValue().equals(code)) {
					usedRes = usedResVo.getStorageUsed();
				}
				int freeRes = Integer.parseInt(vo.getVal()) - usedRes;
				int remainRes = freeRes - reqValue;
				if(remainRes >= 0) {
					validateQuotaFlag = true;
					logger.info("平台编码platformTypeCode:"+platformTypeCode+",配额编码code:"+code+",空闲资源为："+freeRes+",请求资源为："+reqValue);
					break;
				}else {
					String str = "";
					if(RmPlatForm.OPENSTACKX86.getValue().equals(platformTypeCode)) {
						str = vo.getProjectName()+"的"+code+"配额不足,空闲："+freeRes+vo.getUnit()+",请求："+reqValue +vo.getUnit()+";";
					}else {
						str = code+"配额不足,空闲："+freeRes+vo.getUnit()+",请求："+reqValue +vo.getUnit()+";";
					}
					errorInfo.append(str);
					logger.error(str);
				}
			}else {
				logger.error("获取已用资源对象为空projectId:"+vo.getProjectId()+"平台类型："+platformTypeCode);
				throw new RollbackableBizException("获取已用资源对象为空projectId:"+vo.getProjectId()+"平台类型："+platformTypeCode);
			}
		}
		if(!validateQuotaFlag) {
			throw new RollbackableBizException(errorInfo+"");
		}
		return validateQuotaFlag;
	}

	/**
	 * 查询openstack配额已使用的值
	 * @param tenantId
	 * @param projectId
	 * @param code
	 * @return
	 * @throws RollbackableBizException
	 */
	private String getOpenstackUsedValue(String tenantId, String projectId, String code) throws RollbackableBizException {
		AllQuotaCountVo allQuotaCountVo = tenantQuotaDao.countOpenstackUsedQuota(tenantId, projectId);
		String usedValue = null;
		switch (code) {
			case "vm":
				if(allQuotaCountVo.getVmNumUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = allQuotaCountVo.getVmNumUsed().toString();
				}
				break;
			case "cpu" :
				if(allQuotaCountVo.getCpuUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = allQuotaCountVo.getCpuUsed().toString();
				}
				break;
			case "mem" :
				if(allQuotaCountVo.getMemUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = String.valueOf((int)allQuotaCountVo.getMemUsed());
				}
				break;
			case "storage" :
				if(allQuotaCountVo.getStorageUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = allQuotaCountVo.getStorageUsed().toString();
				}
				break;
			case "ip" :
				if(allQuotaCountVo.getFloatingIpNumUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = allQuotaCountVo.getFloatingIpNumUsed().toString();
				}
				break;
			case "vlb" :
				if(allQuotaCountVo.getVlbNumUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = allQuotaCountVo.getVlbNumUsed().toString();
				}
				break;
			case "network" :
				if(allQuotaCountVo.getNetworkNumUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = allQuotaCountVo.getNetworkNumUsed().toString();
				}
				break;
			case "group" :
				if(allQuotaCountVo.getSecurityGroupNumUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = allQuotaCountVo.getSecurityGroupNumUsed().toString();
				}
				break;
			default:
				break;
		}
		return usedValue;
	}
	
	/**
	 * 查询openstack配额已使用的值
	 * @param tenantId
	 * @param projectId
	 * @param code
	 * @return
	 * @throws RollbackableBizException
	 */
	private String getVMwareUsedValue(String tenantId, String code) throws RollbackableBizException {
		AllQuotaCountVo allQuotaCountVo =tenantQuotaDao.countVmwareUsedQuota(tenantId);
		String usedValue = null;
		switch (code) {
			case "vm":
				if(allQuotaCountVo.getVmNumUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = allQuotaCountVo.getVmNumUsed().toString();
				}
				break;
			case "cpu" :
				if(allQuotaCountVo.getCpuUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = allQuotaCountVo.getCpuUsed().toString();
				}
				break;
			case "mem" :
				if(allQuotaCountVo.getMemUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = allQuotaCountVo.getMemUsed().toString();
				}
				break;
			case "storage" :
				if(allQuotaCountVo.getStorageUsed() == null) {
					usedValue = "0";
				}
				else {
					usedValue = allQuotaCountVo.getStorageUsed().toString();
				}
				break;
			default:
				break;
		}
		return usedValue;
	}

	@Override
	public String getResPoolByTenantId(String tenantId) throws RollbackableBizException {
			Map map = new HashMap<>();
			List<String>  list = tenantQuotaDao.selectResPoolByTenantId(tenantId);
			if(list.size()>0) {
				return "success";
			}else {
				return "fail";
			}
	}
}
