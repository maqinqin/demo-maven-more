var tempVmId;
function initCondition(vmId){
	$("#yesOrNo").change(
			function (){
				var share=$("#yesOrNo").val();
				var build = $("#newBuild").val();
				//选择非共享卷，不新创建情况，隐藏卷大小并且列表中只显示非共享卷以及状态为可用
				if(share=='N' && build=='N'){
					//隐藏保存按钮
					$("#btn_add_mountVolume").css("display","none");
					$("#volumeSizeId").css("display","none");
					$("#mountGridTable_div").show();
					$("#mountGridTable").show();
					$("#mountGridPager").show();
					$("#newBuildId").css("display","inline");
					var shareFlag = "";
					var createFlag = "";
					if(share == 'Y'){
						shareFlag = 'true';
					}else{
						shareFlag = 'false';
					}
					if(build == 'Y'){
						createFlag = 'true';
					}else{
						createFlag = 'false';
					}
					//先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
					$("#mountGridTable").jqGrid().GridUnload("mountGridTable");
					$("#mountGridTable").jqGrid({
						url : ctx+"/resmgt-common/device/getServerVolume.action",
						rownumbers : false, // 是否显示前面的行号
						datatype : "json", // 返回的数据类型
						mtype : "post", // 提交方式
						height : 300,
						postData:{"vmId":vmId,"share":shareFlag,"isCreate":createFlag},
						autowidth : true, // 是否自动调整宽度
						multiboxonly: false,
						colModel : [ {
							name : "id",
							index : "id",
							label : "id",
							width : 40,
							sortable : true,
							align : 'left',
							hidden:true	
						},  {
							name : "diskSize",
							index : "diskSize",
							label : i18nShow('compute_res_volume_size'),
							width : 80,
							sortable : true,
							search:true,
							align : 'left'
						},{
							name : "status",
							index : "status",
							label : i18nShow('compute_res_volume_status'),
							width : 80,
							sortable : true,
							search:true,
							align : 'left',
							formatter:function(cellValue,options,rowObject){
								if(cellValue == 'available'){
									return i18nShow('compute_res_volume_available');
								}else if(cellValue =='in-use'){
									return i18nShow('compute_res_volume_bound');
								}
							}
						},{
							name:"azName",
							index:"azName",
							label:i18nShow('compute_res_volume_azName'),
							width: 80,
							sortable: true,
							search:true,
							align:'left'
							
						},{
							name : "isShare",
							index : "isShare",
							label : i18nShow('compute_res_volume_isShare'),
							width : 60,
							sortable : true,
							align : 'left',
							hidden:false,
							formatter : function(cellValue,options,rowObject){
								if(cellValue=='false'){
									return i18nShow('compute_res_volume_shareN');
								}else if(cellValue=='true'){
									return i18nShow('compute_res_volume_shareY');
								}
							}
						},{
							name : 'option',
						    index : 'option',
						    label : i18nShow('com_operate'),
							width : 50,
							align : "left",
							sortable:false,
							formatter : function(cellValue,options,rowObject) {
									return  "<a  style='margin-right: 10px;margin-left: 5px;text-decoration:none;' href='javascript:#' title=''  onclick=saveMountVolume('"+rowObject.id+"','"+rowObject.diskSize+"','"+vmId+"')>"+i18nShow('compute_res_device_mountVolume')+"</a>";
							}														
						}],
						
						viewrecords : true,
						sortname : "id",
						/*rowNum : 10,
						rowList : [ 5, 10, 15, 20, 30 ],*/
						prmNames : {
							search : "search"
						},
						jsonReader : {
							root : "dataList",
							records : "record",
							repeatitems : false
						},
						pager : "#mountGridPager",
						hidegrid : false
					});
				//非共享卷，新创建卷情况，则显示卷大小，并且不加载也不显示列表
				}else if(share=='N' && build=='Y'){
					$("#newBuildId").css("display","inline");
					$("#volumeSizeId").css("display","inline");
					$("#mountGridTable_div").hide();
					$("#btn_add_mountVolume").css("display","inline");
				}
				//共享卷、新创建卷情况，则显示卷大小，并且不加载也不显示列表
				else if(share=='Y' && build=='Y'){
					$("#volumeSizeId").css("display","inline");
					$("#mountGridTable_div").hide();
					$("#btn_add_mountVolume").css("display","inline");
				}else if(share=='Y' && build=='N'){//共享卷、不新创建情况，隐藏卷大小并且列表中不显示非共享卷
					//隐藏保存按钮
					$("#btn_add_mountVolume").css("display","none");
					$("#volumeSizeId").css("display","none");
					$("#mountGridTable_div").show();
					$("#mountGridTable").show();
					$("#mountGridPager").show();
					var shareFlag = "";
					var createFlag = "";
					if(share == 'Y'){
						shareFlag = 'true';
					}else{
						shareFlag = 'false';
					}
					if(build == 'Y'){
						createFlag = 'true';
					}else{
						createFlag = 'false';
					}
					//先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
					$("#mountGridTable").jqGrid().GridUnload("mountGridTable");
					$("#mountGridTable").jqGrid({
						url : ctx+"/resmgt-common/device/getServerVolume.action",
						rownumbers : false, // 是否显示前面的行号
						datatype : "json", // 返回的数据类型
						mtype : "post", // 提交方式
						height : 300,
						postData:{"vmId":vmId,"share":shareFlag,"isCreate":createFlag},
						autowidth : true, // 是否自动调整宽度
						multiboxonly: false,
						colModel : [ {
							name : "id",
							index : "id",
							label : "id",
							width : 40,
							sortable : true,
							align : 'left',
							hidden:true	
						},  {
							name : "diskSize",
							index : "diskSize",
							label : i18nShow('compute_res_volume_size'),
							width : 80,
							sortable : true,
							search:true,
							align : 'left'
						},{
							name : "status",
							index : "status",
							label : i18nShow('compute_res_volume_status'),
							width : 80,
							sortable : true,
							search:true,
							align : 'left',
							formatter:function(cellValue,options,rowObject){
								if(cellValue == 'available'){
									return i18nShow('compute_res_volume_available');
								}else if(cellValue =='in-use'){
									return i18nShow('compute_res_volume_bound');
								}
							}
						},{
							name:"azName",
							index:"azName",
							label:i18nShow('compute_res_volume_azName'),
							width: 80,
							sortable: true,
							search:true,
							align:'left'
							
						},{
							name : "isShare",
							index : "isShare",
							label : i18nShow('compute_res_volume_isShare'),
							width : 60,
							sortable : true,
							align : 'left',
							hidden:false,
							formatter : function(cellValue,options,rowObject){
								if(cellValue=='false'){
									return i18nShow('compute_res_volume_shareN');
								}else if(cellValue=='true'){
									return i18nShow('compute_res_volume_shareY');
								}
							}
						}, {
							name : 'option',
						    index : 'option',
						    label : i18nShow('com_operate'),
							width : 50,
							align : "left",
							sortable:false,
							formatter : function(cellValue,options,rowObject) {
									return  "<a  style='margin-right: 10px;margin-left: 5px;text-decoration:none;' href='javascript:#' title=''  onclick=saveMountVolume('"+rowObject.id+"','"+rowObject.diskSize+"','"+vmId+"')>"+i18nShow('compute_res_device_mountVolume')+"</a>";
							}														
						}],
						
						viewrecords : true,
						sortname : "id",
						/*rowNum : 10,
						rowList : [ 5, 10, 15, 20, 30 ],*/
						prmNames : {
							search : "search"
						},
						jsonReader : {
							root : "dataList",
							records : "record",
							repeatitems : false
						},
						pager : "#mountGridPager",
						hidegrid : false
					});
				}
			}
	);
	
	$("#newBuild").change(
			function (){
				var share=$("#yesOrNo").val();
				var build = $("#newBuild").val();
				//选择非共享卷，不新创建情况，隐藏卷大小并且列表中只显示非共享卷以及状态为可用
				if(share=='N' && build=='N'){
					//隐藏保存按钮
					$("#btn_add_mountVolume").css("display","none");
					$("#volumeSizeId").css("display","none");
					$("#mountGridTable_div").show();
					$("#mountGridTable").show();
					$("#mountGridPager").show();
					$("#newBuildId").css("display","inline");
					var shareFlag = "";
					var createFlag = "";
					if(share == 'Y'){
						shareFlag = 'true';
					}else{
						shareFlag = 'false';
					}
					if(build == 'Y'){
						createFlag = 'true';
					}else{
						createFlag = 'false';
					}
					//先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
					$("#mountGridTable").jqGrid().GridUnload("mountGridTable");
					$("#mountGridTable").jqGrid({
						url : ctx+"/resmgt-common/device/getServerVolume.action",
						rownumbers : false, // 是否显示前面的行号
						datatype : "json", // 返回的数据类型
						mtype : "post", // 提交方式
						height : 300,
						postData:{"vmId":vmId,"share":shareFlag,"isCreate":createFlag},
						autowidth : true, // 是否自动调整宽度
						multiboxonly: false,
						colModel : [ {
							name : "id",
							index : "id",
							label : "id",
							width : 40,
							sortable : true,
							align : 'left',
							hidden:true	
						},  {
							name : "diskSize",
							index : "diskSize",
							label : i18nShow('compute_res_volume_size'),
							width : 80,
							sortable : true,
							search:true,
							align : 'left'
						},{
							name : "status",
							index : "status",
							label : i18nShow('compute_res_volume_status'),
							width : 80,
							sortable : true,
							search:true,
							align : 'left',
							formatter:function(cellValue,options,rowObject){
								if(cellValue == 'available'){
									return i18nShow('compute_res_volume_available');
								}else if(cellValue =='in-use'){
									return i18nShow('compute_res_volume_bound');
								}
							}
						},{
							name:"azName",
							index:"azName",
							label:i18nShow('compute_res_volume_azName'),
							width: 80,
							sortable: true,
							search:true,
							align:'left'
							
						},{
							name : "isShare",
							index : "isShare",
							label : i18nShow('compute_res_volume_isShare'),
							width : 60,
							sortable : true,
							align : 'left',
							hidden:false,
							formatter : function(cellValue,options,rowObject){
								if(cellValue=='false'){
									return i18nShow('compute_res_volume_shareN');
								}else if(cellValue=='true'){
									return i18nShow('compute_res_volume_shareY');
								}
							}
						}, {
							name : 'option',
						    index : 'option',
						    label :i18nShow('com_operate'),
							width : 50,
							align : "left",
							sortable:false,
							formatter : function(cellValue,options,rowObject) {
									return  "<a  style='margin-right: 10px;margin-left: 5px;text-decoration:none;' href='javascript:#' title=''  onclick=saveMountVolume('"+rowObject.id+"','"+rowObject.diskSize+"','"+vmId+"')>"+i18nShow('compute_res_device_mountVolume')+"</a>";
							}														
						}],
						
						viewrecords : true,
						sortname : "id",
						/*rowNum : 10,
						rowList : [ 5, 10, 15, 20, 30 ],*/
						prmNames : {
							search : "search"
						},
						jsonReader : {
							root : "dataList",
							records : "record",
							repeatitems : false
						},
						pager : "#mountGridPager",
						hidegrid : false
					});
					
				}else if(share=='N' && build=='Y'){//非共享卷，新创建卷情况，则显示卷大小，并且不加载也不显示列表
					$("#newBuildId").css("display","inline");
					$("#volumeSizeId").css("display","inline");
					$("#mountGridTable_div").hide();
					$("#btn_add_mountVolume").css("display","inline");
				}
				//共享卷、新创建卷情况，则显示卷大小，并且不加载也不显示列表
				else if(share=='Y' && build=='Y'){
					$("#volumeSizeId").css("display","inline");
					$("#mountGridTable_div").hide();
					$("#btn_add_mountVolume").css("display","inline");
				}else if(share=='Y' && build=='N'){//共享卷、不新创建情况，隐藏卷大小并且列表中不显示非共享卷
					//隐藏保存按钮
					$("#btn_add_mountVolume").css("display","none");
					$("#volumeSizeId").css("display","none");
					$("#mountGridTable_div").show();
					$("#mountGridTable").show();
					$("#mountGridPager").show();
					var shareFlag = "";
					var createFlag = "";
					if(share == 'Y'){
						shareFlag = 'true';
					}else{
						shareFlag = 'false';
					}
					if(build == 'Y'){
						createFlag = 'true';
					}else{
						createFlag = 'false';
					}
					//先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
					$("#mountGridTable").jqGrid().GridUnload("mountGridTable");
					$("#mountGridTable").jqGrid({
						url : ctx+"/resmgt-common/device/getServerVolume.action",
						rownumbers : false, // 是否显示前面的行号
						datatype : "json", // 返回的数据类型
						mtype : "post", // 提交方式
						height : 300,
						postData:{"vmId":vmId,"share":shareFlag,"isCreate":createFlag},
						autowidth : true, // 是否自动调整宽度
						multiboxonly: false,
						colModel : [ {
							name : "id",
							index : "id",
							label : "id",
							width : 40,
							sortable : true,
							align : 'left',
							hidden:true	
						},  {
							name : "diskSize",
							index : "diskSize",
							label : i18nShow('compute_res_volume_size'),
							width : 80,
							sortable : true,
							search:true,
							align : 'left'
						},{
							name : "status",
							index : "status",
							label : i18nShow('compute_res_volume_status'),
							width : 80,
							sortable : true,
							search:true,
							align : 'left',
							formatter:function(cellValue,options,rowObject){
								if(cellValue == 'available'){
									return i18nShow('compute_res_volume_available');
								}else if(cellValue =='in-use'){
									return i18nShow('compute_res_volume_bound');
								}
							}
						},{
							name:"azName",
							index:"azName",
							label:i18nShow('compute_res_volume_azName'),
							width: 80,
							sortable: true,
							search:true,
							align:'left'
							
						},{
							name : "isShare",
							index : "isShare",
							label : i18nShow('compute_res_volume_isShare'),
							width : 60,
							sortable : true,
							align : 'left',
							hidden:false,
							formatter : function(cellValue,options,rowObject){
								if(cellValue=='false'){
									return i18nShow('compute_res_volume_shareN');
								}else if(cellValue=='true'){
									return i18nShow('compute_res_volume_shareY');
								}
							}
						}, {
							name : 'option',
						    index : 'option',
						    label : i18nShow('com_operate'),
							width : 50,
							align : "left",
							sortable:false,
							formatter : function(cellValue,options,rowObject) {
									return  "<a  style='margin-right: 10px;margin-left: 5px;text-decoration:none;' href='javascript:#' title=''  onclick=saveMountVolume('"+rowObject.id+"','"+rowObject.diskSize+"','"+vmId+"')>"+i18nShow('compute_res_device_mountVolume')+"</a>";
							}														
						}],
						
						viewrecords : true,
						sortname : "id",
						/*rowNum : 10,
						rowList : [ 5, 10, 15, 20, 30 ],*/
						prmNames : {
							search : "search"
						},
						jsonReader : {
							root : "dataList",
							records : "record",
							repeatitems : false
						},
						pager : "#mountGridPager",
						hidegrid : false
					});
				}
				
			}
	);
	
	//自适应宽度
	$("#mountGridTable").setGridWidth($("#mountGridTable_div").width());
	$(window).resize(function() {
		$("#mountGridTable").setGridWidth($("#mountGridTable_div").width());
    });
}
function getRmComputeVmListAction(tid){
	var nodeId = $("#hide_nodeId").val();
	var aptAPDuId = $("#hidNodeId").val();
	var appId = $("#hidenAppSysId").val();
	var duId = $("#du_id").val();
	var rmdatacenterId,resPoolId,rmVqCdpId,clusterId,hostId,rmVmId,
    rmdatacenterIndex,resPoolIndex,cdpqIndex,clusterIndex,hostIndex,rmVmIndex,apptAPIndex,apptACPId,apptADIndex,apptADuId;
	if(tid == "apptADuId"){
		apptAPIndex = aptAPDuId.indexOf("apptACPId:");
		apptADIndex = aptAPDuId.indexOf("apptADuId:");
		if(apptAPIndex >= 0){
			apptACPId = aptAPDuId.substr(10);
		}
		if(apptADIndex >= 0){
			apptADuId = aptAPDuId.substr(10);
		}
	}
	if(tid == "reCeId"){
		rmdatacenterIndex = nodeId.indexOf("rmdatacenterId:");
		resPoolIndex = nodeId.indexOf("resPoolId:");
		cdpqIndex = nodeId.indexOf("rmVqCdpId:");
		clusterIndex = nodeId.indexOf("clusterId:");
		hostIndex = nodeId.indexOf("hostId:");
		rmVmIndex = nodeId.indexOf("rmVmId:");
		if(rmdatacenterIndex >= 0){
		    rmdatacenterId = nodeId.substr(15);
		}
		if(resPoolIndex >= 0){
			resPoolId = nodeId.substr(10);
		}
		if(cdpqIndex >= 0){
			rmVqCdpId = nodeId.substr(10);
		}
		if(clusterIndex >= 0){
			clusterId = nodeId.substr(10);
		}
		if(hostIndex >= 0){
			hostId = nodeId.substr(7);
		}
		if(rmVmIndex >= 0){
			rmVmId = nodeId.substr(7);
		}
	}
	$("#gridTable_virware").jqGrid().GridUnload("gridTable_virware");
	$("#gridTable_virware").jqGrid({
		url : ctx+"/getRmComputeVmList.mvc",
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		multiselect : false,
		postData : {"rmdatacenterId":rmdatacenterId,"resPoolId":resPoolId,"rmVqCdpId":rmVqCdpId,"clusterId":clusterId,
					"hostId":hostId,"rmVmId":rmVmId,"apptACPId":apptACPId,"apptADuId":apptADuId},
		height : 310,
		autowidth : true, // 是否自动调整宽度
		colModel : [{
			name : "vid",
			index : "vid",
			label : i18nShow('host_res_id'),
			width : 10,
			sortable : true,
			align : 'left',
			hidden : true
		},
		{
			name : "vmTypeCode",
			index : "vmTypeCode",
			label : i18nShow('compute_res_vmTypeCode'),
			width : 10,
			sortable : true,
			align : 'left',
			hidden : true
		},
		{
			name : "vmName",
			index : "vmName",
			label : "vmName",
			width : 130,
			sortable : true,
			align : 'left',
			hidden : true
		},
		{
			name : "ipName",
			index : "ipName",
			label : i18nShow('compute_res_deviceName'),
			width : 130,
			sortable : true,
			align : 'left'
		},
		{
			name : "ram",
			index : "ram",
			label : i18nShow('compute_res_server'),
			width : 130,
			sortable : true,
			align : 'left',
			formatter: function(cellValue,options,rowObject){
				return rowObject.cpu+"C"+" "
					+(cellValue/1024)+"G"+" "+rowObject.diskSpace+"G";
			}
//			name : "cpu",
//			index : "cpu",
//			label : i18nShow('compute_res_deviceCpu')+" (123456核)",
//			width :62,
//			sortable : true,
//			align : 'left'
		},
		{
			name : "lastName",
			index : "lastName",
			label : i18nShow('res_l_comput_loginName'),
			width : 110,
			sortable : true,
			align : 'left',
			formatter: function(cellValue,options,rowObject){
				return rowObject.firstName+""+cellValue+"\n"+rowObject.loginName;
			}
//			name : "ram",
//			index : "ram",
//			label : i18nShow('compute_res_deviceMem')+" (987654GB)",
//			width : 62,
//			sortable : true,
//			align : 'left',
//			formatter:function(cellVall, options, rowObject){
//				return parseInt(cellVall/1024);
//			}
		},
		{
			name : "tenantName",
			index : "tenantName",
			label : i18nShow('res_l_comput_tenantName'),
			width : 80,
			sortable : true,
			align : 'left'
//			name : "ip",
//			index : "ip",
//			label : i18nShow('compute_res_ipAddress'),
//			width : 100,
//			sortable : true,
//			align : 'left',
//			hidden : true
		},
//		{
//			name : "diskSpace",
//			index : "diskSpace",
//			label : i18nShow('compute_res_diskSpace')+"(GB)",
//			width : 35,
//			sortable : true,
//			align : 'left'
//		},
		{
			name : "cloudServiceName",
			index : "cloudServiceName",
			label : i18nShow('compute_res_cloudServiceName'),
			width : 140,
			sortable : true,
			align : 'left'
		},
		{
			name : "cname",
			index : "cname",
			label : i18nShow('compute_res_appCname'),
			width : 80,
			sortable : true,
			align : 'left',
			formatter:function(cellVall, options, rowObject){
				var str = ""+cellVall;
				if(str.indexOf("null")){
					return cellVall;
				}else{
					return "";
				}
			}
		},
		{
			name : "apGroup",
			index : "apGroup",
			label : i18nShow('compute_res_duName'),
			width : 66,
			sortable : true,
			align : 'left',
			hidden : true
		},
		{
			name : "vmType",
			index : "vmType",
			label : "",
			width : 0,
			align : 'left',
			hidden : true
		},
		{
			name : "platFormCode",
			index : "platFormCode",
			label : i18nShow('compute_res_platFormCode'),
			width : 20,
			align : 'left',
			hidden : true
		},
		{
			name : "rmRedPoolType",
			index : "rmRedPoolType",
			label : "",
			width : 0,
			align : 'left',
			hidden : true
		},
		{
			name : "dataDeTreType",
			index : "dataDeTreType",
			label : "",
			width : 0,
			align : 'left',
			hidden : true
		},
		{
			name : "vmState",//这个是自己构造的
			index : "vmState",
			label : "vmState",
			width : 0,
			align : 'left',
			hidden : true,
			formatter:function(cellVall, options, rowObject){
				return rowObject.runningState;
			}
		},
		{
			name:"runningState",
			index:"runningState",
			label:i18nShow('compute_res_runningState'),
			width:60,
			sortable:true,
			align:'left',
			formatter:function(cellVall, options, rowObject){
				var result = "";
				if("poweron" == rowObject.runningState){
					result = "<p id='"+ rowObject.vid+"_state" +"'>"+i18nShow('compute_res_poweron')+"</p>";
				}else if("poweroff" == rowObject.runningState){
					result = "<p id='"+ rowObject.vid+"_state" +"'>"+i18nShow('compute_res_poweroff')+"</p>";
				}else if("paused" == rowObject.runningState){
					result = "<p id='"+ rowObject.vid+"_state" +"'>"+i18nShow('compute_res_hang')+"</p>";
				}else if("unknown" == rowObject.runningState || "" == rowObject.runningState){
					result = "<p id='"+ rowObject.vid+"_state" +"'>"+i18nShow('compute_res_unknown')+"</p>";
				}
				return result;
			}
		},
		{
			name : "",
			index : "",
			label : i18nShow('com_operate'),
			width : 100,
			sortable : true,
			align : 'left',
			formatter:function(cellVall, options, rowObject){
				var result = "";
				var deviceStatus=rowObject.deviceStatus;
				if(deviceStatus=="BUILDING"){
					return "";
				}else{
					var flag = "select";
					if(tid == "apptADuId"){
						result += '<select id="'+rowObject.vid+'_operation" name="operation" onchange=operation("'+flag+'","'+rowObject.vid+'","'+rowObject.vmName+'","'+rowObject.cpu+'","'+rowObject.ram+'","'+rowObject.vmType+'","'+rowObject.rmRedPoolType+'","'+rowObject.dataDeTreType+'","'+apptACPId+'","'+rowObject.runningState+'","'+rowObject.vmTypeCode+'","'+rowObject.platFormCode+'") style=" text-align:left; width:75px" ><option value="0" select="selected">'+i18nShow('compute_res_select')+'</option></select>';
					}else {
						result += '<select id="'+rowObject.vid+'_operation" name="operation" onchange=operation("'+flag+'","'+rowObject.vid+'","'+rowObject.vmName+'","'+rowObject.cpu+'","'+rowObject.ram+'","'+rowObject.vmType+'","'+rowObject.rmRedPoolType+'","'+rowObject.dataDeTreType+'","'+apptACPId+'","'+rowObject.runningState+'","'+rowObject.vmTypeCode+'","'+rowObject.platFormCode+'") style=" text-align:left; width:75px" ><option value="0" select="selected">'+i18nShow('compute_res_select')+'</option><option value="7">'+i18nShow('compute_res_device_transfer')+'</option></select>';
					}
				}
				return result;		
			}
		},
		{
			name : "",
			index : "",
			label : '性能监控',
			width : 80,
			sortable : true,
			align : 'left',
			hidden:true,
			formatter:function(cellVall, options, rowObject){
				var ip = rowObject.ipName.replace(rowObject.vmName,'').replace('\n','');
//				return '<a href="javascript:#" onclick=monitor(\''+rowObject.ipName.replace(rowObject.vmName,'')+'\')>查看</a>';
				return '<a href="javascript:#" onclick=monitor(\''+ip+'\')>查看</a>';
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
		pager : "#gridPager_virware",
		hidegrid : false,
		gridComplete : function() {
			getVmDeviceId("gridTable_virware", "");
			addOptions("gridTable_virware");
		}
	});
}
// 加载完数据列表之后，添加操作列选项
function addOptions(tableId){
	var rowIds = $("#"+tableId).getDataIDs();
	for ( var i = 0; i < rowIds.length; i++) {
		var vmTypeCode = $("#"+tableId).getRowData(rowIds[i]).vmTypeCode;
		//openstack用
		var platFormCode = $("#"+tableId).getRowData(rowIds[i]).platFormCode;
		var rowData = $("#"+tableId).getRowData(rowIds[i]);
		var state = $("#"+tableId).getRowData(rowIds[i]).vmState;
		if((platFormCode == 'O') && (vmTypeCode=='OV' || vmTypeCode =='OI' || vmTypeCode=='OK')){
			if("poweron" == state){
				jQuery("<option value='2'>"+i18nShow('compute_res_device_poweroff')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='5'>"+i18nShow('compute_res_device_restart')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='12'>"+i18nShow('compute_res_device_remoteControl')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='forcedRestart'>"+i18nShow('compute_res_device_forcedRestart')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='mountVolume'>"+i18nShow('compute_res_device_mountVolume')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='unloadVolume'>"+i18nShow('compute_res_device_unloadVolume')+"</option>").appendTo("#" + rowData.vid + "_operation");
			}else if("poweroff" == state){
				jQuery("<option value='1'>"+i18nShow('compute_res_device_poweron')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='mountVolume'>"+i18nShow('compute_res_device_mountVolume')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='unloadVolume'>"+i18nShow('compute_res_device_unloadVolume')+"</option>").appendTo("#" + rowData.vid + "_operation");
			}
		}else if(vmTypeCode == 'PV'){
			if("poweron" == state){
				jQuery("<option value='2'>"+i18nShow('compute_res_device_poweroff')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='5'>"+i18nShow('compute_res_device_restart')+"</option>").appendTo("#" + rowData.vid + "_operation");
			}else if("poweroff" == state){
				jQuery("<option value='1'>"+i18nShow('compute_res_device_poweron')+"</option>").appendTo("#" + rowData.vid + "_operation");
			}else {}
		}else{
			if("poweron" == state){
				jQuery("<option value='2'>"+i18nShow('compute_res_device_poweroff')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='6'>"+i18nShow('compute_res_device_hang')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='5'>"+i18nShow('compute_res_device_restart')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='3'>"+i18nShow('compute_res_device_createSnapshot')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='4'>"+i18nShow('compute_res_device_snapshotManage')+"</option>").appendTo("#" + rowData.vid + "_operation");
				if(vmTypeCode == 'KV'){
					jQuery("<option value='10'>"+i18nShow('compute_res_device_remoteControl')+"</option>").appendTo("#" + rowData.vid + "_operation");
                }else if(vmTypeCode == 'VM'){
                	jQuery("<option value='11'>"+i18nShow('compute_res_device_remoteControl')+"</option>").appendTo("#" + rowData.vid + "_operation");
                }
			}else if("poweroff" == state){
				jQuery("<option value='1'>"+i18nShow('compute_res_device_poweron')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='3'>"+i18nShow('compute_res_device_createSnapshot')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='4'>"+i18nShow('compute_res_device_snapshotManage')+"</option>").appendTo("#" + rowData.vid + "_operation");
			}else if("paused" == state){
				jQuery("<option value='2'>"+i18nShow('compute_res_device_poweroff')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='8'>"+i18nShow('compute_res_device_awaken')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='3'>"+i18nShow('compute_res_device_createSnapshot')+"</option>").appendTo("#" + rowData.vid + "_operation");
				jQuery("<option value='4'>"+i18nShow('compute_res_device_snapshotManage')+"</option>").appendTo("#" + rowData.vid + "_operation");
			}else if("unknown" == state){
			}
		}
		
	}
}

function getVmDeviceId(tableId, type) {
	var tmp_ids = $("#"+tableId).getDataIDs();
	var vmDeviceId = "";
	for ( var t = 0; t < tmp_ids.length; t++) {
		var tmp_data = $("#"+tableId).getRowData(tmp_ids[t]);
		vmDeviceId = tmp_data.vid;
		$.post(ctx+"/request/base/getVmNetIp.action", {'vmdeviceId' : vmDeviceId}, function(data){
			var ips = "";
			for(var i=0 ; i<data.ipList.length ; i++) {
				ips += "<tr><td style='border: 0px solid #abc6d1'><label id=''>"+data.ipList[i].ip+"</label></td></tr>";
			}
			$("#" + data.id + "").html("<table id='ip_table' class='ip_table' cellspacing='0' cellpadding='0' style='border-collapse:collapse;width:100%'>" + ips + "</table>");
		});
	};
}

function search_hvm() {
	$("#gridTable_virware").jqGrid('setGridParam', {
		url : ctx + "/getRmComputeVmList.mvc",
		postData : {
			"vmName":$("#vmNameInput").val().replace(/(^\s*)|(\s*$)/g, "")
// "vmIpAddre":$("#vmIpAdrInput").val()
		},
		pager : "#gridPager_virware"
	}).trigger("reloadGrid");
}

function showDu(){
	var duId = $("#duId").val();
	openDialog('gridTable_showDu_div',i18nShow('compute_res_selectDu'), 900, 450);
	$("#gridTable_showDuButtonDiv").show();
	$("#gridTable_showDu").jqGrid().GridUnload("gridTable_showDu");
	$("#gridTable_showDu").jqGrid({
		url : ctx + "/appmgt/deploy-unit/getDuById.action",
		rownumbers : false,
		datatype : "json",
		mtype : "post",
		multiselect : false,
		postData : {"id":duId},
		height : 310,
		autowidth : true,
		colModel : [{
			name : "duId",
			index : "duId",
			label : i18nShow('compute_res_duId'),
			width : 10,
			sortable : false,
			align : 'left',
			hidden : true
		},
		{
			name : "appCName",
			index : "appCName",
			label : i18nShow('compute_res_appCName'),
			width : 80,
			sortable : true,
			align : 'left'
		},
		{
			name : "appEName",
			index : "appEName",
			label : i18nShow('compute_res_appEName'),
			width : 80,
			sortable : true,
			align : 'left'
		},
		{
			name : "duCName",
			index : "duCName",
			label : i18nShow('compute_res_duCName'),
			width : 80,
			sortable : true,
			align : 'left'
		},
		{
			name : "duEName",
			index : "duEName",
			label : i18nShow('compute_res_duEName'),
			width : 80,
			sortable : true
		},
		{
			name : "op",
			index : "op",
			label : i18nShow('com_operate'),
			width : 20,
			sortable : false,
			align : 'left',
			hidden : false,
			formatter:function(cellVall, options, rowObject){
				var ret = "";
				ret += "<a href='javascript:' style=' text-decoration:none'onclick=changeDu('"+rowObject.duId+"','"+rowObject.duCName+"')>"+i18nShow('compute_res_changeDu')+"</a>"
				return ret; 
			}
		}],
		viewrecords : true,
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		// height : 90%,
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			repeatitems : false
		},
		pager : "#gridPager_showDu",
		hidegrid : false
	});
}

function changeDu(duId,duCName){
	var vmId = $("#vmId").val();
	showTip(i18nShow('compute_res_changeDuTip'),function(){
		$("#gridTable_showDu_div").dialog("close");
		showTip("load");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/resmgt-compute/vm/updateVmDuId.action",  
		data : {"cmVmVo.duId":duId,"cmVmVo.id":vmId},
		error : function() {// 请求失败处理函数
			closeTip();
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) { // 请求成功后处理函数。
			closeTip();
			if(data.result=="SUCCESS"){
				showTip(i18nShow('compute_res_updateSuccess'));
				$("#vm_du_name").html(duCName);
			}else{
				showError(i18nShow('compute_res_updateFail'));
			}
		}
	});
	});
}

require.config({
    paths: {
        echarts:ctx + "/common/echart/"
    }
});
function getMonitorData(ip,name){
	var result=[];
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/resmgt-compute/vm/getVmMonitor.action",  
		data : {ip:ip,name:name},
		error : function() {// 请求失败处理函数
			showError('请求异常');
		},
		success : function(data) { // 请求成功后处理函数。
			result = data;
		}
	});
	return result;
}
function monitor(ip){
	$("#monitorDiv").dialog({  
 	 	autoOpen : true,
			modal:true,
			height:400,
			width:800,
			title:'性能查看',
			resizable:false,
			close: function () {
				
         }
     }); 
	
	var cpuData = getMonitorData(ip,"system.cpu.user");
	var memData = getMonitorData(ip,"system.mem.used.percent");
	var ioData = getMonitorData(ip,"system.io.util.sda");
	
	$("#lastCpu").html("CPU最后使用率："+ ((cpuData && cpuData.length) ? (cpuData[cpuData.length-1].value + "%") : 0));
	$("#lastMem").html("MEM最后使用率："+ ((memData && memData.length) ? (memData[memData.length-1].value + "%") : 0));
	console.log(ioData);
	$("#lastIO").html("IO最后使用率  ："+ ((ioData && ioData.length) ? (ioData[ioData.length-1].value + "%") : 0));
    
    require(
               [
                   "echarts",
                   "echarts/chart/line",
                   "echarts/chart/bar"
               ],
               function (ec) {
                   var myChart = ec.init(document.getElementById("monitorChart")); 
                   var option = {title : {
                       text : '最近一天性能监控图'
                   },
                   tooltip : {
                       trigger: 'item',
                       formatter : function (params) {
                           var date = new Date(params.value[0]);
                           data = date.getFullYear() + '-'
                                  + (date.getMonth() + 1) + '-'
                                  + date.getDate() + ' '
                                  + date.getHours() + ':'
                                  + date.getMinutes();
                           return data + '<br/>'
                                  + params.value[1] +"%";
                       }
                   },

                   legend : {
                       data : ['CPU','MEM','IO']
                   },
                   xAxis : [
                       {
                           type : 'time',
                           splitNumber:10
                       }
                   ],
                   yAxis : [
                       {
                           type : 'value'
                       }
                   ],
                   series : [
                       {
                           name: 'CPU',
                           type: 'line',
                           showAllSymbol: true,
                           data: (function () {
                               var d = [];
                               if(cpuData){
                            	   for(var index in cpuData){
                                	   d.push([new Date(cpuData[index].date),cpuData[index].value]);
                                   }
                               }
                               
                               return d;
                           })()
                       },{
                           name: 'MEM',
                           type: 'line',
                           showAllSymbol: true,
                           data: (function () {
                               var d = [];
                               if(memData){
                            	   for(var index in memData){
                                	   d.push([new Date(memData[index].date),memData[index].value]);
                                   }
                               }
                               return d;
                           })()
                       },{
                           name: 'IO',
                           type: 'line',
                           showAllSymbol: true,
                           data: (function () {
                               var d = [];
                               if(ioData){
                            	   for(var index in ioData){
                                	   d.push([new Date(ioData[index].date),ioData[index].value]);
                                   }  
                               }
                               return d;
                           })()
                       }
                       
                   ]};
                         myChart.setOption(option); 
                    }
                 );
}


