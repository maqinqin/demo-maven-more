//************************************************
/** 运行中的节点的进度条显示 */
var paramsRunning;
/** 定义流程实例状态 */
var instanceStateId = "";
var timer = "";
var container;
var property={};
var currentNodeId;

$(function(){
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
					//查询节点编辑代码
					var checkNodeEditCode = "all";
					checkNodeEditCode = checkNodeEditRight($("#modelId").val(), $('#instanceId').val(), nodeObj.nodeId, "none");
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
								$('#unExecuteSignal').css("display", "");
								$('#seeLog').css("display", "");
							}
						}
						$("#menuContainer").css({
							top: event.pageY+10,
							left: event.pageX+30
						}).show();
					}
				}
	        }
	        return false;
	    }
	    return true;
	}
	$(document).click(function(){
				$("#menuContainer").hide();
			});
	//将此类的构造函数加入至JQUERY对象中
	jQuery.extend({
		createGooFlow:function(bgDiv,property){
			return new GooFlow(bgDiv,property);
		}
	});
 	container=$.createGooFlow($("#container"),property);
 	
	getInstanceInfo();
	getModelDiagram();
	if ($("#userAdmin").val() == 'userAdmin' && instanceStateId != finish && instanceStateId != forceToExit) {
		$("#modifyParams").css("display", "");
	}
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
	}
}

function showTip1(tipContent){
	jBox.alert(tipContent, "tip", {
		opacity: 0.01,
		top:300,
		width: 250, 
		showType: 'fade', 
		icon: 'info',
		showScrolling:true 
	});
}
/**
 * 展示流程实信息
 * @returns
 */
function getInstanceInfo() {
	$.ajax({
	     type : "POST",
	     url : ctx +'/workflow/instance/bpmInstance_getBpmInstanceInfoById.action',
	     datatype : "json",
	     data : {
	    	 "bpmInstanceVo.instanceId" : $("#instanceId").val()
	     },
	     async:false,
	     cache:false,
	     success : function(data) {
	    	 //指定实例状态
//	    	 debugger;
	    	 instanceStateId = data.instanceStateId;
	    	 //指定表单值
	    	 $("#instanceName").val(data.instanceName);
	    	 $("#instanceType").val(data.typeName);
	    	 $("#instanceStateName").val(getStateName(data.instanceStateId));
	    	 $("#instanceStateId").val(data.instanceStateId);
	    	 $("#instanceState").val(data.instanceStateId);
	    	 $("#srCode").val(data.srCode);
	    	 $("#modelId").val(data.modelId);
	    	 $("#wfInstanceId").val(data.wfInstanceId);
	    	 //操作按钮控制
	    	 changeButtonState(data.instanceStateId,data.typeId);
	    	 hiddenButton($('#editType').val());
	    	 
	    	 //根据实例状态控制30秒自动刷新
	    	 if(data.instanceStateId == "1" || data.instanceStateId == "2") {
	    		 //获取配置的页面刷新时间
	    		 $.post(ctx + "/parameter/management/pageRefreshTime.action",{},function(data){
	    			 pageRefreshTime = data.result;
	    			 flushPage();
	    			 timer = setInterval('flushPage()',pageRefreshTime);
		    		 if (instanceStateId != running && instanceStateId != pause) {
		    			 clearInterval(timer);
		    		 }
	    		});
	    	 }
	     },
	     error : function(e) {
	      	showError(i18nShow('tip_instance_fail1'));
	     }
	 });
}

/**
 * 刷新后更新流程状态（不带定时器）
 * @returns
 */
