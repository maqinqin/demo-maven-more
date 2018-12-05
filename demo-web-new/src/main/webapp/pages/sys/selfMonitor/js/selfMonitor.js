$(function() {
		hmcListShow();		//获取hmc列表(ok)
		
		vcListShow();		//获取vc列表
		
		scriptListShow();	//获取脚本列表(ok)
		
		autoListShow();		//获取automation列表
		
		mqListShow();		//获取mq(ok)
		
		bpmListShow();		//获取bpm(ok)
		
		imageListShow();	//获取镜像列表	(ok)	
	});
//获取hmc列表
function hmcListShow(){
	var path = ctx + "/sys/selfMonitor/getHmcServiceList.action";
	$.ajax({
		async : true,
		cache : false,
		type : 'post',
		datatype : "json",
		url : path,  
		data : {},
		error : function() {//请求失败处理函数 
			showTip(i18nShow('compute_res_load_error'));
		},
		success : function(data) { //请求成功后处理函数。
			hmcList(data);
		}
	});
}


function onloadHmc(id){
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/sys/selfMonitor/hmcServiceMonitor.action",
		async : true,
		data:{
			"selefMonitorVo.id" : id
		},
		success:(function(data){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			if(data.status==i18nShow('sys_selfTest_status_normal') ){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		} 
	});
}


function hmcList(data){
	$("#hmc_list").append("<tr><td align='center' colspan='5' style='background:#E4EAEC; font-weight:bold;'>"+i18nShow('sys_selfTest_hmcList')+"</td></tr>");
	for(var i=0;i<data.length;i++){
		var number = i+1;
			$("#hmc_list").append("<tr id=''><td width='3%'id='number"+i+"' name='number"+i+"'>"+number+"</td>"+
			"<td width='22%' id='serverName"+i+"' value='"+data[i].serverName+"'>"+data[i].serverName+"</td>"+
			"<td width='60%' id='ip"+i+"' value='"+data[i].ip+"'>"+data[i].ip+"</td>"+
			"<td value='"+data[i].status+"' id='"+data[i].id+"' window.onload='"+onloadHmc(data[i].id)+"'>"+data[i].status+"</td>"+
			"<td><a href='javascript:void(0)' onclick='hmcMonitor(\""+data[i].id+"\")'>"+i18nShow('sys_selfTest_test')+"</a></td>"+
			"</tr>")
			if(data[i].status==i18nShow('sys_selfTest_status_normal') || data[i].status==i18nShow('sys_selfTest_testing') ){
				document.getElementById(data[i].id).style.color='green';
			}else{
				document.getElementById(data[i].id).style.color='red';
			}
	}
}

function hmcMonitor(id){
	showTip(i18nShow('sys_selfTest_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/sys/selfMonitor/hmcServiceMonitor.action",
				async : true,
				data:{
					"selefMonitorVo.id" : id
				},
				beforeSend: function () {
		        	showTip("load");
		        },
				success:(function(data){
					closeTip();
					showTip(i18nShow('sys_selfTest_hmc_message')+data.message);
					if(data.status==i18nShow('sys_selfTest_status_normal')){
						document.getElementById(id).style.color='green';
					}else{
						document.getElementById(id).style.color='red';
					}
					document.getElementById(id).innerHTML = data.status;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					closeTip();
					showError(i18nShow('sys_selfTest_hmc_fail'));
					document.getElementById(id).style.color='red';
					document.getElementById(id).innerHTML = i18nShow('sys_selfTest_status_abnormal');
				} 
			});
		});
}


//显示vc列表方法
function vcListShow(){
	var path =ctx + "/sys/selfMonitor/getVcenterServerList.action";
	$.ajax({
		async : true,
		cache : false,
		type : 'post',
		datatype : "json",
		url : path,  
		data : {},
		error : function() {//请求失败处理函数
			showTip(i18nShow('compute_res_load_error'));
		},
		success : function(data) { //请求成功后处理函数。
			vcList(data);
		}
	});
	
}

function onloadVc(id){
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/sys/selfMonitor/vcenterServerMonitor.action",
		async : true,
		data:{"selefMonitorVo.id":id},
		success:(function(data){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			if(data.status==i18nShow('sys_selfTest_status_normal') ){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		} 
	});
}

