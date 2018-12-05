var method = "";
var choose_orgId = "";
var choose_userName = "";
/**
 * 初始化
 */
$(function() {
	initUserList();//初始列表
	initOrgTree();//初始机构树
	$("#orgName").attr("readOnly","true");
	
	$("#add_update_User_Form").validate({//校验
		rules: {
		firstName: "required",
		lastName: "required",
		loginName: "required",
		loginPassword: "required",
		userType: "required",
		orgName: "required"
		},
		messages: {
			firstName: i18nShow('validate_data_required'),
			lastName: i18nShow('validate_data_required'),
			loginName: i18nShow('validate_data_required'),
			loginPassword: i18nShow('validate_data_required'),
			userType: i18nShow('validate_data_required'),
			orgName: i18nShow('validate_data_required')
		},
		submitHandler: function() {
			saveUser();
		}
	});
	
	// 自适应宽度
	$("#gridTable").setGridWidth($("#gridMain").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
		$("#gridTable").setGridHeight(heightTotal() + 85);
	});
});

function clearAll(){
	$("#loginName_labl").val("");
	$("#userType_labl").get(0).selectedIndex=0; 
}

//修改创建时间格式的方法
function formatTime(ns){
	if(ns){
		var date = new Date(parseInt(ns));
		
		Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
        h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
		
       return Y+M+D+h+m+s;
	}
}

