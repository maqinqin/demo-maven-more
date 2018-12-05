var firstModel = true;
function showOptionModel(serviceId) {
	$("#serviceIdf").val(serviceId);
	$("#serviceModel").dialog({
		autoOpen : true,
		modal : true,
		height : 420,
		width : 800,
		title:i18nShow('cloud_service_model'),
		close:function(){
			//重新加载页面
			$("#gridTable").jqGrid().trigger("reloadGrid");
		}
	});
	var queryData = {
		serviceId : serviceId
	};
	if (firstModel) {
		firstModel = false;
		$("#serviceModelGridTable").jqGrid(
				{
					url : ctx + "/cloud-service/serviceflowref/search.action",
					rownumbers : true,
					datatype : "json",
					postData : queryData,
					mtype : "post",
					height : 260,
					autowidth : true,
					colNames : [ '操作Id','操作类型ID',i18nShow('cloud_service_operate_type'), i18nShow('cloud_service_model_flow'), i18nShow('cloud_service_operate') ],
					colModel : [
							{
								name : "flowId",
								index : "flowId",
								sortable : false,
								width : 150,
								align : 'left',
								hidden : true
							},
							{
								name : "operModelType",
								index : "operModelType",
								sortable : false,
								width : 150,
								align : 'left',
								hidden : true
							},
							{
								name : "operModelTypeName",
								index : "operModelTypeName",
								sortable : false,
								width : 150,
								align : 'left'
							},{
								name : "flowName",
								index : "flowName",
								sortable : false,
								width : 150,
								align : 'left'
							},
							{
								name : "option",
								index : "option",
								sortable : false,
								width : 300,
								align : "left",
								formatter : function(cellVall, options, rowObject) {
									var updateModelFlag = $('#updateModelFlag').val();
									var deleteModelFlag = $('#deleteModelFlag').val();
									var ret = "　　";
									if(updateModelFlag)
									ret +="<a  style='margin-right: 10px;margin-left: -25px;text-decoration:none;' href='javascript:#' title=''  onclick=loadModel('" + rowObject.serviceFlowId+"')>"+i18nShow('cloud_service_edit')+"</a>";
									if(deleteModelFlag)
									ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteModel('" + rowObject.serviceFlowId + "')>"+i18nShow('cloud_service_delete')+"</a>";
									ret += "　　　　　　　";
									return ret;
								}
							} ],
					viewrecords : true,
					sortname : "serviceId",
					prmNames : {
						search : "search"
					},
					jsonReader : {
						root : "pagination.dataList",
						records : "pagination.record",
						page : "pagination.page",
						total : "pagination.total",
						repeatitems : false
					},
					hidegrid : false
				});
	} else {
		$("#serviceModelGridTable").jqGrid("setGridParam", {
			postData : queryData
		}).trigger("reloadGrid");
	}
	//自适应
	$("#serviceModelGridTable").setGridWidth($("#serviceModelGridDiv").width());
	$(window).resize(function() {
		$("#serviceModelGridTable").setGridWidth($("#serviceModelGridDiv").width());
    });
	$("#serviceModelForm").validate({
		rules: {
			operModelType: {  required:true,
						      remote:{                
					              type:"POST",
					              url:ctx+"/cloud-service/serviceflowref/checkCloudServiceFlowRefsAct.action",
					              data:{
					            	  "cloudServiceFlowRefPo.serviceId":function(){return $("#serviceIdf").val()},"cloudServiceFlowRefPo.serviceFlowId":function(){return $("#serviceFlowId").val()},"cloudServiceFlowRefPo.operModelType":function(){return $("#operModelType").val();}
					              		} 
					              } 
					      	   },
			flowId: "required"
		},
		messages: {
			operModelType:  {required:i18nShow('validate_service_model_op_required'),remote:i18nShow('validate_service_model_op_remote')},
			flowId: i18nShow('validate_service_model_required')
		},
		submitHandler: function() {
//			alert("ss")
			saveModel();
		}
	});
}

function loadModel(serviceFlowId) {
	$("label.error").remove();
	clearServiceModelTab();
	var title;
	emptyValue("operModelType");
	if (serviceFlowId != ""){
		title =i18nShow('cloud_service_model_update');
		$.post(ctx + "/cloud-service/serviceflowref/load.action", {
			"cloudServiceFlowRefPo.serviceFlowId" : serviceFlowId
		}, function(data) {
			if (data.cloudServiceFlowRefPo != null) {
				$("#serviceIdf").val(data.cloudServiceFlowRefPo.serviceId);
				$("#serviceFlowId").val(data.cloudServiceFlowRefPo.serviceFlowId);
				$("#flowId").val(data.cloudServiceFlowRefPo.flowId);
				$("#operModelType").val(data.cloudServiceFlowRefPo.operModelType);
				$("#isActivef").val(data.cloudServiceFlowRefPo.isActive);
			}
		});
	}else{
		title =i18nShow('cloud_service_model_save');
		selectByValue("operModelType","");
		selectByValue("flowId","");
		$("#serviceFlowId").val("");
	}
	$("#serviceModelLoad").dialog({
		autoOpen : true,
		modal : true,
		height : 230,
		width : 350,
		title :title
	});
}
function clearServiceModelTab(){
	 var tab = document.getElementById("serviceModelTab") ;
	 var inputs = tab.getElementsByTagName("input"); 
	 for(var k=0;k<inputs.length;k++) 
	 { 
		 if(inputs[k].type!='button'){
			 inputs[k].value=""; 
		 }
	 } 
	 var textareas = tab.getElementsByTagName("textarea");
	 for(var k=0;k<textareas.length;k++) 
	 { 
		 textareas[k].value=""; 
	 } 
}
function saveServiceModel(){
	$("#serviceModelForm").submit();  
}


function saveModel() {
	$.post(ctx + "/cloud-service/serviceflowref/save.action", {
		"cloudServiceFlowRefPo.serviceFlowId" : $("#serviceFlowId").val(),
		"cloudServiceFlowRefPo.flowId" : $("#flowId").val(),
		"cloudServiceFlowRefPo.serviceId" : $("#serviceIdf").val(),
		"cloudServiceFlowRefPo.operModelType" : $("#operModelType").val(),
		"cloudServiceFlowRefPo.isActive" : $("#isActivef").val()
	}, function(data) {
		closeViews("serviceModelLoad");
		showOptionModel($("#serviceIdf").val());
	});
}

function deleteModel(serviceFlowId) {
	var params = {
		"cloudServiceFlowRefPo.serviceFlowId" : serviceFlowId
	};
	showTip(i18nShow('tip_delete_confirm'),function(){
		$.post(ctx + "/cloud-service/serviceflowref/delete.action", params, function(data) {
			showTip(i18nShow('tip_delete_success'));
			showOptionModel($("#serviceIdf").val());
		});
	});
}

function stopModel(serviceFlowId) {
	var params = {
		"cloudServiceFlowRefPo.serviceFlowId" : serviceFlowId
	};
	$.post(ctx + "/cloud-service/serviceflowref/stop.action", params, function(data) {
		showOptionModel($("#serviceIdf").val());
	});
}