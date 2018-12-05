function initDistributeSwitchList() {
	
	var queryData = {};
	
	$("#gridTable").jqGrid({
		url : ctx+"/resmgt-common/distibute_switch/getDistributeSwitchList.action",
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 315,
		autowidth : true, // 是否自动调整宽度
		//multiselect:true,
		multiboxonly: false,
		colModel : [ {
			name : "switch_id",
			index : "switch_id",
			label : "id",
			width : 0,
			sortable : true,
			align : 'left',
			hidden:true	
		},  {
			name : "switchName",
			index : "switchName",
			label : "分布式交换机名称",
			width : 150,
			sortable : true,
			search:true,
			align : 'left'
		},{
			name:"dataCenterName",
			index:"dataCenterName",
			label:"所属数据中心",
			width: 80,
			sortable: true,
			search:true,
			align:'left'
			
		},{
			name : "remark",
			index : "remark",
			label : "备注",
			width : 60,
			sortable : true,
			align : 'left',
			hidden:false
		}, {
			name : 'option',
		    index : 'option',
		    label : "操作",
			width : 120,
			align : "left",
			sortable:false,
			formatter : function(cellValue,options,rowObject) {
				var deleteFlag = $("#deleteFlag").val();
				var updateflag = $("#updateFlag").val();
				var str1 = "",str2 = "";
				if(updateflag=='1'){
					str1 = "<a  style='margin-right: 10px;margin-left: 5px;text-decoration:none;' href='javascript:#' title=''  onclick=showUpdateDiv('"+rowObject.switch_id+"')>修改</a>";
				}
				str2 = "<a  style='margin-right: 10px;margin-left: 5px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteDistributeSwitch('"+rowObject.switch_id+"')>删除</a>";
				
				var s = "",result = "";
			
				s += "<option onclick=chooseHost('"+rowObject.switch_id+"')>选择物理机</option >";
				s += "<option onclick=showDistributeport('"+rowObject.switch_id+"')>端口组维护</option >";
				
				result += "<select style=' margin-right: 10px;text-decoration:none;'title=''><option vallue='' select='selected'>请选择</option>'"+s+"'</select>" ;
				
				return str1+str2+result;
			}														
		}],
		
		viewrecords : true,
		sortname : "switchName",
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
	
	//端口组
	
	$("#softwareVerTable").jqGrid({
		url : ctx+"/resmgt-common/distibute_switch/getDistributePortGroupList.action", 
		rownumbers : false, 
		postData:{"switchId":"null"},
		datatype : "json", 
		mtype : "post", 
		height : 200,
		autowidth : true, 
		//multiselect:true,
		colNames:['switchId','端口组名称','VlanId','是否有效','操作'],
		colModel : [ 
		            {name : "switchId",index : "switchId",sortable : true,align : 'left',hidden:true},
		            {name : "portGroupName",index : "portGroupName", sortable : true,align : 'left'},
		            {name : "vlanId",index : "vlanId", sortable : true,align : 'left'},
		            {name : "isActive",index : "isActive",sortable : false,align : 'left',hidden:true},
		            {name:"option",index:"option",sortable:false,align:"left",
						formatter:function(cellVall,options,rowObject){
							 var result = " ";
							 var deleteVarBut = "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteProtGroup('"+rowObject.portGroupId+"')>删除</a>";
							
								 result += deleteVarBut;
							 
							return result;
						}
		            }
		            ],
		viewrecords : true,
		sortname : "portGroupName",
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		hidegrid : false
	});

	

		//表单验证
	$("#updateForm").validate({
		rules: {
			switchName:{ required: true },
			dataCenterName: {required: true }
		},
	 messages: {
		 switchName: {required: "用户名不能为空"},
		 dataCenterName: {required: "数据中心不能为空"}
	},
		submitHandler: function() {
			updateOrSaveData();
		}
	});
	
	
	/**
	 * 端口组表单验证
	 */
	$("#softWareForm").validate({
		rules: {
			portGroupName:{
				required:true,
				remote : {
					type : "POST",
					url : ctx + "/resmgt-common/distibute_switch/checkPortGroupName.action",
					data : {
						"distributePortGroupPo.portGroupName" : function(){return $("#portGroupName").val()} ,
						"distributePortGroupPo.switchId": function(){return  $("#switchId").val()}
						},
						
					}
				},
				vlanId:{
					required:true
				}
				
			},
		
		messages: {
			portGroupName: {required: "端口组名不能为空",remote:"端口组名不能重复"},
			vlanId:{required: "vlanId不能为空"}
		},
		submitHandler: function() {
			saveNewPortGroup();
		}
	});
	
	


	//自适应宽度
	$("#gridTable").setGridWidth($("#gridTable_div").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridTable_div").width());
    });
	
}

//提交增加或修改分布式交换机的表单

function saveOrUpdateBtn(){
	$("#updateForm").submit();
}

//新增分布式端口组
function addPortGroupBtn(){
	
	$("#softWareForm").submit();
}

//关闭端口组添加页面
function closePortGroupDiv(){
	$("#softWareDiv").dialog("close");
}

function search() {
	jqGridReload("gridTable", {
		"switchName" : $("#switchName22").val(),
		"dataCenterName":$("#dataCenterId").val()
//		"platformType" : $("#platformNameId").val(),
//		"vmType" : $("#vmTypeNameId").val()
	});
}



	// 修改分布式交换机
	function showUpdateDiv(objectId){
		$("label.error").remove();
		$("#DistributeSwitchUpdateDiv").dialog({ 
				autoOpen : true,
				modal:true,
				height:290,
				width:350,
				title:'修改分布式交换机信息',
		       // resizable:false,
		        close : function(){
		        	//$("#serverId").val("");//修改操作成功后，清空虚机服务器ID缓存域。
		        }
		});
		clearTab();
		
		$("#switch_id").val(objectId);
		$.post(ctx+"/resmgt-common/distibute_switch/getDisributeByID.action",
			{"distributeSwitchPo.switch_id":objectId},
			function(data){
				 $("#switch_id").val(objectId);//交换机的id
				$("#switchName").val(data.switchName);//交换机名
				$("#remark").val(data.remark);//备注
				$("#dataCenterName").val(data.dataCenterName);//数据中心id
			}
		)
		
		
	}
	
	


 
 	//修改或者添加分布式交换机信息
	function updateOrSaveData(){
		$("label.error").remove();
		
		var switch_id = $("#switch_id").val();//交换机的id
		var switchName=$("#switchName").val().trim();//交换机名
		var remark=$("#remark").val().trim();//备注
		var dataCenterName = $("#dataCenterName").val();//数据中心id
					
		var url = ctx+"/resmgt-common/distibute_switch/createDistributeSwitch.action";
		var data = {'distributeSwitchPo.switch_id':switch_id,'distributeSwitchPo.switchName':switchName,'distributeSwitchPo.dataCenterName':dataCenterName,'distributeSwitchPo.remark':remark};
		$("#saveDistributeSwitch").attr({"disabled":true});
		
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data,
			cache : false,
			success:(function(data){
				if(data.result=="success"){
				
					showTip(i18nShow('zh4'));
					$("#DistributeSwitchUpdateDiv").dialog("close");
//					$("#gridTable").jqGrid().trigger("reloadGrid");
					// 先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
					$("#gridTable").jqGrid("GridUnload");
					initDistributeSwitchList();
				}else{
					
					showError("保存失败"+data);
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError("保存失败");
			} 
		});
		
		$("#saveDistributeSwitch").attr({"disabled":false});
	}
	
	
	/**
	 * 保存分布式端口组
	 */
	function saveNewPortGroup(){

		$("label.error").remove();
		
		var switchId = $("#switchId").val();//交换机的id
		var vlanId=$("#vlanId").val().trim();
		var portGroupName=$("#portGroupName").val().trim();//端口组名 192.168.1.X
					
		var url = ctx+"/resmgt-common/distibute_switch/createPortGroup.action";
		var data = {'distributePortGroupPo.switchId':switchId,'distributePortGroupPo.vlanId':vlanId,'distributePortGroupPo.portGroupName':portGroupName};
		$("#btn_add_sp").attr({"disabled":true});
		
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data,
			cache : false,
			success:(function(data){
				if(data.result=="success"){
				
					showTip(i18nShow('zh4'));
					$("#softWareDiv").dialog("close");
					
				}else{
					
					showError("保存失败"+data);
				}
				jqGridReload("softwareVerTable",{});
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('zh6'));
			} 
		});
		
		$("#btn_add_sp").attr({"disabled":false});
	
	}
	// 添加分布式交换机view
	function createDistributeSwitchView(){
		$("label.error").remove();
		$("#DistributeSwitchUpdateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:320,
				width:350,
				title:'添加分布式交换机',
		});
		clearTab();
		
		$("#switchName").val("");
		$("#remark").val("");
		$("#switch_id").val("");
		$("#dataCenterName").val("");
		
	}
	
	function clearTab(){
		 var inputs = document.getElementsByTagName("input"); 
		 for(var k=0;k<inputs.length;k++) 
		 { 
			 if(inputs[k].type!='button'&&inputs[k].type!='hidden'){
				 inputs[k].value=""; 
			 }
		 } 
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
		var d = new Date(parseInt(ns.time + ""));
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		var date = d.getDate();
		var hour = d.getHours();
		var minute = d.getMinutes();
		var second = d.getSeconds();
		return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
	}
}