function initUserList() {
	$("#gridTable").jqGrid({
		url : ctx + "/sys/user/findUserPage.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 340,
//		multiselect : true,
		autowidth : true, // 是否自动调整宽度
		colModel : [ {
			name : "lastName",
			index : "us.last_name",
			label : i18nShow('sys_role_user_name'),
			width : 120,
			sortable : true,
			align : 'left',
			 formatter: function(cellValue,options,rowObject){
			return rowObject.firstName+" "+cellValue;
        }
		}, {
			name : "loginName",
			index : "us.login_name",
			label : i18nShow('sys_role_user_login'),
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			name : "email",
			index : "us.email",
			label : i18nShow('sys_role_user_email'),
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			name : "ipAddress",
			index : "us.ip_address",
			label : "IP",
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			name : "phone",
			index : "us.phone",
			label : i18nShow('sys_role_user_tel'),
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "userTypeName",
			index : "dic.dic_name",
			label : i18nShow('sys_user_type'),
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "orgName",
			index : "org.org_name",
			label : i18nShow('sys_user_org'),
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "createDateTime",
			label : i18nShow('sys_user_create_time'),
			width : 120,
			sortable : false,
			align : 'left',
              formatter: function(cellValue,options,rewObject){
					return formatTime(cellValue);
              }
		}, {
			name:i18nShow('com_operate'),index:"option",sortable:false,align:"left",width : 150,title:false,
			formatter:function(cellVall,options,rowObject){
			    var result = "";
			    var upFlag = $("#updateFlag").val(); 
				var delFlag = $("#deleteFlag").val();
				var rolFlag = $("#authorizationFlag").val();
				var userCloudFlag = $("#userCloudFlag").val();
				var userSystemFlag = $("#userSystemFlag").val();
				var userLimitFlag = $("#userLimitFlag").val();
				var s = "";
				if(upFlag == "1"){
					result += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=modifyUser('"+rowObject.userId+"','update') >"+i18nShow('com_update')+"</a>";
				}
				if(delFlag == "1"){
					result += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteUser('"+rowObject.userId+"')>"+i18nShow('com_delete')+"</a>";
				}
				if(rolFlag == '1'){
					s += "<option value='1' >"+i18nShow('sys_user_author_role')+"</option >";
				}
				if(userCloudFlag == '1'){
					s +="<option value='2' >"+i18nShow('sys_user_author_service')+"</option>";
				}
				if(userSystemFlag == '1'){
					s += "<option value='3' >"+i18nShow('sys_user_author_app')+"</option>";
				}
				if(userLimitFlag == '1'){
					s += "<option value='4' >"+i18nShow('sys_user_author_limit')+"</option>";
				}
				if(rolFlag=='1' || userCloudFlag == '1' || userSystemFlag == '1' || userLimitFlag == '1'){
					result += "<select onchange=\"selMeched(this,'"+rowObject.userId+"')\" style=' margin-right: 10px;text-decoration:none;width:90px;'title=''><option vallue='' select='selected'>"+i18nShow('com_select_defalt')+"</option>'"+s+"'</select>" ;
				}
				return result;
			}
        }],
		viewrecords : true,
		sortname : "us.last_name",
		sortorder : "desc",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "searchUser"
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

function searchUser() {
	jqGridReload("gridTable", {
		"loginName" : $("#loginName_labl").val().replace(/(^\s*)|(\s*$)/g, ""),
		"userType" : $("#userType_labl").val()
	});
}

function selMeched(element,id){
	var val = element.value;
	if(val == "1"){
		addRole(id);
	}else if(val == "2"){
		addCloudService(id);
	}else if(val == "3"){
		addSysRole(id);
	}else if(val == "4"){
		setLimit(id);
	}
}
/**
 * 所属机构
 * @return
 */
function showOrgTree() {
	initOrgTree();
	var treeTitle = i18nShow('sys_user_select');
	if(method=="update")
		treeTitle = '【' + choose_userName + '】';
	
	$("#orgAuthorizationDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 450,
		width : 360,
		title : treeTitle+i18nShow('sys_user_org'),
		//resizable : false
	})
	$.fn.zTree.getZTreeObj("orgTree").checkAllNodes(false);

	var menuList = [];
	menuList.push(choose_orgId);
	
	if(menuList.length>0){
		setTimeout(function(){
			expandNode(menuList,"orgTree");
		},300);
	}
}

/**
 * 初始化机构
 * @return
 */
function initOrgTree() {
	var t = $("#orgTree");
	// 加载左侧树
	$.ajax({
		type : "post",
		url : ctx + "/sys/organization/loadtree.do",
		data : {},
		beforeSend : function(XMLHttpRequest) {
		},
		success : function(data, textStatus) {
			var zNodes = data.list;
			t = $.fn.zTree.init(t, {
				check : {
				enable : true,
				chkStyle : "radio",
				chkboxType : {
					"Y" : "s",
					"N" : "s"
				}
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pid",
					rootPId : ""
				}
			},
			callback : {
				beforeClick: beforeClickmenus,//点击事件
				onCheck:function(event,treeid,treeNode){//单选事件
				 beforeClickmenus(treeid, treeNode);
				},
			}
		}, zNodes);
			return true;
		},
		complete : function(XMLHttpRequest, textStatus) {
		},
		error : function() {
			flage = false;
		}
	});
}

function beforeClickmenus(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("orgTree");
			var menunodes = zTree.getCheckedNodes(true);
			
			for(var i=0;i<menunodes.length;i++){//取消选中
				zTree.checkNode(menunodes[i],null, false);	
			}
			zTree.checkNode(treeNode, null, true);//选中当前
			return false;
		}

/**
 * 选取机构节点
 */
function chooseAuthOrg(){
	var menutree = $.fn.zTree.getZTreeObj("orgTree");
	var menunodes = menutree.getCheckedNodes(true);
	if(menunodes.length>1){
		showTip(i18nShow('tip_sys_user_org'));
	    return;	
	}
	$("#orgName").val(menunodes[0].name);
	choose_orgId = menunodes[0].id;
	closeView("orgAuthorizationDiv");//关闭树窗
}

/**
 * 添加用户信息
 */
function addUser(methodname) {
	//清除验证失败信息
	$("label.error").remove();
	method = methodname;//insert
	clearUser();
	openDialog('add_update_User_Div',i18nShow('sys_user_save'),670,450);
}

/**
 * 修改用户信息
 */
function modifyUser(userId,methodname){
	$("label.error").remove();
	method = methodname;//update
	$("#userId").val(userId);
	getUser(userId);
	openDialog('add_update_User_Div',i18nShow('sys_user_update'),670,400);
}

/**
 * 角色复选框
 */
function addRole(userId){
	$("#userId").val(userId);
	if(""==$("#gridTableRole").text())
	  autoUserRole();//加载角色
	else
	  searchUserRole();
	//openDialog('roleAuthorizationDiv','授予角色',667,470);
	$("#roleAuthorizationDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 470,
		width : 667,
		title :i18nShow('sys_user_author_role'),
		close:function(){
			 $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
		}
	});
}

/**
 * 系统授权复选框
 */
