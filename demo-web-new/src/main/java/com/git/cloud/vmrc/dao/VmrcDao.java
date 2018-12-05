package com.git.cloud.vmrc.dao;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.vmrc.model.po.VmrcPo;

/**
* @ClassName: VmrcDao  
* @Description: vmware remote console dao
* @author WangJingxin
* @date 2016年6月27日 下午2:22:02  
*
 */
public class VmrcDao extends CommonDAOImpl {

	/**
	* @Title: queryVmrcInfoByVmId  
	* @Description: 查询 vmware remote console 连接所需的信息
	* @param vmId
	* @return VmrcPo    
	 */
	public VmrcPo queryVmrcInfoByVmId(String vmId) throws Exception {
		VmrcPo po = super.findObjectByID("queryVmrcInfoByVmId",vmId);
		return po;
	}
}