function getInstanceInfo1() {
	$.ajax({
	     type : "POST",
	     url : ctx +'/workflow/instance/bpmInstance_getBpmInstanceInfoById.action',
	     datatype : "json",
	     data : {
	    	 "bpmInstanceVo.instanceId" : $("#instanceId").val()
	     },
	     async:false,
	     cache:false,
	     success : function(data) {
	    	 //指定表单值
	    	 $("#instanceName").val(data.instanceName);
	    	 $("#instanceType").val(data.typeName);
	    	 $("#instanceStateName").val(getStateName(data.instanceStateId));
	    	 $("#srCode").val(data.srCode);
	    	 //操作按钮控制
	    	 changeButtonState(data.instanceStateId,data.typeId);
	    	 hiddenButton($('#editType').val());
	     },
	     error : function(e) {
	      	showError(i18nShow('tip_instance_fail2'));
	     }
	 });
}

//获取实例状态编码对应实例状态
function getStateName(value) {
	if (value == 0) {
		return i18nShow('instance_status0');
	} else if (value == 1) {
		return i18nShow('instance_status1');
	} else if (value == 2) {
		return i18nShow('instance_status2');
	} else if (value == 3) {
		return i18nShow('instance_status3');
	} else if (value == 4) {
		return i18nShow('instance_status4');
	}
}

function formatTime(ns){
	if(ns){
		var d = new Date(parseInt(ns.time+""));
		var year = d.getFullYear();
		var month = d.getMonth()+1;
		var date = d.getDate();
		var hour = d.getHours();
		var minute = d.getMinutes();
		var second = d.getSeconds();
		return year + "-" +month + "-" + date + " " + hour +":"+minute+":"+second;
	}
}

/** 
 * 按钮的显示隐藏设置
 */
function changeButtonState(stateId,typeId){
	$('#start_instance').css("display","none");
	$('#pause_instance').css("display","none");
	$('#resume_instance').css("display","none");
	$('#end_instance').css("display","none");
	$('#flush_instance').css("display","none");
	$('#view_report').css("display","none");
	if(create == stateId){
		$('#start_instance').css("display","");
	}else if(running == stateId){
		$('#pause_instance').css("display","");
		 if(typeId == "3"){//typeId=3，代表审批流程，隐藏强制结束按钮
    		 $('#end_instance').css("display","none");
    	 }else{
    		 $('#end_instance').css("display",""); 
    	 }
		$('#flush_instance').css("display","");
	}else if(pause == stateId){
		$('#resume_instance').css("display","");
		$('#end_instance').css("display","");
		$('#flush_instance').css("display","");
	}else if(finish == stateId || forceToExit == stateId){
		$('#view_report').css("display","");
	}
}

/**
 * 初始化流程图信息
 */
function getModelDiagram() {
	$.ajax({
		url : ctx + "/workflow/instance/bpmInstance_getWorkflowJsonData.action",
		async : false,
		dataType : "json",
		data : {
			"bpmInstanceVo.instanceId" : $("#instanceId").val()
		},
		success : function(data) {
			$(".GooFlow_work_inner div").remove();
			$("#draw_container g").remove();
			container.$lineData={};
			container.loadData(data);
		},
		error : function(e){
			alert(i18nShow('tip_instance_fail3'));
		}
	});
}

/**
 * 无闪刷新流程实例和流程节点状态
 */
function flushPage(){
//	debugger;
	getInstanceInfo1();
	if(instanceStateId == running) {
//		$("#container").empty();
//		container=$.createGooFlow($("#container"),property);
		getModelDiagram();
	}
	//如果流程为结束状态并且timer不为空清除定时器
	if (instanceStateId != running && instanceStateId != pause && timer) {
		clearInterval(timer);
	}
}

/**
 * 闪烁刷新流程实例和流程节点状态
 */
function flushPage1(){
	window.location.reload();
}
/**
 * 启动流程
 */
function startInstance(){
	$("#start_instance").attr("disabled", "disabled");
	$.ajax({
	     type : "POST",
	     url : ctx +'/workflow/instance/bpmInstance_startInstance.action',
	     datatype : "json",
	     data : {
	    	 "bpmInstanceVo.instanceId" : $("#instanceId").val(),
			 "bpmInstanceVo.wfInstanceId" : $("#wfInstanceId").val()
	     },
	     async:false,
	     cache:false,
	     success : function(data) {
	    	 instanceStateId = "1";
	    	 getInstanceInfo();
	    	 showTip1(data);
	     },
	     error : function(e) {
	      	showError("error");
	     }
	 });
}
/**
 * 暂停流程实例
 */
