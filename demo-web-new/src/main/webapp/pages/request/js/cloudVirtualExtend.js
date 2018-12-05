var extendServiceObjMapList = new Array();
var extendParamList = [];
$(function() {
	extendParamList = [];
	//点击扩容按钮
	$("#service-extend-btn").click(function() {
		checkFlag = "first";
		validateBaseInfo();
	});
	
	//选择扩容应用服务器角色页面，点击下一步
	$("#next_extendDu").click(function() {
		nextExtendDu();
	});
	
	//选择扩容应用虚拟机页面，点击下一步
	$("#next_extendDev").click(function() {
		nextExtendDev();
	});
	
	//选择扩容应用虚拟机页面，点击上一步
	$("#pre_extendDev").click(function() {
		preExtendDev();
	});
	
	//选择扩容服务配置页面，点击上一步
	$("#pre_extendSet").click(function() {
		preExtendSet();
	});
	//添加一个应用服务器角色的云服务(扩容)
	$("#save_extend_btn").click(function(){
		extendAppDuService();
	});
	$("#extend_cancel_btn").click(function(){
		cancelRequest();
		extendParamList = [];
	});
	$("#extend_delete_btn").click(function(){
		deleteRequest();
	});
	//点击提交按钮
	$("#extend_submit_btn").click(function(){
		checkFlag = "second";
		validateBaseInfo();
		extendParamList = [];
	});
})

var dataDiskTemp;
$(function(){
	dataDiskTemp = $('#dataDiskVe').jRange({
		from: 0,
		to: 500,
		step: 10,
		format: '%s',
		width: 400,
		showLabels: false,
		//scale: [0,500],
		showScale: true
	});
})
function extendAppDuRequest() {
	// 校验是否已选择应用系统组件
	// 校验输入
	if(true) {
		// 弹出选择应用服务器角色页面
		$("#step_s").show();		
		$("#div_baseCase").slideUp('slow');
		$("#service-extend-div").slideUp('slow',function(){			
			$("#select_extendDu_div").show();
				$("#appExtendDu_Table").setGridWidth($("#radio_extendDu_div").width());
		});
		$("#extend_main_div").hide();
		
		var appId = $("#appId").val();
		getAppExtendDu(appId);
	}
}

//获取应用服务器角色
function getAppExtendDu(appId) {
	$("#appExtendDu_Table").jqGrid().GridUnload("appExtendDu_Table");
	$("#appExtendDu_Table").jqGrid(
		{
			url : ctx + '/request/extend/queryVEBmSrRrinfoList.action',
			datatype : "json",
			postData : {"appId":$("#appId").val(), "datacenterId":$("#datacenterId").val()},
			height : 300,
			autowidth : true,
			colModel : [
					{
						name : 'duId',
						index : 'duId',
						label :'Appdu_ID',
						width : 0,
						hidden : true,
						align : "left"
					},{
						name : 'select',
						index : 'select',
						label :i18nShow('my_req_select'),
						width : 2,
						sortable : false,
						align : "center",
						formatter : function(cellValue, options, rowObject) {
							return "<input type='radio' name='radioAppExtendDu' value='" + options.rowId + "'" + ">";
						}
					},{
						name : 'duName',
						index : 'duName',
						label:i18nShow('my_req_sr_duName'),
						width : 20,
						sortable : false,
						align : "left",
						formatter : function(cellValue,options,rowObject){
							var duCname = cellValue ? cellValue : "";
							var duEname = rowObject.duEname ? rowObject.duEname : "";
							return i18nShow('my_req_sr_chinese')+duCname+"\n"+i18nShow('my_req_sr_english')+duEname;
						}
					},{
						name : 'duType',
						index : 'duType',
						label:i18nShow('my_req_du_type'),
						width : 20,
						sortable : false,
						align : "left"
					}],
				rowNum : 10,
				rowList : [ 5, 10, 15, 20, 30 ],
				sortname : 'duId',
				sortorder : "asc",
				pager : '#AppdupageNav',
				viewrecords : true,
				jsonReader : {
					root : "dataList",
					records : "record",
					repeatitems : false
				}
		}).navGrid('AppdupageNav', cloud_view_btn);
}

