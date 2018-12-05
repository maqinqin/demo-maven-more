package com.git.cloud.resmgt.common.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.excel.model.vo.DataStoreVo;
import com.git.cloud.excel.model.vo.HostVo;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmLocalDiskDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.dao.IRmResPoolDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastoreRefPo;
import com.git.cloud.resmgt.common.model.po.CmLocalDiskPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmResPoolPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.CmVmVo;
import com.git.cloud.resmgt.common.model.vo.RmResPoolVo;
import com.git.cloud.resmgt.common.service.IScanVcService;
import com.git.cloud.resmgt.compute.dao.IRmClusterDAO;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.emory.mathcs.backport.java.util.Collections;

public class ScanVcServiceImpl implements IScanVcService {
	private static Logger logger = LoggerFactory.getLogger(ScanVcServiceImpl.class);
	private IRmResPoolDAO rmResPoolDAO;
	private IRmClusterDAO rmClusterDAO; 
	private ICmHostDAO cmHostDAO;
	private ICmVmDAO cmVmDAO;
	private ICommonDAO commonDAO;
	private ICmDeviceDAO cmDeviceDAO;
	private ICmLocalDiskDAO cmLocalDiskDAO;
	private ResAdptInvokerFactory resInvokerFactory;
	private IRmVmManageServerDAO rmVmManageServerDAO;
	
	private List<CmVmVo> needSaveVmList = Lists.newArrayList();
	private List<CmVmVo> needDeleteVmList = Lists.newArrayList();
	private List<DataStoreVo> needSaveSanList = Lists.newArrayList();
	/**
	 * 读取vc信息，启动扫描vc线程和db读取线程
	 * @throws RollbackableBizException 
	 * @throws Exception
	 */
	public void saveOrUpdateOrDelSyncData() throws RollbackableBizException {
		logger.info("===========vc信息与数据库信息同步:开始=============");
		long startTime = System.currentTimeMillis();
		List<RmVmManageServerPo> rmServerList = rmVmManageServerDAO.getvmManageServerList();
		saveOrUpdateOrDelAfterThread(rmServerList);
		long endTime = System.currentTimeMillis();
		logger.info("vc信息与数据库信息同步总耗时:{}ms", endTime - startTime);
		logger.info("===========vc信息与数据库信息同步:结束=============");
	}
	
	/**
	 * 启动扫描vc线程和db读取线程 ，比对结果集
	 * @param vmServerList
	 * @throws RollbackableBizException 
	 */
	public void saveOrUpdateOrDelAfterThread(List<RmVmManageServerPo> vmServerList) throws RollbackableBizException {
		if (vmServerList != null) {
			// 扫描vc线程池
			ExecutorService scanThreadPool = Executors.newFixedThreadPool(vmServerList.size());
			// 读取db线程池
			ExecutorService dbThreadPool = Executors.newFixedThreadPool(vmServerList.size());

			List<Future<Map<String, List<RmDatacenterPo>>>> scanTaskList = Lists.newArrayList();
			List<Future<Map<String, RmDatacenterPo>>> readTaskList = Lists.newArrayList();
			for (RmVmManageServerPo vmServerPo : vmServerList) {
				Future<Map<String, List<RmDatacenterPo>>> scanTask = scanThreadPool.submit(new ScanVc(vmServerPo));
				scanTaskList.add(scanTask);

				Future<Map<String, RmDatacenterPo>> readTask = dbThreadPool
						.submit(new ReadDB(vmServerPo, rmResPoolDAO, rmClusterDAO, cmHostDAO, cmVmDAO,cmLocalDiskDAO));
				readTaskList.add(readTask);
			}
			// 扫描vc结果集：vmManagerServerID为key
			List<Map<String, List<RmDatacenterPo>>> scanResultList = Lists.newArrayList();
			for (Future<Map<String, List<RmDatacenterPo>>> scanTask : scanTaskList) {
				try {
					Map<String, List<RmDatacenterPo>> vmManagerAndDataCenterByScan = scanTask.get();
					scanResultList.add(vmManagerAndDataCenterByScan);
				} catch (InterruptedException e) {
					logger.error("扫描vc线程异常中断", e);
					return;
				} catch (ExecutionException e) {
					logger.error("扫描vc线程池异常", e);
					return;
				}
			}
			// 读取db结果集：vmManagerServerID为key
			List<Map<String, RmDatacenterPo>> readResultList = Lists.newArrayList();
			for (Future<Map<String, RmDatacenterPo>> readTask : readTaskList) {
				try {
					Map<String, RmDatacenterPo> vmManagerAndDataCenterByRead = readTask.get();
					readResultList.add(vmManagerAndDataCenterByRead);
				} catch (InterruptedException e) {
					logger.error("读取db线程异常中断", e);
					return;
				} catch (ExecutionException e) {
					logger.error("读取db线程池异常", e);
					return;
				}
			}
			scanThreadPool.shutdown();
			dbThreadPool.shutdown();
			// 比对结果集
			saveOrUpdateOrDelAfterCompareData(scanResultList, readResultList);
		}
	}
	