function pauseInstance(){
	$.ajax({
		type : "POST",
	    url : ctx +'/workflow/instance/bpmInstance_pauseInstance.action',
	    datatype : "json",
	    data : {
	    	 "bpmInstanceVo.instanceId" : $("#instanceId").val()
	    },
	    async:false,
	    cache:false,
	    success : function(data) {
	    	instanceStateId = "2";
	    	showTip1(data);
	    	flushPage();
	    	$("#pause_instance").css("display", "none");
	    	$("#resume_instance").css("display", "");
	    },
	    error : function(e) {
	    	showError(i18nShow('tip_instance_fail4'));
	    }
	});
}
/**
 * 激活流程实例
 */
function resumeInstance(){
	$.ajax({
		type : "POST",
	    url : ctx +'/workflow/instance/bpmInstance_resumeInstance.action',
	    datatype : "json",
	    data : {
	    	 "bpmInstanceVo.instanceId" : $("#instanceId").val()
	    },
	    async:false,
	    cache:false,
	    success : function(data) {
	    	instanceStateId = "1";
	    	showTip1(data);
	    	flushPage();
	    	$("#resume_instance").css("display", "none");
	    	$("#pause_instance").css("display", "");
	    },
	    error : function(e) {
	    	showError("error");
	    }
	});
}

/**
 * 强制结束流程实例
 */
function endInstance(){
	showTip(i18nShow('tip_force_over_confirm'),function (){
		$("#end_instance").attr("disabled", "disabled");
		$.ajax({
			type : "POST",
		    url : ctx +'/workflow/instance/bpmInstance_endInstance.action',
		    datatype : "json",
		    data : {
		    	 "bpmInstanceVo.instanceId" : $("#instanceId").val()
		    },
		    async:false,
		    cache:false,
		    success : function(data) {
		    	flushPage();
		    },
		    error : function(e) {
		    	showError("error");
		    }
		});
	})
}

/** 查看日志 */
function showNodeExeLog(checkNodeId){
	$.ajax({
		type : "POST",
		url:ctx+"/workflow/instance/bpmInstance_showNodeExeLog.action",
		dataType:"json",
		data:{
			"bpmInstanceNodePo.instanceId" :$('#instanceId').val(),
			"bpmInstanceNodePo.instanceNodeId" :checkNodeId
		},
		async:false,
	    success: function(data) {
	    	$("#logInfo").val(data);
	    	$("#nodeLog_div").dialog({
	    		autoOpen : true,
	    		modal : true,
	    		width : 700 ,
	    		height: 500
	    	});
	    	$("#nodeLog_div").dialog("option", "title", i18nShow('tip_instance_log'));
	    }
	});
}

/**
 * 单步执行及执行标识
 * @param wfNodeId -- 工作流节点ID
 * @param typeCode -- 操作类型 ES-执行并流转 | EX-执行不流转 | SI-流转不执行
 * @param nodeType 节点类型
 */
var currentNodeId,currentTypeCode,currentNodeType;
function nodeSingleExe(wfNodeId,typeCode,nodeType){
	nodeSingleExec(wfNodeId,typeCode,nodeType);
	flushPage();//wmy，点击图边上方菜单中的选项，图标的状态会进行刷新
}

/**
 * 执行错误重做的操作选择
 * @param operType 点击执行错误operType为'true',点击全部执行operType为'false'
 */
