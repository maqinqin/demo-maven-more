/**
 初始化表格
 */
 var _height = 180;
$(document).ready(function() {
	initMainGrid();
	attrTableInit();//初始化属性列表
	//自适应宽度
	$("#gridTable1").setGridWidth($("#gridMain1").width()-1);
	$("#gridTable2").setGridWidth($("#gridMain2").width()-1);
	$("#gridTable3").setGridWidth($("#gridMain3").width()-1);
	$(window).resize(function() {
		$("#gridTable1").setGridWidth($("#gridMain1").width());
		$("#gridTable2").setGridWidth($("#gridMain2").width());
		$("#gridTable3").setGridWidth($("#gridMain3").width());
    });
});

//首页主grid初始化
function initMainGrid() {
		var srTypeStr = "";
		if (srId != "" && srId != null && srId != undefined) {
		  
			if ((srType == 'VS') || (srType == 'PS')) { // 供给
			    initSupplyGrid1(srId);
			    if(srType == 'VS'){
					srTypeStr = i18nShow('my_req_service_supply_vs');
				}else if(srType == 'PS'){
					srTypeStr = i18nShow('my_req_service_supply_ps');
				}
				if(srStatusCode == 'REQUEST_WAIT_OPERATE' || srStatusCode == 'REQUEST_OPERATING' || srStatusCode == 'REQUEST_ASSIGN_SUCCESS'){
				    $("#title2").show();
				    $("#gridMain2").show();
				    initSupplyGrid2(srId);//预分配信息
				}else if(srStatusCode == 'REQUEST_WAIT_CLOSE' || srStatusCode == 'REQUEST_CLOSED'){
				    $("#gridMain3").show();
				    $("#title3").show();
				    initSupplyGrid3(srId);//交付信息
				}
			} else if (srType == 'VR' || srType == 'PR') { // 回收
				initRecycleGrid1(srId);
				if(srType == 'VR'){
			    	srTypeStr = i18nShow('my_req_service_recovery_vs');
			    }else if(srType == 'PR'){
			    	srTypeStr = i18nShow('my_req_service_recovery_ps');
			    }
			} else if (srType == 'VE') { // 扩容
			        $("#title1").hide();
				    $("#gridMain1").hide();
				    srTypeStr = i18nShow('my_req_service_expand_ve');
				if(srStatusCode == 'REQUEST_WAIT_OPERATE' || srStatusCode == 'REQUEST_OPERATING'  || srStatusCode == 'REQUEST_ASSIGN_SUCCESS'){
				    $("#title2").show();
				    $("#gridMain2").show();
				    initExtendGrid2(srId);
				}else if(srStatusCode == 'REQUEST_WAIT_CLOSE' || srStatusCode == 'REQUEST_CLOSED'){
				     $("#title3").show();
				     $("#gridMain3").show();
				     initExtendGrid3(srId);
				}else {
				    $("#title1").show();
				    $("#gridMain1").show(); 
				   initExtendGrid1(srId);
				}
			}else if (srType == 'SA'){
				//服务自动化所有的显示详情
				srTypeStr = i18nShow('my_req_service_auto');
				 $("#title1").show();
				 $("#gridMain1").show(); 
				 initServiceAutoGrid(srId)
			}else if(srType == "SA_ADD_VOLUME"){
				srTypeStr = i18nShow('my_req_service_auto_addVolume');
				 $("#title1").show();
				 $("#gridMain1").show(); 
				 initServiceAutoGrid_addVolume(srId)
			}else if(srType == 'PEA'){
				srTypeStr = "定价审批";
				initServicePriceGrid_approve(srId)
			}
		} else {
			showTip(i18nShow('my_req_sr_id_null'));
		}
		document.getElementById("titlePage1").innerHTML=srTypeStr;//资源申请信息
		document.getElementById("titlePage2").innerHTML=srTypeStr;//预分配信息
		document.getElementById("titlePage3").innerHTML=srTypeStr;//交付信息
}
function initServicePriceGrid_approve(srId) {
	$("#gridTable1").jqGrid({
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
	$("#showCategoryInfoDetail").dialog({
		autoOpen : true,
		modal : true,
		height : 300,
		width : 900,
		position : "middle",
		title :'查看定价策略',
	});
	$("#gridTablePriceApprovalDetail").jqGrid("GridUnload");
	$("#gridTablePriceApprovalDetail").jqGrid({
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
				return cellValue/1000;
			}
		},{
			name : "priceType",
			index : "priceType",
			label : '计费方式',
			width : 50,
			sortable : true,
			align : 'left'
		}, {
			index : "factorVoInfo",
			name  : 'factorVoInfo',
			label : '元素信息',
			width : 50,
			align : 'left'
		}],
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
		pager : "#gridPagerPriceApprovalDetail",
		hidegrid : false
	});
};
//服务自动化，添加卷
function initServiceAutoGrid_addVolume(srId){
	$("#gridTable1")
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
	$("#gridTable1")
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
									if(rowObject.lparNamePrefix) {
										html.push("<div>"+i18nShow('my_req_sr_LPAR')+": "
												+ rowObject.lparNamePrefix
												+ "</div>");
									}
									return html.join("");
								}
							}
						}  ,
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
                       },
						{
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
				pager : "#gridPager1",
				hidegrid : false,
				gridComplete : function() {
					getVmdeviceNetAuto();
				}
			});


}

