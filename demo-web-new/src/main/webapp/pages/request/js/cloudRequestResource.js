/**
 * 初始化表格
 */
var _height = 260;
$(document).ready(function() {
    attrTableInit();
	initMainGrid();
	//自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
    });
});

function showHostList(){
	var vmId = $("#vmId").val();
	alertHost();
}

function alertHost(){
	var rrinfoId = $("#rrinfoId").val();
	var hostId = $("#hostId").val();
	openDialog('gridTable_alternativeHost_div',i18nShow('my_req_choose_host'), 900, 450);
	$("#gridTable_alternativeHostButtonDiv").show();
	$("#gridTable_alternativeHost").jqGrid().GridUnload("gridTable_alternativeHost");
	$("#gridTable_alternativeHost").jqGrid({
		url : ctx+"/resmgt-common/device/getHost.action",
		rownumbers : false,
		datatype : "json",
		mtype : "post",
		multiselect : false,
		postData : {"id":rrinfoId,"hostId":hostId},
		height : 310,
		autowidth : true,
		colModel : [{
			name : "id",
			index : "id",
			label : "物理机ID",
			width : 10,
			sortable : false,
			align : 'left',
			hidden : true
		},
		{
			name : "cmHostName",
			index : "cmHostName",
			label : i18nShow('my_req_host_name'),
			width : 80,
			sortable : true,
			align : 'left'
		},
		{
			name : "resPoolName",
			index : "resPoolName",
			label : i18nShow('my_req_host_res_pool'),
			width : 80,
			sortable : true,
			align : 'left'
		},
		{
			name : "clusterName",
			index : "clusterName",
			label : i18nShow('my_req_det_cluster'),
			width : 80,
			sortable : true,
			align : 'left'
		},
		{
			name : "hostCpuUsed",
			index : "hostCpuUsed",
			label : i18nShow('my_req_det_cpu_used'),
			width : 35,
			sortable : true
		},
		{
			name : "remainingCpu",
			index : "remainingCpu",
			label : i18nShow('my_req_det_cpu_free'),
			width : 35,
			sortable : true,
			align : 'left'
		},
		{
			name : "hostMemUsed",
			index : "hostMemUsed",
			label : i18nShow('my_req_det_mem_used'),
			width : 35,
			sortable : true
		},
		{
			name : "remainingMem",
			index : "remainingMem",
			label : i18nShow('my_req_det_mem_free'),
			width : 35,
			sortable : true,
			align : 'left'
		},
		{
			name : "op",
			index : "op",
			label : i18nShow('my_req_operate'),
			width : 20,
			sortable : false,
			align : 'left',
			hidden : false,
			formatter:function(cellVall, options, rowObject){
				var ret = "";
				//ret += "<a href='#' style=' text-decoration:none'onclick=updataVM('"+rowObject.cmvHostId+"','"+vId+"','"+vmName+"','"+rowObject.hostCpuUsed+"','"+rowObject.hostMemUsed+"','"+tp+"','"+rowObject.dataStoreName+"','"+rceCIp+"','"+rowObject.ip+"')>选择</a>"
				ret += "<a href='javascript:' style=' text-decoration:none'onclick=changeVM('"+rowObject.id+"','"+rowObject.cmHostName+"')>"+i18nShow('my_req_select')+"</a>"
				return ret; 
			}
		}],
		viewrecords : true,
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		height : _height,
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			repeatitems : false
		},
		pager : "#gridPager_alternativeHost",
		hidegrid : false
	});
}

function changeVM(hostId,cmHostName){
	$("#gridTable_alternativeHost_div").dialog("close");
	$("#hostName").val(cmHostName);
	$("#hostId").val(hostId);
}