function addSysRole(userId){
	$("#radio1").attr("checked",false);
	$("#radio2").attr("checked",false);
	$("#checkAll_roleTree1").attr("checked",false);
	$("#checkAll_roleTree2").attr("checked",false);
	// 获取当前登陆用户配置的操作系统权限
	$("#userId").val(userId);
	var flag = "";
	$.ajax({
		type : "POST",
		url :  ctx + "/sys/user/initRadio.action",
		data : {
			"userId" : userId
		},
		dataType : "json",
		async : false,
		cache : false,
		
		success : function(data) {
			for(var i=0 ; i<data.length ; i++) {
				if(data[i].appInfoId !=null && data[i].appInfoId !=""){
					if( data[i].appInfoId == "ALL") {
						$("#radio1").attr("checked",true);//所有系统
						$("#radio2").attr("checked",false);//指定系统
						flag = "all";
					}else{
						$("#radio2").attr("checked",true);
						$("#radio1").attr("checked",false);
						flag = "assign";
					}
				}
			}
		},
		error : function(e) {
			showTip(exception_info);
		}
	});
	initAddSysRoleTree(userId);
	if(flag == "all"){
		$("#SysLtoRaddBtn").attr("disabled",true);
		$("#SysRtoLaddBtn").attr("disabled",true);
	}
	if(flag == "assign"){
		$("#SysLtoRaddBtn").attr("disabled",false);
		$("#SysRtoLaddBtn").attr("disabled",false);
	}
	$("#add_sysRole_Div").dialog({
		autoOpen : true,
		modal : true,
		height : 520,
		width : 830,
		title :i18nShow('sys_user_author_app'),
		close:function(){
			 $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
		}
	});
}
function setLimit(userId) {
	$("#userId").val(userId);
	$.ajax({ 
		type : 'post',
		datatype : "json",
		url : ctx + "/sys/user/findUserLimitByUserId.action",
		async : true,
		data : {
			"userId" :userId
		},
		success : (function(data) {
			openLimit();
			if(data && data.userId) {
				flatSelectByValue("limitCpu", data.limitCpu);
				flatSelectByValue("limitMem", data.limitMem);
				$("#limitCpu").val(data.limitCpu);
				$("#limitMem").val(data.limitMem);
			} else {
				// 默认为不限
				flatSelectByValue("limitCpu", "");
				flatSelectByValue("limitMem", "");
				$("#limitCpu").val("");
				$("#limitMem").val("");
			}
		}),
		error : function() {
			showError(i18nShow('tip_error'));
		}
	});
}
function openLimit() {
	// 弹出设置配额页面
	$("#set_limit_Div").dialog({
		autoOpen : true,
		modal : true,
		height : 300,
		width : 700,
		title :i18nShow('sys_user_author_limit'),
		close:function(){
			 $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
		}
	});
}
/**
 * 系统授权已选系统初始化
 */
function initAddSysRoleTree(userId) {
	var setting1 = {
		check : {
			enable : true,
			chkType : "checkbox"
		},
		data : {
			key : {
				title : "title"
			}
		}
	};
	var setting2 = {
		check : {
			enable : true,
			chkType : "checkbox"
		},
		data : {
			key : {
				title : "title"
			}
		}
	};
	var zNodes1 = "";
	var zNodes2 = "";
	//查询未选树的数据;
	$.ajax({
		type : "POST",
		url :  ctx + "/sys/user/initAddSysRoleNoTree.action",
		data : {
			"userId" : userId
		},
		dataType : "json",
		async : false,
		cache : false,
		
		success : function(data) {
			if(data!=''){
				zNodes1 = data;
			}
		},
		error : function(e) {
			showTip(exception_info);
		}
	});
	//查询已选树的数据;
	$.ajax({
		type : "POST",
		url :  ctx + "/sys/user/initAddSysRoleTree.action",
		data : {
			"userId" : userId
		},
		dataType : "json",
		async : false,
		cache : false,
		
		success : function(data) {
			if(data!=''){
				zNodes2 = data;
			}
		},
		error : function(e) {
			showTip(exception_info);
		}
	});
	// 初始化左侧树
	$.fn.zTree.init($("#roleTree1"), setting1,zNodes1);
	$.fn.zTree.init($("#roleTree2"), setting2,zNodes2);
}

/**
 * 设置系统：增删节点按钮
 * 所有系统：禁用
 */
function setSysMoveNodeDisable(){
	$("#SysLtoRaddBtn").attr("disabled",true);
	$("#SysRtoLaddBtn").attr("disabled",true);
}
/**
 * 指定系统：启用
 */
function setSysMoveNodeUnDisable(){
	$("#SysLtoRaddBtn").attr("disabled",false);
	$("#SysRtoLaddBtn").attr("disabled",false);
}


/**
 * 设置禁用/启用节点
 */
function checkDisabled(treeId){
	$('#checkAll_roleTree1').attr("disabled",true);
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	if(treeId=="roleTree1"){	
	var disabledNode = treeObj.getNodeByParam("level", 0);  
	treeObj.setChkDisabled(disabledNode, true);
	}
}

