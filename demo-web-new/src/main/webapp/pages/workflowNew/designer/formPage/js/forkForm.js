/**
 * 路由节点树设置
 */
var setting = {
	check: {
		enable: true,
		chkboxType: {"Y":"", "N":""}
	},
	view: {
		dblClickExpand: false,
		selectedMulti: false,
		showIcon:false,
		showLine:false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeClick: beforeClick,
		onCheck: onCheck
	}
};

/**
 * 页面初始化
 */
$(document).ready(function() {
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
	$("#forkForm").validate({
		rules: {"forkName":{"required":true,"maxlength":50,"forbidComma":true}},
		messages: {"forkName":{"required":i18nShow('input_node_name'),"maxlength":i18nShow('maxline4'),"forbidComma":i18nShow('node_notice')}},
		submitHandler: function() {
			onSave();
		}
	});
	
	//获取组件数据
	var compData = window.parent.getComponentContent($('#dNodeId').val());
	var jsonData = null;
	var routeArray = null;
	if(compData.json){
		jsonData = JSON.parse(compData.json);
		//获取路由数组
		routeArray = jsonData.routeArray;
	}
	
	//给组件名赋值
	$('#forkName').val(compData.name);
	
	//给类型赋值为表达式
	$('#decisionType').val('expression');
	
	//初始化已有路由
	if(routeArray != null){
		var maxRow,innerTr = '',innerMenu = '';
		//创建表格行
		for(var i = 0 ; i < routeArray.length; i++){
			maxRow = $('#expressionTable tbody > tr').length;
			if($('#state').val() == "design" || $('#state').val() == "definition" || ($('#state').val() == "instance" && $('#userAdmin').val() == "userAdmin")){
				innerTr = getTr(maxRow,routeArray[i]);
				//创建节点树
				innerMenu = '<ul id="nextNodeTree_'+maxRow+'" class="ztree" style="border:1px solid #cccccc;background:#FFFFFF;width:249px; height:60px;display:none;position: absolute;overflow:auto;"></ul>';
				$('#menuContent').append(innerMenu);
				
				//根据routeArray的nodeRouteId初始化节点选中状态
				var zNodes = getTreeNodes();
				var nodeRouteId = routeArray[i].nodeRouteId;
				for ( var j = 0, l = nodeRouteId.length; j < l; j++) {
					for ( var k = 0; k < zNodes.length; k++) {
						if(zNodes[k].id == nodeRouteId[j]){
							zNodes[k].checked = true;
							break;
						}
					}
				}
				
				$.fn.zTree.init($("#nextNodeTree_"+maxRow), setting, zNodes);
			}else{
				//添加节点行
				innerTr = getTr(maxRow,routeArray[i],true);
				
				//禁用其他组件
				$('#forkName').prop("disabled",true);
				$('#btnTable').hide();
			}
			//增加行追加到表格
			$('#expressionTable tbody').append(innerTr); 
			
			//增加验证规则
			addRules();
		}
	}
});

function getTreeNodes(){
	//持有路由节点树的数组
	var zNodes = [];
	//获取分支节点数据
	var nodeArray = window.parent.getNextNodes($('#dNodeId').val());
	//准备下拉列表中的内容
	if(nodeArray != null){
		for(var i = 0 ; i < nodeArray.length ; i++){
			zNodes.push({id:nodeArray[i].id,pId:0,name:nodeArray[i].name});
		}
	}
	return zNodes;
}					
					
/**
 * 复选预处理函数
 */
function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("nextNodeTree_"+currentNodeId);
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

/**
 * 复选处理函数
 */
function onCheck(e, treeId, treeNode) {
	//显示输入框文本内容
	var zTree = $.fn.zTree.getZTreeObj("nextNodeTree_"+currentNodeId);
	var nodes = zTree.getCheckedNodes(true);
	v = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	var $routeObj = $("#nodeRoute_"+currentNodeId);
	$routeObj.val(v);
}

/**
 * 持有当前选择输入框id
 */
var currentNodeId;
/**
 * 在指定id输入框下面显示menu
 */
