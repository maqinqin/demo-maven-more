function getNotiLogList(resourceType){
	if(resourceType=='H'){
		var resourceId = $("#hostId").val();
	}else if(resourceType=='V'){
		var resourceId = $("#vmId").val();
	}
	$("#gridTable_notiLog").jqGrid().GridUnload("gridTable_notiLog");
	$("#gridTable_notiLog").jqGrid({
		url : ctx+"/log/notification/findNotificationsPage.action",
		rownumbers : false,
		datatype : "json",
		mtype : "post",
		multiselect : false,
		postData : {"resourceType":resourceType,"resourceId":resourceId},
		height : 310,
		autowidth : true,
		colModel : [{
			name : "id",
			index : "id",
			label : i18nShow('compute_res_deviceId'),
			width : 10,
			sortable : true,
			align : 'left',
			hidden : true
		},{
			name : "source",
			index : "source",
			label : i18nShow('sys_notice_source'),
			width : 25,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				if(cellVall == "AUTO" ){
					return i18nShow('sys_notice_source_auto');
				}else if(cellVall == "MANUAL" ){
					return i18nShow('sys_notice_source_man');
				}else if(cellVall == "FROMVC" ){
					return i18nShow('sys_notice_source_vc');
				}
			}
		},
		{
			name : "type",
			index : "type",
			label : i18nShow('sys_notice_type'),
			width : 15,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				if(cellVall == "TIP" ){
					return i18nShow('sys_notice_type_tip');
				}else if(cellVall == "WARNING"){
					return i18nShow('sys_notice_type_warn');
				}
			}
		},
		{
			name : "operationType",
			index : "operationType",
			label : i18nShow('sys_notice_op_type'),
			width : 23,
			sortable : true,
			align : 'left'
		},
		{
			name : "createTime",
			index : "createTime",
			label : i18nShow('sys_notice_op_time'),
			width : 50,
			sortable : true,
			align : 'left',
			formatter: function(cellValue,options,rewObject){
					return formatTime(cellValue);
           }
		},
		{
			name : "operationContent",
			index : "operationContent",
			label : i18nShow('sys_notice_op_content'),
			width : 160,
			sortable : true,
			align : 'left'
		},
		{
			name : "state",
			index : "state",
			label : i18nShow('com_status'),
			width : 25,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				if(cellVall == "NOTCLOSE" ){
					return "<span class='tip_red'>"+i18nShow('sys_notice_unclosed')+"</span>";
				}else{
					return "<span class='tip_green'>"+i18nShow('sys_notice_closed')+"</span>";
				}
			}
		},
		{
			name : "state",
			index : "state",
			label : i18nShow('com_operate'),
			width : 15,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				if(cellVall == 'NOTCLOSE'){
					return  "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=updateDealState('"+rowObject.id+"') >"+i18nShow('sys_notice_close')+"</a>"; 
				}else{
					return " ";
				}
			}
		}],
		viewrecords : true,
		sortname : "createTime",
		sortorder : "desc",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "search_notiLog"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#gridPager_notiLog",
		hidegrid : false
	});
}

function search_notiLog() {
	var resourceType = $("#resourceType").val().replace(/(^\s*)|(\s*$)/g, "");
	if(resourceType=='H'){
		var resourceId = $("#hostId").val();
	}else if(resourceType=='V'){
		var resourceId = $("#vmId").val();
	}
	$("#gridTable_notiLog").jqGrid('setGridParam', {
		url : ctx+"/log/notification/findNotificationsPage.action",
		postData : {"resourceType":resourceType,"resourceId":resourceId},
		pager : "#gridPager_notiLog"
	}).trigger("reloadGrid");
}
//更改处理状态
function updateDealState(id){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx+"/log/notification/updateNotiState.action",  
		data : {"notiPo.id":id},
		error : function() {//请求失败处理函数  
			closeTip();
			showError(i18nShow('tip_req_fail'));
		},
		success : function(data) { //请求成功后处理函数。     
			showTip(i18nShow('tip_op_success'));
			search_notiLog();
		}
	});
}
	