//获取应用虚拟机
/*function getAppExtendDev(duId) {
	$("#appExtendDev_Table").jqGrid().GridUnload("appExtendDev_Table");
	$("#appExtendDev_Table").jqGrid(
		{
			url : ctx + '/request/extend/queryVmSrDeviceinfoList.action',
			datatype : "json",
			postData : {"duId":duId},
			height : 300,
			autowidth : true,
			colModel : [
					{
						name : 'deviceId',
						index : 'deviceId',
						label :'Device_ID',
						width : 0,
						hidden : true,
						align : "left"
					},{
						name : 'select',
						index : 'select',
						label :i18nShow('my_req_select'),
						width : 2,
						sortable : false,
						align : "center",
						formatter : function(cellValue, options, rowObject) {
							return "<input type='radio' name='radioAppExtendDev' value='" + options.rowId + "'" + ">";
						}
					},{
						name : 'deviceName',
						index : 'deviceName',
						label:i18nShow('my_req_sr_vmName'),
						width : 20,
						sortable : false,
						align : "left"
					},{
						name : 'cpu',
						index : 'cpu',
						label:i18nShow('my_req_cpuNum'),
						width : 20,
						sortable : false,
						align : "left"
					},{
						name : 'mem',
						index : 'mem',
						label:i18nShow('my_req_memNum'),
						width : 20,
						sortable : false,
						align : "left"
					},{
						name : 'disk',
						index : 'disk',
						label:i18nShow('my_req_sysDisk'),
						width : 20,
						sortable : false,
						align : "left"
					},{
						name : 'platformType',
						index : 'platformType',
						label: 'Platform_Type',
						width : 0,
						hidden : true,
						align : "left"
					}],
				rowNum : 10,
				rowList : [ 5, 10, 15, 20, 30 ],
				sortname : 'deviceId',
				sortorder : "asc",
				pager : '#AppdevpageNav',
				viewrecords : true,
				jsonReader : {
					root : "dataList",
					records : "record",
					repeatitems : false
				}
		}).navGrid('AppdevpageNav', cloud_view_btn);
}*/


