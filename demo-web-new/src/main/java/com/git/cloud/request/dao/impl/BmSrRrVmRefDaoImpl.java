package com.git.cloud.request.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.CollectionUtil;
import com.git.cloud.request.dao.IBmSrRrVmRefDao;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;

/**
 * 资源申请设备数据层实现类
 * @ClassName:BmSrRrVmRefDaoImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-9-30 上午10:26:15
 */
public class BmSrRrVmRefDaoImpl extends CommonDAOImpl implements IBmSrRrVmRefDao {
	
	public void insertBmSrRrVmRef(List<BmSrRrVmRefPo> rrVmRefList) throws RollbackableBizException {
		if(CollectionUtil.hasContent(rrVmRefList)){
			this.batchInsert("insertBmSrVmRef",rrVmRefList);	
		}
	}
	
	public void updateBmSrRrVmRef(BmSrRrVmRefPo vmRef) throws RollbackableBizException {
		this.update("updateBmSrRrVmRef", vmRef);
	}
	
	public void deleteBmSrRrVmRefBySrId(String srId) throws RollbackableBizException {
		this.delete("deleteBmSrRrVmRefBySrId", srId);
	}
	
	public void deleteBmSrRrVmRefByRrinfoId(String rrinfoId) throws RollbackableBizException {
		this.delete("deleteBmSrRrVmRefByRrinfoId", rrinfoId);
	}
	
	public List<BmSrRrVmRefPo> findBmSrRrVmRefListByRrinfoId(String rrinfoId) throws RollbackableBizException {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("rrinfoId", rrinfoId);
		return this.findListByParam("findBmSrRrVmRefListByRrinfoId", paramMap);
	}
	
	public List<BmSrRrVmRefPo> findBmSrRrVmRefListBySrId(String srId) throws RollbackableBizException {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("srId", srId);
		return this.findListByParam("findBmSrRrVmRefListBySrId", paramMap);
	}
	
	public BmSrRrVmRefPo findBmSrRrVmRefByRrinfoIdAndVmId(String rrinfoId, String deviceId) throws RollbackableBizException {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("rrinfoId", rrinfoId);
		paramMap.put("deviceId", deviceId);
		List<BmSrRrVmRefPo> vmRefList = this.findListByParam("findBmSrRrVmRefByRrinfoIdAndVmId", paramMap);
		BmSrRrVmRefPo vmRef = null;
		if(vmRefList != null && vmRefList.size() > 0) {
			vmRef = vmRefList.get(0);
		}
		return vmRef;
	}

	@Override
	public BmSrRrVmRefPo findBmSrRrVmVolumeTypeByDeviceId(String deviceId)throws RollbackableBizException {
		Map<String, String> paramMap = new HashMap<String, String> ();
		paramMap.put("deviceId", deviceId);
		List<BmSrRrVmRefPo> vmRefList = this.findListByParam("findBmSrRrVmVolumeTypeByDeviceId", paramMap);
		BmSrRrVmRefPo vmRef = null;
		if(vmRefList != null && vmRefList.size() > 0) {
			vmRef = vmRefList.get(0);
		}
		return vmRef;
	}
}
