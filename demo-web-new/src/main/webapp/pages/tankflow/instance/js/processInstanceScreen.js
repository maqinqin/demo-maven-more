/** 运行中的节点的进度条显示 */
var paramsRunning;
/** 定义流程实例状态 */
var instanceStateId = "";
var timer = "";
var container;
var property={};
var currentNodeId;
var initWidth;
var initHeight;
$(function() {
	document.oncontextmenu= function(event){
	    var event = event || window.event;
	    if(event.button == "2"){ //判断是否是右键
	        var str = $(event.target).parent().parent().parent().parent().attr("id");
	        currentNodeId = str;
	        if(str!==undefined){
	            var nodeObj = container.$nodeData[currentNodeId];
				//"log"仅能查看log，“all”全部操作,running 不显示
				if ("instance" != $('#editType').val()) {

				} else {
					if(nodeObj.type=="start"||nodeObj.type=="end"){
						return false;
					}
					//查询节点编辑代码
					var checkNodeEditCode = "all";
					checkNodeEditRight($("#modelId").val(), $('#instanceId').val(), nodeObj.nodeId, "none")
					.then(function(data) {
						checkNodeEditCode = data;
						if (checkNodeEditCode == "finish" ||checkNodeEditCode == "running"){
							if (nodeObj.type == "sub-process"){
								$('#showSubProcess').css("display", "");
//								$('#reExecuteSignal').hide();
//	 	        				$('#reExecute').hide();
//	 	        				$('#unExecuteSignal').hide();
//	 	        				$('#seeLog').hide();
//	 	        				$('#suspendNode').hide();
								$("#menuContainer").css({
									top: event.pageY+10,
									left: event.pageX+30
								}).show();
							}else if (nodeObj.type == "api" || nodeObj.type == "scr" || nodeObj.type == "cmd"){
	 	        				$('#seeLog').css("display", "");
	 	        				if(checkNodeEditCode == "running"){
									$('#froceTimeoutNode').css("display", "");
	 	        				}
								$("#menuContainer").css({
									top: event.pageY+10,
									left: event.pageX+30
								}).show();
							}
						}
						if (checkNodeEditCode == "all" || checkNodeEditCode == "log") {
							/** 当节点为自动任务节点时API|SCR|CMD.开放菜单 */
							if (nodeObj.type == "api" || nodeObj.type == "scr" || nodeObj.type == "cmd") {
								if (checkNodeEditCode == "log") {
		// 	        				$('#reExecuteSignal').hide();
		// 	        				$('#reExecute').hide();
		// 	        				$('#unExecuteSignal').hide();
									$('#seeLog').css("display", "");
								} else if (checkNodeEditCode == "all") {
									$('#reExecuteSignal').css("display", "");
									$('#reExecute').css("display", "");
									//$('#froceTimeoutNode').css("display", "");
									$('#unExecuteSignal').css("display", "");
									$('#seeLog').css("display", "");
								}
							}else if(nodeObj.type == "task") {
								if(checkNodeEditCode == "all" && instanceStateId != "2") {
									$('#oaNodeOper').css("display", "");
									
//									$('#reExecuteSignal').hide();
//		 	        				$('#reExecute').hide();
//		 	        				$('#unExecuteSignal').hide();
//		 	        				$('#seeLog').hide();
//		 	        				$('#suspendNode').hide();
								}
							}else if (nodeObj.type == "sub-process"){//增加子流程的菜单控制
								$('#showSubProcess').css("display", "");
//								$('#reExecuteSignal').hide();
//	 	        				$('#reExecute').hide();
//	 	        				$('#unExecuteSignal').hide();
//	 	        				$('#seeLog').hide();
//	 	        				$('#suspendNode').hide();
							}
							$("#menuContainer").css({
								top: event.pageY+10,
								left: event.pageX+30
							}).show();
						}
					}, function(error) {
						alert(error);
					});
					
				}
	        }
	        return false;
	    }
	    return true;
	}
	$(document).click(function(){
				$("#menuContainer").hide();
				$('#showSubProcess').hide();
				$('#reExecuteSignal').hide();
 				$('#reExecute').hide();
 				$('#unExecuteSignal').hide();
 				$('#seeLog').hide();
 				$('#suspendNode').hide();
				$('#froceTimeoutNode').hide();
			});
	//将此类的构造函数加入至JQUERY对象中
	jQuery.extend({
		createGooFlow:function(bgDiv,property){
			return new GooFlow(bgDiv,property, "instance", null, null);
		}
	});
 	container=$.createGooFlow($("#container"),property);
 	initWidth = container.$bgDiv.width();
 	initHeight = container.$bgDiv.height();
	getInstanceInfo();
	if ($("#userAdmin").val() == 'userAdmin' && instanceStateId != finish && instanceStateId != forceToExit) {
		$("#modifyParams").css("display", "");
	}
	getModelDiagram();
	getLogDetails();
})

