var clickFun;
var expandFun;

$(function(){
	$("#hid_icon").val(iconPath+'cluster.png');
	$("#lab_title").html(i18nShow('compute_res_pool_manage'));
	clickFun =  function(){
			if(bizType == "xd"){
				showDiv("xd_div",i18nShow('compute_res_sdPool_manage'));
			}
			if(bizType == "dc"){
					ajaxCall("/resmgt-common/datacenter/getDataCenterById.action",{"dataCenterId":nodeId},
							function(){
								showDiv("dc_div",i18nShow('compute_res_pool_dcDatil'));
						
								showEchartDiv("new_tab");
					        	new_tab("new_tab_t","dc_div",i18nShow('compute_res_pool_dcDatil'));
					        	var rmdatacenterId = "rmdatacenterId:"+nodeId;
					        	echartMeterDic(rmdatacenterId);
					        	$("#s_poolName_current").html(ajaxResult.datacenterCname);
					        	
								$("#lab_dcname").html(ajaxResult.datacenterCname);
								$("#lab_dcEname").html(ajaxResult.ename);
								$("#lab_address").html(ajaxResult.address);
								if(ajaxResult.isActive == "Y"){
									$("#lab_isActive").html("<span class='tip_green'>"+i18nShow('compute_res_isActiveY')+"</span>");
								}else{
									$("#lab_isActive").html("<span class='tip_red'>"+i18nShow('compute_res_isActiveN')+"</span>");	
								}						
							}
						)
				}else if(bizType == "pool"){
					loadPoolData();
				}else if(bizType == "cdp"){
					loadCdpData();
				}else if(bizType == "cluster"){
					//暂时保持当前集群id
					$("#clusterIdSpecified").val(nodeId);
					loadClusterData();
				}else if(bizType == "host"){
					ajaxCall("/resmgt-compute/host/getCmDeviceHostInfo.action",
						{"hostId" : nodeId},
						function(){
							$("#datacenterId").val(ajaxResult.datacenter_id);
							showDiv("host_div",i18nShow('compute_res_pool_pmDetail'));
							var hostId = "hostId:"+nodeId;
							$("#hostId").val(nodeId);
							$("#resourceType").val("H");
							$("#clusterId").val(ajaxResult.clusterId);
							var virtualTypeCode = ajaxResult.virtualTypeCode;
							$("#hostType").val(virtualTypeCode);
				        	showEchartDiv("new_tab");
				        	new_tab("new_tab_t","host_div",i18nShow('compute_res_pool_pmDetail'));
				        	echartMeterDic(hostId);
				        	$("#s_poolName_current").html(ajaxResult.device_name);
							
							$("#device_name").html(ajaxResult.device_name);
							$("#sn").html(ajaxResult.sn);
							$("#cpu").html(ajaxResult.cpu+" 核");
							$("#mem").html(parseInt(ajaxResult.mem/1024)+" GB");
							$("#cpuUsed").html(ajaxResult.cpuUsed+" 核");
							$("#memUsed").html(parseInt(ajaxResult.memUsed/1024)+" GB");
							$("#device_model").html(ajaxResult.device_model);
							$("#manufacturer").html(ajaxResult.manufacturer);
							$("#seat_name").html(ajaxResult.seat_name);
							$("#disk").html((ajaxResult.disk==null||ajaxResult.disk==""?"0":ajaxResult.disk)+" GB");
							$("#cluster_name").html(ajaxResult.cluster_name);
							$("#cdp_name").html(ajaxResult.cdp_name);
							$("#pool_name").html(ajaxResult.pool_name);
							$("#datacenter_cname").html(ajaxResult.datacenter_cname);
							var datastore_name = ajaxResult.datastore_name;
							$("#host_run_state").html(ajaxResult.dic_name);
							$("#datastore_name").html(datastore_name);
							$("#ipmiU").html("<a href= "+ajaxResult.ipmiUrl+" target='blank'>"+((ajaxResult.ipmiUrl)==null||ajaxResult.ipmiUrl==""?"":ajaxResult.ipmiUrl)+"</a>");
							$("#host_table_id").html("");
							var datastore_type = ajaxResult.datastore_type;
							$("#datatstore_type").show();
							loadDatastore(nodeId);//查询已经选择的datastore
							var isInvc = ajaxResult.isInvc;
							$("#inVcbtn").unbind('click');
							$("#outVcbtn").unbind('click');
							var platformCode = ajaxResult.platformCode;
							if(platformCode == 'X' && virtualTypeCode == 'VM'){
								getCmDeviceHosRunningState(nodeId);
							}else{
								$("#hostState").html('Unknown');
								$("#impiFlag").val('N');
							}
							if (platformCode == 'X' && virtualTypeCode == 'VM') {
								//显示设置datastore的按钮
								$("#datatstore_type").attr("style","display:inline");
								//显示硬件状态按钮
								$("#HardwareStatus").attr("style", "display:block");
								//显示扫描虚拟机的按钮
								$("#ScanButton").attr("style", "display:block");
								//显示修改登录用户信息按钮
								$("#updateLoginInfoBtn").attr("style", "display:block");
								if(isInvc=='N'){
									$("#is_invc").html(i18nShow('compute_res_isInvcN'));
									$("#inVcbtn").attr("style", "display:block");
									$("#outVcbtn").attr("style", "display:none");
									$("#inVcbtn").bind('click',function(){validateNaguan();});//新增加物理机验证工作
									$("#outVcbtn").bind('click',function(){outVcControl(nodeId,ajaxResult.device_name)});
									$("#control_time").html('');
									$("#maintenanceMode").attr("style", "display:none");
									$("#exitMaintenanceMode").attr("style", "display:none");
									$("#pmClose").attr("style", "display:none");
									$("#pmRestart").attr("style", "display:none");
								}else if(isInvc == 'Y'){
									$("#is_invc").html(i18nShow('compute_res_isInvcY'));
									$("#inVcbtn").attr("style", "display:none");
									$("#outVcbtn").attr("style", "display:block");
									$("#inVcbtn").bind('click',function(){inVcControl(nodeId,ajaxResult.device_name)});
									$("#outVcbtn").bind('click',function(){outVcControl(nodeId,ajaxResult.device_name)});
									$("#control_time").html(ajaxResult.control_time);
									//显示扫描虚拟机端口组的按钮
									$("#scanVirtualSwitchBtn").attr("style", "display:block");
									if(ajaxResult.state == 'ENTER_MAINTENANCE'){
										$("#exitMaintenanceMode").attr("style", "display:block");
										$("#exitMaintenanceMode").bind('click',function(){exitMaintenanceModeMethod();});
										$("#pmClose").attr("style", "display:block");
										$("#pmClose").bind('click',function(){pmCloseMethod();});
										$("#pmRestart").attr("style", "display:block");
										$("#pmRestart").bind('click',function(){pmRestart();});
										$("#maintenanceMode").attr("style", "display:none");
										$("#maintenanceMode").unbind('click');
									}else if(ajaxResult.state == 'EXIT_MAINTENANCE'){
										$("#maintenanceMode").attr("style", "display:block");
										$("#maintenanceMode").bind('click',function(){maintenanceModeMethod();});
										$("#exitMaintenanceMode").attr("style", "display:none");
										$("#exitMaintenanceMode").unbind('click');
										$("#pmClose").attr("style", "display:none");
										$("#pmClose").unbind('click');
										$("#pmRestart").attr("style", "display:none");
										$("#pmRestart").unbind('click');
									}else{//没有状态
										$("#maintenanceMode").attr("style", "display:block");
										$("#maintenanceMode").bind('click',function(){maintenanceModeMethod();});
										$("#exitMaintenanceMode").attr("style", "display:none");
										$("#exitMaintenanceMode").unbind('click');
										$("#pmClose").attr("style", "display:none");
										$("#pmClose").unbind('click');
										$("#pmRestart").attr("style", "display:none");
										$("#pmRestart").unbind('click');
									}
									
								}else if(isInvc == 'UM'){
									$("#is_invc").html('UM');
									//显示的是未纳管的按钮及样式
									$("#inVcbtn").attr("style", "display:block");
									$("#outVcbtn").attr("style", "display:none");
									$("#inVcbtn").bind('click',function(){validateNaguan();});//新增加物理机验证工作
									$("#outVcbtn").bind('click',function(){outVcControl(nodeId,ajaxResult.device_name)});
									$("#control_time").html('');
								}else{
									$("#is_invc").html(isInvc);
									//显示的是未纳管的按钮及样式
									$("#inVcbtn").attr("style", "display:block");
									$("#outVcbtn").attr("style", "display:none");
									$("#inVcbtn").bind('click',function(){validateNaguan();});//新增加物理机验证工作
									$("#outVcbtn").bind('click',function(){outVcControl(nodeId,ajaxResult.device_name)});
									$("#control_time").html('');
								}
							} else if(platformCode == 'P' && virtualTypeCode == 'PV'){
								//隐藏设置datastore的按钮
								$("#datatstore_type").attr("style","display:none");
								//power类型物理机，不显示查看硬件状态的按钮
								$("#HardwareStatus").attr("style", "display:none");
								$("#is_invc").html(i18nShow('compute_res_isInvcY'));
								$("#inVcbtn").attr("style", "display:none");
								$("#outVcbtn").attr("style", "display:none");
								
								$("#maintenanceMode").attr("style", "display:none");
								$("#exitMaintenanceMode").attr("style", "display:none");
								$("#pmClose").attr("style", "display:none");
								$("#pmRestart").attr("style", "display:none");
								//显示扫描虚拟机的按钮
								$("#ScanButton").attr("style", "display:block");
								//隐藏扫描虚拟机端口组的按钮
								$("#scanVirtualSwitchBtn").attr("style", "display:none");
								//隐藏修改登录用户信息按钮
								$("#updateLoginInfoBtn").attr("style", "display:none");
							}else {
								//显示硬件状态按钮
								$("#HardwareStatus").attr("style", "display:block");
								$("#is_invc").html(i18nShow('compute_res_isInvcY'));
								$("#inVcbtn").attr("style", "display:none");
								$("#outVcbtn").attr("style", "display:none");
								//显示设置datastore的按钮
								$("#datatstore_type").attr("style","display:inline");
								$("#maintenanceMode").attr("style", "display:none");
								$("#exitMaintenanceMode").attr("style", "display:none");
								$("#pmClose").attr("style", "display:none");
								$("#pmRestart").attr("style", "display:none");
								//隐藏扫描虚拟机的按钮
								$("#ScanButton").attr("style", "display:none");
								//隐藏扫描虚拟机端口组的按钮
								$("#scanVirtualSwitchBtn").attr("style", "display:none");
								//隐藏修改登录用户信息按钮
								$("#updateLoginInfoBtn").attr("style", "display:none");
							}
							$(ajaxResult.ipList).each(function(i,item){
								$("#host_table_id").append("<tr><th>"+item.rm_ip_type_name+"：</th><td><label style='padding-left:0px' id=''>"+item.ip+"</label></td><th></th><td><label id=''></label></td></tr>");
							});	
						}
					)
				}else if(bizType == "vm"){
					showTip("load");
					ajaxCall("/resmgt-compute/host/getCmDeviceVMInfo.action",
						{"vmId" : nodeId},
						function(){
							closeTip();
							//先清空
							$("#vm_virtualTypeCode").val("");//虚拟化类型
							$("#vm_platFormCode").val("");//平台类型
							showDiv("vm_div",i18nShow('compute_res_pool_vmDetail'));
							var rmVmId = "rmVmId:"+nodeId;
							$("#dcId").val(ajaxResult.datacenterID);
				        	showEchartDiv("new_tab");
				        	new_tab("new_tab_t","vm_div",i18nShow('compute_res_pool_vmDetail'));
				        	echartMeterDic(rmVmId);
				        	$("#s_poolName_current").html(ajaxResult.vm_name);
				        	$("#vm_virtualTypeCode").val(ajaxResult.virtualTypeCode);
				        	$("#vm_platFormCode").val(ajaxResult.platFormCode);
				        	//平台类型
				        	var platFormCode = ajaxResult.platFormCode;
				        	//虚拟机的虚拟化类型
				        	var vm_virtualTypeCode = ajaxResult.virtualTypeCode;
				        	$("#vmId").val(nodeId);
							$("#vm_name").html(ajaxResult.vm_name);
							var state = ajaxResult.vmState;
							$("#vmRunningState").val(ajaxResult.vm_name);
							initCondition(nodeId);
							//openstack 机器显示控制
							if(platFormCode == 'O' &&(vm_virtualTypeCode == 'OV' ||vm_virtualTypeCode == 'OI' || vm_virtualTypeCode == 'OK')){
								if("poweron" == state){
									$('#vm_state').html(i18nShow('compute_res_poweron'));
									document.getElementById(2).style.display="inline";//关机
									document.getElementById(5).style.display="inline";
									//强制重启
									document.getElementById('forcedRestart').style.display="inline";
									//挂载卷
									document.getElementById('mountVolume').style.display="inline";
									//卸载卷
									document.getElementById('unloadVolume').style.display="inline";
									document.getElementById('putVmName').style.display="inline";
									document.getElementById(1).style.display="none";
									document.getElementById(3).style.display="none";
									document.getElementById(4).style.display="none";
									document.getElementById(6).style.display="none";
									if(vm_virtualTypeCode =='OI'){//物理机，不显示迁移按钮
										document.getElementById(7).style.display="none";
									}else{
										document.getElementById(7).style.display="inline";
									}
									document.getElementById(8).style.display="none";
									document.getElementById(9).style.display="none";
								}else if("poweroff" == state){
									$('#vm_state').html(i18nShow('compute_res_poweroff1'));
									document.getElementById(1).style.display="inline";//开机
									document.getElementById(5).style.display="none";
									document.getElementById(2).style.display="none";
									document.getElementById(3).style.display="none";
									document.getElementById(4).style.display="none";
									document.getElementById(6).style.display="none";
									document.getElementById(7).style.display="none";
									document.getElementById(8).style.display="none";
									document.getElementById(9).style.display="none";
									document.getElementById('forcedRestart').style.display="none";
									//挂载卷
									document.getElementById('mountVolume').style.display="inline";
									//卸载卷
									document.getElementById('unloadVolume').style.display="inline";
									document.getElementById('putVmName').style.display="inline";
								}else{
									document.getElementById(1).style.display="none";
									document.getElementById(5).style.display="none";
									document.getElementById(2).style.display="none";
									document.getElementById(3).style.display="none";
									document.getElementById(4).style.display="none";
									document.getElementById(6).style.display="none";
									document.getElementById(7).style.display="none";
									document.getElementById(8).style.display="none";
									document.getElementById(9).style.display="none";
									document.getElementById('forcedRestart').style.display="none";
									document.getElementById('mountVolume').style.display="none";
									document.getElementById('unloadVolume').style.display="none";
									document.getElementById('putVmName').style.display="none";
								}
							}else if(vm_virtualTypeCode == 'PV'){
								document.getElementById('forcedRestart').style.display="none";
								document.getElementById('mountVolume').style.display="none";
								document.getElementById('unloadVolume').style.display="none";
								document.getElementById('putVmName').style.display="none";
								if("poweron" == state){
									$('#vm_state').html(i18nShow('compute_res_poweron'));
									document.getElementById(2).style.display="inline";
									document.getElementById(5).style.display="inline";
									document.getElementById(1).style.display="none";
									document.getElementById(3).style.display="none";
									document.getElementById(4).style.display="none";
									document.getElementById(6).style.display="none";
									document.getElementById(7).style.display="inline";
									document.getElementById(8).style.display="none";
									document.getElementById(9).style.display="inline";
								}else if("poweroff" == state){
									$('#vm_state').html(i18nShow('compute_res_poweroff1'));
									document.getElementById(2).style.display="none";
									document.getElementById(5).style.display="none";
									document.getElementById(1).style.display="inline";
									document.getElementById(3).style.display="none";
									document.getElementById(4).style.display="none";
									document.getElementById(6).style.display="none";
									document.getElementById(7).style.display="inline";
									document.getElementById(8).style.display="none";
									document.getElementById(9).style.display="inline";
								}else{
									$('#vm_state').html(i18nShow('compute_res_unknown'));
									document.getElementById(2).style.display="none";
									document.getElementById(5).style.display="none";
									document.getElementById(1).style.display="none";
									document.getElementById(3).style.display="none";
									document.getElementById(4).style.display="none";
									document.getElementById(6).style.display="none";
									document.getElementById(7).style.display="none";
									document.getElementById(8).style.display="none";
									document.getElementById(9).style.display="none";
								}
							}else if((vm_virtualTypeCode == 'KV') || (vm_virtualTypeCode == 'VM')){
								document.getElementById('forcedRestart').style.display="none";
								document.getElementById('mountVolume').style.display="none";
								document.getElementById('unloadVolume').style.display="none";
								document.getElementById('putVmName').style.display="none";
								if("poweron" == state){
									$('#vm_state').html(i18nShow('compute_res_poweron'));
									document.getElementById(1).style.display="none";
									document.getElementById(2).style.display="inline";
									document.getElementById(5).style.display="inline";
									document.getElementById(6).style.display="inline";
									document.getElementById(8).style.display="none";
									document.getElementById(4).style.display="inline";
									document.getElementById(3).style.display="inline";
									document.getElementById(7).style.display="inline";
									document.getElementById(9).style.display="inline";
								}else if("poweroff" == state){
									$('#vm_state').html(i18nShow('compute_res_poweroff1'));
									document.getElementById(1).style.display="inline";
									document.getElementById(2).style.display="none";
									document.getElementById(3).style.display="inline";
									document.getElementById(4).style.display="inline";
									document.getElementById(5).style.display="none";
									document.getElementById(6).style.display="none";
									document.getElementById(7).style.display="none";
									document.getElementById(8).style.display="none";
									document.getElementById(9).style.display="inline";
								}else if("paused" == state){
									$('#vm_state').html(i18nShow('compute_res_hang'));
									document.getElementById(1).style.display="none";
									document.getElementById(2).style.display="inline";
									document.getElementById(3).style.display="inline";
									document.getElementById(4).style.display="inline";
									document.getElementById(5).style.display="none";
									document.getElementById(6).style.display="none";
									document.getElementById(7).style.display="none";
									document.getElementById(8).style.display="inline";
									document.getElementById(9).style.display="inline";
								}else{
									$('#vm_state').html(i18nShow('compute_res_unknown'));
									document.getElementById(1).style.display="none";
									document.getElementById(2).style.display="none";
									document.getElementById(3).style.display="none";
									document.getElementById(4).style.display="none";
									document.getElementById(5).style.display="none";
									document.getElementById(6).style.display="none";
									document.getElementById(7).style.display="none";
									document.getElementById(8).style.display="none";
									document.getElementById(9).style.display="inline";
								}
							}
							$("#availableZoneId").val(ajaxResult.availableZoneId);
							$("#new_tab_inf_b").hide();
							$("#vm_cpu").html(ajaxResult.cpu+" 核");
							$("#vm_mem").html(parseInt(ajaxResult.mem/1024)+" GB");
							$("#vm_disk").html(ajaxResult.disk+"G");
							$("#cvmDeviceId").val(ajaxResult.hostId);
							$("#vm_device_name").html(ajaxResult.device_name);
							$("#vm_cluster_name").html(ajaxResult.cluster_name);
							$("#vm_cdp_name").html(ajaxResult.cdp_name);
							$("#vm_pool_name").html(ajaxResult.pool_name);
							$("#vm_datacenter_cname").html(ajaxResult.datacenter_cname);
							$("#vm_dic_name").html(ajaxResult.dic_name);
							$("#vm_du_name").html(ajaxResult.du_name);
							$("#vm_app_name").html(ajaxResult.appInfo_name);
							$("#onlineTime").html(ajaxResult.online_time);
							$("#vmInfo tr[name='rm_ip_info']").remove();
							$("#duId").val(ajaxResult.duID);
							$("#dic_name").val(ajaxResult.dic_name);
							if(undefined==ajaxResult.loginName||null==ajaxResult.loginName||""==ajaxResult.loginName){
								$("#login_name").html("");
							}else{
								$("#login_name").html(ajaxResult.firstName+ajaxResult.lastName+"("+ajaxResult.loginName+")");
							}
							$("#user_name").html(ajaxResult.userName);
							if(ajaxResult.tenantName){
								$("#tenantTr").show();
								$("#projectVpcId").val(ajaxResult.projectId);
								$("#tenantId").html(ajaxResult.tenantName);
							}else{
								$("#tenantTr").hide();
							}
							var mountDiskSize = undefined==ajaxResult.mountDiskSize||null==ajaxResult.mountDiskSize||""==ajaxResult.mountDiskSize?'0':ajaxResult.mountDiskSize;
							
							$("#mountInfo").html("<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=showMountVolumes('"+nodeId+"')>"+ajaxResult.mountDiskNumber+i18nShow('compute_res_device_mountDiskNumbe')+"/"+mountDiskSize+"G</a>");
							//$("#mountInfo").html(ajaxResult.mountDiskNumber+"/"+ajaxResult.mountDiskSize);
							if("已上线" == ajaxResult.dic_name){
								$("#changeSystem").css('display', 'inline');
							}else{
								$("#changeSystem").css('display', 'none');
							}
							var vmIp = "";
							$(ajaxResult.ipList).each(function(i,item){
								if(i%2==0){
									vmIp += "<tr name='rm_ip_info'>";
								}
								vmIp += "<th>"+item.rm_ip_type_name+"：</th><td><label id=''>"+item.ip+"</label></td>";
								if(i%2!=0){
									vmIp += "</tr>";
								}
							});	
							if(ajaxResult.ipList.length % 2 !=0){
								vmIp += "</tr>";
							}
							$("#vmInfo").append(vmIp);
							// 控制台按钮只对vmware有效
							if(vm_virtualTypeCode == 'VM' && "poweron" == state){
							    document.getElementById(11).style.display="inline";
							} else {
							    document.getElementById(11).style.display="none";
							}
							// VNC按钮只对kvm有效,vnc按钮现在也叫远程控制
                            if(vm_virtualTypeCode == 'KV' && "poweron" == state){
                                document.getElementById(10).style.display="inline";
                            } else {
                                document.getElementById(10).style.display="none";
                            }
                            //openstackVNC
                            if(platFormCode == 'O' && "poweron" == state){
                                document.getElementById(12).style.display="inline";
                            } else {
                                document.getElementById(12).style.display="none";
                            }
						}
					)
				}
			};
				 
				
	expandFun= function(){
	expandGlobalFunc();return;
	
	function expandGlobalFunc(){
		ajaxCall(
				"/resmgt-compute/tree/buildComputeResTree.action",
				{"treeId":nodeId,"bizType":bizType},
//				function(){
//					$.each(ajaxResult,function(){
//						addNode(this.id,this.name,false,this.bizType,false,this.isParent,nodeId);
//					});
//				}
				asyncAddNode
				);
	}

//	if(bizType == "dc"){
//		
//	}else if(bizType == "pool"){
//		loadPoolData();
//	}else if(bizType == "cdp"){
//		loadCdpData();
//	}else if(bizType == "cluster"){
//		showDiv("gridDiv", "已关联物理机信息：");
//		//已关联物理机信息显示表格的生成。
//		initCmClusterHostInfo();
//	}else if(bizType == "host"){
//		ajaxCall("/resmgt-common-host/resmgt-common/host/getCmDeviceHostInfo.action",
//			{"hostId" : nodeId},
//			function(){
//				showDiv("host_div","基本信息");
//				$("#device_name").val(ajaxResult.device_name);
//				$("#sn").val(ajaxResult.sn);
//				$("#cpu").val(ajaxResult.cpu);
//				$("#mem").val(ajaxResult.mem);
//				$("#cpuUsed").val(ajaxResult.cpuUsed);
//				$("#memUsed").val(ajaxResult.memUsed);
//				$("#device_model").val(ajaxResult.device_model);
//				$("#manufacturer").val(ajaxResult.manufacturer);
//				$("#is_active").val(ajaxResult.is_active);
//				$("#seat_name").val(ajaxResult.seat_name);
//				$("#disk").val(ajaxResult.disk);
//				$("#cluster_name").val(ajaxResult.cluster_name);
//				$("#cdp_name").val(ajaxResult.cdp_name);
//				$("#pool_name").val(ajaxResult.pool_name);
//				$("#datacenter_cname").val(ajaxResult.datacenter_cname);
//			}
//		)
//	}else if(bizType == "vm"){
//		ajaxCall("/resmgt-common-host/resmgt-common/host/getCmDeviceVMInfo.action",
//				{"vmId" : nodeId},
//				function(){
//					showDiv("vm_div","基本信息");
//					$("#vm_name").val(ajaxResult.vm_name);
//					$("#vm_sn").val(ajaxResult.sn);
//					$("#vm_cpu").val(ajaxResult.cpu);
//					$("#vm_mem").val(ajaxResult.mem);
//					$("#vm_device_name").val(ajaxResult.device_name);
//					$("#vm_cluster_name").val(ajaxResult.cluster_name);
//					$("#vm_cdp_name").val(ajaxResult.cdp_name);
//					$("#vm_pool_name").val(ajaxResult.pool_name);
//					$("#vm_datacenter_cname").val(ajaxResult.datacenter_cname);
//				}
//			)
//		}
	};
			
	/*注册方法*/
	regFunction(clickFun,expandFun);
	
    $.ajax({
        async:false,   
        cache:false,   
        type: 'POST',   
        url: ctx+"/resmgt-compute/tree/buildComputeResTree.action",//请求的action路径   
        beforeSend: function () {
        	showTip("load");
        },
        error: function () {//请求失败处理函数   
            showError(i18nShow('compute_res_requestError'));   
        },  
        success:function(data){ //请求成功后处理函数。     
        	zTreeInit("treeRm", data);
        	zTree.selectNode(zTree.getNodeByParam("id","-1",null));
        	bizType = "xd";
        	clickFun();
        	closeTip();
        	/* //点击新增数据中心
			$("#center_add").click(function() {
				addCenter(getSelectNodeId());
			});
        	 */
			//点击新增计算资源池
			$("#pool_add").click(function() {
				createPoolData(nodeId);
			});
			//点击修改计算资源池
			$("#pool_modify").click(function() {
				showPoolUpdateDiv();

			});
			//点击删除计算资源池
			$("#pool_del").click(function() {
				deletePoolData();
			});
			
			//点击新增CDP
			$("#cdp_add").click(function() {
// 				addCdp(getSelectNodeId());
				createCdpData(nodeId);
				
			});
			//点击修改CDP
			$("#cdp_modify").click(function() {
				showCdpUpdateDiv();
			});
			//点击删除CDP
			$("#cdp_del").click(function() {
				deleteCdpData();
			});
			//点击新增集群
 			$("#cluster_add").click(function() {
 				createClusterData(nodeId);
 			});
 			//在资源池下点击新建集群
 			$("#cluster_add_underRes").click(function() {
 				createClusterUnderRes(nodeId);
 			});
 			//点击删除集群
 			$("#cluster_delete").click(function() {
 				deleteClusterData(nodeId);
 			});
 			//点击修改集群
 			$("#cluster_update").click(function() {
 				showClusterUpdateDiv(nodeId);
 			});
			//点击新增关联物理主机
// 			$("#host_add").click(function() {
// 				addHosts(getSelectNodeId());

// 			}); 
        }   
    });
    
    $("#btnAddTreeNode").click(function(){
    	addNode("1002","cba",false);//下级添加
    	//addNode("3456","abc",true);//同级添加
    })
    
    $("#btnDelTreeNode").click(function(){
    	deleteTreeNode(nodeId);//删除选中节点
	   	//deleteTreeNode("S001");//删除指定节点
    })
    
    $("#btnModTreeNode").click(function(){
    	//modifyNode("S001","TEST");//修改指定节点
    	//modifyNode(null,"TEST");//修改选中节点
    	//modifyNode("haha");//最简便修改选中节点    	
    })
    var jsonStr = '{"id":"2212","username":"adsfad"}';
   	var obj = jQuery.parseJSON(jsonStr);
    jsonToDiv("testDiv",obj);
});

