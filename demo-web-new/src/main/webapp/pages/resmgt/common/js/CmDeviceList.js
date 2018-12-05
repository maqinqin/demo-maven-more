function initCmDeviceList() {
	$("#deviceGridTable").jqGrid({
		url : ctx+"/resmgt-common/device/getDeviceList.action", // resmgt-common/device提交的action地址ctx+"/resmgt-storage/device/getStorageDataStoresById.action
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : heightTotal() + 40,
		autowidth : true, // 是否自动调整宽度
		/*multiselect:true,*/
		colNames:['id',i18nShow('res_device_sn'),i18nShow('tip_floating_ip_device_name'),i18nShow('my_req_host_res_pool'),i18nShow('my_req_sr_deviceType'),i18nShow('device_type_code'),i18nShow('storage_res_pool_location_code'),i18nShow('storage_res_pool_device_model'),i18nShow('info_host_manufacturer'),/*i18nShow('res_device_bareState'),*/i18nShow('res_device_isInvc_state'),i18nShow('com_operate')],
		multiboxonly: false,
		colModel : [  {
			name : "id",
			index : "id",
			//label : i18nShow('compute_res_deviceId'),
			width : 120,
			sortable : true,
			align : 'left',
			hidden: true	
		}, {
			name : "sn",
			index : "sn",
			//label : i18nShow('res_device_order'),
			width : 140,
			sortable : true,
			align : 'left',
			formatter : function(cellValue,options,rowObject) {
				if(cellValue != null && cellValue !=""){
					return "<a href='#' style='text-decoration : none' onclick=showLookDiv('"+rowObject.id+"','"+rowObject.deviceTypeCode+"')>"+cellValue+"</a>"
				}else{
					return "";
				}
				 
			}			
		}, {
			name : "deviceName",
			index : "deviceName",
			//label : i18nShow('tip_floating_ip_device_name'),
			width : 210,
			sortable : true,
			align : 'left'
			
		}, {
			name : "poolName",
			index : "poolName",
			//label : i18nShow('my_req_host_res_pool'),
			width : 240,
			sortable : true,
			align : 'left'
		}, {
			name : "deviceType",
			index : "deviceType",
			//label : i18nShow('my_req_sr_deviceType'),
			width : 75,
			sortable : true,
			align : 'left'
		},{
			name : "deviceTypeCode",
			index : "deviceTypeCode",
			//label : i18nShow('device_type_code'),
			width : 75,
			sortable : true,
			hidden : true
		},{
			
			name : "seatId",
			index : "seatId",
			//label : i18nShow('storage_res_pool_location_code'),
			width : 120,
			sortable : true,
			align : 'left'	
		},  {
			name : "deviceModel",
			index : "deviceModel",
			//label : i18nShow('storage_res_pool_device_model'),
			width : 75,
			sortable : true,
			align : 'left'
		}, {
			name : "deviceManufacturer",
			index : "deviceManufacturer",
			//label : i18nShow('info_host_manufacturer'),
			width : 60,
			sortable : true,
			align : 'left'
		},/*{
			name:"isBare",
			index:"isBare",
			//label:i18nShow('res_device_bareState'),
			width: 80,
			sortable:true ,
			align:'left' ,
			formatter : function(cellValue,options,rowObject){
				if(cellValue == 'Y'){
					return "<span class='tip_red'>"+i18nShow('host_res_isBareY')+"</span>";
				}else if(cellValue == 'N'){
					return "<span class='tip_green'>"+i18nShow('host_res_isBarN')+"</span>";
				}else if(cellValue == null){
					return "<span></span>" ;
				}
				return cellValue ;
			}
		},*/{
			name:"isInvc",
			index:"isInvc",
			//label:i18nShow('res_device_isInvc_state'),
			width: 80,
			sortable: true,
			align:'left',
			formatter : function(cellValue,options,rowObject){
				/*if(rowObject.isBare == 'Y'){*/
					if(cellValue == 'Y'){
						return "<span class='tip_green'>"+i18nShow('compute_res_isInvcY')+"</span>" ;
					}else if(cellValue == 'N'){
						return "<span class='tip_black'>"+i18nShow('res_device_unmatched')+"</span>" ;
					}else if(cellValue == 'NA'){
						return "<span class='tip_black'>"+i18nShow('res_device_unmatched')+"</span>" ;
					}else if(cellValue == null || cellValue==''){
						//如果为空，前台禁止显示为undefined，要显示为空（什么也没有） 
						return "<span>"+" "+"</span>" ;
					}
				/*}else if(rowObject.isBare == 'N'){
					if(cellValue == 'NA'){
						return "<span></span>" ;
					}else if(cellValue == 'Y'){
						return "<span class='tip_green'>"+i18nShow('compute_res_isInvcY')+"</span>" ;
					}else if(cellValue == 'N'){
						return "<span class='tip_red'>"+i18nShow('compute_res_isInvcN')+"</span>" ;
					}
				}else if(rowObject.isBare == null){
					return "<span></span>"
				}
				return cellValue ;*/
			}
		},
		{
			name : 'option',
		    index : 'option',
		   // label : i18nShow('com_operate'),
			width : 210,
			align : "left",
			sortable:false,
			formatter : function(cellValue,options,rowObject) {
				var updateFlag = $("#updateFlag").val();
				var deleteFlag = $("#deleteFlag").val();
				var matchFlag = $("matchFlag").val();
				var ref="";
				var f =3;
				if(f>=2){
					if(updateFlag=="1"){
						ref += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdateDiv('"+rowObject.id+"','"+rowObject.deviceTypeCode+"') >"+i18nShow('com_update')+"</a>";  
					}
					if(deleteFlag=="1"){
						ref += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteDevice('"+rowObject.id+"') >"+i18nShow('com_delete')+"</a>" ;   
					}
					/*if("1"=="1" && rowObject.isInvc =='UM'){
					ref += "<select style=' margin-right: 10px;text-decoration:none;' title=''><option vallue='' select='selected'>"+i18nShow('zh5')+"</option><option onclick=matching('"+rowObject.sn+"','"+rowObject.id+"')>"+i18nShow('res_device_matched')+"</option ></select>";
					}*/
				}
				
				
				/*if("1"=="1" && rowObject.isInvc =='UM'){
					//str3 = "<input type='button' value='匹配' onclick=matching('"+rowObject.sn+"','"+rowObject.id+"')  title='匹配' />&nbsp;&nbsp;"
					ref += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=matching('"+rowObject.sn+"','"+rowObject.id+"') >匹配</a>" ;   
				}*/
				
				return 	ref;
//			    return  "<input type='button' class='btn_edit_s' onclick=showUpdateDiv('"+rowObject.id+"','"+rowObject.deviceType+"')  title='修改' /><input type='button' style='margin-left:5px;' class='btn_del_s' onclick=deleteDevice('"+rowObject.id+"') title='删除'/>"
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
		pager : "#gridPager",
//		caption : "设备信息记录",
		hidegrid : false
	});
	//验证设备表单
	jQuery.validator.addMethod("SNCheck", function(value, element) { 
		var validateValue=true;
		var method=$("#method").val();
		var validateSN = $("#validateSN").val();
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"cmDeviceAndModelVo.sn":value},
			url:ctx+"/resmgt-common/device/getSnForTrim.action",
			async : false,
			success:(function(data){
				if(method=="update"){
					if(data==null||data.sn==validateSN){
						validateValue=true;
//						alert(validateValue+"1");
					}else{
						validateValue=false;
					}
					
				}else{
				if(data==null){
					validateValue=true;
				}else{
					validateValue=false;
				}
				}
			}),
		});
		return this.optional(element) || validateValue;
		},
		i18nShow('res_device_snValidate')); 
	//验证位置编码
	jQuery.validator.addMethod("seatIdCheck", function(value, element) { 
		var validateValue=true;
		var method=$("#method").val();
		var validateSeatId = $("#validateSeatId").val();
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"cmDeviceAndModelVo.seatId":value},
			url:ctx+"/resmgt-common/device/getSeatIdForTrim.action",
			async : false,
			success:(function(data){
				if(method=="update"){	//修改信息
					if(data==null||data.seatId==validateSeatId){
						validateValue=true;
					}else{
						validateValue=false;
					}
				}else{					//添加信息
					if(data==null){
						validateValue=true;
					}else{
						validateValue=false;
					}
				}
			}),
		});
		return this.optional(element) || validateValue;
	},
	i18nShow('res_device_saetValidate')); 
	jQuery.validator.addMethod("deviceNameCheck", function(value, element) { 
		var validateValue=true;
		var method=$("#method").val();
		var validatedeviceName = $("#validatedeviceName").val();
		if(value!=""){
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"cmDeviceAndModelVo.deviceName":value},
			url:ctx+"/resmgt-common/device/getDeviceNameForTrim.action",
			async : false,
			success:(function(data){
				if(method=="update"){
					if(data==null||data.deviceName==validatedeviceName){
						validateValue=true;
//						alert(validateValue+"1");
					}else{
						validateValue=false;
					}
					
				}else{
				if(data==null){
					validateValue=true;
				}else{
					validateValue=false;
				}
				}
			}),
			
		});
		}
		return this.optional(element) || validateValue;
		},
		i18nShow('res_device_deviceName_validate')); 
	jQuery.validator.addMethod("stringCheck", function(value, element) { 
		return this.optional(element) || /^[a-zA-Z0-9-_]*$/g.test(value);     
		},
		i18nShow('res_device_stringCheck')); 
	jQuery.validator.addMethod("checkIPV4", function(value, element) { 
		return this.optional(element) || /^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])$/i
		.test(value);     
		},
		i18nShow('validate_sg_ip'));
	jQuery.validator.addMethod("storageDeviceMgrIpCheck", function(value, element) {
        var validateValue=true;
        var method=$("#method").val();
        var validateIpV4Name = $("#validateIpV4Name").val();
        $.ajax({
             type:'post',
             datatype : "json",
             data:{"cmStorageVo.mgrIp":value},
             url:ctx+"/resmgt-common/device/storageDeviceMgrIpCheck.action",
             async : false,
             success:(function(data){
                  if(method=="update"){
                       if(data==null||data.mgrIp==validateIpV4Name){
                           validateValue=true;
//                          alert(validateValue+"1");
                       }else{
                            validateValue=false;
                       }
                       
                  }else{
                  if(data==null){
                       validateValue=true;
                  }else{
                       validateValue=false;
                  }
                  }
             }),
        });
        return this.optional(element) || validateValue;
        },
        i18nShow('res_device_manageIp_validate'));
	
	//-----------------
	$("#updateForm").validate({
		rules: {
//			deviceName2: {
//				deviceNameCheck: true,
//			    },
			    sn2: {
			        required: true,
			        stringCheck: true,
			        SNCheck:true
			    },
			    seatId: {
			    	required:true,
			    	stringCheck: true,
			    	seatIdCheck:true
			    },
			    deviceType: {
			        required: true
			    },
			    manufacturer: {
			        required: true
			    },
			    deviceModelId2: {
			        required: true
			    },
			    /*isBare:{
			    	required : true
			    },*/
			    cpu: {
			        required: true,
			        digits: true
			        
			    },
			    mem: {
			        required: true,
			        digits: true
			    },
			    disk: {
			        required: true,
			        digits: true
			    },
			   storageType:{
			    	 required: true
			    },
			    localDisk:{
			    	required:true
			    },
			    cacheCapacity:{digits: true},
		        diskCapacity:{digits: true},
		        diskSize:{digits: true},
		        diskNumber:{digits: true},
		        diskRpm:{digits: true},
		        portCount:{digits: true},
		        portSpeed:{digits: true},
		        orderNum:{digits: true},
		        localsize:{digits: true},
		        mgrIp:{
		        	//required:true,
		        	checkIPV4: true,
		        	//storageDeviceMgrIpCheck:true
		        }
		},
		messages: {
			mgrIp:{required:i18nShow('res_device_manageIp_notEmpty'),checkIPV4: i18nShow('validate_sg_ip'),storageDeviceMgrIpCheck:i18nShow('compute_res_used')},
			deviceName2:{deviceNameCheck: i18nShow('compute_res_used')},
			sn2: {required: i18nShow('res_device_validate_sn'),stringCheck: i18nShow('compute_res_cdpStringCheck'),SNCheck: i18nShow('compute_res_used')},
			seatId:{required:i18nShow('res_device_validate_seat'),stringCheck: i18nShow('compute_res_cdpStringCheck'),seatIdCheck: i18nShow('compute_res_used')},
			deviceType: {required: i18nShow('res_device_validate_type')},
			 manufacturer: {required: i18nShow('res_device_validate_manufacturer')},
			deviceModelId2: {required: i18nShow('res_device_validate_deviceModelId2')},
			/*isBare:{required: i18nShow('res_device_validate_isBare')},*/
			cpu: {required: i18nShow('res_device_validate_cpu'),digits: i18nShow('res_device_validate_mustBeNumbers')},
			mem: {required: i18nShow('res_device_validate_mem'),digits: i18nShow('res_device_validate_mustBeNumbers')},
			disk: {required: i18nShow('res_device_validate_disk'),digits: i18nShow('res_device_validate_mustBeNumbers')},
			storageType:{required: i18nShow('res_device_validate_style')},
			cacheCapacity:{digits: i18nShow('res_device_validate_mustBeNumbers')},
		    diskCapacity:{digits: i18nShow('res_device_validate_mustBeNumbers')},
		    diskSize:{digits: i18nShow('res_device_validate_mustBeNumbers')},
		    diskNumber:{digits: i18nShow('res_device_validate_mustBeNumbers')},
		    diskRpm:{digits: i18nShow('res_device_validate_mustBeNumbers')},
		    portCount:{digits: i18nShow('res_device_validate_mustBeNumbers')},
		    portSpeed:{digits: i18nShow('res_device_validate_mustBeNumbers')},
		    orderNum:{digits: i18nShow('res_device_validate_mustBeNumbers')},
		    localsize:{digits: i18nShow('res_device_validate_mustBeNumbers')},
		    localDisk:{required:i18nShow('res_device_validate_diskName')}
		},
		submitHandler: function() {
			updateOrSaveData();
		}
	});
	//根据设备厂商和类型联动设备型号
	$("#manufacturer").change(
			function (){
				var manufacturerVal=$("#manufacturer").val();
				 var deviceTypeVal=$("#deviceType").val();
				
					$.ajax({
						type:'post',
						datatype : "json",
						url:ctx+"/resmgt-storage/device/selectDeviceModelByManuf.action",
						async : false,
						data:{"deviceManufacturer":manufacturerVal,"deviceType":deviceTypeVal},
						success:(function(data){
							$("#deviceModelId2").empty();
							$(data).each(function(i,item){
								$("#deviceModelId2").append("<option value='"+item.id+"'>"+item.deviceModel+"</option>");
							});			
						}),
						error:function(XMLHttpRequest, textStatus, errorThrown){
							showTip(i18nShow('compute_res_selectError'));
						} 
					});
				
			}
	);
	
	$("#deviceType").change(function(){
		var manufacturerVal=$("#manufacturer").val();
		 var deviceTypeVal=$("#deviceType").val();
		 $.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-storage/device/selectDeviceModelByManuf.action",
				async : false,
				data:{"deviceManufacturer":manufacturerVal,"deviceType":deviceTypeVal},
				success:(function(data){
					$("#deviceModelId2").empty();
					$(data).each(function(i,item){
						$("#deviceModelId2").append("<option value='"+item.id+"'>"+item.deviceModel+"</option>");
					});			
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('compute_res_selectError'));
				} 

			});
		//根据设备类型改变表单内容
	         var datastoreTypelVal= $("#storageType").val();
		 if(datastoreTypelVal=="NAS"&&deviceTypeVal=="STORAGE"){
			 $("#cmHost").css("display","none");
			 $("#cMstorge").css("display","block");
	          $("#cMdatastore").css("display","block");
	          //存储时设备名称不能为空
	          $("#deviceCss").css("display","inline");
		 }
		 else if(datastoreTypelVal!="NAS"&&deviceTypeVal=="STORAGE"){
	          $("#cmHost").css("display","none");
			 $("#cMstorge").css("display","block");
	          $("#cMdatastore").css("display","none");
	          //存储时设备名称不能为空
	          $("#deviceCss").css("display","inline");
	         }else if(deviceTypeVal=="SERVER"){
	 		 $("#cmHost").css("display","block");
			 $("#cMstorge").css("display","none");
	          $("#cMdatastore").css("display","none");
	        //非存储时设备名称可以不填写
	          $("#deviceCss").css("display","none");
	    }else{
	    	$("#cmHost").css("display","none");
			 $("#cMstorge").css("display","none");
	          $("#cMdatastore").css("display","none");
	          //非存储时设备名称可以不填写
	          $("#deviceCss").css("display","none");
	    }
		});
	        
	    $("#storageType").change(function(){
	           var datastoreTypelVal=$("#storageType").val();
	           if(datastoreTypelVal == 'SAN' && $("#deviceType").val()=="STORAGE"){
		  			$("#glFont").css("display","none");
		  		 }else{
		  			$("#glFont").css("display","inline");
		  		 }
	           var deviceTypeVal =$("#deviceType").val();
	           var storageId=$("#storageId").val();
			   $("#cMdatastore").css("display","block");
			   cmDatastoreList();
			   searchDatastore(storageId);
			
	        //  $("#user").next().attr("name",user);
		 });
	    //加载datastore列表gride
	    cmDatastoreList();
		   searchDatastore();
		 //加载datastore查看列表gride
		 labCmDatastoreList();
		 searchLabDatastore();
			//自适应宽度
			$("#deviceGridTable").setGridWidth($("#deviceGridTable_div").width());
			$(window).resize(function() {
				$("#deviceGridTable").setGridWidth($("#deviceGridTable_div").width());
				$("#deviceGridTable").setGridHeight(heightTotal() + 40);
		    });
}