function autoNodeOperate(oper) {
	var nodeObj = container.$nodeData[currentNodeId];
	$("#menuContainer").hide();
	if (oper == 'reExecuteSignal') {
		nodeSingleExe(nodeObj.nodeId, "ES", nodeObj.type.toUpperCase());
	} else if (oper == 'reExecute') {
		nodeSingleExe(nodeObj.nodeId, "EX", nodeObj.type.toUpperCase());
	} else if (oper == 'unExecuteSignal') {
		nodeSingleExec(nodeObj.nodeId, "SI", nodeObj.type.toUpperCase());
	} else if (oper == 'seeLog') {
		showNodeExeLog(nodeObj.nodeId, nodeObj.type.toUpperCase());
	} else if (oper == 'modifyParams') {
		businessCompClick(nodeObj);
	} else if (oper == 'froceTimeoutNode') {
		froceTimeoutNode(nodeObj.nodeId);
	}
}

function initInstance() {
	/** 初始化流程图信息 */
	getModelDiagram();
	getLogDetails();
};

function showTip1(tipContent) {
	jBox.alert(tipContent, "提示", {
		opacity : 0.01,
		top : 300,
		width : 250,
		showType : 'fade',
		icon : 'info',
		showScrolling : true
	});
}

/**
 * 展示流程实例列表
 * 
 * @returns
 */
function getInstanceInfo() {
	$.ajax({
				type : "POST",
				url : ctx
						+ '/workflow/instance/bpmInstance_getBpmInstanceInfoById.action',
				datatype : "json",
				data : {
					"bpmInstanceVo.instanceId" : $("#instanceId").val()
				},
				async : false,
				cache : false,
				success : function(data) {
					// 指定实例状态
					instanceStateId = data.instanceStateId;

					// 指定表单值
					$("#instanceName").val(data.instanceName);
					$("#instanceType").val(data.typeName);
					$("#instanceStateName").val(getStateName(data.instanceStateId));
					$("#instanceStateId").val(data.instanceStateId);
					$("#instanceState").val(data.instanceStateId);
					$("#srCode").val(data.srCode);
					$("#modelId").val(data.modelId);
					$("#wfInstanceId").val(data.wfInstanceId);

					// 操作按钮控制
					changeButtonState(data.instanceStateId);
					hiddenButton($('#editType').val());

					// 根据实例状态控制10秒自动刷新
					if (data.instanceStateId == "1" || data.instanceStateId == "2") {
						// 获取配置的页面刷新时间
						$
								.post(
										ctx+"/workflow/instance/bpmInstance_pageRefreshTime.action",{}, function(data) {
											pageRefreshTime = data.result;
											flushPage();
											timer = setInterval('flushPage()',	pageRefreshTime);
											if (instanceStateId != running && instanceStateId != pause) {
												clearInterval(timer);
											}
										});
					}
				},
				error : function(e) {
					showError("error");
				}
			});
}

/**
 * 刷新后更新流程状态（不带定时器）
 * 
 * @returns
 */
function getInstanceInfo1() {
	$.ajax({
		type : "POST",
		url : ctx
				+ '/workflow/instance/bpmInstance_getBpmInstanceInfoById.action',
		datatype : "json",
		data : {
			"bpmInstanceVo.instanceId" : $("#instanceId").val()
		},
		async : false,
		cache : false,
		success : function(data) {
			// 指定表单值
			$("#instanceName").val(data.instanceName);
			$("#instanceType").val(data.typeName);
			$("#instanceStateName").val(getStateName(data.instanceStateId));
			$("#srCode").val(data.srCode);
			// 指定实例状态
			instanceStateId = data.instanceStateId;
			// 操作按钮控制
			changeButtonState(data.instanceStateId);
			hiddenButton($('#editType').val());
		},
		error : function(e) {
			showError("error");
		}
	});
}