function vcList(data){
	$("#vcenter_list").append("<tr><td align='center' colspan='5' style='background:#E4EAEC; font-weight:bold;'>"+i18nShow('sys_selfTest_vcList')+"</td></tr>");
	for(var i=0;i<data.length;i++){
		var number = i+1;
			$("#vcenter_list").append("<tr id=''><td width='3%'id='number"+i+"' name='number"+i+"'>"+number+"</td>"+
			"<td width='22%' id='serverName"+i+"' value='"+data[i].serverName+"'>"+data[i].serverName+"</td>"+
			"<td width='60%' id='ip"+i+"' value='"+data[i].ip+"'>"+data[i].ip+"</td>"+
			"<td value='"+data[i].status+"' id='"+data[i].id+"' window.onload='"+onloadVc(data[i].id)+"'>"+data[i].status+"</td>"+
			"<td><a href='javascript:void(0)' onclick='vcMonitor(\""+data[i].id+"\")'>"+i18nShow('sys_selfTest_test')+"</a></td>"+
			"</tr>")
			if(data[i].status==i18nShow('sys_selfTest_status_normal') || data[i].status==i18nShow('sys_selfTest_testing')){
				document.getElementById(data[i].id).style.color='green';
			}else{
				document.getElementById(data[i].id).style.color='red';
			}
	}
	
}

function vcMonitor(id){
	showTip(i18nShow('sys_selfTest_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/sys/selfMonitor/vcenterServerMonitor.action",
				async : true,
				data:{"selefMonitorVo.id":id},
				beforeSend: function () {
		        	showTip("load");
		        },
				success:(function(data){
					closeTip();
					showTip(i18nShow('sys_selfTest_vc_message')+data.message);
					if(data.status==i18nShow('sys_selfTest_status_normal') ){
						document.getElementById(id).style.color='green';
					}else{
						document.getElementById(id).style.color='red';
					}
					document.getElementById(id).innerHTML = data.status;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					closeTip();
					showError(i18nShow('sys_selfTest_vc_fail'));
					document.getElementById(id).style.color='red';
					document.getElementById(id).innerHTML = i18nShow('sys_selfTest_status_abnormal');
				} 
			});
		});
}




//显示镜像列表方法
function imageListShow(){
	var path =ctx + "/sys/selfMonitor/getImageServiceList.action";
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
			imageList(data);
		}
	});
}

function onloadImage(url,id){
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/sys/selfMonitor/imageServiceMonitor.action",
		async : true,
		data:{"selefMonitorVo.url":url},
		success:(function(data){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		} 
	});
}

function imageList(data){
	$("#image_list").append("<tr><td align='center' colspan='5' style='background:#E4EAEC; font-weight:bold;'>"+i18nShow('sys_selfTest_imageList')+"</td></tr>");
	for(var i=0;i<data.length;i++){
		var number = i+1;
			$("#image_list").append("<tr id=''><td width='3%' id='number"+i+"' name='number"+i+"'>"+number+"</td>"+
			"<td width='22%' id='serverName"+i+"' value='"+data[i].serverName+"'>"+data[i].serverName+"</td>"+
			"<td width='60%' id='url"+i+"' value='"+data[i].url+"'>"+data[i].url+"</td>"+
			"<td value='"+data[i].status+"' id='"+data[i].id+"' window.onload='"+onloadImage(data[i].url,data[i].id)+"'>"+data[i].status+"</td>"+
			"<td><a href='javascript:void(0)' onclick='imageMonitor(\""+data[i].url+"\",\""+data[i].id+"\")'>"+i18nShow('sys_selfTest_test')+"</a></td>"+
			"</tr>")
			if(data[i].status==i18nShow('sys_selfTest_status_normal') || data[i].status==i18nShow('sys_selfTest_testing')){
				document.getElementById(data[i].id).style.color='green';
			}else{
				document.getElementById(data[i].id).style.color='red';
			}
	}
	
}

