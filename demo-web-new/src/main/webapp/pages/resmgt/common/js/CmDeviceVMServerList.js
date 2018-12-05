function initCmDeviceList() {
	$("#gridTable").jqGrid({
		url : ctx+"/resmgt-common/vmmanageserver/getvmManageServerList.action",
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : heightTotal() + 40,
		autowidth : true, // 是否自动调整宽度
		//multiselect:true,
		multiboxonly: false,
		colModel : [ {
			name : "id",
			index : "id",
			label : "id",
			width : 0,
			sortable : true,
			align : 'left',
			hidden:true	
		},  {
			name : "serverName",
			index : "serverName",
			label : i18nShow('rm_server_name'),
			width : 100,
			sortable : true,
			align : 'left',
			formatter : function(cellValue,options,rowObject) {
				return 	"<a href='#' style=' text-decoration:none' "+"onclick=showVMServerShowDiv('"+rowObject.id+"')>"+rowObject.serverName+"</a>"
			}	
		}, {
			name : "dataCenterName",
			index : "dataCenterName",
			label : i18nShow('rm_datacenter'),
			width : 90,
			sortable : true,
			align : 'left'
		}, {
			name : "manageIp",
			index : "manageIp",
			label : i18nShow('cm_vm_server_manageIp'),
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			
			name : "platformType",
			index : "platformType",
			label : i18nShow('cloud_service_platform'),
			width : 100,
			sortable : true,
			align : 'left'	
		}, {
			name : "vmType",
			index : "vmType",
			label : i18nShow('cloud_service_vm_type'),
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			name : "userName",
			index : "userName",
			label : i18nShow('image_username'),
			width : 120,
			sortable : true,
			align : 'left'
		},{
			name : "version",
			index : "version",
			label : "版本号",
			width : 120,
			sortable : true,
			align : 'left'
		},{
			name : "manageOneIp",
			index : "manageOneIp",
			label : "manageOneIp",
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			name : 'option',
		    index : 'option',
		    label : i18nShow('com_operate'),
			width : 60,
			align : "center",
			sortable:false,
			formatter : function(cellValue,options,rowObject) {
				var deleteFlag = $("#deleteFlag").val();
				var updateflag = $("#updateFlag").val();
				var str1 = "";
				if(updateflag=='1'){
					str1 += "<a  style='margin-right: 10px;margin-left: 5px;text-decoration:none;' href='javascript:#' title=''  onclick=showUpdateDiv('"+rowObject.id+"')>"+i18nShow('com_update')+"</a>";
				}
				if(deleteFlag=='1'){
					str1 += "<a  style='margin-right: 30px;margin-left: 0px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteVMServer('"+rowObject.id+"','"+rowObject.serverName+"')>"+i18nShow('com_delete')+"</a>";
				}
				return 	str1;
			}														
		}],
		
		viewrecords : true,
		sortname : "serverName",
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
//		caption : "虚机管理服务器信息记录",
		hidegrid : false
	});
		
	$("#updateForm").validate({
		rules: {
		  dataCenterIdInput: { required: true },
			serverNameInput: { required: true, remote: {
														type : "POST",
														url : ctx+"/resmgt-common/vmmanageserver/checkServerName.action",
														data : {
															"rmVmManageServerPo.id" : function() {
																return $("#serverId").val();
															},
															"rmVmManageServerPo.datacenterId" : function() {
																return $("#dataCenterIdInput").val();
															},
															"rmVmManageServerPo.serverName" : function() {
																return $("#serverNameInput").val();
															}
														}
													}
												  },
			  manageIpInput: { required: true,isIPAddress: true, remote: {
																		type : "POST",
																		url : ctx+"/resmgt-common/vmmanageserver/checkServerIp.action",
																		data : {
																			"rmVmManageServerPo.id" : function() {
																				return $("#serverId").val();
																			},
																			"rmVmManageServerPo.manageIp" : function() {
																				return $("#manageIpInput").val();
																			}
																		}
																	}
		  														},
		platformNameIdInput: { required: true },
		  vmTypeNameIdInput: { required: true },
		      userNameInput: { required: true,"maxlength":30 },
		      passwordInput: { required: true,"maxlength":50 },
		      domainName: { required: true },
		      version: { required: true },
		      manageProjectName: { required: true },
		      manageProjectDOMAIN: { required: true },
		      //manageOneIp: { required: true,isIPAddress: true },
		},
	 messages: {
		dataCenterIdInput: {required: i18nShow('validate_data_required')},
		  serverNameInput: {required: i18nShow('validate_data_required'),remote: i18nShow('validate_data_remote')},
            manageIpInput: {required: i18nShow('validate_data_required'),"isIPAddress":i18nShow('validate_general_server_ip'),remote:i18nShow('validate_data_remote')},
      platformNameIdInput: {required: i18nShow('validate_data_required')},
		vmTypeNameIdInput: {required: i18nShow('validate_data_required')},
			userNameInput: {required: i18nShow('validate_data_required'),"maxlength":i18nShow('validate_general_server_username')},
			passwordInput: {required: i18nShow('validate_data_required'),"maxlength":i18nShow('validate_general_server_pwd')},
			domainName: {required: i18nShow('validate_data_required')},
			version: { required: i18nShow('validate_data_required')},
			manageProjectName: { required: i18nShow('validate_data_required')},
			manageProjectDOMAIN: { required: i18nShow('validate_data_required')},
		},
		submitHandler: function() {
			updateOrSaveData();
		}
	});
	
	// 虚拟机类型取决于平台类型。（查询部分）
	$("#platformNameId").change(
			function(){
				var platformNameId=$("#platformNameId").val()
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-common/vmmanageserver/getVmTypeNameInfo.action",
					async : false,
					data:{"platformId":platformNameId},
					success:(function(data){
						$("#vmTypeNameId").empty();
						$("#vmTypeNameId").append("<option value=''>"+''+i18nShow('com_select_defalt')+'...'+"</option>");
						$(data).each(function(i,item){
							$("#vmTypeNameId").append("<option value='"+item.virtualTypeId+"'>"+item.virtualTypeName+"</option>");
						});			
					})
				});
			}
		);

	// 虚拟机类型取决于平台类型。
	$("#platformNameIdInput").change(
			function(){
				getvmTypeList();
			}
		);
	// 版本号改变
	$("#version").change(
			function(){
				versionChange();
			}
		);
	//自适应宽度
	$("#gridTable").setGridWidth($("#gridTable_div").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridTable_div").width());
		$("#gridTable").setGridHeight(heightTotal() + 40);
    }); 
	
}

