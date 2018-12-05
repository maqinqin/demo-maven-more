var treeRm;
var myExpandFun;
var datacenterId;
// jquery 屏蔽或者启用一个区域内的所有元素，禁止输入
(function($) {
	$.fn.disable = function() {
		// / <summary>
		// / 屏蔽所有元素
		// / </summary>
		// / <returns type="jQuery" />
		return $(this).find("*").each(function() {
			$(this).attr("disabled", "disabled");
		});
	}
	$.fn.enable = function() {
		// / <summary>
		// / 使得所有元素都有效
		// / </summary>
		// / <returns type="jQuery" />
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



$(function() {
	treeRm = function() {
		// alert(bizType);
		//alert(nodeId);
		if (bizType == "center") {
			$('#datacenterId').val(nodeId);
		}
		if (bizType == "root") {
			showDiv("xd_div", "网络资源池管理向导");
		}
		if (bizType == "sp") {
			// ajaxCall("/resmgt-storage/pool/getStoragePoolById.action",{"storagePoolId":nodeId},
			// function(){
			// showDiv("sp_div","存储资源池详细信息");
			// $("#lab_dc").html(ajaxResult.dataCenterName);
			// $("#lab_name").html(ajaxResult.poolName);
			// $("#lab_ename").html(ajaxResult.ename);
			// $("#lab_pooltype").html(ajaxResult.poolType);
			// $("#lab_serlev").html(ajaxResult.serviceLevelCode);
			// $("#lab_remark").html(ajaxResult.remark);
			// }
			// );
		} else if (bizType == "center") {

			ajaxCall("/resmgt-common/datacenter/getDataCenterById.action", {
				"dataCenterId" : nodeId
			},
					function() {
						showDiv("dc_div", "数据中心详细信息");
						$('#res_pro_div').show();
						$("#dcId").val(nodeId);
						$("#lab_dcname").html(ajaxResult.datacenterCname);
						$("#lab_dcEname").html(ajaxResult.ename);
						$("#lab_address").html(ajaxResult.address);
						$('#selection_current').html(ajaxResult.datacenterCname);
						if (ajaxResult.isActive == "Y") {
							$("#lab_isActive").html(
									"<span class='tip_green'>"+i18nShow('openstack_res_show_active_Y')+"</span>");
						} else {
							$("#lab_isActive").html(
									"<span class='tip_red'>"+i18nShow('openstack_res_show_active_N')+"</span>");
						}
						var dataCenterId = "dataCenterId:"+nodeId ;
						echartMeterDic(dataCenterId);
						$("#echart_Network_div").show();
						$("#usableipCount").val($("#usableipNum").val());
						$("#ipCount").val($("#ipNum").val());
						$("#usedipCount").val($("#usedipNum").val());
					});
		} else if (bizType == "pool") {
			loadNwPoolData();
		} else if (bizType == "bclass") {
			loadBclassData();
			var bClassId = "bClassId:"+nodeId ;
			echartMeterDic(bClassId);
			$('#res_pro_div').show();
			$("#echart_Network_div").show();
			$("#usableipCount").val($("#usableipNum").val());
			$("#ipCount").val($("#ipNum").val());
			$("#usedipCount").val($("#usedipNum").val());
		} else if (bizType == "cclass") {
			loadCclassData();
			var cClassId = "cClassId:"+nodeId ;
			echartMeterDic(cClassId);
			$('#res_pro_div').show();
			$("#echart_Network_div").show();
			$("#usableipCount").val($("#usableipNum").val());
			$("#ipCount").val($("#ipNum").val());
			$("#usedipCount").val($("#usedipNum").val());
		}
	};
	/**
	 * 异步加载节点定义方法
	 */
	myExpandFun = function() {
		if (bizType == "bclass") {
			// ajaxCall("/network/buildCclassNodeForTreeAsyncAct.action",{"bclassId":nodeId},
			// asyncAddNode
			// )
			var data = {
				'treeId' : nodeId,
				'bizType' : bizType
			};
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : ctx + "/network/buildNetworkResTree.action",
				data : data,
				error : function() {
					// alert(i18nShow('tip_req_fail'));
					showTip(i18nShow('tip_req_fail'));
				},
				success : function(data) {
					ajaxResult = data;
					asyncAddNode();
				}
			});
		}
		if (bizType == "cclass") {
			// ajaxCall("/network/buildCclassNodeForTreeAsyncAct.action",{"bclassId":nodeId},
			// asyncAddNode
			// )
			var data = {
				'treeId' : nodeId,
				'bizType' : bizType
			};
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : ctx + "/network/buildNetworkResTree.action",
				data : data,
				error : function() {
					// alert(i18nShow('tip_req_fail'));
					showTip(i18nShow('tip_req_fail'));
				},
				success : function(data) {
					ajaxResult = data;
					asyncAddNode();
				}
			});
		}
	};
	/* 注册方法 */
	// clickFunction(treeRm);
	regFunction(treeRm, myExpandFun);
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/network/buildNetworkResTree.action",// 请求的action路径
		// ,/network为struts配置文件的命名空间
		beforeSend : function() {
			showTip("load");
		},
		error : function() {// 请求失败处理函数
			// alert(i18nShow('tip_req_fail'));
			showTip(i18nShow('tip_req_fail'));
		},
		success : function(data) { // 请求成功后处理函数。
			zTreeInit("treeRm", data);
			zTree.selectNode(zTree.getNodeByParam("id", "0", null));
			bizType = "root";
			treeRm();
			closeTip();
		}
	});

	/*
	 * //点击新增数据中心 $("#center_add").click(function() {
	 * addCenter(getSelectNodeId()); });
	 */
	// 点击新增网络资源池
	$("#pool_add").click(function() {
		
		createNwPoolData(nodeId);
	});
	// 点击修改网络资源池
	$("#pool_modify").click(function() {
		showNwPoolUpdateDiv(nodeId);
	});
	// 点击删除网络资源池
	$("#pool_del1").click(function() {
		showNwPoolDelDiv(nodeId);
	});
	// 点击新增B段IP
	$("#bclass_add").click(function() {
		// addCdp(getSelectNodeId());
		createBclassData(nodeId);
	});
	// 点击修改B段信息
	$("#bclass_modify").click(function() {
		showBclassUpdateDiv(nodeId);
	});
	// 点击添加C段地址
	$("#add_Cclass").click(function() {
		showCclassAddDiv(nodeId);
	});
	// 表单验证
	$("#poolUpdateForm").validate({
		rules : {
			'pl_pool_name' : {
				required : true,
				poolNameCheck : true
			},
			'pl_ename' : {
				required : true,
				stringCheck:true,
				poolEnameCheck:true
			},
			'pl_nwPoolType' : {
				required : true
			},
			'c_ipStart' : {
				required : true,
				digits : true,
				min : 1,
				max : 255,
				validateIpValue :true
			},
			'c_ipEnd' : {
				required : true,
				digits : true,
				min : 1,
				max : 255,
				equalStartEndValue : true,
				validateIpValue : true,
				validateIpValuePlus : true
			}
		},
		messages : {
			'pl_pool_name' : {
				required : i18nShow('validate_data_required'),
				poolNameCheck : i18nShow('validate_data_remote')
			},
			'pl_ename' : {
				required : i18nShow('validate_data_required'),
				stringCheck: i18nShow('validate_availableZoneName_stringCheck'),
				poolEnameCheck : i18nShow('validate_data_remote')
			},
			'pl_nwPoolType' : {
				required : i18nShow('validate_data_required')
			},
			'c_ipStart' : {
				required : i18nShow('validate_data_required'),
				digits : i18nShow('validate_vmBase_digits'),
				number : i18nShow('validate_number'),
				min : i18nShow('validate_vmBase_min'),
				max : i18nShow('validate_vmBase_max'),
				validateIpValue : i18nShow('validate_rm_network_res_pool_other')
			},
			'c_ipEnd' : {
				required : i18nShow('validate_data_required'),
				digits : i18nShow('validate_vmBase_digits'),
				min : i18nShow('validate_vmBase_min'),
				max : i18nShow('validate_vmBase_max'),
				equalStartEndValue : i18nShow('validate_end_great_start'),
				validateIpValue : i18nShow('validate_rm_network_res_pool_other'),
				validateIpValuePlus : i18nShow('validate_rm_network_res_pool_ip_other')
			}

		},
		submitHandler : function() {
			updateOrSaveNwPoolData();
		}
	});
	
	validateIpValuePlus = function(){ 
		var flag;
		var ipStart = $('#c_ipStart').val();
		var ipEnd = $('#c_ipEnd').val();
		var u_cclassId = $('#u_cclassId').val();
		var ips = $('#ips').val();
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"ipStart":ipStart,"ipEnd":ipEnd,"u_cclassId":u_cclassId,"ips":ips},
			url:ctx+"/resmgt-compute/pool/validateIpValuePlus.action",
			async : false,
			success:(function(data){
				if(data.result == 'true'){
					flag = true;
				}else{
					flag = false;
				}
			}),
		});
		return flag;
	}
	jQuery.validator.addMethod("validateIpValuePlus",validateIpValuePlus,"");
	
	
	//验证资源池英文名称不能重复
	jQuery.validator.addMethod("poolEnameCheck", function(value, element) { 
		var cclassid = $('#u_cclassId').val();
		var validateValue=true;
		var poolMethod=$("#poolMethod").val();
		var pl_checkEname = $("#pl_checkEname").val();
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmResPoolVo.ename":value,"rmResPoolVo.cclassid":cclassid},
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
	"资源池英文编码不能重复"); 
	//验证资源池名称不能重复
		jQuery.validator.addMethod("poolNameCheck", function(value, element) { 
			var validateValue=true;
			var poolMethod=$("#poolMethod").val();
			var pl_checkName = $("#pl_checkName").val();
			var pl_datacenter_id=$("#pl_datacenter_id").val();
			var cclassid = $('#u_cclassId').val();
			$.ajax({
				type:'post',
				datatype : "json",
				data:{"rmResPoolVo.poolName":value,"rmResPoolVo.cclassid":cclassid},
				url:ctx+"/resmgt-compute/pool/selectRmResPoolByPoolName.action",
				async : false,
				success:(function(data){
					if(poolMethod=="update"){
						if(data==null||data.poolName==pl_checkName){
							validateValue=true;
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
		"资源池名称不能重复");
	//验证别名
	jQuery.validator.addMethod("stringCheck", function(value, element) { 
		return this.optional(element) || /^[a-zA-Z0-9-_]*$/g.test(value);     
		},
	"只能包括英文字母");
	equalStartEndValue = function() {
		var startNum = $('#c_ipStart').val();
		var endNum = $('#c_ipEnd').val();
		if (parseInt(endNum) < parseInt(startNum)) {
			return false;
		}
		return true;
	}
	jQuery.validator.addMethod("equalStartEndValue", equalStartEndValue, "结束值不能小于起始值");
	validateIpValue = function(inputValue){
		var flag;
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : ctx + "/network/findRmNwResPoolFullVoListAct.action",
			data : {
				"rmNwResPoolFullVo.cclassId" : nodeId,
				"rmNwResPoolFullVo.validateIp" : inputValue
			},
			success : function(data) {
				$('#inputIp').val(data.result);
			},
			error : function() {
				showTip(i18nShow('tip_req_fail'));
			}
		});
		var f = $('#inputIp').val();
		if(f=='true'){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	jQuery.validator.addMethod("validateIpValue", validateIpValue, "输入的IP已包含在其他资源池中");
	
	$("#cdpUpdateForm").validate({
		rules : {
			bclass_Name : "required",
			bclass_subnetmask : "required",
			bclass_scope : "required"
		},
		messages : {
			bclass_Name : i18nShow('validate_data_required'),
			bclass_subnetmask : i18nShow('validate_data_required'),
			bclass_scope : i18nShow('validate_data_required')
		},
		submitHandler : function() {
			updateOrSaveBclassData();
		}
	});

	// wmy，添加C段时验证
	$('#addCclassForm').validate({
		rules : {
			'c_cclassName' : {
				required : true,
				digits : true,
				min : 1,
				max : 255
			}
		},
		messages : {
			'c_cclassName' : {
				required : i18nShow('validate_data_required'),
				digits : i18nShow('validate_vmBase_digits'),
				number : i18nShow('validate_number'),
				min : i18nShow('validate_vmBase_min'),
				max : i18nShow('validate_vmBase_max')
			}
		},
		submitHandler : function() {
			updateOrSaveCdate();
		}
	});
	//wmy，修改C段时验证
	$('#updateCclassForm').validate({
		rules : {
			'uu_subnetmask' : {
				required : true
			},
			'uu_gateway' : {
				required : true
			}
		},
		messages : {
			'uu_subnetmask' : {
				required : i18nShow('validate_data_required')
			},
			'uu_gateway' : {
				required : i18nShow('validate_data_required')
			}
		},
		submitHandler : function() {
			updateOrSaveCdate();
		}
	});
	// 检查C段值
	function checkC(bclassId) {
		var result = false;
		var c_cclassNameHidden = $("#c_cclassNameHidden").val();// 当前B段的ip
		var inputCclass = $("#c_cclassName").val();
		var tempArr = c_cclassNameHidden.split(".");
		var inputCclassName = tempArr[0] + '.' + tempArr[1] + '.' + inputCclass
				+ '.0';
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : ctx + "/network/findCscopeCheck.action",// 请求的action路径

			data : {
				"rmNwCclassPo.cclassName" : inputCclassName,
				"rmNwCclassPo.bclassId" : bclassId
				
			},
			error : function() {// 请求失败处理函数
				// alert(i18nShow('tip_req_fail'));
				showTip(i18nShow('tip_req_fail'));
			},
			success : function(data) { // 请求成功后处理函数。
				if (data.result == 'false') {
					showTip(i18nShow('validate_rm_network_res_pool_ip_c_use'));
					result = false;
				} else {
					result = true;
				}
			}
		});
		return result;
	}
	// wmy，保存C段值
	function updateOrSaveCdate() {
		var addCclassMethod = $("#addCclassMethod").val();
		if(addCclassMethod == 'save'){
			var c_cclassNameHidden = $("#c_cclassNameHidden").val();// 当前B段的ip
			var inputCclass = $("#c_cclassName").val();
			var tempArr = c_cclassNameHidden.split(".");
			var inputCclassName = tempArr[0] + '.' + tempArr[1] + '.' + inputCclass + '.0';
			var c_subnetmask = $('#c_subnetmask').val();
			var c_gateway = $('#c_gateway').val();
			var bclassId = $('#bclassId').val();
			var c_remark = $('#c_remark').val();
			var datacenterId = $('#c_datacenterId').val();
			var zTree = $.fn.zTree.getZTreeObj("treeRm");
			var sNodes = zTree.getSelectedNodes();
			// checkC();检查输入的C段IP是否重复
			if (checkC(bclassId)) {
				var url;
				var data;
				url = ctx + "/network/saveRmNwCclassPo.action"
				data = {
					'rmNwCclassPo.cclassName' : inputCclassName,
					'rmNwCclassPo.subnetmask' : c_subnetmask,
					'rmNwCclassPo.gateway' : c_gateway,
					'rmNwCclassPo.remark' : c_remark,
					'rmNwCclassPo.bclassId' : bclassId,
					'rmNwCclassPo.datacenterId' : datacenterId
				};
				$.ajax({
					type : 'post',
					datatype : "json",
					url : url,
					async : false,
					data : data,
					success : (function(data) {
							/*addNode(data.cclassId, data.cclassName, $("#hid_icon")
									.val(), null, false, "cclass", true, true,
									null);
							myExpandFun();*/
							if (sNodes && sNodes.length>0) {
								refreshZTreeAfterAdd(zTree,sNodes[0]);
							}
						$("#addCclassDiv").dialog("close");
						showTip(i18nShow('tip_save_success'));
					}),
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						showTip(i18nShow('tip_save_fail'));
					}
				});
			}
		}
		
		if(addCclassMethod == 'update'){
			var c_className = $('#uu_cclassName').val();
			var c_classId = $('#uu_cclassId').val();
			var c_subnetmask = $('#uu_subnetmask').val();
			var c_gateway = $('#uu_gateway').val();
			var c_remark = $('#uu_remark').val();
			var url;
			url = ctx + "/network/updateRmNwCclassChangeAct.action"
			var data = {
					'rmNwCclassPo.cclassName' : c_className,
					'rmNwCclassPo.cclassId' : c_classId,
					'rmNwCclassPo.subnetmask' : c_subnetmask,
					'rmNwCclassPo.gateway' : c_gateway,
					'rmNwCclassPo.remark' : c_remark
			};
			// 按钮置灰
			//$("#c_updateCclassBtn").attr("disabled", true);
			$("#c_close").attr("disabled", true);
			$.ajax({
				type : 'post',
				datatype : "json",
				url : url,
				async : false,
				data : data,
				success : (function(data) {
					closeCclassView();	
					loadCclassData();//调用查询C段
					showTip(i18nShow('tip_save_success'));
					//调用仪表显示	
					var cClassId = "cClassId:"+nodeId ;
					echartMeterDic(cClassId);
					$('#res_pro_div').show();
					$("#echart_Network_div").show();
					$("#usableipCount").val($("#usableipNum").val());
					$("#ipCount").val($("#ipNum").val());
					$("#usedipCount").val($("#usedipNum").val());
				
				}),
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					showTip(i18nShow('tip_save_fail'));
				}
			});
		
		}
		
	}

	// **********例子start**********
	$("#btnAddTreeNode").click(function() {
		addNode("1002", "cba", false);// 下级添加
		// addNode("3456","abc",true);//同级添加
	})

	$("#btnDelTreeNode").click(function() {
		deleteTreeNode(nodeId);// 删除选中节点
		// deleteTreeNode("S001");//删除指定节点
	})

	$("#btnModTreeNode").click(function() {
		// modifyNode("S001","TEST");//修改指定节点
		// modifyNode(null,"TEST");//修改选中节点
		// modifyNode("haha");//最简便修改选中节点
	})
	// var jsonStr = '{"id":"2212","username":"adsfad"}';
	// var obj = jQuery.parseJSON(jsonStr);
	// jsonToDiv("testDiv",obj);
	// **********例子end**********
});
// wmy,新建网络资源池
function createNwPoolData(centerId) {
	$("label.error").remove();
	$("#hid_icon").val(iconPath + 'respoolnet.png');
	$("#poolUpdateDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 540,
		width : 680,
		title : i18nShow('rm_network_res_pool_save'),
		//resizable : false
	});
	$("#switchSpan").remove();
	$("#switchLable").append('<span class="switch-off" name="radio-1" id="switchSpan"></span>');
	honeySwitch.init();
	
	$("#physicalNetwork").empty();
    var op0 = "<option value=''>"+i18nShow('com_select_defalt')+"...</option>";
	$("#physicalNetwork").append(op0);
	
	$("#nwResPoolSaveBtn").attr("disabled", false);
	$("#nwResPoolcancelBtn").attr("disabled", false);
	// clearTab();
	// 清除form所有数据
	clearForm($('#poolUpdateForm'));
	// flatSelectByValue("pl_nwPoolType","IPPL");
	// 使form所有数据为可输入
	$("#poolUpdateDiv").enable();
	// 添加隐藏域的默认值
	$("#poolMethod").val("save");
	// alert("centerId="+centerId);
	// 默认为请选择
	selectByValue("pl_nwPoolType", "");
	$("#c_end").val(0);
	$("#c_mask").val(24);
	$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : ctx + "/network/findRmNwCclassPoByIdAct.action",// 请求的action路径

				data : {
					"rmNwCclassPo.cclassId" : nodeId
				},
				error : function() {// 请求失败处理函数
					// alert(i18nShow('tip_req_fail'));
					showTip(i18nShow('tip_req_fail'));
				},
				success : function(data) { // 请求成功后处理函数。
					openstackShow();
					// 初始化下拉列表
					initSecureTierList();
					initSecureAreaList();
					// 初始化下拉列表
					initUseList();
					initHostTypeList();
					initVirtualTypeList();
					initPlatFormList();
					// 点击提示先选安全区域信息
					$("#u_secureTierId").click(function() {
						showCaution($("#u_secureAreaId").val());
					});
					$("#u_virtualTypeId").click(function() {
						clickForVirtualTypeChange($("#u_platformId").val());
					});
					$("#u_hostTypeId").click(function() {
						clickForHostTypeChange();
					});
					$("#u_useId").click(function() {
						clickForUseChange();
					});

					datacenterId = data.datacenterId;
					initConvergeList(datacenterId);
					initConvergeList1(datacenterId);
					$('#u_cclassId').val(nodeId);
					$("#pl_datacenter_id").val(data.datacenterId);
					//document.getElementById('u_cclassName').innerHTML = data.cclassName;
					$("#u_cclassName").val(data.cclassName);
					var tempArr = data.cclassName.split(".");
					var ip = tempArr[0] + '.' + tempArr[1] + '.'+ tempArr[2] + '.';
					$("#c_cclassName2").val(ip);
					/*
					 * document.getElementById('u_ipStart').innerHTML =
					 * data.ipStart;
					 * document.getElementById('u_ipEnd').innerHTML =
					 * data.ipEnd;
					 */
					$("#u_platformId").val(
							data.platformId == null ? "" : data.platformId);
					$("#u_virtualTypeId").val(
							data.virtualTypeId == null ? ""
									: data.virtualTypeId);
					if ($("#u_virtualTypeId").val() != "") {
						clickForVirtualTypeChange("");
						$("#u_virtualTypeId").val(
								data.virtualTypeId == null ? ""
										: data.virtualTypeId);
					}
					$("#u_hostTypeId").val(
							data.hostTypeId == null ? "" : data.hostTypeId);
					if ($("#u_hostTypeId").val() != "") {
						changeUseByAll();
						// $("#u_useId").val(data.virtualTypeId==null?"":data.virtualTypeId);
					}
					$("#u_useId").val(data.useId == null ? "" : data.useId);
					$("#u_secureAreaId").val(
							data.secureAreaId == null ? "" : data.secureAreaId);
					$("#u_secureTierId").val(
							data.secureTierId == null ? "" : data.secureTierId);
					if ($("#u_secureTierId").val() != "") {
						changeSecureTierBySA("");
						$("#u_secureTierId").val(
								data.secureTierId == null ? ""
										: data.secureTierId);
					}
					$("#u_vlanId").val(data.vlanId);
					$("#u_vlanId1").val(data.vlanId);
					$("#u_convergeId").val(
							data.convergeId == null ? "" : data.convergeId);
					$("#u_convergeId1").val(
							data.convergeId == null ? "" : data.convergeId);
					$('#u_gateway').val(data.gateway);
					$('#u_subnetmask').val(data.subnetmask);
					$("#networkType").val(
							data.networkType == null ? "" : data.networkType);
					//document.getElementById('u_canUsedNum').innerHTML = 248 - data.usedNum;
					//document.getElementById('u_usedNum').innerHTML = data.usedNum;
					// $("#datacenterId").val(data.datacenterId);

				}
			});
	getIPInfo();

}
function loadNwPoolData() {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/network/findRmNwResPoolFullVoByIdAct.action",// 请求的action路径
		data : {
			"rmNwResPoolFullVo.id" : nodeId
		},
		error : function() {// 请求失败处理函数
			// alert(i18nShow('tip_req_fail'));
			showTip(i18nShow('tip_req_fail'));
		},
		success : function(data) { // 请求成功后处理函数。
			showDiv("pl_div", "网络资源池详细信息");
			document.getElementById('ss_cclass').innerHTML = data.cclassName;
			document.getElementById('ss_gateway').innerHTML = data.gateway;
			document.getElementById('ss_subnetmask').innerHTML = data.subnetmask;
			document.getElementById('s_poolName').innerHTML = data.poolName;// 用$("#s_poolName").val(data.poolName);不好使。
			document.getElementById('s_ename').innerHTML = data.ename;
			document.getElementById('s_nwPoolType').innerHTML = data.nwResPoolTypeName;
			document.getElementById('s_ipStart').innerHTML = data.ipStart;
			document.getElementById('s_ipEnd').innerHTML = data.ipEnd;
			$('#st').val(data.ipStart);
			$('#en').val(data.ipEnd);
			document.getElementById('s_platformName').innerHTML = data.platformName;
			document.getElementById('s_virtualTypeName').innerHTML = data.virtualTypeName;
			document.getElementById('s_hostTypeName').innerHTML = data.hostTypeName;
			document.getElementById('s_useName').innerHTML = data.useName;
			document.getElementById('s_secureAreaName').innerHTML = data.secureAreaName;
			document.getElementById('s_secureTierName').innerHTML = data.secureTierName;
			document.getElementById('s_convergeName').innerHTML = data.convergeName;
			document.getElementById('s_vlanId1').innerHTML = data.vlanId;
            document.getElementById('s_vlanId2').innerHTML = data.vlanId;
			document.getElementById('s_remark').innerHTML = data.remark;
			//alert("data.ipTotalCnt:"+data.ipTotalCnt+",data.ipAvailCnt:"+data.ipAvailCnt);
			document.getElementById('s_usedNum').innerHTML = data.ipTotalCnt-data.ipAvailCnt;
			document.getElementById('s_canUsedNum').innerHTML = data.ipAvailCnt;
			//平台类型为openstack时 显示信息
			var platformIdOP=data.platformId;
			document.getElementById('s_VmSmId').innerHTML = data.vmMsName;
			document.getElementById('s_vpcId').innerHTML = data.vpcName;
			document.getElementById('s_virtualRouterId').innerHTML = data.virtualRouterName;
			document.getElementById('s_networkType').innerHTML = data.networkType;
			$("#addRouter").hide();
			$("#cancalRouter").hide();
			if(platformIdOP=='4' || platformIdOP=='5'){
				$("#opControl").show();
				$("#opControl1").show();
				$("#opControl2").hide();
				var virtualRouterId = data.virtualRouterId;
				var vmMsId = data.vmMsId;
				var vpcId = data.vpcId;
				var resPoolId = data.resPoolId;
				if(data.isExternal == 'Y'){
					$("#addRouter").hide();
					$("#cancalRouter").hide();
				}else{
					if(virtualRouterId == null||virtualRouterId.trim() == ''){
						$("#addRouter").show();
						$("#cancalRouter").hide();
						//alert(vpcId);
						$("#addRouter").unbind('click').click(function() {
							addRouter(virtualRouterId,vmMsId,vpcId,resPoolId);
						});
					}else{
						$("#cancalRouter").show();
						$("#addRouter").hide();
						$("#cancalRouter").unbind('click').click(function() {
							cancalRouter(virtualRouterId,vmMsId,vpcId,resPoolId);
						});
					}
				}
			}else{
				$("#opControl").hide();
				$("#opControl1").hide();
				$("#opControl2").show();
			}
			/*
			if (data.isEnable == "N") {
				//$("#c_change").hide();//修改C段ip的按钮
				//$("#c_valid").hide();//启用按钮
				$('#holdCclass').hide();//按段占位按钮
				$('#ipupdate').hide();
				$('#gatewayEdit').hide();
				$('#ipdelete').hide();
			}*/
				/*
				 * if (data.isActive == "H") { $('#cclassStatus').html( '<font
				 * color="red">已占位</font>'); } else
				 */if (data.isEnable == "Y") {
					$("#c_valid").hide();//启用按钮隐藏
					$("#pool_modify").hide();//修改资源池信息按钮
					$('#holdCclass').show();//显示按段占位按钮
					$('#ipupdate').show();//显示修改按钮
					$('#ipdelete').show();//显示删除占位按钮
					$('#gatewayEdit').show();
					$('#pool_del1').hide();
				    $('#s_isEnable1').html( '<font color="green">'+i18nShow('com_status_Y')+'</font>');
                    $('#s_isEnable2').html( '<font color="green">'+i18nShow('com_status_Y')+'</font>');
					$('#showIpStatusDiv').show();
					$('#gridTableDiv').show();
					$('#pageGrid').show();
					showIpInfo();//加载生成的IP地址
			
			} else {
				$('#holdCclass').hide();//按段占位按钮
				$('#ipupdate').hide();
				$('#gatewayEdit').hide();
				$('#ipdelete').hide();
				$("#addRouter").hide();
				$("#cancalRouter").hide();
				
				$("#pool_modify").show();
				$("#c_valid").show();
				$("#pool_del1").show();
				$('#s_isEnable1').html( '<font color="red">'+i18nShow('com_status_N_1')+'</font>');
                $('#s_isEnable2').html( '<font color="red">'+i18nShow('com_status_N_1')+'</font>');
			}
		}
	});
}
function saveOrUpdateNwPoolBtn() {
	$("#poolUpdateForm").submit();
}
// wmy，保存资源池信息
function updateOrSaveNwPoolData() {
	// ---------------------开始校验下拉框中数值
	if($("#u_platformId").val() != null || $("#u_platformId").val() != ""){
		if($("#u_platformId").val() == "4" || $("#u_platformId").val() == "5"){
			if ($("#u_platformId").val() == null || $("#u_platformId").val() == ""
				|| $("#u_virtualTypeId").val() == null
				|| $("#u_virtualTypeId").val() == ""
					|| $("#u_hostTypeId").val() == null
					|| $("#u_hostTypeId").val() == "" || $("#u_useId").val() == null
					|| $("#u_useId").val() == "" || $("#u_secureAreaId").val() == null
					|| $("#u_secureAreaId").val() == "" || $("#networkType").val() == null
					|| $("#networkType").val() == "" 
					|| $("#isExternalNet").val() == null
					|| $("#isExternalNet").val() == ""
					|| $("#c_VmMsId").val()==null || $("#c_VmMsId").val()==""
					|| $("#c_vpcId").val()==null || $("#c_vpcId").val()==""
						|| $("#u_convergeId1").val()==null || $("#u_convergeId1").val()==""
					|| $("#u_vlanId1").val()==null || $("#u_vlanId1").val()=="") {
				// alert("带*的为必填项，不可为空！");
				showTip(i18nShow('tip_all_required'));
				return;
			}
		}else{
			if ($("#u_platformId").val() == null || $("#u_platformId").val() == ""
				|| $("#u_virtualTypeId").val() == null
				|| $("#u_virtualTypeId").val() == ""
					|| $("#u_hostTypeId").val() == null
					|| $("#u_hostTypeId").val() == "" || $("#u_useId").val() == null
					|| $("#u_useId").val() == "" || $("#u_secureAreaId").val() == null
					|| $("#u_secureAreaId").val() == "" || $("#u_convergeId").val() == null
					|| $("#u_convergeId").val() == "" 
						) {
				// alert("带*的为必填项，不可为空！");
				showTip(i18nShow('tip_all_required'));
				return;
			}
		}
		
	}else{
		showTip(i18nShow('tip_all_required'));
		return;
	}
	/*if (!checkNum($("#u_vlanId").val())) {
		// alert("VLANID的值必须为数字！");
		showTip("VLANID的值必须为数字！");
		return;
	}*/

	var tiercode = $.trim($("#u_secureTierId").val());
	var option_count = 0;
	$("#u_secureTierId option").each(function() {
		option_count++;
	});
	if (option_count > 1 && tiercode == "") {
		showTip(i18nShow('tip_rm_nw_res_pool_secure_tier_need'));
		return;
	}
	// ---------------------校验下拉框中数值结束
	// 按钮置灰
	$("#nwResPoolSaveBtn").attr("disabled", true);
	$("#nwResPoolcancelBtn").attr("disabled", true);

	// 资源池信息
	var poolMethod = $("#poolMethod").val();
	var pl_pool_name = $("#pl_pool_name").val();
	var pl_ename = $("#pl_ename").val();
	var pl_remark = $("#pl_remark").val();
	var nwResPoolTypeCode = $("#pl_nwPoolType").val();
	var datacenterId = $("#pl_datacenter_id").val();
	var cclassId = $('#u_cclassId').val();
	var subnetmask = $('#u_subnetmask').val();
	var gateway = $("#u_gateway").val();
	var ipStart = $("#c_ipStart").val();
	var ipEnd = $("#c_ipEnd").val();
	var platformId = $("#u_platformId").val();
	var virtualTypeId = $("#u_virtualTypeId").val();
	var hostTypeId = $("#u_hostTypeId").val();
	var useId = $("#u_useId").val();
	var secureAreaId = $("#u_secureAreaId").val();
	var secureTierId = $("#u_secureTierId").val();
	var physicalNetwork = $("#physicalNetwork").val();
	var subnetAddress = $("#u_cclassName").val();
	var isExternal = $("#isExternalNet").val();
	if(platformId=="4" || platformId=="5"){
		var vlanId = $("#u_vlanId1").val();
		var convergeId = $("#u_convergeId1").val();
	}else{
		var vlanId = $("#u_vlanId").val();
		var convergeId = $("#u_convergeId").val();
	}
	var networkType=$("#networkType").val();
	var total = ipEnd-ipStart+1;
	var vavil = total;
	//平台为openstack 保存的信息
	var vmMsId =$("#c_VmMsId").val();
	var vpcId =$("#c_vpcId").val();
	var virtualRouterId =$("#c_virtualRouterId").val();
	var url;
	var data;
	var zTree = $.fn.zTree.getZTreeObj("treeRm");
	var sNodes = zTree.getSelectedNodes();
	if (poolMethod == "update") {
		url = ctx + "/network/updateRmNwResPoolAct.action"
		data = {
			'rmNwResPoolFullVo.id' : nodeId,
			'rmNwResPoolFullVo.poolName' : pl_pool_name,
			'rmNwResPoolFullVo.ename' : pl_ename,
			'rmNwResPoolFullVo.remark' : pl_remark,
			'rmNwResPoolFullVo.platformId' : platformId,
			'rmNwResPoolFullVo.virtualTypeId' : virtualTypeId,
			'rmNwResPoolFullVo.hostTypeId' : hostTypeId,
			'rmNwResPoolFullVo.useId' : useId,
			'rmNwResPoolFullVo.secureAreaId' : secureAreaId,
			'rmNwResPoolFullVo.secureTierId' : secureTierId,
			'rmNwResPoolFullVo.vlanId' : vlanId,
			'rmNwResPoolFullVo.convergeId' : convergeId,
			'rmNwResPoolFullVo.ipStart' : ipStart,
			'rmNwResPoolFullVo.ipEnd' : ipEnd,
			'rmNwResPoolFullVo.subnetmask' : subnetmask,
			'rmNwResPoolFullVo.gateway' : gateway,
			'rmNwResPoolFullVo.ipTotalCnt' : total,
			'rmNwResPoolFullVo.ipAvailCnt' : vavil,
			'rmNwResPoolFullVo.vmMsId' : vmMsId,
			'rmNwResPoolFullVo.vpcId' : vpcId,
			'rmNwResPoolFullVo.virtualRouterId' : virtualRouterId,
			'rmNwResPoolFullVo.networkType':networkType,
			'rmNwResPoolFullVo.physicalNetwork':physicalNetwork,
			'rmNwResPoolFullVo.isExternal':isExternal,
			'rmNwResPoolFullVo.subnetAddress':subnetAddress
		};
	} else {// 存储资源池以及其他相关信息
		url = ctx + "/network/saveRmNwResPoolAct.action"
		data = {
			'rmNwResPoolFullVo.datacenterId' : datacenterId,
			'rmNwResPoolFullVo.poolName' : pl_pool_name,
			'rmNwResPoolFullVo.ename' : pl_ename,
			'rmNwResPoolFullVo.remark' : pl_remark,
			'rmNwResPoolFullVo.nwResPoolTypeCode' : nwResPoolTypeCode,
			'rmNwResPoolFullVo.platformId' : platformId,
			'rmNwResPoolFullVo.virtualTypeId' : virtualTypeId,
			'rmNwResPoolFullVo.hostTypeId' : hostTypeId,
			'rmNwResPoolFullVo.useId' : useId,
			'rmNwResPoolFullVo.secureAreaId' : secureAreaId,
			'rmNwResPoolFullVo.secureTierId' : secureTierId,
			'rmNwResPoolFullVo.vlanId' : vlanId,
			'rmNwResPoolFullVo.convergeId' : convergeId,
			'rmNwResPoolFullVo.ipStart' : ipStart,
			'rmNwResPoolFullVo.ipEnd' : ipEnd,
			'rmNwResPoolFullVo.subnetmask' : subnetmask,
			'rmNwResPoolFullVo.gateway' : gateway,
			'rmNwResPoolFullVo.cclassId' : cclassId,
			'rmNwResPoolFullVo.ipTotalCnt' : total,
			'rmNwResPoolFullVo.ipAvailCnt' : vavil,
			'rmNwResPoolFullVo.vmMsId' : vmMsId,
			'rmNwResPoolFullVo.vpcId' : vpcId,
			'rmNwResPoolFullVo.virtualRouterId' : virtualRouterId,
			'rmNwResPoolFullVo.networkType':networkType,
			'rmNwResPoolFullVo.physicalNetwork':physicalNetwork,
			'rmNwResPoolFullVo.isExternal':isExternal,
			'rmNwResPoolFullVo.subnetAddress':subnetAddress
		};
	}

	$.ajax({
		type : 'post',
		datatype : "json",
		url : url,
		async : false,
		data : data,
		/*
		 * beforeSend:(function(data){ return validate(datas); }),
		 */
		success : (function(data) {
			if (poolMethod == "update") {
				modifyNode(nodeId, pl_pool_name);
				loadNwPoolData();
			} else {
				/*addNode(data.id, data.poolName, $("#hid_icon").val(), null,
						false, "pool", true, true, null);*/
				if (sNodes && sNodes.length>0) {
					refreshZTreeAfterAdd(zTree,sNodes[0]);
				}
			}
			$("#poolUpdateDiv").dialog("close");
			showTip(i18nShow('tip_save_success'));
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// alert("保存失败");
			showTip(i18nShow('tip_save_fail'));
		}
	});
}

// wmy，新建C段信息
function showCclassAddDiv(nodeId) {
	// 清除错误信息
	$("label.error").remove();
	$("#hid_icon").val(iconPath + 'c_address.png');
	$("#addCclassDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 290,
		width : 350,
		title : i18nShow('rm_nw_res_pool_cclass_save'),
		//resizable : false
	});
	$("#CclassSaveBtn").attr("disabled", false);
	$("#CclassSaveCancelBtn").attr("disabled", false);
	// 清除form所有数据
	clearForm($('#addCclassForm'));
	// 使form所有数据为可输入
	$("#addCclassDiv").enable();
	$("#addCclassMethod").val("save");
	// C段子网掩码
	$('#c_subnetmask').val('255.255.255.0');
	
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/network/findRmNwBclassPoByIdAct.action",// 请求的action路径

		data : {
			"rmNwBclassPo.bclassId" : nodeId
		},
		error : function() {// 请求失败处理函数
			// alert(i18nShow('tip_req_fail'));
			showTip(i18nShow('tip_req_fail'));
		},
		success : function(data) {
			$("#c_cclassNameHidden").val(data.scope);
			var tempArr = data.scope.split(".");
			var ip = tempArr[0] + '.' + tempArr[1] + '.';
			$("#c_cclassName1").val(ip);
			$('#bclassId').val(nodeId);
			$('#c_datacenterId').val(data.datacenterId)
		}
	});
	document.getElementById("c_cclassName").focus().select(); 
}

