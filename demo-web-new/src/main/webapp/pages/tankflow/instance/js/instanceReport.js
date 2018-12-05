/**
 * 保存报告数据，导出pdf时备用
 */
var insReport;
/**
 * 缓存节点报告数据
 */
var allNodes = {};
/**
 * 实例graph信息，排序节点时备用
 */
var model;

/**
 * 获取实例图形信息，在后面得到实例节点列表信息以后，需要根据图形信息对节点信息列表排序
 */
function getInstanceGraphInfo(processDefinitionId){
	var returnData;
	$.ajax({
		url : ctx + "/workflow/model/model_myh.action?processDefinitionId=" + processDefinitionId,
		async : false,
		dataType : "json",
		success : function(data) {
			returnData = data.diagramXml;
		}
	});
	return returnData;
}

/**
 * 获取实例信息
 */
function getInstanceInfo(processInstanceId){
	var returnData;
	$.ajax({
	     type : "POST",
	     url : ctx +'/workflow/instance/bpmInstance_getInstanceNodes.action',
	     datatype : "json",
	     data : {
	    	 "bpmInstanceVo.instanceId" : processInstanceId
	     },
	     async:false,
	     cache:false,
	     success : function(instanceReport) {
	    	 returnData = instanceReport;
	     },
	     error : function(e) {
	      	showError("error");
	     }
	 });
	return returnData;
}


/**
 * 计算运行时长
 * @param startDate 开始时间
 * @param endDate 结束时间
 */
function getDuration(startDate,endDate){
	if(startDate && endDate) {
		var time = Date.prototype.DateDiff(startDate,endDate);
		var min = parseInt(time / 60);
		var sec = time %60;
		if(min != 0 && sec != 0) {
			return min+"分"+sec+"秒";
		} else if(min != 0 && sec == 0) {
			return min+"分";
		} else if(min == 0 && sec !=0) {
			return sec+"秒";
		}
	}
	return "";
}

Date.prototype.DateDiff = function(dtStartStr,dtEndStr){
	var dtStart = StringToDate(dtStartStr);
	var dtEnd = StringToDate(dtEndStr);
	return parseInt((dtEnd.getTime() - dtStart.getTime())/1000);
};

function StringToDate(DateStr){
	var dStr = DateStr.replace(/-/g,"/");
	var converted = Date.parse(dStr);
	var myDate = new Date(converted);
	if(isNaN(myDate)){
		var arys = DateStr.split('-');
		myDate = new Date(arys[0],--arys[1],arys[2]);
	}
	return myDate;
}

/**
 * 获取节点报告
 * @param nodeId 节点Id
 */
var flag = false;
function getNodeReport(instanceNodeId){
	var returnData;
	$.ajax({
		type : "POST",
		url : ctx +'/workflow/instance/bpmInstance_getNodeReport.action',
		datatype : "json",
		data : {
			"bpmInstanceNodePo.instanceNodeId" : instanceNodeId
		},
		async:false,
		dataType:"json",
	    success: function(node) {
	    	returnData = node;
	    }
	});
	return returnData;
}

/**
 * 获取节点历史记录
 * @param id 实例节点id 
 */
function showNodeHistory(id){
	var returnData;
	$.ajax({
		type : "POST",
		url : ctx +'/workflow/instance/bpmInstance_getTaskControlRecord.action',
		datatype : "json",
		data : {
			"bpmInstanceNodePo.instanceNodeId" : id
		},
		async:false,
		dataType:"json",
	    success: function(records) {
	    	returnData = records;
	    }
	});
	return returnData;
}

function downloadPdf(duration){
	$.ajax({
		type : "POST",
		url : ctx +'/workflow/instance/bpmInstance_buidPDF.action',
		datatype : "json",
		data : {
			"instanceReportVo.instanceInfo.duration" : duration
		},
		async:false,
		dataType:"json",
	    success: function(data) {
	    	window.open(ctx +"/workflow/pdf/WorkFlowPdfDownLoadAction.action?reportName="+encodeURI(data));
	    }
	});
}


