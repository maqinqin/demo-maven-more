<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<html>
<head>
<title>服务策略管理</title>
<script type="text/javascript" src="${ctx}/scripts/My97DatePicker/WdatePicker.js"></script>
<script type='text/javascript' src='${ctx}/scripts/json.js'></script>
<script type="text/javascript" src="${ctx}/common/javascript/main.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery-jiajian.js"></script>
<script type="text/javascript" charset="UTF-8">
window.onload=function(){
	// 自适应宽度
	var heightTotal =$(document).height()-270;
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
	});
	
		$("#gridTable")
				.jqGrid(
						{
							url : ctx+"/workflow/strategy/serviceStrategy_index.action", // 提交的action地址
							rownumbers : true, // 是否显示前面的行号
							datatype : "json", // 返回的数据类型
							mtype : "post", // 提交方式
							height : heightTotal,
							autowidth : true, // 是否自动调整宽度
							colNames : [ '服务名称', '执行路径', '返回码',
									'执行方式', '操作' ],
							colModel : [
									{
										name : "moduleName",
										index : "moduleName",
										label : "服务名称",
										width : 2,
										sortable : true,
									},
									{
										name : "exePath",
										index : "exePath",
										label : "执行路径",
										width : 5,
										sortable : true,
									},
									{
										name : "checkCode",
										index : "checkCode",
										label : "返回码",
										width : 1,
										sortable : true,
									},
									{
										name : "exeTypeCode",
										index : "exeTypeCode",
										label : "执行方式",
										width : 1,
										sortable : true,
									},
									{
										lable : '操作',
										name : 'op',
										index : 'op',
										width : 1,
										sortable : false,
										formatter : function(cellValue,
												options, rowObject) {
											return "<input type='button' title='修改' value='修改' class='btn_edit_s'"
													+ " onclick=\"udpateServiceDialog('"
													+ rowObject.moduleId
													+ "',false)\">"
													+ "<input type='button' style='margin-left:5px;' class='btn_del_s' value='删除' title='删除'"
													+ " onclick=\"removeModule('"
													+ rowObject.moduleId
													+ "')\">";
										}
									} ],
							viewrecords : true,
							sortname : "MODULE_NAME",
							sortorder : "asc",
							rowNum : 20,
							rowList : [ 5, 10, 15, 20, 30 ],
							prmNames : {
								search : "search"
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
	
	//实现搜索功能;
	function search() {
		$("#gridTable").jqGrid('setGridParam',{
			url:ctx+'/workflow/strategy/serviceStrategy_index.action',//你的搜索程序地址
			postData:{
				'moduleName':$("#tm_serviceName").val(),
				'exeTypeCode':$("#tm_exeTypeId").val()
				}, //发送搜索条件
			pager : "#gridPager"
            ,page : 1
		}).trigger("reloadGrid"); //重新载
	}

	//显示添加策略的对话窗口;
	function showDialog(title) {
		
		//初始化界面
		$('#serviceName').val("");
		$('#exePath').val("");
// 		$('#busTypeId').val("");
		$('#exeTypeId').val("");
		$('#isActive').val("");
		$('#checkCode').val("");
		$('#propTable tbody').html("");
		$('#saveServiceModuleBtn').prop("disabled",false);
		$('#operatorType').val("add");  //构建添加的操作;
		$("#isActive_y").attr("checked","checked");
		$("#isActive_n").attr("checked",false);

		/**
		 * 构建弹出窗口
		 */
		$("#component_div").dialog({
			autoOpen : true,
			modal : true,
			width : 825,
			height : 480
		});
		$("#component_div").css("width","90%");//对话框内容占满对话框
		$("#component_div").dialog("option", "title", title);
	}
	
	//验证form提交;
	function validateForm(){
		if($("#serviceName").val() == "")
		{
			showTip("服务名称不能为空");
			return false;
		}
		if($("#exePath").val() == "")
		{
			showTip("执行路径不能为空");
			return false;
		}
// 		if($("#busTypeId").val() == "")
// 		{
// 			showTip("请选择业务类型");
// 			return false;
// 		}
		if($("#exeTypeId").val() == "")
		{
			showTip("请选择执行方式");
			return false;
		}
		if($("#checkCode").val() == "")
		{
			showTip("返回码不为能空");
			return false;
		}
		return true;
	}

	//保存服务策略;
	function saveServiceModule() {
	
		//校验资源;
		var result = validateForm();
		//alert(result);
		if(!result){
			 return;
		}
		var paramKeys = "";
		var paramNames = "";
			
		for(var a = 0 ; a < $('#propTable tbody > tr').length ; a++){
			if($('#prop_'+a).css("display")!="none"){
				if($('#paramKey_'+a).val() == ""){
					alert("参数Key不能为空");
					return;
				}else if($('#paramName_'+a).val() == ""){
					alert("参数名称不能为空");
					return;
				}else if($('#paramType_'+a).val() == ""){
					alert("参数类型不能为空");
					return;
				}else if($('#paramTypeInput_'+a).val() == "" && $('#paramTypeInput_'+a).css("display")!="none"){
					alert("参数值缺省值不能为空");
					return;
				}else if($('#paramIOType_'+a).val() == ""){
					alert("参数值类型不能为空");
					return;
				}else if($('#paramOperateType_'+a).val() == ""){
					alert("操作类型不能为空");
					return;
				}else if(paramKeys.indexOf($('#paramKey_'+a).val()) > -1){
					alert("参数Key不能重复");
					return;
				}else if(paramNames.indexOf($('#paramName_'+a).val()) > -1){
					alert("参数名称不能重复");
					return;
				}
				paramKeys += $('#paramKey_'+a).val()+",";
				paramNames += $('#paramName_'+a).val()+",";
			}
		}
		
		/**
		 * 定义参数集合
		 */
		var paramsArr =  new Array();
		/**
		 * 对于隐藏行则不予保存
		 */
		for(var b = 0 ; b < $('#propTable tbody > tr').length ; b++){
			if($('#prop_'+b).css("display")!="none"){
				var jsonRequest = {
						"paramId":null,
						"paramKey":$('#paramKey_'+b).val(),
						"paramName":$('#paramName_'+b).val(),
						"paramValue":($('#paramType_'+b).val() == "SELECT") ? $('#paramTypeInput_'+b).val():($('#paramType_'+b).val() == "CUSTOM" ? $('#paramTypeCustom_'+b).val(): ($('#paramType_'+b).val() == "CUSTOM_1" ? $('#paramTypeInput1_'+b).val() : "")),
						"moduleId":null,
						"paramTypeCode":$('#paramType_'+b).val(),
						"paramIoCode":$("#paramIOType_"+b).val(),
						"paramOperCode":$("#paramOperateType_"+b).val(),
						"parentId":null
					};
				paramsArr[paramsArr.length] = jsonRequest;
			}
		}
		
		/**
		 * 构造服务模板信息对象
		 * 对于编辑和新增的操作取值有区别
		 */
		 
		//获取操作的类型;
		var operatorType = $("#operatorType").val();
		var moduleIdValue = "";
		 
		if(operatorType != null && operatorType == 'update'){
			moduleIdValue = $('#serviceId').val();
		}
		 
		var serviceModule = {
			"moduleId":moduleIdValue,
			"moduleName":$("#serviceName").val(),
			"exePath":$("#exePath").val(),
			"busTypeCode":"",
			"exeTypeCode":$('#exeTypeId').val(),
			"checkCode":$('#checkCode').val()+"",
			"isActive":$('input[name="isActive"]:checked').val()
		};
		
		
		//禁用保存按钮，避免重复提交
		$('#saveServiceModuleBtn').prop("disabled",true);
		
		/**
		 * 调用后台保存方法
		 */
		 $.ajax({
		     type : "POST",
		     url : ctx+"/workflow/strategy/serviceStrategy_addServiceModule.action",
		     data : {
		    	 'jsonData' : JSON.stringify({"bpmServiceModulePo":serviceModule,"list":paramsArr}),
		    	 'jsonClass' : 'com.git.cloud.workflow.model.vo.BpmServiceModuleParamVo',
		    	 'operatorType':operatorType
		     },
		     dataType:"json",
		     async:true,
		     cache:false,
		     success : function(data) {
			     showTip(data[0].msg);
				 $("#component_div").dialog("close");
				 $("#gridTable").jqGrid().trigger("reloadGrid");
		     },
		     error : function(e) {
		    	showError("error");
		      	$("#component_div").dialog("close");
		     }
		 });
	}

	var initTextNo = "";
	var initTextYes = "格式:key1,value1;key2,value2";
	var initTextYes1 = ".action或.jsp";
	/**
	 * 添加行方法实现asd
	 */
	function addTr() {
		var maxRow = $('#propTable tbody > tr').length;
		var inner = '';
		inner += '<tr id="prop_'+maxRow+'" style="height:34px">'
				+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" style="width:85%" id="paramKey_'
				+ maxRow
				+ '"/></span></div></td>'
				+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" style="width:85%" id="paramName_'
				+ maxRow
				+ '"/></span></div></td>'
				+ '<td height="22" bgcolor="#FFFFFF">'
				+ '<div align="center">'
				+ '<span>'
				+ '<select class="selInput" style="width:95%" id="paramType_'
				+ maxRow
				+ '" onchange="javascript:paramTypeChangeHandler('
				+ maxRow
				+ ')">'
				+ '<option value="INPUT" selected>文本类型</option>'
				+ '<option value="SELECT">选择类型</option>'
				+ '<option value="CUSTOM">定制类型</option>'
				+ '<option value="CUSTOM_1">自定义类型</option>'
				+ '</select>'
				+ '<input class="textInput2" type="text" value='+initTextYes+' onfocus="if(this.value==initTextYes){this.value=initTextNo}" onblur="if(this.value==initTextNo){this.value=initTextYes}" style="width:91%;display:none" id="paramTypeInput_'
				+ maxRow
				+ '"/>'
				+ '<input class="textInput2" type="text" value='+initTextYes1+' onfocus="if(this.value==initTextYes1){this.value=initTextNo}" onblur="if(this.value==initTextNo){this.value=initTextYes1}" style="width:91%;display:none" id="paramTypeInput1_'
				+ maxRow
				+ '"/>'
				//添加定制类型
				+ '<select class="selInput" style="width:95%;display:none" id="paramTypeCustom_'
				+ maxRow
				+ '" onchange="javascript:customTypeChangeHander('
				+ maxRow
				+ ')"'
				+ '">'
				+ '<option value="server" selected>服务器</option>'
				+ '<option value="shell">脚本</option>'
				+ '<option value="software" >介质</option>'
				+ '<option value="managementTarget" >管理对象</option>'
				+ '<option value="serverVarType" >服务器变量</option>'
				+ '<option value="fileVarType" >文件变量</option>'
				+ '</select>'
				
				+'</span>'
				+ '</div>'
				+ '</td>'
				+ '<td height="22" bgcolor="#FFFFFF">'
				+ '<div align="center">'
				+ '<span>'
				+ '<select class="selInput" style="width:95%" id="paramIOType_'
				+ maxRow
				+ '">'
				+ '<option value="IN" selected>输入</option>'
				+ '<option value="OUT">输出</option>'
				+ '</select>'
				+ '</span>'
				+ '</div>'
				+ '</td>'
				+ '<td height="22" bgcolor="#FFFFFF">'
				+ '<div align="center">'
				+ '<span>'
				+ '<select class="selInput" style="width:95%" id="paramOperateType_'
				+ maxRow
				+ '">'
				+ '<option value="NO-OPER" selected>无操作</option>'
				+ '<option value="BIND-BP">绑定业务全局参数</option>'
				+ '<option value="BIND-WP">绑定流程全局参数</option>'
				+ '<option value="BIND-BW">绑定业务及流程全局参数</option>'
				+ '</select>'
				+ '</span>'
				+ '</div>'
				+ '</td>'
// 				+ '<td height="22" bgcolor="#FFFFFF" style="text-align:center"><span class="tip_green" style="cursor: pointer;" onclick="javascript:hiddenRow(\'prop_'
// 				+ maxRow
// 				+ '\')" >删除行</span></td>'
				+ '<td style="text-align:center">'
				+ '<input class="btn_del_s" type="button" onclick="javascript:hiddenRow(\'prop_'+maxRow+'\')" title="删除" value="删除">'
				+ '</td>'
				+ '</tr>';
		/**
		 * 增加行追加到表格
		 */
		$('#propTable tbody').append(inner);
	}

	/**
	 * 删除行,不能物理删除，只是隐藏当前行
	 * @param id
	 */
	function hiddenRow(id) {
		showTip("您确认要删除此行参数数据吗?",function(){
			$('#' + id).css("display", "none");
		});
	}
	
	function paramTypeChangeHandler(rowNum){
		//参数key、name
		var paramKey = $("#paramKey_"+rowNum);
		var paramName = $("#paramName_"+rowNum);
		
		var $paramTypeSelect = $("#paramType_"+rowNum);
		var $paramTypeInput  = $("#paramTypeInput_"+rowNum);
		var $paramTypeCustom  = $("#paramTypeCustom_"+rowNum);
		var $paramTypeInput1  = $("#paramTypeInput1_"+rowNum);
		
		paramKey.removeAttr("disabled");
		paramName.removeAttr("disabled");
		
		if($paramTypeSelect.val() == "INPUT"){
			$paramTypeInput.css("display","none");
			$paramTypeInput1.css("display","none");
			$paramTypeCustom.css("display","none");
// 			paramKey.val('');
// 			paramName.val('');
		} else if ($paramTypeSelect.val() == "CUSTOM") {
			$paramTypeInput.css("display","none");
			$paramTypeInput1.css("display","none");
			$paramTypeCustom.css("display","block");
			
			paramKey.attr("disabled", "disabled");
			paramName.attr("disabled", "disabled");
			
			paramKey.val('SERVER_NAME');
			paramName.val('服务器名称');
		}else if($paramTypeSelect.val() == "SELECT"){
			$paramTypeCustom.css("display","none");
			$paramTypeInput1.css("display","none");
			$paramTypeInput.css("display","block");
// 			paramKey.val('');
// 			paramName.val('');
		} else if($paramTypeSelect.val() == "CUSTOM_1") {
			$paramTypeCustom.css("display","none");
			$paramTypeInput.css("display","none");
			$paramTypeInput1.css("display","block");
// 			paramKey.val('');
// 			paramName.val('');
		}
		
	}
	//修改定义物件下拉框时
	function customTypeChangeHander(rowNum){
		
		//参数key、name
		var paramKey = $("#paramKey_"+rowNum);
		var paramName = $("#paramName_"+rowNum);
		paramKey.attr("disabled", "disabled");
		paramName.attr("disabled", "disabled");
		var $paramTypeCustom  = $("#paramTypeCustom_"+rowNum);
		if($paramTypeCustom.val() == "server"){
			paramKey.val('SERVER_NAME');
			paramName.val('服务器名称');
		} else if ($paramTypeCustom.val() == "shell") {
			paramKey.val('SCRIPT_NAME');
			paramName.val('脚本名称');
		} else if ($paramTypeCustom.val() == "software") {
			paramKey.val('SOFTWARE_PACKAGE');
			paramName.val('介质名称');
		} else if ($paramTypeCustom.val() == "managementTarget") {
			paramKey.val('MANAGEMENT_TARGET');
			paramName.val('管理对象');
		} else if ($paramTypeCustom.val() == "serverVarType") {
			paramKey.val('SERVER');
			paramName.val('服务器变量');
		}else if ($paramTypeCustom.val() == "fileVarType") {
			paramKey.val('FILE');
			paramName.val('文件变量');
		}
	}
	
	/**
	 * 删除服务策略数据;
	 */
	function removeModule(mId){
		showTip("您确认要删除服务策略吗?",function(){
			$.ajax({
			     type : "POST",
			     url : ctx+"/workflow/strategy/serviceStrategy_deleteServiceModule.action",
			     data : {
			    	 "moduleId" : mId
			     },
			     dataType:"json",
			     async:true,
			     cache:false,
			     success : function(data) {
			    	 showTip(data[0].msg);
					 $("#gridTable").jqGrid().trigger("reloadGrid");
			     },
			     error : function(e) {
			    	 showTip("error");
			     }
			 });
		});
	}
	 
	 //修改服务策略数据;
		function udpateServiceDialog(mId) {
			
			//初始化参数;
			$('#operatorType').val("update");  //为修改的操作;
			
			$.ajax({
			     type : "POST",
			     url : ctx+"/workflow/strategy/serviceStrategy_updateServiceModule.action",
			     data : {
			    	 "moduleId" : mId
			     },
			     dataType:"json",
			     async:true,
			     cache:false,
			     success : function(data) {
			    	 //初始化服务策略对象数据;
			    	 initServiceModule(data,mId);
			    	 //初始化服务策略参数集合数据;
			    	 initServiceParams(data);
			    	
			     },
			     error : function(e) {
			      	alert("error");
			     }
			 });
			
			
			/**
			 * 构建弹出窗口
			 */
			$("#component_div").dialog({
				autoOpen : true,
				modal : true,
				width : 825,
				height : 580
			});
			$("#component_div").css("width","90%");//对话框内容占满对话框
			$("#component_div").dialog("option", "title", "修改服务策略");
		}
		
		//初始化服务策略对象数据;
		function initServiceModule(data,mId)
		{
			 $('#serviceName').val(data.bpmServiceModulePo.moduleName);
			 $('#exePath').val(data.bpmServiceModulePo.exePath);
// 			 $('#busTypeId').val(data.bpmServiceModulePo.busTypeCode);
			 $('#exeTypeId').val(data.bpmServiceModulePo.exeTypeCode);
			 $('#checkCode').val(data.bpmServiceModulePo.checkCode);
			 $('#propTable tbody').html("");
			 $('#saveServiceModuleBtn').prop("disabled",false);
			 $('#serviceId').val(mId);
			 if(data.bpmServiceModulePo.isActive == 'Y')
			 {
				 $("#isActive_y").attr("checked","checked");
				 $("#isActive_n").attr("checked",false);
			 }
			 else{
				 $("#isActive_n").attr("checked","checked");
				 $("#isActive_y").attr("checked",false);
			 }
			
		}
		
		//初始化服务策略参数集俣数据;
		function initServiceParams(data)
		{
			$.each(data.list,function(n,value) {
				var maxRow = n;
				var inner = '';
				var _paramType = value.paramTypeCode;
				var _typeStr = "";
				//判断类型生成字符串数据;
				if(_paramType == 'INPUT') {
					inner += '<tr id="prop_'+maxRow+'"style="height:34px;">'
					+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" value="'+value.paramKey+'" style="width:85%" id="paramKey_'
					+ maxRow
					+ '"/></span></div></td>'
					+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" value="'+value.paramName+'" style="width:85%" id="paramName_'
					+ maxRow
					+ '"/></span></div></td>'
					
					_typeStr = '<span>'
						+ '<select class="selInput" style="width:95%" id="paramType_'
						+ maxRow
						+ '" onchange="javascript:paramTypeChangeHandler('
						+ maxRow
						+ ')">'
						+ '<option value="INPUT" selected>文本类型</option>'
						+ '<option value="SELECT">选择类型</option>'
						+ '<option value="CUSTOM">定制类型</option>'
						+ '<option value="CUSTOM_1">自定义类型</option>'
						+ '</select>'
						+ '<input class="textInput2" type="text" style="width:95%;display:none" id="paramTypeInput_'
						+ maxRow
						+ '"/>'
						+ '<input class="textInput2" type="text" style="width:95%;display:none" id="paramTypeInput1_'
						+ maxRow
						+ '"/>'
						//添加定制类型
						+ '<select class="selInput" style="width:95%;display:none" id="paramTypeCustom_'
						+ maxRow
						+ '" onchange="javascript:customTypeChangeHander('
						+ maxRow
						+ ')"'
						+ '">'
						+ '<option value="server" selected>服务器</option>'
						+ '<option value="shell">脚本</option>'
						+ '<option value="software">介质</option>'
						+ '<option value="managementTarget" >管理对象</option>'
						+ '<option value="serverVarType" >服务器变量</option>'
						+ '<option value="fileVarType" >文件变量</option>'
						+ '</select>'
						+'</span>'
				} else if (_paramType == 'CUSTOM') {
					inner += '<tr id="prop_'+maxRow+'"style="height:34px">'
					+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" value="'+value.paramKey+'" style="width:85%;" disabled="disabled" id="paramKey_'
					+ maxRow
					+ '"/></span></div></td>'
					+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" value="'+value.paramName+'" style="width:85%;" disabled="disabled" id="paramName_'
					+ maxRow
					+ '"/></span></div></td>'
					
					_typeStr += '<span>'
						+ '<select class="selInput" style="width:95%" id="paramType_'
						+ maxRow
						+ '" onchange="javascript:paramTypeChangeHandler('
						+ maxRow
						+ ')">'
						+ '<option value="INPUT">文本类型</option>'
						+ '<option value="SELECT">选择类型</option>'
						+ '<option value="CUSTOM" selected>定制类型</option>'
						+ '<option value="CUSTOM_1">自定义类型</option>'
						+ '</select>'
						+ '<input class="textInput2" type="text" value='+initTextYes+' onfocus="if(this.value==initTextYes){this.value=initTextNo}" onblur="if(this.value==initTextNo){this.value=initTextYes}" style="width:91%;display:none" id="paramTypeInput_'
						+ maxRow
						+ '"/>'
						+ '<input class="textInput2" type="text" value='+initTextYes1+' onfocus="if(this.value==initTextYes){this.value=initTextNo}" onblur="if(this.value==initTextNo){this.value=initTextYes}" style="width:91%;display:none" id="paramTypeInput1_'
						+ maxRow
						+ '"/>'
						//添加定制类型
						+ '<select class="selInput" style="width:95%;" id="paramTypeCustom_'
						+ maxRow
						+ '" onchange="javascript:customTypeChangeHander('
						+ maxRow
						+ ')"'
						+ '">'
						if(value.paramValue == 'server') {
							_typeStr += '<option value="server" selected>服务器</option>'
							+ '<option value="shell">脚本</option>'
							+ '<option value="software">介质</option>'
							+ '<option value="managementTarget" >管理对象</option>'
							+ '<option value="serverVarType" >服务器变量</option>'
							+ '<option value="fileVarType" >文件变量</option>'
						} else if (value.paramValue == 'shell') {
							_typeStr +=   '<option value="server" >服务器</option>'
							+ '<option value="shell" selected>脚本</option>'
							+ '<option value="software">介质</option>'
							+ '<option value="managementTarget" >管理对象</option>'
							+ '<option value="serverVarType" >服务器变量</option>'
							+ '<option value="fileVarType" >文件变量</option>'
						} else if (value.paramValue == 'software') {
							_typeStr +=   '<option value="server" >服务器</option>'
							+ '<option value="shell" >脚本</option>'
							+ '<option value="software" selected>介质</option>'
							+ '<option value="managementTarget" >管理对象</option>'
						} else if (value.paramValue == 'managementTarget') {
							_typeStr +=   '<option value="server" >服务器</option>'
								+ '<option value="shell" >脚本</option>'
								+ '<option value="software">介质</option>'
								+ '<option value="managementTarget" selected>管理对象</option>'
								+ '<option value="serverVarType" >服务器变量</option>'
								+ '<option value="fileVarType" >文件变量</option>'
						} else if (value.paramValue == 'serverVarType') {
							_typeStr +=   '<option value="server" >服务器</option>'
								+ '<option value="shell" >脚本</option>'
								+ '<option value="software">介质</option>'
								+ '<option value="managementTarget" >管理对象</option>'
								+ '<option value="serverVarType" selected>服务器变量</option>'
								+ '<option value="fileVarType" >文件变量</option>'
						} else if (value.paramValue == 'fileVarType') {
							_typeStr +=   '<option value="server" >服务器</option>'
								+ '<option value="shell" >脚本</option>'
								+ '<option value="software">介质</option>'
								+ '<option value="managementTarget" >管理对象</option>'
								+ '<option value="serverVarType" >服务器变量</option>'
								+ '<option value="fileVarType" selected>文件变量</option>'
						}
						_typeStr +=  '</select>'
						+'</span>'
				} else if (_paramType == 'SELECT') {
					inner += '<tr id="prop_'+maxRow+'"style="height:34px">'
					+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" value="'+value.paramKey+'" style="width:85%" id="paramKey_'
					+ maxRow
					+ '"/></span></div></td>'
					+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" value="'+value.paramName+'" style="width:85%" id="paramName_'
					+ maxRow
					+ '"/></span></div></td>'
					
					_typeStr = '<span>'
						+ '<select class="selInput" style="width:95%" id="paramType_'
						+ maxRow
						+ '" onchange="javascript:paramTypeChangeHandler('
						+ maxRow
						+ ')">'
						+ '<option value="INPUT">文本类型</option>'
						+ '<option value="SELECT" selected>选择类型</option>'
						+ '<option value="CUSTOM">定制类型</option>'
						+ '<option value="CUSTOM_1">自定义类型</option>'
						+ '</select>'
						+ '<input class="textInput2" type="text" value='+value.paramValue+' onfocus="if(this.value==initTextYes){this.value=initTextNo}" onblur="if(this.value==initTextNo){this.value=initTextYes}" style="width:91%;" id="paramTypeInput_'
						+ maxRow
						+ '"/>'
						+ '<input class="textInput2" type="text" value='+initTextYes1+' onfocus="if(this.value==initTextYes1){this.value=initTextNo}" onblur="if(this.value==initTextNo){this.value=initTextYes1}" style="width:91%;display:none" id="paramTypeInput1_'
						+ maxRow
						+ '"/>'
						//添加定制类型
						+ '<select class="selInput" style="width:95%;display:none" id="paramTypeCustom_'
						+ maxRow
						+ '" onchange="javascript:customTypeChangeHander('
						+ maxRow
						+ ')"'
						+ '">'
						+ '<option value="server" selected>服务器</option>'
						+ '<option value="shell">脚本</option>'
						+ '<option value="software" >介质</option>'
						+ '<option value="managementTarget" >管理对象</option>'
						+ '<option value="serverVarType" >服务器变量</option>'
						+ '<option value="fileVarType" >文件变量</option>'
						+ '</select>'
						+'</span>'
				} else if(_paramType == 'CUSTOM_1') {
					inner += '<tr id="prop_'+maxRow+'"style="height:34px">'
					+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" value="'+value.paramKey+'" style="width:85%" id="paramKey_'
					+ maxRow
					+ '"/></span></div></td>'
					+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" value="'+value.paramName+'" style="width:85%" id="paramName_'
					+ maxRow
					+ '"/></span></div></td>'
					
					_typeStr = '<span>'
						+ '<select class="selInput" style="width:95%" id="paramType_'
						+ maxRow
						+ '" onchange="javascript:paramTypeChangeHandler('
						+ maxRow
						+ ')">'
						+ '<option value="INPUT">文本类型</option>'
						+ '<option value="SELECT">选择类型</option>'
						+ '<option value="CUSTOM">定制类型</option>'
						+ '<option value="CUSTOM_1" selected>自定义类型</option>'
						+ '</select>'
						+ '<input class="textInput2" type="text" value='+value.paramValue+' onfocus="if(this.value==initTextYes1){this.value=initTextNo}" onblur="if(this.value==initTextNo){this.value=initTextYes1}" style="width:91%;" id="paramTypeInput1_'
						+ maxRow
						+ '"/>'
						+ '<input class="textInput2" type="text" value='+initTextYes+' onfocus="if(this.value==initTextYes){this.value=initTextNo}" onblur="if(this.value==initTextNo){this.value=initTextYes}" style="width:91%;display:none" id="paramTypeInput1_'
						+ maxRow
						+ '"/>'
						
						//添加定制类型
						+ '<select class="selInput" style="width:95%;display:none" id="paramTypeCustom_'
						+ maxRow
						+ '" onchange="javascript:customTypeChangeHander('
						+ maxRow
						+ ')"'
						+ '">'
						+ '<option value="server" selected>服务器</option>'
						+ '<option value="shell">脚本</option>'
						+ '<option value="software" >介质</option>'
						+ '<option value="managementTarget" >管理对象</option>'
						+ '<option value="serverVarType" >服务器变量</option>'
						+ '<option value="fileVarType" >文件变量</option>'
						+ '</select>'
						+'</span>'
				}
				
				//判断输入输出;
				var _paramIOType = value.paramIoCode;
				var ioStr = "";
				if(_paramIOType == 'IN')
				{
					ioStr = '<option value="IN" selected>输入</option>'
					+ '<option value="OUT">输出</option>';
				}
				else{
					ioStr = '<option value="IN">输入</option>'
					+ '<option value="OUT" selected>输出</option>';
				}
				
				//判断操作类型;
				var _paramOperateType = value.paramOperCode;
				var operStr = ""
				if(_paramOperateType == 'NO-OPER')
				{
					operStr = '<option value="NO-OPER" selected>无操作</option>'
					+ '<option value="BIND-BP">绑定业务全局参数</option>'
					+ '<option value="BIND-WP">绑定流程全局参数</option>'
					+ '<option value="BIND-BW">绑定业务及流程全局参数</option>';
				}
				else if(_paramOperateType == 'BIND-BP'){
					operStr = '<option value="NO-OPER">无操作</option>'
						+ '<option value="BIND-BP" selected>绑定业务全局参数</option>'
						+ '<option value="BIND-WP">绑定流程全局参数</option>'
						+ '<option value="BIND-BW">绑定业务及流程全局参数</option>';
				}
				else if(_paramOperateType == 'BIND-WP'){
					operStr = '<option value="NO-OPER">无操作</option>'
						+ '<option value="BIND-BP">绑定业务全局参数</option>'
						+ '<option value="BIND-WP" selected>绑定流程全局参数</option>'
						+ '<option value="BIND-BW">绑定业务及流程全局参数</option>';
				}
				else if(_paramOperateType == 'BIND-BW'){
					operStr = '<option value="NO-OPER">无操作</option>'
						+ '<option value="BIND-BP">绑定业务全局参数</option>'
						+ '<option value="BIND-WP">绑定流程全局参数</option>'
						+ '<option value="BIND-BW" selected>绑定业务及流程全局参数</option>';
				}
				
				/* inner += '<tr id="prop_'+maxRow+'"style="height:34px">'
						+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" value="'+value.paramKey+'" style="width:85%" id="paramKey_'
						+ maxRow
						+ '"/></span></div></td>'
						+ '<td height="22" bgcolor="#FFFFFF"><div><span><input class="textInput2" type="text" value="'+value.paramName+'" style="width:85%" id="paramName_'
						+ maxRow
						+ '"/></span></div></td>' */
					inner += '<td height="22" bgcolor="#FFFFFF">'
						+ '<div align="center">'
						+_typeStr
						+ '</div>'
						+ '</td>'
						+ '<td height="22" bgcolor="#FFFFFF">'
						+ '<div align="center">'
						+ '<span>'
						+ '<select class="selInput" style="width:95%" id="paramIOType_'
						+ maxRow
						+ '">'
						+ioStr
						+ '</select>'
						+ '</span>'
						+ '</div>'
						+ '</td>'
						+ '<td height="22" bgcolor="#FFFFFF">'
						+ '<div align="center">'
						+ '<span>'
						+ '<select class="selInput" style="width:95%" id="paramOperateType_'
						+ maxRow
						+ '">'
						+operStr
						+ '</select>'
						+ '</span>'
						+ '</div>'
						+ '</td>'
						+ '<td height="22" bgcolor="#FFFFFF" style="text-align:center"><span class="tip_green" style="cursor:pointer;" onclick="javascript:hiddenRow(\'prop_'
						+ maxRow
						+ '\')">删除行</span></td>'
						+ '</tr>';
				/**
				 * 增加行追加到表格
				 */
				$('#propTable tbody').append(inner);
			});
		}
		
		//关闭窗口;
		function closeMy(){
			$("#component_div").dialog("close");
		}

</script>
<style type="text/css">
html,body {
	height: 100%;
}
.table_l th{background:#F2F5F6; padding-left:5px; text-align:left; font-weight:bold;}
.table_l td{padding-left:5px;}
</style>
<link rel="stylesheet"
	href="${ctx}/common/css/search_new.css" type="text/css"></link>

</head>

<body class="main1">
	<div class="content-main clear">
		<div class="panelTop">
			<h2>
				服务策略
			</h2>
			</div>
		<div id="topDiv" class="pageFormContent">
		   
		<div class="mainSeach3_InputWrap jiaoBen_seachWrap" style="height:45px; padding-top:14px; width:100%;border-top: 0px;">
			<table height="12%" width="97%" align="center">
				<tr>
					<td width="8%" align="right">服务名称：</td>
					<td><input type="text" id="tm_serviceName"
						name="tm_serviceName" class="textInput" style="width:150px"/></td>
					<td width="8%" align="right">执行方式：</td>
					<td><select id="tm_exeTypeId" name="tm_exeTypeId" class="selInput" />
							<option value="" selected>请选择...</option>
							<option value="API">API执行</option>
							<option value="SCRIPT">脚本执行</option>
							<option value="COMMAND">命令执行</option>
							<option value="INF">外部调用</option> </select></td>
<!-- 					<td align="right" >查询方式：</td> -->
<!-- 					<td width="20%"><select name="selType" id="selType" class="selInput"> -->
<!-- 							<option value="0">请选择...</option> -->
<!-- 							<option value="MODULE_NAME">服务名称</option> -->
<!-- 							<option value="EXE_TYPE_CODE">执行方式</option> -->
<!-- 					</select></td> -->
<!-- 					<td><input type="text" id="selContent" name="selContent" class="textInput" /></td> -->
                         <td  align="right" colspan="4">
                         <a class="btn" href="javascript:search()" title="查询" onclick="search();return false;"><span class="icon iconfont icon-icon-search"></span><span class="text">查询</span></a>
						<a href="javascript:void(0)" class="btn btn-green" title="添加" onclick="showDialog('新建服务策略');return false;"><span class="icon iconfont icon-icon-add"></span><span class="text">添加</span></a>
					    </td>
				</tr>
			</table>
			</div>
		</div>
		<div class="panel clear" id="gridMain">
			<table id="gridTable"></table>
			<table id="gridPager"></table>
		</div>
	</div>

	<!-- 添加框服务策略对话框  start -->
	<form id="serviceModuleform" method="post">
		<div id="component_div" class="pageFormContent" style="display: none;">
				<input type="hidden" id="componentId" name="componentId" /> <input
					type="hidden" id="isActive" name="isActive" />
				<table width="100%" cellpadding="0"	cellspacing="0"  style="line-height: 50px;">
					<tr>
						<td><font class="font_r" color="red">*</font>服务名称</td>
						<td><input type="text" id="serviceName" name="serviceName"
							class="textInput" /></td>
							
						<td><font class="font_r" color="red">*</font>执行方式</td>
						<td><select id="exeTypeId" name="exeTypeId"
							class="selInput" />
							<option value="" selected>请选择...</option>
							<option value="API">API执行</option>
							<option value="SCRIPT">脚本执行</option>
							<option value="COMMAND">命令执行</option>
							<option value="INF">外部调用</option> </select></td>
					</tr>
					<tr>
						<td><font class="font_r" color="red">*</font>执行路径</td>
						<td><textarea id="exePath" name="exePath"
							rows="2" style="width: 400px;"></textarea></td>
						<td><font class="font_r" color="red">*</font>返回码</td>
						<td><input type="text" id="checkCode" name="checkCode"
							class="textInput" /></td>
					</tr>
					<tr>
						<td><font class="font_r" color="red">*</font>是否激活</td>
						<td><input type="radio" value="Y" id="isActive_y"
							name="isActive" checked="checked" />是 <input type="radio"
							value="N" id="isActive_n" name="isActive" />否</td>
					</tr>
				</table>

			<!-- 添加/修改策略属性  start -->
			<div id="tabs-1" style="height: 200px; overflow-y: auto; overflow-x: hidden; position: relative; border-bottom-style: none;">
				<!-- 通过这个值来判断是什么样的操作(修改/添加) -->
				<input type="hidden" id="operatorType" name="operatorType" /> <input
					type="hidden" id="serviceId" name="serviceId" />
				<table width="100%" cellpadding="0" cellspacing="0"
					style="border: 1px solid #E4EAEC;">
					<tr>
						<td width="100" id="addRow"
							style="padding-left: 5px; border:1px solid #E4EAEC; background:#E4EAEC;">
							<label style="font-size: 14px;font-weight: bolder; line-height:32px;">服务策略参数</label>
							</td></tr>
					
					<tr><td height="32">
							<a href="javascript:void(0)" style="border:none; float: right;"  class="btn" title="添加" onclick="javascript:addTr();"><span class="icon iconfont icon-icon-add"></span><span class="text">添加</span></a>
							</td>
							</tr>
					<tr>
						<td >
							<table width="100%" class="table_l" id="propTable"
								border="0">
								<thead>
									<tr>
										<th width="20%" height="32">参数Key</th>
										<th width="12%" height="32">参数名称</th>
										<th width="28%" height="32">参数类型</th>
										<th width="8%" height="32">参数值状态</th>
										<th width="10%" height="22">操作类型</th>
										<th width="7%" height="22">基本操作</th>
									</tr>
									
								</thead>
								<tbody>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<!-- 添加/修改策略属性 end -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:20px;">
				<tr>
					<td align="center">
						<a href="javascript:void(0)" class="btn" title="保存" id="saveServiceModuleBtn" onclick="saveServiceModule();return false;"><span class="icon iconfont icon-icon-save"></span><span class="text">保存</span></a>
						<a href="javascript:void(0)" class="btn btn-green" title="取消" id="cancelScriptBtn" onclick="closeMy();return false;"><span class="icon iconfont icon-icon-cancel"></span><span class="text">取消</span></a>
						</td>
				</tr>
			</table>
		</div>
	</form>

	<!-- 添加服务策略弹出框 end -->


</body>
</html>
