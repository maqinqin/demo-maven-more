package com.git.cloud.resmgt.network.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.resmgt.common.model.po.CmRoutePo;
import com.git.cloud.resmgt.network.dao.IRmNwSecureDAO;
import com.git.cloud.resmgt.network.model.po.RmNwSecurePo;
import com.git.cloud.resmgt.network.model.vo.RmNwSecureVo;

/**
 * @Title 		RmNwSecureDAO.java 
 * @Package 	com.git.cloud.resmgt.network.dao.impl
 * @author 		mijia
 * @date 		2015-3-10
 * @version 	1.0.0
 * @Description  
 *
 */
public class RmNwSecureDAO extends CommonDAOImpl implements IRmNwSecureDAO {

	@Override
	public Pagination<RmNwSecureVo> queryPagination(PaginationParam pagination)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		 return this.pageQuery("selectSecureTotal", "selectSecurePage", pagination);
	}

	@Override
	public void insertSecureArea(RmNwSecurePo rmNwSecurePo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		this.save("insertSecureArea", rmNwSecurePo);
	}

	@Override
	public RmNwSecurePo getSecureAreaById(String secureAreaId)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("secureAreaId", secureAreaId);
		RmNwSecurePo  po=super.findObjectByMap("getSecureAreaById", map);
		return po;
		
		
		
	}

	@Override
	public void updateSecureArea(RmNwSecurePo rmNwSecurePo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		this.update("updateSecureArea", rmNwSecurePo);
	}

	@Override
	public Pagination<RmNwSecureVo> queryPaginationTier(
			PaginationParam pagination) throws RollbackableBizException {
		// TODO Auto-generated method stub
		 return this.pageQuery("selectSecureTierTotal", "selectSecureTierPage", pagination);
	}

	@Override
	public void insertSecureTier(RmNwSecureVo rmNwSecureVo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		this.save("insertSecureTier", rmNwSecureVo);
	}

	@Override
	public RmNwSecureVo getSecureTierById(String secureTierId)
			throws RollbackableBizException {
		// TODO Auto-generated method stub

		Map<String,String> map=new HashMap<String,String>();
		map.put("secureTierId", secureTierId);
		RmNwSecureVo  vo=super.findObjectByMap("getSecureTierById", map);
		return vo;
		
	}

	@Override
	public void updateSecureTier(RmNwSecureVo rmNwSecureVo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		this.update("updateSecureTier", rmNwSecureVo);
	}



	@Override
	public void updateSecureTierById(RmNwSecureVo rmNwSecureVo)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		update("seureTierById",rmNwSecureVo);
	}

	@Override
	public List<RmNwSecureVo> checkSecureAreaSon(String waitForCheckId)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		List<RmNwSecureVo> list=super.findByID("findSecureAreaSon", waitForCheckId);
		return list;
	}

	@Override
	public List<RmNwSecureVo> checkNetIp(String waitForCheckId)
			throws RollbackableBizException {
				
		// TODO Auto-generated method stub
		List<RmNwSecureVo> list=super.findByID("findSecureNetIp", waitForCheckId);
		return list;
	}

	@Override
	public List<RmNwSecureVo> checkAppDu(String waitForCheckId)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		List<RmNwSecureVo> list=super.findByID("findSecureAppDu", waitForCheckId);
		return list;
	}

	@Override
	public List<RmNwSecureVo> checkResPoll(String waitForCheckId)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		List<RmNwSecureVo> list=super.findByID("findSecurePool", waitForCheckId);
		return list;
	}

	@Override
	public void updateSecureAreaById(String[] ids)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		RmNwSecurePo rmNwSecurePo=new RmNwSecurePo();
			for(String id:ids){
				rmNwSecurePo.setSecureAreaId(id);
				  
				rmNwSecurePo.setIsActive(IsActiveEnum.NO.getValue());
				 this.update("updateSecureAreaById",rmNwSecurePo);//安全区域验证成功后进行删除
			}
		
	}
	@Override
	public List<RmNwSecureVo> checkTierAppDu(String tierId)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		List<RmNwSecureVo>  list=super.findByID("findSecureTierAppDu", tierId);
		return list;
	}

	@Override
	public List<RmNwSecureVo> checkTierPoll(String tierId)
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		List<RmNwSecureVo>  list=super.findByID("findSecureTierPool", tierId);
		return list;
	}
	
}