function addCgateway() {
	var c_cclassNameHidden = $("#c_cclassNameHidden").val();// 当前B段的ip
	var cclass = $('#c_cclassName').val();
	var tempArr = c_cclassNameHidden.split(".");
	var cGateway = tempArr[0] + '.' + tempArr[1] + '.' + cclass + '.254';
	$('#c_gateway').val(cGateway);
}
// *********B段操作**************
// 新建B段
function createBclassData(resId) {
	$("label.error").remove();
	$("#hid_icon").val(iconPath + 'b_address.png');
	$("#cdpUpdateDiv").dialog({
		autoOpen : true,
		modal : true,   
		height : 350,
		width : 385,
		title : i18nShow('rm_nw_res_pool_bclass_save'),
		//resizable : false
	});
	// 按钮恢复
	$("#bclassSaveBtn").attr("disabled", false);
	$("#bclassSaveCancelBtn").attr("disabled", false);
	// clearTab();
	// 清除form所有数据
	clearForm($('#cdpUpdateForm'));
	// $("#cdpUpdateDiv").disable();
	// 使form所有数据为可输入
	$("#cdpUpdateDiv").enable();
	// 添加隐藏域的默认值
	$("#cdpMethod").val("save");

	// alert("resId="+resId);
	// $("#bclass_resPoolId").val(resId);
	$("#bclass_subnetmask").val("255.255.0.0");
	// 添加默认的用户信息
	// $("#cdp_createUser").val("createUser");
}
// 检查B段值
function checkScope() {
	var scope = $("#bclass_scope").val();
	var dcId = $("#dcId").val();
	var result = false;
	var tempArr = scope.split(".");
	if (tempArr[2] != 0 || tempArr[3] != 0) {
		// alert("B段IP值的第三和第四位必须为0，请重新输入！");
		showTip(i18nShow('rm_nw_res_pool_bclass_0'));
		result = true;
	} else {
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : ctx + "/network/findScopeCheckAct.action",// 请求的action路径

			data : {
				"rmNwBclassPo.scope" : scope,
				"rmNwBclassPo.datacenterId" : dcId
			},
			error : function() {// 请求失败处理函数
				// alert(i18nShow('tip_req_fail'));
				showTip(i18nShow('tip_req_fail'));
			},
			success : function(data) { // 请求成功后处理函数。
				// alert(data.scope);
				if (data.scope == null || data.scope == "") {
				} else {
					// alert("此B段IP值已存在，请输入其它值！");
					showTip(i18nShow('rm_nw_res_pool_bclass_use'));
					result = true;
				}
			}
		});
	}

	return result;
}
// 检查
function saveOrUpdateCdpBtn() {
	$("#cdpUpdateForm").submit();
}
// 添加C段地址时点击保存按钮
function saveOrUpdateCclassBtn() {
	$("#addCclassForm").submit();
}
// 修改C段地址点击保存按钮
function updateCclassBtn(){
	$("#updateCclassForm").submit();
}
// B段保存执行
function updateOrSaveBclassData() {
	if (!checkIPV4($("#bclass_subnetmask").val())) {
		// alert("请正确填写子网掩码IP！");
		showTip(i18nShow('rm_nw_res_pool_mask_check'));
		return;
	}
	if (!checkIPV4($("#bclass_scope").val())) {
		// alert("请正确填写B段IP值！");
		showTip(i18nShow('rm_nw_res_pool_bip_check'));
		return;
	}

	// 按钮置灰
	$("#bclassSaveBtn").attr("disabled", true);
	$("#bclassSaveCancelBtn").attr("disabled", true);
	var cdpMethod = $("#cdpMethod").val();
	var bclass_Name = $("#bclass_Name").val();
	var bclass_subnetmask = $("#bclass_subnetmask").val();
	var bclass_scope = $("#bclass_scope").val();
	var bclass_remark = $("#bclass_remark").val();
	var datacenterId = $('#datacenterId').val();
	var url;
	var data;
	if (cdpMethod == "update") {
		url = ctx + "/network/updateRmNwBclassAct.action"
		data = {
			'rmNwBclassPo.bclassId' : nodeId,
			'rmNwBclassPo.bclassName' : bclass_Name,
			'rmNwBclassPo.subnetmask' : bclass_subnetmask,
			'rmNwBclassPo.remark' : bclass_remark
		};
	} else {
		if (checkScope()) {
			$("#bclassSaveBtn").attr("disabled", false);
			$("#bclassSaveCancelBtn").attr("disabled", false);
			return;
		}
		url = ctx + "/network/saveRmNwBclassAct.action"
		data = {
			'rmNwBclassPo.bclassName' : bclass_Name,
			'rmNwBclassPo.subnetmask' : bclass_subnetmask,
			'rmNwBclassPo.scope' : bclass_scope,
			'rmNwBclassPo.remark' : bclass_remark,
			'rmNwBclassPo.datacenterId' : datacenterId
		};
	}

	$.ajax({
		type : 'post',
		datatype : "json",
		url : url,
		async : false,
		data : data,
		/*
		 * beforeSend:(function(data){ return validate(datas); }),
		 */
		success : (function(data) {
			if (cdpMethod == "update") {
				modifyNode(nodeId, bclass_Name);
				loadBclassData();						
			} else if (cdpMethod == "save") {
				addNode(data.bclassId, data.bclassName, $("#hid_icon").val(),
						null, false, "bclass", true, true, null);
				myExpandFun();
			}			
			$("#cdpUpdateDiv").dialog("close");
			loadBclassData();//调用查询B段
			showTip(i18nShow('tip_save_success'));
			//调用仪表		
			var bClassId = "bClassId:"+nodeId ;
			echartMeterDic(bClassId);
			$('#res_pro_div').show();
			$("#echart_Network_div").show();
			$("#usableipCount").val($("#usableipNum").val());
			$("#ipCount").val($("#ipNum").val());
			$("#usedipCount").val($("#usedipNum").val());
			
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_save_fail'));
		}
	});
}
// 查询B段
function loadBclassData() {
	$
			.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : ctx + "/network/findRmNwBclassPoByIdAct.action",// 请求的action路径

				data : {
					"rmNwBclassPo.bclassId" : nodeId
				},
				error : function() {// 请求失败处理函数
					// alert(i18nShow('tip_req_fail'));
					showTip(i18nShow('tip_req_fail'));
				},
				success : function(data) { // 请求成功后处理函数。
					$('#selection_current').html(data.bclassName);
					showDiv("cdp_div", "B段信息");
					document.getElementById('s_bclass_Name').innerHTML = data.bclassName;
					document.getElementById('s_bclass_subnetmask').innerHTML = data.subnetmask;
					document.getElementById('s_bclass_scope').innerHTML = data.scope;
					document.getElementById('s_bclass_remark').innerHTML = data.remark;
				}
			});
}
// 查询C段
function loadCclassData() {
	$
			.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : ctx + "/network/findRmNwCclassPoByIdAct.action",// 请求的action路径

				data : {
					"rmNwCclassPo.cclassId" : nodeId
				},
				error : function() {// 请求失败处理函数
					// alert(i18nShow('tip_req_fail'));
					showTip(i18nShow('tip_req_fail'));
				},
				success : function(data) { // 请求成功后处理函数。
					$('#selection_current').html(data.cclassName);
					showDiv("gridDiv", "C段信息");
					$("#c_change").attr("disabled", false);
					$("#c_valid").attr("disabled", false);
					$("#c_hold").attr("disabled", false);
					document.getElementById('s_cclassName').innerHTML = data.cclassName;
					document.getElementById('s_reamrk').innerHTML = data.remark;
					document.getElementById('s_gateway').innerHTML = data.gateway;
					document.getElementById('s_subnetmask').innerHTML = data.subnetmask;
					datacenterId = data.datacenterId;
				}
			});
}
var newPage;
var newRowNum;
// 展示ip信息
function showIpInfo() {
	// 先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
	$("#gridTable").jqGrid().GridUnload("gridTable");
	$('#gridTable')
	.jqGrid(
			{
				url : ctx + "/network/queryRmNwIpAddress.action",
				datatype : "json",
				mtype : "post",
				height : 215,
				postData : {
					'resPoolId' : nodeId
				},
				page : newPage,
				autowidth : true,
				rownumbers : true,
				colNames : [ 'IP', i18nShow('rm_nw_res_pool_ip_allocedStatus'), i18nShow('rm_nw_res_pool_ip_allocedStatus'), i18nShow('rm_nw_res_pool_ip_deviceName'), i18nShow('rm_nw_res_pool_ip_updateUser'),
					i18nShow('rm_nw_res_pool_ip_updateTime'), i18nShow('com_operate') ],
				colModel : [
						{
							name : "ip",
							index : "ip",
							sortable : true,
							align : 'left',
							width : 100
						},
						{
							name : "allocedStatusCode",
							index : "allocedStatusCode",
							hidden : false,
							formatter : function(cellVal, options,rowObject) {
								var result = "";
								if (rowObject.allocedStatusCode == 'A2PH') {
									result += i18nShow('rm_nw_res_pool_ip_A2PH');
								}else if (rowObject.allocedStatusCode == 'NA') {
									result +=i18nShow('rm_nw_res_pool_ip_NA');
								}else if(rowObject.allocedStatusCode == 'A2DV'){
									result +=i18nShow('rm_nw_res_pool_ip_A2DV');
								}
								return result;
							}
						},
						{
							name : "allocedStatusName",
							index : "allocedStatusName",
							sortable : true,
							align : 'left',
							width : 100,
							hidden : true
						},
						{
							name : "deviceName",
							index : "deviceName",
							sortable : true,
							align : 'left',
							width : 130
						},
						{
							name : "updateUser",
							index : "updateUser",
							sortable : false,
							align : 'left',
							width : 130
						},
						{
							name : "allocedDateString",
							index : "allocedDateString",
							sortable : false,
							align : 'left',
							width : 130
						},
						{
							name : "option",
							index : "option",
							sortable : false,
							align : 'left',
							width : 80,
							formatter : function(cellVal, options,
									rowObject) {
								var result = "";
								var beAvailableFlag = $(
										'#beAvailableFlag').val();
								var occupyFlag = $('#occupyFlag').val();
								if (rowObject.allocedStatusCode == 'A2PH'
										&& beAvailableFlag) {
									result +="<a  style='margin-right: 10px;margin-left: 10px;text-decoration:none;color: #18a689' href='javascript:#' title=''  onclick=beAvailable('" + rowObject.ip + "')>"+i18nShow('rm_nw_res_pool_ip_reUse')+"</a>";
									/*result += "<input type='button' class='gridBtn' style='color:#18a689' onclick=beAvailable('"
											+ rowObject.ip
											+ "')  value='"+i18nShow('rm_nw_res_pool_ip_reUse')+"'/>";*/
								}
								if (rowObject.allocedStatusCode == 'NA'
										&& occupyFlag) {
									result += 	result +="<a  style='margin-right: 10px;margin-left: 10px;text-decoration:none;color: #18a689' href='javascript:#' title=''  onclick=occupy('" + rowObject.ip + "')>"+i18nShow('rm_nw_res_pool_ip_use')+"</a>";
									/*result += "<input type='button' class='gridBtn' style='color:#18a689' onclick=occupy('"
											+ rowObject.ip
											+ "') value='"+i18nShow('rm_nw_res_pool_ip_use')+"'/>";*/
								}
								return result;
							}
						} ],
				sortname : "ip",
				sortorder : 'asc',
				viewrecords : true,
				rowNum : newRowNum ? newRowNum : 10,
				rowList : [ 10, 20, 50, 100 ],
				prmNames : {
					search : "search"
				},
				jsonReader : {
					root : "dataList",
					records : "record",
					repeatitems : false
				},
				pager : "#pageGrid",
				hidegrid : false
			});

	$('#holdCclassForm').validate({
		rules : {
			'querySeqStart' : {
				required : true,
				digits : true,
				min : 1,
				max : 255,
				equalStart:true
			},
			'querySeqEnd' : {
				required : true,
				digits : true,
				min : 1,
				max : 255,
				equalEnd:true,
				equalStartEnd : true
			}
		},
		messages : {
			'querySeqStart' : {
				required : i18nShow('validate_data_required'),
				digits : i18nShow('validate_vmBase_digits'),
				number : i18nShow('validate_number'),
				min : i18nShow('validate_vmBase_min'),
				max : i18nShow('validate_vmBase_max'),
				equalStart: i18nShow('validate_start_beyond')
			},
			'querySeqEnd' : {
				required : i18nShow('validate_data_required'),
				digits : i18nShow('validate_vmBase_digits'),
				min : i18nShow('validate_vmBase_min'),
				max : i18nShow('validate_vmBase_max'),
				equalEnd :i18nShow('validate_end_beyond'),
				equalStartEnd : i18nShow('validate_end_great_start')
			}
		},
		submitHandler : function() {
			occupyBySeq();
		}
	});
}
equalStartEnd = function(endNum) {
	var startNum = $('#querySeqStart').val();
	if (parseInt(endNum) < parseInt(startNum)) {
		return false;
	}
	return true;
}
jQuery.validator.addMethod("equalStartEnd", equalStartEnd, "结束值不能小于起始值");
equalStart = function(){
	var en = $('#en').val();
	var startNum = $('#querySeqStart').val();
	var st = $('#st').val();
	if(parseInt(startNum)>=parseInt(st) && parseInt(startNum)<parseInt(en)){
		return true;
	}
	return false;
}
jQuery.validator.addMethod("equalStart", equalStart, "起始值不能超出范围");
equalEnd = function(){
	var en = $('#en').val();
	var en1 = $('#querySeqEnd').val();
	var st = $('#st').val();
	if(parseInt(en1)>parseInt(st) && parseInt(en1)<=parseInt(en)){
		return true;
	}
	return false;
}
jQuery.validator.addMethod("equalEnd", equalEnd, "截止值不能超出范围");