// 首页主grid初始化
function initMainGrid() {
     var srTypeStr = "";
		if (srId != "" && srId != null && srId != undefined) {
			if ((srType == 'VS') || (srType == 'PS')) { // 供给
			    initSupplyGrid(srId);
			    if(srType == 'VS'){
					srTypeStr = i18nShow('my_req_service_supply_vs');
				}else if(srType == 'PS'){
					srTypeStr = i18nShow('my_req_service_supply_ps');
				}
			} else if (srType == 'VR' || srType == 'PR') { // 回收
			} else if (srType == 'VE') { // 扩容
			    initExpandGrid(srId);
			    srTypeStr = i18nShow('my_req_service_expand_ve');
			}
		} else {
			showTip(i18nShow('my_req_sr_id_null'));
		}
	document.getElementById("titlePage").innerHTML=srTypeStr;
}
//供给
function initSupplyGrid(srId) {
	$("#gridTable")
			.jqGrid(
					{
						url : 'request/base/queryBmSrRrinfoResoureList.action?srId=' + srId
								+ '',
						datatype : "json",
						mtype : "post", // 提交方式
						rownumbers : true,
						autowidth : true, // 是否自动调整宽度
						colNames : [i18nShow('my_req_sr_rrinfoId'), i18nShow('my_req_sr_confParameter'), i18nShow('my_req_sr_duName'),i18nShow('my_req_sr_vmdeviceId'), i18nShow('my_req_sr_vm_info'),
							i18nShow('my_req_sr_host_info'),i18nShow('cloud_service_platform'),i18nShow('opensatck_volume_name'),i18nShow('rm_platform_virture_type'),i18nShow('my_req_sr_storage'), i18nShow('my_req_sr_input_para') , i18nShow('my_req_sr_net'),i18nShow('my_req_operate')],
						colModel : [
								{
									name : 'rrinfoId',
									index : 'rrinfoId',
									sortable : false,
									align : "left",
									hidden : true
								},
								{
									name : 'confParameter',
									index : 'confParameter',
									width : 80,
									sortable : false,
									align : "left",
									hidden :true
								},
								{
									name : 'duName',
									index : 'duName',
									width : 80,
									sortable : false,
									align : "left",
									formatter : function (cellValue,options,rowObject){
										var duCname = cellValue ? cellValue : "";
										var duEname = rowObject.duEname ? rowObject.duEname : "";
										return i18nShow('my_req_sr_chinese')+duCname+"\n"+i18nShow('my_req_sr_english')+duEname;
					              }

								},
								{
									name : 'vmId',
									index : 'vmId',
									width : 50,
									hidden : true
								},
								{
									name : 'vmName',
									index : 'vmName',
									width : 90,
									height : 80,
									resizable : true,
									sortable : false,
									align : "left",
									formatter : function(cellValue, options,
											rowObject) {
										if (rowObject.vmName == null) {
											return "";
										} else {
											var html = new Array();
											html.push("<div id='dev_name"
													+ options.rowId + "'>"+i18nShow('my_req_sr_vm')+": "
													+ rowObject.vmName
													+ "</div>");
											html
													.push("<div>CPU: "
															+ rowObject.cpu
															+ "C</div>");
											html
													.push("<div>"+i18nShow('my_req_sr_mem')+": "
															+ rowObject.mem/1024
															+ "G</div>");

											/**
											 * if(actType != "view"){
											 * if(editTypeCode == "INE"){
											 * html.push("<div
											 * id='dev_btn"+options.rowId+"'
											 * align=\"center\">"); html.push("<a
											 * href=\"javascript:void(0)\"
											 * style=\"\"
											 * onclick=\"updateDeviceAfter('"+rowObject.vmName+"','"+options.rowId+"');\">修改虚机名</a>");
											 * html.push("</div>"); } }
											 */

											return html.join("");
										}
									}
								},
								{
									name : 'resPoolName',
									index : 'resPoolName',
									width : 100,
									sortable : false,
									align : "left",
									formatter : function(cellValue, options,
											rowObject) {
										if (rowObject.resPoolName == null) {
											return "";
										} else {
											var html = new Array();
											html.push("<div>"+i18nShow('my_req_sr_res_pool')+": "
													+ rowObject.resPoolName
													+ "</div>");
											/*html.push("<div>CDP: "
													+ rowObject.cdpName
													+ "</div>");*/
											html.push("<div>"+i18nShow('my_req_sr_cluster')+": "
													+ rowObject.clusterName
													+ "</div>");
											var platFormCode = rowObject.platFormCode;
											if(platFormCode != 'O'){
												html.push("<div>"+i18nShow('my_req_sr_host')+": "
														+ rowObject.deviceName
														+ "</div>");
											}
											/*html.push("<div>"+i18nShow('my_req_sr_LPAR')+": "
													+ rowObject.lparNamePrefix
													+ "</div>");*/
											/**
											 * if(actType != "view"){
											 * if(editTypeCode == "INE"){
											 * html.push("<div
											 * align='center'>"); html.push("<a
											 * href=\"javascript:void(0)\"
											 * style=\"\" ");
											 * html.push("onclick=\"selectDeviceForUpdate(\'"+rowObject.suId+"\',\'"+rowObject.vmId+"\',\'"+rowObject.deviceId+"\');\">");
											 * html.push("修改物理机</a>");
											 * html.push("</div>"); } }
											 */
											return html.join("");
										}
									}
								},
								{
									name : 'platFormCode',
									index : 'platFormCode',
									width : 40,
									sortable : false,
									align : "left",
									hidden : true
									
								},
								{
									name : 'volumeType',
									index : 'volumeType',
									width : 40,
									sortable : false,
									align : "left",
									hidden : true
									
								},
								{
									name : 'hostTypeCode',
									index : 'hostTypeCode',
									width : 40,
									sortable : false,
									align : "left",
									hidden : true
									
								},
								{
									name : 'datastoreName',
									index : 'datastoreName',
									width : 40,
									sortable : false,
									align : "left",
									formatter : function(cellValue, options,
											rowObject) {
										var platFormCode = rowObject.platFormCode;
										var volumeType = rowObject.volumeType
										if(platFormCode=='O'){
											var str = "";
											str += "<a href='javascript:;' style=' text-decoration:none' onclick=\"getHostVolumeType('" + rowObject.vmId + "')\" >"+i18nShow('my_req_det_select_volume_type')+"</a>";
											if(volumeType !=null && volumeType !=""){
												str +="\n"+volumeType;
											}
											return str;
										}else{
											if (rowObject.datastoreName == null) {
												return "";
											} else {
												var html = new Array();
												html.push("<span>"
														+ rowObject.datastoreName
														+ "</span><br/>");
												return html.join("");
											}
										}
										
									}
								},
								{
									align : "left",
									width : 30,
									formatter : function(cellValue, options,
											rowObject) {
										//新流程中， dcm审核后自定义参数不做修改
										if(attrRrinfoIds.indexOf(rowObject.rrinfoId) > 0) {
											return "<a href='javascript:;' style=' text-decoration:none' onclick=\"detailFromTable('" + options.rowId + "','" + rowObject.duId + "','" + rowObject.rrinfoId + "')\" >"+i18nShow('my_req_sr_watch')+"</a>";
										} else {
											return "";
										}
									}
								}, {
									name : 'vidept',
									index : 'vidept',
									width : 100,
									align : 'left',
									sortable : false,
									formatter : function(cellValue, options, rowObject) {
										var vmdevid = rowObject.vmId;
										return "<div id='" + vmdevid + "'>loading....</div>";
									}
								},{
									name : "option",
									index : "option",
									width : 30,
									sortable : false,
									align : "left",
									formatter : function(cellValue, options, rowObject){
										return "<a  style=' margin-right: 0px;text-decoration:none;' href='javascript:#'  title='' onclick=modifyVm('"+rowObject.vmId+"','"+((rowObject.vmName==null||rowObject.vmName=="")?"":rowObject.vmName)+"','"+((rowObject.deviceName==null||rowObject.deviceName=="")?"":rowObject.deviceName)+"','"+rowObject.rrinfoId+"','"+rowObject.hostId+"','"+((rowObject.lparNamePrefix==null||rowObject.lparNamePrefix=="")?"":rowObject.lparNamePrefix)+"','"+rowObject.platFormCode+"') >"+i18nShow('my_req_det_name_and_host')+"</a>" ;
									}
								} ],
						/*sortable : true,
						sortname : "duName",
						sortorder: 'asc', */
						viewrecords : true,
						rowNum : 10,
						rowList : [ 5, 10, 15, 20, 30 ],
						height : _height,
						prmNames : {
							search : "search"
						},
						jsonReader : {
							root : "dataList",
							records : "record",
							repeatitems : false
						},
						pager : "#gridPager",
						hidegrid : false,
						gridComplete : function() {
							getVmdeviceNet();
						}
					});
	     
	
	jQuery.validator.addMethod("vmNameCheck", function() { 
		var validateValue=true;
		var oldVmName = $("#oldVmName").val();
		var vmName= $("#vmName").val();
		if(vmName == oldVmName){
			validateValue=true;
		}else{
			$.ajax({
				type:'post',
				datatype : "json",
				data : {'vmName':vmName},
				url:ctx + '/request/base/checkVmName.action',
				async : false,
				success:(function(data){
//					alert(data.result);
					if(data.result != "Y"){
						validateValue=false;
					}		
				}),
			});
		}
		return validateValue;
		},
	"虚拟机名称已存在！"); 
	
	
	jQuery.validator.addMethod("vmIpCheck", function() { 
		var validateValue=true;
		var oldVmIp = $("#oldVmIp").val();
		var vmIp= $("#vmIp").val();
		if(vmIp == oldVmIp){
			validateValue=true;
		}else{
			$.ajax({
				type:'post',
				datatype : "json",
				data : {'newIp':vmIp,'oldIp':oldVmIp},
				url:ctx + '/request/base/validateVmIp.action',
				async : false,
				success:(function(data){
					if(data.result != "Y"){
						validateValue=false;
					}		
				}),
			});
		}
		return validateValue;
		},
	"此IP已被占用！");  
	
	//wmy---检验IP
	jQuery.validator.addMethod("checkIPV4", function(value, element) { 
		return this.optional(element) || /^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])$/i
		.test(value);     
		},
	"请正确填写IP地址");
	
	//---检验虚拟机名称
	jQuery.validator.addMethod("specialCharFilter", function(value, element) { 
		return this.optional(element) || /^[a-zA-Z][a-zA-Z0-9-]*[a-zA-Z0-9]$/g.test(value);       
		},
	"字母数字的组合，请以字母开头"); 
	
	//验证虚拟机名称
	$("#updateVmForm").validate({
		rules: {
			"vmName": {required : true,maxlength:30,specialCharFilter:true, vmNameCheck : true}
		},
		messages: {
			"vmName": {required : i18nShow('validate_data_required'),maxlength:"最大位数不能超过30",specialCharFilter:"字母数字组合，并且以字母开头", vmNameCheck : i18nShow('validate_data_remote')}
		},
		submitHandler: function() {
			updateOrSaveData();
		}
	});
	//验证虚拟机IP
	$("#updateVmIpForm").validate({
		rules: {
			"vmIp": {checkIPV4 : true}
		},
		messages: {
			"vmIp": {checkIPV4 : "请正确填写IP地址"}
		},
/*		rules: {
			"vmIp": {checkIPV4 : true,vmIpCheck : false}
		},
		messages: {
			"vmIp": {checkIPV4 : "请正确填写IP地址",vmIpCheck : "已被占用或不符合分配规则"}
		},
*/		submitHandler: function() {
			saveVmIpMethod();
		}
	});
}
//修改虚机名称和所在物理机对话框
function modifyVm(vmId,vmName,deviceName,rrinfoId,hostId,lparNamePrefix,platFormCode){
	$("#platFormCode").val(platFormCode);
	$("#oldVmName").val(vmName);
	$("#vmName").val(vmName);
	$("#lparNamePrefix").val(lparNamePrefix);
	$("#vmId").val(vmId);
	$("#rrinfoId").val(rrinfoId);
	$("#hostId").val(hostId);
	$("#hostName").val(deviceName);
	if(platFormCode == 'O'){
		$("#p_hostName").hide();
	}
	$("#updateVmDiv").dialog({
		autoOpen : true,
		modal:true,
		height:250,
		width:400,
		title:i18nShow('my_req_det_vm_update'),
		position: "middle"
})
$("#vmName-error").remove();
}
function submitForm()
{
	$("#updateVmForm").submit();
}
function submitIpForm()
{
	$("#updateVmIpForm").submit();
}
//保存虚机名称和所在物理机
function updateOrSaveData(){
	var platFormCode = $("#platFormCode").val();
	var vmName = $("#vmName").val();
	var lparNamePrefix = $("#lparNamePrefix").val();
	var vmId = $("#vmId").val();
	var hostId = $("#hostId").val();
	var data = {'vmName':vmName,'vmId':vmId,'hostId':hostId,'platFormCode':platFormCode}
	if(platFormCode=='PV'){
		data.lparNamePrefix =lparNamePrefix;
	}	

	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx + '/request/base/saveVmName.action',
		async : false,
		data:data,
		beforeSend: function () {
        	showTip("load");
        },
		success:(function(data){
			closeTip();
			showTip(i18nShow('tip_save_success'));   
			$("#updateVmDiv").dialog("close");
			$("#gridTable").jqGrid().trigger("reloadGrid");
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			closeTip();
			showTip(i18nShow('tip_save_fail')+XMLHttpRequest.responseText);
		} 
	});
}

