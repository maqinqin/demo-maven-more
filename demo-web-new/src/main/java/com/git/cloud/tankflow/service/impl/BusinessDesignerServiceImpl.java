package com.git.cloud.tankflow.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.CommonTreeNode;
import com.git.cloud.common.model.ZtreeIconEnum;
import com.git.cloud.taglib.util.Internation;
import com.git.cloud.tankflow.dao.IBusinessDesignerDao;
import com.git.cloud.tankflow.service.IBusinessDesignerService;
import com.git.cloud.workflow.model.po.TemplateTypePo;

public class BusinessDesignerServiceImpl implements IBusinessDesignerService {


	private IBusinessDesignerDao desinerDao;
	
	/**
	 * 获取模板树
	 */
	@Override
	public List<CommonTreeNode> getTemplateTree() throws RollbackableBizException{
//		try {
//			return desinerDao.findAll("getTemplateTree");
//		} catch (RollbackableBizException e) {
//			logger.error("异常exception",e);
//			return null;
//		}
		List<CommonTreeNode> result_json_nodes = new ArrayList<CommonTreeNode>();		
		
		CommonTreeNode rootNode = new CommonTreeNode();
		rootNode.setId("-1");
		rootNode.setName(Internation.language("java_process_tree_title"));
		rootNode.setOpen(true);
		rootNode.setParent(true);
		rootNode.setFirstExpand(true);
		rootNode.setBizType("mb");
				
		Map<String,Object> param_map = new HashMap<String,Object>();	
		param_map.put("typeLevel", 1);
		param_map.put("parentId", "0");
		
		
		List<TemplateTypePo> tts = desinerDao.findListByParam("getTemplateTree",param_map);
		for (TemplateTypePo tt: tts) {
			CommonTreeNode ttNode = new CommonTreeNode();
			ttNode.setId(tt.getTypeId());
			ttNode.setName(tt.getTypeName());
			ttNode.setOpen(true);
			ttNode.setParent(true);
			ttNode.setFirstExpand(true);
			ttNode.setIcon(ZtreeIconEnum.WORKFLOWNODE2.getIcon());
			//rootNode.addChildNode(ttNode);
			
//			if(ttNode.getId().equals("2")){
				Map<String,Object> param_map1 = new HashMap<String,Object>();	
				param_map1.put("typeLevel", "2");
				param_map1.put("parentId", ttNode.getId());
				List<TemplateTypePo> tts1 = desinerDao.findListByParam("getTemplateTree",param_map1);
				for (TemplateTypePo tt1: tts1) {								
					CommonTreeNode tt1Node = new CommonTreeNode();
					tt1Node.setId(tt1.getTypeId());
					tt1Node.setName(tt1.getTypeName());
					tt1Node.setOpen(false);
					tt1Node.setParent(true);
					tt1Node.setFirstExpand(true);
					tt1Node.setBizType("tt");			
					tt1Node.setIcon(ZtreeIconEnum.WORKFLOWNODE.getIcon());
					//ttNode.addChildNode(tt1Node);
					result_json_nodes.add(tt1Node);
				}
//			}
		}
		
		
		return result_json_nodes;
	}

	/**
	 * 通过类型获取模板
	 */
	@Override
	public List<CommonTreeNode> getTemplateByType(String id) throws SQLException {
		
		List<CommonTreeNode> nodes =desinerDao.getTemplateByType(id);
		for (CommonTreeNode tt1Node : nodes) {
			tt1Node.setOpen(false);
			tt1Node.setParent(false);
			tt1Node.setFirstExpand(true);
			tt1Node.setBizType("lc");			
			tt1Node.setIcon(ZtreeIconEnum.WORKFLOW.getIcon());			
		}
		return nodes;
	}
	public IBusinessDesignerDao getDesinerDao() {
		return desinerDao;
	}

	public void setDesinerDao(IBusinessDesignerDao desinerDao) {
		this.desinerDao = desinerDao;
	}
	
	
	

}