function operation(flag,vmId,vmName,cpu,mem,vmType,rmRedPoolType,dataDeTreType,tt,runningState,vmTypeCode,platFormCode){
	//openstack----platFormCode:O
	$("#platFormCode").val(platFormCode);
	// 虚拟机列表中的OnChange事件，
	$("#vmTypeCode").val(vmTypeCode);
	if(flag == "select"){
		var value = $("#" + vmId + "_operation").val();
		var cvmDeviceId = $("#cId").val();
		// 防止用户点击某一操作并取消后 造成该操作在再次刷新前不可用。
		$("#" + vmId + "_operation").val("0");
		if(1 == value){
			startCmVM("select",vmId);
		} else if(2 == value){
			shutDownCVM("select",vmId);
		} else if(3 == value){
			createSnapshotDiv(vmId,vmName,runningState,vmTypeCode);
		} else if(4 == value){
			magSnapshotDiv(vmId,vmName);
		} else if(5 == value){
			vmRestart(vmId);
		} else if(6 == value){
			vmPause("select",vmId);
		} else if(7 == value){
			alternativeHost(cvmDeviceId,vmId,cpu,mem,"l",vmName,vmType,rmRedPoolType,dataDeTreType);
		}else if(8 == value){
			vmResume("select",vmId);
		}
		else if(10 == value){
		    vmNovnc(vmId);
		}
		else if(11 == value) {
		    vmrc(vmId);
		}else if(12 == value){
			openStackVNC(vmId);
		}else if("forcedRestart" == value){
			forcedRestartMethod(vmId);
		}else if("mountVolume" == value){
			initCondition(vmId);
			mountVolumeMethod(vmId);
		}else if("unloadVolume" == value){
			unloadVolumeMethod(vmId);
		}else if("putVmName" == value){
			putVmNameMethod(vmId,vmName);
		}
	} else{
		var vmId = $("#vmId").val();
		var vmName = document.getElementById("vm_name").innerHTML;
		var runningState = document.getElementById("vm_state").innerHTML;
		var vmTypeCode = $("#vm_virtualTypeCode").val();
		if(runningState == i18nShow('compute_res_poweron')){
			runningState = "poweron";
		}else{
			runningState = "";
		}
		if(1 == flag){
			startCmVM("a",vmId);
		} else if(2 == flag){
			shutDownCVM("a",vmId);
		} else if(3 == flag){
			createSnapshotDiv(vmId,vmName,runningState,vmTypeCode);
		} else if(4 == flag){
			magSnapshotDiv(vmId,vmName);
		} else if(5 == flag){
			vmRestart(vmId);
		} else if(6 == flag){
			vmPause("a",vmId);
		} else if(7 == flag){
			alternativeHost('','','','','',vmName,'','','');
		}else if(8 == flag){
			vmResume("a",vmId);
		}
		else if(10 == flag){
            vmNovnc(vmId);
        }
        else if(11 == flag) {
            vmrc(vmId);
        }else if(12 == flag){
			openStackVNC(vmId);
		}else if("forcedRestart" == flag){
			forcedRestartMethod(vmId);
		}else if("mountVolume" == flag){
			mountVolumeMethod(vmId);
		}else if("unloadVolume" == flag){
			unloadVolumeMethod(vmId);
		}else if("putVmName" == flag){
			putVmNameMethod(vmId,vmName);
		}
	}
}
$(function(){
	jQuery.validator.addMethod("checkVm", function(value, element) { 
		var validateValue=true;
		var snapshot_vmId = $.trim($("#snapshot_vmId").val());
		var snapshot_Name = $.trim($("#snapshot_Name").val());
		if(snapshot_vmName == ""||snapshot_vmId == ""){
			return false; 
		}else{
			return true;
		}
		return this.optional(element) || validateValue;
		},
		i18nShow('compute_res_login_err'));
	$("#snapshotAddForm").validate({
		rules: {
		snapshot_Name: {required : true,checkVm:true}
		},
		messages: {
			snapshot_Name: {required : i18nShow('compute_res_device_snapshotTip'),checkVm:i18nShow('compute_res_login_err')}
		},
		submitHandler: function() {
			saveSnapshot();
		}
	});
});

