var jsonData,nodeData;
/*
 * 页面初始化
 */
$(document).ready(function() {
	//添加一个校验方法，可以禁止用户在节点名字段输入逗号“,”
	$.validator.addMethod("forbidComma", function(value) {
		if(value.indexOf(",") != -1) return false;
		return true;
	}, '节点名中不能包含逗号'); 
	
	//设置验证
	$("#OaForm").validate({
		rules: {"NodeName":{"required":true,"maxlength":50,"forbidComma":true},
			"url":{"required":true}},
		messages: {"NodeName":{"required":i18nShow('input_node_name'),"maxlength":i18nShow('maxline4'),"forbidComma":i18nShow('node_notice')},
			"url":{"required":i18nShow('requiredURL')}},
		submitHandler: function() {
			onSave();
		}
	});
	
	if($('#state').val() == "design"){
		//获取节点信息并初始化
		var compData = null;
		try{
			var dNodeId = $('#dNodeId').val();
			compData = parent.getComponentContent(dNodeId);
		}finally{
			if(compData!=null && compData!=""){
				$('#NodeName').val(compData.name);
			}
		}
		jsonData = JSON.parse(compData.json);
		initForm(jsonData);
	}else if($('#state').val() == "definition"){
		typeCode = parent.getTypeCode();
		//获取modelnode信息
		$.ajax({
		    type : "POST",
			url:$('#url').val()+"/baseForm/getOaFormContentAct.action",
			data : {
		    	 "_fw_service_id":"getOaFormContentSrv",
		    	 "modelId":$('#modelId').val(),
		    	 "nodeId":$('#nodeId').val()
		    },
		    async:false,
		    cache:false,
		    success : function(data) {
		    	jsonData = JSON.parse(data);//结果集
		    	
		    	if(jsonData){
		    		initForm(jsonData);
		    		$('#NodeName').val(jsonData.name);
		    	}
		    },
		    error : function(e) {
		      	alert("error");
		    }
		});
	}else{
		//隐藏oaForm
		$("#OaForm").css("display","none");
		$("#todoList").css("display","block");
		
		resetHeight(305);
		
		//获取todoList
		$("#todoListTable").jqGrid({
			url : $('#url').val()+"/baseForm/getBpmTodoListAct.action",
			datatype : "json",
			height : 220,
			width  : 720,
			colNames : [ '代办ID','工单ID','待办事项', '代办状态','处理人', '响应时间', '处理时间' ],
			colModel : [ {
				name : 'todoId',
				index : 'todoId',
				width : 100,
				align : "center",
				hidden: true
			}, {
				name : 'orderId',
				index : 'orderId',
				width : 100,
				align : "center",
				hidden: true
			},{
				name : 'todoName',
				index : 'todoName',
				width : 100,
				align : "center"
			},{
				name : 'todoStatusCode',
				index : 'todoStatusCode',
				width : 100,
				align : "center",
				formatter : function(cellValue, options, rowObject) { 
					return getStatus(cellValue);
				}
			},{
				name : 'currentUserName',
				index : 'currentUserName',
				width : 100,
				align : "center",
			},{
				name : 'responseTime',
				index : 'responseTime',
				width : 100,
				align : "center",
			},{
				name : 'handleTime',
				index : 'handleTime',
				width : 100,
				align : "center",
			}],
			rowNum : defaultRowNum,
			rowList : defaultRowList,
			sortname : 'toDoName',
			sortorder : "asc",
			onCellSelect : function(rowId, iCol,cellcontent, e) {
				//var taskId = $("#todoListTable").getCell(rowId, 0);
				//var srId   = $("#todoListTable").getCell(rowId, 1);
				//var url    = 'http://128.192.138.98:7001/iomp-cloud-web/pages/newselfservice/resourceRequest.jsp?actType=view&taskId='+taskId+'&srId='+srId;
				//window.open(url);
			},
			pager : '#pageNav',
			viewrecords : true,
			jsonReader : defaultJsonReader,
			beforeRequest : function() {
				defaultBeforeRequest("todoListTable","getBpmTodoListSrv", {
					 "nodeId":$('#nodeId').val(),
			    	 "instanceId":$("#instanceId").val()
				});
			}
		}).navGrid('#pageNav', defaultBtnCfg);
	}
});


