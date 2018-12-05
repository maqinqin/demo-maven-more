package com.git.cloud.resmgt.common.util;

import java.util.HashMap;
import java.util.List;

import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.policy.model.vo.PolicyResultVo;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.IRmResPoolDAO;
import com.git.cloud.resmgt.common.dao.impl.RmDatacenterDAO;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.vo.RmResPoolVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @ClassName:VmNameGenerateByResPoolManager
 * @Description:根据资源池生成虚机名
 * @author sunhailong
 * @date 2018-8-7
 *
 *
 */
public class VmNameGenerateByResPoolManager {
	private static ICmDeviceDAO deviceDao;
	private static RmDatacenterDAO rmDatacenterDAO;
	private static IRmResPoolDAO rmResPoolDAO;

	public static void main(String[] args){
		
	}

	@SuppressWarnings("unchecked")
	public synchronized static List<String> getResourceName(HashMap<String,Object> map) throws Exception {
		//规则信息
		List<PolicyResultVo> policyResultList = (List<PolicyResultVo>) map.get("ls");

		deviceDao = (ICmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
		rmDatacenterDAO = (RmDatacenterDAO)WebApplicationManager.getBean("rmDatacenterDAO");
		rmResPoolDAO = (IRmResPoolDAO) WebApplicationManager.getBean("rmResPoolDAO");
		
		List<String> rs = Lists.newArrayList();
		// 查询数据中心
		String datacenterId = String.valueOf(map.get("dcId"));
		RmDatacenterPo dp = (RmDatacenterPo) rmDatacenterDAO.findObjectByID("findDatacenterById",datacenterId);
		// 数据中心编码
		String datacenterCode = dp.getDatacenterCode();
		String resPoolCode = "";
		PolicyResultVo policyResult;
		int hasVmNum = 0;
		int num = 1;
		for(int i = 0 ; i < policyResultList.size() ; i++) {
			policyResult = policyResultList.get(i);
			if(i == 0) {
				HashMap<String, String> params = Maps.newHashMap();
				params.put("resPoolId", policyResult.getPoolId());
				hasVmNum = deviceDao.getCountCmAllVM("getVMCountByResPool", params);
				RmResPoolVo rmResPool = rmResPoolDAO.findRmResPoolVoById(policyResult.getPoolId());
				resPoolCode = rmResPool.getEname();
				if(resPoolCode.length() > 9) {
					resPoolCode = resPoolCode.substring(0, 10);
				}
			}
			String endNum = String.format("%04d", hasVmNum + num);
			if(hasVmNum + num > 9999) {
				endNum = String.format("%05d", hasVmNum + num);
			}
			num ++;
			String deviceName = datacenterCode + resPoolCode + endNum;
			deviceName = deviceName.replaceAll("_", "").replaceAll("-", "").toLowerCase();
			int c = deviceDao.getCountByDeviceNameNew(deviceName);
			if (c > 0) {
				i--;
				continue;
			}
			rs.add(deviceName);
		}
		return rs;
	}
}
