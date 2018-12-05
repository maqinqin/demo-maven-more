/**
 * 提交服务策略表单参数时调用,用来获取key及对应的value值
 */
var servicePolicyParamFormDefinition = null;

var serviceParamKeyName = [];

/**
 * 持有节点json数据，如果第一次打开表单，则为null，否则为上次提交的数据
 */
var jsonData = null;

/**
 * 页面初始化
 */
$(document).ready(function() {
	/**
	 * 当前是设计阶段
	 */
	if ($('#state').val() == "design") {
		/**
		 * 获取节点的mxGraph Id，并获取其上面附加的属性值
		 */
		var params = $('#dNodeId').val();
		var nodeObjTemp = null;
		try {
			nodeObjTemp = parent.getComponentContent(params);
		} finally {
			if (nodeObjTemp != null && nodeObjTemp != "") {
				$('#tNodeName').val(nodeObjTemp.name);
				$("#typeId").val(nodeObjTemp.compId);
				$("#typeName").val("脚本执行");
			}
		}

		/**
		 * 获取json字段数据并初始化表单
		 */
		jsonData = JSON.parse(nodeObjTemp.json);

		if(jsonData != null){
			if (jsonData.outTime) {
				$("#outtime").val(jsonData.outTime);
			}
			
			if (jsonData.exceptionCode) {
				$("#exceptionCode").val(jsonData.exceptionCode);
			}
			
			if (jsonData.isAutoNode) {
				$("#isAutoNode").prop("checked",
						jsonData.isAutoNode == "Y" ? true : false);
			}
			
			if (jsonData.serPolicyId) {
				// 策略表单初始化
				buildModulePolicyParamTree(jsonData.serPolicyId);
			}
			
			if (jsonData.scriptId) {
				$("#scriptCode").val(jsonData.scriptCode);
				$("#scriptId").val(jsonData.scriptId);
			}
		}

		// 获取服务策略数据----苗有虎
		$.ajax({
			type : "POST",
			url : ctx
					+ "/workflow/strategy/serviceStrategy_selAllServiceStratey.action?busType=SCRIPT",
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

				if(jsonData != null)
					$("#servicePolicyCode").val(
							jsonData.serPolicyId);

			},
			error : function(e) {
				alert("error");
			}
		});
	}
	/**
	 * 如果当前流程为定义阶段，需要从数据库获取节点信息
	 */
	else if ($('#state').val() == "definition") {
		$.ajax({
			type : "POST",
			url : ctx +'/workflow/model/bpmInstance_getInstanceNodeInfo.action',
			data : {
				"_fw_service_id" : "getScriptFormContentSrv",
				"modelId" : $('#modelId').val(),
				"nodeId" : $('#nodeId').val(),
				"typeId" : $('#typeId').val()
			},
			async : true,
			cache : false,
			success : function(data) {
				/**
				 * 获取到结果集成功，解析为json形式，并根据值初始化表单
				 */
				var resultObj = stringToJson(data);
				initForm(resultObj, false);
			},
			error : function(e) {
				alert("error");
			}
		});
	}
	/**
	 * 如果当前流程为实例阶段，需要根据#userAdmin判断是否禁用组件
	 */
	else {
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
		}

		//当前为instance阶段，获取modelnode信息
		$.ajax({
		    type : "POST",
		    url : ctx + "/workflow/instance/bpmInstance_getScriptFormContentAtInstance.action?busType=SCRIPT",
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
			"scriptCode"	    :{"required":true},
			"servicePolicyCode"	:{"required":true}
		},
		messages: {
			"tNodeName"			:{"required":i18nShow('input_node_name'),"maxlength":i18nShow('maxline4'),"forbidComma":i18nShow('node_notice')},
			"outtime" 			:{"required":i18nShow('input_outtime'),"maxlength":10,"digits":i18nShow('input_integer')},
			"exceptionCode"		:{"required":i18nShow('select_expect')},
			"scriptCode"	    :{"required":i18nShow('select_script')},
			"servicePolicyCode"	:{"required":i18nShow('select_service')}
		},
		submitHandler: function() {
			onSave();
		}
	});
});
function initForm(resultObj, isDisabled) {
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
						+ "/workflow/strategy/serviceStrategy_selAllServiceStratey.action?busType=SCRIPT",
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
		if (resultObj.nodeScriptId) {
			$("#scriptCode").val(resultObj.nodeScriptCode);
			$("#scriptId").val(resultObj.nodeScriptId);
		}
	}
}

