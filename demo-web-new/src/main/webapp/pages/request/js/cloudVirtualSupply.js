var _status = "Y";
var inputValWidth = "";//输入值列宽度
var virtualSupplyParam = [];
var hardDisk = "";
$(function() {
	virtualSupplyParam = [];
	$('#floatIpDiv').on('switch-change', function (e, data) {
	    var $el = $(data.el),
	    value = data.value;
	    $('#floatIp').val(value);
	});
	$('#shareDiv').on('switch-change', function (e, data) {
	    var $el = $(data.el),
	    value = data.value;
	    $('#share').val(value);
	});
	//点击增加按钮
	$("#service-add-btn").click(function() {
		//初始化云服务套数和虚拟机数量
	     $("#service_num").val("1");
	     $("#vm_num").val("1");
		checkFlag = "first";
		validateBaseInfo();
	   //addAppDuRequest();
	});
	//点击应用子系统查询按钮
	$("#search_comp_button").click(function() {
		searchAppComp();
	});
	//选择应用服务器角色页面，点击下一步
	$("#next_du").click(function() {
		nextDu();
	});
	//选择应用服务器角色页面，点击上一步
	$("#pre_du").click(function() {
		preDu();
	});
	//选择服务目录页面，点击上一步
	$("#pre_catalog").click(function() {
		preCatalog();
	});
	//选择服务目录页面，点击下一步
	$("#next_catalog").click(function() {
		nextCatalog();
	});
	//选择硬件套餐页面，点击下一步
	$("#next_set").click(function() {
		nextSet();
	});
	//选择硬件套餐页面，点击上一步
	$("#pre_set").click(function() {
		preSet();
	});
	//填写参数页面，点击上一步
	$("#pre_attr").click(function() {
		preAttr();
	});
	//添加一个应用服务器角色的云服务
	$("#save_service").click(function(){
		addAppDuService();
	});
	//点击保存按钮
	$("#save_btn").click(function(){
		saveRequest();
	});
	//点击提交按钮
	$("#submit_btn").click(function(){
		//submitRequest();
		checkFlag = "second";
		validateBaseInfo();
		virtualSupplyParam = [];
		//alert("提交按钮暂时不可用,请点击保存按钮!");
	});
	
	//点击关单按钮 --oa工作流使用
	$("#delete_btn").click(function() {
		deleteRequest();
	});
//	$("#save_btn").hide();
//	$("#groupTr").hide();
//	$("#personTr").hide();
	//点击取消按钮
	$("#cancel_btn").click(function(){
		cancelRequest();
		virtualSupplyParam = [];
	});
	//点击保存按钮，保存参数修改
	$("#save_attr_btn").click(function(){
		saveAttr();
	});
	//点击取消按钮，取消参数修改
	$("#cancel_attr_btn").click(function(){
		cancelUpdateAttr();
	});
	
	//点击保存按钮，保存云服务参数,通用方式
	$("#save_attr").click(function(){
		saveCloudAttr();
	});
	//点击取消按钮，取消云服务参数修改,通用方式
	$("#cancel_attr").click(function(){
		cancelCloudAttr();
	});
})
var sysDiskTemp;
var dataDiskTemp;
$(function(){
	sysDiskTemp = $('#hardDisk').jRange({
		from: 0,
		to: 100,
		step: 10,
		format: '%s',
		width: 400,
		showLabels: false,
		//scale: [0,100],
		showScale: false
	});
	dataDiskTemp = $('#dataDisk').jRange({
		from: 0,
		to: 500,
		step: 10,
		format: '%s',
		width: 400,
		showLabels: false,
		//scale: [0,500],
		showScale: false
	});
})



// 获取单选框行号
function getCheckedRadioRow(div_name, tablename, colname, cellvalue) {
	var temp_v = null;
	for (var j = 0; j < $(tablename).jqGrid('getDataIDs').length; j++) {
		temp_v = jQuery(tablename).getCell(j, colname);
		if (temp_v == cellvalue) {
			document.getElementsByName(div_name)[j].checked = true;
			break;
		}
	}
	return j;
}

// 点击取消按钮，取消参数修改
function cancelUpdateAttr() {
	// 关闭填写参数页面
	$("#select_attr_div").dialog("close");
}
// 点击保存按钮，保存参数修改
function saveAttr() {
	var du_id = $("#duId_attr").val();
	var serviceObjMap = new Object();
	for ( var k = 0; k < serviceObjMapList.length; k++) {
		if (serviceObjMapList[k].duId == du_id) {
			serviceObjMap = serviceObjMapList[k];
			break;
		}
	}
	// 校验保存输入填写的参数
	var attrList = new Array();
	var ids = $("#attrTable").jqGrid('getDataIDs');
	var attr_id, attr_input, attr_need, attr_def, attrVal_id, rrInfo_id;
	for ( var j = 0; j < ids.length; j++) {
		attr_id = jQuery('#attrTable').getCell(ids[j], "attrId");
		attr_need = jQuery('#attrTable').getCell(ids[j], "isRequire");
		attr_def = jQuery('#attrTable').getCell(ids[j], "defVal");
		attrVal_id = jQuery('#attrTable').getCell(ids[j], "attrValId");
		rrInfo_id = jQuery('#attrTable').getCell(ids[j], "rrinfoId");
		attr_input = $.trim($("#attr_" + attr_id).val());
		attr_name = jQuery('#attrTable').getCell(ids[j], "attrName");
		attr_cname = jQuery('#attrTable').getCell(ids[j], "attrCname");
		attr_class = jQuery('#attrTable').getCell(ids[j], "attrClass");
		attrType=jQuery('#attrTable').getCell(ids[j], "attrType");
		var attrSelList = eval(jQuery('#attrTable').getCell(ids[j], "attrSelList"));
		attrTypeCode = jQuery('#attrTable').getCell(ids[j], "attrTypeCode");
		remark = jQuery('#attrTable').getCell(ids[j], "remark");

		if (attr_need == 'Y' && attr_input == "") {
			attrList = null;
			showError(i18nShow('error_all_requre'));
			return;
		}
		var attrObj = {
			"attrValue" : attr_input,
			"attrId" : attr_id,
			"attrName" : attr_name,
			"attrCname" : attr_cname,
			"attrClass" : attr_class,
			"attrTypeCode" : attrTypeCode,
			"isRequire" : attr_need,
			"defVal" : attr_def,
			"attrValId" : attrVal_id,
			"rrinfoId" : rrInfo_id,
			"attrType" : attrType,
			"attrSelList" : attrSelList,
			"remark" : remark
		};
		attrList.push(attrObj);
	}
	serviceObjMap.attrValList = null;
	serviceObjMap.attrValList = attrList;
	attrListMap[du_id] = attrList;
	serviceObjMapList[k] = serviceObjMap;
	// 关闭填写参数页面
	$("#select_attr_div").dialog("close");
	showTip(i18nShow('tip_save_success'));
}
// 点击硬件套餐列表的单选框
function clickServiceRadio(row_id) {
	for ( var j = 0; j < $("#setTable").jqGrid('getDataIDs').length; j++) {
		if ((j + 1) == row_id) {
			$("input[id*='" + row_id + "']").prop("readonly", false);
			$("input[id*='" + row_id + "']").val(i18nShow('my_req_fillIn'));
		} else {
			$("input[id*='" + (j + 1) + "']").prop("readonly", true);
			$("input[id*='" + (j + 1) + "']").val("");
		}
	}
}