	/**
	 * 比对结果集，vmManagerServerID为key找到对应的结果集,调用数据比对操作入口
	 * @param scanResultList
	 * @param readResultList
	 * @throws RollbackableBizException 
	 */
	public void saveOrUpdateOrDelAfterCompareData(List<Map<String, List<RmDatacenterPo>>> scanResultList,
			List<Map<String, RmDatacenterPo>> readResultList) throws RollbackableBizException {
		if (scanResultList != null) {
			for (Map<String, List<RmDatacenterPo>> scanResult : scanResultList) {
				Iterator<String> scanVmManagerKeys = scanResult.keySet().iterator();
				while (scanVmManagerKeys.hasNext()) {
					String vmManagerkeyByScan = scanVmManagerKeys.next();
					CmDevicePo sanDevice = null;
					try {
						sanDevice = cmDeviceDAO.findCmDeviceById(vmManagerkeyByScan);
					} catch (RollbackableBizException e1) {
						logger.error("获取san设备异常",e1);
						throw e1;
					}
					if(sanDevice == null){
						sanDevice = new CmDevicePo();
						sanDevice.setId(vmManagerkeyByScan);
						sanDevice.setDeviceName(vmManagerkeyByScan);
						try {
							cmDeviceDAO.save("saveSanDeviceByPo", sanDevice);
							commonDAO.save("saveSanStorage", sanDevice);
						} catch (RollbackableBizException e) {
							logger.error("插入san设备异常",e);
							throw e;
						}
						
					}
					if (readResultList != null && readResultList.size() != 0) {
						for (Map<String, RmDatacenterPo> readResult : readResultList) {
							Iterator<String> readVmManagerKeys = readResult.keySet().iterator();
							while (readVmManagerKeys.hasNext()) {
								String vmManagerkeyByRead = readVmManagerKeys.next();
								// 找到vmManager对应数据
								if (vmManagerkeyByScan.equals(vmManagerkeyByRead)) {
									List<RmDatacenterPo> vcDataCenterList = scanResult.get(vmManagerkeyByScan);
									RmDatacenterPo dbDataCenter = readResult.get(vmManagerkeyByRead);
									updateOrSaveVcData(vmManagerkeyByScan,vcDataCenterList, dbDataCenter);
									saveAndDeleteAfterCompareVm(needSaveVmList,needDeleteVmList);
									needSaveVmList = Lists.newArrayList();
									needDeleteVmList = Lists.newArrayList();
									needSaveSanList = Lists.newArrayList();
								}
							}
						}
					}
				}
			}
		}
	}
	/**
	 * 找到对应的数据中心后进入资源池对象比对入口
	 * @param vcDataCenterList
	 * @param dbDataCenter
	 * @throws RollbackableBizException 
	 */
	public void updateOrSaveVcData(String managerId,List<RmDatacenterPo> vcDataCenterList, RmDatacenterPo dbDataCenter) throws RollbackableBizException {
		if (vcDataCenterList != null && dbDataCenter != null) {
			RmDatacenterPo vcDataCenter = null;
			for (RmDatacenterPo data : vcDataCenterList) {
				if (data.getDatacenterCode().equals(dbDataCenter.getDatacenterCode())) {
					vcDataCenter = data;
					vcDataCenter.setId(dbDataCenter.getId());
					break;
				}
			}
			List<RmResPoolVo> dbPoolList = dbDataCenter.getPoolList();
			// 资源池比对
			if (vcDataCenter != null){
				List<RmResPoolVo> vcPoolList = vcDataCenter.getPoolList();
				updateOrSaveVcDataForResPool(managerId,vcDataCenter.getId(), vcPoolList, dbPoolList);
			}
		}
	}
	/**
	 * 找到对应的资源池后进入集群对象比对入口
	 * @param vcPoolList
	 * @param dbPoolList
	 * @throws RollbackableBizException 
	 */
	public void updateOrSaveVcDataForResPool(String managerId,String dataCenterId, List<RmResPoolVo> vcPoolList,
			List<RmResPoolVo> dbPoolList) throws RollbackableBizException {
		if (vcPoolList != null) {
			for (RmResPoolVo vcPoolPo : vcPoolList) {
				boolean isExistPoolFlag = false;
				if (dbPoolList != null) {
					for (RmResPoolVo dbPoolPo : dbPoolList) {
						//资源池只有数据中心为资源池名称，如存在，则不需要对资源池进行任何操作，直接进入集群比对入口
						if (vcPoolPo.getPoolName().equals(dbPoolPo.getPoolName())) {
							isExistPoolFlag = true;
							updateOrSaveVcDataForCluster(managerId,dataCenterId,dbPoolPo.getId(), vcPoolPo.getClusterList(),
									dbPoolPo.getClusterList());
							break;
						}
					}
				}
				//不存在执行新增后，进入集群比对入口
				if(!isExistPoolFlag){
					vcPoolPo.setId(UUIDGenerator.getUUID());
					vcPoolPo.setDatacenterId(dataCenterId);
					try {
						rmResPoolDAO.saveRmResPoolVo(vcPoolPo);
						logger.debug("===========插入资源池成功=============");
					} catch (RollbackableBizException e) {
						logger.error("===========插入资源池失败=============",e);
						throw e;
					}
					updateOrSaveVcDataForCluster(managerId,dataCenterId,vcPoolPo.getId(),vcPoolPo.getClusterList(),null);
				}
			}
		}
	}
	