function imageMonitor(url,id){
	showTip(i18nShow('sys_selfTest_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/sys/selfMonitor/imageServiceMonitor.action",
				async : true,
				data:{"selefMonitorVo.url":url},
				beforeSend: function () {
		        	showTip("load");
		        },
				success:(function(data){
					closeTip();
					showTip(i18nShow('sys_selfTest_image_message')+data.message);
					if(data.status==i18nShow('sys_selfTest_status_normal')){
						document.getElementById(id).style.color='green';
					}else{
						document.getElementById(id).style.color='red';
					}
					document.getElementById(id).innerHTML = data.status;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					closeTip();
					showError(i18nShow('sys_selfTest_image_fail'));
					document.getElementById(id).style.color='red';
					document.getElementById(id).innerHTML = i18nShow('sys_selfTest_status_abnormal');
				} 
			});
		});
}




//显示script列表方法
function scriptListShow(){
	var path =ctx + "/sys/selfMonitor/getScriptServiceList.action";
	$.ajax({
		async : true,
		cache : false,
		type : 'post',
		datatype : "json",
		url : path,  
		data : {},
		error : function() {//请求失败处理函数 
			showTip(i18nShow('compute_res_load_error'));
		},
		success : function(data) { //请求成功后处理函数。
			scriptList(data);
		}
	});
	
}

function onloadScript(id,ip,userName,pwd){
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/sys/selfMonitor/scriptServiceMonitor.action",
		async : true,
		data:{
			  "selefMonitorVo.id":id,
			  "selefMonitorVo.ip":ip,
			  "selefMonitorVo.userName":userName,
			  "selefMonitorVo.pwd":pwd},
			  success:(function(data){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		} 
	});
}

function scriptList(data){
	$("#script_list").append("<tr><td align='center' colspan='5' style='background:#E4EAEC; font-weight:bold;'>"+i18nShow('sys_selfTest_scriptList')+"</td></tr>");
	for(var i=0;i<data.length;i++){
		var number = i+1;
			$("#script_list").append("<tr id=''><td width='3%' id='number"+i+"' name='number"+i+"'>"+number+"</td>"+
			"<td width='22%' id='serverName"+i+"' value='"+data[i].serverName+"'>"+data[i].serverName+"</td>"+
			"<td width='60%'id='ip"+i+"' value='"+data[i].ip+"'>"+data[i].ip+"</td>"+
			"<td value='"+data[i].status+"' id='"+data[i].id+"' window.onload='"+onloadScript(data[i].id,data[i].ip,data[i].userName,data[i].pwd)+"'>"+data[i].status+"</td>"+
			"<td><a href='javascript:void(0)' onclick='scriptMonitor(\""+data[i].id+"\",\""+data[i].ip+"\",\""+data[i].userName+"\",\""+data[i].pwd+"\")'>"+i18nShow('sys_selfTest_test')+"</a></td>"+
			"</tr>")
			if(data[i].status==i18nShow('sys_selfTest_status_normal') || data[i].status==i18nShow('sys_selfTest_testing')){
				document.getElementById(data[i].id).style.color='green';
			}else{
				document.getElementById(data[i].id).style.color='red';
			}
	}
	
}

function scriptMonitor(id,ip,userName,pwd){
	showTip(i18nShow('sys_selfTest_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/sys/selfMonitor/scriptServiceMonitor.action",
				async : true,
				data:{
					  "selefMonitorVo.id":id,
					  "selefMonitorVo.ip":ip,
					  "selefMonitorVo.userName":userName,
					  "selefMonitorVo.pwd":pwd},
					  beforeSend: function () {
				        	showTip("load");
				        },
				success:(function(data){
					closeTip();
					showTip(i18nShow('sys_selfTest_script_message')+data.message);
					if(data.status==i18nShow('sys_selfTest_status_normal')){
						document.getElementById(id).style.color='green';
					}else{
						document.getElementById(id).style.color='red';
					}
					document.getElementById(id).innerHTML = data.status;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					closeTip();
					showError(i18nShow('sys_selfTest_script_fail'));
					document.getElementById(id).innerHTML = i18nShow('sys_selfTest_status_abnormal');
					document.getElementById(id).style.color='red';
				} 
			});
		});
}




//显示automation列表方法
function autoListShow(){
	var path =ctx + "/sys/selfMonitor/getAutoServerList.action";
	$.ajax({
		async : true,
		cache : false,
		type : 'post',
		datatype : "json",
		url : path,  
		data : {},
		error : function() {//请求失败处理函数   
			showTip(i18nShow('compute_res_load_error'));
		},
		success : function(data) { //请求成功后处理函数。
			autoList(data);
		}
	});
	
}

