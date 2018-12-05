var options;
var _height = 260;
/**
 * 初始化表格
 */
$(document).ready(function() {
	formAction();
	initMainGrid();
	// 自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
	});
});
//审批页面
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
			initRecycleGrid(srId);
			if(srType == 'VR'){
		    	srTypeStr = i18nShow('my_req_service_recovery_vs');
		    }else if(srType == 'PR'){
		    	srTypeStr = i18nShow('my_req_service_recovery_ps');
		    }
		} else if (srType == 'VE') { // 扩容
			initExtendGrid(srId);
			srTypeStr = i18nShow('my_req_service_expand_ve');
		} else if(srType == 'SA'){ //服务自动化
			//step1，待审批页面的信息显示
			initServiceAutoGrid(srId);
			srTypeStr = i18nShow('my_req_service_auto');
		} else if(srType == 'SA_ADD_VOLUME'){
			srTypeStr = i18nShow('my_req_service_auto_addVolume');
			initServiceAutoGrid_addVolume(srId)
		}else if(srType == 'PEA'){
			srTypeStr = "定价审批";
			initServicePriceGrid_approve(srId)
		}
	} else {
		showTip(i18nShow('my_req_sr_id_null'));
	}
	document.getElementById("titlePage").innerHTML = srTypeStr;
}