function getAppExtendDev(duId) {
	$("#appExtendDev_Table").jqGrid().GridUnload("appExtendDev_Table");
	$("#appExtendDev_Table").jqGrid(
		{
			url : ctx + '/request/extend/queryVmSrDeviceinfoList.action',
			datatype : "json",
			postData : {"duId":duId},
			height : 300,
			autowidth : true,
			colModel : [
					{
						name : 'deviceId',
						index : 'deviceId',
						label :'deviceId',
						width : 0,
						hidden : true,
						align : "left"
					},
					{
						name : 'duId',
						index : 'duId',
						label :'Appdu_ID',
						width : 0,
						hidden : true,
						align : "left"
					},{
						name : 'select',
						index : 'select',
						label :i18nShow('my_req_select'),
						width : 5,
						sortable : false,
						align : "center",
						formatter : function(cellValue, options, rowObject) {
							return "<input type='radio' name='radioAppExtendDev' value='" + options.rowId + "'" + ">";
						}
					},{
						name : 'duName',
						index : 'duName',
						label:i18nShow('my_req_sr_duName'),
						width : 20,
						sortable : false,
						align : "left",
						hidden : true,
						formatter : function(cellValue,options,rowObject){
							var duCname = cellValue ? cellValue : "";
							var duEname = rowObject.duEname ? rowObject.duEname : "";
							return i18nShow('my_req_sr_chinese')+duCname+"\n"+i18nShow('my_req_sr_english')+duEname;
						}
					},{
						name : 'deviceName',
						index : 'deviceName',
						label: i18nShow('my_req_sr_vmName'),
						width : 20,
						sortable : false,
						align : "left"
					},{
						name : 'duType',
						index : 'duType',
						label:i18nShow('my_req_du_type'),
						width : 20,
						sortable : false,
						align : "left",
						hidden : true
					},{
						name : 'serviceId',
						index : 'serviceId',
						label:'Service_ID',
						width : 20,
						sortable : false,
						hidden : true,
						align : "left"
					},{
						name : 'serviceName',
						index : 'serviceName',
						label:i18nShow('my_req_service_list'),
						width : 33,
						sortable : false,
						align : "left"
					},{
						name : 'cpu',
						index : 'cpu',
						label:'cpu',
						width : 0,
						hidden : true,
						sortable : false,
						align : "left"
					},{
						name : 'mem',
						index : 'mem',
						label:i18nShow('my_req_sr_mem')+'(M)',
						width : 0,
						hidden : true,
						sortable : false,
						align : "left"
					},{
						name : 'disk',
						index : 'disk',
						label:'disk',
						hidden : true,
						sortable : false
					},{
						name : 'externalDiskSum',
						index : 'externalDiskSum',
						label:'externalDiskSum',
						hidden : true,
						sortable : false
					},{
						name : 'config',
						index : 'config',
						label:i18nShow('my_req_sr_vm_conf_old'),
						hidden : false,
						sortable : false,
						width : 80,
						formatter : function(cellValue,options,rowObject){
							var config = "CPU="+rowObject.cpu+" " + "MEM="+rowObject.mem+"M "+"<br>";
							if(rowObject.platformTypeCode =='X'){
								config += "SYS DISK="+(parseInt(rowObject.disk))+"G ";
								var dataDiskSum = 0;
								if(rowObject.externalDiskSum !== null && rowObject.externalDiskSum !== undefined && rowObject.externalDiskSum !== ''){
									dataDiskSum = rowObject.externalDiskSum;
								}
								config += "DATA DISK="+parseInt(dataDiskSum)+"G";
							}
							return config;
						}
					},{
						name : 'platformType',
						index : 'platformType',
						label : '平台类型ID',
						hidden : true,
						sortable : false
					},
					{
						name : 'platformTypeCode',
						index : 'platformTypeCode',
						label : '平台类型编码',
						hidden : true,
						sortable : false
					}],
				rowNum : 10,
				rowList : [ 5, 10, 15, 20, 30 ],
				sortname : 'deviceId',
				sortorder : "asc",
				pager : '#AppdevpageNav',
				viewrecords : true,
				jsonReader : {
					root : "dataList",
					records : "record",
					repeatitems : false
				}
		}).navGrid('AppdevpageNav', cloud_view_btn);
}
//读取所有可用的服务目录
function ServiceExtendedTableInit() {
	$("#serviceExtendedTable").jqGrid().GridUnload("serviceExtendedTable");
	$("#serviceExtendedTable").jqGrid(
		{
			datatype : 'local',
			height : 180,
			autowidth : true,
			colModel : [
					{
						name : 'rrinfoId',
						index : 'rrinfoId',
						label : '资源请求Id',
						hidden : true,
						align : "left"
					},{
						name : 'deviceId',
						index : 'deviceId',
						label : '虚拟机Id',
						hidden : true,
						align : "left"
					},{
						name : 'deviceName',
						index : 'deviceName',
						label : '虚拟机名称',
						width : 10,
						align : "left"
					},{
						name : 'duId',
						index : 'duId',
						label:'应用服务器角色Id',
						width : 0,
						hidden : true,
						align : "left"
					},{
						name : 'serviceId',
						index : 'serviceId',
						label :'服务目录Id',
						width : 0,
						hidden : true,
						align : "left"
					},{
						name : 'duName',
						index : 'duName',
						label:i18nShow('my_req_sr_duName'),
						width : 8,
						sortable : false,
						align : "left"
					},{
						name : 'serviceName',
						index : 'serviceName',
						label　: i18nShow('my_req_service_list'),
						width : 10,
						sortable : false,
						align : "left"
					},{
						name : 'parameterSelfOld',
						index : 'parameterSelfOld',
						label : i18nShow('my_req_sr_vm_conf_old'),
						width : 10,
						sortable : false,
						hidden : false,
						align : "left"
					},{
						name : 'parameterSelfDef',
						index : 'parameterSelfDef',
						label : i18nShow('my_req_sr_vm_conf_new'),
						width : 10,
						sortable : false,
						align : "left"
					},{
						name : 'parameter_hidden',
						index : 'parameter_hidden',
						label : '隐藏的新服务配置',
						width : 10,
						sortable : false,
						hidden : true,
						align : "left"
					},{
						name : 'serviceNum',
						index : 'serviceNum',
						label : '服务套数',
						width : 5,
						hidden : true,
						align : "left"
					},{
						name : 'vmNum',
						index : 'vmNum',
						label : '虚拟机数量',
						width : 8,
						sortable : false,
						hidden : true,
						align : "left", 
						formatter : function(cellValue, options, rowObject) {
							 if(actionType == 'update'&&editclass!='3'){
							return cellValue+ " 台 "+"<a onclick='showVmNum("+cellValue+","+options.rowId+")'> <span style='color:red'>"+i18nShow('com_update')+"</span></a>";
							 }
						   else {
							   return cellValue+ " 台 ";
							   
						   }
						}
						
						
					},{
						name : 'attr_operate',
						index : 'attr_operate',
						label : '参数',
						width : 5,
						sortable : false,
						hidden : true,
						align : "left",
						formatter : function(cellValue, options, rowObject) {
							return "";
						}
					},{
						name : 'operate',
						index : 'operate',
						label : i18nShow('my_req_operate'),
						width : 8,
						sortable : false,
						hidden : isHiddenFlag,
						align : "center",
						formatter : function(cellValue, options, rowObject) {
							return"<a title='' href='javascript:#' style='margin-right: 10px;text-decoration:none;'  onclick=\"deleteFromExtendTable(" + options.rowId + ",'" + rowObject.deviceId + "')\" >"+i18nShow('com_delete')+"</a>";
							//return "<input type='button' class='btn_del_s' title='删除'" + " onclick=\"deleteFromExtendTable(" + options.rowId + ",'" + rowObject.duId + "')\"/>";
						}
					}],
			rowNum : 10,
			rowList : [ 5, 10, 15, 20, 30 ],
			pager : '#pageNav_extend',
			viewrecords : true,
			jsonReader : {
				root : "dataList",
				records : "record",
				repeatitems : false
			}
		}); 
}