function onloadAuto(serverName,id){
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/sys/selfMonitor/autoServiceMonitor.action",
		async : true,
		data:{"selefMonitorVo.serverName":serverName},
		success:(function(data){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		} 
	});
}

function autoList(data){
	$("#auto_list").append("<tr><td align='center' colspan='5' style='background:#E4EAEC; font-weight:bold;'>"+i18nShow('sys_selfTest_autoList')+"</td></tr>");
	for(var i=0;i<data.length;i++){
		var number = i+1;
			$("#auto_list").append("<tr id=''><td width='3%' id='number"+i+"' name='number"+i+"'>"+number+"</td>"+
			"<td width='22%' id='serverName"+i+"' value='"+data[i].serverName+"'>"+data[i].serverName+"</td>"+
			"<td width='60%' id='ip"+i+"' value='"+data[i].ip+"'>"+data[i].ip+"</td>"+
			"<td value='"+data[i].status+"' id='"+data[i].id+"' window.onload='"+onloadAuto(data[i].serverName,data[i].id)+"'>"+data[i].status+"</td>"+
			"<td><a href='javascript:void(0)' onclick='autoMonitor(\""+data[i].serverName+"\",\""+data[i].id+"\")'>"+i18nShow('sys_selfTest_test')+"</a></td>"+
			"</tr>")
			if(data[i].status==i18nShow('sys_selfTest_status_normal') || data[i].status==i18nShow('sys_selfTest_testing')){
				document.getElementById(data[i].id).style.color='green';
			}else{
				document.getElementById(data[i].id).style.color='red';
			}
	}
	
}

function autoMonitor(serverName,id){
	showTip(i18nShow('sys_selfTest_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/sys/selfMonitor/autoServiceMonitor.action",
				async : true,
				data:{"selefMonitorVo.serverName":serverName},
				 beforeSend: function () {
			        	showTip("load");
			        },
				success:(function(data){
					closeTip();
					showTip(i18nShow('sys_selfTest_auto_message')+data.message);
					if(data.status==i18nShow('sys_selfTest_status_normal')){
						document.getElementById(id).style.color='green';
					}else{
						document.getElementById(id).style.color='red';
					}
					document.getElementById(id).innerHTML = data.status;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					closeTip();
					showError(i18nShow('sys_selfTest_auto_fail'));
					document.getElementById(id).innerHTML = i18nShow('sys_selfTest_status_abnormal');
					document.getElementById(id).style.color='red';
				} 
			});
		});
}




//显示mq列表方法
function mqListShow(){
	var path =ctx + "/sys/selfMonitor/getMqServiceList.action";
	$.ajax({
		async : true,
		cache : false,
		type : 'post',
		datatype : "json",
		url : path,  
		data : {},
		error : function() {//请求失败处理函数   
			showTip(i18nShow('compute_res_load_error'));
		},
		success : function(data) { //请求成功后处理函数。
			mqList(data);
		}
	});
	
}

function onloadMq(id){
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/sys/selfMonitor/mqServiceMonitor.action",
		async : true,
		data:{},
		success:(function(data){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(id).style.color='green';
			}else{
				document.getElementById(id).style.color='red';
			}
			document.getElementById(id).innerHTML = data.status;
		} 
	});
}

function mqList(data){
	$("#mq_list").append("<tr><td align='center' colspan='5' style='background:#E4EAEC; font-weight:bold;'>"+i18nShow('sys_selfTest_mqList')+"</td></tr>");
	for(var i=0;i<data.length;i++){
		var number = i+1;
			$("#mq_list").append("<tr id=''><td width='3%' id='number"+i+"' name='number"+i+"'>"+number+"</td>"+
			"<td width='22%' id='serverName"+i+"' value='"+data[i].serverName+"'>"+data[i].serverName+"</td>"+
			"<td width='60%'></td>"+
			"<td value='"+data[i].status+"' id='"+data[i].id+"' window.onload='"+onloadMq(data[i].id)+"'>"+data[i].status+"</td>"+
			"<td><a href='javascript:void(0)' onclick='mqMonitor(\""+data[i].id+"\")'>"+i18nShow('sys_selfTest_test')+"</a></td>"+
			"</tr>")
			if(data[i].status==i18nShow('sys_selfTest_status_normal') || data[i].status==i18nShow('sys_selfTest_testing')){
				document.getElementById(data[i].id).style.color='green';
			}else{
				document.getElementById(data[i].id).style.color='red';
			}
	}
	
}