function loadDatastore(hostId){
	$("#gridTable_div2").show();
	$("#gridPager2").show();
	$("#gridTable2").jqGrid().GridUnload("gridTable2");
	$("#gridTable2").jqGrid({
		url : ctx+"/resmgt-storage/device/getDataStoresList.action",
		postData:{"hostId":hostId},
		rownumbers : false, 
		datatype : "json", 
		mtype : "post", 
		height : 170,
		autowidth : true, 
		multiselect:false,
		colModel : [
			{name : "id",index : "id",label: "id",width : 50,sortable : true,align : 'left',hidden:true},
            {name : "deviceName",index : "deviceName",label: i18nShow('compute_res_datastore_storageDevice'),	width : 100,sortable : true,align : 'left'},
            {name : "orderNum",index : "orderNum",label: i18nShow('compute_res_datastore_orderNum'),	width : 60,sortable : true,align : 'left'},
            {name : "name",index : "name",label: i18nShow('compute_res_datastore_name'),	width : 60,sortable : true,align : 'left'},
            {name : "path",index : "path",label: i18nShow('compute_res_datastore_path'),	width : 60,sortable : true,align : 'left'},
		    {
    			name : "flag",
    			index : "flag",
    			label: i18nShow('compute_res_whetherDefault'),
    			width : 95,
    			sortable : true,
    			align : 'left',
                formatter: function(cellValue,options,rewObject){
    			var result = "";
    			if(cellValue>0)
    				  result = "<font style='color:green'>"+i18nShow('compute_res_yes')+"</font>";
    			return result;
                }
		          }],
		            
		viewrecords : true,
		sortname : "orderNum",
		rowNum : 5,
		rowList : [10, 20, 50, 100 ],
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#gridPager2",
		hidegrid : false
	});
}

