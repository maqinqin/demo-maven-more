<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<script type='text/javascript' src='${ctx}/pages/resmgt/compute/js/computeResMag.js'></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/computegetCmClusterHostInfo.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/computeAllHostCanRelevanceInfo.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/ScanVm.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/virtualSwitch.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/resPoolMag.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/cdpMag.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/cluseterMag.js"></script>
<script type="text/javascript" src="${ctx}/common/echart/echarts.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/pl_platform.js"></script>
<script src="1.js?ver=1"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/count.css"></link>
<style>
#updataVirtualRouterForm p{width:300px; padding:0; padding-left:16px;}
#updataVirtualRouterForm p i{width:111px; text-align:left;}
#updataVirtualRouterForm label{display:block; padding-left:100px;}
#poolUpdateForm  span{height:55px;}
</style>
<script type="text/javascript">
var setting = { callback: { onClick: function (e, treeId, treeNode) { $.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, !treeNode.open); } } };
//四舍五入的方法       how 要保留的小数位数     dight 要四舍五入的数值
function   ForDight(Dight,How)  
{  
            Dight   =   Math.round   (Dight*Math.pow(10,How))/Math.pow(10,How);  
            return   Dight;  
} 
//释放资源池
function relPool(){
	var resPoolId_use = $("#resPoolId_use").val();
	if($("#vmCount_use").val() == 1){
		showTip(i18nShow('compute_res_pool_releaseTip'));
		return;
	}
	showTip(i18nShow('compute_res_pool_releaseTip2'), function() {
	$.ajax({
	    async:false,
	    cache:false,
	    type: 'POST',
	    url:ctx+"/resmgt-compute/pool/relPool.action",
	    data:{"resPoolId_use":resPoolId_use},
	    error: function () {//请求失败处理函数
	    	showError(i18nShow('compute_res_requestError'));
	    },
	    success:function(data){ //请求成功后处理函数。
	    	loadPoolData();
	    	//deleteTreeNode(nodeId);
	    	showTip(i18nShow('compute_res_pool_release_success'));
	    }
	});
	});
	
}

//关闭窗口
function closeVirtualRouterView(){	  
	  $("#updataVirtualRouter").dialog("close");
}


function synchRes(){
	var resPoolId_use = $("#resPoolId_use").val();
	var datacenterId = $("#datacenterId_use").val();
	$.ajax({
	    async:false,
	    cache:false,
	    type: 'GET',
	    url:ctx+"/res/synchRes.mvc",
	    data:{"resPoolId_use":resPoolId_use,"datacenterId":datacenterId},
	    error: function () {//请求失败处理函数
	    	showError(i18nShow('compute_res_requestError'));
	    },
	    beforeSend:(function(data){
			showTip("load");
		}),
	    success:function(data){ //请求成功后处理函数。
	    	closeTip();
	    	if(data.length > 0){
	    		$("#vmNList").dialog({  
	     	 		autoOpen : true,
	    			modal:true,
	    			height:355,
	    			width:655,
	    			title:"纳管虚机",
	    			resizable:false,
	    			close : function(){
	    				//reloadVmInfo(vmId);
	    			}
	         });  
	    		initVmTable();
	    		$("#vmNListListTable").clearGridData();
	    		var vm = null;
	    		for ( var i = 0; i < data.length; i++) {
	    			vm = {
						"name" : data[i].name,
						"option" : '<a href="javascript:;" onclick="viewSaveVm(\''+data[i].deviceId+'\',\''+data[i].name+'\',\''+data[i].resPoolId+'\',\''+data[i].hostId+'\',\''+data[i].cpu+'\',\''+data[i].mem+'\',\''+data[i].disk+'\',\''+data[i].platCode+'\',\''+data[i].projectId+'\',\''+data[i].powerStatus+'\',\''+data[i].ip+'\')">纳管</a>'
					};
					$("#vmNListListTable").jqGrid("addRowData", i, vm);
				}
	    	}else{
	    		showTip('同步成功');
	    		location.reload();
	    	}
	    }
	});
}

function initVmTable(){
	$("#vmNListListTable").jqGrid({
		datatype : 'local',
		height : 450,
		autowidth : true,
		colNames : ['name','操作'],
		colModel : [ {
			name : 'name',
			index : 'name',
			width : 20,
			align : "left"
		}, {
			name : 'option',
			index : 'option',
			width : 15,
			align : "left"
		} ],
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		//pager : '#volumeListPages',
		rownumbers : true,
		viewrecords : true,
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		}
	});
}



function viewSaveVm(deviceId,name,resPoolId,hostId,cpu,mem,disk,platCode,projectId,powerStatus,ip){
	$("#updataVirtualRouter").dialog({
		autoOpen : true,
		modal:true,
		height:300,
		width:400,
		position: "middle",
		title:"纳管",
	 });
	$("#vmdeviceId").val(deviceId);
	$("#vmname").val(name);
	$("#vmresPoolId").val(resPoolId);
	$("#vmhostId").val(hostId);
	$("#vmcpu").val(cpu);
	$("#vmmem").val(mem);
	$("#vmdisk").val(disk);
	$("#vmplatCode").val(platCode);
	$("#vmprojectId").val(projectId);
	$("#vmpowerStatus").val(powerStatus);
	$("#vmip").val(ip);
	$("#appId").change(function(){
		  var appId = $("#appId").val();
		  $.ajax({
			    async:false,
			    cache:false,
			    type: 'GET',
			    url:ctx+"/res/duList.mvc",
			    data:{"appId":appId},
			    error: function () {//请求失败处理函数
			    	showError(i18nShow('compute_res_requestError'));
			    },
			    success:function(data){ //请求成功后处理函数。
			    	$("#duduId").empty();
					$("#duduId").append("<option value='' selected>"+i18nShow('com_select_defalt')+"...</option>");
					$(data).each(function(i,item){
						$("#duduId").append("<option value='"+item.duId+"#"+item.serviceId+"'>"+item.cname+"</option>");
					});
			    }
			});
	});
	
	$("#duduId").change(function(){
		var duduId = $("#duduId").val();
		var s = duduId.split('#');
		$("#vmdu").val(s[0]);
		$("#vmserviceId").val(s[1]);
	});
}

