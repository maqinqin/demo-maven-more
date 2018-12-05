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
	
	/**
	 * 初始化虚拟机类型第一个选项
	 */
	function initVmType(){
		$("#cdp_vmType").html("");
		$("#cdp_vmType").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	}
	
	/**
	 * 根据所选CDP所属的平台类型初始化虚拟机类型
	 * @param value
	 */
	function initVmTypeSelect(platformType){
		initVmType();
		if(platformType != ""){
			$.ajaxSettings.async = false;
				$.getJSON(ctx+"/resmgt-compute/cdp/getVmTypeListByPlatformType.action", {"platformType":platformType}, function(data) {
					$.each(data, function() {
						$("#cdp_vmType").append("<option value='" + this.virtualTypeId + "'>" + this.virtualTypeName + "</option>");
					});
				});	
		}
	}
	
$(function() {
	$("#cdp_vmType").change(function() {
		//initVmManageServerSelect($("#cdp_datacenterId").val(),this.value);
	});
	jQuery.validator.addMethod("cdpNameCheck", function(value, element) { 
		var validateValue=true;
		var cdpMethod=$("#cdpMethod").val();
		var cdpCheckName = $("#cdpCheckName").val();
		var cdp_resPoolId = $("#cdp_resPoolId").val();
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmCdpVo.cdpName":value,"rmCdpVo.resPoolId":cdp_resPoolId},
			url:ctx+"/resmgt-compute/cdp/findRmCdpVoByName.action",
			async : false,
			success:(function(data){
				if(cdpMethod=="update"){
					if(data==null||data.cdpName==cdpCheckName){
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
		i18nShow('compute_res_validateCdpName')); 
	
	jQuery.validator.addMethod("cdpEnameCheck", function(value, element) { 
		var validateValue=true;
		var cdpMethod=$("#cdpMethod").val();
		var cdpCheckEname = $("#cdpCheckEname").val();
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmCdpVo.ename":value},
			url:ctx+"/resmgt-compute/cdp/findRmCdpVoByEname.action",
			async : false,
			success:(function(data){
				if(cdpMethod=="update"){
					if(data==null||data.ename==cdpCheckEname){
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
		i18nShow('compute_res_validateCdpEname')); 
	jQuery.validator.addMethod("stringCheck", function(value, element) { 
		return this.optional(element) || /^[a-zA-Z0-9-_]*$/g.test(value);       
		},
	i18nShow('compute_res_validateCdpStringCheck'));  
		$("#cdpUpdateForm").validate({
			rules: {
				cdp_cdpName: {required: true,cdpNameCheck:true},
				cdp_ename: {required: true,stringCheck:true,cdpEnameCheck:true}
			},
			messages: {
				cdp_cdpName: {required: i18nShow('compute_res_cdpNot_empty'),cdpNameCheck: i18nShow('compute_res_used')},
				cdp_ename: {required: i18nShow('compute_res_cdpEnameNot_empty'),stringCheck: i18nShow('compute_res_cdpStringCheck'),cdpEnameCheck:i18nShow('compute_res_used')}		
			},
			submitHandler: function() {
				updateOrSaveCdpData();
			}
		});
	});	

function loadCdpData(){
	//根据当前的cdpId取得所属数据中心的网络汇聚下拉菜单
	initNetworkConvergenceSelect("cluster",nodeId);
	$.ajax({   
        async:false,   
        cache:false,   
        type: 'POST',   
        url: ctx+"/resmgt-compute/cdp/findCdpVoByIdAct.action",//请求的action路径   
        data:{"rmCdpVo.id":nodeId},
        error: function () {//请求失败处理函数   
            showError(i18nShow('compute_res_requestError'));   
        },   
        success:function(data){ //请求成功后处理函数。     
//        	showDiv("cdp_div","cdp详细信息");
        	var rmVqCdpId = "rmVqCdpId:"+nodeId;
        	showEchartDiv("new_tab");
        	new_tab("new_tab_t","cdp_div",i18nShow('compute_res_cdpDatail'));
        	echartMeterDic(rmVqCdpId);
        	$("#s_poolName_current").html(data.cdpName);
        	
        	//将当前点击ID暂存下来，为修改，删除时使用
        	$("#s_cdpid").val(data.id);
			$("#s_cdpName").html(data.cdpName);
			$("#s_cdpEname").html(data.ename);
			$("#s_cdpRemark").html(data.remark);
			//记录下CDP的平台类型
			$("#ss_platformType").val(data.platformType);
			$("#cdp_resPoolId").val(data.resPoolId);
			//-----------------------------------------------
			//当前的cdpId取得所属数据中心id
			$("#cdp_datacenterId").val(data.datacenterId);
        }   
    });
	//获取管理服务器和被管服务器
	initVmManageServerSelect($("#cdp_datacenterId").val(),null);
}

function showCdpUpdateDiv(){
		$("label.error").remove();
		$("#cdpUpdateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:310,
				width:500,
				title:i18nShow('compute_res_cdp_update'),
		        //resizable:false
		})
		var id = $("#s_cdpid").val();
		$.post(ctx+"/resmgt-compute/cdp/findCdpVoByIdAct.action",{"rmCdpVo.id" : id},function(data){
			
			$("#cdp_id").val(data.id);
			$("#cdp_resPoolId").val(data.resPoolId);
			$("#cdp_cdpName").val(data.cdpName);
			$("#cdpCheckName").val(data.cdpName);
			$("#cdp_ename").val(data.ename);
			$("#cdpCheckEname").val(data.ename);
			$("#cdp_platformType").val(data.platformType);
			$("#cdp_isActive").val(data.isActive);
			$("#cdp_remark").val(data.remark);
			$("#cdp_createUser").val(data.createUser);
			$("#cdp_updateUser").val(data.updateUser);
			$("#cdpMethod").val("update");
			
			$('#cdp_platformType').attr('disabled',true);
			//使form所有数据为可输入
			$("#cdpUpdateDiv").enable(); 
		})
	}

	function saveOrUpdateCdpBtn(){
		$("#cdpUpdateForm").submit();  
	}
	function updateOrSaveCdpData(){
		var cdpMethod = $("#cdpMethod").val();
		var cdp_id = $("#cdp_id").val();
		var cdp_resPoolId = $("#cdp_resPoolId").val();
		var cdp_cdpName = $("#cdp_cdpName").val();
		var cdp_ename = $("#cdp_ename").val();
		var cdp_platformType = $("#cdp_platformType").val();
		var cdp_status = $("#cdp_status").val();
		var cdp_remark = $("#cdp_remark").val();
		var cdp_createUser = $("#cdp_createUser").val();
		var cdp_updateUser = $("#cdp_updateUser").val();
		var cdp_isActive = $("#cdp_isActive").val();
		
		var url;
		var zTree = $.fn.zTree.getZTreeObj("treeRm");
		var sNodes = zTree.getSelectedNodes();
		if(cdpMethod=="update"){
			url= ctx+"/resmgt-compute/cdp/updateRmCdpPoAct.action"
		}else{
			url= ctx+"/resmgt-compute/cdp/saveRmCdpPoAct.action"
		}
		var data = {
					'rmCdpPo.id':cdp_id,
					'rmCdpPo.resPoolId':cdp_resPoolId,
					'rmCdpPo.cdpName':cdp_cdpName,
					'rmCdpPo.ename':cdp_ename,
					'rmCdpPo.platformType':cdp_platformType,
					'rmCdpPo.status':cdp_status,
					'rmCdpPo.createUser':cdp_createUser,
					'rmCdpPo.updateUser':cdp_updateUser,
					'rmCdpPo.isActive':cdp_isActive,
					'rmCdpPo.remark':cdp_remark
				};
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data,
			/*beforeSend:(function(data){
//				return validate(datas);
				if(cdp_vmType=="1"&&cdp_manageServer==""){
					showTip("虚机类型是VMWare时管理服务器不能为空！");
					return false;
				}else{
					//return true;
		        	//showTip("load");
				}
				if(cdp_vmType=="1"&&cdp_manageServer==cdp_manageServerBak){
					showTip("管理服务器与备用管理服务器不能选择一致！");
					return false;
				}else{
					//return true;
		        	//showTip("load");
				}
				if(cdp_vmType!="1"&&cdp_manageServer!=""&&cdp_manageServer==cdp_manageServerBak){
					showTip("管理服务器与备用管理服务器不能选择一致！");
					return false;
				}else{
					//return true;
		        	//showTip("load");
				}
				
			}),*/
			
			success:(function(data){
				if(cdpMethod=="update"){
					modifyNode(nodeId,cdp_cdpName);
					loadCdpData();
				}else if(cdpMethod=="save"){
					
				//	addNode(data.id,data.cdpName,$("#hid_icon").val(),null,false,"cdp",true,true,null);
					//清除当前节点下的子节点
					if (sNodes && sNodes.length>0) {
						refreshZTreeAfterAdd(zTree,sNodes[0]);
					}
					
				}
				$("#cdpUpdateDiv").dialog("close");
				showTip(i18nShow('compute_res_saveSuccess'));
				
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
	        	closeTip();
	            showError(i18nShow('compute_res_saveFail'));   
			} 
		});
	}

	function createCdpData(resId){
		$("label.error").remove();
		$("#cdpUpdateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:300,
				width:350,
				title:i18nShow('compute_res_cdp_add'),
				//resizable:false
		});
		//清除form所有数据
		clearForm($('#cdpUpdateForm'));
		//使form所有数据为可输入
		$("#cdpUpdateDiv").enable(); 
		//添加隐藏域的默认值
		$("#cdpMethod").val("save");
		$("#cdp_resPoolId").val(resId);
		//添加默认的用户信息
		$("#cdp_createUser").val("createUser");
		//从资源沲中得到默认的平台类型
		$("#cdp_platformType").val($("#s_platformType").val());
		//默认为请选择
		$("#hid_icon").val(iconPath+'cdp.png');
	}
	function deleteCdpData(){
		//根据ID查询当前Pool下是否存在host，如有不让删除
		var dataId = $("#s_cdpid").val();
		if(dataId){
			var hostCount =0 ;
			var clusterCount =0;
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-compute/cdp/findHostCountByRmCdpPoId.action",
				async : false,
				data:{"rmCdpPo.id":dataId},
				success:(function(data){
					hostCount = data.count;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('compute_res_selectError'));
				} 
			});
			//根据ID查询当前Pool下是否存在Cluster，如有不让删除
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-compute/cdp/findClusterCountByRmCdpPoId.action",
				async : false,
				data:{"rmCdpPo.id":dataId},
				success:(function(data){
					clusterCount = data.count;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('compute_res_selectError'));
				} 
			});
			if(hostCount==0 && clusterCount==0){
				showTip(i18nShow('compute_res_deleteData_tip'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-compute/cdp/deleteRmCdpPoByIdAct.action",
					async : false,
					data:{"rmCdpPo.id":dataId},
					beforeSend: function () {
			        	showTip("load");
			        },
					success:(function(data){
			        	closeTip();
						deleteTreeNode(nodeId);
						showTip(i18nShow('compute_res_delSuccess'));
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
			        	closeTip();
						showTip(i18nShow('compute_res_delFail'));
					} 
				});
				});
			}else{
				showTip(i18nShow('compute_res_cdp_del_tip'));
			}
		}else{
			showTip(i18nShow('compute_res_cdp_del_tip2'));
		};

	}
	
	function clearTab(){
		 var tab = document.getElementById("cdpUpdateTab") ;
		 var inputs = tab.getElementsByTagName("input"); 
		 for(var k=0;k<inputs.length;k++) 
		 { 
			 if(inputs[k].type!='button'){
				 inputs[k].value=""; 
			 }
		 } 
	}
	function closeCdpView(){
		$("#cdpUpdateDiv").dialog("close");
	}