//加载虚机类型信息(添加与修改)
function getvmTypeList(){
	
	var platformNameIdInput=$("#platformNameIdInput").val()
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/resmgt-common/vmmanageserver/getVmTypeNameInfo.action",
		async : false,
		data:{"platformId":platformNameIdInput},
		success:(function(data){
			$("#vmTypeNameIdInput").empty();
			$("#vmTypeNameIdInput").append("<option value=''>"+''+i18nShow('com_select_defalt')+'...'+"</option>");
			$(data).each(function(i,item){
				$("#vmTypeNameIdInput").append("<option value='"+item.virtualTypeId+"'>"+item.virtualTypeName+"</option>");
			});			
		})
	});
}

function saveOrUpdateBtn(){
	$("#updateForm").submit();
}

function search() {
	jqGridReload("gridTable", {
		"serverName" : $("#serverName").val().replace(/(^\s*)|(\s*$)/g, ""),
		"manageIp":$("#manageIp").val().replace(/(^\s*)|(\s*$)/g, ""),
		"platformType" : $("#platformNameId").val(),
		"vmType" : $("#vmTypeNameId").val()
	});
}

function deleteVMServer(id,serverName){
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-common/vmmanageserver/deleteVMServerList.action",
				async : false,
				data:{"rmVmManageServerPo.id":id},
				success:(function(data){
					if(data!=""){
						showTip(i18nShow('cm_vm_server')+"："+serverName+i18nShow('cm_vm_server_using'));
					}else{
						closeTip();
						showTip(i18nShow('tip_delete_success'));
						$("#gridTable").jqGrid().trigger("reloadGrid");
					}
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError(i18nShow('tip_delete_fail'));
				} 
			});
		});

}

	// 修改虚机管理服务器
	function showUpdateDiv(objectId){
		$("label.error").remove();
		$("#VMServerUpdateiv").dialog({ 
				autoOpen : true,
				modal:true,
				height:380,
				width:670,
				title:i18nShow('cm_vm_server_update'),
		       // resizable:false,
		        close : function(){
		        	$("#serverId").val("");//修改操作成功后，清空虚机服务器ID缓存域。
		        }
		});
		clearTab();
		
		selectByValue("dataCenterIdInput","");
		selectByValue("serverNameInput","");
		selectByValue("manageIpInput","");
		selectByValue("platformNameIdInput","");
		selectByValue("vmTypeNameIdInput","");
		selectByValue("userNameInput","");
		selectByValue("passwordInput","");
		
		$.post(ctx+"/resmgt-common/vmmanageserver/getvmManageServerInfo.action",
			{"serverId":objectId},
			function(data){
				selectByValue("dataCenterIdInput",data.datacenterId);
				$("#serverNameInput").val(data.serverName);
				$("#manageIpInput").val(data.manageIp);
				selectByValue("platformNameIdInput",data.platformType);
				getvmTypeList();//获取虚机类型
				selectByValue("vmTypeNameIdInput",data.vmType);
				$("#userNameInput").val(data.userName);
				$("#passwordInput").val(data.password);
				$("#serverId").val(data.id);
				$("#cmPasswordid").val(data.cmPasswordid);
				$("#domainName").val(data.domainName);
				$("#manageOneIp").val(data.manageOneIp);
				$("#manageOneOcIp").val(data.manageOneOcIp);
                $("#manageProjectId").val(data.manageProjectId);
				$("#version").val(data.version);
				if(data.platformCode == 'O'){
					if(data.version == '2.0.6'){
						$('#span_manageOneIp').attr("style","display: none");
						$('#span_manageOneOcIp').attr("style","display: none");
                        $('#span_manageProjectId').attr("style","display: none");
					}else{
						$('#span_manageOneIp').removeAttr("style");
						$('#span_manageOneOcIp').removeAttr("style");
                        $('#span_manageProjectId').removeAttr("style");
					}
				}else{
					$('#span_manageOneIp').attr("style","display: none");
					$('#span_manageOneOcIp').attr("style","display: none");
                    $('#span_manageProjectId').attr("style","display: none");
					$('#span_version').attr("style","display: none");
				}
				
				changePlatform();
			}
		)
		
	}

	// 查看虚机管理服务器详细信息。
	function showVMServerShowDiv(objectId){
		$("label.error").remove();
		$("#VMServerShowDiv").dialog({
				autoOpen : true,
				modal:true,
				height:400,
				width:540,
				title:i18nShow('cm_vm_server_view'),
//				draggable: false,
		       // resizable:false
		})
		clearTab();
		selectByValue("dataCenterShow","");
		selectByValue("serverNameShow","");
		selectByValue("manageIpShow","");
		selectByValue("platformNameIdShow","");
		selectByValue("vmTypeNameIdShow","");
		selectByValue("userNameShow","");
		selectByValue("passwordShow","");
		
		$.post(ctx+"/resmgt-common/vmmanageserver/getvmManageServerInfo.action",
			{"serverId":objectId},
			function(data){
				$("#dataCenterShow").val(data.dataCenterName);
				$("#serverNameShow").val(data.serverName);
				$("#manageIpShow").val(data.manageIp);
				$("#platformNameIdShow").val(data.platformName);
				$("#vmTypeNameIdShow").val(data.virtualTypeName);
				$("#createUserShow").val(data.createUser);
				$("#createDatetimeShow").val(formatTime(data.createDateTime));
				$("#updateUserShow").val(data.updateUser);
				$("#updateDatetimeShow").val(formatTime(data.updateDateTime));
				$("#userNameShow").val(data.userName);
				$("#versionShow").val(data.version);
				$("#manageOneIpShow").val(data.manageOneIp);
				$("#manageOneOcIpShow").val(data.manageOneOcIp);
                $("#manageProjectIdShow").val(data.manageProjectId);
				if(data.platformCode == 'O'){
					if(data.version == '2.0.6'){
						$('#manageOneIp_show').attr("style","display: none");
						$('#manageOneOcIp_show').attr("style","display: none");
                        $('#manageProjectId_show').attr("style","display: none");
						$('#version_show').removeAttr("style");
						
					}else{
						$('#manageOneIp_show').removeAttr("style");
						$('#manageOneOcIp_show').removeAttr("style");
                        $('#manageProjectId_show').removeAttr("style");
						$('#version_show').removeAttr("style");
					}
				}else{
					$('#manageOneIp_show').attr("style","display: none");
					$('#manageOneOcIp_show').attr("style","display: none");
                    $('#manageProjectId_show').attr("style","display: none");
					$('#version_show').attr("style","display: none");
				}
				
//				$("#passwordShow").html(data.password);
			}
		)
	}
 
 	//修改或者添加虚机管理服务器信息
	function updateOrSaveData(){
		$("label.error").remove();
		var serverId=$("#serverId").val();//服务器id
		var dataCenterIdInput=$("#dataCenterIdInput").val();//数据中心ID
		var serverNameInput=$("#serverNameInput").val();//服务器名称
		var manageIpInput=$("#manageIpInput").val();//主机IP
		var platformNameIdInput=$("#platformNameIdInput").val();//平台类型
		var vmTypeNameIdInput=$("#vmTypeNameIdInput").val();//虚拟机类型
		var userNameInput=$("#userNameInput").val();//用户名称
		var passwordInput = $("#passwordInput").val();//密码
		var cmPasswordid = $("#cmPasswordid").val();//密码表id
		var domainName = $("#domainName").val();
		var version = $("#version").val();
		var manageOneIp = $("#manageOneIp").val();
		var manageOneOcIp = $("#manageOneOcIp").val();
        var manageProjectId = $("#manageProjectId").val();
		if(version == '6.3'){
			if(manageOneIp == null || manageOneIp == ""){
				showTip("请输入manageOne的ip地址");
				return ;
			}else{
				if(!isIPAddress(manageOneIp)){
					showTip("请输入正确的manageOneIp地址");
					return ;
				}
			}
			if(manageOneOcIp == null || manageOneOcIp == ""){
				showTip("请输入manageOneOc的ip地址");
				return ;
			}else{
				if(!isIPAddress(manageOneOcIp)){
					showTip("请输入正确的manageOneOcIp地址");
					return ;
				}
			}
            if(manageProjectId == null || manageProjectId == ""){
                showTip("请输入Project ID");
                return ;
            }
		}
		var url=ctx+"/resmgt-common/vmmanageserver/insertvmManageServer.action";
		var data = {	'rmVmManageServerPo.id':serverId,
			  'rmVmManageServerPo.datacenterId':dataCenterIdInput,
				  'rmVmManageServerPo.userName':userNameInput,
				'rmVmManageServerPo.serverName':serverNameInput,
				  'rmVmManageServerPo.manageIp':manageIpInput,
			  'rmVmManageServerPo.platformType':platformNameIdInput,
			  		'rmVmManageServerPo.vmType':vmTypeNameIdInput,
		   'rmVmManageServerPo.cmPasswordPo.id':cmPasswordid,
	 'rmVmManageServerPo.cmPasswordPo.userName':userNameInput,
	 'rmVmManageServerPo.cmPasswordPo.password':passwordInput,
	            'rmVmManageServerPo.domainName':domainName,
	            'rmVmManageServerPo.version':version,
	            'rmVmManageServerPo.manageOneIp':manageOneIp,
	            'rmVmManageServerPo.manageOneOcIp':manageOneOcIp,
	            'rmVmManageServerPo.manageProjectId':manageProjectId,
	            };
	
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
				if(data.rtnMsg!=""){
					showTip(data.rtnMsg);
				}else{
					showTip(i18nShow('tip_save_success'));
					$("#VMServerUpdateiv").dialog("close");
//					$("#gridTable").jqGrid().trigger("reloadGrid");
					// 先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
					$("#gridTable").jqGrid("GridUnload");
					initCmDeviceList();
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('tip_save_fail'));
			} 
		});
	}
	