//加载xm字符串
function loadXML(xmlString){
    var xmlDoc=null;
    //判断浏览器的类型
    //支持IE浏览器 
    if(!window.DOMParser && window.ActiveXObject){   //window.DOMParser 判断是否是非ie浏览器
        var xmlDomVersions = ['MSXML.2.DOMDocument.6.0','MSXML.2.DOMDocument.3.0','Microsoft.XMLDOM'];
        for(var i=0;i<xmlDomVersions.length;i++){
            try{
                xmlDoc = new ActiveXObject(xmlDomVersions[i]);
                xmlDoc.async = false;
                xmlDoc.loadXML(xmlString); //loadXML方法载入xml字符串
                break;
            }catch(e){
            }
        }
    }
    //支持Mozilla浏览器
    else if(window.DOMParser && document.implementation && document.implementation.createDocument){
        try{
            /* DOMParser 对象解析 XML 文本并返回一个 XML Document 对象。
             * 要使用 DOMParser，使用不带参数的构造函数来实例化它，然后调用其 parseFromString() 方法
             * parseFromString(text, contentType) 参数text:要解析的 XML 标记 参数contentType文本的内容类型
             * 可能是 "text/xml" 、"application/xml" 或 "application/xhtml+xml" 中的一个。注意，不支持 "text/html"。
             */
            domParser = new  DOMParser();
            xmlDoc = domParser.parseFromString(xmlString, 'text/xml');
        }catch(e){
        }
    }
    else{
        return null;
    }

    return xmlDoc;
}

//构造节点递归算法
var targerTemp;
var tempNodes = [];
function getNode(target) {
	var flag = true;
	if(target == endId) {
		flag = false;
		return tempNodes;
	} else {
		//vertices组件节点
		for ( var n = 0; n < vertices.length; n++) {
			vertice = vertices[n];
			if (vertice.id == target) {
				tempNodes.push(vertice);
				break;
			}
		}
		for ( var j = 0; j < outgoingEdges.length; j++) {
			if(flag) {
				edge = outgoingEdges[j];
				if(target == edge.source) {
					targerTemp = edge.target;
					return getNode(targerTemp);
				}
			}
		}
	}
}
function goback() {
	if(flag) {
		javascript:history.go(-2);
	} else {
		javascript:history.go(-1);
	}
}



//----------------------------客户方法--------------------------

$(function(){
	//获取实例图形信息
	getInstGraphInfo();
	//获取实例信息
	getInstInfo();
});

/**
 * 获取实例图形信息，在后面得到实例节点列表信息以后，需要根据图形信息对节点信息列表排序
 */
function getInstGraphInfo(){
	model = getInstanceGraphInfo($('#processDefinitionId').val());
}

/**
 * 获取实例信息
 */
function getInstInfo(){
	var instanceReport = getInstanceInfo($("#processInstanceId").val());
	var instanceInfo = instanceReport.instanceInfo;
	$('#instanceName').html(instanceInfo.instanceName);
	$('#instanceType').html(instanceInfo.typeName);
	$('#serviceReqId').html(instanceInfo.srCode);
	$('#instanceStateId').html(getInstanceState(instanceInfo.instanceStateId));
	$('#startDate').html(instanceInfo.startDate);
	$('#endDate').html(instanceInfo.endDate);
	var duration  = getDuration(instanceInfo.startDate,instanceInfo.endDate)
	$('#duration').html(duration);
	instanceInfo.duration = duration;
	insReport = instanceReport;
	//创建节点列表
	createNodeList(instanceReport.nodes,instanceReport.turnNodeIds);
}

/**
 * 创建节点列表
 * @param nodes 节点集合
 */
