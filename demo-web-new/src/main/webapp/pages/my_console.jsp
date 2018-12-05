<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<link href="${ctx}/css/Workbench.css" type="text/css" rel="stylesheet" />
</head>
<style type="text/css">
.aColor{
    	color:#18a689;
    	text-decoration:none;
    }
    .aColor:hover{
    	color:#18a689;
    }
    .aColor1:link,.aColor1:visited{
    	color:#18a689;
    	text-decoration:none;
    }
    .aColor1:hover{
    	color:#18a689;
    }

    #resourceUl>li{background: #d6dfe2;}
   #resourceUl>li:hover {color: #4f5c63;
background: #c9d5d9;}
</style>
<script type="application/javascript">
$(function(){
	
	 $("#resourceUl>li:first").css({
        /*  'border-top':'2px solid #18a689', */
         'background':'#fff',
         'color':'#a71e32'
     });
	 
    $("#resourceUl>li").live("click",function(){
        $("#resourceUl>li").css({
        	/* 'border-top':'2px solid #e4eaec', */
            'background':'#d6dfe2',
            'color':'#58666e'
        })
        $(this).css({
        	/* 'border-top':'2px solid #18a689', */
            'background':'#fff',
            'color':'#a71e32'
        })
    })
    $("#dataCenterSum>li").live("mouseenter",function(){
        $(this).css("background-color", "#f3f7fa");
        $(this).find("span").css("background-color", "#a71e32");

    })
    $("#dataCenterSum>li").live("mouseleave",function(){
        $(this).css("background-color", "#ffffff");
        $(this).find("span").css("background-color", "#7C8396");
    })

    $("#resourcePoolUl>li").live("mouseenter",function(){
        $(this).css("background-color", "#f3f7fa");
        $(this).find("span").css("background-color","#a71e32");

    })
    $("#resourcePoolUl>li").live("mouseleave",function(){
        $(this).css("background-color", "#ffffff");
        $(this).find("span").css("background-color", "#7C8396");
    })
	})

