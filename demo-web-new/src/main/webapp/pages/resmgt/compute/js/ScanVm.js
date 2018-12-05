$(function() {
	
	 $("#dialog-confirm").dialog(
			 {
		         autoOpen: false,
		         height:'auto',
		         width:900, modal: true, 
		     }	
	    );
  
});

function scanVm(){
    $("#dialog-confirm").dialog("open");
    var nodeId = $("#hide_nodeId").val();
	var apptADuId = $("#hidNodeId").val();
	var hostType = $("#hostType").val();
	var rmdatacenterId,resPoolId,rmVqCdpId,clusterId,hostId,rmVmId,
    rmdatacenterIndex,resPoolIndex,cdpqIndex,clusterIndex,hostIndex,rmVmIndex;

		rmdatacenterIndex = nodeId.indexOf("rmdatacenterId:");
		resPoolIndex = nodeId.indexOf("resPoolId:");
		cdpqIndex = nodeId.indexOf("rmVqCdpId:");
		clusterIndex = nodeId.indexOf("clusterId:");
		hostIndex = nodeId.indexOf("hostId:");
		rmVmIndex = nodeId.indexOf("rmVmId:");
		if(rmdatacenterIndex >= 0){
		    rmdatacenterId = nodeId.substr(15);
		}
		if(resPoolIndex >= 0){
			resPoolId = nodeId.substr(10);
		}
		if(cdpqIndex >= 0){
			rmVqCdpId = nodeId.substr(10);
		}
		if(clusterIndex >= 0){
			clusterId = nodeId.substr(10);
		}
		if(hostIndex >= 0){
			hostId = nodeId.substr(7);
		}
		if(rmVmIndex >= 0){
		rmVmId = nodeId.substr(7);
		}
	
		$("#rmVmHostId").val(hostId);
		
		var PostData={HostId:hostId};
		$("#dialog-confirm").dialog({
			autoOpen : true,
			title : i18nShow('compute_res_scanVm'),
			height : 'auto'
		});
		$("#scanVm_table").jqGrid().GridUnload("scanVm_table");
		 $("#scanVm_table").jqGrid({
			        url:ctx+"/rest/vmScan/listvm",
			        rownumbers : true,
			        datatype:"json", //数据来源，本地数据
			        postData: {"hostId":hostId,"hostType":hostType},
			        mtype:"POST",//提交方式
			        height:'450',//高度，表格高度。可为数值、百分比或'auto'
			        width:'auto',//这个宽度不能为百分比
//			        caption:"扫描虚拟机",
			        autowidth:true,//自动宽
			        colModel:[
					{
					 	name : "vmId",
						index : "vmId",
						label : "vmId",
						width : 30,
						sortable : true,
						align : 'left',
						hidden : true
					 },
					 {
					 	name : "powerState",
						index : "powerState",
						label : "powerState",
						width : 30,
						sortable : true,
						align : 'left',
						hidden : true
						},
						{
		        	 	name : "rules",
			        	index : "rules",
			        	label : i18nShow('compute_res_ipRule'),
			        	width : 30,
			        	sortable : true,
			        	align : 'left',
			        	hidden : true
			         },
			         {
			        	 name : "hostType",
			        	 index : "hostType",
			        	 label : i18nShow('compute_res_ipRule'),
			        	 width : 30,
			        	 sortable : true,
			        	 align : 'left',
			        	 hidden : true
			         },
			         {
			        	name : "cloudServiceList",
			        	index : "cloudServiceList",
			        	label : i18nShow('compute_res_scanVm_cloudServiceList'),
			        	width : 30,
			        	sortable : true,
			        	align : 'left',
			        	hidden : true
			        },
			        {
			        	name : "DuList",
			        	index : "DuList",
			        	label : i18nShow('compute_res_scanVm_duList'),
			        	width : 30,
			        	sortable : true,
			        	align : 'left',
			        	hidden : true
			        },
			     
					{
						name : "vmName",
						index : "vmName",
						label :i18nShow('compute_res_datastore_name'),
						width : 145,
						sortable : true,
						align : 'left'
					},
					   {
			        	name : "dataStore",
			        	index : "dataStore",
			        	label : "dataStore",
			        	width : 30,
			        	sortable : true,
			        	align : 'left',
			        	hidden : true
			        },
					{
						name : "cpu",
						index : "cpu",
						label : "CPU",
						width : 42,
						sortable : true,
						align : 'left',
							formatter:function(cellValue,options,rowObject){
								var result="";
								result+="<div id ='cpu"+rowObject.vmId+"'>"+cellValue+"</div>"
								return result;
							}
					},
					{
						name : "memory",
						index : "memory",
						label : i18nShow('compute_res_deviceMem'),
						width : 42,
						sortable : true,
						align : 'left',
							formatter:function(cellValue,options,rowObject){
								var result="";
								result+="<div id ='memory"+rowObject.vmId+"'>"+rowObject.memory+"</div>"
								
								return result;
							}
					},
					{
						name : "cloudServiceName",
						index : "cloudServiceName",
						label : i18nShow('compute_res_cloudServiceName'),
						width : 290,
						sortable : true,
						align : 'center',
							formatter:function(cellValue,options,rowObject){
								var result="";	
								var isexsit = rowObject.isExist;
								if (isexsit==1)
								{
									if (""==cellValue )
										result += i18nShow('compute_res_scanVm_importAlready');
								
								}
								else
									{
									var a = rowObject.ipList;
									result += "<select  id='cloudSelect"+rowObject.vmId+"' onchange=ss('"+rowObject.vmId+"')><option select='selected' value='0'>"+i18nShow('compute_res_select')+"</option>";
									var cslist = rowObject.cloudServiceList;
									for (var i=0;i<cslist.length;i++)
										result+="<option value = '"+cslist[i].cloudServiceId+"'>"+cslist[i].cloudServiceName+"</option>";
									result+="</select>";
									}
								return result;
							}
					},
					{
						name : "addDuName",
						index : "addDuName",
						label : i18nShow('compute_res_duName'),
						width : 99,
						sortable : true,
						align : 'left',
						formatter:function(cellValue,options,rowObject){
							var result="";	
							var isexsit = rowObject.isExist;
							if (isexsit==1)
								{
									result += i18nShow('compute_res_scanVm_updateDuTip');
								}
							else
								{
								var a = rowObject.ipList;
								result += "<select  id='DuSelect"+rowObject.vmId+"'><option select='selected' value='0'>"+i18nShow('compute_res_select')+"</option>";
								result+="</select>";
								}
							return result;
						}
					} ,
					{
						name : "ipList",
						index : "ipList",
						label : i18nShow('compute_res_ipAddress'),
						width : 250,
						sortable : true,
						align : 'left',
						formatter:function(cellValue,options,rowObject){
							var rule = rowObject.rules;
							var result = "";
							var a = rowObject.ipList;
							var isexist = rowObject.isExist;
						if (isexist == 0)
							{
							var s = 0;
							for(var i=0;i<a.length;i++)
								{
								if (a[i].ip!="")
									{
									s++;
									var iparr = a[i].ip.split(".");
									var ipstring = ""
									for (var j =0;j<iparr.length;j++){
										ipstring+=iparr[j];
									}
									var ipCheckArr = a[i].ip.split(",");
									var ipCheckString = "";
									for (var j =0;j<ipCheckArr.length;j++){
										ipCheckString+=ipCheckArr[j];
									}
									var ipV4check = checkIpV4(ipCheckString);
									if(ipV4check==true){
										result +=a[i].ip +"&nbsp&nbsp"+"<select id='"+ipstring+"_opt'> <option select='selected' value = '1'>"+i18nShow('compute_res_select')+"</option>" ;
										for (var k =0;k<rule.length;k++)
									 		result+= "<option value = '"+rule[k].ipTypeName+"'>"+rule[k].ipTypeName+"</option>";
									 		result+="</select><br/>";
										}
									}else{
										result += i18nShow('compute_res_scanVm_vm_management')+"："+"<input type='text' style='border: #d5d5d5 1px solid;' id = '"+rowObject.vmId+"_manage' value = '' />"+"<br/>";
										result += i18nShow('compute_res_scanVm_vm_produce')+"："+"<input type='text' style='border: #d5d5d5 1px solid;' id = '"+rowObject.vmId+"_product' value = ''/>"+"<br/>";
									}
									
								}
							if (s>0)
								{
								result+="<input type='text' hidden='true' id = '"+rowObject.vmId+"_ips' value = '"
								for(var i=0;i<a.length;i++)
								{
								if (a[i].ip!="")
									{
										if (i!=0)
										result+=","+a[i].ip;
									else
									result+=a[i].ip;
									}
								}
								result+="'/>";
								}
							else
								result+="<input type='text' hidden='true' id = '"+rowObject.vmId+"_ips' value =''/>";
							}
							else{
								for(var i=0;i<a.length;i++){
									var ipV4check = checkIpV4(a[i].ip);
									if(ipV4check==true){
										result +=a[i].ip + "<br/>";
									}
								}
							}
							return result;
						}
					},
					{
						name : "",
						index : "",
						label:i18nShow('compute_res_scanVm_import'),
						width : 60,
						align:'center',
						formatter:function(cellValue,options,rowObject){
							var result = "";
							var a = rowObject;
							if (rowObject.isExist == 0)
							{
								var a = rowObject.ipList;
								if ((a.length==1&&a[0].ip==""))
									result += '<a  style=" margin-right: 10px;text-decoration:none;" id="'+rowObject.vmId+'button" href="javascript:#" title="" onclick=saveInputIpVm("' + options.rowId + '","' + rowObject.vmId + '","' + rowObject.dataStore + '","' + rowObject.powerState + '") >'+i18nShow('compute_res_scanVm_import')+'</a>'
								else
									result += '<a  style=" margin-right: 10px;text-decoration:none;" id="'+rowObject.vmId+'button" href="javascript:#" title="" onclick=saveVmwareVm("' + options.rowId + '","' + rowObject.vmId + '","' + rowObject.dataStore + '","' + rowObject.powerState + '") >'+i18nShow('compute_res_scanVm_import')+'</a>'
							}
							return result;
						}
					}
					],
			        sortname: "memory",
			        sortorder:'asc',
			        viewrecords: true,//是否在浏览导航栏显示记录总数
			        rowNum:15,//每页显示记录数
			        rowList:[5,15,20,25,50],//用于改变显示行数的下拉列表框的元素数组。
			        jsonReader:{
			            id: "blackId",//设置返回参数中，表格ID的名字为blackId
			            repeatitems : false,
			            records : "record"
			        },
			        pgbuttons : true,
					pager : '#scanVm_pager',
					hidegrid : false,
					viewrecords : true,
					lastpage : 10
			    });
		 $("#dialog-confirm").removeClass("ui-dialog-content");
		 $("#dialog-confirm").removeClass("ui-widget-content");
		 $("#dialog-confirm").addClass("panel clear");
}