// 点击添加按钮
function addAppDuRequest() {
	// 初始化应用服务器角色
	$("#step").show();		
	$("#div_baseCase").slideUp('slow');
	$("#supply_main_div").slideUp('slow',function(){			
		$("#select_du_div").show();
		$("#appdu_Table").setGridWidth($("#supply_div1").width()-1);
	});
	
	$(window).resize(function() {
		$("#appdu_Table").setGridWidth($("#supply_div1").width()-1);
    });
	var appId = $("#appId").val();
	getAppDu(appId);
}
var csId,du_id;
// 在应用服务器角色页面，点击下一步
function nextDu() {
	// 校验必须选择服务器角色
	var set_row_id = getCheckedRadio("radioAppdu");
	if (set_row_id == -1) {
		showError(i18nShow('error_select_du'));
		return;
	}
	// 校验之前未提交该服务器角色
	du_id = jQuery('#appdu_Table').getCell(set_row_id, "duId");
	$("#du_id").val(du_id);
	if (duList[du_id] == 1) {
		showError(i18nShow('error_re_select_du'));
	} else {
		csId = jQuery('#appdu_Table').getCell(set_row_id, "serviceId");
		// 关闭选择应用服务器角色页面
		$("#select_du_div").hide();
		// 弹出选择服务配置页面
		$("#select_catalog_div").show();
		$("#step1").attr("class","last");
		$("#step2").attr("class","now");
		// 读入所有可用的服务目录
		$("#catalogTable").setGridWidth($("#supply_div2").width()-1);
		$(window).resize(function() {
		$("#catalogTable").setGridWidth($("#supply_div2").width()-1);
        });
		catalogTableInit();
	}
}
// 获取应用服务器角色
function getAppDu(appId) {
	$("#appdu_Table").jqGrid().GridUnload("appdu_Table");
	$("#appdu_Table").jqGrid(
		{
			url : ctx + '/request/supply/findAppDuByTypeAComp.action',
			datatype : "json",
			postData : {"appId":$("#appId").val(), "datacenterId":$("#datacenterId").val()},
			height : 200,
			autowidth:true,
			colNames : [ 'Appdu_ID', '服务目录Id', i18nShow('my_req_select'), i18nShow('my_req_sr_duName'), i18nShow('my_req_sr_du_type'),i18nShow('my_req_sr_band_service') ],
			colModel : [
					{
						name : 'duId',
						hidden : true
					},{
						name : 'serviceId',
						hidden : true
					},{
						name : 'select',
						index : 'select',
						sortable : false,
						width:20,
						align : "center",
						formatter : function(cellValue, options, rowObject) {
							return "<input type='radio' name='radioAppdu' value='" + options.rowId + "'" + ">";
						}
					},{
						name : 'cname',
						index : 'cname',
						sortable : false,
						align : "left",
						formatter : function(cellValue, options, rowObject) {
							return i18nShow('my_req_sr_chinese')+cellValue+"\n"+i18nShow('my_req_sr_english')+rowObject.ename;
						}
					},{
						name : 'serviceTypeName',
						index : 'serviceTypeName',
						sortable : false,
						align : "left"
					},{
						name : 'option',
						index : 'option',
						align : "left",
						hidden : false,
						formatter:function(cellvalue,option,rowObject){
							var serviceId = rowObject.serviceId;
							if(serviceId!=null && serviceId!=""){
								return i18nShow('my_req_sr_band')+"："+rowObject.serviceName;
							}else{
								return i18nShow('my_req_sr_unband');
							}
						}
					}],
				rowNum : 10,
				rowList : [ 5, 10, 15, 20, 30 ],
				prmNames : {
					search : "search"
				},				
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

function duSearch() {
	jqGridReload("appdu_Table", {
		"appId" : $("#appId").val(),
		"datacenterId" : $("#datacenterId").val(),
		"duName" : $("#duName").val().replace(/(^\s*)|(\s*$)/g, ""),
		"duType" : $("#duType").val()
	});
}

function duReset(){
	$('#duName').val("");
	$('#duType').val("");
}

function getCheckedRadio(div_name) {
	var id_temp = -1;
	for (var i = 0 ; i < document.getElementsByName(div_name).length ; i++) {
		if (document.getElementsByName(div_name)[i].checked) {
			id_temp = document.getElementsByName(div_name)[i].value;
			break;
		}
	}
	return id_temp;
}

Array.prototype._indexOf = function(n) {
	if ("indexOf" in this) {
		return this["indexOf"](n);
	}
	for ( var i = 0; i < this.length; i++) {
		if (n === this[i]) {
			return i;
		}
	}
	return -1;
};
// 保存添加一个应用服务器角色的云服务，更新显示表格
function addAppDuService() {
	var service_row_id = getCheckedRadio("radioService");
	var platformTypeCode = jQuery('#catalogTable').getCell(service_row_id, "platformTypeCode");
	// 校验保存输入填写的参数
		var attrList = new Array();
		var ids = $("#attrTable").jqGrid('getDataIDs');
		var vmNum = $("#vm_num").val();
		var attr_id, attr_input, attr_need, attr_def;
		for (var j = 0; j < ids.length ; j++) {
			attr_id = jQuery('#attrTable').getCell(ids[j], "attrId");
			attr_need = jQuery('#attrTable').getCell(ids[j], "isRequire");
			attr_def = jQuery('#attrTable').getCell(ids[j], "defVal");
			// attr_input = jQuery('#attrTable').getCell(ids[j],"inputVal");
			attr_input = $.trim($("#attr_" + attr_id).val());
			attr_name = jQuery('#attrTable').getCell(ids[j], "attrName");
			if(attr_name == "DATA_DISK"){
				var needValidateQuota = needValidateQuotaFlag();
				if(needValidateQuota == true){
					var diskQuotaVali = validateQuota(platformTypeCode,"storage",attr_input,vmNum);
					if(diskQuotaVali != true) return ;
				}
				
			}
			attr_cname = jQuery('#attrTable').getCell(ids[j], "attrCname");
			attr_class = jQuery('#attrTable').getCell(ids[j], "attrClass");
			attrTypeCode = jQuery('#attrTable').getCell(ids[j], "attrTypeCode");
			attrType = jQuery('#attrTable').getCell(ids[j], "attrType");
			var rowDatas = $("#attrTable").jqGrid('getRowData', ids[j]);
			remark = $("#textarea_" + ids[j]).val();
			var attrSelList = eval(jQuery('#attrTable').getCell(ids[j], "attrSelList"));
			//if (attr_need == "Y" && (attr_input == "" || attr_input == "请填写")) {
			if (attr_need == 'Y' && attr_input == "") {
				attrList = null;
				showError(i18nShow('error_all_requre'));
				return;
			}			
			var attrObj = {
				"attrValue" : attr_input,
				"attrId" : attr_id,
				"attrName" : attr_name,
				"attrCname" : attr_cname,
				"attrClass" : attr_class,
				"attrTypeCode" : attrTypeCode,
				"isRequire" : attr_need,
				"defVal" : attr_def,
				"attrSelList" : attrSelList,
				"srAttrValId" : null,       
				"rrinfoId" : null,
				"attrType":attrType,
				"remark" : remark
			};
			attrList.push(attrObj);
		}
		// 关闭填写虚拟机数量的对话框
		$("#select_attr_div").hide();
		GoBackToMain();
	// 获取应用服务器角色id
	var appdu_row_id = getCheckedRadio("radioAppdu");
	var du_id = jQuery('#appdu_Table').getCell(appdu_row_id, "duId");
	attrListMap[du_id]=attrList;
	var du_name = jQuery('#appdu_Table').getCell(appdu_row_id, "cname");
	// 获取服务目录id
	var service_row_id = getCheckedRadio("radioService");
	var service_name = jQuery('#catalogTable').getCell(service_row_id,"serviceName");
	var service_id = jQuery('#catalogTable').getCell(service_row_id, "serviceId");
	// 获取服务配置id和自定义参数
	var set_row_id = getCheckedRadio("radioServiceSet");
	var set_name = jQuery('#setTable').getCell(set_row_id, "serviceSetName");
	var cpu, mem, sysdisk, datadisk, archivedisk, network, vm_base;
	if (!Array.indexOf) {
		Array.prototype.indexOf = function(obj) {
			for (var i = 0 ; i < this.length ; i++) {
				if (this[i] == obj) {
					return i;
				}
			}
			return -1;
		};
	}
	var srTypeMark=$("#srTypeMark").val();
	if(srTypeMark == 'PS'){
		var hostConfig = getCheckedRadio("radioHostConfig");
		cpu = jQuery('#hostConfigGridTable').getCell(hostConfig, "cpu");
		mem = jQuery('#hostConfigGridTable').getCell(hostConfig, "mem");;
		sysdisk = jQuery('#hostConfigGridTable').getCell(hostConfig, "disk");
	}else{
		cpu = $("#cpu").val();
		mem = $("#mem").val();
		sysdisk = $("#hardDisk").val();
		datadisk = $("#dataDisk").val() ? $("#dataDisk").val() : 0;
	}
	
	// 服务套数
	var service_num = $("#service_num").val();
	var vm_num = $("#vm_num").val();
	// 向汇总表格中添加一行
	var length = $("#serviceAppliedTable").jqGrid('getDataIDs').length;
	
	var diskStr =  "SYS DISK=" + sysdisk + "G DATA DISK="+datadisk+"G";
	var parameter_def = "CPU=" + cpu + "C MEM=" + mem + "M" +"<br>"+ diskStr;
	var parameterStr = cpu+","+mem+","+sysdisk+","+vm_num;
	var diskPlacementType = $("#diskPlacementType").val();
	var jsobj = {
		duId : du_id,
		serviceId : service_id,
		duName : du_name,
		serviceName : service_name,
		serviceSetName : set_name,
		parameterSelfDef : parameter_def,
		parameterStr : parameterStr,
		serviceNum : service_num,
		vmNum : vm_num
	};
	$("#serviceAppliedTable").jqGrid("addRowData", length + 1, jsobj);
	// 保存du
	duList[du_id] = 1;
	// 保存表格数据
	var bmSrRrinfoVo = {
		"duId" : du_id,
		"serviceId" : service_id,
		"duName" : du_name,
		"serviceName" : service_name,
		"serviceSetName" : set_name,
		"cpu" : cpu,
		"mem" : mem,
		"sysDisk" : sysdisk,
		"dataDisk" : datadisk,
		"diskPlacementType":diskPlacementType,
		"archiveDisk" : archivedisk,
		"network" : network,
		"vmNum" : vm_num,
		"serviceNum" : service_num,
		"attrValList" : attrList
	};
	serviceObjMapList.push(bmSrRrinfoVo);
	var service_row_id = getCheckedRadio("radioService");
	var platformTypeCode = jQuery('#catalogTable').getCell(service_row_id, "platformTypeCode");
	console.log($("#share").val());
	console.log($("#floatIp").val());
	virtualSupplyParam.push({
		"cpu" : cpu,
		"mem" : parseInt(mem),
		"sysDisk" : parseInt($("#hardDisk").val() ? $("#hardDisk").val() : 0),
		"dataDisk" : parseInt($("#dataDisk").val() ? $("#dataDisk").val() : 0),
		"diskPlacementType": diskPlacementType,
		"diskShareFlag":$("#share").val()=="true"?true:false,
		"operModelType":"VS",
		"flavorId":"",
		"serviceId" : service_id,
		"vmNum" : parseInt(vm_num),
		"attrValList" : attrList,
		"floatIpFlag":$("#floatIp").val()=="true"?true:false,
		"rmHostRespoolId":"",
		"appId":$("#appId").val(),
		"duId" : du_id,
		"appSysSelectedState":true,
		"platformTypeCode":platformTypeCode,
		"datacenterId":$("#datacenterId").val()
	})
	console.log(virtualSupplyParam);
}

//保存添加一个应用服务器角色的云服务，更新显示表格（没有服务目录属性）
function addAppDuServiceForNoAttrs() {
	// 获取应用服务器角色id
	var appdu_row_id = getCheckedRadio("radioAppdu");
	var du_id = jQuery('#appdu_Table').getCell(appdu_row_id, "duId");
	var du_name = jQuery('#appdu_Table').getCell(appdu_row_id, "cname");
	// 获取服务目录id
	var service_row_id = getCheckedRadio("radioService");
	var service_name = jQuery('#catalogTable').getCell(service_row_id,"serviceName");
	var service_id = jQuery('#catalogTable').getCell(service_row_id, "serviceId");
	// 获取服务配置id和自定义参数
	var set_row_id = getCheckedRadio("radioServiceSet");
	var cpu, mem, sysdisk, datadisk, archivedisk, network, vm_base,maintain;
	var cpu = $("#cpu").val();
	var mem = $("#mem").val();
	var sysdisk = $("#hardDisk").val();
	var datadisk = $("#dataDisk").val() ? $("#dataDisk").val() : 0;
	var diskPlacementType = $("#diskPlacementType").val();
	// 服务套数
//	var service_num = $("#service_num").val();
	var vm_num = $("#vm_num").val();
	// 向汇总表格中添加一行
	var length = $("#serviceAppliedTable").jqGrid('getDataIDs').length;
	var diskStr =  "SYS DISK=" + sysdisk + "G DATA DISK="+datadisk+"G";
	var parameter_def = "CPU=" + cpu + "C MEM=" + mem + "M" +"<br>"+ diskStr;
	var parameterStr = cpu+","+mem+","+sysdisk+","+vm_num;
	var jsobj = {
		duId : du_id,
		serviceId : service_id,
		duName : du_name,
		serviceName : service_name,
//		serviceSetName : set_name,
		parameterSelfDef : parameter_def,
		parameterStr : parameterStr,
		attr_flag : false,
//		serviceNum : service_num,
		vmNum : vm_num
	};
	$("#serviceAppliedTable").jqGrid("addRowData", length + 1, jsobj);
	// 保存du
	duList[du_id] = 1;
	// 保存表格数据
	var bmSrRrinfoVo = {
		"duId" : du_id,
		"serviceId" : service_id,
		"duName" : du_name,
		"serviceName" : service_name,
//		"serviceSetName" : set_name,
		"diskPlacementType":diskPlacementType,
		"cpu" : cpu,
		"mem" : mem,
		"sysDisk" : hardDisk,
		"dataDisk" : datadisk,
		"archiveDisk" : archivedisk,
		"network" : network,
		"vmNum" : vm_num
//		"serviceNum" : service_num
		//"attrValList" : attrList
	};
	var platformTypeCode = jQuery('#catalogTable').getCell(service_row_id, "platformTypeCode");
	virtualSupplyParam.push({
		"cpu" : cpu,
		"mem" : parseInt(mem),
		"sysDisk" : parseInt($("#hardDisk").val()),
		"dataDisk" : parseInt($("#dataDisk").val()),
		"diskPlacementType": diskPlacementType,
		"diskShareFlag":$("#share").val()=="true"?true:false,
		"floatIpFlag":$("#floatIp").val()=="true"?true:false,
		"operModelType":"VS",
		"serviceId" : service_id,
		"vmNum" : parseInt(vm_num),
		"rmHostRespoolId":"",
		"appId":$("#appId").val(),
		"duId" : du_id,
		"appSysSelectedState":true,
		"platformTypeCode":platformTypeCode,
		"datacenterId":$("#datacenterId").val()
	})
	serviceObjMapList.push(bmSrRrinfoVo);
	// 关闭选择服务配置页面
	$("#select_set_div").hide();
	GoBackToMain();
}
// 读取所有可用的服务目录
function ServiceAppliedTableInit() {
	$("#serviceAppliedTable").jqGrid().GridUnload("serviceAppliedTable");
	$("#serviceAppliedTable").jqGrid(
		{
//		    url : ctx + '/request/supply/findVirtualSupplyById.action', 
//			postData:{ "srId":srId},
			datatype : 'local',
			height : 180,
			autowidth :true, 
			colModel : [
					{
						name : 'rrinfoId',
						index : 'rrinfoId',
						label : "rrinfoId",
						width : 0,
						hidden : true,
						align : "left"
					},{
						name : 'duId',
						index : 'duId',
						label : "应用服务器角色Id",
						width : 0,
						hidden : true,
						align : "left"
					},{
						name : 'serviceId',
						index : 'serviceId',
						label : "服务目录Id",
						width : 0,
						hidden : true,
						align : "left"
					},{
						name : 'duName',
						index : 'duName',
						label : i18nShow('my_req_sr_duName'),
						width : 13,
						sortable : false,
						align : "left"/*,
						formatter : function (cellValue,options,rowObject){
							  return "中文简称：" +cellValue+"\n"+ "英文简称：" +rowObject.duEname;
						}*/
					},{
						name : 'serviceName',
						index : 'serviceName',
						label : i18nShow('my_req_sr_service_name'),
						width : 20,
						sortable : false,
						align : "left"
					},
					{
						name : 'parameterSelfDef',
						index : 'parameterSelfDef',
						label : i18nShow('my_req_sr_confParameter'),
						width : 15,
						sortable : false,
						align : "left"
					},
					{
						name : 'parameterStr',
						index : 'parameterStr',
						label : i18nShow('配置参数Map'),
						width : 10,
						sortable : false,
						hidden : true,
						align : "left"
					},{
						name : 'serviceNum',
						index : 'serviceNum',
						label : i18nShow('my_req_server_number'),
						width : 5,
						hidden : true,
						align : "left"
					},{
						name : 'vmNum',
						index : 'vmNum',
						label : i18nShow('my_req_sr_vm_count'),
						width : 8,
						sortable : false,
						align : "left", 
						formatter : function(cellValue, options, rowObject) {
							if(actionType == 'update'&&editclass!='3'){
								return cellValue+ i18nShow('my_req_device_stage');
							 }
						   else {
							    return cellValue+ i18nShow('my_req_device_stage');
						   }
						}
					   },{
						name : 'attr_flag',
						index : 'attr_flag',
						label : i18nShow('参数Flag'),
						width : 5,
						sortable : false,
						hidden : true,
						align : "left"
					   },{
						name : 'attr_operate',
						index : 'attr_operate',
						label : i18nShow('my_req_sr_para'),
						width : 5,
						sortable : false,
						align : "left",
						hidden : false,
						formatter : function(cellValue, options, rowObject) {
							if(attrListMap[rowObject.duId] == null || attrListMap[rowObject.duId].length <=0){
								return "";
							}else{
								return"<a title=''   style='margin-right: 10px;text-decoration:none;' onclick=\"detailFromTable(" + options.rowId + ",'" + rowObject.duId + "')\" >"+i18nShow('my_req_device_attrDatil')+"</a>";
							}
							
						}
					   },{
						name : 'operate',
						index : 'operate',
						label : i18nShow('my_req_operate'),
						width : 8,
						sortable : false,
						align : "left",
						hidden : isHiddenFlag,
						formatter : function(cellValue, options, rowObject) {
							if ((actionType == 'new' || actionType == 'update')&&editType=='all'){
								return"<a title=''   style='margin-right: 10px;text-decoration:none;' onclick=\"deleteFromTable('"+options.rowId+"','"+rowObject.duId+"','" + rowObject.rrinfoId + "')\" >"+i18nShow('com_delete')+"</a>";
								//return "<input type='button' class='btn_del_s' title='删除'" + " onclick=\"deleteFromTable("+options.rowId+",'"+rowObject.duId+"','" + rowObject.rrinfoId + "')\"/>";
							}
							else if (actionType == 'detail'||actionType == 'view'||editType=='other'){
								return "";
							}
						}
					}],
			rowNum : 10,
			rowList : [ 5, 10, 15, 20, 30 ],
			pager : '#pageNav_supply',
			viewrecords : true,
			jsonReader : {
				root : "dataList",
				records : "record",
				repeatitems : false
			}
		});	
}

// 删除所在行的服务器角色申请云服务
function deleteFromTable(id, du_id,rrinfoId) {
	showTip(i18nShow('my_req_delete_confirm'),function(){
		$("#serviceAppliedTable").jqGrid("delRowData", id);
		duList[du_id] = 0;
		for ( var k = 0; k < serviceObjMapList.length; k++) {
			if (serviceObjMapList[k].duId == du_id) {
				serviceObjMapList.splice(k, 1);
				break;
			}
		}
		//删除该申请时，将数据清空
		if (attrListMap[du_id] !=null) {
			attrListMap[du_id] = null;
		}
		if(rrinfoId != "" &&  rrinfoId != "undefined" &&  typeof(rrinfoId) != "undefined" && delRrinfoIdStr.indexOf(rrinfoId) == -1){
			 delRrinfoIdStr = delRrinfoIdStr + "," + rrinfoId;
	    }
		
		$.post(ctx+"/request/supply/deleteRrinfoById.action", {rrinfoId:rrinfoId}, function (data) {
		});
	});
	
}
// 查看所在行的服务器角色申请云服务
function detailFromTable(id, du_id) {
	$("#duId_attr").val(du_id);
	// 弹出填写参数页面
	$("#select_attr_div").dialog({
		autoOpen : true,
		modal : true,
		width : 1000,
		height:470
	});
	$("#select_attr_div").dialog("option", "title", "查看/修改参数");
	$("#pre_attr").hide();
	$("#save_service").hide();
	if (actionType == 'new' || actionType == 'update') {
		$("#save_attr_btn").show();
		$("#cancel_attr_btn").show();
	} else if (actionType == 'detail'||actionType == 'view') {
		$("#save_attr_btn").hide();
		$("#cancel_attr_btn").hide();
		// attrTableInit(service_id);
	}
	// 清空参数表
	$("#attrTable").clearGridData();
	$("#attrTable").setGridWidth(950);
	$("#supply_div4").attr("style","");//清除margin-top:100px
	
	var serviceObjMap = new Object();
	for ( var k = 0; k < serviceObjMapList.length; k++) {
		if (serviceObjMapList[k].duId == du_id) {
			serviceObjMap = serviceObjMapList[k];
			break;
		}
	}
	
	//var attrList = serviceObjMap.attrValList;
	var attrList = attrListMap[du_id];
	// var length = $("#attrTable").jqGrid('getDataIDs').length;
	// 往参数表中插入记录
	for ( var j = 0; j < attrList.length; j++) {
		var jsobj = {
			"attrValId" : attrList[j].srAttrValId,
			"rrinfoId" : attrList[j].rrinfoId,
			"attrTypeCode" : attrList[j].attrTypeCode,
			"remark" : attrList[j].remark,
			"attrId" : attrList[j].attrId,
			"attrName" : attrList[j].attrname,
			"attrCname" : attrList[j].attrCname,
			"attrClass" : attrList[j].attrClass,
			"attrType" : attrList[j].attrType,
			"isRequire" : attrList[j].isRequire,
			"defVal" : attrList[j].defVal,
			"attrSelList" : attrList[j].attrSelList,
			"inputVal" : retValInputForDetail(attrList[j].attrValue, attrList[j], true)
		};
		// 往参数表中插入记录
		$("#attrTable").jqGrid("addRowData", j, jsobj);
		//直接写字符串里的selected 不起作用，故重新用js设置
		if(attrList[j].attrClass == 'SELECT'){
		    var selectLable = document.getElementById("attr_"+attrList[j].attrId).options;
			for(var i=0;i<selectLable.length;i++){
			    if(selectLable[i].value == attrList[j].attrValue){
			       selectLable[i].selected="selected";
			    }
		    }
	    }
	}
}

//输入区域构建
function retValInputForTheDetail(cellValue, rowObject, isread,type) {
	var cValue = ""; // 单元格数值
	var restStr = "";// 返回数值
	var readonly = "";// 是否只读
	var disabled = "";
	var inputType=rowObject.attrClass;
	var clickfun="onClick=checkDate(this,'"+inputType+"')";//点击调用方法
	
	if (isread) {
		readonly = " readonly";
		disabled = "disabled";
		clickfun="";
	}
	// 当前没有值获默认数值
	if (cellValue == undefined) {
		if (rowObject.defVal != undefined) {
			cValue = rowObject.defVal;
		}
	} else {
		cValue = cellValue;
	}
	if (inputType == 'TEXT') { // input输入
	   if(cValue.length <= 100){
		 restStr = cValue;//'<input type=text  id=\"attr_'+ rowObject.attrId + '\" value=\"' + cValue + '\" style=\"width:400px\"' + readonly + '>';
	   }else{
	     restStr = cValue;//"<textarea cols='115' rows='2' id='attr_" + rowObject.attrId + "'" + readonly + ">" + cValue + "</textarea>";
	   }
	} 
	else if (inputType == 'SELECT') {  // select 下拉框
		restStr = "<select id='attr_" + rowObject.attrId + "'  class='form_select'  " + disabled + "  >";
		var attrsel = rowObject.attrSelList;
		for ( var t = 0; t < attrsel.length; t++) {
			if (cValue == attrsel[t].attrValue) { // 选中数值
				restStr = restStr + "<option  value='" + attrsel[t].attrValue + "' selected>" + attrsel[t].attrKey + "</option>";
			} else {
				restStr = restStr + "<option value='" + attrsel[t].attrValue + "'>" + attrsel[t].attrKey + "</option>";
			}
		}
		restStr = restStr + "</select>";
	}else if (inputType == 'LIST') {
		
	}else if(inputType == 'N') { // 数值
		restStr = "<input type='text'   onchange=checkNum(this,'"+rowObject.attrCname+"') id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:400px'" + readonly + ">";
	} else if(inputType == 'D') { //日期  
		restStr = "<input type='text'   readonly='readonly'  id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:400px'"+clickfun+" >";
	} else {
		restStr = "<input type='text'  id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:400px'>";
	}
	return restStr;
}

// 输入区域构建
function retValInputForDetail(cellValue, rowObject, isread,type) {
	var cValue = ""; // 单元格数值
	var restStr = "";// 返回数值
	var readonly = "";// 是否只读
	var disabled = "";
	var inputType=rowObject.attrClass;
	var clickfun="onClick=checkDate(this,'"+inputType+"')";//点击调用方法
	
	if (isread) {
		readonly = " readonly";
		disabled = "disabled";
		clickfun="";
	}
	// 当前没有值获默认数值
	if (cellValue == undefined) {
		if (rowObject.defVal != undefined) {
			cValue = rowObject.defVal;
		}
	} else {
		cValue = cellValue;
	}
	if (inputType == 'TEXT') { // input输入
	   if(cValue.length <= 100){
		 restStr = cValue;//'<input type=text  id=\"attr_'+ rowObject.attrId + '\" value=\"' + cValue + '\" style=\"width:400px\"' + readonly + '>';
	   }else{
	     restStr = cValue;//"<textarea cols='115' rows='2' id='attr_" + rowObject.attrId + "'" + readonly + ">" + cValue + "</textarea>";
	   }
	} 
	//else if (inputType == 'A') {  // Textarea 输入域
	//	restStr = "<textarea cols='115' rows='2' id='attr_" + rowObject.attrId + "'" + readonly + ">" + cValue + "</textarea>";
	//} 
	else if (inputType == 'SELECT') {  // select 下拉框
		restStr = "<select id='attr_" + rowObject.attrId + "'  class='form_select'  " + disabled + "  >";
		var attrsel = rowObject.attrSelList;
		for ( var t = 0; t < attrsel.length; t++) {
			if (cValue == attrsel[t].attrValue) { // 选中数值
				restStr = restStr + "<option  value='" + attrsel[t].attrValue + "' selected>" + attrsel[t].attrKey + "</option>";
			} else {
				restStr = restStr + "<option value='" + attrsel[t].attrValue + "'>" + attrsel[t].attrKey + "</option>";
			}
		}
		restStr = restStr + "</select>";
	}else if (inputType == 'LIST') {
		restStr += "<input type='hidden'  id='attr_" + rowObject.attrId + "' name='keyParam' value=''>";
		restStr +=  "<textarea style='width:"+inputValWidth+"px' cols='110' rows='1' id='"+ rowObject.attrId +"' readonly></textarea>";
		restStr +=  "<a  style=''   title='' onclick=findListSql('"+ rowObject.attrId +"','"+ deviceId +"')>选择</a>"
	}else if(inputType == 'N') { // 数值
		restStr = "<input type='text'   onchange=checkNum(this,'"+rowObject.attrCname+"') id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:400px'" + readonly + ">";
	} else if(inputType == 'D') { //日期  
		restStr = "<input type='text'   readonly='readonly'  id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:400px'"+clickfun+" >";
	} else {
		restStr = "<input type='text'  id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:400px'>";
	}
	return restStr;
}


// 选择服务目录页面，点击上一步
function preCatalog() {
	// 关闭选择服务配置页面
	$("#select_catalog_div").hide();
	// 弹出选择服务配置页面
	$("#select_du_div").show();
	switchStep(1);
}
//设置向导导航的选中样式
function switchStep(nowId){
	$("#step li").each(function(i,item){
		if((i+1)< nowId){
			$(item).attr("class","last");
		}else if((i+1) == nowId){
			$(item).attr("class","now");
		}else{
			$(item).attr("class","");
		}		
	});	
}

function switchStep_s(nowId){
	$("#step_s li").each(function(i,item){
		if((i+1)< nowId){
			$(item).attr("class","last");
		}else if((i+1) == nowId){
			$(item).attr("class","now");
		}else{
			$(item).attr("class","");
		}		
	});	
}
function changeVmNum(type){
	if(type=='add'){
		jQuery.add('#service_num',10);
	}else if(type=='reduce'){
		jQuery.reduce('#service_num');
	}else {
		jQuery.modify('#service_num',10);
	}
	var service_row_id = getCheckedRadio("radioService");
	var vm_base = jQuery('#catalogTable').getCell(service_row_id, "vmBase");
	$("#vm_num").val($("#service_num").val() * vm_base);
	
}
function nextCatalog() {
	switchStep(3); 
	// 校验必须选择服务目录
	var service_row_id = getCheckedRadio("radioService");
	if (service_row_id == -1) {
		showError(i18nShow('my_req_select_cloudServices'));
		return;
	}
	var vm_base = jQuery('#catalogTable').getCell(service_row_id, "vmBase");
	var duId = jQuery('#catalogTable').getCell(service_row_id, "duId");
	var rrinfoId = jQuery('#catalogTable').getCell(service_row_id,"rrinfoId");
	changeVmNum();
	// 服务配置id
	var service_id = jQuery('#catalogTable').getCell(service_row_id, "serviceId");
	var platformType = jQuery('#catalogTable').getCell(service_row_id, "platformType");
	//获取服务目录属性
	attrNeededFlag = getServiceAttrSize(service_id);
	// 关闭选择服务目录页面
	$("#select_catalog_div").hide();
	// 弹出选择硬件套餐页面
	$("#select_set_div").show();
	
	var srTypeMark=$("#srTypeMark").val();
	//物理机供给，读取物理机配置
	if(srTypeMark == 'PS'){//物理机供给
		$("#vmConfig").hide();
		$("#showHostConfigList").show();
		$("#showHostConfigListDiv").show();
		$("#hostConfigGridPager").show();
		//先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
		$("#hostConfigGridTable").jqGrid().GridUnload("hostConfigGridTable");
			$("#hostConfigGridTable").jqGrid({
				url : ctx+"/resmgt-common/device/getHostConfigure.action", 
				postData:{},
				rownumbers : false, 
				datatype : "json", 
				mtype : "post", 
				height : 230,
				autowidth : true, 
				multiselect:false,
				colNames:[i18nShow('my_req_select'),i18nShow('my_req_cpuNum'),i18nShow('my_req_memNum'),i18nShow('my_req_sysDisk')],
				colModel : [{	name : 'select',
								index : 'select',
								sortable : false,
								width:20,
								align : "center",
								formatter : function(cellValue, options, rowObject) {
									return "<input type='radio' checked='checked' name='radioHostConfig' value='" + options.rowId + "'" + ">";
								}
							},
				            {name : "cpu",index : "cpu",	width : 60,sortable : true,align : 'left'},
				            {name : "mem",index : "mem",	width : 80,sortable : true,align : 'left'},
				            {name : "disk",index : "disk",	width : 100,sortable : true,align : 'left'},],
				viewrecords : true,
				sortname : "cpu",
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
				pager : "#hostConfigGridPager",
				hidegrid : false
			});
    }else if(srTypeMark == 'VS'){//虚拟机供给
    	$("#vmConfig").show();
		$("#showHostConfigList").hide();
		$("#showHostConfigListDiv").hide();
		$("#hostConfigGridPager").hide();
    }
	$("#next_set").attr("title",i18nShow('my_req_nextStep'));
	$("#next_set").attr("class","btn btn_dd2 btn_dd3");
	$("#step4").show();
	$("#setTable").setGridWidth($("#supply_div3").width()-1);
	$(window).resize(function() {
	    $("#setTable").setGridWidth($("#supply_div3").width()-1);
    });
	// 读入所有可用的硬件套餐
	var duIdd = $("#du_id").val();
	// 如果需要设置cpu和内存不能修改，只能默认保持服务器角色设置
	// 加上判断if(csId){setTableInit(duIdd);}else{下面设置开关样式代码}
	
	//设置开关的样式
	$('#floatIp').val("false");
	$("#floatIpDiv > div").removeClass("switch-on");
	$("#floatIpDiv > div").addClass("switch-off");
	$('#share').val("false");
	$("#shareDiv > div").removeClass("switch-on");
	$("#shareDiv > div").addClass("switch-off");
	
	var cpu = $("#cpu").val();
	var mem = $("#mem").val();
	//var hardDisk = $("#hardDisk").val();
	var sysDisk = $("#hardDisk").val();
	var dataDisk = $("#dataDisk").val();
	flatSelectByValue("cpu","1");
	flatSelectByValue("mem","1024");
	var service_row_id = getCheckedRadio("radioService");
	var platformTypeCode = jQuery('#catalogTable').getCell(service_row_id, "platformTypeCode");
	var srTypeMark=$("#srTypeMark").val();
	var diskCapacity = jQuery('#catalogTable').getCell(service_row_id, "diskCapacity");
	// 平台类型等于POWER平台时，隐藏系统盘和数据盘
	if(platformTypeCode == "P") { 
		$("#sysDisk_Tr").hide();
		$("#dataDisk_Tr").hide();
		$("#share_Tr").hide();
		$("#float_Tr").hide();
		$("#placementType").hide();	
	} else{
		$("#sysDisk_Tr").show();
		$("#dataDisk_Tr").show();
		$("#placementType").hide();	
		if(platformTypeCode == "O"){
			sysDiskTemp.options.disable= false;
		}else{
			sysDiskTemp.options.disable= true;
		}
		sysDiskTemp.options.from = parseInt(diskCapacity);
		sysDiskTemp.options.to = parseInt(diskCapacity)+100;
		sysDiskTemp.renderScale();
		//设置数值
		sysDiskTemp.setValue(parseInt(diskCapacity));
		dataDiskTemp.setValue(0);
		if(platformTypeCode == "X"){
			$("#share_Tr").hide();
			$("#float_Tr").hide();	
			$("#placementType").show();	//Vmware情况下显示置备类型
		}else if(platformTypeCode == "O"){
			$("#share_Tr").show();
			$("#float_Tr").show();
			$("#placementType").hide();	
		}
	}

	if(!attrNeededFlag){
		$("#step4").hide();
		$("#next_set").attr("title",i18nShow('my_req_yes'));
		$("#next_set").attr("class","btn btn_dd2 btn_dd3");
	}
}
//验证配额
function validateQuota(platformTypeCode,code,value,vmNum){
	var flag = false;
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx +"/quota/validateQuota.mvc",  
		data : {
			"platformTypeCode" : platformTypeCode,
			"reqValue": (parseInt(value)*parseInt(vmNum)),
			"code":code
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

//是否需要验证配额标识
function needValidateQuotaFlag(){
	var flag = false;
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx +"/quota/needValidateQuotaFlag.mvc",  
		data : {},
		error : function() {// 请求失败处理函数
			showError();
		},
		success : function(data) { 
			if(data.result=="true"){
				flag = true;
			}else{
				flag = false;
			}
		}
	});
	return flag;
}
function nextSet() {
	var service_row_id = getCheckedRadio("radioService");
	var platformType = jQuery('#catalogTable').getCell(service_row_id, "platformType");
	var srTypeMark=$("#srTypeMark").val();
	//平台类型为openstack时,需要验证磁盘容量,磁盘容量大于镜像容量才可进行下一步操作
	//2018/5/8删除系统盘验证，只验证数据盘
	/*if(platformType == '4' && srTypeMark == 'VS'){
		//验证系统盘
		var disk = $("#hardDisk").val();
		var diskCapacity = jQuery('#catalogTable').getCell(service_row_id, "diskCapacity");
		if(parseInt(disk) < parseInt(diskCapacity)){
			showTip("选择的磁盘应大于镜像容量【"+diskCapacity+"G】，否则无法完成机器的安装");
			return;
		}	
	}*/
	//是否需要验证配额标识
	var needValidateQuota = needValidateQuotaFlag();
	if(needValidateQuota == true){
		var cpu = $("#cpu").val();
		var mem = $("#mem").val();
		var vmNum = $("#vm_num").val();//所选套数*云服务中虚机基数
		//验证数据盘是否满足配额
		var disk = $("#dataDisk").val();
		var platformTypeCode = jQuery('#catalogTable').getCell(service_row_id, "platformTypeCode");
		var vmNumVlidate = validateQuota(platformTypeCode,"vm",vmNum,1);
		if(vmNumVlidate != true) return ;
		var cpuQuotaVali = validateQuota(platformTypeCode,"cpu",cpu,vmNum);
		if(cpuQuotaVali != true) return ;
		var memQuotaVali = validateQuota(platformTypeCode,"mem",mem,vmNum);
		if(memQuotaVali != true) return ;
		var diskQuotaVali = validateQuota(platformTypeCode,"storage",disk,vmNum);
		if(diskQuotaVali != true) return ;
		if(vmNumVlidate == true && cpuQuotaVali == true && memQuotaVali == true && diskQuotaVali == true){
			if(attrNeededFlag){//如果有套餐属性则显示属性填写页面
				openAttr();
			}else{//直接添加
				addAppDuServiceForNoAttrs();
			}
		}
	}else{
		if(attrNeededFlag){//如果有套餐属性则显示属性填写页面
			openAttr();
		}else{//直接添加
			addAppDuServiceForNoAttrs();
		}
	}
}


// 选择硬件套餐页面，点击下一步
function openAttr() {
	// 校验必须输入参数
	// IE 7不支持indexof方法，需要扩展
	var cpu, mem, hardDisk, datadisk, archivedisk;
	cpu = $("#cpu").val();
	mem = $("#mem").val();
	hardDisk = $("#hardDisk").val();

	// 关闭选择服务配置页面
	$("#select_set_div").hide();
		// 弹出选择硬件套餐属性页面
		$("#select_attr_div").show();
		$("#supply_div4").attr("style","margin-top:138px");//添加margin-top:100px
		switchStep(4);
		$("#save_attr_btn").hide();
		$("#cancel_attr_btn").hide();
		$("#pre_attr").show();
		$("#save_service").show();
		// 获取服务目录id
		var service_row_id = getCheckedRadio("radioService");
		var service_id = jQuery('#catalogTable').getCell(service_row_id, "serviceId");
		// 读入需要填写的参数
		$("#attrTable").setGridWidth($("#supply_div4").width()-1);
		$(window).resize(function() {
	      $("#setTable").setGridWidth($("#supply_div4").width()-1);
        });
		attrTableInit(service_id);
}
function updateVmNum(){
	
	var rowId=$("#vmrowId").val();
	var vmNum=$("#vm_num").val();	
    jQuery('#serviceAppliedTable').setCell(rowId, "vmNum",vmNum);
    var du_id=jQuery('#serviceAppliedTable').getCell(rowId,'duId')

	for ( var k = 0; k < serviceObjMapList.length; k++) {
		if (serviceObjMapList[k].srRrinfoPo.duId == du_id) {
			serviceObjMap = serviceObjMapList[k];
			break;
		}
	}
   var obj= serviceObjMap.srRrinfoPo;
     obj.vmNum=vmNum;
     serviceObjMapList[k]=serviceObjMap;
    
	 $("#select_vm_div").dialog("close");
	 
}
// 选择硬件套餐页面，点击上一步
function preSet() {
	// 关闭选择服务配置页面
	$("#select_set_div").hide();
	// 弹出选择服务配置页面
	$("#select_catalog_div").show();
	switchStep(2);
}
// 填写虚拟机数量页面，点击上一步
function preAttr() {
	// 关闭选择服务配置页面
	$("#select_attr_div").hide();
	//$("#service_num").val("");
	//$("#vm_num").val("");
	// 弹出填写虚拟机数量页面
	$("#select_set_div").show();
	var service_row_id = getCheckedRadio("radioService");
	var service_id = jQuery('#catalogTable').getCell(service_row_id, "serviceId");
	//获取服务目录属性
	attrNeededFlag = getServiceAttrSize(service_id);
	switchStep(3);
}
// 读取所有可用的服务目录
function catalogTableInit() {
	var srTypeMark=$("#srTypeMark").val();
	$("#spCs").hide();
	var ispa ="";
	if(csId) {
		$("#spCs").show();
		ispa =" checked='checked' ";
	}
	var hostTypeId;
	if(srTypeMark == 'PS'){
		hostTypeId = '2';//物理机
	}else if(srTypeMark == 'VS'){
		hostTypeId = '1';//虚拟机
	}
	$("#catalogTable").jqGrid().GridUnload("catalogTable");
	$("#catalogTable").jqGrid({
		url : ctx + '/request/supply/getServiceList.action',
		datatype : "json",
		postData : {"serviceId":csId,"serviceStatic":_status,"hostTypeId":hostTypeId,"serviceType":"SERVER"},
		height : 220,
		autowidth : true,
		colModel : [
		{
			name : 'rrinfoId',
			index : 'rrinfoId',
			label : 'rrinfoId',
			width : 0,
			hidden : true,
			align : "left"
		},{
			name : 'serviceId',
			index : 'serviceId',
			label : '服务目录ID',
			width : 0,
			hidden : true,
			align : "left"
		},{
			name : 'select',
			index : 'select',
			label : i18nShow('my_req_select'),
			width : 5,
			sortable : false,
			align : "center",
			formatter : function(cellValue, options, rowObject) {
				return "<input type='radio'"+ispa+" name='radioService' value='" + options.rowId + "'" + ">";
				}
		},{
			name : 'serviceName',
			index : 'serviceName',
			label : i18nShow('my_req_sr_service_name'),
			width : 20,
			sortable : false,
			align : "left",
			formatter : function(cellValue, options, rowObject) {
				var ops = "";
				ops += "<a href='#' style='' ";
				ops += "id='href_" + options.rowId + "' ";
				ops += "onclick='viewCatalogdetail(" + rowObject.serviceId + ")'" + ">" + cellValue + "</a>";
				return cellValue;
			}
		},{
			name : 'funcRemark',
			index : 'funcRemark',
			label : i18nShow('my_req_summary'),
			width : 40,
			sortable : false,
			align : "left"
		},{
			name : 'platformTypeName',
			index : 'platformTypeName',
			label : i18nShow('cloud_service_platform'),
			width : 10,
			sortable : false,
			align : "left"
		},{
			name : 'platformType',
			index : 'platformType',
			label : '平台类型ID',
			hidden : true
		},{
			name : 'platformTypeCode',
			index : 'platformTypeCode',
			label : '平台类型ID对应的编码',
			hidden : true
		},{
			name : 'vmBase',
			index : 'vmBase',
			label : i18nShow('my_req_vmNum'),
			width : 10,
			sortable : false,
			align : "left"
		},{
			name : 'diskCapacity',
			index : 'diskCapacity',
			label : i18nShow('my_req_diskCapacity'),
			width : 10,
			hidden : false,
			sortable : false,
			align : "left"
		}],
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		sortname : 'serviceId',
		sortorder : "asc",
		pager : '#pageNav',
		viewrecords : true,
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		}
	}).navGrid('#pageNav', cloud_view_btn);
};

function catalogSearch() {
	var queryData = {
		haType : $("#haType").val(),
		platformType : $("#platformType").val()
	};
	jqGridReload("catalogTable", queryData);
};

function catalogReset(){
	$("#haType").val("");
	$("#platformType").val("");
}

// 读取所有可用的服务配置
function setTableInit(duId) {
	$("#spCss").hide();
	$("#setTable").jqGrid().GridUnload("setTable");	
	$.post(ctx + "/request/supply/getServiceConfigure.action", {
		"duId" : duId
	}, function(data) {
		if(data!=null && data!=''){
			flatSelectForUnitVt("cpu",data.cpu);
			flatSelectForUnitVt("mem",data.mem);
			//2：power类型虚拟机
			if(data.platformType == "2") {
				$("#sysDisk_Tr").hide();
				$("#dataDisk_Tr").hide();
			} else {
				$("#sysDisk_Tr").show();
				$("#dataDisk_Tr").show();
				
				if(platformTypeCode == "O"){
					sysDiskTemp.options.disable= false;
				}else{
					sysDiskTemp.options.disable= true;
				}
				
				sysDiskTemp.options.from = parseInt(diskCapacity);
				sysDiskTemp.options.to = parseInt(diskCapacity)+100;
				sysDiskTemp.renderScale();
				sysDiskTemp.setValue(diskCapacity);
				
				dataDiskTemp.setValue(0);
				
				//vmware   
				if(data.platformType == "1"){
					$("#share_Tr").hide();
					$("#float_Tr").hide();
					//openstack
				}else if(data.platformType == "4"){
					$("#share_Tr").show();
					$("#float_Tr").show();
				}
			}
		}
	});
}

// 读取用户需要填写的参数
function attrTableInit(serviceId) {
	var paramObj = {"serviceId":serviceId,"SERVICE_ID":serviceId};
	$("#attrTable").jqGrid().GridUnload("attrTable");		
	$("#attrTable").jqGrid(
			{
				url : ctx + '/request/supply/getServiceAttrList.action',
				datatype : "json",
				postData:paramObj,
				height : 300,
				autowidth : true,
				colModel : [
				{
					name : 'attrValId',
					index : 'attrValId',
					label : '参数值ID',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'rrinfoId',
					index : 'rrinfoId',
					label : '资源请求ID',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'attrId',
					index : 'attrId',
					label : '参数ID',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'attrTypeCode',
					index : 'attrTypeCode',
					label : '数据类型',
					hidden : true,
					width : 5,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						return "A";
					}
				},
				
				{
					name : 'attrName',
					index : 'attrName',
					label : '参数名称英文',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'attrCname',
					index : 'attrCname',
					label : i18nShow('cloud_service_attr_name'),
					sortable : false,
					width : 20,
					align : "left"
				},{
					name : 'attrClass',
					index : 'attrClass',
					label : '属性类型',
					sortable : false,
					hidden : true,
					width : 20,
					align : "left"
				},{
					name : 'isRequire',
					index : 'isRequire',
					label : '是否必填隐藏值',
					sortable : false,
					hidden : true,
					width : 20,
					align : "left"
				},{
					name : 'option',
					index : 'option',
					label : i18nShow('cloud_service_model_isRequire'),
					sortable : false,
					hidden : false,
					width : 10,
					align : "left",
					formatter : function(cellValue,options,rowObject){
						if(rowObject.isRequire == 'N'){
							return i18nShow('cloud_service_require_N');
						}else if(rowObject.isRequire == 'Y'){
							return i18nShow('cloud_service_require_Y');
						}
					}
				},{
					name : 'defVal',
					sortable : false,
					label : '是否必填隐藏值',
					hidden : true,
					index : 'defVal',
					width : 10,
					align : "left"
				},{
					name : 'attrSelList',
					sortable : false,
					label : '列表',
					hidden : true,
					index : 'attrSelList',
					width : 10,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						return JSON.stringify(cellValue);
					}
				},
				{
					name : 'attrType',
					sortable : false,
					label : '分类',
					hidden : true,
					index : 'attrType',
					width : 10,
					align : "left"
		      	},
				{
					name : 'Classes',
					index : 'Classes',
					label : i18nShow('my_req_parameter_class'),
					width : 10,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						      var className= "";
						         if(rowObject.attrType=='COMPUTE') {className=i18nShow('my_req_compute_res');}else
						          if (rowObject.attrType=='NET') {className=i18nShow('my_req_network');}else
						        	  if(rowObject.attrType=='STORAGE') {className=i18nShow('my_req_storage_res');}
						         
						
						return  className;
					}
				},{
					name : 'inputVal',
					index : 'inputVal',
					label : i18nShow('my_req_fillIn_rule'),
					sortable : false,
					width : 45,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						if (actionType == 'detail') {
							return retValInput(cellValue, rowObject, true);
						}
						  if(editclass==rowObject.attrClass||editclass=='0'){
						      return retValInput(cellValue, rowObject, false);
						   }
						  else {
							  return retValInput(cellValue, rowObject, true);
							  
						  }
						
						
						
					}
				},{
					name : 'remark',
					index : 'remark',
					label : i18nShow('com_remark'),
					sortable : false,
					width : 50,
					formatter : function(cellValue, options, rowObject) {
						if (cellValue != undefined) {
							return "<textarea id='textarea_"+options.rowId+"'  style='width: 300px;height:35px;overflow-y:auto' cols='60' rows='1' disabled='disabled' class='textInput'>" + cellValue + "</textarea>";
						} else {
							return '';
						}
					}
				}],
				rowNum : 100,
				rowList : [ 100, 20, 30, 50 ],
				sortname : 'attrId',
				sortorder : "asc",
				// pager : '#pageNav_attr',
				viewrecords : true,
				jsonReader : {
					root : "dataList",
					records : "record",
					repeatitems : false
				}

			});
	// setGridSize("setTable", $("#searchDiv").width()-1, 300);
	var ids = $("#attrTable").jqGrid('getDataIDs');
	if(ids.length>=0){
		attrNeededFlag = false;
	}
}
function initAttrTable2() { 
	$("#attrTable").jqGrid(
	{
		datatype : "local",
		height : 300,
		autowidth : true,
		colModel : [
		{
			name : 'attrValId',
			index : 'attrValId',
			label : '参数值ID',
			width : 0,
			hidden : true,
			align : "left"
		},{
			name : 'rrinfoId',
			index : 'rrinfoId',
			label : '资源请求ID',
			width : 0,
			hidden : true,
			align : "left"
		},{
			name : 'attrId',
			index : 'attrId',
			label : '参数ID',
			width : 0,
			hidden : true,
			align : "left"
		},{
			name : 'attrTypeCode',
			index : 'attrTypeCode',
			label : '数据类型',
			hidden : true,
			width : 5,
			align : "left"
		},
		{
			name : 'attrName',
			index : 'attrName',
			label : '参数名称英文',
			width : 0,
			hidden : true,
			align : "left"
		},{
			name : 'attrCname',
			index : 'attrCname',
			label : i18nShow('cloud_service_attr_name'),
			sortable : false,
			width : 10,
			align : "left"
		},{
			name : 'attrClass',
			index : 'attrClass',
			label : i18nShow('cloud_service_model_type'),
			sortable : false,
			width : 10,
			align : "left"
		},{
			name : 'isRequire',
			index : 'isRequire',
			label : '是否必填隐藏值',
			sortable : false,
			width : 5,
			align : "left",
			hidden : true
		},{
			name : 'option',
			index : 'option',
			label : i18nShow('cloud_service_model_isRequire'),
			sortable : false,
			hidden : false,
			width : 10,
			align : "left",
			formatter : function(cellValue,options,rowObject){
				if(rowObject.isRequire == 'N'){
					return i18nShow('cloud_service_require_N');
				}else if(rowObject.isRequire == 'Y'){
					return i18nShow('cloud_service_require_Y');
				}
			}
		},{
			name : 'defVal',
			sortable : false,
			hidden : true,
			label : '默认值',
			index : 'defVal',
			width : 10,
			align : "left"
		},{
			name : 'attrSelList',
			label : '选项',
			sortable : false,
			hidden : true,
			index : 'attrSelList',
			width : 10,
			align : "left",
			formatter : function(cellValue, options, rowObject) {
				return JSON.stringify(cellValue);
			}
		},
		{
			name : 'attrType',
			label : '分类',
			sortable : false,
			hidden : true,
			index : 'attrType',
			width : 10,
			align : "left"
      	},
		{
			name : 'Classes',
			index : 'Classes',
			label : i18nShow('my_req_parameter_class'),
			width : 6,
			align : "left",
			formatter : function(cellValue, options, rowObject) {
				      var className= "";
				         if(rowObject.attrType=='COMPUTE') {className=i18nShow('my_req_compute_res');}else
				          if (rowObject.attrType=='NET') {className=i18nShow('my_req_network');}else
				        	  if(rowObject.attrType=='STORAGE') {className=i18nShow('my_req_storage_res');}
				         
				
				return  className;
			}
		},{
			name : 'inputVal',
			index : 'inputVal',
			sortable : false,
			label : i18nShow('my_req_fillIn'),
			width : 20,
			align : "left",
			formatter : function(cellValue, options, rowObject) {
				if (actionType == 'detail') {
					return retValInput(cellValue, rowObject, true);
				}
				  if(editclass==rowObject.attrClass||editclass=='0'){
				      return retValInput(cellValue, rowObject, false);
				   }
				  else {
					  return retValInput(cellValue, rowObject, true);
					  
				  }
			}
		},{
			name : 'remark',
			index : 'remark',
			sortable : false,
			label : i18nShow('my_req_fillIn_rule'),
			width : 15,
			formatter : function(cellValue, options, rowObject) {
				if (cellValue != undefined) {
					//return "<textarea style='width: 300px;height:35px;overflow-y:auto' cols='60' rows='1' disabled='disabled' class='textInput'>" + cellValue + "</textarea>";
					return cellValue;
				} else {
					return '';
				}
			}
		}],
		rowNum : 100,
		rowList : [ 100, 20, 30, 50 ],
		sortname : 'attrId',
		sortorder : "asc",
		// pager : '#pageNav_attr',
		viewrecords : true,
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		}

	});
}
// 输入区域构建
function retValInput(cellValue, rowObject, isread,type) {
	var cValue = ""; // 单元格数值
	var restStr = "";// 返回数值
	var readonly = "";// 是否只读
	var disabled = "";
	
	var inputType=rowObject.attrClass;
	var clickfun="onClick=checkDate(this,'"+inputType+"')";//点击调用方法
	inputValWidth = '300';
	if (isread) {
		readonly = " readonly";
		disabled = "disabled";
		clickfun="";
	}
	// 当前没有值获默认数值
	if (cellValue == undefined) {
		if (rowObject.defVal != undefined) {
			cValue = rowObject.defVal;
		}
	} else {
		cValue = cellValue;
	}
	if (inputType == 'TEXT') { // input输入
		 restStr = "<textarea style='width:"+inputValWidth+"px' cols='110' rows='1' id='attr_" + rowObject.attrId + "'" + readonly + ">" + cValue + "</textarea>";
	} 
	//else if (inputType == 'A') {  // Textarea 输入域
	//	restStr = "<textarea cols='115' rows='2' id='attr_" + rowObject.attrId + "'" + readonly + ">" + cValue + "</textarea>";
	//} 
	else if (inputType == 'SELECT') {  // select 下拉框
		restStr = "<select style='width:300px' id='attr_" + rowObject.attrId + "'  class='form_select'  " + disabled + " >";
		var attrsel = rowObject.attrSelList;
		for ( var t = 0; t < attrsel.length; t++) {
			if (cValue == attrsel[t].attrValue) { // 选中数值
				restStr = restStr + "<option  selected=true value='" + attrsel[t].attrValue + "'>" + attrsel[t].attrKey + "</option>";
			} else {
				restStr = restStr + "<option value='" + attrsel[t].attrValue + "'>" + attrsel[t].attrKey + "</option>";
			}
		}
		restStr = restStr + "</select>";
	}else if( inputType == 'LIST' ) {
			var treeObj = $.fn.zTree.getZTreeObj("deviceTree2_auto");
//			var treeObj = getSelectedDeviceIds();
//			alert(treeObj);
			var nodeArr = treeObj.transformToArray(treeObj.getNodes());
			var deviceId = '';
			var flag = false;
			for(var k=0 ; k<nodeArr.length ; k++) {
//				alert(nodeArr[k].id);
				if (nodeArr[k].isParent) {
					continue;
				}
				if(flag) {
					deviceId = '';
					break;
				}
				deviceId = nodeArr[k].id;
				flag = true;
			}
			restStr += "<input type='hidden'  id='attr_" + rowObject.attrId + "' name='keyParam' value=''>";
			restStr +=  "<textarea style='width:"+inputValWidth+"px' cols='110' rows='1' id='"+ rowObject.attrId +"' readonly></textarea>";
//			restStr +=  "<a  style=''   title='' onclick=\"findListSql('" + rowObject.attrListSql + "')\" >选择</a>"
			restStr += "<a title='' style='margin-right: 10px;text-decoration:none;' onclick=\"findListSql('"+ rowObject.attrId +"','"+ deviceId +"')\" >选择</a>";
			//restStr +=  "<a  style=''   title='' onclick=findListSql('"+ rowObject.attrId +"','"+ deviceId +"')>选择</a>"
//			restStr +=  "<a  style=''   title='' onclick=\"test('ewew')\" >选择</a>"
	}else if(inputType == 'N') { // 数值
		restStr = "<input type='text'   onchange=checkNum(this,'"+rowObject.attrCname+"') id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:400px'" + readonly + ">";
	} else if(inputType == 'D') { //日期  
		restStr = "<input type='text'   readonly='readonly'  id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:400px'"+clickfun+" >";
	} else {
		restStr = "<input type='text'  id='attr_" + rowObject.attrId + "' value='" + cValue + "' style='width:400px'>";
	}
	return restStr;
}
var isInitListSql = false;
var currentAttrId;
//显示ListSql查询结果并选择
function findListSql(listSqlId, deviceId) {
	currentAttrId = listSqlId;
	if(!isInitListSql) {
		initListSql(listSqlId, deviceId);
		isInitListSql = true;
	} else {
		autoListSql(listSqlId, deviceId);
	}

	$("#attrListSqlDiv").dialog({
		autoOpen : true,
		height : 470,
		width : 667,
		title :i18nShow('my_req_selectList'),
		close:function(){
			 $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
		}
	})
}
function initListSql(listSqlId, deviceId) {
	$("#gridTableListSql").jqGrid({
		url : ctx+"/cloud-service/serviceattr/queryDynamicSql.action",
		rownumbers : false, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 300,
		autowidth : true, // 是否自动调整宽度
		//multiselect:true,
		//multiboxonly: false,
		postData: {"listSqlId" :listSqlId, "deviceId" : deviceId},
		colModel : [ {
			name : "keyParam",
			index : "keyParam",
			label : "keyParam",
			width : 0,
			sortable : true,
			align : 'left',
			hidden:true	
		},{
			name : "valueParam",
			index : "valueParam",
			label : i18nShow('my_req_valueParam'),
			width : 500,
			sortable : true,
			align : 'left'
		},{
			name : i18nShow('my_req_select'),
			index : i18nShow('my_req_select'),
			label : i18nShow('my_req_select_parameter'),
			width : 80,
			sortable : true,
			align : 'left',
			formatter : function(cellValue, options, rowObject) {
				var str = "<a style=''   onclick=\"chooseQueryData('" + rowObject.keyParam + "','" + rowObject.valueParam + "')\">"+i18nShow('my_req_select')+"</a>"
				return str;
			}
	}],
		
		viewrecords : true,
		sortname : "keyParam",
		rowNum : 1000,
//		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#gridPagerListSql",
		hidegrid : false
	});
}
function autoListSql(listSqlId, deviceId) {
	jqGridReload("gridTableListSql", {
		"listSqlId" :listSqlId, "deviceId" : deviceId
	});
//	alert(1);
	/*$("#gridTableListSql").jqGrid({
		url : ctx+"/cloud-service/serviceattr/queryDynamicSql.action",
		rownumbers : false, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 300,
		autowidth : true, // 是否自动调整宽度
		//multiselect:true,
		//multiboxonly: false,
		postData: {"listSqlId" :listSqlId, "deviceId" : deviceId},
		colModel : [ {
			name : "keyParam",
			index : "keyParam",
			label : "keyParam",
			width : 0,
			sortable : true,
			align : 'left',
			hidden:true	
		},{
			name : "valueParam",
			index : "valueParam",
			label : "选择项",
			width : 500,
			sortable : true,
			align : 'left'
		},{
			name : "选择",
			index : "选择",
			label : "选择参数",
			width : 80,
			sortable : true,
			align : 'left',
			formatter : function(cellValue, options, rowObject) {
				var str = "<a style=''   onclick=\"chooseQueryData('" + listSqlId + "','" + rowObject.keyParam + "','" + rowObject.valueParam + "')\">选择</a>"
				return str;
			}
	}],
		
		viewrecords : true,
		sortname : "keyParam",
		rowNum : 1000,
//		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#gridPagerListSql",
		hidegrid : false
	});*/
}
function chooseQueryData(keyParam,valueParam) {
	$("#attr_" + currentAttrId).val(keyParam);
//	alert($("input[name='keyParam']").val());
	$("#" + currentAttrId).val(valueParam);
	$( "#gridTableListSql" ).jqGrid().trigger("reloadGrid" );
	$("#attrListSqlDiv").dialog("close");
}
//显示服务请求使用的参数
function viewServiceAttr(rrinfoId,types,attrClass) {
   actionType=types;
	$("#cloud_attr_div").dialog({
		autoOpen : true,
		modal : true,
		width : 800
	});
	$("#cloud_attr_div").dialog("option", "title", i18nShow('my_req_finllIn_update'));
    cloudattrTableInit(attrClass);

	if (actionType == 'update') {
		$("#save_attr").show();
		$("#cancel_attr").show();
	} else if (actionType == 'view') {
		$("#save_attr").hide();
		$("#cancel_attr").show();
	}
	  $("#cloudattrTable").clearGridData();
	  $.post(ctx+"/ss/findCloudRequestById.action", {srId:rrinfoId}, function (data) {
         var attrList =JSON.parse(data);
     for ( var j = 0; j < attrList.length; j++) {
		var jsobj = {
			attrValId : attrList[j].attrValId,
			rrinfoId : attrList[j].rrinfoId,
			attrTypeCode : attrList[j].attrTypeCode,
			remark : attrList[j].remark,
			attrId : attrList[j].attrId,
			attrName : attrList[j].attrName,
			attrCname : attrList[j].attrCname,
			attrClass : attrList[j].attrClass,
			isRequire : attrList[j].isRequire,
			defVal : attrList[j].defVal,
			attrClass : attrList[j].attrClass,
			attrSelList : attrList[j].attrSelList,
			inputVal : attrList[j].attrValue
		};
		// 往参数表中插入记录
		$("#cloudattrTable").jqGrid("addRowData", j, jsobj);
	   }
	
		});
}
function saveCloudAttr(){

	// 校验保存输入填写的参数
		var attrList = new Array();
		var ids = $("#cloudattrTable").jqGrid('getDataIDs');
		var attr_id, attr_input, attr_need, attr_def, attrVal_id, rrInfo_id;
		for ( var j = 0; j < ids.length; j++) {
			attr_id = jQuery('#cloudattrTable').getCell(ids[j], "attrId");
			attr_need = jQuery('#cloudattrTable').getCell(ids[j], "isRequire");
			attr_def = jQuery('#cloudattrTable').getCell(ids[j], "defVal");
			attrVal_id = jQuery('#cloudattrTable').getCell(ids[j], "attrValId");
			rrInfo_id = jQuery('#cloudattrTable').getCell(ids[j], "rrinfoId");
			attr_input = $.trim($("#attr_" + attr_id).val());
			attr_name = jQuery('#cloudattrTable').getCell(ids[j], "attrName");
			attr_cname = jQuery('#cloudattrTable').getCell(ids[j], "attrCname");
			attrClass = jQuery('#cloudattrTable').getCell(ids[j], "attrClass");
			var attrSelList = eval(jQuery('#cloudattrTable').getCell(ids[j], "attrSelList"));
			attrTypeCode = jQuery('#cloudattrTable').getCell(ids[j], "attrTypeCode");
			remark = jQuery('#cloudattrTable').getCell(ids[j], "remark");

			if (attr_need == "Y" && attr_input == "") {
				attrList = null;
				showError(i18nShow('error_all_requre'));
				return;
			}
			var attrObj = {
				"value" : attr_input,
				"attrId" : attr_id,
				"attrValName" : attr_name,
				"attrValCname" : attr_cname,
				"attrValClass" : attr_class,
				"attrTypeCode" : attrTypeCode,
				"isRequire" : attr_need,
				"defVal" : attr_def,
				"attrValId" : attrVal_id,
				"rrinfoId" : rrInfo_id,
				"attrSelList" : attrSelList,
				"remark" : remark
			};
			attrList.push(attrObj);
		};
	  $.post(ctx+"/ss/findCloudRequestById.action", {
		  "_fw_service_id":"updateCloudRequestAttrSrv",
		    jsonData : JSON.stringify(attrList), 
		    jsonClass : "java.util.List",
		    rrinfoId:rrInfo_id
		    	}, 
		    function (data) {
	         showTip(i18nShow('tip_save_success'));
	         $("#cloud_attr_div").dialog("close");
	         
	  });
	
	
	
};

