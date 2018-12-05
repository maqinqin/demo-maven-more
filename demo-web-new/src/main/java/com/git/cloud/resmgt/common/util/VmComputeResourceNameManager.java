/**
 * Copyright (c) 2014, Git Co., Ltd. All rights reserved.
 *
 * 审核人：
 */
package com.git.cloud.resmgt.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
//import com.ccb.iomp.cloud.data.dao.VmDAO;
import com.git.cloud.appmgt.dao.IDeployunitDao;
import com.git.cloud.appmgt.model.DuSerTypeEnum;
import com.git.cloud.appmgt.model.po.DeployUnitPo;
import com.git.cloud.appmgt.model.vo.DeployUnitVo;
import com.git.cloud.cloudservice.model.HaTypeEnum;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.policy.model.vo.PolicyResultVo;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.impl.RmDatacenterDAO;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.compute.dao.IRmClusterDAO;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * @ClassName:VmComputeResourceNameManager
 * @Description:X86虚拟化平台命名规范管理类
 * @author chengbin
 * @date 2014-10-9 下午3:19:24
 *
 *
 */
public class VmComputeResourceNameManager {
	private static Logger logger = LoggerFactory.getLogger(VmComputeResourceNameManager.class);

	private static int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999,
			9999999, 99999999, 999999999, Integer.MAX_VALUE };

	private static ICmDeviceDAO deviceDao;
	private static IRmClusterDAO clusterDao;
	private static RmDatacenterDAO rmDatacenterDAO;
	private static IDeployunitDao appDuDao;


	/*
	 * private static VmDAO vmDao = (VmDAO) FrameworkContext
	 * .getApplicationContext().getBean("vmDAO");
	 */

	/**
	 * 获得虚拟名
	 * 
	 * @param duId
	 * @param nameNumber
	 * @param ls
	 * @param sp
	 * @return
	 * @throws Exception
	 */
	public synchronized static List<String> getVMName(String duId,
			int nameNumber, List<PolicyResultVo> ls, CloudServicePo sp)
			throws Exception {
		List<String> rs = Lists.newArrayList();
		if (duId != null) {
			String bank_name = null;// 行号
			//String env_name = "1";// 环境名
			String clusterSerial = "0";// 集群编号
			boolean isCluster = false;// 是集群
			DeployUnitPo appDu = (DeployUnitPo) appDuDao.findObjectByID("findAppDuById", duId);
			RmDatacenterPo dp = (RmDatacenterPo) rmDatacenterDAO.findObjectByID("findDatacenterById",appDu.getDatacenterId());
			
			DeployUnitVo appDuVo = appDuDao.getDeployUnitById(duId);
			
			bank_name = dp.getEname();
//			if (dataCenterName != null) {
//				if (dataCenterName.indexOf(CloudGlobalConstants.BJ_YQ_CNAME) > -1) {
//					bank_name = adminParameterServiceImpl
//							.findParameter(CloudGlobalConstants.BJ_YQ_HOSTNAME_IDEN);
//				} else if (dataCenterName
//						.indexOf(CloudGlobalConstants.WH_NH_CNAME) > -1) {
//					bank_name = adminParameterServiceImpl
//							.findParameter(CloudGlobalConstants.WH_NH_HOSTNAME_IDEN);
//				} else if (dataCenterName
//						.indexOf(CloudGlobalConstants.BJ_DXH_CNAME) > -1) {
//					bank_name = adminParameterServiceImpl
//							.findParameter(CloudGlobalConstants.BJ_DXH_HOSTNAME_IDEN);
//				}
//			}
			String function_name = appDu.getServiceTypeCode();// 服务器角色类型/功能用途
			
			String phyOrApp_name = appDu.getEname();//服务器角色英文名
			
			String app_name = appDuVo.getAppEname();

			if (function_name.equals(DuSerTypeEnum.DU_SEV_TYPE_DB.getValue())) {// 根据功能用途判断集群编号
				clusterSerial = "1";
			} else if (function_name.equals(DuSerTypeEnum.DU_SEV_TYPE_AP.getValue())
					|| function_name.equals(DuSerTypeEnum.DU_SEV_TYPE_WB.getValue())) {
				String haTypeCode = sp.getHaType();
				if (haTypeCode.equals(HaTypeEnum.CLUSTER.name())) {// 根据部署模式判断集群编号
					isCluster = true;
				} else if (haTypeCode.equals(HaTypeEnum.SINGLE.name())
						|| haTypeCode.equals(HaTypeEnum.HA.name())) {
					isCluster = false;
					clusterSerial = "0";

				} else {
					throw new Exception("高可用类型异常！");
				}
			} else {
				throw new Exception("服务器角色类型编码错误！");
			}

			Map<String, Integer> tmpMap = Maps.newHashMap();
			int next_max = 0;
			for (int i = 0; i < nameNumber; i++) {
				HashMap<String, String> params = Maps.newHashMap();
				Integer serNum = 0;
				Integer serNum_t = 0;
				String clusterId = null;
				isCluster = false;
				if (isCluster) {
					PolicyResultVo prsi = ls.get(i);
					clusterId = prsi.getClusterId();
					RmClusterPo clusterPo = (RmClusterPo) clusterDao.findObjectByID("findClusterById",clusterId);
					String clusterName = clusterPo.getEname();
					if (clusterName != null) {
//						clusterSerial = clusterName.substring(
//								clusterName.lastIndexOf("-") + 1,
//								clusterName.length());
						//String queryJQL = "select count(*) from DevicePo d, VmPo v ,DevicePo dv where d.deviceId = v.hostId and v.deviceId = dv.deviceId and d.suId = :suId and dv.duId = :duId";// and
																																															// :isActive
						params.put("clusterId", clusterId);
						params.put("duId", duId);
						// params.put("isActive",
						// CloudGlobalConstants.ACTIVE_STS_YES);
						
						
						int hasVmNum = deviceDao.getCountCmAllVM("getVMCountByClusterDu", params);
						serNum = hasVmNum + 1;// 当前数据库记录数+1
					}
					// 存储各集群最大数
					if (tmpMap.containsKey(clusterId)) {
						int tmpval = tmpMap.get(clusterId);
						serNum_t = serNum > tmpval ? serNum : tmpval;
						tmpMap.put(clusterId, serNum_t);
					} else {
						serNum_t = serNum;
						next_max = 0;
						tmpMap.put(clusterId, serNum_t);
					}
				} else {
					params.put("duId", duId);
					serNum = deviceDao.getCountCmAllVM("getVMCountByDuId",params) + 1;
					serNum_t = serNum + i;
				}
				if (serNum_t > 999) {
					throw new Exception("编号超出999.");
				}
				if (serNum_t < next_max) {
					serNum_t = next_max;
				}
				String rsNoSerNum = bank_name+ app_name
						+ phyOrApp_name + function_name + clusterSerial;// 不带序号的名称

				Integer uniqueSerNum = getUniqueSerNum(
						rsNoSerNum.toLowerCase(), serNum_t);
				if (uniqueSerNum > 999) {
					throw new Exception("编号超出999.");
				}
				next_max = uniqueSerNum + 1;
				if (isCluster) {
					tmpMap.put(clusterId, next_max);
				}
				String serialNum = getSerialNum(uniqueSerNum);
				String rs1 = rsNoSerNum + serialNum;
				rs.add(rs1.replaceAll("_", "").replaceAll("-", "")
						.toLowerCase());
			}
		}
		return rs;
	}

	private static Integer getUniqueSerNum(String nameOfNoSer, Integer serNum) {
		Integer uniqueNum = serNum;
		String SerialNum = getSerialNum(serNum);
		String fullName = nameOfNoSer + SerialNum;
		Map<String, String> params = Maps.newHashMap();
		// params.put("isActive", CloudGlobalConstants.ACTIVE_STS_YES);
		params.put("deviceName", fullName);
		int c = deviceDao.getCountByDeviceName("getCountByDeviceName",fullName);
		if (c > 0) {
			return getUniqueSerNum(nameOfNoSer, serNum + 1);
		}
		return uniqueNum;
	}

	private static String getSerialNum(Integer serNum_t) {
		int headZeroNum = 3 - sizeOfInt(serNum_t);
		String serialNum = String.valueOf(serNum_t);
		if (headZeroNum > 0) {
			for (int j = headZeroNum; j > 0; j--) {
				serialNum = "0" + serialNum;
			}
		}
		return serialNum;
	}

	private static int sizeOfInt(int x) {
		for (int i = 0;; i++) {
			if (x <= sizeTable[i]) {
				return i + 1;
			}
		}
	}
