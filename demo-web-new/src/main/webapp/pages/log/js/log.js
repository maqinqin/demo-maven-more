var method = "";
/**
 * 初始化
 */
$(function() {
	initLogList();//初始列表
	// 自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
		$("#gridTable").setGridHeight(heightTotal() + 80);
	});
});

function clearAll(){
	$("#moduleName_s").val("");
	$("#operateName_s").val("");
	$("#operateContent_s").val("");
}

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
function initLogList() {
	$("#gridTable").jqGrid({
		url : ctx + "/log/findLogPage.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : heightTotal() + 80,
//		multiselect : true,
		autowidth : true, // 是否自动调整宽度
		colModel : [ {
			name : "moduleName",
			index : "moduleName",
			label : i18nShow('sys_log_model_name'),
			width : 50,
			sortable : true,
			align : 'left'
		},{
			name : "loginName",
			index : "loginName",
			label : i18nShow('sys_log_login_name'),
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			name : "operator",
			index : "operator",
			label : i18nShow('sys_log_op_persion'),
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			name : "operateName",
			index : "operateName",
			label : i18nShow('sys_log_op_type'),
			width : 80,
			sortable : true,
			align : 'left'
		},  {
			name : "operateContent",
			index : "operateContent",
			label : i18nShow('sys_log_op_content'),
			width : 50,
			sortable : true,
			align : 'left',
			hidden:true
		}, {
			name : "operateContent",
			index : "operateContent_",
			label : i18nShow('sys_log_op_content'),
			width : 220,
			sortable : true,
			title:false,
			align : 'left',
			formatter: function(cellValue,options,rowObject){
	            var subVal = "";
	            if(cellValue!=null){
	               if(cellValue.length > 80){
	                    subVal =  cellValue.substring(0,80) + "......";
	               }else{
	                    subVal =  cellValue;
	               }
	               var returnStr = "<a href='javascript:;'  style='color:#18a689'  onclick='showLogDetail(\""+ options.rowId + "\")'>" + subVal + "</a>";
	               return returnStr;
	            }else {
				   return "";
				}
            }
		}, {
			name : "ipAddr",
			index : "ipAddr",
			label : "IP",
			width : 60,
			sortable : true,
			align : 'left'
		}, {
			name : "operateTime",
			index : "operateTime",
			label : i18nShow('sys_log_op_time'),
			width : 80,
			sortable : true,
			align : 'left',
            formatter: function(cellValue,options,rowObject){
	            if(cellValue!=null)
				   return formatTime(cellValue);
	            else
				   return "";
            }
		},{
			name : "result",
			index : "result",
			label : i18nShow('com_status'),
			width : 80,
			sortable : true,
			align : 'left',
            formatter: function(cellValue,options,rowObject){
	            if(cellValue && cellValue == "successed"){
	            	return "<span style='color:green'>成功</span>"; 
	            }else if(cellValue && cellValue == "falied"){
	            	return "<span style='color:red'>失败</span>";
	            }else{
	            	return "";
	            }	            	
            }
		}],
		viewrecords : true,
		sortname : "operateTime",
		sortorder : "desc",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "searchLog"
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

function showLogDetail(rowId) { 
    var operateContent=$("#gridTable").getCell(rowId, "operateContent");
    $("#log_detail_text").html();
    $("#log_detail_text").html(operateContent);
    $("#log_detail_text").readonly = true;
	$("#log_detail_div").dialog({
		autoOpen : true,
		modal : true,
		width : 520,
		title : i18nShow('sys_log_op_content'),
		height : 360
	});
}
function searchLog() {
	jqGridReload("gridTable", {
		"moduleName" : $("#moduleName_s").val().replace(/(^\s*)|(\s*$)/g, ""),
		"operateName" : $("#operateName_s").val().replace(/(^\s*)|(\s*$)/g, ""),
		"operateContent" : $("#operateContent_s").val().replace(/(^\s*)|(\s*$)/g, "")
	});
}

/**
 * 根据ID日志信息
 */
function getLog(logId){
	$.ajax({
		async : false,
		cache : false,
		url : ctx + "/log/findLogById.action",
		data : {"logPo.logId":logId},
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('tip_req_fail'));
		},
		success : function(datas){
			initLog(datas);
		}
	});
}


/**
 * 初始日志查看页面
 * @param datas
 */
function initLog(datas) { 
	$("#logId").val(datas.logId);
	$("#logCode").val(datas.logCode);
	$("#logName").val(datas.logName);
	$("#logTypeCode").val(datas.logTypeCode);
	$("#orderNum").val(datas.orderNum);
	$("#createUser").val(datas.createUser);
	$("#createDatetime").val(formatTime(datas.createDatetime));
	$("#remark").val(datas.remark);
}
/**
 * 关闭对话框
 * @param divId
 * @return
 */
function closeView(divId) {
	$("#" + divId).dialog("close");
}