function saveOrUpdateBtn(){
	$("#updateForm").submit();  
}

//查询列表
function search() {
//	jqGridReload("deviceGridTable", {
//		"deviceName" : $.trim($("#deviceName").val()),
//		"sn":$.trim($("#sn").val()),
//		"resPoolId" : $("#resPoolId").val(),
//		"deviceModelId" : $("#deviceModelId").val()
//	});
	$("#deviceGridTable").jqGrid('setGridParam', {
		url : ctx + "/resmgt-common/device/getDeviceList.action",//你的搜索程序地址
		postData : {
			"deviceName" : $.trim($("#deviceName").val()),
			"sn":$.trim($("#sn").val()),
			"resPoolId" : $("#resPoolId").val(),
			"deviceModelId" : $("#deviceModelId").val()
		}, //发送搜索条件
		pager : "#gridPager"
	}).trigger("reloadGrid"); //重新载入
}
//删除设备
function deleteDevice(dataId){
	if(dataId){
		showTip(i18nShow('compute_res_deleteData_tip'),function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-common/device/deleteDeviceListAction.action",
			async : false,
			data:{"cmDevicevo.id":dataId},
			success:(function(data){
				showTip(i18nShow('deletesuc'));
				$("#deviceGridTable").jqGrid().trigger("reloadGrid");
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('tip_delete_fail'));
			} 
		});
		});
	}else{
		var ids = jQuery("#deviceGridTable").jqGrid('getGridParam','selarrrow');

		if(ids.length == 0){
			showError(i18nShow('compute_res_selectData'));
			return;
		}
		var list = [];
		$(ids).each(function (index,id2){
			var rowData = $("#deviceGridTable").getRowData(id2);
			list[list.length] = rowData.id;
			})
	showTip(i18nShow('compute_res_deleteData_tip'),function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-common/device/deleteDeviceListAction.action",
			async : false,
			data:{"cmDevicevo.id":list.join(",")},
			success:(function(data){
				showTip(data.data);
				$("#deviceGridTable").jqGrid().trigger("reloadGrid");
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('tip_delete_fail'));
			} 
		});
	});
	};
	
}