//修改虚机IP对话框
function modifyIp(value){
	$("#vmIp-error").remove();
	var arr = new Array();
	arr=value.split(",");
	var ip = arr[0];
	var name = arr[1];
	var vmId = arr[3];
	//将之前的ip记录下来，做验证会用到
	$("#oldVmIp").val(ip);
	$("#vmIp").val(ip);
	$("#vmId").val(vmId);
	document.getElementById('vmIpName').innerHTML = name; 
	$("#vmIpName").val(name);
	$("#updateVmIpDiv").dialog({
		autoOpen : true,
		modal:true,
		height:220,
		width:380,
		title:i18nShow('com_update')+" "+name+"IP",
		position: "middle"
	})
/*
$(document).ready(function () {
	var ipValue = $("#vmIp").val();
    $.ajax({
        type: "POST",
        url:ctx + '/request/base/findVmVmVpName.action',
        data: {'oldVmVmVpName':ipValue},
        dataType: "json",
        success: function (data) {
        	$("#vmIp").autocomplete({
        		source: data,
        		select: function( event, ui ) {
        			$("#vmIp-error").remove();
        		    }
        	});
        }
    });
});*/
	//$("#vmIp-error").remove();
	/*var arr = new Array();
	arr=value.split(",");
	var ip = arr[0];
	var name = arr[1];
	var virtualTypeCode = arr[2];
	var vmId = arr[3];
	var useId = arr[4];
	var hostTypeId = arr[5];
	var secureAreaId = arr[6];
	var secureTireId = arr[7];
	//将之前的ip记录下来，做验证会用到
	$("#oldVmIp").val(ip);
	//$("#vmIp").val(ip);
	getNwPoolList(virtualTypeCode,useId,hostTypeId,secureAreaId,secureTireId,vmId);*/
}
function saveVmIpMethod(newIp,vmId,projectId,virtualTypeCode){
	var oldIp = $("#oldVmIp").val();
	//var newIp = $("#vmIp").val();
	$.ajax({
		type:'post',
		datatype : "json",
//		url:ctx + '/request/base/saveVmIpMethod.action',
		url:ctx + '/request/base/saveVmIpNewMethod.action',
		async : false,
		data: {'newIp':$("#vmIp").val(),'oldIp':oldIp,'vmId':$("#vmId").val(),'projectId':projectId,'virtualTypeCode':virtualTypeCode},
		beforeSend: function () {
        	showTip("load");
        },
		success:(function(data){
			if(data) {
				closeTip();
				showTip(data.result);
			} else {
				closeTip();
				showTip(i18nShow('tip_save_success'));   
				$("#updateVmIpDiv").dialog("close");
//			$("#ip_div").dialog("close");
				$("#gridTable").jqGrid().trigger("reloadGrid");
			}
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert(JSON.stringify(XMLHttpRequest));
			closeTip();
			showTip(i18nShow('tip_save_fail')+XMLHttpRequest.responseText);
		} 
	});
}

//修改虚机弹性IP对话框
function modifyFloatingIp(value){
//	$("#vmIp-error").remove();
	$("#vmFloatingIp").empty();
	var arr = new Array();
	arr=value.split(",");
	var ip = arr[0];
	var name = arr[1];
	var vmId = arr[2];
//	//将之前的ip记录下来，做验证会用到
	$("#oldVmFloatingIp").val(ip);
	var vmIp = $("#vmIp").val();
//	document.getElementById('vmIpName').innerHTML = name; 
//	$("#vmIpName").val(name);
	$.ajax({
        type: "POST",
        url:ctx + '/request/base/findUnUsedFloatingIp.action',
        data: {'floatingIp':vmIp},
        dataType: "json",
        success: function (data) {
        	var op0 = "<option value=''>"+i18nShow('com_select_defalt')+"...</option>";
    		$("#vmFloatingIp").append(op0);
        	for(var i = 0;i<data.length;i++){
        		var op = "<option>"+data[i].floatingIp+"</option>";
        		$("#vmFloatingIp").append(op);
        	}
        }
    });
	
	$("#updateVmFloatingIpDiv").dialog({
		autoOpen : true,
		modal:true,
		height:220,
		width:380,
		title:i18nShow('com_update')+" "+name,
		position: "middle"
})
}

function updateVmFloatingIp(){
	var oldIp = $("#oldVmFloatingIp").val();
	var newIp = $("#vmFloatingIp").val();
	if(newIp == ''){
		showTip(i18nShow('com_select_defalt')+" ip");
		return;
	}
	$.ajax({
        type: "POST",
        url:ctx + '/request/base/updateVmFloatingIp.action',
        data: {'oldIp':oldIp,'newIp':newIp},
        dataType: "json",
        beforeSend: function () {
        	showTip("load");
        },
        success: function (data) {
        	closeTip();
			showTip(i18nShow('tip_save_success'));   
			$("#updateVmFloatingIpDiv").dialog("close");
			$("#gridTable").jqGrid().trigger("reloadGrid");
        }
    });
	
}

function getVmdeviceNet() {
	var tmp_ids = $("#gridTable").getDataIDs();
	var rrinfoid = "";
	var vmdeviceid = "";
	for ( var t = 0; t < tmp_ids.length; t++) {
		var tmp_data = $("#gridTable").getRowData(tmp_ids[t]);
		rrinfoid = tmp_data.rrinfoId;
		vmdeviceid = tmp_data.vmId;
		if(vmdeviceid && vmdeviceid != "") {
			$.post(ctx+"/request/base/getVmNetIpOpenstack.action", {'rrinfoId' : rrinfoid,'vmdeviceId' : vmdeviceid}, function(data){
				var ips = "";
				for(var i=0 ; i<data.ipList.length ; i++) {
					if(data.ipList[i].rm_ip_type_name == '弹性IP'){
						ips += "<tr><td style='border: 0px solid #abc6d1;width:20%;'>"+data.ipList[i].rm_ip_type_name+"：</td><td style='border: 0px solid #abc6d1'><label id=''>"+data.ipList[i].ip+ "</label></td><td style='border: 0px solid #abc6d1;width:5%;'>"+"<!--<a  style=' margin-right: 10px;text-decoration:none;' href='javascript:#'  title='' onclick=modifyFloatingIp('"+data.ipList[i].ip+","+data.ipList[i].rm_ip_type_name+","+vmdeviceid+"') >"+i18nShow('com_update')+"</a>-->"+"</td></tr>";
					}else{
						$("#vmIp").val(data.ipList[i].ip);
						ips += "<tr><td style='border: 0px solid #abc6d1;width:20%;'>"+data.ipList[i].rm_ip_type_name+"：</td><td style='border: 0px solid #abc6d1'><label id=''>"+data.ipList[i].ip+ "</label></td><td style='border: 0px solid #abc6d1;width:5%;'>"+"<a  style=' margin-right: 10px;text-decoration:none;' href='javascript:#'  title='' onclick=modifyIp('"+data.ipList[i].ip+","+data.ipList[i].rm_ip_type_name+","+data.virtualTypeCode+","+data.id+","+data.ipList[i].useId+","+data.ipList[i].hostTypeId+","+data.ipList[i].secureAreaId+","+data.ipList[i].secureTireId+"') >"+i18nShow('com_update')+"</a>"+"</td></tr>";
					}
				}
				$("#" + data.id + "").html("<table id='ip_table' class='ip_table' cellspacing='0' cellpadding='0' style='border-collapse:collapse;width:100%'>" + ips + "</table>");
			});
		}
	};
}
//扩容
var dealNum = 0;
var sumNum = 0;
function initExpandGrid(srId) {
	dealNum = 0;
	sumNum = 0;
	$("#gridTable").jqGrid({
	    url : 'request/base/findExpandVirtualDeviceBySrId.action?srId=' + srId+ '',
		datatype : 'json',
		height:_height,	
		autowidth : true,
		colModel : [{
			name : 'hostId',
			index : 'hostId',
			label : '当前物理机Id',
			hidden : true,
			align : "left"
		},{
			name : 'duName',
			index : 'duName',
			label : i18nShow('my_req_sr_duName'),
			width : 70,
			sortable : false,
			align : "left",
			formatter : function (cellValue,options,rowObject){
				var duCname = cellValue ? cellValue : "";
				var duEname = rowObject.duEname ? rowObject.duEname : "";
				return i18nShow('my_req_sr_chinese')+duCname+"\n"+i18nShow('my_req_sr_english')+duEname;
          }
		},{
			name : 'deviceName',
			index : 'deviceName',
			label : i18nShow('my_req_sr_vmName'),
			width : 100,
			sortable : false,
			align : "left"
		},{
			name : 'oldConfParameter',
			index : 'oldConfParameter',
			label : i18nShow('my_req_sr_vm_conf_old'),
			width : 130,
			sortable : false,
			align : "left",
			formatter : function (cellValue,options,rowObject){
				var oldConfig="";
				//x86类型的虚拟机，显示磁盘配置
				oldConfig = "CPU="+rowObject.cpuOld+"C MEM="+rowObject.memOld+"M"+"<br>"
				if(rowObject.platformTypeCode =='X'){
					oldConfig += "SYS DISK="+rowObject.diskOld+"G "+"DATA DISK="+rowObject.externalDiskSum;
				}
				return oldConfig;
          }
		}, {
			name : 'parametersJson',
			index : 'parametersJson',
			label : i18nShow('my_req_sr_vm_conf_new'),
			width : 130,
			align : "left",
			sortable : false,
			formatter : function (cellValue,options,rowObject){
				var newConfig="";
				//x86类型的虚拟机，显示磁盘配置
				newConfig = "CPU="+cellValue.cpu+"C MEM="+cellValue.mem+"M"+"<br>"
				if(rowObject.platformTypeCode =='X'){
					newConfig += "SYS DISK="+rowObject.diskOld+"G "+"DATA DISK="+(rowObject.externalDiskSum+cellValue.dataDisk);
				}
				return newConfig;
          }
		},{
			name : 'oldHostName',
			index : 'oldHostName',
			label : i18nShow('my_req_det_current_host'),
			width : 130,
			sortable : false,
			align : "left"
		},{
			name : 'isEnough',
			index : 'isEnough',
			label : i18nShow('my_req_det_is_move'),
			width : 60,
			sortable : false,
			align : "left",
			formatter : function(cellValue, options, rowObject) {
				return getEnoughString(rowObject.isEnough);
			}
		},{
			name : 'newHostName',
			index : 'newHostName',
			label : i18nShow('my_req_det_move_name'),
			width : 90,
			sortable : false,
			align : "center",
			formatter : function(cellValue, options, rowObject) {
			    var newHostName = getFormatterText("/request/extend/getHostName.action",rowObject.newHostId);
				return getNewHostString(rowObject.rrinfoId,rowObject.deviceId,rowObject.oldHostId,rowObject.newHostId,newHostName,rowObject.isEnough,options.rowId);
			}
		},{
			name : 'platformTypeCode',
			index : 'platformTypeCode',
			label : 'platformTypeCode',
			width : 280,
			sortable : false,
			align : "left",
			hidden : true
		},{
			name : 'cpuOld',
			index : 'cpuOld',
			label : 'cpuOld',
			width : 280,
			sortable : false,
			align : "left",
			hidden : true
		},{
			name : 'memOld',
			index : 'memOld',
			label : 'memOld',
			width : 280,
			sortable : false,
			align : "left",
			hidden : true
		},{
			name : 'diskOld',
			index : 'diskOld',
			label : 'diskOld',
			width : 280,
			sortable : false,
			align : "left",
			hidden : true
		}],
		viewrecords : true,
		sortname : "srCode",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		height : _height,
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
}
//服务配置信息
function getServiceSetParams(rowObject,type){
      var data = "cpu=" + rowObject.newCpu
					+ ";mem=" + rowObject.newMem
					+ ";sysdisk=" + rowObject.sysDisk;
		var params = "cpu=" + (rowObject.newCpu == null ? "0":rowObject.newCpu )+"C"
					  + " "+i18nShow('my_req_sr_memery')+"=" + (rowObject.newMem == null ? "0":rowObject.newMem )+"M"
					  + " "+i18nShow('my_req_sr_disk')+"=" + (rowObject.sysDisk == null ? "0":rowObject.sysDisk )+"G";
		if(type == "data") {
			return data;
		}
		return params;
}
//翻译isEnough
function getEnoughString(isEnough) {
		if(isEnough) {
			sumNum ++;
			if(isEnough == "1") { // 本机资源充足
				dealNum ++;
				return i18nShow('com_N');
			} else if(isEnough == "0"){ // 本机资源不足
				return i18nShow('com_Y');
			} else if(isEnough == "2") { // 迁移机器资源充足，已锁定
				dealNum ++;
				return i18nShow('com_Y');
			}
		}
		return "";
}
/*function getEnoughString(isEnough) {
	if(isEnough) {
		sumNum ++;
		if(isEnough == "1") { // 本机资源充足
			dealNum ++;
			return "是";
		} else if(isEnough == "0"){ // 本机资源不足
			return "否";
		} else if(isEnough == "2") { // 迁移机器资源充足，已锁定
			dealNum ++;
			return "否";
		}
	}
	return "";
}*/
//选择物理机
function getNewHostString(rrinfoId, vmId, oldHostId, newHostId, newHostName, isEnough, n) {
	var hostInputHtml = '<div id="hostDiv_' + n + '">' + getHostLinkHtml(newHostId, newHostName) + '</div>';
	var value = hostInputHtml;
	if(isEnough != null && isEnough != "1") {
		var selectBtnHtml = '<a href="javascript:;" onclick="selectHost(\''+rrinfoId+'\', \''+vmId+'\', \''+oldHostId+'\', \''+newHostId+'\');">'+i18nShow('compute_res_selectHost')+'</a>';
		value = selectBtnHtml + value;//查看迁移物理机详细入口html
	}
	return value;
}
function getHostLinkHtml(hostId, hostName) {
	if(hostId)
		return hostName;//'<a href="javascript:;" onclick="viewHostDetail(\'' + hostId + '\');">' + hostName + '</a>';
	return '';
}
function viewHostDetail(hostId) {
   window.open('../rm/deviceHostMag.jsp?actType=detail&deviceId=' + hostId);
}
var selectedHostId = "";
function selectHost(rrinfoId, vmId, hostId, newHostId) {
	selectedHostId = newHostId;
	expandRrinfoId = rrinfoId;
	expandVmId = vmId;
	//window.open('devicePageDiv', '选择物理机', 920, 600);
	openDialog('devicePageDiv',i18nShow('compute_res_selectHost'), 930, 450);
	$("#deviceTable").jqGrid({
		width : 898,
		height: 300,
		datatype : "json",
		altRows : true,
		rownumbers:true,
		postData:{"rrinfoId":rrinfoId, "vmId":vmId, "hostId":hostId},
		ajaxGridOptions : {async:false},
		url : ctx+"/request/extend/findHostDeviceList.action",
		colNames : [i18nShow('my_req_select'),i18nShow('my_req_sr_host'), i18nShow('my_req_det_config'), i18nShow('my_req_det_config_mem'), i18nShow('my_req_det_config_cpu'), i18nShow('my_req_det_config_vm')],
		colModel : [
            {
            	sortable : false,
            	width : 20,
            	formatter : function(cellValue, options, rowObject) {
					return "<input type='radio' name='radio' value="+rowObject.deviceId+"></input>";
				}
            },
            {name : 'deviceName',index : 'deviceName',width : 60,sortable : false,align : "center",
            	formatter : function(cellValue, options, rowObject) {
    				//return "<a href=\"javascript:void(0)\" onclick=\"queryDeviceInfo(\'"+rowObject.deviceId+"\');\">"+rowObject.deviceName+"</a>";
    				return ""+rowObject.deviceName+"";
    			}
            },
            {width : 50,sortable : false,align : "center",
           	formatter : function(cellValue, options, rowObject) {
				return ""+rowObject.cpu + "C" + rowObject.mem +"G";
			}},
            {name : 'usedMem',index : 'usedMem',width : 50,sortable : false,align : "center"},
            {name : 'usedCpu',index : 'usedCpu',width : 50,sortable : false,align : "center"},
			{name : 'yfpVm',index : 'yfpVm',width : 50,sortable : false,align : "center"}
		],
		rowNum : 20,
		pager : '#pageNavHostPage',
		viewrecords : true,
		jsonReader : {
		    root : "dataList",
			records : "record",
			repeatitems : false
		}
	});
}

function openDialog(divId,title,width,height){
		$("#"+divId).dialog({
				autoOpen : true,
				modal:true,
				height:height,
				width:width,
				title:title,
		       // resizable:false
		})
}

function closeView(divId){
	$("#"+divId).dialog("close");
}
function updateDeviceInfo() {
	var newDeviceId = $( 'input:radio:checked' ).val();
	if(newDeviceId == undefined){
		showTip(i18nShow('tip_my_req_det_res_host1'));
		return false;
	} else if(newDeviceId == selectedHostId) {
		showTip(i18nShow('tip_my_req_det_res_host2'));
		return false;
	} else {
		$.post(ctx+"/request/extend/lockTargetHost.action", {rrinfoId : expandRrinfoId,vmId : expandVmId,targetHostId : newDeviceId},
			function(data){
				closeView('devicePageDiv');
				if(data.result == "success"){
					showTip(i18nShow('tip_save_success'),function(){
						//重新加载预分配资源请求页面
						$("#coludsrTable").clearGridData();
						initExpandGrid(srId);
					});
				} else {
					showTip(i18nShow('tip_save_fail'));
				}
		},"json");
	}
}

function getFormatterText(url,paramValue){
    var text = "";
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType:'text',
		url : ctx + url,//请求的action路径   
		data : {"paramName" : paramValue},
		error : function() {//请求失败处理函数   
			showTip(i18nShow('tip_req_fail'),null,"red");
		},
		success : function(data) { //请求成功后处理函数。  
		    text = data;
		}
    });
	return text;
}

//资源重新分配
function resourceAssign(){ 
	if(srType == "VS" || srType == "PS") {
		$.post(ctx + '/request/supply/resourceAssign.action', {srId:srId}, function (data) {
			  if(data.result == 'success'){
			    document.getElementById('reAssign').style.display='none';
			    showTipNoCancel(i18nShow('tip_my_req_det_res_success'),function(){
			    	location.reload();
			    });
			  }else {
			    showTip(data.result);
			  }
		 });
	} else if(srType == "VE") {
		$.post(ctx + '/request/extend/resourceAssign.action', {srId:srId}, function (data) {
			  if(data.result == 'success'){
			    document.getElementById('reAssign').style.display='none';
			    
			    showTipNoCancel(i18nShow('tip_my_req_det_res_success'),function(){
			    	location.reload();
			    });
			  }else {
			    showTip(data.result);
			  }
		 });
	}
}

//提交
function _submit1(){
	if(srType == "VE") {
		if(dealNum != sumNum) {
			showTip(i18nShow('tip_my_req_det_select_vm'));
			return;
		}
	}else if(srType == "PS"){
		 var ids = $("#gridTable").jqGrid('getDataIDs');
			for ( var t = 0; t < ids.length; t++) {
				 var hostTypeCode = jQuery('#gridTable').getCell(ids[t], "hostTypeCode");
				 var volumeType = jQuery('#gridTable').getCell(ids[t], "volumeType");
				 if(hostTypeCode == 'H'){
					 if(volumeType == null || volumeType ==""){
						 showTip(i18nShow('tip_my_req_det_select_volume')); 
						 return false;
					 }
				  }
				};
		     
	}
	
	$("#resourceBtn").attr("disabled",true);
     showTip("load");
	 $.post(ctx + '/request/base/resourceSubmit.action', {srId:srId, todoId:todoId}, function (data) {
		 closeTip();
		 if(data.result == 'success') {
    		 showTip(i18nShow('tip_my_req_det_sub'),function(){
//				window.opener.location.reload();
//				window.close();
                $("#resourceBtn").attr("disabled",false);
				history.go(-1);
			});
    	 } else {
    		 showError(data.result);
    		 $("#resourceBtn").attr("disabled",false);
    	 }
	 });
}

function getHostVolumeType(vmId){
		openDialog('getVolumeType_div',i18nShow('my_req_det_select_volume_type'), 440, 380);
		$("#getVolumeTypeDiv").show();
		$("#gridTable_getVolumeType").jqGrid().GridUnload("gridTable_getVolumeType");
		$("#gridTable_getVolumeType").jqGrid({
			url : ctx+"/resmgt-storage/device/getRmVolumeTypeByVmMsId.action",
			rownumbers : false,
			datatype : "json",
			mtype : "post",
			multiselect : false,
			postData : {"vmId":vmId},
			autowidth : true,
			colModel : [{
				name : "deviceId",
				index : "deviceId",
				label : "虚拟机ID",
				width : 5,
				sortable : false,
				align : 'left',
				hidden : true
			},{
				name : "id",
				index : "id",
				label : "主键id",
				width : 5,
				sortable : false,
				align : 'left',
				hidden : true
			},
			{
				name : "volumeType",
				index : "volumeType",
				label : i18nShow('my_req_sr_volume_type'),
				width : 15,
				sortable : true,
				align : 'left'
			},
			{
				name : "VM_MS_ID",
				index : "VM_MS_ID",
				label : "管理服务器id",
				width : 5,
				sortable : true,
				align : 'left',
				hidden : true
			},
			{
				name : "op",
				index : "op",
				label : i18nShow('my_req_operate'),
				width : 10,
				sortable : false,
				align : 'left',
				hidden : false,
				formatter:function(cellVall, options, rowObject){
					return "<a href='javascript:' style=' text-decoration:none'onclick=saveVolumeType('"+rowObject.id+"','"+rowObject.deviceId+"')>"+i18nShow('my_req_det_select_volume_type')+"</a>";
				}
			}],
			viewrecords : true,
			rowNum : 5,
			rowList : [ 5, 10, 15, 20, 30 ],
			height : _height,
			prmNames : {
				search : "search"
			},
			jsonReader : {
				root : "dataList",
				repeatitems : false
			},
			pager : "#gridPager_getVolumeType",
			hidegrid : false
		});
}

function saveVolumeType(volumeTypeId,vmId){
	showTip(i18nShow('tip_my_req_det_select_volume_type'),function(){
		var data = {'rmVolumeTypeId':volumeTypeId,'vmId':vmId};
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx + '/request/base/saveRmVolumeType.action',
			async : false,
			data:data,
			beforeSend: function () {
	        	showTip("load");
	        },
			success:(function(data){
				closeTip();
				showTip(i18nShow('tip_save_success'));   
				$("#getVolumeType_div").dialog("close");
				$("#gridTable").jqGrid().trigger("reloadGrid");
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				closeTip();
				showTip(i18nShow('tip_save_fail')+XMLHttpRequest.responseText);
			} 
		});
	});
}