function saveSnapshotBtn(){
	$("#snapshotAddForm").submit(); 
}
// 新建快照
function saveSnapshot() {
	var snapshot_Name = $.trim($("#snapshot_Name").val());
	var snapshot_vmName = $.trim($("#snapshot_vmName").val());
	var snapshot_vmId = $.trim($("#snapshot_vmId").val()); 
	var silence = document.getElementById("silence").value;
	var memory = document.getElementById("memory").value;
	if($("#silence").attr("checked")=="checked"){
		silence = 'true';
	}else {
		silence = 'false';
	}
	if($("#memory").attr("checked")=="checked"){
		memory = 'true';
	}else{
		memory = 'false';
	}
	/*
	 * var reg = new RegExp("^[0-9]*$"); if(!reg.test(snapshot_Name)){ encode
	 * alert("请按照时间轴来命名，精确到时间分钟数(全是数字)"); return false; }
	 */
	
	showTip(i18nShow('compute_res_device_createSnapshotTip'),function(){
		showTip("load");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx +"/createSnapshot.mvc",  
		data : {"snapshotName" : encodeURIComponent(snapshot_Name),
				"vmId":snapshot_vmId,
				"vmName":snapshot_vmName,
				"snapshotMemory":memory,
				"snapshotSilence":silence},
		error : function() {// 请求失败处理函数
			closeTip();
			showError();
		},
		success : function(data) { // 请求成功后处理函数。
			// zTreeInit("treeRm", data);
			closeTip();
			if(data=="success"){
				cancelSnapshotDiv();
				showTip(i18nShow('compute_res_device_createSnapshotSuccess'));
			}else if(data=="db fail"){
				// cancelSnapshotDiv();
				showError(i18nShow('compute_res_device_createSnapshotFail'));
			}else if(data=="name existed"){
				showError(i18nShow('compute_res_device_snapshot_nameExisted'));
			}else{
				cancelSnapshotDiv();
				showError(i18nShow('compute_res_device_createSnapshotFail'));
			}
			
		}
	});
	});
}