</script>
<script type="text/javascript" src="../jquery/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="../jquery/js/echarts.js"></script>
<script type="text/javascript" src="../jquery/js/echarts-plain.js"></script>
<script type="text/javascript">
	$(function(){
		greatCpuCrm();
		NewestCreate();
		NewestWaitDeal();
		
		
/*		$(".WorkPho_con li").mouseover(function(){
			alert("11111");
			$(this).css({background:"#f9fafa"});
			$(this).find(".tip").addClass("CurTip")
		}).mouseleave(function(){
			$(this).css({background:"none"});
			$(this).find(".tip").removeClass("CurTip")
		});*/

		$("table tr").mouseover(function(){
			$(this).css({background:"#f6ad54"});
			$(this).css({color:"#fff"});	
			$(this).find(".nur").css({background:"#7c8496"});
			$(this).find("a").css({color:"#fff"});	
		}).mouseout(function(){
			$(this).css({background:"#eceff1"});
			$(this).find(".nur").css({background:"#bfc2cf",color:"#fff"}); 
			$(this).css({color:"#96a0ac"});
			$(this).find("a").css({color:"#96a0ac"});
			$(".table_bt").css({color:"#fff" });
			$(".table_bt .nur").css({background:"#7c8496" });
			
		})
	});
	
	
	
	function greatCpuCrm(){
		var url =ctx+'/resmgt/common/route/selectDateCenter.action';
		$.ajax({
			async : false,
			cache : false,
			type : 'post',
			datatype : "json",
			url : url,  
			data : {},
			error : function() {//请求失败处理函数   
				alert(i18nShow('tip_req_fail'));
			},
			success : function(data) { //请求成功后处理函数。     
				//图形显示方法
				backData(data);
			}
		});
	}
	//四舍五入的方法       how 要保留的小数位数     dight 要四舍五入的数值
	function   ForDight(Dight,How)  
	{  
	            Dight   =   Math.round   (Dight*Math.pow(10,How))/Math.pow(10,How);  
	            return   Dight;  
	}  
	//返回的json值
	function backData(data){
		
		// 路径配置
		require.config({
		    paths: {
		        //echarts:'http://echarts.baidu.com/build/dist'
		        echarts:'../jquery/js'
		    }
		});
		// 使用
		require(
		           [
		               'echarts',
        				'echarts/chart/gauge'  //使用柱状图就加载bar模块，按需加载
		           ],
		           function (ec) {
		           for(var i=0;i<data.length;i++){
		           //创建数据中心DIV
		           $('#resourceUl').append("<li class='tabBt_list' id ='resourceLi"+i+"'value='"+data[i].id+"' onclick='greatResourcePool(\""+data[i].id+"\")'>"+data[i].dname+"</li>");  
//		           if(data[i].ramUsed != 0 && data[i].ram != 0 && data[i].cpuUsed != 0 && data[i].cpu != 0){
		        	   creatDiv(i,data[i].id,data[i].dname,data[i].ramUsed,data[i].ram,data[i].cpuUsed,data[i].cpu);
			           $(".cpuAndRamTable").css('width','200px');
			           $(".cpuAndRamTable").css('height','40px');
			           $(".cpuAndRamTable").css('margin-top','15px');
			           $(".cpuAndRamTable").css('margin-bottom','-13px');
			           
			   		   $(".cpuAndRamTd").css('border-bottom', 'initial');
			   		   $(".cpuAndRamTd").css('font-size','10px');
			   		   $(".cpuAndRamTd").css('padding-left','0px');
			   		   //$(".cpuAndRamTd").css('height','5px');
			   		   $(".cpuAndRamTd").css('line-height','initial');

			   		   $(".cpuAndRamTr").css('background', 'initial');
			   		   $(".cpuAndRamTr").css('color', 'initial');
			   		   $(".cpuAndRamTr").css('height','initial');
			           var ramSum =0;;
						var cpuSum = 0;
			           if(data[i].ramUsed == 0){
			           		ramSum= 0;
			           }else if(data[i].ram == 0){
			        	   ramSum= 0;
			           }else{
			           		ramSum = ((data[i].ramUsed/data[i].ram)*100).toFixed(2);
			           }
			           if(data[i].cpuUsed == 0){
			           		cpuSum = 0;
			           }else if(data[i].cpu == 0){
			        	   
			           }else{
			           		cpuSum = ((data[i].cpuUsed/data[i].cpu)*100).toFixed(2);
			           }
			           
							
			           		var myChart = ec.init(document.getElementById('main'+[i])); 
			           		//alert("i="+[i]);
			           		var temp = i+"option";
			                    temp = {
								tooltip : {
									formatter: "{a} <br/>{c} {b}"
								},
								toolbox: {
								    show : true,
								    feature : {
								        mark : {show: false},
								        restore : {show: false},
								        saveAsImage : {show: false}
								    }
								},
								series : [
								    {
								        name:'CPU',
								        type:'gauge',
								        center : ['68.5%', '51.5%'],    // 默认全局居中
	            						radius : '100%',
								        z: 3,
								        min:0,
								        max:100,
								        splitNumber:0,
								        axisLine: {            // 坐标轴线
								            show: true,        // 默认显示，属性show控制显示与否
								            lineStyle: {       // 属性lineStyle控制线条样式
								                color: [[0.6, '#5abc97'],[0.605,'#ffffff'],[0.8, '#f6ae54'],[0.805,'#ffffff'],[1,'#eb6143']], 
								                width: 7.5
								            }
								        },
								        axisTick: {            // 坐标轴小标记
								            show: false,        // 属性show控制显示与否，默认不显示
								            splitNumber: 5,    // 每份split细分多少段
								            length :8,         // 属性length控制线长
								            lineStyle: {       // 属性lineStyle控制线条样式
								                color: '#eee',
								                width: 1,
								                type: 'solid'
								            }
								        },
								        splitLine: {           // 分隔线
								             show: false,
								            length :20,         // 属性length控制线长
								            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
								               color: 'auto'
								            }
								        },
								        title : {
								            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
								                fontWeight: 'bolder',
								                fontSize: 20,
								                fontStyle: 'arial'
								            },
								            offsetCenter: ['0%', 30]       // x, y，单位px
								         },
								          pointer: {
								                width:2.5,
								                color: '#7b8a98'
								          },
								         detail : {
								         		formatter:'{value}%',
								          		borderWidth: 0,
								                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
								                	fontWeight: 'bolder',
								                	fontSize : 13
								          		}
								         },
								         
								         data:[{value:cpuSum,name:'CPU'}]
								    },
								    {
								         name:i18nShow('my_req_sr_memery'),
								         type:'gauge',
								         center : ['28%', '57%'],    // 默认全局居中
	           							 radius : '78%',
								         min:0,
								         max:100,
								         endAngle:43,
								         splitNumber:0,
								             axisLine: {            // 坐标轴线
				 				                 show: true,        // 默认显示，属性show控制显示与否
									             lineStyle: {       // 属性lineStyle控制线条样式
									                 color: [[0.6,'#5abc97'],[0.61,'#ffffff'],[0.8,'#f6ae54'],[0.81,'#ffffff'],[1, '#eb6143']], 
									                 width: 7
									             }
								         	 },
								            axisTick: {            // 坐标轴小标记
								                show: false,        // 属性show控制显示与否，默认不显示
								                splitNumber: 5,    // 每份split细分多少段
								                length :8,         // 属性length控制线长
								                lineStyle: {       // 属性lineStyle控制线条样式
								                    color: '#eee',
								                    width: 1,
								                    type: 'solid'
								                }
								            },
								            
								            splitLine: {           // 分隔线
								            		show: false,
								                length :20,         // 属性length控制线长
								                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
								                    color: 'auto'
								                }
								            },
								             title : {
								            	textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
									                fontWeight: 'bolder',
									                fontSize: 20,
									                fontStyle: 'arial'
									            },
								                offsetCenter: [0, '50%']       // x, y，单位px
								            },
								            pointer: {
								                 width:2,
								                 color: '#7b8a98'
								            },
								           
								            detail : {
								            	formatter:'{value}%',
								                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
								                    fontWeight: 'bolder',
								                    fontSize : 13
								                }
								            },
								          
								            data:[{value:ramSum,name:i18nShow('my_req_sr_memery')}]
								        }
								        
								    ]
								};
								myChart.setOption(temp);
		           //}
		           
					}
								 //如果现实数据中心小于4个，则调用灰色图片
								 //var len = $('#dataCenterSum > li').length % 4;
								 var len = data.length % 4;
								 if(len != 0){
								 	var noneSum = 4- len;
								 	for(var i=0;i<noneSum;i++ ){
								 		creatNoneDiv();
								 	}
								 }
								 
							}
		                    
		            );
		
	}
	//数据中心生成DIV标签
	function creatDiv(i,id,dname,ramUsed,ram,cpuUsed,cpu){
		cpu = Math.round(cpu);
		ram = Math.round(ram);
		var ramUsable = ram-ramUsed ;
		ramUsable = Math.round(ramUsable);
		var cpuUsable = cpu-cpuUsed ;
		cpuUsable = Math.round(cpuUsable);
		$('#dataCenterSum').append("<li><div style='height:115px;padding-top:3%;margin:0 auto;z-index:999;width:99%;' id='main"+i+"'></div>"+
				"<table class='cpuAndRamTable' style='width:200px;'><tr class='cpuAndRamTr'><td class='cpuAndRamTd' style='width:130px; text-align:center; background:#none;'> "+i18nShow('my_console_total_mem')+": "+parseInt(ram/1024)+" GB</td>"+
		        "<td class='cpuAndRamTd' style='width:130px; text-align:center; background:#none;'> "+i18nShow('my_console_total_CPU')+":&nbsp;"+cpu+i18nShow('unit_he')+"</td></tr>"+
		        "<tr class='cpuAndRamTr'><td class='cpuAndRamTd' style='width:130px; text-align:center; background:#none;'>"+i18nShow('my_console_free_mem')+": "+parseInt(ramUsable/1024)+" GB</td>"+
		        "<td class='cpuAndRamTd' style='width:130px; text-align:center; background:#none;'>"+i18nShow('my_console_free_CPU')+": "+cpuUsable+i18nShow('unit_he')+"</td></tr></table>"+
                "<span class='tip' id='spanname"+i+"' value='"+id+"'>"+dname+"</span></li>");
        if(i==0){
             greatResourcePool(id);
        }
        
	}
	//数据中心生成灰色DIV标签
	function creatNoneDiv(){
		$('#dataCenterSum').append("<li><div class='pho'><img src='${ctx}/images/WorkPh0None.png'/></div><div style='width: 200px; height: 40px; margin-top: 15px; margin-bottom: -13px;'></div><span class='tip tipnone'></span></li>");
      
		
	}
	//数据中心生成灰色DIV标签
	function creatPoolNoneDiv(){
		$('#resourcePoolUl').append("<li><div class='pho'><img src='${ctx}/images/WorkPh0None.png'/></div><div style='width: 200px; height: 40px; margin-top: 15px; margin-bottom: -13px;'><span class='tip tipnone'></span></li>");
      
		
	}
	//资源池
	function greatResourcePool(rid){
		$('#resourcePoolUl').empty();
		var url =ctx+'/resmgt/common/route/selectResourcePool.action';
		$.ajax({
			async : false,
			cache : false,
			type : 'post',
			datatype : "json",
			url : url,  
			data : {"id":rid},
			error : function() {//请求失败处理函数   
				alert(i18nShow('tip_req_fail'));
			},
			success : function(data) { //请求成功后处理函数。     
				//图形显示方法
				backDataPool(data);
			}
		});
	}
	
	
	
		//返回的json值
	function backDataPool(data){
		
		// 路径配置
		require.config({
		    paths: {
		        //echarts:'http://echarts.baidu.com/build/dist'
		        echarts:'../jquery/js'
		    }
		});
		// 使用
		require(
		           [
		               'echarts',
        				'echarts/chart/gauge'  //使用柱状图就加载bar模块，按需加载
		           ],
		           function (ec) {
		           for(var i=0;i<data.length;i++){
	//	        	   if(data[i].ramUsed != 0 && data[i].ram != 0 && data[i].cpuUsed != 0 && data[i].cpu != 0){
		        		   creatPoolDiv(i,data[i].id,data[i].pname,data[i].ramUsed,data[i].ram,data[i].cpuUsed,data[i].cpu);
				           $(".cpuAndRamTable").css('width','200px');
				           $(".cpuAndRamTable").css('height','40px');
				           $(".cpuAndRamTable").css('margin-top','15px');
				           $(".cpuAndRamTable").css('margin-bottom','-13px');
				           
				   		   $(".cpuAndRamTd").css('border-bottom', 'initial');
				   		   $(".cpuAndRamTd").css('font-size','10px');
				   		   $(".cpuAndRamTd").css('padding-left','0px');
				   		   //$(".cpuAndRamTd").css('height','5px');
				   		   $(".cpuAndRamTd").css('line-height','initial');

				   		   $(".cpuAndRamTr").css('background', 'initial');
				   		   $(".cpuAndRamTr").css('color', 'initial');
				   		   $(".cpuAndRamTr").css('height','initial');
				           	var ramSum =0;;
							var cpuSum = 0;
				           if(data[i].ramUsed == 0){
				           		ramSum= 0;
				           }else if(data[i].ram == 0){
				        	   
				           }else{
				           		ramSum = ((data[i].ramUsed/data[i].ram)*100).toFixed(2);
				           }
				           if(data[i].cpuUsed == 0){
				           		cpuSum = 0;
				           }else if(data[i].cpu == 0){
				        	   
				           }else{
				           		cpuSum = ((data[i].cpuUsed/data[i].cpu)*100).toFixed(2);
				           }
				           		var myChart = ec.init(document.getElementById('mil'+[i])); 
				           		//alert("i="+[i]);
				           		var temp = i+"option";
				                    temp = {
									tooltip : {
										formatter: "{a} <br/>{c} {b}"
									},
									toolbox: {
									    show : true,
									    feature : {
									        mark : {show: false},
									        restore : {show: false},
									        saveAsImage : {show: false}
									    }
									},
									series : [
									    {
									        name:'CPU',
									        type:'gauge',
									        center : ['68.5%', '51.5%'],    // 默认全局居中
		            						radius : '100%',
									        z: 3,
									        min:0,
									        max:100,
									        splitNumber:0,
									        axisLine: {            // 坐标轴线
									            show: true,        // 默认显示，属性show控制显示与否
									            lineStyle: {       // 属性lineStyle控制线条样式
									                color: [[0.6, '#5abc97'],[0.605,'#ffffff'],[0.8, '#f6ae54'],[0.805,'#ffffff'],[1,'#eb6143']], 
									                width: 7.5
									            }
									        },
									        axisTick: {            // 坐标轴小标记
									            show: false,        // 属性show控制显示与否，默认不显示
									            splitNumber: 5,    // 每份split细分多少段
									            length :8,         // 属性length控制线长
									            lineStyle: {       // 属性lineStyle控制线条样式
									                color: '#eee',
									                width: 1,
									                type: 'solid'
									            }
									        },
									        splitLine: {           // 分隔线
									             show: false,
									            length :20,         // 属性length控制线长
									            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
									               color: 'auto'
									            }
									        },
									        title : {
									            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
									                fontWeight: 'bolder',
									                fontSize: 20,
									                fontStyle: 'arial'
									            },
									            offsetCenter: ['0%', 30]       // x, y，单位px
									         },
									          pointer: {
									                width:2.5,
									                color: '#7b8a98'
									          },
									         detail : {
									         		formatter:'{value}%',
									          		borderWidth: 0,
									                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
									                	fontWeight: 'bolder',
									                	fontSize : 13
									          		}
									         },
									         data:[{value:cpuSum,name:'CPU'}]
									    },
									    {
									         name:i18nShow('my_req_sr_memery'),
									         type:'gauge',
									         center : ['28%', '57%'],    // 默认全局居中
		           							 radius : '78%',
									         min:0,
									         max:100,
									         endAngle:43,
									         splitNumber:0,
									             axisLine: {            // 坐标轴线
					 				                 show: true,        // 默认显示，属性show控制显示与否
										             lineStyle: {       // 属性lineStyle控制线条样式
										                 color: [[0.6,'#5abc97'],[0.61,'#ffffff'],[0.8,'#f6ae54'],[0.81,'#ffffff'],[1, '#eb6143']], 
										                 width: 7
										             }
									         	 },
									            axisTick: {            // 坐标轴小标记
									                show: false,        // 属性show控制显示与否，默认不显示
									                splitNumber: 5,    // 每份split细分多少段
									                length :8,         // 属性length控制线长
									                lineStyle: {       // 属性lineStyle控制线条样式
									                    color: '#eee',
									                    width: 1,
									                    type: 'solid'
									                }
									            },
									            
									            splitLine: {           // 分隔线
									            		show: false,
									                length :20,         // 属性length控制线长
									                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
									                    color: 'auto'
									                }
									            },
									             title : {
									            	textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
										                fontWeight: 'bolder',
										                fontSize: 20,
										                fontStyle: 'arial'
										            },
									                offsetCenter: [0, '50%']       // x, y，单位px
									            },
									            pointer: {
									                 width:2,
									                 color: '#7b8a98'
									            },
									           
									            detail : {
									            	formatter:'{value}%',
									                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
									                    fontWeight: 'bolder',
									                    fontSize : 13
									                }
									            },
									            data:[{value:ramSum,name:i18nShow('my_req_sr_memery')}]
									        }
									        
									    ]
									};
										 myChart.setOption(temp);