function getRmComputeHostListAction(){
	var nodeId = $("#hide_nodeId").val();
	var rmdatacenterId,resPoolId,rmVqCdpId,clusterId,hostId,rmVmId,
    rmdatacenterIndex,resPoolIndex,cdpqIndex,clusterIndex,hostIndex,rmVmIndex;
	rmdatacenterIndex = nodeId.indexOf("rmdatacenterId:");
	resPoolIndex = nodeId.indexOf("resPoolId:");
	cdpqIndex = nodeId.indexOf("rmVqCdpId:");
	clusterIndex = nodeId.indexOf("clusterId:");
	hostIndex = nodeId.indexOf("hostId:");
	rmVmIndex = nodeId.indexOf("rmVmId:");
	if(rmdatacenterIndex >= 0){
	    rmdatacenterId = nodeId.substr(15);
	}
	if(resPoolIndex >= 0){
		resPoolId = nodeId.substr(10);
	}
	if(cdpqIndex >= 0){
		rmVqCdpId = nodeId.substr(10);
	}
	if(clusterIndex >= 0){
		clusterId = nodeId.substr(10);
	}
	if(hostIndex >= 0){
		hostId = nodeId.substr(7);
	}
	if(rmVmIndex >= 0){
		rmVmId = nodeId.substr(7);
	}
	$("#gridTable_pm").jqGrid().GridUnload("gridTable_pm");
	$("#gridTable_pm").jqGrid({
		url : ctx+"/compute/getRmComputeHostListAction.action",
		rownumbers : true,
		datatype : "json",
		mtype : "post",
		multiselect : false,
		postData : {"rmdatacenterId":rmdatacenterId,"resPoolId":resPoolId,"rmVqCdpId":rmVqCdpId,"clusterId":clusterId,
					"hostId":hostId,"rmVmId":rmVmId},
		height : 310,
		autowidth : true,
		colModel : [{
			name : "id",
			index : "id",
			label : i18nShow('host_res_id'),
			width : 10,
			sortable : true,
			align : 'left',
			hidden : true
		},{
			name : "deviceName",
			index : "deviceName",
			label : i18nShow('compute_res_deviceName'),
			width : 100,
			sortable : true,
			align : 'left'
		},
		{
			name : "cpu",
			index : "cpu",
			label : i18nShow('compute_res_deviceCpu')+"(核)",
			width : 30,
			sortable : true,
			align : 'left'
		},
		{
			name : "ram",
			index : "ram",
			label : i18nShow('compute_res_deviceMem')+"(GB)",
			width : 30,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				return parseInt(cellVall/1024);
			}
		},
		{
			name : "cpuUsed",
			index : "cpuUsed",
			label : i18nShow('compute_res_deviceCpuUsed')+"(核)",
			width : 51,
			sortable : true,
			align : 'left'
		},
		{
			name : "ramUsed",
			index : "ramUsed",
			label : i18nShow('compute_res_deviceMemUsed1')+"(GB)",
			width : 51,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				return parseInt(cellVall/1024);
			}
			
		},
		{
			name : "ip",
			index : "ip",
			label : i18nShow('compute_res_ipAddress'),
			width : 100,
			sortable : true,
			align : 'left'
		},
		{
			name : "isNano",
			index : "isNano",
			label : i18nShow('compute_res_isInvc'),
			width : 100,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject){
				if(cellVall == "Y" || cellVall == "NA"){
					return i18nShow('compute_res_isInvcY');
				}else{
					return i18nShow('compute_res_isInvcN');
				}
			}
		},
		{
			name : "did",
			index : "did",
			label : i18nShow('compute_res_dataCenterId'),
			width : 10,
			sortable : true,
			align : 'left',
			hidden : true
		},
		{
			name : "rid",
			index : "rid",
			label : i18nShow('compute_res_respoolId'),
			width : 10,
			sortable : true,
			align : 'left',
			hidden : true
		},
		{
			name : "vid",
			index : "vid",
			label : i18nShow('compute_res_vmId'),
			width : 10,
			sortable : true,
			align : 'left',
			hidden : true
		}],
		viewrecords : true,
		sortname : "pname",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "search_pm"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#gridPager_pm",
		hidegrid : false
	});
}

