/* 查询虚拟机运行状态.
* @param  tabId  jrid的ID
* @param  global 是否整体查询。
* @param  id     若整体查询则该参数非必须；否则即为需要查询的vmId。
*/
function getVmRunningState(tabId, global, id) {
	if(global == true){
		var rowIds = $("#"+tabId).getDataIDs();
		var vmId = "";	
		for ( var i = 0; i < rowIds.length; i++) {
			var rowData = $("#"+tabId).getRowData(rowIds[i]);
			vmId = rowData.vid;
			$.post(ctx+"/resmgt-compute/vm/vmRunningState.action", {'vmId' : vmId}, function(data){
				var arr = data.result.split(':');
				if("poweredOn" == $.trim(arr[1]) || "VIR_DOMAIN_RUNNING" == $.trim(arr[1])){
					$("#" + arr[0] + "_state").html("运行中");
					jQuery("<option value='2'>关机</option>").appendTo("#" + arr[0] + "_operation");
					jQuery("<option value='6'>挂起</option>").appendTo("#" + arr[0] + "_operation");
					jQuery("<option value='5'>重启</option>").appendTo("#" + arr[0] + "_operation");
					jQuery("<option value='3'>新建快照</option>").appendTo("#" + arr[0] + "_operation");
					jQuery("<option value='4'>快照管理</option>").appendTo("#" + arr[0] + "_operation");
				}else if("poweredOff" == $.trim(arr[1]) || "VIR_DOMAIN_SHUTOFF" == $.trim(arr[1])){
					$("#" + arr[0] + "_state").html("已关闭");
					jQuery("<option value='1'>开机</option>").appendTo("#" + arr[0] + "_operation");
					jQuery("<option value='3'>新建快照</option>").appendTo("#" + arr[0] + "_operation");
					jQuery("<option value='4'>快照管理</option>").appendTo("#" + arr[0] + "_operation");
				}else if("suspended" == $.trim(arr[1])){
					$("#" + arr[0] + "_state").html("已挂起");
					jQuery("<option value='1'>开机</option>").appendTo("#" + arr[0] + "_operation");
					jQuery("<option value='2'>关机</option>").appendTo("#" + arr[0] + "_operation");
					jQuery("<option value='3'>新建快照</option>").appendTo("#" + arr[0] + "_operation");
					jQuery("<option value='4'>快照管理</option>").appendTo("#" + arr[0] + "_operation");
				}else{
					$("#" + arr[0] + "_state").html("未知");
					$("#" + arr[0] + "_operation").empty();
				}
			});
		};
	} else if(global == false){
		$.post(ctx+"/resmgt-compute/vm/vmRunningState.action", {'vmId' : id}, function(data){
			var arr = data.result.split(':');
			if("poweredOn" == $.trim(arr[1]) || "VIR_DOMAIN_RUNNING" == $.trim(arr[1])){
				$("#" + arr[0] + "_state").html("运行中");
				$("#" + arr[0] + "_operation option[value='1']").attr("disabled",true);
				$("#" + arr[0] + "_operation option[value='2']").attr("disabled",false);
				$("#" + arr[0] + "_operation option[value='5']").attr("disabled",false);
				$("#" + arr[0] + "_operation option[value='6']").attr("disabled",false);
				
				document.getElementById(1).style.visibility="hidden";
				document.getElementById(2).style.visibility="visible";
				document.getElementById(5).style.visibility="visible";
				document.getElementById(6).style.visibility="visible";
			}else if("poweredOff" == $.trim(arr[1]) || "VIR_DOMAIN_SHUTOFF" == $.trim(arr[1])){
				$("#" + arr[0] + "_state").html("已关闭");
				$("#" + arr[0] + "_operation option[value='1']").attr("disabled",false);
				$("#" + arr[0] + "_operation option[value='2']").attr("disabled",true);
				$("#" + arr[0] + "_operation option[value='5']").attr("disabled",true);
				$("#" + arr[0] + "_operation option[value='6']").attr("disabled",true);
				
				document.getElementById(1).style.visibility="visible";
				document.getElementById(2).style.visibility="hidden";
				document.getElementById(5).style.visibility="hidden";
				document.getElementById(6).style.visibility="hidden";
			}else if("suspended" == $.trim(arr[1])){
				$("#" + arr[0] + "_state").html("已挂起");
				$("#" + arr[0] + "_operation option[value='1']").attr("disabled",false);
				$("#" + arr[0] + "_operation option[value='2']").attr("disabled",false);
				$("#" + arr[0] + "_operation option[value='5']").attr("disabled",true);
				$("#" + arr[0] + "_operation option[value='6']").attr("disabled",true);
				
				document.getElementById(1).style.visibility="visible";
				document.getElementById(2).style.visibility="visible";
				document.getElementById(5).style.visibility="hidden";
				document.getElementById(6).style.visibility="hidden";
			}else{
				$("#" + arr[0] + "_state").html("未知");
			}
		});
	}
}


