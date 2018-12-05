/**
 * 提交服务策略表单参数时调用,用来获取key及对应的value值
 */
var servicePolicyParamFormDefinition = null;
var serviceParamKeyName = [];
var currentHeight = 300;
/**
 * 页面初始化
 */
$(document).ready(function() {
	if($('#state').val() == "design"){
		var params = $('#dNodeId').val();
		var nodeObjTemp = null;
		try{
			nodeObjTemp = parent.TankFlow.getComponentContent(params);
			//如果有特殊字符替换之，避免出现josn解析错误
			//nodeObjTemp.json.replace()
			//获取json数据，初始化上面基本数据
			jsonData = JSON.parse(nodeObjTemp.json);
		}catch(e){
			showTip("解析数据出错！");
		}finally{
			if(nodeObjTemp!=null && nodeObjTemp!=""){
				$('#tNodeName').val(nodeObjTemp.name);
				$("#typeId").val(nodeObjTemp.compId);
				$("#typeName").val("命令执行");
			}
		}
		var jsonTemp = nodeObjTemp.json;
		var regn = new RegExp("\n","g");
		var regt = new RegExp("\t","g");
		jsonTemp = jsonTemp.replace(regn,"");
		jsonTemp = jsonTemp.replace(regt,"");
//		jsonTemp = jsonTemp.replace("\"","&quot;");
		console.log(jsonTemp);
		jsonData = JSON.parse(jsonTemp);
		if(jsonData!= null){
			
			if(jsonData.outTime){
				$("#outtime").val(jsonData.outTime);
			}
			
			if(jsonData.isAutoNode){
				$("#isAutoNode").prop("checked",jsonData.isAutoNode == "Y" ? true : false);
			}
			
			if(jsonData.exceptionCode){
				$("#exceptionCode").val(jsonData.exceptionCode);
				if('REPEAT'==jsonData.exceptionCode){
					$('#exceptionRepeatDiv').css('display','inline-block');
					$('#exceptionRepeat').val(jsonData.exceptionRepeat);
				}else{
					$('#exceptionRepeatDiv').css('display','none');
				}
			}
			if(jsonData.serPolicyId){
				//策略表单初始化
				buildModulePolicyParamTree(jsonData.serPolicyId);
			}
			
			if(jsonData.commandContent){
				$("#commandContent").val(restoreSpecial(jsonData.commandContent));
			}
			if(jsonData.outtimeAlarm){
				$("#outtimeAlarm").prop("checked",jsonData.outtimeAlarm == "Y" ? true : false);
			}
			if(jsonData.exceptionAlarm){
				$("#exceptionAlarm").prop("checked",jsonData.exceptionAlarm == "Y" ? true : false);
			}
		}
		
		// 获取服务策略数据----苗有虎
		$.ajax({
					type : "POST",
					url : ctx
							+ "/workflow/strategy/serviceStrategy_selAllServiceStratey.action?busType=COMMAND",
					datatype : "json", // 返回的数据类型
					async : true,
					cache : false,
					success : function(data) {
						var htmlStr = '<option value="">请选择...</option>';
						for ( var i = 0; i < data.length; i++) {
							var opt = data[i];
							htmlStr += '<option value="'
									+ opt.moduleId + '">'
									+ opt.moduleName
									+ '</option>';
						}
						$(htmlStr).appendTo(
								$('#servicePolicyCode'));

						if(jsonData != null)
							$("#servicePolicyCode").val(
									jsonData.serPolicyId);

					},
					error : function(e) {
						alert("error");
					}
				});
		$('#exceptionCode').change(function(){
			var p1=$(this).children('option:selected').val();
			if('REPEAT'==p1){
					$('#exceptionRepeatDiv').css('display','inline-block');
			}else{
				$('#exceptionRepeatDiv').css('display','none');	
			}
		});
	}else if($('#state').val() == "definition"){
		//获取modelnode信息
		$.ajax({
		    type : "POST",
			url:$('#url').val()+"/baseForm/getCommandFormContentAct.action",
			data : {
		    	 "_fw_service_id":"getCommandFormContentSrv",
		    	 "modelId":$('#modelId').val(),
		    	 "nodeId":$('#nodeId').val(),
		    	 "typeId":$('#typeId').val()
		    },
		    async:true,
		    cache:false,
		    success : function(data) {
		    	var resultObj = stringToJson(data);//结果集
		    	initForm(resultObj,false);
		    },
		    error : function(e) {
		      	alert("error");
		    }
		});
	}else{
		var isDisabled = $('#userAdmin').val()!="userAdmin";
		if(isDisabled){
			$('#tNodeName').prop('disabled',true);
			$("#typeId").prop('disabled',true);
			$("#outtime").prop('disabled',true);
			$("#exceptionCode").prop('disabled',true);
			$("#isAutoNode").prop('disabled',true);
			$("#servicePolicyCode").prop('disabled',true);
			$("#commandContent").prop('disabled',true);
			$("#table_btn").css('display','none');
		}
		//当前为instance阶段，获取modelnode信息
		$.ajax({
		    type : "POST",
		    url : ctx + "/workflow/instance/bpmInstance_getCommandFormContentAtInstance.action",
			data : {
				 "bpmInstanceNodePo.instanceId" :$('#instanceId').val(),
		    	 "bpmInstanceNodePo.wfNodeId":$('#nodeId').val()
		    },
		    async:true,
		    cache:false,
		    success : function(data) {
		    	var resultObj = data;//结果集
		    	initForm(resultObj,isDisabled);
		    },
		    error : function(e) {
		      	showTip("error");
		    }
		});
	}
	
	resetHeight(currentHeight);
	
	//添加一个校验方法，可以禁止用户在节点名字段输入逗号“,”
	$.validator.addMethod("forbidComma", function(value) {
		for(var i=0; i<value.length; i++){
			if(value[i] == ","){
				return false;
			}
		}
		return true;
	}, '节点名中不能包含逗号'); 
	
	//设置验证
	$("#serviceModuleForm").validate({
		rules: {
			"tNodeName"			:{"required":true,"maxlength":100,"forbidComma":true},
			"outtime"			:{"required":true,"maxlength":10,"digits":true},
			"exceptionCode"		:{"required":true},
			"servicePolicyCode"	:{"required":true}
		},
		messages: {
			"tNodeName"			:{"required":"请输入节点名称","maxlength":"字符长度不得大于50","forbidComma":"节点名中不能包含逗号"},
			"outtime" 			:{"required":"请输入超时时间（整数）","maxlength":10,"digits":"请输入整数"},
			"exceptionCode"		:{"required":"请选择异常处理类型"},
			"servicePolicyCode"	:{"required":"请选择服务策略"}
			
		},
		submitHandler: function() {
			onSave();
		}
	});
});

