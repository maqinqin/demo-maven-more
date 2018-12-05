package com.git.cloud.resmgt.network.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.network.dao.IRmNwSecureDAO;
import com.git.cloud.resmgt.network.model.po.RmNwSecurePo;
import com.git.cloud.resmgt.network.model.vo.RmNwSecureVo;
import com.git.cloud.resmgt.network.service.IRmNwSecureService;

public class RmNwSecureServiceImpl implements IRmNwSecureService{
 private IRmNwSecureDAO  rmNwSecureDAO;

    public IRmNwSecureDAO getRmNwSecureDAO() {
	return rmNwSecureDAO;
  }

   public void setRmNwSecureDAO(IRmNwSecureDAO rmNwSecureDAO) {
	this.rmNwSecureDAO = rmNwSecureDAO;
}

@Override
public Pagination<RmNwSecureVo> queryPagination(PaginationParam pagination)
		throws RollbackableBizException {
	// TODO Auto-generated method stub
	return rmNwSecureDAO.queryPagination(pagination);
}

@Override
public void saveSecureArea(RmNwSecurePo rmNwSecurePo)
		throws RollbackableBizException {
	// TODO Auto-generated method stub
	rmNwSecureDAO.insertSecureArea(rmNwSecurePo);
	
}

@Override
public RmNwSecurePo getSecureAreaById(String secureAreaId)
		throws RollbackableBizException {
	// TODO Auto-generated method stub
	return rmNwSecureDAO.getSecureAreaById(secureAreaId);
}

@Override
public void updateSecureArea(RmNwSecurePo rmNwSecurePo)
		throws RollbackableBizException {
	// TODO Auto-generated method stub
	rmNwSecureDAO.updateSecureArea(rmNwSecurePo);
}

@Override
public Pagination<RmNwSecureVo> queryPaginationTier(PaginationParam pagination)
		throws RollbackableBizException {
	// TODO Auto-generated method stub
	return rmNwSecureDAO.queryPaginationTier(pagination);
}

@Override
public void saveSecureTier(RmNwSecureVo rmNwSecureVo)
		throws RollbackableBizException {
	// TODO Auto-generated method stub
	rmNwSecureDAO.insertSecureTier(rmNwSecureVo);
}

@Override
public RmNwSecureVo getSecureTierById(String secureTierId)
		throws RollbackableBizException {
	// TODO Auto-generated method stub
	return rmNwSecureDAO.getSecureTierById(secureTierId);
}

@Override
public void updateSecureTier(RmNwSecureVo rmNwSecureVo)
		throws RollbackableBizException {
	// TODO Auto-generated method stub
	rmNwSecureDAO.updateSecureTier(rmNwSecureVo);
}


@Override
public void deleteSecureTier(String secureTierId)
		throws RollbackableBizException {
	// TODO Auto-generated method stub
	RmNwSecureVo rmNwSecureVo=new RmNwSecureVo();
	       rmNwSecureVo.setSecureTierId(secureTierId);
	       rmNwSecureVo.setIsActive(IsActiveEnum.NO.getValue());
	       rmNwSecureDAO.updateSecureTierById(rmNwSecureVo);
}

@Override
public Map<String,String> deleteCheckArea(String[] ids) throws RollbackableBizException {
		  
		   Map<String,String> map=new HashMap<String,String>();
		   for(String waitForCheckId:ids){
			  List<RmNwSecureVo> list1= rmNwSecureDAO.checkSecureAreaSon(waitForCheckId);//第一个检查 父子
			  String flag = "";
				 if(list1.size()>0){
					 flag = ",tier";
					// map.put(waitForCheckId, flag);
					 //continue;
				 }
				 //第二步： 检查C段网络IP表
				 List<RmNwSecureVo> list2= rmNwSecureDAO.checkNetIp(waitForCheckId);
				 if(list2.size()>0){
					 flag += ",cnet";
					// map.put(waitForCheckId, flag);
					// continue;
				 }
				 //第三步：检查服务器角色使用情况
				 List<RmNwSecureVo> list3= rmNwSecureDAO.checkAppDu(waitForCheckId);
				 if(list3.size()>0){
					 flag += ",app";
					// map.put(waitForCheckId, flag);
					 //continue;
				 }
				 //第四部：计算资源池使用情况
				 List<RmNwSecureVo> list4=rmNwSecureDAO.checkResPoll(waitForCheckId);
				 if(list4.size()>0){
					 flag += ",pool";
					// map.put(waitForCheckId, flag);
					// continue;
				 }	
				 map.put(waitForCheckId,flag);
			 }
		    
		   return map;
	
        }


@Override
public void deleteSecureArea(String[] ids)throws RollbackableBizException {
	// TODO Auto-generated method stub
	
      rmNwSecureDAO.updateSecureAreaById(ids);
}
//安全层删除前检查，是否被C段IP ，服务器角色，计算资源池所占有
@Override
public Map<String, String> deleteCheckTierById(String TierId)
		throws RollbackableBizException {
	// TODO Auto-generated method stub
	Map<String,String> map=new HashMap<String,String>();
	      String  fg="";
		//第二步：安全层下检查服务器角色使用情况
		List<RmNwSecureVo>   list2=rmNwSecureDAO.checkTierAppDu(TierId);
		if(list2.size()>0){
			fg=",app";
			}
		//第三步：安全层下检查计算资源池使用情况
		List<RmNwSecureVo>   list3=rmNwSecureDAO.checkTierPoll(TierId);
		if(list3.size()>0){
			fg=",pool";
			}
		  map.put(TierId,fg);
	 
	return map;
    }

}

 