// 获取实例状态编码对应实例状态
function getStateName(value) {
	if (value == 0) {
		return "创建";
	} else if (value == 1) {
		return "运行中";
	} else if (value == 2) {
		return "暂停";
	} else if (value == 3) {
		return "正常结束";
	} else if (value == 4) {
		return "强制结束";
	}
}

function formatTime(ns) {
	if (ns) {
		var d = new Date(parseInt(ns.time + ""));
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		var date = d.getDate();
		var hour = d.getHours();
		var minute = d.getMinutes();
		var second = d.getSeconds();
		return year + "-" + month + "-" + date + " " + hour + ":" + minute
				+ ":" + second;
	}
}

/**
 * 按钮的显示隐藏设置
 */
function changeButtonState(stateId) {
	if (create == stateId) {
		$('#save_Instance').css("display", "none");
		$('#start_instance').css("display", "");
		$('#pause_instance').css("display", "none");
		$('#resume_instance').css("display", "none");
		$('#end_instance').css("display", "none");
		$('#flush_instance').css("display", "none");
		$('#view_report').css("display", "none");
	} else if (running == stateId) {
		$('#save_Instance').css("display", "none");
		$('#start_instance').css("display", "none");
		$('#pause_instance').css("display", "");
		$('#resume_instance').css("display", "none");
		$('#end_instance').css("display", "");
		$('#flush_instance').css("display", "");
		$('#view_report').css("display", "none");
	} else if (pause == stateId) {
		$('#save_Instance').css("display", "none");
		$('#start_instance').css("display", "none");
		$('#pause_instance').css("display", "none");
		$('#resume_instance').css("display", "");
		$('#end_instance').css("display", "");
		$('#flush_instance').css("display", "");
		$('#view_report').css("display", "none");
	} else if (finish == stateId || forceToExit == stateId) {
		$('#save_Instance').css("display", "none");
		$('#start_instance').css("display", "none");
		$('#pause_instance').css("display", "none");
		$('#resume_instance').css("display", "none");
		$('#end_instance').css("display", "none");
		$('#flush_instance').css("display", "none");
		$('#view_report').css("display", "");
	}
}

/**
 * 初始化流程图信息
 */
//libin----2017-7-18
function getModelDiagram() {
	$.ajax({
		type : "POST",
		url : ctx + '/workflow/instance/bpmInstance_getWorkflowJsonData.action',
		datatype : "json",
		data : {
			"instanceId" : $("#instanceId").val(),
			"modelId" : $("#modelId").val()
		},
		success : function(data) {
			$(".GooFlow_work_inner div").remove();
			//修改无闪刷新
			$("#draw_container g").remove();
			container.$lineData={};
			container.loadData(data);
		}
	});
}

/**
 * 无闪刷新流程实例和流程节点状态
 */
function flushPage() {
	if(instanceStateId == running) {
//		$("#container").empty();
//		container=$.createGooFlow($("#container"),property);
		getModelDiagram();
		getLogDetails();
	}
	//如果流程为结束状态并且timer不为空清除定时器
	if (instanceStateId != running && instanceStateId != pause && timer) {
		clearInterval(timer);
	}
	getInstanceInfo1();
}

function flushInstance(){
	return new Promise(function(resolve, reject) {
		flushPage();
	});
}

/**
 * 闪烁刷新流程实例和流程节点状态
 */
function flushPage1() {
	window.location.reload();
}
/**
 * 启动流程
 */
function startInstance() {
	$("#start_instance").attr("disabled", "disabled");
	return new Promise(function(resolve, reject) {
		$.ajax({
			type : "POST",
			url : ctx + '/workflow/instance/bpmInstance_startInstance.action',
			datatype : "json",
			data : {
				"bpmInstanceVo.instanceId" : $("#instanceId").val(),
				"bpmInstanceVo.wfInstanceId" : $("#wfInstanceId").val()
			},
			async : false,
			cache : false,
			success : function(data) {
				getInstanceInfo();
				resolve(data);
			},
			error : function(e) {
				reject(e);
			}
		});
		
	});
}
/**
 * 暂停流程实例
 */