function checkIpV4(ip){  
	var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;  
	var flag = ip.match(exp);  
	if(flag != undefined && flag!=""){  
	   return true;  
	} else {  
	  return false;  
	}  
	} 
function ss(vmId)
{
	var hostId = $("#rmVmHostId").val();
	var d = $("#cloudSelect"+vmId).val();
	var ips = $("#"+vmId+"_ips").val();
	var iparr = ips.split(",");
	
	
	for (var i =0;i<iparr.length;i++)
		{
				var ipV4check = checkIpV4(iparr[i]);
				if(ipV4check==true){
					var ip = iparr[i].split(".");
					var selectId = ""
					for (var j=0;j<ip.length;j++)
						{
							selectId+=ip[j];
						}
					selectId +="_opt";
					document.getElementById(selectId).options.length=0;
				}
		}
	$.ajax({
	    async : false,
	    cache : false,    
	    type: 'POST',     
	    dataType : "json",
	    data:{HostId:hostId,cloudServiceId:d},
	    url: ctx+"/rest/vmScan/listrules",//请求的action路径
	    error: function () {//请求失败处理函数
	    	showTip(i18nShow('compute_res_requestError'));
	    },
	     success:function(data){
	    	 for (var i =0;i<iparr.length;i++)
	 		{
	 				var ipV4check = checkIpV4(iparr[i]);
	 				if(ipV4check==true){
	 					var ip = iparr[i].split(".");
	 		 			var selectId = ""
	 		 			for (var j=0;j<ip.length;j++)
	 		 				{
	 		 					selectId+=ip[j];
	 		 				}
	 		 			selectId +="_opt";
	 		 			document.getElementById(selectId).options.add(new Option(i18nShow('compute_res_select'),"0"));;
	 		 			for (var j=0;j<data.length;j++)
	 		 			document.getElementById(selectId).options.add(new Option(data[j].ipTypeName,data[j].ipTypeName));
	 				}
	 		}
	     }
	});
	$.ajax({
	    async : false,
	    cache : false,    
	    type: 'POST',     
	    dataType : "json",
	    data:{cloudServiceId:d},
	    url: ctx+"/rest/vmScan/getdulist",//请求的action路径
	    error: function () {//请求失败处理函数
	    	showTip(i18nShow('compute_res_requestError'));
	    },
	     success:function(data){
	    	 document.getElementById("DuSelect"+vmId).options.length=0;
	    	var a = data;
	    	document.getElementById("DuSelect"+vmId).options.add(new Option(i18nShow('compute_res_select'),"0"));;
	    	for (var q=0;q<a.length;q++){
	    		document.getElementById("DuSelect"+vmId).options.add(new Option(a[q].appDuName,a[q].appDuId));
	    	}
	    	 
	     }
	});
	
}
//判断输入的是否为IP格式
isIPAddress=function(ip)   
{   
    var re = /^$|^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/   
    return re.test(ip);
}
function saveInputIpVm(rowId,vmId,datastore,powerState){
		var rowData = $('#scanVm_table').jqGrid('getRowData',rowId);
		var vmName = rowData.vmName;
		var ipresult = "";
		var manageIp = $("#"+rowData.vmId+"_manage").val();
		var productIp = $("#"+rowData.vmId+"_product").val();
		if(productIp=="" && manageIp==""){
			showError(i18nShow('compute_res_scanVm_proAndMan_notEmpty'));
		}
		if(manageIp!=""){
			if(isIPAddress(manageIp)==false){
				showError(i18nShow('compute_res_scanVm_ip_tip'));
			}
			ipresult = manageIp+","+i18nShow('compute_res_scanVm_vm_management');
		}
		if(productIp!=""){
			if(isIPAddress(productIp)==false){
				showError(i18nShow('compute_res_scanVm_ip_tip'));
			}else{
				ipresult = productIp+","+i18nShow('compute_res_scanVm_vm_produce');
			}
		}
		if(productIp!="" && manageIp!=""){
			if(productIp==manageIp){
				showError(i18nShow('compute_res_scanVm_proAndMan_tip'));
			}
			ipresult = productIp+","+i18nShow('compute_res_scanVm_vm_produce')+"-"+manageIp+","+i18nShow('compute_res_scanVm_vm_management');
		}
		var cloudresult = $("#cloudSelect" + vmId).val();
		if (cloudresult == "0" || cloudresult == null){
			showTip(i18nShow('compute_res_scanVm_import_cloudServiceTip'));
		}
		var cpuresult = $("#cpu" + vmId).text();
		var memoryresult = $("#memory" + vmId).text();
		var hostId = $("#rmVmHostId").val();
		var duresult = $("#DuSelect" + vmId).val();
		var hostType = $('#hostType').val();
		if (duresult == "0" || duresult == null)
			showTip(i18nShow('compute_res_scanVm_import_duTip'));	
		
		showTip(i18nShow('compute_res_scanVm_import_tip'),function(){
			showTip("load");
		$.ajax({
			type : "POST",
			url : ctx + "/rest/vmScan/savevm",
			data : {
				'ipList' : ipresult,
				'cloudservice' : cloudresult,
				'vmName' : vmName,
				'cpu' : cpuresult,
				'memory' : memoryresult,
				'hostId' : hostId,
				'APP_Du' : duresult,
				'datastore' : datastore,
				'hostType' : hostType,
				'powerState' : powerState
			},
			traditional : true,
			success : function(data) {
				closeTip();
				if (data.result== i18nShow('compute_res_scanVm_import_success')) {
					$("#"+vmId+"button").hide();
					showTip(i18nShow('compute_res_scanVm_import_success'));
					$("#cloudSelect" + vmId).attr("disabled", "disabled");
				}else{
					showError(data.ipresult);
				}
			},
			error : function() { 
				closeTip();
				showTip(i18nShow('compute_res_error'),"red");
			}
		});
		});
	}

