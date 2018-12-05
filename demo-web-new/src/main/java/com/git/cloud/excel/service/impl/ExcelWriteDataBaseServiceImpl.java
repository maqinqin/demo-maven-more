package com.git.cloud.excel.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.appmgt.service.IDeployunitService;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.excel.model.po.ExcelInfoPo;
import com.git.cloud.excel.model.vo.DataStoreVo;
import com.git.cloud.excel.model.vo.HostVo;
import com.git.cloud.excel.model.vo.VmVo;
import com.git.cloud.excel.service.IExcelWriteDataBaseService;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.request.tools.SrDateUtil;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.service.IRmDatacenterService;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
import com.git.cloud.resmgt.network.model.po.RmNwIpAddressPo;
import com.git.support.util.PwdUtil;

public class ExcelWriteDataBaseServiceImpl implements
		IExcelWriteDataBaseService {
	private ICommonDAO iCommonDAO;
	private IRmDatacenterService rmDataCenterService;
	private IDeployunitService deployunitServiceImpl;
	private ICmPasswordDAO cmPasswordDAO;
	


	public ICmPasswordDAO getCmPasswordDAO() {
		return cmPasswordDAO;
	}


	public void setCmPasswordDAO(ICmPasswordDAO cmPasswordDAO) {
		this.cmPasswordDAO = cmPasswordDAO;
	}


	public void setDeployunitServiceImpl(IDeployunitService deployunitServiceImpl) {
		this.deployunitServiceImpl = deployunitServiceImpl;
	}


	public void setRmDataCenterService(IRmDatacenterService rmDataCenterService) {
		this.rmDataCenterService = rmDataCenterService;
	}



	public void setiCommonDAO(ICommonDAO iCommonDAO) {
		this.iCommonDAO = iCommonDAO;
	}


	@Override
	public String saveDataCenterByCode(List<HostVo> hostVoList,List<VmVo> vmList,List<DataStoreVo> dsList,String fileName) throws Exception {
		boolean resultflag = true;
		String result = "";
		if(hostVoList.size() > 0 ){
			for (int i = 0; i < hostVoList.size(); i++) {
				HostVo vo = hostVoList.get(i);
				List<RmDatacenterPo> datacenterPoList = iCommonDAO.findListByParam("selectDataCenterByCode", vo.getDataCenterCode());
				if(datacenterPoList.size() > 0){
					
					resultflag = true;
					List<RmClusterPo> clusterPoList = iCommonDAO.findListByParam("selectClusterByEname", vo.getClusterCode());
					if(clusterPoList.size() > 0){
						resultflag = true;
					}else{
						result = "物理机中的集群编码:"+vo.getClusterCode()+"不存在";
						resultflag = false;
						return result;
					}
					
				}else{
					resultflag = false;
					result = "物理机中的数据中心编码:"+vo.getDataCenterCode()+"不存在";
					return result;
					
				}
			}
		}
		
		if(vmList.size() > 0){
			for(int i =0;i<vmList.size();i++){
				VmVo vo = vmList.get(i);
				List<RmClusterPo> clusterPoList = iCommonDAO.findListByParam("selectClusterByEname", vo.getClusterCode());
				if(clusterPoList.size() > 0 ){
					resultflag = true;
					
				}
			}
		}
		
		if(resultflag){
			Map hostIdMap = new HashMap();
			for(int i=0;i<hostVoList.size();i++){
				List<RmClusterPo> clusterPoList = iCommonDAO.findListByParam("selectClusterByEname", hostVoList.get(i).getClusterCode());
				RmClusterPo clu = clusterPoList.get(0);
				HostVo hostVo = hostVoList.get(i);
				CmDevicePo cmDevicePo = new CmDevicePo();
				cmDevicePo.setId(UUIDGenerator.getUUID());
				cmDevicePo.setDeviceName(hostVo.getHostName());
				cmDevicePo.setSn(hostVo.getHostSn());
				cmDevicePo.setResPoolId(clu.getResPoolId());
				cmDevicePo.setDeviceModelId(hostVo.getDeviceModelId());
				cmDevicePo.setSeatId(hostVo.getSeatId());
				hostIdMap.put(cmDevicePo.getDeviceName(), cmDevicePo.getId());
				//向CM_LOCAL_DISK表中添加数据
				for(int j=0;j<dsList.size();j++){
					DataStoreVo dataStoreVo = dsList.get(j);
					if(dataStoreVo.getHostName().equals(hostVo.getHostIp())){
						dataStoreVo.setId(UUIDGenerator.getUUID());
						dataStoreVo.setDeviceId(cmDevicePo.getId());
						hostVo.setDefaultDatastoreId(dataStoreVo.getId());
						iCommonDAO.save("insertLocalDisk", dataStoreVo);
					}
				}
				//向CM_DEVICE表中插入数据
				iCommonDAO.save("insertCmDevicePo", cmDevicePo);
				
				//向CM_HOST表中插入数据
				hostVo.setId(cmDevicePo.getId());
				hostVo.setClusterId(clu.getId());
				hostVo.setDefaultDatastoreType("LOCAL_DISK");
				iCommonDAO.save("insertCmHostPo", hostVo);
				
				//向CM_PASSWORD表中填写用户名、密码等信息
				CmPasswordPo cmPasswordPo = new CmPasswordPo(UUIDGenerator.getUUID());
				cmPasswordPo.setResourceId(cmDevicePo.getId());
				cmPasswordPo.setUserName(hostVo.getHostUser());
				cmPasswordPo.setPassword(PwdUtil.encryption(hostVo.getHostPwd()));
				cmPasswordPo.setLastModifyTime(SrDateUtil.getSrFortime(new Date()));
				cmPasswordDAO.insertCmPassword(cmPasswordPo);
				
				//占用网络资源池
				String[] ips = hostVo.getHostIp().split(",");
				List<RmNwIpAddressPo> rmNwIpList = null; //(此处代码需要进行改造) iCommonDAO.findListByParam("selectRmNwIpAddressByIp", ips[0]);
				if(rmNwIpList.size() > 0){
					RmNwIpAddressPo addressPo = rmNwIpList.get(0);
					addressPo.setDeviceId(cmDevicePo.getId());
					addressPo.setAllocedStatusCode("A2DV");
					addressPo.setNwRuleListId(hostVo.getNwRuleListId());
					iCommonDAO.update("updateIpAddress", addressPo);
					// 更新资源池可用ip(此处代码需要进行改造)
					//RmNwResPoolPo resPo = null;//nwResPoolDAO.findResPool(addressPo.getNwResPoolId());
					//resPo.setIpAvailCnt(resPo.getIpAvailCnt() - 1);
					//nwResPoolDAO.updateAvailCnt(resPo);
				}else{
					result = "没有可分配的网络资源池";
					return result;
				}
				
			}
			
			/*for(int i=0;i<dsList.size();i++){
				DataStoreVo dataStoreVo = dsList.get(i);
				dataStoreVo.setId(UUIDGenerator.getUUID());
				iCommonDAO.save("insertCmDataStorePo", dataStoreVo);
			}*/
		
			
			/*if(vmList.size() > 0){
				for(int i=0;i<vmList.size();i++){
					VmVo vmVo = vmList.get(i);
//					iCommonDAO.save("insertCmVmPo", vmVo);
					
					CmDevicePo cmDevicePo = new CmDevicePo();
					cmDevicePo.setId(UUIDGenerator.getUUID());
					cmDevicePo.setDeviceName(vmVo.getVmName());
					iCommonDAO.save("insertCmDevicePo", cmDevicePo);
					
					CmVmPo cmVmPo = new CmVmPo();
					cmVmPo.setId(cmDevicePo.getId());
					cmVmPo.setCpu(Integer.parseInt(vmVo.getVmCpu()));
					cmVmPo.setHostId(vmVo.getId());
					cmVmPo.setMem(Integer.parseInt(vmVo.getVmMem()));
					cmVmPo.setDuId(vmVo.getDuId());
					iCommonDAO.save("insertCmVmPo", cmVmPo);
					
					String datacenterCode = vmVo.getDataCenterCode();
					DeployUnitVo deployUnitVo = deployunitServiceImpl.getDeployUnitById(vmVo.getDuId());
					RmDatacenterPo rmDatacenterPo = rmDataCenterService.selectDCenamefortrim(datacenterCode);
					AppStatVo appStatVo =new AppStatVo();
					appStatVo.setDataCenterID(rmDatacenterPo.getId());
					appStatVo.setCpu(Integer.parseInt(vmVo.getVmCpu()));
					appStatVo.setDisk(0);
					appStatVo.setMem(Integer.parseInt(vmVo.getVmMem()));
					appStatVo.setServiceID("0");
					appStatVo.setDuID(vmVo.getDuId());
					appStatVo.setSrStatusCode("REQUEST_CLOSED");
					appStatVo.setSrTypeMark(SrTypeMarkEnum.VIRTUAL_NEW.getValue());
					appStatVo.setDiviceID(UUIDGenerator.getUUID());
					appStatVo.setAppID(deployUnitVo.getAppId());
					appMagServiceImpl.addAppStat(appStatVo);
					
					String[] ips = vmVo.getVmIp().split(",");
					List<RmNwIpAddressPo> rmNwIpList = iCommonDAO.findListByParam("selectRmNwIpAddressByIp", ips[0]);
					if(rmNwIpList.size() > 0){
						RmNwIpAddressPo addressPo = rmNwIpList.get(0);
						addressPo.setDeviceId((String) hostIdMap.get(vmVo.getHostName()));
						iCommonDAO.update("updateIpAddress", addressPo);
					}
				}
			}*/
			
			ExcelInfoPo excelInfoPo = iCommonDAO.findObjectByID("selectExcelInfoByName", fileName);
			if(excelInfoPo != null){
				excelInfoPo.setIsInput("Y");
				iCommonDAO.update("updateExcelInfoByName", excelInfoPo);
				result = "写入成功";
				return result;
			}
		}else{
			
		}
		
		return result;
	}


	@Override
	public String selecthostIdByName(String name) throws RollbackableBizException {
		HostVo v = iCommonDAO.findObjectByID("selecthostIdByName", name);
		return v.getId();
	}

}