function search(){
	var queryData = {allocated : $('#allocated').val().replace(/(^\s*)|(\s*$)/g, "")};
	jqGridReload("gridTable", queryData);
}

/**
 * 单个占位
 * 
 * @param {Object}
 *            ip
 */
function occupy(ip) {
	showTip(i18nShow('tip_ip_use_confirm'),function(){
		$.ajax({
			type : 'post',
			datatype : "json",
			url : ctx + "/network/updateRmNwIpForOccupy.action",
			async : false,
			data : {
				'ip' : ip,
				"rmNwCclassPo.cclassId" : nodeId,
				"status" : "occupy"
			},
			success : (function(data) {
				newPage = $("#gridTable").jqGrid().getGridParam("page");
				newRowNum = $("#gridTable").jqGrid().getGridParam("rowNum");
				loadNwPoolData();
				showTip(i18nShow('tip_ip_use_success'));
			}),
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				showTip(i18nShow('tip_ip_use_fail'));
			}
		});
	});
	
}
// 单个恢复可用
function beAvailable(ip) {
	showTip(i18nShow('tip_ip_unuse_confirm'),function(){
		$.ajax({
			type : 'post',
			datatype : "json",
			url : ctx + "/network/updateRmNwIpForOccupy.action",
			async : false,
			data : {
				'ip' : ip,
				"rmNwCclassPo.cclassId" : nodeId,
				"status" : "available"
			},
			success : (function(data) {
				newPage = $("#gridTable").jqGrid().getGridParam("page");
				newRowNum = $("#gridTable").jqGrid().getGridParam("rowNum");
				loadNwPoolData();
				showTip(i18nShow('tip_ip_unuse_success'));
			}),
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				showTip(i18nShow('tip_ip_unuse_fail'));
			}
		});
	});
	
}
function showOccupyBySeq() {
	$("label.error").remove();
	clearForm($('#holdCclassForm'));
	$('#querySeqDiv').dialog({
		autoOpen : true,
		modal : true,
		height : 85,
		width : 680,
		title : i18nShow('rm_nw_res_pool_ip_use_by_class')
	});
	$('#querySeqDiv').height(85);
	$('#buttonDiv').show();
}
function updateOccupyBySeq() {	
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/network/findRmNwResPoolFullVoByIdAct.action",// 请求的action路径

		data : {
			"rmNwResPoolFullVo.id" : nodeId
		},		
		error : function() {// 请求失败处理函数
			// alert(i18nShow('tip_req_fail'));
			showTip(i18nShow('tip_req_fail'));
		},
		success : function(data) {
			$("#being_pl_pool_name").val(data.poolName);
			$("#being_pl_ename").val(data.ename);
			}
		})
			
	$('#ipupdateDiv').dialog({
		autoOpen : true,
		modal : true,
		height : 200,
		width : 350,
		title : i18nShow('tip_rm_nw_res_pool_beupdate')
	});	
}