function getVmdeviceNetAuto() {
	var tmp_ids = $("#gridTable1").getDataIDs();
	var rrinfoid = "";
	var vmdeviceid = "";
	for ( var t = 0; t < tmp_ids.length; t++) {

		var tmp_data = $("#gridTable1").getRowData(tmp_ids[t]);

		rrinfoid = tmp_data.rrinfoId;
		vmdeviceid = tmp_data.vmId;
		$.post(ctx+"/request/base/getVmNetIpOpenstack.action", {'rrinfoId' : rrinfoid,'vmdeviceId' : vmdeviceid}, function(data){
			var ips = "";
			for(var i=0 ; i<data.ipList.length ; i++) {
				ips += "<tr><td style='border: 0px solid #abc6d1;width:25%;'>"+data.ipList[i].rm_ip_type_name+"：</td><td style='border: 0px solid #abc6d1'><label id=''>"+data.ipList[i].ip+"</label></td></tr>";
			}
			$("#" + data.id + "").html("<table id='ip_table' class='ip_table' cellspacing='0' cellpadding='0' style='border-collapse:collapse;width:100%'>" + ips + "</table>");
		});

	};
}

//资源申请信息（供给）
function initSupplyGrid1(srId) {
	$("#gridTable1").jqGrid({
		url : "request/base/queryBmSrRrinfoList.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		postData : {
			"srId" : srId
		},
		mtype : "post", // 提交方式
		height : _height,
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
			width : 150,
			sortable : false,
			align : 'left',
			formatter : function (cellValue,options,rowObject){
				cellValue = JSON.parse(cellValue);
				var str = "CPU="+cellValue.cpu+"C MEM="+cellValue.mem+"M "+"<br/>";
				str += "SYS DISK="+cellValue.sysDisk+"G DATA DISK="+cellValue.dataDisk+"G";
				return str;
			}
		}, {
			name : "parametersJson",
			index : "vmNum",
			label : i18nShow('my_req_sr_vm_count'),
			width : 90,
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
		pager : "#gridPager1",
		//caption : "资源信息申请",
		hidegrid : false
	});
}

//资源申请信息（回收）		
function initRecycleGrid1(srId) {
	$("#gridTable1").jqGrid({
		datatype : 'local',
		height : 180,
		autowidth : true, 
		//width : _width,
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
						return i18nShow('my_req_sr_chinese') +((cellValue==null||cellValue=="")?"":cellValue)+"\n"+ i18nShow('my_req_sr_english') +((rowObject.duEname==null||rowObject.duEname=="")?"":rowObject.duEname);
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
			var parameter_def = "cpu=" + deviceVoArr[i].cpu + " "+i18nShow('my_req_sr_memery')+"=" + deviceVoArr[i].mem + "M "+i18nShow('my_req_sr_disk')+"=" + deviceVoArr[i].disk + "G";
			var deviceVoObj = {
				duId : 'DU:' + deviceVoArr[i].duId,
				deviceId : deviceVoArr[i].id,
				deviceTypeCode : "",
				haTypeCode : "",
				du : deviceVoArr[i].duName,
				duEname : deviceVoArr[i].duEname,
				device : deviceVoArr[i].deviceName,
				deviceParamDef : parameter_def,
				deviceIps : deviceVoArr[i].cmVmIps
			};
			$("#gridTable1").jqGrid("addRowData", i, deviceVoObj);
		}
	});
}

