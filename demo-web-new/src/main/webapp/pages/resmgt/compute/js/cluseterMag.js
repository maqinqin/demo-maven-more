//jquery 屏蔽或者启用一个区域内的所有元素，禁止输入

(function($) { 
	$.fn.disable = function() { 
	/// <summary> 
	/// 屏蔽所有元素 
	/// </summary> 
	/// <returns type="jQuery" /> 
		return $(this).find("*").each(function() { 
			$(this).attr("disabled", "disabled"); 
		}); 
	} 
	$.fn.enable = function() { 
	/// <summary> 
	/// 使得所有元素都有效 
	/// </summary> 
	/// <returns type="jQuery" /> 
		return $(this).find("*").each(function() { 
			$(this).removeAttr("disabled"); 
		}); 
	} 
})(jQuery); 

function clearForm(form) {
	  // iterate over all of the inputs for the form
	  // element that was passed in
	  $(':input', form).each(function() {
	    var type = this.type;
	    var tag = this.tagName.toLowerCase(); // normalize case
	    // it's ok to reset the value attr of text inputs,
	    // password inputs, and textareas
	    if (type == 'text' || type == 'password' || tag == 'textarea')
	      this.value = "";
	    // checkboxes and radios need to have their checked state cleared
	    // but should *not* have their 'value' changed
	    else if (type == 'checkbox' || type == 'radio')
	      this.checked = false;
	    // select elements need to have their 'selectedIndex' property set to -1
	    // (this works for both single and multiple select elements)
	    else if (tag == 'select')
	      this.selectedIndex = -1;
	  });
	};
	

function loadClusterData(){
	//根据当前的cdpId取得所属数据中心的网络汇聚下拉菜单
	//initNetworkConvergenceSelect("cluster",$("#s_cdpid").val());
	$.ajax({   
        async:false,   
        cache:false,   
        type: 'POST',   
        url: ctx+"/resmgt-compute/cluster/findClusterByIdAct.action",//请求的action路径   
        data:{"rmClusterPo.id":$("#clusterIdSpecified").val()},
        error: function () {//请求失败处理函数   
            showError(i18nShow('compute_res_requestError'));   
        },   
        success:function(data){ //请求成功后处理函数。 
        	showDiv("cluster_div",i18nShow('compute_res_cluster_detail'));
        	var clusterId = "clusterId:"+nodeId;
        	showEchartDiv("new_tab");
        	new_tab("new_tab_t","cluster_div",i18nShow('compute_res_cluster_detail'));
        	echartMeterDic(clusterId);
        	$("#s_poolName_current").html(data.clusterName);
			$("#cluster_Name").html(data.clusterName);
			$("#cluster_ename").html(data.ename);
			$("#cluster_remark").html(data.remark);
//			$("#datastore_type").html(data.datastore_type);
//			$("#datastore_type").html(data.datastore_type);
			$("#s_vmType").html(data.vmTypeName);
			$("#hidden_vmType").val(data.vmTypeName);
			$("#s_vmDistriType").html(data.vmDistriTypeName);
			if(data.platformType != 4){
				$("#s_manageServer_th").css("display","");
				$("#s_manageServer_td").css("display","");
				$("#s_manageServer").html(data.manageServerName);
			}else{
				$("#s_manageServer_th").css("display","none");
				$("#s_manageServer_td").css("display","none");
			}
			
			/*$("#s_manageServerBak").html(data.manageServerBakName);*/
			//$("#s_datastoreType").html(data.datastoreTypeName);
			$("#s_networkConvergence").html(data.networkConvergenceName);
			$("#cluster_id").val(data.id);
			$("#datastoreType").val(data.datastore_type);//隐藏域，保存CDP的存储类型，在主机关联集群时，做判断使用。
			var plat=data.platformType;
			$("#tab10").show();
			if(plat==4){
				$("#tab10").hide();
			}else{
				$("#tab10").show();				
			}
			//保存集群上级CDP的存储类型
			//已关联物理机信息显示表格的生成。
			initCmClusterHostInfo();
        }   
    });
}

