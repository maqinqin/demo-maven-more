package com.git.cloud.tankflow.dao;

import java.sql.SQLException;
import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.model.CommonTreeNode;

public interface IBusinessDesignerDao extends ICommonDAO {

	List<CommonTreeNode> getTemplateByType(String id) throws SQLException;
	
}