function showSnapshotList(vmId) {
	$("#snapshotGridTable").jqGrid({
		url :  ctx +"/listSnapshot.mvc", 
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		postData : {vmId : vmId},
		mtype : "post", // 提交方式
		autowidth : true, // 是否自动调整宽度
		height : 300,
		multiselect:false,
		multiboxonly: false,
		colModel : [  {
			name : "snapshotName",
			index : "snapshotName",
			label : i18nShow('compute_res_device_snapshot_name'),
			width : 120,
			sortable : true,
			align : 'left'
			
		},{
			name : "createUser",
			index : "createUser",
			label : i18nShow('compute_res_creator'),
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			name : "createDate",
			index : "createDate",
			label : i18nShow('compute_res_createDate'),
			width : 170,
			sortable : true,
			align : 'left',
			formatter: function(cellValue,options,rowObject){
				return formatTime(cellValue);
          }
		},  {
			name : "vmName",
			index : "vmName",
			label : i18nShow('compute_res_vmName'),
			width : 195,
			sortable : true,
			align : 'left'
		},
		 {
			name : "option",
			index : "option",
			label : i18nShow('compute_res_PowerStatus'),
			width : 195,
			sortable : true,
			align : 'left',
			hidden : true,
			formatter : function(cellValue,options,rowObject) {
				var s = rowObject.snapSilence;
				var m = rowObject.snapMemory;
				if(m == 'true'){
					return i18nShow('compute_res_device_poweron');
				}else{
					return i18nShow('compute_res_device_poweroff');
				}
			}
		},
		{
			name : "snapSilence",
			index : "snapSilence",
			label : i18nShow('compute_res_device_snapSilence'),
			width : 195,
			sortable : true,
			align : 'left',
			hidden : true
		},
		{
			name : "snapMemory",
			index : "snapMemory",
			label : i18nShow('compute_res_device_snapMemory'),
			width : 195,
			sortable : true,
			align : 'left',
			hidden : true
		},
		{
			name : 'option',
		    index : 'option',
		    label : i18nShow('com_operate'),
			width : 150,
			align : "left",
			sortable:false,
			formatter : function(cellValue,options,rowObject) {
				var ref="";
				ref += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=revertSnap('"+rowObject.vmName+"','"+rowObject.snapshotName+"','"+rowObject.vmId+"','"+rowObject.snapSilence+"','"+rowObject.snapMemory+"') >"+i18nShow('compute_res_device_snapRecovery')+"</a>";  
				ref += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteSnap('"+rowObject.vmName+"','"+rowObject.snapshotName+"','"+rowObject.vmId+"') >"+i18nShow('compute_res_device_snapDelete')+"</a>" ;   
				return 	ref;
			}														
		}],
		viewrecords : true,
		sortname : "id",
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
		pager : "#snapshotgridPager",
		hidegrid : false
	});
}