function showDiv(divId,title){
	$("#rightContentDiv div").hide();
	$("#" + divId).fadeIn('fast');
	$("#lab_title").html(title);
}

function openDialog(divId,title){
		$("#"+divId).dialog({
				autoOpen : true,
				modal:true,
				height:300,
				width:400,
				title:title,
//				draggable: false,
		       // resizable:false
		})
}


//刷新树
function refreshTree(){
	var treeObj = $.fn.zTree.getZTreeObj("treeRm");
	//var nodes = treeObj.getSelectedNodes();

	treeObj.reAsyncChildNodes(null, "refresh");

}
//刷新树的上级节点
function refreshTreeParentNode(){
	var treeObj = $.fn.zTree.getZTreeObj("treeRm");
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length>0) {
		treeObj.reAsyncChildNodes(nodes[0].getParentNode(), "refresh");
	}
}
//刷新树节点
function refreshTreeNode(){
	var treeObj = $.fn.zTree.getZTreeObj("treeRm");
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length>0) {
		treeObj.reAsyncChildNodes(nodes[0], "refresh");
	}
}
//根据已选中的树节点id，分别节点类型，获取节点Id
function getSelectNodeId()
{
	var treeObj = $.fn.zTree.getZTreeObj("treeRm");
	var nodes = treeObj.getSelectedNodes();
	var node_id = nodes[0].id;
	var obj_id = null;
	if(node_id.indexOf("center") > 0)
		obj_id = node_id.substring(0,node_id.length-6);
	else if(node_id.indexOf("pool") > 0)
		obj_id = node_id.substring(0,node_id.length-4);
	else if(node_id.indexOf("cdp") > 0)
		obj_id = node_id.substring(0,node_id.length-3);
	else if(node_id.indexOf("cluster") > 0)
		obj_id = node_id.substring(0,node_id.length-7);
	else if(node_id.indexOf("host") > 0)
		obj_id = node_id.substring(0,node_id.length-4);
	else if(node_id.indexOf("vm") > 0)
		obj_id = node_id.substring(0,node_id.length-7);
	return obj_id;
}
//根据已选中的树节点id，获取节点名称
function getSelectNodeName()
{
	var treeObj = $.fn.zTree.getZTreeObj("treeRm");
	var nodes = treeObj.getSelectedNodes();
	return nodes[0].name;
}

