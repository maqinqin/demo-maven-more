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
	 * 根据所选资源沲或CDP所属的数据中心初始化当前数据中心下的网络汇聚
	 * @param value
	 */
	function initNetworkConvergenceSelect(type,value){
		initNetworkConvergence();
		if(value != ""){
			$.ajaxSettings.async = false;
			if(type=="cdp"){
				$.getJSON(ctx+"/resmgt-compute/cdp/getRmNwConvergeListByRmCdpPoId.action", {"rmCdpPo.id":value}, function(data) {
					$.each(data, function() {
						$("#cdp_networkConvergence").append("<option value='" + this.convergeId + "'>" + this.convergeName + "</option>");
					});
				});	
			}else if(type=="cluster"){
				$.getJSON(ctx+"/resmgt-compute/cdp/getRmNwConvergeListByRmCdpPoId.action", {"rmCdpPo.id":value}, function(data) {
					$.each(data, function() {
						$("#u_networkConvergence").append("<option value='" + this.convergeId + "'>" + this.convergeName + "</option>");
					});
				});	
			}else{
				$.getJSON(ctx+"/resmgt-compute/pool/getRmNwConvergeListByRmResPoolVoId.action", {"rmResPoolVo.id":value}, function(data) {
					$.each(data, function() {
						$("#u_networkConvergence").append("<option value='" + this.convergeId + "'>" + this.convergeName + "</option>");
					});
				});	
			}
		}
	}
	/**
	 * 初始化网络汇聚第一个选项
	 */
	function initNetworkConvergence(){
		$("#u_networkConvergence").html("");
		$("#u_networkConvergence").append("<option value=''>"+i18nShow('compute_res_select')+"</option>");
	}
	
	/**
	 * 根据所选资源沲或CDP所属的数据中心初始化当前数据中心下的管理服务器/备用管理服务器
	 * @param value
	 */
	function initVmManageServerSelect(value,vmType){
		initVmManageServer();
		if(value != ""){
			$.ajaxSettings.async = false;
				$.getJSON(ctx+"/resmgt-compute/cdp/getManageServerListByDatacenterIdAndVmType.action", {"rmCdpVo.datacenterId":value,"vmType":vmType}, function(data) {
					$.each(data, function() {
						$("#u_manageServer").append("<option value='" + this.id + "'>" + this.serverName + "</option>");
						$("#u_manageServerBak").append("<option value='" + this.id + "'>" + this.serverName + "</option>");
					});
				});	
		}
	}
	/**
	 * 初始化管理服务器/备用管理服务器第一个选项
	 */
	function initVmManageServer(){
		$("#u_manageServer").html("");
		$("#u_manageServer").append("<option value=''>"+i18nShow('compute_res_select')+"</option>");
		$("#u_manageServerBak").html("");
		$("#u_manageServerBak").append("<option value=''>"+i18nShow('compute_res_select')+"</option>");
	}