function checkUndisabled(treeId){
	$('#checkAll_roleTree1').attr("disabled",false);
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	if(treeId=="roleTree1"){
		var disabledNode = treeObj.getNodeByParam("level", 0);  
		treeObj.setChkDisabled(disabledNode, false);
	}
}

/**
 * 全选树节点
 * zjx
 * */
var checkOrUncheckRoleTree1 = true;
var checkOrUncheckRoleTree2 = true;
var checkOrUncheckRoleTree3 = true;
var checkOrUncheckRoleTree4 = true;
function checkAll(treeId){
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	if(treeId == "roleTree1"){
		treeObj.checkAllNodes(checkOrUncheckRoleTree1);
		checkOrUncheckRoleTree1 = ((checkOrUncheckRoleTree1 == true) ? false : true);
	}else if(treeId == "roleTree2"){
		treeObj.checkAllNodes(checkOrUncheckRoleTree2);
		checkOrUncheckRoleTree2 = ((checkOrUncheckRoleTree2 == true) ? false : true);
	} else if(treeId == "roleTree3"){
		treeObj.checkAllNodes(checkOrUncheckRoleTree3);
		checkOrUncheckRoleTree3 = ((checkOrUncheckRoleTree3 == true) ? false : true);
	} else if(treeId == "roleTree4"){
		treeObj.checkAllNodes(checkOrUncheckRoleTree4);
		checkOrUncheckRoleTree4 = ((checkOrUncheckRoleTree4 == true) ? false : true);
	} 
}



/**
 * 云服务授权复选框
 */
function addCloudService(userId){
	$("#radio3").attr("checked",false);
	$("#radio4").attr("checked",false);
	$("#checkAll_roleTree3").attr("checked",false);
	$("#checkAll_roleTree4").attr("checked",false);
	$("#userId").val(userId);
	var flag = "";
	$.ajax({
		type : "POST",
		url :  ctx + "/sys/user/initRadio.action",
		data : {
			"userId" : userId
		},
		dataType : "json",
		async : false,
		cache : false,
		
		success : function(data) {
			for(var i=0 ; i<data.length ; i++) {
				if(data[i].cloudServiceId !=null && data[i].cloudServiceId !=""){
					if(data[i].cloudServiceId == "ALL") {
						$("#radio3").attr("checked",true);//所有云服务
						$("#radio4").attr("checked",false);//指定云服务
						flag = "all";
					}
					else{
						$("#radio4").attr("checked",true);
						$("#radio3").attr("checked",false);
						flag = "assign";
					}
				}
			}
		},
		error : function(e) {
			showTip(exception_info);
		}
	});
	initCloudServiceSysRoleTree(userId);
	if(flag == "all"){
		$("#CloudLtoRaddBtn").attr("disabled",true);
		$("#CloudRtoLaddBtn").attr("disabled",true);
	}
	if(flag == "assign"){
		$("#CloudLtoRaddBtn").attr("disabled",false);
		$("#CloudRtoLaddBtn").attr("disabled",false);
	}
	$("#add_CloudService_Div").dialog({
		autoOpen : true,
		modal : true,
		height : 520,
		width : 830,
		title :i18nShow('sys_user_author_service'),
		close:function(){
			$( "#gridTable" ).jqGrid().trigger("reloadGrid" );
		}
	});
}

/**
 * 云服务授权已选系统初始化
 */
function initCloudServiceSysRoleTree(userId) {
	var setting1 = {
			check : {
				enable : true,
				chkType : "checkbox"
			},
			data : {
				key : {
					title : "title"
				}
			}
	};
	var setting2 = {
			check : {
				enable : true,
				chkType : "checkbox"
			},
			data : {
				key : {
					title : "title"
				}
			}
	};
	var zNodes1 = "";
	var zNodes2 = "";
	//查询未选树的数据;
	$.ajax({
		type : "POST",
		url :  ctx + "/sys/user/initCloudServiceNoTree.action",
		data : {
			"userId" : userId
		},
		dataType : "json",
		async : false,
		cache : false,
		
		success : function(data) {
			if(data!=''){
				zNodes1 = data;
			}
		},
		error : function(e) {
			showTip(exception_info);
		}
	});
	//查询已选树的数据;
	$.ajax({
		type : "POST",
		url :  ctx + "/sys/user/initCloudServiceTree.action",
		data : {
			"userId" : userId
		},
		dataType : "json",
		async : false,
		cache : false,
		
		success : function(data) {
			if(data!=''){
				zNodes2 = data;
			}
		},
		error : function(e) {
			showTip(exception_info);
		}
	});
	// 初始化左侧树
	$.fn.zTree.init($("#roleTree3"), setting1,zNodes1);
	$.fn.zTree.init($("#roleTree4"), setting2,zNodes2);
}