function showMenu(id) {
	//显示树
	var nodeObj = $("#nodeRoute_"+id);
	var nodeOffset = $("#nodeRoute_"+id).offset();
	currentNodeId = id;
	$("#nextNodeTree_"+currentNodeId).css({left:nodeOffset.left + "px", top:nodeOffset.top + nodeObj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#nextNodeTree_"+currentNodeId).fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "nodeName_"+currentNodeId || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

/**
 * 添加新行到表单中
 */
function addRow(){
	//创建新行
	var maxRow = $('#expressionTable tbody > tr').length;
	var inner = getTr(maxRow);
	
	//增加行追加到表格
	$('#expressionTable tbody').append(inner); 
	
	//增加验证规则
	addRules();
	
	//创建节点树
	inner = '<ul id="nextNodeTree_'+maxRow+'" class="ztree" style="border:1px solid #cccccc;background:#FFFFFF;width:249px; height:60px;display:none;position: absolute;overflow:auto;"></ul>';
	$('#menuContent').append(inner);
	
	$.fn.zTree.init($("#nextNodeTree_"+maxRow), setting, getTreeNodes());
}

function removeRow(index){
	$('#exception_'+index).remove();
}
var inputArr = [];
function getTr(index,routeObj,isDisabled){
	var expression = routeObj ? routeObj.nodeExpression : "";
	var route = routeObj ? routeObj.nodeRoute : "";
	var innerOper;
	var disabled = isDisabled ? "disabled" : "";
	if(isDisabled){
		innerOper = "";
	}else{
		innerOper = '<td  align="center"><a href="#" onclick="removeRow('+index+')">'+i18nShow('delroute')+'</a></td>';
	}
	var inner = '<tr id="exception_'+index+'" align="center">'+
				   '<td><input type="text" style="width:97%;text-align:center;" id="nodeExpression_'+index+'" name="nodeExpression_'+index+'" value="'+expression+'" '+disabled+'/></td>'+
				   '<td><input type="text" style="width:97%;text-align:center;"  id="nodeRoute_'     +index+'" name="nodeRoute_'     +index+'" onclick="showMenu('+index+')" value="'+route+'" '+disabled+'/></td>'+
				   innerOper + '</tr>';
	
	//保存输入框id，添加验证规则时用到
	inputArr.push("#nodeExpression_"+index);
	inputArr.push("#nodeRoute_"+index);
	
	return inner;
}
//添加验证规则
function addRules(){
	while(inputArr.length>0){
		var inputId = inputArr.pop();
		if(inputId.indexOf("nodeRoute") != -1){
			$(inputId).rules("add", { required: true, maxlength:50, messages: { required:i18nShow('input_route'),maxlength:i18nShow('maxline3') } });
		}else{
			$(inputId).rules("add", { required: true, maxlength:50, messages: { required:i18nShow('input_exp'),maxlength:i18nShow('maxline3') } });
		}
	}
}

/**
 * 保存节点数据
 */
function forkSave(){
	$("#forkForm").submit();
}

function onSave(){
if($('#state').val() == "design" || $('#state').val() == "definition" || 
   ($('#state').val() == "instance" && $('#userAdmin').val() == "userAdmin")){
		var paramsArr =  new Array();
		for(var i = 0 ; i < $('#expressionTable tbody > tr').length ; i++){
			var jsonRequest = {
				"nodeRoute":$('#nodeRoute_' + i).val(),
				"nodeRouteId":getNodeRouteId(i),
				"nodeExpression":$('#nodeExpression_' + i).val() 
			};
			paramsArr[paramsArr.length] = jsonRequest;
		}
		window.parent.onSubmitComponent($('#dNodeId').val(),$('#forkName').val(),JSON.stringify({"type":$('#decisionType').val(),"routeArray":paramsArr}));
		cancel();
	}
}

function getNodeRouteId(treeId){
	var zTree = $.fn.zTree.getZTreeObj("nextNodeTree_"+treeId);
	var nodes = zTree.getCheckedNodes(true);
	var result = new Array();
	for (var i=0, l=nodes.length; i<l; i++) {
		result.push(nodes[i].id);
	}
	return result;
}
/**
 * 关闭窗口
 */
function cancel(){
	window.parent.closeComponentDialog("forkComponent_div");
}
/**
 * 改变显示的配置行
 */
function showCurTr(){
	if($('#decisionType').val() == "action"){
		$('#decisionActionTr').css("display","block");
		$('#expressionTable').css("display","none");
	}else if($('#decisionType').val() == "expression"){
		$('#decisionActionTr').css("display","none");
		$('#expressionTable').css("display","block");
	}
}