function saveVm(){
	var appId = $("#appId").val();
	var du = $("#vmdu").val();
	if(appId == null|| appId ==''||du == null||du==''){
		showError('不能为空');
		return false;
	}
	$.ajax({
        //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "text",//预期服务器返回的数据类型
            url:ctx+"/res/saveVm.mvc",//url
            data: $('#vmForm').serialize(),
            beforeSend:(function(data){
    			showTip("load");
    		}),
            success: function (data) {
            	closeTip();
                if(data == 'ok'){
                	showTip('纳管成功');
                	closeVirtualRouterView();
                }else if(data == 'exsit'){
                	showError('资源已经纳管');
                	closeVirtualRouterView();
                }else if(data == 'noIp'){
                	showError('ip不存在');
                	closeVirtualRouterView();
                }else{
                	showError(i18nShow('compute_res_requestError'));
                	closeVirtualRouterView();
                }
            },
            error : function() {
            	showError(i18nShow('compute_res_requestError'));
            	closeVirtualRouterView();
            }
        });
}





















































function echartMeterDic(nodeId){
	$("#hide_nodeId").val(nodeId);
	var rmdatacenterId,resPoolId,rmVqCdpId,clusterId,hostId,rmVmId, rmdatacenterIndex,resPoolIndex,cdpqIndex,clusterIndex,hostIndex,rmVmIndex;
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
	var url = ctx+"/compute/getComputeAbstract.action";
	$.ajax({
		async : false,
		cache : false,
		type : "post",
		datatype : "json",
		url : url,  
		data : {"rmdatacenterId":rmdatacenterId,"resPoolId":resPoolId,"rmVqCdpId":rmVqCdpId,"clusterId":clusterId,
			    "hostId":hostId,"rmVmId":rmVmId},
		error : function() {//请求失败处理函数
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) { //请求成功后处理函数。
			$("#resPoolId_use").val(resPoolId);
			$("#datacenterId_use").val(data.rmdatacenterId);
			if(data.rmVmCount > 0){
				$("#vmCount_use").val(1);
				//document.getElementById("pool_rel").style.display="none";
				//$("#pool_rel_div").hide();
        	}else{ 
        		$("#vmCount_use").val(0);
        		//$("#pool_rel").attr("disabled", false);
        		//$("#pool_rel_div").show();
        	}
			var ram = data.ram ;
			var cpu = data.cpu ;
			var ramUsed = data.ramUsed ;
			var cpuUsed = data.cpuUsed ;
			var ramUsable = ram-ramUsed ;
			ramUsable = ForDight(ramUsable,1);
			var cpuUsable = cpu-cpuUsed ;
			cpuUsable = ForDight(cpuUsable,1);
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
		                    "echarts/chart/pie"
		                ],
		                function (ec) {
		                	if(rmdatacenterIndex >= 0){
		                		$("#rmResPoolCount").val(data.rmResPoolCount);
		                	}
		                	if(resPoolIndex >= 0){
		                		$("#rmResPoolCount").val(data.rmCdpCount);
		                	}
		                	if(cdpqIndex >= 0){
		                		$("#rmResPoolCount").val(data.rmRmClusterCount);
		                	}
		                	if(clusterIndex >= 0){
		                		$("#rmResPoolCount").val(data.cmDeviceCount);
		                	}
		                	if(hostIndex >= 0){
		                		$("#rmResPoolCount").val(data.rmVmCount);
		                	}
		                	if(rmVmIndex >= 0){
		                		$("#rmResPoolCount").val(i18nShow('compute_res_nothing'));
		                		$("#cmDeviceCount").val(i18nShow('compute_res_nothing'));
		                	}else{
		                  		$("#cmDeviceCount").val(data.cmDeviceCount);
		                	}
		                	$("#cpu_count").val(data.cpu);
		                  	$("#cpuUsed_count").val(data.cpuUsed);
		                  	$("#rmVmCount").val(data.rmVmCount);
		                  	$("#cId").val(data.cId);
		                  	$("#vId").val(data.vId);
		                  	$("#vchvmType").val(data.vchvmType);
		                  	$("#vcmPoolType").val(data.vcmPoolType);
		                  	$("#dataDeTreType").val(data.dataDeTreType);
				           if(data.cpu && data.cpu != 0){
                               cpuNum = ((data.cpuUsed/data.cpu)*100).toFixed(2);
                               cpuNub = (((data.cpu-data.cpuUsed)/data.cpu)*100).toFixed(2);
				           }else{
                               cpuNum = 0;
                               cpuNub = 0;
				           }
		                   if(data.ram && data.ram != 0){
                               ramNum = ((data.ramUsed/data.ram)*100).toFixed(2);
                               ramNub = (((data.ram-data.ramUsed)/data.ram)*100).toFixed(2);
				           }else{
                               ramNum = 0;
                               ramNub = 0
				           }
		                    var myChart = ec.init(document.getElementById("echart_meterDic_left")); 
		                    var myRightChart = ec.init(document.getElementById("echart_meterDic_right")); 
		                    var cpuOption = {
		                    		   tooltip : {
		                    			   trigger : 'item',
		                    		        formatter: "{a} <br/>{b} : {c}%"
		                    		    },
		                    		    color:['#F47279','#38C0A2'],
		                    		    legend: {  
		                                    orient: 'vertical',  
		                                    x:"72%", 
		                                    y:"38%",   
		                                    data:[{value:cpuNum, name: i18nShow('my_req_det_cpu_used')},
		                                    	  {value:cpuNub, name: i18nShow('my_req_det_cpu_free')}
	                                          ]
		                                },  
		                    		    series : [
		                    		        {
		                    		            name:i18nShow('compute_res_utilization_ratio'),
		                    		            type:'pie',
		                    		            /*splitNumber: 1,        //分割段数，默认为5
		                    		             axisLine: {            //坐标轴线
		                    		                lineStyle: {       //属性lineStyle控制线条样式
		                    		                    color: [[0.2, '#228b22'],[0.8, '#38b'],[1, '#ff3500']], 
		                    		                    width: 3
		                    		                }
		                    		            },
		                    		            axisTick: {           //坐标轴小标记
		                    		                splitNumber: 10,  //每份split细分多少段
		                    		                length :6,        //属性length控制线长
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
		                    		            }, */
		                    		            radius : ['52%','65%'],  
		                                        center: ['52%', '50%'],
		                                        itemStyle : {  //图形样式
		                                            normal : { //normal 是图形在默认状态下的样式；emphasis 是图形在高亮状态下的样式，比如在鼠标悬浮或者图例联动高亮时。
		                                                label : {  //饼图图形上的文本标签
		                                                    show : false  //平常不显示
		                                                },
		                                                labelLine : {     //标签的视觉引导线样式
		                                                    show : false  //平常不显示
		                                                },
		                                            },
		                                            emphasis : {   //normal 是图形在默认状态下的样式；emphasis 是图形在高亮状态下的样式，比如在鼠标悬浮或者图例联动高亮时。
		                                                label : {  //饼图图形上的文本标签
		                                                	formatter: "{c}%",
		                                                    show : true,
		                                                    position : 'center',
		                                                    textStyle : {
		                                                        fontSize : '14',
		                                                        fontWeight : 'bold'
		                                                    }
		                                                }
		                                            }
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
		                    		            data:[{value:cpuNum, name: i18nShow('my_req_det_cpu_used')},
			                                    	  {value:cpuNub, name: i18nShow('my_req_det_cpu_free')}
		                                          ]
		                    		        }
		                    		    ]
		                    	};
		                    var ramOption = {
		                    		   tooltip : {
		                    		        formatter: "{a} <br/>{b} : {c}%"
		                    		    },
		                    		    color:['#FFB980','#5AB1EF'],
		                    		    legend: {  
		                    		    	  orient: 'vertical',  
			                                    x:"73%", 
			                                    y:"38%",  
		                                    data:[{value: ramNum, name: i18nShow('my_req_det_mem_used')},
	                    		                  {value: ramNub, name: i18nShow('my_req_det_mem_free')}
                  		                  ]
		                                },  
		                    		    series : [
		                    		        {
		                    		            name:i18nShow('compute_res_utilization_ratio'),
		                    		            type:'pie',
		                    		            /* splitNumber: 1,        //分割段数，默认为5
		                    		            axisLine: {            //坐标轴线
		                    		                lineStyle: {       //属性lineStyle控制线条样式
		                    		                    color: [[0.2, '#228b22'],[0.8, '#38b'],[1, '#ff3500']], 
		                    		                    width: 3
		                    		                }
		                    		            },
		                    		            axisTick: {           //坐标轴小标记
		                    		                splitNumber: 10,  //每份split细分多少段
		                    		                length :6,        //属性length控制线长
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
		                    		            }, */
		                    		            radius : ['52%','65%'],  
		                                        center: ['52%', '50%'],
		                                        itemStyle : {  //图形样式
		                                            normal : { //normal 是图形在默认状态下的样式；emphasis 是图形在高亮状态下的样式，比如在鼠标悬浮或者图例联动高亮时。
		                                                label : {  //饼图图形上的文本标签
		                                                    show : false  //平常不显示
		                                                },
		                                                labelLine : {     //标签的视觉引导线样式
		                                                    show : false  //平常不显示
		                                                },
		                                            },
		                                            emphasis : {   //normal 是图形在默认状态下的样式；emphasis 是图形在高亮状态下的样式，比如在鼠标悬浮或者图例联动高亮时。
		                                                label : {  //饼图图形上的文本标签
		                                                	formatter: "{c}%",
		                                                    show : true,
		                                                    position : 'center',
		                                                    textStyle : {
		                                                        fontSize : '14',
		                                                        fontWeight : 'bold'
		                                                    }
		                                                }
		                                            }
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
		                    		            data:[{value: ramNum, name: i18nShow('my_req_det_mem_used')},
		                    		                  {value: ramNub, name: i18nShow('my_req_det_mem_free')}
		                    		                  ]
		                    		        }
		                    		    ]
		                    	};
		                        // 为echart对象加载数据 
		                        myChart.setOption(cpuOption);
		                        $('#echart_meterDic_left').append(
		                        		"<div class='pieDetail'><table class='cpuAndRamTable'><tr class='cpuAndRamTr'><td class='cpuAndRamTd' style='width:110px;color:#666'><icms-i18n:label name="res_l_cpuAll"/>:&nbsp;<br/><span style='font-size:19px;'>"+cpu+"</span> <icms-i18n:label name="res_l_cpuNucleus"/></td></tr>"+
		                		        "<tr class='cpuAndRamTr'><td class='cpuAndRamTd' style='width:100px;color:#666'><icms-i18n:label name="res_l_cpuAndRam"/>:&nbsp;<br/><span style='font-size:19px;'>"+cpuUsable+" </span><icms-i18n:label name="res_l_cpuNucleus"/></td></tr><table></div>"
		                        );
		                        myRightChart.setOption(ramOption);
		                        $('#echart_meterDic_right').append(
		                        		"<div class='pieDetail'><table class='cpuAndRamTable'><tr class='cpuAndRamTr'><td class='cpuAndRamTd' style='width:120px; color:#666'><icms-i18n:label name="res_l_memAll"/>:&nbsp;<br/><span style='font-size:19px;'>"+parseInt(ram/1024)+"</span>GB</td></tr>"+
		                		        "<tr class='cpuAndRamTr'><td class='cpuAndRamTd' style='width:120px;color:#666'><icms-i18n:label name="res_l_memAndRam"/>:&nbsp;<br/><span style='font-size:19px;'>"+parseInt(ramUsable/1024)+"</span>GB</td></tr><table></div>"
		                        );
		                        $(".cpuAndRamTable").css('margin-left','29px');
		                        $(".cpuAndRamTable").css('margin-right','0px');
		                        $(".cpuAndRamTable").css('margin-top','-20px');
		     		            $(".cpuAndRamTable").css('margin-bottom','20px');
		                     }
		                  );
		}}
	});
}
</script>