function pauseInstance() {
	$("#pause_instance").attr("disabled", "disabled");
	return new Promise(function(resolve, reject) {
		$.ajax({
			type : "POST",
			url : ctx + '/workflow/instance/bpmInstance_pauseInstance.action',
			datatype : "json",
			data : {
				"bpmInstanceVo.instanceId" : $("#instanceId").val()
			},
			async : false,
			cache : false,
			success : function(data) {
				flushPage();
				$("#pause_instance").css("display", "none");
				$("#resume_instance").css("display", "");
				resolve(data);
			},
			error : function(e) {
				reject(e);
			}
		});
		
	});
	$("#resume_instance").removeAttr("disabled");
}
/**
 * 激活流程实例
 */
function resumeInstance() {
	$("#resume_instance").attr("disabled", "disabled");
	return new Promise(function(resolve, reject) {
		$.ajax({
			type : "POST",
			url : ctx + '/workflow/instance/bpmInstance_resumeInstance.action',
			datatype : "json",
			data : {
				"bpmInstanceVo.instanceId" : $("#instanceId").val()
			},
			async : false,
			cache : false,
			success : function(data) {
				flushPage();
				$("#resume_instance").css("display", "none");
				$("#pause_instance").css("display", "");
				resolve(data);
			},
			error : function(e) {
				reject(e);
			}
		});
		
	});
	$("#pause_instance").removeAttr("disabled");
}

/**
 * 强制结束流程实例
 */
function endIns() {
	$("#end_instance").attr("disabled", "disabled");
	return new Promise(function(resolve, reject) {
		$.ajax({
			type : "POST",
			url : ctx + '/workflow/instance/bpmInstance_endInstance.action',
			datatype : "json",
			data : {
				"bpmInstanceVo.instanceId" : $("#instanceId").val()
			},
			async : false,
			cache : false,
			success : function(data) {
				flushPage();
				resolve(data);
			},
			error : function(e) {
				reject(e);
			}
		});
	});
}

function endInstance() {
	return new Promise(function(resolve, reject) {
		showTip("确认强制结束？", function() {
			endIns().then(function(data) {
				resolve(data);
			}, function(error) {
				reject(error);
			});
		});
	});
}

/** 查看日志 */
function showNodeExeLog(checkNodeId) {
	$.ajax({
		type : "POST",
		url : ctx + "/workflow/instance/bpmInstance_showNodeExeLog.action",
		dataType : "json",
		data : {
			"bpmInstanceNodePo.instanceId" : $('#instanceId').val(),
			"bpmInstanceNodePo.instanceNodeId" : checkNodeId
		},
		async : false,
		success : function(data) {
			$("#logInfo").val(data);
			$("#nodeLog_div").dialog({
				autoOpen : true,
				modal : false,
				width : 700,
				height : 350
			});
			$("#nodeLog_div").dialog("option", "title", "执行日志");
		}
	});
}

/**
 * 强制节点超时
 * @param nodeId
 * @returns
 */
function froceTimeoutNode(wfNodeId) {
	//确认
	showTip('请确认是否将此节点强制为超时状态',function(){
		$.ajax({
			type : "POST",
			url : ctx + "/workflow/instance/bpmInstance_forceTimeoutNode.action",
			data : {
				"singleExeVo.instanceId" :  $('#instanceId').val(),
				"singleExeVo.wfNodeId" : wfNodeId
			},
			async : false,
			dataType : "json",
			cache : false,
			success : function(data) {
				showTip('强制节点超时成功');
				//resolve(data);
			},
			error : function(e) {
				showTip('强制节点超时失败');
			}
		});
	});
}
/**
 * 单步执行及执行标识
 * 
 * @param wfNodeId --
 *            工作流节点ID
 * @param typeCode --
 *            操作类型 ES-执行并流转 | EX-执行不流转 | SI-流转不执行
 * @param nodeType
 *            节点类型
 */
