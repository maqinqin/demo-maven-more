/**
 * 提交服务策略表单参数时调用,用来获取key及对应的value值
 */
var servicePolicyParamFormDefinition = null;

var serviceParamKeyName = [];

var currentHeight = 290;
/**
 * 持有节点json数据，如果第一次打开表单，则为null，否则为上次提交的数据
 */
var jsonData = null;

/**
 * 页面初始化
 */
$(function() {
	if($('#state').val() == "design"){
		var params = $('#dNodeId').val();
		var nodeObjTemp = null;
		try{
			nodeObjTemp = parent.getComponentContent(params);
		}finally{
			if(nodeObjTemp!=null && nodeObjTemp!=""){
				$('#tNodeName').val(nodeObjTemp.name);
				$("#typeId").val(nodeObjTemp.compId);
				$("#typeName").val("API调用");
			}
		}
		//获取json数据，初始化上面基本数据
//		var jsonTemp = JSON.stringify(nodeObjTemp.json);
			//JSON.stringify(nodeObjTemp.json);
		var jsonTemp = nodeObjTemp.json;
		var regn = new RegExp("\n","g");
		var regt = new RegExp("\t","g");
		jsonTemp = jsonTemp.replace(regn,"");
		jsonTemp = jsonTemp.replace(regt,"");
//		jsonTemp = jsonTemp.replace("\"","&quot;");
//		console.log(jsonTemp);
		jsonData = JSON.parse(jsonTemp);
		if(jsonData != null){
			
			if(jsonData.outTime){
				$("#outtime").val(jsonData.outTime);
			}
			
			if(jsonData.isAutoNode){
				$("#isAutoNode").prop("checked",jsonData.isAutoNode == "Y" ? true : false);
			}
			if(jsonData.exceptionCode){
				$("#exceptionCode").val(jsonData.exceptionCode);
			}
			if(jsonData.serPolicyId){
				//策略表单初始化
				buildModulePolicyParamTree(jsonData.serPolicyId);
			}
		}
		
		// 获取服务策略数据----苗有虎
		$.ajax({
					type : "POST",
					url : ctx
							+ "/workflow/strategy/serviceStrategy_selAllServiceStratey.action?busType=API",
					datatype : "json", // 返回的数据类型
					async : true,
					cache : false,
					success : function(data) {
						var htmlStr = '<option value="">'+i18nShow('zh5')+'</option>';
						for ( var i = 0; i < data.length; i++) {
							var opt = data[i];
							htmlStr += '<option value="'
									+ opt.moduleId + '">'
									+ opt.moduleName
									+ '</option>';
						}
						$(htmlStr).appendTo(
								$('#servicePolicyCode'));

						if(jsonData != null){
							$("#servicePolicyCode").val(
									jsonData.serPolicyId);
						}
					},
					error : function(e) {
						showTip("error");
					}
				});
	}else if($('#state').val() == "definition"){
		//获取modelnode信息
		$.ajax({
		    type : "POST",
			url:$('#url').val()+"/baseForm/getApiFormContentAct.action",
			data : {
		    	 "_fw_service_id":"getApiFormContentSrv",
		    	 "modelId":$('#modelId').val(),
		    	 "nodeId":$('#nodeId').val(),
		    	 "typeId":$('#typeId').val()
		    },
		    async:true,
		    cache:false,
		    success : function(data) {
		    	var resultObj = stringToJson(data);//结果集
		    	
		    	if(resultObj){
		    		initForm(resultObj,false);
		    	 }
		    },
		    error : function(e) {
		      	showTip("error");
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
			$("#table_btn").css('display','none');
		}
		
		//当前为instance阶段，获取modelnode信息
		$.ajax({
		    type : "POST",
		    url : ctx + "/workflow/instance/bpmInstance_getApiFormContentAtInstance.action",
			data : {
				 "bpmInstanceNodePo.instanceId" :$('#instanceId').val(),
		    	 "bpmInstanceNodePo.wfNodeId":$('#nodeId').val()
		    },
		    async:true,
		    cache:false,
		    success : function(data) {
		    	var resultObj = data;//结果集
		    	
		    	if(resultObj){
		    		 initForm(resultObj,isDisabled);
		    	}
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
			"tNodeName"			:{"required":i18nShow('input_node_name'),"maxlength":i18nShow('maxline4'),"forbidComma":i18nShow('node_notice')},
			"outtime" 			:{"required":i18nShow('input_outtime'),"maxlength":10,"digits":i18nShow('input_integer')},
			"exceptionCode"		:{"required":i18nShow('select_expect')},
			"servicePolicyCode"	:{"required":i18nShow('select_service')}
			
		},
		submitHandler: function() {
			onSave();
		}
	});
});
function initForm(resultObj,isDisabled){
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
							+ "/workflow/strategy/serviceStrategy_selAllServiceStratey.action?busType=API",
					datatype : "json", // 返回的数据类型
					async : true,
					cache : false,
					success : function(data) {
						var htmlStr = '<option value="">'+i18nShow('zh5')+'</option>';
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
}


/**
 * 隐藏dic选择树,并释放body的鼠标点击事件的绑定
 */
function hideMenu() {
	$("#servicePolicyContent").fadeOut("fast");
	$("body").unbind("mousedown", onMenuDown);
	if($("#servicePolicyId").val()==""){
		$("#servicePolicyCode").val("");
	}
}
/**
 * 鼠标在dic类别树打开时候,点击非打开的div和回退脚本类别文本框,则隐藏回退脚本类别树
 * @param event
 */
function onMenuDown(event) {
	if (!(event.target.id == "servicePolicyCode" || event.target.id == "servicePolicyContent" || $(event.target).parents("#servicePolicyContent").length>0)) {
		hideMenu();
	}
}
/**
 * 初始dic目录树
 * @param e
 * @param treeId
 * @param treeNode
 * @returns {Boolean}
 */
function onDicClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("servicePolicyTreeSelect");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
/**
 * 保存当前表单信息
 * 通过父页面调用实现
 */
/**
 * 保存节点数据
 */
function componentSave(){
	$("#serviceModuleForm").submit();
}
function onSave(){
	/**
	 * 返回json格式的字符串
	 * 根据servicePolicyParamFormDefinition的id-value格式进行json封装
	 */
	var innerArr = new Array();
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
	objTemp.exceptionCode = $("#exceptionCode").val();
	objTemp.isAutoNode  = $("#isAutoNode").prop("checked") == true ? "Y" : "N";
	objTemp.serPolicyId = $("#servicePolicyCode").val();
	objTemp.serPolicyCode = $("#servicePolicyCode").val();
	objTemp.serPolicy = innerArr;
	
	/**
	 * 判定来自流程设计器-design/流程模板-model/流程实例-instance
	 */
	if($('#state').val() == "design"){
		window.parent.saveComponentNameData($('#dNodeId').val(),
				$('#tNodeName').val(),JSON.stringify(objTemp));
	}else if($('#state').val() == "definition"){
		objTemp.modelId=$('#modelId').val();
		objTemp.nodeId=$('#nodeId').val();
		objTemp.nodeName = $("#tNodeName").val();
		objTemp.typeId = $("#typeId").val();
		$.ajax({
		     type : "POST",
		     url : $('#url').val()+"/baseForm/saveApiFormContentAct.action",
		     data : {
		    	 "_fw_service_id":"saveApiFormContentSrv",
		    	 "jsonData":JSON.stringify(objTemp),
		    	 'jsonClass' : 'com.ccb.iomp.cloud.data.vo.workflow.BpmSaveModelNodeFormVo'
		     },
		     async:true,
		     cache:false,
		     success : function(data) {
		    	window.parent.saveComponentNameData($('#dNodeId').val(),$('#tNodeName').val());
		     },
		     error : function(e) {
		      	showTip("error");
		     }
		 });
	}else{
		var bpmSaveInstanceNodeFormVo = {
				"outTime" : $("#outtime").val(),
		 		"exceptionCode" : $("#exceptionCode").val(),
		 		"isAutoNode"  : $("#isAutoNode").prop("checked") == true ? "Y" : "N",
		 		"serPolicyId" : $("#servicePolicyCode").val(),
		 		"serPolicyCode" : $("#servicePolicyCode").val(),
		 		"serPolicy" : innerArr,
		 		"instanceId" : $('#instanceId').val(),
		 		"nodeId" : $('#nodeId').val(),
		 		"nodeName" : $("#tNodeName").val()
		};
		$.ajax({
		     type : "POST",
		     url : ctx + "/workflow/instance/bpmInstance_saveApiFormContentAtInstance.action",
		     data : {
		    	 jsonData : JSON.stringify(bpmSaveInstanceNodeFormVo),
				 jsonClass : "com.git.cloud.workflow.model.vo.BpmSaveInstanceNodeFormVo"
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

//关闭窗口
function cancel(){
	window.parent.closeComponentDialog("userComponent_div");
}

/**
 * 重新计算表单高度
 */
function resetHeight(height){
	window.parent.setFrameHeight(height);
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
		if(result[a].paramIoCode=="IN"){
		
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
				inner += '<textarea rows="2" cols="40" style="width: 230px; height: 28px;" draggable="false" id="' + result[a].paramKey
				+ '" name="' + result[a].paramKey + '"class="form_input" ' + disabled + '>'+val+'</textarea>';
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
				inner += '<textarea rows="2" cols="40" style="width: 230px; height: 28px;" draggable="false" id="' + result[a].paramKey
				+ '" name="' + result[a].paramKey + '"class="form_input" ' + disabled + '>'+val+'</textarea>';
				inner += '</td>';
			}
			inner += '</tr>';
		}
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
	$.ajax({
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
			showTip("error");
		}
	});
}

/**
* 字符串形式转json对象
*/
function stringToJson(stringValue) {
	eval("var theJsonValue = " + stringValue);
	return theJsonValue;
}