function gatewayEdit() {	
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/network/findRmNwResPoolFullVoByIdAct.action",// 请求的action路径

		data : {
			"rmNwResPoolFullVo.id" : nodeId
		},
		error : function() {// 请求失败处理函数
			// alert(i18nShow('tip_req_fail'));
			showTip(i18nShow('tip_req_fail'));
		},
		success : function(data) {
			$("#being_pl_pool_wg").val(data.gateway);
			}
		})
			
	$('#gwUpdateDiv').dialog({
		autoOpen : true,
		modal : true,
		height : 200,
		width : 350,
		title : i18nShow('tip_rm_nw_res_pool_beupdate')
	});	
}

function gateWayEditBtn(){
	var gateway = $("#being_pl_pool_wg").val();
	if(!gateway){
		showTip(i18nShow('tip_rm_nw_res_pool_gateway_not_null'));
		return false;
	}
	url = ctx + "/network/updatePoolGateway.action"
	data = {
		'rmNwResPoolFullVo.id' : nodeId,
		'rmNwResPoolFullVo.gateway' : gateway
	};
	$.ajax({
		type : 'post',
		datatype : "json",
		url : url,
		async : false,
		data : data,
		success : (function(data) {			
			if(data.success == true){
				closeTip();
				$("#gwUpdateDiv").dialog("close");
				loadNwPoolData();
				showTip(i18nShow('tip_save_success'));
			}else{
				closeTip();
				$("#gwUpdateDiv").dialog("close");
				showTip(i18nShow('tip_cont_update'));
			}
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#gwUpdateDiv").dialog("close");
			showTip(i18nShow('tip_save_fail'));
		}
	});
}

function beingSaveBtn(){
	var name = $("#being_pl_pool_name").val();
	var ename = $("#being_pl_ename").val();
	if(!name){
		showTip(i18nShow('tip_rm_nw_res_pool_name_not_null'));
		return false;
	}
	if(!ename){
		showTip(i18nShow('tip_rm_nw_res_pool_ename_not_null'));
		return false;
	}
	url = ctx + "/network/updateBeingRmNwResPoolAct.action"
	data = {
		'rmNwResPoolFullVo.id' : nodeId,
		'rmNwResPoolFullVo.poolName' : name,
		'rmNwResPoolFullVo.ename' : ename		
	};
	$.ajax({
		type : 'post',
		datatype : "json",
		url : url,
		async : false,
		data : data,
		/*
		 * beforeSend:(function(data){ return validate(datas); }),
		 */
		success : (function(data) {			
			modifyNode(nodeId, name);
			loadNwPoolData();			
			$("#ipupdateDiv").dialog("close");
			showTip(i18nShow('tip_save_success'));
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// alert("保存失败");
			showTip(i18nShow('tip_save_fail'));
		}
	});
}
function deleteOccupyBySeq() {	
	showTip(i18nShow('tip_delete_confirm'),function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url : ctx + "/network/delBeingRmNwResPoolFullVoByIdAct.action",
			async : false,
			data:{"id":nodeId},
			success:(function(data){
				if(data.success == true){
					closeTip();
					deleteTreeNode(nodeId);
					showTip(i18nShow('tip_delete_success'));
					//loadNwPoolData();
				}else{
					showTip(i18nShow('tip_cont_delete'));
				}
				
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				closeTip();
				showTip(i18nShow('tip_delete_fail'));
			} 
		});
		});

}

