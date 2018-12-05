package com.git.cloud.appmgt.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.git.cloud.appmgt.dao.IDeployunitDao;
import com.git.cloud.appmgt.model.AppMgtEnum;
import com.git.cloud.appmgt.model.po.AppSysPO;
import com.git.cloud.appmgt.model.po.DeployUnitPo;
import com.git.cloud.appmgt.model.vo.DeployUnitVo;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.network.model.po.RmNwSecureTierPo;

/**
 * 服务器角色DAO实现类
 * @author liangshuang
 * @date 2014-12-17 上午11:15:55
 * @version v1.0
 *
 */
@Component
public class DeployunitDaoImpl extends CommonDAOImpl implements IDeployunitDao{
	
	private static Logger logger = LoggerFactory.getLogger(DeployunitDaoImpl.class);

	@Override
	public DeployUnitVo getDeployUnitById(String duId) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("duId", duId);
		map.put("serviceTypeCode", AppMgtEnum.DIC_TYPE_CODE_DU_SERVICE_TYPE.getValue());
		map.put("useStatus", AppMgtEnum.DIC_TYPE_CODE_USE_STATUS.getValue());
		DeployUnitVo vo=(DeployUnitVo)getSqlMapClientTemplate().queryForObject("getDeployUnitById",map);
		return vo;
	}
	
	@Override
	public <T extends DeployUnitPo> List<T> findListByFields(String method, HashMap<String, String> params) {
		List<T> list = getSqlMapClientTemplate().queryForList(method, params);
		return list;
	}
	
	/**
	 * 添加服务器角色
	 * @param deploy
	 * @return
	 */
	public void saveDeployunit(DeployUnitPo deploy) throws RollbackableBizException{
		super.save("insertDeployUnit", deploy);
	}
	/**
	 * 修改服务器角色
	 * @param deploy
	 * @return
	 */
	public void updateDeployunit(DeployUnitPo deploy) throws RollbackableBizException{
		super.update("updateDeployUnit", deploy);
	}

	/**
	 * 根据ID检查服务器角色使用状态
	 * @param duId
	 * @return
	 */
	public String deployUnitCheck(String duId){
		return null;
	}
	
	/**
	 * 根据ID查询数据
	 * @param method
	 * @param bizId
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<RmNwSecureTierPo> findByID(String secureAreaId) throws RollbackableBizException{
		List<RmNwSecureTierPo> list = super.findByID("getSecureTierList", secureAreaId);
		return list;
	}
	
	/**
	 * 根据ID查询数据
	 * @param method
	 * @param bizId
	 * @return
	 * @throws RollbackableBizException
	 */
	public AppSysPO findObjectByID(String appId) throws RollbackableBizException{
		AppSysPO tAppSysPO = super.findObjectByID("findAppSysById", appId);
		return tAppSysPO;
	}

	@Override
	public String getServiceIdByDuId(String duID)
			throws RollbackableBizException {
		List<String> serviceId = getSqlMapClientTemplate().queryForList("findServiceIdByDuId",duID);
		//String id = (String)getSqlMapClientTemplate().queryForObject("findServiceIdByDuId", duID);
		return serviceId.get(0);
	} 
	
	@Override
	public void updateDeployUnitServiceId(DeployUnitPo deploy)
			throws RollbackableBizException {
		super.update("updateDeployUnitServiceId", deploy);
	}

	@Override
	public boolean includeVm(String duId) throws RollbackableBizException {
		boolean flag = false;
		List<DeployUnitPo>  duList = getSqlMapClientTemplate().queryForList("includeVm",duId);
		if(duList.size()>0){
			//服务器角色下存在在虚拟机，返回true
			flag =  true;
		}else{
			//服务器角色下不存在虚拟机，返回false
			flag =  false;
		}
	
		return flag;
	}

	@Override
	public List<DeployUnitVo> deployUnitCheckByServiceId(String serviceId) {
		return getSqlMapClientTemplate().queryForList("deployUnitCheckByServiceId",serviceId);
	}
}
