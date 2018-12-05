/**
 * 初始化
 */
var method = "";
$(function() {
		/*单击节点后，右侧DIV业务表现的方法*/
		var myBizFun = function() {
				var node = zTree.getNodeByParam("id", nodeId, null);
				getBmSrType(nodeId);

		};
		regFunction(myBizFun,expendNode);//注册方法
		initBmSrTree();//加载

		$("#add_update_BmSrType_Form").validate({//校验
			rules: {
			srTypeName: "required",
			srTypeMark: "required",
			srTypeCode: "required",
			remark: "required"
			},
			messages: {
				srTypeName: i18nShow('validate_data_required'),
				srTypeMark: i18nShow('validate_data_required'),
				srTypeCode: i18nShow('validate_data_required'),
				remark: i18nShow('validate_data_required')
			},
			submitHandler: function() {
				saveBmSrType();
			}
		});
	});

/**
 * 加载数据
 * @return
 */
function initBmSrTree(){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/request/type/findBmSrTypeTreeList.action",//请求的action路径   
		data : {"srTypeId" : ""},
		error : function() {//请求失败处理函数   
			showTip(i18nShow('tip_req_fail'),null,"red");
		},
		success : function(data) { //请求成功后处理函数  
			zTreeInit("treeRm", data);
			//选择第一个节点并返回ID
			var treeObj =zTree;
		    node=treeObj.getNodesByFilter(function (node) { return node.level == 1 }, true);	
		    treeObj.selectNode(node);
		    getBmSrType(node.id);
		}
	});
}

/**
 * 模糊查询树
 * @return
 */
function searchTreeByCname() {
	var searchForName = $("#name").val();
	if(null==searchForName||""==searchForName){
		initBmSrTree();
		return;
	}
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/request/type/findBmSrTypeTreeListByName.action",//请求的action路径   
		data : {"srTypeName" : searchForName},
		error : function() {//请求失败处理函数   
			showTip(i18nShow('tip_req_fail'),null,"red");
		},
		success : function(data) { //请求成功后处理函数。     
			zTreeInit("treeRm", data);
		}
	});
}

/**
 * 展开树节点
 * @return
 */