//初始化实例节点表单
function initInstanceNodeForm(resultObj, isDisabled) {
	if (resultObj) {
		//节点数据
		$('#tNodeName').val(resultObj.nodeName);// 设置节点名称
		$('#exceptionCode').val(resultObj.exceptionCode);
		$("#outtime").val(resultObj.outTime);
		$("#isAutoNode").prop("checked",resultObj.isAutoNode == "Y" ? true : false);
		$("#typeName").val("脚本执行");

		// 参数数据
		if (resultObj.params && resultObj.params.length>0) {
			var inner = "<tr><th style='width:50%;text-align:center;background-color:#3eaae8;color:#ffffff;'>参数名</th><th style='width:50%;text-align:center;background-color:#3eaae8;color:#ffffff;'>参数值</th></tr>";
			for ( var i = 0; i < resultObj.params.length; i++) {
				inner += "<tr align='center'><td>"+resultObj.params[i].paramKey+"</td><td>"+resultObj.params[i].paramValue+"</td></tr>";
			}
			$('#servicePolicyParamTable').append(inner);
		}

		//脚本数据
		if (resultObj.scriptId) {
			$("#scriptCode").val(resultObj.scriptCode);
			$("#scriptId").val(resultObj.scriptId);
		}
	}
}

/**
 * 初始化服务策略树
 */
function getServicePolicyTree() {
	$.ajax({
		type : "POST",
		url : $('#url').val() + "/baseForm/getSevicePolicyByTypeAct.action",
		data : {
			"_fw_service_id" : "getSevicePolicyByTypeSrv",
			"moduleType" : "SCRIPT"
		},
		async : true,
		cache : false,
		success : function(msg) {
			var result = stringToJson(msg);
			buildModulePolicyTree(result);
		},
		error : function(e) {
			alert("error");
		}
	});
}
/**
 * 构造服务策略树
 */
function buildModulePolicyTree(result) {
	var setting = {
		view : {
			dblClickExpand : false,
			selectedMulti : false
		},
		check : {
			enable : true,
			chkStyle : "radio",
			radioType : "all"
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onDicClick,
			onCheck : onDicCheck
		}
	};
	var zNodesType = "[";
	for ( var i in result) {
		if (typeof (result[i][0]) == "string")
			zNodesType += "{ id:'" + result[i][0] + "', pId:'" + result[i][1]
					+ "', name:'" + result[i][2] + "',open:true,nocheck:true},";
		else
			zNodesType += "{ id:'" + result[i][0] + "', pId:'" + result[i][1]
					+ "', name:'" + result[i][2] + "',open:true},";
	}
	zNodesType = zNodesType.substring(0, zNodesType.length - 1);
	zNodesType += "]";
	var zNodes = stringToJson(zNodesType);
	$.fn.zTree.init($("#servicePolicyTreeSelect"), setting, zNodes);
	var sbfOffset = $("#servicePolicyCode").offset();
	$("#servicePolicyContent").css({
		width : $('#servicePolicyCode').get(0).offsetWidth + "px",
		left : sbfOffset.left + "px",
		top : sbfOffset.top + $("#servicePolicyCode").outerHeight() + "px"
	}).slideDown("fast");
	$("body").bind("mousedown", onMenuDown);
}
/**
 * 点击选择节点后的赋值
 * 
 * @param e
 * @param treeId
 * @param treeNode
 */
function onDicCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("servicePolicyTreeSelect"), nodes = zTree
			.getCheckedNodes(true), v = "";
	k = "";
	for ( var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		k += nodes[i].id + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	if (k.length > 0)
		k = k.substring(0, k.length - 1);
	$("#servicePolicyCode").val(v);
	$("#servicePolicyId").val(k);
	if (k != "")
		getServicePolicyParam(k);
}
/**
 * 初始化服务策略参数
 */