//		        	   }
					}
								 //如果现实数据中心小于4个，则调用灰色图片
								 var len = data.length % 4;
								 if(len !=  0){
								 	var noneSum = 4- len;
								 	for(var i=0;i<noneSum;i++ ){
								 		creatPoolNoneDiv();
								 	}
								 }
							}
		                    
		            );
	}
	
	//资源池生成DIV标签
	function creatPoolDiv(i,id,pname,ramUsed,ram,cpuUsed,cpu){
		cpu = Math.round(cpu);
		ram = Math.round(ram);
		var ramUsable = ram-ramUsed ;
		ramUsable = Math.round(ramUsable);
		var cpuUsable = cpu-cpuUsed ;
		cpuUsable = Math.round(cpuUsable);
		$('#resourcePoolUl').append("<li><div style='height:115px;padding-top:3%;margin:0 auto;z-index:999;width:99%;' id='mil"+i+"'></div>"+
				"<table class='cpuAndRamTable' style='width:200px;'><tr class='cpuAndRamTr'><td class='cpuAndRamTd' style='width:130px; text-align:center; background:#none;'> "+i18nShow('my_console_total_mem')+":&nbsp;"+parseInt(ram/1024)+" GB</td>"+
		        "<td class='cpuAndRamTd' style='width:130px; text-align:center; background:#none;'> "+i18nShow('my_console_total_CPU')+":&nbsp;"+cpu+i18nShow('unit_he')+"</td></tr>"+
		        "<tr class='cpuAndRamTr'><td class='cpuAndRamTd' style='width:130px; text-align:center; background:#none;'>"+i18nShow('my_console_free_mem')+":&nbsp;"+parseInt(ramUsable/1024)+" GB</td>"+
		        "<td class='cpuAndRamTd' style='width:130px; text-align:center; background:#none;'>"+i18nShow('my_console_free_CPU')+":&nbsp;"+cpuUsable+i18nShow('unit_he')+"</td></tr></table>"+
                "<span class='tip' id='spanpoolname"+i+"' value='"+id+"'>"+pname+"</span></li>"); 
               /*  $('#resourcePoolUl').html()); */
        
		
	}
	
	function transformTime(time){
		var date = new Date(time);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
        h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
        return Y+M+D+h+m+s;
	}
	//我最新的服务申请
