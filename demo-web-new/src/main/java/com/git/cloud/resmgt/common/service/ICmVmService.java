package com.git.cloud.resmgt.common.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.vo.CmSnapshotVo;
import com.git.cloud.resmgt.common.model.vo.CmVmVo;

public interface ICmVmService extends IService{
	
	public String updateVmDuId(CmVmVo cmvm)throws Exception;
	
	public List<CmSnapshotVo> getCmSnapshotVoList(String vmId) throws Exception;
	
	/**
	 * 根据服务器角色获取服务器角色下的虚机
	 * 
	 * @Title: findCmVmByDuId
	 * @Description: TODO
	 * @field: @param duId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return List<CmVmPo>
	 * @throws
	 */
	public List<CmVmPo> findCmVmByDuId(String duId) throws RollbackableBizException;

}
