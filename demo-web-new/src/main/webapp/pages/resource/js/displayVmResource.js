$(function() {
	$('#deployUnitSel').click(function(){
		var appIdSel = $('#appIdSel').find("option:selected").text();
		if(appIdSel == i18nShow('com_select_defalt')+'...'){
			showTip(i18nShow('tip_app_need'));
		}
	});
	$('#vmTypeSel').click(function(){
		var appIdSel = $('#platFormIdSel').find("option:selected").text();
		if(appIdSel == i18nShow('com_select_defalt')+'...'){
			showTip(i18nShow('tip_nw_platform_need'));
		}
	});
	$('#gridTable').jqGrid({
			url : ctx+"/resource/queryResource/queryVmResource.action", 
			datatype : "json", 
			mtype : "post", 
			height : heightTotal(),
			autowidth : true, 
			rownumbers : true,
			colNames:[i18nShow('rm_datacenter'),i18nShow('my_req_sr_res_pool'),i18nShow('my_req_sr_cluster'),i18nShow('app_info'),i18nShow('compute_res_duName'), i18nShow('host_res_id'), i18nShow('compute_res_vmId'),i18nShow('info_vm_beyondHost'), i18nShow('compute_res_vmName'),i18nShow('cloud_service_platform'),i18nShow('rm_platform_virture_type'), 'CPU',i18nShow('compute_res_deviceMem'), i18nShow('com_status'), i18nShow('info_vm_onlineTime'),i18nShow('deviceIp'),i18nShow('tenantManage_jsp_tenantName')],
			colModel : [ 
			            {name : "dataCenter",index : "dataCenter",sortable : true,align : 'left', width:100},
			            {name : "poolName",index : "poolName",sortable : true,align : 'left', width:130},
			            {name : "clusterName",index : "clusterName",sortable : true,align : 'left', width:90},
			            {name : "appSystem",index : "appSystem",sortable : false,align : 'left', width:90},
			            {name : "deployUnit",index : "deployUnit",sortable : false,align : 'left',width:90},
			            {name : "hostId",index : "hostId",sortable : false,align : 'left', hidden:true},
			            {name : "vmId",index : "vmId",sortable : false,align : 'left', hidden:true},
			            {name : "hostName",index :"hostName",sortable : false,align : 'left', width:150, formatter:function(cellValue,options,rowObject){
			            	return '<a href="javascript:;" style=" text-decoration:none" onclick="viewMachineInfo(\''+rowObject.hostId+'\')">'+rowObject.hostName+'</a>';
			            }},
			            {name:"vmName",index:"vmName",sortable:false,align:"left", width:170, formatter:function(cellValue,options,rowObject){
			            	return '<a href="javascript:;" style=" text-decoration:none" onclick="viewVmInfo(\''+rowObject.vmId+'\')">'+rowObject.vmName+'</a>';
			            }},
			            {name : "platForm",index : "platForm",sortable : false,align : 'left', width:80,},
			            {name:"virtualName",index:"virtualName",sortable:false,align:"left", width:90},
			            {name:"cpu", index:"cpu",sortable:false,align:"left", width:40},
			            {name:"mem", index:"mem",sortable:false,align:"left", width:50,formatter:function(cellValue,options,rowObject){
			            	return rowObject.mem = rowObject.mem / 1024;
			            }},
			            {name:"cStatus", index:"cStatus",sortable:false,align:"left", width:50},
			            {name:"onlineTimme", index:"onlineTimme",sortable:false,align:"left", hidden:true},
			            {name:"ips", index:"ips",sortable:false,align:"left",width:170},
			            {name:"tenantName", index:"tenantName",sortable:false,align:"left",width:170}
			            ],
			viewrecords : true,
			rowNum : 10,
			rowList : [10, 20, 50, 100 ],
			prmNames : {
				search : "search"
			},
			jsonReader : {
				root : "dataList",
				records : "record",
				repeatitems : false
			},
			pager : "#gridPager",
			hidegrid : false
	});
	
	//自适应宽度
	$("#gridTable").setGridWidth($("#gridTable").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridTable").width());
		$("#gridTable").setGridHeight(heightTotal());
	});	
	
	
	changeApp($('#appIdSel').val());
	$('#appIdSel').change(function(){
		changeApp(this.value);
	});
	
	changePlatFormType($('#platFormIdSel').val());
	$('#platFormIdSel').change(function(){
		changePlatFormType(this.value);
	});
});
//应用系统级联服务器角色
function changeApp(value){
	if(value){
		$.ajax({
			type:"post",
			url:ctx+"/resource/queryResource/queryDeployUnit.action",
			async:false,
			datatype : "json",
			data:{"appId":value},
			success:(function(data){
				$('#deployUnitSel').empty();
				if(data.length>1){
					$('#deployUnitSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
				}
				$.each(data,function(index,obj){
					$('#deployUnitSel').append("<option value='"+obj.DU_ID+"'>"+obj.CNAME+"</option>");
				});
			})
		});
	}else{
		$('#deployUnitSel').empty();
		$('#deployUnitSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	}
}
//平台类型级联虚拟化类型
function changePlatFormType(value){
	if(value){
		$.ajax({
			type:"post",
			url:ctx+"/cloud-service/servicedef/queryVmType.action",
			async:false,
			datatype : "json",
			data:{"platFormId":value},
			success:(function(data){
				$('#vmTypeSel').empty();
			/*if(data.length>1){
					$('#vmTypeSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
				}*/
				$('#vmTypeSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
				$.each(data,function(index,obj){
					$('#vmTypeSel').append("<option value='"+obj.VALUE+"'>"+obj.NAME+"</option>");
				});
			})
		});
	}else{
		$('#vmTypeSel').empty();
		$('#vmTypeSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	}
}
function search(){
	var queryData = {
		appSystem : $('#appIdSel').val(), deployUnit : $('#deployUnitSel').val(),	
		platForm : $('#platFormIdSel').val(), virtualName : $('#vmTypeSel').val(),
		vmIP : $('#vmIP').val().replace(/(^\s*)|(\s*$)/g, ""),vmName : $('#selectVmName').val().replace(/(^\s*)|(\s*$)/g, ""),
		tenantId : $('#tenantId').val()
	};
	jqGridReload("gridTable", queryData);
}
function clearAll(){
	$('#deployUnitSel').empty();
	$('#deployUnitSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$('#vmTypeSel').empty();
	$('#vmTypeSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
}
function viewVmInfo(vmId){
	$.ajax({
			type:"post",
			url:ctx+"/resource/queryResource/queryVmInfo.action",
			async:false,
			datatype : "json",
			data:{"vmId":vmId},
			success:(function(data){
				$('#dataCenter').val(data.dataCenter);
				$('#poolName').val(data.poolName);
				$('#cdpName').val(data.cdpName);
				$('#clusterName').val(data.clusterName);
				$('#appSystem').val(data.appSystem);
				$('#deployUnit').val(data.deployUnit);
				$('#hostName').val(data.hostName);
				$('#vmName').val(data.vmName);
				$('#platForm').val(data.platForm);
				$('#virtualName').val(data.virtualName);
				$('#cpu').val(data.cpu);
				$('#mem').val(data.mem/1024);
				$('#cStatus').val(data.cStatus);
				$('#onlineTimme').val(data.onlineTimme);
				$('#ips').html(data.ips);
//				$('#ips').val(data.ips);
			})
		});
	$('#viewDiv').dialog({
		autoOpen : true,
		modal : true,
		height : 385,
		width : 750,
		title :i18nShow('info_vm_info')
	});
	$('#viewDiv').height(385);
}
function viewMachineInfo(value){
	$('#viewMachieDiv').dialog({
		autoOpen : true,
		modal : true,
		height : 375,
		width : 750,
		title :i18nShow('info_host_info')
	});
	$('#viewMachieDiv').height(375);
	$.post(ctx + "/resource/queryResource/queryMachineInfo.action",{hostId:value},function(data){
		$('#dataCenterMachine').val(data.dataCenter);
		$('#poolNameMachine').val(data.poolName);
		$('#cdpNameMachine').val(data.cdpName);
		$('#clusterNameMachine').val(data.clusterName);
		$('#hostNameMachine').val(data.hostName);
		$('#platFormMachine').val(data.platForm);
		$('#virtualNameMachine').val(data.virtualName);
		$('#cpuMachine').val(data.cpu);
		$('#memMachine').val(parseInt(data.mem/1024));
		$('#controlTime').val(data.controlTime);
		$('#ipsMachine').html(data.ips);
		$('#sn').val(data.sn);
		$('#manufacturer').val(data.manufacturer);
		$('#model').val(data.model);
		$('#status').val(data.status);
	});
}