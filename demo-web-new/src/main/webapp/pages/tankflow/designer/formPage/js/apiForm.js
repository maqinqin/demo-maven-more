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
			nodeObjTemp = parent.TankFlow.getComponentContent(params);
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
		var start = jsonTemp.indexOf("{");
		var end = jsonTemp.lastIndexOf("}");
		jsonTemp = jsonTemp.substring(start, end + 1);
		//console.log(jsonTemp);
		
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
				if('REPEAT'==jsonData.exceptionCode){
					$('#exceptionRepeatDiv').css('display','inline-block');
					$('#exceptionRepeat').val(jsonData.exceptionRepeat);
				}else{
					$('#exceptionRepeatDiv').css('display','none');
				}
				if(jsonData.circleInterval){
					$("#circleInterval").val(jsonData.circleInterval);
				}
				if(jsonData.circleNum){
					$("#circleNum").val(jsonData.circleNum);
				}
			}
			if(jsonData.isActivity){
				$("#isActivity").prop("checked",jsonData.isActivity == "Y" ? true : false);
				if(jsonData.isActivity == "Y"){
					$('#activitySubmitDiv').css('display','inline-block');
					$('#activityTime').val(jsonData.activityTime);
				}else{
					$('#activitySubmitDiv').css('display','none');					
				}
				if(jsonData.activityAlarm){
					$("#activityAlarm").prop("checked",jsonData.activityAlarm == "Y" ? true : false);
					if(jsonData.activityAlarm=='Y'){
						$('#activityAlarmDiv').css('display','inline-block');
						$('#activityAlarmTime').val(jsonData.activityAlarmTime);
					}else{
						$('#activityAlarmDiv').css('display','none');	
					}
				}
			}
			if(jsonData.isCircle){
				$("#isCircle").prop("checked",jsonData.isCircle == "Y" ? true : false);
				if(jsonData.isCircle=='Y'){
					$('#circleInfo').show();
				}else{
					$('#circleInfo').hide();
				}
			}
			if(jsonData.exceptionAlarm){
				$("#exceptionAlarm").prop("checked",jsonData.exceptionAlarm == "Y" ? true : false);
			}
			if(jsonData.serPolicyId){
				buildModulePolicyParamTree(jsonData.serPolicyId);
			}
			if(jsonData.submitStart){
				$('#submitStart').val(jsonData.submitStart);
			}
			if(jsonData.outtimeAlarm){
				$("#outtimeAlarm").prop("checked",jsonData.outtimeAlarm == "Y" ? true : false);
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
					var htmlStr = '<option value="">请选择...</option>';
					for ( var i = 0; i < data.length; i++) {
						var opt = data[i];
						htmlStr += '<option value="'
								+ opt.moduleId + '">'
								+ opt.moduleName
								+ '</option>';
					}
					$(htmlStr).appendTo($('#servicePolicyCode'));

					if(jsonData != null){
						$("#servicePolicyCode").val(jsonData.serPolicyId);
					}
				},
				error : function(e) {
					showTip("error");
				}
			});
		$('#exceptionCode').change(function(){
			var p1=$(this).children('option:selected').val();
			if('REPEAT'==p1){
				if($('#isCircle').prop("checked")){
					showTip("已设置<循环执行>，不可设置异常重复执行！");
					$('#exceptionCode').val('no');
					return false;
				}else{
					$('#exceptionRepeatDiv').css('display','inline-block');
				}
			}else{
				$('#exceptionRepeatDiv').css('display','none');	
			}
		});
		$('#isActivity').change(function(){
			 if ($(this).prop("checked")) {
				$('#activitySubmitDiv').css('display', 'inline-block');
			} else {
				$('#activitySubmitDiv').css('display', 'none');
			}
		});

		$('#isCircle').change(function(){
			 if ($(this).prop("checked")) {
				if($('#exceptionCode').val()=='REPEAT'){
					showTip("异常处理为<重复执行>，不可进行循环执行设置");
					$(this).attr("checked", false);
					return false;
				}else{
					 $("#circleInfo").show();
				}
			} else {
				$("#circleInfo").hide();
			}
		});
		$('#activityAlarm').change(function(){
			 if ($(this).prop("checked")) {
					$('#activityAlarmDiv').css('display', 'inline-block');
				} else {
					$("#activityAlarmDiv").css('display', 'none');
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
	// 不重置高度
	// resetHeight(currentHeight);
	
	//添加一个校验方法，可以禁止用户在节点名字段输入逗号“,”
	$.validator.addMethod("forbidComma", function(value) {
		for(var i=0; i<value.length; i++){
			if(value[i] == ","){
				return false;
			}
		}
		return true;
	}, '节点名中不能包含逗号'); 
	//添加一个校验方法，验证重复执行
	$.validator.addMethod("exceptionRepeat", function(value) {
		if($('#exceptionCode').val()=='REPEAT'){
			if($('#exceptionRepeat').val()!=''){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}, '请正确填写执行');
	//添加一个校验方法，验证激活时间
	$.validator.addMethod("activityTime", function(value) {
		if($('#isActivity').prop("checked")){
			if($('#activityTime').val()!=''){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
		return true;
	}, '请设置激活时间');
	//添加一个校验方法，验证激活时间
	$.validator.addMethod("activityTime", function(value) {
		if($('#isActivity').prop("checked")){
			if($('#activityTime').val()!=''){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
		return true;
	}, '请输入时间间隔(整数)');
	//添加一个校验方法，验证重复执行时间间隔
	$.validator.addMethod("circleNum", function(value) {
		if($('#isCircle').prop("checked")){
			if($('#circleNum').val()==''){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
		return true;
	}, '请输入执行(整数)');
	//添加一个校验方法，验证重复执行时间间隔
	$.validator.addMethod("circleInterval", function(value) {
		if($('#isCircle').prop("checked")){
			if($('#circleInterval').val()==''){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
		return true;
	}, '请输入时间间隔(整数)');
	//添加一个校验方法，验证激活告警时间
	$.validator.addMethod("activityAlarmTime", function(value) {
		if($('#isActivity').prop("checked")){
			if($('#activityAlarm').prop("checked")){
				var activityTime = $('#activityTime').val();
				var activityAlarmTime = $('#activityAlarmTime').val();
				if(activityAlarmTime==''){
					return false;
				}
				if(checkDate(activityTime,activityAlarmTime)){
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
		return true;
	}, '请入正确时间并晚于激活时间');
	//设置验证
	$("#serviceModuleForm").validate({
		rules: {
			"tNodeName"			:{"required":true,"maxlength":100,"forbidComma":true},
			"outtime"			:{"required":true,"maxlength":10,"digits":true},
			"exceptionCode"		:{"required":true},
			"servicePolicyCode"	:{"required":true},
			"exceptionRepeat"	:{"required":true,"maxlength":2,"exceptionRepeat":true,"digits":true},
			"activityTime"		:{"required":true,"activityTime":true},
			"circleInterval"	:{"required":true,"maxlength":10,"circleInterval":true,"digits":true},
			"circleNum"			:{"required":true,"circleNum":true,"digits":true},
			"activityAlarmTime"	:{"required":true,"activityAlarmTime":true},
		},
		messages: {
			"tNodeName"			:{"required":"请输入节点名称","maxlength":"字符长度不得大于50","forbidComma":"节点名中不能包含逗号"},
			"outtime" 			:{"required":"请输入超时时间(整数)","maxlength":"时间长度不得大于10位","digits":"请输入整数"},
			"exceptionCode"		:{"required":"请选择异常处理类型"},
			"servicePolicyCode"	:{"required":"请选择服务策略"},
			"exceptionRepeat"	:{"required":"请输入重复次数(整数)","maxlength":"不得大于2位","digits":"请输入整数"},
			"activityTime"		:{"required":"请选择激活时间"},
			"circleInterval"	:{"required":"请输入时间间隔(整数)","maxlength":"时间长度不得大于10位","digits":"请输入整数"},
			"circleNum"			:{"required":"请输入执行(整数)","maxlength":"时间长度不得大于10位","digits":"请输入整数"},
			"activityAlarmTime"	:{"required":"请入正确时间并晚于激活时间"},
		},
		submitHandler: function() {
			onSave();
		}
	});
	
});

function initBpmException() {
	$.ajax({
		type : "POST",
		url : ctx
				+ "/workflow/template/initBpmException.action",
		async : true,
		cache : false,
		success : function(data) {
//			DIC_ID dicId, 
//            DIC_NAME dicName, 
//            DIC_CODE dicCode,
//            ATTR attr 
			var bpmExceptions = data;// 结果集
			alert(bpmExceptions.length);
			var length = bpmExceptions.length;
			if (bpmExceptions) {
				
				for (var k = 0, length = array.length; k < length; k++) {
					var dicId = bpmExceptions[k].dicId;
				}
			}
		},
		error : function(e) {
			showTip("error");
		}
	});
}

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
		url : ctx + "/workflow/strategy/serviceStrategy_selAllServiceStratey.action?busType=API",
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
			$(htmlStr).appendTo($('#servicePolicyCode'));

			$("#servicePolicyCode").val(jsonData.serPolicyId);
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
		if (serviceParamKeyName[i]=="MANAGEMENT_TARGET") {
			innerObj.value = $('#managementTargetId').val();
		}else if(serviceParamKeyName[i]=='SERVER'){
			innerObj.value = $('#serverTargetId').val();
		}else if(serviceParamKeyName[i]=='FILE'){
			innerObj.value = $('#fileTargetId').val();
		}else{
			innerObj.value = $('#' + serviceParamKeyName[i]).val();
		}
		
		innerArr.push(innerObj);
	}
	
	var objTemp = {};
	objTemp.compId = $("#typeId").val();
	objTemp.name = $("#tNodeName").val();
	objTemp.outTime = $("#outtime").val();
	objTemp.outtimeAlarm = $('#outtimeAlarm').prop("checked") == true ? "Y" : "N";
	objTemp.isAutoNode  = $("#isAutoNode").prop("checked") == true ? "Y" : "N";
	objTemp.exceptionCode = $("#exceptionCode").val();
	objTemp.exceptionAlarm = $('#exceptionAlarm').prop("checked") == true ? "Y" : "N";
	objTemp.exceptionRepeat = $('#exceptionRepeat').val();
	objTemp.isActivity = $('#isActivity').prop("checked") == true ? "Y" : "N";
	if('Y'==objTemp.isActivity){
		objTemp.activityAlarm = $('#activityAlarm').prop("checked") == true ? "Y" : "N";
	}else{
		objTemp.activityAlarm = "N";
	}
	if("Y"==objTemp.activityAlarm){
		objTemp.activityAlarmTime = $('#activityAlarmTime').val();
	}else{
		objTemp.activityAlarmTime="";
	}
	objTemp.activityTime = $('#activityTime').val();
	objTemp.isCircle = $('#isCircle').prop("checked") == true ? "Y" : "N";
	objTemp.circleInterval = $("#circleInterval").val();
	objTemp.circleNum = $("#circleNum").val();
	objTemp.serPolicyId = $("#servicePolicyCode").val();
	objTemp.serPolicyCode = $("#servicePolicyCode").val();
	objTemp.serPolicy = innerArr;
	
	
	/**
	 * 判定来自流程设计器-design/流程模板-model/流程实例-instance
	 */
	if($('#state').val() == "design"){
		window.parent.TankFlow.saveComponentNameData($('#dNodeId').val(), $('#tNodeName').val(), JSON.stringify(objTemp));
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
		    	window.parent.TankFlow.saveComponentNameData($('#dNodeId').val(),$('#tNodeName').val());
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
	window.parent.TankFlow.closeComponentDialog();
}

/**
 * 重新计算表单高度
 */
function resetHeight(height){
	window.parent.TankFlow.setFrameHeight(height);
}

/**
 * 创建服务策略对应的表单字段
 * 
 * @param result 服务策略参数
 * @param isDisabled
 */
function initModulePolicyParamTree(result, isDisabled) {
	$('#servicePolicyParamTable').html("");
	nodeObjTemp = window.parent.TankFlow.getComponentContent("2");
	var start = nodeObjTemp.json.indexOf("[");
	var end = nodeObjTemp.json.lastIndexOf("]");
	nodeObjTemp.json = nodeObjTemp.json.substring(start, end + 1);
	//console.log(nodeObjTemp.json);
	if(nodeObjTemp.json!=""){
		var startData = JSON.parse(nodeObjTemp.json);
	}
	var inner = '';
	var disabled = isDisabled ? "disabled" : "";
	var tmepParamKeyName = [];
	for ( var a in result) {
		if(result[a].paramIoCode != "IN"){
			continue;
		}
		// 持有jsonData.serPolicy中相应key的值
		var val = '';

		// 如果已经提交过表单，则保存其值以备填充表单
		if (jsonData != null && jsonData.serPolicy) {
			for ( var i = 0; i < jsonData.serPolicy.length; i++) {
				if (result[a].paramKey == jsonData.serPolicy[i].id) {
					if (result[a].paramKey=="MANAGEMENT_TARGET") {
						var str=jsonData.serPolicy[i].value.split(",");
						for(var p in startData){//遍历json数组时，这么写p为索引，0,1
							var s=startData[p].paramKey;
							if ($.inArray(s, str)>=0) {
								val += startData[p].paramName+",";
							}
						}
						val=val.substring(0, val.length-1);
						$("#managementTargetId").attr("value", str);
//							$('#'+result[a].paramKey).val(str);
					}else if(result[a].paramKey=="SERVER"){
						var str=jsonData.serPolicy[i].value.split(",");
						for(var p in startData){//遍历json数组时，这么写p为索引，0,1
							var s=startData[p].paramKey;
							if ($.inArray(s, str)>=0) {
								val += startData[p].paramName+",";
							}
						}
						val=val.substring(0, val.length-1);
						$("#serverTargetId").attr("value", str);
					}else if(result[a].paramKey=="FILE"){
						var str=jsonData.serPolicy[i].value.split(",");
						for(var p in startData){//遍历json数组时，这么写p为索引，0,1
							var s=startData[p].paramKey;
							if ($.inArray(s, str)>=0) {
								val += startData[p].paramName+",";
							}
						}
						val=val.substring(0, val.length-1);
						$("#fileTargetId").attr("value", str);
					}else{
						val = jsonData.serPolicy[i].value;
					}
					
					break;
				}
			}
		}

		// 保存所有参数名称
		tmepParamKeyName.push(result[a].paramKey);
		//alert(result[a].paramName + ":::" + result[a].paramTypeCode);
		if (a == 0 || a % 2 == 0 || true) {
			if (a % 2 == 0) {
				inner += '<tr>';
			}
			inner += '<td style="width: 80px;" class="tf_form_win_label">' + result[a].paramName + '</td>';
			inner += '<td style="width: 260px;" >';
			if (result[a].paramTypeCode == "SELECT") {
				inner += '<div class="select_border">';
				inner += '<div class="select_container">';
				inner += '<select id="' + result[a].paramKey + '" name="' + result[a].paramKey + '" class="form_select" '
						+ disabled + ' onchange="selectChange(\''+result[a].paramKey+'\');">';
				var selectValue = result[a].paramValue.split(";");
				for ( var b in selectValue) {
					var selectValueItem = selectValue[b].split(",");
					if (selectValueItem[0] == $("#managementTargetId").val())
						inner += '<option value="' + selectValueItem[0] + '" selected>' + selectValueItem[1] + '</option>';
					else if(selectValueItem[0] == val){
						inner += '<option value="' + selectValueItem[0] + '" selected>' + selectValueItem[1] + '</option>';
					} else {
						inner += '<option value="' + selectValueItem[0] + '">' + selectValueItem[1] + '</option>';
					}
				}
				inner += '			</select>';
				inner += '		</div>';
				inner += '	</div>';
			} else if (result[a].paramTypeCode == "CUSTOM") {
				// 自定义类型，需要弹出新div
				var paramValue = result[a].paramValue;
				inner += '<textarea style="float: left;" readonly class="tf_form_textarea" draggable="false" id="' + result[a].paramKey
					+ '" name="' + result[a].paramKey + '"class="tf_form_input" ' + disabled + '>'+val+'</textarea>';
				inner += '<input type="button" class="tf_select_button" value="选择" onclick=\'showCustomDiv("'+paramValue+'","'+result[a].paramKey+'")\' />';
			} else {
//				debugger
				var str = val, pos;
				var lineCount = 0, tmp;
				var charNumPerLine = 50;
				var et = "\n"
				while(val.indexOf(et) > -1) {
					pos = val.indexOf(et);
					tmp = val.substr(0, pos + 1);
					lineCount += Math.ceil(tmp.length / charNumPerLine);
					if (val.length <= (pos + 1))
						break;
					val = val.substr(pos + 1, val.length);
				}
				lineCount = lineCount * 16;
				lineCount = lineCount > 50 ? lineCount : 50;
				lineCount = lineCount > 150 ? 150 : lineCount;
				inner += '<textarea style="height: ' + lineCount + 'px;" rows="' + lineCount + '" draggable="false" id="' + result[a].paramKey + '" name="' + 
					result[a].paramKey + '"class="tf_form_textarea" ' + disabled + '>' + str + '</textarea>';
			}
			inner += '</td>';
			if (a % 2 == 1) {
				inner += '</tr>';
			}
		}/* else {
			// 此段代码无用
			inner += '<td width="120" style="text-align:left;padding:5px 0px 5px 5px;" class="tf_form_win_label">'
					+ result[a].paramName
					+ '</td>';
			if (result[a].paramTypeCode == "SELECT") {
				inner += '<td style="text-align:left;padding:5px 0px 5px 5px;">';
				inner += '	<div class="tf_select_border">';
				inner += '		<div class="tf_select_container">';
				inner += '			<select id="' + result[a].paramKey + '" name="'
						+ result[a].paramKey + '" class="tf_form_select" '
						+ disabled + ' onchange="selectChange(\''+result[a].paramKey+'\');">';

				var selectValue = result[a].paramValue.split(";");
				for ( var b in selectValue) {
					var selectValueItem = selectValue[b].split(",");
					if (selectValueItem[0] == $("#managementTargetId").val())
						inner += '<option value="' + selectValueItem[0]
								+ '" selected>' + selectValueItem[1]
								+ '</option>';
					else if(selectValueItem[0] == val){
						inner += '<option value="' + selectValueItem[0]
								+ '" selected>' + selectValueItem[1]
								+ '</option>';
					} else
						inner += '<option value="' + selectValueItem[0] + '">'
						+ selectValueItem[1] + '</option>';
				}
				inner += '			</select>';
				inner += '		</div>';
				inner += '	</div>';
				inner += '</td>';
			} else if (result[a].paramTypeCode == "CUSTOM") {
				var paramValue = result[a].paramValue;
//					if(result[a].paramKey=="SERVER"||result[a].paramKey=="FILE"){
//						inner += '<td style="text-align:left;padding:5px 0px 5px 5px;">';
//						inner += '<textarea rows="4" cols="40" style="width: 210px; height: 60px;" draggable="false" id="' + result[a].paramKey
//						+ '" name="' + result[a].paramKey + '"class="form_input" ' + disabled + '>'+val+'</textarea>';
//					}else{
					inner += '<td style="text-align:left;padding:5px 0px 5px 5px;">';
					inner += '<textarea rows="4" cols="40" readonly style="width: 210px; height: 60px;" draggable="false" id="' + result[a].paramKey
					+ '" name="' + result[a].paramKey + '"class="tf_form_input" ' + disabled + '>'+val+'</textarea>';
					inner += '<a id="menuBtn" href="javascript:;" onclick=\'showCustomDiv("'+paramValue+'","'+result[a].paramKey+'")\' >选择</a>'
//					}
				inner += '</td>';
				
			} else {
				inner += '<td style="text-align:left;padding:5px 0px 5px 5px;">';
				inner += '<textarea rows="2" cols="40" style="width: 230px; height: 28px;" draggable="false" id="' + result[a].paramKey
				+ '" name="' + result[a].paramKey + '"class="tf_form_input" ' + disabled + '>'+val+'</textarea>';
				inner += '</td>';
				
			}
			if (a % 2 == 0) {
				inner += '</tr>';
			}
		}*/
	}

	// 总的参数名称

	serviceParamKeyName = [];
	serviceParamKeyName = tmepParamKeyName;

	if (result.length % 2 != 0) {
		inner += '<td></td><td></td>';
		inner += '</tr>';
	}
	$('#servicePolicyParamTable').append(inner);
}

function selectChange(paramKey) {
	if(paramKey == 'MANAGEMENT_TARGET') {
		$('#managementTargetId').val($('#'+paramKey).val());
	}
}

/**
 * 构造服务策略参数表单
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
		url : ctx + "/workflow/strategy/serviceStrategy_queryServiceParam.action?mid=" + mid,
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
//根据值弹出不通层
var customAreaId;
function showCustomDiv(paramValue,paramKey) {
	customAreaId = paramKey;
	var width = 500, height = 400;
	//点击选择事件,弹出选择脚本框;
	if(paramValue == "shell") {
		shellTreeInit();
		$("#shellTreeDiv").dialog({
			autoOpen : true,
			modal : true,
			width : width,
			height : height,
			resizable : true
		});
		$("#shellTreeDiv").dialog("option", "title", "选择脚本");
	} else if(paramValue == "server"){
		serverTreeInit();
		$("#serverTreeDiv").dialog({
			autoOpen : true,
			modal : true,
			width : width,
			height : height,
			resizable : false
		});
		$("#serverTreeDiv").dialog("option", "title", "选择服务器");
	} else if(paramValue == "software"){
		softwareTreeInit();
		$("#softwareTreeDiv").dialog({
			autoOpen : true,
			modal : true,
			width : width,
			height : height,
			resizable : false
		});
		$("#softwareTreeDiv").dialog("option", "title", "选择介质");
	} else if(paramValue == "managementTarget"){
		managementTreeInit();
		$("#managementTreeDiv").dialog({
			autoOpen : true,
			modal : true,
			width : width,
			height : height,
			resizable : false
		});
		$("#managementTreeDiv").dialog("option", "title", "选择管理对象");
	} else if(paramValue == "serverVarType"){
		serverVarTreeInit();
		$("#serverVarTreeDiv").dialog({
			autoOpen : true,
			modal : true,
			width : width,
			height : height,
			resizable : false
		});
		$("#serverVarTreeDiv").dialog("option", "title", "选择服务器变量");
	} else if(paramValue == "fileVarType"){
		fileTreeInit();
		$("#fileTreeDiv").dialog({
			autoOpen : true,
			modal : true,
			width : width,
			height : height,
			resizable : false
		});
		$("#fileTreeDiv").dialog("option", "title", "选择文件变量");
	}
}
//定义公共节点
var customNodes;
//-----------------------------脚本树开始----------------------------------------
//设置树属性
var settingShell = {
		check: {
			enable: true,
			chkboxType: {"Y":"", "N":""}
		},
		view: {
			dblClickExpand: false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			}
		},
		callback: {
			beforeClick: beforeClickShell,
			onCheck: onCheckShell
		}
	};

	function beforeClickShell(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("shellTreeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	//勾选方法
	function onCheckShell(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("shellTreeDemo");
		customNodes = zTree.getCheckedNodes(true);
	}

//初始化树节点信息
function shellTreeInit() {
	var t = $("#shellTreeDemo");
	var shellAreaVal = $("#"+customAreaId).val().trim();
	var shellArr = new Array();
	if(shellAreaVal != "") {
		shellArr = shellAreaVal.split(",");
	}
	// 加载脚本树
	$.ajax({
		type : "POST",
		url : ctx+"/device/groupscript/loadGroupScript.do",
		data : {
			"tempTypeId" : $('#tempTypeId').val()
		},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			var zNodes = data;
			
//			for ( var i = 0; i < zNodes.length; i++) {
//				if (zNodes[i].name == "Default") {
//					zNodes[i].name = "未分组";
//				}
//			}
			for ( var i = 0; i < zNodes.length; i++) {
				var node = zNodes[i];
				for ( var j = 0; j < shellArr.length; j++) {
					if(node.name == shellArr[j]) {
						zNodes[i].checked = true;
					}
				}
			}
			//重新获取选中的节点
			t = $.fn.zTree.init(t, settingShell, zNodes);
			customNodes = t.getCheckedNodes(true);
			return true;
		},
		error : function() {
			showTip("添加失败！");
			flage = false;
		}
	});
}
//-----------------------------脚本树结束----------------------------------------

//-----------------------------服务器树开始----------------------------------------
//设置树属性
var settingServer = {
		check: {
			enable: true,
			chkboxType: {"Y":"", "N":""}
		},
		view: {
			dblClickExpand: false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			}
		},
		callback: {
			beforeClick: beforeClickServer,
			onCheck: onCheckServer
		}
	};

	function beforeClickServer(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("serverTreeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	//勾选方法
	function onCheckServer(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("serverTreeDemo");
		customNodes = zTree.getCheckedNodes(true);
	}

//初始化服务器树节点信息
function serverTreeInit() {
	var t = $("#serverTreeDemo");
	var serverAreaVal = $("#"+customAreaId).val().trim();
	var serverArr = new Array();
	if(serverAreaVal != "") {
		serverArr = serverAreaVal.split(",");
	}
	// 加载服务器树
	$.ajax({
		type : "POST",
		//带有权限控制的
		url : ctx + "/system/deviceattr/group_loadGroupDevice.do",
		//没有权限控制的
		//url : ctx + "/system/deviceattr/group_loadGroupDeviceNoPower.do",
		data : {
			"tempTypeId" : $('#tempTypeId').val()
		},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			var zNodes = data;
			
//			for ( var i = 0; i < zNodes.length; i++) {
//				if (zNodes[i].name == "Default") {
//					zNodes[i].name = "未分组";
//				}
//			}
			for ( var i = 0; i < zNodes.length; i++) {
				var node = zNodes[i];
				for ( var j = 0; j < serverArr.length; j++) {
					if(node.name == serverArr[j]) {
						zNodes[i].checked = true;
					}
				}
			}
			//重新获取选中的节点
			t = $.fn.zTree.init(t, settingServer, zNodes);
			customNodes = t.getCheckedNodes(true);
			return true;
		},
		error : function() {
			showTip("添加失败！");
			flage = false;
		}
	});
}
//-----------------------------服务器树结束----------------------------------------
//-----------------------------软件包树开始----------------------------------------
//设置树属性
var settingSoftware = {
		check: {
			enable: true,
			chkboxType: {"Y":"", "N":""}
		},
		view: {
			dblClickExpand: false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			}
		},
		callback: {
			beforeClick: beforeClickSoftware,
			onCheck: onCheckSoftware
		}
};

function beforeClickSoftware(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("softwareTreeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
//勾选方法
function onCheckSoftware(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("softwareTreeDemo");
	customNodes = zTree.getCheckedNodes(true);
}

//初始化服务器树节点信息
function softwareTreeInit() {
	var t = $("#softwareTreeDemo");
	var softwareAreaVal = $("#"+customAreaId).val().trim();
	var softwareArr = new Array();
	if(softwareAreaVal != "") {
		softwareArr = softwareAreaVal.split(",");
	}
	// 加载服务器树
	$.ajax({
		type : "POST",
		url : ctx + "/device/groupmedia/loadGroupMedia.do",
		data : {
			"tempTypeId" : $('#tempTypeId').val()
		},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			var zNodes = data;
			
//			for ( var i = 0; i < zNodes.length; i++) {
//				if (zNodes[i].name == "Default") {
//					zNodes[i].name = "未分组";
//				}
//			}
			for ( var i = 0; i < zNodes.length; i++) {
				var node = zNodes[i];
				for ( var j = 0; j < softwareArr.length; j++) {
					if(node.name == softwareArr[j]) {
						zNodes[i].checked = true;
					}
				}
			}
			//重新获取选中的节点
			t = $.fn.zTree.init(t, settingSoftware, zNodes);
			customNodes = t.getCheckedNodes(true);
			return true;
		},
		error : function() {
			showTip("添加失败！");
			flage = false;
		}
	});
}
//-----------------------------软件包树结束----------------------------------------
function loadReady() {
	var bodyH = demoIframe.contents().find("body").get(0).scrollHeight, htmlH = demoIframe.contents().find("html").get(0).scrollHeight, maxH = Math.max(bodyH, htmlH), minH = Math
			.min(bodyH, htmlH), h = demoIframe.height() >= maxH ? minH : maxH;
	if (h < 530)
		h = 530;
	demoIframe.height(h);
}



//保存复选狂方法
function saveCustom (divId) {
	v = "";
	var paramKey="";
	if (customNodes.length <= 0) {
		showTip("请选择脚本！")
	} else {
		for (var i=0, l=customNodes.length; i<l; i++) {
			if(v.indexOf(customNodes[i].name) < 0) {
				v += customNodes[i].name + ",";
				paramKey += customNodes[i].id + ",";
			}
		}
		if (v.length > 0 ) {
			v = v.substring(0, v.length-1);
			paramKey = paramKey.substring(0, paramKey.length-1);
		}
		var cityObj = $("#"+customAreaId);
		cityObj.attr("value", v);
		if('managementTreeDiv'==divId){
			$("#managementTargetId").attr("value", paramKey);
		}else if('serverVarTreeDiv'==divId){
			$('#serverTargetId').attr("value", paramKey);
		}else if('fileTreeDiv'==divId){
			$("#fileTargetId").attr("value", paramKey);
		}
		$("#"+divId).dialog("close");
	}
}
//取消复选狂选择方法
function cancelCustom(divId) {
	$("#" + divId).dialog("close");
}

//-----------------------------管理对象树开始----------------------------------------
//设置树属性
var settingManagement = {
		check: {
			enable: true,
			chkboxType: {"Y":"", "N":""}
		},
		view: {
			dblClickExpand: false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			}
		},
		callback: {
			beforeClick: beforeClickManagement,
			onCheck: onCheckManagement
		}
	};

	function beforeClickManagement(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("managementTreeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	//勾选方法
	function onCheckManagement(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("managementTreeDemo");
		customNodes = zTree.getCheckedNodes(true);
	}


//管理对象弹出选择

function managementTreeInit(){
	nodeObjTemp = window.parent.TankFlow.getComponentContent("2");
	//var startData = JSON.parse(nodeObjTemp.json);
	var t = $("#managementTreeDemo");
	var managementVal = $("#"+customAreaId).val().trim();
	var managementArr = new Array();
	if(managementVal != "") {
		managementArr = managementVal.split(",");
	}
	// 加载开始节点参数
	$.ajax({
		type : "POST",
		url : ctx + "/workflow/template/getParamsTreeByStatrt.do",
		data : {
			"startData" : nodeObjTemp.json,
			"startType":"SERVER"
		},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			var zNodes = data;
			for ( var i = 0; i < zNodes.length; i++) {
				var node = zNodes[i];
				for ( var j = 0; j < managementArr.length; j++) {
					if(node.name == managementArr[j]) {
						zNodes[i].checked = true;
					}
				}
			}
			//重新获取选中的节点
			t = $.fn.zTree.init(t, settingManagement, zNodes);
			customNodes = t.getCheckedNodes(true);
			return true;
		},
		error : function() {
			showTip("加载失败！");
			flage = false;
		}
	});
}

//-----------------------------服务器对象树开始----------------------------------------
//设置树属性
var settingServerVar = {
		check: {
			enable: true,
			chkboxType: {"Y":"", "N":""}
		},
		view: {
			dblClickExpand: false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			}
		},
		callback: {
			beforeClick: beforeClickServerVar,
			onCheck: onCheckServerVar
		}
};

function beforeClickServerVar(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("serverVarTreeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
//勾选方法
function onCheckServerVar(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("serverVarTreeDemo");
	customNodes = zTree.getCheckedNodes(true);
}
//服务器变量对象弹出选择
function serverVarTreeInit(){
	nodeObjTemp = window.parent.TankFlow.getComponentContent("2");
	//var startData = JSON.parse(nodeObjTemp.json);
	var t = $("#serverVarTreeDemo");
	var serverVal = $("#"+customAreaId).val().trim();
	var serverArr = new Array();
	if(serverVal != "") {
		serverArr = serverVal.split(",");
	}
	// 加载开始节点参数
	$.ajax({
		type : "POST",
		url : ctx + "/workflow/template/getParamsTreeByStatrt.do",
		data : {
			"startData" : nodeObjTemp.json,
			"startType":"SERVER"
		},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			var zNodes = data;
			for ( var i = 0; i < zNodes.length; i++) {
				var node = zNodes[i];
				for ( var j = 0; j < serverArr.length; j++) {
					if(node.name == serverArr[j]) {
						zNodes[i].checked = true;
					}
				}
			}
			//重新获取选中的节点
			t = $.fn.zTree.init(t, settingServerVar, zNodes);
			customNodes = t.getCheckedNodes(true);
			return true;
		},
		error : function() {
			showTip("加载失败！");
			flage = false;
		}
	});
}
//-----------------------------服务器对象树开始----------------------------------------
//设置树属性
var settingFile = {
		check: {
			enable: true,
			chkboxType: {"Y":"", "N":""}
		},
		view: {
			dblClickExpand: false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			}
		},
		callback: {
			beforeClick: beforeClickFile,
			onCheck: onCheckFile
		}
};

function beforeClickFile(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("fileTreeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
//勾选方法
function onCheckFile(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("fileTreeDemo");
	customNodes = zTree.getCheckedNodes(true);
}
//文件变量对象弹出选择
function fileTreeInit(){
	nodeObjTemp = window.parent.TankFlow.getComponentContent("2");
	//var startData = JSON.parse(nodeObjTemp.json);
	var t = $("#fileTreeDemo");
	var fileVal = $("#"+customAreaId).val().trim();
	var fileArr = new Array();
	if(fileVal != "") {
		fileArr = fileVal.split(",");
	}
	// 加载开始节点参数
	$.ajax({
		type : "POST",
		url : ctx + "/workflow/template/getParamsTreeByStatrt.do",
		data : {
			"startData" : nodeObjTemp.json,
			"startType":"FILE"
		},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			var zNodes = data;
			for ( var i = 0; i < zNodes.length; i++) {
				var node = zNodes[i];
				for ( var j = 0; j < fileArr.length; j++) {
					if(node.name == fileArr[j]) {
						zNodes[i].checked = true;
					}
				}
			}
			//重新获取选中的节点
			t = $.fn.zTree.init(t, settingFile, zNodes);
			customNodes = t.getCheckedNodes(true);
			return true;
		},
		error : function() {
			showTip("加载失败！");
			flage = false;
		}
	});
}


function checkDate(startTime,endTime){ 
	var now= new Date(); 
	var year=now.getYear(); 
	var month=now.getMonth()+1; 
	var day=now.getDate(); 
	if(startTime.length>0 && endTime.length>0){
		var date = new Date();
	    var startTmp=startTime.split(":");  
	    var endTmp=endTime.split(":");
	    var sd=new Date(year,month,day,startTmp[0],startTmp[1]); 
	    var ed=new Date(year,month,day,endTmp[0],endTmp[1]);  
	    if(sd.getTime()>ed.getTime()){   
	        return false;     
	    }
	} 
	return true;     
}
