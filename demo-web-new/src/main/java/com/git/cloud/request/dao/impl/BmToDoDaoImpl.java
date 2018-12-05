package com.git.cloud.request.dao.impl;

import java.sql.Timestamp;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.dao.IBmToDoDao;
import com.git.cloud.request.model.po.BmToDoPo;

/**
 * 服务申请待办数据层实现类
 * @ClassName:BmToDoDaoImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-9-30 上午10:26:15
 */
public class BmToDoDaoImpl extends CommonDAOImpl implements IBmToDoDao {

	public void insertBmToDo(BmToDoPo bmToDo) throws RollbackableBizException {
		this.save("insertBmToDo", bmToDo);
	}
	
	public void updateBmToDoStatus(String todoId, String todoStatus) throws RollbackableBizException {
		BmToDoPo bmToDo = new BmToDoPo();
		bmToDo.setTodoId(todoId);
		bmToDo.setTodoStatus(todoStatus);
		bmToDo.setDealTime((new Timestamp(System.currentTimeMillis())));
		this.update("updateBmToDoStatus", bmToDo);
	}
	
	public void updateBmToDoStatusBySrId(String srId) throws RollbackableBizException {
		BmToDoPo bmToDo = new BmToDoPo();
		bmToDo.setSrId(srId);
		bmToDo.setDealTime((new Timestamp(System.currentTimeMillis())));
		this.update("updateBmToDoStatusBySrId", bmToDo);
	}
	
	public BmToDoPo findBmToDoById(String todoId) throws RollbackableBizException {
		return this.findObjectByID("findBmToDoById", todoId);
	}
}
