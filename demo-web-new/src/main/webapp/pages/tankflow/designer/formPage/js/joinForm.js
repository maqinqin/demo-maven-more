/**
 * 节点数据对象
 */
var nodeData;
var jsonData;
/**
 * 页面初始化
 */
$(document).ready(function() {
	//添加一个校验方法，可以禁止用户在节点名字段输入逗号“,”
	$.validator.addMethod("forbidComma", function(value) {
		if(value.indexOf(",")!=-1 || value.indexOf("，")!=-1)
			return false;
		return true;
	}, '节点名中不能包含逗号'); 
	
	//设置验证
	$("#joinForm").validate({
		rules: {"dNodeName":{"required":true,"maxlength":50,"forbidComma":true}},
		messages: {"dNodeName":{"required":"请输入节点名称","maxlength":"字符长度不得大于50","forbidComma":"节点名中不能包含逗号"}},
		submitHandler: function() {
			onSave();
		}
	});
	
	//根据当前流程状态初始化
	if($('#state').val()=="design"){
		//获取当前节点数据对象：name/json/type
		nodeData = window.parent.TankFlow.getComponentContent($('#dNodeId').val());
		//取出自定义json数据
		$('#dNodeName').val(nodeData.name);
//		jsonData = JSON.parse(nodeData.json);
//		initForm();
	}else if($('#state').val()=="definition"){
//		getModelJoinNodeFormData();
	}else if($('#state').val()=="instance"){
//		getInstanceJoinNodeFormData();
	}
});

/**
 * 从BPM_MODEL_JOIN_NODE获取当前节点数据对象
 */
function getModelJoinNodeFormData(){
	$.ajax({
	     type : "POST",
	     url : $('#url').val()+"/design/getComponentListAct.action",
	     data : {
	    	 "_fw_service_id":"getModelJoinFormDataSrv",
	    	 "modelId":$('#modelId').val(),
	    	 "nodeId":$('#nodeId').val()
	     },
	     async:false,
	     cache:false,
	     success : function(data) {
	    	 jsonData = JSON.parse(data);
	    	 initModelForm();
	     },
	     error : function(e) {
	      	alert("error");
	     }
	 });
}

/**
 * 从BPM_INSTANCE_JOIN_NODE获取当前节点数据对象
 */
function getInstanceJoinNodeFormData(){
	$.ajax({
	     type : "POST",
	     url : $('#url').val()+"/design/getComponentListAct.action",
	     data : {
	    	 "_fw_service_id":"getInstanceJoinFormDataSrv",
	    	 "instanceId":$('#instanceId').val(),
	    	 "nodeId":$('#nodeId').val()
	     },
	     async:false,
	     cache:false,
	     success : function(data) {
	    	 jsonData = JSON.parse(data);
	    	 initInstanceForm();
	     },
	     error : function(e) {
	      	alert("error");
	     }
	 });
}

/**
 * 设计阶段初始化表单
 */
function initForm(){
	//获取业务场景类型
//	getBusinessTypeOptions();
	
	//节点名称
	$('#dNodeName').val(nodeData.name);
	
	//设置业务类型并根据业务类型设置相应表单
//	$('#businessType').val(jsonData.businessType);
//	businessTypeChange();
}

/**
 * 模型阶段初始化表单
 */
function initModelForm(){
	//获取业务场景类型
	getBusinessTypeOptions();
	//节点名称
	$('#dNodeName').val(jsonData.joinNodeName);
	//设置业务类型并根据业务类型设置相应表单
	$('#businessType').val(jsonData.businessType);
	businessTypeChange();
}

/**
 * 实例阶段初始化表单
 */
function initInstanceForm(){
	//跟模型阶段一样初始化
	initModelForm();
	//根据当前userAdmin看是否禁用输入控件
	if($('#userAdmin').val() != "userAdmin"){
		$('#dNodeName').prop("disabled",true);
		$('#businessType').prop("disabled",true);
		$('#table_btn').hide();
	}
}

/**
 * 获取业务类型选项
 */
function getBusinessTypeOptions(){
	$.ajax({
	     type : "POST",
	     url : $('#url').val()+"/design/getComponentListAct.action",
	     data : {
	    	 "_fw_service_id":"getJoinBusinessTypeOptionsSrv"
	     },
	     async:false,
	     cache:false,
	     success : function(data) {
	    	 var inner = '';
	    	 var businessTypes = JSON.parse(data);
    		 for(var i=0; i<businessTypes.length; i++){
    			 inner += '<option value="'+businessTypes[i].dicCode+'">'+businessTypes[i].dicname+'</option>';
    		 }
    		 $('#businessType').append(inner);
	     },
	     error : function(e) {
	      	alert("error");
	     }
	 });
}