//左键单击,进入查看页面
function zTreeOnClick(event, treeId, treeNode) {
	var node_id = treeNode.id;
	if(node_id.indexOf("center") > 0)
		showCenter(node_id.substring(0,node_id.length-6));
	else if(node_id.indexOf("pool") > 0)
		showPool(node_id.substring(0,node_id.length-4));
	else if(node_id.indexOf("cdp") > 0)
		showCdp(node_id.substring(0,node_id.length-3));
	else if(node_id.indexOf("cluster") > 0)
		showCluster(node_id.substring(0,node_id.length-7));
	else if(node_id.indexOf("host") > 0)
		showHost(node_id.substring(0,node_id.length-4));
	else if(node_id.indexOf("vm") > 0)
		showVm(node_id.substring(0,node_id.length-2));
	
}

//查看数据中心，右侧打开查看数据中心页面
function showCenter(centerId){
	var rightpage = document.getElementById("rightpageobj");
	rightpage.src="dataCenterMag.jsp?actType=detail&centerId="+centerId;
	
}
//查看计算资源池，右侧打开查看计算资源池页面
function showPool(poolId){
	var rightpage = document.getElementById("rightpageobj");
	rightpage.src="hostResPoolMag.jsp?actType=detail&poolId="+poolId;
	
}
//查看CDP，右侧打开查看CDP页面
function showCdp(cdpId){
	var rightpage = document.getElementById("rightpageobj");
	rightpage.src="cdpMag.jsp?actType=detail&cdpId="+cdpId;
	
}
//查看cluster，右侧打开查看cluster页面
function showCluster(clusterId){
	var rightpage = document.getElementById("rightpageobj");
	rightpage.src="clusterMag.jsp?actType=detail&clusterId="+clusterId;
	
}
//查看物理机，右侧打开查看物理机页面
function showHost(deviceId){
	var rightpage = document.getElementById("rightpageobj");
	rightpage.src="deviceHostMag.jsp?actType=detail&deviceId="+deviceId;
	
}
//查看虚拟机，右侧打开查看虚拟机页面
function showVm(deviceId){
	var rightpage = document.getElementById("rightpageobj");
	rightpage.src="deviceVirtualMag.jsp?actType=detail&deviceId="+deviceId;	
}