$(function() {
	jQuery.validator.addMethod("poolNameCheck", function(value, element) { 
		var validateValue=true;
		var poolMethod=$("#poolMethod").val();
		var pl_checkName = $("#pl_checkName").val();
		var pl_datacenter_id=$("#pl_datacenter_id").val();
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmResPoolVo.poolName":value,"rmResPoolVo.datacenterId":pl_datacenter_id},
			url:ctx+"/resmgt-compute/pool/selectRmResPoolByPoolName.action",
			async : false,
			success:(function(data){
				if(poolMethod=="update"){
					if(data==null||data.poolName==pl_checkName){
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
		i18nShow('compute_res_pool_validate')); 
	jQuery.validator.addMethod("poolEnameCheck", function(value, element) { 
		var validateValue=true;
		var poolMethod=$("#poolMethod").val();
		var pl_checkEname = $("#pl_checkEname").val();
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmResPoolVo.ename":value},
			url:ctx+"/resmgt-compute/pool/selectRmResPoolByEname.action",
			async : false,
			success:(function(data){
				if(poolMethod=="update"){
					if(data==null||data.ename==pl_checkEname){
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
		i18nShow('compute_res_pool_validate_ename')); 
	jQuery.validator.addMethod("stringCheck", function(value, element) { 
		return this.optional(element) || /^[a-zA-Z0-9-_]*$/g.test(value);     
		},
		i18nShow('compute_res_validateCdpStringCheck')); 
/*	jQuery.validator.addMethod("availableZoneCheck", function(value, element) { 
		var validateValue=true;
		if($("#pl_platformType").val()=='4'){
			if($("#pl_availablePartition".val()=="")){
				validateValue=false;
				
			}
		}
		return this.optional(element) || validateValue;     
		},
	"可用分区不能为空"); */
		$("#poolUpdateForm").validate({
			rules: {
				pl_pool_name:{required: true,poolNameCheck:true},
				pl_platformType: "required",
				pl_serviceType: "required",
				pl_secureAreaType: "required",
//				pl_secureLayer: "required",
//				pl_ename:"required"
				pl_ename: {required: true,stringCheck:true,poolEnameCheck:true},
				pl_availablePartition:{required: true},
				pl_hostType:{required: true}
			},
			messages: {
				pl_pool_name: {required: i18nShow('compute_res_pool_name_notEmpty'),poolNameCheck:i18nShow('compute_res_used')},
				pl_platformType:i18nShow('compute_res_pool_platformType_notEmpty'),
				pl_serviceType: i18nShow('compute_res_pool_serviceType_notEmpty'),
				pl_secureAreaType:i18nShow('compute_res_pool_secureAreaType_notEmpty'),
//				pl_secureLayer:"安全层不能为空",
//				pl_ename:"英文编码不能为空"
				pl_ename: {required: i18nShow('compute_res_ename_notEmpty'),stringCheck:i18nShow('compute_res_cdpStringCheck'),poolEnameCheck:i18nShow('compute_res_used')}	,
				pl_availablePartition:{required:i18nShow('compute_res_pool_availablePartition_notEmpty')},
				pl_hostType:{required:i18nShow('compute_res_pool_hostType_notEmpty')}
			},
			submitHandler: function() {
				updateOrSaveData();
			}
		});
	});	

function showEchartDiv(divId){
	$("#xd_div").hide();
	$("#"+divId).fadeIn('fast');
}

function loadPoolData(){
	
	//根据当前的cdpId取得所属数据中心的网络汇聚下拉菜单
	initNetworkConvergenceSelect("pool",nodeId);
	$.ajax({   
        async:false,
        cache:false,
        type: 'POST',
        url: ctx+"/resmgt-compute/pool/findRmResPoolVoByIdAct.action",//请求的action路径   
        data:{"rmResPoolVo.id":nodeId},
        error: function () {//请求失败处理函数   
            showError(i18nShow('compute_res_requestError'));
        },   
        success:function(data){ //请求成功后处理函数。

        	showDiv("pl_div",i18nShow('compute_res_pool_poolcDatil'));
//        	showEchartDiv("echart_div");
//        	echartDiv(nodeId);
        	showEchartDiv("new_tab");
        	new_tab("new_tab_t","pl_div",i18nShow('compute_res_pool_poolcDatil'));
        	var resPoolId = "resPoolId:"+nodeId;
        	echartMeterDic(resPoolId);
        	
        	//将当前点击ID暂存下来，为修改，删除时使用
        	$("#s_resid").val(data.id);
			$("#s_poolName").html(data.poolName);
			$("#s_poolName_current").html(data.poolName);
			$("#s_ename").html(data.ename);
			$("#s_platformTypeName").html(data.platformTypeName);
			//初始化CDP的平台类型;
			$("#s_platformType").val(data.platformType);
			$("#s_serviceType").html(data.serviceTypeName);
			$("#s_secureAreaType").html(data.secureAreaTypeName);
			$("#s_secureLayer").html(data.secureLayerName);
			$("#s_remark").html(data.remark);	
			
			//当前的资源沲Id取得所属数据中心id
			$("#cdp_datacenterId").val(data.datacenterId);
			$("#s_availableZone").html(data.availableName);
			$("#s_hostType").html(data.hostTypeName);
			$("#s_remark1").html(data.remark);
			//控制 可用分区 显示与隐藏    
//			if(data.platformType=='4'){
//				$("#azControl").show();
//				$("#remarkControl").hide();
//				$("#cluster_add_underRes").hide();
//				$("#cdp_add").hide();
//			}else{
//				$("#s_remark1").html(data.remark);
//				$("#azControl").hide();
//				$("#remarkControl").show();
//				$("#cluster_add_underRes").show();
//				$("#cdp_add").show();
//			}
			if(data.platformType=='4'){
			$("#azControl").show();
			$("#s_hostType").show();
			$("#cluster_add_underRes").hide();
			$("#cdp_add").hide();
			$("#pool_rel").show();
			$("#pool_synch").show();
		}else{
			$("#azControl").hide();
			$("#s_hostType").hide();
			$("#cluster_add_underRes").show();
			$("#cdp_add").show();
			$("#pool_rel").hide();
			$("#pool_synch").hide();
		}
			//根据当前的数据中心ID取得所属数据中心的管理服务器/备用管理服务器下拉菜单
			initVmManageServerSelect($("#cdp_datacenterId").val(),data.platformType);
        }   
    });

}
function showPoolUpdateDiv(){
	var cdpCount=0;
	var count = 0;
	var clusterCount = 0;
		$("label.error").remove();
		$("#poolUpdateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:360,
				width:654,
				title:i18nShow('compute_res_pool_update'),
//				draggable: false,
		       // resizable:false
		})
		var id = $("#s_resid").val();
		
		$.post(ctx+"/resmgt-compute/pool/findRmResPoolVoByIdAct.action",{"rmResPoolVo.id" : id},function(data){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-compute/pool/findDeviceCountByRmResPoolVoId.action",
				async : false,
				data:{"rmResPoolVo.id":id},
				success:(function(data){
					count = data.count;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError(i18nShow('compute_res_requestError'));
				} 
			});
			//查询资源池下cdp数量
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-compute/pool/findCDPCountByRmResPoolVoId.action",
				async : false,
				data:{"rmResPoolVo.id":id},
				success:(function(data){
					cdpCount = data.count;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('compute_res_requestError'));
				} 
			});
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-compute/pool/findClusterCountByRmResPoolVoId.action",
				async : false,
				data:{"rmResPoolVo.id":id},
				success:(function(data){
					clusterCount = data.count;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('compute_res_requestError'));
				} 
			});
			$("#pl_id").val(data.id);
			$("#pl_datacenter_id").val(data.datacenterId);
			$("#pl_pool_name").val(data.poolName);
			$("#pl_checkName").val(data.poolName);
			$("#pl_ename").val(data.ename);
			$("#pl_checkEname").val(data.ename);
			selectByValue("pl_platformType",data.platformType);
			selectByValue("pl_serviceType",data.serviceType);
			//可用分区 限制与 控制
			if($("#pl_platformType").val()=='4'){
				$("#availablePartition").css("display","block");
				$("#pl_availablePartition").empty();
//				selectByValue("pl_availablePartition",data.availableZoneId);
				selectTierByAreaAZ1(data.availableZoneId);
				$("#pl_availablePartition").val(data.availableZoneId);
				$("#pl_hostType").val(data.hostTypeId);
				if(clusterCount>0){
					$("#pl_availablePartition").attr("disabled","disabled");
				}else{
					$("#pl_availablePartition").removeAttr("disabled"); 
				}
			}else{
				$("#availablePartition").css("display","none");
			}
			$("#pl_platformType").change(function(){
				var val=this.value;
				if(val=='4'){
					$("#pl_availablePartition").removeAttr("disabled"); 
					$("#pl_availablePartition").empty();
					$("#pl_availablePartition").val("");
					$("#pl_availablePartition").html("<option value=''>"+i18nShow('compute_res_select')+"</option>");
					$("#availablePartition").css("display","block");
					selectTierByAreaAZ1(data.availableZoneId);
					$("#pl_hostType").val("");
				}else{
					$("#availablePartition").css("display","none");
				}
			});
//			selectByValue("pl_secureAreaType",data.secureAreaType);
			$("#pl_secureAreaType").empty();
			initSecureAreaType();
			 $("#pl_secureAreaType option").each(function(){
			        if($(this).text() == data.secureAreaTypeName){
			        $(this).attr("selected",true);
			        }
			    }); 
//			 $("#pl_secureAreaType").append("<option value='"+data.secureAreaType+"'>"+data.secureAreaTypeName+"</option>");
//			selectByValue("pl_secureLayer",data.secureLayer);
			$("#pl_secureLayer").empty();
			initSecureLayer($("#pl_secureAreaType").val());
//			$("#pl_secureAreaType").change(function(){
//				var val=this.value;
//				initSecureLayer(val);
//			});
			//initSecureLayer();
			//selectTierByArea();
			 $("#pl_secureLayer option").each(function(){
			        if($(this).text() == data.secureLayerName){
			        $(this).attr("selected",true);
			        }
			    });
			 $("#pl_secureAreaType").change(function(){
				var val=this.value;
				initSecureLayer(val);
				});
//			 $("#pl_secureLayer").append("<option value='"+data.secureLayer+"'>"+data.secureLayerName+"</option>");
			$("#pl_pool_type").val(data.poolType);
			$("#pl_status").val(data.status);
			$("#pl_isActive").val(data.isActive);
			$("#pl_createUser").val(data.createUser);
			$("#pl_updateUser").val(data.updateUser);
			$("#pl_remark").val(data.remark);
			$("#poolMethod").val("update");
			if(count>0){
				$('#pl_platformType').attr('disabled',true);
				$('#pl_serviceType').attr('disabled',true);
				$('#pl_secureAreaType').attr('disabled',true);
				$('#pl_secureLayer').attr('disabled',true);
				$('#pl_availablePartition').attr('disabled',true);
				$('#pl_hostType').attr('disabled',true);
			}else{
				$("#poolUpdateDiv").enable();
			}
			if(cdpCount>0||clusterCount>0||count>0){
				$('#pl_platformType').attr('disabled',true);
			}
		})
	}
	
	function saveOrUpdatePoolBtn(){
//		var tiercode = $.trim($("#pl_secureLayer").val());
//		var option_count = 0;
//		$("#pl_secureLayer option").each(function() {    
//			option_count++;
//		});  
//		if(option_count > 1 && tiercode == ""){
//			showTip("请您选择一个安全层");
//		}else{
		$("#poolUpdateForm").submit(); 
//		}
	}
	
	function updateOrSaveData(){
		var poolMethod = $("#poolMethod").val();
		var pl_id = $("#pl_id").val();
		var pl_datacenter_id = $("#pl_datacenter_id").val();
		var pl_pool_name = $("#pl_pool_name").val();
		var pl_ename = $("#pl_ename").val();
		var pl_status = $("#pl_status").val();
		var pl_pool_type = $("#pl_pool_type").val();
		var pl_remark = $("#pl_remark").val();
		var pl_isActive = $("#pl_isActive").val();
		var pl_createUser = $("#pl_createUser").val();
		var pl_updateUser = $("#pl_updateUser").val();
		var pl_platformType = $("#pl_platformType").val();
		var pl_serviceType = $("#pl_serviceType").val();
		var pl_secureAreaType = $("#pl_secureAreaType").val();
		var pl_secureLayer = $("#pl_secureLayer").val();
		var pl_availablePartition = $("#pl_availablePartition").val();
		var pl_hostType = $("#pl_hostType").val();
		var url;
		var zTree = $.fn.zTree.getZTreeObj("treeRm");
		var sNodes = zTree.getSelectedNodes();
		if(poolMethod=="update"){
			url= ctx+"/resmgt-compute/pool/updateRmResPoolVoAct.action"
		}else{
			url= ctx+"/resmgt-compute/pool/saveRmResPoolVoAct.action"
		}
		var data = {
					'rmResPoolVo.id':pl_id,
					'rmResPoolVo.datacenterId':pl_datacenter_id,
					'rmResPoolVo.poolName':pl_pool_name,
					'rmResPoolVo.ename':pl_ename,
					'rmResPoolVo.status':pl_status,
					'rmResPoolVo.poolType':pl_pool_type,
					'rmResPoolVo.isActive':pl_isActive,
					'rmResPoolVo.remark':pl_remark,
					'rmResPoolVo.createUser':pl_createUser,
					'rmResPoolVo.updateUser':pl_updateUser,
					'rmResPoolVo.platformType':pl_platformType,
					'rmResPoolVo.serviceType':pl_serviceType,
					'rmResPoolVo.secureAreaType':pl_secureAreaType,
					'rmResPoolVo.secureLayer':pl_secureLayer,
					'rmResPoolVo.availableZoneId':pl_availablePartition,
					'rmResPoolVo.hostTypeId':pl_hostType
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
				if(poolMethod=="update"){
					modifyNode(nodeId,pl_pool_name);
					loadPoolData();
				}else{
					//addNode(data.id,data.poolName,$("#hid_icon").val(),null,false,"pool",true,true,null);
					
					if (sNodes && sNodes.length>0) {
						refreshZTreeAfterAdd(zTree,sNodes[0]);
					}
				}
				$("#poolUpdateDiv").dialog("close");
				showTip(i18nShow('compute_res_saveSuccess'));
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip(i18nShow('compute_res_saveFail'));
			} 
		});
	}

	function createPoolData(centerId){
		$("label.error").remove();
		$("#poolUpdateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:360,
				width:654,
				title:i18nShow('compute_res_pool_add'),
				//resizable:false
		});
		//清除form所有数据
		clearForm($('#poolUpdateForm'));
		$("#pl_secureAreaType").empty();
		$("#pl_secureLayer").val("<option value='' selected>"+i18nShow('res_l_comput_select')+"...</option>");
		//使form所有数据为可输入
		$("#poolUpdateDiv").enable(); 
		//显示可用分区选择框
		
		availableZoneShow();
		//添加隐藏域的默认值
		$("#poolMethod").val("save");
		$("#pl_datacenter_id").val(centerId);
		$("#pl_pool_type").val("COMPUTE");
//		$("#pl_status").val("Y");
		//添加默认的用户信息
		$("#cdp_createUser").val("createUser");
		//默认为请选择
		flatSelectByValue("pl_platformType","1");
		flatSelectByValue("pl_serviceType","AP");
//		selectByValue("pl_secureAreaType","");
		flatSelectByValue("pl_secureLayer","1");
		initSecureAreaType();
		//initSecureLayer();
		$("#hid_icon").val(iconPath+'respoolcou.png');
		$("#pl_platformType  option[value=''] ").attr("selected",true);
		$("#pl_serviceType  option[value=''] ").attr("selected",true);
		$("#pl_secureLayer  option[value=''] ").attr("selected",true);
	}
	function deletePoolData(){
		//根据ID查询当前Pool下是否存在CDP，如有不让删除
		var dataId = $("#s_resid").val();
		if(dataId){
			var cdpCount =0 ;
			var deviceCount =0;
			var clusterCount = 0;
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-compute/pool/findCdpCountByRmResPoolVoId.action",
				async : false,
				data:{"rmResPoolVo.id":dataId},
				success:(function(data){
					cdpCount = data.count;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('compute_res_selectError'));
				} 
			});
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-compute/pool/findClusterCountByRmResPoolVoId.action",
				async : false,
				data:{"rmResPoolVo.id":dataId},
				success:(function(data){
					clusterCount = data.count;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('compute_res_selectError'));
				} 
			});
			//根据ID查询当前Pool下是否存在CDP，如有不让删除
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-compute/pool/findDeviceCountByRmResPoolVoId.action",
				async : false,
				data:{"rmResPoolVo.id":dataId},
				success:(function(data){
					deviceCount = data.count;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('compute_res_selectError'));
				} 
			});
			if(cdpCount==0 && deviceCount==0&&clusterCount==0){
				showTip(i18nShow('compute_res_deleteData_tip'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-compute/pool/deleteRmResPoolVoByIdAct.action",
					async : false,
					data:{"rmResPoolVo.id":dataId},
//					beforeSend:(function(data){
//						if(confirm("确定删除数据？")){
//							return true;
//						}else{
//							return false;
//						}
//					}),
					success:(function(data){
						deleteTreeNode(nodeId);
						showTip(i18nShow('compute_res_delSuccess'));
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showError(i18nShow('compute_res_delFail'));
					} 
				});
				});
			}else{
				showError(i18nShow('compute_res_pool_delte_tip'));
			}
		}else{
			showTip(i18nShow('compute_res_cdp_del_tip2'));
		};
	}
	function clearTab(){
		 var tab = document.getElementById("updateTab") ;
		 var inputs = tab.getElementsByTagName("input"); 
		 for(var k=0;k<inputs.length;k++) 
		 { 
			 if(inputs[k].type!='button'){
				 inputs[k].value=""; 
			 }
		 } 
	}
	function closePoolView(){
		$("#poolUpdateDiv").dialog("close");
	}
	function initSecureAreaType(){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-compute/pool/selectSecureArea.action",
			async : false,
			data:{},//当前功能的设备类型就是STORAGE
			success:(function(data){
				$("#pl_secureAreaType").empty();
				$("#pl_secureAreaType").append("<option value='' selected>"+i18nShow('compute_res_select')+"...</option>");
				$(data).each(function(i,item){
					$("#pl_secureAreaType").append("<option value='"+item.areaId+"'>"+item.areaName+"</option>");
				});			
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('compute_res_pool_load_secureAreaTypeErr'));
			} 
		});
	}
	
	function initSecureLayer(typeId){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-compute/pool/selectSecureLayer.action",
			async : false,
			data:{'typeId':typeId},//当前功能的设备类型就是STORAGE
			success:(function(data){
				$("#pl_secureLayer").empty();
				$("#pl_secureLayer").append("<option value='' selected>"+i18nShow('compute_res_select')+"</option>");
				$(data).each(function(i,item){
					$("#pl_secureLayer").append("<option value='"+item.value+"'>"+item.name+"</option>");
				});			
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('compute_res_pool_load_secureLayerErr'));
			} 
		});
	}
	
	function selectTierByAreaAZ(){
		var dataCenterId = $("#pl_datacenter_id").val();
		$("#pl_availablePartition").html("<option value=''>"+i18nShow('compute_res_select')+"</option>");
		var id=" ";
		$.ajaxSettings.async = false;
		$.getJSON(ctx+"/resmgt-compute/az/limitAvailableZone.action", {"availableZonePo.availableZoneId":id,"availableZonePo.dataCenterId":dataCenterId}, function(data) {
			$.each(data, function() {
				$("#pl_availablePartition").append("<option value='" + this.availableZoneId + "'>" +this.serverName +"/"+this.availableZoneName + "</option>");
			});
		});	
	}
	function selectTierByAreaAZ1(id){
		var dataCenterId = $("#pl_datacenter_id").val();
		$("#pl_availablePartition").html("<option value=''>"+i18nShow('compute_res_select')+"</option>");
		$.ajaxSettings.async = false;
		$.getJSON(ctx+"/resmgt-compute/az/limitAvailableZone.action", {"availableZonePo.availableZoneId":id,"availableZonePo.dataCenterId":dataCenterId}, function(data) {
			$.each(data, function() {
				$("#pl_availablePartition").append("<option value='" + this.availableZoneId + "'>" + this.serverName +"/"+this.availableZoneName + "</option>");
			});
		});	
	}
$(function() {
	$("#pl_secureAreaType").change(function(){
	var val=this.value;
	initSecureLayer(val);
	});
})

	