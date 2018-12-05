<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/count.css"></link>
<script type="text/javascript">


//生成EXCEL方法
function creat_excel(){
	$("#creat_excel_form").submit();
}

function saveExcel(){
	var username = $("#username").val();
	var password = $("#password").val();
	var url_ip = $("#url_ip").val();
	var amqType = $("#amqType").val();
	if(!checkIpv4(url_ip)){
		showTip(i18nShow('sys_excel_validateIp'));
		return;
	}
	showTip("load");
	var path =ctx+'/excel/create/createExcelAction.action';
	$.ajax({
		async : false,
		cache : false,
		type : 'post',
		datatype : "json",
		url : path,  
		data : {"username":username,"password":password,"url_ip":url_ip,"amqType":amqType},
		error : function(data) {//请求失败处理函数   
			showTip(data.result);
		},
		success : function(data) { //请求成功后处理函数。
			$("#flag").val(data.result);
			//图形显示方法
			closeTip();
		    
			showTip(data.result);
			information_excel();
		}
	});
	
}

//检验IP
function checkIpv4(value) {
	return /^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])$/i
			.test(value);
}



//显示EXCEL列表方法
function information_excel(){
$('#new_list').show();
	$('#derived_excel').empty();
	var path =ctx+'/excel/create/excelInfomationAction.action';
	$.ajax({
		async : false,
		cache : false,
		type : 'post',
		datatype : "json",
		url : path,  
		data : {},
		error : function() {//请求失败处理函数   
			showTip(i18nShow('compute_res_load_error'));
		},
		success : function(data) { //请求成功后处理函数。     
			excelList(data);
		}
	});
	
}

//修改日期格式
function formatDate(date){
	if(date ){		
		Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';	
       return Y+M+D;
	}
}
//修改时间格式
function formatTime(time){
	if(time){		
        h = (time.getHours() < 10 ? '0'+(time.getHours()) : time.getHours()) + ':';
        m = (time.getMinutes() < 10 ? '0'+(time.getMinutes()) : time.getMinutes()) + ':';
        s = (time.getSeconds() < 10 ? '0'+(time.getSeconds()) : time.getSeconds());		
       return h+m+s;
	}
}

 
function excelList(data){
	$("#derived_excel").append("<tr><td align='center'>"+i18nShow('compute_res_number')+"</td>"+
    			"<td align='center'>"+i18nShow('sys_excel_fileName')+"</td>"+
    			"<td align='center'>"+i18nShow('sys_excel_createTime')+"</td>"+
    			"<td align='center'>"+i18nShow('sys_excel_modifyTime')+"</td>"+
    			"<td align='center'>"+i18nShow('sys_excel_writeDb')+"</td>"+
    			"<td align='center'>"+i18nShow('com_operate')+"</td></tr>");
	for(var i=0;i<data.length;i++){
		var number = i+1;
		var createdateobj=new Date(data[i].createDateTime.time); 	    
		var updatedateobj=new Date(data[i].updateDateTime.time);
		
		if(data[i].isInput == "N"){
			$("#derived_excel").append("<tr><td id='number"+i+"' name='number"+i+"' value='"+data[i].id+"'>"+number+"</td>"+
			"<td id='fileName"+i+"' value='"+data[i].fileName+"'>"+data[i].fileName+"</td>"+
			"<td id='createTime"+i+"' value='"+data[i].createDateTime+"'>"+formatDate(createdateobj)+"  "+formatTime(createdateobj)+"</td>"+
			"<td id='updateTime"+i+"' value='"+data[i].updateDateTime+"'>"+formatDate(updatedateobj)+"  "+formatDate(updatedateobj)+"</td>"+
			"<td id='isInput"+i+"' value='"+data[i].isInput+"'>"+i18nShow('compute_res_no')+"</td>"+
			//"<td><a href=''>导入</a>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='excelOutPut(\""+data[i].fileType+"\")'>导出</a>&nbsp;&nbsp;&nbsp;<a href=''>写入数据库</a></td>"+
			"<td><a href='javascript:void(0)' onclick='showInputDiv()'>"+i18nShow('compute_res_scanVm_import')+"</a>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='excelOutPut(\""+data[i].fileType+"\")'>"+i18nShow('sys_excel_export')+"</a>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='write_dataBase_excel(\""+data[i].fileName+"\")'>"+i18nShow('sys_excel_write_database')+"</a></td>"+
			"</tr>")
		}else{
		
			$("#derived_excel").append("<tr><td id='number"+i+"' name='number"+i+"' value='"+data[i].id+"'>"+number+"</td>"+
			"<td id='fileName"+i+"' value='"+data[i].fileName+"'>"+data[i].fileName+"</td>"+
			"<td id='createTime"+i+"' value='"+data[i].createDateTime+"'>"+formatDate(createdateobj)+"  "+formatTime(createdateobj)+"</td>"+
			"<td id='updateTime"+i+"' value='"+data[i].updateDateTime+"'>"+formatDate(updatedateobj)+"  "+formatDate(updatedateobj)+"</td>"+
			"<td id='isInput"+i+"' value='"+data[i].isInput+"'>"+i18nShow('compute_res_yes')+"</td>"+
			"</tr>")
		}
		
	}
	
}