/**
 * 设置云服务：增删节点按钮
 * 所有云服务：禁用
 */
function setCloudMoveNodeDisable(){
	$("#CloudLtoRaddBtn").attr("disabled",true);
	$("#CloudRtoLaddBtn").attr("disabled",true);
}
/**
 * 指定云服务：启用
 */
function setCloudMoveNodeUnDisable(){
	$("#CloudLtoRaddBtn").attr("disabled",false);
	$("#CloudRtoLaddBtn").attr("disabled",false);
}



//云服务保存
function saveCloudTree() {
	var userId = $("#userId").val();
	var radioValue = $('input:radio:checked').val();
	var cloudStr = '';
	if(radioValue == '3') {
		cloudStr = "ALL";
	} else {
		cloudStr = getSelectedCloud();
	}
	if (userId == "" || userId == null) {
		showTip(i18nShow('tip_sys_user_need'));
		return;
	}
	$.ajax({ 
		type : 'post',
		datatype : "json",
		url : ctx + "/sys/user/saveCloudService.action",
		async : true,
		data : {
			"userId" :userId,
			"cloudStr" : cloudStr
		},
		success : (function(data) {
			showTipSingleButton(i18nShow('tip_save_success'), function () {
				closeView('add_CloudService_Div');
			});
		}),
		error : function() {
			showTip(i18nShow('tip_error'), null, "red");
		}
	});
}
function saveUserLimit() {
	var userId = $("#userId").val();
	if (userId == "" || userId == null) {
		showTip(i18nShow('tip_sys_user_need'));
		return;
	}
	var limitCpu = $("#limitCpu").val();
	var limitMem = $("#limitMem").val();
	if (limitCpu == null || limitCpu == "" || limitMem == null || limitMem == "") {
		showTip(i18nShow('tip_sys_user_limit_need'));
		return;
	}
	$.ajax({ 
		type : 'post',
		datatype : "json",
		url : ctx + "/sys/user/saveUserLimit.action",
		async : true,
		data : {
			"userId" :userId,
			"limitCpu" : limitCpu,
			"limitMem" : limitMem,
		},
		success : (function(data) {
			showTipSingleButton(i18nShow('tip_save_success'), function () {
				closeView('set_limit_Div');
			});
		}),
		error : function() {
			showTip(i18nShow('tip_error'), null, "red");
		}
	});
}

function clearUserLimit() {
	showTip(i18nShow('tip_sys_user_limit_clear'), function() {
		var userId = $("#userId").val();
		if (userId == "" || userId == null) {
			showTip(i18nShow('tip_sys_user_need'));
			return;
		}
		$.ajax({ 
			type : 'post',
			datatype : "json",
			url : ctx + "/sys/user/clearUserLimit.action",
			async : true,
			data : {
				"userId" :userId
			},
			success : (function(data) {
				showTip(i18nShow('tip_op_success'))
				closeView('set_limit_Div');
			}),
			error : function() {
				showTip(i18nShow('tip_error'), null, "red");
			}
		});
	});
}

//获取已选系统信息
function getSelectedCloud() {
	var deviceIds = "";
	var treeObj = $.fn.zTree.getZTreeObj("roleTree4");
	if(treeObj!=null){
		nodes = treeObj.getNodes();
		for ( var i = 0; i < nodes.length; i++) {
			deviceIds += nodes[i].id + ",";
		}
		deviceIds = deviceIds.substring(0, deviceIds.length - 1);
	}
	
	return deviceIds;
}

/**
 * 通用方法
 * 
 * */