	/**
	 * 比对集群，没有则新增，有则更新，反有则删除的数据操作后进入物理机比对入口
	 * @param resPoolId
	 * @param vcClusterList
	 * @param dbClusterList
	 * @throws RollbackableBizException 
	 */
	public void updateOrSaveVcDataForCluster(String managerId,String dataCenterId,String resPoolId,List<RmClusterPo> vcClusterList,List<RmClusterPo> dbClusterList) throws RollbackableBizException{
		if(vcClusterList != null){
			List<Integer> isExistClusterIdices = Lists.newArrayList();
			for(RmClusterPo vcClusterPo : vcClusterList){
				boolean isExistClusterFlag = false;
				if(dbClusterList != null){
					for(int i=0;i<dbClusterList.size();i++){
						RmClusterPo dbClusterPo = dbClusterList.get(i);
						//集群只有集群名称信息，如存在，则不需要对集群进行任何操作，直接进入物理机比对入口
						if(vcClusterPo.getClusterName().equals(dbClusterPo.getClusterName())){
							isExistClusterFlag = true;
							isExistClusterIdices.add(i);
							//比对物理机
							updateOrSaveVcDataForHost(managerId,dataCenterId,resPoolId,dbClusterPo.getId(),vcClusterPo.getHostList(),dbClusterPo.getHostList());
							break;
						}
					}
				}
				//不存在执行新增后，进入物理比对入口
				if(!isExistClusterFlag){
					vcClusterPo.setId(UUIDGenerator.getUUID());
					vcClusterPo.setResPoolId(resPoolId);
					vcClusterPo.setDatacenterId(dataCenterId);
					vcClusterPo.setManageServer(managerId);
					try {
						rmClusterDAO.saveRmClusterPo(vcClusterPo);
						logger.debug("===========插入集群成功=============");
					} catch (RollbackableBizException e) {
						logger.error("===========插入集群失败=============",e);
						throw e;
					}
					
					updateOrSaveVcDataForHost(managerId,dataCenterId,resPoolId,vcClusterPo.getId(),vcClusterPo.getHostList(),null);
				}
			}
			
			//批量删除已不存在的集群
			if(dbClusterList != null && isExistClusterIdices != null && isExistClusterIdices.size()>0){
				Collections.sort(isExistClusterIdices);
				for(int i=isExistClusterIdices.size()-1;i>=0;i--){
					dbClusterList.remove(Integer.parseInt(isExistClusterIdices.get(i)+""));
				}
				if(dbClusterList.size() > 0)
					deleteClusterByClusterList(dbClusterList);
			}
			
		}
	}
	
	public void deleteClusterByClusterList(List<RmClusterPo> dbClusterList) throws RollbackableBizException{
		if(dbClusterList != null){
			try {
				for(RmClusterPo cluster : dbClusterList){
					commonDAO.deleteForIsActive("deleteRmClusterPoById", cluster.getId());
					if(cluster.getHostList().size() > 0)
						deleteHostByHostList(cluster.getHostList());
				}
				logger.debug("===========批量删除集群成功=============");
			} catch (RollbackableBizException e) {
				logger.error("===========删除集群失败=============",e);
				throw e;
			}
		}
	}
	
	public void deleteHostByHostList(List<HostVo> deviceList) throws RollbackableBizException{
		if(deviceList != null){
			try {
				commonDAO.deleteForParam("deleteDeviceByParams", deviceList);
				logger.debug("===========批量删除物理机成功=============");
				for(HostVo hostVo:deviceList){
					List<CmVmVo> vmList = hostVo.getVmList();
					if(vmList != null){
						needDeleteVmList.addAll(vmList);
					}
					List<DataStoreVo> ldDataStoreList = hostVo.getLdDataStoreList();
					if(ldDataStoreList != null && ldDataStoreList.size() > 0 ){
						cmLocalDiskDAO.deleteLocalDiskList(ldDataStoreList);
						logger.debug("===========删除本地存储成功=============");
					}
					List<DataStoreVo> sanDataStoreList = hostVo.getSanDataStoreList();
					if(sanDataStoreList != null && sanDataStoreList.size() > 0 ){
						cmHostDAO.deleteSanDataStoreList(hostVo.getSanDataStoreList());
						logger.debug("===========删除SAN存储成功=============");
					}
					
				}
			} catch (RollbackableBizException e) {
				logger.error("===========删除物理机失败=============",e);
				throw e;
			}
		}
	}
	
	/**
	 * 比对物理机，没有则新增，有则更新，反有则删除的数据操作后进入虚拟机比对入口
	 * @param clusterId
	 * @param vcHostList
	 * @param dbHostList
	 * @throws RollbackableBizException 
	 */
	public void updateOrSaveVcDataForHost(String managerId,String dataCenterId,String resPoolId,String clusterId,List<HostVo> vcHostList,List<HostVo> dbHostList) throws RollbackableBizException{
		if(vcHostList != null){
			List<Integer> isExistHostIndics = Lists.newArrayList();
			for(HostVo vcHost : vcHostList){
				boolean isExistHostFlag = false;
				if(dbHostList != null){
					for(int i=0;i<dbHostList.size();i++){
						HostVo dbHost = dbHostList.get(i);
						//如存在，将数据的物理机对象的cpu等相关属性更新为扫描过来的数据，执行更新操作后进入虚拟机比对入口
						if(vcHost.getHostIp().equals(dbHost.getHostIp())){
							isExistHostFlag = true;
							String vcCpu = vcHost.getHostCpu();
							String vcMem = vcHost.getHostMem();
							String vcUsedMem = vcHost.getUsedMem();
							String vcUsedCpu = vcHost.getUsedCpu();
							
							String dbCpu = dbHost.getHostCpu();
							String dbMem = dbHost.getHostMem();
							String dbUsedMem = dbHost.getUsedMem();
							String dbUsedCpu = dbHost.getUsedCpu();
							if(!(vcCpu.equals(dbCpu) && vcMem.equals(dbMem) &&  
									vcUsedMem.equals(dbUsedMem) && vcUsedCpu.equals(dbUsedCpu))){
								dbHost.setHostCpu(vcHost.getHostCpu());
								dbHost.setHostMem(vcHost.getHostMem());
								dbHost.setUsedMem(vcHost.getUsedMem());
								dbHost.setUsedCpu(vcHost.getUsedCpu());
								try {
									commonDAO.update("updateHost", dbHost);
									logger.debug("===========更新物理机成功=============");
								} catch (RollbackableBizException e) {
									logger.error("===========更新物理机失败=============",e);
								}
							}
							isExistHostIndics.add(i);
							//虚拟机比对入口
							updateOrSaveVcDataForVm(managerId,dataCenterId,resPoolId,clusterId,dbHost.getId(),vcHost.getVmList(),dbHost.getVmList());
							//本地存储比对入口
							updateOrSaveVcDataForLdDataStore(dbHost.getId(),vcHost.getLdDataStoreList(),dbHost.getLocalDiskList());
							//SAN存储比对入口
							updateOrSaveVcDataForSanDataStore(managerId,dbHost.getId(),vcHost.getSanDataStoreList(),dbHost.getSanDataStoreList());
							break;
						}
					}
				}
				//不存在执行新增后，进入虚拟机比对入口
				if(!isExistHostFlag){
					vcHost.setId(UUIDGenerator.getUUID());
					vcHost.setClusterId(clusterId);
					try {
						commonDAO.save("saveDeviceByHostVo", vcHost);
						commonDAO.save("saveHostByHostVo", vcHost);
						// 数据库接口变化，需要进行代码修改
						//commonDAO.save("saveIpByHostVo", vcHost);
						logger.debug("===========插入物理机成功=============");
					} catch (RollbackableBizException e) {
						logger.error("===========插入物理机失败=============",e);
						throw e;
					}
					updateOrSaveVcDataForVm(managerId,dataCenterId,resPoolId,clusterId,vcHost.getId(),vcHost.getVmList(),null);
					updateOrSaveVcDataForLdDataStore(vcHost.getId(),vcHost.getLdDataStoreList(),null);
					updateOrSaveVcDataForSanDataStore(managerId,vcHost.getId(),vcHost.getSanDataStoreList(),null);
				}
			}
			//批量删除已不存在的物理机
			if(dbHostList != null && isExistHostIndics != null && isExistHostIndics.size()>0){
				Collections.sort(isExistHostIndics);
				for(int i=isExistHostIndics.size()-1;i>=0;i--){
					dbHostList.remove(Integer.parseInt(isExistHostIndics.get(i)+""));
				}
				if(dbHostList.size() > 0 )
					deleteHostByHostList(dbHostList);
			}
		}
	}
	