function setAutoNodeDetail(operType){
	$.ajax({
		type : "POST",
		url:ctx+"/workflow/instance/bpmInstance_nodeExecOperation.action",
		dataType:"json",
		data:{
			"bpmInstanceNodePo.instanceId" :$('#instanceId').val(),
			"bpmInstanceNodePo.wfNodeId" :checkNodeId,
			"operType":operType
		},
		async:false,
		dataType:"json",
	    success: function(data) {
	    	if(data == "success"){
	    		nodeSingleExec(currentNodeId,currentTypeCode,currentNodeType);
	    	}else{
	    		showTip1(i18nShow('tip_req_fail'));
	    	}
	    },
	    error:function(e){
	    	showError("error");
	    }});
}

function nodeSingleExec(wfNodeId,typeCode,nodeType){
	$.ajax({
		url:ctx+"/workflow/instance/bpmInstance_singleExec.action",
		data:{
			"singleExeVo.instanceId":$('#instanceId').val(),
			"singleExeVo.wfNodeId":wfNodeId,
			"singleExeVo.typeCode":typeCode,
			"singleExeVo.nodeType":nodeType
		},
		async:false,
		dataType:"json",
	    success: function(data) {
	    	showTip1(data);
	    	//alert(data,closeErrorRedoWindow);
	    }
	});
}

function closeErrorRedoWindow(){
	if($("#nodeDetails_div").css("display")!="none")
		$("#nodeDetails_div").dialog("close");
}

/**
 * 点击开始节点的回调函数
 */
function startStateClick(){
	var param = "/pages/workflow/formPage/globalParamsForm.jsp?" +
			"state=instance&processInstanceId="+$('#processInstanceId').val();
	$('#globalParamsFrame').attr("src",ctx+param);
	$("#startComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#startComponent_div").dialog("option", "title", i18nShow('tip_instance_para'));
}


/**
 * 显示选择节点
 */
function decisionClick(id){
	var param = "/pages/workflow/formPage/decisionForm.jsp?"+"id="+id+"&state=instance";
	$('#decisionFrame').attr("src",ctx+param);
	$("#decisionComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#decisionComponent_div").dialog("option", "title", i18nShow('tip_instance_choose'));
}

/**
 * 显示分支节点
 */
function forkClick(id){
	var param = "/pages/workflow/formPage/forkForm.jsp?"+"id="+id+"&state=instance";
	$('#forkFrame').attr("src",ctx+param);
	$("#forkComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#forkComponent_div").dialog("option", "title", i18nShow('tip_instance_branch'));
}

/**
 * 获取分支节点可选分支
 * @param id
 * @returns {Array}
 */
function getNextNodes(id){
	var nodesArr = new Array();
	var cell = graph.getModel().getCell(id);
	if(cell.edges){
		for(var i = 0; i < cell.edges.length; i++){
			if(cell.edges[i].source == cell){
				var cellTarget = cell.edges[i].target;
				var jsonRequest = {
					"nodeId":cellTarget.id,
					"nodeName":cellTarget.getAttribute(NAME),
					"nodeExpression":""
				};
				nodesArr[nodesArr.length] = jsonRequest;
			}
		}
	}
	
	return nodesArr;
}

/**
 * 显示聚合表单
 * @param id mxgraph节点id
 * @param nodeId 流程节点id
 */
function joinClick(id,nodeId){
	var param = "/pages/workflow/formPage/joinForm.jsp?id="+id+"&nodeId="+nodeId+
		"&state=instance&processInstanceId="+$('#processInstanceId').val()+"&userAdmin="+$('#userAdmin').val();
	$('#joinFrame').attr("src",ctx+param);
	$("#joinComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700,
		title : i18nShow('tip_instance_converge')
	});
}

/**
 * 获取可用跳转节点：排除聚合节点本身和开始节点
 * @param currentNodeId
 * @returns {Array}
 */
function getNavNodeList(currentNodeId){
	var vertices = graph.getChildVertices(graph.getDefaultParent());
	var vertice = null;
	var nodeList = [];
	for (var index in vertices) {
		vertice = vertices[index];
		if(vertice.id != currentNodeId && vertice.getAttribute(TYPE) != "start-state"){
			nodeList.push({nodeId:vertice.id,nodeName:vertice.getAttribute(NAME)});
		}
	}
	return nodeList;
}