function getServicePolicyParam(moduleID) {
	$.ajax({
		type : "POST",
		url : $('#url').val() + "/baseForm/getSevicePolicyParamAct.action",
		data : {
			"_fw_service_id" : "getSevicePolicyParamSrv",
			"moduleId" : moduleID
		},
		async : true,
		cache : false,
		success : function(msg) {
			var result = stringToJson(msg);
			buildModulePolicyParamTree(result);
			servicePolicyParamFormDefinition = result;
		},
		error : function(e) {
			alert("error");
		}
	});
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
			inner += '<td width="120" style="text-align:center;padding:5px 0px 5px 5px;">'
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
			inner += '<td width="120" style="text-align:center;padding:5px 0px 5px 5px;">'
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
 * 隐藏dic选择树,并释放body的鼠标点击事件的绑定
 */
function hideMenu() {
	$spc = $("#servicePolicyContent");

	if ($spc != null) {
		$spc.fadeOut("fast");

		if ($("#servicePolicyId").val() == "") {
			$("#servicePolicyCode").val("");
		}
	}

	$mc = $("#menuContent");
	if ($mc != null) {
		$mc.fadeOut("fast");
		if ($("#scriptId").val() == "") {
			$("#scriptCode").val("");
		}
	}

	$("body").unbind("mousedown", onMenuDown);
}
/**
 * 鼠标在dic类别树打开时候,点击非打开的div和回退脚本类别文本框,则隐藏回退脚本类别树
 * 
 * @param event
 */
function onMenuDown(event) {
	if (!(event.target.id == "servicePolicyCode"
			|| event.target.id == "servicePolicyContent"
			|| $(event.target).parents("#servicePolicyContent").length > 0
			|| event.target.id == "scriptCode"
			|| event.target.id == "menuContent" || $(event.target).parents(
			"#menuContent").length > 0)) {
		hideMenu();
	}
}
/**
 * 初始dic目录树
 * 
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
 * 保存当前表单信息 通过父页面调用实现
 */
function saveForm() {
	$("#serviceModuleForm").submit();
}
function onSave(){
	/**
	 * 返回json格式的字符串 根据servicePolicyParamFormDefinition的id-value格式进行json封装
	 */
	var innerArr = [];
	for ( var i = 0; i < serviceParamKeyName.length; i++) {
		innerObj = {};
		innerObj.id = serviceParamKeyName[i];
		innerObj.value = $('#' + serviceParamKeyName[i]).val();
		innerArr.push(innerObj);
	}

	var objTemp = {};
	objTemp.name = $("#tNodeName").val();
	objTemp.outTime = $("#outtime").val();
	objTemp.exceptionCode = $("#exceptionCode").val();
	objTemp.isAutoNode = $("#isAutoNode").prop("checked") == true ? "Y" : "N";
	objTemp.serPolicyId = $("#servicePolicyCode").val();
	objTemp.serPolicyCode = $("#servicePolicyCode").val();
	objTemp.scriptCode = $("#scriptCode").val();
	objTemp.scriptId = $("#scriptId").val();
	objTemp.serPolicy = innerArr;

	/**
	 * 判定来自流程设计器-design/流程模板-model/流程实例-instance
	 */
	if ($('#state').val() == "design") {
		window.parent.saveComponentNameData($('#dNodeId').val(),
				$('#tNodeName').val(), JSON.stringify(objTemp));
	} else if ($('#state').val() == "definition") {
		/**
		 * 定义阶段，保存节点信息到数据库，额外定义一些参数值
		 */
		objTemp.modelId = $('#modelId').val();
		objTemp.nodeId = $('#nodeId').val();
		objTemp.nodeName = $("#tNodeName").val();
		objTemp.typeId = $("#typeId").val();
		$.ajax({
			type : "POST",
			url : $('#url').val()
					+ "/baseForm/saveScriptFormContentAct.action",
			data : {
				"_fw_service_id" : "saveScriptFormContentSrv",
				"jsonData" : JSON.stringify(objTemp),
				'jsonClass' : 'com.ccb.iomp.cloud.data.vo.workflow.BpmSaveModelNodeScriptFormVo'
			},
			async : true,
			cache : false,
			success : function(data) {
				// 保存成功后，把新的节点名称更新给mxGraph
				window.parent.saveComponentNameData(
						$('#dNodeId').val(), $('#tNodeName').val());
			},
			error : function(e) {
				alert("error");
			}
		});
	} else {
		var bpmSaveInstanceNodeScriptFormVo = {
				"outTime" : $("#outtime").val(),
		 		"exceptionCode" : $("#exceptionCode").val(),
		 		"isAutoNode"  : $("#isAutoNode").prop("checked") == true ? "Y" : "N",
		 		"serPolicyId" : $("#servicePolicyCode").val(),
		 		"serPolicyCode" : $("#servicePolicyCode").val(),
		 		"serPolicy" : innerArr,
		 		"instanceId" : $('#instanceId').val(),
		 		"nodeId" : $('#nodeId').val(),
		 		"nodeName" : $("#tNodeName").val(),
		 		"scriptCode" : $("#scriptCode").val(),
		 		"scriptId" :  $("#scriptId").val()
		};
		$.ajax({
		     type : "POST",
		     url : ctx + "/workflow/instance/bpmInstance_saveScriptFormContentAtInstance.action",
		     data : {
		    	 jsonData : JSON.stringify(bpmSaveInstanceNodeScriptFormVo),
				 jsonClass : "com.git.cloud.workflow.model.vo.BpmSaveInstanceNodeScriptFormVo"
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

// 关闭窗口
function cancel() {
	window.parent.closeComponentDialog("userComponent_div");
}

/**
 * 构造和用户输入匹配的脚本列表
 * 
 * @param result
 */
function getMenuTree(result) {
	/**
	 * 脚本包集合对象
	 */
	var rSP = result[0];
	/**
	 * 模块集合对象
	 */
	var rSM = result[1];
	/**
	 * 脚本集合对象
	 */
	var rS = result[2];
	/**
	 * 如果脚本为空则不做任何处理
	 */
	if (rS == null || rS == "" || rS == "[]") {
		var zNodes = [];
		$.fn.zTree.init($("#treeSelect"), setting, zNodes);
		return;
	}
	var setting = {
		view : {
			dblClickExpand : false,
			selectedMulti : false
		},
		check : {
			enable : true,
			chkStyle : "radio",
			radioType : "all"
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onBackClick,
			onCheck : onBackCheck
		}
	};
	var zNodesType = "[";
	for ( var i in rSP) {
		zNodesType += "{ id:" + rSP[i][0] + ", pId:" + rSP[i][2] + ", name:'"
				+ rSP[i][1] + "',open:true, nocheck:true},";
	}
	for ( var j in rSM) {
		zNodesType += "{ id:" + rSM[j][0] + ", pId:" + rSM[j][2] + ", name:'"
				+ rSM[j][1] + "',open:true, nocheck:true},";
	}
	for ( var k in rS) {
		zNodesType += "{ id:" + rS[k][0] + ", pId:" + rS[k][2] + ", name:'"
				+ rS[k][1] + "'},";
	}
	zNodesType = zNodesType.substring(0, zNodesType.length - 1);
	zNodesType += "]";
	var zNodes = stringToJson(zNodesType);
	$.fn.zTree.init($("#treeSelect"), setting, zNodes);
}
/**
 * 初始化回退脚本目录树
 * 
 * @param e
 * @param treeId
 * @param treeNode
 * @returns {Boolean}
 */
function onBackClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeSelect");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
/**
 * 点击选择节点后的赋值
 * 
 * @param e
 * @param treeId
 * @param treeNode
 */
function onBackCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeSelect"), nodes = zTree
			.getCheckedNodes(true), v = "";
	k = "";
	for ( var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		k += nodes[i].id + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	if (k.length > 0)
		k = k.substring(0, k.length - 1);
	$("#scriptCode").val(v);
	$("#scriptId").val(k);
}

/**
 * 字符串形式转json对象
 */
function stringToJson(stringValue) {
	eval("var theJsonValue = " + stringValue);
	return theJsonValue;
}

//关闭窗口
function cancel(){
	window.parent.closeComponentDialog("userComponent_div");
}
