package com.git.cloud.sys.task;

import com.git.cloud.sys.service.ITimingTaskExecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 扫描定时任务，执行流程中的节点
 *
 * @author w
 * @date 2018/09/19
 *
 */
public class ScanTimingTask {
    private static Logger logger = LoggerFactory.getLogger(ScanTimingTask.class);

    private ITimingTaskExecService timingTaskExecService;

    public void execute()  {
        logger.info("------ScanTimingTask begin-----");
        try {
            // 扫描并执行任务
            timingTaskExecService.updateScanTimingTaskAndExecute();
        }
        catch (Exception ex) {
            logger.error("扫描定时任务错误：" + ex.getMessage(), ex);
        }
        logger.info("------ScanTimingTask end-----");
    }

    public ITimingTaskExecService getTimingTaskExecService() {
        return timingTaskExecService;
    }

    public void setTimingTaskExecService(ITimingTaskExecService timingTaskExecService) {
        this.timingTaskExecService = timingTaskExecService;
    }
}