<script type="text/javascript">
function showParameterDiv(divId, title) {
	$("#con_div div").hide();
	$("#" + divId).show();
	$("#lab_title").html(title);
}

function new_tab(tab,divId,divName) { 
		//处理摘要的业务
		if(tab == "new_tab_t"){  	//tab 为li的id
	        $("#new_tab_t").attr("class", "BtCur");//将所有选项置为选中
	        $("#new_tab_pm").attr("class", "BtVCur"); 
	        $("#new_tab_pmlog").attr("class", "BtVCur"); 
	        $("#new_tab_vmlog").attr("class", "BtVCur");
	        $("#new_tab_virtualSwitch").attr("class", "BtVCur");
	        $("#new_tab_vm").attr("class", "BtRCur");//设置当前选中项为选中样式
	        $("#new_tab_vmlog").hide();
	        $("#new_tab_pmlog").hide();
	        $("#new_tab_virtualSwitch").hide();
	        $("#new_tab_body_virtualSwitch").hide();
	        $("#new_tab_inf").show();
	        $("#new_tab_inf_a").show();
	        $("#new_tab_inf_b").show();
	        $("#echart_meterDic_div").show();
	        $("#echart_meterDic_left").show();
	        $("#echart_meterDic_right").show();
	        $("#con_div").show();
	        $("#hiddenDiv").val(divId);
	        
	        if(divId == "cluster_div"){
	        	$("#con_div").attr("class", "sumCon_top_clu");
	        }else{
	        	$("#con_div").attr("class", "sumCon_top");
	        }
	        if(divId == "vm_div"){
	        	$("#new_tab_pm").hide();
	        	$("#new_tab_vm").hide();
	        	$("#new_tab_virtualSwitch").hide();
	        	$("#new_tab_inf_b").hide();
	        	$("#new_tab_vmlog").show();
	        	$("#new_tab_t").attr("class", "BtRlVmCur");
	        }else{
	        	if(divId == "host_div"){
		        	$("#new_tab_pm").hide();
		        	$("#new_tab_pmlog").show();
		        	var hostType = $("#hostType").val();
		        	if(hostType=='VM'){
		        		$("#new_tab_virtualSwitch").show();
		        	}else{
		        		$("#new_tab_virtualSwitch").hide();
		        	}
		        }else{
		        	$("#new_tab_pm").show();
		        	$("#new_tab_pmlog").hide();
		        	 $("#new_tab_virtualSwitch").hide();
		        }
	        	$("#new_tab_vm").show();
	        }
	        showParameterDiv(divId,divName);
	        $("#new_tab_body_pm").hide();
	        $("#new_tab_body_vm").hide();
	        initCmClusterHostInfo();
		}else if(tab == "new_tab_inf"){
			$("#new_tab_t").attr("class", "BtRlVmCur");
		    $("#new_tab_pm").attr("class", "BtCur");
		    $("#new_tab_vm").attr("class", "BtCur");
	        $("#vMwarePage").show();
	        $("#content_pm_div").show();
	        $("#gridTable_pm_div").show();
	        $("#new_tab_body_pm").show();
	        $("#new_tab_inf").hide();
	        $("#new_tab_body_vm").hide();
	        getRmComputeHostListAction();
	        //物理机new_tab_pm
		}else if(tab == "new_tab_pm"){
			$("#new_tab_t").attr("class", "BtVCur");
		    $("#new_tab_pm").attr("class", "BtCur");
		    $("#new_tab_vm").attr("class", "BtRCur");
		    $("#new_tab_pmlog").attr("class", "BtRCur");
		    $("#new_tab_vmlog").attr("class", "BtRCur");
		    $("#new_tab_virtualSwitch").attr("class", "BtRCur");
	        $("#vMwarePage").show();
	        $("#content_pm_div").show();
	        $("#gridTable_pm_div").show();
	        $("#new_tab_body_pm").show();
	        $("#new_tab_inf").hide();
	        $("#new_tab_body_vm").hide();
	        getRmComputeHostListAction();
	        
		}else if(tab == "new_tab_pm"){
			$("#new_tab_t").attr("class", "BtVCur");
		    $("#new_tab_pm").attr("class", "BtCur");
		    $("#new_tab_vm").attr("class", "BtRCur");
		    $("#new_tab_pmlog").attr("class", "BtRCur");
		    $("#new_tab_vmlog").attr("class", "BtRCur");
		    $("#new_tab_virtualSwitch").attr("class", "BtRCur");
	        $("#vMwarePage").show();
	        $("#content_pm_div").show();
	        $("#new_tab_body_pm").show();
	        $("#new_tab_inf").hide();
	        $("#new_tab_body_vm").hide();
	        getRmComputeHostListAction();
		}
		//物理机日志
		else if(tab == "new_tab_pmlog"){
			$("#new_tab_t").attr("class", "BtVCur");
		    $("#new_tab_pm").attr("class", "BtRCur");
		    $("#new_tab_vmlog").attr("class", "BtRCur");
		    $("#new_tab_vm").attr("class", "BtRCur");
		    $("#new_tab_virtualSwitch").attr("class", "BtRCur");
		    $("#new_tab_pmlog").attr("class", "BtCur");
	        $("#vMwarePage").show();
	       // $("#content_pm_div").show();
	        $("#new_tab_body_pm").show();
	        $("#gridTable_notiLog_div").show();
	        $("#new_tab_body_virtualSwitch").hide();
	        $("#new_tab_inf").hide();
	        $("#new_tab_body_vm").hide();
	        getNotiLogList("H");
	        $("#resourceType").val('H');
		}
		//虚拟交换机
		else if(tab == "new_tab_virtualSwitch"){
			$("#new_tab_t").attr("class", "BtVCur");
		    $("#new_tab_pm").attr("class", "BtRCur");
		    $("#new_tab_vmlog").attr("class", "BtRCur");
		    $("#new_tab_vm").attr("class", "BtRCur");
		    $("#new_tab_pmlog").attr("class", "BtRCur");
		    $("#new_tab_virtualSwitch").attr("class", "BtCur");
	        $("#vMwarePage").show();
	        $("#new_tab_body_virtualSwitch").show();
	        $("#new_tab_body_pm").hide();
	        $("#new_tab_inf").hide();
	        $("#new_tab_body_vm").hide();
	        //查询数据显示
	        getVirtualSwitchByHostId();
		}
		//虚拟机日志
		else if(tab == "new_tab_vmlog"){
			$("#new_tab_t").attr("class", "BtVCur");
		    $("#new_tab_pm").attr("class", "BtRCur");
		    $("#new_tab_pmlog").attr("class", "BtRCur");
		    $("#new_tab_vm").attr("class", "BtRCur");
		    $("#new_tab_vmlog").attr("class", "BtCur");
	        $("#vMwarePage").show();
	       // $("#content_pm_div").show();
	        $("#new_tab_body_pm").show();
	        $("#gridTable_notiLog_div").show();
	        $("#new_tab_body_virtualSwitch").hide();
	        $("#new_tab_inf").hide();
	        $("#new_tab_body_vm").hide();
	        getNotiLogList("V");
	        $("#resourceType").val('V');
		}
		else{
			$("#new_tab_t").attr("class", "BtVCur");
		    $("#new_tab_pm").attr("class", "BtVCur");
		    $("#new_tab_vm").attr("class", "BtVRCur");
		    $("#new_tab_pmlog").attr("class", "BtRCur");
		    $("#new_tab_vmlog").attr("class", "BtRCur");
		    $("#new_tab_virtualSwitch").attr("class", "BtRCur");
	        $("#new_tab_body_vm").show();
	        $("#new_tab_inf").hide();
	        $("#new_tab_body_pm").hide();
	        $("#content_vm_div").show();
	        $("#gridTable_virware_div").show();
	        getRmComputeVmListAction("reCeId");
		}
}
</script>
 <script type="text/javascript">
	$(function(){
		
		$(window).scroll(function(){
			//滚动105px，左侧导航固定定位
			if ($(window).scrollTop()>45) {
				$('.tree-div1').css({'position':'fixed','top':0,'height':'100%'})
			}else{
				$('.tree-div1').css({'position':'absolute','top':45})
			};
		});
		//树菜单伸缩
		$('.tree-div1').css('left','20px');
		var expanded = true;
		$('.tree-bar').click(function(){
			if(expanded){
				$('.tree-div1').animate({left:'-25%'},500);
				$('.tree-bar i').addClass('fa-caret-right');
				$('.right-div1').css({'width':'auto','margin-left':0});
			}else{
				$('.tree-div1').animate({left:'20'},500);
				$('.tree-bar i').removeClass('fa-caret-right');
				$('.right-div1').animate({'width':'74%','margin-left':'26%'},500);
			}
			expanded = !expanded;
		});
	
	});