function getNwPoolList(virtualTypeCode,useId,hostTypeId,secureAreaId,secureTireId,vmId){
	openDialog('nwPool_div',i18nShow('rm_network_res_pool'), 670, 400);//440, 380
	$("#nwPoolDiv").show();
	$("#gridTable_nwPool").jqGrid().GridUnload("gridTable_nwPool");
	$("#gridTable_nwPool").jqGrid({
		url : ctx+"/network/selectRmNwPoolList.action",
		rownumbers : false,
		datatype : "json",
		postData : {"virtualTypeCode":virtualTypeCode,"useId":useId,"hostTypeId":hostTypeId,"secureAreaId":secureAreaId,"secureTireId":secureTireId},
		mtype : "post",
		multiselect : false,
		autowidth : true,
		colModel : [{
			name : "poolName",
			index : "poolName",
			label : i18nShow('rm_network_res_pool_name'),
			width : '50%',
			sortable : false,
			align : 'left',
			hidden : false
		},
		{
			name : "op",
			index : "op",
			label : i18nShow('my_req_operate'),
			width : '50%',
			sortable : false,
			align : 'left',
			hidden : false,
			formatter:function(cellVall, options, rowObject){
				return "<a href='javascript:' style=' text-decoration:none'onclick=getIpList('"+rowObject.resPoolId+"','"+vmId+"')>"+i18nShow('my_req_select')+"</a>";
			}
		}],
		viewrecords : true,
		sortname : "id",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		height : _height,
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			repeatitems : false
		},
		pager : "#gridPager_nwPool",
		hidegrid : false
	});
}

