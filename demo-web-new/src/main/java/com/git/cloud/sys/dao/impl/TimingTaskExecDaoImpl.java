package com.git.cloud.sys.dao.impl;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.dao.ITimingTaskExecDao;
import com.git.cloud.sys.model.po.TimingTaskPo;

import java.util.List;

/**
 * 扫描定时任务DAO 实现类
 *
 * @author w
 * @date 2018/09/19
 *
 */
public class TimingTaskExecDaoImpl extends CommonDAOImpl implements ITimingTaskExecDao {


    @Override
    public List<TimingTaskPo> queryAllUnexecutedTask() throws RollbackableBizException {
        return super.findAll("queryAllUnexecutedTask");
    }

    @Override
    public void updateFinishTask(String id) throws RollbackableBizException {
        TimingTaskPo po = new TimingTaskPo();
        po.setId(id);
        super.update("updateFinishTask", po);
    }
}