function initForm(resultObj,isDisabled){
	if(resultObj){
		 jsonData = resultObj.json;//模型节点
	   	 if(jsonData){
			 $('#tNodeName').val(jsonData.nodeName);//设置节点名称
	 		 $("#outtime").val(jsonData.outTime);
			 $("#isAutoNode").prop("checked",jsonData.isAutoNode == "Y" ? true : false);
			 $("#exceptionCode").val(jsonData.exceptionCode);
	   	 }
	  // 获取服务策略数据----苗有虎
	 	$.ajax({
	 				type : "POST",
	 				url : ctx
	 						+ "/workflow/strategy/serviceStrategy_selAllServiceStratey.action?busType=COMMAND",
	 				datatype : "json", // 返回的数据类型
	 				async : true,
	 				cache : false,
	 				success : function(data) {
	 					var htmlStr = '<option value="">请选择...</option>';
	 					for ( var i = 0; i < data.length; i++) {
	 						var opt = data[i];
	 						htmlStr += '<option value="'
	 								+ opt.moduleId + '">'
	 								+ opt.moduleName
	 								+ '</option>';
	 					}
	 					$(htmlStr).appendTo(
	 							$('#servicePolicyCode'));

	 					$("#servicePolicyCode").val(
	 							jsonData.serPolicyId);

	 				},
	 				error : function(e) {
	 					showTip("error");
	 				}
	 			});
	   	 
	 	if(jsonData.serPolicyId){
			//策略表单初始化
			buildModulePolicyParamTree(jsonData.serPolicyId);
		}
//	   	 if(resultObj.serviceModuleParams){
//	   		 //节点参数表单构建
//		     buildModulePolicyParamTree(resultObj.serviceModuleParams,isDisabled);
//		     servicePolicyParamFormDefinition = resultObj.serviceModuleParams;
//	   	 }
	   	 if(resultObj.nodeCommands){
	   		 $('#commandContent').val(resultObj.nodeCommands);
	   	 }
	 }
}

