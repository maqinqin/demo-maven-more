$(document).ready(function() {
	alert("苗有虎");
	showInstanceList();
});

/**
 * 展示流程实例列表
 * 
 * @returns
 */
function showInstanceList() {
	$("#modelTable").jqGrid({
		url : ctx + "/workflow/model/model_index.action",
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 280,
		autowidth : true, // 是否自动调整宽度
		colNames : [ '模板序号', '模板名称', '模板类型', '创建时间' ],
		colModel : [ {
			name : 'modelId',
			index : 'modelId',
			width : 18,
			align : "center"

		}, {
			name : 'modelName',
			index : 'modelName',
			width : 100,
			align : "center"
		}, {
			name : 'typeId',
			index : 'typeId',
			width : 100,
			align : "center"
		}, {
			name : 'createDate',
			index : 'createDate',
			width : 100,
			align : "center",
			formatter : function(cellValue, options, rowObject) {
				return dateFormat(cellValue);
			}
		} ],
		rowNum : defaultRowNum,
		rowList : defaultRowList,
		sortname : 'modelId',
		sortorder : "desc",
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#pageNav",
		caption : "流程模板",
		hidegrid : false
	});
}


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

function showInstance(instanceId, wfModelId) {
	window.location.href = ctx + "/pages/tankflow/instance/instance.jsp?instanceId="+ instanceId + "&wfModelId=" + wfModelId;
}