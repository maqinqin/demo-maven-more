package com.git.cloud.handler.automation.se.dao;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.handler.automation.se.po.CmLun;
import com.git.cloud.handler.automation.se.po.CmSwitchPo;
import com.git.cloud.handler.automation.se.po.FabricPo;
import com.git.cloud.handler.automation.se.po.FcsPo;
import com.git.cloud.handler.automation.se.po.StorageFCPortGroupPo;
import com.git.cloud.handler.automation.se.po.StoragePo;
import com.git.cloud.handler.automation.se.po.StorageSuPo;
import com.git.cloud.handler.automation.se.po.VsanPo;
import com.git.cloud.handler.automation.se.vo.FabricVo;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.resmgt.storage.model.vo.StorageDeviceVo;

public interface StorageDAO  extends ICommonDAO {
	public List<Map<String, ?>> findStorageDevInfoBySuId(String su_id,String model_code);
	public List<Map<String,?>> findUsedStorageDevInfoBySuId(String su_id,String deviceId);
	public StorageMgrVo findStorageMgrInfoBySuId(String su_id);
	public StorageSuPo findSuInfoBySN(String sn);
	
	public List<StorageSuPo> findStorageSuByPoolId(String poolId);
	
	public StorageDeviceVo findStorageDevBySn(String sn);
	public StorageSuPo findStorageSuById(String suId);
	public FabricVo findFabricVoById(String fabricId);
	public StoragePo getLastSelectedPortGroup(String sn);
	public List<StorageFCPortGroupPo> getStorageFcPortListBySN(String sn);
	public List<VsanPo> findVsanInfoByFabircId(String fabricId);
	public StorageMgrVo findStorageMgrInfoByFabric(String fabricId);
	public FabricPo findFabricPoByName(String fabricName);
	public FabricPo findFabricPoByMgrId(String storageMgrId);
	public VsanPo findVsanPoByParams(Map<String,String> paramMap);
	public List<FcsPo> findFcsPoListByFabricName(Map<String,String> paramMap);
	public List<CmSwitchPo> findSwitchList(Map<String,String> paramMap);
	public List<CmLun> findLunList(Map<String,String> paramMap);
	public void lunInfoBatchupdate(List<CmLun> cmLunList);
	public void batchUpdateLunStatusAndType(List<CmLun> cmLunList);
	public void batchUpdateLunName(List<CmLun> cmLunList);
	public List<CmLun> getUsedLunsByResourceIds(List<String> deviceIdList);
	public StorageMgrVo findStorageMgrInfoByParam(Map<String, String> map);
	public StorageFCPortGroupPo findFCPortByWWN(String wwn);
	public List<String> findOccupyLunBysn(String sn);
	public CmLun findLunByPath(String path);
}