//分布式端口组管理
function showDistributeport(softwareId){
	$("#softwareVersDiv").dialog({
		autoOpen : true,
		modal:true,
		height:400,
		title:'分布式端口组',
		width:700,
		close:function(){
			  $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
		}
	})	
	$("#switchId").val(softwareId);
	$("#softwareVerTable").jqGrid("setGridParam", { postData : {'switchId':softwareId} }).trigger("reloadGrid");
	//自适应宽度
	$("#softwareVerTable").setGridWidth($("#softwareVerTableDiv").width());
	$(window).resize(function() {
		$("#softwareVerTable").setGridWidth($("#softwareVerTableDiv").width());
    });
}
//增加端口组view
function createPortGroup(){
	emptyValue("softWareVerSelectId");
	$("label.error").remove();
	$("#softWareDiv").dialog({
			autoOpen : true,
			modal:true,
			height:230,
			title:'添加端口组',
			width:400
	});
	$("#softWareMethod").val("create");
	$("#portGroupName").val(""); //清空上次的表单
	$("#vlanId").val("");
	selectByValue("softWareSelectId","");
	var id = $("#softWareTable").jqGrid('getDataIDs').length+1;
	$("#softWareRowId").val(id);
	$("#softWareVerSelectId").html("<option value=''>--请选择--</option>");
}