function occupyBySeqFun() {
	$('#holdCclassForm').submit();
}
// wmy,分段占位
function occupyBySeq() {
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/network/updateRmNwIpForSeqOccupy.action",
		async : false,
		data : {
			'seqStart' : $('#querySeqStart').val(),
			"rmNwCclassPo.cclassId" : nodeId,
			"seqEnd" : $('#querySeqEnd').val()
		},
		success : (function(data) {
			$("#querySeqDiv").dialog("close");
			if (data.result) {
				showTip(i18nShow('rm_nw_res_pool_ip_use_by_class_fail0') + data.result);
			} else {
				showTip(i18nShow('rm_nw_res_pool_ip_use_by_class_success'));
			}
			newPage = $("#gridTable").jqGrid().getGridParam("page");
			newRowNum = $("#gridTable").jqGrid().getGridParam("rowNum");
			loadNwPoolData();
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#querySeqDiv").dialog("close");
			showTip(i18nShow('rm_nw_res_pool_ip_use_by_class_fail'));
		}
	});
}
// 加载C段下的IP地址，RM_NW_IP_ADDRESS
function initIpInfoByCclassId() {
	// $("#gridDiv").show();
	// $("#gridDiv div").show();
	// $("#deviceDiv").hide();
	// $("#deviceDiv div").hide();

	// // 对计算资源树的nodeId进行保存。
	// localNotId = nodeId;

	// 先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
	$("#gridTable").jqGrid("GridUnload");
	$("#gridTable").jqGrid({
		url : ctx + "/network/findRmNwCclassPoByIdAct.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		postData : {
			clusterid : nodeId
		},
		height : 350,
		autowidth : true, // 是否自动调整宽度
		colModel : [ {
			name : "device_name",
			index : "device_name",
			label : "主机名称",
			width : 120,
			sortable : true,
			align : 'center'
		}, {
			name : "sn",
			index : "sn",
			label : "SN",
			width : 120,
			sortable : true,
			align : 'center'
		}, {
			name : "seat_name",
			index : "seat_name",
			label : "位置",
			width : 120,
			sortable : true,
			align : 'center'
		}, {
			name : "ip",
			index : "ip",
			label : "IP",
			width : 120,
			sortable : true,
			align : 'center'
		} ],
		viewrecords : true,
		sortname : "device_name",
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
		caption : "已关联物理机信息：",
		hidegrid : false
	});
}

// // 查询已关联的物理机信息
// function search() {
// jqGridReload("gridTable", {
// "clusterid" : nodeId,
// "deviceName" : $("#deviceName").val(),
// "sn" : $("#sn").val()
// });
// }