function componentSave(){
	$("#serviceModuleForm").submit();
}
function onSave(){
	/**
	 * 返回json格式的字符串
	 * 根据servicePolicyParamFormDefinition的id-value格式进行json封装
	 */
	var innerArr = [];
	for ( var i = 0; i < serviceParamKeyName.length; i++) {
		innerObj = {};
		innerObj.id = serviceParamKeyName[i];
		innerObj.value = $('#' + serviceParamKeyName[i]).val();
		innerArr.push(innerObj);
	}
	
	var objTemp = {};
	objTemp.compId = $("#typeId").val();
	objTemp.name = $("#tNodeName").val();
	objTemp.outTime = $("#outtime").val();	
	objTemp.outtimeAlarm = $('#outtimeAlarm').prop("checked") == true ? "Y" : "N";
	objTemp.exceptionCode = $("#exceptionCode").val();
	objTemp.exceptionAlarm = $('#exceptionAlarm').prop("checked") == true ? "Y" : "N";
	objTemp.exceptionRepeat = $('#exceptionRepeat').val();
	objTemp.isAutoNode  = $("#isAutoNode").prop("checked") == true ? "Y" : "N";
	objTemp.serPolicyId = $("#servicePolicyCode").val();
	objTemp.serPolicyCode = $("#servicePolicyCode").val();
	//保存前将commandContent中的双引号转换为#quot#避免json解析出错
	objTemp.commandContent = replaceSpecial($("#commandContent").val());
	objTemp.serPolicy = innerArr;
	
	/**
	 * 判定来自流程设计器-design/流程模板-model/流程实例-instance
	 */
	if($('#state').val() == "design"){
		window.parent.TankFlow.saveComponentNameData($('#dNodeId').val(),
				$('#tNodeName').val(),JSON.stringify(objTemp));
	}else if($('#state').val() == "definition"){
		objTemp.modelId=$('#modelId').val();
		objTemp.nodeId=$('#nodeId').val();
		objTemp.nodeName = $("#tNodeName").val();
		objTemp.typeId = $("#typeId").val();
		$.ajax({
		     type : "POST",
		     url : $('#url').val()+"/baseForm/saveCommandFormContentAct.action",
		     data : {
		    	 "_fw_service_id":"saveCommandFormContentSrv",
		    	 "jsonData":JSON.stringify(objTemp),
		    	 'jsonClass' : 'com.ccb.iomp.cloud.data.vo.workflow.BpmSaveModelNodeCommandFormVo'
		     },
		     async:true,
		     cache:false,
		     success : function(data) {
		    	 window.parent.TankFlow.saveComponentNameData($('#dNodeId').val(),$('#tNodeName').val());
		     },
		     error : function(e) {
		      	alert("error");
		     }
		 });
	}else{
		var isDisabled = $('#userAdmin').val() != "userAdmin";
		if (isDisabled) {
			$('#tNodeName').prop('disabled', true);
			$("#typeId").prop('disabled', true);
			$("#outtime").prop('disabled', true);
			$("#exceptionCode").prop('disabled', true);
			$("#isAutoNode").prop('disabled', true);
			$("#servicePolicyCode").prop('disabled', true);
			$("#scriptCode").prop('disabled', true);
			$("#table_btn").css('display', 'none');
			$("#helpContent").css('display', 'none');
		}

		//当前为instance阶段，获取modelnode信息
		var bpmSaveInstanceNodeCommandFormVo = {
				"outTime" : $("#outtime").val(),
		 		"exceptionCode" : $("#exceptionCode").val(),
		 		"isAutoNode"  : $("#isAutoNode").prop("checked") == true ? "Y" : "N",
		 		"serPolicyId" : $("#servicePolicyCode").val(),
		 		"serPolicyCode" : $("#servicePolicyCode").val(),
		 		"serPolicy" : innerArr,
		 		"instanceId" : $('#instanceId').val(),
		 		"nodeId" : $('#nodeId').val(),
		 		"nodeName" : $("#tNodeName").val(),
		 		"commandContent" :  $("#commandContent").val()
		};
		$.ajax({
		     type : "POST",
		     url : ctx + "/workflow/instance/bpmInstance_saveCommandFormContentAtInstance.action",
		     data : {
		    	 jsonData : JSON.stringify(bpmSaveInstanceNodeCommandFormVo),
				 jsonClass : "com.git.cloud.workflow.model.vo.BpmSaveInstanceNodeCommandFormVo"
		     },
		     async:true,
		     cache:false,
		     success : function(data) {
		    	 window.parent.closeUserComponent(data);
		     },
		     error : function(e) {
		      	showTip("error");
		     }
		 });
	}
}
//替换特别字符，比如双引号
function replaceSpecial(s){
	return s.replace(/"/g,"#quot#");
}
//还原特别字符，比如双引号
function restoreSpecial(s){
	return s.replace(/#quot#/g,'"');
}
//关闭窗口
function cancel(){
	window.parent.TankFlow.closeComponentDialog();
}

/**
* 字符串形式转json对象
*/
function stringToJson(stringValue) {
	eval("var theJsonValue = " + stringValue);
	return theJsonValue;
}
/**
 * 重新计算表单高度
 */
function resetHeight(height){
	window.parent.TankFlow.setFrameHeight(height);
}

/**
 * 创建表单;
 * 
 * @param result
 * @param isDisabled
 */
function initModulePolicyParamTree(result, isDisabled) {
	$('#servicePolicyParamTable').html("");

	var inner = '';
	var disabled = isDisabled ? "disabled" : "";

	var tmepParamKeyName = [];

	for ( var a in result) {
		// 持有jsonData.serPolicy中相应key的值
		var val = '';

		// 如果已经提交过表单，则保存其值以备填充表单
		if (jsonData != null && jsonData.serPolicy) {
			for ( var i = 0; i < jsonData.serPolicy.length; i++) {
				if (result[a].paramKey == jsonData.serPolicy[i].id) {
					val = jsonData.serPolicy[i].value;
					break;
				}
			}
		}

		// 保存所有参数名称
		tmepParamKeyName.push(result[a].paramKey);

		if (a == 0 || a % 2 == 0) {
			inner += '<tr>';
			inner += '<td width="120" style="text-align:left;padding:5px 0px 5px 5px;" class="form_win_label">'
					+ result[a].paramName
					+ '</td>';
			if (result[a].paramTypeCode == "SELECT") {
				inner += '<td style="text-align:left;padding:5px 0px 5px 5px;">';
				inner += '	<div class="select_border">';
				inner += '		<div class="select_container">';
				inner += '			<select id="' + result[a].paramKey + '" name="'
						+ result[a].paramKey + '" class="form_select" '
						+ disabled + '>';
				var selectValue = result[a].paramValue.split(";");
				for ( var b in selectValue) {
					var selectValueItem = selectValue[b].split(",");
					if (selectValueItem[0] == val)
						inner += '<option value="' + selectValueItem[0]
								+ '" selected>' + selectValueItem[1]
								+ '</option>';
					else
						inner += '<option value="' + selectValueItem[0] + '">'
								+ selectValueItem[1] + '</option>';
				}
				inner += '			</select>';
				inner += '		</div>';
				inner += '	</div>';
				inner += '</td>';
			} else {
				inner += '<td style="text-align:left;padding:5px 0px 5px 5px;">';
				inner += '<input type="text" id="' + result[a].paramKey
						+ '" name="' + result[a].paramKey + '" value="' + val
						+ '" class="form_input" ' + disabled + '/>';
				inner += '</td>';
			}
		} else {
			inner += '<td width="120" style="text-align:left;padding:5px 0px 5px 5px;" class="form_win_label">'
					+ result[a].paramName
					+ '</td>';
			if (result[a].paramTypeCode == "SELECT") {

				inner += '<td style="text-align:left;padding:5px 0px 5px 5px;">';
				inner += '	<div class="select_border">';
				inner += '		<div class="select_container">';
				inner += '			<select id="' + result[a].paramKey + '" name="'
						+ result[a].paramKey + '" class="form_select" '
						+ disabled + '>';

				var selectValue = result[a].paramValue.split(";");

				for ( var b in selectValue) {
					var selectValueItem = selectValue[b].split(",");
					if (selectValueItem[0] == val)
						inner += '<option value="' + selectValueItem[0]
								+ '" selected>' + selectValueItem[1]
								+ '</option>';
					else
						inner += '<option value="' + selectValueItem[0] + '">'
								+ selectValueItem[1] + '</option>';
				}
				inner += '			</select>';
				inner += '		</div>';
				inner += '	</div>';
				inner += '</td>';
			} else {
				inner += '<td style="text-align:left;padding:5px 0px 5px 5px;">';
				inner += '<input type="text" id="' + result[a].paramKey
						+ '" name="' + result[a].paramKey + '" value="' + val
						+ '" class="form_input" ' + disabled + '/>';
				inner += '</td>';
			}
			inner += '</tr>';
		}
	}

	// 总的参数名称

	serviceParamKeyName = [];
	serviceParamKeyName = tmepParamKeyName;

	if (result.length % 2 != 0) {
		inner += '</tr>';
	}
	$('#servicePolicyParamTable').append(inner);
}
/**
 * 构造服务策略参数表单(苗有虎)
 * 
 * @param result
 */
function buildModulePolicyParamTree(mid) {

	if (mid == null || mid == '') {
		mid = $("#servicePolicyCode").val();
	}

	// 通过策略编号获取相应的策略参数----苗有虎
	$
			.ajax({
				type : "POST",
				url : ctx
						+ "/workflow/strategy/serviceStrategy_queryServiceParam.action?mid="
						+ mid,
				datatype : "json", // 返回的数据类型
				async : true,
				cache : false,
				success : function(data) {
					initModulePolicyParamTree(data, false);
				},
				error : function(e) {
					alert("error");
				}
			});
}