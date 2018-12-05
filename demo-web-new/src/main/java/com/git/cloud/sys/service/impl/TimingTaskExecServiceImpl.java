package com.git.cloud.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.sys.dao.ITimingTaskExecDao;
import com.git.cloud.sys.model.po.TimingTaskPo;
import com.git.cloud.sys.service.ITimingTaskExecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * 执行定时任务
 *
 * @author w
 * @date 2018/09/19
 */
public class TimingTaskExecServiceImpl implements ITimingTaskExecService {
    private static Logger logger = LoggerFactory.getLogger(TimingTaskExecServiceImpl.class);

    private ITimingTaskExecDao timingTaskExecDao;

    @Override
    public void updateScanTimingTaskAndExecute() throws RollbackableBizException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<TimingTaskPo> unexecutedTaskList = timingTaskExecDao.queryAllUnexecutedTask();
        for (TimingTaskPo task : unexecutedTaskList) {
            Date execTime = task.getExecTime();
            Date sysTime = new Date();
            if (execTime.compareTo(sysTime) > 0) {
                continue;
            }
            timingTaskExecDao.updateFinishTask(task.getId());
            // 执行方法
            String beanName = task.getTargetBean();
            String methodName = task.getTargetMethod();
            String paramJson = task.getParameterJson();
            JSONObject jObj = JSONObject.parseObject(paramJson);
            String instanceId = jObj.getString("instanceId");
            Long wfNodeId = jObj.getLong("wfNodeId");
            String nodeType = jObj.getString("nodeType");
            String typeCode = jObj.getString("typeCode");

            Object beanObj = WebApplicationManager.getBean(beanName);
            Class<?> beanClazz = AopUtils.getTargetClass(beanObj);
            logger.info("class [ {} ] ", beanClazz);

            Method mh = ReflectionUtils.findMethod(beanObj.getClass(), methodName, String.class, Long.class, String.class, String.class);
            Object obj = ReflectionUtils.invokeMethod(mh, beanObj, instanceId, wfNodeId, typeCode, nodeType);
            logger.info("res [ {} ] ", obj);
        }
    }

    public ITimingTaskExecDao getTimingTaskExecDao() {
        return timingTaskExecDao;
    }

    public void setTimingTaskExecDao(ITimingTaskExecDao timingTaskExecDao) {
        this.timingTaskExecDao = timingTaskExecDao;
    }
}