// 启用或占位:保存云服务器角色为启用或占位
function saveCclassValid(isActive) {
	// 占位操作不必校验
	var url;
	url = ctx + "/network/updateClassHoldOrValidAct.action"

	if (isActive == "H") {
		showTip(i18nShow('rm_nw_res_pool_ip_use_by_class_msg'), function() {
			// 置灰启用按钮
			$("#c_change").attr("disabled", "disabled");
			$("#c_valid").attr("disabled", "disabled");
		//	$("#pool_del").attr("disabled", "disabled");
			$("#c_hold").attr("disabled", "disabled");
			var data = {
				"rmNwCclassPo.cclassId" : nodeId,
				"rmNwCclassPo.isActive" : isActive
			};
			$.ajax({
				type : 'post',
				datatype : "json",
				url : url,
				async : false,
				data : data,
				/*
				 * beforeSend:(function(data){ return validate(datas); }),
				 */
				success : (function(data) {
					loadCclassData();
					var treeObj = $.fn.zTree.getZTreeObj("treeRm");
					var node = treeObj.getNodeByParam("id", nodeId);
					treeObj.selectNode(node, false);
					var name1 = node.name.substring(0, node.name.length - 3);
					// alert(name1);
					node.name = name1 + i18nShow('rm_nw_res_pool_ip_use_Y');
					// 调用updateNode(node)接口进行更新
					treeObj.updateNode(node);
					showTip(i18nShow('tip_ip_use_success'));
				}),
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					showTip(i18nShow('tip_ip_use_fail'));
				}
			});
		});
	}

	if (isActive == "Y") {
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : ctx + "/network/findRmNwResPoolFullVoByIdAct.action",
			data : {
				"rmNwResPoolFullVo.id" : nodeId
			},
			success : function(data) {
				// 校验必填项是否均输入
				if (data.platformId == null || data.platformId == ""
						|| data.virtualTypeId == null
						|| data.virtualTypeId == "" || data.hostTypeId == null
						|| data.hostTypeId == "" || data.useId == null
						|| data.useId == "" || data.secureAreaId == null
						|| data.secureAreaId == ""|| data.convergeId == null
						|| data.convergeId == "") {
					showTip(i18nShow('rm_nw_res_pool_ip_use_by_class_need'));
					return;
				}

				// 校验通过后执行
				showTip(i18nShow('rm_nw_res_pool_ip_use_by_class_start'), function() {
					// 置灰启用按钮
					$("#pool_modify").hide();//修改资源池信息按钮
					$("#c_valid").hide();//启用按钮消失
					$("#pool_del1").hide();
					var data = {
						"rmNwCclassPo.cclassId" : nodeId,//这个传递的是资源池id
						"rmNwCclassPo.isActive" : isActive
					};
					$.ajax({
						type : 'post',
						datatype : "json",
						url : url,
						async : false,
						data : data,
						/*
						 * beforeSend:(function(data){ return validate(datas);
						 * }),
						 */
						success : (function(data) {
							loadNwPoolData();// 重新加载资源池信息
							/*var treeObj = $.fn.zTree.getZTreeObj("treeRm");
							var node = treeObj.getNodeByParam("id", nodeId);
							treeObj.selectNode(node, false);
							var name1 = node.name.substring(0,
									node.name.length - 3);
							node.name = name1 + "已启用";
							// 调用updateNode(node)接口进行更新
							treeObj.updateNode(node);*/
							showTip(i18nShow('tip_ip_use_start_success'));
						}),
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							showTip(i18nShow('tip_ip_use_start_fail'));
						}
					});
				});
			},
			error : function() {
				showTip(i18nShow('tip_ip_use_start_fail'));
			}
		});

	}
}
// 初始化网络汇聚下拉
function initConvergeList(datacenterId) {
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/network/findInitConvergeListAct.action",
		async : false,
		data : {
			"datacenterId" : datacenterId
		},
		success : (function(data) {
			$("#u_convergeId").empty();
			$("#u_convergeId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
			$(data).each(
					function(i, item) {
						$("#u_convergeId").append(
								"<option value='" + item.convergeId + "'>"
										+ item.convergeName + "</option>");
					});
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_nw_converge_fail'));
		}
	});
}
function initConvergeList1(datacenterId) {
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/network/findInitConvergeListAct.action",
		async : false,
		data : {
			"datacenterId" : datacenterId
		},
		success : (function(data) {
			$("#u_convergeId1").empty();
			$("#u_convergeId1").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
			$(data).each(
					function(i, item) {
						$("#u_convergeId1").append(
								"<option value='" + item.convergeId + "'>"
										+ item.convergeName + "</option>");
					});
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_nw_converge_fail'));
		}
	});
}
// 初始化平台下拉
function initPlatFormList() {
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/network/findInitPlatFormListAct.action",
		async : false,
		// data:{"datacenterId":datacenterId},
		success : (function(data) {
			$("#u_platformId").empty();
			$("#u_platformId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
			$(data).each(
					function(i, item) {
						$("#u_platformId").append(
								"<option value='" + item.platformId + "'>"
										+ item.platformName + "</option>");
					});
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_nw_platform_fail'));
		}
	});
}
// 初始化虚拟化类型下拉
function initVirtualTypeList() {
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/network/findInitVirtualTypeListAct.action",
		async : false,
		// data:{"datacenterId":datacenterId},
		success : (function(data) {
			$("#u_virtualTypeId").empty();
			$("#u_virtualTypeId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
			$(data).each(
					function(i, item) {
						$("#u_virtualTypeId").append(
								"<option value='" + item.virtualTypeId + "'>"
										+ item.virtualTypeName + "</option>");
					});
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_nw_virtualType_fail'));
		}
	});
}
// 初始化主机类型下拉
function initHostTypeList() {
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/network/findInitHostTypeListAct.action",
		async : false,
		// data:{"datacenterId":datacenterId},
		success : (function(data) {
			$("#u_hostTypeId").empty();
			$("#u_hostTypeId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
			$(data).each(
					function(i, item) {
						$("#u_hostTypeId").append(
								"<option value='" + item.hostTypeId + "'>"
										+ item.hostTypeName + "</option>");
					});
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_nw_hostType_fail'));
		}
	});
}
// 初始化用途下拉
function initUseList() {
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/network/findInitUseListAct.action",
		async : false,
		// data:{"datacenterId":datacenterId},
		success : (function(data) {
			$("#u_useId").empty();
			$("#u_useId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
			$(data).each(
					function(i, item) {
						$("#u_useId").append(
								"<option value='" + item.useId + "'>"
										+ item.useName + "</option>");
					});
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_nw_useType_fail'));
		}
	});
}
// 初始化安全区域下拉
function initSecureAreaList() {
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/network/findInitSecureAreaListAct.action",
		async : false,
		// data:{"datacenterId":datacenterId},
		success : (function(data) {
			$("#u_secureAreaId").empty();
			$("#u_secureAreaId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
			$(data).each(
					function(i, item) {
						$("#u_secureAreaId").append(
								"<option value='" + item.secureAreaId + "'>"
										+ item.secureAreaName + "</option>");
					});
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_nw_secuireAire_fail'));
		}
	});
}
// 初始化安全分层下拉
function initSecureTierList() {
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/network/findInitSecureTierListAct.action",
		async : false,
		// data:{"datacenterId":datacenterId},
		success : (function(data) {
			$("#u_secureTierId").empty();
			$("#u_secureTierId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
			$(data).each(
					function(i, item) {
						$("#u_secureTierId").append(
								"<option value='" + item.secureTierId + "'>"
										+ item.secureTierName + "</option>");
					});
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_nw_secureTier_fail'));
		}
	});
}
// 安全区域及安全分区2级联动
function showCaution(secureAreaIdTran) {
	var secureAreaId = $("#u_secureAreaId").val();
	if (secureAreaId == null || secureAreaId == "") {
		// alert("请先选择安全区域！");
		showTip(i18nShow('tip_nw_secuireAire_need'));
		closeTip();
		return;
	} else {
		changeSecureTierBySA(secureAreaIdTran);
	}
}
function changeSecureTierBySA(secureAreaIdTran) {
	var secureAreaId = $("#u_secureAreaId").val();
	var secureTierId = $("#u_secureTierId").val();
	$("#u_secureTierId").unbind("click");
	if (secureAreaId == null || secureAreaId == "") {
		$("#u_secureTierId").empty();
		$("#u_secureTierId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
		// 点击提示先选安全区域信息
		$("#u_secureTierId").bind('click', function() {
			showCaution();
		});
	} else if (secureAreaId != secureAreaIdTran) {
		$
				.ajax({
					type : 'post',
					datatype : "json",
					url : ctx + "/network/findChangeSecureTierListAct.action",
					async : false,
					data : {
						"secureAreaId" : secureAreaId
					},
					success : (function(data) {
						$("#u_secureTierId").empty();
						$("#u_secureTierId").append(
								"<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
						$(data).each(
								function(i, item) {
									$("#u_secureTierId").append(
											"<option value='"
													+ item.secureTierId + "'>"
													+ item.secureTierName
													+ "</option>");
								});
					}),
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						showTip(i18nShow('tip_nw_secureTier_fail'));
					}
				});
	} else {

	}
}
function changeNetworkType(){
	var options = $("#networkType").val();
	var platformId = $("#u_platformId").val();
	if(options =='vlan'&& platformId=='4'){
		getPhyNet();
		$("#physicalNetworkSpan").removeAttr("style");
	}else if(options =='vlan'&& platformId=='5'){
		$("#physicalNetwork").append('<option value="default">default</option>');
		$("#physicalNetworkSpan").removeAttr("style");
	}else{
		$("#physicalNetwork").val("");
		$("#physicalNetworkSpan").attr("style","display: none");
	}
}
function changeExternal(){
	var options = $("#isExternalNet").val();
	if(options =='N'){
		$("#virtualRouterSpan").removeAttr("style");
	}else{
		$("#virtualRouterSpan").attr("style","display: none");
	}
}

//获取物理网络
function getPhyNet(){
	var vmmsId = $("#c_VmMsId").val(); 
	if(vmmsId == null || vmmsId == ''){
		showTip(i18nShow('tip_nw_vmms_need'));
		return;
	}
	$.ajax({
			type:'get',
			datatype : "json",
			url:ctx+"/network/getPhyNet.action",
			async : true,
			data:{"externalNetworkPo.vmMsId":vmmsId},
			success:(function(data){
				$("#physicalNetwork").empty();
				//var op0 = "<option value=''>请选择...</option>";
				//$("#addPhysicalNetwork").append(op0);
				for(var i=0;i<data.length;i++){
					var op1 = "<option value='"+data[i]+"'>"+data[i]+"</option>"
					$("#physicalNetwork").append(op1);
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				//alert(2);
				//showError("删除失败!");
			} 
		}); 
}
// 平台，虚拟化类型，主机类型，用途 4级联动
// 虚拟化类型
function clickForVirtualTypeChange(platformIdTrans) {
	var platformId = $("#u_platformId").val();
	
	if (platformId == null || platformId == "") {
		// alert("请先选择平台类型！");
		showTip(i18nShow('tip_nw_platform_need'));
		return;
	} else {
		changeVirtualTypeByPF(platformIdTrans);
	}
}
function changeVirtualTypeByPF(platformIdTrans) {
	$("#u_virtualTypeId").empty();
	$("#u_virtualTypeId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$("#u_hostTypeId").empty();
	$("#u_hostTypeId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$("#u_useId").empty();
	$("#u_useId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$("#networkType").change(function(){
		changeNetworkType();});
	//该功能在honeySwitch3.js中实现
	/*$("#isExternalNet").change(function(){
		changeExternal();});	*/
	initHostTypeList();
	var platformId = $("#u_platformId").val();
	var virtualTypeId = $("#u_virtualTypeId").val();
	var hostTypeId = $("#u_hostTypeId").val();
	$("#u_virtualTypeId").unbind("click");
	if (platformId == null || platformId == "") {
		$("#u_virtualTypeId").empty();
		$("#u_virtualTypeId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
		// 点击提示先选安全区域信息
		$("#u_virtualTypeId").bind('click', function() {
			clickForVirtualTypeChange();
		});
	} else if (platformId != platformIdTrans) {
		$.ajax({
			type : 'post',
			datatype : "json",
			url : ctx + "/network/findChangeVirtualTypeByPFAct.action",
			async : false,
			data : {
				"platformId" : platformId
			},
			success : (function(data) {
				$("#u_virtualTypeId").empty();
				$("#u_virtualTypeId")
						.append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
				$(data).each(
						function(i, item) {
							$("#u_virtualTypeId").append(
									"<option value='" + item.virtualTypeId
											+ "'>" + item.virtualTypeName
											+ "</option>");
						});
			}),
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				showTip(i18nShow('tip_nw_virtualType_fail'));
			}
		});
	} else {

	}
	// 主机类型
	if ((platformId == null || platformId == "")
			&& (virtualTypeId == null || virtualTypeId == "")) {
		$("#u_hostTypeId").unbind("click");
		$("#u_hostTypeId").click(function() {
			clickForHostTypeChange();
		});
	} else if ((platformId != null && platformId != "")
			&& (virtualTypeId == null || virtualTypeId == "")) {
		$("#u_hostTypeId").unbind("click");
		$("#u_hostTypeId").click(function() {
			clickForHostTypeChange();
		});
	} else if ((platformId != null && platformId != "")
			&& (virtualTypeId != null && virtualTypeId != "")) {
		$("#u_hostTypeId").unbind("click");

	}
	// 用途
	if ((platformId == null || platformId == "")
			&& (virtualTypeId == null || virtualTypeId == "")
			&& (hostTypeId == null || hostTypeId == "")) {
		$("#u_useId").unbind("click");
		$("#u_useId").click(function() {
			clickForUseChange();
		});
	} else if ((platformId != null && platformId != "")
			&& (virtualTypeId == null || virtualTypeId == "")
			&& (hostTypeId == null || hostTypeId == "")) {
		$("#u_useId").unbind("click");
		$("#u_useId").click(function() {
			clickForUseChange();
		});
	} else if ((platformId != null && platformId != "")
			&& (virtualTypeId != null && virtualTypeId != "")
			&& (hostTypeId == null || hostTypeId == "")) {
		$("#u_useId").unbind("click");
		$("#u_useId").click(function() {
			clickForUseChange();
		});
	} else {
		$("#u_useId").unbind("click");
	}
}
// 主机类型
function clickForHostTypeChange() {
	var platformId = $("#u_platformId").val();
	var virtualTypeId = $("#u_virtualTypeId").val();
	if (platformId == null || platformId == "") {
		showTip(i18nShow('tip_nw_platform_need'));
		// alert("请先选择平台类型！");
		closeTip();
		return;
	} else if (virtualTypeId == null || virtualTypeId == "") {
		// alert("请先选择虚拟化类型！");
		showTip(i18nShow('tip_nw_virtualType_need'));
		closeTip();
		return;
	} else {
		$("#u_hostTypeId").unbind("click");
	}

}

// 用途
function changeUseByVirtual() {
	$("#u_useId").click(function() {
		clickForUseChange();
	});
	changeUseByAll();
	$("#u_useId").click(function() {
		clickForUseChange();
	});
}
function changeUseByHost() {
	if ($("#u_hostTypeId").val() == null || $("#u_hostTypeId").val() == "") {
		$("#u_useId").click(function() {
			clickForUseChange();
		});
	} else {
		$("#u_useId").click(function() {
			clickForUseChange();
		});
		changeUseByAll();
	}
}
function clickForUseChange() {
	// alert("11111");
	var platformId = $("#u_platformId").val();
	var virtualTypeId = $("#u_virtualTypeId").val();
	var hostTypeId = $("#u_hostTypeId").val();
	if (platformId == null || platformId == "") {
		// alert("请先选择平台类型！");
		showTip(i18nShow('tip_nw_platform_need'));
		closeTip();
		return;
	} else if (virtualTypeId == null || virtualTypeId == "") {
		// alert("请先选择虚拟化类型！");
		showTip(i18nShow('tip_nw_virtualType_need'));
		closeTip();
		return;
	} else if (hostTypeId == null || hostTypeId == "") {
		// alert("请先选择主机类型！");
		showTip(i18nShow('tip_nw_hostType_need'));
		closeTip();
		return;
	} else {
		changeUseByAll();
		// $("#u_hostTypeId").unbind("click");
	}
}
function changeUseByAll() {
	var platformId = $("#u_platformId").val();
	var virtualTypeId = $("#u_virtualTypeId").val();
	var hostTypeId = $("#u_hostTypeId").val();
	$("#u_useId").unbind("click");
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/network/findChangeUseByAllAct.action",
		async : false,
		data : {
			"platformId" : platformId,
			"virtualTypeId" : virtualTypeId,
			"hostTypeId" : hostTypeId
		},
		success : (function(data) {
			$("#u_useId").empty();
			$("#u_useId").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
			$(data).each(
					function(i, item) {
						$("#u_useId").append(
								"<option value='" + item.useId + "'>"
										+ item.useName + "</option>");
					});
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_nw_useType_fail'));
		}
	});
}
// C段信息可修改
function cclassChange() {
	$("label.error").remove();
	$("#updategridDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 290,
		width : 350,
		title : i18nShow('rm_nw_res_pool_cclass_update'),
		// draggable: false,
		//resizable : false
	})
	// 按钮恢复
	$("#c_save").attr("disabled", false);
	$("#c_close").attr("disabled", false);
	//标记是更新
	$("#addCclassMethod").val("update");
	$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : ctx + "/network/findRmNwCclassPoByIdAct.action",// 请求的action路径

				data : {
					"rmNwCclassPo.cclassId" : nodeId
				},
				error : function() {// 请求失败处理函数
					// alert(i18nShow('tip_req_fail'));
					showTip(i18nShow('tip_req_fail'));
				},
				success : function(data) { // 请求成功后处理函数。
					datacenterId = data.datacenterId;
					//document.getElementById('uu_cclassName').innerHTML = data.cclassName;
					$('#uu_cclassName').val(data.cclassName);
					$('#uu_gateway').val(data.gateway);
					$('#uu_subnetmask').val(data.subnetmask);
					$('#uu_remark').val(data.remark);
					$('#uu_cclassId').val(nodeId);
				}
			});
}
// 保存C段信息
function updateCclassSave() {
	

	var platformId = $("#u_platformId").val();
	var virtualTypeId = $("#u_virtualTypeId").val();
	var hostTypeId = $("#u_hostTypeId").val();
	var useId = $("#u_useId").val();
	var secureAreaId = $("#u_secureAreaId").val();
	var secureTierId = $("#u_secureTierId").val();
	var vlanId = $("#u_vlanId").val();
	var convergeId = $("#u_convergeId").val();

	var url;
	url = ctx + "/network/updateRmNwCclassChangeAct.action"
	var data = {
		'rmNwCclassPo.cclassId' : nodeId,
		'rmNwCclassPo.platformId' : platformId,
		'rmNwCclassPo.virtualTypeId' : virtualTypeId,
		'rmNwCclassPo.hostTypeId' : hostTypeId,
		'rmNwCclassPo.useId' : useId,
		'rmNwCclassPo.secureAreaId' : secureAreaId,
		'rmNwCclassPo.secureTierId' : secureTierId,
		'rmNwCclassPo.vlanId' : vlanId,
		'rmNwCclassPo.convergeId' : convergeId
	};
	// 按钮置灰
	$("#c_save").attr("disabled", true);
	$("#c_close").attr("disabled", true);
	$.ajax({
		type : 'post',
		datatype : "json",
		url : url,
		async : false,
		data : data,
		success : (function(data) {
			closeCclassView();
			loadCclassData();
			showTip(i18nShow('tip_save_success'));
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showTip(i18nShow('tip_save_fail'));
		}
	});
}
//删除资源池
function showNwPoolDelDiv(id){
	showTip(i18nShow('tip_delete_confirm'),function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url : ctx + "/network/delRmNwResPoolFullVoByIdAct.action",
			async : false,
			data:{"rmNwResPoolFullVo.id":id},
			success:(function(data){
				closeTip();
				deleteTreeNode(nodeId);
				showTip(i18nShow('tip_delete_success'));
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				closeTip();
				showTip(i18nShow('tip_delete_fail'));
			} 
		});
		});
}
// 修改资源池
function showNwPoolUpdateDiv(id) {
	$("label.error").remove();
	$("#poolUpdateDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 540,
		width : 680,
		title : i18nShow('rm_network_res_pool_update'),
		// draggable: false,
		//resizable : false
	})
	$("#nwResPoolSaveBtn").attr("disabled", false);
	$("#nwResPoolcancelBtn").attr("disabled", false);
	
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/network/findRmNwResPoolFullVoByIdAct.action",// 请求的action路径

		data : {
			"rmNwResPoolFullVo.id" : nodeId
		},
		error : function() {// 请求失败处理函数
			// alert(i18nShow('tip_req_fail'));
			showTip(i18nShow('tip_req_fail'));
		},
		success : function(data) { // 请求成功后处理函数。
			// 初始化下拉列表
			initSecureAreaList();
			initSecureTierList();
			initUseList();
			initHostTypeList();
			initVirtualTypeList();
			initPlatFormList();
			initConvergeList(data.datacenterId);
			initConvergeList1(data.datacenterId);
			
			$("#poolMethod").val("update");
			$("#pl_pool_name").val(data.poolName);
			$("#pl_checkName").val(data.poolName);
			$("#pl_ename").val(data.ename);
			$("#pl_checkEname").val(data.ename)
			$("#pl_datacenter_id").val(data.datacenterId);
			selectByValue("pl_nwPoolType", data.nwResPoolTypeCode);
			$('#pl_nwPoolType').attr('disabled', 'disabled');
			selectByValue("u_platformId", data.platformId);
			selectByValue("u_virtualTypeId", data.virtualTypeId);
			selectByValue("u_hostTypeId", data.hostTypeId);
			selectByValue("u_useId", data.useId);
			selectByValue("u_secureAreaId", data.secureAreaId);
			if ($("#u_secureTierId").val() == "") {
				changeSecureTierBySA("");
				$("#u_secureTierId").val(
						data.secureTierId == null ? ""
								: data.secureTierId);
			}
			//selectByValue("u_secureTierId", data.secureTierId);
//			selectByValue("u_convergeId", data.convergeId);
			$("#u_cclassId").val(data.cclassId);
			//document.getElementById('u_cclassName').innerHTML = data.cclassName;
			var tempArr = data.cclassName.split(".");
			var ip = tempArr[0] + '.' + tempArr[1] + '.'+ tempArr[2] + '.';
			$("#c_cclassName2").val(ip);
			$("#u_cclassName").val(data.subnetAddress);
			$("#c_end").val(data.subnetEnd);
			$("#c_mask").val(data.maskBit);
			$("#pl_remark").val(data.remark);
			$("#u_cclass").val(data.cclassName);
			$("#u_gateway").val(data.gateway);
			$("#u_subnetmask").val(data.subnetmask);
			$("#c_ipStart").val(data.ipStart);
			$("#c_ipEnd").val(data.ipEnd);
//			$("#u_vlanId").val(data.vlanId);
			$("#ips").val(data.ipStart);
			$("#physicalNetwork").val(data.physicalNetwork);
			
			//初始化平台为openstack时   服务器 和 vpc 、路由器 的下拉框
			var u_platformId1 = $("#u_platformId").val();
			 $("#networkType").val(data.networkType);
			var networkType1 = $("#networkType").val();
			if (networkType1 == 'vlan') {
				$("#physicalNetworkSpan").removeAttr("style");
			} else {
				$("#physicalNetworkSpan").attr("style", "display: none");
			}
			$("#networkType").change(function() {
				changeNetworkType();
			});
			$("#isExternalNet").val(data.isExternal);
			var isExternalNet1 = $("#isExternalNet").val();
			if (isExternalNet1 == 'N') {
				$("#virtualRouterSpan").removeAttr("style");
			} else {
				$("#virtualRouterSpan").attr("style", "display: none");
			}
			$("#isExternalNet").change(function() {
				changeExternal();
			});
			var om1=$("#Openstack_message1");
			var om2=$("#Openstack_message2");
			var om3=$("#Openstack_message3");
			var om4=$("#Openstack_message4");
			var om5=$("#isExternalNet_p");
			if(u_platformId1=='4' || u_platformId1=='5'){
				om1.css("display","block");
				om2.css("display","block");
				om3.css("display","block");
				om4.css("display","none");
				om5.css("display","block");
				$("#virtualRouterSpan").css("display","block");//显示路由器
				$("#c_VmMsId").val(data.vmMsId);
					initVpcSelect();
					$("#c_vpcId").val(data.vpcId);
					$("#c_virtualRouterId").empty();
					initVirtualRouterSelect();
					$("#c_virtualRouterId").val(data.virtualRouterId);
			$("#c_VmMsId").change(function(){
				initVpcSelect();
				$("#c_virtualRouterId").empty();
				$("#c_vpcId").change(function(){
					initVirtualRouterSelect()
				});
			});
			$("#c_vpcId").change(function(){
				initVirtualRouterSelect()
			});
			selectByValue("u_convergeId1", data.convergeId);
			$("#u_vlanId1").val(data.vlanId);
		    $("#networkType").val(data.networkType);
		    $("#isExternalNet").val(data.isExternal);
		    //--------------------
			}else{
				om1.css("display","none");
				om2.css("display","none");
				om3.css("display","none");
				om4.css("display","block");
				om5.css("display","none");
				$("#virtualRouterSpan").css("display","none");//隐藏路由器
				selectByValue("u_convergeId", data.convergeId);
				$("#u_vlanId").val(data.vlanId);
			}
			openstackShow1();
		}
	});
}