function saveVmwareVm(rowId,vmId,datastore,powerState){
	var rowData = $('#scanVm_table').jqGrid('getRowData',rowId);
	var vmName = rowData.vmName;
	var s = $("#" + vmId + "_ips").val();
	var ips = s.split(",");
	var ipsop = [];
	for ( var i = 0; i < ips.length; i++) {
		/*if (ips[i].indexOf(":") > -1) {
			continue;
		}*/
		if(checkIpV4(ips[i])==true){
			var iparr = ips[i].split(".");
			var ipstring = "";
			for ( var j = 0; j < iparr.length; j++)
				ipstring += iparr[j];
			var opt = $("#" + ipstring + "_opt").val()
			/*if(opt == "0"){
				showTip("ip类型为必选项，请选择");
			}*/
			ipsop.push(ips[i] + "," + opt);
		}
		
	}
	var ipresult = "";
	var cloudresult = $("#cloudSelect" + vmId).val();
	if (cloudresult == "0" || cloudresult == null){
		showTip(i18nShow('compute_res_scanVm_import_cloudServiceTip'));
	}
	var cpuresult = $("#cpu" + vmId).text();
	var memoryresultStr = $("#memory" + vmId).text();
	var memoryresult = parseInt(memoryresultStr) * 1024;
	var hostId = $("#rmVmHostId").val();
	var duresult = $("#DuSelect" + vmId).val();
	var hostType = $('#hostType').val();
	if (duresult == "0" || duresult == null)
		showTip(i18nShow('compute_res_scanVm_import_duTip'));
	else {
		for ( var i = 0; i < ipsop.length; i++) {
			if (ipsop != "1") {
				if (i == 0)
					ipresult += ipsop[i];
				else
					ipresult += "-" + ipsop[i];
			}
		}
		showTip(i18nShow('compute_res_scanVm_import_tip'),function(){
			showTip("load");
		$.ajax({
			type : "POST",
			url : ctx + "/rest/vmScan/savevm",
			data : {
				'ipList' : ipresult,
				'cloudservice' : cloudresult,
				'vmName' : vmName,
				'cpu' : cpuresult,
				'memory' : memoryresult,
				'hostId' : hostId,
				'APP_Du' : duresult,
				'datastore' : datastore,
				'hostType' : hostType,
				'powerState' : powerState
			},
			traditional : true,
			success : function(data) {
				closeTip();
				if (data.result== i18nShow('compute_res_scanVm_import_success')) {
					$("#"+vmId+"button").hide();
					showTip(i18nShow('compute_res_scanVm_import_success'));
					$("#cloudSelect" + vmId).attr("disabled", "disabled");
				}else{
					showError(data.ipresult);
				}
			},
			error : function() { 
				closeTip();
				showTip(i18nShow('compute_res_error'),"red");
			}
		});
		});
	}
}