</script> 
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/vmController.js"></script>
<link rel="stylesheet" type="text/css" href="/icms-web/css/new.css"></link>
<title>计算资源池管理</title>
<style type="text/css">
html,body{height:100%}
#poolUpdateDiv .pageFormContent {padding-left:0; padding-right:0; padding-bottom:0;}
#poolUpdateDiv i {text-align:left; padding-right:3px;}
#poolUpdateDiv p{width:600px; margin-bottom:5px; margin-left:18px; height: 42px;}
#poolUpdateDiv p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#poolUpdateDiv .btnbar{padding-top:0;}
#poolUpdateDiv textarea{width:466px; text-indent:4px; padding:4px 0 ;border: 1px solid #d5d5d5;color: #615d70;background-color: #f5f5f5;padding: 1px 4px 0px 5px;font-size: 12px;box-shadow: 1px 1px 2px #ededed;}
 label{display:block; padding-left:110px;}

#cdpUpdateDiv p{width:280px; padding:0; padding-left:16px;height:25px;}
#cdpUpdateDiv p i{width:80px; text-align:left;}
#cdpUpdateDiv label{display:block; padding-left:100px;}

#clusterUpdateDiv p{width:600px; margin-bottom:5px; margin-left:16px;height:25px;}
#clusterUpdateDiv i {text-align:left; padding-right:3px;}
#clusterUpdateDiv p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#clusterUpdateDiv .winbtnbar{margin-right:34px; width:566px;}
.tree-fixed{position:fixed;top:0px;}
.pieDetail{position: absolute;left:10px;top:66px;}
/* .filters-box{background:#fff;padding:10px 20px;}
.provider-filter {
    background: #eee;
    padding: 5px 15px;
    -webkit-border-radius: 25px;
    -moz-border-radius: 25px;
    border-radius: 25px;
    margin-left: 5px;
    text-decoration: none;
}
.main1 a.provider-filter:hover {
    -webkit-transition: color .2s ease-in;
    background: #1c82b4;
    color:#fff;
}
.provider {
    padding: 10px 0;
    color: #777;
    vertical-align: middle;
    margin-right: 10px;
}
.provider-filter {
    margin-left: 4px;
    padding: 5px 10px;
    margin-bottom: 4px;
}
.main1 a.provider-filter.selected {
    background: #1f90c8;
    color: #fff;
}*/

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
<input type="hidden" id="resPoolId_use"/>
<input type="hidden" id="datacenterId_use"/>
<input type="hidden" id="vmCount_use"/>
<div id="div_tip"></div>

	<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="res_title_computing_resPool"/></div>
				<div class="WorkSmallBtBg">
					<small>
					<icms-i18n:label name="res_desc_computing_resPool"/>
					</small>
				</div>
			</h1>
		</div>