function checkDate(obj,type){
	WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2000-01-01 00:00:00',maxDate:'2099-12-31 23:59:59'});
}
function checkNum(obj,name){
	var rr= /^[0-9]{1,}(.[0-9]*)?$/;
	if(!rr.test(obj.value)){
		showTip(name+i18nShow('my_req_validate_tip'));
		obj.value='';
	} else {
		obj.value=obj.value*1.0;
	}
}

//点击提交按钮，提交服务请求
function submitRequest() {
    if(delRrinfoIdStr != ""){
	    delRrinfoIdStr = delRrinfoIdStr.substring(1,delRrinfoIdStr.length);
	    $.post(ctx+"/request/supply/deleteRrinfoByIds.action", {rrinfoIds:delRrinfoIdStr}, function (data) {
			       
	    });	
    }
	if(serviceObjMapList.length == 0) {
		showError(i18nShow('my_req_finllIn_du'));
		return;
	}
	
	
	var bmSr = {
			"srId" : $("#srId").val(),
			"srCode" : $("#srCode").val(),
			"srTypeMark" : $("#srTypeMark").val(),
			"appId" : $("#appId").val(),
			"datacenterId" : $("#datacenterId").val(),
			"startTimeStr" : $("#serviceBeginTimes").val(),
			"endTimeStr" : $("#serviceEndtimes").val(),
			"summary" : $("#summary").val(),
			"remark" : $("#describe").val(),
			"creatorId" : $("#userId").val()
		};
	var temp = [];
	for(var i =0 ;i<virtualSupplyParam.length;i++){
		temp.push({
			"parametersJson":JSON.stringify(virtualSupplyParam[i])
		})
	}
	var virtualSupplyVo = {
		"bmSr" : bmSr,
		"rrinfoList" :temp,
	}
	$("#submit_btn").attr("disabled", "disabled");
	$("#submit_btn").removeClass("btn").addClass('btn_disabled');
	if (actionType == 'new') {
		showTip("load");
		$.post(ctx + "/request/supply/saveVirtualSupply.action", {
			jsonData : JSON.stringify(virtualSupplyVo),
			jsonClass : "com.git.cloud.request.model.vo.VirtualSupplyVo",
			parametersJson:JSON.stringify(temp)
		}, function(data) {
			if(data.result!=null && data.result=="success"){
				showTip(i18nShow('my_req_save_successTip')+ data.message);
				closeTip();
				initCloudRequestList();
			}else{
				closeTip();
				showError(i18nShow('my_req_save_failTip')+data.result);
				$("#submit_btn").attr("disabled", true);
				$("#submit_btn").removeClass("btn_disabled").addClass('btn');
			}
		}).error(function() {
			closeTip();
			showError(i18nShow('compute_res_error'),null,"red");
			$("#submit_btn").attr("disabled", true);
			$("#submit_btn").removeClass("btn_disabled").addClass('btn');
		});
	} else if (actionType == 'update') {
		showTip("load");
		$.post(ctx + "/request/supply/saveVirtualSupply.action", {
			jsonData : JSON.stringify(virtualSupplyVo),
			jsonClass : "com.git.cloud.request.model.vo.VirtualSupplyVo",
			todoId : todoId
		}, function(data) {
			if (data.result!=null && data.result=="success") {
				showTipNoCancel(i18nShow('my_req_submit_successTip'), function () {
					history.go(-1);
				});
			} else {
				showError(i18nShow('my_req_submit_failTip') + data.result);
				$("#submit_btn").attr("disabled", true);
				$("#submit_btn").removeClass("btn_disabled").addClass('btn');
			}
		}).error(function() { 
			showError(i18nShow('compute_res_error'),null,"red");
			$("#submit_btn").attr("disabled", true);
			$("#submit_btn").removeClass("btn_disabled").addClass('btn');
		});
	}
}

