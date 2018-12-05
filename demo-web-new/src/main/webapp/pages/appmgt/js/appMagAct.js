function appMagAct(){
	showDiv("app_tab_tr");
	var apptADuId = "apptNodId:" + nodeId;
	showDiv("view_AppInfo_Div");
	showPubEDiv("app_tab_act",apptADuId);
}

function showPubEDiv(div,nodeId){
	showDiv("app_mag_tab");
	showDiv("View_AppInfo_ActTop");
	showDiv("View_AppInfo_ActvChart");
	showDiv("View_AppInfo_ActOperation");
	showDiv("View_AppInfo_ActEChart");
	showDiv("app_tab_inf");
	hideDiv("app_tab_body_tr");
	if(div == "icoDiv"){
		$("#app_tab_act").attr("class", "BtRlVmCur");
		$("#systemNumThId").css('display','');
		$("#systemNum").css('display','');
		$("#deplUnitNID").css('display','');
		$("#deplUnitNum").css('display','');
		$("#cloundServith").css('display','none');
		$("#cloudServiceNum").css('display','none');
		$("#cmVmDConfig").css('display','none');
		$("#cvmConfig").css('display','none');
	}else{
		$("#systemNumThId").css('display','none');
		if(div == "appDuView"){
			$("#deplUnitNID").css('display','none');
			$("#deplUnitNum").css('display','none');
			$("#cloundServith").css('display','');
			$("#cloudServiceNum").css('display','');
			$("#cmVmDConfig").css('display','');
			$("#cvmConfig").css('display','');
		}else{
			$("#cloundServith").css('display','none');
			$("#cloudServiceNum").css('display','none');
			$("#cmVmDConfig").css('display','none');
			$("#cvmConfig").css('display','none');
			$("#deplUnitNID").css('display','');
			$("#deplUnitNum").css('display','');
		}
		$("#systemNum").css('display','none');
		$("#app_tab_act").attr("class", "BtCur");
		$("#app_tab_tr").attr("class", "BtRCur");
	}
	var rmDataCId,apptId,apptADuId,rmDataIdIndex,apptIdIndex,apptDuIdIndex;
	rmDataIdIndex = nodeId.indexOf("rmDataCId:");
	apptIdIndex = nodeId.indexOf("apptNodId:");
	apptDuIdIndex = nodeId.indexOf("apptADuId:");
	if(rmDataIdIndex >= 0){
		rmDataCId = nodeId.substr(10);
	}
	if(apptIdIndex >= 0){
		apptId = nodeId.substr(10);
	}
	if(apptDuIdIndex >= 0){
		apptADuId = nodeId.substr(10);
	}
	echartAppMag(nodeId);
	
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : ctx + "/resmgt/appmgt/appMagCount.action",
		data : {"rmDataCId" : rmDataCId,"apptId" : apptId,"apptADuId" : apptADuId},
		error : function() {
			showError(i18nShow('tip_req_fail'),null,"red");
		},
		success : function(data) {
			$("#systemNum").val(data.systemNum == '' ? 0:data.systemNum);
			$("#cloudServiceNum").val(data.cloudServiceNum);
			$("#deplUnitNum").val(data.deplUnitNum =='' ? 0:data.deplUnitNum);
			$("#vmNum").val(data.vmNum =='' ? 0 : data.vmNum);
			$("#cpuNum").val((data.cpuNum == ''||data.cpuNum == null) ? 0:data.cpuNum);
			$("#memNum").val((data.memNum == '' ||data.memNum == null)? 0:parseInt(data.memNum/1024));
			$("#cvmConfig").val(data.cvmConfig == '' ? i18nShow('compute_res_nothing'):data.cvmConfig);
		}
	});
}

function appDuMagAct(){
	showDiv("view_AppDu_Div");
	showDiv("app_tab_tr");
	var apptADuId = "apptADuId:" + nodeId;
	showPubEDiv("appDuView",apptADuId);
}

function applicationMagAt(){
	showPubEDiv("icoDiv","rmDataCId:0");
	hideDiv("view_AppInfo_Div");
	hideDiv("view_AppDu_Div");
	hideDiv("app_tab_tr");
	showDiv("icon_Div");
}