var currentNodeId, currentTypeCode, currentNodeType;
function nodeSingleExe(wfNodeId, typeCode, nodeType) {
	//获取错误信息列表，如果有列出列表，没有则直接执行
	$.ajax({
		url : ctx + "/workflow/instance/getAutoNodeDetail.action",
		data:{
			"instanceId" : $('#instanceId').val(),
			"nodeId" : wfNodeId,
		},
		async:false,
		dataType:"json",
	    success: function(data) {
	    	var nodeDetails = data;
	    	//结果集有数据显示数据列表，否则直接调用节点执行操作
	    	if(nodeDetails && nodeDetails.length > 0){
	    		//清空之前结果
	    		$("#nodeDetail_tbody").html("");
	    		//拼装结果集
	    		var inner = '';
	    		for(var i = 0; i < nodeDetails.length; i++){
	    			var detail = nodeDetails[i];
	    			var result = (detail.resultStatus == 2) ? "失败":"成功";
	    			//inner += '<tr align="center"><td>'+detail.deviceId+'</td>'+
	    			inner += '<tr align="center">'+
	    					 '<td>'+detail.deviceName+'</td>'+
	    					 '<td>'+result+'</td>'+
	    					 '<td style="word-break:break-all;">'+detail.resultMesg+'</td></tr>';
	    		}
	    		//添加结果集
	    		$("#nodeDetail_tbody").append(inner);
	    		//弹出窗口
	    		$("#nodeDetails_div").dialog({
		    		autoOpen : true,
		    		modal : true,
		    		width : 700
		    	});
		    	$("#nodeDetails_div").dialog("option", "title", "执行异常列表");
		    	//保存当前参数
		    	currentNodeId = wfNodeId;
		    	currentTypeCode = typeCode;
		    	currentNodeType = nodeType;
		    	
	    	}else{
	    		nodeSingleExec(wfNodeId,typeCode,nodeType);
	    	}
	    }
	});
	
	//nodeSingleExec(wfNodeId, typeCode, nodeType);
	flushPage();// wmy，点击图边上方菜单中的选项，图标的状态会进行刷新
}

/**
 * 执行错误重做的操作选择
 * 
 * @param operType
 *            点击执行错误operType为'true',点击全部执行operType为'false'
 */
function setAutoNodeDetail(operType) {
	$
			.ajax({
				type : "POST",
				url : ctx
						+ "/workflow/instance/bpmInstance_nodeExecOperation.action",
				dataType : "json",
				data : {
					"bpmInstanceNodePo.instanceId" : $('#instanceId').val(),
					"bpmInstanceNodePo.wfNodeId" : currentNodeId,
					"operType" : operType
				},
				async : false,
				dataType : "json",
				success : function(data) {
					if (data == "success") {
						nodeSingleExec(currentNodeId, currentTypeCode,currentNodeType);
					} else {
						showTip1("执行失败！");
					}
				},
				error : function(e) {
					showError("error");
				}
			});
	$("#nodeDetails_div").dialog("close");
}

function nodeSingleExec(wfNodeId, typeCode, nodeType) {
	return new Promise(function(resolve, reject) {
		$.ajax({
			url : ctx + "/workflow/instance/bpmInstance_singleExec.action",
			data : {
				"singleExeVo.instanceId" : $('#instanceId').val(),
				"singleExeVo.wfNodeId" : wfNodeId,
				"singleExeVo.typeCode" : typeCode,
				"singleExeVo.nodeType" : nodeType
			},
			async : false,
			dataType : "json",
			success : function(data) {
				resolve(data);
			},
			error : function(e) {
				reject(e);
			}
		});
		
	});
}

function closeErrorRedoWindow() {
	if ($("#nodeDetails_div").css("display") != "none")
		$("#nodeDetails_div").dialog("close");
}

/**
 * 点击开始节点的回调函数
 */
function startStateClick() {
	var param = "/pages/workflow/formPage/globalParamsForm.jsp?"
			+ "state=instance&processInstanceId="
			+ $('#processInstanceId').val();
	$('#globalParamsFrame').attr("src", ctx + param);
	$("#startComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#startComponent_div").dialog("option", "title", "定义全局参数");
}

/**
 * 显示选择节点
 */
function decisionClick(id) {
	var param = "/pages/workflow/formPage/decisionForm.jsp?" + "id=" + id
			+ "&state=instance";
	$('#decisionFrame').attr("src", ctx + param);
	$("#decisionComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#decisionComponent_div").dialog("option", "title", "选择");
}

/**
 * 显示分支节点
 */