function getRrinfoList() {
	var rrinfoList = new Array();
	var ids = $("#serviceAppliedTable").jqGrid('getDataIDs');
	var rrinfo_id,service_id,du_id,cpu,mem,sysDisk,vmNum;
	var parameterStr;
	var bmsrAttrVals;
	for ( var i = 0; i < ids.length; i++) {
		rrinfo_id = jQuery('#serviceAppliedTable').getCell(ids[i], "rrinfoId");
		service_id = jQuery('#serviceAppliedTable').getCell(ids[i], "serviceId");
		du_id = jQuery('#serviceAppliedTable').getCell(ids[i], "duId");
		parameterStr = jQuery('#serviceAppliedTable').getCell(ids[i], "parameterStr");
//		bmsrAttrVals = jQuery('#serviceAppliedTable').getCell(ids[i], "bmsrAttrVals");
		var paramList = parameterStr.split(",");
		cpu = paramList[0];
		mem = paramList[1];
		sysDisk = paramList[2];
		vmNum = paramList[3];
		if(attrListMap[du_id]!=null){
			var attrValList = attrListParse(attrListMap[du_id]);
		}else{
			var attrValList = new Array(); // getAttrValList();没有参数的就不需要取得参数list了 by wangdy
		}
				
		var serviceObj = {
			"rrinfoId" : rrinfo_id,
			"serviceId" : service_id,
			"duId" : du_id,
			"cpu" : cpu,
			"mem" : mem,
			"sysDisk" : sysDisk,
			"vmNum" : vmNum,
			"attrValList" : attrValList
		};
		rrinfoList.push(serviceObj);			
		}
	return rrinfoList;
}
function getAttrValList1(duid){
	if(attrListMap[duid]!=null){
		return attrListMap[duid];
	}else{
		var attrList = new Array();
		var ids = $("#attrTable").jqGrid('getDataIDs');
		var attr_id, attr_input, attr_name,attr_cname,attr_class,attrTypeCode,attrType,remark,attr_need, attr_def,attrVal_id,rrInfo_id;
		for (var j = 0; j < ids.length ; j++) {
			attr_id = jQuery('#attrTable').getCell(ids[j], "attrId");
			attr_need = jQuery('#attrTable').getCell(ids[j], "isRequire");
			attr_def = jQuery('#attrTable').getCell(ids[j], "defVal");
			attr_input = $("#attr_" + attr_id).val();
			attr_name = jQuery('#attrTable').getCell(ids[j], "attrName");
			attr_cname = jQuery('#attrTable').getCell(ids[j], "attrCname");
			attr_class = jQuery('#attrTable').getCell(ids[j], "attrClass");
			attrTypeCode = jQuery('#attrTable').getCell(ids[j], "attrTypeCode");
			attrType = jQuery('#attrTable').getCell(ids[j], "attrType");
			remark = jQuery('#attrTable').getCell(ids[j], "remark");
			attrVal_id = jQuery('#attrTable').getCell(ids[j], "attrValId");
			rrInfo_id = jQuery('#attrTable').getCell(ids[j], "rrinfoId");
			var attrObj = {
				"attrValue" : attr_input,
				"attrId" : attr_id,
				"attrName" : attr_name,
				"attrCname" : attr_cname,
				"attrClass" : attr_class,
				"attrTypeCode" : attrTypeCode,
				"isRequire" : attr_need,
				"defVal" : attr_def,
				"srAttrValId" : attrVal_id,
				"rrinfoId" : rrInfo_id,
				"attrType":attrType,
				"remark" : remark
			};
			attrList.push(attrObj);
		};	
		return attrList;		
	}
}
function getAttrValList(){
	var attrList = new Array();
	var ids = $("#attrTable").jqGrid('getDataIDs');
	var attr_id, attr_input, attr_name,attr_cname,attr_class,attrTypeCode,attrType,remark,attr_need, attr_def,attrVal_id,rrInfo_id;
	for (var j = 0; j < ids.length ; j++) {
		attr_id = jQuery('#attrTable').getCell(ids[j], "attrId");
		attr_need = jQuery('#attrTable').getCell(ids[j], "isRequire");
		attr_def = jQuery('#attrTable').getCell(ids[j], "defVal");
		attr_input = $("#attr_" + attr_id).val();
		attr_name = jQuery('#attrTable').getCell(ids[j], "attrName");
		attr_cname = jQuery('#attrTable').getCell(ids[j], "attrCname");
		attr_class = jQuery('#attrTable').getCell(ids[j], "attrClass");
		attrTypeCode = jQuery('#attrTable').getCell(ids[j], "attrTypeCode");
		attrType = jQuery('#attrTable').getCell(ids[j], "attrType");
		remark = jQuery('#attrTable').getCell(ids[j], "remark");
		attrVal_id = jQuery('#attrTable').getCell(ids[j], "attrValId");
		rrInfo_id = jQuery('#attrTable').getCell(ids[j], "rrinfoId");
		var attrObj = {
			"attrValue" : attr_input,
			"attrId" : attr_id,
			"attrName" : attr_name,
			"attrCname" : attr_cname,
			"attrClass" : attr_class,
			"attrTypeCode" : attrTypeCode,
			"isRequire" : attr_need,
			"defVal" : attr_def,
			"srAttrValId" : attrVal_id,
			"rrinfoId" : rrInfo_id,
			"attrType":attrType,
			"remark" : remark
		};
		attrList.push(attrObj);
	};	
	return attrList;
}

