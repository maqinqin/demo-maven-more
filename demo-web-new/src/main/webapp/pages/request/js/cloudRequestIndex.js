/**
 初始化表格
 */
 var _height = 260;
$(document).ready(function() {
	initMainGrid();
	attrTableInit();
	
	//自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
    });
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
			} else if(srType == 'SA'){
				initServiceAutoGrid(srId);
				srTypeStr = i18nShow('my_req_service_auto');
			} else if (srType == 'SA_ADD_VOLUME'){
				initServiceAutoGrid_addVolume(srId);
				srTypeStr = i18nShow('my_req_service_auto_addVolume');
			}
		} else {
			showTip(i18nShow('my_req_sr_id_null'));
		}
		document.getElementById("titlePage").innerHTML=srTypeStr;
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
						},
						{
							align : "left",
							width : 30,
							formatter : function(cellValue, options, rowObject) {
								//新流程中， dcm审核后自定义参数不做修改
								if(attrRrinfoIds.indexOf(rowObject.rrinfoId) > 0) {
									return "<a href='javascript:;' style=' text-decoration:none' onclick=\"detailFromTable('" + options.rowId + "','" + rowObject.duId + "','" + rowObject.rrinfoId + "')\" >"+i18nShow('my_req_sr_watch')+"</a>";
								} else {
									return "";
								}
							}
						}, {
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
								return i18nShow('my_req_sr_chinese')+cellValue+"\n"+i18nShow('my_req_sr_english')+rowObject.duEname;
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
									/*html.push("<div>CDP: "
											+ rowObject.cdpName
											+ "</div>");*/
									html.push("<div>"+i18nShow('my_req_sr_cluster')+": "
											+ rowObject.clusterName
											+ "</div>");
									html.push("<div>"+i18nShow('my_req_sr_host')+": "
											+ rowObject.deviceName
											+ "</div>");
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
						},
						{
							align : "left",
							width : 30,
							formatter : function(cellValue, options, rowObject) {
								//新流程中， dcm审核后自定义参数不做修改
								if(attrRrinfoIds.indexOf(rowObject.rrinfoId) > 0) {
									return "<a href='javascript:;' style=' text-decoration:none' onclick=\"detailFromTable('" + options.rowId + "','" + rowObject.duId + "','" + rowObject.rrinfoId + "')\" >"+i18nShow('my_req_sr_watch')+"</a>";
								} else {
									return "";
								}
							}
						}, {
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
//回收		
function initRecycleGrid(srId) {
	$("#gridTable").jqGrid({
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
//						var ops = "";
//						ops += "<a href='#' style='color:blue' ";
//						ops += "id='href_du" + options.rowId + "' ";
//						ops += "onclick='viewDudetail(" + rowObject.duId.substring(3) + ")'" + ">" + cellValue + "</a>";
//						return ops;
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
			var parameter_def = "cpu=" + deviceVoArr[i].cpu + " "+i18nShow('my_req_sr_memery')+"=" + deviceVoArr[i].mem + "M "+i18nShow('my_req_sr_disk')+"=" + deviceVoArr[i].disk + "G";
			var deviceVoObj = {
				duId : 'DU:' + deviceVoArr[i].duId,
				deviceId : deviceVoArr[i].id,
				//deviceTypeCode : deviceVoArr[i].deviceTypeCode,
				//haTypeCode : deviceVoArr[i].haTypeCode,
				deviceTypeCode : "",
				haTypeCode : "",
				//du : deviceVoArr[i].duName + '_' + deviceVoArr[i].duTypeCode,
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
//开始实施
function beginOperate(){
    $("#beginOperateBtn").attr("disabled",true);
	var url = ctx + '/request/base/saveOperateBegin.action';
	showTip("load");
	$.post(url, {srId:srId, todoId:todoId, srType:srType}, function (data) {
			  if(data.result == 'success'){
			    showTip(i18nShow('tip_my_req_det_sub1'),function(){
//					window.opener.location.reload();
//					window.close();
					history.go(-1);
				});
			  }else {
			    showTip(data.result);
			    $("#beginOperateBtn").attr("disabled",false);
			  }
	});

}
//完成实施
function overOperate(){
    $("#overOperateBtn").attr("disabled",true);
	var url = ctx + '/request/base/saveOperateEnd.action';
	showTip("load");
	$.post(url, {srId:srId, todoId:todoId, srType:srType}, function (data) {
		closeTip();
			  if(data.result == 'success'){
			    showTip(i18nShow('tip_my_req_det_sub2'),function(){
//					window.opener.location.reload();
//					window.close();
			    	history.go(-1);
				});
			  }else {
			    showTip(data.result);
			    $("#overOperateBtn").attr("disabled",false);
			  }
	});
}