	/**
	 * 比对物理机，没有则新增，有则更新
	 * @param hostId
	 * @param vcVmList
	 * @param dbVmList
	 * @throws RollbackableBizException 
	 */
	public void updateOrSaveVcDataForVm(String managerId,String dataCenterId,String resPoolId,String clusterId,String hostId,List<CmVmVo> vcVmList,List<CmVmVo> dbVmList) throws RollbackableBizException{
		if(vcVmList != null){
			List<Integer> isExistVmIndices = Lists.newArrayList();
			for(CmVmVo vcVm : vcVmList){
				boolean isExistVmFlag = false;
				if(dbVmList != null){
					for(int i=0;i<dbVmList.size();i++){
						CmVmVo dbVm = dbVmList.get(i);
						//如存在，将数据的虚拟机对象的cpu等相关属性更新为扫描过来的数据，执行更新操作
						if(vcVm.getCmVmIps().equals(dbVm.getCmVmIps())){
							isExistVmFlag = true;
							String vcCpu = vcVm.getCpu();
							String vcVmName = vcVm.getCmVmName();
							String vcMem = vcVm.getMem();
							String dbCpu = dbVm.getCpu();
							String dbVmName = dbVm.getCmVmName();
							String dbMem = dbVm.getMem();
							if(!(vcCpu.equals(dbCpu) && vcVmName.equals(dbVmName) && vcMem.equals(dbMem))){
								dbVm.setCpu(vcVm.getCpu());
								dbVm.setCmVmName(vcVm.getCmVmName());
								dbVm.setMem(vcVm.getMem());
								try {
									commonDAO.update("updateVmByVmVo", dbVm);
									logger.debug("===========更新虚拟机成功=============");
								} catch (RollbackableBizException e) {
									logger.error("===========更新虚拟机失败=============",e);
									throw e;
								}
							}
							isExistVmIndices.add(i);
							break;
						}
					}
				}
				//不存在执行新增后
				if(!isExistVmFlag){
					vcVm.setId(UUIDGenerator.getUUID());
					vcVm.setHostId(hostId);
					vcVm.setResPoolId(resPoolId);
					needSaveVmList.add(vcVm);
				}
			}
			if(dbVmList != null && isExistVmIndices != null && isExistVmIndices.size()>0){
				Collections.sort(isExistVmIndices);
				for(int i=isExistVmIndices.size()-1;i>=0;i--){
					dbVmList.remove(Integer.parseInt(isExistVmIndices.get(i)+""));
				}
				needDeleteVmList.addAll(dbVmList);
			}
		}
	}
	/**
	 * 比对本地存储，没有则新增，有则更新
	 * @param hostId
	 * @param ldDataStoreList
	 * @param localDiskList
	 * @throws RollbackableBizException 
	 */
	public void updateOrSaveVcDataForLdDataStore(String hostId,List<DataStoreVo> ldDataStoreList,List<CmLocalDiskPo> localDiskList) throws RollbackableBizException{
		if(ldDataStoreList != null){
			List<Integer> isExistLdIndices = Lists.newArrayList();
			for(DataStoreVo vcLd : ldDataStoreList){
				boolean isExistLdFlag = false;
				if(localDiskList != null){
					for(int i=0;i<localDiskList.size();i++){
						CmLocalDiskPo dbLd = localDiskList.get(i);
						//如存在，不对本地存储做任何操作。
						if(vcLd.getDsName().equals(dbLd.getName())){
							isExistLdFlag = true;
							isExistLdIndices.add(i);
							break;
						}
					}
				}
				//不存在执行新增后
				if(!isExistLdFlag){
					vcLd.setId(UUIDGenerator.getUUID());
					vcLd.setHostId(hostId);
					try {
						cmLocalDiskDAO.saveLocalDisk(vcLd);
						logger.debug("===========插入本地存储成功=============");
					} catch (RollbackableBizException e) {
						logger.error("===========插入本地存储失败=============",e);
						throw e;
					}
				}
			}
			if(localDiskList != null && isExistLdIndices != null && isExistLdIndices.size()>0){
				Collections.sort(isExistLdIndices);
				for(int i=isExistLdIndices.size()-1;i>=0;i--){
					localDiskList.remove(Integer.parseInt(isExistLdIndices.get(i)+""));
				}
				try {
					if(localDiskList.size() > 0){
						cmLocalDiskDAO.deleteLocalDiskList(localDiskList);
						logger.debug("===========删除本地存储成功=============");
					}
				} catch (RollbackableBizException e) {
					logger.error("===========删除本地存储失败=============",e);
					throw e;
				}
			}
		}
	}
	