function createNodeList(nodes,turnNodeIds){
	//先对nodes排序
	var orderedNodes = orderNodeList(nodes,turnNodeIds);
	insReport.nodes = orderedNodes;
	var inner = "",node;
	for ( var i = 0; i < orderedNodes.length; i++) {
		node = orderedNodes[i];
		if("sub-process"==node.exeTypeCode){
			inner ="<tr height='30'><td width='30'><img src='"+ctx+'/'+node.componentIcon+"' width='16' height='16' /></td>"+
			"<td>"+node.nodeName+"</a></td><td width='30'><div class='LC_coning'><div style='float: left'>"+
			"<img src='"+getNodeStatePdf(node.nodeStateId)+"' width='16' height='16'/></div><div style='float: left'>"+getNodeStateName(node.nodeStateId)+"</div></div></td></tr>";
		}else{
			inner ="<tr height='30'><td width='30'><img src='"+ctx+'/'+node.componentIcon+"' width='16' height='16' /></td>"+
			"<td><a href='#' onclick=\"getNodeRept('"+node.instanceNodeId+"')\">"+node.nodeName+"</a></td><td width='30'><div class='LC_coning'><div style='float: left'>"+
			"<img src='"+getNodeStatePdf(node.nodeStateId)+"' width='16' height='16'/></div><div style='float: left'>"+getNodeStateName(node.nodeStateId)+"</div></div></td></tr>";
		}
		$('#nodeList').append(inner);
		//如果是子流程  增加子流程的记录
		if(node.exeTypeCode=="sub-process"){
			getSubNodeListInfo(node.wfNodeId,i);
		}
	}
}
/**
 * 创建子流程执行日志
 * @param wfNodeId
 * @returns
 */
function getSubNodeListInfo(wfNodeId,index){
	//父流程的实例id
	var processInstanceId=  $("#processInstanceId").val();
	//通过父流程实例id和节点id获取子流程实例id
	$.ajax({
	     type : "POST",
	     url : ctx +'/workflow/instance/bpmInstance_viewSubProcess.action',
	     datatype : "json",
	     data : {
	    	 "instanceId" : $("#processInstanceId").val(),
	    	 "nodeID":wfNodeId
	     },
	     async:false,
	     cache:false,
	     success : function(data) {
	    	 if(data.success){
	    		 var  subInstanceId=data.msg;
	    		 getSubInstanceInfo(subInstanceId,index);
	    	 }else{
	    		 showTip("获取子流程实例id错误！！！");
	    	 }
	     },
	     error : function(e) {
	      	showTip("获取子流程信息错误！！！！");
	     }
	 });
}

/**
 * 获取子流程实例信息
 */
function getSubInstanceInfo(instanceId,index){
	$.ajax({
	     type : "POST",
	     url : ctx +'/workflow/instance/bpmInstance_getInstanceNodes.action',
	     datatype : "json",
	     data : {
	    	 "bpmInstanceVo.instanceId" : instanceId,
	    	 "isSub":true
	     },
	     async:false,
	     cache:false,
	     success : function(instanceReport) {
	    	 var instanceInfo = instanceReport.instanceInfo;
	    	 //insReport.turnNodeIds.splice(index,1,instanceReport.turnNodeIds);
				//创建节点列表
	    	 createSubNodeList(instanceReport.nodes,instanceReport.turnNodeIds,index);
	     },
	     error : function(e) {
	      	showError("error");
	     }
	 });
}
/**
 * 创建子流节点列表
 * @param nodes 节点集合
 */
function createSubNodeList(nodes,turnNodeIds,index){
	//先对nodes排序
	var orderedNodes = orderNodeList(nodes,turnNodeIds);
	//insReport.nodes = orderedNodes;
	
    //insReport.nodes.splice(index,1,orderedNodes);
	var inner = "",
	node;
	for ( var i = 0; i < orderedNodes.length; i++) {
		
		node = orderedNodes[i];
		var componentIcon=node.componentIcon;
		inner +="<tr height='30'><td width='30'  style='padding-left:30px'><img src='"+ctx+'/'+componentIcon+"' width='16' height='16' /></td>"+
		"<td><a href='#' onclick=\"getNodeRept('"+node.instanceNodeId+"')\">"+node.nodeName+"</a></td><td width='30'><div class='LC_coning'><div style='float: left'>"+
		"<img src='"+getNodeStatePdf(node.nodeStateId)+"' width='16' height='16'/></div><div style='float: left'>"+getNodeStateName(node.nodeStateId)+"</div></div></td></tr>";
	
//		inner += '<tr height="30"><td width="30"><img src="'+ctx+"/"+node.componentIcon+'" width="16" height="16" /></td>'+
//			'<td><a href="#" onclick="getNodeReport('+node.instanceNodeId+')">'+node.nodeName+'</a></td><td width="50"><div class="LC_coning"><div style="float: left">'+
//			'<img src="'+getNodeState(node.nodeStateId)+'" width="16" height="16"/></div><div style="float: left">'+getNodeStateName(node.nodeStateId)+'</div></div></td></tr>';
	}
	$('#nodeList').append(inner);
}
/**
 * 按照执行先后顺序排序获取到的节点列表
 * @param nodes 未排序的节点列表
 */
