var method = "";
/**
 * 初始化
 */
$(function() {
	initDicList();//初始列表
	
    jQuery.validator.addMethod("isEnglish", function(value, element) {       
         return this.optional(element) || /^([a-z_A-Z0-9]+)$/.test(value);       
    }); 
     
	$("#add_update_Dic_Form").validate({//校验
		rules: {
			dicName: "required",
			dicTypeCode:"required",
			dicCode: {
			   "required":true,
			   "isEnglish":true
			}, 
			orderNum: {
			   "required":true,
			   "number":true
			}
		},
		messages: {
			dicTypeCode: i18nShow('validate_data_required'),
			dicName: i18nShow('validate_data_required'),
			dicCode: {
				   "required":i18nShow('validate_data_required'),
				   "isEnglish":i18nShow('validate_num_us')
				},
			orderNum: {
			   "required":i18nShow('validate_data_required'),
			   "number":i18nShow('validate_number')
			}
		},
		submitHandler: function() {
			saveDic();
		}
	});
	
	// 自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
		$("#gridTable").setGridHeight(heightTotal() + 85);
	});
});

function clearAll(){
	$("#dicName_s").val("");
	$("#dicCode_s").val("");
	$("#dicTypeCode_s").val("");
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

function initDicList() {
	$("#gridTable").jqGrid({
		url : ctx + "/dic/findDicPage.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height :heightTotal() + 85,
//		multiselect : true,
		autowidth : true, // 是否自动调整宽度
		colModel : [ {
			name : "dicName",
			index : "dicName",
			label : i18nShow('sys_dic_name'),
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			name : "dicCode",
			index : "dicCode",
			label : i18nShow('sys_dic_code'),
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			name : "dicTypeCode",
			index : "dicTypeCode",
			label : i18nShow('sys_dictype_name'),
			width : 140,
			sortable : true,
			align : 'left'
		}, {
			name : "orderNum",
			index : "orderNum",
			label : i18nShow('sys_dic_order_num'),
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "createUser",
			index : "createUser",
			label : i18nShow('sys_dic_type_creator'),
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "createDatetime",
			index : "createDatetime",
			label : i18nShow('sys_dic_type_create_time'),
			width : 150,
			sortable : true,
			align : 'left',
            formatter: function(cellValue,options,rewObject){
	            if(cellValue!=null)
				   return formatTime(cellValue);
	            else
				   return "";
            }
		}, {
			name : "updateUser",
			index : "updateUser",
			label : i18nShow('sys_dic_type_editor'),
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "updateDatetime",
			index : "updateDatetime",
			label : i18nShow('sys_dic_type_edit_time'),
			width : 140,
			sortable : true,
			align : 'left',
            formatter: function(cellValue,options,rewObject){
				if(cellValue!=null)
				   return formatTime(cellValue);
	            else
				   return "";
	        }
		}, {
			name : "remark",
			index : "remark",
			label : i18nShow('com_remark'),
			width : 80,
			sortable : true,
			align : 'left'
		},
		{
			name:i18nShow('com_operate'),index:"option",sortable:false,align:"left",width : 70,title:false,
			formatter:function(cellVall,options,rowObject){
			    var result = "";
			    
				/*var mod = "<input type='button' class='btn_edit_s' title='修改' onclick=modifyDic('"+rowObject.dicId+"','update') />";
				var del = "<input type='button' class='btn_del_s' title='删除' onclick=deleteDic('"+rowObject.dicId+"') />";*/
			    var mod = "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=modifyDic('"+rowObject.dicId+"','update') >"+i18nShow('com_update')+"</a>";
			    var del = "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteDic('"+rowObject.dicId+"')>"+i18nShow('com_delete')+"</a>";
			    var upFlag = $("#updateFlag").val(); 
				var delFlag = $("#deleteFlag").val();
				var rolFlag = $("#authorizationFlag").val();
				
				if(upFlag == "1")
					result += mod;
				
				if(delFlag == "1")
					result += "  "+del;
				
				
				return result;
			}
        }],
		viewrecords : true,
		sortname : "createDateTime",
		sortorder : "desc",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "searchDic"
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

function searchDic() {
	jqGridReload("gridTable", {
		"dicTypeCode" : $("#dicTypeCode_s").val().replace(/(^\s*)|(\s*$)/g, ""),
		"dicName" : $("#dicName_s").val().replace(/(^\s*)|(\s*$)/g, ""),
		"dicCode" : $("#dicCode_s").val().replace(/(^\s*)|(\s*$)/g, "")
	});
}

/**
 * 添加字典类型
 */
function addDic(methodname) {
	method = methodname;//insert
	clearDic();
	$("label.error").remove();
	openDialog('add_update_Dic_Div',i18nShow('sys_dic_save'),654,300);
}

/**
 * 修改字典信息
 */
function modifyDic(dicId,methodname){
	method = methodname;//update
	$("#dicId").val(dicId);
	getDic(dicId);
	//清空验证错误的提示
	$("label.error").remove();
	openDialog('add_update_Dic_Div',i18nShow('sys_dic_update'),654,280);
}




/**
 * 打开对话框
 * @param divId
 * @param title
 * @param width
 * @param height
 * @return
 */
function openDialog(divId, title, width, height) {
	$("#" + divId).dialog({
		autoOpen : true,
		modal : true,
		height : height,
		width : width,
		title : title,
		//resizable : false
	})
}

/**
 * 提交保存
 * @return
 */
function saveDicBtn(){
	$("#add_update_Dic_Form").submit();  
}

/**
 * 保存子弹信息
 */
function saveDic(){
	var dicId=$("#dicId").val();
	if("insert"==method){
		dicId = null;
	}
	var dicCode=$("#dicCode").val();
	var dicName=$("#dicName").val();
	var createUser=$("#createUser").val();
	var createDatetime=$("#createDatetime").val();
	var dicTypeCode=$("#dicTypeCode").val();
	var orderNum=$("#orderNum").val();
	var remark=$("#remark").val();
	var dataObj={
			"dicPo.dicId":dicId,
			"dicPo.dicCode":dicCode,
			"dicPo.dicName":dicName,
			"dicPo.dicTypeCode":dicTypeCode,
			"dicPo.createUser":createUser,
			"dicPo.createDateTime":createDatetime,
			"dicPo.orderNum":orderNum,
			"dicPo.remark":remark
	};
    $("#saveDic").attr({"disabled":true});
	$.ajax({
		async : false,
		cache : false,
		url : ctx + "/dic/saveDic.action",
		data : dataObj,
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('tip_req_fail'));
		},
		success : function(datas){
			if(datas.result=="fail"){
				showTip(i18nShow('tip_sys_dic_save'));
			}else{
			searchDic();
			closeView('add_update_Dic_Div');//关闭
		 }
			$("#saveDic").attr({"disabled":false});
		}
	});
}

/**
 * 根据ID查询字典信息
 * @param nodeId
 */
function getDic(dicId){
	$.ajax({
		async : false,
		cache : false,
		url : ctx + "/dic/findDicById.action",
		data : {"dicPo.dicId":dicId},
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('tip_req_fail'));
		},
		success : function(datas){
			initDic(datas);
		}
	});
}

/**
 * 删除字典类型
 */
function deleteDic(dicId){
	showTip(i18nShow('tip_delete_confirm'),function(){
		$.ajax({
			async : false,
			cache : false,
			url : ctx + "/dic/deleteDic.action",
			data : {"dicPo.dicId":dicId},
			type:'post',
			dataType : "json",
			success : function(datas){
				if(datas.result=="0"){
					showError(i18nShow('tip_req_fail'));
				}
				searchDic();
			}
		});
	});
}

/**
 * 初始化字典类型添加页面
 * @param userId
 */
function clearDic(){
	$("#dicCode").val("");
	$("#dicName").val("");
	$("#dicTypeCode").val("");
	$("#orderNum").val("");
	$("#remark").val("");
	$("#dicId").val("");
	$("#createUser").val("");
	$("#createDatetime").val("");
}

/**
 * 初始字典类型查看页面
 * @param datas
 */
function initDic(datas) { 
	$("#dicId").val(datas.dicId);
	$("#dicCode").val(datas.dicCode);
	$("#dicName").val(datas.dicName);
	$("#dicTypeCode").val(datas.dicTypeCode);
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