//
//	public IParameterService getParameterServiceImpl() {
//		return parameterServiceImpl;
//	}
//
//	public void setParameterServiceImpl(IParameterService parameterServiceImpl) {
//		this.parameterServiceImpl = parameterServiceImpl;
//	}

//	public static ICmDeviceDAO getDeviceDao() {
//		return deviceDao;
//	}
//
//	public static void setDeviceDao(ICmDeviceDAO deviceDao) {
//		VmComputeResourceNameManager.deviceDao = deviceDao;
//	}
//
//	public static IRmClusterDAO getClusterDao() {
//		return clusterDao;
//	}
//
//	public static void setClusterDao(IRmClusterDAO clusterDao) {
//		VmComputeResourceNameManager.clusterDao = clusterDao;
//	}
//
//	public static RmDatacenterDAO getDatacenterDAO() {
//		return datacenterDAO;
//	}
//
//	public static void setDatacenterDAO(RmDatacenterDAO datacenterDAO) {
//		VmComputeResourceNameManager.datacenterDAO = datacenterDAO;
//	}
//
//	public static IDeployunitDao getAppDuDao() {
//		return appDuDao;
//	}
//
//	public static void setAppDuDao(IDeployunitDao appDuDao) {
//		VmComputeResourceNameManager.appDuDao = appDuDao;
//	}


	public static void main(String[] args){
		//getVMName(String duId,int nameNumber, List<PolicyResultVo> ls, CloudServicePo sp)
		
//		String duId = "08F7784B93BC444E5F14132682269184";
//		int nameNumber = 1;
//		PolicyResultVo vo = new PolicyResultVo();
//		vo.setClusterId("F32DE448F21B43BBA46D8BD4954BEFE5");
//		List<PolicyResultVo> ls = Lists.newArrayList();
//		ls.add(vo);
//		CloudServicePo sp = new CloudServicePo();
//		sp.setHaType(HaTypeEnum.CLUSTER.name());
//		try {
//			List<String> nameList = getVMName(duId,nameNumber,ls,sp);
//			for(int i=0;i<nameNumber;i++){
//				System.out.println(nameList.get(i));
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error("异常exception",e);
//		}
		JSONObject jso = new JSONObject();
		System.out.println(jso.getBooleanValue("fiFlag"));
		//System.out.println(getNames(4));
	}

	@SuppressWarnings("unchecked")
	public synchronized static List<String> getResourceName(HashMap<String,Object> map) throws Exception{
		//服务器角色ID
		String duId = String.valueOf(map.get("duId"));
		//生成名称数量
		int nameNumber = Integer.valueOf(map.get("nameNumber").toString());
		//规则信息
		List<PolicyResultVo> ls = (List<PolicyResultVo>)map.get("ls");
		//云服务
		CloudServicePo sp = (CloudServicePo)map.get("sp");

		deviceDao = (ICmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
		rmDatacenterDAO = (RmDatacenterDAO)WebApplicationManager.getBean("rmDatacenterDAO");
		clusterDao = (IRmClusterDAO)WebApplicationManager.getBean("rmClusterDAO");
		appDuDao = (IDeployunitDao)WebApplicationManager.getBean("deployunitDaoImpl");
		
		List<String> rs = Lists.newArrayList();
		duId = "";
		if (duId != null && !duId.equals("") && !duId.equals("null")) {
			String bank_name = null;// 行号
			//String env_name = "1";// 环境名
			String clusterSerial = "0";// 集群编号
			boolean isCluster = false;// 是集群
			
			DeployUnitPo appDu = (DeployUnitPo) appDuDao.findObjectByID("findAppDuById", duId);
			RmDatacenterPo dp = (RmDatacenterPo) rmDatacenterDAO.findObjectByID("findDatacenterById",appDu.getDatacenterId());
			
			DeployUnitVo appDuVo = appDuDao.getDeployUnitById(duId);
			// 数据中心编码
			bank_name = dp.getDatacenterCode();
			String function_name = appDu.getServiceTypeCode();// 服务器角色类型/功能用途
			
			String phyOrApp_name = appDu.getEname();//服务器角色英文名
			
			String app_name = appDuVo.getAppEname();

			if (function_name.equals(DuSerTypeEnum.DU_SEV_TYPE_DB.getValue())) {// 根据功能用途判断集群编号
				clusterSerial = "1";
			} else if (function_name.equals(DuSerTypeEnum.DU_SEV_TYPE_AP.getValue())
					|| function_name.equals(DuSerTypeEnum.DU_SEV_TYPE_WB.getValue())) {
				String haTypeCode = sp.getHaType();
				if (haTypeCode.equals(HaTypeEnum.CLUSTER.name())) {// 根据部署模式判断集群编号
					isCluster = true;
				} else if (haTypeCode.equals(HaTypeEnum.SINGLE.name())
						|| haTypeCode.equals(HaTypeEnum.HA.name())) {
					isCluster = false;
					clusterSerial = "0";

				} else if (haTypeCode.equals(HaTypeEnum.RAC.name())) {
					isCluster = true;
					clusterSerial = "0";

				} else {
					throw new Exception("高可用类型异常！");
				}
			} else {
				throw new Exception("服务器角色类型编码错误！");
			}

			Map<String, Integer> tmpMap = Maps.newHashMap();
			int next_max = 0;
			for (int i = 0; i < nameNumber; i++) {
				HashMap<String, String> params = Maps.newHashMap();
				Integer serNum = 0;
				Integer serNum_t = 0;
				String clusterId = null;
				isCluster = false;
				if (isCluster) {
					PolicyResultVo prsi = ls.get(i);
					clusterId = prsi.getClusterId();
					RmClusterPo clusterPo = (RmClusterPo) clusterDao.findObjectByID("findClusterById",clusterId);
					String clusterName = clusterPo.getEname();
					if (clusterName != null) {
//						clusterSerial = clusterName.substring(
//								clusterName.lastIndexOf("-") + 1,
//								clusterName.length());
						//String queryJQL = "select count(*) from DevicePo d, VmPo v ,DevicePo dv where d.deviceId = v.hostId and v.deviceId = dv.deviceId and d.suId = :suId and dv.duId = :duId";// and
																																															// :isActive
						params.put("clusterId", clusterId);
						params.put("duId", duId);
						// params.put("isActive",
						// CloudGlobalConstants.ACTIVE_STS_YES);
						
						
						int hasVmNum = deviceDao.getCountCmAllVM("getVMCountByClusterDu", params);
						serNum = hasVmNum + 1;// 当前数据库记录数+1
					}
					// 存储各集群最大数
					if (tmpMap.containsKey(clusterId)) {
						int tmpval = tmpMap.get(clusterId);
						serNum_t = serNum > tmpval ? serNum : tmpval;
						tmpMap.put(clusterId, serNum_t);
					} else {
						serNum_t = serNum;
						next_max = 0;
						tmpMap.put(clusterId, serNum_t);
					}
				} else {
					params.put("duId", duId);
					// params.put("isActive",
					// CloudGlobalConstants.ACTIVE_STS_YES);
					serNum = deviceDao.getCountCmAllVM("getVMCountByDuId",params) + 1;
					serNum_t = serNum + i;
				}
				if (serNum_t > 999) {
					throw new Exception("编号超出999.");
				}
				if (serNum_t < next_max) {
					serNum_t = next_max;
				}
				String rsNoSerNum = bank_name+ app_name
						+ phyOrApp_name + function_name + clusterSerial;// 不带序号的名称

//				System.out.println("****************************************************************");
//				System.out.println("bank_name:"+bank_name);
//				System.out.println("app_name:"+app_name);
//				System.out.println("phyOrApp_name:"+phyOrApp_name);
//				System.out.println("function_name:"+function_name);
//				System.out.println("clusterSerial:"+clusterSerial);
//				System.out.println("rsNoSerNum:"+rsNoSerNum);
//				System.out.println("****************************************************************");
				Integer uniqueSerNum = getUniqueSerNum(
						rsNoSerNum.toLowerCase(), serNum_t);
				if (uniqueSerNum > 999) {
					throw new Exception("编号超出999.");
				}
				next_max = uniqueSerNum + 1;
				if (isCluster) {
					tmpMap.put(clusterId, next_max);
				}
				String serialNum = getSerialNum(uniqueSerNum);
				String rs1 = rsNoSerNum + serialNum;
				rs.add(rs1.replaceAll("_", "").replaceAll("-", "")
						.toLowerCase());
			}
		}else{
			//应用系统，服务器角色为空的情况,虚拟机名称生成规则如下
			String bank_name = null;// 行号
			String clusterSerial = "0";// 集群编号
			boolean isCluster = false;// 是集群
			
			String datacenterId = String.valueOf(map.get("dcId"));
			RmDatacenterPo dp = (RmDatacenterPo) rmDatacenterDAO.findObjectByID("findDatacenterById",datacenterId);
			
			// 数据中心编码
			bank_name = dp.getDatacenterCode();
			//服务类型编码，直接在map中获取
			String function_name = String.valueOf(map.get("serviceTypeCode"));// 服务器角色类型/功能用途

			if (function_name.equals(DuSerTypeEnum.DU_SEV_TYPE_DB.getValue())) {// 根据功能用途判断集群编号
				clusterSerial = "1";
			} else if (function_name.equals(DuSerTypeEnum.DU_SEV_TYPE_AP.getValue())
					|| function_name.equals(DuSerTypeEnum.DU_SEV_TYPE_WB.getValue())) {
				String haTypeCode = sp.getHaType();
				if (haTypeCode.equals(HaTypeEnum.CLUSTER.name())) {// 根据部署模式判断集群编号
					isCluster = true;
				} else if (haTypeCode.equals(HaTypeEnum.SINGLE.name())
						|| haTypeCode.equals(HaTypeEnum.HA.name())) {
					isCluster = false;
					clusterSerial = "0";
				} else if (haTypeCode.equals(HaTypeEnum.RAC.name())) {
					isCluster = true;
					clusterSerial = "0";
				} else {
					throw new Exception("高可用类型异常！");
				}
			} else {
				//无应用系统、服务器角色时使用
				clusterSerial = "2";
				function_name = "";
			}

			Map<String, Integer> tmpMap = Maps.newHashMap();
			int next_max = 0;
			for (int i = 0; i < nameNumber; i++) {
				HashMap<String, String> params = Maps.newHashMap();
				Integer serNum = 0;
				Integer serNum_t = 0;
				String clusterId = null;
				isCluster = true;
				String clusterName = null;
				if (isCluster) {
					if(ls != null){
						PolicyResultVo prsi = ls.get(i);
						clusterId = prsi.getClusterId();
						RmClusterPo clusterPo = (RmClusterPo) clusterDao.findObjectByID("findClusterById",clusterId);
						clusterName = clusterPo.getEname();
						if (clusterName != null) {
							params.put("clusterId", clusterId);
							int hasVmNum = deviceDao.getCountCmAllVM("getVMCountByCluster", params);
							serNum = hasVmNum + 1;// 当前数据库记录数+1
						}
						// 存储各集群最大数
						if (tmpMap.containsKey(clusterId)) {
							int tmpval = tmpMap.get(clusterId);
							serNum_t = serNum > tmpval ? serNum : tmpval;
							tmpMap.put(clusterId, serNum_t);
						} else {
							serNum_t = serNum;
							next_max = 0;
							tmpMap.put(clusterId, serNum_t);
						}
					}
				} else {
					params.put("duId", duId);
					serNum = deviceDao.getCountCmAllVM("getVMCountByCluster",params) + 1;
					serNum_t = serNum + i;
				}
				if (serNum_t > 999) {
					throw new Exception("编号超出999.");
				}
				if (serNum_t < next_max) {
					serNum_t = next_max;
				}
				String rsNoSerNum = bank_name
						 + clusterName + clusterSerial;// 不带序号的名称

				Integer uniqueSerNum = getUniqueSerNum(
						rsNoSerNum.toLowerCase(), serNum_t);
				if (uniqueSerNum > 999) {
					throw new Exception("编号超出999.");
				}
				next_max = uniqueSerNum + 1;
				if (isCluster) {
					tmpMap.put(clusterId, next_max);
				}
				String serialNum = getSerialNum(uniqueSerNum);
				String rs1 = rsNoSerNum + serialNum;
				rs.add(rs1.replaceAll("_", "").replaceAll("-", "")
						.toLowerCase());
			}
		}
		return rs;
	}
	
	public static List<String> getNames(int n){
		List<String> list = new ArrayList<>();
		for(int i = 0;i < n; i++){
			//Date d = new Date();
			double random = Math.random();
//			long l = System.currentTimeMillis();
			String time = random+"";
			time = time.substring(time.length()-5, time.length());
			String name = "FI_"+time;
			list.add(name);
		}
		
		return list;
	}
}