function search_pm() {
	$("#gridTable_pm").jqGrid('setGridParam', {
		url : ctx + "/compute/getRmComputeHostListAction.action",
		postData : {
			"deviceName":$("#cmNameInput").val().replace(/(^\s*)|(\s*$)/g, ""),
			"ip":$("#ipAdrInput").val().replace(/(^\s*)|(\s*$)/g, "")
		},
		pager : "#gridPager_pm"
	}).trigger("reloadGrid");
}

function powerOnPm(pmId) {
	createPowerLoginDiv(pmId,"powerOn");
}
//新建快照弹框
function createPowerLoginDiv(pmId,tip){
	$("label.error").remove();
	//得到当前窗口的父类
	 var dialogParent = $("#powerLoginDiv").parent();
	 //对窗口进行克隆，并进行隐藏
     var dialogOwn = $("#powerLoginDiv").clone();  
     dialogOwn.hide();  
          
     $("#powerLoginDiv").dialog({  
    	 	autoOpen : true,
			modal:true,
			height:350,
			width:500,
			title:i18nShow('compute_res_login'),
			//resizable:false,
            close: function () {
            	//将隐藏的克隆窗口追加到页面上
                dialogOwn.appendTo(dialogParent);  
                $(this).dialog("destroy").remove();   
            }
        });  
	//----------------------------------------
//	clearTab();
	//清除form所有数据
	clearForm($('#powerLoginForm'));
	//使form所有数据为可输入  
	$("#powerLoginDiv").enable(); 
	//添加隐藏域的默认值
	$("#pmId").val(pmId);
	$("#tip").val(tip);
}
function savePowerLoginBtn(){
	var pmId = $.trim($("#pmId").val());
	var pmIp = $.trim($("#pmIp").val());
	var tip = $.trim($("#tip").val());
	var ipmi_ver = $.trim($("#ipmi_ver").find("option:selected").text());
	var ipmi_Name = $.trim($("#ipmi_Name").val());
	var ipmi_Pword = $.trim($("#ipmi_Pword").val());
	var url = "";
	if(pmIp == ""||pmIp == "undefined"){
		showTip(i18nShow('compute_res_login_ipTip'));
		return false;
	}
	if(pmId == ""||pmId == "undefined"){
		showTip(i18nShow('compute_res_login_err'));
		return false;
	}
	if(ipmi_ver == ""){
		showTip(i18nShow('compute_res_login_version'));
		return false;
	}
	if(ipmi_Name == ""||ipmi_Pword == ""){
		showTip(i18nShow('compute_res_login_pwd'));
		return false;
	}
	url =  ctx +"/powerPmOpertion.mvc";
	showTip("load");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,  
		data : {"opertion":tip,"ipmi_Name" : encodeURIComponent(ipmi_Name),"pmId":pmId,"pmIp":pmIp,"ipmi_ver":ipmi_ver,"ipmi_Pword":encodeURIComponent(ipmi_Pword)},
		error : function() {//请求失败处理函数  
			closeTip();
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) { //请求成功后处理函数。     
			closeTip();
			cancelPowerLoginDiv();
			if(data=="success"){
				showTip(i18nShow('compute_res_operateSuccess'));
			}else if(data=="db fail"){
				showError(i18nShow('compute_res_operateFail'));
			}else{
				showError(i18nShow('compute_res_operateFail'));
			}
			
		}
	});
}
//修改创建时间格式的方法
function formatTime(ns){
	if(ns){
		var date = new Date(parseInt(ns));
		
		Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
        h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
		
       return Y+M+D+h+m+s;
	}
}

function cancelPowerLoginDiv(){
	$("#powerLoginDiv").dialog("close");
}
function powerOffPm() {
	createPowerLoginDiv(pmId,"powerOff");
}

function ResetPm() {
	createPowerLoginDiv(pmId,"powerReset");
}