//将treeId1树上选择的节点移动到treeId2树上
function setDeviceSelectNode(treeId1, treeId2) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId1);
	var nodeArr = treeObj.getCheckedNodes(true);
	for ( var i = 0; i < nodeArr.length; i++) {
		moveTreeNode(treeId2, treeId1, nodeArr[i].id, false);
	}
}
//将treeId2树种的节点nodeId移动到treeId1树中
function moveTreeNode(treeId1, treeId2, nodeId, isSilent) {
	var treeObj1 = $.fn.zTree.getZTreeObj(treeId1);
	var treeObj2 = $.fn.zTree.getZTreeObj(treeId2);
	var nodeObj = treeObj2.getNodeByParam("id", nodeId, null);
	var obj = treeObj1.getNodeByParam("id", nodeId, null);
	var upId = nodeObj.upId;
	if (!obj) { // 当此节点不存在此树中才进行添加
		var parentObj = addParentNode(treeObj1, treeObj2, upId, isSilent);
		nodeObj.checked = false;
		treeObj1.addNodes(parentObj, nodeObj, isSilent);
	}
	removeNode(treeObj2, nodeObj);
}
//递归添加上级节点
function addParentNode(treeObj1, treeObj2, upId, isSilent) {
	if (!upId) {
		return null;
	}
	var parentObj = treeObj1.getNodeByParam("id", upId, null);
	if (!parentObj) {
		var obj = treeObj2.getNodeByParam("id", upId, null);
		parentObj = addParentNode(treeObj1, treeObj2, obj.upId);
		var node = {
			id : obj.id,
			upId : obj.upId,
			name : obj.name,
			title : obj.name,
			open : true,
			nocheck : (obj.id.indexOf("DU:") == 0) ? false : true
		};
		treeObj1.addNodes(parentObj, node, isSilent);
		parentObj = treeObj1.getNodeByParam("id", obj.id, null);
	}
	return parentObj;
}
//递归移除上级节点
function removeNode(treeObj, nodeObj) {
	var upId = nodeObj.upId;
	treeObj.removeNode(nodeObj);
	var nodeArr = treeObj.getNodesByParam("upId", upId);
	if (nodeArr && nodeArr.length == 0) {
		var parentNode = treeObj.getNodeByParam("id", upId, null);
		if (parentNode) {
			removeNode(treeObj, parentNode);
		}
	}
}

//保存
function saveRoleTree() {
 	var userId = $("#userId").val();
 	var val = $('input:radio:checked').val();
	var roleStr = '';
	if(val == '1') {
		roleStr = "ALL";
	} else {
		roleStr = getSelectedRole();
	}
	if (userId == "" || userId == null) {
		showTip(i18nShow('tip_sys_user_need'));
		return;
	}
		$.ajax({
			type : 'post',
			datatype : "json",
			url : ctx + "/sys/user/saveRole.action",
			async : true,
			data : {
				"userId" :userId,
				"roleStr" : roleStr
			},
			success : (function(data) {
				showTipSingleButton(i18nShow('tip_save_success'), function () {
					closeView('add_sysRole_Div');
				});
			}),
			error : function() {
				showTip(i18nShow('tip_error'), null, "red");
			}
		});
}


//获取已选系统信息
function getSelectedRole() {
	var deviceIds = "";
	var treeObj = $.fn.zTree.getZTreeObj("roleTree2");
	if(treeObj!=null){
		nodes = treeObj.getNodes();
		for ( var i = 0; i < nodes.length; i++) {
			deviceIds += nodes[i].id + ",";
		}
		deviceIds = deviceIds.substring(0, deviceIds.length - 1);
	}
	
	return deviceIds;
}

/**
 * 保存角色授予
 */
function chooseAuthRole(){
	var userId=$("#userId").val();
	var ids = jQuery("#gridTableRole").jqGrid('getGridParam','selarrrow');
	var list = [];
	var extis = 0;
	
	$(ids).each(function (index,id){
		var rowData = $("#gridTableRole").getRowData(id);
		if(extis==0){
			var indexCount = rowData.roleCount.indexOf("已授予");
				if(indexCount>-1){
					extis = 1;//已被授予角色
				}
		}
		list.push(rowData.roleId);
		});
		
		if(extis==1){
			showTip(i18nShow('tip_sys_user_author_role_1'));
			return;
		}
		
	$.ajax({
		async : false,
		cache : false,
		url : ctx + "/sys/user/saveAutoRole.action",
		data : {
				"userId":userId,
				"roleIds":list.join(",")
		},
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('tip_req_fail'));
		},
		success : function(datas){
			searchUserRole();
		}
	});
	
}

/**
 * 
 * 取消角色授予
 */
function cancleAuthRole(){
	var userId=$("#userId").val();
	var ids = jQuery("#gridTableRole").jqGrid('getGridParam','selarrrow');
	var list = [];
	
	$(ids).each(function (index,id){
		var rowData = $("#gridTableRole").getRowData(id);
		list.push(rowData.roleId);
		})
		
	$.ajax({
		async : false,
		cache : false,
		url : ctx + "/sys/user/calcleAutoRole.action",
		data : {
				"userId":userId,
				"roleIds":list.join(",")
		},
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('tip_req_fail'));
		},
		success : function(datas){
			searchUserRole();
		}
	});
}