//弹出修改设备窗口
 function showUpdateDiv(objectId,type){
	 $("#datastoreGridTable").clearGridData(); 
		$("label.error").remove();
//		$("#updateDiv").dialog({ 
//				autoOpen : true,
//				modal:true,
//				height:480,
//				width:800,
//				title:'修改设备信息',
////				draggable: false,
//		        resizable:false
//		})
		$("#list").hide();
		$("#updateDiv").show();
		$("#s_updateTitle").html(i18nShow('res_device_update_title'));
		clearTab();
		selectByValue("resPoolId2","");
		selectByValue("deviceType","");
		selectByValue("deviceModelId2","");
		selectByValue("manufacturer","");
		selectByValue("storageType","");
	//	$("#cdpTdId").attr("display","block");
	if(type=="SERVER"){
		$.post(ctx+"/resmgt-common/device/updateCmDeviceHostPageAction.action",{"id" : objectId},function(data){
			 $("#cmHost").css("display","block");
			 $("#cMstorge").css("display","none");
	         $("#cMdatastore").css("display","none");
	         $("#deviceModelId2").removeAttr("disabled");
	     	$("#deviceType").removeAttr("disabled");
	     	$("#manufacturer").removeAttr("disabled");
	     	$("#validateSN").val(data[0].sn);
	     	$("#validateSeatId").val(data[0].seatId);
	     	$("#deviceId").val(data[0].id); 	
			 $("#deviceName2").val(data[0].deviceName);
			 $("#validatedeviceName").val(data[0].deviceName);
			 $("#sn2").val(data[0].sn);
//		   $("#deviceModelId2").empty();
//			 $("#deviceModelId2").append("<option value='"+data[0].deviceModelId+"'>"+data[0].deviceModel+"</option>");
			 //联动设备型号
			 $.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-storage/device/selectDeviceModelByManuf.action",
					async : false,
					data:{"deviceManufacturer":data[0].deviceManufacturer,"deviceType":data[0].deviceType},
					success:(function(data){
						$("#deviceModelId2").empty();
						$(data).each(function(i,item){
							$("#deviceModelId2").append("<option value='"+item.id+"'>"+item.deviceModel+"</option>");
						});			
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showTip(i18nShow('compute_res_selectError'));
					} 
				});
			 $("#deviceModelId2 option").each(function(){
			        if($(this).text() == data[0].deviceModel){
			        $(this).attr("selected",true);
			        }
			    });  
			 $("#seatId").val(data[0].seatId);
			 selectByValue("resPoolId2",data[0].resPoolId);
			 selectByValue("deviceType",data[0].deviceType);
			 selectByValue("manufacturer",data[0].deviceManufacturer);
			 $("#description").val(data[0].description);
			 $("#hostId").val(data[1].id);
			 $("#cpu").val(data[1].cpu);
			 $("#IPMI_USER").val(data[1].ipmiUser);
			 $("#IPMI_PWD").val(data[1].ipmiPwd);
			 $("#IPMI_URL").val(data[1].ipmiUrl);
			 $("#IPMI_VER").val(data[1].ipmiVer);
	        /* $("#isBare").val(data[1].isBare);*/
			 $("#mem").val(data[1].mem/1024);
			 $("#disk").val(data[1].disk);
			 if(data[3]!=null){
			 $("#localDisk").val(data[3].name);
			 $("#localsize").val(data[3].size);
			 $("#LocalDeviceId").val(data[3].deviceId);
			 $("#localDiskId").val(data[3].id);
			 }
			 $("#method").val("update");
			  if(data[0].resPoolId!=""){
		        	 $("#deviceModelId2").attr("disabled","disabled");
		        	 $("#deviceType").attr("disabled","disabled");
		        	 $("#manufacturer").attr("disabled","disabled");
		         }
				  $("#deviceType").attr("disabled","disabled");
		})
		
		}else{
			$("#cmHost").css("display","none");
			 $("#cMstorge").css("display","block");
			 $("#cMdatastore").css("display","none");
			 $("#resPoolId2").removeAttr("disabled");
				$("#deviceModelId2").removeAttr("disabled");
		    	$("#deviceType").removeAttr("disabled");
		    	$("#manufacturer").removeAttr("disabled");
			$.post(ctx+"/resmgt-common/device/updateCmDeviceStoragePageAction.action",{"id" : objectId},function(data){
		     	$("#validateSN").val(data[0].sn);
		     	$("#validateSeatId").val(data[0].seatId);
		     	 $("#validatedeviceName").val(data[0].deviceName);
				$("#deviceId").val(data[0].id);
				 $("#deviceName2").val(data[0].deviceName);
				 $("#sn2").val(data[0].sn);
//				 $("#deviceModelId2").empty();
//				 $("#deviceModelId2").append("<option value='"+data[0].deviceModelId+"'>"+data[0].deviceModel+"</option>");
				 //联动型号
				 $.ajax({
						type:'post',
						datatype : "json",
						url:ctx+"/resmgt-storage/device/selectDeviceModelByManuf.action",
						async : false,
						data:{"deviceManufacturer":data[0].deviceManufacturer,"deviceType":data[0].deviceType},
						success:(function(data){
							$("#deviceModelId2").empty();
							$(data).each(function(i,item){
								$("#deviceModelId2").append("<option value='"+item.id+"'>"+item.deviceModel+"</option>");
							});			
						}),
						error:function(XMLHttpRequest, textStatus, errorThrown){
							showTip(i18nShow('compute_res_selectError'));
						} 
					});
				 $("#deviceModelId2 option").each(function(){
				        if($(this).text() == data[0].deviceModel){
				        $(this).attr("selected",true);
				        }
				    });  
				 $("#seatId").val(data[0].seatId);
				 selectByValue("resPoolId2",data[0].resPoolId);
				 selectByValue("deviceType",data[0].deviceType);
				 selectByValue("manufacturer",data[0].deviceManufacturer);
				 $("#description").val(data[0].description);
				 selectByValue("storageType",data[1].storageType);
				 $("#storageId").val(data[1].id);
				 $("#diskRpm").val(data[1].diskRpm);
				 $("#portCount").val(data[1].portCount);
				 $("#microCode").val(data[1].microCode);
				 $("#diskSize").val(data[1].diskSize);
				 $("#cacheCapacity").val(data[1].cacheCapacity);
				 $("#diskCapacity").val(data[1].diskCapacity);
				 $("#diskNumber").val(data[1].diskNumber);
				 $("#diskSize").val(data[1].diskSize);
				 $("#mgrIp").val(data[1].mgrIp);
				 $("#validateIpV4Name").val(data[1].mgrIp);
				 $("#portSpeed").val(data[1].portSpeed);
				 $("#method").val("update");
				
				 $("#cMdatastore").css("display","block");
			       cmDatastoreList();
				   searchDatastore(data[1].id);
				   if(data[1].storageType == "SAN"){
						 $("#glFont").css("display","none");
					}else{
						 $("#glFont").css("display","inline");
					}
				 $("#deviceType").attr("disabled","disabled");
				 $("#storageType").attr("disabled","disabled");
				 if(data[0].resPoolId!=""){
		        	 $("#deviceModelId2").attr("disabled","disabled");
		        	 $("#manufacturer").attr("disabled","disabled");
		         }
			
			})
		}
	}
