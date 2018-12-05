package com.git.cloud.sys.dao;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.model.po.TimingTaskPo;

import java.util.List;

/**
 * 扫描定时任务DAO
 *
 * @author w
 * @date 2018/09/19
 *
 */
public interface ITimingTaskExecDao {
    /**
     * 查询未执行任务
     *
     * @return
     */
    List<TimingTaskPo> queryAllUnexecutedTask() throws RollbackableBizException;

    /**
     * 更新任务状态为完成
     *
     * @param id
     * @throws RollbackableBizException
     */
    void updateFinishTask(String id) throws RollbackableBizException;
}
