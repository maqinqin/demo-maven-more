var method = "";
/**
 * 初始化
 */
$(function() {
	initDicTypeList();//初始列表
	//wmy------------------------------------------------------------
	jQuery.validator.addMethod("validateDicTypeName", function(value, element) {
        var validateValue=true;
        var dicTypeNameMethod=$("#dicTypeNameMethod").val();
        //alert(dicTypeNameMethod)
        var dicTypeNameCheck = $("#dicTypeNameCheck").val();
        //alert(dicTypeNameCheck);
        $.ajax({
             type:'post',
             datatype : "json",
             data:{"dicTypePo.dicTypeName":value},
             url : ctx + "/dic/validateDicTypeName.action",
             async : false,
             success:(function(data){
                  if(dicTypeNameMethod=="update"){
                       if(data==null||data.dicTypeName==dicTypeNameCheck){
                           validateValue=true;
//                          alert(validateValue+"1");
                       }else{
                            validateValue=false;
                       }
                       
                  }else{
                  if(data==null){
                       validateValue=true;
                  }else{
                       validateValue=false;
                  }
                  }
             }),
        });
        return this.optional(element) || validateValue;
        },
   "数据字典不能重复");
	//-------------------------------------------------------
	
    jQuery.validator.addMethod("isEnglish", function(value, element) {       
         return this.optional(element) || /^([a-z_A-Z0-9]+)$/.test(value);       
    }, "只能输入英文字母，数字，下划线");  
     
	$("#add_update_DicType_Form").validate({//校验
		rules: {
		dicTypeName: {
		    required:true,
		    isEnglish:true,
		    validateDicTypeName:true
		}
		},
		messages: {
			dicTypeName: {
			   required:i18nShow('validate_data_required'),
			   isEnglish:i18nShow('validate_availableZoneName_stringCheck'),
			   validateDicTypeName:i18nShow('validate_data_remote')
			}
		},
		submitHandler: function() {
			saveDicType();
		}
	});
	
	
	// 自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
		$("#gridTable").setGridHeight(heightTotal() + 83);
	});
});

