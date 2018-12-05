/**
 * @Title:AutoNodeCallBackServiceImpl.java
 * @Package:com.git.cloud.workflow.service.impl
 * @Description:TODO
 * @author libin
 * @date 2014-10-12 下午12:06:20
 * @version V1.0
 */
package com.git.cloud.workflow.service.impl;

import java.sql.Timestamp;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.workflow.service.IAutoNodeCallBackService;
import com.gitcloud.tankflow.dao.IBpmInstanceDao;
import com.gitcloud.tankflow.dao.IBpmInstanceNodeDao;
import com.gitcloud.tankflow.model.po.BpmInstanceNodePo;
import com.gitcloud.tankflow.model.po.BpmInstancePo;
import com.gitcloud.tankflow.util.Constants;
import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;

/**
 * @ClassName:AutoNodeCallBackServiceImpl
 * @Description:TODO
 * @author libin
 * @date 2014-10-12 下午12:06:20
 * 
 * 
 */
@WebService(endpointInterface = "com.git.cloud.workflow.service.IAutoNodeCallBackService", targetNamespace = "http://com.git.cloud.workflow.service")
public class AutoNodeCallBackServiceImpl implements IAutoNodeCallBackService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private IBpmInstanceDao bpmInstanceDao;
	private IBpmInstanceNodeDao bpmInstanceNodeDao;
	
	public IBpmInstanceDao getBpmInstanceDao() {
		return bpmInstanceDao;
	}

	public void setBpmInstanceDao(IBpmInstanceDao bpmInstanceDao) {
		this.bpmInstanceDao = bpmInstanceDao;
	}

	public IBpmInstanceNodeDao getBpmInstanceNodeDao() {
		return bpmInstanceNodeDao;
	}

	public void setBpmInstanceNodeDao(IBpmInstanceNodeDao bpmInstanceNodeDao) {
		this.bpmInstanceNodeDao = bpmInstanceNodeDao;
	}

	private static final Log log = LogFactory.getLog(AutoNodeCallBackServiceImpl.class);

	/**
	 * 在执行节点前调用,判断节点状态并根据是否需要执行更新节点状态
	 * 
	 * @param instanceID
	 * @param nodeID
	 * @return 不需执行： Constants.nodeExecuteSuccess："success" 非自动执行节点：
	 *         Constants.nodeExecutePause ："pause" 执行中： Constants.nodeRuning："4"
	 * @throws RollbackableBizException 
	 * @throws com.gitcloud.tankflow.common.exception.RollbackableBizException 
	 */
	@Override
	public String changeNodeState(String instanceID, String nodeID) throws RollbackableBizException, com.gitcloud.tankflow.common.exception.RollbackableBizException {
		BpmInstancePo instance = bpmInstanceDao.findInstancePoInfoById(instanceID);
		BpmInstanceNodePo nodepo = this.getInstanceNodePo(instanceID, nodeID);
		AutoNodeCallBackServiceImpl.log
				.debug("===========================判定流程节点执行状态开始================================");
		/** 如果节点不存在或节点无状态，返回成功 */
		if ((nodepo == null) || (nodepo.getNodeStateId() == null)) {
			AutoNodeCallBackServiceImpl.log
					.debug("===========================节点不存在.跳过此环节的执行================================");
			return Constants.nodeExecuteSuccess;
		}
		try {
			/** 判断节点是否需要执行，若非，将节点状态改为未运行，直接返回成功 */
			if ("N".equals(nodepo.getIsNeedExecute())) {
				nodepo.setNodeStateId(Constants.nodeNotRun);
				bpmInstanceNodeDao.update("", nodepo);
//				this.bpmInstanceNodeDAO.merge(nodepo);
				AutoNodeCallBackServiceImpl.log
						.debug("===========================此环节不需要执行.直接跳过================================");
				return Constants.nodeExecuteSuccess;
			}
			/** 判断是否自动执行，若非，将节点状态改为正常暂停，返回暂停 */
			if ("N".equals(nodepo.getIsAutoNode())) {
				nodepo.setNodeStateId(Constants.nodePauseNormal);
				bpmInstanceNodeDao.update("", nodepo);
//				this.bpmInstanceNodeDAO.merge(nodepo);
				instance.setInstanceStateId(Constants.pause);
				bpmInstanceDao.updateBpmInstance(instance);
				AutoNodeCallBackServiceImpl.log
						.debug("===========================非自动执行节点.暂停此环节的执行================================");
				return Constants.nodeExecutePause;
			}
			/** 更新节点状态为执行中 */
			nodepo.setNodeStateId(Constants.nodeRuning);
			bpmInstanceNodeDao.update("", nodepo);
//			this.bpmInstanceNodeDAO.merge(nodepo);
			return Constants.nodeRuning.toString();
		} catch (Exception e) {
			logger.error("异常exception",e);
			nodepo.setNodeStateId(Constants.nodeFinishException);
			bpmInstanceNodeDao.update("", nodepo);
//			this.bpmInstanceNodeDAO.merge(nodepo);
			AutoNodeCallBackServiceImpl.log
					.debug("===========================节点异常结束."
							+ e.getMessage()
							+ "================================");
			return Constants.nodeFinishException.toString();
		}

	}

	/**
	 * 判断节点是否需要执行，需要执行，则按照策略对节点上的方法排序分组执行
	 * 
	 * @param instanceID
	 *            流程实例ID
	 * @param nodeID
	 *            流程节点ID
	 * @param signCode
	 *            状态返回码
	 * @return
	 */
	@Override
	public String autoNodeCallBack(String instanceID, String nodeID, String signCode) {
		return "";
	}

	/**
	 * 根据实例ID及流程模板节点ID获取实例节点对象
	 * 
	 * @param instanceID
	 *            ：实例ID
	 * @param nodeID
	 *            ：流程模板节点ID
	 * @return 实例节点对象
	 */
	private BpmInstanceNodePo getInstanceNodePo(String instanceID, String nodeID) {
		return null;
	}

	/**
	 * 根据节点异常处理规则返回结果
	 * 
	 * @param nodepo
	 * @return 异常强制结束：设置节点状态为Constants.nodeFinishException：1
	 *         设置实例状态为Constants.nodeExecuteFail："fail"
	 *         忽略异常：设置节点状态为Constants.nodeFinishException：1
	 *         设置实例状态为Constants.nodeExecuteSuccess："success"
	 *         异常暂停：设置节点状态为Constants.nodePauseException：3
	 *         设置实例状态为Constants.nodeExecuteFail："pause"
	 * @throws RollbackableBizException 
	 * @throws com.gitcloud.tankflow.common.exception.RollbackableBizException 
	 */
	@SuppressWarnings("unused")
	private String getExceptionResult(BpmInstanceNodePo nodepo) throws RollbackableBizException, com.gitcloud.tankflow.common.exception.RollbackableBizException {
		String result = "";
		if (Constants.exceptionForceEnd.equals(nodepo.getExceptionCode())) {
			nodepo.setNodeStateId(Constants.nodeFinishException);
			result = Constants.nodeExecuteFail;
		} else if (Constants.exceptionIgnore.equals(nodepo.getExceptionCode())) {
			nodepo.setNodeStateId(Constants.nodeFinishException);
			result = Constants.nodeExecuteSuccess;
		} else if (Constants.exceptionPause.equals(nodepo.getExceptionCode())) {
			nodepo.setNodeStateId(Constants.nodePauseException);
			result = Constants.nodeExecutePause;
		}
		bpmInstanceNodeDao.update("", nodepo);
//		this.bpmInstanceNodeDAO.merge(nodepo);
		return result;
	}

	/**
	 * 结束节点状态更新
	 * 
	 * @param instanceId
	 *            如果正常结束，则更新实例状态为：Constants.finish :3
	 *            如果异常结束，则更新实例状态为：Constants.forceToExit：4
	 */
	@Override
	public void endNode(String instanceId, Boolean isFinishNormal) {
		try {
			BpmInstancePo bip = bpmInstanceDao.findInstancePoInfoById(instanceId);
			bip.setEndDate(new Timestamp(System.currentTimeMillis()));
			if (isFinishNormal) {
				bip.setInstanceStateId(Constants.finish);
			} else {
				bip.setInstanceStateId(Constants.forceToExit);
			}
			bpmInstanceDao.updateBpmInstance(bip);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
	}

	/**
	 * 手工执行业务实现接口调用
	 * 
	 * @param instanceID
	 * @param nodeID
	 * @return 成功：Constants.nodeExecuteSuccess:"success";
	 *         失败：Constants.nodeExecuteFail:"fail"
	 * 
	 * @see com.ccb.iomp.workflow.service.inf.AutoNodeCallBackService#manualNodeCallBack(long,
	 *      long)
	 */
	@Override
	public String manualNodeCallBack(String instanceID, String nodeID) {
		return "";
	}

	/**
	 * 执行命令及脚本
	 * 
	 * @param instanceID
	 *            流程实例ID
	 * @param nodeID
	 *            流程节点ID
	 * @param signCode
	 *            状态返回码
	 */
	@Override
	public String autoNodeCommandScript(String instanceID, String nodeID,
			String signCode) {
		return "";
	}

	/**
	 * 单步执行命令或脚本
	 * 
	 * @param instanceId
	 * @param nodeId
	 * @return
	 */
	
	public String manualNodeCommandCallBack(String instanceId, String nodeId) {
		return "";
	}
}