//选择物理机
function chooseHost(switchId){
	
	$("#recycle_select_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700,
		title :"选择物理机",
		close:function(){
			 $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
		}
	});
	$("#switchIdTree").val(switchId);
	
	var isAsyncS2 = false;
	
	//判断该分布式交换机下是否存在物理机
	$.ajax({
  		 async: false,
	        cache: false,
	        type: 'POST',
	        dataType: 'json',
	        data:{"switchId":switchId},
	        url:  ctx+"/resmgt-common/distibute_switch/findDistributeSwitchHaveHost.action",
	       error: function () {
	        	alert("请求失败", null, "red");
	        },
	        success:function(data){
	        //	alert(data);
	        	if(data>0) {
	        		isAsyncS2 = true;
	        	}
	        }
  	});
	
	initDeviceTree(switchId,isAsyncS2);
	
	
	
}

/**
 * 初始化物理机树
 */

function initDeviceTree(switchId,idAsync) {
	
	var appId = '';
	var datacenterId = '';
	var deviceIdArr = getSelectedDeviceIds(); // 获取已选的虚拟机Id
	var setting1 = {
		async : {
			enable : true,
			url : ctx + "/resmgt-common/distibute_switch/findHostTreeForChoose.action",
			autoParam : [ "id" ],
			otherParam : {
				appId : appId,
				datacenterId : datacenterId,
				deviceIdArr : deviceIdArr
			}
		},
		check : {
			enable : true,
			chkType : "checkbox"
		},
		data : {
			key : {
				title : "title"
			}
		},
		callback : {
			beforeCheck : zTreeBeforeCheck,
			onAsyncSuccess : zTreeOnAsyncSuccess,
			//beforeExpand:deleteTree1
		}
	};
	var setting2 = {
		async : {
				enable : idAsync,
				url : ctx + "/resmgt-common/distibute_switch/findHostTreeAlreadyChoose.action",
				autoParam : [ "id" ],
				otherParam : {
					switchId : switchId
				}
		},
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
	// 初始化左侧树，默认只站看到资源池，下级节点默认隐藏
	$.fn.zTree.init($("#deviceTree1"), setting1);
	$.fn.zTree.init($("#deviceTree2"), setting2);
	var treeObj2 = $.fn.zTree.getZTreeObj("deviceTree2");
	treeObj2.expandAll(true); 
}

/**
 * check tree
 */
var nodeStr = "";
var treeIdArr = new Array();
function zTreeBeforeCheck(treeId, treeNode) {
	if (treeNode && treeNode.id && treeNode.id.indexOf("DU:") == 0) {
		if (treeIdArr.indexOf(treeNode.id) < 0) {
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			nodeStr += treeNode.id + ";";
			treeObj.reAsyncChildNodes(treeNode, "refresh", false);
			treeIdArr.push(treeNode.id);
		}
	}
}
/**
 * check tree2
 */
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	if (treeNode && treeNode.id && treeNode.id.indexOf("DU:") == 0) {
		if (nodeStr.indexOf(treeNode.id + ';') >= 0) {
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var nodeArr = treeObj.getNodesByParam("upId", treeNode.id, null);
			for ( var i = 0; i < nodeArr.length; i++) {
				treeObj.checkNode(nodeArr[i], true, true);
			}
			nodeStr.replace(treeNode.id + ';', '');
		}
	}
}