function getDeviceState(hostId){
	var flag = "";
	$.ajax({
	    async:false,
	    cache:false,
	    type: 'POST',
	    url:ctx+"/resmgt-common/device/getPmRunningState.action",
	    data:{"hostId":hostId},
	    error: function () {//请求失败处理函数
	    	showError(i18nShow('compute_res_requestError'));
	    },
	    success:function(data){ //请求成功后处理函数。
	    	flag = data.result;
	    }
	});
	return flag;

}
// 新建快照弹框
function createSnapshotDiv(vmId,vmName,runningState,vmTypeCode){
	$("label.error").remove();
	if(vmTypeCode == 'VM'){
   	 var state = getDeviceState(vmId);
   	 // 只有是开机状态，才可以选择内存快照或者静默状态快照
   	 if(state == "poweron"){
	    	$("#memory").attr("checked",true);
	    	$("#memoryAndSilence").show();
	    }else{
	    	$("#memoryAndSilence").hide();
	    }
    }else if(vmTypeCode == 'KV'){
   	 $("#memoryAndSilence").hide();
    }
     $("#snapshotAddDiv").dialog({  
    	 	autoOpen : true,
			modal:true,
			height:300,
			width:500,
			title:i18nShow('compute_res_device_createSnapshot'),
			resizable:false,
            close: function () {
            	$("#silence").attr("checked",false);
            	$("#memory").attr("checked",false);
            	$("#snapshot_Name").val("");
            }
        });  
     
   
	$("#snapshot_vmId").val(vmId);
	$("#snapshot_vmName").val(vmName);
	
}
// 快照管理弹框
function magSnapshotDiv(vmId,vmName){
	$("label.error").remove();
	// 得到当前窗口的父类
	 var dialogParent = $("#snapshotMagDiv").parent();
	 // 对窗口进行克隆，并进行隐藏
     var dialogOwn = $("#snapshotMagDiv").clone();
     dialogOwn.hide();  
     
          
     $("#snapshotMagDiv").dialog({  
    	 	autoOpen : true,
			modal:true,
			height:400,
			width:640,
			title:i18nShow('compute_res_device_snapshotManage'),
			resizable:false,
            close: function () {
            	// 将隐藏的克隆窗口追加到页面上
                dialogOwn.appendTo(dialogParent);  
                $(this).dialog("destroy").remove(); 
            }
        });
     $("#snapshotMagButtonDiv").show();
     
	// ----------------------------------------
// clearTab();
	// 清除form所有数据
// clearForm($('#snapshotMagDiv'));
	// 使form所有数据为可输入
// $("#snapshotMagDiv").enable();
	// 添加隐藏域的默认值
	$("#snapshot_vmId").val(vmId);
	$("#snapshot_vmName").val(vmName);
	showSnapshotList(vmId);
    $(".ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-draggable").css("width","660px");
	$("#snapshotMagDiv").css("height","360px");
}
function cancelSnapshotDiv(){
	$("#snapshotAddDiv").dialog("close");
}

function cancelSnapshotMagDiv(){
	$("#snapshotMagDiv").dialog("close");
}


