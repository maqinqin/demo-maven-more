package com.git.cloud.resmgt.common.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;

public interface IRmPlatformService extends IService{

	public Object getRmPlatformPagination(PaginationParam paginationParam);

	public void saveRmPlatform(RmPlatformPo rmPlatformPo) throws RollbackableBizException;

	public void updateRmPlatform(RmPlatformPo rmPlatformPo) throws RollbackableBizException;

	public RmPlatformPo selectRmPlatform(String platformId) throws RollbackableBizException;
	
	public List<Map<String, Object>> selectRmPlatformSel() throws RollbackableBizException;

	public String deleteRmPlatform(String[] split) throws RollbackableBizException;

	public RmPlatformPo selectRmPlatformForTrim(String platformId) throws RollbackableBizException;

	public Object getRmVirtualTypePagination(PaginationParam paginationParam);

	public void saveVirtualType(RmVirtualTypePo rmVirtualTypePo) throws RollbackableBizException;

	public RmVirtualTypePo selectRmVirtualTypeForTrim(String virtualTypeCode) throws RollbackableBizException;

	public RmVirtualTypePo selectRmVirtualType(String virtualTypeId) throws RollbackableBizException;
	
	public List<Map<String, Object>> selectRmVirtualTypeSel(String platformId) throws RollbackableBizException;

	public void updateRmVirtualType(RmVirtualTypePo rmVirtualTypePo) throws RollbackableBizException;

	public String deleteVirtualType(String virtualTypeId) throws RollbackableBizException;

	public RmPlatformPo selectRmPlatformNameForTrim(String platformName) throws RollbackableBizException;
	
	public String isDeleteRmPlatform(String[] split) throws RollbackableBizException;
	
	public String isDeleteVirtualType(String virtualTypeId) throws RollbackableBizException;

}