/**
 * 递归添加上级节点
 */

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
			icon: "/icms-web/css/zTreeStyle/img/icons/appsys.png",
			open : true,
			nocheck : (obj.id.indexOf("DU:") == 0) ? false : true
		};
		treeObj1.addNodes(parentObj, node, isSilent);
		parentObj = treeObj1.getNodeByParam("id", obj.id, null);
	}
	return parentObj;
}


/**
 * 对所选择的节点操作进行验证
 */

function checkSelectNode(treeObj) {
	var nodeArr = treeObj.getCheckedNodes(true); 
	if (nodeArr.length == 0) {
		showError("您当前尚未选择任何设备，不能进行此操作！");
		return false;
	}
	var confirmResult = false; // 操作人员对弹出的确认框的结果
	var flag = true; // 是否需要弹出确认框来让用户确认标示
	var duNames = "";
	return true;
	
	
}

/**
 * 点击树下面的确认按钮
 */

function selectRecycleDevice() {
	$("#recycle_select_div").dialog("close");
	var sw_id = $("#switchIdTree").val();
	var treeObj = $.fn.zTree.getZTreeObj("deviceTree2");
	//alert(sw_id);
	var tree2Ids = "";
	var nodeArr = treeObj.getNodes();
	for(var i=0; i<nodeArr.length;i++){
		var chArr = nodeArr[i].children;
		//console.log(chArr)
		for(var j=0;j<chArr.length;j++){
			tree2Ids +=chArr[j].id+",";
		}
	}
	//console.log(tree2Ids);
	
	//提交 交换机下的所有主机id[] 和交换机id
	$.ajax({
		async : false,
		cache : false,
		url : ctx+"/resmgt-common/distibute_switch/addDistributeSwitchHost.action",
		data : {"switchId":sw_id,"switch_HostIds":tree2Ids},
		type:'post',
		dataType : "json",
		success : function(datas){
			
			if(datas.result=="1"){
				showTip("添加成功");
			}
			search();
		}
	});
	
	
	
}