	/**
	 * 比对san存储，没有则新增，有则更新
	 * @param hostId
	 * @param vcDataStoreList
	 * @param sanDataStoreList
	 * @throws RollbackableBizException 
	 */
	public void updateOrSaveVcDataForSanDataStore(String managerId,String hostId,List<DataStoreVo> vcDataStoreList,List<DataStoreVo> dbDataStoreList) throws RollbackableBizException{
		if(vcDataStoreList != null){
			List<Integer> isExistLdIndices = Lists.newArrayList();
			for(DataStoreVo vcLd : vcDataStoreList){
				boolean isExistLdFlag = false;
				if(dbDataStoreList != null){
					for(int i=0;i<dbDataStoreList.size();i++){
						DataStoreVo dbLd = dbDataStoreList.get(i);
						//如存在
						if(vcLd.getDsName().equals(dbLd.getDsName())){
							isExistLdFlag = true;
							isExistLdIndices.add(i);
							//如可用容量不等，则更新。
							if(!vcLd.getFreeSize().equals(dbLd.getFreeSize())){
								dbLd.setFreeSize(vcLd.getFreeSize());
								try {
									commonDAO.update("updateSanDataStoreByDataStoreVo", dbLd);
									logger.debug("===========更新SAN存储成功=============");
								} catch (RollbackableBizException e) {
									logger.debug("===========更新SAN存储失败=============");
									throw e;
								}
							}
							break;
						}
					}
				}
				//不存在执行新增后
				if(!isExistLdFlag){
					String dataStoreId = UUIDGenerator.getUUID();
					vcLd.setId(dataStoreId);
					vcLd.setHostId(hostId);
					vcLd.setDeviceId(managerId);
					
					CmHostDatastoreRefPo ref = new CmHostDatastoreRefPo();
					ref.setId(UUIDGenerator.getUUID());
					ref.setHostId(hostId);
					ref.setDatastoreId(dataStoreId);
					try {
						if(needSaveSanList != null){
							boolean isExistSanInNeedSaveList = false;
							for(DataStoreVo ds : needSaveSanList){
								if(ds.getDsName().equals(vcLd.getDsName())){
									isExistSanInNeedSaveList = true;
									ref.setDatastoreId(ds.getId());
									commonDAO.save("saveSanHostDataStoreRef", ref);
									break;
								}
							}
							if(!isExistSanInNeedSaveList){
								needSaveSanList.add(vcLd);
								commonDAO.save("saveSanDataStoreByDataStoreVo",vcLd);
								commonDAO.save("saveSanHostDataStoreRef", ref);
							}
						}
						logger.debug("===========插入SAN存储成功=============");
					} catch (RollbackableBizException e) {
						logger.error("===========插入SAN存储失败=============",e);
						throw e;
					}
				}
			}
			if(dbDataStoreList != null && isExistLdIndices != null && isExistLdIndices.size()>0){
				Collections.sort(isExistLdIndices);
				for(int i=isExistLdIndices.size()-1;i>=0;i--){
					dbDataStoreList.remove(Integer.parseInt(isExistLdIndices.get(i)+""));
				}
				if(dbDataStoreList.size() > 0){
					cmHostDAO.deleteSanDataStoreList(dbDataStoreList);
					logger.debug("===========删除本地存储成功=============");
				}
			}
		}
	}
	/**
	 * 需要新增的虚拟机与需要删除的虚拟机进行虚拟机名称比对,为发现迁移虚拟机操作
	 * @param needSaveVmList
	 * @param needDeleteVmList
	 * @throws RollbackableBizException 
	 */
	public void saveAndDeleteAfterCompareVm(List<CmVmVo> needSaveVmList,List<CmVmVo> needDeleteVmList) throws RollbackableBizException{
		if(needSaveVmList != null){
			List<Integer> isNeedTransferVmIndices = Lists.newArrayList(); 
			for(CmVmVo needSaveVmVo : needSaveVmList){
				boolean isTransferVmFlag = false;
				if(needDeleteVmList != null){
					for(int i=0;i<needDeleteVmList.size();i++){
						CmVmVo needDeleteVmVo = needDeleteVmList.get(i);
						//发现迁移虚拟,更新内存,cpu,ip
						if(needSaveVmVo.getCmVmName().equals(needDeleteVmVo.getCmVmName())){
							isTransferVmFlag = true;
							isNeedTransferVmIndices.add(i);
							needDeleteVmVo.setCpu(needSaveVmVo.getCpu());
							needDeleteVmVo.setMem(needSaveVmVo.getMem());
							needDeleteVmVo.setCmVmIps(needSaveVmVo.getCmVmIps());
							try {
								commonDAO.update("updateVmByVmVo", needDeleteVmVo);
								commonDAO.update("updateVmIpByVmVo", needDeleteVmVo);
								logger.debug("===========更新迁移虚拟机成功=============");
							} catch (RollbackableBizException e) {
								logger.error("===========更新迁移虚拟机失败=============",e);
								throw e;
							}
							break;
						}
					}
				}
				if(!isTransferVmFlag){
					try {
						Map<String,String> params = Maps.newHashMap();
						params.put("ip", needSaveVmVo.getCmVmIps());
						List<CmVmVo> tempList = cmVmDAO.findVmInfoListByParams(params);
						if(tempList == null || tempList.size() == 0){
							commonDAO.save("saveDeviceByVmVo", needSaveVmVo);
							commonDAO.save("saveVmByVmVo", needSaveVmVo);
							// commonDAO.save("saveIpByVmVo", needSaveVmVo);
							logger.debug("===========插入虚拟机成功=============");
						}else{
							CmVmVo tempVo = tempList.get(0);
							if(!needSaveVmVo.getCmVmName().equals(tempVo.getCmVmName()))
								commonDAO.update("updateDeviceByVmVo", needSaveVmVo);
							commonDAO.update("updateVmDeviceByVmVo", needSaveVmVo);
						}
						
					} catch (RollbackableBizException e) {
						logger.error("===========插入虚拟机失败=============",e);
						throw e;
					}
				}
			}
			if(needDeleteVmList != null && isNeedTransferVmIndices != null && isNeedTransferVmIndices.size()>0){
				Collections.sort(needDeleteVmList);
				for(int i=isNeedTransferVmIndices.size()-1;i>=0;i--){
					needDeleteVmList.remove(Integer.parseInt(isNeedTransferVmIndices.get(i)+""));
				}
				try {
					if(needDeleteVmList.size() > 0){
						List deviceList = needDeleteVmList;
						commonDAO.deleteForParam("deleteDeviceByParams", deviceList);
						logger.debug("===========插入虚拟机成功=============");
					}
				} catch (RollbackableBizException e) {
					logger.error("===========插入虚拟机失败=============",e);
					throw e;
				}
			}
		}
	}
	/**
	 * 调用AMQ接口，解析返回报文。
	 * @throws Exception 
	 */
	private List<RmDatacenterPo> getAmqXml(String url_ip,String username,String password) throws Exception{
		//请求适配代理
		IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
		IDataObject reqData = getIDataObject(url_ip, username, password);
		IDataObject rspData = null;
		try {
			rspData = invoker.invoke(reqData, 120000);
		} catch (Exception e) {
			logger.error("===========请求响应失败!=============",e);
			return null;
		}
		HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,
				HeaderDO.class);
		BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY,
				BodyDO.class);
		if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
			return parseData(rspBody);
		}else{
			logger.error("===========请求响应状态错误!错误代码:{}=============",rspHeader.getRetCode());
		}
		return null;
	}
	
	
	/**
	 * 解析封闭数据
	 * @param rspBody
	 * @throws Exception 
	 */
	
	@SuppressWarnings("unchecked")
	private List<RmDatacenterPo> parseData(BodyDO rspBody) throws Exception {
		List<DataObject> dataCenterObjList = rspBody.getList("DCINFO");
		List<DataObject> clusterObjList = rspBody.getList("CLUSTERINFO");
		List<DataObject> hostObjList = rspBody.getList("HOSTINFO");
		List<DataObject> vmObjList = rspBody.getList("VMINFO");
		List<DataObject> dataStoreObjList = rspBody.getList("DATASTOREINFO");
		
		List<RmDatacenterPo> dataCenterList = Lists.newArrayList();
		// 数据中心-集群
		// 迭代vc扫描结果
		for (DataObject dataObject : dataCenterObjList) {
			// 数据中心
			String dataCenter = dataObject.getString("DATACENTERCODE");
			RmDatacenterPo dataCenterPo = getDataCenterInfo(dataObject);
			
			//资源池
			List<RmResPoolVo> poolList = Lists.newArrayList();
			RmResPoolVo poolVo = new RmResPoolVo(null,dataCenter);
			
			// 集群
			List<RmClusterPo> clusterList = Lists.newArrayList();
			for(DataObject clusterObject : clusterObjList){
				if(dataCenter.equals(clusterObject.getString("DATACENTERCODE"))){
					String clusterCode = clusterObject.getString("CLUSTERCODE");
					RmClusterPo clusterPo = getClusterInfo(clusterObject);
					//物理机
					List<HostVo> hostList = Lists.newArrayList();
					for(DataObject hostObject : hostObjList){
						if(dataCenter.equals(hostObject.getString("DATACENTERCODE")) && 
								clusterCode.equals(hostObject.getString("CLUSTERCODE"))){
							String hostName = hostObject.getString("HOST_NAME");
							HostVo host = getHostInfo(dataCenter,clusterCode,hostObject);
							int usedCpu = 0,
								usedMem = 0;
							//虚拟机
							List<CmVmVo> vmList = Lists.newArrayList();
							for(DataObject vmObject : vmObjList){
								if(dataCenter.equals(vmObject.getString("DATACENTERCODE")) && 
										clusterCode.equals(vmObject.getString("CLUSTERCODE")) && 
											hostName.equals(vmObject.getString("HOST_NAME"))){
									CmVmVo vm = getVmInfo(hostName,vmObject);
									if(vm != null){
										vmList.add(vm);
										usedCpu += Integer.parseInt(vm.getCpu());
										usedMem += Integer.parseInt(vm.getMem());
									}
										
								}
							}
							host.setUsedCpu(usedCpu+"");
							host.setUsedMem(usedMem+"");
							host.setVmList(vmList);
							
							//本地存储及关联
							List<DataStoreVo> ldDataStoreVoList = Lists.newArrayList();
							List<DataStoreVo> sanDataStoreVoList = Lists.newArrayList();
							for(DataObject dataStoreObject : dataStoreObjList){
								if(hostName.equals(dataStoreObject.getString("HOST_NAME"))){
									DataStoreVo dataStore = getDataStoreInfo(dataStoreObject);
									if(dataStore != null){
										//本地
										if(dataStore.getDsName().substring(0, 2).equals("LD")){
											ldDataStoreVoList.add(dataStore);
										//san
										}else{
											sanDataStoreVoList.add(dataStore);
										}
									}
										
								}
							}
							host.setLdDataStoreList(ldDataStoreVoList);
							host.setSanDataStoreList(sanDataStoreVoList);
							hostList.add(host);
						}
					}
					clusterPo.setHostList(hostList);
					clusterList.add(clusterPo);
				}
				
			}
			poolVo.setClusterList(clusterList);
			poolList.add(poolVo);
			dataCenterPo.setPoolList(poolList);
			dataCenterList.add(dataCenterPo);
			
		}
		return dataCenterList;
	}
	/**
	 * 获取数据体
	 * @param header
	 * @param body
	 * @return
	 */
	private IDataObject getIDataObject(String url_ip,String username,String password){
		
		HeaderDO header = null;
		//waiwangIP
		if("192.168.72.210".equals(url_ip))
			header = getHeaderDO("DATACENTER_QUEUE_IDEN", "BJ");
		else//TODO
			header = getHeaderDO("DATACENTER_QUEUE_IDEN", "BJ");
			
		BodyDO body = getBodyDO(url_ip,username,password);
		return getIDataObject(header,body);
	}
	private IDataObject getIDataObject(HeaderDO header,BodyDO body){
		IDataObject reqData = DataObject.CreateDataObject();
		reqData.setDataObject(MesgFlds.HEADER, header);
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}
	/**
	 * 获取数据体正文
	 * @param url_ip
	 * @param username
	 * @param password
	 * @return
	 */
	private BodyDO getBodyDO(String url_ip,String username,String password){
		BodyDO body = BodyDO.CreateBodyDO();
		body.set(VMFlds.URL, "https://"+url_ip+"/sdk");
		body.set(VMFlds.USERNAME, username);
		body.set(VMFlds.PASSWORD, password);
		return body;
	}
	/**
	 * 获取数据体头部
	 * @return
	 */
	private HeaderDO getHeaderDO(String queueIded,String pos){
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperationBean("queryGyrxVcServiceImpl");
//		header.set("DATACENTER_QUEUE_IDEN", "BJ");
		header.set(queueIded, pos);
		return header;
	}
	
	/**
	 * 扫描vc线程
	 * @author jsm
	 *
	 */
	class ScanVc implements Callable<Map<String,List<RmDatacenterPo>>>{
		private RmVmManageServerPo vmServerPo;
//		private ResAdptInvokerFactory resInvokerFactory;
		public ScanVc(RmVmManageServerPo vmServerPo){
			this.vmServerPo = vmServerPo;
		}
		@Override
		public Map<String,List<RmDatacenterPo>> call() throws Exception {
			//调用AMQ接口
			List<RmDatacenterPo> scanDataList = getAmqXml(vmServerPo.getManageIp(),vmServerPo.getUserName(),PwdUtil.decryption(vmServerPo.getPassword()));
			Map<String,List<RmDatacenterPo>> vmManagerAndDataCenterByScan = Maps.newHashMap();
			vmManagerAndDataCenterByScan.put(vmServerPo.getId(), scanDataList);
			return vmManagerAndDataCenterByScan;
		}
		
	}
	
	/**
	 * 读取数据库线程
	 * @author jsm
	 *
	 */
	class ReadDB implements Callable<Map<String,RmDatacenterPo>>{
		private RmVmManageServerPo vmServerPo;
		//数据库服务层接口
		private IRmResPoolDAO rmResPoolDAO;
		private IRmClusterDAO rmClusterDAO; 
		private ICmHostDAO cmHostDAO;
		private ICmVmDAO cmVmDAO;
		private ICmLocalDiskDAO cmLocalDiskDAO;
		//构造函数
		public ReadDB(RmVmManageServerPo vmServerPo,IRmResPoolDAO rmResPoolDAO,IRmClusterDAO rmClusterDAO,ICmHostDAO cmHostDAO,ICmVmDAO cmVmDAO,ICmLocalDiskDAO cmLocalDiskDAO){
			this.vmServerPo = vmServerPo;
			this.rmResPoolDAO = rmResPoolDAO;
			this.rmClusterDAO = rmClusterDAO;
			this.cmHostDAO = cmHostDAO;
			this.cmVmDAO = cmVmDAO;
			this.cmLocalDiskDAO = cmLocalDiskDAO;
		}
		@Override
		public Map<String,RmDatacenterPo> call() throws Exception {
//			map<数据中心,map<资源池,map<集群,Map<物理机Ip,List<虚拟机>>>>
//			Map<String,Map<String,Map<String,List<CmVmVo>>>> dataCenterAndClusterAndHostAndVM = Maps.newHashMap();
			//数据中心
			String dataCenterName = vmServerPo.getDataCenterName();
			RmDatacenterPo dataCenter = new RmDatacenterPo(vmServerPo.getDatacenterId());
			dataCenter.setDatacenterCode(dataCenterName);
			//资源池
			HashMap<String,String> poolParams = Maps.newHashMap();
			poolParams.put("datacenterId", vmServerPo.getDatacenterId());
			poolParams.put("poolType", "COMPUTE");
			List<RmResPoolPo> poolList = rmResPoolDAO.findListByFieldsAndOrder("selectResPool",poolParams);
			List<RmResPoolVo> poolVoList = Lists.newArrayList();
			if(poolList != null){
				//集群
				HashMap<String,String> clusterParams = Maps.newHashMap();
				for(RmResPoolPo poolPo : poolList){
					RmResPoolVo poolVo = new RmResPoolVo(poolPo.getId(), poolPo.getPoolName());
					clusterParams.put("resPoolId", poolPo.getId());
					List<RmClusterPo> clusterList = rmClusterDAO.findListByFieldsAndOrder("selectClusterByResPoolId", clusterParams);
					if(clusterList != null){
						//物理机
						Map<String,String> hostParams = Maps.newHashMap();
						for(RmClusterPo clusterPo : clusterList){
							hostParams.put("clusterId", clusterPo.getId());
							List<HostVo> hostList = cmHostDAO.findHostInfoListByParams(hostParams);
							if(hostList != null){
								Map<String,String> vmParams = Maps.newHashMap();
								for(HostVo hostVo : hostList){
									//虚拟机
									vmParams.put("hostId", hostVo.getId());
									List<CmVmVo> vmList = cmVmDAO.findVmInfoListByParams(vmParams);
									hostVo.setVmList(vmList);
									
									//本地存储
									List<CmLocalDiskPo> ldDataStoreList = cmLocalDiskDAO.findLocalDiskListByHostId(hostVo.getId());
									hostVo.setLocalDiskList(ldDataStoreList);
									
									//san
									List<DataStoreVo> sanDataStoreList = cmHostDAO.findSanStorgeListByHostId(hostVo.getId());
									hostVo.setSanDataStoreList(sanDataStoreList);
								}
							}
							clusterPo.setHostList(hostList);
						}
					}
					poolVo.setClusterList(clusterList);
					poolVoList.add(poolVo);
				}
				dataCenter.setPoolList(poolVoList);
			}
			Map<String,RmDatacenterPo> vmManagerAndDataCenterByRead = Maps.newHashMap();
			vmManagerAndDataCenterByRead.put(vmServerPo.getId(), dataCenter);
			return vmManagerAndDataCenterByRead;
		}
		
	}
	/**
	 * 封装数据中心
	 * @param dataObject
	 * @return
	 */
	public RmDatacenterPo getDataCenterInfo(DataObject dataObject){
		String dataCenter = dataObject.getString("DATACENTERCODE");
		RmDatacenterPo dataCenterPo = new RmDatacenterPo();
		dataCenterPo.setDatacenterCname(dataCenter);
		dataCenterPo.setDatacenterCode(dataCenter);
		dataCenterPo.setEname(dataCenter);
		dataCenterPo.seteName(dataCenter);
		return dataCenterPo;
	}
	/**
	 * 封装集群
	 * @param clusterObject
	 * @return
	 */
	public RmClusterPo getClusterInfo(DataObject clusterObject){
		String clusterCode = clusterObject.getString("CLUSTERCODE");
		RmClusterPo clusterPo = new RmClusterPo(null,clusterCode,null);
		return clusterPo;
	}
	/**
	 * 封装物理机
	 * @param dataCenter
	 * @param clusterCode
	 * @param hostObject
	 * @return
	 */
	public HostVo getHostInfo(String dataCenter,String clusterCode,DataObject hostObject){
		// 物理机名称-实为物理机管理IP
		String hostName = hostObject.getString("HOST_NAME");
		// 物理机内存
		String mem = hostObject.getString("HOST_MEM");
		String usedMem = hostObject.getString("USED_MEM");
		// 物理机CPU
		String cpu = hostObject.getString("HOST_CPU");
		String usedCpu = hostObject.getString("USED_CPU");
		return new HostVo(null,dataCenter,null,clusterCode,hostName,mem,cpu,usedCpu,usedMem,hostName);
	}
	/**
	 * 封装虚拟机
	 * @param hostName
	 * @param vmObject
	 * @return
	 * @throws Exception 
	 */
	public CmVmVo getVmInfo(String hostName,DataObject vmObject) throws Exception{
		String vmName = vmObject.getString("VM_NAME");
		String vmMem = vmObject.getInt("VM_MEM")+"";
		String vmCpu  =vmObject.getInt("VM_CPU")+"";
		String vmIp = vmObject.getString("VM_IP");
		
		if(vmIp != null && !("".equals(vmIp.trim()))){
			CmVmVo vm = new CmVmVo();
			vm.setCmVmIps(vmIp);
			if(vmIp.contains(",")){
				String[] ipArr = vmIp.split(",");
				boolean isExistManagerIp = false;
				try{
					vm.setCmVmIps(ipArr[ipArr.length-1]);
					isExistManagerIp = true;
				}catch(Exception e){
					throw new Exception("查找虚拟机管理ip失败");
				}
				if(!isExistManagerIp){
					throw new Exception("虚拟机无管理ip");
				}
				
			}
			
			vm.setCmVmName(vmName);
			vm.setCpu(vmCpu);
			vm.setDeviceName(hostName);
			vm.setMem(vmMem);
			return vm;
		}
		return null;
	}
	
	public DataStoreVo getDataStoreInfo(DataObject dsObject){
		String dsName = dsObject.getString("DS_NAME"),
			   path = dsObject.getString("PATH"),
			   hostName = dsObject.getString("HOST_NAME"),
			   type = dsObject.getString("DATASTORE_TYPE"),
			   freeSize = dsObject.getString("DATASTORE_FREE_SIZE"),
			   remoteIp = dsObject.getString("DATASTORE_REMOTE_IP");
		DataStoreVo vo = new DataStoreVo(null, dsName, path, hostName, freeSize);
		vo.setDatastoreType(type);
		vo.setRemoteIp(remoteIp);
		return vo;
	}
	public void setResInvokerFactory(ResAdptInvokerFactory resInvokerFactory) {
		this.resInvokerFactory = resInvokerFactory;
	}

	public void setRmResPoolDAO(IRmResPoolDAO rmResPoolDAO) {
		this.rmResPoolDAO = rmResPoolDAO;
	}

	public void setRmClusterDAO(IRmClusterDAO rmClusterDAO) {
		this.rmClusterDAO = rmClusterDAO;
	}

	public void setCmHostDAO(ICmHostDAO cmHostDAO) {
		this.cmHostDAO = cmHostDAO;
	}

	public void setCmVmDAO(ICmVmDAO cmVmDAO) {
		this.cmVmDAO = cmVmDAO;
	}
	public void setRmVmManageServerDAO(IRmVmManageServerDAO rmVmManageServerDAO) {
		this.rmVmManageServerDAO = rmVmManageServerDAO;
	}

	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void setCmLocalDiskDAO(ICmLocalDiskDAO cmLocalDiskDAO) {
		this.cmLocalDiskDAO = cmLocalDiskDAO;
	}

	public void setCmDeviceDAO(ICmDeviceDAO cmDeviceDAO) {
		this.cmDeviceDAO = cmDeviceDAO;
	}
}
