var method = "";
/**
 * 初始化
 */
$(function() {
	initAddTenantNameSelect();
	initTotalInvoice();//初始列表
	// 自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
		$("#gridTable").setGridHeight(heightTotal() + 80);
	});
});

function clearAll(){
	//清除方法
	$("#selTenant").val("");
	$("#billMonth").val("");
	
};
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
};
function initTotalInvoice() {
	var d = new Date(); 
	 var m = d.getMonth()+1;  
	 m = m < 10 ? "0" + m : m;
	var billMonth = d.getFullYear() + m;
	$("#gridTable").jqGrid({
		url : ctx + "/bill/query/queryTotalInvoicePage.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		postData :{
			"billMonth" : billMonth
		},
		height : heightTotal() + 80,
		autowidth : true, // 是否自动调整宽度
		colModel : [
		{
			name : "name",
			index : "name",
			label : '租户名称',
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			name : "totalInvoice",
			index : "totalInvoice",
			label : '发票总额(元)',
			width : 50,
			sortable : true,
			align : 'left',
			formatter:function(cellVall, options,rowObject){
				var result = "0";
				if(cellVall !="null" && cellVall !="0"){
					result =  cellVall/1000;
				}else{
					result =  "0";
				}
				return result;
			}
		},],
		viewrecords : true,
		sortname : "tenantName",
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
};
//查询按钮
function searchBillvoucher() {
	jqGridReload("gridTable", {
		"tenantId" : $("#selTenant").val(),
		"billMonth" : $("#billMonth").val()
	});
}

