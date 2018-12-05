<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><icms-i18n:label name="show_title"/></title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<link href="${ctx}/css/public.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="../jquery/js/echarts-plain.js"></script>
<script type="text/javascript">
function setEcharts(){
	var labelTop = {
		    normal : {
		        label : {
		            show : true,
		            position : 'center',
		            textStyle: {
		                baseline : 'bottom'
		            }
		        },
		        labelLine : {
		            show : false
		        }
		    }
		};
		var labelBottom = {
		    normal : {
		        color: '#ccc',
		        label : {
		            show : true,
		            position : 'center',
		            formatter : function (a,b,c){return 100 - c + '%'},
		            textStyle: {
		                baseline : 'top'
		            }
		        },
		        labelLine : {
		            show : false
		        }
		    },
		    emphasis: {
		        color: 'rgba(0,0,0,0)'
		    }
		};
		var radius = [40, 55];
}
//处理按钮 
function dealRequest(url, todoId) {
	$.post(ctx+"/request/base/todoStartDeal.action", {todoId:todoId}, function (data) {
		if(data.result == "success") {
			var targetUrl = "";
			if(url.indexOf("?") > 0) {
				targetUrl = ctx + "/" + url + "&todoId=" + todoId;
			} else {
				targetUrl = ctx + "/" + url + "?todoId=" + todoId;
			}
			window.location.href = targetUrl;
		}
	});
}
//操作按钮
function lookupWorkflow(srId) {
	$.post(ctx+"/request/base/findInstanceIdBySrId.action", {srId:srId}, function(data) {
		window.location.href = ctx+"/pages/workflow/instance/processInstance.jsp?state=view&instanceId="+data.result;
	});
}
//最新实施完成的申请
function NewestComplete(){
	$.post(ctx + '/request/base/findNewestCompleteRequest.action', {}, function(result) {
		if(result.length>0){
			for(var i=0 ; i<result.length ; i++) {
				var dateobj=new Date(result[i].closeTime.time);
				$("#NewestCompleteTab").append('<tr><td>'+result[i].srCode+'</td><td>'+dateobj.toLocaleDateString()+"  "+dateobj.toLocaleTimeString()+'</td><td>'+result[i].appName+'</td><td>'+statusHtml(result[i].srStatus)+'</td><td>'+"<input type='button' class='btn_apply_s' title='申请进度' onclick=lookupWorkflow('"+result[i].srId+"') />"+'</td></tr>');
			}
		}else{
			$("#NewestCompleteTab").append('<tr><td>无数据</td></tr>');
		}
		
	});
}
//我最新的服务申请
function NewestCreate(){
	$.post(ctx + '/request/base/findNewestCreateRequest.action', {}, function(result) {
		if(result.length>0){
			for(var i=0 ; i<result.length ; i++) {
				var dateobj=new Date(result[i].createTime.time);
				$("#NewestCreateTab").append('<tr><td>'+result[i].srCode+'</td><td>'+dateobj.toLocaleDateString()+"  "+dateobj.toLocaleTimeString()+'</td><td>'+result[i].appName+'</td><td>'+statusHtml(result[i].srStatus)+'</td><td>'+"<input type='button' class='btn_apply_s' title='申请进度' onclick=lookupWorkflow('"+result[i].srId+"') />"+'</td></tr>');
			}
		}else{
			$("#NewestCreateTab").append('<tr><td>无数据</td></tr>');
		}
		
	});
}
//最新待我处理的申请
function NewestWaitDeal(){
	$.post(ctx + '/request/base/findNewestWaitDealRequest.action', {}, function(result) {
		if(result.length>0){
			for(var i=0 ; i<result.length ; i++) {
				var dateobj=new Date(result[i].createTime.time);
				$("#NewestWaitDealTab").append('<tr><td>'+result[i].srCode+'</td><td>'+dateobj.toLocaleDateString()+"  "+dateobj.toLocaleTimeString()+'</td><td>'+result[i].appName+'</td><td>'+statusHtml(result[i].srStatus)+'</td><td>'+"<input type='button' class='btn_config_s' title='处理' onclick=dealRequest('"+result[i].pageUrl+"','"+result[i].todoId+"') />"+'</td></tr>');
			}
		}else{
			$("#NewestWaitDealTab").append('<tr><td>无数据</td></tr>');
		}
		
	});
}

