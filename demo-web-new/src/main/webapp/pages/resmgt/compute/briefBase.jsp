<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/count.css"></link>
<link rel="stylesheet" type="text/css" href="/icms-web/css/new.css"></link>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<style>
#updataProjectVpcForm p{width:300px; padding:0; padding-left:16px;}
#updataProjectVpcForm p i{width:100px; text-align:left;}
#updataProjectVpcForm label{display:block; padding-left:115px;}
#vm_form label{padding-left:20px;}
.btn_dd3{ 
	float: right; height: 24px;
	font-weight: normal;
	padding: 0 24px;
}
.sumTable2 label,#cluster_div label{
	padding-left:0px;
}
</style>
<script type="text/javascript">
function updateLoginInfo(){
	var hostId = $("#cId").val();
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx + "/resmgt-common/phymacmanageserver/getUserInfo.action",
		async : false,
		data:{"resourceId":hostId},
		success:(function(data){
			$("#u_username").val(data.username);
			$("#u_password").val(data.password);			
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
		} 
	});
	$("#loginInfoDiv").dialog({
		height:250,
		width:400,
		modal:true,
		title:i18nShow('update_logininfo')
	}) 
} 
function closeDiv(id){
	$("#"+id).dialog("close");
}
function saveLoginInfoBtn(){
	var hostId = $("#cId").val();
	var username = $("#u_username").val();
	var password = $("#u_password").val();
	if(!username){
		showTip(i18nShow('validate_image_username_required'));
		return false;
	}
	if(!password){
		showTip(i18nShow('validate_image_pwd_required'));
		return false;
	}
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx + "/resmgt-common/phymacmanageserver/updateHostPasswd.action",
		async : false,
		data:{
			"cupp.id":hostId,
			"cupp.username":username,
			"cupp.password":password
			},
		success:(function(data){
			closeDiv("loginInfoDiv");
			showTip(i18nShow('compute_res_updateSuccess'));
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
		} 
	}); 
	
}
function alternativeHost(cnVtId,vId,cpu,mem,tp,vmName,vmType,rmRedPoolType,dataDeTreType){
	if(vId == "" || vId == null){
		vId = $("#vId").val();
	}
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx + "/queryCVCpuMem.mvc",
		async : false,
		data:{"vId":vId},
		success:(function(dat){
			$("#ip").val(dat.ip);
			$("#cId").val(dat.devceId);
			$("#cmCpuUsed").val(dat.cmCpuUsed);
			$("#cmMemUsed").val(dat.cmMemUsed);
			$("#dataStoreId").val(dat.dataStoreId);
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
		} 
	});
	cvmNuHost(cnVtId,vId,cpu,mem,tp,vmName,vmType,rmRedPoolType,dataDeTreType);
}

function cvmNuHost(cvmDeviceId,vId,cpu,mem,tp,vmName,vmType,rmRedPoolType,dataDeTreType){
	$("#vcCpuUsed").val(cpu);
	$("#vcMemUsed").val(mem);
	var vchvmType,vcmPoolType,dataDeTreType;
	if(tp == 'l'){
		vchvmType = vmType;
		vcmPoolType = rmRedPoolType;
	}else{
		vchvmType = $("#vchvmType").val();
		vcmPoolType = $("#vcmPoolType").val();
		dataDeTreType = $("#dataDeTreType").val();
	}
	var rceCIp = $("#ip").val();
	var dcId = $("#dcId").val();
	openDialog('gridTable_alternativeHost_div',i18nShow('compute_res_selectHost'), 900, 450);
	$("#gridTable_alternativeHostButtonDiv").show();
	$("#gridTable_alternativeHost").jqGrid().GridUnload("gridTable_alternativeHost");
	$("#gridTable_alternativeHost").jqGrid({
		url : ctx+"/alternativeHost.mvc",
		rownumbers : false,
		datatype : "json",
		mtype : "post",
		multiselect : false,
		postData : {"vchvmType":vchvmType,"vcmPoolType":vcmPoolType,"vId":vId,"dataDeTreType":dataDeTreType,"dcId":dcId},
		height : 310,
		autowidth : true,
		colModel : [{
			name : "devceId",
			index : "devceId",
			label : i18nShow('host_res_id'),
			width : 10,
			sortable : true,
			align : 'left',
			hidden : true
		},
		{
			name : "pname",
			index : "pname",
			label : i18nShow('compute_res_transfer_destHostName'),
			width : 139,
			sortable : true,
			align : 'left'
		},
		{
			name : "cpu",
			index : "cpu",
			label : i18nShow('compute_res_transfer_cpu'),
			width : 33,
			sortable : true,
			align : 'left'
		},
		{
			name : "cpuUsed",
			index : "cpuUsed",
			label : "cpuUsed",
			width : 0,
			sortable : false,
			hidden : true
		},
		{
			name : "ramUsed",
			index : "ramUsed",
			label : "ramUsed",
			width : 0,
			sortable : false,
			hidden : true
		},
		{
			name : "dataStoreName",
			index : "dataStoreName",
			label : "dataStoreName",
			width : 0,
			sortable : false,
			hidden:true
		},
		{
			name : "ip",
			index : "ip",
			label : "ip",
			width : 0,
			sortable : false,
			hidden:true
		},
		{
			name : "remainingCpu",
			index : "remainingCpu",
			label : i18nShow('compute_res_transfer_remainingCpu'),
			width : 33,
			sortable : true,
			align : 'left'
		},
		{
			name : "ram",
			index : "ram",
			label : i18nShow('compute_res_transfer_mem'),
			width : 33,
			sortable : true,
			align : 'left'
		},
		{
			name : "remainingMem",
			index : "remainingMem",
			label : i18nShow('compute_res_transfer_remainingMem'),
			width : 33,
			sortable : true,
			align : 'left'
		},
		{
			name : "virtTypeCode",
			index : "virtTypeCode",
			label : i18nShow('compute_res_transfer_virtTypeCode'),
			width : 66,
			sortable : true,
			align : 'left',
			hidden: true
		},
		{
			name : "",
			index : "",
			label : i18nShow('com_operate'),
			width : 13,
			sortable : true,
			align : 'left',
			hidden : false,
			formatter:function(cellVall, options, rowObject){
				var ret =  "<a href='javascript:#' style=' text-decoration:none' onclick=checkMoveVM('"+rowObject.devceId+"','"+vId+"','"+rowObject.cpuUsed+"','"+rowObject.ramUsed+"','"+tp+"','"+rowObject.dataStoreName+"','"+rceCIp+"','"+rowObject.ip+"','"+rowObject.virtTypeCode+"')>"+i18nShow('compute_res_device_transfer')+"</a>"
				return ret;
			}
		}],
		 viewrecords : true,
		sortname : "pname",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "search_hvm"
		}, 
		 jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		}, 
		 pager : "#gridPager_alternativeHost",
		hidegrid : false,
		gridComplete : function() {
			getVmDeviceId("gridPager_alternativeHost", "");
		} 
	});
	$("#gridTable_alternativeHost_pname").css("width","266px");
	$("#gridTable_alternativeHost_cpu").css("width","90px");
	$("#gridTable_alternativeHost_remainingCpu").css("width","90px");
	$("#gridTable_alternativeHost_ram").css("width","90px");
	$("#gridTable_alternativeHost_remainingMem").css("width","90px");
	$("#gridTable_alternativeHost_cmvmCount").css("width","90px");
	$("#gridTable_alternativeHost_").css("width","90px");
	$("#gridTable_alternativeHost tbody .jqgfirstrow td:eq(1)").css("width","266px");
	$("#gridTable_alternativeHost tbody .jqgfirstrow td:eq(2)").css("width","90px");
	$("#gridTable_alternativeHost tbody .jqgfirstrow td:eq(7)").css("width","90px");
	$("#gridTable_alternativeHost tbody .jqgfirstrow td:eq(8)").css("width","90px");
	$("#gridTable_alternativeHost tbody .jqgfirstrow td:eq(9)").css("width","90px");
	$("#gridTable_alternativeHost tbody .jqgfirstrow td:eq(10)").css("width","90px");
	$("#gridTable_alternativeHost tbody .jqgfirstrow td:eq(11)").css("width","90px");
}