/**
 * 获取指定cellId节点wfNodeId
 * @param cellId
 * @returns
 */
function getNavWfNodeId(cellId){
	var cell = graph.getModel().getCell(cellId);
	return cell.getAttribute(NODE_ID);
}

/**
 * 点击嵌套子流节点
 */
function containerClick(id,nodeId){
	var param = "/pages/workflow/formPage/containerForm.jsp?"+"id="+id+"&nodeId="+nodeId+
		"&state=instance&processDefinitionID="+$('#processDefinitionID').val();
	$('#containerFrame').attr("src",ctx+param);
	$("#containerComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#containerComponent_div").dialog("option", "title", i18nShow('tip_instance_assembly'));
}
/**
 * 点击嵌套子流节点
 */
function spClick(id,nodeId){
	var param = "/pages/workflow/formPage/subprocessForm.jsp?"+"id="+id+"&nodeId="+nodeId+
		"&state=instance&processInstanceId="+$('#processInstanceId').val();
	$('#subprocessFrame').attr("src",ctx+param);
	$("#subprocessComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 830
	});
	$("#subprocessComponent_div").dialog("option", "title", i18nShow('tip_instance_prog'));
}

/**
 * 点击组件节点的回调函数
 * @param 节点id
 * @param 节点name
 * @param 节点数据串json
 * @param 组件Id
 * @param 组件表单路径
 */
function businessCompClick(currentNodeVo){
	var width,height,page;
	var type = currentNodeVo.type;
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
	
//	var width,height;
	var param = "?state=instance&id="+currentNodeVo.id+"&nodeId="+currentNodeVo.nodeId+"&processInstanceId="+$('#instanceId').val()+"&userAdmin="+$('#userAdmin').val();
	$('#userComponentFrame').attr("src",ctx+currentNodeVo.formPath+param);
	
	$("#userComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : width,
		height: height
	});
	$("#userComponent_div").dialog("option", "title",currentNodeVo.name);
}
function closeComponentDialog(id){
	$("#"+id).dialog("close");
}

function getBaseComponentContent(id){
	var cell = graph.getModel().getCell(id);
	var objTemp = {
		"name":cell.getAttribute(NAME),
		"json":cell.getAttribute(DATA),
		"type":cell.getAttribute(TYPE)
	};
	return objTemp;
}

/**
 * 构建自定义组件数据定义表单
 */
function showUserComponentDialog(title){
	$("#userComponent_div").dialog({
		autoOpen : true,
		modal : true,
		width : 750
	});
	$("#userComponent_div").dialog("option", "title", title);
}
/** 
 * 判定节点的编辑权限 
 * modelId 模板id
 * instanceId 实例id
 * nodeId 节点id
 * parentNodeId 容器id
 */
function checkNodeEditRight(modelId,instanceId,nodeId,parentNodeId){
	var returnCode = "";
	$.ajax({
		url:ctx+"/workflow/instance/bpmInstance_checkNodeEditRight.action",
		data:{
			"bpmInstancePo.modelId" :modelId,
			"bpmInstancePo.instanceId" :instanceId,
			"bpmInstanceNodePo.wfNodeId" :nodeId,
			"operType":parentNodeId
		},
		async:false,
		dataType:"json",
	    success: function(data) {
	    	returnCode = data;
	    }
	});
	return returnCode;
}

function hiddenButton(editType){
	if("view" == editType){
		$('#save_Instance').css("visibility","hidden");
		$('#start_instance').css("visibility","hidden");
		$('#pause_instance').css("visibility","hidden");
		$('#resume_instance').css("visibility","hidden");
		$('#end_instance').css("visibility","hidden");
		$('#flush_instance').css("visibility","hidden");
	}
}

