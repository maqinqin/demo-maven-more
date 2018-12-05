package com.git.cloud.sys.service;

import com.git.cloud.common.exception.RollbackableBizException;

import java.lang.reflect.InvocationTargetException;

/**
 * 执行定时任务Service
 *
 * @author w
 * @date 2018/09/19
 *
 */
public interface ITimingTaskExecService {
    /**
     * 扫描并执行定时任务
     */
    void updateScanTimingTaskAndExecute() throws RollbackableBizException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