function forkClick(id) {
	var param = "/pages/workflow/formPage/forkForm.jsp?" + "id=" + id
			+ "&state=instance";
	$('#forkFrame').attr("src", ctx + param);
	$("#forkComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#forkComponent_div").dialog("option", "title", "分支");
}

/**
 * 获取分支节点可选分支
 * 
 * @param id
 * @returns {Array}
 */
function getNextNodes(id) {
	var nodesArr = new Array();
	var cell = graph.getModel().getCell(id);
	if (cell.edges) {
		for ( var i = 0; i < cell.edges.length; i++) {
			if (cell.edges[i].source == cell) {
				var cellTarget = cell.edges[i].target;
				var jsonRequest = {
					"nodeId" : cellTarget.id,
					"nodeName" : cellTarget.getAttribute(NAME),
					"nodeExpression" : ""
				};
				nodesArr[nodesArr.length] = jsonRequest;
			}
		}
	}

	return nodesArr;
}

/**
 * 显示聚合表单
 * 
 * @param id
 *            mxgraph节点id
 * @param nodeId
 *            流程节点id
 */
function joinClick(id, nodeId) {
	var param = "/pages/workflow/formPage/joinForm.jsp?id=" + id + "&nodeId="
			+ nodeId + "&state=instance&processInstanceId="
			+ $('#processInstanceId').val() + "&userAdmin="
			+ $('#userAdmin').val();
	$('#joinFrame').attr("src", ctx + param);
	$("#joinComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700,
		title : "聚合定义"
	});
}

/**
 * 获取可用跳转节点：排除聚合节点本身和开始节点
 * 
 * @param currentNodeId
 * @returns {Array}
 */
function getNavNodeList(currentNodeId) {
	var vertices = graph.getChildVertices(graph.getDefaultParent());
	var vertice = null;
	var nodeList = [];
	for ( var index in vertices) {
		vertice = vertices[index];
		if (vertice.id != currentNodeId
				&& vertice.getAttribute(TYPE) != "start-state") {
			nodeList.push({
				nodeId : vertice.id,
				nodeName : vertice.getAttribute(NAME)
			});
		}
	}
	return nodeList;
}

/**
 * 获取指定cellId节点wfNodeId
 * 
 * @param cellId
 * @returns
 */
function getNavWfNodeId(cellId) {
	var cell = graph.getModel().getCell(cellId);
	return cell.getAttribute(NODE_ID);
}

/**
 * 点击嵌套子流节点
 */
function containerClick(id, nodeId) {
	var param = "/pages/workflow/formPage/containerForm.jsp?" + "id=" + id
			+ "&nodeId=" + nodeId + "&state=instance&processDefinitionID="
			+ $('#processDefinitionID').val();
	$('#containerFrame').attr("src", ctx + param);
	$("#containerComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#containerComponent_div").dialog("option", "title", "组件容器");
}
/**
 * 点击嵌套子流节点
 */
function spClick(id, nodeId) {
	var param = "/pages/workflow/formPage/subprocessForm.jsp?" + "id=" + id
			+ "&nodeId=" + nodeId + "&state=instance&processInstanceId="
			+ $('#processInstanceId').val();
	$('#subprocessFrame').attr("src", ctx + param);
	$("#subprocessComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 830
	});
	$("#subprocessComponent_div").dialog("option", "title", "嵌套子流");
}

/**
 * 点击组件节点的回调函数
 * 
 * @param 节点id
 * @param 节点name
 * @param 节点数据串json
 * @param 组件Id
 * @param 组件表单路径
 */
function businessCompClick(nodeObj) {
	var width,height,page;
	var type = nodeObj.type;
	if(type=="task"){
		page="/pages/workflowNew/designer/formPage/oaForm.jsp";
		width=400;
		height=360;
	}else if(type=="cmd"){
		page="/pages/workflowNew/designer/formPage/commandForm.jsp";
		width=700;
		height=400;
	}else if(type=="scr"){
		page="/pages/workflowNew/designer/formPage/scriptForm.jsp";
		width=850;
		height=360;
	}else if(type=="api"){
		page="/pages/workflowNew/designer/formPage/apiForm.jsp";
		width=850;
		height=360;
	}
	
	var width, height;
	var param = "?state=instance&id=" + nodeObj.id + "&nodeId="
			+ nodeObj.nodeId + "&processInstanceId="
			+ $('#instanceId').val() + "&userAdmin=" + $('#userAdmin').val();
	$('#userComponentFrame').attr("src", ctx + page + param);
	
	$("#userComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : width,
		height : height
	});
	$("#userComponent_div").dialog("option", "title", nodeObj.name);
}
function closeComponentDialog(id) {
	$("#" + id).dialog("close");
}

