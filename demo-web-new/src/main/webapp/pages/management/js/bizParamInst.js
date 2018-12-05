$(function() {
	initBizParamInstList();//初始列表
	// 自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
		$("#gridTable").setGridHeight(heightTotal() + 60);
	});
	
	
	$("#addBizParamInstForm").validate({
		rules: { 
			addkey:'required'
		},
		messages: {
			addkey: {required:i18nShow('validate_data_required')}
		},
		submitHandler: function() {
			saveAddValue();
		}
	});
	
});

//修改创建时间格式的方法
function formatTime(ns){
	if(ns){
		var date = new Date(parseInt(ns));
		
		Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
        h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
		
       return Y+M+D+h+m+s;
	}
}

function initBizParamInstList() {
	$("#gridTable").jqGrid().GridUnload("gridTable");
	$("#gridTable").jqGrid({
		url : ctx+"/parameter/management/getAllBizParamInst.action",
		rownumbers : true,
		datatype : "json",
		mtype : "post",
		multiselect : false,
		postData : {},
		height : heightTotal() + 60,
		autowidth : true,
		colModel : [{
			name : "id",
			index : "id",
			label : "主键ID",
			width : 10,
			sortable : true,
			align : 'left',
			hidden : true
		},{
			name : "deviceId",
			index : "deviceId",
			label : "设备id",
			width : 20,
			sortable : true,
			align : 'left',
			hidden:true
		},
		{
			name : "deviceName",
			index : "deviceName",
			label : i18nShow('sys_flow_device_name'),
			width : 50,
			sortable : true,
			align : 'left'
		},
		{
			name : "flowInstId",
			index : "flowInstId",
			label : "实例ID",
			width : 60,
			sortable : true,
			align : 'left',
			hidden:false
			
		},
		{
			name : "instanceName",
			index : "instanceName",
			label : i18nShow('sys_flow_instance_name'),
			width : 50,
			sortable : true,
			align : 'left'
		},
		{
			name : "startDate",
			index : "startDate",
			label : i18nShow('sys_flow_start_time'),
			width : 40,
			sortable : true,
			align : 'left',
			formatter:function(cellValue,options,rewObject){
				return formatTime(cellValue);
			}
		},
		{
			name : "nodeId",
			index : "nodeId",
			label : "节点ID",
			width : 75,
			sortable : true,
			align : 'left',
			hidden : true
		},
		{
			name : "paramKey",
			index : "paramKey",
			label : "paramKey",
			width : 45,
			sortable : true,
			align : 'left'
		},
		{
			name : "paramValue",
			index : "paramValue",
			label : "paramValue",
			width : 45,
			sortable : true,
			align : 'left'
		},
		{
			name : "option",
			index : "option",
			label : i18nShow('com_operate'),
			width : 30,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				var updateflag = $("#updateFlag").val();
				var addflag = $("#addFlag").val();
				var str="";
				if(updateflag=="1"){
					str += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=updateParamValue('"+options.rowId+"') >"+i18nShow('com_update')+"</a>";
				}
				if(addflag=="1"){
					str += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=addBizParamInst('"+options.rowId+"') >"+i18nShow('sys_flow_add')+"</a>";
				}
				 return str;
			}
		}],
		viewrecords : true,
		sortname : "startDate",
		sortorder : "desc",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "searchList"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#gridPager",
		hidegrid : false
	});
}


//更改paramValue值
function updateParamValue(rowid){
	var rowObject = $("#gridTable").jqGrid("getRowData", rowid);
	title=i18nShow('sys_flow_update');
	$("label.error").remove();
	$("#updateValueDiv").dialog({
			autoOpen : true,
			modal:true,
			height:320,
			width:480,
			title:i18nShow('sys_flow_update'),
			resizable:true
	});
	$("#upvalue").val(rowObject.paramValue);
	$("#hiddenId").val(rowObject.id);
	$("#upkey").val(rowObject.paramKey);
	$("#deviceName").val(rowObject.deviceName);
}


function saveValue(){
	var upvalue = $("#upvalue").val();
	var hiddenId = $("#hiddenId").val();
	showTip(i18nShow('tip_sys_flow_save'),function(){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : ctx+"/parameter/management/updateParamValue.action",  
			data : {"bizParamInstPo.id":hiddenId,"bizParamInstPo.paramValue":upvalue},
			error : function() {//请求失败处理函数  
				closeTip();
				showError(i18nShow('tip_req_fail'));
			},
			success : function(data) { //请求成功后处理函数。     
				showTip(i18nShow('tip_op_success'));
				closeViews('updateValueDiv');
				//重新加载页面
				$("#gridTable").jqGrid().trigger("reloadGrid");
			}
		});
	});
}


function searchList() {
	$("#gridTable").jqGrid('setGridParam', {
		url : ctx+"/parameter/management/getAllBizParamInst.action",
		postData : {"flowInstId":$("#flowInstId").val().trim(),"instanceName":$("#instanceName").val().trim(),"paramKey":$("#paramKey").val().trim(),"deviceName":$("#sedeviceName").val().trim()},
		pager : "#gridPager"
	}).trigger("reloadGrid");
}

function clearAll(){
	$("#flowInstId").val("");
	$("#paramKey").val("");
	$("#instanceName").val("");
	$("#sedeviceName").val("");
}
function closeViews(id){
	$("#"+id).dialog("close");
}

//添加流程实例参数
function addBizParamInst(rowid){
	var rowObject = $("#gridTable").jqGrid("getRowData", rowid);
	$("label.error").remove();
	$("#addBizParamInstDiv").dialog({
			autoOpen : true,
			modal:true,
			height:300,
			width:480,
			title:i18nShow('sys_flow_save'),
			resizable:true
	});
	//页面显示
	$("#addInstanceName").val(rowObject.instanceName);
	$("#addkey").val("");
	$("#addValue").val("");
	//隐藏值，用于保存
	$("#hidNodeId").val(rowObject.nodeId);
	$("#hidFlowInstId").val(rowObject.flowInstId);
	$("#hidDeviceId").val(rowObject.deviceId);
}
//添加参数验证
function addValueValidate(){
$('#addBizParamInstForm').submit();
}

function saveAddValue(){
	var hidNodeId = $("#hidNodeId").val();
	var hidFlowInstId = $("#hidFlowInstId").val();
	var hidDeviceId = $("#hidDeviceId").val();
	var addkey = $("#addkey").val();
	var addValue = $("#addValue").val();
	showTip(i18nShow('tip_sys_flow_save1'),function(){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : ctx+"/parameter/management/addBizParamInst.action",  
			data : {"bizParamInstPo.nodeId":hidNodeId,"bizParamInstPo.deviceId":hidDeviceId,"bizParamInstPo.flowInstId":hidFlowInstId,"bizParamInstPo.paramKey":addkey,"bizParamInstPo.paramValue":addValue},
			error : function() {//请求失败处理函数  
				closeTip();
				showError(i18nShow('tip_req_fail'));
			},
			success : function(data) { //请求成功后处理函数。     
				showTip(i18nShow('tip_save_success'));
				closeViews('addBizParamInstDiv');
				//重新加载页面
				$("#gridTable").jqGrid().trigger("reloadGrid");
			}
		});
	});
}