function hideAllBtn(){
	$("input[id^='btn_']").hide();
}
//通过名字模糊查询树
function searchTreeByCname() {
	var searchForName = $.trim($("#txt_search").val()).replace(/(^\s*)|(\s*$)/g, "");
	var url;
	if(searchForName == ""){
		url = ctx+"/resmgt-compute/tree/buildComputeResTree.action";
	}else{
		url = ctx+"/resmgt-compute/tree/getComputeTreeListByNodeName.action";
	}
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,  
		data : {"nodeName" : searchForName,"searchType":$("#sel_type").val()},
		error : function() {//请求失败处理函数   
			showError(i18nShow('compute_res_requestError'));
		},
		success : function(data) { //请求成功后处理函数。     
			zTreeInit("treeRm", data);
		}
	});
}
//新增加验证纳管物理机数值
function validateNaguan(){
	$.post(ctx + "/resmgt-compute/host/validateNaguan.action",{},function(data){
		if(data.result=="false"){
			showError(i18nShow('compute_res_inVcControl_tip'));
		}else{
			inVcControl(nodeId,ajaxResult.device_name)
		}
	});
}
//查询物理机是否含有默认的datastore
function getDefaultDatastore(hostId){
	var flag = false;
	var hostId = $("#hostId").val();
	$.ajax({
	    async:false,
	    cache:false,
	    type: 'POST',
	    url:ctx+"/resmgt-common/device/getDefaultDatastore.action",
	    data:{"hostId":hostId},
	    error: function () {//请求失败处理函数
	    	showError(i18nShow('compute_res_requestError'));
	    },
	    success:function(data){ //请求成功后处理函数。
	    	var datastoreId = data.datastoreId;
	    	if(datastoreId!=null && datastoreId!=""){
	    		flag =  true;//有默认datastore
	    	}else{
	    		flag =  false;//没有默认datastore
	    	}
	    	
	    }
	});
	return flag;
}