/**
 * 业务类型变化，显示相应表单
 */
function businessTypeChange(){
	switch($('#businessType').val()){
		case "1"://多路审批
			$('#div_multiPathApprove').show();
			fillMpaData();
			break;
		default:
			$('#div_multiPathApprove').hide();
			break;
	}
}

/**
 * 给tb_multiPathApprove填充数据
 */
function fillMpaData(){
	//如果有多路径审批数据，则初始化
	if(jsonData.nodeData){
		if(typeof(jsonData.nodeData) == "string"){
			jsonData.nodeData = JSON.parse(jsonData.nodeData);
		}
		for(var i=0; i<jsonData.nodeData.length; i++){
			addMpaData();
			$('#param_'+i).val(jsonData.nodeData[i].param);
			$('#exp_'+i).val(jsonData.nodeData[i].exp);
			$('#value_'+i).val(jsonData.nodeData[i].value);
			$('#nav_'+i).val(jsonData.nodeData[i].nav);
		}
	}
}

/**
 * 项tb_multiPathApprove中添加新行
 * @param index 新行序号
 */
function addMpaData(){
	var index = $('#tb_multiPathApprove tbody').length-1;
	//避免用户删除mpa项目后造成索引混乱，所以先判断该索引是否被使用，直到找到没被占用的索引再采用
	while($('#mpa_'+index).length > 0){
		index++;
	}
	//根据当前所处阶段和userAdmin初始化禁用设置
	var disabled = "";
	var show = "block";
	if($('#state').val() == "instance" && $('#userAdmin').val() != "userAdmin"){
		disabled = "disabled";
		show = "none";
	}
	var inner = '<tr id="mpa_'+index+'">'+
				'<td align="center"><select id="param_'+index+'" name="param_'+index+'" style="width:94%;" '+disabled+'>'+getParamOptions()+'</select></td>'+
				'<td align="center"><select id="exp_'  +index+'" name="exp_'  +index+'" style="width:94%;" '+disabled+'>'+getExpOptions()+'</select></td>'+
				'<td align="center"><input  id="value_'+index+'" name="value_'+index+'" style="width:94%;" '+disabled+' type="text"/></td>'+
				'<td align="center"><select id="nav_'  +index+'" name="nav_'  +index+'" style="width:94%;" '+disabled+'>'+getNavOptions()+'</td>'+
				'<td align="center"><a href="#" onclick="removeMpaData('+index+')" style="display:'+show+'">'+
				'		<div class="panel_btn">'+
				'		<div class="btn_icon del_row_icon"></div>'+
				'		<span>删除</span>'+
				'	</div>'+
				'</a></td></tr>';
	$('#tb_multiPathApprove tbody').append(inner);
}

/**
 * 获取全局参数选项列表
 */
var globalParams;
function getParamOptions(){
	if(!globalParams)
	{
		if($('#state').val() == "design"){
			globalParams = window.parent.getGlobalParams();
		}else if($('#state').val() == "definition"){
			$.ajax({
				url:$('#url').val()+"/model/newProcessInstanceAct.action",
				data:{
					_fw_service_id : "getModelGlobalParamsSrv",
					modelId : $('#modelId').val()
				},
				async:false,
				dataType:"json",
			    success: function(data) {
			    	globalParams =  data;
			    },
			    error:function(e){
			    	alert('获取全局参数错误！');
			    }
			});
		}else{
			$.ajax({
				url:$('#url').val()+"/model/newProcessInstanceAct.action",
				data:{
					_fw_service_id : "getGlobalProcessInstanceParamsSrv",
					instanceId : $('#instanceId').val()
				},
				async:false,
				dataType:"json",
			    success: function(data) {
			    	globalParams =  data;
			    },
			    error:function(e){
			    	alert('获取全局参数错误！');
			    }
			});
		}
	}
	var inner = '<option value="">请选择</option>';
	for(var i=0; i<globalParams.length; i++){
		inner += '<option value="'+globalParams[i].paramKey+'">'+globalParams[i].paramKey+'</option>';
	}
	return inner;
}

/**
 * 获取表达式选项列表
 */
var expList;
function getExpOptions(){
	if(!expList)
	{
		$.ajax({
		     type : "POST",
		     url : $('#url').val()+"/design/getComponentListAct.action",
		     data : {
		    	 "_fw_service_id":"getJoinExpOptionsSrv"
		     },
		     async:false,
		     cache:false,
		     success : function(types) {
		    	 expList = JSON.parse(types);
		     },
		     error : function(e) {
		      	alert("error");
		     }
		 });
	}
	var inner = '<option value="">请选择</option>';
	for(var i=0; i<expList.length; i++){
		inner += '<option value="'+expList[i].dicCode+'">'+expList[i].dicname+'</option>';
	}
	return inner;
}