//在应用服务器角色页面，点击下一步
function nextExtendDu() {
	var du_row_id = getCheckedRadio("radioAppExtendDu");
	if (du_row_id == -1) {
		showError(i18nShow('error_select_du'));
		return;
	}
	var du_id = jQuery('#appExtendDu_Table').getCell(du_row_id, "duId");
	// 关闭选择应用服务器角色页面
	$("#select_extendDu_div").hide();
	// 弹出选择服务配置页面
	$("#select_extendDev_div").show();
	getAppExtendDev(du_id);
	switchStep_s(2);
}

//在应用服务器角色页面，点击下一步
function nextExtendDev() {
	// 校验必须选择虚拟机
	var dev_row_id = getCheckedRadio("radioAppExtendDev");
	if (dev_row_id == -1) {
		showError(i18nShow('error_select_dev'));
		return;
	}
	//获取之前的服务配置，自动选择原服务配置
	var elCpu = jQuery('#appExtendDev_Table').getCell(dev_row_id, "cpu");
	var elMem = jQuery('#appExtendDev_Table').getCell(dev_row_id, "mem");
	var elHardDisk = jQuery('#appExtendDev_Table').getCell(dev_row_id, "disk");
	var deviceId = jQuery('#appExtendDev_Table').getCell(dev_row_id, "deviceId");
	var platformType = jQuery('#appExtendDev_Table').getCell(dev_row_id, "platformType");
	flatSelectByValue("cpu",elCpu);
	flatSelectByValue("mem",elMem);
	//扩容时，系统盘、浮动ip开关、是否共享开关   隐藏
	$("#share_Tr").hide();
	$("#float_Tr").hide();
	$("#sysDisk_Tr").show();
	//platformType：POWER平台（2），OpenstackX86平台（4），以上两种平台类型，扩容时不显示数据盘磁盘选项
	if(platformType == "2" || platformType == "4") {
		$("#hardDisk_TrVe").hide();
	} else {
		$("#hardDisk_TrVe").show();
	}
	if (duList[deviceId] == 1) {
		showError(i18nShow('error_re_select_dev'));
	} else {
		// 关闭选择应用服务器角色页面
		$("#select_extendDev_div").hide();
		// 弹出选择服务配置页面
		$("#select_extendSet_div").show();
		switchStep_s(3);
	}
}

//选择硬件套餐页面，点击上一步
function preExtendSet() {
	// 关闭选择服务配置页面
	$("#select_extendSet_div").hide();
	// 弹出选择应用服务器角色页面
	$("#select_extendDev_div").show();
	switchStep_s(2);
	//$("#select_extendDu_div").dialog("option", "title", "选择应用服务器角色");
}

//选择虚拟机页面，点击上一步
function preExtendDev() {
	// 关闭选择服务配置页面
	$("#select_extendDev_div").hide();
	// 弹出选择应用服务器角色页面
	$("#select_extendDu_div").show();
	switchStep_s(1);
}


