function initPhyHostList() {
	$("#gridTable").jqGrid({
		url : ctx+"/resmgt-common/phymacmanageserver/getHostList.action",
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 315,
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
			name : "cmHostName",
			index : "cmHostName",
			label : "主机名称",
			width : 150,
			sortable : true,
			align : 'left',
		},{
			name:"isInvc",
			index:"isInvc",
			label:"纳管状态",
			width: 80,
			sortable: true,
			align:'left',
			formatter : function(cellValue,options,rowObject){
				if(cellValue=='UM'){
					return "<span class='tip_black'>未匹配</span>" ;
				}else if(cellValue == 'Y'){
					return "<span class='tip_green'>已纳管</span>" ;
				}else if(cellValue == 'N'){
					return "<span class='tip_red'>匹配未纳管</span>" ;
				}else if(cellValue == 'NA'){
					return  "<span></span>";
				}else {
					return cellValue;
				}
				
			
			}
		}, {
			name : "dataCenterName",
			index : "dataCenterName",
			label : "数据中心",
			width : 90,
			sortable : true,
			align : 'left',
			hidden:true
			
		}, {
			name : "manageIp",
			index : "manageIp",
			label : "主机IP",
			width : 80,
			sortable : true,
			align : 'left',
			hidden:true
		}, {
			
			name : "platformType",
			index : "platformType",
			label : "平台类型",
			width : 100,
			sortable : true,
			align : 'left'	,
			hidden:true
		}, {
			name : "vmType",
			index : "vmType",
			label : "虚机类型",
			width : 100,
			sortable : true,
			align : 'left',
			hidden:true
		}, {
			name : "ipmiUser",
			index : "ipmiUser",
			label : "用户名",
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
					str1 = "<a  style='margin-right: 10px;margin-left: 5px;text-decoration:none;' href='javascript:#' title=''  onclick=showUpdateDiv('"+rowObject.id+"')>修改用户名密码</a>";
				}
				if(rowObject.isInvc == 'N') {
					str2 = "<a  style='margin-right: 10px;margin-left: 5px;text-decoration:none;' href='javascript:#' title=''  onclick=nanotube('"+rowObject.id+"')>纳管物理机</a>";
				}
				
				
				return str1+str2;
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
		  userName:{ required: true },
			//oldPasswd: { required: true },
		      newPasswd: {"minlength":3,"maxlength":30 }
		   //   newPasswdConfirm: { "minlength":3,"maxlength":30,equalTo:"#newPasswd" }
		},
	 messages: {
		 userName: {required: "用户名不能为空"},
	//	oldPasswd: {required: "旧密码不能为空"},
		newPasswd: {"minlength":"密码最短3位","maxlength":"密码最长为30位"}
			//newPasswdConfirm:  {"minlength":"密码最短3位","maxlength":"密码最长为30位",equalTo:"密码不一致"}
		},
		submitHandler: function() {
			updateOrSaveData();
		}
	});
	
	// 虚拟机类型取决于平台类型。（查询部分）
	$("#platformNameId").change(
			function(){
				
				// 虚拟机平台类型是OPENSTACK取消发送ajax请求

				var selected = $("#platformNameId").find(
						"option:selected").text()
				if (selected == 'OPENSTACK') {

			// $("#vmTypeNameId").val(-1);
				//	alert($("#vmTypeNameId").val());
					
					$("#vmTypeNameId").attr("disabled","disabled"); 
				}else{
					$("#vmTypeNameId").removeAttr("disabled");
				}
				
				
				var platformNameId=$("#platformNameId").val()
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-common/vmmanageserver/getVmTypeNameInfo.action",
					async : false,
					data:{"platformId":platformNameId},
					success:(function(data){
						$("#vmTypeNameId").empty();
						$("#vmTypeNameId").append("<option value=''>"+'请选择...'+"</option>");
						$(data).each(function(i,item){
							$("#vmTypeNameId").append("<option value='"+item.virtualTypeId+"'>"+item.virtualTypeName+"</option>");
						});			
					})
				});
			}
		);


	//自适应宽度
	$("#gridTable").setGridWidth($("#gridTable_div").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridTable_div").width());
    });
	
}




function saveOrUpdateBtn(){
	$("#updateForm").submit();
}

function search() {
	jqGridReload("gridTable", {
		"cmHostName" : $("#hostName").val()
	});
}


	// 修改物理机用户名密码
	function showUpdateDiv(objectId){
		$("label.error").remove();
		$("#UNamePasswdUpdateDiv").dialog({ 
				autoOpen : true,
				modal:true,
				height:200,
				width:370,
				title:'修改用户名密码',
		       // resizable:false,
		        close : function(){
		        	$("#serverId").val("");//修改操作成功后，清空虚机服务器ID缓存域。
		        }
		});
		$("#hostId").val(objectId);
		clearTab();
		
		$.post(ctx+"/resmgt-common/phymacmanageserver/getUserInfo.action",
			{"resourceId":objectId},
			function(data){
				$("#userName").val(data.username);
			//	alert(data.password)
			}
		)
		
		
	}


 
 	//修改物理主机用户名密码
	function updateOrSaveData(){
		$("label.error").remove();
		
		var hostId=$("#hostId").val();//主机id
		var userNameInput=$("#userName").val().trim();//用户名称
		var passwordInput = $("#newPasswd").val().trim();//密码
		var url=ctx+"/resmgt-common/phymacmanageserver/updateHostPasswd.action";
		var data = {'cupp.id':hostId,'cupp.username':userNameInput,'cupp.password':passwordInput};
	
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data,
	
			success:(function(data){
				if(data.result=="1"){
				
					showTip("修改成功。");
					$("#UNamePasswdUpdateDiv").dialog("close");
					$("#gridTable").jqGrid("GridUnload");
					initPhyHostList();
				}else{
					
					showError("修改失败"+data);
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError("修改失败");
			} 
		});
	}

	//清空所有输入
	function clearTab(){
		 //var tab = document.getElementById("updateTab") ;
		 var inputs = document.getElementsByTagName("input"); 
		 for(var k=0;k<inputs.length;k++) 
		 { 
			 if(inputs[k].type!='button'&&inputs[k].type!='hidden'){
				 inputs[k].value=""; 
			 }
		 } 
	}
	//关闭弹窗
	function closeView(divId){
		$("#"+divId).dialog("close");
	}
	

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
	
//纳管物理主机 
function nanotube(objectId){
	var obj = {'hostId':objectId};
	var url2 = ctx+'/resmgt-common/phymacmanageserver/updataAutomationHost.action';

		$.ajax({
   		 async: true,
	        cache: false,
	        type: 'POST',
	        dataType: 'json',
	        data:obj,
	        url: url2,
	       error: function () {
	        	alert("请求失败", null, "red");
	        },
	        success:function(data){
	        	if(data.result=="1"){
					
					showTip("纳管成功。");
					$("#gridTable").jqGrid("GridUnload");
					initPhyHostList();
				}else{
					
					showError("修改失败"+data);
				}
	        }
   	});
	
}