function expendNode(){
	var searchForName = $("#name").val();
	if(null!=searchForName&&""!=searchForName)
		return;
	
	if(nodeId=='0')//禁止重复加载
		return;
	
	ajaxCall("/request/type/findBmSrTypeTreeList.action",{"srTypeId" : nodeId},
			asyncAddNode
)
}
	
	/**
	 * 根据ID获取请求类型
	 * @param nodeId
	 */
	function getBmSrType(nodeId){
		if(nodeId=='0')//跟节点不加载
			return;
		$.ajax({
			async : false,
			cache : false,
			url : ctx + "/request/type/findBmSrTypeById.action",
			data : {"srTypeId":nodeId},
			type:'post',
			dataType : "json",
			success : function(datas){
				initBmSrType(datas);
			}
		});
	}
	
	/**
	 * 初始请求类型查看页面
	 * @param datas
	 */
	function initBmSrType(datas) {
		$("#lbl_BmSrType_srTypeName").text(datas.srTypeName);
		$("#lbl_BmSrType_srTypeMark").text(datas.srTypeMark);
		$("#lbl_BmSrType_srTypeCode").text(datas.srTypeCode);
		$("#lbl_BmSrType_remark").text(datas.remark);
		$("#srTypeName").val(datas.srTypeName);
		$("#srTypeMark").val(datas.srTypeMark);
		$("#srTypeCode").val(datas.srTypeCode);
		$("#remark").val(datas.remark);
		if(null==nodeId)
			   nodeId = datas.srTypeId;
//	initSevureTierSelect(datas.secureAreaCode);
		}

	/**
	 * 添加请求类型
	 */
	function addBmSrType(methodname) {
		if("L"==$("#lbl_BmSrType_srTypeCode").text()){
			showTip(i18nShow('tip_my_req_type_msg'),null,"red");
			return;
		}
		method = methodname;//insert
		clearBmSrType(nodeId);
		$("#srTypeCode").removeAttr("disabled");//可用
		openDialog('add_update_BmSrType_Div',i18nShow('my_req_type_save'),500,260);
	}
	
	/**
	 * 修改请求类型
	 */
	function modifyBmSrType(methodname){
		if(nodeId=='0')//不可编辑根节点
			return;
		method = methodname;//update
		getBmSrType(nodeId);
		$("#srTypeCode").attr("disabled","disabled");//设置不可用
		openDialog('add_update_BmSrType_Div',i18nShow('my_req_type_update'),500,260);
	}
	
	function saveBmSrTypeBtn(){
		$("#add_update_BmSrType_Form").submit();  
	}

	/**
	 * 保存请求类型
	 */
	function saveBmSrType(){
		var srTypeId=nodeId;//当前节点 
		var parentId=parentNodeId;//当前点的父节点
		if("insert"==method){
			srTypeId = null;
			parentId = nodeId;
		}
		var srTypeCode=$("#srTypeCode").val();
		var srTypeName=$("#srTypeName").val();
		var srTypeMark=$("#srTypeMark").val();
		var remark=$("#remark").val();
		var dataObj={
				"bmSrTypePo.srTypeId":srTypeId,
				"bmSrTypePo.srTypeName":srTypeName,
				"bmSrTypePo.srTypeMark":srTypeMark,
				"bmSrTypePo.srTypeCode":srTypeCode,
				"bmSrTypePo.parentId":parentId,
				"bmSrTypePo.remark":remark
		};
		$.ajax({
			async : false,
			cache : false,
			url : ctx + "/request/type/saveBmSrType.action",
			data : dataObj,
			type:'post',
			dataType : "json",
			success : function(datas){
				if("insert"==method){
					var newSrtId=datas.result;
					var isParentnew = false;
					var iconPathOpen = this.icon;
					var iconPathClose = null;          
					if("F"==srTypeCode){
					 isParentnew = true;
					 iconPathOpen = iconPath+"scriptpackage.png";
					 iconPathClose = iconPath+"script.png";
					}
					addNode(newSrtId,srTypeName,iconPathOpen,iconPathClose,this.iconOpen,srTypeCode,false,isParentnew,parentId);
				}else {
					modifyNode(srTypeId,srTypeName);
					getBmSrType(nodeId);//刷新
//				moveTreeNode(A,B,"inner"); //将B从任意节点下移动到A下面
				}
				closeView('add_update_BmSrType_Div');//关闭
			}
		});
	}
	
	/**
	 * 删除请求类型
	 */
	function deleteBmSrType(){
		if(confirm(i18nShow('tip_delete_confirm'))){
			var srTypeId=nodeId;
			$.ajax({
				async : false,
				cache : false,
				url : ctx + "/request/type/deleteBmSrType.action",
				data : {"srTypeId":srTypeId},
				type:'post',
				dataType : "json",
				success : function(datas){
					if(datas.result=="1"){
						deleteTreeNode(nodeId);
					}else if(datas.result=="2"){
						showTip(i18nShow('tip_my_req_type_msg1'),null,"red");
					}else{
						showTip(i18nShow('tip_delete_fail'),null,"red");
					}
				}
			});
		}
	}

	/**
	 * 初始化请求类型添加页面
	 * @param appId
	 */
	function clearBmSrType(srTypeId){
//	selectByValue("srTypeId","");//设置option
//	selectByValue("srTypeId",srTypeId);
		$("#srTypeName").val("");
		$("#srTypeMark").val("");
		$("#srTypeCode").val("");
		$("#remark").val("");
	}

	/**
	 * 一次性加载时过滤
	 * @return
	 */
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
	 * 关闭对话框
	 * @param divId
	 * @return
	 */
	function closeView(divId) {
		$("#" + divId).dialog("close");
	}