<!----------选择条件--------- -->

<!-- <div class="filters-box">

		<div class="provider-filters">
			<div class="provider all-provider">数据中心：
				<a href="#" class="provider-filter selected">生产数据中心</a>
				<a href="#" class="provider-filter">灾备数据中心</a>
				<a href="#" class="provider-filter">测试数据中心</a>
			</div>
			<div class="provider system-provider"style="display: none;">资源池：
				<a href="#" class="provider-filter selected">ironic</a>
				<a href="#" class="provider-filter ">openstack_kvm</a>
				<a href="#" class="provider-filter ">powervc</a>
				<a href="#" class="provider-filter ">X86vmware</a>
			</div>
			<div class="provider market-provider" style="display: none;">集群：
				<a href="#" class="provider-filter selected">ironic</a>
			</div>
			<div class="provider market-provider" style="display: none;">物理机：
				<a href="#" class="provider-filter">全部</a>
				<a href="#" class="provider-filter">6C4BA26D-4005-E711-8DAF-F09838F9B920</a>
			</div>
			<div class="provider market-provider" style="display: none;">虚拟机：
				<a href="#" class="provider-filter">全部</a>
				<a href="#" class="provider-filter">dcironic2001</a>
			</div>
		</div>
		</div> -->
  <div id="treeDiv" class="tree-div tree-div1" style="height: 93%;">
  <a href="javascript:" class="tree-bar"><i class="fa fa-caret-left"></i></a>
  <div class="pageFormContent tree-search" style="padding-bottom:5px;">
  <p style="width:auto;margin:0px;">
  	  	<select id="sel_type" class="selInput" style=" float: left;width: 80px">
  	  		<option value="dc"><icms-i18n:label name="bm_l_data_center"/></option>
  	  		<option value="pool"><icms-i18n:label name="res_l_pool"/></option>
  	  		<option value="cdp"><icms-i18n:label name="res_l_cdp"/></option>
  	  		<option value="cluster"><icms-i18n:label name="res_l_cluster"/></option>
  	  		<option value="host"><icms-i18n:label name="res_l_comput_host"/></option>
  	  		<option value="vm"><icms-i18n:label name="res_l_comput_vm"/></option>
  	  	</select>
  	  	<input type="text" id="txt_search" size="30" class="textInput" style="width: 43%;float: left;"/>
  	  	<span class="input-group-btn"><a href="#" class="btn-default"><i class="fa fa-search" style="line-height:15px;width:auto;" onclick="searchTreeByCname()"></i></a></span>
	  	<%-- <input type="button" class="btn_search_s" value='<icms-i18n:label name="common_btn_query"/>' style="margin-top: 3px;float: left; margin-left:5px;" onclick="searchTreeByCname()" /> --%>
  </p>
	  </div>
	  <ul id="treeRm" class="ztree clear" style="padding:0 0 0 5px;overflow-y:auto;height:90%;"></ul>
  </div>
  <div id="rightContentDiv" class="right-div right-div1" style="width: 74%;height: 93%;">
  	<input id="cId" name="cId" type="hidden"/>
  	<input id="vId" name="vId" type="hidden"/>
  	<input id="ip" name="ip" type="hidden"/>
  	<input id="cmCpuUsed" name="cmCpuUsed" type="hidden"/>
  	<input id="cmMemUsed" name="cmMemUsed" type="hidden"/>
  	<input id="vcCpuUsed" name="vcCpuUsed" type="hidden"/>
  	<input id="vcMemUsed" name="vcMemUsed" type="hidden"/>
  	<input id="vchvmType" name="vchvmType" type="hidden"/>
  	<input id="vcmPoolType" name="vcmPoolType" type="hidden"/>
  	<input id="dataDeTreType" name="dataDeTreType" type="hidden"/>
  	<input id="dataStoreId" name="dataStoreId" type="hidden"/>
  	  	<div id="xd_div" style="display:none;font-size:18px;">
  		<table border="0" cellpadding="0" cellspacing="0" class="pagelist" style="float:left;width:40%" >
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/datacenter.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="bm_l_data_center"/></label></td>
			</tr>
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/respoolcou.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="res_l_comput_pool"/></label></td>
			</tr>
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/cdp.png" /></th><td><label style="font-size: 14px; padding-left:0;">CDP</label></td>
			</tr>
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/cluster.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="res_l_comput_cluster"/></label></td>
			</tr>
			
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/host.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="res_pool_operate_pm"/></label></td>
			</tr>
			
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/VM_Unknow.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="res_l_build_virtual"/></label></td>
			</tr>	
			
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/VM_Start.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="res_l_start_virtual"/></label></td>
			</tr>
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/VM_Hangup.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="res_l_hangup_virtual"/></label></td>
			</tr>
			
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/VM_Stop.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="res_l_stop_virtual"/></label></td>
			</tr>			
			</table>
  	</div>
  	
  	    
  <div id="new_tab" class="sumWrap" style="display: none;">
    <ul id="tb" class="tb2">
        <li id="new_tab_t" class="BtCur2" onclick="new_tab('new_tab_t',$('#hiddenDiv').val(),'')"><icms-i18n:label name="res_l_comput_abstract"/></li>
        <li id="new_tab_pm" class="BtVCur2" onclick="new_tab('new_tab_pm',$('#hiddenDiv').val(),'')"><icms-i18n:label name="res_l_comput_host"/></li>
        <li id="new_tab_vm" class="BtRCur2" onclick="new_tab('new_tab_vm',$('#hiddenDiv').val(),'')"><icms-i18n:label name="res_l_comput_vm"/></li>
    	<li id="new_tab_pmlog" class="BtRCur2" onclick="new_tab('new_tab_pmlog',$('#hiddenDiv').val(),'')"><icms-i18n:label name="res_l_comput_hostLog"/></li>
    	<li id="new_tab_vmlog" class="BtRCur2" onclick="new_tab('new_tab_vmlog',$('#hiddenDiv').val(),'')"><icms-i18n:label name="res_l_comput_vmLog"/></li>
    	<li id="new_tab_virtualSwitch" class="BtRCur2" onclick="new_tab('new_tab_virtualSwitch',$('#hiddenDiv').val(),'')"><icms-i18n:label name="res_l_comput_virtSwitch"/></li>
    </ul>
    <div id="new_tab_inf" style="display:block" class="sumCon">
    	<jsp:include page="briefBase.jsp" flush="true"/>
    </div>
    <div id="new_tab_body_pm" style="display:none" class="sumCon">
    	<jsp:include page="vMware.jsp" />
    </div>
    <div id="new_tab_body_vm" style="display:none" class="sumCon">
    	<jsp:include page="vIRware.jsp" />
    </div>
     <div id="new_tab_body_virtualSwitch" style="padding-top:15px;padding-left:15px;display:none;border-style:solid; border-width:1px; border-color:#D5D5D5;margin-left:20px;margin-top: 30px;background-color:#FAFBFC;padding-bottom: 30px;height: 320px;overflow:auto;">
    	<ul id="treeDemo" class="ztree"></ul>
    </div>
    <div id="new_tab_body_pmlog" style="width:300px; height:200px; background:#f00; display:none;"></div>
    <div id="new_tab_body_vmlog" style="width:300px; height:200px; background:#ff0;display:none;"></div>
  </div>
  		
  	<div id="poolUpdateDiv" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="poolUpdateForm">
			<input type="hidden" id=pl_id name="pl_id" />
			<input type="hidden" id="pl_isActive" name="pl_isActive" />
			<input type="hidden" id="poolMethod" name="poolMethod" />
			<input type="hidden" id="pl_datacenter_id" name="pl_datacenter_id" />
			<input type="hidden" id="pl_pool_type" name="pl_pool_type"/>
			<input type="hidden" id="pl_status" name="pl_status"/>
			<input type="hidden" id="pl_checkEname" name="pl_checkEname"/>
			<input type="hidden" id="pl_checkName" name="pl_checkName"/>
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_datastore_name"/>：</i>
				<input type="text" id="pl_pool_name" name="pl_pool_name" class="textInput" />
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_comput_poolEname"/>：</i><input type="text" id="pl_ename" name="pl_ename" class="textInput"/>
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bm_l_platform_type"/>：</i>
								<icms-ui:dic id="pl_platformType" name="pl_platformType"  attr="class='selInput'" showType="select" sql="SELECT PLATFORM_ID AS value, PLATFORM_NAME AS name FROM RM_PLATFORM WHERE IS_ACTIVE = 'Y'" />
				