/**
 * 根据jsonData初始化表单数据
 */
function initForm(jsonData){
	if(jsonData){
		//初始化Url
		$('#path').val(jsonData.url);
			
		//特殊角色
		$('#specialRole').val(jsonData.specialRole);
		
		//初始化用户组行是否显示
		specialRoleChangeHandler();
		
		//如果选择的工作组，初始化工作组值
		if(jsonData.specialRole == "-1"){
			$('#userGroup').val(jsonData.userGroup);
			getOperIds();
			//处理人名称
			//$('#operIds').val(jsonData.operNames);
		}
	}
}


/**
 * 添加userGroup选择项
 */
function buildUserGroupList(userGroupList){
	for ( var i = 0; i < userGroupList.length; i++) {
		var userGroup = userGroupList[i];
		var inner = '<option value="'+userGroup[0]+'">'+userGroup[1]+'</option>';
		$('#userGroup').append(inner);
	}
	//如果jsonData存在，则初始化userGroupList选择项为jsonData.userGroup,同时获取该group操作人
	if(jsonData){
		$('#userGroup').val(jsonData.userGroup);
		getOperIds();
	}
}

/**
 * 添加特殊角色列表
 * @param srList
 */
function buildSpecialRoleList(srList){
	var inner = '';
	
	for ( var i = 0; i < srList.length; i++) {
		var sr = srList[i];
		inner += '<option value="'+sr.dicCode+'">'+sr.dicname+'</option>';
	}
	
	$('#specialRole').append(inner);
	
	if(jsonData && jsonData.specialRole){
		$('#specialRole').val(jsonData.specialRole);
	}
}


/**
 * 根据userGroup选择获取相应操作人
 */
function getOperIds(){
	$.ajax({
	     type : "POST",
	     url : ctx+"/workflow/template/findSysUserByRoleId.action",
	     data : {
	    	 "id":$('#userGroup').val()
	     },
	     async:true,
	     cache:false,
	     success : function(data) {
	    	 //添加操作人项
	    	 //buildUserList(data);
	    	 buildOperIdSelect(data);
	     },
	     error : function(e) {
	      	alert("error");
	     }
	 });
}

function buildOperIdSelect(operIds){
	//清空可能存在的选项
	$('#operId').html("");
	
	//构建新的列表
	var inner = '<option value="-1">'+i18nShow('compute_res_select')+'</option>';
	for(var i = 0 ; i < operIds.length ; i++){
		var operId = operIds[i];
		inner += '<option value="'+operId.userId+'">'+(operId.firstName+operId.lastName)+'</option>'
	}
	$('#operId').append(inner);
	
	//如果存在operId，选中
	if(jsonData != null && jsonData.operId){
		$('#operId').val(jsonData.operId);
	}
}

/**
 * 创建处理人树
 */
function buildUserList(operIds){
	var setting = {
		check: {
			enable: true,
			chkboxType: {"Y":"", "N":""}
		},
		view: {
			dblClickExpand: false,
			selectedMulti: false,
			showIcon:false,
			showLine:false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			onCheck: onCheck
		}
	};
	var zNodes = [];
	
	//准备下拉列表中的内容
	if(operIds != null){
		for(var i = 0 ; i < operIds.length ; i++){
			var operId = operIds[i];
			
			//如果jsonData有值，则遍历jsonData.operIds初始化复选框的选中状态，否则全部初始化为非选中
			if(jsonData && jsonData.operIds && jsonData.operIds.length > 0){
				for ( var j = 0; j < jsonData.operIds.length; j++) {
					if(jsonData.operIds[j] == operId.userId){
						zNodes.push({id:operId.userId,pId:0,name:(operId.firstName+operId.lastName),checked:true});
						break;
					}
					zNodes.push({id:operId.userId,pId:0,name:(operId.firstName+operId.lastName),checked:false});
				}
			}else{
				zNodes.push({id:operId.userId,pId:0,name:(operId.firstName+operId.lastName)});
			}
		}
	}
	
	//初始化操作人树
	$.fn.zTree.init($("#operIdsTree"), setting, zNodes);
	
	//初始化处理人文本框中显示文本
	onCheck();
}