function NewestCreate(){
	$.post(ctx + '/request/base/findNewestCreateRequest.action', {}, function(result) {
		if(result.length>0){
			for(var i=0 ; i<result.length ; i++) {
				var dateobj=new Date(result[i].createTime.time);
				$("#NewestCreateTab").append('<tr><td>'+result[i].srCode+'</td><td>'+transformTime(dateobj.toString())
						+'</td><td>'+result[i].appName+'</td><td>'+statusHtml(result[i].srStatus)+'</td><td>'+"<a class='aColor' href'javascript:void(0)' onclick=lookupWorkflow('"+result[i].srId+"') >"
						+i18nShow('my_req_sch')+"</a>"+'</td></tr>');
			}
		}else{
			$("#NewestCreateTab").append('<tr><td>'+i18nShow('my_console_no_data')+'</td></tr>');
		}
		
	});
}
function transformPendTime(time){
	var date = new Date(time);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
    h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
    m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
    s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
    return Y+M+D+h+m+s;
}
//最新待我处理的申请
function NewestWaitDeal(){
	$.post(ctx + '/request/base/findNewestWaitDealRequest.action', {}, function(result) {
		if(result.length>0){
			for(var i=0 ; i<result.length ; i++) {
				var dateobj=new Date(result[i].createTime.time);
				$("#NewestWaitDealTab").append('<tr><td>'+result[i].srCode+'</td><td>'+transformPendTime(dateobj.toString())+'</td><td>'+result[i].appName+'</td><td>'+statusHtml(result[i].srStatus)+'</td><td>'+"<a class='aColor1' href='javascript:void(0)' onclick=dealRequest('"+result[i].pageUrl+"','"+result[i].todoId+"') >"+i18nShow('my_req_deal')+"</a>"+'</td></tr>');
			}
		}else{
			$("#NewestWaitDealTab").append('<tr><td>'+i18nShow('my_console_no_data')+'</td></tr>');
		}
		
	});
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
		window.location.href = ctx+"/pages/tankflowNew/processInstance.jsp?state=view&instanceId="+data.result;
	});
}
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
<body>

