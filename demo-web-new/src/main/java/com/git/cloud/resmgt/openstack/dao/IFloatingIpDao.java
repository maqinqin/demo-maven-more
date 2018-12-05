package com.git.cloud.resmgt.openstack.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.openstack.model.po.FloatingIpPo;
import com.git.cloud.resmgt.openstack.model.vo.FloatingIpVo;

public interface IFloatingIpDao extends ICommonDAO {
	
	FloatingIpVo findFloatingIpByDeviceId(String deviceId) throws Exception;
	
	void updateFloatingIp(FloatingIpVo floatingIpVo) throws Exception;
	
	FloatingIpVo findFloatingIpByIp(String floatingIp) throws Exception;
	
	List<FloatingIpVo> findFloatingIpUnUsed(String projectId) throws Exception;

}