function excelOutPut(type){
	var f = $("#flag").val();
	if(f=="获取信息成功！"){
		window.location.href=ctx+'/excel/create/outPutExcelAction.action?fileType='+type;
	}else{
		showTip(i18nShow('sys_excel_error'));
	}
	
	/* var path =ctx+'/excel/create/outPutExcelAction.action';
	$.ajax({
		async : false,
		cache : false,
		type : 'post',
		datatype : "json",
		url : path,  
		data : {"fileType":type},
		error : function() {//请求失败处理函数   
			showTip("IP或账号密码有误，未生成EXCEL！");
		}
	}); */
}


function showInputDiv(){
	$('#fileuploadDiv').show();
	$("#fileuploadDiv").dialog({
		autoOpen : true,
		bgiframe : true, //解决IE6,兼容问题
		modal:true,
		height:470,
		width:500,
		position: "middle",
		title:i18nShow('sys_excel_file_import'),
		draggable:false
	});
}


//写入数据库
function write_dataBase_excel(fileName){
	
	var path =ctx+'/excel/create/excelWriteDataBaseAction.action';
	showTip(i18nShow('sys_excel_writeDb_confirm'),function(){
		showTip("load");
		$.ajax({
			async : false,
			cache : false,
			type : 'post',
			datatype : "json",
			url : path,  
			data : {"fileName":fileName},
			error : function() {//请求失败处理函数   
				showTip(i18nShow('compute_res_load_error'));
			},
			success : function(data) { //请求成功后处理函数。     
				closeTip();
				showTip(data.result);
				information_excel();
			}
		});
	
  });
	
	
}

$(function(){
	information_excel();
	$("#creat_excel_form").validate({
			rules: {
				url_ip: {required: true},
				username: {required: true},
				password: {required: true}
			},
			messages: {
				url_ip: {required: i18nShow('sys_excel_validate_ip')},
				username: {required: i18nShow('sys_excel_validate_username')},
				password: {required: i18nShow('sys_excel_validate_password')}
			},
			submitHandler: function() {
				saveExcel();
			}
		});
	
})
function copyFileName(){
	var f = document.getElementById("file1").files; 
	var name=f[0].name;
	$("#fileName").val(name);
}
</script>

<title>计算资源池管理</title>
<style type="text/css">
html,body{height:auto;}
.tip2{ padding-left:20px; padding-top:0; padding-bottom:10px;background-position: 13px 41%;}
.sumCon_top{margin-top:10px; }
#creat_excel{ margin-top:40px; margin-left:89px; clear:both; overflow:hidden;}
#creat_excel th{height:27px; line-height:27px; padding:6px 12px; color:#232323; font-weight:"微软雅黑";}
#creat_excel td{height:27px; line-height:27px; padding:6px 12px; color:#646964;}
#creat_excel input{border:1px solid #D5D9DC; background:#f5f5f5; color:#615d70; box-shadow:1px 1px 2px #ededed; width:170px; height:14px; font-size:12px; line-height:14px;padding:4px 4px;}
#creat_excel select{border:1px solid #D5D9DC; background:#f5f5f5; color:#615d70; box-shadow:1px 1px 2px #ededed; width:180px; height:24px; font-size:12px; line-height:24px;padding:1px 4px;}
.tip2b{width:90%;}
.BtnWrap{margin:10px; padding-left:166px; padding-bottom:14px;}

#derived_excel{border:1px solid #e1e1e1; margin-left:106px; clear:both; margin-top:26px;}
#derived_excel td{height:23px; line-height:23px; padding:4px 16px; color:#646964; border:1px solid #e1e1e1;}
#fileuploadDiv {padding-top:10px;}
</style>
</head>
<body class="main1">