/**
 * 打开对话框
 * @param divId
 * @param title
 * @param width
 * @param height
 * @return
 */
function openDialog(divId, title, width, height) {
	$("#" + divId).dialog({
		autoOpen : true,
		modal : true,
		height : height,
		width : width,
		title : title,
		//resizable : false
	})
}

/**
 * 提交保存
 * @return
 */
function saveUserBtn(){
	$("#add_update_User_Form").submit();  
}
function encodeBase64(mingwen, times) {
	var code = "";
	var num = 1;
	if (typeof times == 'undefined' || times == null || times == "") {
		num = 1;
	} else {
		var vt = times + "";
		num = parseInt(vt);
	}
	if (typeof mingwen == 'undefined' || mingwen == null || mingwen == "") {
	} else {
		$.base64.utf8encode = true;
		code = mingwen;
		for (var i = 0; i < num; i++) {
			code = $.base64.btoa(code);
		}
	}
	return code;
}
/**
 * 保存用户信息
 */
function saveUser(){
	var userId=$("#userId").val();
	if("insert"==method){
		userId = null;
	}
	var firstName=$("#firstName").val();
	var lastName=$("#lastName").val();
	var loginName=$("#loginName").val();
	var loginNameOld=$("#loginNameOld").val();
	var loginPassword=$("#loginPassword").val();
	var loginPassOld=$("#loginPassOld").val();
	var email=$("#email").val();
	var ipAddress=$("#ipAddress").val();
	var userType=$("#userType").val();
	var phone=$("#phone").val();
	var platUser=$("#cloudUser").attr("value")+""+$("#tenantUser").attr("value");
	
	if(loginPassword.length != 40){
		if(!((/(?=.*\d.*)(?=.*[a-z].*)(?=.*[A-Z].*)/.test(loginPassword))&&loginPassword.length>=6&&loginPassword.length<40)){
			showTip(i18nShow('tip_sys_user_user_login_2'));
			return false;
		}
		loginPassword = hex_sha1(loginPassword);
	}

	var dataObj={
			"sysUserPo.userId":userId,
			"sysUserPo.firstName":firstName,
			"sysUserPo.lastName":lastName,
			"sysUserPo.loginName":loginName,
			"sysUserPo.loginPassword":loginPassword,
			"sysUserPo.email":email,
			"sysUserPo.ipAddress":ipAddress,
			"sysUserPo.userType":userType,
			"sysUserPo.orgId":choose_orgId,
			"sysUserPo.phone":phone,
			"sysUserPo.platUser":platUser,
			"loginNameOld":loginNameOld,
			"loginPassOld":loginPassOld
	};
	
    $("#saveUser").attr({"disabled":true});
	$.ajax({
		async : false,
		cache : false,
		url : ctx + "/sys/user/saveUser.action",
		data : dataObj,
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('tip_req_fail'));
		},
		success : function(datas){
			if(datas.result=="faile"){
				showTip(i18nShow('tip_sys_role_user_login_1'));
			}else{
			searchUser();
			closeView('add_update_User_Div');//关闭
		 }
			$("#saveUser").attr({"disabled":false});
		}
	});
}


/**
 * 根据ID用户信息
 * @param nodeId
 */
function getUser(userId){
	$.ajax({
		async : false,
		cache : false,
		url : ctx + "/sys/user/findUserById.action",
		data : {"userId":userId},
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('tip_req_fail'));
		},
		success : function(datas){
			initUser(datas);
		}
	});
}

/**
 * 删除用户
 */
function deleteUser(userId){
	showTip(i18nShow('tip_delete_confirm'),function(){
		$.ajax({
			async : false,
			cache : false,
			url : ctx + "/sys/user/deleteUser.action",
			data : {"userId":userId},
			type:'post',
			dataType : "json",
			success : function(datas){
				if(datas.result=="0"){
					showError(i18nShow('tip_req_fail'));
				}
				searchUser();
			}
		});
	});
}

/**
 * 初始化用户信息添加页面
 * @param userId
 */
function clearUser(){
//selectByValue("srTypeId",srTypeId);
	$("#firstName").val("");
	$("#lastName").val("");
	$("#loginName").val("");
	$("#loginPassword").val("");
	$("#email").val("");
	$("#ipAddress").val("");
	$("#userType").val("");
	$("#orgName").val("");
	$("#phone").val("");
	choose_orgId = "";
	
	$("#cloudUser").remove();
	$("#tenantUser").remove();
	$("#cloudUserLabel").append('<span class="switch-off" name="radio-1" id="cloudUser" value="0"></span>');
	$("#tenantUserLabel").append('<span class="switch-off"  name="radio-2" id="tenantUser" value="0"></span>');
	honeySwitch.init();
}