function inVcControl(hostId,hostName){
		f = getDefaultDatastore(hostId);
		if(f== true){
			//true：含有缺省值，可直接进行
			inVcControlReal(hostId,hostName);
		}else{
			showTip(i18nShow('compute_res_selectDataStore_tip'));
		}
}

function inVcControlReal(hostId,hostName){
	showTip(i18nShow('compute_res_inVcControl_tip1'),function(){
		showTip("load");
		$("#inVcbtn").attr("disabled", "disabled");
		$("#inVcbtn").removeClass("btn").addClass('btn_disabled');
		$("#outVcbtn").attr("disabled", "disabled");
		$("#outVcbtn").removeClass("btn").addClass('btn_disabled');
		$.post(ctx + "/resmgt-common/device/inVCtrole.action?hostId="+hostId+"&hostName="+hostName,
			function(data) {
			if(data.result!=null && data.result=="success"){
				closeTip();
				showTip(i18nShow('compute_res_inVcControl_success'));
				$("#maintenanceMode").attr("style", "display:block");
				$("#maintenanceMode").bind('click',function(){maintenanceModeMethod();});
				$("#is_invc").html(i18nShow('compute_res_isInvcY'));
				$("#inVcbtn").attr("style", "display:none");
				$("#inVcbtn").unbind('click');
				$("#outVcbtn").attr("style", "display:block");								
				$("#outVcbtn").bind('click',function(){outVcbtn(hostId,hostName)});
				//显示扫描虚拟机端口组的按钮
				$("#scanVirtualSwitchBtn").attr("style", "display:block");
				//判断是否显示进入维护模式/退出维护模式/关机的按钮
				var state = getPmState(hostId);
				//为空是还未进入过维护模式或者退出维护模式；
		    	if(state==null || state==""){
		    		$("#maintenanceMode").attr("style", "display:block");
		    		$("#maintenanceMode").bind('click',function(){maintenanceModeMethod();});
					$("#exitMaintenanceMode").attr("style", "display:none");
					$("#pmClose").attr("style", "display:none");
					$("#pmRestart").attr("style", "display:none");
		    	}else if(state=='EXIT_MAINTENANCE'){
		    		//退出维护模式了
		    		$("#maintenanceMode").attr("style", "display:block");
		    		$("#maintenanceMode").bind('click',function(){maintenanceModeMethod();});
					$("#exitMaintenanceMode").attr("style", "display:none");
					$("#pmClose").attr("style", "display:none");
					$("#pmRestart").attr("style", "display:none");
		    		
		    	}else if(state=='ENTER_MAINTENANCE'){
		    		//进入维护模式了
		    		$("#maintenanceMode").attr("style", "display:none");
					$("#exitMaintenanceMode").attr("style", "display:block");
					$("#exitMaintenanceMode").bind('click',function(){exitMaintenanceModeMethod();});
					$("#pmClose").attr("style", "display:block");
					$("#pmClose").bind('click',function(){pmClose();});
					$("#pmRestart").attr("style", "display:block");
					$("#pmRestart").bind('click',function(){pmRestart();});
		    	}
			}else{
				closeTip();
				showError(i18nShow('compute_res_inVcControl_fail')+data.result);
			}
		}).error(function() {
			closeTip();
			showError(i18nShow('compute_res_error'),null,"red");
		});
		$("#inVcbtn").removeAttr("disabled").removeClass("btn_disabled").addClass("btn");
		$("#outVcbtn").removeAttr("disabled").removeClass("btn_disabled").addClass("btn");
	});
}