function isCmVm(id){
    var flag = false;
    $.ajax({
         async : false,
         cache : false,
         type : 'POST',
         url : ctx + "/resmgt-common/device/isCmVm.action" , 
         data : { "hostId":id},
         error : function() {// 请求失败处理函数
             closeTip();
             showError(i18nShow('compute_res_requestError'));
         },
         success : function(data) { // 请求成功后处理函数。
             closeTip();
              if(data.result!=null && data.result!="" && data.result=='true'){
                  flag = true;
             } else{
                  flag = false;
             }
         }
    });
    return flag;
}

// 恢复快照
function revertSnap(vmName,snapshotName,vmId,silence,memory){
	var url= ctx +"/revertSnapshot.mvc";
	showTip(i18nShow('compute_res_device_recoverSnapshot_tip'),function(){
		$.jBox.close();
		showTip("load");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,  
		data : {"snapshotName" : snapshotName,"vmId":vmId,"vmName":vmName,"snapSilence":silence,"snapMemory":memory},
		error : function() {// 请求失败处理函数
			closeTip();
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) { // 请求成功后处理函数。
			closeTip();
			if(data=="success"){
				showTip(i18nShow('compute_res_device_recoverSnapshot_success'));
				cancelSnapshotMagDiv();
				var state = vmRunningState(vmId);
				var type = "";
				ajaxCall("/resmgt-compute/host/getCmDeviceVMInfo.action",{"vmId" : vmId},
						function(){type = ajaxResult.virtualTypeCode;})
				var nodes = zTree.getSelectedNodes();
			    var isCmVmFlag = isCmVm(nodes[0].id);
				if(type == 'VM'){
					if(memory == 'true'){
						if(isCmVmFlag == 'true'){
							updateNode("a","poweron",vmId);
							loadVmState("a",vmId,"poweron");
						}else{
							updateNode("select","poweron",vmId);
							loadVmState("select",vmId,"poweron");
						}
					}else {
						if(state == 'paused'){
							if(isCmVmFlag == 'true'){
								updateNode("a","paused",vmId);
								loadVmState("a",vmId,"paused");
							}else{
								updateNode("select","paused",vmId);
								loadVmState('select',vmId,"paused");
							}
						}else{
							if(isCmVmFlag == 'true'){
								updateNode("a","poweroff",vmId);
								loadVmState("a",vmId,"poweroff");
							}else{
								updateNode("select","poweroff",vmId);
								loadVmState("select",vmId,"poweroff");
							}
						}
					}
				}else if(type == 'KV'){
					
					if(state == 'poweron'){
						if(isCmVmFlag == 'true'){
							updateNode("a","poweron",vmId);
							loadVmState("a",vmId,"poweron");
						}else{
							updateNode("select","poweron",vmId);
							loadVmState("select",vmId,"poweron");
						}
					}else if(state == 'poweroff'){
						if(isCmVmFlag == 'true'){
							updateNode("a","poweroff",vmId);
							loadVmState("a",vmId,"poweroff");
						}else{
							updateNode("select","poweroff",vmId);
							loadVmState("select",vmId,"poweroff");
						}
					}else if(state == 'paused'){
						
						if(isCmVmFlag == 'true'){
							updateNode("a","paused",vmId);
							loadVmState("a",vmId,"paused");
						}else{
							updateNode("select","paused",vmId);
							loadVmState('select',vmId,"paused");
						}
					}
				}
			}else if(data=="db fail"){
				showError(i18nShow('compute_res_dbFail'));
			}else{
				showError(i18nShow('compute_res_device_recoverSnapshot_fail'));
			}
		}
	});
	});
}
function vmRunningState(vmId){
	var state ="";
	$.ajax({
		async : false,
		type : 'POST',
		url : ctx+"/resmgt-compute/vm/vmRunningState.action",  
		data : {"vmId":vmId},
		error : function() {// 请求失败处理函数
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) { // 请求成功后处理函数。
			state =  data.result;
		}
	});
	var arr = new Array();
	arr = state.split(":");
	return arr[1];
}
// 删除快照
function deleteSnap(vmName,snapshotName,vmId){
	var url= ctx +"/removeSnapshot.mvc";
	showTip(i18nShow('compute_res_device_delSnapshot_tip'),function(){
		$.jBox.close();
		showTip("load");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,  
		data : {"snapshotName" : snapshotName,"vmId":vmId,"vmName":vmName},
		error : function() {// 请求失败处理函数
			closeTip();
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) { // 请求成功后处理函数。
			closeTip();
			if(data=="success"){
				cancelSnapshotMagDiv();
				magSnapshotDiv(vmId,vmName);
				showTip(i18nShow('compute_res_delSuccess'));
			}else if(data=="db fail"){
				showError(i18nShow('compute_res_dbFail'));
			}else{
				showError(i18nShow('compute_res_delFail'));
			}
		}
	});
  });
}

function updateNode(flag,state,vmId) {
	var node = "";
	var zTree = $.fn.zTree.getZTreeObj( "treeRm" );
	if(zTree=='undefined'){}else{
		if(flag == 'a'){
		    var nodes = zTree.getSelectedNodes();
		    node = zTree.getNodeByParam('id',nodes[0].id,null);
		}else if(flag == 'select' ){
			 node = zTree.getNodeByParam("id",vmId,null);
		}
		 if(state == "poweron"){
			 state = 'VM_Start.png';
		 }else if(state == "poweroff"){
			 state = 'VM_Stop.png';
		 }else if(state == "paused"){
			 state = 'VM_Hangup.png';
		 }else{
			 state = 'VM_Unknow.png';
		 }
		 if(node!=null){
			 node.icon =iconPath+state;
			 zTree.updateNode(node); 
		 }
	}
}
// 虚拟机开机
function startCmVM(flag,vmId){
	var url= ctx +"/powerOn.mvc";
	showTip(i18nShow('compute_res_device_poweron_tip'),function(){
	showTip("load");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,  
		data : {"vmId":vmId},
		error : function() {// 请求失败处理函数
			closeTip();
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) { // 请求成功后处理函数。
			closeTip();
			if(data=="success"){
				showTip(i18nShow('compute_res_device_poweron_success'));
				updateNode(flag,"poweron",vmId);
				loadVmState(flag,vmId,"poweron");
			}else{
				showError(i18nShow('compute_res_device_poweron_fail'));
			}
		}
	});
  });
}
// 虚拟机关机
function shutDownCVM(flag,vmId){
	var url= ctx +"/powerOff.mvc";
	showTip(i18nShow('compute_res_device_poweroff_tip'),function(){
	showTip("load");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,  
		data : {"vmId":vmId},
		error : function() {// 请求失败处理函数
			closeTip();
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) { // 请求成功后处理函数。
			closeTip();
			if(data=="success"){
				showTip(i18nShow('compute_res_device_poweroff_success'));
				updateNode(flag,"poweroff",vmId);
				loadVmState(flag,vmId,"poweroff");
			}else{
				showError(i18nShow('compute_res_device_poweroff_fail'));
			}
		}
	});
  });
}