//最新入库的设备
function DeviceDiagram(){
	$.post(ctx + '/resmgt-common/device/selectDeviceDiagramInfo.action', {}, function(result) {
		if(result.length>0){
			for(var i=0 ; i<result.length ; i++) {
				var dateobj = new Date(result[i].createDatetime.time);
				$("#DeviceDiagramTab").append('<tr><td>'+dateobj.toLocaleDateString()+" "+dateobj.toLocaleTimeString()+'</td><td>'+result[i].deviceType+'</td><td>'+result[i].sn+'</td><td>'+result[i].poolName+'</td></tr>');
			}
		}else{
			$("#DeviceDiagramTab").append('<tr><td>无数据</td></tr>');
		}
		
	});
}

//IP地址 使用情况
function findRmNwCclass(){
	$.post(ctx + '/network/findRmNwCclassFullVoListAct.action', {}, function(result) {
		if(result.length>0){
			for(var i=0 ; i<result.length ; i++) {
				//$("#findRmNwCclassTab").append('<tr><td>'+result[i].datacenterName+'</td><td>'+result[i].bclassName+'</td><td>'+result[i].ipNum+'</td><td>'+result[i].vIpNum+'</td><td>'+result[i].hIpNum+'</td><td>'+result[i].mIpNum+'</td><td>'+result[i].pIpNum+'</td><td>'+result[i].iLoIpNum+'</td><td>'+result[i].vmoIpNum+'</td><td>'+result[i].priIpNum+'</td><td>'+result[i].fsp1Num+'</td><td>'+result[i].fsp2Num+'</td></tr>');
				$("#findRmNwCclassTab").append('<tr><td>'+result[i].datacenterName+'</td><td>'+result[i].bclassName+'</td><td>'+result[i].ipNum+'</td><td>'+result[i].vIpNum+'</td><td>'+result[i].hIpNum+'</td><td>'+result[i].pIpNum+'</td><td>'+result[i].iLoIpNum+'</td><td>'+result[i].vmoIpNum+'</td></td></tr>');
			}
		}else{
			$("#findRmNwCclassTab").append('<tr><td>无数据</td></tr>');
		}
		
	});
}