//验证扩容配额
function validateQuotaVE(platformTypeCode,code,value,duId){
	var flag = false;
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx +"/quota/validateQuotaVE.mvc",  
		data : {
			"platformTypeCode" : platformTypeCode,
			"reqValue": parseInt(value),
			"code":code,
			"duId":duId
				},
		error : function() {// 请求失败处理函数
			showError();
		},
		success : function(data) { 
			if(data.result=="true"){
				flag = true;
			}else{
				showTip(data.result);
				flag = false;
			}
		}
	});
	return flag;
}


//保存添加一个应用服务器角色的云服务扩容，更新显示表格
function extendAppDuService() {
	// 获取应用服务器角色id
	var cpu, mem, dataDisk;
	var appdu_row_id = getCheckedRadio("radioAppExtendDev");
	var du_id = jQuery('#appExtendDev_Table').getCell(appdu_row_id, "duId");
	var du_name = jQuery('#appExtendDev_Table').getCell(appdu_row_id, "duName");
	//获取设备名称
	var deviceId = jQuery('#appExtendDev_Table').getCell(appdu_row_id, "deviceId");
	var deviceName = jQuery('#appExtendDev_Table').getCell(appdu_row_id, "deviceName");
	// 获取服务目录数据
	var service_id = jQuery('#appExtendDev_Table').getCell(appdu_row_id, "serviceId");
	var service_name = jQuery('#appExtendDev_Table').getCell(appdu_row_id, "serviceName");
	//原虚拟机配置的cpu、mem、disk
	var formerCPU = jQuery('#appExtendDev_Table').getCell(appdu_row_id, "cpu");
	var formerMEM = jQuery('#appExtendDev_Table').getCell(appdu_row_id, "mem");
	var formerEld = jQuery('#appExtendDev_Table').getCell(appdu_row_id, "disk");
	//原来的总磁盘=原挂载的数据盘+系统盘
	var externalDiskSumStr = jQuery('#appExtendDev_Table').getCell(appdu_row_id, "externalDiskSum");
	var externalDiskSum = parseInt(externalDiskSumStr ? externalDiskSumStr : 0);
	var platformTypeCode = jQuery('#appExtendDev_Table').getCell(appdu_row_id, "platformTypeCode");
	
	// 获取页面先择的服务配置和自定义参数
	var set_row_id = getCheckedRadio("radioExtendServiceSet");
	cpu = $("#cpu").val();
	mem = $("#mem").val();
	dataDisk = parseInt($("#dataDiskVe").val() ? $("#dataDiskVe").val() : 0);
	//配置中选择"不增加磁盘"或为openstack虚拟机，
	if(dataDisk == 0 || platformTypeCode =='O'){
		//判断cpu和内存,原服务配置和新服务配置相同，提示不能扩容
		if(cpu == formerCPU && mem == formerMEM){
			showError(i18nShow('tip_my_req_extend_msg'));
			return;	
		}
	}
	//配额相关验证开始
	var needValidateQuota = needValidateQuotaFlag();
	if(needValidateQuota == true){
		//获取差值，差值大于0，才去验证配额
		var diffCpu = parseInt(cpu)-parseInt(formerCPU);
		if(diffCpu > 0){
			var cpuQuotaVali = validateQuotaVE(platformTypeCode,"cpu",diffCpu,du_id);
			if(cpuQuotaVali != true) return ;
		}
		var diffMem = parseInt(mem)-parseInt(formerMEM);
		if(diffMem > 0){
			var memQuotaVali = validateQuotaVE(platformTypeCode,"mem",diffMem,du_id);
			if(memQuotaVali != true) return ;
		}
		if(dataDisk != 0 && platformTypeCode =='X'){
			var diskQuotaVali = validateQuotaVE(platformTypeCode,"storage",dataDisk,du_id);
			if(diskQuotaVali != true) return ;
		}
	}
	//配额相关验证结束
	
	$("#select_extendSet_div").hide();
	// 向汇总表格中添加一行
	var length = $("#serviceExtendedTable").jqGrid('getDataIDs').length;
	//新服务配置
	var diskStr =  "SYS DISK="+formerEld+"G DATA DISK="+(dataDisk+externalDiskSum)+"G";
	var newServiceConfig = "CPU=" + cpu + " "+"MEM=" + mem + "M"  +"<br>"+ diskStr;
	console.log("newServiceConfig:"+newServiceConfig);
	//隐藏的页面选择的服务配置
	var parameter_hidden = cpu+","+mem+","+dataDisk;
	//老服务配置
	var formerEldStr =  "SYS DISK="+formerEld+"G DATA DISK="+(externalDiskSum)+"G"; 
	var oldServiceConfig = "CPU=" + formerCPU + "MEM=" + formerMEM + " M" +"<br>"+ formerEldStr;
	console.log("oldServiceConfig:"+oldServiceConfig);
	var diskPlacementType_k = $("#diskPlacementType_k").val();
	var jsobj = {
		duId : du_id,
		deviceId : deviceId,
		serviceId : service_id,
		deviceName : deviceName,
		duName : du_name,
		serviceName : service_name,
		parameterSelfDef : newServiceConfig,
		parameter_hidden : parameter_hidden,
		parameterSelfOld : oldServiceConfig
	};
	extendParamList.push({
		"deviceId":deviceId,
		"cpu":parseInt(cpu),
		"mem":parseInt(mem),
		"dataDisk":parseInt(dataDisk),
		"platformTypeCode":platformTypeCode,
		"duId":du_id,
		"serviceId" : service_id,
		"diskPlacementType":diskPlacementType_k
		
	})
	$("#serviceExtendedTable").jqGrid("addRowData", length + 1, jsobj);
	duList[deviceId] = 1;
	extendServiceObjMapList.push(jsobj);
	GoBackToMain1();
}

