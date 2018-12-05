/** 
 * 流程实例状态定义 
 */
/** 创建 */
var create=0;
/** 运行中 */
var running = 1;
/** 暂停 */
var pause = 2;
/** 正常结束 */
var finish=3;
/** 强制结束 */
var forceToExit=4;

/** 
 * 任务执行状态定义 
 */
/** 节点正常结束 */
var nodeFinishNormal = 0;
/** 节点异常结束 */
var nodeFinishException = 1;
/** 节点正常暂停 */
var nodePauseNormal = 2;
/** 节点异常暂停 */
var nodePauseException =3;
/** 节点运行中 */
var nodeRuning = 4;
/** 节点未运行 */
var nodeNotRun = 5;
/** 运行超时 */
var nodeRunOutTime = 6;

function getInstanceState(state){
	if(state == create){
		return "创建";
	}else if(state == running){
		return "运行中";
	}else if(state == pause){
		return "暂停";
	}else if(state == finish){
		return "正常结束";
	}else if(state == forceToExit){
		return "强制结束";
	}
}
function getNodeState(state){
	if(state == nodeFinishNormal){
		return "../../../pages/tankflow/instance/images/nodestate-normalfinish.png";
	}else if(state == nodeFinishException){
		return "../../../pages/tankflow/instance/images/nodestate-exceptionfinish.png";
	}else if(state == nodePauseNormal){
		return "../../../pages/tankflow/instance/images/nodestate-normalpause.png";
	}else if(state == nodePauseException){
		return "../../../pages/tankflow/instance/images/nodestate-exceptionpause.png";
	}else if(state == nodeRuning){
		return "../../../pages/tankflow/instance/images/nodestate-running.swf";
	}else if(state == nodeNotRun){
		return "../../../pages/tankflow/instance/images/nodestate-undo.png";
	}else if(state == nodeRunOutTime){
		return "../../../pages/tankflow/instance/images/nodestate-timeout.png";
	}
}
function getNodeStatePdf(state){
	if(state == nodeFinishNormal){
		return "../../../pages/tankflow/instance/images/nodestate-normalfinish.png";
	}else if(state == nodeFinishException){
		return "../../../pages/tankflow/instance/images/nodestate-exceptionfinish.png";
	}else if(state == nodePauseNormal){
		return "../../../pages/tankflow/instance/images/nodestate-normalpause.png";
	}else if(state == nodePauseException){
		return "../../../pages/tankflow/instance/images/nodestate-exceptionpause.png";
	}else if(state == nodeRuning){
		return "../../../pages/tankflow/instance/images/nodestate-running.swf";
	}else if(state == nodeNotRun){
		return "../../../pages/tankflow/instance/images/nodestate-undo.png";
	}else if(state == nodeRunOutTime){
		return "../../../pages/tankflow/instance/images/nodestate-timeout.png";
	}
}

function getNodeStateName(state){
	if(state == nodeFinishNormal){
		return "正常完成";
	}else if(state == nodeFinishException){
		return "异常完成";
	}else if(state == nodePauseNormal){
		return "正常暂停";
	}else if(state == nodePauseException){
		return "异常暂停";
	}else if(state == nodeRuning){
		return "执行中";
	}else if(state == nodeNotRun){
		return "未执行";
	}else if(state == nodeRunOutTime){
		return "执行超时";
	}
}

function loadProcessInstanceDiagram(url,instanceId){
	window.open(url+"/pages/tankflow/instance/processInstance.jsp?state=instance&processInstanceId=" + instanceId + "&editType=show");
}