var vertices = new Array();
var outgoingEdges = [];
var endId;
function orderNodeList(nodes,turnNodeIds){
	//返回结果数组
	var orderedNodes = [];
	//待处理节点
	var verticesPending = [];
//	var tempNodes = [];
	if(model != null){
		var xmlDoc=loadXML(model)
		var customNodes = xmlDoc.getElementsByTagName("CustomNode");
		var startNode = xmlDoc.getElementsByTagName("StartNode");
		var endNode = xmlDoc.getElementsByTagName("EndNode");
		var forkNode = xmlDoc.getElementsByTagName("ForkNode");
		var mxCells = xmlDoc.getElementsByTagName("mxCell");
		// 所有组件节点
		for ( var i = 0; i < customNodes.length; i++) {
			var id = customNodes[i].attributes['id'].nodeValue;
			var name = customNodes[i].attributes['Name'].nodeValue;
			var nodeId = customNodes[i].attributes['nodeId'].nodeValue;
			var jsonRequest = {
				"id" : id,
				"name" : name,
				"nodeId" : nodeId
			}
			vertices[vertices.length] = jsonRequest;
		}
		var startId = startNode[0].attributes["id"].nodeValue;
		verticesPending.push(startId);
		endId = endNode[0].attributes["id"].nodeValue
		//从开始节点开始遍历流程并排序
		var temp = {};
		for( var i = 0; i < mxCells.length; i++) {
    		if(mxCells[i].attributes["edge"] != undefined) {
    			var jsonEdges = {
    					"source":mxCells[i].attributes["source"].nodeValue,
    					"target":mxCells[i].attributes["target"].nodeValue
    			}
    			outgoingEdges.push(jsonEdges);
    		}
    	}
		
		//获取执行节点turnNodeIds
		for(var k = 0; k < turnNodeIds.length; k++) {
			var vertice = turnNodeIds[k];
			for ( var i = 0; i < nodes.length; i++) {
				node = nodes[i];
				if(vertice.targetnodeId == node.wfNodeId){
					orderedNodes.push(node);
					break;
				}
			}
			
		}
	}else{
		alert("实例图形信息为空，请刷新重试");
	}
	return orderedNodes;
}

/**
 * 获取节点报告
 * @param nodeId 节点Id
 */
var flag = false;
function getNodeRept(instanceNodeId){
	flag = true;
	//查看有没有缓存数据，有则直接构造，否则去服务器获取数据
	var node = allNodes[instanceNodeId];
	if(node){
		createNodeReport(node);
	}else{
		var node = getNodeReport(instanceNodeId);
    	allNodes[instanceNodeId] = node;
		//创建节点report
    	createNodeReport(node);
	}
}

/**
 * 创建节点报告
 * @param nodes 节点集合
 */
function createNodeReport(node){
	//指定节点参数链接
	var inner = "";
	$('#nodeParamDiv').html("");//清空链接容器
	if(node.serviceParams && node.serviceParams.length > 0){
		if(inner != ""){
			inner += '&nbsp;&nbsp;&nbsp;&nbsp';
		}
		inner += '<a id="serviceParamLink" href="#" onclick="showServiceParams()">+服务参数</a>';
	}
	$('#nodeParamDiv').append(inner);
	$('#nodeReport').html("");//清空nodeReport里面内容，重新构建
	switch(node.exeTypeCode){
		case "API":
			createApiNode(node);
			break;
		case "CMD":
			createCommandNode(node);
			break;
		case "SCR":
			createScriptNode(node);
			break;
	}
}

