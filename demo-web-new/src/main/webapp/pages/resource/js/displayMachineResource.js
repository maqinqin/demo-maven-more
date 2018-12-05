$(function() {
	$('#vmTypeSel').click(function(){
		var appIdSel = $('#platFormIdSel').find("option:selected").text();
		if(appIdSel == i18nShow('com_select_defalt')+'...'){
			showTip(i18nShow('tip_nw_platform_need'));
		}
	});
	$('#poolIdSel').click(function(){
		var appIdSel = $('#queryDataCenter').find("option:selected").text();
		if(appIdSel == i18nShow('com_select_defalt')+'...'){
			showTip(i18nShow('tip_datacenter_need'));
		}
	});
	$('#clusterIdSel').click(function(){
		var appIdSel = $('#poolIdSel').find("option:selected").text();
		if(appIdSel == i18nShow('com_select_defalt')+'...'){
			showTip(i18nShow('tip_respool_need'));
		}
	});
	$('#gridTable').jqGrid({
			url : ctx+"/resource/queryResource/queryMachineResource.action", 
			datatype : "json", 
			mtype : "post", 
			height : heightTotal() + 60,
			autowidth : true, 
			rownumbers : true,
			colNames:[i18nShow('rm_datacenter'),i18nShow('my_req_sr_res_pool'),i18nShow('my_req_sr_cluster'), i18nShow('my_req_sr_hostId'),i18nShow('host_res_device_name'),i18nShow('cloud_service_platform'),i18nShow('rm_platform_virture_type'), 'CPU', i18nShow('compute_res_deviceMem'), i18nShow('query_vm_m'),i18nShow('info_host_controlTime'),'IP','SN',i18nShow('info_host_manufacturer'),i18nShow('info_host_model')],
			colModel : [ 
			            {name : "dataCenter",index : "dataCenter",sortable : true,align : 'left', width:100},
			            {name : "poolName",index : "poolName",sortable : true,align : 'left', width:130},
			            {name : "clusterName",index : "clusterName",sortable : true,align : 'left', width:130},
			            {name : "hostId",index : "hostId",sortable : true,align : 'left', hidden:true},
			            {name : "hostName",index : "hostName",sortable : false,align : 'left',formatter:function(cellvalue, options, rowObject){
			            	return '<a href="javascript:;" style=" text-decoration:none" onclick="viewMachineInfo(\''+rowObject.hostId+'\')">'+rowObject.hostName+'</a>';
			            }},
			            {name : "platForm",index : "platForm",sortable : false, width:80, align : 'left'},
			            {name:"virtualName",index:"virtualName",sortable:false,width:90, align:"left"},
			            {name:"cpu", index:"cpu",sortable:false,align:"left", width:50},
			            {name:"mem", index:"mem",sortable:false,align:"left", width:50,formatter:function(cellvalue, options, rowObject){
			            	return rowObject.mem = parseInt(rowObject.mem / 1024)
			            }},
			            {name:"status", index:"status",sortable:false,align:"left", width:50},
			            {name:"controlTime", index:"controlTime",sortable:false,align:"left",hidden:true},
			            {name:"ips", index:"ips",sortable:false,align:"left",width:160},
			            {name:"sn", index:"sn",sortable:false,align:"left", hidden:true},
			            {name:"manufacturer", index:"manufacturer",sortable:false,align:"left", hidden:true},
			            {name:"model", index:"model",sortable:false,align:"left", hidden:true}
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
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridTableDiv").width());
		$("#gridTable").setGridHeight(heightTotal() + 60);
    });
	
	changeDateCenter($('#queryDataCenter').val());
	$('#queryDataCenter').change(function(){
		changeDateCenter(this.value);
	});
	
	//changeCdp($('#poolIdSel').val());
	$('#poolIdSel').click(function(){
		changeCdp($('#poolIdSel').val());
	});
	/*changeCdp($('#cdpIdSel').val());
	$('#cdpIdSel').change(function(){
		changeCdp(this.value);
	});*/
	
	changePlatFormType($('#platFormIdSel').val());
	$('#platFormIdSel').change(function(){
		changePlatFormType(this.value);
	});
	
});
//数据中心级联资源池
function changeDateCenter(value){
	if(value){
		$.ajax({
			type:"post",
			url:ctx+"/resource/queryResource/queryPool.action",
			async:false,
			datatype : "json",
			data:{"dataCenterId":value},
			success:(function(data){
				$('#poolIdSel').empty();
				$('#cdpIdSel').empty();
				$('#clusterIdSel').empty();
				
				if(data.length>=1){
					$('#poolIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
					$('#cdpIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
					$('#clusterIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
				}
				
				$.each(data,function(index,obj){
					$('#poolIdSel').append("<option value='"+obj.ID+"'>"+obj.POOL_NAME+"</option>");
				});
			})
		});
	}else{
		$('#poolIdSel').empty();
		$('#cdpIdSel').empty();
		$('#clusterIdSel').empty();
		$('#poolIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
		$('#cdpIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
		$('#clusterIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	}
}
//资源池级联cdp
function changePool(value){
	if(value){
		$.ajax({
			type:"post",
			url:ctx+"/resource/queryResource/queryCDP.action",
			async:false,
			datatype : "json",
			data:{"poolId":value},
			success:(function(data){
				$('#cdpIdSel').empty();
				$('#clusterIdSel').empty();

				if(data.length>1){
					$('#cdpIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
					$('#clusterIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
				}
				$.each(data,function(index,obj){
					$('#cdpIdSel').append("<option value='"+obj.ID+"'>"+obj.CDP_NAME+"</option>");
				});
			})
		});
	}
}
//cdp级联集群
function changeCdp(value){
	if(value){
		$.ajax({
			type:"post",
			url:ctx+"/resource/queryResource/queryCluster.action",
			async:false,
			datatype : "json",
			data:{"poolId":value},
			success:(function(data){
				$('#clusterIdSel').empty();
				/*if(data.length>1){
					$('#clusterIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
				}*/
				$('#clusterIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
				$.each(data,function(index,obj){
					$('#clusterIdSel').append("<option value='"+obj.ID+"'>"+obj.CLUSTER_NAME+"</option>");
				});
			})
		});
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
		dataCenter : $('#queryDataCenter').val(), poolName : $('#poolIdSel').val(),	cdpName:$('#cdpIdSel').val(), clusterName:$('#clusterIdSel').val(),
		platForm : $('#platFormIdSel').val(), virtualName :$('#vmTypeSel').val() , hostName : $('#selectHostName').val().replace(/(^\s*)|(\s*$)/g, "")
	};
	jqGridReload("gridTable", queryData);
}
function clearAll(){
	$('#poolIdSel').empty();
	$('#cdpIdSel').empty();
	$('#clusterIdSel').empty();
	$('#poolIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$('#cdpIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$('#clusterIdSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$('#vmTypeSel').empty();
	$('#vmTypeSel').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
}
function viewMachineInfo(value){
	$('#viewDiv').dialog({
		autoOpen : true,
		modal : true,
		height : 390,
		width : 750,
		title :i18nShow('info_host_info')
	});
	$('#viewDiv').height(375);
	$.post(ctx + "/resource/queryResource/queryMachineInfo.action",{hostId:value},function(data){
		$('#dataCenter').val(data.dataCenter);
		$('#poolName').val(data.poolName);
		$('#cdpName').val(data.cdpName);
		$('#clusterName').val(data.clusterName);
		$('#hostName').val(data.hostName);
		$('#platForm').val(data.platForm);
		$('#virtualName').val(data.virtualName);
		$('#cpu').val(data.cpu);
		$('#mem').val(parseInt(data.mem/1024));
		$('#controlTime').val(data.controlTime);
		$('#ips').html(data.ips);
		$('#sn').val(data.sn);
		$('#manufacturer').val(data.manufacturer);
		$('#model').val(data.model);
		$('#status').val(data.status);
	});
}