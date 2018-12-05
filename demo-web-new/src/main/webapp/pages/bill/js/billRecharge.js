var method = "";
/**
 * 初始化
 */
$(function() {
	initBillrecharge();//初始列表
	initAddTenantNameSelect();
	initBillrechargeList();//充值查询初始列表
	initAddTenantNameSelectList();
	// 自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$("#gridTableList").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
		$("#gridTable").setGridHeight(heightTotal() + 80);
		$("#gridTableList").setGridWidth($("#gridMain").width());
		$("#gridTableList").setGridHeight(heightTotal() + 80);
	});
	
	//充值页面保存按钮保存
	$("#billRechargForm").validate({
		rules: {
			amount: {required: true},
			amount: {number: true},
			fundTypeCode: {required: true},
		},
		messages: {
			amount: {required: "不能为空"},
			amount: {required: "您输入的不是合法的数字"},
			fundTypeCode: {required: "不能为空"},
		},
		submitHandler: function() {
			billRechargeSaveData();
		}
	});
});


function clearAll(){
	//清除方法
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


//充值页面获取所有租户列表
function initAddTenantNameSelectList() {
	$("#selTenantList").html(
			"<option value=''>" + i18nShow('com_select_defalt')
					+ "...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx + "/rest/tenant/getTenants", {
		"_" : new Date().getTime()
	}, function(data) {
		$.each(data, function() {
			$("#selTenantList").append(
					"<option value='" + this.id + "'>" + this.name
							+ "</option>");
		});
	});
}

function initBillrecharge() {
	$("#gridTable").jqGrid({
		url : ctx + "/bill/query/queryTenantBalancePage.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		postData :{
		},
		height : heightTotal() + 80,
		autowidth : true, // 是否自动调整宽度
		colModel : [
		{
			name : "id",
			index : "id",
			label : 'id',
			width : 50,
			sortable : true,
			align : 'left',
			hidden:true
		},{
			name : "name",
			index : "name",
			label : '租戶名稱',
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			name : "balance",
			index : "balance",
			label : '账户余额(元)',
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			name : "operator",
			index : "operator",
			label : '操作',
			width : 50,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options,
					rowObject) {
				var ret = "";
					ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=billRechargeModle('"
						+ rowObject.id
						+ "') >"
						+ '充值'
						+ "</a>"
						;
				return ret;
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

function initBillrechargeList() {
	var d = new Date(); 
	 var m = d.getMonth()+1;  
	 m = m < 10 ? "0" + m : m;
	var billMonth = d.getFullYear() + m;
	$("#gridTableList").jqGrid({
		url : ctx + "/bill/query/fundSerialList.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		postData :{
			"tenantId" : $("#selTenantList").val(),
			"beginTime":$("#serviceBeginTimes").val(),
			"endTime":$("#serviceEndtimes").val()
		},
		height : heightTotal() + 80,
		autowidth : true, // 是否自动调整宽度
		colModel : [
			{
				name : "tenantName",
				index : "tenantName",
				label : '租户',
				width : 50,
				sortable : true,
				align : 'left',
			},{
				name : "createDate",
				index : "createDate",
				label : '充值时间',
				width : 50,
				sortable : true,
				align : 'left'
			}, {
				name : "optType",
				index : "optType",
				label : '充值类型',
				width : 50,
				sortable : true,
				align : 'left'
			}, {
				name : "payStatus",
				index : "payStatus",
				label : '充值状态',
				width : 50,
				sortable : true,
				align : 'left'
			},{
				name : "totalAmount",
				index : "totalAmount",
				label : '充值金额',
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
		pager : "#gridPagerList",
		hidegrid : false
	});
}
//查询按钮
function searchBillRecharge() {
	var queryData = {"tenantId" : $("#selTenant").val()};
	jqGridReload("gridTable", queryData);
}


//充值查询按钮
function searchBillRechargeList() {
	jqGridReload("gridTableList", {
		"tenantId" : $("#selTenantList").val(),
		"beginTime":$("#serviceBeginTimes").val(),
		"endTime":$("#serviceEndtimes").val()
	});
}


//充值页面的充值弹出框
function billRechargeModle(tenantId){
	$("#billRecharge_modle").dialog({
		autoOpen : true,
		modal : true,
		height : 300,
		width : 460,
		position : "middle",
		title :'充值',
	});
	$("#tenantId").val(tenantId);
	$("#amount").val('');
	$('#busiDesc').val('');
}

//关闭操作
function BillRechargeCloseBtn(pageDiv){
	$("#"+pageDiv).dialog("close");
}


function billRechargeSaveBtn(){
	$("#billRechargForm").submit();
}


function billRechargeSaveData(){
	var amount = $("#amount").val();
	var busiDesc = $("#busiDesc").val();
	var fundTypeCode = $("#fundTypeCode").val();
	var tenantId = $("#tenantId").val();
	if(tenantId == "" || tenantId == null){
		alert("id不能为空");
		return ;
	};
	$.ajax({
		async : false,
		cache : false,
		url : ctx + "/bill/query/accountRecharge.action",
		data : {"busiDesc":busiDesc,"amount": amount,"fundTypeCode":fundTypeCode,"tenantId":tenantId},
		type:'post',
		dataType : "json",
		error : function(datas) {//请求失败处理函数   
			showError(datas.result);
		},
		success : function(datas){
			if(datas.result == "000000"){
				showTip("充值成功");
				//关闭对话框
				$("#billRecharge_modle").dialog("close");
				//刷新列表
				$("#gridTable").jqGrid().trigger("reloadGrid");
			}else{
				showError(datas.result);
			}
			
	}
});

}