<%-- 					<icms-ui:dic id="pl_platformType" name="pl_platformType" showType="flatselect" attr="style='width: 132px;'" --%>
<%--  					sql="SELECT PLATFORM_ID AS value, PLATFORM_NAME AS name FROM RM_PLATFORM WHERE IS_ACTIVE = 'Y'"/> --%>
			</span>	
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_comput_serviceType"/>：</i>
				
<%-- 					<icms-ui:dic id="pl_serviceType" name="pl_serviceType" kind="DU_SEV_TYPE" attr="style='width: 80px;'" showType="flatselect" /> --%>
				<icms-ui:dic id="pl_serviceType" name="pl_serviceType" kind="DU_SEV_TYPE" attr="class='selInput'" showType="select" />
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_comput_secureArea"/>：</i>
				
			
				<!-- 	<icms-ui:dic id="pl_secureAreaType" name="pl_secureAreaType" showType="select" attr="style='width: 132px;'"
 					sql="SELECT SECURE_AREA_ID AS value, SECURE_AREA_NAME AS name FROM RM_NW_SECURE_AREA WHERE IS_ACTIVE = 'Y'"/>
				 -->
				<select id="pl_secureAreaType" name="pl_secureAreaType"   onchange="selectTierByArea(this.value)" class="selInput"><option value=''><icms-i18n:label name="l_common_select"/>...</option></select>
			</span>		
			<span class="updateDiv_list">
				<i><icms-i18n:label name="res_l_comput_secureLayer"/>：</i>
				
				<!-- 
					<icms-ui:dic id="pl_secureLayer" name="pl_secureLayer" showType="select" attr="style='width: 132px;'"
 					sql="SELECT SECURE_TIER_ID AS value, SECURE_TIER_NAME AS name FROM RM_NW_SECURE_TIER WHERE IS_ACTIVE = 'Y'"/>
				-->
				<select id="pl_secureLayer" name="pl_secureLayer"  class="selInput"><option value='' selected><icms-i18n:label name="res_l_comput_select"/>...</option></select>
				
				</span>
			</p>
			<!-- style="display: none;" -->
			<p id="availablePartition" >
			<span class="updateDiv_list">
				<i><icms-i18n:label name="res_l_comput_availableZone1"/>：</i>
					<%-- <icms-ui:dic id="pl_availablePartition" name="pl_availablePartition" attr="class='selInput'" showType="select" 
					sql="SELECT ID AS value, NAME AS name FROM RM_AVAILABILITY_ZONE WHERE IS_ACTIVE = 'Y'" /> --%>
				<select id="pl_availablePartition" name="pl_availablePartition"  class="selInput"><option value='' selected><icms-i18n:label name="res_l_comput_select"/>...</option></select>

			</span>	
			<span class="updateDiv_list">
				<i><icms-i18n:label name="res_l_comput_hostType"/>：</i>
					<icms-ui:dic id="pl_hostType" name="pl_hostType" attr="class='selInput'" showType="select" 
					sql="SELECT HOST_TYPE_ID AS value, HOST_TYPE_NAME AS name FROM RM_HOST_TYPE WHERE IS_ACTIVE = 'Y'" />
			</span>	
			</p>
			<p>
				<i><icms-i18n:label name="res_l_comput_remark"/>：</i>
				<textarea  id="pl_remark" name="pl_remark"  align="left" style="width: 455px; height:40px;"></textarea>
			</p>
		<p class="winbtnbar btnbar1" style="padding-right:20px; width:569px; margin-bottom:20px;">
			<label id="sp_error_tip"></label>
			<input type="button" class="btn btn_dd2 btn_dd3 " onclick="closePoolView()" title='<icms-i18n:label name="commom_btn_cancel"/>' value='<icms-i18n:label name="commom_btn_cancel"/>' />
			<input type="button" class="btn btn_dd2 btn_dd3 " onclick="saveOrUpdatePoolBtn()" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' />
		</p>
		</form>
	</div>
  	
	<div id="cdpUpdateDiv"   style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="cdpUpdateForm">
			<input type="hidden" id=cdp_id name="cdp_id" />
			<input type="hidden" id="cdp_isActive" name="cdp_isActive" />
			<input type="hidden" id="cdpMethod" name="cdpMethod" />
			<input type="hidden" id="cdp_resPoolId" name="cdp_resPoolId" />
			<input type="hidden" id="cdp_status" name="cdp_status"/>
			<input type="hidden" id="cdp_platformType" name="cdp_platformType"/>
			<input type="hidden" id="cdp_datacenterId" name="cdp_datacenterId" />
			<input type="hidden" id="cdpCheckName" name="cdpCheckName" />
			<input type="hidden" id="cdpCheckEname" name="cdpCheckEname" />
			<p>
				<i><font color="red">*</font><icms-i18n:label name="res_l_datastore_name"/>：</i>
				<input type="text" id="cdp_cdpName" name="cdp_cdpName" class="textInput" style="width:170px;" />
			</p>
			<p>
				<i><font color="red">*</font><icms-i18n:label name="res_l_comput_poolEname"/>：</i>
				<input type="text" id="cdp_ename" name="cdp_ename" class="textInput" style="width:170px;" />
			</p>
			<p>
				<i><icms-i18n:label name="res_l_comput_remark"/>：</i>
				<textarea  id="cdp_remark" align="left" style="width:179px; height:40px; box-shadow: 1px 1px 2px #ededed; background-color: #f5f5f5;border: 1px solid #d5d5d5;"></textarea>
			</p>
			<p class="winbtnbar btnbar1" style="padding-right:20px; width:276px; margin-bottom:20px;">
				<label id="sp_error_tip"></label>
				<input type="button" class="btn btn_dd2 btn_dd3" onclick="closeCdpView()" title='<icms-i18n:label name="commom_btn_cancel"/>'  value='<icms-i18n:label name="commom_btn_cancel"/>' style="margin-left: 5px;" />
				<input type="button" class="btn btn_dd2 btn_dd3" onclick="saveOrUpdateCdpBtn()" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' style="margin-right: 5px;margin-left: 5px;" />
			</p>
		</form>
	</div>
	
	
  	<div id="xd_div" style="display:none;font-size:18px;margin-top: 180px;">这里将来放本功能的向导图片或相关文字^~^</div>
	
	<div id="testDiv" style="display: block">
	
	</div>
  	
	<div id="gridDiv" style="display: none;">
	</div>
	

	<!-- 集群管理主机时，需要输入用户名和密码 -->
	<div id="setingDiv" class="pageFormContent" style="display: none">
		<input type="hidden" id="objectId" name="objectId" />
