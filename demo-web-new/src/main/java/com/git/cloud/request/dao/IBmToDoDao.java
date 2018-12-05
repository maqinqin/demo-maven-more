package com.git.cloud.request.dao;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.request.model.po.BmToDoPo;

/**
 * 服务申请待办数据层接口
 * @ClassName:IBmToDoDao
 * @Description:TODO
 * @author sunhailong
 * @date 2014-9-30 上午10:26:15
 */
public interface IBmToDoDao extends ICommonDAO {

	/**
	 * 插入待办信息
	 * @Title: insertBmToDo
	 * @Description: TODO
	 * @field: @param bmToDo
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void insertBmToDo(BmToDoPo bmToDo) throws RollbackableBizException;
	
	/**
	 * 修改待办状态
	 * @Title: updateBmToDo
	 * @Description: TODO
	 * @field: @param todoId
	 * @field: @param todoStatus
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void updateBmToDoStatus(String todoId, String todoStatus) throws RollbackableBizException;
	
	/**
	 * 根据服务申请ID批量更新待办状态为已完成
	 * @param srId
	 * @throws RollbackableBizException
	 */
	public void updateBmToDoStatusBySrId(String srId) throws RollbackableBizException;
	
	/**
	 * 根据Id获取待办对象
	 * @Title: findBmToDoById
	 * @Description: TODO
	 * @field: @param todoId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return BmToDoPo
	 * @throws
	 */
	public BmToDoPo findBmToDoById(String todoId) throws RollbackableBizException;
}
