$(function() {
	initLogList();//初始列表
	// 自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
		$("#gridTable").setGridHeight(heightTotal() + 80);
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

function initLogList() {
	$("#gridTable").jqGrid().GridUnload("gridTable");
	$("#gridTable").jqGrid({
		url : ctx+"/log/notification/findNotificationsPage.action",
		rownumbers : true,
		datatype : "json",
		mtype : "post",
		multiselect : false,
		postData : {},
		height : heightTotal() + 80,
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
			name : "source",
			index : "source",
			label : i18nShow('sys_notice_source'),
			width : 20,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				if(cellVall == "AUTO" ){
					return i18nShow('sys_notice_source_auto');
				}else if(cellVall == "MANUAL" ){
					return i18nShow('sys_notice_source_man');
				}else if(cellVall == "FROMVC" ){
					return i18nShow('sys_notice_source_vc');
				}
			}
		},
		{
			name : "type",
			index : "type",
			label : i18nShow('sys_notice_type'),
			width : 10,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				if(cellVall == "TIP" ){
					return i18nShow('sys_notice_type_tip');
				}else if(cellVall == "WARNING"){
					return i18nShow('sys_notice_type_warn');
				}else if(cellVall == "ERROR"){
					return i18nShow('sys_notice_type_error');
				}
			}
		},
		{
			name : "operationType",
			index : "operationType",
			label : i18nShow('sys_notice_op_type'),
			width : 15,
			sortable : true,
			align : 'left'
		},
		{
			name : "operationContent",
			index : "operationContent",
			label : i18nShow('sys_notice_op_content'),
			width : 100,
			sortable : true,
			align : 'left'
		},
		{
			name : "createTime",
			index : "createTime",
			label : i18nShow('sys_notice_op_time'),
			width : 30,
			sortable : true,
			align : 'left',
			formatter: function(cellValue,options,rewObject){
				return formatTime(cellValue);
			}
		},
		{
			name : "state",
			index : "state",
			label : i18nShow('com_status'),
			width : 20,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				if(cellVall == "UNREAD" ){
					return "<span class='tip_red'>"+i18nShow('sys_notice_unread')+"</span>";
				}else if(cellVall == "READ" ){
					return "<span class='tip_green'>"+i18nShow('sys_notice_read')+"</span>";
				}else{
					return "<span class='tip_red'>"+i18nShow('sys_notice_unread')+"</span>";
				}
			}
		},
		{
			name : "state",
			index : "state",
			label : i18nShow('com_operate'),
			width : 30,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				if(cellVall == 'NOTCLOSE'){
					return  "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=updateDealState('"+rowObject.id+"') >"+i18nShow('sys_notice_close')+"</a>"; 
				}else{
					return " ";
				}
			}
		}],
		viewrecords : true,
		sortname : "createTime",
		sortorder : "desc",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "searchNotificationLog"
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


//更改处理状态
function updateDealState(id){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx+"/log/notification/updateNotiState.action",  
		data : {"notiPo.id":id},
		error : function() {//请求失败处理函数  
			closeTip();
			showError(i18nShow('tip_req_fail'));
		},
		success : function(data) { //请求成功后处理函数。     
			showTip(i18nShow('tip_op_success'));
			searchNotificationLog();
		}
	});
}

function searchNotificationLog() {
	$("#gridTable").jqGrid('setGridParam', {
		url : ctx+"/log/notification/findNotificationsPage.action",
		postData : {"state":$("#state").val(),"operationType":$("#operation_Type").val(),"type":$("#type").val()},
		pager : "#gridPager"
	}).trigger("reloadGrid");
}

function clearAll(){
	$("#state").val("");
	$("#operation_Type").val("");
	$("#type").val("");
}