<!-- 		<table id="updateTab" class="pagelist" style="width:100%;float:left;"> -->
			<p style="width: 100%">
				<i><font color="#FF0000">*</font><icms-i18n:label name="res_l_comput_userName"/>:</i><input type="text" id="username" class="textInput" />
			</p>
			<p style="width: 100%">
				<i><font color="#FF0000">*</font><icms-i18n:label name="res_l_comput_password"/>:</i><input type="password" id="password" class="textInput" />
			</p>
			<p style="width: 100%" id="isManuallySetIP_P">
				<input type="checkbox" id="isManuallySetIP" name="isManuallySetIP"></input>&nbsp;
				<font style="margin-right: 2px;"><icms-i18n:label name="res_l_comput_vmManageIP"/>:</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" id="manageIP" class="textInput" disabled="disabled" style="width: 155px"/>
			</p>
			<p style="width:288px; margin-right:40px;" id="operation" class="btnbar1" >
				<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="commom_btn_cancel"/>' title='<icms-i18n:label name="commom_btn_cancel"/>' onclick="closeSetingDiv()" style="margin-right: 5px;"/>
				<input type="button" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' class="btn btn_dd2 btn_dd3" onclick="setingBtn()"/>
			</p>
	</div>

	<!-- 更新集群信息 -->
	<div id="clusterUpdateDiv" style="display:none;background-color:white;"   class="pageFormContent" >
		<form action="" method="post" id="clusterUpdateForm">
		<table id="clusterUpdateTab"  border="0" class="pagelist" style="float:left; margin:0;" > 
			<input type="hidden" id="cluster_isActive" name="cluster_isActive" />
			<input type="hidden" id="clusterMethod" name="clusterMethod" />
			<input type="hidden" id="cluster_cdpId" name="cluster_cdpId" />
			<input type="hidden" id="cluster_status" name="cluster_status"/>
			<input type="hidden" id="cluster_createUser" name="cluster_createUser"/>
			<input type="hidden" id="cluster_updateUser" name="cluster_updateUser" />
			<input type="hidden" id="clusterCheckName" name="clusterCheckName"/>
			<input type="hidden" id="clusterCheckEname" name="clusterCheckEname" />
               <p>
               <span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="res_l_datastore_name"/>：</i><input type="text" id="u_cluster_Name" name="u_cluster_Name" class="textInput" ></input>
				</span>
				 <span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="res_l_comput_poolEname"/>：</i><input type="text" id="u_cluster_ename" name="u_cluster_ename" class="textInput"></input>
				</span>
				</p>
				<p>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="res_l_comput_vmType"/>：</i><select id="u_vmType" name="u_vmType" class="selInput" ></select>
				</span>	
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="res_l_comput_vmDistriType"/>：</i><icms-ui:dic id="u_vmDistriType" name="u_vmDistriType" kind="VM_DISTRI_TYPE" showType="select" attr="class='selInput'" />
				</span>
				</p>
				<%-- <p>
					<i><font color="red">*</font>存储类型：</i>
						<icms-ui:dic id="u_datastoreType" name="u_datastoreType" kind="DATASTORE_TYPE" showType="select" attr="class='selInput'" />
				</p> --%>
				<p>
				 <span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="res_l_comput_networkConvergence"/>：</i>
					<select id="u_networkConvergence" name="u_networkConvergence" class="selInput" ></select>
				 </span>
				 <span class="updateDiv_list">
					<i><icms-i18n:label name="res_l_comput_manageServer"/>：</i>
					<select id="u_manageServer" name="u_manageServer" class="selInput" ></select>
				 </span>
				</p>
				<p>
				 <%-- <span class="updateDiv_list" style="float:left;">
					<i><icms-i18n:label name="res_l_comput_manageServerBak"/>：</i>
					<select id="u_manageServerBak" name="u_manageServerBak" class="selInput"></select>
				</span> --%>
				
				<%-- <p id="hide_storage"><i><font color="red">*</font>存储设备</i><icms-ui:dic id="u_storage_id" name="u_storage_id"
								showType="select" attr="class='selInput'"
								sql="SELECT D.ID AS value,D.DEVICE_NAME AS name  FROM CM_STORAGE C,RM_RES_STORAGE_CHILD_POOL R,CM_DEVICE D  WHERE C.STORAGE_CHILD_POOL_ID = R.STORAGE_CHILD_RES_POOL_ID AND D.ID = C.ID AND R.STORAGE_APP_TYPE_CODE = 'VMWare'" />
				</p> --%>
				<span class="updateDiv_list" style="float:left;">
					<i><icms-i18n:label name="res_l_comput_remark"/>：</i><textarea id="u_cluster_remark"  name="u_cluster_remark" style="width:164px; height:34px; border: 1px solid #d5d5d5; background-color: #f5f5f5; "></textarea>
				</span>
				</p>
			<p class="winbtnbar btnbar1" style="margin-bottom:20px;">
				<label id="sp_error_tip"></label>
				<input type="button" id="btnAddSp" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="commom_btn_cancel"/>' value='<icms-i18n:label name="commom_btn_cancel"/>' onclick="clusterUpdateDiv()" style="margin-left: 0px; " />
				<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="saveOrUpdateClusterBtn()" title='<icms-i18n:label name="common_btn_save"/>'  value='<icms-i18n:label name="common_btn_save"/>' style="margin-right: 5px;margin-left: 5px;" />
			</p>
		</table>
		</form>
	</div>
		<!-- <div id="dc_div" style="display: none">
			<table id="tab2" border="0" align="center">
			<tr> 
				<td colspan="2" align="center">
					<input type="button" id="pool_add" value="新建资源池" />
				</td>
			</tr>  		
		</table>
  	</div> -->
	
 </div>
 <input type="hidden" id=cluster_id name="cluster_id" />
 	<div id="vmNList" style="display: none">
  		<div class="panel clear" id="vtableDiv">
			<table id="vmNListListTable"></table>
		</div>
  	</div>
  	
  	<div id="updataVirtualRouter" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="vmForm">
				<input type="hidden" id="vmdeviceId" name="deviceId" />
				<input type="hidden" id="vmname" name="name" />
				<input type="hidden" id="vmresPoolId" name="resPoolId" />
				<input type="hidden" id="vmhostId" name="hostId" />
				<input type="hidden" id="vmcpu" name="cpu" />
				<input type="hidden" id="vmmem" name="mem" />
				<input type="hidden" id="vmdisk" name="disk" />
				<input type="hidden" id="vmplatCode" name="platCode" />
				<input type="hidden" id="vmprojectId" name="projectId" />
				<input type="hidden" id="vmpowerStatus" name="powerStatus" />
				<input type="hidden" id="vmdu" name="duId" />
				<input type="hidden" id="vmserviceId" name="serviceId" />
				<input type="hidden" id="vmip" name="ip" />
				<p>
					<i><font color="red">*</font>应用系统：</i>
					<%-- <select id="appId" class="selInput" name="appId" 
					onchange="selectTierByArea1(this.value)">
						<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
					</select> --%>
					<icms-ui:dic  name="appId" id="appId" sql="select a.APP_ID as value,a.CNAME as name from app_info a,sys_role_manage b where a.APP_ID=b.APP_INFO_ID" showType="select" attr="class='selInput'" />
				</p>
				<p>
					<i><font color="red">*</font>服务器角色：</i>
					<select id="duduId" class="selInput" name="duduId">
						<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
					</select>
				</p>
			<p class="winbtnbar btnbar1" style="text-align:right; width:289px; margin-bottom:20px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeVirtualRouterView()" style="margin-left: 0px">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveVm()" style="margin-right: 5px">
			</p>
		</form>
	</div>
  	
</body>
</html>