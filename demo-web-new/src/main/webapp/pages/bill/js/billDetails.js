var method = "";
/**
 * 初始化
 */
$(function() {
	initAddTenantNameSelect();
	initBilldetails();//初始列表
	// 自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
		$("#gridTable").setGridHeight(heightTotal() + 80);
	});
});

function clearAll(){
	//只是清理租户名称
	$("#selTenant").val("");
	$("#serviceBeginTimes").val("");
	$("#serviceEndtimes").val("");
    $("#serviceEndtimes").val("")
    //清理服务器类型
    $("#serviceType").val("");
}
//获取所有租户列表
function initAddTenantNameSelect() {
	$("#selTenant").html(
			"<option value=''>" + i18nShow('com_select_defalt')
					+ "...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx + "/rest/tenant/getTenants", {
		"_" : new Date().getTime()
	}, function(data) {
		$.each(data, function() {
			$("#selTenant").append(
					"<option value='" + this.id + "'>" + this.name
							+ "</option>");
		});
	});
}
function initBilldetails() {
	var d = new Date(); 
	 var m = d.getMonth()+1;  
	 m = m < 10 ? "0" + m : m;
	var billMonth = d.getFullYear() + m;
	$("#gridTable").jqGrid({
		url : ctx + "/bill/query/detailList.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		postData :{
			"tenantId" : $("#selTenant").val(),
			"serviceType": $("#serviceType").val(),
			"startDate" :$("#serviceBeginTimes").val(),
			"endDate": $("#serviceEndtimes").val()
		},
		height : heightTotal() + 80,
		autowidth : true, // 是否自动调整宽度
		colModel : [
		{
			name : "instanceId",
			index : "instanceId",
			label : '实例ID',
			width : 50,
			sortable : true,
			align : 'left'
		},{
			name : "tenantName",
			index : "tenantName",
			label : '租户名称',
			width : 50,
			sortable : true,
			align : 'left'
		},{
			name : "billMoney",
			index : "billMoney",
			label : '费用',
			width : 50,
			sortable : true,
			align : 'left',
			hidden:false
		}, {
			name : "startTime",
			index : "startTime",
			label : '开始时间',
			width : 50,
			sortable : true,
			align : 'left',
		}, {
			name : "billMonth",
			index : "billMonth",
			label : '账期',
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			name : "createDate",
			index : "createDate",
			label : '创建日期',
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			name : "serviceType",
			index : "serviceType",
			label : '服务类型 ',
			width : 50,
			sortable : true,
			align : 'left'
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
//查询按钮
function searchBillDetails() {
	jqGridReload("gridTable", {
		"tenantId" : $("#selTenant").val(),
		"serviceType": $("#serviceType").val(),
		"startDate" :$("#serviceBeginTimes").val(),
		"endDate": $("#serviceEndtimes").val()
	});
}