/**
 * 初始用户信息查看页面
 * @param datas
 */
function initUser(datas) {
	choose_orgId = datas.orgId;
	choose_userName	 = datas.firstName+" "+datas.lastName;
	$("#firstName").val(datas.firstName);
	$("#lastName").val(datas.lastName);
	$("#loginName").val(datas.loginName);
	$("#loginNameOld").val(datas.loginName);
	$("#loginPassword").val(datas.loginPassword);
	$("#loginPassOld").val(datas.loginPassword);
	$("#email").val(datas.email);
	$("#ipAddress").val(datas.ipAddress);
	$("#userType").val(datas.userType);
	$("#orgName").val(datas.orgName);
	$("#phone").val(datas.phone);
	var platUser = datas.platUser;
	$("#cloudUser").remove();
	$("#tenantUser").remove();
	$("#cloudUserLabel").append('<span class="switch-off" name="radio-1" id="cloudUser"></span>');
	$("#tenantUserLabel").append('<span class="switch-off"  name="radio-2" id="tenantUser"></span>');
	if(platUser.charAt(0) == "0"){
		$("#cloudUser").attr("value", "0");
		$("#cloudUser").removeClass("switch-on").addClass("switch-off");
	}else{
		$("#cloudUser").attr("value", "1");
		$("#cloudUser").removeClass("switch-off").addClass("switch-on");
	}
	if(platUser.charAt(1) == "0"){
		$("#tenantUser").attr("value", "0");
		$("#tenantUser").removeClass("switch-on").addClass("switch-off");
	}else{
		$("#tenantUser").attr("value", "1");
		$("#tenantUser").removeClass("switch-off").addClass("switch-on");
	}
	honeySwitch.init();
//initSevureTierSelect(datas.secureAreaCode);
	}

/**
 * 关闭对话框
 * @param divId
 * @return
 */
function closeView(divId) {
	$("#" + divId).dialog("close");
}

/**
 * 展开节点
 * @param menuList
 * @return
 */
function expandNode(menuList,treename){
	if (menuList.length > 0) {
		for (i = 0; i < menuList.length; i++) {
			var zTree = $.fn.zTree.getZTreeObj(treename);
			var node = zTree.getNodeByParam('id', menuList[i], null);
			 zTree.checkNode(node, true, false);// 选中原有机构
			 var parentNode = node.getParentNode();
			 zTree.expandNode(parentNode, true, false, true); //默认展开
		}
	}
}

/**
 * 授予角色
 * @return
 */
function autoUserRole() {
	$("#gridTableRole").jqGrid({
		url : ctx+"/sys/user/findSysRoleList.action",
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 260,
		autowidth : true, // 是否自动调整宽度
		multiselect:true,
		multiboxonly: false,
		postData: {"userIdchoose" :$("#userId").val()},
		colModel : [ {
			name : "roleId",
			index : "roleId",
			label : "roleId",
			width : 0,
			sortable : true,
			align : 'left',
			hidden:true	
		},{
			name : "roleName",
			index : "roleName",
			label : i18nShow('sys_role_name'),
			width : 200,
			sortable : true,
			align : 'left'
		},{
			name : "remark",
			index : "remark",
			label : i18nShow('com_remark'),
			width : 238,
			sortable : true,
			align : 'left'
		},{
			name : "roleCount",
			label : i18nShow('sys_user_author'),
			width : 95,
			sortable : true,
			align : 'left',
            formatter: function(cellValue,options,rewObject){
			var result = "<font style='color:red'>"+i18nShow('sys_user_author_N')+"</font>";
			if(cellValue>0)
				  result = "<font style='color:green'>"+i18nShow('sys_user_author_Y')+"</font>";
			return result;
      }
	}],
		
		viewrecords : true,
		sortname : "roleName",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "searchUserRole"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#gridPagerRole",
		hidegrid : false
	});
}
//自适应宽度
$("#gridTableRole").setGridWidth($("#gridTableRole").width());
$(window).resize(function() {
	$("#gridTableRole").setGridWidth($("#gridTableRole").width());
	//$("#gridTableRole").setGridHeight(heightTotal() + 25);
});	


function searchUserRole() {	
	var userRoleId = $("#userId").val();
	jqGridReload("gridTableRole", {
		"userIdchoose" :userRoleId
	});
}