function attrListParse(obj){ 
	var attrList = new Array();
	var attr_id, attr_input, attr_name,attr_cname,attr_class,attrTypeCode,attrType,remark,attr_need, attr_def,attrVal_id,rrInfo_id;
	for (var j = 0; j < obj.length ; j++) {
		attr_id = obj[j].attrId;
		attr_need = obj[j].isRequire;
		attr_def = obj[j].defVal;
		attr_input = obj[j].attrValue;
		attr_name = obj[j].attrName;
		attr_cname = obj[j].attrCname;
		attr_class = obj[j].attrClass;
		attrTypeCode = obj[j].attrTypeCode;
		attrType = obj[j].attType;
		remark = obj[j].remark;
		attrVal_id = obj[j].attrValId;
		rrInfo_id = obj[j].rrinfoId;
		var attrObj = {
			"attrValue" : attr_input,
			"attrId" : attr_id,
			"attrName" : attr_name,
			"attrCname" : attr_cname,
			"attrClass" : attr_class,
			"attrTypeCode" : attrTypeCode,
			"isRequire" : attr_need,
			"defVal" : attr_def,
			"srAttrValId" : attrVal_id,
			"rrinfoId" : rrInfo_id,
			"attrType":attrType,
			"remark" : remark
		};
		attrList.push(attrObj);
	};	
	return attrList;
}

//获取服务目录属性
function getServiceAttrSize(serviceId){
	var paramObj = {"serviceId":serviceId,"SERVICE_ID":serviceId};
	var flag =true;
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/request/supply/getAttrListSize.action",//请求的action路径   
		data : {"serviceId":serviceId},
		error : function() {//请求失败处理函数   
		},
		success : function(data) { //请求成功后处理函数。     
		    if(data.size<=0){
		    	flag = false;
		    }
		}
	});
	  return flag;
}