//删除所在行的服务器角色扩容云服务
function deleteFromExtendTable(id, deviceId) {
	showTip(i18nShow('tip_delete_confirm'),function(){
		$("#serviceExtendedTable").jqGrid("delRowData", id);
		duList[deviceId] = 0;
		for ( var k = 0; k < extendServiceObjMapList.length; k++) {
			if (extendServiceObjMapList[k].deviceId == deviceId) {
				extendServiceObjMapList.splice(k, 1);
				break;
			}
		}
		for ( var i = 0; i < extendParamList.length; i++) {
			if (extendParamList[i].deviceId == deviceId) {
				extendParamList.splice(i, 1);
				break;
			}
		}
	});
}

//点击提交按钮，提交服务请求
function submitExtendRequest() {
	if(extendServiceObjMapList.length == 0) {
		showError(i18nShow('error_select_du'));
		return;
	}
	// 校验无误，提交
	var bmSr = {
			"srId" : $("#srId").val(),
			"srCode" : $("#srCode").val(),
			"srTypeMark" : $("#srTypeMark").val(),
			"appId" : $("#appId").val(),
			"datacenterId" : $("#datacenterId").val(),
			"startTimeStr" : $("#serviceBeginTimes").val(),
			"endTimeStr" : $("#serviceEndtimes").val(),
			"summary" : $("#summary").val(),
			"creatorId" : $("#userId").val()
		};
	var temp = [];
	for(var i =0 ;i<extendParamList.length;i++){
		temp.push({
			"parametersJson":JSON.stringify(extendParamList[i])
		})
	}
	var virtualSupplyVo = {
		"bmSr" : bmSr,
		"rrinfoList" : temp
	};
	$("#extend_submit_btn").attr("disabled", "disabled");
	$("#extend_submit_btn").removeClass("btn").addClass('btn_disabled');
	if (actionType == 'new') {
		showTip("load");
		$.post(ctx + "/request/extend/saveVirtualExtend.action", {
			jsonData : JSON.stringify(virtualSupplyVo),
			jsonClass : "com.git.cloud.request.model.vo.VirtualSupplyVo",
			parametersJson:JSON.stringify(temp)
		}, function(data) {
			if(data.result!=null && data.result=="success"){
				showTip(i18nShow('tip_my_req_recycle_msg4') + data.message);
				closeTip();
				initCloudRequestList();
			}else{
				showError(i18nShow('tip_save_fail')+data.result);
				$("#extend_submit_btn").attr("disabled", true);
				$("#extend_submit_btn").removeClass("btn_disabled").addClass('btn');
				closeTip();
			}
		}).error(function() {
			showError(i18nShow('tip_error'),null,"red");
			$("#extend_submit_btn").attr("disabled", true);
			$("#extend_submit_btn").removeClass("btn_disabled").addClass('btn');
			closeTip();
		});
		
	} else if (actionType == 'update') {
		showTip("load");
		$.post(ctx + "/request/extend/saveVirtualExtend.action", {
			jsonData : JSON.stringify(virtualSupplyVo),
			jsonClass : "com.git.cloud.request.model.vo.VirtualSupplyVo",
			todoId : todoId
		}, function(data) {
			if (data.result!=null && data.result=="success") {
				showTip(i18nShow('tip_op_success'), function () {
					history.go(-1);
				});
			} else {
				showError(i18nShow('tip_save_fail') + data.result);
				$("#extend_submit_btn").attr("disabled", true);
				$("#extend_submit_btn").removeClass("btn_disabled").addClass('btn');
				closeTip();
			}
		}).error(function() { 
			showError(i18nShow('tip_error'),null,"red");
			$("#extend_submit_btn").attr("disabled", true);
			$("#extend_submit_btn").removeClass("btn_disabled").addClass('btn');
			closeTip();
		});
	}
}