function outVcControl(hostId,hostName){
	showTip(i18nShow('compute_res_outVcControl_tip'),function(){
	showTip("load");
	$("#inVcbtn").attr("disabled", "disabled");
	$("#inVcbtn").removeClass("btn").addClass('btn_disabled');
	$("#outVcbtn").attr("disabled", "disabled");
	$("#outVcbtn").removeClass("btn").addClass('btn_disabled');
	$.post(ctx + "/resmgt-common/device/outVCtrole.action?hostId="+hostId+"&hostName="+hostName,
		function(data) {
		if(data.result!=null && data.result=="success"){
			closeTip();
			showTip(i18nShow('compute_res_outVcControl_success'));
			$("#is_invc").html(i18nShow('compute_res_isInvcN'));
			$("#outVcbtn").attr("style", "display:none");
			$("#outVcbtn").unbind('click');
			$("#inVcbtn").attr("style", "display:block");		
			$("#inVcbtn").bind('click',function(){validateNaguan()});
			
			$("#maintenanceMode").attr("style", "display:none");
			$("#exitMaintenanceMode").attr("style", "display:none");
			$("#pmClose").attr("style", "display:none");
			$("#pmRestart").attr("style", "display:none");
			//隐藏扫描虚拟机端口组的按钮
			$("#scanVirtualSwitchBtn").attr("style", "display:none");
//			$("#inVcbtn").removeAttr("disabled").removeClass("btn_disabled").addClass("btn");
			//$("#inVcbtn").attr("style", "display:block");
		}else if(data.result!=null && data.result==i18nShow('compute_res_outVcControl_hostnotExit')){
			closeTip();
			showError(i18nShow('compute_res_outVcControl_alreadyOut'));
			$("#is_invc").html(i18nShow('compute_res_isInvcN'));
			$("#outVcbtn").attr("style", "display:none");
			$("#outVcbtn").unbind('click');
			$("#inVcbtn").attr("style", "display:block");		
			$("#inVcbtn").bind('click',function(){validateNaguan()});
			
			$("#maintenanceMode").attr("style", "display:none");
			$("#exitMaintenanceMode").attr("style", "display:none");
			$("#pmClose").attr("style", "display:none");
			$("#pmRestart").attr("style", "display:none");
			
		}else{
			closeTip();
			showError(i18nShow('compute_res_inVcControl_fail')+data.result);
//			$("#outVcbtn").removeAttr("disabled").removeClass("btn_disabled").addClass("btn");
		}
		//加载挂载的datastore信息
		loadDatastore(hostId);
	}).error(function() {
		closeTip();
		showError(i18nShow('compute_res_error'),null,"red");
//		$("#outVcbtn").removeAttr("disabled").removeClass("btn_disabled").addClass("btn");
	});
	$("#inVcbtn").removeAttr("disabled").removeClass("btn_disabled").addClass("btn");
	$("#outVcbtn").removeAttr("disabled").removeClass("btn_disabled").addClass("btn");
	});
}

