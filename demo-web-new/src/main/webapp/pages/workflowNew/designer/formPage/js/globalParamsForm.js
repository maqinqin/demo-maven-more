/**
 * 表单初始化
 */
$(document).ready(function() {
	//设置验证
	$("#globalParamsForm").validate({
		rules: {},
		messages: {},
		submitHandler: function() {
			onSave();
		}
	});
	
	//初始化
	if($('#state').val() == "design"){
		var params = $('#dNodeId').val();
		var nodeObjTemp = null;
		try{
			nodeObjTemp = window.parent.getComponentContent(params);
		}finally{
			if(nodeObjTemp!=null && nodeObjTemp!=""){
				var data = JSON.parse(nodeObjTemp.json);
				initForm(data);
			}
		}
	}else if($('#state').val() == "definition"){
		var modelId = $('#modelId').val();//获取modelId
		//根据modelId获取全局参数信息
		$.ajax({
		    type : "POST",
			url:$('#url').val()+"/baseForm/getModelGlobalParamsAct.action",
			data : {
		    	 "_fw_service_id":"getModelGlobalParamsSrv",
		    	 "modelId":modelId
		    },
		    async:true,
		    cache:false,
		    success : function(data) {
		    	var resultObj = JSON.parse(data);//结果集
		    	initForm(resultObj);
		    },
		    error : function(e) {
		      	alert("error");
		    }
		});
	}else if($('#state').val() == "instance"){
		var instanceId = $('#instanceId').val();//获取modelId
		$('#btnTable').css('display','none');//隐藏按钮
		//根据instanceId获取全局参数信息
		$.ajax({
		    type : "POST",
			url:$('#url').val()+"/baseForm/getInstanceGlobalParamsAct.action",
			data : {
		    	 "_fw_service_id":"getInstanceGlobalParamsSrv",
		    	 "instanceId":instanceId
		    },
		    async:true,
		    cache:false,
		    success : function(data) {
		    	var resultObj = JSON.parse(data);//结果集
		    	initForm(resultObj);
		    },
		    error : function(e) {
		      	alert("error");
		    }
		});
		//隐藏“增加全局参数”按钮和基本操作列
		$('#addRow').remove();
		$('#basicOperate').remove();
	}else if($('#state').val() == "instanceEdit"){
		
	}
	
	
});

/**
 * 根据返回结果初始化表单
 * @param resultObj
 */
function initForm(resultObj){
	if(resultObj != null){
		for ( var i = 0; i < resultObj.length; i++) {
			addGlobalParamsTr(resultObj[i]);
		}
	}
}

/**
 * 添加数据行
 */
function addGlobalParamsTr(paramsVo){
	
	var maxRow = $('#propTable tbody > tr').length;
	var inner = '',paramKey = '',paramName = '',paramValue = '';
	if(paramsVo != null){
		paramKey   = paramsVo.paramKey;
		paramName  = paramsVo.paramName;
		paramValue = paramsVo.paramValue ? paramsVo.paramValue : "";
	}
	if($('#state').val() == "design" || $('#state').val() == "definition"){
		inner +='<tr id="prop_'+maxRow+'">'+
			'<td align="center"><input type="text" style="width:97%;text-align:center;" id="paramKey_'+maxRow+'" name="paramKey_'+maxRow+'" value="' + paramKey + '"/></td>'+
			'<td align="center"><input type="text" style="width:97%;text-align:center;" id="paramName_'+maxRow+'" name="paramName_'+maxRow+'" value="' + paramName + '"/></td>'+
			'<td align="center"><input type="text" style="width:97%;text-align:center;" id="paramValue_'+maxRow+'" name="paramValue_'+maxRow+'" value="' + paramValue + '"/></td>'+
			'<td align="center" style="text-align:center"><a href="#" onclick="javascript:hiddenRow(\'prop_'+maxRow+'\')" style="text-decoration : none"><div class="panel_btn"><div class="btn_icon del_row_icon"></div><span>'+i18nShow('deleteline2')+'</a></td>'+
		'</tr>';
	}else if($('#state').val() == "instance"){
		inner +='<tr id="prop_'+maxRow+'">'+
			'<td align="center"><input type="text" style="width:97%;text-align:center;" id="paramKey_'+maxRow+'" name="paramKey_'+maxRow+'" value="' + paramKey + '" disabled/></td>'+
			'<td align="center"><input type="text" style="width:97%;text-align:center;" id="paramName_'+maxRow+'" name="paramName_'+maxRow+'" value="' + paramName + '" disabled/></td>'+
			'<td align="center"><input type="text" style="width:97%;text-align:center;" id="paramValue_'+maxRow+'" name="paramValue_'+maxRow+'" value="' + paramValue + '" disabled/></td>'+
		'</tr>';
	}
	 //增加行追加到表格
	 $('#propTable tbody').append(inner);
	
	 //添加验证规则
	 $('#paramKey_'+maxRow).rules("add", { required: true, maxlength:100, messages: { required:i18nShow('paraname'),maxlength:i18nShow('maxline1')} });
	 $('#paramName_'+maxRow).rules("add", { required: true, maxlength:200,messages: { required:i18nShow('paravalue'), maxlength:i18nShow('maxline2')} });
	 $('#paramValue_'+maxRow).rules("add", { maxlength:100,messages: { maxlength:i18nShow('maxline1')} });
}
/**
 * 删除行
 * @param id
 */