function selectCmClusterHostInfos(){
	var clusterId=$("#cluster_id").val();
	var flag;
	$.post(ctx + "/resmgt-compute/host/selectCmClusterHostInfoss.action",{"cluster_id": clusterId},function(data){
		 flag = data.result;
		
	});
		 return flag;
}


//------------------------------------
function showClusterUpdateDiv(){
	 var clusterId=$("#cluster_id").val();
		//得到当前窗口的父类
	    var dialogParent = $("#clusterUpdateDiv").parent();
		//对窗口进行克隆，并进行隐藏
        var dialogOwn = $("#clusterUpdateDiv").clone();  
        dialogOwn.hide(); 
		
		$("label.error").remove();
		$("#clusterUpdateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:305,
				width:654,
				title:i18nShow('compute_res_cluster_update'),
		        //resizable:false,
		        close: function () {
	            	//将隐藏的克隆窗口追加到页面上
	                dialogOwn.appendTo(dialogParent);  
	                $(this).dialog("destroy").remove();   
	            }
		})
		
		$.post(ctx+"/resmgt-compute/cluster/findClusterByIdAct.action",{"rmClusterPo.id" : clusterId},function(data){
			$("#cluster_id").val(data.id);
			$("#cluster_cdpId").val(data.cdpId);
			$("#u_cluster_Name").val(data.clusterName);
			$("#clusterCheckName").val(data.clusterName);
			$("#u_cluster_ename").val(data.ename);
			$("#clusterCheckEname").val(data.ename);
			$("#u_cluster_remark").val(data.remark);
			$("#clusterMethod").val("update");
			initVmTypeSelectu(data.platformType);
			$("#u_vmType option").each(function(){
		        if($(this).text() == data.vmTypeName){
		        $(this).attr("selected",true);
		        }
		    });  
			$("#u_vmDistriType option").each(function(){
		        if($(this).val() == data.vmDistriType){
		        $(this).attr("selected",true);
		        }
		    });  
			$("#u_datastoreType option").each(function(){
		        if($(this).val() == data.datastoreType){
		        $(this).attr("selected",true);
		        }
		    }); 
			initVmManageServerSelect(data.datacenterId,null);
			$("#u_manageServer option").each(function(){
		        if($(this).val() == data.manageServer){
		        $(this).attr("selected",true);
		        }
		    });  
			/*$("#u_manageServerBak option").each(function(){
		        if($(this).val() == data.manageServerBak){
		        $(this).attr("selected",true);
		        }
		    }); */ 
			initNetworkConvergenceSelect("pool",data.resPoolId);
			$("#u_networkConvergence option").each(function(){
		        if($(this).val() == data.networkConvergence){
		        $(this).attr("selected",true);
		        }
		    });
		
			//使form所有数据为可输入
			if(data.platformType == 4){
				$("#u_cluster_Name").attr("readonly",true);
				$("#u_cluster_ename").attr("readonly",true);
				$("#u_vmType").attr("disabled",true);
				$("#u_manageServer").attr("disabled",true);
			}else{
				$("#u_cluster_Name").attr("readonly",false);
				$("#u_cluster_ename").attr("readonly",false);
				$("#u_vmType").attr("disabled",false);
				$("#u_manageServer").attr("disabled",false);
			}
			
		})
	}
	
	
	function load(){
		jQuery.validator.addMethod("stringCheck", 
				function(value, element) { 
					return this.optional(element) || /^[a-zA-Z0-9-_]*$/g.test(value);      
				},
				i18nShow('compute_res_cluster_stringCheck'));
		jQuery.validator.addMethod("clusterNameCheck", function(value, element) {
			var validateValue = true;
			var clusterMethod = $("#clusterMethod").val();
			var clusterCheckName = $("#clusterCheckName").val();
			var cluster_cdpId = $("#cluster_cdpId").val();
			$.ajax({
				type : 'post',
				datatype : "json",
				data : {
					"rmClusterPo.clusterName" : value,
					"rmClusterPo.cdpId" : cluster_cdpId
				},
				url : ctx + "/resmgt-compute/cluster/findRmClusterPoByName.action",
				async : false,
				success : (function(data) {
					if (clusterMethod == "update") {
						if (data == null || data.clusterName == clusterCheckName) {
							validateValue = true;
							// alert(validateValue+"1");
						} else {
							validateValue = false;
						}
		
					} else {
						if (data == null) {
							validateValue = true;
						} else {
							validateValue = false;
						}
					}
				}),
			});
			return this.optional(element) || validateValue;
		}, i18nShow('compute_res_cluster_clusterNameCheck')); 
		
		jQuery.validator.addMethod("clusterEnameCheck", function(value, element) { 
			var validateValue=true;
			var clusterMethod=$("#clusterMethod").val();
			var clusterCheckEname = $("#clusterCheckEname").val();
			$.ajax({
				type:'post',
				datatype : "json",
				data:{"rmClusterPo.ename":value},
				url:ctx+"/resmgt-compute/cluster/findRmClusterPoByEname.action",
				async : false,
				success:(function(data){
					if(clusterMethod=="update"){
						if(data==null||data.ename==clusterCheckEname){
							validateValue=true;
//							alert(validateValue+"1");
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
		i18nShow('compute_res_cluster_clusterEnameCheck')); 
		$("#clusterUpdateForm").validate({
			rules: {
				u_cluster_Name: {required: true,clusterNameCheck:true},
				u_cluster_ename: {required: true,stringCheck:true,clusterEnameCheck:true,maxlength:30},
				u_vmType: {required: true},
				u_vmDistriType: {required: true},
				u_datastoreType: {required: true},
				u_networkConvergence: {required: true}
			},
			messages: {
				u_cluster_Name: {required: i18nShow('compute_res_cluster_name_notEmpty'),clusterNameCheck:i18nShow('compute_res_used')},
				u_cluster_ename: {required: i18nShow('compute_res_ename_notEmpty'),stringCheck: i18nShow('compute_res_cdpStringCheck'),clusterEnameCheck:i18nShow('compute_res_used'),maxlength:i18nShow('compute_res_cluster_maxLength')},
				u_vmType: {required: i18nShow('compute_res_cluster_vmType_notEmpty')}, 
				u_vmDistriType:{required: i18nShow('compute_res_cluster_vmDistriType_notEmpty')},
				/*u_datastoreType:{required: "存储类型不能为空"},*/
				u_networkConvergence:{required: i18nShow('compute_res_cluster_networkConvergence_notEmpty')}
			},
			submitHandler: function() {
				updateOrSaveClusterData();
			}
		});
	}
function saveOrUpdateClusterBtn(){
	load();
	$("#clusterUpdateForm").submit();  
}

function updateOrSaveClusterData(){
	var resPoolId = "";
	var clusterMethod = $("#clusterMethod").val();
	var cluster_id = $("#cluster_id").val();
	var cluster_cdpId = $("#cluster_cdpId").val();
	if(cluster_cdpId!=null && cluster_cdpId!=""){
		resPoolId = $("#cdp_resPoolId").val();
	}else{
		resPoolId = $("#s_resid").val();
	}
	var u_cluster_Name = $("#u_cluster_Name").val();
	var u_cluster_ename = $("#u_cluster_ename").val();
	var u_cluster_remark = $("#u_cluster_remark").val();
	
	var u_vmType = $("#u_vmType").val();
	var u_vmDistriType = $("#u_vmDistriType").val();
	var u_manageServer = $("#u_manageServer").val();
	/*var u_manageServerBak = $("#u_manageServerBak").val();*/
	var u_datastoreType = $("#u_datastoreType").val();
	var u_networkConvergence = $("#u_networkConvergence").val();
	var url;
	var zTree = $.fn.zTree.getZTreeObj("treeRm");
	var sNodes = zTree.getSelectedNodes();
	if(clusterMethod=="update"){
		url= ctx+"/resmgt-compute/cluster/updateRmClusterPoByIdAct.action"
	}else{
		url= ctx+"/resmgt-compute/cluster/savaRmClusterPoAct.action"
	}
	var data = {
				'rmClusterPo.id':cluster_id,
				'rmClusterPo.cdpId':cluster_cdpId,
				'rmClusterPo.clusterName':u_cluster_Name,
				'rmClusterPo.ename':u_cluster_ename,
				'rmClusterPo.remark':u_cluster_remark,
				'rmClusterPo.vmType':u_vmType,
				'rmClusterPo.vmDistriType':u_vmDistriType,
				'rmClusterPo.manageServer':u_manageServer,
				/*'rmClusterPo.manageServerBak':u_manageServerBak,*/
				'rmClusterPo.networkConvergence':u_networkConvergence,
				'rmClusterPo.resPoolId':resPoolId
			};
	$.ajax({
		type:'post',
		datatype : "json",
		url:url,
		async : false,
		data:data,
		beforeSend: function () {
			if(u_vmType=="1"&&u_manageServer==""){
				showTip(i18nShow('compute_res_cluster_save_tip1'));
				return false;
			}else{
			}
			/*if(u_vmType=="1"&&u_manageServer==u_manageServerBak){
				showError(i18nShow('compute_res_cluster_save_tip2'));
				return false;
			}else{
			}*/
			/*if(u_vmType!="1"&&u_manageServer!=""&&u_manageServer==u_manageServerBak){
				showError(i18nShow('compute_res_cluster_save_tip2'));
				return false;
			}else{
			}*/
			showTip("load");
        },
		success:(function(data){
			closeTip();
			if(clusterMethod=="update"){
				modifyNode(nodeId,u_cluster_Name);
				loadClusterData();
			}else if(clusterMethod=="save"){
				//addNode(data.id,data.clusterName,$("#hid_icon").val(),null,false,"cluster",true,true,null);
				if (sNodes && sNodes.length>0) {
					refreshZTreeAfterAdd(zTree,sNodes[0]);
				}
			}
			$("#clusterUpdateDiv").dialog("close");
			showTip(i18nShow('compute_res_saveSuccess'));
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
        	//closeTip();
            showError(i18nShow('compute_res_saveFail'));   
		} 
	});
}
//
	function createClusterData(resId){
		$("label.error").remove();
		//得到当前窗口的父类
		 var dialogParent = $("#clusterUpdateDiv").parent();
		 //对窗口进行克隆，并进行隐藏
         var dialogOwn = $("#clusterUpdateDiv").clone();  
         dialogOwn.hide();  
	          
	     $("#clusterUpdateDiv").dialog({  
	    	 	autoOpen : true,
				modal:true,
				height:320,
				width:930,
				title:i18nShow('compute_res_cluster_add'),
				//resizable:false,
	            close: function () {
	            	//将隐藏的克隆窗口追加到页面上
	                dialogOwn.appendTo(dialogParent);  
	                $(this).dialog("destroy").remove();   
	            }
	        });  
//		clearTab();
		//清除form所有数据
		clearForm($('#clusterUpdateForm'));
//		$("#cdpUpdateDiv").disable(); 
		//使form所有数据为可输入
		$("#clusterUpdateDiv").enable(); 
		//添加隐藏域的默认值
		$("#clusterMethod").val("save");
		$("#cluster_cdpId").val(resId);
//		$("#cdp_status").val("Y");
		//从cdp中得到默认的平台类型
		$("#u_platformType").val($("#ss_platformType").val());
		//添加默认的用户信息
		$("#cluster_createUser").val("createUser");
		//默认为请选择
		selectByValue("u_vmType","");
		flatSelectByValue("u_vmDistriType","SINGLE");
		selectByValue("u_manageServer","");
		/*selectByValue("u_manageServerBak","");*/
		flatSelectByValue("u_datastoreType","LOCAL_DISK");
		$("#u_networkConvergence option:first").prop("selected", 'selected');
		initVmTypeSelectu($("#ss_platformType").val());
		$("#hid_icon").val(iconPath+'cluster.png');
		selectByValue("u_storage_id","");
	}
	//在资源池下子新建集群，将cdpId设置为空
	function createClusterUnderRes(resId){
		$("label.error").remove();
		//得到当前窗口的父类
		 var dialogParent = $("#clusterUpdateDiv").parent();
		 //对窗口进行克隆，并进行隐藏
         var dialogOwn = $("#clusterUpdateDiv").clone();  
         dialogOwn.hide();  
	          
	     $("#clusterUpdateDiv").dialog({  
	    	 	autoOpen : true,
				modal:true,
				height:342,
				width:654,
				title:i18nShow('compute_res_cluster_add_toRespool'),
				//resizable:false,
	            close: function () {
	            	//将隐藏的克隆窗口追加到页面上
	                dialogOwn.appendTo(dialogParent);  
	                $(this).dialog("destroy").remove();   
	            }
	        });  
//		clearTab();
		//清除form所有数据
		clearForm($('#clusterUpdateForm'));
//		$("#cdpUpdateDiv").disable(); 
		//使form所有数据为可输入
		$("#clusterUpdateDiv").enable(); 
		//添加隐藏域的默认值
		$("#clusterMethod").val("save");
		$("#cluster_cdpId").val("");
//		$("#cdp_status").val("Y");
		//从cdp中得到默认的平台类型
		$("#u_platformType").val($("#s_platformType").val());
		//添加默认的用户信息
		$("#cluster_createUser").val("createUser");
		//默认为请选择
		selectByValue("u_vmType","");
		flatSelectByValue("u_vmDistriType","SINGLE");
		selectByValue("u_manageServer","");
		/*selectByValue("u_manageServerBak","");*/
		flatSelectByValue("u_datastoreType","LOCAL_DISK");
		$("#u_networkConvergence option:first").prop("selected", 'selected');
		initVmTypeSelectu($("#s_platformType").val());
		$("#hid_icon").val(iconPath+'cluster.png');
		selectByValue("u_storage_id","");
	}
	
	function deleteClusterData( ){
		//根据ID查询当前Pool下是否存在host，如有不让删除
		var clusterId=$("#cluster_id").val();
		showTip(i18nShow('compute_res_deleteData_tip'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-compute/cluster/deleteRmClusterPoByIdAct.action",
					async : false,
					data:{"rmClusterPo.id":clusterId},
					beforeSend: function () {
			        	showTip("load");
			        },
//					beforeSend:(function(data){
//						if(confirm("确定删除数据？")){
//							return true;
//						}else{
//							return false;
//						}
//					}),
					success:(function(data){
	//					$("#gridTable").jqGrid().trigger("reloadGrid");
			        	closeTip();
						if(data.data==i18nShow('compute_res_delSuccess')){
						deleteTreeNode(clusterId);
						}
						showTip(data.data);
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
			        	closeTip();
						showError(i18nShow('compute_res_delFail'));
					} 
				});
		});

	}
	
	function clearTab(){
		 var tab = document.getElementById("clusterUpdateForm") ;
		 var inputs = tab.getElementsByTagName("input"); 
		 for(var k=0;k<inputs.length;k++) 
		 { 
			 if(inputs[k].type!='button'){
				 inputs[k].value=""; 
			 }
		 } 
	}
	function clusterUpdateDiv(){
		$("#clusterUpdateDiv").dialog("close");
	}
	/**
	 * 初始化虚拟机类型第一个选项
	 */
	function initVmType(){
		$("#u_vmType").html("");
		$("#u_vmType").append("<option value=''>"+i18nShow('compute_res_select')+"</option>");
	}
	/**
	 * 根据所选的平台类型初始化虚拟机类型
	 * @param value
	 */
	
	function initVmTypeSelectu(platformType){
		initVmType();
		if(platformType != ""){
			$.ajaxSettings.async = false;
				$.getJSON(ctx+"/resmgt-compute/cdp/getVmTypeListByPlatformType.action", {"platformType":platformType}, function(data) {
					$.each(data, function() {
						$("#u_vmType").append("<option value='" + this.virtualTypeId + "'>" + this.virtualTypeName + "</option>");
					});
				});	
		}
	}