//开启
function vmStart(vmId){
	showTip("确定执行开启操作吗？",function(){
	showTip("load");
	$.ajax({   
        async:true,//   
        cache:false,   
        type: 'POST',   
        url: ctx + "/resmgt-compute/vm/vmStart.action",//请求的action路径   
        data:{"vmId" : vmId},
        error: function () {//请求失败处理函数   
            showError('请求失败');   
        },   
        success:function(data){ //请求成功后处理函数。 
        	closeTip();
        	if(data.result=="success"){
        		showTip("执行开机操作成功");
        		getVmRunningState("gridTable_virware", false, vmId);
    		}else{
    			showTip("执行开机操作失败");
    		}
        }   
		});
	});
}
//挂起
function vmPause1(vmId){
	//var vmId = $("#vmId").val();
	showTip("确定执行挂起操作吗？",function(){
		showTip("load");
	$.ajax({   
        async:true,//   
        cache:false,   
        type: 'POST',   
        url: ctx + "/resmgt-compute/vm/vmPause.action",//请求的action路径   
        data:{"vmId" : vmId},
        error: function () {//请求失败处理函数   
            showError('请求失败');   
        },   
        success:function(data){ //请求成功后处理函数。
        	closeTip();
        	if(data.result=="success"){
        		showTip("执行挂起操作成功");
        		getVmRunningState("gridTable_virware", false, vmId);
    		}else{
    			showTip("执行挂起操作失败");
    		}
        }   
    });
	 });
}
//关闭
function vmClose(vmId){
	//var vmId = $("#vmId").val();
	showTip("确定执行关闭操作吗？",function(){
		showTip("load");
	$.ajax({   
        async:false,//   
        cache:false,   
        type: 'POST',   
        url: ctx + "/resmgt-compute/vm/vmClose.action",//请求的action路径   
        data:{"vmId" : vmId},
        error: function () {//请求失败处理函数   
            showError('请求失败');   
        },   
        success:function(data){ //请求成功后处理函数。
        	closeTip();
        	if(data.result=="success"){
        		showTip("执行关闭操作成功");
        		getVmRunningState("gridTable_virware", false, vmId);
        		//setTimeout(function(){getVmRunningState("gridTable_virware", false, vmId);},0);
    		}else{
    			showTip("执行关闭操作失败");
    		}
        }   
    });
	});
} 
//重启
function vmRestart1(vmId){
	showTip("确定执行重启操作吗？",function(){
		showTip("load");
	$.ajax({   
        async:true,//   
        cache:false,   
        type: 'POST',   
        url: ctx + "/resmgt-compute/vm/vmRestart.action",//请求的action路径   
        data:{"vmId" : vmId},
        error: function () {//请求失败处理函数  
            showError('请求失败');   
        },   
        success:function(data){ //请求成功后处理函数。 
        	closeTip();
        	if(data.result=="success"){
        		showTip("执行重启操作成功");
        		getVmRunningState("gridTable_virware", false, vmId);
        	}else{
    			showTip("执行重启操作失败");
    		}
        }
    });
	});
}