function initServicePriceGrid_approve(srId) {
	$("#gridTable").jqGrid({
		url : ctx + '/bill/query/queryBmSrRrinfoPriceApproval.action?srId=' + srId
						+ '', // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		postData :{
		},
		autowidth : true, // 是否自动调整宽度
		colModel : [
		{
			name : "categoryType",
			index : "categoryType",
			label : '产品类型',
			width : 50,
			sortable : true,
			align : 'left',
			hidden:true
		}, {
			name : "mainProductName",
			index : "mainProductName",
			label : '主产品名称',
			width : 50,
			sortable : true,
			align : 'left'
		},{
			name : "categoryName",
			index : "categoryName",
			label : '子产品名称',
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			name : "billingCycle",
			index : "billingCycle",
			label : '计费周期',
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			name : "billingMode",
			index : "billingMode",
			label : '计费模式编码',
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			name : "modeCode",
			index : "modeCode",
			label : '行业类型编码',
			width : 50,
			sortable : true,
			align : 'left'
		},{
			name : "pricePolicyName",
			index : "pricePolicyName",
			label : '策略名称',
			width : 50,
			sortable : true,
			align : 'left'
		},{
			name : "specTypeName",
			index : "specTypeName",
			label : '元素名称',
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			label : '查看产品策略',
			index : "pricePolicy",
			name  : 'pricePolicy',
			width : 50,
			align : 'left',
			formatter : function(cellValue, options,
						rowObject) {
				return  "<a href='javascript:;' style=' text-decoration:none' onclick=\"showCategoryInfo('"+cellValue+"')\" >查看</a>";
			}	
		}],
		viewrecords : true,
		sortname : "operateTime",
		sortorder : "desc",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "searchLog"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#gridPager",
		hidegrid : false
	});
};
function showCategoryInfo(id){
	$("#showCategoryInfo").dialog({
		autoOpen : true,
		modal : true,
		height : 300,
		width : 900,
		position : "middle",
		title :'查看定价策略',
	});
	$("#gridTablePriceApproval").jqGrid("GridUnload");
	$("#gridTablePriceApproval").jqGrid({
		url : ctx + '/bill/query/queryPriceStrategyById.action?srId=' + srId
						+ '', // 提交的action地址
		rownumbers : false, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		postData :{
		},
		autowidth : true, // 是否自动调整宽度
		colModel : [
			{
				name : "policyType",
				index : "policyType",
				label : '定价类型',
				width : 50,
				sortable : true,
				align : 'left',
				hidden:false,
				formatter :function(cellValue, options,rowObject){
					if(cellValue == 'EXPR'){
						return "表达式";
					}else if(cellValue == 'CONST' ||  cellValue == 'ENUM'){
						return "常量";
					}
				}
			},{
			name : "price",
			index : "price",
			label : '价格',
			width : 50,
			sortable : true,
			align : 'left',
			formatter :function(cellValue, options,rowObject){
				if(rowObject.policyType == 'EXPR'){
					return cellValue;
				}else if(rowObject.policyType == 'CONST' ||  rowObject.policyType == 'ENUM'){
					return cellValue/1000;
				}
				//return cellValue/1000;
			}
		},{
			name : "priceType",
			index : "priceType",
			label : '计费方式',
			width : 50,
			sortable : true,
			align : 'left'
		}/*, {
			index : "factorVoInfo",
			name  : 'factorVoInfo',
			label : '元素信息',
			width : 50,
			align : 'left'
		}*/],
		viewrecords : true,
		sortname : "policyType",
		sortorder : "desc",
		rowNum : 200,
		//rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "searchLog"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#gridPagerPriceApproval",
		hidegrid : false
	});
};
//服务自动化，添加卷
function initServiceAutoGrid_addVolume(srId){

	$("#gridTable")
	.jqGrid(
			{
				url : 'request/base/queryBmSrRrinfoResoureListAuto.action?srId=' + srId
						+ '',
				datatype : "json",
				mtype : "post", // 提交方式
				rownumbers : true,
				autowidth : true, // 是否自动调整宽度
				colModel : [
						{
							name : 'attNanme',
							index : 'attNanme',
							label : '参数名',
							sortable : true,
							align : "left",
							hidden : false
						},
						{
							name : 'attValue',
							index : 'attValue',
							label:'参数值',
							width : 80,
							sortable : true,
							align : "left",
							formatter : function (cellValue,options,rowObject){
								if(rowObject.attNanme !='PROJECT_ID' ){
									return rowObject.attValue;
									
								}else{
									return rowObject.nameValue+"("+rowObject.attValue+")";
									
								}
								//return rowObject.attValue;
			              }
						},
						{
							name : 'nameValue',
							index : 'nameValue',
							label : "nameValue",
							width : 80,
							sortable : true,
							align : "left",
							hidden : true
							
						}],
				viewrecords : true,
				sortname : "attNanme",
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
}

//服务自动化
function initServiceAutoGrid(srId){

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
					i18nShow('my_req_sr_host_info'),i18nShow('my_req_sr_plat_form'),i18nShow('my_req_sr_volume_type'), i18nShow('my_req_sr_storage'), /*'输入参数' ,*/ i18nShow('my_req_sr_net')],
				colModel : [
						{
							name : 'rrinfoId',
							index : 'rrinfoId',
							sortable : true,
							align : "left",
							hidden : true
						},
						{
							name : 'confParameter',
							index : 'confParameter',
							width : 80,
							sortable : true,
							align : "left"
						},
						{
							name : 'duName',
							index : 'duName',
							width : 80,
							sortable : true,
							align : "left",
							formatter : function (cellValue,options,rowObject){
			                    return i18nShow('my_req_sr_chinese') +cellValue+"\n"+ i18nShow('my_req_sr_english') +rowObject.duEname;
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
							width : 85,
							height : 80,
							resizable : true,
							sortable : true,
							align : "left",
							formatter : function(cellValue, options,
									rowObject) {
								if (rowObject.vmName == null) {
									return cellValue;
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
													+ rowObject.mem
													+ "M</div>");
									return html.join("");
								}
							}
						},
						{
							name : 'resPoolName',
							index : 'resPoolName',
							width : 95,
							sortable : true,
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
									html.push("<div>"+i18nShow('my_req_sr_cluster')+": "
											+ rowObject.clusterName
											+ "</div>");
									html.push("<div>"+i18nShow('my_req_sr_host')+": "
											+ rowObject.deviceName
											+ "</div>");
									return html.join("");
								}
							}
						},
						{
							name : 'platFormCode',
							index : 'platFormCode',
							width : 50,
							hidden : true
						},
						{
							name : 'volumeType',
							index : 'volumeType',
							width : 50,
							hidden : true
						},{
							name : 'datastoreName',
							index : 'datastoreName',
							width : 40,
							sortable : true,
							align : "left",
							formatter : function(cellValue, options,
									rowObject) {
								if(rowObject.platFormCode == 'O'){
									 if(rowObject.volumeType !=null && rowObject.volumeType !=""){
										 return rowObject.volumeType;
									 }else{
										 return "";
									 }
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
						}/*,
						{
							align : "left",
							width : 30,
							formatter : function(cellValue, options, rowObject) {
								//新流程中， dcm审核后自定义参数不做修改
								alert(attrRrinfoIds);
								if(attrRrinfoIds.indexOf(rowObject.rrinfoId) > 0) {
									return "<a href='javascript:;' style=' text-decoration:none' onclick=\"detailFromTable('" + options.rowId + "','" + rowObject.duId + "','" + rowObject.rrinfoId + "')\" >查看</a>";
								} else {
									return "";
								}
							}
						}*/, {
							name : 'vidept',
							index : 'cdpName',
							width : 100,
							align : 'left',
							sortable : false,
							formatter : function(cellValue, options, rowObject) {
								var vmdevid = rowObject.vmId;
								return "<div id='" + vmdevid + "'>loading....</div>";
							}
						} ],
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
				hidegrid : false,
				gridComplete : function() {
					getVmdeviceNet();
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

		$.post(ctx+"/request/base/getVmNetIp.action", {'rrinfoId' : rrinfoid,'vmdeviceId' : vmdeviceid}, function(data){
			var ips = "";
			for(var i=0 ; i<data.ipList.length ; i++) {
				ips += "<tr><td style='border: 0px solid #abc6d1;width:25%;'>"+data.ipList[i].rm_ip_type_name+"：</td><td style='border: 0px solid #abc6d1'><label id=''>"+data.ipList[i].ip+"</label></td></tr>";
			}
			$("#" + data.id + "").html("<table id='ip_table' class='ip_table' cellspacing='0' cellpadding='0' style='border-collapse:collapse;width:100%'>" + ips + "</table>");
		});

	};
}
// 供给待审批列表
function initSupplyGrid(srId) {
	$("#gridTable").jqGrid({
		url : "request/base/queryBmSrRrinfoList.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		postData : {
			"srId" : srId
		},
		mtype : "post", // 提交方式
		height : 98,
		autowidth : true, // 是否自动调整宽度
		colModel : [ {
			name : "duName",
			index : "duName",
			label : i18nShow('my_req_sr_duName'),
			width : 120,
			sortable : false,
			align : 'left',
			formatter : function (cellValue,options,rowObject){
				var duCname = cellValue ? cellValue : "";
				var duEname = rowObject.duEname ? rowObject.duEname : "";
				return i18nShow('my_req_sr_chinese')+duCname+"\n"+i18nShow('my_req_sr_english')+duEname;
			}
		}, {
			name : "serviceName",
			index : "serviceName",
			label : i18nShow('my_req_sr_service_name'),
			width : 120,
			sortable : false,
			align : 'left'
		}, {
			name : "parametersJson",
			index : "confParameter",
			label : i18nShow('my_req_sr_confParameter'),
			width : 120,
			sortable : false,
			align : 'left',
			formatter : function (cellValue,options,rowObject){
				cellValue = JSON.parse(cellValue);
				str = "CPU="+cellValue.cpu+" MEM="+parseInt(cellValue.mem)/1024+"G "+"<br/>";
				str += "SYS DISK="+cellValue.sysDisk+"G DATA DISK="+cellValue.dataDisk+"G";
				return str;
			}
		}, {
			name : "parametersJson",
			index : "vmNum",
			label : i18nShow('my_req_sr_vm_count'),
			width : 120,
			sortable : false,
			align : 'left',
			formatter : function (cellValue,options,rowObject){
				cellValue = JSON.parse(cellValue);							
				return cellValue.vmNum;
			}
		} ],
		viewrecords : true,
		sortname : "duId",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
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

// 回收
function initRecycleGrid(srId) {
	$("#gridTable").jqGrid({
		datatype : 'local',
		height : 180,
		autowidth : true, 
		colNames : [i18nShow('my_req_sr_duId'), i18nShow('my_req_sr_vmdeviceId'), i18nShow('my_req_sr_deviceType'), i18nShow('my_req_sr_haType'), i18nShow('my_req_sr_duName'), i18nShow('my_req_sr_vmName'),i18nShow('my_req_sr_vm_conf'), 'IP'],
		colModel : [
				{
					name : 'duId',
					index : 'duId',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'deviceId',
					index : 'deviceId',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'deviceTypeCode',
					index : 'deviceTypeCode',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'haTypeCode',
					index : 'haTypeCode',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'du',
					index : 'du',
					width : 3,
					sortable : false,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						return i18nShow('my_req_sr_chinese') +cellValue+"\n"+ i18nShow('my_req_sr_english') +rowObject.duEname;
					}
				},{
					name : 'device',
					index : 'device',
					width : 3,
					sortable : false,
					align : "left"
				},{
					name : 'deviceParamDef',
					index : 'deviceParamDef',
					width : 3,
					sortable : false,
					align : "left"
				},{
					name : 'deviceIps',
					index : 'deviceIps',
					width : 5,
					sortable : false,
					align : "left"
				}],
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		pager : '#pageNav_recycle',
		rownumbers : true,
		viewrecords : true,
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		}
	});
	$.post(ctx + '/request/recycle/getVirtualRecycleVoBySrId.action', {srId:srId}, function(result) {
		var requestObj = result;
		var deviceVoArr = requestObj.cmVmList;
		for(var i=0 ; i<deviceVoArr.length ; i++) {
			var parameter_def = "cpu=" + deviceVoArr[i].cpu + " "+i18nShow('my_req_sr_memery')+"=" + deviceVoArr[i].mem/1024 + "G "+i18nShow('my_req_sr_disk')+"=" + deviceVoArr[i].disk + "G";
			var deviceVoObj = {
				duId : 'DU:' + deviceVoArr[i].duId,
				deviceId : deviceVoArr[i].id,
				deviceTypeCode : "",
				haTypeCode : "",
				du : deviceVoArr[i].duEname == undefined ? "":deviceVoArr[i].duName,
				duEname : deviceVoArr[i].duEname == undefined ? "":deviceVoArr[i].duEname,
				device : deviceVoArr[i].deviceName,
				deviceParamDef : parameter_def,
				deviceIps : deviceVoArr[i].cmVmIps
			};
			$("#gridTable").jqGrid("addRowData", i, deviceVoObj);
		}
	});
}

// 扩容
function initExtendGrid(srId) {
	$("#gridTable").jqGrid({
		url : 'request/base/queryBmSrRrinfoExtendByParam.action?srId=' + srId + '',
		datatype : "json",
		height : 220,
		mtype : "post", // 提交方式
		rownumbers : true,
		autowidth : true, // 是否自动调整宽度
		colModel : [ {
			name : 'rrinfoId',
			index : 'rrinfoId',
			label : i18nShow('my_req_sr_rrinfoId'),
			width : 80,
			align : 'left',
			sortable : false,
			hidden : true
		}, {
			name : 'duName',
			index : 'duName',
			label : i18nShow('my_req_sr_duName'),
			width : 50,
			sortable : false,
			align : "left",
			formatter : function (cellValue,options,rowObject){
				var duCname = cellValue ? cellValue : "";
				var duEname = rowObject.duEname ? rowObject.duEname : "";
				return i18nShow('my_req_sr_chinese')+duCname+"\n"+i18nShow('my_req_sr_english')+duEname;
          }
		}, {
			name : 'vmName',
			index : 'vmName',
			label : i18nShow('my_req_sr_vmName'),
			width : 80,
			align : 'left',
			sortable : false
		}, {
			name : 'oldConfParameter',
			index : 'oldConfParameter',
			label : i18nShow('my_req_sr_vm_conf_old'),
			width : 80,
			sortable : false,
			align : "left",
			formatter : function (cellValue,options,rowObject){
				var oldConfig="";
				//x86类型的虚拟机，显示磁盘配置
				oldConfig = "CPU="+rowObject.cpuOld+"C MEM="+parseInt(rowObject.memOld)/1024+"G "+"<br>"
				if(rowObject.platformTypeCode =='X'){
					oldConfig += "SYS DISK="+rowObject.diskOld+"G "+"DATA DISK="+rowObject.externalDiskSum;
				}
				return oldConfig;
          }
		}, {
			name : 'parametersJson',
			index : 'parametersJson',
			label : i18nShow('my_req_sr_vm_conf_new'),
			width : 120,
			align : "left",
			sortable : false,
			formatter : function (cellValue,options,rowObject){
				var newConfig="";
                var jsonObj = JSON.parse(cellValue);
				//x86类型的虚拟机，显示磁盘配置
				newConfig = "CPU="+jsonObj.cpu+"C MEM="+parseInt(jsonObj.mem)/1024+"G "+"<br>"
				if(rowObject.platformTypeCode =='X'){
					newConfig += "SYS DISK="+rowObject.diskOld+"G "+"DATA DISK="+(rowObject.externalDiskSum+jsonObj.dataDisk);
				}
				return newConfig;
          }
		},
		{
			name : 'cpuOld',
			index : 'cpuOld',
			label : 'cpuOld',
			width : 120,
			align : "left",
			hidden : true,
			sortable : false
		},
		{
			name : 'memOld',
			index : 'memOld',
			label : 'memOld',
			width : 120,
			align : "left",
			hidden : true,
			sortable : false
		},
		{
			name : 'cpu',
			index : 'cpu',
			label : 'cpu',
			width : 120,
			align : "left",
			hidden : true,
			sortable : false
		},
		{
			name : 'mem',
			index : 'mem',
			label : 'mem',
			width : 120,
			align : "left",
			hidden : true,
			sortable : false
		},
		{
			name : 'diskOld',
			index : 'diskOld',
			label : 'diskOld',
			width : 120,
			align : "left",
			hidden : true,
			sortable : false
		},
		{
			name : 'externalDiskSum',
			index : 'externalDiskSum',
			label : 'externalDiskSum',
			width : 120,
			align : "left",
			hidden : true,
			sortable : false
		},
		{
			name : 'platformTypeCode',
			index : 'platformTypeCode',
			label : 'platformTypeCode',
			width : 120,
			align : "left",
			hidden : true,
			sortable : false
		}],
		viewrecords : true,
		sortname : "srCode",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
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
/**
 * 初始化form提交方式
 */
function formAction() {
	options = {
		beforeSubmit : (function(mypara, myobj, myother) {
			showTip("load");
			var queryString = $('#form_approve').formSerialize();
			$("#submit_btn").attr("disabled",true);
			$.post(baseUrl + "/request/base/saveApprove.action", queryString, function(data) {
				closeTip();
				if (data.result == 'success') {
					showTip(i18nShow('my_req_approve_success'), function() {
						history.go(-1);
					});
				} else if (data.result == 'error') {
					showTip(i18nShow('my_req_approve_fail'), function() {
						$("#submit_btn").attr("disabled",false);
					});
				} else if (data.result == 'exception') {
					showTip(i18nShow('my_req_approve_error'), function() {
						$("#submit_btn").attr("disabled",false);
					});
				} else {
					showTip(data.result, function() {
						$("#submit_btn").attr("disabled",false);
					});
				}
			});
			return false;
		})
	};
}
/**
 * 提交表单
 */
function submitFrm() {
	$('form_approve').ajaxSubmit(options);
	return false;
}