function getBaseComponentContent(id) {
	var cell = graph.getModel().getCell(id);
	var objTemp = {
		"name" : cell.getAttribute(NAME),
		"json" : cell.getAttribute(DATA),
		"type" : cell.getAttribute(TYPE)
	};
	return objTemp;
}

/**
 * 构建自定义组件数据定义表单
 */
function showUserComponentDialog(title) {
	$("#userComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 750
	});
	$("#userComponent_div").dialog("option", "title", title);
}
/**
 * 判定节点的编辑权限 modelId 模板id instanceId 实例id nodeId 节点id parentNodeId 容器id
 */
function checkNodeEditRight(modelId, instanceId, nodeId, parentNodeId) {
	return new Promise(function(resolve, reject) {
		$.ajax({
			url : ctx + "/workflow/instance/bpmInstance_checkNodeEditRight.action",
			data : {
				"bpmInstancePo.modelId" : modelId,
				"bpmInstancePo.instanceId" : instanceId,
				"bpmInstanceNodePo.wfNodeId" : nodeId,
				"operType" : parentNodeId
			},
			async : false,
			dataType : "json",
			success : function(data) {
				resolve(data);
			},
			error : function(e) {
				reject(e);
			}
		});
		
	});
}

function hiddenButton(editType) {
	if ("view" == editType) {
		$('#save_Instance').css("display", "none");
		$('#start_instance').css("display", "none");
		$('#pause_instance').css("display", "none");
		$('#resume_instance').css("display", "none");
		$('#end_instance').css("display", "none");
		$('#flush_instance').css("display", "");
	}
}

function setFrameHeight(height) {
	$('#userComponentFrame').height(height);// 重新设置iframe高度
	// 重新设置对话框高度
	$("#userComponent_div").dialog({
		"height" : height + 60
	});
}

function closeUserComponent(data) {
	$("#userComponent_div").dialog("close");
	if (data == "success") {
		showTip("保存成功！");
	} else {
		showTip("保存失败！");
	}
}

function viewReport() {
	window.location.href = ctx
			+ "/pages/workflow/instance/instanceReport.jsp?processDefinitionId="
			+ $("#modelId").val() + "&processInstanceId="
			+ $("#instanceId").val();
}
var laseSize = 0;
var closeFlag = true;

function viewInstanceLog() {
	closeFlag = true;
	$("#log-container").dialog({
		autoOpen : true,
		modal : false,//false无遮罩层,true存在遮罩层
		width : 650,
		height : 400
	});
	$('#log-container').bind('dialogclose', function(event) {
		closeFlag = false;
	});
	$("#log-container div").val("");
	getLogResult();
}