function copyFcValue(){
	document.getElementById("virtual_fc_mappings").value = "300//1/312/fcs1,301//2/312/fcs1,302//1/313/fcs3,303//2/313/fcs3";
}
function copyScsiValue(){
	document.getElementById("virtual_scsi_mappings").value ="200//1/206,201//2/206";
}
function closeMessage(){
	$("#powerMessage").dialog("close");
}
function closeIpmiMessage(){
	$("#ipmiMessage").dialog("close");
}
function savePowerInfo(){
	var info="";
	var dest_lpar_id = $('#dest_lpar_id').val();
	var virtual_fc_mappings = $('#virtual_fc_mappings').val();
	var virtual_scsi_mappings = $('#virtual_scsi_mappings').val();
	var source_msp_id = $('#source_msp_id').val();
	var dest_msp_id = $('#dest_msp_id').val();
	if(dest_lpar_id==""){
		showTip(i18nShow('compute_res_transfer_power_lparId'));
		return false;
	}else if(virtual_fc_mappings==""){
		showTip(i18nShow('compute_res_transfer_powerFc'));
		return false;
	}else if(virtual_scsi_mappings==""){
		showTip(i18nShow('compute_res_transfer_power_scsi'));
		return false;
	}else if(source_msp_id==""){
		showTip(i18nShow('compute_res_transfer_power_sourceLparID'));
		return false;
	}else if(dest_msp_id==""){
		showTip(i18nShow('compute_res_transfer_power_destLparID'));
		return false;
	}
	closeMessage();
	var vId = $('#powVid').val();
    var tarHostId = $('#tarHostId').val();
	info = tarHostId+";"+dest_lpar_id+";"+virtual_fc_mappings+";"+virtual_scsi_mappings+";"+source_msp_id+";"+dest_msp_id;
	showTip(i18nShow('compute_res_transfer_tip'),function(){
       	showTip("load");
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/moveVM.mvc", //调接口
			async : false,
			data:{
					"vmId":vId,
					"dataStoreName":"",
					"rceCIp":"",
					"targetCIp":"",
					"cId":tarHostId,
					"info":info
				},
			success:(function(dat){
				if(dat.result == "success"){
					closeTip();
					showTip(i18nShow('compute_res_transfer_success'));
					location.reload(); 
				}else{
					closeTip();
					showError(i18nShow('compute_res_transfer_fail')+dat.result);
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				closeTip();
				showError(i18nShow('compute_res_transfer_fail'));
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#gridTable_alternativeHost_div").dialog("close");
			}
		});
 	});
}
function checkMoveVM(forRet,vId,hostCpuUsed,hostMemUsed,tp,dataStoreName,rceCIp,targetCIp,virtTypeCode){
	if(virtTypeCode=='PV'){
		openDialog('powerMessage',i18nShow('compute_res_transfer_inputInfo'),710,300);
		$('#dest_lpar_id').val("");
		$('#virtual_fc_mappings').val("");
		$('#virtual_scsi_mappings').val("");
		$('#source_msp_id').val("");
		$('#dest_msp_id').val("");
		$('#powVid').val(vId);
	    $('#tarHostId').val(forRet);
	}else{
		moveVM(forRet,vId,hostCpuUsed,hostMemUsed,tp,dataStoreName,rceCIp,targetCIp,"");
	}
}

function moveVM(forRet,vId,hostCpuUsed,hostMemUsed,tp,dataStoreName,rceCIp,targetCIp,info){
 	showTip(i18nShow('compute_res_transfer_toHost'),function(){
       	showTip("load");
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/moveVM.mvc", //调接口
			async : false,
			data:{
					"vmId":vId,
					"dataStoreName":dataStoreName,
					"rceCIp":rceCIp,
					"targetCIp":targetCIp,
					"cId":forRet,
					"info":""
				},
			success:function(dat){
				if(dat.result == "success"){
					closeTip();
					showTip(i18nShow('compute_res_transfer_success'));
					location.reload(); 
				}else{
					closeTip();
					showError(i18nShow('compute_res_transfer_fail')+dat.result);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				closeTip();
				showError(i18nShow('compute_res_transfer_fail'));
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#gridTable_alternativeHost_div").dialog("close");
			}
		});
 	});
}

