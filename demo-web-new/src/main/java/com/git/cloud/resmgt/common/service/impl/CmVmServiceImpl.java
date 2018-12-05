package com.git.cloud.resmgt.common.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.appmgt.dao.IDeployunitDao;
import com.git.cloud.appmgt.model.po.DeployUnitPo;
import com.git.cloud.appmgt.model.vo.AppStatVo;
import com.git.cloud.appmgt.service.IAppMagService;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.vo.CmSnapshotVo;
import com.git.cloud.resmgt.common.model.vo.CmVmVo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.common.service.ICmVmService;

public class CmVmServiceImpl implements ICmVmService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	ICmVmDAO cmVmDAO;
	private ICmDeviceService iCmDeviceService;
	
	private IAppMagService appMagServiceImpl;
	private IDeployunitDao deployunitDaoImpl;
	
	public IDeployunitDao getDeployunitDaoImpl() {
		return deployunitDaoImpl;
	}


	public void setDeployunitDaoImpl(IDeployunitDao deployunitDaoImpl) {
		this.deployunitDaoImpl = deployunitDaoImpl;
	}


	public IAppMagService getAppMagServiceImpl() {
		return appMagServiceImpl;
	}


	public void setAppMagServiceImpl(IAppMagService appMagServiceImpl) {
		this.appMagServiceImpl = appMagServiceImpl;
	}


	public ICmDeviceService getiCmDeviceService() {
		return iCmDeviceService;
	}


	public void setiCmDeviceService(ICmDeviceService iCmDeviceService) {
		this.iCmDeviceService = iCmDeviceService;
	}


	
	public ICmVmDAO getCmVmDAO() {
		return cmVmDAO;
	}


	public void setCmVmDAO(ICmVmDAO cmVmDAO) {
		this.cmVmDAO = cmVmDAO;
	}


	@Override
	public String updateVmDuId(CmVmVo cmvm) {
		String data = "";
		try {
			CmDeviceVMShowBo CmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(cmvm.getId());
			//写入App_stat表,将原有的数据置为无效
			//DeployUnitVo dep = deployunitServiceImpl.getDeployUnitById(cmvm.getDuId());
			AppStatVo appStatVoDel = new AppStatVo();
			appStatVoDel.setDuID(CmDeviceVMShowBo.getDuID());
			appStatVoDel.setDataCenterID(CmDeviceVMShowBo.getDatacenterID());
			appStatVoDel.setAppID(CmDeviceVMShowBo.getAppID());
			appStatVoDel.setSrStatusCode("REQUEST_CLOSED");
			appStatVoDel.setSrTypeMark("VR");
			appStatVoDel.setDiviceID(cmvm.getId());
			appStatVoDel.setServiceID(CmDeviceVMShowBo.getServiceId());
			appStatVoDel.setCpu(Integer.parseInt("0"));
			appStatVoDel.setMem(Integer.parseInt("0"));
			appStatVoDel.setDisk(0);
			appMagServiceImpl.addAppStat(appStatVoDel);
			
			//云服务ID在服务器角色表中清空
			DeployUnitPo deployDel = new DeployUnitPo();
			deployDel.setDuId(CmDeviceVMShowBo.getDuID());
			deployDel.setServiceId("");
			deployunitDaoImpl.updateDeployUnitServiceId(deployDel);
			
			cmVmDAO.updateVmDuId(cmvm);
			
			//写入App_stat表中插入数据
			AppStatVo appStatVoNew = new AppStatVo();
			appStatVoNew.setDuID(cmvm.getDuId());
			appStatVoNew.setDataCenterID(CmDeviceVMShowBo.getDatacenterID());
			appStatVoNew.setAppID(CmDeviceVMShowBo.getAppID());
			appStatVoNew.setSrStatusCode("REQUEST_CLOSED");
			appStatVoNew.setSrTypeMark("VS");
			appStatVoNew.setDiviceID(cmvm.getId());
			appStatVoNew.setServiceID(CmDeviceVMShowBo.getServiceId());
			appStatVoNew.setCpu(Integer.parseInt(CmDeviceVMShowBo.getCpu()));
			appStatVoNew.setMem(Integer.parseInt(CmDeviceVMShowBo.getMem()));
			appStatVoNew.setDisk(CmDeviceVMShowBo.getDisk());
			appMagServiceImpl.addAppStat(appStatVoNew);
			
			//云服务ID写入服务器角色表中
			 DeployUnitPo deploy = new DeployUnitPo();
			deploy.setDuId(cmvm.getDuId());
			deploy.setServiceId(CmDeviceVMShowBo.getServiceId());
			deployunitDaoImpl.updateDeployUnitServiceId(deploy);
			data = "SUCCESS";
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("异常exception",e);
			data = "ERROR";
		}
		return data;
	}


	@Override
	public List<CmSnapshotVo> getCmSnapshotVoList(String vmId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<CmVmPo> findCmVmByDuId(String duId) throws RollbackableBizException {
		return cmVmDAO.findCmVmByDuId(duId);
	}

}