/**
 * 将treeId1树上选择的节点移动到treeId2树上
 * @param treeId1
 * @param treeId2
 */

function setSelectNode(treeId1, treeId2) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId1);
	if (checkSelectNode(treeObj)) { // 在验证过程中，通过验证则进行移动树节点的操作
		var nodeArr = treeObj.getCheckedNodes(true);
		for ( var i = 0; i < nodeArr.length; i++) {
			if (nodeArr[i].id.indexOf("DU:") < 0) {
				moveTreeNode(treeId2, treeId1, nodeArr[i].id, false);
			}
		}
	}
}

/**
 * 将treeId2树种的节点nodeId移动到treeId1树中
 */

function moveTreeNode(treeId1, treeId2, nodeId, isSilent) {
	var treeObj1 = $.fn.zTree.getZTreeObj(treeId1);
	var treeObj2 = $.fn.zTree.getZTreeObj(treeId2);
	var nodeObj = treeObj2.getNodeByParam("id", nodeId, null);
	var obj = treeObj1.getNodeByParam("id", nodeId, null);
	var upId = nodeObj.upId;
	if (!obj) { // 当此节点不存在此树中才进行添加
		var parentObj = addParentNode(treeObj1, treeObj2, upId, isSilent);
		//确保可向parentNode下添加子节点。
		parentObj.isParent = true;
		nodeObj.checked = false;
		treeObj1.addNodes(parentObj, nodeObj, isSilent);
	}
	removeNode(treeObj2, nodeObj);
}

/**
 * 递归移除上级节点
 */

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

/**
 * copy 获取已选的虚拟机Id
 */
function getSelectedDeviceIds() {
	var deviceIdArr = new Array();
	var dataIds = $("#selectDeviceTable").jqGrid('getDataIDs');
	for ( var i = 0; i < dataIds.length; i++) {
		var deviceId = jQuery('#selectDeviceTable').getCell(dataIds[i], "deviceId");
		deviceIdArr.push(deviceId);
	}
	return deviceIdArr;
}

/**
 * 删除分布式交换机
 */


function deleteDistributeSwitch(objId){
	//alert(objId);
	showTip("确定删除该分布式交换机吗？",function(){
		$.ajax({
			async : false,
			cache : false,
			url : ctx+"/resmgt-common/distibute_switch/delDistributeSwitch.action",
			data : {"distributeSwitchPo.switch_id":objId},
			type:'post',
			dataType : "json",
			success : function(datas){
				
			//	console.log()
			//	console.log(datas)
				if(datas.result=="0"){
					showError("删除分布式交换机时发生异常!");
				}
				search();
			}
		});
	});
}

/**
 * 删除端口组
*/
function deleteProtGroup(objId){

	//alert(objId);
	showTip("确定删除该端口组吗？",function(){
		$.ajax({
			async : false,
			cache : false,
			url : ctx+"/resmgt-common/distibute_switch/delPortGroup.action",
			data : {"distributePortGroupPo.portGroupId":objId},
			type:'post',
			dataType : "json",
			success : function(datas){
				if(datas.result=="0"){
					showError("删除该端口组时发生异常!");
				}
				jqGridReload("softwareVerTable",{});
			}
		});
	});

}