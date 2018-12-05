/**
 * 提交服务策略表单参数时调用,用来获取key及对应的value值
 */

var currentHeight = 290;
/**
 * 持有节点json数据，如果第一次打开表单，则为null，否则为上次提交的数据
 */
var jsonData = null;

/**
 * 页面初始化
 */
$(function() {
	if ($('#state').val() == "design") {
		var compData = null;
		try {
			var dNodeId = $('#dNodeId').val();
			// alert(dNodeId);
			compData = parent.TankFlow.getComponentContent(dNodeId);
		} finally {
			if (compData != null && compData != "") {
				$('#tNodeName').val(compData.name);
				jsonData = JSON.parse(compData.json);
				if (jsonData) {
					$('#tNodeName').val(jsonData.name);// 设置节点名称
					$("#subprocessCode").val(jsonData.subModelId);
					$("#exeWay").val(jsonData.exeWay);
				}
			}else{
				$('#tNodeName').val("子流程");
			}
		}
		var typeId = $("#tempTypeId").val();
		if(typeof(value)=="undefined"){
			typeId = "";
		}
		//初始化子流节点信息
		$.ajax({
			type : "POST",
			url : ctx + "/workflow/model/getModelList.action",
			datatype : "json", // 返回的数据类型
			data : {
				 "typeId" :typeId,
				 "templateId":$("#templateId").val()
		    },
			async : true,
			cache : false,
			success : function(data) {
				//console.log(data);
				var htmlStr = '<option value="-1" selected>请选择</option>';
				for (var i = 0; i < data.length; i++) {
					htmlStr += '<option value="' + data[i].modelId + '">'
							+ data[i].modelName + '</option>';
				}
				$("#subprocessCode").append(htmlStr);
				if (jsonData != null) {
					$("#subprocessCode").val(jsonData.subModelId);
				}
			},
			error : function(e) {
				showTip("error");
			}
		});

		resetHeight(currentHeight);

		// 添加一个校验方法，可以禁止用户在节点名字段输入逗号“,”
		$.validator.addMethod("forbidComma", function(value) {
			for (var i = 0; i < value.length; i++) {
				if (value[i] == ",") {
					return false;
				}
			}
			return true;
		}, '节点名中不能包含逗号');
		// 选择流程模板验证
		$.validator.addMethod("modelChose", function(value) {
			if($("#subprocessCode").val()=='-1')
				return false;
			else
				return true;
		}, '请选择流程模板');

		// 设置验证
		$("#subProcessForm").validate({
			rules : {
				"tNodeName" : {
					"required" : true,
					"maxlength" : 100,
					"forbidComma" : true
				},
				"subprocessCode" : {
					"required" : true,
					"modelChose" : true
				}
			},
			messages : {
				"tNodeName" : {
					"required" : "请输入节点名称",
					"maxlength" : "字符长度不得大于50",
					"forbidComma" : "节点名中不能包含逗号"
				},
				"subprocessCode" : {
					"required" : "请选择流程模板",
					"modelChose" : "请选择流程模板"
				}
			},
			submitHandler : function() {
				onSave();
			}
		});
	}
});

function initForm(resultObj,isDisabled){
	 $('#tNodeName').val(resultObj.name);//设置节点名称
	 $("#subprocessCode").val(resultObj.subModelId);
}

/**
 * 保存节点数据
 */
function componentSave(){
	$("#subProcessForm").submit();
	
}
function onSave(){
	/**
	 * 返回json格式的字符串
	 * 根据servicePolicyParamFormDefinition的id-value格式进行json封装
	 */
	
	var param = {};
	param.name = $('#tNodeName').val();
	param.subModelId = $('#subprocessCode').val();
	if(param.subModelId == '-1'){
		showTip("请选择流程模板");
		return false;
	}
	param.exeWay = $('#exeWay').val();
	window.parent.TankFlow.saveComponentNameData(
			$('#dNodeId').val(),
			$('#tNodeName').val(),
			JSON.stringify(param));
}

//关闭窗口
function cancel(){
	window.parent.TankFlow.closeComponentDialog("userComponent_div");
}

/**
 * 重新计算表单高度
 */
function resetHeight(height){
	//window.parent.TankFlow.setFrameHeight(height);
}


/**
* 字符串形式转json对象
*/
function stringToJson(stringValue) {
	eval("var theJsonValue = " + stringValue);
	return theJsonValue;
}
