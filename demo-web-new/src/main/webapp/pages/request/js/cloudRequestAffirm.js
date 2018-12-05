var _height = 260;
$(document).ready(function() {
	initMainGrid(srId);
	attrTableInit();//初始化属性列表
	$( '#reqclose' ).hide();
});
//首页主grid初始化
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
		}else if(srType == 'SA_ADD_VOLUME'){
			initServiceAutoGrid_addVolume(srId);
			srTypeStr = i18nShow('my_req_service_auto_addVolume');
		}
	} else {
		showTip(i18nShow('my_req_sr_id_null'));
	}
	document.getElementById("titlePage").innerHTML=srTypeStr;
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
    });
}
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

//供给
function initSupplyGrid(srId) {
	$("#gridTable").jqGrid(
			{
				url : 'request/base/queryBmSrRrinfoAffirmList.action?srId=' + srId + '',
				datatype : "json",
				mtype : "post",
				altRows : true,
				colNames : [ i18nShow('my_req_sr_rrinfoId'), i18nShow('my_req_sr_duName'), i18nShow('my_req_sr_confParameter'), i18nShow('my_req_sr_vmName'), i18nShow('my_req_sr_vmuserName'),
					i18nShow('my_req_sr_vmpwd'),i18nShow('my_req_sr_para'), i18nShow('my_req_sr_vmdeviceId'), i18nShow('my_req_sr_net') ],
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
					formatter:function(cellValue,options,rowObject){
						  return i18nShow('my_req_sr_chinese') +cellValue+"\n"+ i18nShow('my_req_sr_english') +rowObject.duEname;
					}
				}, {
					name : 'confParameter',
					index : 'confParameter',
					width : 10,
					sortable : false,
					align : "left"
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
					width : 2,
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
				pager : '#gridPager',
				viewrecords : true,
				autowidth : true,
				height : _height,
				gridComplete : function() {
					getVmdeviceNet();
				}
			});
}
//回收
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
						var duCname = cellValue ? cellValue : "";
						var duEname = rowObject.duEname ? rowObject.duEname : "";
						return i18nShow('my_req_sr_chinese')+duCname+"\n"+i18nShow('my_req_sr_english')+duEname;
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
			$("#gridTable").jqGrid("addRowData", i, deviceVoObj);
		}
	});
}
//扩容
function initExtendGrid(srId) {
	$("#gridTable").jqGrid({
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
			width : 120,
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
			name : 'cpuNew',
			index : 'cpuNew',
			label : 'cpuNew',
			width : 120,
			align : "left",
			hidden : true,
			sortable : false
		},
		{
			name : 'memNew',
			index : 'memNew',
			label : 'memNew',
			width : 120,
			align : "left",
			hidden : true,
			sortable : false
		},
		{
			name : 'mountDisk',
			index : 'mountDisk',
			label : 'mountDisk',
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
	function viewVmDevice(deviceId) {
		window.open(ctx+ "/pages/rm/deviceVirtualMag.jsp?actType=detail&deviceId="+ deviceId);
	}

	function getVmdeviceNet() {
		var tmp_ids = $("#gridTable").getDataIDs();
		var rrinfoid = "";
		var vmdeviceId = "";
		for ( var t = 0; t < tmp_ids.length; t++) {
			var tmp_data = $("#gridTable").getRowData(tmp_ids[t]);
			rrinfoid = tmp_data.rrinfoId;
			vmdeviceId = tmp_data.vmdeviceId;
			$.post(ctx+"/request/base/getVmNetIp.action", {'rrinfoId' : rrinfoid,'vmdeviceId' : vmdeviceId}, function(data){
				var ips = "";
				for(var i=0 ; i<data.ipList.length ; i++) {
					ips += "<tr><td style='border: 0px solid #abc6d1;width:25%;'>"+data.ipList[i].rm_ip_type_name+"：</td><td style='border: 0px solid #abc6d1'><label id=''>"+data.ipList[i].ip+"</label></td></tr>";
				}
				$("#" + data.id + "").html("<table id='ip_table' class='ip_table' cellspacing='0' cellpadding='0' style='border-collapse:collapse;width:100%'>" + ips + "</table>");
			});
		};
	}

function showServiceSetDetail(serviceId) {
	var imageId;
	$("#catalog_detail_div").dialog({
		autoOpen : true,
		modal : true,
		width : 800,
		height : 500
	});
	$.get(ctx + "/ss/findServiceByIdAct.action", {
		_fw_service_id : "findServiceByIdSrv",serviceId : serviceId
	}, function(data) {
		var obj = JSON.parse(data);
		imageId = eval("(" + data + ")").imageId;
		$("#serviceName").val(obj.serviceName);
		$("#funcRemark").val(obj.funcRemark);
		$("#unfuncRemark").val(obj.unfuncRemark);
		$("#vmBase").val(obj.vmBase);
		setFormReadonly("catalogdetailform", true, false);
		$.get("getImageSoftwareConfigAct.action", {
			_fw_service_id : "getImageSoftwareConfigSrv",
			imageId : imageId
		}, function(data) {
			var obj = eval("(" + data + ")");
			var tbody = $("#software-config").find("tbody");
			tbody.html("");
			$.each(obj, function(index, item) {
				var str = "";
				str += "<tr softwareId='" + item.softwareId + "'>";
				str += "<td>" + item.softwareName + "</td>";
				str += "<td>" + item.softwareTypeCode + "</td>";
				str += "<td>" + item.softwareVer + "</td>";
				str += "</tr>";
				tbody.append(str);
			});
		});
	});
	$.get(ctx + "/ss/getServieSetByIdAct.action", {
		'_fw_service_id' : 'findServiceSetByIdSrv'
	}, function(obj) {
		var sp = JSON.parse(obj);
		$("#serviceSetName").val(sp.serviceSetName);
		$("#cpu").val(sp.cpu);
		$("#mem").val(sp.mem);
		$("#sysDisk").val(sp.sysDisk);
		$("#dataDiskCode").val(sp.dataDiskCode);
		$("#archiveDiskCode").val(sp.archiveDiskCode);
		$("#network").val(sp.network);
		$("#networkTypeCode").val(sp.networkType);
	});
}
function closeRequestSr(){
    var url = ctx + '/request/base/closeRequestSr.action';
    showTip("load");
	$.post(url, {srId:srId, todoId:todoId}, function (data) {
		closeTip();
		  if(data.result == 'success'){
		    showTip(i18nShow('my_req_close_success'),function(){
		    	history.go(-1);
			});
		  }else {
		    showTip(i18nShow('my_req_close_fail_1'),function(){history.go(-1);});
		  }
	});
}