function getExtendRrinfoList() {
	var rrinfoList = new Array();
	var RrVmRefList = new Array();
	var ids = $("#serviceExtendedTable").jqGrid('getDataIDs');
	var service_id,du_id,du_name,cpu,mem,dataDisk,vmNum,device_id;
	var parameterStr;
	
	for ( var i = 0; i < ids.length; i++) {
		service_id = jQuery('#serviceExtendedTable').getCell(ids[i], "serviceId");
		du_id = jQuery('#serviceExtendedTable').getCell(ids[i], "duId");
		du_name = jQuery('#serviceExtendedTable').getCell(ids[i], "duName");
		device_id = jQuery('#serviceExtendedTable').getCell(ids[i], "deviceId");
		parameter_hidden = jQuery('#serviceExtendedTable').getCell(ids[i], "parameter_hidden");
		var a = new Array(); 
		a = parameter_hidden.split(",");
		cpu = a[0];
		mem = a[1];
		dataDisk = a[2];
		var RrVmRefPo = {
			"deviceId" : device_id
		};
		RrVmRefList.push(RrVmRefPo);
		
		var serviceObj = {
			"serviceId" : service_id,
			"duId" : du_id,
			"duName" :du_name,
			"cpu" : cpu,
			"mem" : mem,
			"sysDisk" : dataDisk,
			"vmNum" : vmNum
		};
		rrinfoList.push(serviceObj);			
		}
	
	return rrinfoList;
}

function showExtendRequestInfo(srId){
	// 获取服务申请详细信息
	$.post(ctx + '/request/extend/findVirtualExtendById.action', { "srId":srId}, function (data) {
	var rrinfoList = data.rrinfoList;
	extendServiceObjMapList = rrinfoList;
	for(var j = 0; j < rrinfoList.length; j++){
		//本次扩容申请的cpu、mem、disk
		var cpu = rrinfoList[j].cpu;
		var mem = rrinfoList[j].mem;
		var sysdisk = rrinfoList[j].sysDisk;
		//虚拟机扩容前的配置（cpu、mem、disk）
		var cpuOld = rrinfoList[j].cpuOld;
		var memOld = rrinfoList[j].memOld;
		var diskOld = rrinfoList[j].diskOld;
		var platformTypeCode = rrinfoList[j].platformTypeCode;
		//拼接虚拟机的原服务配置和新服务配置
		
		var oldConfParameter = "CPU="+cpuOld+" MEM="+memOld+"M";
		var confParameter = "CPU="+cpu+" MEM="+mem+"M";
		if(platformTypeCode == 'X'){
			oldConfParameter += " DISK="+diskOld+"G";
			confParameter += " DISK="+(parseInt(sysdisk)+parseInt(diskOld))+"G"
		}
		var vm_num = rrinfoList[j].vmNum;
		var parameterStr = cpu+","+mem+","+sysdisk+","+vm_num;
		
		var jsobj = {
				rrinfoId : rrinfoList[j].rrinfoId,
				duId : rrinfoList[j].duId,
				serviceId : rrinfoList[j].serviceId,
				duName : rrinfoList[j].duName,
				serviceName : rrinfoList[j].serviceName,
				parameterSelfOld :oldConfParameter,
				parameterSelfDef :confParameter,
				parameterStr : parameterStr,
				serviceNum : rrinfoList[j].serviceNum,
				vmNum : rrinfoList[j].vmNum
			};
		
			// 往参数表中插入记录
			$("#serviceExtendedTable").jqGrid("addRowData", j, jsobj);
	}
	});	
}