function mqMonitor(id){
	showTip(i18nShow('sys_selfTest_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/sys/selfMonitor/mqServiceMonitor.action",
				async : true,
				data:{},
				beforeSend: function () {
			        showTip("load");
			    },
				success:(function(data){
					closeTip();
					showTip(i18nShow('sys_selfTest_mq_message')+data.message);
					if(data.status==i18nShow('sys_selfTest_status_normal')){
						document.getElementById(id).style.color='green';
					}else{
						document.getElementById(id).style.color='red';
					}
					document.getElementById(id).innerHTML = data.status;
					
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					closeTip();
					showError(i18nShow('sys_selfTest_mq_fail'));
					document.getElementById(id).style.color='red';
					document.getElementById(id).innerHTML = i18nShow('sys_selfTest_status_abnormal');
				} 
			});
		});
}




//显示bpm列表方法
function bpmListShow(){
	var path =ctx + "/sys/selfMonitor/getBpm.action";
	$.ajax({
		async : true,
		cache : false,
		type : 'post',
		datatype : "json",
		url : path,  
		data : {},
		error : function() {//请求失败处理函数   
			showTip(i18nShow('compute_res_load_error'));
		},
		success : function(data) { //请求成功后处理函数。
			bpmList(data);
		}
	});
	
}

function onloadBpm(id,url){
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/sys/selfMonitor/bpmServerMonitor.action",
		async : true,
		data:{"selefMonitorVo.id":id,
			  "selefMonitorVo.url":url},
		success:(function(data){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(url).style.color='green';
			}else{
				document.getElementById(url).style.color='red';
			}
			document.getElementById(url).innerHTML = data.status;
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			if(data.status==i18nShow('sys_selfTest_status_normal')){
				document.getElementById(url).style.color='green';
			}else{
				document.getElementById(url).style.color='red';
			}
			document.getElementById(url).innerHTML = data.status;
		} 
	});
}

function bpmList(data){
	$("#bpm_list").append("<tr><td align='center' colspan='5' style='background:#E4EAEC; font-weight:bold;'>"+i18nShow('bpmlist')+"</td></tr>");
	for(var i=0;i<data.length;i++){
		var number = i+1;
			$("#bpm_list").append("<tr id=''><td width='3%' id='number"+i+"' name='number"+i+"'>"+number+"</td>"+
			"<td width='22%' id='serverName"+i+"' value='"+data[i].serverName+"'>"+data[i].serverName+"</td>"+
			"<td width='60%' id='url"+i+"' value='"+data[i].url+"'>"+data[i].url+"</td>"+
			"<td value='"+data[i].status+"' id='"+data[i].url+"' window.onload='"+onloadBpm(data[i].id,data[i].url)+"'>"+data[i].status+"</td>"+
			"<td><a href='javascript:void(0)' onclick='bpmMonitor(\""+data[i].id+"\",\""+data[i].url+"\")'>"+i18nShow('sys_selfTest_test')+"</a></td>"+
			"</tr>")
			if(data[i].status==i18nShow('sys_selfTest_status_normal') || data[i].status==i18nShow('sys_selfTest_testing')){
				document.getElementById(data[i].url).style.color='green';
			}else{
				document.getElementById(data[i].url).style.color='red';
			}
	}
}

function bpmMonitor(id,url){
	showTip(i18nShow('sys_selfTest_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/sys/selfMonitor/bpmServerMonitor.action",
				async : true,
				data:{"selefMonitorVo.id":id,
					  "selefMonitorVo.url":url},
				beforeSend: function () {
					showTip("load");
				},
				success:(function(data){
					closeTip();
					showTip(i18nShow('sys_selfTest_bpm_message')+data.message);
					if(data.status==i18nShow('sys_selfTest_status_normal')){
						document.getElementById(url).style.color='green';
					}else{
						document.getElementById(url).style.color='red';
					}
					document.getElementById(url).innerHTML = data.status;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					closeTip();
					showError(i18nShow('sys_selfTest_bpm_fail'));
					document.getElementById(url).style.color='red';
					document.getElementById(url).innerHTML = i18nShow('sys_selfTest_status_abnormal');
				} 
			});
		});
}
