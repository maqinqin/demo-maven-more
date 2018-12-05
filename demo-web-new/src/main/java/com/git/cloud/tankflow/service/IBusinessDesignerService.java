package com.git.cloud.tankflow.service;

import java.sql.SQLException;
import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.CommonTreeNode;

public interface IBusinessDesignerService {

	List<CommonTreeNode> getTemplateTree() throws RollbackableBizException;

	List<?> getTemplateByType(String id) throws SQLException;

}