function getLogResult() {
	$.ajax({
		type : "POST",
		url : ctx+ '/workflow/instance/bpmInstance_viewInstanceLog.action',
		datatype : "json",
		async : true,
		cache : false,
		data : {
			"laseSize" : laseSize,
			"instanceId" : $("#instanceId").val()
		},
		success : function(data) {
			//得到返回结果后在执行，java会有1分钟等待
			laseSize = data.laseSize;
			var resL = data.resL;
			if(resL) {
				for ( var i = 0; i < resL.length; i++) {
					$("#log-container div").append(resL[i]+"</br>");
					// 滚动条滚动到最低部
					$("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
					
				}
			}
			if (finish != instanceStateId && forceToExit != instanceStateId && closeFlag) {
				getLogResult();
			}
		},
		error : function(e) {
			showTip("error");
		}
	});
}

function returnLastPage() {
	var chosenGroupId = $("#chosenGroupId").val();
	window.location.href = ctx+"/workflow/instance/bpmInstance.action";
}

/**
 * 点击显示日志按钮方法
 */
function showLogDetails() {
	var height = container.$bgDiv.height();
	$("#showlog_div").width(initWidth).height(initHeight*0.25);
	$("#showlog_div").show();
	container.reinitSize(initWidth,initHeight*0.75);
//	document.getElementByIdx("log_instance").disabled=true; 
}

/**
 * 点击隐藏日志按钮方法
 */
function hideLogDetails() {
	$("#showlog_div").hide();
	$("#log_instance").show();
	container.reinitSize(initWidth,initHeight);
}

/**
 * 实时获取流程执行日志
 */
function getLogDetails() {
	$.ajax({
		type : "POST",
		url : ctx+ '/workflow/instance/showLogDetails.action',
		datatype : "json",
		async : true,
		cache : false,
		data : {
			"laseSize" : laseSize,
			"instanceId" : $("#instanceId").val()
		},
		success : function(data) {
			var dataStr = "<table  width='" + initWidth + "' border='1' cellspacing='0'>";
			dataStr += "<tr><td>&nbsp;&nbsp;&nbsp;</td><td>节点名称</td><td>执行策略</td><td>开始时间</td><td>结束时间</td><td>执行状态</td><td>详情</td></tr>";
			var jsonData = data;
			$.each(jsonData , function(i , item){
				var beginTime = formatterTime(jsonData[i].beginTime);
				var endTime = formatterTime(jsonData[i].endTime);
				var execNodeStatus = jsonData[i].resultStatus;
				if(endTime == null || endTime == "") {
					execNodeStatus = "正在执行";
				}
				dataStr += "<tr><td>&nbsp;&nbsp;&nbsp;</td><td>" + jsonData[i].nodeName + "</td><td>" + jsonData[i].moduleName + "</td><td>" + beginTime + "</td><td>" + endTime + "</td><td>" + execNodeStatus + "</td>";
				var logStr = jsonData[i].resultRecordLob.replace(/\n/g , "<br>");
				dataStr += "<td><a href='javascript:void(0); onclick=showExecLogDetail(\"" + logStr + "\",\"" + jsonData[i].nodeName + "\")'>查看日志</a></td>";
				dataStr += "</tr>";
			});
			dataStr += "</table>";
			$("#log_details").html(dataStr);
		},
		error : function(e) {
			showTip("error");
		}
	});
}

/**
 * 显示日志详情
 */
function showExecLogDetail(logDetail , nodeName){
//	logDetail = logDetail.replace(/nextRowStr/g , "<br/>");
	$("#logDetailDiv").html(logDetail);
	$("#logDetailDiv").dialog({
		title : nodeName,
		autoOpen : true,
		modal : true,
		width : 1000,
		height : 500
	});
}

//格式化时间;
function formatterTime(ns) {
	if (ns != null && ns != '') {
		var d = new Date(parseInt(ns.time + ""));
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		if (month <= 9) {
			month = "0" + month;
		}
		var date = d.getDate();
		if (date <= 9) {
			date = "0" + date;
		}
		var hour = d.getHours();
		if (hour <= 9) {
			hour = "0" + hour;
		}
		var minute = d.getMinutes();
		if (minute <= 9) {
			minute = "0" + minute;
		}
		var second = d.getSeconds();
		if(second <= 9) {
			second = "0" + second;
		}
		return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
	} else {
		return "";
	}
}
//查看子流程
function showSubProcess(){
	var nodeObj = container.$nodeData[currentNodeId];
	var nodeID=nodeObj.nodeId;
	$.ajax({
		type : "POST",
		url : ctx+ '/workflow/instance/bpmInstance_viewSubProcess.action',
		datatype : "json",
		async : true,
		cache : false,
		data : {
			"nodeID" : nodeID,
			"instanceId" : $("#instanceId").val()
		},
		success : function(data) {
		  if(data.success){
			 var url=ctx+"/pages/tankflow/instance/processInstanceScreen.jsp?state=instance&instanceId="+data.msg;
			 var openWindow= window.open(url,"流程实例");
//			 parent.document.getElementById("WorkflowIframe").src = ctx+"/pages/tankflow/instance/processInstanceScreen.jsp?state=instance&instanceId="+instanceId;
		  }else{
			  showTip("获取子流程实例id错误！！！");
		  }
		},
		error : function(e) {
//			showTip("error");
		}
	});
}