<div class="Workbench_wrap">
	<h2 class="WorkBt">
		<div class="WorkBtBg"><icms-i18n:label name="mw_title_bench"/></div>
		<div class="WorkSmallBtBg"><small> <icms-i18n:label name="mw_title_describe"/></small></div>
	</h2>
    <div class="WorkPho">
        <h3 class="WorkPho_bt" style="background: #e4eaec;"><span class="bt"><icms-i18n:label name="mw_l_dataCenter"/></span></h3>
        <ul class="WorkPho_con" id="dataCenterSum">
        	
        </ul>
        <%-- <span class="WorkPho_bottom"><img src="${ctx}/images/WorkPho_bttom.png" width="100%" height="11" /></span> --%>
       
	</div> 
    <div class="WorkPho">
        <h3 class="WorkPho_bt" style="background: #e4eaec;"><span class="bt"><icms-i18n:label name="mw_l_computPool"/></span></h3>
        <ul class="tabBt" id="resourceUl">
        	
        </ul>
        <div class="tabWrap">
            <ul class="WorkPho_con" id="resourcePoolUl">
               
            </ul>
        </div>
      <%--   <span class="WorkPho_bottom"><img src="${ctx}/images/WorkPho_bttom.png" width="100%" height="11" /></span> --%>
	</div>	
     <div class="TableWarp">
    	<div class="WorkPho Table_Left">
            <h3 class="WorkPho_bt"><span class="bt" style="padding-left:0px;"><icms-i18n:label name="mw_l_newServiceApply"/></span></h3>
             <table cellpadding="0" cellspacing="0" id="NewestCreateTab" >
            	<tr class="table_bt">
                	
                    <td><icms-i18n:label name="mw_l_odd"/></td>
                    <td><icms-i18n:label name="mw_l_finishTime"/></td>
                    <td><icms-i18n:label name="mw_l_system"/></td>
                    <td><icms-i18n:label name="com_l_status"/></td>
                    <td><icms-i18n:label name="mw_l_operate"/></td>
                </tr>
                
            </table>
        	<%-- <span class="WorkPho_bottom"><img src="${ctx}/images/WorkPho_bttom.png" width="100%" height="11" /></span> --%>
		</div> 
        <div class="WorkPho Table_Right">
            <h3 class="WorkPho_bt"><span class="bt" style="padding-left:0px;"><icms-i18n:label name="mw_l_newDisposeApply"/></span></h3>
            <table cellpadding="0" cellspacing="0" id="NewestWaitDealTab" >
            	<tr class="table_bt">
                	
                    <td><icms-i18n:label name="mw_l_odd"/></td>
                    <td><icms-i18n:label name="mw_l_finishTime"/></td>
                    <td><icms-i18n:label name="mw_l_system"/></td>
                    <td><icms-i18n:label name="com_l_status"/></td>
                    <td><icms-i18n:label name="mw_l_operate"/></td>
                </tr>
               
            </table>
        	<%-- <span class="WorkPho_bottom"><img src="${ctx}/images/WorkPho_bttom.png" width="100%" height="11" /></span> --%>
		</div> 
    </div>

</div>
</body>
</html>