/**
 * 构造API节点报告
 * @param node
 */
function createApiNode(node){
	//节点信息
	var inner ='<tr><td width="70" class="label">节点名称：</td><td width="240">'+node.nodeName+'</td><td class="label" width="70">服务策略：</td><td>'+node.moduleName+'</td></tr>'+
		'<tr><td width="70" class="label">执行类型：</td><td>'+node.exeTypeCode+'</td><td width="70" class="label">超时时间：</td><td>'+node.outtime+'</td></tr>'+
		'<tr><td class="label">异常处理：</td><td>'+node.exceptionProcess+'</td><td class="label">自动执行：</td><td>'+(node.isAutoNode == "Y"? "是":"否")+'</td></tr>'+
		'<tr><td class="label">开始时间：</td><td>'+node.beginTime+'</td><td class="label">返回码：</td><td>'+node.checkCode+'</td></tr>'+
		'<tr><td class="label">结束时间：</td><td>'+node.endTime+'</td></tr>'+
		'<tr><td class="label">执行时长：</td><td>'+node.duration+'</td></tr>'+
		'<tr><td class="label">执行路径：</td><td colspan="3">'+node.processData+'</td></tr>'+
		'<tr id="processResult"><td class="label">执行结果：</td><td colspan="3"><textarea cols="100" rows="18" style="width: 99%;border: 1px solid #d9d9d9;padding:5px;" readonly="readonly">'+node.processResult+'</textarea></td></tr>';
	//服务参数
	if(node.serviceParams && node.serviceParams.length > 0){
		for ( var i = 0; i < node.serviceParams.length; i++) {
			var param = node.serviceParams[i];
			inner += '<tr class="paramTr" style="display:none;"><td class="label">'+param[0]+'</td><td colspan="3">'+param[1]+'</td></tr>';
		}
	}
	$('#nodeReport').append(inner);
}
/**
 * 构造Command节点报告
 * @param node
 */
function createCommandNode(node){
	//节点信息
	var inner ='<tr><td width="70" class="label">节点名称：</td><td width="240">'+node.nodeName+'</td><td class="label" width="70">服务策略：</td><td>'+node.moduleName+'</td></tr>'+
	'<tr><td width="70" class="label">执行类型：</td><td>'+node.exeTypeCode+'</td><td width="70" class="label">超时时间：</td><td>'+node.outtime+'</td></tr>'+
	'<tr><td class="label">异常处理：</td><td>'+node.exceptionProcess+'</td><td class="label">自动执行：</td><td>'+(node.isAutoNode == "Y"? "是":"否")+'</td></tr>'+
	'<tr><td class="label">开始时间：</td><td>'+node.beginTime+'</td><td class="label">返回码：</td><td>'+node.checkCode+'</td></tr>'+
	'<tr><td class="label">结束时间：</td><td>'+node.endTime+'</td></tr>'+
	'<tr><td class="label">执行时长：</td><td>'+node.duration+'</td></tr>'+
	'<tr><td class="label">执行命令：</td><td colspan="3"><textarea cols="100" rows="3" style="width: 99%" readonly="readonly">'+node.processData+'</textarea></td></tr>'+
	'<tr id="processResult"><td class="label">执行结果：</td><td colspan="3"><textarea cols="100" rows="15"  style="width: 99%;border:1px solid #d9d9d9;padding:5px;" readonly="readonly">'+node.processResult+'</textarea></td></tr>';
	//服务参数
	if(node.serviceParams && node.serviceParams.length > 0){
		for ( var i = 0; i < node.serviceParams.length; i++) {
			var param = node.serviceParams[i];
			inner += '<tr class="paramTr" style="display:none;"><td class="label">'+param[0]+'</td><td colspan="3">'+param[1]+'</td></tr>';
		}
	}
	$('#nodeReport').append(inner);
}