function changePlatform(){
		var options = $("#platformNameIdInput option:selected").val();
		if(options =='4'){
			$('#span1').removeAttr("style"); 
			$('#span_version').removeAttr("style"); 
		}else{
			$('#span1').attr("style","display: none");
			$('#span_version').attr("style","display: none");
		}
		//如果是PowerVC类型的，则显示“管理Project名称” “管理ProjectDOMAIN”
		if(options=='5'){
			$('#manage_projectName').attr("style","width:500px;display: block");
			$('#manage_projectDOMAIN').attr("style","width:500px;display: block");
		}else{
			$('#manage_projectName').attr("style","display: none");
			$('#manage_projectDOMAIN').attr("style","display: none");
		}
	}
	// 添加虚机管理服务器信息数据
	function createVMServer(){
		$("label.error").remove();
		$('#span1').attr("style","display: none");
		$('#span_version').attr("style","display: none");
		$('#span_manageOneIp').attr("style","display: none");
		$('#span_manageOneOcIp').attr("style","display: none");
        $('#span_manageProjectId').attr("style","display: none");
		$("#VMServerUpdateiv").dialog({
				autoOpen : true,
				modal:true,
				height:367,
				width:670,
				title:i18nShow('cm_vm_server_save'),
				//resizable:false
		});
		clearTab();
		
		selectByValue("dataCenterIdInput","");
		selectByValue("serverNameInput","");
		selectByValue("manageIpInput","");
		selectByValue("platformNameIdInput","");
		selectByValue("vmTypeNameIdInput","");
		selectByValue("userNameInput","");
		selectByValue("passwordInput","");
	}
	
	function clearTab(){
		 //var tab = document.getElementById("updateTab") ;
		 var inputs = document.getElementsByTagName("input"); 
		 for(var k=0;k<inputs.length;k++) 
		 { 
			 if(inputs[k].type!='button'&&inputs[k].type!='hidden'){
				 inputs[k].value=""; 
			 }
		 }
		 $("#version").val("");
	}
	
	function closeView(divId){
		$("#"+divId).dialog("close");
	}
	
	//判断输入的是否为IP格式
	isIPAddress=function(ip)   
	{   
	    var re = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/   
	    return re.test(ip);
	}
	
	jQuery.validator.addMethod("isIPAddress", isIPAddress, "请输入正确的IP地址");
	
// 格式化时间;
function formatTime(ns) {
	if (ns) {
		var d = new Date(parseInt(ns));
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		var date = d.getDate();
		var hour = d.getHours();
		var minute = d.getMinutes();
		var second = d.getSeconds();
		return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
	}
}

function versionChange(){
	var version = $('#version').val();
	if(version == "2.0.6"){
		$('#span_manageOneIp').attr("style","display: none");
		$('#span_manageOneOcIp').attr("style","display: none");
        $('#span_manageProjectId').attr("style","display: none");
	}else{
		$('#span_manageOneIp').removeAttr("style");
		$('#span_manageOneOcIp').removeAttr("style");
        $('#span_manageProjectId').removeAttr("style");
	}
}
/**
 * 修改虚机管理服务器的版本时，触发的事件
 *//*
function changeVersion() {
    var version = $("#version").val();
    if(version == "6.3") {
        $("#manageOneIpSign").html("*");
    }
    else {
        $("#manageOneIpSign").html("");
    }
}*/