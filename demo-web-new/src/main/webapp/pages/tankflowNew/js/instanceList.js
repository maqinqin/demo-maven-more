$(document).ready(function() {
	showInstanceList(); 
	// 自适应宽度
	$("#instanceTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#instanceTable").setGridWidth($("#gridMain").width());
		$("#instanceTable").setGridHeight(heightTotal() + 40);
	});
});

function clearAll(){
	$("#tm_instanceName").val("");
	$("#tm_srCode").val("");
	$("#tm_instanceStateId").get(0).selectedIndex=0; 
	$("#tm_instanceType").get(0).selectedIndex=0; 
}
/**
 * 展示流程实例列表
 * @returns
 */
function showInstanceList() {
	$("#instanceTable").jqGrid({
		url : ctx +'/workflow/instance/bpmInstance_getBpmInstanceList.action', // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : heightTotal() + 40,
		autowidth : true, // 是否自动调整宽度
		colNames : [ '实例序号', i18nShow('instance_name'), i18nShow('instance_type'),i18nShow('instance_status'),'工作流ID','工作流实例ID',i18nShow('instance_srCode'),i18nShow('instance_start_time'),i18nShow('instance_end_time') ],
		colModel : [ {
			hidden:true,
			name : 'instanceId',
			index : 'instanceId',
			width : 60,
			align : "left"
		}, {
			name : 'instanceName',
			index : 'instanceName',
			width : 100,
			align : "left",
			formatter : function(cellValue, options, rowObject) { 
//				return "<a href='"+ctx+"/pages/workflow/instance/instance.jsp?instanceId="+cellValue+"&wfModelId="+cellValue+"'> "+cellValue+" </a>";
				return "<a href='#' style='color:#18a689; text-decoration : none' onclick=\"showInstance('"+rowObject.instanceId+"','"+rowObject.instanceStateId+"','"+rowObject.modelId+"','"+rowObject.instanceName+"','"+rowObject.typeName+"','"+rowObject.srCode+"')\">"+cellValue+"</a>"
				
			}
		}, {
			name : 'typeName',
			index : 'typeName',
			width : 60,
			align : "left"
		}, {
			name : 'instanceStateId',
			index : 'instanceStateId',
			width : 60,
			align : "left",
			formatter : function(
					cellValue,
					options,
					rowObject) {
				return getStateName(cellValue);

			}
		},{
			hidden : true,
			name : 'wfModelId',
			index : 'wfModelId',
			width : 100,
			align : "left"
		}, {
			hidden : true,
			name : 'wfInstanceId',
			index : 'wfInstanceId',
			width : 100,
			align : "left"
		},{
			name : 'srCode',
			index : 'srCode',
			width : 60,
			align : "left"
		},{
			name : 'startDate',
			index : 'startDate',
			width : 100,
			align : "left",
			formatter : function(cellValue, options, rowObject) { 
					return formatTime(cellValue); 
			}
		},{
			name : 'endDate',
			index : 'endDate',
			width : 100,
			align : "left",
			formatter : function(cellValue, options, rowObject) { 
					return formatTime(cellValue); 
			}
		} ],
		viewrecords : true,
		sortname : "bi.START_DATE",
		sortorder : "desc",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		loadComplete : function(data) {
			pushPaginationParamToHistory(data,"page","rows")
		},
		page: pagin == null || pagin == undefined ? 1 : pagin.page,
		rowNum : pagin == null || pagin == undefined ? 10 : pagin.rows,
		pager : "#pageNav",
		hidegrid : false
	});
	//setGridSize("instanceTable",$("#searchDiv").width() - 1, 200)
	/**
	* 点击查询按钮
	*/
	$("#query_btn").click(function() {
		search();
	});
}

/**
 * 刷新查询组件列表
 */ 
function search(){
	var queryData = {
			"instanceName" 	   : $("#tm_instanceName").val().replace(/(^\s*)|(\s*$)/g, ""),
			"typeId"           : $("#tm_instanceType").val(),
			"instanceStateId"  : $("#tm_instanceStateId").val(),
			"srCode" 	  	   : $("#tm_srCode").val().replace(/(^\s*)|(\s*$)/g, "")
		};
		jqGridReload("instanceTable", queryData);
}

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

//创建时间的方法
function formatTime(ns){
	if(ns){
		var date = new Date(parseInt(ns.time+""));
		
		Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
        h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
		
       return Y+M+D+h+m+s;
	}else{
		return "";
	}
}

function showInstance(instanceId,instanceStatus,modelId,instanceName,typeName,srCode) {
	window.location.href = ctx+"/pages/tankflowNew/processInstance.jsp?state=instance&instanceId="+instanceId+"&instanceStatus="+instanceStatus+"&modelId="+modelId+"&instanceName="+encodeURI(encodeURI(instanceName))+"&typeName="+encodeURI(encodeURI(typeName))+"&srCode="+srCode;
}

function setGridSize(tableId, width, subHeight) {

	if (!width)
		width = 800;
	if (!subHeight)
		subHeight = 200;

	$("#" + tableId).setGridHeight($(window).height() - subHeight);
	$("#" + tableId).setGridWidth(width);
}