// 修改B段
function showBclassUpdateDiv(id) {
	$("label.error").remove();
	$("#cdpUpdateDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 300,
		width : 400,
		title : i18nShow('rm_nw_res_pool_bclass_update'),
		//resizable : false
	})
	// 按钮恢复
	$("#bclassSaveBtn").attr("disabled", false);
	$("#bclassSaveCancelBtn").attr("disabled", false);
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/network/findRmNwBclassPoByIdAct.action",// 请求的action路径

		data : {
			"rmNwBclassPo.bclassId" : nodeId
		},
		error : function() {// 请求失败处理函数
			// alert(i18nShow('tip_req_fail'));
			showTip(i18nShow('tip_req_fail'));
		},
		success : function(data) { // 请求成功后处理函数。
			$("#cdpMethod").val("update");
			$("#bclass_Name").val(data.bclassName);
			$("#bclass_subnetmask").val(data.subnetmask);
			$("#bclass_scope").val(data.scope);
			$("#bclass_remark").val(data.remark);
		}
	});
	
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/network/whetherUpdateRmNwBclassPo.action",// 请求的action路径
		data : {
			"rmNwCclassPo.bclassId" : nodeId
		},
		success : function(data) { // 请求成功后处理函数。
			flag = data.result;
			 if(flag=='true'){//可修改
				 $("#bclass_scope").attr("disabled", false);
			 }else{//不能修改
				$("#bclass_scope").attr("disabled", true);
			 }
		}
	});
}

// 检验IP
function checkIPV4(value) {
	return /^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])$/i
			.test(value);
}
// 检验数字
function checkNum(value) {
	return /^[0-9]+$/g.test(value);
}
// 关闭对话框
function closeNwPoolView() {
	$("#poolUpdateDiv").dialog("close");
}
function closeBclassView() {
	$("#cdpUpdateDiv").dialog("close");
}
function closeAddCclassView() {
	$("#addCclassDiv").dialog("close");
}
function closeCclassView() {
	// alert("guan");return;
	$("#u_secureTierId").unbind("click");
	$("#u_virtualTypeId").unbind("click");
	$("#u_hostTypeId").unbind("click");
	$("#u_useId").unbind("click");
	$("#updategridDiv").dialog("close");
}
// 搜索树节点
function searchTreeByCname() {
	var searchForName = $("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var searchType = $("#sel_type").val();
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/network/findSubTreeByNameAct.action",// 请求的action路径
		data : {
			"nodeName" : searchForName,
			"searchType" : searchType
		},
		error : function() {// 请求失败处理函数
			showTip(i18nShow('tip_req_fail'), null, "red");
		},
		success : function(data) { // 请求成功后处理函数。
			zTreeInit("treeRm", data);
		}
	});
}
// 显示DIV
function showDiv(divId, title) {
	$("#rightContentDiv div").hide();
	$("#" + divId).fadeIn('fast');
	$("#lab_title").html(title);
}
// 弹出对话框
function openDialog(divId, title) {
	$("#" + divId).dialog({
		autoOpen : true,
		modal : true,
		height : 425,
		width : 400,
		title : title,
		// draggable: false,
		//resizable : false
	})
}

// 刷新树
function refreshTree() {
	var treeObj = $.fn.zTree.getZTreeObj("treeRm");
	// var nodes = treeObj.getSelectedNodes();

	treeObj.reAsyncChildNodes(null, "refresh");

}
// 刷新树的上级节点
function refreshTreeParentNode() {
	var treeObj = $.fn.zTree.getZTreeObj("treeRm");
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length > 0) {
		treeObj.reAsyncChildNodes(nodes[0].getParentNode(), "refresh");
	}
}
// 刷新树节点
function refreshTreeNode() {
	var treeObj = $.fn.zTree.getZTreeObj("treeRm");
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length > 0) {
		// alert(nodes[0].name);
		treeObj.reAsyncChildNodes(nodes[0], "refresh");
	}
}
// 根据已选中的树节点id，分别节点类型，获取节点Id
function getSelectNodeId() {
	var treeObj = $.fn.zTree.getZTreeObj("treeRm");
	var nodes = treeObj.getSelectedNodes();
	var node_id = nodes[0].id;
	var obj_id = null;
	if (node_id.indexOf("center") > 0)
		obj_id = node_id.substring(0, node_id.length - 6);
	else if (node_id.indexOf("pool") > 0)
		obj_id = node_id.substring(0, node_id.length - 4);
	else if (node_id.indexOf("cdp") > 0)
		obj_id = node_id.substring(0, node_id.length - 3);
	else if (node_id.indexOf("cluster") > 0)
		obj_id = node_id.substring(0, node_id.length - 7);
	else if (node_id.indexOf("host") > 0)
		obj_id = node_id.substring(0, node_id.length - 4);
	else if (node_id.indexOf("vm") > 0)
		obj_id = node_id.substring(0, node_id.length - 7);
	return obj_id;
}
// 根据已选中的树节点id，获取节点名称
function getSelectNodeName() {
	var treeObj = $.fn.zTree.getZTreeObj("treeRm");
	var nodes = treeObj.getSelectedNodes();
	return nodes[0].name;
}
function hideAllBtn() {
	$("input[id^='btn_']").hide();
}