/**
 * 创建者复选框选择处理函数：选中则隐藏用户组行，否则显示用户组行
 */
function specialRoleChangeHandler(){
	if($("#specialRole").val() == "-1"){
		$('.userGroupTr').show();
	}else{
		$('.userGroupTr').hide();
	}
}

/**
 * 复选预处理函数
 */
function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("operIdsTree");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

/**
 * 复选处理函数
 */
function onCheck(e, treeId, treeNode) {
	//显示输入框文本内容
	var zTree = $.fn.zTree.getZTreeObj("operIdsTree");
	var nodes = zTree.getCheckedNodes(true);
	v = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
	}
	if (v.length > 0 ){
		v = v.substring(0, v.length-1);
	}
	$("#operIds").val(v);
}

/**
 * 在指定id输入框下面显示menu
 */
function showMenu() {
	//显示树
	var nodeObj = $("#operIds");
	var nodeOffset = $("#operIds").offset();
	$("#operIdsTree").css({left:nodeOffset.left + "px", top:nodeOffset.top + nodeObj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#operIdsTree").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "operIds" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

/**
 * 保存保单数据
 */
function save(){
	$("#OaForm").submit();
}
function onSave(){
	
	//保存表单
	if($("#state").val() == "design")
	{
		//构建值对象
		var param = {};
		param.specialRole = $('#specialRole').val();
		param.name = $('#NodeName').val();
		param.url = $('#path').val();
		
		//如果创建者没被选中，则获取树中选择的处理人
		if(param.specialRole == "-1"){
			//userGroup必选
			if($('#userGroup').val() == ""){
				 showTip(i18nShow('selectgroup'));
				 return;
			}
			param.operId = $('#operId').val();
			param.userGroup = $('#userGroup').val();
		}
		
		window.parent.onSubmitComponent(
				$('#dNodeId').val(),
				$('#NodeName').val(),
				JSON.stringify(param));
	}else if($("#state").val() == "definition"){
		$.ajax({
		     type : "POST",
		     url : $('#url').val()+"/baseForm/saveOaFormContentAct.action",
		     data : {
		    	 "_fw_service_id":"saveOaFormContentSrv",
		    	 "jsonData":JSON.stringify(param),
		    	 'jsonClass' : 'com.ccb.iomp.cloud.data.vo.workflow.BpmOaFormVo'
		     },
		     async:true,
		     cache:false,
		     success : function(data) {
		    	window.parent.onSubmitComponent(
		    			$('#dNodeId').val(),
		    			$('#NodeName').val());
		     },
		     error : function(e) {
		      	alert("error");
		     }
		 });
	}
	
	cancel();
}


function cancel(){
	window.parent.closeComponentDialog("userComponent_div");
}

//时间格式化
Date.prototype.format = function(format){
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(),	//day
		"h+" : this.getHours(),   //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
		"S" : this.getMilliseconds() //millisecond
	};
	if(/(y+)/.test(format)) {
		format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
		for(var k in o){
			if(new RegExp("("+ k +")").test(format)){
				format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
			}
		}
		return format;
	}
};

function dateFormat(cellValue){
	if(cellValue==""||cellValue==undefined){
		return "";
	}else{
		return new Date(cellValue).format('yyyy-MM-dd hh:mm:ss');
	}
}

function getStatus(cellValue){
	switch(cellValue){
		case "0":	return "未分配"; 
		case "1":	return "已分配"; 
		case "2":	return "已处理"; 
	}
}
/**
 * 重新计算表单高度
 */
function resetHeight(height){
	window.parent.setFrameHeight(height);
}

//关闭窗口
function cancel(){
	window.parent.closeComponentDialog("userComponent_div");
}