/**
 * 构造script节点报告
 * @param node
 */
function createScriptNode(node){
	//节点信息
	var inner ='<tr><td width="70" class="label">节点名称：</td><td width="240">'+node.nodeName+'</td><td class="label" width="70">服务策略：</td><td>'+node.moduleName+'</td></tr>'+
	'<tr><td width="70" class="label">执行类型：</td><td>'+node.exeTypeCode+'</td><td width="70" class="label">超时时间：</td><td>'+node.outtime+'</td></tr>'+
	'<tr><td class="label">异常处理：</td><td>'+node.exceptionProcess+'</td><td class="label">自动执行：</td><td>'+(node.isAutoNode == "Y"? "是":"否")+'</td></tr>'+
	'<tr><td class="label">开始时间：</td><td>'+node.beginTime+'</td><td class="label">返回码：</td><td>'+node.checkCode+'</td></tr>'+
	'<tr><td class="label">结束时间：</td><td>'+node.endTime+'</td></tr>'+
	'<tr><td class="label">执行时长：</td><td>'+node.duration+'</td></tr>'+
	'<tr><td class="label">执行脚本：</td><td colspan="3">'+node.processData+'</td></tr>'+
//	'<tr><td class="label">执行脚本：</td><td colspan="3"><textarea cols="100" rows="2" style="width: 99%" readonly="readonly">'+node.processData+'</textarea></td></tr>'+
	'<tr id="processResult"><td class="label">执行结果：</td><td colspan="3"><textarea cols="100" rows="18" style="width: 99%;border:1px solid #d9d9d9;padding:5px;" readonly="readonly">'+node.processResult+'</textarea></td></tr>';
	//服务参数
	if(node.serviceParams && node.serviceParams.length > 0){
		for ( var i = 0; i < node.serviceParams.length; i++) {
			var param = node.serviceParams[i];
			inner += '<tr class="paramTr" style="display:none;"><td class="label">'+param[0]+'</td><td colspan="3">'+param[1]+'</td></tr>';
		}
	}
	$('#nodeReport').append(inner);
}

/**
 * 获取节点历史记录
 * @param id 实例节点id 
 */
function showNodeHis(id){
	if($('#hisLink').html() == "+历史记录"){
		$('#hisLink').html("-历史记录");
		if($('.recordHis').length > 0){
			$('.recordHis').css('display','table-row');
		}else{
			var records = showNodeHistory(id);
			createNodeHis(records);
		}
	}else{
		$('#hisLink').html("+历史记录");
		$('.recordHis').css('display','none');
	}
}

/**
 * 创建历史记录
 * @param records
 */
function createNodeHis(records){
	var inner = '',record;
	if(records && records.length > 0){
		for ( var i = 0; i < records.length; i++) {
			record = records[i];
			inner += 
				'<tr class="recordHis"><td class="label">开始时间：</td><td>'+record[0]+'</td><td class="label">结束时间：</td><td>'+record[1]+'</td></tr>'+
				'<tr class="recordHis"><td class="label">执行时长：</td><td colspan="3">'+record[2]+'</td></tr>'+
				'<tr class="recordHis"><td class="label">执行结果：</td><td colspan="3"><textarea cols="100" rows="4" style="width: 99%;border: 1px solid #d9d9d9;padding:5px;" readonly="readonly">'+record[3]+'</textarea></td></tr>';
		}
		//构造完成加入到表格中
		$('#processResult').after(inner);
	}
}


/**
 * 显示或隐藏服务参数
 */
function showServiceParams(){
	if($("#serviceParamLink").html() == "+服务参数"){
		$("#nodeReport .paramTr").css("display","table-row");
		$("#serviceParamLink").html("-服务参数");
	}else{
		$("#nodeReport .paramTr").css("display","none");
		$("#serviceParamLink").html("+服务参数");
	}
}

function dlPdf(){
	downloadPdf(insReport.instanceInfo.duration);
}