function appMag_tab(tab,tid,vid){
	if(tab == "appMag_tab_t"){
		showDiv("app_tab_inf");
		hideDiv("app_tab_body_tr");
		$("#app_tab_act").attr("class", "BtCur");
		$("#app_tab_tr").attr("class", "BtRCur");
		appMagAct();
		if(vid == "appDuView"){
			hideDiv("view_AppInfo_Div");
			appDuMagAct();
		}
		if(vid == "app_mag_tab" || vid == "iconDiv"){
			hideDiv("view_AppInfo_Div");
			hideDiv("app_tab_tr");
			$("#app_tab_act").attr("class", "BtRlVmCur");
			applicationMagAt();
		}
	}else{
		$("#app_tab_act").attr("class", "BtVCur");
		$("#app_tab_tr").attr("class", "BtVRCur");
		hideDiv("app_tab_inf");
		showDiv("app_tab_body_tr");
		getRmComputeVmListAction("apptADuId");
	}
}

function echartAppMag(nodeId){
	var rmDataCId,apptId,apptADuId,rmDataIdIndex,apptIdIndex,apptDuIdIndex;
	rmDataIdIndex = nodeId.indexOf("rmDataCId:");
	apptIdIndex = nodeId.indexOf("apptNodId:");
	apptDuIdIndex = nodeId.indexOf("apptADuId:");
	if(rmDataIdIndex >= 0){
		rmDataCId = nodeId.substr(10);
	}
	if(apptIdIndex >= 0){
		apptId = nodeId.substr(10);
	}
	if(apptDuIdIndex >= 0){
		apptADuId = nodeId.substr(10);
	}
	var date = new Date;
	var year = date.getFullYear();
//	var month = date.getMonth()+1;
//	month = (month<10 ? "0"+month:month); 
//	var datetr = (year.toString()+month.toString());
	
	var url = ctx+"/resmgt/appmgt/queryAppChart.action";
	$.ajax({
		async : false,
		cache : false,
		type : "post",
		datatype : "json",
		url : url,  
		data : {"rmDataCId" : rmDataCId,"apptId" : apptId,"apptADuId" : apptADuId},
		error : function() {
			showError(i18nShow('tip_req_fail'));
		},
		success : function(data) {
			
			require.config({
		         paths: {
		             echarts:ctx + "/common/echart/"
		         }
		     });
		     
		     require(
		                [
		                    "echarts",
		                    "echarts/chart/line",
		                    "echarts/chart/bar"
		                ],
		                function (ec) {
		                    var myChart = ec.init(document.getElementById("View_AppInfo_ActEChart")); 
		                    var option = {
		                    	    title : {
//		                    	        text: "CPU使用率",
//		                    	        subtext: "CPU大小"
		                    	    },
		                    	    tooltip : {
		                    	        trigger: "axis"
		                    	    },
		                    	    color:['#F47279','#38C0A2','#B6A2DE'],
		                    	    legend: {
		                    	        data:["CPU",i18nShow('my_req_sr_memery'),i18nShow('compute_res_diskSpace')]
		                    	    },
		                    	    grid: {
		                    	        bottom: '33%'
		                    	    },
		                    	    toolbox: {
		                    	        show : false,
		                    	        feature : {
		                    	            mark : {show: false},
		                    	            dataView : {show: true, readOnly: false},
		                    	            magicType : {show: true, type: ["line", "bar"]},
		                    	            restore : {show: true},
		                    	            saveAsImage : {show: true}
		                    	        }
		                    	    },
		                    	    calculable : true,
		                    	    xAxis : [
		                    	        {	
		                    	        	axisLabel: {
		                    	        		textStyle : { fontWeight : 'lighter', fontSize : 11 , color:"#777"},
		                    	                rotate : 61
		                    	            },
		                    	            type : "category",
		                    	            boundaryGap : false,
		                    	            data:[data.mon[0],data.mon[1],data.mon[2],data.mon[3],data.mon[4],data.mon[5],data.mon[6],data.mon[7],data.mon[8],data.mon[9],data.mon[10],data.mon[11]]
		                    	        }
		                    	    ],
		                    	    yAxis : [
		                    	        {
		                    	            type : "value",
		                    	            axisLabel : {
		                    	            textStyle : { fontWeight : 'lighter', fontSize : 11 , color:"#777"},
		                    	            formatter: "{value}"
		                    	            },
		                    	            splitLine:{    //坐标轴分隔线。默认数值轴显示，类目轴不显示。
		                                        show:false
		                                    }
		                    	        }
		                    	    ],
		                    	    series : [
		                    	        {
		                    	            name:"CPU",
		                    	            type:"line",
		                    	            barMaxWidth : 25,
								            symbol:'Triangle',
								            itemStyle: {
								                normal: {
								                    lineStyle: {            // 系列级个性化折线样式，横向渐变描边
								                        width: 4,
								                        shadowColor : 'rgba(0,0,0,0.2)',
								                        shadowBlur: 4,
								                        shadowOffsetX: 4,
								                        shadowOffsetY: 4
								                    }
								                }
								               
								            },
		                    	            data:[data.cpu[0],data.cpu[1],data.cpu[2],data.cpu[3],data.cpu[4],data.cpu[5],data.cpu[6],data.cpu[7],data.cpu[8],data.cpu[9],data.cpu[10],data.cpu[11],data.cpu[12]],
//		                    	            data:[data.cpu_sum_one,data.cpu_sum_two,data.cpu_sum_three,data.cpu_sum_four,data.cpu_sum_five,data.cpu_sum_six,data.cpu_sum_seven,data.cpu_sum_eight,data.cpu_sum_nine,data.cpu_sum_ten,data.cpu_sum_eleven,data.cpu_sum_twelve],
		                    	            markPoint : {
		                    	                data : [
		                    	                    {type : "max", name: i18nShow('l_app_max')},
		                    	                    {type : "min", name: i18nShow('l_app_min')}
		                    	                ]
		                    	            },
		                    	            markLine : {
		                    	                data : [{type : "average", name: i18nShow('l_app_average')}]
		                    	            }
		                    	        },{
		                    	            name:i18nShow('my_req_sr_memery'),
		                    	            type:"line",
		                    	            symbol:'Rectangle',
		                    	            itemStyle: {
								                normal: {
								                    lineStyle: {            // 系列级个性化折线样式，横向渐变描边
								                        width: 4,
								                        shadowColor : 'rgba(0,0,0,0.2)',
								                        shadowBlur: 4,
								                        shadowOffsetX: 4,
								                        shadowOffsetY: 4
								                    }
								                }
								               
								            },
		                    	            data:[data.mem[0],data.mem[1],data.mem[2],data.mem[3],data.mem[4],data.mem[5],data.mem[6],data.mem[7],data.mem[8],data.mem[9],data.mem[10],data.mem[11],data.mem[12]],
		                    	            markPoint : {
		                    	                data : [
		                    	                    {type : "max", name: i18nShow('l_app_max')},
		                    	                    {type : "min", name: i18nShow('l_app_min')}
		                    	                ]
		                    	            },
		                    	            markLine : {
		                    	                data : [{type : "average", name: i18nShow('l_app_average')}]
		                    	            }
		                    	        }
		                    	        ,{
                                            name:i18nShow('compute_res_diskSpace'),
                                            type:"line",
                                            symbol:'Circle',
                                            itemStyle: {
								                normal: {
								                    lineStyle: {            // 系列级个性化折线样式，横向渐变描边
								                        width: 4,
								                        shadowColor : 'rgba(0,0,0,0.2)',
								                        shadowBlur: 4,
								                        shadowOffsetX: 4,
								                        shadowOffsetY: 4
								                    }
								                }
								               
								            },
                                            data:[data.disk[0],data.disk[1],data.disk[2],data.disk[3],data.disk[4],data.disk[5],data.disk[6],data.disk[7],data.disk[8],data.disk[9],data.disk[10],data.disk[11],data.disk[12]],
                                            markPoint : {
                                                data : [
                                                    {type : "max", name: i18nShow('l_app_max')},
                                                    {type : "min", name: i18nShow('l_app_min')}
                                                ]
                                            },
                                            markLine : {
                                                data : [{type : "average", name: i18nShow('l_app_average')}]
                                            }
                                        }
		                    	    ]};
		                          myChart.setOption(option); 
		                     }
		                  );
		}
	});
}