//四舍五入的方法       how 要保留的小数位数     dight 要四舍五入的数值
function   ForDight(Dight,How)  
{  
            Dight   =   Math.round   (Dight*Math.pow(10,How))/Math.pow(10,How);  
            return   Dight;  
}  

//显示仪表的方法
function echartMeterDic(nodeId){
	var dataCenterId,bClassId,cClassId;
	dataCenterIndex = nodeId.indexOf("dataCenterId:");
	bClassIdIndex = nodeId.indexOf("bClassId:");
	cClassIdIndex = nodeId.indexOf("cClassId:");
	if(dataCenterIndex >= 0){
		dataCenterId = nodeId.substr(13);
	}
	if(bClassIdIndex >= 0){
		bClassId = nodeId.substr(9);
	}
	if(cClassIdIndex >= 0){
		cClassId = nodeId.substr(9);
	}
	var url = ctx+"/network/findNetworkAbstract.action";
	$.ajax({
		async : false,
		cache : false,
		type : "post",
		datatype : "json",
		url : url,  
		data : {"dataCenterId":dataCenterId,"bClassId":bClassId,"cClassId":cClassId},
		error : function() {//请求失败处理函数
			showError(i18nShow('tip_req_fail'));
		},
		success : function(data) { //请求成功后处理函数。 
			if(dataCenterIndex >= 0){
				$('#subPoolCount').val(data.bCount);
			}
			if(bClassIdIndex >= 0){
				$('#subPoolCount').val(data.cCount);
			}
			if(cClassIdIndex >= 0){
				$('#subPoolCount').val(data.poolCount);
			}
			var usableipNum ,ipNum ,ipPct ,usedipNum ;
			usableipNum = data.usableipNum ;
			ipNum = data.ipNum ;
			usedipNum = ForDight(ipNum-usableipNum,0) ;
			$("#usableipNum").val(usableipNum);
			$("#ipNum").val(ipNum);
			$("#usedipNum").val(usedipNum);
			if(data!='' || data!=null){
			// 路径配置
		     require.config({
		         paths: {
		             echarts:ctx + "/common/echart/"
		         }
		     });
		     // 使用
		     require(
		                [
		                    "echarts",
		                    "echarts/chart/gauge"
		                ],
		                function (ec) {
		                	if(usableipNum == 0){
		                		ipPct = 0 ;
		                	}else{
		                		ipPct = ((usedipNum/ipNum)*100).toFixed(2);
		                	}
		                	var myChart = ec.init(document.getElementById("echart_Network_div"));
		                    var ipOption = {
		                    		   tooltip : {
		                    		        formatter: "{a} <br/>{b} : {c}%"
		                    		    },
		                    		    series : [
		                    		        {
		                    		            name:i18nShow('rm_nw_res_pool_Utilization_ratio'),
		                    		            type:'gauge',
		                    		            radius: "100%", //仪表大小
		                    		            splitNumber: 1,        //分割段数，默认为5
		                    		            axisLine: {            //坐标轴线
		                    		                lineStyle: {       //属性lineStyle控制线条样式
		                    		                    color: [[0.2, '#B6A2DE'],[0.8, '#38C0A2'],[1, '#F47279']], 
		                    		                    width: 8
		                    		                }
		                    		            },
		                    		            axisTick: {           //坐标轴小标记
		                    		                splitNumber: 10,  //每份split细分多少段
		                    		                length :13,        //属性length控制线长
		                    		                lineStyle: {      //属性lineStyle控制线条样式
		                    		                    color: 'auto'
		                    		                }
		                    		            },
		                    		            axisLabel: {           //坐标轴文本标签
		                    		                textStyle: {       //其余属性默认使用全局文本样式
		                    		                    color: 'auto',
		                    		                    fontSize:10
		                    		                }
		                    		            },
		                    		            splitLine: {           //分隔线
		                    		                show: true,        //默认显示，属性show控制显示与否
		                    		                length :10,        //属性length控制线长
		                    		                lineStyle: {       //属性lineStyle（详见lineStyle）控制线条样式
		                    		                    color: 'auto'
		                    		                }
		                    		            },
		                    		            pointer : {
		                    		                width : 3
		                    		            },
		                    		            title : {
		                    		                show : true,
		                    		                offsetCenter: [0, '-30%'],//x, y，单位px
		                    		                textStyle: {              //其余属性默认使用全局文本样式
		                    		                    fontWeight: 'bolder'
		                    		                }
		                    		            },
		                    		            detail : {
		                    		                formatter:'{value}%',
		                    		                textStyle: {       //其余属性默认使用全局文本样式
		                    		                    color: 'auto',
		                    		                    fontSize:16,
		                    		                    fontWeight: 'bolder'
		                    		                }
		                    		            },
		                    		            data:[{value: ipPct, name: 'IP'}]
		                    		        }
		                    		    ]
		                    	};
		                        // 为echart对象加载数据 
		                        myChart.setOption(ipOption,true);
		                        $('#echart_Network_div').append(
		                        		"<div class='gaugeDetail'><table class='ipTable'><tr class='ipTr'><td class='ipTd' style='width:90px;color:#888'><nobr>"+i18nShow('rm_nw_res_pool_ip_total')+":&nbsp;<br/><span style='font-size:19px;color:#444'>"+ipNum+"</span></nobr></td></tr>"+
		                		        "<tr class='ipTr'><td class='ipTd' style='width:90px;color:#888'><nobr>"+i18nShow('rm_nw_res_pool_ip_free')+":&nbsp;<br/><span style='font-size:19px;color:#444'>"+usableipNum+"</span></nobr></td></tr><table></div>"
		                        );
		                        $(".ipTable").css('margin-left','29px');
		                        $(".ipTable").css('margin-right','0px');
		                        $(".ipTable").css('margin-top','-20px');
		     		            $(".ipTable").css('margin-bottom','20px');
		                     }
		                  );
		}}
	});
}
//根据平台类型选择openstack  显示相关属性 
function openstackShow(){
	$("#physicalNetworkSpan").attr("style","display: none");
	var om1=$("#Openstack_message1");
	var om2=$("#Openstack_message2");
	var om3=$("#Openstack_message3");
	var om4=$("#Openstack_message4");
	var om5=$("#isExternalNet_p");
	var om6=$("#virtualRouterSpan");
	om1.css("display","none");
	om2.css("display","none");
	om3.css("display","none");
	om5.css("display","none");
	om6.css("display","none");
	$("#c_VmMsId").val("");
	$("#c_vpcId").val("");
	$("#c_virtualRouterId").val("");
	$("#u_platformId").change(function(){
		$("#physicalNetwork").empty();
		var val=this.value;
		if(val=='4' || val=='5'){
			om1.css("display","block");
			om2.css("display","block");
			om3.css("display","block");
			om4.css("display","none");
			om5.css("display","block");
			om6.css("display","block");
			$("#c_VmMsId").val("");
			$("#c_vpcId").val("");
			$("#c_virtualRouterId").val("");
			$("#c_vpcId").empty(); 
			$("#c_virtualRouterId").empty();
			initVirtualRouterSelect();
			$("#c_VmMsId").change(function(){
				initVpcSelect();
				$("#c_virtualRouterId").empty();
				$("#c_vpcId").change(function(){
					initVirtualRouterSelect()
				});
			});
		}else{
			$("#physicalNetworkSpan").attr("style","display: none");
			om1.css("display","none");
			om2.css("display","none");
			om3.css("display","none");
			om4.css("display","block");
			om5.css("display","none");
			om6.css("display","none");
		}
	});
}
function openstackShow1(){
	var om1=$("#Openstack_message1");
	var om2=$("#Openstack_message2");
	var om3=$("#Openstack_message3");
	var om4=$("#Openstack_message4");
	var om5=$("#isExternalNet_p");
	$("#u_platformId").change(function(){
		var val=this.value;
		if(val=='4' || val=='5'){
			om1.css("display","block");
			om2.css("display","block");
			om3.css("display","block");
			om4.css("display","none");
			om5.css("display","block");
			$("#c_VmMsId").val("");
			$("#c_vpcId").empty(); 
			$("#c_virtualRouterId").empty();
			$("#c_VmMsId").change(function(){
				initVpcSelect();
				$("#c_virtualRouterId").empty();
				$("#c_vpcId").change(function(){
					initVirtualRouterSelect()
				});
			});
			$("#networkType").val("");
			$("#u_vlanId1").val("");
		}else{
			om1.css("display","none");
			om2.css("display","none");
			om3.css("display","none");
			om4.css("display","block");
			om5.css("display","none");
		}
	});
}
//显示vpc名称 的下拉框
function initVpcSelect()
{
	var pl_datacenter_id =$("#pl_datacenter_id").val();
	var c_VmMsId=$("#c_VmMsId").val();
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/network/onDVgetVpcIdAndName.action",
		async : false,
		data:{'projectVpcPo.datacenterId':pl_datacenter_id,'projectVpcPo.vmMsId':c_VmMsId},
		success:(function(data){
			$("#c_vpcId").empty();
			$("#c_vpcId").append("<option value='' selected>"+i18nShow('com_select_defalt')+"...</option>");
			$(data).each(function(i,item){
				$("#c_vpcId").append("<option value='"+item.vpcId+"'>"+item.vpcName+"</option>");
			});			
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			showTip(i18nShow('error_rm_nw_res_pool_vpc'));
		} 
	});
}
//显示虚拟路由器名称的下拉框
function initVirtualRouterSelect()
{
	var vpcId = $("#c_vpcId").val();
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/network/onVpcgetVrIdAndName.action",
		async : false,
		data:{'projectVpcPo.vpcId':vpcId},
		success:(function(data){
			$("#c_virtualRouterId").empty();
			$("#c_virtualRouterId").append("<option value='' selected>"+i18nShow('com_select_defalt')+"...</option>");
			$(data).each(function(i,item){
				$("#c_virtualRouterId").append("<option value='" + this.virtualRouterId + "'>" + this.virtualRouterName + "</option>");
			});			
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			showTip(i18nShow('error_rm_nw_res_pool_router'));
		} 
	});
}
//取消路由器网络virtualRouterId,vmMsId,vpcId,resPoolId
function cancalRouter(virtualRouterId,vmMsId,vpcId,resPoolId){
	showTip(i18nShow('tip_rm_nw_res_pool_remove_router'),function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url : ctx + "/network/cancalRouter.action",
			async : false,
			data:{"rmNwResPoolFullVo.virtualRouterId":virtualRouterId,"rmNwResPoolFullVo.vmMsId":vmMsId,
				"rmNwResPoolFullVo.vpcId":vpcId,"rmNwResPoolFullVo.resPoolId":resPoolId},
			success:(function(data){
				closeTip();
				showTip(i18nShow('tip_rm_nw_res_pool_router1'));
				loadNwPoolData();
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				closeTip();
				showTip(i18nShow('tip_rm_nw_res_pool_router2'));
			} 
		});
		});
}
//加入路由器网络
function addRouter(virtualRouterId,vmMsId,vpcId,resPoolId){
	//alert(vpcId);
	showTip(i18nShow('tip_rm_nw_res_pool_add_router'),function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url : ctx + "/network/addRouter.action",
			async : false,
			data:{"rmNwResPoolFullVo.virtualRouterId":virtualRouterId,"rmNwResPoolFullVo.vmMsId":vmMsId,
				"rmNwResPoolFullVo.vpcId":vpcId,"rmNwResPoolFullVo.resPoolId":resPoolId},
			success:(function(data){
				closeTip();
				showTip(i18nShow('tip_rm_nw_res_pool_router3'));
				loadNwPoolData();
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				closeTip();
				showTip(i18nShow('tip_rm_nw_res_pool_router4'));
			} 
		});
		});
}

function getIPInfo(){
	//alert(vpcId);
	var ip = $("#c_cclassName2").val()+$("#c_end").val();
	var maskBit = $("#c_mask").val();
		$.ajax({
			type:'post',
			datatype : "json",
			url : ctx + "/network/getIPInfo.action",
			async : false,
			data:{"ip":ip,"maskBit":maskBit},
			success:(function(data){
				$("#c_ipStart").val(data.start);
				$("#c_ipEnd").val(data.end);
				$("#u_subnetmask").val(data.mask);
				//document.getElementById('u_cclassName').innerHTML = data.subnet;
				$("#u_cclassName").val(data.subnet);
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip(i18nShow('tip_error'));
			} 
		});
}
function closeView(idDiv){
	$("#"+idDiv).dialog("close");
}