function getPmState(hostId){
	var flag = "";
	$.ajax({
	    async:false,
	    cache:false,
	    type: 'POST',
	    url:ctx+"/resmgt-common/device/getPmRunningState.action",
	    data:{"hostId":hostId},
	    error: function () {//请求失败处理函数
	    	showError(i18nShow('compute_res_requestError'));
	    },
	    success:function(data){ //请求成功后处理函数。
	    	flag = data.result;
	    }
	});
	return flag;

}
//显示设备挂载的磁盘列表
function showMountVolumes(vmId){
	openDialog('gridTable_showMount_div',i18nShow('compute_res_volume_info'), 650, 450);
	$("#gridTable_showMonutDiv").show();
	$("#gridTable_showMount").jqGrid().GridUnload("gridTable_showMount");
	$("#gridTable_showMount").jqGrid({
		url : ctx + "/resmgt-common/device/getRmDeviceVolumesRefPoList.action",
		rownumbers : false,
		datatype : "json",
		mtype : "post",
		multiselect : false,
		postData : {"deviceId":vmId},
		height : 310,
		autowidth : true,
		colModel : [{
			name : "id",
			index : "id",
			label : i18nShow('compute_res_rimaryKey_id'),
			width : 10,
			sortable : false,
			align : 'left',
			hidden : true
		},
		{
			name : "diskSize",
			index : "diskSize",
			label : i18nShow('compute_res_volume_diskSize'),
			width : 80,
			sortable : true,
			align : 'left'
		},
		{
			name : "diskType",
			index : "diskType",
			label : i18nShow('compute_res_volume_diskType'),
			width : 80,
			sortable : true,
			align : 'left',
			formatter:function(cellValue,options,rowObject){
				if(cellValue == "exterDisk"){
					return i18nShow('compute_res_volume_exterDisk');
				}else{
					return cellValue;
				}
			}
		},
		{
			name : "mountStatus",
			index : "mountStatus",
			label : i18nShow('compute_res_volume_status'),
			width : 80,
			sortable : true,
			align : 'left',
			hidden : true,
			formatter:function(cellValue,iptions,rowObject){
				if(cellValue == 'mount' ){
					return i18nShow('compute_res_volume_mount');
				}else{
					return i18nShow('compute_res_volume_unmont');
				}
			}
		}],
		viewrecords : true,
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			repeatitems : false
		},
		pager : "#gridPager_showMount",
		hidegrid : false
	});
}
//获取物理机的运行状态
function getCmDeviceHosRunningState(hostId){
	var flag = "";
	$.ajax({
	    async:true,
	    cache:false,
	    type: 'POST',
	    url:ctx+"/resmgt-compute/host/getCmDeviceHosRunningState.action",
	    data:{"hostId":hostId},
	    error: function () {//请求失败处理函数
	    	$("#impiFlag").val('N');
	    	$("#hostState").html('Unknown');
	    },
	    success:function(data){ //请求成功后处理函数。
	    	flag = data.running_State;
	    	if(flag == 'Unknown' || '未知'){
	    		$("#impiFlag").val('N');
	    	}else{
	    		$("#impiFlag").val('Y');
	    	}
	    	$("#hostState").html(flag);
	    }
	});
}