//获取虚拟机类型对应的虚拟机数量
function getVmTypeNum(){
	$.post(ctx + '/resmgt-common/device/selectVmTypeNumInfo.action', {}, function(result) {
		if(result.length>0){
			var dateValue=[];
			var Xvalue=[];
			for(var i=0;i<result.length;i++){
				dateValue[i]={'value':result[i].vmNum,'name':result[i].vmType};
			}
			for(var i=0 ; i<result.length ; i++) {
				Xvalue[i]=result[i].vmType;
			}
			var myChartTop = echarts.init(document.getElementById('topss')); 
			setEcharts();
			var options = {
					/**title : {
				        text: '计算资源图',
				        textStyle: {
								            fontWeight: 'normal',
								            color: '#33C2FE',
								            fontFamily:'微软雅黑'       // 主标题文字颜色
					    }
				    
				    },*/
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient : 'vertical',
				        x : 'right',
				        data:Xvalue
				    },
				    //color:['#00448a','#0580b9','#28c6b9','#84e6f1','#dddddd'],
				    calculable : true,
				    series : [
				        {
				            name:'虚拟机类型',
				            type:'pie',
				            radius : ['50%', '70%'],
				            itemStyle : {
				                normal : {
				                    label : {
				                        show : false
				                    },
				                    labelLine : {
				                        show : false
				                    }
				                },
				                emphasis : {
				                    label : {
				                        show : true,
				                        position : 'center',
				                        textStyle : {
				                            fontSize : '30',
				                            fontWeight : 'bold'
				                        }
				                    }
				                }
				            },
				            data:dateValue
				        }
				    ]
				};
			myChartTop.setOption(options);
		}else{
			//$("#DeviceDiagramTab").append('<tr>无数据sadfa</tr>');
		}
		
	});
}
//应用系统服务器柱状图
function AppSysVirtual(){
	$.post(ctx+'/request/base/findAppSysVirtualServer.action', {}, function(result) {
		var Xvalue=[];//得到X轴数值
		var Yvalue=[];//得到Y轴数值
		if(result.length>0){
			for(var i=0 ; i<result.length ; i++) {
				Xvalue[i]=result[i].appCName;
				Yvalue[i]=result[i].deviceNums;
			}
			var myChartTopss = echarts.init(document.getElementById('topssss'));
			setEcharts();
			var optionsss = {
								/**title : {
							        text: '应用系统服务器图',
							        textStyle: {
								            fontWeight: 'normal',
								            color: '#33C2FE',
								            fontFamily:'微软雅黑'       // 主标题文字颜色
								    }
							    },*/
			                    legend: {
			                    	x : 'right',
			                        data:['虚拟机数量']
			                    },
			                    toolbox: {
							        show : true,
							        orient : 'vertical',
							        y : 'center',
							        feature : {
							            magicType : {show: true, type: ['line', 'bar']},
							        }
							    },
							    //color:['#00448a','#0580b9','#28c6b9','#84e6f1','#dddddd'],
			                    xAxis : [
			                        {
			                            type : 'category',
			                            data : Xvalue
			                        }
			                    ],
			                    yAxis : [
			                        {
			                            type : 'value'
			                        }
			                    ],
			                    series : [
			                        {
			                            "name":"虚拟机数量",
			                            "type":"bar",
			                            "data":Yvalue
			                        }
			                    ]
			                };
			myChartTopss.setOption(optionsss);
		}else{
			//$("#NewestWaitDealTab").append('<tr>无数据sadfa</tr>');
		}
		
	});
}
//本月实施完成的申请   
function AppSysComplete(){
	$.post(ctx+'/request/base/findAppSysCompleteRequest.action', {}, function(result) {
		var Xvalue=[];//得到X轴数值
		var Yvalue=[];//得到Y轴数值
		if(result.length>0){
			for(var i=0 ; i<result.length ; i++) {
				Xvalue[i]=result[i].appCName;
				Yvalue[i]=result[i].requestNums;
			}
			var myChart = echarts.init(document.getElementById('mainss')); 
			setEcharts();
			var option = {
								/**title : {
							        text: '本月实施完成的申请',
							        textStyle: {
								            fontWeight: 'normal',
								            color: '#33C2FE',
								            fontFamily:'微软雅黑'       // 主标题文字颜色
								    }
							    },*/
			                    tooltip: {
			                        show: true
			                    },
			                    legend: {
			                    	x : 'right',
			                        data:['申请数量']
			                    },
			                    toolbox: {
							        show : true,
							        orient : 'vertical',
							        y : 'center',
							        feature : {
							            magicType : {show: true, type: ['line', 'bar']},
							        }
							    },
							   // color:['#00448a','#0580b9','#28c6b9','#84e6f1','#dddddd'],
			                    xAxis : [
			                        {
			                            type : 'category',
			                            data : Xvalue
			                        }
			                    ],
			                    yAxis : [
			                        {
			                            type : 'value'
			                        }
			                    ],
			                    series : [
			                        {
			                            "name":"申请数量",
			                            "type":"bar",
			                            "data":Yvalue
			                        }
			                    ]
			                };
			myChart.setOption(option);
		}else{
			//$("#NewestWaitDealTab").append('<tr>无数据sadfa</tr>');
		}
		
	});
}
//计算资源使用情况--- 物理机数、虚拟机数、物理机cpu数、虚拟机cpu数
function getPoolHostAndVm(){
	$.post(ctx + '/resmgt-common/device/selectResPoolHostVmInfo.action', {}, function(result) {
		if(result.length>0){
			var Xvalue=[];
			var vmemArray = [];
			var hmemArray = [];
			var vcpuArray = [];
			var hcpuArray = [];
			for(var i=0 ; i<result.length ; i++) {
				Xvalue[i]=result[i].poolName;
				vmemArray[i]=result[i].vmem;
				hmemArray[i]=result[i].hmem;
				vcpuArray[i]=result[i].vcpu;
				hcpuArray[i]=result[i].hcpu;
			}			
			var myChartTops = echarts.init(document.getElementById('topsss'));
			setEcharts();
			var optionss = {
					/**title : {
					        text: '计算资源使用情况',
					        textStyle: {
								            fontWeight: 'normal',
								            color: '#33C2FE',
								            fontFamily:'微软雅黑'       // 主标题文字颜色
						    }
					    },*/
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {
				            type: 'shadow'
				        }
				    },
				    legend: {
				    	x : 'right',
				        data:['物理机数','虚拟机数','物理机cpu数','虚拟机cpu数']
				    },
				    toolbox: {
				        show : true,
				        orient : 'vertical',
				        y : 'center',
				        feature : {
				            magicType : {show: true, type: ['line', 'bar']},
				        }
				    },
				    //color:['#00448a','#0580b9','#28c6b9','#84e6f1','#dddddd'],
				    calculable : true,
				    xAxis : [
				        {
				            type : 'category',
				            data : Xvalue
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            splitArea : {show : true}
				        }
				    ],
				    grid: {
				        x2:40
				    },
				    series : [
				        {
				            name:'物理机数',
				            type:'bar',
				            tiled: '总量',
				            data:hmemArray
				        },
				        {
				            name:'虚拟机数',
				            type:'bar',
				            tiled: '总量',
				            data:vmemArray
				        },
				        {
				            name:'物理机cpu数',
				            type:'bar',
				            tiled: '总量',
				            data:hcpuArray
				        },
				        {
				            name:'虚拟机cpu数',
				            type:'bar',
				            tiled: '总量',
				            data:vcpuArray
				        }
				    ]
				};
			myChartTops.setOption(optionss);
		}else{
			//$("#DeviceDiagramTab").append('<tr>无数据</tr>');
		}
		
	});
}