<input type="hidden" id="hiddenDiv" />
<input type="hidden" id="hid_icon" />
<input type="hidden" id="datastoreType" />
<input type="hidden" id="clusterIdSpecified" />
<input type="hidden" id="cluster_id"></input>
<input type="hidden" id="hide_datastoreType"></input>
<input type="hidden" id="hide_nodeId"></input>
<input type="hidden" id="flag"></input>
	<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_excel_title"/></div>
					<div class="WorkSmallBtBg">
						<small>
							<icms-i18n:label name="sys_excel_desc"/>
						</small>
					</div>
			</h1>
		</div>
	<div>
		<div id="new_tab" class="sumWrap" >
		<ul id="tb" class="tb">
			<li id="new_tab_t" class="BtCur" ><icms-i18n:label name="sys_l_excel_create"/></li>
    	</ul>
    </div>
    <div id="new_tab_inf_b" class="sumCon_top" style="margin-top:0; width:100%;" >
    <div id="new_creat" class="sumWrap" style="display: block; padding-bottom:20px;">
    	<p class="tip2"><icms-i18n:label name="sys_l_excel_detail"/></p>
    	<form action="" id="creat_excel_form">
    	<table id="creat_excel" border="0" >
    		<tr>
    			<th width="25%"><icms-i18n:label name="sys_l_excel_ip"/>:</th>
    			<td width="75%">
    				<input type="text" id="url_ip" name="url_ip" value="" class="textInput" />
    			</td>
    		</tr>
    		<tr>
    			<th><icms-i18n:label name="sys_l_excel_username"/>:</th>
    			<td>
    				<input type="text" id="username" name="username" value="" class="textInput" />
    			</td>
    		</tr>
    		<tr>
    			<th><icms-i18n:label name="sys_l_excel_pwd"/>: </th>
    			<td>
    				<input type="password" id="password" name="password" value="" class="textInput" />
    			</td>
    		</tr>
    		<tr>
    			<th><icms-i18n:label name="sys_l_excel_type"/>: </th>
    			<td >
    				<select name="amqType" id="amqType">
    					<option id="vcenter" value="vcenter" selected="selected" >vcenter</option>
    					<option id="kvm" value="kvm">kvm</option>
    					<option id="xen" value="xen">xen</option>
    					<option id="power" value="power">power</option>
    				</select>
    			</td>
    		</tr>
    	</table>
    	<div class="BtnWrap" style="padding-left:159px;">
    		<a href="javascript:void(0)" id="saveButton" type="button" onclick="creat_excel()" class="btn btn-size" ><span class="icon iconfont icon-icon-save icon-size"></span> <icms-i18n:label name="com_btn_save"/>
    		</a>
    		<button class="btnDel" ><span class="icon iconfont icon-icon-cancel icon-size"></span> <icms-i18n:label name="com_btn_cancel"/>
    		</button>
    	</div>
    	</form>
    </div>
  	</div>
	</div>
	
  	
	<div id="new_list" style="display: block; background:#fff; margin-top:18px; padding-top:20px; padding-bottom:20px;">
		<p class="tip2"><icms-i18n:label name="sys_excel_file_import"/></p>	
		<table id="derived_excel" border="1" >
    		<tr>
    			<td align="center"><icms-i18n:label name="sys_l_excel_order"/></td>
    			<td align="center"><icms-i18n:label name="sys_l_excel_failName"/></td>
    			<td align="center"><icms-i18n:label name="sys_l_excel_createTime"/></td>
    			<td align="center"><icms-i18n:label name="sys_l_excel_modifyTime"/></td>
    			<td align="center"><icms-i18n:label name="sys_l_excel_writeDb"/></td>
    			<td align="center"><icms-i18n:label name="mw_l_operate"/></td>
    		</tr>
    		
    	</table>
	</div>

	<div id="fileuploadDiv" style="display: none">
		<div style="margin:20px 30px auto 20px">
		<form name="uploadForm" id="uploadForm" action="<%=basePath%>excel/create/uploadAction.action" method="post" enctype="multipart/form-data" >
		<input type="text" id="fileName" name="fileName"  style="width:179px; height:22px; line-height:22px; float:left; box-shadow: 1px 1px 2px #ededed; background-color: #f5f5f5;border: 1px solid #d5d5d5;"/>
		<input type="file" id="file1" name="file" onchange="copyFileName()" style="float:left; height:22px; width:135px; line-height:22px; margin-left:5px"/>
		<input name='<icms-i18n:label name="com_btn_save"/>' type="submit" value='<icms-i18n:label name="com_btn_save"/>' class="btn btn_dd2 btn_dd3 " style="margin:0x 0; padding-bottom:0px; height:23px;width:54px;"/>
		
		</form>
		</div>
	</div>

</body>
</html>