function loadVmState(flag,vmId,state){
	//opensatck用,虚拟机页面获取

	var vmPlatFormCode = $("#vm_platFormCode").val();
	//openstack  select
	var platFormCode = $("#platFormCode").val();
	//页面
	var vm_type = $("#vm_virtualTypeCode").val();
	//select
	var vmTypeCode = $("#vmTypeCode").val();
	// 虚拟机列表
	if(flag == "select"){
		//openstack判断
		if((platFormCode == 'O') && (vmTypeCode=='OV' || vmTypeCode =='OI' || vmTypeCode=='OK')){
			if(state == "poweroff"){
				$("#" + vmId + "_state").html(i18nShow('compute_res_poweroff'));
				$("#" + vmId + "_operation").empty();
				jQuery("<option value='0' select='selected'>"+i18nShow('compute_res_select')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='1'>"+i18nShow('compute_res_device_poweron')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='mountVolume'>"+i18nShow('compute_res_device_mountVolume')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='unloadVolume'>"+i18nShow('compute_res_device_unloadVolume')+"</option>").appendTo("#" + vmId + "_operation");
			}else if(state == "poweron"){
				$("#" + vmId + "_state").html(i18nShow('compute_res_poweron'));
				$("#" + vmId + "_operation").empty();
				jQuery("<option value='0' select='selected'>"+i18nShow('compute_res_select')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='2'>"+i18nShow('compute_res_device_poweroff')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='5'>"+i18nShow('compute_res_device_restart')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='forcedRestart'>"+i18nShow('compute_res_device_forcedRestart')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='mountVolume'>"+i18nShow('compute_res_device_mountVolume')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='unloadVolume'>"+i18nShow('compute_res_device_unloadVolume')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='12'>"+i18nShow('compute_res_device_remoteControl')+"</option>").appendTo("#" + vmId + "_operation");
			}
		}else if(vmTypeCode == 'PV'){
			if(state == "poweroff"){
				$("#" + vmId + "_state").html(i18nShow('compute_res_poweroff'));
				$("#" + vmId + "_operation").empty();
				jQuery("<option value='0' select='selected'>"+i18nShow('compute_res_select')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='1'>"+i18nShow('compute_res_device_poweron')+"</option>").appendTo("#" + vmId + "_operation");
			}else if(state == "poweron"){
				$("#" + vmId + "_state").html(i18nShow('compute_res_poweron'));
				$("#" + vmId + "_operation").empty();
				jQuery("<option value='0' select='selected'>"+i18nShow('compute_res_select')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='2'>"+i18nShow('compute_res_device_poweroff')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='5'>"+i18nShow('compute_res_device_restart')+"</option>").appendTo("#" + vmId + "_operation");
			}else{}
		}else{
			$("#" + vmId + "_operation").empty();
			if(state == "poweroff"){
				$("#" + vmId + "_state").html(i18nShow('compute_res_poweroff'));
				jQuery("<option value='0' select='selected'>"+i18nShow('compute_res_select')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='1'>"+i18nShow('compute_res_device_poweron')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='3'>"+i18nShow('compute_res_device_createSnapshot')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='4'>"+i18nShow('compute_res_device_snapshotManage')+"</option>").appendTo("#" + vmId + "_operation");
			}else if(state == "poweron"){
				$("#" + vmId + "_state").html(i18nShow('compute_res_poweron'));
				jQuery("<option value='0' select='selected'>"+i18nShow('compute_res_select')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='2'>"+i18nShow('compute_res_device_poweroff')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='6'>"+i18nShow('compute_res_device_hang')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='5'>"+i18nShow('compute_res_device_restart')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='3'>"+i18nShow('compute_res_device_createSnapshot')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='4'>"+i18nShow('compute_res_device_snapshotManage')+"</option>").appendTo("#" + vmId + "_operation");
				if(vmTypeCode == 'KV'){
					jQuery("<option value='10'>"+i18nShow('compute_res_device_remoteControl')+"</option>").appendTo("#" + rowData.vid + "_operation");
                }else if(vmTypeCode == 'VM'){
                	jQuery("<option value='11'>"+i18nShow('compute_res_device_remoteControl')+"</option>").appendTo("#" + rowData.vid + "_operation");
                }
			}else if(state == "paused"){
				$("#" + vmId + "_state").html(i18nShow('compute_res_hang'));
				jQuery("<option value='0' select='selected'>"+i18nShow('compute_res_select')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='8'>"+i18nShow('compute_res_device_awaken')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='2'>"+i18nShow('compute_res_device_poweroff')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='3'>"+i18nShow('compute_res_device_createSnapshot')+"</option>").appendTo("#" + vmId + "_operation");
				jQuery("<option value='4'>"+i18nShow('compute_res_device_snapshotManage')+"</option>").appendTo("#" + vmId + "_operation");
			}
			
		}
		// 虚拟机页面按钮
	} else if(flag == "a"){
		if(vmPlatFormCode == 'O'){
			var vm_virtualTypeCode = $("#vm_virtualTypeCode").val();;
			if(state == "poweron"){
				$('#vm_state').html(i18nShow('compute_res_poweron'));
				document.getElementById(2).style.display="inline";//关机
				document.getElementById(5).style.display="inline";//重启
				//强制重启
				document.getElementById('forcedRestart').style.display="inline";
				//挂载卷
				document.getElementById('mountVolume').style.display="inline";
				//卸载卷
				document.getElementById('unloadVolume').style.display="inline";
				document.getElementById(1).style.display="none";
				document.getElementById(3).style.display="none";
				document.getElementById(4).style.display="none";
				document.getElementById(6).style.display="none";
				if(vm_virtualTypeCode =='OI'){//物理机，不显示迁移按钮
					document.getElementById(7).style.display="none";
				}else{
					document.getElementById(7).style.display="inline";
				}
				document.getElementById(8).style.display="none";
				document.getElementById(9).style.display="none";
				document.getElementById(12).style.display="inline";
			}else if(state == "poweroff"){
				$('#vm_state').html(i18nShow('compute_res_poweroff'));
				document.getElementById(1).style.display="inline";//开机
				document.getElementById('forcedRestart').style.display="none";
				//挂载卷
				document.getElementById('mountVolume').style.display="inline";
				//卸载卷
				document.getElementById('unloadVolume').style.display="inline";
				document.getElementById(5).style.display="none";
				document.getElementById(2).style.display="none";
				document.getElementById(3).style.display="none";
				document.getElementById(4).style.display="none";
				document.getElementById(6).style.display="none";
				document.getElementById(7).style.display="none";
				document.getElementById(8).style.display="none";
				document.getElementById(9).style.display="none";
				document.getElementById(12).style.display="none";
			}
		}else{
		if(vm_type == 'PV'){
			document.getElementById(3).style.display="none";
			document.getElementById(4).style.display="none";
			document.getElementById(6).style.display="none";
			document.getElementById(8).style.display="none";
			document.getElementById('forcedRestart').style.display="none";	//强制重启
			document.getElementById('mountVolume').style.display="none";	//挂载卷
			document.getElementById('unloadVolume').style.display="none";	//卸载卷
			if(state == "poweroff"){
				$("#vm_state").html(i18nShow('compute_res_poweroff'));
				document.getElementById(2).style.display="none";
				document.getElementById(5).style.display="none";
				document.getElementById(1).style.display="inline";
				document.getElementById(9).style.display="inline";
				document.getElementById(7).style.display="inline";
			}else if(state == "poweron"){
				$("#vm_state").html(i18nShow('compute_res_poweron'));
				document.getElementById(7).style.display="none";
				document.getElementById(2).style.display="inline";
				document.getElementById(5).style.display="inline";
				document.getElementById(1).style.display="none";
				document.getElementById(9).style.display="inline";
			}else{}
		}else{
			document.getElementById(3).style.display="inline";
			document.getElementById(4).style.display="inline";
			// document.getElementById(9).style.display="inline";
			document.getElementById('forcedRestart').style.display="none";	//强制重启
			document.getElementById('mountVolume').style.display="none";	//挂载卷
			document.getElementById('unloadVolume').style.display="none";	//卸载卷
			if(state == "poweroff"){
				$("#vm_state").html(i18nShow('compute_res_poweroff'));
				document.getElementById(1).style.display="inline";
				document.getElementById(2).style.display="none";
				document.getElementById(5).style.display="none";
				document.getElementById(6).style.display="none";
				document.getElementById(7).style.display="none";
				document.getElementById(8).style.display="none";
				document.getElementById(10).style.display="none";
				document.getElementById(11).style.display="none";
			}else if(state == "poweron"){
				$("#vm_state").html(i18nShow('compute_res_poweron'));
				document.getElementById(1).style.display="none";
				document.getElementById(2).style.display="inline";
				document.getElementById(5).style.display="inline";
				document.getElementById(6).style.display="inline";
				document.getElementById(7).style.display="inline";
				document.getElementById(8).style.display="none";
				if(vm_type == 'KV'){
					document.getElementById(10).style.display="inline";
                }else if(vm_type == 'VM'){
                	document.getElementById(11).style.display="inline";
                }
			}else if(state == "paused"){
				$("#vm_state").html(i18nShow('compute_res_hang'));
				document.getElementById(1).style.display="none";
				document.getElementById(2).style.display="inline";
				document.getElementById(5).style.display="none";
				document.getElementById(6).style.display="none";
				document.getElementById(7).style.display="none";
				document.getElementById(8).style.display="inline";
				document.getElementById(10).style.display="none";
				document.getElementById(11).style.display="none";
			}
		}
	}
	} else {
		i18nShow('compute_res_operateFail');
	}
}
// 虚拟机重启
function vmRestart(vmId){
	var url= ctx +"/vmRestart.mvc";
	showTip(i18nShow('compute_res_device_restart_tip'),function(){
		showTip("load");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,  
		data : {"vmId":vmId},
		error : function() {// 请求失败处理函数
			closeTip();
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) { // 请求成功后处理函数。
			closeTip();
			if(data=="success"){
				showTip(i18nShow('compute_res_device_restart_success'));
			}else{
				showError(i18nShow('compute_res_device_restart_fail'));
			}
		}
	});
  });
}
// 虚拟机挂起
function vmPause(flag,vmId){
	var url= ctx +"/vmPause.mvc";
	showTip(i18nShow('compute_res_device_hang_tip'),function(){
		showTip("load");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,  
		data : {"vmId":vmId},
		error : function() {// 请求失败处理函数
			closeTip();
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) { // 请求成功后处理函数。
			closeTip();
			if(data=="success"){
				showTip(i18nShow('compute_res_device_hang_success'));
				updateNode(flag,"paused",vmId);
				loadVmState(flag,vmId,"paused");
			}else{
				showError(i18nShow('compute_res_device_hang_fail'));
			}
		}
	});
  });
}

// 唤醒虚拟机
function vmResume(flag,vmId){
var url= ctx +"/vmResume.mvc";
showTip(i18nShow('compute_res_device_awaken_tip'),function(){
	showTip("load");
$.ajax({
	async : false,
	cache : false,
	type : 'POST',
	url : url,  
	data : {"vmId":vmId},
	error : function() {// 请求失败处理函数
		closeTip();
		showError(i18nShow('compute_res_requestError'));
	},
	success : function(data) { // 请求成功后处理函数。
		closeTip();
		if(data=="success"){
			showTip(i18nShow('compute_res_device_awaken_success'));
			updateNode(flag,"poweron",vmId);
			loadVmState(flag,vmId,"poweron");
		}else{
			showError(i18nShow('compute_res_device_awaken_fail'));
		}
	}
});
});
}
/**
 * 打开VNC
 * 
 * @param {Object}
 *            flag
 * @param {Object}
 *            vmId
 */
function vmNovnc(vmId){
  showTip(i18nShow('compute_res_device_remoteControl_tip'),function(){
    var url = ctx + '/novnc/main/indexAct.action?vmId=' + vmId;
    window.open (url);
  });
}

function vmrc(vmId) {
    showTip(i18nShow('compute_res_device_remoteControl_tip'),function(){
    var url = ctx + '/vmrc/main/indexAct.action?vmId=' + vmId;
    window.open (url);
  });
}

function openStackVNC(vmId){
	var url= ctx +"/getVNCConsole.mvc";
	showTip(i18nShow('compute_res_device_remoteControl_tip'),function(){
		showTip("load");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,  
		data : {"vmId":vmId},
		error : function() {// 请求失败处理函数
			closeTip();
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) {
			window.open (data);
			closeTip();
		}
	});
  });
	
}
/**
 * openstack虚拟机，强制重启
 * */