function clearAll(){
	$("#dicTypeName_s").val("");
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
function initDicTypeList() {
	$("#gridTable").jqGrid({
		url : ctx + "/dic/findDicTypePage.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : heightTotal() + 83,
//		multiselect : true,
		autowidth : true, // 是否自动调整宽度
		colModel : [ {
			name : "dicTypeName",
			index : "dicTypeName",
			label : i18nShow('sys_dic_type_name'),
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			name : "createUser",
			index : "createUser",
			label : i18nShow('sys_dic_type_creator'),
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			name : "createDatetime",
			index : "createDatetime",
			label : i18nShow('sys_dic_type_create_time'),
			width : 120,
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
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			name : "updateDatetime",
			index : "updateDatetime",
			label : i18nShow('sys_dic_type_edit_time'),
			width : 80,
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
			name:i18nShow('com_operate'),index:"option",sortable:false,align:"left",width : 120,title:false,
			formatter:function(cellVall,options,rowObject){
			    var result = "";
			    
				//var mod = "<input type='button' class='btn_edit_s' title='修改' onclick=modifyDicType('"+rowObject.dicTypeCode+"','update') />";
				//var del = "<input type='button' class='btn_del_s' title='删除' onclick=deleteDicType('"+rowObject.dicTypeCode+"','"+rowObject.dicTypeName+"') />";
			    var mod = "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=modifyDicType('"+rowObject.dicTypeCode+"','update') >"+i18nShow('com_update')+"</a>";
			    var del = "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteDicType('"+rowObject.dicTypeCode+"','"+rowObject.dicTypeName+"')>"+i18nShow('com_delete')+"</a>";
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
			search : "searchDicType"
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

function searchDicType() {
	jqGridReload("gridTable", {
		"dicTypeName" : $("#dicTypeName_s").val().replace(/(^\s*)|(\s*$)/g, ""),
	});
}

/**
 * 添加字典类型
 */
function addDicType(methodname) {
	$("#dicTypeNameMethod").val('insert');
	method = methodname;
	clearDicType();
	$("label.error").remove();
	openDialog('add_update_DicType_Div',i18nShow('sys_dic_type_save'),350,250);
}

/**
 * 修改字典类型信息
 */
function modifyDicType(dicTypeCode,methodname){
	$("label.error").remove();
	$("#dicTypeNameMethod").val(methodname);
	method = methodname;//update
	$("#dicTypeCode").val(dicTypeCode);
	getDicType(dicTypeCode);
	
	openDialog('add_update_DicType_Div',i18nShow('sys_dic_type_update'),350,240);
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
function saveDicTypeBtn(){
	$("#add_update_DicType_Form").submit();  
}

/**
 * 保存字典类型信息
 */
function saveDicType(){
	var dicTypeCode=$("#dicTypeCode").val();
	if("insert"==method){
		dicTypeCode = null;
	}
	var dicTypeName=$("#dicTypeName").val();
	var createUser=$("#createUser").val();
	var createDatetime=$("#createDatetime").val();
	var remark=$("#remark").val();
	var beforeUpdateDicTypeName = $("#dicTypeNameCheck").val();
	var dataObj={
			"dicTypePo.dicTypeCode":dicTypeCode,
			"dicTypePo.dicTypeName":dicTypeName,
			"dicTypePo.createUser":createUser,
			"dicTypePo.createDateTime":createDatetime,
			"dicTypePo.remark":remark,
			"dicTypePo.beforeUpdateName":beforeUpdateDicTypeName
	};
    $("#saveDicType").attr({"disabled":true});
	$.ajax({
		async : false,
		cache : false,
		url : ctx + "/dic/saveDicType.action",
		data : dataObj,
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('tip_req_fail'));
		},
		success : function(datas){
			if(datas.result=="fail"){
				showTip(i18nShow('tip_sys_dic_type_save'));
			}else{
			searchDicType();
			closeView('add_update_DicType_Div');//关闭
		 }
			$("#saveDicType").attr({"disabled":false});
		}
	});
}

/**
 * 根据ID查询字典类型信息
 * @param nodeId
 */
function getDicType(dicTypeCode){
	$.ajax({
		async : false,
		cache : false,
		url : ctx + "/dic/findDicTypeByCode.action",
		data : {"dicTypePo.dicTypeCode":dicTypeCode},
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('tip_req_fail'));
		},
		success : function(datas){
			initDicType(datas);
		}
	});
}

/**
 * 删除字典类型
 */
function deleteDicType(dicTypeCode,dicTypeName){
	showTip(i18nShow('tip_delete_confirm'),function(){
		$.ajax({
			async : false,
			cache : false,
			url : ctx + "/dic/deleteDicType.action",
			data : {"dicTypePo.dicTypeCode":dicTypeCode,"dicTypePo.dicTypeName":dicTypeName},
			type:'post',
			dataType : "json",
			success : function(datas){
				if(datas.result=="0"){
					showError(i18nShow('tip_req_fail'));
				}else if(datas.result=="2"){
				    showError(i18nShow('tip_sys_dic_type_delete'));
				}
				searchDicType();
			}
		});
	});
}

/**
 * 初始化字典类型添加页面
 * @param userId
 */
function clearDicType(){
	$("#dicTypeCode").val("");
	$("#dicTypeName").val("");
	$("#remark").val("");
}

/**
 * 初始字典类型查看页面
 * @param datas
 */
function initDicType(datas) {
	$("#dicTypeCode").val(datas.dicTypeCode);
	$("#dicTypeName").val(datas.dicTypeName);
	$("#createUser").val(datas.createUser);
	$("#createDatetime").val(formatTime(datas.createDatetime));
	$("#remark").val(datas.remark);
	$("#dicTypeNameCheck").val(datas.dicTypeName);
}

/**
 * 关闭对话框
 * @param divId
 * @return
 */
function closeView(divId) {
	$("#" + divId).dialog("close");
}
