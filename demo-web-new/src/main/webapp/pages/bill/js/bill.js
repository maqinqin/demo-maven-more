var method = "";
/**
 * 初始化
 */
$(function() {
	initBillList();//初始列表
	initAddTenantNameSelect();
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
function initBillList() {
	var d = new Date(); 
	 var m = d.getMonth()+1;  
	 m = m < 10 ? "0" + m : m;
	var billMonth = d.getFullYear() + m;
	$("#gridTable").jqGrid({
		url : ctx + "/bill/query/queryList.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		postData :{
			"tenantId":"",
			"billMonth":billMonth,
			"billFeeMin":"",
			"billFeeMax":""
		},
		height : heightTotal() + 80,
		autowidth : true, // 是否自动调整宽度
		colModel : [
		{
			name : "tenantId",
			index : "tenantId",
			label : '租户id',
			width : 50,
			sortable : true,
			align : 'left',
			hidden:true
		},{
			name : "billMonth",
			index : "billMonth",
			label : '账单月',
			width : 50,
			sortable : true,
			align : 'left'
		},{
			name : "tenantName",
			index : "tenantName",
			label : '租户名称',
			width : 50,
			sortable : true,
			align : 'left',
			hidden:false
		},{
			name : "billMoney",
			index : "billMoney",
			label : '账单应缴金额（元）',
			width : 50,
			sortable : true,
			align : 'left',
			formatter: function(cellValue,options,rowObject){
	            if(cellValue != 0){
	            	return cellValue/1000; 
	            }else{
	            	return cellValue
	            }	            	
            }
		}, {
			name : "realIncomeMoney",
			index : "realIncomeMoney",
			label : '实收金额（元）',
			width : 50,
			sortable : true,
			align : 'left',
			formatter: function(cellValue,options,rowObject){
	            if(cellValue != 0){
	            	return cellValue/1000; 
	            }else{
	            	return cellValue
	            }	            	
            }
		}, {
			name : "paymentState",
			index : "paymentState",
			label : '支付状态',
			width : 50,
			sortable : true,
			align : 'left',
			 formatter: function(cellValue,options,rowObject){
		            if(cellValue == 0){
		            	return "<span style='color:green'>已支付</span>"; 
		            }else {
		            	return "<span style='color:red'>待支付</span>";
		            }	            	
	            }
		},  {
			name : "remark",
			index : "remark",
			label : '备注',
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
function searchBill() {
	jqGridReload("gridTable", {
		"tenantId" : $("#selTenant").val(),
		"billMonth" : $("#billMonth").val(),
		"billFeeMin":"",
		"billFeeMax":""
	});
}

