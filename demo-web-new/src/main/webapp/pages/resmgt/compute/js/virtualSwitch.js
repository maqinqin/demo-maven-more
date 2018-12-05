$(function() {
	$("#scanVirtualSwitchBtn").click(function() {
		var dialogParent = $("#dialog-switch").parent();  
	    var dialogOwn = $("#dialog-switch").clone();  
	    dialogOwn.hide();  
	    
		 $("#dialog-switch").dialog(
				 {
			         autoOpen: false,
			         height:'auto',
			         width:'900',
			         height:'1000',
			        modal: true,
			        close: function () {
			        	 dialogOwn.appendTo(dialogParent);
		                $(this).dialog("destroy").remove();   
		            }
			     }	
		    );
    $("#dialog-switch").dialog("open");
    //var hostId = $("#hide_nodeId").val();
    var hostId = $("#hostId").val();
		var PostData={HostId:hostId};
		$("#dialog-switch").dialog({
			autoOpen : true,
			title : i18nShow('compute_res_virtualswitch'),
			height : 'auto'
		});
		var path = ctx + "/virtualswitch/resmgt/scanVirtualSwitch.action";
		$.ajax({
			async : false,
			cache : false,
			type : 'post',
			datatype : "json",
			url : path,  
			data : {'hostId':hostId},
			beforeSend: function () {
	        	showTip("load");
	        },
			error : function() {//请求失败处理函数 
				closeTip();
				showTip(i18nShow('compute_res_load_error'));
			},
			success : function(data) { //请求成功后处理函数。
				closeTip();
				//$("#switchInfo_list").append("<tr><td align='center' colspan='5'>交换机信息列表</td></tr>");
				$("#switchInfo_list").append("<tr><td align='center'>"+i18nShow('compute_res_number')+"</td>"+
		    			"<td align='center'>"+i18nShow('compute_res_virtualswitch_name')+"</td>"+
		    			"<td align='center'>"+i18nShow('compute_res_virtualswitch_networkTag')+"</td>"+
		    			"<td align='center'>"+i18nShow('compute_res_virtualswitch_vlanid')+"</td></tr>");
				for(var i=0;i<data.length;i++){
					var number = i+1;
						$("#switchInfo_list").append("<tr id=''><td width='10%'id='number"+i+"' name='number"+i+"'>"+number+"</td>"+
						"<td width='22%'  value='"+data[i].switchName+"'>"+data[i].switchName+"</td>"+
						"<td width='22%'  value='"+data[i].switchName+"'>"+data[i].networkTag+"</td>"+
						"<td width='22%'  value='"+data[i].switchName+"'>"+data[i].vlanId+"</td>"+
						"</tr>")
				}
				
			}
		});
		
	});
});



function getVirtualSwitchByHostId(){
	var hostId = $("#hostId").val();
	var setting = {
			view: {
				showLine: false
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
	
	
	$.ajax({   
        async:false,   
        cache:false,   
        type: 'POST', 
        data : {'hostId':hostId},
        url: ctx+"/virtualswitch/resmgt/getVirtualSwitchListByHostId.action",//请求的action路径   
        beforeSend: function () {
        	showTip("load");
        },
        error: function () {//请求失败处理函数   
        	closeTip();
            showError(i18nShow('compute_res_load_error'));   
        },   
        success:function(data){ //请求成功后处理函数。     
        	closeTip();
        	if(data==""){
        		showTip(i18nShow('compute_res_virtualswitch_tip'));
        		//***************返回摘要的样式************************
        		$("#new_tab_t").attr("class", "BtCur");//将所有选项置为选中
    	        $("#new_tab_pm").attr("class", "BtVCur"); 
    	        $("#new_tab_pmlog").attr("class", "BtVCur"); 
    	        $("#new_tab_vmlog").attr("class", "BtVCur");
    	        $("#new_tab_virtualSwitch").attr("class", "BtVCur");
    	        $("#new_tab_vm").attr("class", "BtRCur");//设置当前选中项为选中样式
    	        $("#new_tab_body_virtualSwitch").hide();
    	        $("#new_tab_vmlog").hide();
    	        $("#new_tab_pmlog").show();
    	        $("#new_tab_inf").show();
    	        $("#new_tab_inf_a").show();
    	        $("#new_tab_inf_b").show();
    	        $("#echart_meterDic_div").show();
    	        $("#echart_meterDic_left").show();
    	        $("#echart_meterDic_right").show();
    	        $("#con_div").show();
	        	$("#new_tab_pm").hide();
	        	$("#new_tab_pmlog").show();
	        	var hostType = $("#hostType").val();
	        	if(hostType=='VM'){
	        		$("#new_tab_virtualSwitch").show();
	        	}else{
	        		$("#new_tab_virtualSwitch").hide();
	        	}
		        
	        	$("#new_tab_vm").show();
	 	        $("#new_tab_body_pm").hide();
	 	        $("#new_tab_body_vm").hide();
	 	     //***************返回摘要的样式************************
        	}else{
        		$.fn.zTree.init($("#treeDemo"), setting, data);
        	}
        }   
    });
}

function closeVsView(){
	$("#dialog-switch").dialog("close");
}

function savevSwitchInfo(){
	var hostId = $("#hostId").val();
	$.ajax({   
        async:false,   
        cache:false,   
        type: 'POST', 
        data : {'hostId':hostId},
        url: ctx+"/virtualswitch/resmgt/saveScanVirSwitchInfo.action",//请求的action路径   
        beforeSend: function () {
        	showTip("load");
        },
        error: function () {//请求失败处理函数   
        	closeTip();
            showError(i18nShow('compute_res_saveFail'));   
        },   
        success:function(data){ //请求成功后处理函数。     
        	closeTip();
        	if(data.result == 'success'){
        		showTip(i18nShow('compute_res_scanVm_import_success'));
        		closeVsView();
        		//**************跳转到虚拟交换机页签***************
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
        		getVirtualSwitchByHostId();
        		//**************跳转到虚拟交换机页签***************
        	}
        	
        }   
    });
}