function hiddenRow(id){
	showTip(i18nShow('deleteline1'),function(){
		$('#'+id).hide();
	});
}


//提交表单
function paramsSave(){
	$("#globalParamsForm").submit();
}

//执行保存
function onSave(){
	/**
	 * 定义参数集合
	 */
	var paramsArr =  new Array();
	/**
	 * 对于隐藏行则不予保存
	 */
	if($('#state').val() == "design"){
		$('#propTable tbody > tr').each(function(index,item){
			if($(item).css("display") != "none"){
				var jsonRequest = {
					"paramKey":$('#paramKey_'+index).val(),
					"paramName":$('#paramName_'+index).val(),
					"paramValue":$('#paramValue_'+index).val()
				};
				paramsArr.push(jsonRequest);
			}
		});
		window.parent.onSubmitComponent($('#dNodeId').val(),"开始",JSON.stringify(paramsArr));
		cancel();
	}else if($('#state').val() == "definition"){
		for(var b = 0 ; b < $('#propTable tbody > tr').length ; b++){
			if($('#prop_'+b).css("display")!="none"){
				var jsonRequest = {
					"paramId":null,
					"paramKey":$('#paramKey_'+b).val(),
					"paramName":$('#paramName_'+b).val(),
					"paramValue":$('#paramValue_'+b).val(),
					"modelId":$('#modelId').val(),
					"isAuto":"N"
				};
				paramsArr[paramsArr.length] = jsonRequest;
			}
		}
		$.ajax({
		    type : "POST",
			url:$('#url').val()+"/baseForm/saveModelGlobalParamsAct.action",
			data : {
		    	 "_fw_service_id":"saveModelGlobalParamsSrv",
		    	 "jsonData":JSON.stringify({params:paramsArr}),
		    	 'jsonClass' : 'com.ccb.iomp.cloud.data.vo.workflow.BpmModelGlobalParamsVo'
		    },
		    async:true,
		    cache:false,
		    success : function(data) {
		    	cancel();
		    },
		    error : function(e) {
		      	alert("error");
		    }
		});
	}else if($('#state').val() == "instance"){
		for(var b = 0 ; b < $('#propTable tbody > tr').length ; b++){
			if($('#prop_'+b).css("display")!="none"){
				var jsonRequest = {
					"paramId":null,	
					"paramKey":$('#paramKey_'+b).val(),
					"paramName":$('#paramName_'+b).val(),
					"paramValue":$('#paramValue_'+b).val(),
					"instanceId":$('#instanceId').val(),
					"isAuto":"N"
				};
				paramsArr[paramsArr.length] = jsonRequest;
			}
		}
		$.ajax({
		    type : "POST",
			url:$('#url').val()+"/baseForm/saveInstanceGlobalParamsAct.action",
			data : {
		    	 "_fw_service_id":"saveInstanceGlobalParamsSrv",
		    	 "jsonData":JSON.stringify({params:paramsArr}),
		    	 'jsonClass' : 'com.ccb.iomp.cloud.data.vo.workflow.BpmInstanceGlobalParamsVo'
		    },
		    async:true,
		    cache:false,
		    success : function(data) {
		    	cancel();
		    },
		    error : function(e) {
		      	alert("error");
		    }
		});
	}
}

/**
 * 关闭窗口
 */
function cancel(){
	window.parent.closeComponentDialog("startComponent_div");
}