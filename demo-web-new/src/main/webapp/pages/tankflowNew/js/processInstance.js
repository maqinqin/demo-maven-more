$(function(){
	getInstanceInfo();
	$("#WorkflowIframe").height($(window).height()-150);
	var instanceId = $("#instanceId").val();
	var instanceStatus = $("#instanceStatus").val();
	var modelId = $("#insmodelId").val();
	var instanceName = $("#instanceName").val();
	$("#instanceName").val(decodeURI(instanceName));
	$("#submit_btn").show();
	$("#flush_btn").show();
	document.getElementById("WorkflowIframe").src = ctx+"/pages/tankflow/instance/processInstanceScreen.jsp?state=instance&instanceId="+instanceId;
})
/*window.onload = (
    function () {
		$("#WorkflowIframe").height($(window).height()-100);
		console.log(document.getElementById('WorkflowIframe').contentWindow.document.getElementsByClassName('GooFlow_work_inner')[0])
		document.getElementById('WorkflowIframe').contentWindow.document.getElementsByClassName('GooFlow_work_inner')[0].style.height = '440px';
    }
);*/



/**
 * 展示流程实例列表
 * @returns
 */
function getInstanceInfo() {
	$.ajax({
		type : "POST",
		url : ctx +'/workflow/instance/bpmInstance_getBpmInstanceInfoById.action',
		datatype : "json",
		data : {
			"bpmInstanceVo.instanceId" : $("#instanceId").val()
		},
		async:false,
		cache:false,
		success : function(data) {
			//指定实例状态
			instanceStateId = data.instanceStateId;
	    	 
	    	//指定表单值
			$("#instanceName").val(data.instanceName);
			$("#instanceType").val(data.typeName);
			$("#instanceStateName").val(getStateName(data.instanceStateId));
	    	$("#instanceStateId").val(data.instanceStateId);
	    	$("#srCode").val(data.srCode);
	    	$("#modelId").val(data.modelId);
	    	$("#wfInstanceId").val(data.wfInstanceId);

	    	if ($("#editType").val() == 'instance') {
		    	switch (data.instanceStateId) {
		    	case 0:
		    		$("#start_instance").show();
		    		break;
		    	case 1:
		    		$("#pause_instance").show();
		    		if(data.typeId != "3"){
		    			$("#end_instance").show();
		    		}		    		
		    		break;
		    	case 2:
		    		$("#start_instance").show();
		    		$("#end_instance").show();
		    		break;
		    	case 3:
		    		$("#view_report").show();
		    		break;
		    	case 4:
		    		$("#view_report").show();
		    		break;
		    	}
	    	} else {
	    		if (data.instanceStateId == 3 || data.instanceStateId == 4) {
		    		$("#view_report").show();
	    		}
	    	}
	    	 
	    	//根据实例状态控制10秒自动刷新
	    	if(data.instanceStateId == "1") {
	    		//获取配置的页面刷新时间
	    		$.post(ctx + "/parameter/management/pageRefreshTime.action",{},function(data){
	    			pageRefreshTime = data.result;
	    			t1 = window.setInterval("refreshBtn()",pageRefreshTime); 
	    		});
	    	}
	     },
	     error : function(e) {
	      	showError("getInstanceInfo error");
	     }
	 });
}

//获取实例状态编码对应实例状态
function getStateName(value){
	if(value==0){
		return "创建";
	}else if(value==1){
		return "运行中";
	}else if(value==2){
		return "暂停";
	}else if(value==3){
		return "正常结束";
	}else if(value==4){
		return "强制结束";
	}
}

//实例操作
function instanceOper(type) {
	switch (type) {
	case "resume":
		document.getElementById('WorkflowIframe').contentWindow.resumeInstance().then(function(data){
			showTip(data);
			$("#start_instance").hide();
			$("#pause_instance").show();
			$("#end_instance").show();
		},function(error){
			showTip(error);
		});
		window.clearInterval(t1); 
		t1 = window.setInterval("refreshBtn()",1000); 
		break;
	case "end":
		document.getElementById('WorkflowIframe').contentWindow.endInstance().then(function(data){
			showTip(data);
			$("#start_instance").hide();
			$("#pause_instance").hide();
			$("#end_instance").hide();
			$("#view_report").show();
		},function(error){
			showTip(error);
		});
		window.clearInterval(t1); 
		break;
	case "pause":
		document.getElementById('WorkflowIframe').contentWindow.pauseInstance().then(function(data){
			showTip(data);
			$("#pause_instance").hide();
			$("#start_instance").show();
		},function(error){
			showTip(error);
		});
		window.clearInterval(t1); 
		break;
		
	}
	returnFlag = false;
}

function refreshBtn() {
	var instanceStatusId = document.getElementById('WorkflowIframe').contentWindow.instanceStateId;
	if (instanceStatusId == 3) {
		$("#runInstance").hide();
		$("#endInstance").show();
		window.clearInterval(t1); 
	}
}

function returnLastPage() {
	if ($("#editType").val() == 'view') {
		window.location.href = ctx+"/request/base/cloudRequestInfo.action";
	} else if ($("#editType").val() == 'instance') {
		window.location.href = ctx+"/pages/tankflowNew/instanceList.jsp";
	}
}

function viewReport() {
	window.location.href = ctx
	+ "/pages/tankflowNew/report/instanceReport.jsp?processDefinitionId="
	+ $("#modelId").val() + "&processInstanceId="
	+ $("#instanceId").val();
}

function flush() {
	document.getElementById('WorkflowIframe').contentWindow.flushInstance();
}