function getIpList(nwPoolId,vmId){
	openDialog('ip_div','IP', 670, 400);
	$("#ipDiv").show();
	$("#gridTable_ip").jqGrid().GridUnload("gridTable_ip");
	$("#gridTable_ip").jqGrid({
		url : ctx+"/network/selectIPList.action",
		rownumbers : false,
		datatype : "json",
		mtype : "post",
		postData : {"nwResPoolId":nwPoolId},
		multiselect : false,
		autowidth : true,
		colModel : [{
			name : "ip",
			index : "ip",
			label : "IP",
			width : '50%',
			sortable : false,
			align : 'left',
			hidden : false
		},
		{
			name : "projectId",
			index : "projectId",
			label : "projectId",
			width : '50%',
			sortable : false,
			align : 'left',
			hidden : true
		},{
			name : "virtualTypeCode",
			index : "virtualTypeCode",
			label : "virtualTypeCode",
			width : '50%',
			sortable : false,
			align : 'left',
			hidden : true
		},
		{
			name : "op",
			index : "op",
			label : i18nShow('my_req_operate'),
			width : '50%',
			sortable : false,
			align : 'left',
			hidden : false,
			formatter:function(cellVall, options, rowObject){
				return "<a href='javascript:' style=' text-decoration:none'onclick=saveVmIpMethod('"+rowObject.ip+"','"+vmId+"','"+rowObject.projectId+"','"+rowObject.virtualTypeCode+"')>"+i18nShow('my_req_select')+"</a>";
			}
		}],
		viewrecords : true,
		sortname : "seq",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		height : _height,
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			repeatitems : false
		},
		pager : "#gridPager_ip",
		hidegrid : false
	});
}