function updateSourceStorage(cid,vId,hostCpuUsed,hostMemUsed,tp){
	var cpu,mem,sourceCPU,sourceMEM,vid;
	var cmCpuUsed = $("#cmCpuUsed").val();
	var cmMemUsed = $("#cmMemUsed").val();
	var cId = $("#cId").val();
	if(tp == "l"){
		cpu = $("#vcCpuUsed").val();
		mem = $("#vcMemUsed").val();
		sourceCPU = cmCpuUsed - cpu;
		sourceMEM = cmMemUsed - mem;
		vid = vId;
	}else{
		var vCpued = $("#vm_cpu").html();
		var vMemed = $("#vm_mem").html();
		sourceCPU = cmCpuUsed - vCpued;
		sourceMEM = cmMemUsed - vMemed;
		vid = $("#vId").val();
	}
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/updateHostStorage.mvc",
		async : false,
		data:{"devceId":cId,"sourceCPU":sourceCPU,"sourceMEM":sourceMEM,"vid":vid},
		success:(function(data){
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
		} 
	});
}

function updateTargetStorage(vid,id,hostCpuUsed,hostMemUsed,tp){
	var cpu,mem,sourceCPU,sourceMEM,id;
	if(tp == "l"){
		var vCpued = $("#vcCpuUsed").val();
		var vMemed = $("#vcMemUsed").val();
		sourceCPU = parseInt(vCpued) + parseInt(hostCpuUsed);
		sourceMEM = parseInt(vMemed) + parseInt(hostMemUsed);
		id = id;
	}else{
		var vCpued = $("#vm_cpu").html();
		var vMemed = $("#vm_mem").html();
		sourceCPU = parseInt(hostCpuUsed) + parseInt(vCpued);
		sourceMEM = parseInt(hostMemUsed) + parseInt(vMemed);
		id = $("#vId").val();
	}
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/updateHostStorage.mvc",
		async : false,
		data:{"devceId":vid,"sourceCPU":sourceCPU,"sourceMEM":sourceMEM,"vid":id},
		success:(function(data){
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
		} 
	});
}

</script>

<style type="text/css">
.Btn_jianXi{margin-left:0;}
.btn_gray{margin-right:5px; margin-left:0px;}
.btn_gray:hover{margin-right:5px; margin-left:0px;}
.short_btn .btn_dd2{
	width:100px;
	padding:0;
	text-align:center;
	height:24px;
	line-height:24px
}

.short_btn .btn_dd2:hover{
	width:100px;
	padding:0;
	text-align:center;
	height:24px;
	line-height:24px
}

.btn_dd3:hover{
	height:24px;
	text-align:center;
}

.no_btnbar1 .btnbar1{
	padding:0;
}

.no_btnbar1 .btn_dd2{
	margin-top:10px;
}
</style>

<div id="new_tab_inf_a" >
  	<div id="new_tab_inf_b" class="sumCon_top">
      	<p class="tip2"><icms-i18n:label name="res_l_comput_survey"/></p><span class="tip2b"></span>
          <table class="sumTable2" style="width:97%;">
          	<tr>
              	<th style="width:50px;"><icms-i18n:label name="res_l_comput_selected"/>:</th>
      			<td id="s_poolName_current" style="color:#646964;padding-left: 34px;"></td>
                <th style="width:50px;"><icms-i18n:label name="res_l_comput_rmResPoolCount"/>:</th>
      			<td><input id="rmResPoolCount" style="padding-left: 34px;"/></td>
                <th style="width:50px;"><icms-i18n:label name="res_l_comput_cmDeviceCount"/>:</th>
      			<td><input id="cmDeviceCount"style="padding-left: 34px;"/></td>
      			<th style="width:50px;"><icms-i18n:label name="res_l_comput_rmVmCount"/>:</th>
      			<td><input id="rmVmCount" style="padding-left: 34px;"/></td>
            </tr>
            <!--
            <tr>
            <td ><label id="s_poolName_current"></label></td>
                <th>总可分CPU核数:</th>
      			<td><input id="cpu_count"/></td>
      			<th>已分配CPU核数:</th>
      			<td><input id="cpuUsed_count"/></td> 
            </tr>
            -->
          </table>
         <div id="echart_meterDic_div">
         	<ul class="workPho_echart">
	         	<li><div id="echart_meterDic_left" class="phoLeft" style="width:90%"></div></li>
	         	<li><div id="echart_meterDic_right" class="phoRight" style="width:90%"></div></li>
         	</ul>
         </div>
       </div>
       <div id="con_div" class="sumCon_top">
       	<p class="tip2"><icms-i18n:label name="res_l_comput_basic_info"/></p><span class="tip2b"></span>
   		<div id="pl_div" style="display: none">
		   <input type="hidden" id="s_platformType" name="s_platformType"/>
		   <input type="hidden" id="s_resid" name="s_resid" >
		   <input type="hidden" id="datacenterId" name="datacenterId" > 
		   <table class="sumTable2" style="width: 97%">
			<tr>
				<th><icms-i18n:label name="res_l_comput_poolName"/>：</th><td width="30%"><label id="s_poolName" 