/**
 * 获取可跳转节点列表选项
 */
var navNodeList;
function getNavOptions(){
	if(!navNodeList){
		navNodeList = window.parent.getNavNodeList($('#dNodeId').val());
	}
	var inner = '<option value="">请选择</option>';
	for(var i=0; i<navNodeList.length; i++){
		inner += '<option value="'+navNodeList[i].nodeId+'">'+navNodeList[i].nodeName+'</option>';
	}
	return inner;
}

/**
 * 删除指定索引的mpa项
 * @param index
 */
function removeMpaData(index){
	$('#mpa_'+index).remove();
}

/**
 * 保存聚合操作
 */
function save(){
	$("#joinForm").submit();
	
//	//添加一个校验方法，可以禁止用户在节点名字段输入逗号“,”
//	$.validator.addMethod("forbidComma", function(value) {
//		if(value.indexOf(",")!=-1 || value.indexOf("，")!=-1)
//			return false;
//		return true;
//	}, '节点名中不能包含逗号'); 
//	
//	var rules = {
//		"dNodeName" : {required : true, maxlength : 100, forbidComma : {}},
//		"businessType" : {required : true}
//	};
//	
//	//如果是multiPathApprove场景，检查所有选项选中状态及文本框校验
//	$('#tb_multiPathApprove :input').each(function(index,item){
//		rules[item.id] = {required:true};
//	});
//	
//	var opts = {rules : rules};
//	var result = validateForm("joinForm", opts, "bottom");
//	
//	//校验通过，根据场景保存数据
//	if(result){
//		//构造mpa数据
//		var paramsArr =  new Array();
//		for(var i = 0 ; i < $('#tb_multiPathApprove tbody > tr').length ; i++){
//			var mpa = {
//				"param":$('#param_' + i).val(),
//				"exp"  :$('#exp_'+i).val(),
//				"value":$('#value_' + i).val(),
//				"nav"  :$('#nav_' + i).val(),
//				"navName":document.all['nav_'+i].options[document.all['nav_'+i].selectedIndex].text
//			};
//			if($('#state').val() != "design"){
//				mpa.navWf = window.parent.getNavNodeWfNodeId($('#nav_' + i).val());
//			}
//			paramsArr.push(mpa);
//		}
//		
//		if($('#state').val() == "design"){
//			window.parent.onSubmitComponent(
//					$('#dNodeId').val(),
//					$('#dNodeName').val(),
//					JSON.stringify({"joinNodeName":$('#dNodeName').val(),"businessType":$('#businessType').val(),"nodeData":paramsArr}));
//		}else{
//			jsonData.joinNodeName = $('#dNodeName').val();
//			jsonData.businessType = $('#businessType').val();
//			jsonData.nodeData = paramsArr;
//			
//			if($("#state").val() == "definition"){
//				$.ajax({
//				     type : "POST",
//				     url : $('#url').val()+"/design/getComponentListAct.action",
//				     data : {
//				    	 "_fw_service_id":"saveModelJoinFormDataSrv",
//				    	 "jsonData":JSON.stringify(jsonData),
//				    	 'jsonClass' : 'com.ccb.iomp.cloud.data.po.workflow.BpmModelJoinNodePo'
//				     },
//				     async:true,
//				     cache:false,
//				     success : function(data) {
//				    	window.parent.onSubmitComponent($('#dNodeId').val(),$('#dNodeName').val());
//				     },
//				     error : function(e) {
//				      	alert("error");
//				     }
//				 });
//			}else{
//				$.ajax({
//				     type : "POST",
//				     url : $('#url').val()+"/design/getComponentListAct.action",
//				     data : {
//				    	 "_fw_service_id":"saveInstanceJoinFormDataSrv",
//				    	 "jsonData":JSON.stringify(jsonData),
//				    	 'jsonClass' : 'com.ccb.iomp.cloud.data.po.workflow.BpmInstanceJoinNodePo'
//				     },
//				     async:true,
//				     cache:false,
//				     success : function(data) {
//				    	window.parent.onSubmitComponent($('#dNodeId').val(),$('#dNodeName').val());
//				     },
//				     error : function(e) {
//				      	alert("error");
//				     }
//				 });
//			}
//		}
//			
//		cancel();
//	}
}

function onSave(){
	if($('#state').val() == "design" || $('#state').val() == "definition" || 
	   ($('#state').val() == "instance" && $('#userAdmin').val() == "userAdmin")){
		var paramsArr =  new Array();
		window.parent.TankFlow.onSubmitComponent($('#dNodeId').val(),$('#dNodeName').val(),JSON.stringify({"routeArray":paramsArr}));
		cancel();
	}
}
function cancel(){
	window.parent.TankFlow.closeComponentDialog();
}