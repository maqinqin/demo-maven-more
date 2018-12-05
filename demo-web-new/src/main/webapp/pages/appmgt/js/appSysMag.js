var method="";
function load(){
	//只能输入小写字母和数字
	jQuery.validator.addMethod("chrnum", function(value, element) {   
		var chrnum = /^([a-z0-9]+)$/;   
		return this.optional(element) || (chrnum.test(value));   
		}, "只能输入数字和小写字母(字符a-z, 0-9)");   
	
	//验证英文
	jQuery.validator.addMethod("stringCheck",function(value, element) {return this.optional(element) || /^[a-z0-9A-Z_\s-\.]*$/g.test(value);},
								"只能入英文字母和数字"); 
	
	//验证服务器角色英文名称不能重复
	jQuery.validator.addMethod("checkDuEname", function(value, element) {
		var validateValue = true;
		var method = $("#duMethod").val();
		$.ajax({
			type : 'post',
			datatype : "json",
			data : {
				"du_ename":value,
         	    "du_appId":$("#du_appId").val(),
         	    "du_id":$("#du_id").val()
			},
			url: ctx+"/appmgt/deploy-unit/checkDuEname.action",
			async : false,
			success : (function(data) {
				if (method == "modify") {
					if (data == true) {
						validateValue = true;
					} else {
						validateValue = false;
					}
	
				} else {
					if (data == true) {
						validateValue = true;
					} else {
						validateValue = false;
					}
				}
			}),
		});
		return this.optional(element) || validateValue;
	}, "服务器角色英文名称不能重复"); 

	//验证应用系统英文名称不能重复
	jQuery.validator.addMethod("checkAppInfoEname", function(value, element) {
		var validateValue = true;
		var method = $("#addAppInfoMethod").val();
		$.ajax({
			type : 'post',
			datatype : "json",
			data : {
				"ename":value
			},
			url: ctx+"/appmgt/application/checkEname.action",
			async : false,
			success : (function(data) {
				if (method == "modify") {
					var enameHidden = $("#enameHidden").val();
					if (data==null || data.ename == enameHidden) {
						validateValue = true;
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
	}, "应用系统英文名称不能重复"); 
	
	//验证应用系统中文名称不能重复
	jQuery.validator.addMethod("checkAppInfoCname", function(value, element) {
		var validateValue = true;
		var method = $("#addAppInfoMethod").val();
		$.ajax({
			type : 'post',
			datatype : "json",
			data : {
				"cname":value
			},
			url: ctx+"/appmgt/application/checkCname.action",
			async : false,
			success : (function(data) {
				if (method == "modify") {
					var cnameHidden = $("#cnameHidden").val();
					if (data== null || data.cname == cnameHidden) {
						validateValue = true;
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
	}, "应用系统中文名称不能重复"); 
	
}
$(function() {
		/*单击节点后，右侧DIV业务表现的方法*/
		var myBizFun = function() {
			if (bizType == "app") {
				getAppSys(nodeId);
			} else if (bizType == "du") {
				var node = zTree.getNodeByParam("id", nodeId, null);
				getDeployUnit(nodeId);
			}else{
				selectByValue("newfatherId","0");
				$("#hidenAppSysId").val("0");
				$("#hidenfatherId").val("0");
				$("#isSubAppSys").val("0");
				$("#isAppDu").val("0");
				$("#isBeUse").val("0");
				$("#hid_icon").val(iconPath+'appsys.png');
				$("#hidNodeId").val(nodeId);
				initDiv("iconDiv");
			}
		};

		/**
		 * 异步加载节点定义方法
		 */
		
		var myExpandFun = function(){	
			var currentNode = zTree.getNodeByParam("id",nodeId,null);
			zTree.removeChildNodes(currentNode);
			var searchForName = $("#name").val();
			ajaxCall("/appmgt/application/getAppSysTreeList.action",{"appSysPO.appId":nodeId,"appSysPO.cname":searchForName},
						asyncAddNode
			)
		};			
		/*注册方法*/
		regFunction(myBizFun,myExpandFun);

		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : ctx + "/appmgt/application/getAppSysTreeList.action",//请求的action路径   
			data : {"appSysPO.appId" : "","appSysPO.cname":""},
			error : function() {//请求失败处理函数   
				showError(i18nShow('tip_req_fail'),null,"red");
			},
			success : function(data) { //请求成功后处理函数。    
				zTreeInit("treeRm", data);
				//选择第一个应用系统节点并返回ID
				var treeObj =zTree;
			    node=treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);
			    initDiv("app_mag_tab");
			    $("#hidNodeId").val("node.level:"+node.level);
			    treeObj.selectNode(node);
			}
		});
		
		
		
		
		//服务器角色页面的验证
		$("#add_update_AppDu_Form").validate({
			rules: {
				du_datacenterId: "required",
				du_appId: "required",
				du_cname: {required:true,maxlength:100},
				du_fullCname: {required:true,maxlength:100},
				du_ename: {required:true,
						   checkDuEname:true,
			               rangelength:[3,6],
			               chrnum:true
				},
				du_fullEname:{maxlength:100,stringCheck:true},
				du_serviceTypeCode: "required",
				du_status: "required",
				du_secureAreaCode: "required",
				/*du_sevureTierCode: "required",*/
				du_fullEname:{required:true,
					   checkDuEname:true,
		               rangelength:[3,6],
		               chrnum:true},
				du_remark:{maxlength:500}
			},
			messages: {
				du_datacenterId: i18nShow('validate_data_required'),
				du_appId: i18nShow('validate_data_required'),
				du_cname: {required:i18nShow('validate_data_required'),maxlength:i18nShow('val_l_app_length_100')},
				du_fullCname: {required:i18nShow('validate_data_required'),maxlength:i18nShow('val_l_app_length_100')},
				du_ename: {required:i18nShow('validate_data_required'),checkDuEname:i18nShow('validate_data_remote'),rangelength:i18nShow('val_l_app_length_3-6'),chrnum:i18nShow('val_l_app_letter_num')},
				du_fullEname:{required:i18nShow('validate_data_required'),maxlength:i18nShow('val_l_app_length_100'),stringCheck:i18nShow('val_l_app_letter_num1'),rangelength:i18nShow('val_l_app_length_3-6')},
				du_serviceTypeCode: i18nShow('validate_data_required'),
				du_status: i18nShow('validate_data_required'),
				du_secureAreaCode: i18nShow('validate_data_required'),
				/*du_sevureTierCode: "安全层不能为空",*/
				du_remark:{maxlength:i18nShow('val_l_app_remark_long')}
			},
			submitHandler: function() {
				saveAppDu();
			}
		});
		
		//应用系统修改不为空校验
		$("#update_AppInfo_Form").validate({
			rules:{
					fatherId: "required",
					cname: {required:true,
						checkAppInfoCname:true,
							maxlength:100},
					fullCname: {required:true,maxlength:100},
					ename:{required:true,
						checkAppInfoEname:true,
				           rangelength:[3,6],
				           chrnum:true
				          },
				    manager: {
				    	maxlength:32
				    },
				    fullEname:{maxlength:100,stringCheck:true},
				    sysLevelCode: "required",
				    remark:{maxlength:500}
			      },
			messages: {
				fatherId: i18nShow('validate_data_required'),
				cname: {required:i18nShow('validate_data_required'),checkAppInfoCname:i18nShow('validate_data_remote'),maxlength:i18nShow('val_l_app_length_100')},
				fullCname: {required:i18nShow('validate_data_required'),maxlength:i18nShow('val_l_app_length_100')},
				ename: {required:i18nShow('validate_data_required'),checkAppInfoEname:i18nShow('validate_data_remote'),rangelength:i18nShow('val_l_app_length_3-6'),chrnum:i18nShow('val_l_app_letter_num')},
				manager:{maxlength:i18nShow('val_l_app_length_32')},
				fullEname:{maxlength:i18nShow('val_l_app_length_100'),stringCheck:i18nShow('val_l_app_letter_num1')},
				sysLevelCode:i18nShow('validate_data_required'),
				remark:{maxlength:i18nShow('val_l_app_remark_long')}
			},
			submitHandler: function() {
				saveupdateAppInfo();
			}
		});
		//应用系统新增不为空校验
		$("#add_AppInfo_Form").validate({
			rules: {
				newfatherId: "required",
				newcname: {required:true,
					checkAppInfoCname:true,
							maxlength:100},
				newfullCname: {required:true,maxlength:100},
				newename:{required:true,
						  checkAppInfoEname:true,
	               		  rangelength:[3,6],
	               		  chrnum:true
	             },
	            newmanager: {
				    	maxlength:32
				    },
	            newfullEname:{maxlength:100,stringCheck:true},
				newsysLevelCode: "required",
				newremark:{maxlength:500}
			},
			messages: {
				newfatherId: i18nShow('validate_data_required'),
				newcname: {required:i18nShow('validate_data_required'),checkAppInfoCname:i18nShow('validate_data_remote'),maxlength:i18nShow('val_l_app_length_100')},
				newfullCname: {required:i18nShow('validate_data_required'),maxlength:i18nShow('val_l_app_length_100')},
				newename: {required:i18nShow('validate_data_required'),checkAppInfoEname:i18nShow('validate_data_remote'),rangelength:i18nShow('val_l_app_length_3-6'),chrnum:i18nShow('val_l_app_letter_num')},
				newmanager: {maxlength:i18nShow('val_l_app_length_32')},
				newfullEname:{maxlength:i18nShow('val_l_app_length_100'),stringCheck:i18nShow('val_l_app_letter_num1')},
				newsysLevelCode:i18nShow('validate_data_required'),
				newremark:{maxlength:i18nShow('val_l_app_remark_long')}
			},
			submitHandler: function() {
				saveNewAppInfo();
			}
		});
		
		$("#du_secureAreaCode").change(function() {
			initSevureTierSelect(this.value);
		});

	});
	
	function openDialog(divId, title, width, height) {
		$("#" + divId).dialog({
			autoOpen : true,
			modal : true,
			height : height,
			width : width,
			title : title,
			
		})
	}

	function closeView(divId) {
		$("label.error").remove();
		$("#" + divId).dialog("close");
	}
	
	/**
	 * 根据ID获取应用系统
	 */
	function getAppSys(nodeId){
		$.ajax({
			async : false,
			cache : false,
			url : ctx + "/appmgt/application/initAppSys.action",
			data : {"nodeId" : nodeId},
			dataType : "json",
			type:'post',
			success : function(datas){
				initAppInfo(datas);
			}
		});
	}
	
	/**
	 * 根据ID获取服务器角色
	 * @param nodeId
	 */
	function getDeployUnit(nodeId){
		$.ajax({
			async : false,
			cache : false,
			url : ctx + "/appmgt/deploy-unit/getDeployUnitById.action",
			data : {"deployUnitVo.duId":nodeId},
			type:'post',
			dataType : "json",
			success : function(datas){
				initDeployUnitInfo(datas);
			}
		});
	}

	/**
	 * 初始化应用系统查看页面
	 * @param datas
	 */
	function initAppInfo(datas) {
		//应用系统查看赋值
		$("#lbl_AppInfo_Father").text(datas.fatherCname);
		$("#lbl_AppInfo_Cname").text(datas.cname);
		$("#lbl_AppInfo_Full_Cname").text(datas.fullCname);
		$("#lbl_AppInfo_Ename").text(datas.ename);
		$("#lbl_AppInfo_Full_Ename").text(datas.fullEname);
		$("#lbl_AppInfo_Manager").text(datas.manager);
		$("#lbl_AppInfo_SysLevel").text(datas.sysLevelCode);
		$("#lbl_AppInfo_Remark").text(datas.remark);
		//保留appId值
		$("#hidenAppSysId").val(datas.appId);
		$("#du_id").val("");
		$("#hidenfatherId").val(datas.fatherId);
		$("#isSubAppSys").val(datas.isSubAppSys);
		$("#isAppDu").val(datas.isAppDu);
		$("#isBeUse").val(datas.isBeUse);
		$("#hid_icon").val(iconPath+'appsys.png');
		//修改赋初值
		selectByValue("fatherId",datas.fatherId);
		$("#cname").val(datas.cname);
		$("#cnameHidden").val(datas.cname);
		$("#fullCname").val(datas.fullCname);
		$("#ename").val(datas.ename);
		$("#enameHidden").val(datas.ename);
		$("#fullEname").val(datas.fullEname);
		$("#manager").val(datas.manager);
		selectByValue("sysLevelCode",datas.sysLevelCode);
		$("#remark").val(datas.remark);
		//新增
		selectByValue("newfatherId",datas.appId);
		$("#hidNodeId").val("apptACPId:"+datas.appId);
		initDiv("appInfoView");
	}
	
	/**
	 * 初始化服务器角色查看页面
	 * @param datas
	 */
	function initDeployUnitInfo(datas) {
		$("#hid_icon").val(iconPath+'du.png');
		$("#lbl_AppDu_DataCenter").text(datas.datacenterName);
		$("#lbl_AppDu_App").text(datas.appCname);
		$("#lbl_AppDu_Cname").text(datas.cname);
		$("#lbl_AppDu_Full_Cname").text(datas.fullCname);
		$("#lbl_AppDu_Ename").text(datas.ename);
		$("#lbl_AppDu_Full_Ename").text(datas.fullEname);
		$("#lbl_AppDu_Service_Type").text(datas.serviceTypeName);
		if(datas.statusName=='启用'){
			$("#lbl_AppDu_Status").html("<span class='tip_green'>"+i18nShow('cloud_service_start')+"</span>");
		}else{
			$("#lbl_AppDu_Status").html("<span class='tip_red'>"+i18nShow('cloud_service_stop1')+"</span>");
		}
		$("#lbl_AppDu_SecureArea").text(datas.secureAreaName);
		$("#lbl_AppDu_SevureTier").text(datas.sevureTierName);
		$("#lbl_AppDu_Remark").text(datas.remark);
		
		selectByValue("du_datacenterId",datas.datacenterId);
		selectByValue("du_appId",datas.appId);
		$("#du_cname").val(datas.cname);
		$("#du_fullCname").val(datas.fullCname);
		$("#du_ename").val(datas.ename);
		$("#du_fullEname").val(datas.fullEname);
		selectByValue("du_serviceTypeCode",datas.serviceTypeCode);
		selectByValue("du_status",datas.status);
		
		$("#du_statusDiv").remove();
		$("#du_statusSpan").append('<span class="switch-off"  id="du_statusDiv"style="position: relative;top: 8px;"></span>');
		if(datas.status == "Y"){
			$("#du_statusDiv").removeClass("switch-off").addClass("switch-on");
		}else {
			$("#du_statusDiv").removeClass("switch-on").addClass("switch-off");
			
		}
		honeySwitch.init();
		
		selectByValue("du_secureAreaCode",datas.secureAreaCode);
		initSevureTierSelect(datas.secureAreaCode);
		$("#du_sevureTierCode").val(datas.sevureTierCode);
		$("#du_remark").val(datas.remark);
		$("#du_oldAppId").val(datas.appId);
		$("#du_id").val(datas.duId);
		$("#hidNodeId").val("apptADuId:"+datas.duId);
		initDiv("appDuView");
	}
	
	/**
	 * 隐藏DIV
	 * @param divId
	 */
	function hideDiv(divId){
		$("#"+divId).css('display','none');
	}
	
	/**
	 * 显示DIV
	 * @param divId
	 */
	function showDiv(divId){
		$("#" + divId).fadeIn('fast');
		//$("#"+divId).css('display','block');
	}

	/**
	 * 新增应用系统
	 * @return
	 */
	function saveNewAppInfo() {
		$.ajax({
			type : 'post',
			datatype : "json",
			url : ctx + "/appmgt/application/addAppSys.action",
			async : false,
			data : {//获得表单数据
				'appSysPO.fatherId' : $("#newfatherId").val(),
				'appSysPO.cname' : $("#newcname").val(),
				'appSysPO.fullCname' : $("#newfullCname").val(),
				'appSysPO.ename' : $("#newename").val(),
				'appSysPO.fullEname' : $("#newfullEname").val(),
				'appSysPO.manager' : $("#newmanager").val(),
				'appSysPO.sysLevelCode' : $("#newsysLevelCode").val(),
				'appSysPO.remark' : $("#newremark").val() 
			},
			success : (function(data) {
				if(data.resultFlag=="0"){
					showTip(data.errormsg,null,"red");
				}else{
					addSelectInfo(data.appId,$("#newcname").val(),"add");
					closeView('add_AppInfo_Div');
					addNode(data.appId,$("#newcname").val(),$("#hid_icon").val(),null,false,"app",true,true,$("#newfatherId").val());
					getAppSys(data.appId);
					showTip(i18nShow('add_suceess_ahuo'));
				}
			}),
			error : function() {
				showTip(i18nShow('tip_error'),null,"red");
			}
		});
	}

	/**
	 * 更新应用系统
	 * @return
	 */
	function saveupdateAppInfo() {
		var appId = $("#hidenAppSysId").val();
		var newFatherId = $("#fatherId").val();
		var oldFatherId = $("#hidenfatherId").val();
		var cname = $("#cname").val();
		$.ajax({
			type : 'post',
			datatype : "json",
			url : ctx + "/appmgt/application/updateAppSys.action",
			async : false,
			data : {//获得表单数据
				'appSysPO.appId' : appId,
				'appSysPO.fatherId' : newFatherId,
				'appSysPO.cname' : cname,
				'appSysPO.fullCname' : $("#fullCname").val(),
				'appSysPO.ename' : $("#ename").val(),
				'appSysPO.fullEname' : $("#fullEname").val(),
				'appSysPO.manager' : $("#manager").val(),
				'appSysPO.sysLevelCode' : $("#sysLevelCode").val(),
				'appSysPO.remark' : $("#remark").val() 
			},
			success : (function(data) {
				modifyNode(appId,cname);
				if(newFatherId!=oldFatherId){
					moveTreeNode($("#fatherId").val(),$("#hidenAppSysId").val(),"inner");
				}
				addSelectInfo(appId,cname,"update");
				getAppSys(appId);
				closeView('update_AppInfo_Div');
				showTip(data.otherContent,null,"red");
			}),
			error : function() {
				showTip(i18nShow('tip_error'),null,"red");
			}
		});
	}

	/**
	 * 删除应用
	 * @return
	 */
	function delAppSys() {
		$.ajax({
					type : 'post',
					datatype : "json",
					url : ctx + "/appmgt/application/delAppSys.action",
					async : false,
					data : {//获得表单数据-替换假数据 '1','2','3'
						'appIds' : $("#hidenAppSysId").val()
					},
					success : (function(data) {
						addSelectInfo($("#hidenAppSysId").val(),"","del");
						deleteTreeNode($("#hidenAppSysId").val());
						showTip(data.otherContent,null,"red");
					}),
					error : function() {
						showTip(i18nShow('tip_error'),null,"red");
					}
				});
	}
	
	/**
	 * 添加应用系统按钮
	 */
	function addAppInfo() {
		$("label.error").remove();
		load();
		$("#addAppInfoMethod").val("add");
		var isAppDu = $("#isAppDu").val();
		if(isAppDu=="1"){
			showTip(i18nShow('tip_l_app_save'),null,"red");
			return;
		}
		$("#hid_icon").val(iconPath+'appsys.png');
		openDialog('add_AppInfo_Div',i18nShow('l_app_save'),654,300);
		clearAppSys();
	}
	/**
	 * 添加服务器角色按钮
	 */
	function addAppDu() {
		load();
		$("label.error").remove();
		var isSubAppSys= $("#isSubAppSys").val();
		if(isSubAppSys=="1"){
			showTip(i18nShow('tip_l_app_du_save'),null,"red");
			return;
		}
		
		method="add";
		$("#duMethod").val(method);
		$("#hid_icon").val(iconPath+'du.png');
		clearAppDu($("#hidenAppSysId").val());
		$("#du_id").val("");
		
		openDialog('add_update_AppDu_Div',i18nShow('l_app_du_save'),654,420);
		$("#du_datacenterId").removeAttr("disabled");
		$("#du_serviceTypeCode").removeAttr("disabled");
		$("#du_secureAreaCode").removeAttr("disabled");
		$("#du_sevureTierCode").removeAttr("disabled");
	}
	
	function searchTree() {
		var zTree = $.fn.zTree.getZTreeObj("treeRm");
		var searchForName = $("#name").val();
		var nodes = zTree.getNodesByParamFuzzy("name",searchForName,null);//按名字取节点
		var allNodes = zTree.transformToArray(zTree.getNodes());
		if (searchForName=="") {
			zTree.showNodes(allNodes);
			return;
		}
		zTree.hideNodes(allNodes);//先隐藏
		if(nodes.length==0){
			return;
		}
		var parentNodes = [];
		//获取父节点
		for(var i=0;i<nodes.length;i++){
			var node=nodes[i].getParentNode();
			while(node!=null){
				parentNodes.push(node);
				node = node.getParentNode();
			}
		}
		var showNode = parentNodes.concat(nodes);
		zTree.showNodes(showNode);
		zTree.expandAll(true);
	}
	
	function searchTreeByCname() {
		var searchForName = $("#name").val().replace(/(^\s*)|(\s*$)/g, "");
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : ctx + "/appmgt/application/getAppSysTreeListByCname.action",//请求的action路径   
			data : {"appSysPO.appId" : nodeId,"appSysPO.cname" : searchForName},
			error : function() {//请求失败处理函数   
				showTip(i18nShow('tip_req_fail'),null,"red");
			},
			success : function(data) { //请求成功后处理函数。     
				zTreeInit("treeRm", data);
			}
		});
	}
	
	function modifyAppInfo(){
		$("label.error").remove();
		load();
		$("#addAppInfoMethod").val("modify");
		var isSubAppSys = $("#isSubAppSys").val();
		var isAppDu = $("#isAppDu").val();
		var isBeUse = $("#isBeUse").val();
		getAppSys($("#hidenAppSysId").val());
		//if(isBeUse=="1"){
			//showTip("系统(子系统)正在被其他模块使用，不允许修改！",null,"red");
			//return;
		//}
		//if(isSubAppSys=="1" || isAppDu=="1"){
			//$("#sysLevelCode").prop("disabled","disabled");
		//}else{
			//$("#sysLevelCode").prop("disabled","");
		//}
		openDialog('update_AppInfo_Div',i18nShow('l_app_update'),654,300);
	}
	
	function cencelAppInfo(){
		showDiv("view_AppInfo_Div");
		hideDiv("update_AppInfo_Div");
		hideDiv("add_AppInfo_Div");
	}
	
	function checkDelAppInfo(){
		showTip(i18nShow('tip_l_app_delete'),function(){
			var isSubAppSys = $("#isSubAppSys").val();
			var isAppDu = $("#isAppDu").val();
			var isBeUse = $("#isBeUse").val();
			if(isAppDu=="1"){
				showError(i18nShow('tip_l_app_delete_msg'),null,"red");
				return;
			}
			if(isSubAppSys=="1" || isAppDu==""){
				showTip(i18nShow('tip_l_app_delete_msg1'),null,"red");
				return;
			}
			delAppSys();
		});
	}
	
	/**
	 * 修改服务器角色
	 */
	function modifyAppDu(){
		$("label.error").remove();
		load();
		method="modify";
		$("#duMethod").val(method);
		var duId=nodeId;
		getDeployUnit(duId);
		$.ajax({
			async : false,
			cache : false,
			url : ctx + "/appmgt/deploy-unit/deployUnitCheck.action",
			data : {"deployUnitVo.duId":duId},
			type:'post',
			dataType : "json",
			success : function(datas){
//				if(datas.message=="该服务器角色已被使用!"){
//					showTip(datas.message,null,"red");
//					return;
//				}else 
				if(datas.message=="该服务器角色下存在虚拟机!"){
					setAppDU("update");
				}else{
					setAppDU("");
					$("#du_appId").attr("disabled","disabled");
				}	
				openDialog('add_update_AppDu_Div',i18nShow('l_app_du_update'),654,460);
			}
		});
		
	}
	
	/**
	 * 删除服务器角色
	 */
	function deleteAppDu(){
		showTip(i18nShow('tip_delete_confirm'),function(){
			var duId=nodeId;
			$.ajax({
				async : false,
				cache : false,
				url : ctx + "/appmgt/deploy-unit/deleteDeployUnit.action",
				data : {"deployUnitVo.duId":duId},
				type:'post',
				dataType : "json",
				success : function(datas){
					if(datas.message=="删除成功!"){
						showTip(i18nShow('tip_delete_success'));
						deleteTreeNode(nodeId);
					}else{
						showError(datas.message);
					}
				}
			});
		});
	}
	
	function saveUpdateAppDu(){
		var tiercode = $.trim($("#du_sevureTierCode").val());
		var option_count = 0;
		$("#du_sevureTierCode option").each(function() {    
			option_count++;
		});  
//		if(option_count > 1 && tiercode == ""){
//			showTip("请您选择一个安全层");
//		}else{
			$("#add_update_AppDu_Form").submit();  
//		}
	}

	/**
	 * 保存服务器角色
	 */
	function saveAppDu(){
		setAppDU("");
		var duId=nodeId;
		var datacenterId=$("#du_datacenterId").val();
		var appId=$("#du_appId").val();
	
		
		var fullCname=$("#du_fullCname").val();//中文全称
		var fullEname=$("#du_fullEname").val();//英文全称
		var cname=fullCname;//中文简称
		var ename=fullEname;//英文简称
		//var serviceTypeCode=$("#du_serviceTypeCode").val();
		var status=$("#du_status").val();
		var secureAreaCode=$("#du_secureAreaCode").val();
		//var sevureTierCode=$("#du_sevureTierCode").val();
		var remark=$("#du_remark").val();
		var oldAppId=$("#du_oldAppId").val();
		var dataObj={
				"deployUnitVo.datacenterId":datacenterId,
				"deployUnitVo.appId":appId,
				"deployUnitVo.duId":duId,
				"deployUnitVo.cname":cname,
				"deployUnitVo.fullCname":fullCname,
				"deployUnitVo.ename":ename,
				"deployUnitVo.fullEname":fullEname,
				//"deployUnitVo.serviceTypeCode":serviceTypeCode,
				"deployUnitVo.status":status,
				"deployUnitVo.secureAreaCode":secureAreaCode,
				//"deployUnitVo.sevureTierCode":sevureTierCode,
				"deployUnitVo.remark":remark,
				"deployUnitVo.oldAppId":oldAppId
		};
		var act;	
		
		//获取zTree信息和当前选中节点
		var zTree = $.fn.zTree.getZTreeObj("treeRm");
		var sNodes = zTree.getSelectedNodes();		
		
		if(method=="add"){
			act="/appmgt/deploy-unit/newDeployUnit.action";
		}else{
			act="/appmgt/deploy-unit/updateDeployUnit.action";
		}
		
		$.ajax({
			async : false,
			cache : false,
			url : ctx + act,
			data : dataObj,
			type:'post',
			dataType : "json",
			success : function(datas){
				var msg=datas.message.split(":");
				showTip(i18nShow('compute_res_operateSuccess'));
				if(msg[0]!="success"){
					closeView('add_update_AppDu_Div');					
					//清除当前节点下的子节点
					if (sNodes && sNodes.length>0) {
						refreshZTreeAfterAdd(zTree,sNodes[0]);
					}

				}else if(msg[0]=="success"){
					closeView('add_update_AppDu_Div');
					getDeployUnit(duId);
					modifyNode(duId,cname);
					if (sNodes && sNodes.length>0) {
						refreshZTreeAfterAdd(zTree,sNodes[0]);
					}
					if(appId!=oldAppId){
						moveTreeNode(appId,duId,"inner");
					} 
					
				}
			}
		});
	}
	
	/**
	 * 取消添加、修改服务器角色
	 */
	function cancelAppDu(){
		if (bizType == "app") {
			initDiv("appInfoView");
		} else if (bizType == "du") {
			initDiv("appDuView");
		}
	}
	
	/**
	 * DIV控制
	 * @param type
	 */
	function initDiv(type){
		if(type=="appInfoView"){
//			showDiv("view_AppInfo_Div");
			$("#hidNodeIV").val("appInfoView");
			appMagAct();
			
			hideDiv("update_AppInfo_Div");
			hideDiv("add_AppInfo_Div");
			hideDiv("view_AppDu_Div");
			hideDiv("add_update_AppDu_Div");
			hideDiv("icon_Div");
		}else if(type=="appDuView"){
//			showDiv("view_AppDu_Div");
			$("#hidNodeIV").val("appDuView");
			appDuMagAct();
			
			hideDiv("view_AppInfo_Div");
			hideDiv("update_AppInfo_Div");
			hideDiv("add_AppInfo_Div");
			hideDiv("add_update_AppDu_Div");
			hideDiv("icon_Div");
		}else{
			hideDiv("add_update_AppDu_Div");
			hideDiv("update_AppInfo_Div");
			hideDiv("add_AppInfo_Div");
			hideDiv("view_AppInfo_Div");
			hideDiv("view_AppDu_Div");
//			showDiv("icon_Div");
			$("#hidNodeIV").val("iconDiv");
			applicationMagAt();
		}
	}
	
	/**
	 * 初始化服务器角色修改页面
	 * @param opt
	 */
	function setAppDU(opt){
		if(opt=="update"){
			$("#du_datacenterId").attr("disabled","disabled");
			$("#du_appId").attr("disabled","disabled");
			$("#du_serviceTypeCode").attr("disabled","disabled");
			$("#du_secureAreaCode").attr("disabled","disabled");
			$("#du_sevureTierCode").attr("disabled","disabled");
		}else{
			$("#du_datacenterId").removeAttr("disabled");
			$("#du_appId").removeAttr("disabled");
			$("#du_serviceTypeCode").removeAttr("disabled");
			$("#du_secureAreaCode").removeAttr("disabled");
			$("#du_sevureTierCode").removeAttr("disabled");
		}
	}
	/**
	 * 初始化服务器角色添加页面
	 * @param appId
	 */
	function clearAppDu(appId){
		selectByValue("du_datacenterId","");
		selectByValue("du_appId",appId);
		$("#du_cname").val("");
		$("#du_fullCname").val("");
		$("#du_ename").val("");
		$("#du_fullEname").val("");
		selectByValue("du_serviceTypeCode","");
		
		$("#du_status").val("Y");
		$("#du_statusDiv").remove();
		$("#du_statusSpan").append('<span class="switch-on"  id="du_statusDiv" style="position: relative;top: 8px;"></span>');
		honeySwitch.init();
		
		selectByValue("du_secureAreaCode","");
		initSevureTier();
		$("#du_remark").val("");
		$("#du_oldAppId").val(appId);
		$("#du_appId").attr("disabled","disabled");
	}
	
	/**
	 * 根据安全区域初始化安全层
	 * @param value
	 */
	function initSevureTierSelect(value){
		initSevureTier();
		if(value != ""){
			$.ajaxSettings.async = false;
			$.getJSON(ctx+"/appmgt/deploy-unit/getSecureTier.action", {"deployUnitVo.secureAreaCode":value}, function(data) {
				$.each(data, function() {
					$("#du_sevureTierCode").append("<option value='" + this.secureTierId + "'>" + this.secureTierName + "</option>");
				});
			});	
		}
	}
	/**
	 * 初始化安全层第一个选项
	 */
	function initSevureTier(){
		$("#du_sevureTierCode").html("");
		$("#du_sevureTierCode").append("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	}
	
	//新增
	function savedateBtn(){
		$("#add_AppInfo_Form").submit();  
	}
	//修改
	function updateBtn(){
		$("#update_AppInfo_Form").submit();  
	}
	
	//新增后增加下拉项目值
	function addSelectInfo(appId,cname,opertype){
		if(opertype=="del"||opertype=='update'){
			$("#fatherId option[value='"+appId+"']").remove();
			$("#newfatherId option[value='"+appId+"']").remove();
			$("#du_appId option[value='"+appId+"']").remove();
		}
		if(opertype=="add"||opertype=='update'){
			//应用系统修改
			$("#fatherId").append("<option value='"+appId+"'>"+cname+"</option>");
			//应用系统新增
			$("#newfatherId").append("<option value='"+appId+"'>"+cname+"</option>");
			//服务器角色 du_appId
			$("#du_appId").append("<option value='"+appId+"'>"+cname+"</option>");
		}
	}
	
	//展开树节点
	function expendNode(){
		var searchForName = $("#name").val();
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : ctx + "/appmgt/application/getAppSysTreeList.action",//请求的action路径   
			data : {
				"appSysPO.appId" : nodeId,
				"appSysPO.cname":searchForName
				},
			error : function() {//请求失败处理函数   
				showTip(i18nShow('tip_req_fail'),null,"red");
			},
			success : function(data) { //请求成功后处理函数。  
				$.each(data,function(){
					addNode(this.id,this.name,$("#hid_icon").val(),$("#hid_icon").val(),false,this.bizType,false,this.isParent,nodeId);
				});
			}
		});
	}
	
	/**
	 * 初始化应用系统添加页面
	 */
	function clearAppSys(){
		selectByValue("newfatherId",$("#hidenAppSysId").val());
		$("#newcname").val("");
		$("#newfullCname").val("");
		$("#newename").val("");
		$("#newfullEname").val("");
		$("#newmanager").val("");
		$("#newremark").val("");
		selectByValue("newsysLevelCode","");
	}