$(function() {
	
	AppSysVirtual();
	AppSysComplete();
	getVmTypeNum();
	getPoolHostAndVm();
	NewestComplete();
	NewestCreate();
	NewestWaitDeal();
	DeviceDiagram();
	findRmNwCclass();
	//showLeftBanner();
	//showRightBanner();
});
//根据状态显示不同样式
function statusHtml(status){
   if(status=='已关闭'){
      return '<span class="blue">'+status+'</span>';
   }else if(status=='分配失败'){
      return '<span class="red">'+status+'</span>';
   }else{
      return status;
   }
}
</script>
<style type="text/css">
html,body{height:100%}
#top .top_bg {width:100%;}
</style>
</head>
<body style="background:#f6f6f8;">
    <div class="main">
     	<div class="mainBox">
        	<div class="mainTop"><strong>我的工作平台</strong><%--a href="${ctx}/pages/myconsole.jsp">home</a--%></div>
            <div class="kuickMenu_box" style="display:none">
                <ul class="kuickMenu">
                    <li class="kuickMenu_1"><div class="txtWrap"><strong>待处理申请</strong>您有<span> 12 </span>条消息</div><br></li>
                    <li class="kuickMenu_2"><div class="txtWrap"><strong>待处理申请</strong>您有<span> 12 </span>条消息</div><br></li>
                    <li class="kuickMenu_3"><div class="txtWrap"><strong>待处理申请</strong>您有<span> 12 </span>条消息</div><br></li>
                    <li class="kuickMenu_4"><div class="txtWrap"><strong>待处理申请</strong>您有<span> 12 </span>条消息</div><br></li>
                    <li class="kuickMenu_5"><div class="txtWrap"><strong>待处理申请</strong>您有<span> 12 </span>条消息</div><br></li>
                    <li class="kuickMenu_6">1<div class="txtWrap"><strong>待处理申请</strong>您有<span> 12 </span>条消息</div></li>
                </ul>
            </div>
    <div class="mainImg">
            	<div class="mainImg_left">
                	<h2>计算资源使用情况</h2>
                	<p><div id="topsss"  style="height:230px;width:100%;float:left;border:#f0f0f0 1px solid;"></div></p>
				</div>
                <div class="mainImg_right">
                	<h2>计算资源图</h2>
                	<p><div id="topss"  style="height:230px;width:100%;float:left;border:#f0f0f0 1px solid;"></div></p>
                </div>
            	
	</div>
    <div class="mainImg">
            	<div class="mainImg_left">
                	<h2>应用系统服务器图</h2>
                	<p><div id="topssss"  style="height:230px;width:100%;float:left;border:#f0f0f0 1px solid;"></div></p>
				</div>
                <div class="mainImg_right">
                	<h2>本月实施完成的申请</h2>
                	<p><div id="mainss"  style="height:230px;width:100%;float:left;border:#f0f0f0 1px solid;"></div></p>
                </div>
            	
	</div>
                <div class="tableWrap">
                	<div class="Table">
                    	<h2 class="tableBt2">最新完成的申请</h2>
                    	<table id="NewestCompleteTab" cellpadding="0" cellspacing="0" border="0" width="100%">
                        	<tr class="bt">
                            	<td  width="26%">单号</td>
                                <td  width="26%">完成时间</td>
                                <td  width="26%">系统</td>
                                <td  width="9%">状态</td>
                                <td>操作</td>
                            </tr>
                        </table>
                    </div>
                    <div class="Table" style="margin:0;">
                    	<h2 class="tableBt1">我最新的服务申请</h2>
                    	<table id="NewestCreateTab" cellpadding="0" cellspacing="0" border="0" width="100%">
                        	<tr class="bt">
                            	<td  width="26%"  >单号</td>
                                <td  width="26%"  >完成时间</td>
                                <td  width="26%"  >系统</td>
                                <td  width="13%"  >状态</td>
                                <td  >操作</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="tableWrap">
                	<div class="Table">
                    	<h2 class="tableBt2">最新待处理的申请</h2>
                    	<table id="NewestWaitDealTab" cellpadding="0" cellspacing="0" border="0" width="100%">
                        	<tr class="bt">
                            	<td  width="26%">单号</td>
                                <td  width="26%">申请时间</td>
                                <td  width="26%" >系统</td>
                                <td  width="13%">状态</td>
                                <td>操作</td>
                            </tr>
                        </table>
                    </div>
                    <div class="Table" style="margin:0;">
                    	<h2 class="tableBt1">最新入库的设备</h2>
                    	<table id="DeviceDiagramTab" cellpadding="0" cellspacing="0" border="0" width="100%">
                        	<tr class="bt">
                            	<td  width="26%">时间</td>
                                <td  width="9%">类型</td>
                                <td  width="14%" >SN</td>
                                <td  width="24%">所在资源池</td>
                            </tr>
                        </table>
                    </div>
                </div>
                 <div class="tableWrap">
                	<div class="BigTable Table">
                    	<h2 class="tableBt3">IP地址使用情况</h2>
                    	<table id="findRmNwCclassTab" cellpadding="0" cellspacing="0" border="0" width="100%">
                        	<tr class="bt">
                            	<td  width="15%">数据中心</td>
                                <td width="15%">已建B段名</td>
                                <td  width="8%">已用IP个数</td>
                                <td  width="13%" >虚拟机占用IP个数</td>
                                <td  width="13%">物理机占用IP个数</td>
                                <td width="13%">生产占用IP数</td>
                                <td width="12%">ILO占用IP数</td>
                                <td width="">VMotion占用IP数</td>
                            </tr>
                        </table>
                    </div>
                </div>
        </div> 
    </div>
</body>
</html>