function setFrameHeight(height){
	$('#userComponentFrame').height(height);//重新设置iframe高度
	//重新设置对话框高度
	$("#userComponent_div").dialog({"height": height+60});
}

function closeUserComponent(data){
	$("#userComponent_div").dialog("close");
	 if(data == "success") {
		 showTip(i18nShow('tip_save_success'));
	 } else {
		 showTip(i18nShow('tip_save_fail'));
	 }
}

function viewReport() {
	window.location.href = ctx+"/pages/workflow/instance/instanceReport.jsp?processDefinitionId="+$("#modelId").val()+"&processInstanceId="+$("#instanceId").val();
}

var ID = 0;
var laseSize = 0;
function viewInstanceLog() {
	$("#log-container").dialog({
		autoOpen : true,
		modal : true,
		width : 650,
		height : 400
	});
	$('#log-container').bind('dialogclose', function(event) {
		clearInterval(ID);
	});
	$("#log-container div").val("");
	getLogResult();
	if (finish != instanceStateId && forceToExit != instanceStateId) {
		ID = setInterval(function (){getLogResult();},61000);
	}
}

function getLogResult() {
	$.ajax({
		type : "POST",
		url : ctx+ '/workflow/instance/bpmInstance_viewInstanceLog.action',
		datatype : "json",
		async : true,
		cache : false,
		data : {
			"bpmInstanceVo.laseSize" : laseSize,
			"bpmInstanceVo.instanceId" : $("#instanceId").val()
		},
		success : function(data) {
			debugger;
			laseSize = data.laseSize;
			var resL = data.resL;
			for ( var i = 0; i < resL.length; i++) {
				$("#log-container div").append(resL[i]+"</br>");
				// 滚动条滚动到最低部
				$("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
				
			}
		},
		error : function(e) {
			showTip("error");
		}
	});

//**************************************************************

//过时方法

///** 通过XML数据初始化graph */
//function initFormXML(xmlData){
//	var graph = getGraph();
//
//	if(graph != null){
//		var mode = $('#editType').val();
//		graph.importTemplate(xmlData,mode);
//	}
//};
///** 
// * 初始化流程状态 
// * var model = graph.getModel();getCell
// * model.getRoot() root
// * model.getChildEdges(root) [edge,.......]
// */
//function initBusinessState(){
//	$.ajax({
//	     type : "POST",
//	     url : ctx +'/workflow/instance/bpmInstance_getNodeListByInstanceId.action',
//	     datatype : "json",
//	     data : {
//	    	 "bpmInstancePo.instanceId" : $("#instanceId").val()
//	     },
//	     async:false,
//	     cache:false,
//	     success: function(data) {
////	    	/** 定义节点属性对象 */
////	    	var nodeAttr = new Array();
//	    	
//	    	/** 解析自动节点 */
//	    	var autoNodes = data[0];
//	    	
//	    	/**流转节点*/
//	    	var turnNodes = data[1];
//	    	var graph = getGraph();
//	    	
//	    	if(graph == null){
//	    		showTip("获取画布对象失败！");
//	    	}
//	    	
//	    	//节点状态更新
//	    	if(autoNodes.length > 0){
//	    		//解析状态图片路径
//	    		for ( var i = 0; i < autoNodes.length; i++) {
//	    			autoNodes[i].stateIcon = getNodeState(autoNodes[i].nodeStateId);
//				}
//	    		graph.updateAutoNodeStatus(autoNodes);
//	    	}
//	    	
//	    	if(turnNodes.length > 0){
//	    		graph.updateTurnStatus(turnNodes);
//	    	}
//	    }
//	});
//}
///** -----------------------------流程图处理---------------------------------- */
///** 画布对象 */
//var graph;
///** 常量 */
//var NAME = 'Name', TYPE = 'Type',DATA = 'Data', NODEID = 'nodeId', PATH = 'Path', CID = 'ComponentId';
///** 初始化画布 */
//function initGraph(container){
//	/** 检查浏览器兼容性 */
//	if (!mxClient.isBrowserSupported()){
//		mxUtils.error('浏览器不支持!', 200, false);
//	}else{
//		/** IE浏览器特殊设置 */
//		if (mxClient.IS_QUIRKS){
//			document.body.style.overflow = 'hidden';
//			new mxDivResizer(container);
//		}
//		/** 新建画布对象 */
//		graph = new mxGraph(container);
//		/** 启用画布左键拖动 */
//		graph.setPanning(true);
//		graph.panningHandler.useLeftButtonForPanning = true;
//		/** 添加自定义右键菜单 */
//		graph.panningHandler.factoryMethod = function(menu, cell, evt){
//			return createPopupMenu(menu, cell, evt);
//		};
//		/** 禁用画布编辑 */
//		graph.setEnabled(false);
//		
//		/** 启用提示信息 */
//		graph.setTooltips(false);
//		/** 如果是分组则可以折叠 */
//		graph.isCellFoldable = function(cell){
//			return false;
//		};
//		/** 转换对象值为标签 */
//		graph.convertValueToString = function(cell){
//			var lbl = mxUtils.isNode(cell.value) ? cell.value.getAttribute(NAME) :  cell.value;
//	        return lbl;
//	    };
//		/** 双击响应 */
//		graph.dblClick = function(event,cell){
//			if(!mxEvent.isConsumed(event) && cell != null){
//				if(mxUtils.isNode(cell.value)){
//					/** 调用回调函数 */
//					if($('#userAdmin').val()=="userAdmin"){
//						clickNode(cell);
//					}
//				}
//			}
//			/** 取消双击更改标签的默认行为 */
//			mxEvent.consume(event);
//		};
//		/** 禁用系统右键菜单 */
//		mxEvent.disableContextMenu(container);
//		/** 设置流程图样式 */
//		configureStylesheet(graph);
//	};
//};
//
///** 设置流程图样式 */
//function configureStylesheet(graph){
//	/** 定义默认节点样式 */
//	var style = new Object();
//	style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_LABEL;
//	style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
//	style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
//	style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
//	style[mxConstants.STYLE_IMAGE_ALIGN] = mxConstants.ALIGN_CENTER;
//	style[mxConstants.STYLE_IMAGE_VERTICAL_ALIGN] = mxConstants.ALIGN_TOP;
//	style[mxConstants.STYLE_SPACING_TOP] = '50';
//	style[mxConstants.STYLE_FONTCOLOR] = '#1d258f';
//	style[mxConstants.STYLE_FONTFAMILY] = '微软雅黑';
//	style[mxConstants.STYLE_FONTSIZE] = '12';
//	style[mxConstants.STYLE_FONTSTYLE] = '1';
//	style[mxConstants.STYLE_ROUNDED] = '1';
//	style[mxConstants.STYLE_IMAGE_WIDTH] = '48';
//	style[mxConstants.STYLE_IMAGE_HEIGHT] = '48';
//	style[mxConstants.STYLE_OPACITY] = '80';
//	graph.getStylesheet().putDefaultVertexStyle(style); 
//
//	style = new Object();
//	style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_SWIMLANE;
//	style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
//	style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
//	style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_BOTTOM;
//	style[mxConstants.STYLE_FILLCOLOR] = '#CDE5F6';
//	style[mxConstants.STYLE_GRADIENTCOLOR] = '#F8FBFE';
//	style[mxConstants.STYLE_STROKECOLOR] = '#C0D3E2';
//	style[mxConstants.STYLE_FONTCOLOR] = '#000000';
//	style[mxConstants.STYLE_ROUNDED] = false;
//	style[mxConstants.STYLE_OPACITY] = '80';
//	style[mxConstants.STYLE_STARTSIZE] = '30';
//	style[mxConstants.STYLE_FONTSIZE] = '16';
//	style[mxConstants.STYLE_FONTSTYLE] = 1;
//	graph.getStylesheet().putCellStyle('group', style);
//	
//	style = graph.getStylesheet().getDefaultEdgeStyle();
//	style[mxConstants.STYLE_LABEL_BACKGROUNDCOLOR] = '#FFFFFF';
//	style[mxConstants.STYLE_STROKEWIDTH] = '2';
//	style[mxConstants.STYLE_ROUNDED] = true;
//	style[mxConstants.STYLE_EDGE] = mxEdgeStyle.ElbowConnector;
//};
///** 
// * 增加运行结果状态图标 
// */
//function initExeResultState(states){
//	try{
//		graph.getModel().beginUpdate();
//		for (var i = 0; i < states.length; i++) {
//			var cell = graph.getModel().getCell(states[i].id);
//			graph.removeCellOverlays(cell);
//			graph.addCellOverlay(cell, new mxCellOverlay(
//					new mxImage(states[i].type, 16, 16),
//					"",mxConstants.ALIGN_RIGHT,mxConstants.ALIGN_TOP,
//					new mxPoint(-20,50)));
//		}
//	}finally{
//		graph.getModel().endUpdate();
//	};
//}
///** 
// * 增加checkbox状态 
// */
//function initExeCheckState(states){
//	var container = document.getElementById("container");
//	for ( var i = 0; i < states.length; i++) {
//		var id = states[i].id;
//		var state = states[i].state == "Y" ? " checked" : "";
//		var disabledState = states[i].disabledState == "Y" ? " disabled" : "";
//		var cell = graph.getModel().getCell(id);
//		var xPos,yPos;
//		if(cell){
//			xPos = cell.geometry.x + 15;
//			yPos = cell.geometry.y + 10;
//			$('<div style="position:absolute;opacity:0.9;left:' + xPos + 'px;top:'+ yPos +'px;"><input id="exe_'+id+'" type="checkbox" '+state+" "+disabledState+' onclick="exeChooseOnClick(\'exe_'+id+'\')" value="'+states[i].nodeId+'"></input</div>')
//				.appendTo(container);
//		}
//	}
//}
///** 
// * 改变节点的选中状态并在数据库表中做标记
// * @param obj - 节点checkbox id
// */
//function exeChooseOnClick(obj){
//	$.ajax({
//		url:$('#url').val()+"/instance/changeInstanceNodeCheckStateAct.action",
//		data:{
//			_fw_service_id : "changeInstanceNodeCheckStateSrv",
//			instanceId : $('#processInstanceId').val(),
//			wfNodeId : $('#'+obj).val(),
//			state:$('#'+obj).prop("checked") ? "Y" :"N"
//		},
//		async:false,
//		dataType:"json",
//	    success: function(data) {
//	    	alert(data);
//	    }
//	});
}
///**
// * 点击节点响应函数，可以根据节点类型选择是否响应以及如何响应
// * @param cell 点击的节点
// */
//function clickNode(cell){
//	switch(cell.value.getAttribute(TYPE)){
//		case "start-state":
//			//startStateClick();
//		break;
//		case "end-state":
//			//alert("功能暂未开放");
//			break;
//		case "decision":
//			//decisionClick(cell.id);
//			break;
//		case "fork":
//			//forkClick(cell.id);
//			break;
//		case "join":
//			//alert("功能暂未开放");
//			break;
//		case "container":
//			//containerClick(cell.id,cell.value.getAttribute(NODEID));
//			break;
//		case "sub-process":
//			//spClick(cell.id,cell.getAttribute(NODEID));
//			break;
//		default : 
//			/** 业务组件回调 */
//			businessCompClick(cell.id,
//					cell.value.getAttribute(NAME),
//					cell.value.getAttribute(NODEID),
//					cell.value.getAttribute(CID),
//					cell.value.getAttribute(PATH),
//					cell.value.getAttribute(TYPE));
//			break;
//	}
//};
