/**
 * 查询页面初始化-ls
 */
function initTest() {
	
	var queryData = {};
	
	
	$("#gridTable").jqGrid({
		url : ctx + "/parameter/management/search.action",
		rownumbers : true,
		datatype : "json",
		postData : queryData,
		mtype : "post",
		height : heightTotal() + 85,
		autowidth : true,
		colNames : [ 'paramId', i18nShow('sys_para_name'),i18nShow('sys_para_value'),i18nShow('sys_para_remark'),i18nShow('sys_para_secrit'),i18nShow('com_operate')],
		colModel : [ {
			name : "paramId",
			index : "paramId",
			width: 150,
			hidden : true
		}, {
			name : "paramName",
			index : "paramName",
			sortable : true,
			width: 240,
			align : 'left'
		},{
			name : "paramValue",
			index : "paramValue",
			sortable : true,
			width: 300,
			align : 'left'
		} ,{
			name : "remark",
			index : "remark",
			sortable : true,//排序属性
			width: 180,
			align : 'left'
		},{
			name : "isEncryption",
			index : "isEncryption",
			sortable : true,//排序属性
			width: 60,
			align : 'left'
		}, {
			name : "option",
			index : "option",
			width : 90,
			sortable : false,
			align : "left",
			formatter : function(cellVall, options, rowObject) {
				var updateFlag = $('#updateFlag').val();
				var deleteFlag = $('#deleteFlag').val();
				
				var ret = "　　";
				if(updateFlag){
					ret += "<a  style='margin-right: 10px;margin-left: -25px;text-decoration:none;color: #18a689' href='javascript:#' title=''  onclick=addParameter('" + rowObject.paramId + "')>"+i18nShow('com_update')+"</a>";
				}
				if(deleteFlag){
					ret += "<a  style='margin-right: 10px;text-decoration:none;color: #18a689' href='javascript:#' title=''  onclick=deletes('" + rowObject.paramId + "')>"+i18nShow('com_delete')+"</a>";
				}
				ret += "　";
				return ret;
				}
			}],
		viewrecords : true,
		sortname : "paramName",
		rowNum : 10,
		rowList : [5, 10, 15, 20, 30 ],
		jsonReader : {
			root : "dataList",
			records : "record",
			page : "page",
			total : "total",
			repeatitems : false
		},
		pager : "#gridPager"
	});
	
		$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridTableDiv").width());
		$("#gridTable").setGridHeight(heightTotal() + 85);
    });
	 $("#addParameterForm").validate({
		 submitHandler:function(){
			 save1();
		 }
		 
	 });

	
}

function clearAll(){
	$("#qryParamName").val("");
}

var operator;
/*
 * 新增或者修改参数
 */	
function addParameter(paramId){
	//初始化下拉框
	$("#paramId").val("");
	$("#paramName").val("");
	$("#paramValue").val("");
	$("#remark").val("");
	var title;
	if(paramId){
		title=i18nShow('sys_para_update');
		operator='modify';
		$.post(ctx + "/parameter/management/view.action",{"paramPo.paramId" : paramId},function(data){
			$("#paramId").val(paramId);
			$("#paramName").attr("readOnly",false).val(data.paramName);
			$("#paramValue").val(data.paramValue);
			$("#remark").val(data.remark);
			if("Y" == data.isEncryption){
				$(".radioItem:eq(1)").attr("checked",true);								
			}else{
				$(".radioItem:first").attr("checked",true);				
			}
			$("#addParameterDiv").dialog({
				width : 654,
				autoOpen : true,
				modal : true,
				height : 245,
				title:title
			});
		});
	}else{
		title=i18nShow('sys_para_save');
		operator='create';
		$("#addParameterDiv").dialog({
			width : 654,
			autoOpen : true,
			modal : true,
			height : 245,
			title:title
		});
	}
}
/**
 * 提交验证，验证成功后进行保存操作
 */
function updateOrSave(){
	if(operator=='create')
	{
		submitValidate2();
	}
	else
	{
		if(submitValidate1())
		{
			save1();//请求响应函数
	    }
	}
}

function submitValidate1(){
	var paramValue = $("#paramValue").val().trim();
	if(paramValue == null || paramValue == ""){
		showTip(i18nShow('validate_data_required'));
		return false;
	}
	return true;
}
function submitValidate2(){
	var paramName = $("#paramName").val().trim();
	if(paramName == null || paramName == ""){
		showTip(i18nShow('validate_data_required'));
		return false;
	}
	
	var paramValue = $("#paramValue").val().trim();
	if(paramValue == null || paramValue == ""){
		showTip(i18nShow('validate_data_required'));
		return false;
	}
	
	var isEncryption = $("#isEncryption").val();
	if(isEncryption == null || isEncryption == ""){
		showTip(i18nShow('validate_data_required'));
		return false;
	}
	
	//通过传入的参数去去请求ACTION
	$.post(ctx + "/parameter/management/getParamName.action",{"paramPo.paramName" : paramName},function(data){
		if(data.result=="false"){
			showTip(i18nShow('validate_data_remote'));
			return false;
		}else {		
			save1();//请求响应函数			
	     }
	});
}

function save1(){
	if($("input[name='isEncryption']:checked").val()== "Y" & isNaN($("#paramValue").val())){
		showTip(i18nShow('tip_paramValue_isNum'),null,"red");
		return;
	}
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/parameter/management/save.action",
		async : false,
       data : {//获得表单数据
    	    "paramPo.paramId" : $("#paramId").val(),
    	    "paramPo.paramName" : $("#paramName").val(),
			"paramPo.paramValue" : $("#paramValue").val(),
			"paramPo.remark" : $("#remark").val(),
			"paramPo.isEncryption" : $("input[name='isEncryption']:checked").val(),
		},

		success : function(){
			closeView("addParameterDiv");
			search();
		},
		error : function() {
			showTip(i18nShow('tip_error'),null,"red");
		}
	});
}
function search(){
	var queryData = {
		paramName : $("#qryParamName").val().replace(/(^\s*)|(\s*$)/g, "")
		};
	jqGridReload("gridTable", queryData);
}
function closeView(id) {
	$("#"+id).dialog("close");
}
function deletes(paramId){
	if(paramId){
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.post(ctx+"/parameter/management/delete.action",{
				"paramPo.paramId" : paramId
			},function(data){
				search();
				showTip(i18nShow('tip_delete_success'));
				
			});
		});
	}
	
}