package com.git.cloud.tankflow.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.model.CommonTreeNode;
import com.git.cloud.tankflow.dao.IBusinessDesignerDao;

public class BusinessDesignerDaoImpl extends CommonDAOImpl implements IBusinessDesignerDao {

	@Override
	public List<CommonTreeNode> getTemplateByType(String id) throws SQLException {
		return getSqlMapClient().queryForList("getTemplateByTypeWeb", id);
	}
}