//资源申请信息（扩容）
function initExtendGrid1(srId) {
	$("#gridTable1").jqGrid({
		url : 'request/base/queryBmSrRrinfoExtendByParam.action?srId=' + srId + '',
		datatype : "json",
		height : _height,
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
		},{
			name : 'oldConfParameter',
			index : 'oldConfParameter',
			label : i18nShow('my_req_sr_vm_conf_old'),
			width : 80,
			sortable : false,
			align : "left",
			formatter : function (cellValue,options,rowObject){
				var oldConfig="";
				//x86类型的虚拟机，显示磁盘配置
				oldConfig = "CPU="+rowObject.cpuOld+"C MEM="+parseInt(rowObject.memOld)/1024 + "G " + "<br>"
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
				//x86类型的虚拟机，显示磁盘配置
				newConfig = "CPU="+rowObject.cpu+"C MEM="+parseInt(rowObject.mem)/1024+"G "+"<br>"
				if(rowObject.platformTypeCode =='X'){
					newConfig += "SYS DISK="+rowObject.diskOld+"G "+"DATA DISK="+(rowObject.externalDiskSum+cellValue.dataDisk);
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
		pager : "#gridPager1",
		hidegrid : false
	});
}

//预分配信息（供给）   
function initSupplyGrid2(srId) {
	$("#gridTable2")
	.jqGrid(
			{
				url : 'request/base/queryBmSrRrinfoResoureList.action?srId=' + srId
						+ '',
				datatype : "json",
				mtype : "post", // 提交方式
				rownumbers : true,
				autowidth : true, // 是否自动调整宽度
				colNames : [i18nShow('my_req_sr_rrinfoId'), i18nShow('my_req_sr_confParameter'), i18nShow('my_req_sr_duName'),i18nShow('my_req_sr_vmdeviceId'), i18nShow('my_req_sr_vm_info'),
					i18nShow('my_req_sr_host_info'),i18nShow('my_req_sr_plat_form'),i18nShow('my_req_sr_volume_type'), i18nShow('my_req_sr_storage'), i18nShow('my_req_sr_input_para') , i18nShow('my_req_sr_net')],
				colModel : [
						{
							name : 'rrinfoId',
							index : 'rrinfoId',
							sortable : true,
							align : "left",
							hidden : true
						},
						{
							name : 'parametersJson',
							index : 'parametersJson',
							width : 140,
							sortable : true,
							align : "left",
							formatter : function (cellValue,options,rowObject){
								cellValue = JSON.parse(cellValue);
								str = "CPU="+cellValue.cpu+"C MEM="+cellValue.mem+"M "+"<br/>";
								str += "SYS DISK="+cellValue.sysDisk+"G DATA DISK="+cellValue.dataDisk+"G";
								return str;
							}
						},
						{
							name : 'duName',
							index : 'duName',
							width : 50,
							sortable : true,
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
							width : 100,
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
                            width : 20,
                            hidden : true
	                    },
						{
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
                                     if (rowObject.datastoreName == null || rowObject.datastoreName == "" || rowObject.datastoreName == "null") {
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
								if(attrRrinfoIds.indexOf(rowObject.rrinfoId) > 0) {
									return "<a href='javascript:;' style=' text-decoration:none' onclick=\"detailFromTable('" + options.rowId + "','" + rowObject.duId + "','" + rowObject.rrinfoId + "')\" >"+i18nShow('my_req_sr_watch')+"</a>";
								} else {
									return "";
								}
							}
						}, {
							name : 'vidept',
							index : 'cdpName',
							width : 90,
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
				pager : "#gridPager2",
				hidegrid : false,
				gridComplete : function() {
					getVmdeviceNet("gridTable2", "2");
				}
			});
}
//预分配信息（扩容）
var dealNum = 0;
var sumNum = 0;
function initExtendGrid2(srId) {
	dealNum = 0;
	sumNum = 0;
	$("#gridTable2").jqGrid({
	    url : 'request/base/findExpandVirtualDeviceBySrId.action?srId=' + srId+ '',
		datatype : 'json',
		height:_height,	
		rownumbers : true,
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
			width : 110,
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
				oldConfig = "CPU="+rowObject.cpuOld+"C MEM="+parseInt(rowObject.memOld)/1024 + "G "+"<br>"
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
				cellValue=JSON.parse(cellValue);
				var newConfig="";
				//x86类型的虚拟机，显示磁盘配置
				newConfig = "CPU="+cellValue.cpu+"C MEM="+parseInt(cellValue.mem)/1024 + "G" + "<br>"
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
		pager : "#gridPager2",
		hidegrid : false
	});
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
			showTip("error",null,"red");
		},
		success : function(data) { //请求成功后处理函数。  
		    text = data;
		}
    });
	return text;
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
//选择物理机
function getNewHostString(rrinfoId, vmId, oldHostId, newHostId, newHostName, isEnough, n) {
	var hostInputHtml = '<div id="hostDiv_' + n + '">' + getHostLinkHtml(newHostId, newHostName) + '</div>';
	var value = hostInputHtml;
	if(isEnough != "1") {
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
    				return ""+rowObject.deviceName+"";
    			}
            },
            {width : 50,sortable : false,align : "center",
           	formatter : function(cellValue, options, rowObject) {
				return ""+rowObject.cpu + "C" + rowObject.mem +"M";
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
//交付信息（供给）
function initSupplyGrid3(srId) {
	$("#gridTable3").jqGrid(
			{
				url : 'request/base/queryBmSrRrinfoAffirmList.action?srId=' + srId + '',
				datatype : "json",
				mtype : "post",
				altRows : true,
				rownumbers : true,
				colNames : [ i18nShow('my_req_sr_rrinfoId'), i18nShow('my_req_sr_duName'), i18nShow('my_req_sr_confParameter'), i18nShow('my_req_sr_vm'), i18nShow('my_req_sr_vmuserName'),
					i18nShow('my_req_sr_vmpwd'),i18nShow('my_req_sr_para'), '虚拟机id', i18nShow('my_req_sr_net') ],
				colModel : [ {
					name : 'rrinfoId',
					index : 'rrinfoId',
					width : 3,
					align : 'left',
					sortable : false,
					hidden : true
				}, {
					name : 'duName',
					index : 'duName',
					width : 6,
					sortable : false,
					align : "left",
					formatter : function (cellValue,options,rowObject){
						var duCname = cellValue ? cellValue : "";
						var duEname = rowObject.duEname ? rowObject.duEname : "";
						return i18nShow('my_req_sr_chinese')+duCname+"\n"+i18nShow('my_req_sr_english')+duEname;
	              }
				}, {
					name : 'parametersJson',
					index : 'parametersJson',
					width : 11,
					sortable : false,
					align : "left",
					formatter : function (cellValue,options,rowObject){
						cellValue=JSON.parse(cellValue);
						str = "CPU="+cellValue.cpu+" MEM="+cellValue.mem+"M "+"<br/>";
						str += "SYS DISK="+cellValue.sysDisk+"G DATA DISK="+cellValue.dataDisk+"G";
						return str;
					}
				}, {
					name : 'vmdeviceName',
					index : 'vmName',
					width : 5,
					align : 'left',
					sortable : false
				}, {
					name : 'vmuserName',
					index : 'vmuserName',
					width : 2,
					align : 'left',
					sortable : false
				}, {
					name : 'vmpwd',
					index : 'vmpwd',
					width : 3,
					align : "left",
					sortable : false
				}, {
						align : "left",
						width : 3,
						formatter : function(cellValue, options,
								rowObject) {
							var param = null;
							$.ajax({  
						         type : "post",  
						         url : ctx + '/request/base/getBmSrAttr.action',  
						         data : {"rrinfoId":rowObject.rrinfoId},  
						         async : false,  
						         success : function(data){  
						            param = data;
						         }  
						       }); 
						 if(param != "" && param != null){
						 	return "<a href='javascript:;' style=' text-decoration:none' onclick=\"detailFromTable('" + options.rowId + "','" + rowObject.duId + "','" + rowObject.rrinfoId + "')\" >"+i18nShow('my_req_sr_watch')+"</a>";
						 }else{
						    return "";
						 }	
						}
					}, {
					name : 'vmdeviceId',
					index : 'vmdeviceId',
					width : 3,
					align : "left",
					hidden : true,
					sortable : false
				}, {
					name : 'vidept',
					index : 'cdpName',
					width : 8,
					align : 'left',
					sortable : false,
					formatter : function(cellValue, options, rowObject) {
						var vmdevid = rowObject.vmdeviceId;
						return "<div id='" + vmdevid + "'>loading....</div>";
					}
				} ],
				rowNum : 10,
				sortname : 'rrinfoId',// 默认排序的字段
				sortorder : "desc",// 默认排序的
				jsonReader : {
					root : "dataList",
					records : "record",
					repeatitems : false
				},
				pager : '#gridPager3',
				viewrecords : true,
				autowidth : true,
				height : _height,
				gridComplete : function() {
					getVmdeviceNet("gridTable3", "3");
				}
			});
}
function getVmdeviceNet(tableId, type) {
	var tmp_ids = $("#"+tableId).getDataIDs();
	var rrinfoid = "";
	var vmdeviceid = "";
	for ( var t = 0; t < tmp_ids.length; t++) {
		var tmp_data = $("#"+tableId).getRowData(tmp_ids[t]);
		rrinfoid = tmp_data.rrinfoId;
		if(type == "2") {
			vmdeviceid = tmp_data.vmId;
		} else {
			vmdeviceid = tmp_data.vmdeviceId;
		}
		$.post(ctx+"/request/base/getVmNetIpOpenstack.action", {'rrinfoId' : rrinfoid,'vmdeviceId' : vmdeviceid}, function(data){
			var ips = "";
			for(var i=0 ; i<data.ipList.length ; i++) {
				ips += "<tr><td style='border: 0px solid #abc6d1;width:25%;'>"+data.ipList[i].rm_ip_type_name+"：</td><td style='border: 0px solid #abc6d1'><label id=''>"+data.ipList[i].ip+"</label></td></tr>";
			}
			$("#" + data.id + "").html("<table id='ip_table' class='ip_table' cellspacing='0' cellpadding='0' style='border-collapse:collapse;width:100%'>" + ips + "</table>");
		});
	};
}
//交付信息（回收）
function initRecycleGrid3(srId) {
	$("#gridTable3").jqGrid({
		datatype : 'local',
		height : 180,
		autowidth : true, 
		colNames : ['服务器角色Id', '虚拟机Id', '设备类型', '高可用类型', i18nShow('my_req_sr_duName'), i18nShow('my_req_sr_vmName'),i18nShow('my_req_sr_vm_conf'), 'IP'],
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
						return cellValue;
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
			var parameter_def = "cpu=" + deviceVoArr[i].cpu + " "+i18nShow('my_req_sr_mem')+"=" + deviceVoArr[i].mem + "M "+i18nShow('my_req_sr_disk')+"=" + deviceVoArr[i].disk + "G";
			var deviceVoObj = {
				duId : 'DU:' + deviceVoArr[i].duId,
				deviceId : deviceVoArr[i].id,
				deviceTypeCode : "",
				haTypeCode : "",
				du : deviceVoArr[i].duName,
				device : deviceVoArr[i].deviceName,
				deviceParamDef : parameter_def,
				deviceIps : deviceVoArr[i].cmVmIps
			};
			$("#gridTable3").jqGrid("addRowData", i, deviceVoObj);
		}
	});
}

//交付信息（扩容）
function initExtendGrid3(srId) {
	$("#gridTable3").jqGrid({
		url : 'request/base/queryBmSrRrinfoExtendByParam.action?srId=' + srId + '',
		datatype : "json",
		height : _height,
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
		},{
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
				cellValue = JSON.parse(cellValue);
				var newConfig="";
				//x86类型的虚拟机，显示磁盘配置
				newConfig = "CPU="+cellValue.cpu+"C MEM="+parseInt(cellValue.mem)/1024 + "G " + "<br>"
				if(rowObject.platformTypeCode =='X'){
					newConfig += "SYS DISK="+rowObject.diskOld+"G "+"DATA DISK="+(rowObject.externalDiskSum+cellValue.dataDisk);
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
			name : 'diskOld',
			index : 'diskOld',
			label : 'diskOld',
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
		pager : "#gridPager3",
		hidegrid : false
	});
}