package com.git.cloud.parame.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.parame.model.po.ParameterPo;
import com.git.cloud.parame.service.ParameterService;
import com.git.support.util.PwdUtil;


/**
 * 服务器角色Action类
 * @author chenshumei
 * @date 2015-1-4下午4:23:34
 * @version v1.0
 *
 */
@SuppressWarnings("rawtypes")
public class ParameterTestAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private ParameterService  parameterServiceImpl=null;
	private ParameterPo  paramPo=new ParameterPo();
	private Pagination pagination;
	 
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public ParameterPo getParamPo() {
		return paramPo;
	}
	public void setParamPo(ParameterPo paramPo) {
		this.paramPo = paramPo;
	}
	public ParameterService getParameterServiceImpl() {
		return parameterServiceImpl;
	}
	public void setParameterServiceImpl(ParameterService parameterServiceImpl) {
		this.parameterServiceImpl = parameterServiceImpl;
	}
	/*
	 * 查询参数
	 */
	public void search() throws Exception{
		pagination = parameterServiceImpl.queryPagination(this.getPaginationParam());
		this.jsonOut(pagination);
	}
	
	/*
	 * 保存参数
	 */
	public void save() throws Exception{
		
		String paramId=paramPo.getParamId();
		String IsEncryption = paramPo.getIsEncryption();		
		if("Y".equals(IsEncryption)){
			String paramValueEncrypted = PwdUtil.encryption(paramPo.getParamValue());
			paramPo.setParamValue(paramValueEncrypted);
		}
		
		//添加参数
		if(paramId==null||"".equals(paramId)){
            paramPo.setParamId(UUIDGenerator.getUUID());
		    paramPo.setIsActive(IsActiveEnum.YES.getValue());
		    
			parameterServiceImpl.save(paramPo);
			
		//修改参数
		}else{		
			parameterServiceImpl.update(paramPo);
		}
		
	}
	/*
	 * 新增修改参数
	 */
     public void view() throws Exception{
    	 String paramId=paramPo.getParamId();//获得前台传入的ID
    	 ParameterPo po=parameterServiceImpl.view(paramId);
    	 
    	 this.jsonOut(po);
   
     } 
     /*
      * 根据Id删除参数
      */
     public void delete() throws Exception{
    	 String paramId=paramPo.getParamId();
    	 parameterServiceImpl.delete(paramId);
     }
     /*
 	 * 根据参数名判断
 	 */
    public void getParamName()  throws Exception{
    	String paramName=paramPo.getParamName();//获得前台参数名
    	List<ParameterPo> list=parameterServiceImpl.getParamName(paramName);
    	//根据返回list大小确定是否有重名参数
    	if(list.size()>0){
    		this.stringOut("false");
    	}else{
    		this.stringOut("true");
    	}
    }
	  
	   public  String   init(){
		return "success";
	}
	/**
	 * 获取页面刷新时间
	 * @throws Exception
	 */
	public void pageRefreshTime() throws Exception{
		String  pageRefreshTime = parameterServiceImpl.getParamValueByName("PageRefreshTime");
		this.stringOut(pageRefreshTime);
	}
	
}