//<<<<<<< HEAD
//window.onload=function(){
//	showInstanceList(); 
//	// 自适应宽度
//	$("#instanceTable").setGridWidth($("#gridMain").width());
//	$(window).resize(function() {
//		$("#instanceTable").setGridWidth($("#gridMain").width());
//	});
//	buildTempLateType();
//}
//
///**
// * 展示流程实例列表
// * @returns
// */
//function showInstanceList() {
//	$("#instanceTable").jqGrid({
//		url : ctx +'/workflow/instance/bpmInstance_getBpmInstanceList.action', // 提交的action地址
//		rownumbers : true, // 是否显示前面的行号
//		datatype : "json", // 返回的数据类型
//		mtype : "post", // 提交方式
//		height : 345,
//		autowidth : true, // 是否自动调整宽度
//		colNames : [ '实例序号', '实例名称', '实例类型','实例状态','工作流ID','工作流实例ID','服务单号','开始时间','结束时间' ],
//		colModel : [ {
//			hidden:true,
//			name : 'instanceId',
//			index : 'instanceId',
//			width : 60,
//			align : "left"
//		}, {
//			name : 'instanceName',
//			index : 'instanceName',
//			width : 100,
//			align : "center",
//			formatter : function(cellValue, options, rowObject) { 
//				return "<a href='#'  onclick=\"showInstance('"+rowObject.instanceId+"','"+rowObject.wfModelId+"')\">"+cellValue+"</a>"
//			}
//		}, {
//			name : 'typeName',
//			index : 'typeName',
//			width : 60,
//			align : "center"
//		}, {
//			name : 'instanceStateId',
//			index : 'instanceStateId',
//			width : 60,
//			align : "center",
//			formatter : function(
//					cellValue,
//					options,
//					rowObject) {
//				return getStateName(cellValue);
//
//			}
//		},{
//			hidden : true,
//			name : 'wfModelId',
//			index : 'wfModelId',
//			width : 100,
//			align : "left"
//		}, {
//			hidden : true,
//			name : 'wfInstanceId',
//			index : 'wfInstanceId',
//			width : 100,
//			align : "center"
//		},{
//			hidden:true,
//			name : 'srCode',
//			index : 'srCode',
//			width : 60,
//			align : "center"
//		},{
//			name : 'startDate',
//			index : 'startDate',
//			width : 100,
//			align : "center",
//			formatter : function(cellValue, options, rowObject) { 
//					return formatTime(cellValue); 
//			}
//		},{
//			name : 'endDate',
//			index : 'endDate',
//			width : 100,
//			align : "center",
//			formatter : function(cellValue, options, rowObject) { 
//					return formatTime(cellValue); 
//			}
//		} ],
//		viewrecords : true,
//		sortname : "bi.START_DATE",
//		sortorder : "desc",
//		rowNum : 10,
//		rowList : [ 5, 10, 15, 20, 30 ],
//		prmNames : {
//			search : "search"
//		},
//		jsonReader : {
//			root : "dataList",
//			records : "record",
//			repeatitems : false
//		},
//		pager : "#pageNav",
//		hidegrid : false
//	});
//	/**
//	* 点击查询按钮
//	*/
//	$("#query_btn").click(function() {
//		search();
//	});
//}
//
///**
// * 刷新查询组件列表
// */
//function search(){
//	$("#instanceTable").jqGrid('setGridParam', {
//		url : ctx +'/workflow/instance/bpmInstance_getBpmInstanceList.action', // 提交的action地址
//		postData : {
//			"instanceName" 	   : $("#tm_instanceName").val(),
//			"typeId"           : $("#tm_instanceType").val(),
//			"instanceStateId"  : $("#tm_instanceStateId").val(),
//			"srCode" 	  	   : $("#tm_srCode").val()
//		}, //发送搜索条件
//		pager : "#pageNav"
//	}).trigger("reloadGrid"); //重新载入
//}
//
//function getStateName(value) {
//	if (value == 0) {
//		return "创建";
//	} else if (value == 1) {
//		return "运行中";
//	} else if (value == 2) {
//		return "暂停";
//	} else if (value == 3) {
//		return "正常结束";
//	} else if (value == 4) {
//		return "强制结束";
//	}
//}
//
//function formatTime(ns){
//	if (ns != null && ns != '') {
//		var d = new Date(parseInt(ns.time + ""));
//		var year = d.getFullYear();
//		var month = d.getMonth() + 1;
//		if (month <= 9) {
//			month = "0" + month;
//		}
//		var date = d.getDate();
//		if (date <= 9) {
//			date = "0" + date;
//		}
//		var hour = d.getHours();
//		if (hour <= 9) {
//			hour = "0" + hour;
//		}
//		var minute = d.getMinutes();
//		if (minute <= 9) {
//			minute = "0" + minute;
//		}
//		return year + "-" + month + "-" + date + " " + hour + ":" + minute;
//	} else {
//		return "";
//	}
//}
//
//function showInstance(instanceId,wfModelId) {
//	window.location.href = ctx+"/workflow/instance/bpmInstance_processInstance.action?state=instance&instanceId="+instanceId;
//}
//
//function setGridSize(tableId, width, subHeight) {
//
//	if (!width)
//		width = 800;
//	if (!subHeight)
//		subHeight = 200;
//
//	$("#" + tableId).setGridHeight($(window).height() - subHeight);
//	$("#" + tableId).setGridWidth(width);
//}
//function pageFulsh(){
//	$("#tm_instanceName").val("");
//	$("#tm_instanceType").val("");
//	$("#tm_instanceStateId").val("");
//	$("#tm_srCode").val("");
//	search();
//}
////初始化模板类型
//function buildTempLateType(){
//	$.ajax({
//	     type : "POST",
//	     url : ctx + "/system/deviceattr/group_findAllDeviceGrp.do",//显示所有；不考虑权限。,
//	     async:true,
//	     cache:false,
//	     success : function(data) {
//	    	/**
//	    	 * 应用选择下拉项初始化
//	    	 */
//	    	$('#tm_instanceType').find("option").remove();
//	    	$("#tm_instanceType").append("<option value='' selected>请选择</option>");
//	    	for(var a in data){
//	    		$("#tm_instanceType").append("<option value='"+data[a].groupId+"' >"+data[a].groupName+"</option>");  
//	    	}
//	    	
//	     },
//	     error : function(e) {
//	      	showTip("error");
//	     }
//	 });
//	
//}
//=======
//>>>>>>> refs/remotes/origin/develop