//弹出查看设备窗口
 function showLookDiv(objectId,type){
	 $("#lab_datastoreGridTable").clearGridData(); 
		$("label.error").remove();
//		$("#lookDiv").dialog({
//				autoOpen : true,
//				modal:true,
//				height:480,
//				width:800,
//				title:'设备详情',
////				draggable: false,
//		        resizable:false
//		})
		$("#list").hide();
		$("#lookDiv").show();
		clearTab();
	if(type=="SERVER"){
		$("label").val("");
		$.post(ctx+"/resmgt-common/device/updateCmDeviceHostPageAction.action",{"id" : objectId},function(data){
			 $("#lab_cmHost").css("display","block");
			 $("#lab_cMstorge").css("display","none");
	         $("#lab_cMdatastore").css("display","none");
	         $("#lab_cpuUsed").val("0");
			 $("#lab_memUsed").val("0");
			 $("#lab_deviceId").val(data[0].id);
			 $("#lab_deviceName2").val(data[0].deviceName);
			 $("#lab_sn2").val(data[0].sn);
			 $("#lab_deviceModelId2").val(data[0].deviceModel);
			 $("#lab_seatId").val(data[0].seatId);
//			 selectByValue("lab_resPoolId2",data[0].resPoolId);
			 if(data[0].poolName!=""){
				 $("#lab_resPoolId2").val(data[0].datacenterName+"/"+data[0].poolName);
				 $("#lab_nanotubes").html("<span class='tip_green'>"+i18nShow('compute_res_isInvcY')+"</span>");
			 }else{
				 $("#lab_nanotubes").html("<span class='tip_red'>"+i18nShow('compute_res_isInvcN')+"</span>");
			 }
			 $("#lab_deviceType").val(data[0].deviceTypeName);
			 $("#lab_manufacturer").val(data[0].deviceManufacturer);
			 $("#lab_description").val(data[0].description);
			 $("#lab_hostId").val(data[1].id);
			 $("#lab_cpu").val(data[1].cpu);
			 $("#lab_mem").val(data[1].mem);
			 $("#lab_disk").val(data[1].disk);
			 /*if(data[1].isBare =="Y"){
			   $("#lab_isBare").html("<span class='tip_red'>"+i18nShow('host_res_isBareY')+"</span>");
			 }else if(data[1].isBare =="N"){
				   $("#lab_isBare").html("<span class='tip_green'>"+i18nShow('host_res_isBarN')+"</span>");
			 }*/
			 if(data[1].cpuUsed!=""){
			 $("#lab_cpuUsed").val(data[1].cpuUsed);
			 }
			 if(data[1].memUsed!=""){
			 $("#lab_memUsed").val(data[1].memUsed);
			 }
			 if(data[3]!=null){
			 $("#lab_localsize").val(data[3].size);
			 $("#lab_localDisk").val(data[3].name);
			 }
		})
		}else{
			$("label").val("");
			$("#lab_cmHost").css("display","none");
			 $("#lab_cMstorge").css("display","block");
			 $("#lab_cMdatastore").css("display","none");
			$.post(ctx+"/resmgt-common/device/updateCmDeviceStoragePageAction.action",{"id" : objectId},function(data){
				$("#lab_deviceId").val(data[0].id);
				 $("#lab_deviceName2").val(data[0].deviceName);
				 $("#lab_sn2").val(data[0].sn);
				 $("#lab_deviceModelId2").val(data[0].deviceModel);
				 $("#lab_seatCode").val(data[0].seatId);
				 if(data[0].poolName!=""){
					 $("#lab_resPoolId2").val(data[0].datacenterName+"/"+data[0].poolName);
					 $("#lab_nanotubes2").html("<span class='tip_green'>"+i18nShow('compute_res_isInvcY')+"</span>");
				 }else{
					 $("#lab_nanotubes2").html("<span class='tip_red'>"+i18nShow('compute_res_isInvcN')+"</span>");
				 }
				 $("#lab_deviceType").val(data[0].deviceTypeName);
				 $("#lab_manufacturer").val(data[0].deviceManufacturer);
				 $("#lab_description").val(data[0].description);
				 $("#lab_storageType").val(data[1].storageType);
//				 $("#storageId").val(data[1].id);
				 $("#lab_diskRpm").val(data[1].diskRpm);
				 $("#lab_portCount").val(data[1].portCount);
				 $("#lab_microCode").val(data[1].microCode);
				 $("#lab_diskSize").val(data[1].diskSize);
				 $("#lab_cacheCapacity").val(data[1].cacheCapacity);
				 $("#lab_diskCapacity").val(data[1].diskCapacity);
				 $("#lab_diskNumber").val(data[1].diskNumber);
				 $("#lab_diskSize").val(data[1].diskSize);
				 $("#lab_mgrIp").val(data[1].mgrIp);
				 $("#lab_portSpeed").val(data[1].portSpeed);
				 if(data[1].storageType=="NAS"){
					 $("#lab_cMdatastore").css("display","block");
				     labCmDatastoreList(); 
					 searchLabDatastore(objectId);
				}else{
					 $("#lab_cMdatastore").css("display","none");
				}
			})
		}
	}
