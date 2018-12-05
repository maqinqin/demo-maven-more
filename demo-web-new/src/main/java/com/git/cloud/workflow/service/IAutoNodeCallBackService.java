/**
 * @Title:IAutoNodeCallBackService.java
 * @Package:com.git.cloud.workflow.service
 * @Description:TODO
 * @author libin
 * @date 2014-10-12 下午12:03:24
 * @version V1.0
 */
package com.git.cloud.workflow.service;

import javax.jws.WebService;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;

/**
 * @ClassName:IAutoNodeCallBackService
 * @Description:TODO
 * @author libin
 * @date 2014-10-12 下午12:03:24
 *
 *
 */
@WebService(name="IAutoNodeCallBackService",targetNamespace = "http://com.git.cloud.workflow.service")
public interface IAutoNodeCallBackService extends IService{
	/**
	 * 在执行节点前调用
	 * @param instanceID
	 * @param nodeID
	 * @return 不需执行： Constants.nodeExecuteSuccess："success"
	 *        非自动执行节点： Constants.nodeExecutePause   ："pause"
	 *                      执行中：  Constants.nodeRuning："4"
	 * @throws com.gitcloud.tankflow.common.exception.RollbackableBizException 
	 */
	public String changeNodeState(String instanceID,String nodeID) throws RollbackableBizException, com.gitcloud.tankflow.common.exception.RollbackableBizException;
	/**
	 * 自动执行业务实现接口调用
	 * @param instanceID
	 * @param nodeID
	 * @return 成功：Constants.nodeExecuteSuccess:"success";
	 *                   失败：Constants.nodeExecuteFail:"fail";
	 *                   暂停：Constants.nodeExecutePause:"pause"
	 */
	public String autoNodeCallBack(String instanceID,String nodeID,String signCode);
	/**
	 * 自动执行业务实现接口调用
	 * @param instanceID
	 * @param nodeID
	 * @return 成功：Constants.nodeExecuteSuccess:"success";
	 *                   失败：Constants.nodeExecuteFail:"fail";
	 *                   暂停：Constants.nodeExecutePause:"pause"
	 */
	public String autoNodeCommandScript(String instanceID,String nodeID,String signCode);
	
	/**
	 * 手工执行业务实现接口调用
	 * @param instanceID
	 * @param nodeID
	 * @return 成功：Constants.nodeExecuteSuccess:"success";
	 *                   失败：Constants.nodeExecuteFail:"fail"
	 */
	public String manualNodeCallBack(String instanceID, String nodeID);
	/**
	 * 结束节点状态更新
	 * @param instanceId
	 * 如果正常结束，则更新实例状态为：Constants.finish :3
	 * 如果异常结束，则更新实例状态为：Constants.forceToExit：4
	 */
	public void endNode(String instanceId,Boolean isFinishNormal);
}