function forcedRestartMethod(vmId){
	var url= ctx +"/vmForcedrestart.mvc";
	showTip(i18nShow('compute_res_device_forcedRestart_tip'), function(){
		showTip("load");
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,
			data : {'vmId': vmId},
			error : function() {  // 请求失败处理函数
				closeTip();
				showError(i18nShow('compute_res_requestError'));
			},
			success : function(data) {
				closeTip();
				if(data=="success"){
					showTip(i18nShow('compute_res_device_forcedRestart_success'));
					/*updateNode(flag,'运行中',vmId);
					loadVmState(flag,vmId,i18nShow('compute_res_poweron'));*/
				}else{
					showTip(i18nShow('compute_res_device_forcedRestart_fail'));
				}
				
			}
		});
  });

}

//挂载卷div
function mountVolumeMethod(vmId){
	 $("#newBuildId").css("display","inline");
	 $("#newBuild").val("");
	 $("#volumeSizeId").css("display","inline");
	 $("#volumeSize").val("");
	 $("#yesOrNoId").css("display","inline");
	 $("#yesOrNo").val("");
	 $("#_volumeType").val("");
	 $("#mountGridTable_div").hide();
	 $.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : ctx +"/resmgt-common/device/findVolumeTypeList.action",
			data : {'availableZoneId': $("#availableZoneId").val()},
			error : function() {  // 请求失败处理函数
				closeTip();
				showError(i18nShow('compute_res_requestError'));
			},
			success : function(data) {
				$("#_volumeType").empty();
				$("#_volumeType").append("<option value=''>请选择</option>");
				for(var i = 0; i < data.length; i++){
					$("#_volumeType").append("<option value='"+data[i]+"'>"+data[i]+"</option>");
				}
			}
		});
	 
	 $("#mountVolumeDiv").dialog({  
 	 	autoOpen : true,
			modal:true,
			height:600,
			width:800,
			title:i18nShow('compute_res_device_mountVolume'),
			resizable:false,
			close: function () {
				
         }
     }); 
	 tempVmId = vmId;
}
function saveMountVolumeBtn(){
	var vmId = $("#vmId").val();
	if(vmId == null || vmId == ""){
		vmId = tempVmId
	}
	var size = $("#volumeSize").val();
	var shareFlag = $("#yesOrNo").val();
	var volumeType = $("#_volumeType").val();
	var share = "";
	if(shareFlag == 'Y'){
		share = 'true';
	}else{
		share = 'false';
	}
	var url= ctx +"/resmgt-common/device/createServerVolume.action";
	showTip(i18nShow('compute_res_device_mountVolume_tip'), function(){
		showTip("load");
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,
			data : {'vmId': vmId,'volumeSize':size,'share':share,'volumeType':volumeType},
			error : function() {  // 请求失败处理函数
				closeTip();
				showError(i18nShow('compute_res_requestError'));
			},
			success : function(data) {
				closeTip();
				if(data.result=="success"){
					showTip(i18nShow('compute_res_device_createVolume_success'));
					tempVmId="";
					reloadVmInfo(vmId);
					closeViews('mountVolumeDiv')
				}else{
					closeTip();
					showTip(i18nShow('compute_res_device_mountVolume_fail'));
				}
			}
		});
  });
}

//保存挂载卷
function saveMountVolume(id,diskSize,vmId){
	var url= ctx +"/resmgt-common/device/mountVolume.action";
	showTip(i18nShow('compute_res_device_mountVolume_tip'), function(){
		showTip("load");
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,
			data : {'vmId': vmId,'volumeId':id,"volumeSize":diskSize},
			error : function() {  // 请求失败处理函数
				closeTip();
				showError(i18nShow('compute_res_requestError'));
			},
			success : function(data) {
				closeTip();
				if(data.result=="success"){
					showTip(i18nShow('compute_res_device_mountVolume_success'));
					closeViews('mountVolumeDiv')
					reloadVmInfo(vmId);
					//$("#mountGridTable").jqGrid().trigger("reloadGrid");
				}else{
					showTip(i18nShow('compute_res_device_mountVolume_fail'));
				}
			}
		});
  });
}

//修改虚机名称div
function putVmNameMethod(vmId,vmName){
	$("#vmName").val(vmName);
	$("#putVmId").val(vmId);
	$("#projectVpcId").val();
	$("#putVmNameDiv").dialog({  
 	 		autoOpen : true,
			modal:true,
			height:200,
			width:400,
			title:"修改名称",
			resizable:false,
			close : function(){
				reloadVmInfo(vmId);
			}
     });  
}

function putVmName(){
	var url= ctx +"/resmgt-common/device/putVmName.action";
	var vmName= $("#vmName").val();
	var vmId = $("#putVmId").val();
	var projectId = $("#projectVpcId").val();
	if(!vmName){
		showTip("名称不能为空");
		return false;
	}
	showTip('确认修改吗？', function(){
		showTip("load");
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,
			data : {'vmName': vmName,'vmId':vmId,'projectId':projectId},
			error : function() {  // 请求失败处理函数
				closeTip();
				showError(i18nShow('compute_res_requestError'));
			},
			success : function(data) {
				closeTip();
				if(data.result=="success"){
					showTip(i18nShow('compute_res_operateSuccess'));
					closeViews('putVmNameDiv');
					reloadVmInfo(vmId);
				}else{
					closeViews('putVmNameDiv');
					showTip(i18nShow('compute_res_operateFail'));
				}
			}
		});
  });
}

//卸载卷div
function unloadVolumeMethod(vmId){
	$("#unMountVolumeDiv").dialog({  
 	 		autoOpen : true,
			modal:true,
			height:420,
			width:700,
			title:i18nShow('compute_res_device_unloadVolume'),
			resizable:false,
			close : function(){
				reloadVmInfo(vmId);
			}
     });  
	
	$("#unMountGridTable_div").show();
	$("#unMountGridTable").show();
	//$("#unMountGridPager").show();
	//先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
	$("#unMountGridTable").jqGrid().GridUnload("unMountGridTable");
	$("#unMountGridTable").jqGrid({
		url : ctx+"/resmgt-common/device/getRmDeviceVolumesRefPoList.action",
		rownumbers : false, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 300,
		postData:{"deviceId":vmId},
		autowidth : true, // 是否自动调整宽度
		multiboxonly: false,
		colModel : [ {
			name : "id",
			index : "id",
			label : "id",
			width : 40,
			sortable : true,
			align : 'left',
			hidden:true	
		},{
			name : "targetVolumeId",
			index : "targetVolumeId",
			label : i18nShow('compute_res_volumeId'),
			width : 100,
			sortable : true,
			align : 'left',
			hidden:false	
		},  {
			name : "volumeName",
			index : "volumeName",
			label : i18nShow('compute_res_volumeName'),
			width : 90,
			sortable : true,
			search:true,
			align : 'left'
		},  {
			name : "diskSize",
			index : "diskSize",
			label : i18nShow('compute_res_volume_diskSize'),
			width : 30,
			sortable : true,
			search:true,
			align : 'left'
		}, {
			name : 'option',
		    index : 'option',
		    label : i18nShow('com_operate'),
			width : 30,
			align : "left",
			sortable:false,
			formatter : function(cellValue,options,rowObject) {
				return  "<a  style='margin-right: 10px;margin-left: 5px;text-decoration:none;' href='javascript:#' title=''  onclick=saveUnMountVolume('"+rowObject.targetVolumeId+"','"+vmId+"')>"+i18nShow('compute_res_device_unloadVolume')+"</a>";
			}														
		}],
		
		viewrecords : true,
		sortname : "id",
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
		pager : "#unMountGridPager",
		hidegrid : false
	});
}

function saveUnMountVolume(id,vmId){
	var url= ctx +"/resmgt-common/device/unmountServerVolume.action";
	showTip(i18nShow('compute_res_device_unloadVolume_tip'), function(){
		showTip("load");
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,
			data : {'vmId': vmId,'volumeId':id},
			error : function() {  // 请求失败处理函数
				closeTip();
				showError(i18nShow('compute_res_requestError'));
			},
			success : function(data) {
				closeTip();
				if(data.result=="success"){
					showTip(i18nShow('compute_res_device_unloadVolume_success'));
					reloadVmInfo(vmId);
					$("#unMountGridTable").jqGrid().trigger("reloadGrid");
				}else{
					showTip(i18nShow('compute_res_device_unloadVolume_fail'));
				}
			}
		});
  });
}


function reloadVmInfo(vmId){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx+"/resmgt-compute/host/getCmDeviceVMInfo.action",
		data : {'vmId': vmId},
		error : function() {  // 请求失败处理函数
			closeTip();
			showTip(i18nShow('compute_res_requestError'));
		},
		success : function(data) {
			var mountDiskSize = undefined==ajaxResult.mountDiskSize||null==ajaxResult.mountDiskSize||""==ajaxResult.mountDiskSize?'0':ajaxResult.mountDiskSize;
			$("#vm_name").html(data.vm_name);
			$('#mountInfo').html(data.mountDiskNumber+i18nShow('compute_res_device_mountDiskNumbe')+"/"+mountDiskSize+"G");
			$('#mountInfo').html("<a  style='margin-right: 10px;margin-left: 5px;text-decoration:none;' href='javascript:#' title=''  onclick=showMountVolumes('"+vmId+"')>"+data.mountDiskNumber+i18nShow('compute_res_device_mountDiskNumbe')+"/"+mountDiskSize+"G</a>");
		}
	});
}
//关闭div
function closeViews(id){
	$("#"+id).dialog("close");
}