//新增或者保存设备信息
 function updateOrSaveData(){
	var method=$("#method").val();
	var LocalDeviceId=$("#LocalDeviceId").val();
	var localDiskId=$("#localDiskId").val();
	 var deviceId = $("#deviceId").val();
	 var hostId=$("#hostId").val();
	 var datastoreId=$("#datastoreId").val();
		var storageId=$("#storageId").val();
		var deviceName = $("#deviceName2").val();
		var sn = $("#sn2").val();
		var deviceModelId = $("#deviceModelId2").val();
		var seatId = $("#seatId").val();
		var resPoolId = $("#resPoolId2").val();
		var deviceType = $("#deviceType").val();
		var storageType = $("#storageType").val();
		//var manufacturer = $("#manufacturer").val();
		var description = $("#description").val();
		/*var isBare=$("#isBare").val();*/
		var cpu=$("#cpu").val();
		var ipmiUser = $("#IPMI_USER").val();
		var ipmiPwd = $("#IPMI_PWD").val();
		var ipmiUrl = $("#IPMI_URL").val();
		var ipmiVer = $("#IPMI_VER").val();
		var mem=$("#mem").val()*1024;
		var disk=$("#disk").val();
		var remark=$("#remark").val();
		var portCount=$("#portCount").val();
		var microCode=$("#microCode").val();
		var diskSize=$("#diskSize").val();
		var mgrIp=$("#mgrIp").val();
		if(((storageType=='ISCSI') || (storageType=='NAS')) && mgrIp==""){
			showTip(i18nShow('res_device_validate_ISCSI'));
			return false;
		}
		var cacheCapacity=$("#cacheCapacity").val();
		var diskCapacity=$("#diskCapacity").val();
		var diskNumber=$("#diskNumber").val();
		var portSpeed=$("#portSpeed").val();
		var diskRpm=$("#diskRpm").val();
		var path=$("#path").val();
		var orderNum=$("#orderNum").val();
		var name=$("#name").val();
		var localDisk=$("#localDisk").val();
		var localsize=$("#localsize").val();
		if(localsize==""){
			localsize="0";
		}
		if(diskRpm==""){
			diskRpm="0";
		}
		if(portCount==""){
			portCount="0";
		}
		if(diskSize==""){
			diskSize="0";
		}
		if(cacheCapacity==""){
			cacheCapacity="0";
		}
		if(diskCapacity==""){
			diskCapacity="0";
		}
		if(diskNumber==""){
			diskNumber="0";
		}
		if(portSpeed==""){
			portSpeed="0";
		}
		var url;
		if(method=="update"){
			if(deviceType=="SERVER"){
				url= ctx+"/resmgt-common/device/updateCmDeviceHostAction.action";
				var data = {'cmDevicevo.deviceName':deviceName,'cmDevicevo.sn':sn,'cmDevicevo.id':deviceId,
						'cmDevicevo.resPoolId':resPoolId,'cmDevicevo.seatId':seatId,'cmDevicevo.deviceModelId':deviceModelId,
						'cmDevicevo.description':description,/*'cmHostVo.isBare':isBare,*/'cmHostVo.ipmiUser':ipmiUser,'cmHostVo.ipmiPwd':ipmiPwd,
						'cmHostVo.ipmiUrl':ipmiUrl,'cmHostVo.ipmiVer':ipmiVer,'cmHostVo.cpu':cpu,'cmHostVo.mem':mem,'cmHostVo.id':hostId,'cmHostVo.disk':disk,
						'cmLocalDiskPo.name':localDisk,'cmLocalDiskPo.size':localsize,'cmLocalDiskPo.id':localDiskId,
						'cmLocalDiskPo.deviceId':LocalDeviceId,};
			}else{
				url= ctx+"/resmgt-common/device/updateCmDeviceStorageAction.action";
				var data = {'cmDevicevo.deviceName':deviceName,'cmDevicevo.sn':sn,'cmDevicevo.deviceModelId':deviceModelId,
						'cmDevicevo.resPoolId':resPoolId,'cmDevicevo.seatId':seatId,'cmDevicevo.id':deviceId,
						'cmDevicevo.description':description,'cmStorageVo.portCount':portCount,'cmStorageVo.id':storageId,
						'cmStorageVo.microCode':microCode,'cmStorageVo.diskSize':diskSize,'cmStorageVo.mgrIp':mgrIp,'cmStorageVo.storageType':storageType,
						'cmStorageVo.cacheCapacity':cacheCapacity,'cmStorageVo.diskCapacity':diskCapacity,'cmStorageVo.diskNumber':diskNumber,
						'cmStorageVo.portSpeed':portSpeed,'cmStorageVo.diskRpm':diskRpm,'cmStorageVo.remark':remark,
						'cmDatastoreVo.path':path,'cmDatastoreVo.orderNum':orderNum,'cmDatastoreVo.name':name,'cmDatastoreVo.id':datastoreId
				};
			}
		}else{
			if(deviceType=="SERVER"){
				url= ctx+"/resmgt-common/device/addCmDeviceHostAction.action";
				var data = {'cmDevicevo.deviceName':deviceName,'cmDevicevo.sn':sn,
						'cmDevicevo.resPoolId':resPoolId,'cmDevicevo.seatId':seatId,'cmDevicevo.deviceModelId':deviceModelId,
						'cmDevicevo.description':description,/*'cmHostVo.isBare':isBare,*/'cmHostVo.ipmiUser':ipmiUser,
						'cmHostVo.ipmiPwd':ipmiPwd,'cmHostVo.ipmiUrl':ipmiUrl,'cmHostVo.ipmiVer':ipmiVer,'cmHostVo.cpu':cpu,'cmHostVo.mem':mem,
						'cmHostVo.disk':disk,'cmLocalDiskPo.name':localDisk,'cmLocalDiskPo.size':localsize};
			}else{
				url= ctx+"/resmgt-common/device/addCmDeviceStorageAction.action";
				var data = {'cmDevicevo.deviceName':deviceName,'cmDevicevo.sn':sn,'cmDevicevo.description':description,'cmDevicevo.id':storageId,
						'cmDevicevo.resPoolId':resPoolId,'cmDevicevo.seatId':seatId,'cmDevicevo.deviceModelId':deviceModelId,
						'cmStorageVo.portCount':portCount,'cmStorageVo.storageType':storageType,'cmStorageVo.id':storageId,
						'cmStorageVo.microCode':microCode,'cmStorageVo.diskSize':diskSize,'cmStorageVo.mgrIp':mgrIp,
						'cmStorageVo.cacheCapacity':cacheCapacity,'cmStorageVo.diskCapacity':diskCapacity,'cmStorageVo.diskNumber':diskNumber,
						'cmStorageVo.portSpeed':portSpeed,'cmStorageVo.diskRpm':diskRpm,'cmStorageVo.remark':remark
				};
			}
			
		}
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data,
			beforeSend:(function(data){
				if(deviceType!="SERVER"&&deviceName==""){
					showTip(i18nShow('res_device_validate_deviceName'));
					return false;
				}
			}),
			success:(function(data){
				showTip(i18nShow('compute_res_saveSuccess'));
//				$("#updateDiv").dialog("close");
				$("#updateDiv").hide();
				$("#list").show();
				$("#deviceGridTable").jqGrid().trigger("reloadGrid");
				initCmDeviceList();
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('compute_res_saveFail'));
			} 
		});
	}

