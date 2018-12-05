package com.git.cloud.dic.action;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.dic.model.po.DicPo;
import com.git.cloud.dic.model.po.DicTypePo;
import com.git.cloud.dic.service.IDicService;

/**
 * 字典管理
 * @description: 
 * @author: wangdy
 * @Date: Dec 29, 2014
 * @modify：
 * @version: 1.0
 * @Company: 高伟达软件股份有限公司
 */
public class DicAction extends BaseAction<Object>{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(DicAction.class);
	private IDicService dicService;
	private DicTypePo dicTypePo = new DicTypePo();
	private DicPo dicPo = new DicPo();
	
	/**
	 * wmy,验证字典名称不能重复
	 * @throws Exception
	 */
	public void validateDicTypeName()throws Exception{
		this.jsonOut(dicService.validateDicTypeName(dicTypePo.getDicTypeName()));
	}
	/**
	 * 查询字典类型
	 * @Title: findDicTypePage
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void findDicTypePage() throws Exception {
		this.jsonOut(dicService.findDicTypePage(this.getPaginationParam()));
	}
	/**
	 * 保存字典类型
	 * @Title: saveDicType
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	//修改数据字典类型
	public void saveDicType() throws Exception {
		try{
			String result=null;
			if(dicService.dicTypeNameExist(dicTypePo)){
				result="fail";
			}
			if(null==dicTypePo.getDicTypeCode()||dicTypePo.getDicTypeCode().length()==0){//新增
				 result=dicService.insertDicType(dicTypePo);
			}else{
				 result=dicService.updateDicType(dicTypePo);
				 //wmy,修改字典类型时，数据字典表中的名称也相应进行更改
				dicService.updateDicTypeCode(dicTypePo);
			}
			this.stringOut(result);
		} catch (Exception e) {
	        logger.error("操作异常:"+e);
	        throw new RollbackableBizException(e.getMessage());
		}
	}
	
	public void findDicTypeByCode() throws Exception {
		    DicTypePo  po = dicService.findDicTypeByCode(dicTypePo.getDicTypeCode());
			this.jsonOut(po);
	}
	public void deleteDicType() throws Exception {
		try {
		  String result = dicService.deleteDicType(dicTypePo.getDicTypeCode(),dicTypePo.getDicTypeName());
		  this.stringOut(result);
		} catch (Exception e) {
			logger.error("操作异常:"+e);
		}
	}
	
	/**
	 * 查询字典
	 * @Title: findDicTypePage
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void findDicPage() throws Exception {
		this.jsonOut(dicService.findDicPage(this.getPaginationParam()));
	}
	
	/**
	 * 保存字典信息
	 * @Title: saveDic
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	//修改之后2015/5/18
	public void saveDic() throws Exception {
		String result=null;
		try{
			if(dicService.dicNameExist(dicPo)){
			 result="fail";
		    }
			if(dicPo.getDicId()==null||dicPo.getDicId().length()==0){
				result=dicService.insertDic(dicPo);
			}else{
				result=dicService.updateDic(dicPo);
			}
			this.stringOut(result);
		} catch (Exception e) {
	        logger.error("操作异常:"+e);
	        throw new RollbackableBizException(e.getMessage());
		}
	}
	/**
	 * 按dicId查询字典
	 * @Title: findDicById
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void findDicById() throws Exception {
	    DicPo  po = dicService.findDicById(dicPo.getDicId());
		this.jsonOut(po);
    }
	 /**
	  * 按dicId删除字典信息
	  * @Title: deleteDic
	  * @Description: TODO
	  * @field: @throws Exception
	  * @return void
	  * @throws
	  */
	public void deleteDic() throws Exception {
		try {
			  String result = dicService.deleteDic(dicPo.getDicId());
			  this.stringOut(result);
			} catch (Exception e) {
				logger.error("操作异常:"+e);
			}
	}
/*****************************************setter&&getter**************************************************/
	public void setDicService(IDicService dicService) {
		this.dicService = dicService;
	}
	public DicTypePo getDicTypePo() {
		return dicTypePo;
	}
	public void setDicTypePo(DicTypePo dicTypePo) {
		this.dicTypePo = dicTypePo;
	}
	public DicPo getDicPo() {
		return dicPo;
	}
	public void setDicPo(DicPo dicPo) {
		this.dicPo = dicPo;
	}
	
}
