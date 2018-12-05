/**
 * 页面初始化
 */
var nodeArray;
$(document).ready(function() {
	//添加一个校验方法，可以禁止用户在节点名字段输入逗号“,”
	$.validator.addMethod("forbidComma", function(value) {
		if(value.indexOf(",") != -1) return false;
		return true;
	}, '节点名中不能包含逗号'); 
	
	//设置验证
	$("#decisionForm").validate({
		rules: {"decisionName":{"required":true,"maxlength":50,"forbidComma":true}},
		messages: {"decisionName":{"required":"请输入节点名称","maxlength":"字符长度不得大于50","forbidComma":"节点名中不能包含逗号"}},
		submitHandler: function() {
			onSave();
		}
	});
	
	//获取节点数据
	var compData = window.parent.TankFlow.getComponentContent($('#dNodeId').val());
	
	//给表单赋值
	$('#decisionName').val(compData.name);
	
	//获取分支节点数据，初始化表格
	nodeArray = window.parent.TankFlow.getNextNodes($('#dNodeId').val());
	if(nodeArray != null){
		var inner = '', rowId;
		
		//遍历输出节点数组，生成路由设置行
		for(var i = 0 ; i < nodeArray.length ; i++){
			rowId = nodeArray[i].id;
			if($('#state').val() == "design"){
				inner ='<tr id="exception_'+rowId+'" align="left" style="height:30px">'+
							'<td><input type="text" class="tf_form_input" style="width:95%" id="nodeExpression_'+rowId+'" name="nodeExpression_'+rowId+'"/></td>'+
							'<td><input type="text" class="tf_form_input" style="width:95%" id="nodeRoute_'+rowId+'" name="nodeRoute_'+rowId+'"/></td>'+
							'<td><input type="text" class="tf_form_input" style="width:95%;color:#000000" id="nodeName_'+rowId+'" name="nodeName_'+rowId+'" value="'+nodeArray[i].name+'" disabled/></td>'+
							'<input type="hidden" id="nodeId_'+rowId+'" value="'+rowId+'"/>'+
						'</tr>';
			}else{
				inner ='<tr id="exception_'+rowId+'" align="left" style="height:30px">'+
							'<td><input type="text" class="tf_form_input" style="width:95%" id="nodeExpression_'+rowId+'" name="nodeExpression_'+rowId+'" disabled/></td>'+
							'<td><input type="text" class="tf_form_input" style="width:95%" id="nodeRoute_'+rowId+'" name="nodeRoute_'+rowId+'" disabled/></td>'+
							'<td><input type="text" class="tf_form_input" style="width:95%;color:#000000" id="nodeName_'+rowId+'" name="nodeName_'+rowId+'" value="'+nodeArray[i].nodeName+'" disabled/></td>'+
							'<input type="hidden" id="nodeId_'+rowId+'" value="'+rowId+'"/>'+
						'</tr>';
			}
			
			//增加行追加到表格
			$('#expressionTable tbody').append(inner);  
			
			//添加验证规则
			$("#nodeExpression_"+rowId).rules("add", { required: true, maxlength:50, messages: { required: "请输入表达式",maxlength:"最大长度50"} });
			$("#nodeRoute_"+rowId).rules("add", { required: true, maxlength:50, messages: { required: "请输入路由名称",maxlength:"最大长度50"} });
		}
	}
	
	//表单赋值
	var jsonData = JSON.parse(compData.json);
	if(jsonData != null && jsonData.nextNodes != null){
		for(var i = 0 ; i < jsonData.nextNodes.length ; i++){
			var n = jsonData.nextNodes[i];
			$('#nodeExpression_'+n.nodeId).val(n.nodeExpression);
			$('#nodeRoute_'+n.nodeId).val(n.nodeRoute);
		}
	}
	
	if($('#state').val() != "design"){
		$('#decisionName').prop("disabled",true);
		$('#decisionType').prop("disabled",true);
		$('#btnTr').css("display","none");
	}
});
/**
 * 保存节点数据
 */
function decisionSave(){
	$("#decisionForm").submit();
}

function onSave(){
	if($('#state').val() == "design"){
		var paramsArr =  new Array();
		if(nodeArray.length == 0){
			alert("请先给选择节点设置输出节点后再保存。");
			return;
		}
		
		for(var i = 0 ; i < nodeArray.length ; i++){
			var rowId = nodeArray[i].id;
			paramsArr.push({
				"nodeId":rowId,
				"nodeRoute":$('#nodeRoute_'+rowId).val(),
				"nodeExpression":$('#nodeExpression_'+rowId).val(),
				"nodeName" :$('#nodeName_'+rowId).val()
			});
		}
		window.parent.TankFlow.onSubmitComponent($('#dNodeId').val(),$('#decisionName').val(),JSON.stringify({"type":$('#decisionType').val(),"nextNodes":paramsArr}));
		cancel();
	}
}

/**
 * 关闭窗口
 */
function cancel(){
	window.parent.TankFlow.closeComponentDialog();
}

/**
 * 改变显示的配置行
 */
function showCurTr(){
	if($('#decisionType').val() == "action"){
		$('#decisionActionTr').css("display","block");
		$('#decisionExpressionTr').css("display","none");
	}else if($('#decisionType').val() == "expression"){
		$('#decisionActionTr').css("display","none");
		$('#decisionExpressionTr').css("display","block");
	}
}