//弹出添加设备窗口
	function createData(){
		$("#datastoreGridTable").clearGridData();//清空datastore jqgrid表格
		$("label.error").remove();
//		$("#updateDiv").dialog({
//				autoOpen : true,
//				modal:true,
//				height:480,
//				width:800,
//				title:'新增设备信息',
//				resizable:false
//		});
		$("#list").hide();
		$("#updateDiv").show();
		$("#s_updateTitle").html(i18nShow('res_device_add_title'));
		clearTab();
		selectByValue("resPoolId2","");
		selectByValue("deviceType","");
		selectByValue("manufacturer","");
		selectByValue("storageType","");
		selectByValue("resPoolId2","");
//		selectByValue("deviceModelId2","");
		 $("#deviceModelId2").empty();
		$("#resPoolId2").removeAttr("disabled");
		$("#deviceModelId2").removeAttr("disabled");
    	$("#deviceType").removeAttr("disabled");
    	$("#storageType").removeAttr("disabled");
    	$("#manufacturer").removeAttr("disabled");
    	$("#cmHost").css("display","none");
		$("#cMstorge").css("display","none");
        $("#cMdatastore").css("display","none");
		$("#method").val("save");
		$("#portCount").val("");
		$("#diskRpm").val("");
		$("#diskSize").val("");
		$("#cacheCapacity").val("");
		$("#diskCapacity").val("");
		$("#diskNumber").val("");
		$("#portSpeed").val("");
		$("#cpu").val("");//cpu设置为空
		$("#IPMI_USER").val("");
		$("#IPMI_PWD").val("");
		$("#IPMI_URL").val("");
		$("#IPMI_VER").val("");
		/*$("#isBare").val("");*/
		$("#mem").val("");//内存总数设置为空
		$("#disk").val("");//磁盘大小设置为空
		$("#localsize").val("");//容量设置为空
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-common/device/addCmDevicePage.action",
			async : false,
			data:"",
			success:(function(data){
				$("#storageId").val(data.id);
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('compute_res_saveFail'));
			} 
		});
		
	}
	//清空表单
	function clearTab(){
		 //var tab = document.getElementById("updateTab") ;
		 var inputs = document.getElementsByTagName("input"); 
		 $("#description").val("");
		 for(var k=0;k<inputs.length;k++) 
		 { 
			 if(inputs[k].type!='button'&&inputs[k].type!='hidden'){
				 inputs[k].value=""; 
			 }
		 } 
	}
	//关闭窗口
	function closeView(){
		var storageId=$("#storageId").val();
		var method=$("#method").val();
//		$("#updateDiv").dialog("close");
		$("#updateDiv").hide();
		$("#list").show();
		if(method=="save"){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-common/device/deleteCmtDatastoreVo.action",
			async : false,
			data:{"cmDatastoreVo.storageId":storageId},
			success:(function(){
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip(i18nShow('firewall_add_fail'));
			} 
		});
		}
	}
	//以下是datastore列表的操作
	function cmDatastoreList() {
		//"storageId" : storageId
		/*var isHiddenFlag = $("#storageType").val();
		if(isHiddenFlag=='NAS'){
			jQuery("#datastoreGridTable").setGridParam().hideCol("identifier").trigger("reloadGrid");
			jQuery("#datastoreGridTable").setGridParam().showCol("path").trigger("reloadGrid");
		}if(isHiddenFlag=='SAN'){
			jQuery("#datastoreGridTable").setGridParam().showCol("identifier").trigger("reloadGrid");
			jQuery("#datastoreGridTable").setGridParam().hideCol("path").trigger("reloadGrid");
		}*/
		//alert(ctx+"/resmgt-common/device/getDatastoreList.action?storageId="+storageId);
		$("#datastoreGridTable").jqGrid({
			url : ctx+"/resmgt-common/device/getDatastoreList.action", 
			rownumbers : true, // 是否显示前面的行号
			datatype : "json", // 返回的数据类型
			mtype : "post", // 提交方式
			height : 110,
			autowidth : true, // 是否自动调整宽度
			multiselect:true,
			multiboxonly: false,
			colNames:['storageId','id',i18nShow('res_device_datastore_order'),i18nShow('res_device_datastore_name'),i18nShow('res_device_datastore_path'),i18nShow('res_device_datastore_identifier'),i18nShow('com_operate')],
			colModel : [ 
                        {name : "storageId",index : "storageId",sortable : true,align : 'left',hidden:true},
			            {name : "id",index : "id",sortable : true,align : 'left',hidden:true},
			            {name : "orderNum",index : "orderNum",	sortable : true,align : 'left',width:70},
			            {name : "name",index : "name",	sortable : true,align : 'left',width:70},
			            {name : "path",index : "path",	sortable : true,align : 'left',width:200},
			            {name : "identifier",index : "identifier",	sortable : true,align : 'left',width:220},
			            {name:"option",index:"option",sortable:false,align:"left",
							formatter:function(cellVall,options,rowObject){
								return "<a href='#' style=' text-decoration:none' onclick=showUpdateDatastoreDiv('"+rowObject.id+"')>"+i18nShow('com_update')+"</a>&nbsp;&nbsp;&nbsp;<a href='#' style=' text-decoration:none' onclick=deletDatastore('"+rowObject.id+"')>"+i18nShow('com_delete')+"</a>&nbsp;&nbsp"
							}
			            }
			            ],
			viewrecords : true,
			sortname : "id",
			rowNum : 10,
			rowList : [5, 10, 15, 20, 30 ],
			prmNames : {
				search : "search"
			},
			jsonReader : {
				root : "dataList",
				records : "record",
				repeatitems : false
			},
			pager : "#datastoregridPager",
//			caption : "虚机分配规则管理",
			hidegrid : false
		});
		
		//自适应宽度
		$("#datastoreGridTable").setGridWidth($("#grid_div").width());
		$(window).resize(function() {
			$("#datastoreGridTable").setGridWidth($("#grid_div").width());
	    });
	}
	//------wmy--------------
	
		$(function() {
			 jQuery.validator.addMethod("datastoreNameCheck", function(value, element) {
                 var validateValue=true;
                 var datastoreMethod=$("#datastoreMethod").val();
                 var datastoreCheckName = $("#datastoreCheckName").val();
                // alert(datastoreCheckName);
                 var storageId=$("#storageId").val();
               //  alert(storageId);
                 $.ajax({
                      type:'post',
                      datatype : "json",
                      data:{"cmDatastoreVo.name":value,"cmDatastoreVo.storageId":storageId},
                      url:ctx+"/resmgt-common/device/findDatastoreVoByName.action",
                      async : false,
                      success:(function(data){
                           if(datastoreMethod=="update"){
                                if(data==null||data.name==datastoreCheckName){
                                    validateValue=true;
//                                   alert(validateValue+"1");
                                }else{
                                     validateValue=false;
                                }
                                
                           }else{
                           if(data==null){
                                validateValue=true;
                           }else{
                                validateValue=false;
                           }
                           }
                      }),
                 });
                 return this.optional(element) || validateValue;
                 },
                 i18nShow('res_device_datastore_valiName'));
			 jQuery.validator.addMethod("datastorePathCheck", function(value, element) {
                 var validateValue=true;
                 var datastoreMethod=$("#datastoreMethod").val();
                 var datastoreCheckPath = $("#datastoreCheckPath").val();
                 var storageId=$("#storageId").val();
                 $.ajax({
                      type:'post',
                      datatype : "json",
                      data:{"cmDatastoreVo.path":value,"cmDatastoreVo.storageId":storageId},
                      url:ctx+"/resmgt-common/device/findDatastoreVoByPath.action",
                      async : false,
                      success:(function(data){
                           if(datastoreMethod=="update"){
                                if(data==null||data.path==datastoreCheckPath){
                                    validateValue=true;
//                                   alert(validateValue+"1");
                                }else{
                                     validateValue=false;
                                }
                                
                           }else{
                           if(data==null){
                                validateValue=true;
                           }else{
                                validateValue=false;
                           }
                           }
                      }),
                 });
                 return this.optional(element) || validateValue;
                 },
                 i18nShow('res_device_datastore_valiPath'));

			$("#updateDatastoreForm").validate({
				rules: {
					path:{datastorePathCheck:true},
					orderNum:{required: true,digits: true},
					name:{required: true,datastoreNameCheck:true}
				},
				messages: {
					path:{datastorePathCheck:i18nShow('compute_res_used')},
					orderNum:{required: i18nShow('res_device_datastore_valiOrder'),digits: i18nShow('res_device_validate_mustBeNumbers')},
					name:{required: i18nShow('compute_res_pool_name_notEmpty'),datastoreNameCheck:i18nShow('compute_res_used')}
				},
//				errorPlacement:function(error,element){
//		    		error.insertAfter("#device_error_tip");
//		    	},
				submitHandler: function() {
					updateOrSaveDatastoreData();
				}
			});
		});
	
	function saveOrUpdateDatastoreBtn(){
		$("#updateDatastoreForm").submit();  
	}
	//保存或者添加datastore信息
	 function updateOrSaveDatastoreData(){
			var datastoreMethod=$("#datastoreMethod").val();
			var identifier = $("#identifier").val();
			var path=$("#path").val();
			var datastoreTypelVal= $("#storageType").val();
			if(datastoreTypelVal=='SAN' && identifier==""){
				showTip(i18nShow('res_device_datastore_valiSan'));
				return false;
			}else if(((datastoreTypelVal=='ISCSI') || (datastoreTypelVal=='NAS')) && path==""){
				showTip(i18nShow('res_device_datastore_valiNas'));
				return false;
			}
			var orderNum=$("#orderNum").val();
			var name = $("#name").val();
			var storageId=$("#storageId").val();
			var datastoreId=$("#datastoreId").val();
			var url;
				if(datastoreMethod=="update"){
					url= ctx+"/resmgt-common/device/updateCmtDatastoreVo.action"
				}else{
					url= ctx+"/resmgt-common/device/addCmDatastoreVo.action"
				}
				var data = {'cmDatastoreVo.id':datastoreId,'cmDatastoreVo.name':name,'cmDatastoreVo.orderNum':orderNum,'cmDatastoreVo.path':path,'cmDatastoreVo.storageId':storageId,'cmDatastoreVo.identifier':identifier};
				$.ajax({
					type:'post',
					datatype : "json",
					url:url,
					async : false,
					data:data,
					/*beforeSend:(function(data){
						return validate(datas);
					}),*/
					success:(function(data){
						showTip(i18nShow('compute_res_saveSuccess'));
						$("#updataCmDatastore").dialog("close");
						$("#datastoreGridTable").jqGrid().trigger("reloadGrid");
						searchDatastore(storageId);
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showError(i18nShow('compute_res_saveFail'));   
					} 
				});
			}

	 function searchDatastore(storageId) {
//		 alert(storageId);
//			jqGridReload("datastoreGridTable", {
//				"storageId" : storageId
//			});
		 $("#datastoreGridTable").jqGrid('setGridParam', {
				url : ctx + "/resmgt-common/device/getDatastoreList.action",//你的搜索程序地址
				postData : {
					"storageId" : storageId
				}, //发送搜索条件
				pager : "#datastoregridPager"
			}).trigger("reloadGrid"); //重新载入
		}
	//弹出添加Datastore窗口
	function createDatastoreData(){
		$("label.error").remove();
		var datastoreTypelVal= $("#storageType").val();
		if(datastoreTypelVal=='SAN'){
			 $("#identifierFont").css("display","inline");
			 $("#pathFont").css("display","none");
		}else{
			 $("#identifierFont").css("display","none");
			 $("#pathFont").css("display","inline");
		}
		$("#updataCmDatastore").dialog({
				autoOpen : true,
				modal:true,
				height:280,
				width:340,
				title:i18nShow('res_device_datastore_add'),
				//resizable:false
		});
		$("#path").val("");
		$("#orderNum").val("");
		$("#name").val("");
		$("#datastoreId").val("");
		$("#identifier").val("");
		$("#datastoreMethod").val("save");
	}
	//关闭添加Datastore窗口
	function closeDatastoreView(){
		$("#updataCmDatastore").dialog("close");
	}
	//弹出修改Datastore窗口
	function showUpdateDatastoreDiv(objectId){
		$("label.error").remove();
		var datastoreTypelVal= $("#storageType").val();
		if(datastoreTypelVal=='SAN'){
			 $("#identifierFont").css("display","inline");
			 $("#pathFont").css("display","none");
		}else{
			 $("#identifierFont").css("display","none");
			 $("#pathFont").css("display","inline");
		}
		$("#updataCmDatastore").dialog({
				autoOpen : true,
				modal:true,
				height:280,
				width:400,
				title:i18nShow('res_device_update_param'),
//				draggable: false,selectCmtDatastoreVoById
		       // resizable:false
		})
		$.post(ctx+"/resmgt-common/device/selectCmtDatastoreVoById.action",{"cmDatastoreVo.id" : objectId},function(data){
			$("#path").val(data.path);
			$("#orderNum").val(data.orderNum);
			$("#name").val(data.name);
			$("#identifier").val(data.identifier);
			//----------wmy--------------
			$("#datastoreCheckName").val(data.name);
			$("#datastoreCheckPath").val(data.path);
			//--------------------------
			$("#datastoreId").val(data.id);
			$("#datastoreMethod").val("update");
		})
	}
	//删除Datastore信息
	function deletDatastore(dataId){
		if(dataId){
			showTip(i18nShow('compute_res_deleteData_tip'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-common/device/deleteCmtDatastoreVoById.action",
				async : false,
				data:{"cmDatastoreVo.id":dataId},
				success:(function(data){
					showTip(i18nShow('res_device_delete_datastore_success'));
					$("#datastoreGridTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError(i18nShow('res_device_delete_datastore_fail'));
				} 
			});
			});
		}else{
			var ids = jQuery("#datastoreGridTable").jqGrid('getGridParam','selarrrow');

			if(ids.length == 0){
				showError(i18nShow('compute_res_selectData'));
				return;
			}
			var list = [];
			$(ids).each(function (index,id2){
				var rowData = $("#datastoreGridTable").getRowData(id2);
				list[list.length] = rowData.id;
				})
				showTip(i18nShow('compute_res_deleteData_tip'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-common/device/deleteCmtDatastoreVoById.action",
				async : false,
				data:{"cmDatastoreVo.id":list.join(",")},
				success:(function(data){
					showTip(i18nShow('res_device_delete_datastore_success'));
					$("#datastoreGridTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError(i18nShow('res_device_delete_datastore_fail'));
				} 
			});
			});
		};
		
	}
	//加载查看Datastore信息列表
	function labCmDatastoreList() {
		$("#lab_datastoreGridTable").jqGrid({
			url : ctx+"/resmgt-common/device/getDatastoreList.action", 
			rownumbers : true, 
			datatype : "json", 
			mtype : "post", 
			height : 120,
			autowidth : true, 
//			multiselect:true,
			colNames:['storageId','id',i18nShow('res_device_datastore_order'),i18nShow('res_device_datastore_name'),i18nShow('res_device_datastore_path')],
			colModel : [ 
                        {name : "storageId",index : "storageId",sortable : true,align : 'left',hidden:true},
			            {name : "id",index : "id",sortable : true,align : 'left',hidden:true},
			            {name : "orderNum",index : "orderNum",sortable : true,align : 'left'},
			            {name : "name",index : "name",sortable : true,align : 'left'},
			            {name : "path",index : "path",sortable : true,align : 'left'}
			            ],
			viewrecords : true,
			sortname : "id",
			rowNum : 10,
			rowList : [5, 10, 15, 20, 30],
			prmNames : {
				search : "search"
			},
			jsonReader : {
				root : "dataList",
				records : "record",
				repeatitems : false
			},
			pager : "#lab_datastoregridPager",
//			caption : "虚机分配规则管理",
			hidegrid : false
		});
		//自适应宽度
		$("#lab_datastoreGridTable").setGridWidth($("#labGrid_div").width());
		$(window).resize(function() {
			$("#lab_datastoreGridTable").setGridWidth($("#labGrid_div").width());
	    });
	}
	 function searchLabDatastore(storageId) {
//			jqGridReload("lab_datastoreGridTable", {
//				"storageId" : storageId
//			});
		 $("#lab_datastoreGridTable").jqGrid('setGridParam', {
				url : ctx + "/resmgt-common/device/getDatastoreList.action",//你的搜索程序地址
				postData : {
					"storageId" : storageId
				}, //发送搜索条件
				pager : "#lab_datastoregridPager"
			}).trigger("reloadGrid"); //重新载入
		}
	 //关闭查看页面
	 function closeLabView(){
			$("#lookDiv").hide();
			$("#list").show();
	 }
function matching(Sn,ObjectId){
	if(Sn){
		showTip(i18nShow('match1'),function(){
			$.ajax({
				type: 'post',
				datatype: 'json',
				url:ctx+"/resmgt-common/device/matchDeviceListAction.action",
				async : false,
				data:{
				"cmDevicevo.sn" :Sn,
				"cmHostVo.id" : ObjectId
			},
			success:(function(data){
				    showError(data.data);
					$("#deviceGridTable").jqGrid().trigger("reloadGrid");
				}),
				error : (function(XMLHttpRequest, textStatus, errorThrown) {
					showError(i18nShow('match2'));
				})
			});
		});
	}
}
	 
	 
	 
	 