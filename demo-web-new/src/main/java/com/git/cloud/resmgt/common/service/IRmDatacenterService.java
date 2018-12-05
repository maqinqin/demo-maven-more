package com.git.cloud.resmgt.common.service;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmResPoolPo;

public interface IRmDatacenterService extends IService{

		public RmDatacenterPo getDataCenterById(String dcId) throws RollbackableBizException;
		
		public List<RmDatacenterPo> getDataCenters() throws RollbackableBizException;

		public Object getDevicePagination(PaginationParam paginationParam);

		public void saveRmDatacenter(RmDatacenterPo rmDatacenterPo) throws RollbackableBizException;

		public void updateRmDatacenter(RmDatacenterPo rmDatacenterPo) throws RollbackableBizException;

		public Map<String, String> selectPoolByDatacenterId(String dataCenterId) throws RollbackableBizException;

		public void deleteDatacenter(String[] split) throws RollbackableBizException;

		public RmDatacenterPo selectQueueIdenfortrim(String queueIden) throws RollbackableBizException;

		public RmDatacenterPo selectDCenamefortrim(String ename) throws RollbackableBizException;

		/**
		 * 获取Datacenter的url,username,password
		  *
		  * @throws RollbackableBizException
		  * @return RmDatacenterPo
		 */
		public List<RmDatacenterPo> getDataCenterAccessData() throws RollbackableBizException;

		/**
		 * 获取卷列表信息
		 * @throws Exception 
		 */
		public String getDiskList() throws Exception;

		/**
		 * 获取卷详情
		 * @throws Exception 
		 */
		public String getDiskDetailed(String volumeId) throws Exception;
}
