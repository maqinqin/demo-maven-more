package com.git.cloud.tankflow.action;

import java.util.List;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.tankflow.service.IBusinessDesignerService;

@SuppressWarnings("rawtypes")
public class BusinessDesignerAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2243555600731225261L;
	private IBusinessDesignerService designerService;
	private String id;
	
	/**
	 * 获取模板树型列表
	 * @throws Exception 
	 */
	public void getTemplateTree() throws Exception 
	{
		arrayOut( designerService.getTemplateTree() );
	}

	/**
	 * 通过类型获取模板列表
	 */
	public void getTemplateByType() throws Exception 
	{
		List<?> list = designerService.getTemplateByType(id);
		arrayOut( list );
	}
	
	
	
	public IBusinessDesignerService getDesignerService() {
		return designerService;
	}

	public void setDesignerService(IBusinessDesignerService designerService) {
		this.designerService = designerService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