style=""></label></td>
				<th><icms-i18n:label name="res_l_comput_poolEname"/>：</th><td><label id="s_ename" style=""></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="bm_l_platform_type"/>：</th>
				<td>
					<label id="s_platformTypeName" style=""></label>
				</td>
				<th><icms-i18n:label name="res_l_comput_serviceType"/>：</th>
				<td>
					<label id="s_serviceType" style=""></label>
				</td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_comput_secureArea"/>：</th>
				<td>
					<label id="s_secureAreaType" style=""></label>
				</td>
				<th><icms-i18n:label name="res_l_comput_secureLayer"/>：</th>
				<td>
					<label id="s_secureLayer" style=""></label>
				</td>
			</tr>
			<tr id="azControl">
				<th ><icms-i18n:label name="res_l_comput_availableZone"/>：</th>
				<td >
					<label id="s_availableZone" style=""></label>
				</td>
				<th><icms-i18n:label name="res_l_comput_hostType"/>：</th>
				<td><label id="s_hostType" style=""></label></td>
			</tr>
			<tr id="remarkControl">
				<th><icms-i18n:label name="res_l_comput_remark"/>：</th>
				<td><label id="s_remark1" style=""></label></td>
			</tr>
		  </table>
	<p class="btnbar1" style="margin-left:25px;">
	<!-- 
	<shiro:hasPermission name="js_xjcdp">
		<input type="button" id="cdp_add" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_cdp_add"/>'  style="margin-left:0px;" />
	</shiro:hasPermission>
 	-->
	 <shiro:hasPermission name="js_xjjq">
		<input type="button" id="cluster_add_underRes" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_cluster_add"/>' />
	</shiro:hasPermission>
	<shiro:hasPermission name="js_xgzyc">
		<input type="button" id="pool_modify"  class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_updatePool"/>'  onclick="showPoolUpdateDiv()" />
	</shiro:hasPermission>
	<shiro:hasPermission name="js_sczyc">
		<input type="button" id="pool_del"     class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_delResPool"/>' />
     </shiro:hasPermission>
     <shiro:hasPermission name="js_sfzyc">
		<input type="button" id="pool_rel"     class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_releaseResPool"/>' onclick="relPool()" />
     </shiro:hasPermission>
     <input type="button" id="pool_synch"     class="btn btn_dd2"  value="同步资源" onclick="synchRes()" />
	</p>
	</div>
	
	<div id="dc_div" style="display: none; top:136px;">
  		<table border="0" cellpadding="0" cellspacing="0" class="sumTable2" style="width: 97%;">
			<tr>
			<th><icms-i18n:label name="res_l_comput_dcName"/>：</th><td width="30%"><label id="lab_dcname" style="padding-left:24px;"></label></td>
			
			<th><icms-i18n:label name="res_l_comput_poolEname"/>：</th><td><label id="lab_dcEname" style="padding-left:24px;"></label></td>
			</tr>
			<tr>
			<th><icms-i18n:label name="res_l_comput_dcAddress"/>：</th><td><label id="lab_address" style="padding-left:24px;"></label></td>
			
			<th><icms-i18n:label name="res_l_comput_isActive"/>：</th><td><label id="lab_isActive" style="padding-left:24px;"></label></td>
			</tr>
  		</table>
  		<p class="btnbar1" style="padding-left:29px;">
  		<shiro:hasPermission name="js_xjzyc">
			<input type="button" id="pool_add" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_addResPool"/>'  style="margin-right: 5px;" />
			</shiro:hasPermission>
		</p>
  	</div>
  	
  	<div id="cdp_div" style="display: none">
			<table border="0" cellpadding="0" cellspacing="0" class="report" style="width: 97%">
			<input type="hidden" id="s_cdpid" name="s_cdpid" >
			<input type="hidden" id="ss_platformType" name="ss_platformType"/>
			<tr>
				<th>cdp<icms-i18n:label name="res_l_datastore_name"/>：</th><td width="30%"><label id="s_cdpName"></label></td>
				<th><icms-i18n:label name="res_l_comput_poolEname"/>：</th><td><label id="s_cdpEname"></label></td>
			</tr>
			<tr>
			<th><icms-i18n:label name="res_l_comput_remark"/>：</th>
			<td>
				<label id="s_cdpRemark"></label>
			</td>
			</tr>
		</table>
		<p class="btnbar1" style="margin-left:20px;">
		<shiro:hasPermission name="js_xjjq">
			<input type="button" id="cluster_add" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_cluster_add"/>'  style="margin-left: 5px;" />
		</shiro:hasPermission>
		<shiro:hasPermission name="js_xgcdp">	
			<input type="button" id="cdp_modify"  class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_cdp_modify"/>'  style="margin-left: 5px;" />
		</shiro:hasPermission>
		<shiro:hasPermission name="js_sccdp">	
			<input type="button" id="cdp_del"     class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_cdp_del"/>'  style="margin-rleft: 5px;" />
		</shiro:hasPermission>
		</p>
  	</div>
  	
  	<!-- 集群信息展示 -->
	<div id="cluster_div" style="display: none">
		<table border="0" cellpadding="0" cellspacing="0" class="report" style="width: 97%">
			<tr>
			<th><icms-i18n:label name="res_l_comput_clusterName"/>：</th>
			<td><label id="cluster_Name"></label></td>
			<th><icms-i18n:label name="res_l_comput_poolEname"/>：</th>
			<td><label id="cluster_ename" ></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_comput_vmType"/>：</th>
				<td>
					<label id="s_vmType" ></label>
					<input type="hidden" id="hidden_vmType" name="hidden_vmType"/>
				</td>
				<th><icms-i18n:label name="res_l_comput_vmDistriType"/>：</th>
				<td>
					<label id="s_vmDistriType"></label>
				</td>
			</tr>
			<tr>
				<!-- <th>存储类型：</th>
				<td>
					<label id="s_datastoreType"></label>
				</td> -->
				<th><icms-i18n:label name="res_l_comput_networkConvergence"/>：</th>
					<td>
					<label id="s_networkConvergence" ></label>
				</td>
				<th id="s_manageServer_th"><icms-i18n:label name="res_l_comput_manageServer"/>：</th>
				<td id="s_manageServer_td">
					<label id="s_manageServer" ></label>
				</td>
			</tr>
			<%-- <tr>
				<th><icms-i18n:label name="res_l_comput_manageServerBak"/>：</th>
				<td>
					<label id="s_manageServerBak"></label>
				</td>
			</tr> --%>
			<tr>
			<!-- <th id="ccsb">存储设备：<input type="hidden" id="storage_device_id" name="storage_device_id" /></th>
			<td id="ccsbz"><label id="storage_device_name"></label></td> -->
			<th><icms-i18n:label name="res_l_comput_remark"/>：</th>
			<td><label id="cluster_remark"></label><input type="hidden" id="datastore_type"/></td>
			<td><input type="hidden" id="cdpId"/></td>
			<td></td>
			</tr>
		</table>
	
		<!-- 展示已关联的主机信息 -->
		<div id="cluster_show_div" style="width: 97%;margin:0 auto;float: left;margin-left: 10px;display: none;">
			<div class="btnbar1" style="display: inline-block;height: 41px;float: left;padding-top: 10px; padding-left:0;">
				<shiro:hasPermission name="js_xgjq">
					<input id="opstackUpdateCluster" type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_cluster_modify"/>' onclick="showClusterUpdateDiv();" style="margin-left:25px;font-size: 13px;margin-top: 3px;"/>
				</shiro:hasPermission>
			</div>
			<table id="tab10" border="0" style="width: 70%;text-align: left; padding-top:10px; padding-bottom:5px; height:41px;">
				<tr style="height: 30px;">
				<td class="btnbar1" style="padding-left:0px;">
				<shiro:hasPermission name="js_scjq">
				<input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_cluster_delete"/>' onclick="deleteClusterData();" />
				</shiro:hasPermission>
				<shiro:hasPermission name="js_xzgl">
				<input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_add_rela"/>' onclick="initAllHostCanRelevanceInfo();" id="js_xzgl"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="js_scgl">
				<input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_delete_rela"/>' onclick="deleteHostRel();"/>
				</shiro:hasPermission>
				<!--
				<shiro:hasPermission name="js_ljaz">
				<input type="button" class="btn" value="裸机安装" onclick='installHostOS();' style="margin-left:5px;"/>
				</shiro:hasPermission>
				-->
				</td>
				</tr>
	  		</table>
	  		<div id="gridTable_div" class="panel clear" style="margin-left:25px;margin-top: 10px;">
			<table id="gridTable"></table>
			<table id="gridPager"></table>
			</div>
		</div>
		
		<!-- 展示未关联的主机信息 -->
		<div id="device_show_div"  style="width: 97%;margin:10px auto;float: left;margin-left: 0px;display: none;">
	  		<table id="tab11" border="0" class="pageFormContent" style="margin-left:11px">
				<tr style="height: 25px;">
					<td style="width:80px; padding-left:20px;"><icms-i18n:label name="res_l_comput_hostName"/>:</td>
					<td style="width:200px;"><input type="text" id="deviceName2" class="textInput" style="width: 180px"></input></td>
					<td style="width:80px; padding-left:20px;">&nbsp;SN：</td>
					<td style="width:200px;"><input type="text" id="sn2" class="textInput" style="width: 180px"></input></td> 
				</tr>
				<!--<tr style = "height: 50px ;">
				    <td style="width:80px; padding-left:20px;"><icms-i18n:label name="res_l_device_isBare"/>:</td>
				    <td style="width:200px;">
				    	<select name="isBare2" id="isBare2" style="width: 190px ; height:24px; line-height:24px; background-color: #f5f5f5; box-shadow: 1px 1px 2px #ededed; border: 1px solid #d5d5d5;">
				    		<option selected="selected" value=""><icms-i18n:label name="res_l_comput_select"/>...</option>
							<option value="Y"><icms-i18n:label name="res_l_comput_bare"/></option>
							<option value="N"><icms-i18n:label name="res_l_comput_noBare"/></option>
				    	</select>
				    </td>	
					<td style="width:80px; padding-left:20px;"></td>
					<td style="width:200px;"></td>
				</tr>-->
				<tr>
					<td colspan="5" class="btnbar1">
						<input type="button" value='<icms-i18n:label name="common_btn_return"/>' onclick="rtnCanRelevanceInfo()" class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;"/>
						<input type="button" value='<icms-i18n:label name="common_btn_query"/>' onclick="search2();" class="btn btn_dd2 btn_dd3" style="margin-right: 2px;"></input>
						<input type="button" value='<icms-i18n:label name="res_btn_rela"/>' onclick="relevance();" class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;" ></input>
					</td>
				</tr>
	  		</table>
	  		<div id="deviceTable_div" class="panel clear" style="margin-left: 11px;">
		 	<table id="deviceTable"></table>
	  		<table id="devicePager"></table>
	  		</div>
		</div>
		
  	</div>
  	<!-- 弹框提示输入ipmi信息 -->
  	<div id="ipmiMessage" style="display: none;height:auto;text-align:right; padding-top:18px;">
  	       <table border="0" class="pageFormContent" style="margin-left:11px">
  	       <tr style="height: 25px; margin-top:15px"><td style="width:80px; padding-left:20px;"><font color="red">*</font><icms-i18n:label name="res_l_ipmi_user"/>:</td><td><input id="iUser" type="text" class="textInput" style="width: 180px" value=""></td></tr>
  	       <tr style="height: 25px;"></tr>
  	       <tr style="height: 25px;"><td style="width:80px; padding-left:20px;"><font color="red">*</font><icms-i18n:label name="res_l_ipmi_pwd"/>:</td><td><input id="iPwd" type="password" class="textInput" style="width: 180px" value=""></td></tr>
  	       <tr style="height: 25px;"></tr>
  	      <tr style="height: 25px;"><td style="width:80px; padding-left:20px;"><font color="red">*</font><icms-i18n:label name="res_l_ipmi_address"/>:</td><td><input id="iUrl" type="text" class="textInput" style="width: 180px" value=""></td></tr>
  	       <tr style="height: 25px;"></tr>
  	       <tr>
  	       <td style="width:80px; padding-left:20px;"><font color="red">*</font><icms-i18n:label name="res_l_ipmi_ver"/>:</td>
  	       <td>
  	       <select id="iVer" class="textInput" style="height:24px; line-height:24px; width:194px;" >
  	       <option value=""><icms-i18n:label name="res_l_comput_select"/></option>
  	       <option value="1.5">1.5</option>
  	       <option value="2.0">2.0</option>
  	       </select>
  	       </td>
  	       </tr>
  	       </table>
		   <input type="button" id="btn_ipmi_cancel" title='<icms-i18n:label name="commom_btn_cancel"/>' value='<icms-i18n:label name="commom_btn_cancel"/>'  class="btn btn_dd2 btn_dd3" onclick="closeIpmiMessage()" style="margin-right:55px; margin-left:0;" />
  	   		 <input type="button" id="btn_ipmi_sp" class="btn btn_dd2 btn_dd3" onclick="showHardWareStatus()" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' style=" margin-right: 5px;margin-left: 5px;" />
  	       
		   
		   </div>
  	
  	</div>
  	
  	<!-- 显示硬件状态 -->
  	<div id="showHardWareStatus" style ="display: none;height: auto;text-align:right;">
  	<table id ="showHardWareStatusTable" style="margin-top: -15px; margin-left:0px;margin-right:0px ">
  	</table>
  	
			<input class="unit unit2" type="button" style="margin-right: 5px; margin-top:15px; margin-left:150px;" onclick="closeHardWareStatus()" value='<icms-i18n:label name="res_btn_closeWin"/>'>
			
  	</div>
  	
    <!-- 显示datastore -->
    <div id="showDataStore" style="display: none; padding-top:18px;">
    <div id="gridTableDiv" class="panel clear" style="margin-left: 1px;margin-top: 10px;">
			<input type="hidden" id="datastoreId" name="datastoreId"/>
			<table id="gridTable1"></table>
			<div id="gridPager1"></div>
			</div>
			<p class="btnbar btnbar1" style="text-align:right; width:659px;padding-left:0;"><label id="sp_error_tip"></label>
			<input class="btn btn_dd2 btn_dd3" type="button" style="margin-right: 5px; margin-left:0;" onclick="closeShowDataStore()" value='<icms-i18n:label name="res_btn_closeWin"/>'>
			</p>
		</div> 
		
  	<!-- 物理机详细信息 -->
	<div id="host_div" style="display: none">
	<input type="hidden" id="hostType" name="hostType"/>
	<form id="host_form" action="" method="post">
		<table border="0" cellpadding="0" cellspacing="0" class="report" style="width: 97%">
			<tr>
				<th><icms-i18n:label name="res_l_comput_hostName"/>：</th>
				<td width="30%"><label id="device_name" style="padding-left:0;"></label>
				<input type="hidden" id="hostId" name="hostId"/>
				<input type="hidden" id="clusterId" name="clusterId"/></td>
				<th>SN：</th><td><label id="sn" style="padding-left:0;"></label></td>
			</tr>
			<tr>
				<th>CPU：</th><td><label id="cpu" style="padding-left:0;"></label></td>
				<th><icms-i18n:label name="bm_l_device_mem"/>：</th><td><label id="mem" style="padding-left:0;"></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_comput_usedCpu"/>：</th><td><label id="cpuUsed" style="padding-left:0;"></label></td>
				<th><icms-i18n:label name="res_l_comput_usedMem"/>：</th><td><label id="memUsed" style="padding-left:0;"></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_comput_model"/>：</th><td><label id="device_model" style="padding-left:0;"></label></td>
				<th><icms-i18n:label name="res_l_device_manufacturer"/>：</th><td><label id="manufacturer" style="padding-left:0;"></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_comput_sdisk"/>：</th><td><label id="disk" style="padding-left:0;"></label></td>
				<th><icms-i18n:label name="res_l_comput_seat"/>：</th><td><label id="seat_name" style="padding-left:0;"></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="bm_l_belongDc"/>：</th><td><label id="datacenter_cname" style="padding-left:0;"></label></td>
				<th><icms-i18n:label name="res_l_bl_clusterName"/>：</th><td><label id="cluster_name" style="padding-left:0;"></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_bl_cdpName"/>：</th><td><label id="cdp_name" style="padding-left:0;"></label></td>
				<th><icms-i18n:label name="res_l_bl_resPoolName"/>：</th><td><label id="pool_name" style="padding-left:0;"></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_comput_invcState"/>：</th>
				<td>
					<table>
						<tr>
							<td><label id='is_invc' style="padding-left:0;"></label></td>

						</tr>
					</table>
				<th><icms-i18n:label name="res_l_comput_invcTime"/>：</th><td><label id='control_time' style="padding-left:0;"></label></td>
				<tr>
				<th><icms-i18n:label name="res_l_comput_hostState"/>：</th><td><label id="hostState" style="padding-left:0;"></label></td>
				<th><icms-i18n:label name="res_l_ipmi_address"/>：</th><td><label id="ipmiU" style="padding-left:0;"></label></td>
			</tr>
			<tr>
				<td clospan="2"><input type="hidden" id="impiFlag"></td>
			</tr>
		</table>
		<table id="host_table_id" border="0" cellpadding="0" cellspacing="0" class="report" style="width: 97%">
		</table>
		<table id="host_ipmi_url" border="0" cellpadding="0" cellspacing="0" class="report" style="width: 97%">
		</table>
		<table id="tab101" border="0" style="width:90%; text-align: left; margin-left:25px; padding-top:10px; padding-bottom:5px; height:41px;">
		<tr>
			<td style="height:60px; padding-top:10px; padding-left:5px;" class="short_btn">
			<p style="float:left; padding:0; height:30px;" class="btnbar1"><input id="outVcbtn" type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_outVcbtn"/>' style="margin-right:5px; margin-left:0;" /></p>
			<p style="float:left; padding:0; height:30px;" class="btnbar1" id= "datatstore_type" ><input type= "button" value= '<icms-i18n:label name="res_btn_datatstore"/>' onclick= "showDataStore()" class= "btn btn_dd2"></input></p>
		    <p style="float:left; padding:0; height:30px;" class="btnbar1"><input id ="ScanButton" type= "button" value='<icms-i18n:label name="res_btn_ScanButton"/>' onclick="scanVm()" class="btn btn_dd2" style="margin-right:5px; margin-left:0;"/></p>
		    <p style="float:left; padding:0; height:30px;" class="btnbar1"><input id="inVcbtn" type="button" class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_inVcbtn"/>' style="margin-right:5px; margin-left:0;"/></p>
		    <p style="float:left; padding:0; height:30px;" class="btnbar1"><input id="HardwareStatus" type="button" class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_hardwareStatus"/>' onclick="ipmiMessage()" style="margin-right:5px; margin-left:0;"/></p>
		    <p style="float:left; padding:0; height:30px;" class="btnbar1"><input id="maintenanceMode" type="button" class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_maintenanceMode"/>' onclick="maintenanceModeMethod()" style="margin-right:5px; margin-left:0;"/></p>
		    <p style="float:left; padding:0; height:30px;" class="btnbar1"><input id="exitMaintenanceMode" type="button" class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_exitMaintenanceMode"/>' onclick="exitMaintenanceModeMethod()" style="margin-right:5px; margin-left:0;"/></p>
		    <p style="float:left; padding:0; height:30px;" class="btnbar1"><input id="pmClose" type="button" class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_device_close"/>' onclick="pmCloseMethod()" style="margin-right:5px; margin-left:0;"/></p>
		    <p style="float:left; padding:0; height:30px;" class="btnbar1"><input id="pmRestart" type="button" class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_device_restart"/>' onclick="pmReboot()" style="margin-right:5px; margin-left:0;"/></p>
		    <p style="float:left; padding:0; height:30px;" class="btnbar1"><input id="scanVirtualSwitchBtn" type="button" class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_scanPort"/>' style="margin-right:5px;"/></p>
		    <p style="float:left; padding:0; height:30px;" class="btnbar1"><input id="updateLoginInfoBtn" type="button" class="btn btn_dd2"  value='<icms-i18n:label name="res_btn_update_u_p"/>' onclick="updateLoginInfo()" style="margin-right:5px; margin-left:0;"/></p>
		    </td>

		</tr>
		</table>
		<div id="gridTable_div2" class="panel clear" style="margin-left:42px; margin-top: 10px; width:90%;">
		
		<table id="gridTable2"></table>
			<div id="gridPager2"></div></div>
	</form>
	
	</div>
	
	<!-- power虚拟机迁移，弹出的对话框 -->
	<div id="powerMessage" style="display: none;height:auto;text-align:right">
  	       <table border="0" class="pageFormContent" style="margin-left:8px">
  	       <tr style="height: 25px; margin-top:15px"><td style="width:150px; padding-left:10px;">
  	       <input type="hidden" name="powVid" id="powVid"/>
  	       <input type="hidden" name="tarHostId" id="tarHostId"/>
  	       <font color="red">*</font><icms-i18n:label name="res_l_comput_destLparId"/>:</td><td><input id="dest_lpar_id" type="text" class="textInput" style="width: 420px" value="" placeholder='<icms-i18n:label name="res_l_compute_example"/>：106'></td></tr>
  	       <tr style="height: 25px;"></tr>
  	       <tr style="height: 25px;"><td style="width:150px; padding-left:10px;">
  	       <font color="red">*</font><icms-i18n:label name="res_l_comput_fcMappings"/>:</td><td><input id="virtual_fc_mappings" type="text" class="textInput" style="width: 420px" value="" placeholder='<icms-i18n:label name="res_l_compute_example"/>：300//1/312/fcs1,301//2/312/fcs1,302//1/313/fcs3,303//2/313/fcs3'><a onclick="copyFcValue()" href="#" style="padding-left: 2px;color:#18a689;">复制格式</a></td></tr>
  	       <tr style="height: 25px;"></tr>
  	      <tr style="height: 25px;"><td style="width:150px; padding-left:10px;">
  	      <font color="red">*</font><icms-i18n:label name="res_l_comput_scsiMappings"/>:</td><td><input id="virtual_scsi_mappings" type="text" class="textInput" style="width: 420px" value="" placeholder='<icms-i18n:label name="res_l_compute_example"/>：200//1/206,201//2/206'><a onclick="copyScsiValue()" href="#" style="padding-left: 2px;color:#18a689;">复制格式</a></td></tr>
  	       <tr style="height: 25px;"></tr>
  	      <tr style="height: 25px;"><td style="width:150px; padding-left:10px;">
  	      <font color="red">*</font><icms-i18n:label name="res_l_comput_sMspId"/>:</td><td><input id="source_msp_id" type="text" class="textInput" style="width: 420px" value="" placeholder='<icms-i18n:label name="res_l_compute_example"/>：255'></td></tr>
  	       <tr style="height: 25px;"></tr>
  	      <tr style="height: 25px;"><td style="width:150px; padding-left:10px;">
  	      <font color="red">*</font><icms-i18n:label name="res_l_comput_dMspId"/>:</td><td><input id="dest_msp_id" type="text" class="textInput" style="width: 420px" value="" placeholder='<icms-i18n:label name="res_l_compute_example"/>：255'></td></tr>
  	       <tr style="height: 25px;"></tr>
  	       </table>
		   <input type="button" id="btn_power_cancel" title='<icms-i18n:label name="commom_btn_cancel"/>' value='<icms-i18n:label name="commom_btn_cancel"/>'  class="btn" onclick="closeMessage()" style="margin-right: 5px; margin-left:0;" />
  	       <input type="button" id="btn_power_sp" class="btn" onclick="savePowerInfo()" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' style=" margin-right: 5px;margin-left: 5px;" />
	</div>
	
  	<!-- 虚拟机信息 -->
	<div id="vm_div" style="display: none">
	<input type="hidden" id="dcId" name="dcId"></input>
	<form id="vm_form" action="" method="post">
		<table id="vmInfo" border="0" cellpadding="0" cellspacing="0" class="report" style="width: 97%">
			<input type="hidden" id="resourceType"></input>
			<input type="hidden" id="vmId"></input>
			<input type="hidden" id="duId"></input>
			<input type="hidden" id="dic_name"></input>
			<input type="hidden" id="vm_virtualTypeCode"></input>
			<input type="hidden" id="vm_platFormCode"></input>
			<input type="hidden" id="vmRunningState"></input>
			<input type="hidden" id="availableZoneId"></input>
			<tr>
				<th><icms-i18n:label name="res_l_comput_vmName"/>：</th><td width="30%" name="vm_name"><label id="vm_name"></label></td>
				<th><icms-i18n:label name="res_l_bl_hostName"/>：</th><td><label id="vm_device_name"></label></td>
			</tr>
			<tr>
				<th>CPU：</th><td><label id="vm_cpu"></label></td>
				<th><icms-i18n:label name="bm_l_device_mem"/>：</th><td><label id="vm_mem"></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_comput_vm_disk"/>：</th><td><label id="vm_disk"></label></td>
				<th><icms-i18n:label name="res_l_comput_mountDisk"/>：</th><td><label id="mountInfo"><a href="http://www.baidu.com.cn" ></a></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_comput_vm_state"/>：</th><td><label id=vm_dic_name></label></td>
				<th><icms-i18n:label name="res_l_comput_onlineTime"/>：</th><td><label id="onlineTime"></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_comput_runningState"/>：</th><td><label id="vm_state"></label></td>
				
			</tr>
			
			<tr>
				<th><icms-i18n:label name="bm_l_belongDc"/>：</th><td><label id="vm_datacenter_cname"></label></td>
				<th><icms-i18n:label name="res_l_bl_resPoolName"/>：</th><td><label id="vm_pool_name"></label></td>
			</tr>
			<tr>
			<tr>
			<tr>
				<%--<th><icms-i18n:label name="res_l_bl_cdpName"/>：</th><td><label id="vm_cdp_name"></label></td>--%>
				<th><icms-i18n:label name="res_l_bl_clusterName"/>：</th><td><label id="vm_cluster_name"></label></td>
				<th><icms-i18n:label name="bm_l_app_system"/>：</th><td><label id="vm_app_name"></label></td>
			</tr>	
			<tr>
				<th><icms-i18n:label name="res_l_comput_du"/>：</th><td><label id="vm_du_name"></label></td>
				<th><icms-i18n:label name="res_l_comput_loginName"/>：</th><td><label id="login_name"></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_comput_Name"/>：</th><td><label id="user_name"></label></td>
			</tr>
			<tr id="vm_table_id" class="report">	
			</tr> 
		</table>
		
		<table style="width:100%; text-align: left; margin-left: 38px; margin-top:10px; padding-bottom:5px; height:60px;" class="no_btnbar1">
			<tr style="text-align: left;">
				<td><a href="#" id="1" class="btnbar1" onclick="javascript:operation('1','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_open"/>' style="margin-left:0px; margin-right:12px;"/></a>
				<a href="#" id="8" class="btnbar1" onclick="javascript:operation('8','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_awaken"/>' style="margin-left:0px; margin-right:12px;"/></a>
				<a href="#" id="2" class="btnbar1" onclick="javascript:operation('2','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_close"/>' style="margin-left:0px; margin-right:12px;"/></a>
				<a href="#" id="6" class="btnbar1" onclick="javascript:operation('6','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_hang"/>' style="margin-left:0px; margin-right:12px;"/></a>
				<a href="#" id="5" class="btnbar1" onclick="javascript:operation('5','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_restart"/>' style="margin-left:0px; margin-right:12px;"/></a>
				<a href="#" id="7" class="btnbar1" onclick="javascript:operation('7','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_transfer"/>' style="margin-left:0px; margin-right:12px;" /></a>
				<a href="#" id="10" class="btnbar1" onclick="javascript:operation('10','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_remote_control"/>' style="margin-left:0px; margin-right:12px;" /></a><!-- kvm类型 -->
				<a href="#" id="11" class="btnbar1" onclick="javascript:operation('11','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_remote_control"/>' style="margin-left:0px; margin-right:12px;" /></a>
				<a href="#" id="12" class="btnbar1" onclick="javascript:operation('12','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_remote_control"/>' style="margin-left:0px; margin-right:12px;" /></a><!-- openstackVNC -->
				<a href="#" id="4" class="btnbar1" onclick="javascript:operation('4','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_snapshot"/>' style="margin-left:0px; margin-right:12px;" /></a>
				<a href="#" id="3" class="btnbar1" onclick="javascript:operation('3','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_newSnap"/>' style="margin-left:0px; margin-right:12px;"/></a>
				<a href="#" id="9" class="btnbar1" onclick="javascript:operation('9','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_shangeAppSys"/>' style="margin-left:0px; margin-right:12px;" onclick="showDu()" /></a>
				<a href="#" id="forcedRestart" class="btnbar1" onclick="javascript:operation('forcedRestart','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_forcedRestart"/>' style="margin-left:0px; margin-right:12px;"  /></a>
				<a href="#" id="mountVolume" class="btnbar1" onclick="javascript:operation('mountVolume','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_mountVolume"/>' style="margin-left:0px; margin-right:12px;" onclick="mountVolumeMethod()" /></a>
				<a href="#" id="unloadVolume" class="btnbar1" onclick="javascript:operation('unloadVolume','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value='<icms-i18n:label name="res_btn_device_unloadVolume"/>' style="margin-left:0px; margin-right:12px;" onclick="unloadVolumeMethod()" /></a>
				<a href="#" id="putVmName" class="btnbar1" onclick="javascript:operation('putVmName','null',null)" style=" text-decoration:none;"><input type="button" class="btn btn_dd2" value="修改名称" style="margin-left:0px; margin-right:1px;"/></a></td>
			</tr>
		</table>
		
	</form>
	</div>
  	<jsp:include page="scanVm.jsp"></jsp:include>
  	<jsp:include page="virtualSwitch.jsp"></jsp:include>
 	</div>
	<div id="gridTable_alternativeHost_div" style="display: none">
			<div id="gridTable_alternativeHostButtonDiv" class="panel clear" style="margin-left: 17px;margin-top: px;">
			<table id="gridTable_alternativeHost"></table>
			<table id="gridPager_alternativeHost"></table>
			</div>
	</div>
	<div id="gridTable_showDu_div" style="display: none" >
 			<div id="gridTable_showDuButtonDiv" class="panel clear" style="margin-left: 17px;margin-top: px;"> 
			<table id="gridTable_showDu"></table>
 			<table id="gridPager_showDu"></table> 
 			</div> 
	</div>
	<!-- 显示设备挂载的磁盘 -->
	<div id="gridTable_showMount_div" style="display: none" >
 			<div id="gridTable_showMonutDiv" class="panel clear" style="margin-left: 17px;margin-top: px;"> 
			<table id="gridTable_showMount"></table>
 			<table id="gridPager_showMount"></table> 
 			</div> 
	</div>
	<div id="putVmNameDiv" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataProjectVpcForm">
				<input type="hidden" id="projectVpcId" name="projectVpcId" />
				<input type="hidden" id="putVmId" name="putVmId" />
				<p>
					<i><font color="red">*</font>虚机名称：</i>
					<input type="text" name="vmName" id="vmName" style="vertical-align:middle;" class="textInput" />
				</p>
			<p class="winbtnbar btnbar1" style="text-align:right; width:265px; margin-bottom:20px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeViews('putVmNameDiv')" style="margin-right: 1px">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="putVmName()" style="margin-right: 0px">
			</p>
		</form>
	</div>	
</div>

<!-- 集群管理主机时，需要输入用户名和密码 -->
	<div id="loginInfoDiv" class="pageFormContent" style="display: none">
		<p style="width: 100%">
			<i><font color="#FF0000">*</font><icms-i18n:label name="res_l_comput_userName"/>:</i><input type="text" id="u_username" class="textInput" />
		</p>
		<p style="width: 100%">
			<i><font color="#FF0000">*</font><icms-i18n:label name="res_l_comput_password"/>:</i><input type="password" id="u_password" class="textInput" />
		</p>			
		<p style="width:288px; margin-right:40px;" id="operation" class="btnbar1" >
			<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="commom_btn_cancel"/>' title='<icms-i18n:label name="commom_btn_cancel"/>' onclick="closeDiv('loginInfoDiv')" style="margin-right: 5px;"/>
			<input type="button" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' class="btn btn_dd2 btn_dd3" onclick="saveLoginInfoBtn()"/>
		</p>
	</div>