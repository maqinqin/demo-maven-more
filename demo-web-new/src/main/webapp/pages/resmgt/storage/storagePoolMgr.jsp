<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<script type="text/javascript" src="${ctx}/plugins/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/storage/js/storagePoolMgr.js"></script>
<!-- <script type="text/javascript" src="${ctx}/jquery/js/jquery-jiajian.js"></script>-->
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
<title>存储资源池管理</title>
<style type="text/css">
html,body{height:100%;}
#add_sp_div p{width:600px; margin-bottom:5px; margin-left:16px;height:40px; }
#add_sp_div i {text-align:left; padding-right:3px;}
#add_sp_div p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#add_sp_div .winbtnbar{margin-right:28px; width:572px;}
#add_sp_div label{display:block; padding-left:105px;}

#add_scp_div p{width:600px; margin-bottom:5px; margin-left:16px; }
#add_scp_div i {text-align:left; padding-right:3px;}
#add_scp_div p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#add_scp_div .winbtnbar{margin-right:44px; width:550px;}
#add_scp_div label{display:block; padding-left:105px;}
.current:link, .uc-radio .current:visited, .uc-radio .current:hover {
	
	background:url(../../pages/workflow/designer/images/btn_gray8Y.jpg) no-repeat left center;
	height:25px;
	cursor:pointer;
	color:#000;
	font-size:12px;	
	font-family:"微软雅黑";
	line-height:25px;
	text-align:center;
	border:none;
}
.unit
{ 
	
	background:url(../../pages/workflow/designer/images/btn_gray8.jpg) no-repeat left center;
	height:25px;
	cursor:pointer;
	color:#000;
	font-size:12px;	
	font-family:"微软雅黑";
	line-height:25px;
	text-align:center;
	border:none;
}
.unit:hover
{

	background:url(../../pages/workflow/designer/images/btn_gray8Y.jpg) no-repeat left center;
	height:25px;
	cursor:pointer;
	color:#000;
	font-size:12px;	
	font-family:"微软雅黑";
	line-height:25px;
	text-align:center;
	border:none;
}
#add_scp_div a:link,#add_scp_div a:visited{
	color:#18a689;
	text-decoration:none;
}

#add_scp_div a:hover{
	color:#18a689;
	text-decoration:none;
}
</style>
</head>
<body  class="main1">
<input type="hidden" id="hid_icon" /> 
<input type="hidden" id="hid_nodeId" />
<input type="hidden" id="hid_pnodeId" />
 
	<div id="div_tip"></div>
	<div class="page-header">
			<h1>
			<div class="WorkBtBg"><icms-i18n:label name="res_title_storage_resPool"/></div>
			<div class="WorkSmallBtBg">
				<small>
					<icms-i18n:label name="res_desc_storage_resPool"/>
				</small>
			</div>	
					
			</h1>
	</div>
	
  <div id="treeDiv" class="tree-div tree-div1">
  <a href="javascript:" class="tree-bar"><i class="fa fa-caret-left"></i></a>
  	  <div class="pageFormContent tree-search">
  	  		<p style="margin:0px;">
				<select id="sel_type" class="selInput" style=" float: left;width: 80px" >
				<option value="dc"><icms-i18n:label name="bm_l_data_center"/></option>
				<option value="sp"><icms-i18n:label name="res_l_pool"/></option>
				<option value="scp"><icms-i18n:label name="res_l_storage_scpname"/></option>
				<option value="sd"><icms-i18n:label name="res_l_storage_device"/></option></select>
				<input name="sn" type="text"  id="txt_search" size="30" class="textInput" style="width: 43%;float: left;">
					<span class="input-group-btn"><a href="#" class="btn-default"><i class="fa fa-search" style="line-height:15px;width:auto;" onclick="searchTreeByCname()"></i></a></span>
				<!-- <input type="button" class="btn_search_s" style="float: left; margin-left:5px;margin-top: 3px;" onclick="searchTreeByCname()" /> -->
			</p>
	  </div>
	  <ul id="treeRm" class="ztree clear" style="padding:0 0 0 5px;overflow-y:auto;height:90%;"></ul>
  </div>
  <div id="rightContentDiv" class="right-div right-div1" style="width:74%">
  	
  	<div id="xd_div" style="display:none;font-size:18px;">
  		<table border="0" cellpadding="0" cellspacing="0" class="pagelist" style="float:left;width:40%" >
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/datacenter.png" /></th><td><label style="font-size: 14px;"><icms-i18n:label name="bm_l_data_center"/></label></td>
			</tr>
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/respoolstor.png" /></th><td><label style="font-size: 14px;"><icms-i18n:label name="res_l_storage_resPoolAndChild"/></label></td>
			</tr>
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/storagedev.png" /></th><td><label style="font-size: 14px;"><icms-i18n:label name="res_l_storage_device"/></label></td>
			</tr>			
		</table>
		<!-- span>
			<p>项目中用到的按钮样式，包括在jqgrid表格上显示的小图标样式</p>
			<input type="button" title="确定" class="btn_ok"  onclick="demo07()" />
			<input type="button" title="取消" class="btn_cancel" />	
			<input type="button" title="新增" class="btn_add" onclick="hideMenu()" />
			<input type="button" title="删除" class="btn_del" />
			<input type="button" title="修改" class="btn_edit" />
			<input type="button" title="查询" class="btn_search" />
			<input type="button" title="关联" class="btn_link" />		
			<input type="button" title="刷新" class="btn_refresh" />		
			<input type="button" title="重置" class="btn_reset" />
			<input type="button" title="上一步" class="btn_last" />		
			<input type="button" title="下一步" class="btn_next" />
			<input type="button" title="确定" class="btn_s" />			
			<input type="button" title="确定" class="btn_ok_s" />		
			<input type="button" title="修改" class="btn_edit_s" />		
			<input type="button" title="删除" class="btn_del_s" />		
			<input type="button" title="查询" class="btn_search_s" />
			<input type="button" title="关联" class="btn_link_s" />		
			<input type="button" title="新增" class="btn_add_s" />	
			<input type="button" title="服务配置" class="btn_taocan_s" />	
			<input type="button" title="属性信息" class="btn_shuxing_s" />	
			<input type="button" title="操作类型" class="btn_caozuoleixing_s" />	
			<input type="button" title="启用" class="btn_start_s" />	
			<input type="button" title="暂停" class="btn_pause_s" />	
			<input type="button" title="停用" class="btn_stop_s" />	
			<input type="button" title="申请进度" class="btn_apply_s" />	
			<input type="button" title="处理" class="btn_config_s" />		
		</span-->
		 <!-- 带加减号的输入控件 jQuery.add('#amount',10); 第一个参数为文本框ID，第二个参数为最大值
		<span  id="q"  class="pageFormContent" style="display:'black'">	
  	      <p>
            <i>数量：</i>
            <span id="uc-number" class="uc-number">
             <a  href="javascript:jQuery.reduce('#amount');" target="_self" rel="nofollow" class="uc-reduce" hidefocus="">
             <small class="icon-reduce">-</small>
             </a><input id="amount" class="ar-input" name="" value="1" maxlength="4" onblur="jQuery.modify('#amount',10);">
             <a href="javascript:jQuery.add('#amount',10);" target="_self" rel="nofollow" class="uc-add" hidefocus="">
             <small class="icon-add">+</small></a>
            </span>
          </p>	
  	   </span>--> 
  	     
  	</div>
  	
  	<div id="dc_div" style="display: none">  		
  		<table border="0" cellpadding="0" cellspacing="0" class="report" width="97%">
			<tr>
			<th><icms-i18n:label name="res_l_comput_dcName"/>：</th><td width="30%"><label id="lab_dcname" style="font-size: 14px;"></label></td>
			<th><icms-i18n:label name="res_l_network_ename"/>：</th><td><label id="lab_dcEname" style="font-size: 14px;"></label></td>
			</tr>
			<tr>
			<th><icms-i18n:label name="res_l_comput_dcAddress"/>：</th><td><label id="lab_address" style="font-size: 14px;"></label></td>
			
			<th><icms-i18n:label name="res_l_comput_isActive"/>：</th><td><label id="lab_isActive" style="font-size: 14px;"></label></td>
			</tr>
			</table>
  		<p class="btnbar1" style="padding-left:28px;">
  			<shiro:hasPermission name="storage:saveResPool">
	  			<input type="button" value='<icms-i18n:label name="storage_btn_addPool"/>'  class="btn btn_dd2" onclick="newStoragePool()" style="margin-right: 5px;" />
	  		</shiro:hasPermission>
  		</p>
  	</div>
  
  	<div id="sp_div" style="display: none;">
	  		<table id="tab" border="0"  class="report" width="97%">
				<tr>
				<th><icms-i18n:label name="bm_l_belongDc"/>：</th><td width="30%"><label id="lab_dc"></label></td>
				<th><icms-i18n:label name="res_l_comput_poolName"/>：</th><td><label id="lab_name"></label></td>
				</tr>
				<tr>
				<th><icms-i18n:label name="res_l_network_ename"/>：</th><td><label id="lab_ename"></label></td>
				<th><icms-i18n:label name="res_l_storage_pooltype"/>：</th><td><label id="lab_pooltype"></label></td>
				</tr>
				<tr>
				<th><icms-i18n:label name="res_l_storage_serlev"/>：</th><td><label id="lab_serlev"></label></td>
				<th><icms-i18n:label name="res_l_comput_remark"/>：</th><td><label id="lab_remark" /></td>
				</tr>	
	  		</table>
	  		<p class="btnbar1" style="padding-left:27px;">
	  			<shiro:hasPermission name="storage:saveResChildPool">
	  				<input type="button" value='<icms-i18n:label name="storage_btn_addChildPool"/>' class="btn btn_dd2" onclick="newStorageChildPool()" style="margin-right: 12px; " />
	  			</shiro:hasPermission>
	  			<shiro:hasPermission name="storage:updateResPool">
	  				<input type="button" class="btn btn_dd2"  value='<icms-i18n:label name="storage_btn_updatePool"/>' onclick="modStoragePool()" style="margin-right: 12px; margin-left:0;" />
	  			</shiro:hasPermission>
	  			<shiro:hasPermission name="storage:deleteResPool">
	  				<input type="button" class="btn btn_dd2"  value='<icms-i18n:label name="storage_btn_delPool"/>' onclick="delStoragePool()" style="margin-right: 12px; margin-left:0;" />
	  			</shiro:hasPermission>
	  		</p>
  	</div>
  	
  	<div id="scp_div" style="display: none">
	  		<table border="0"  class="report" width="97%">
	  			<tr><td><input type="hidden" id = "hid_scpapptype" name="hid_scpapptype"/>
	  					<input type="hidden" id = "childPoolId" name="childPoolId"/></td></tr>
				<tr>
				<th><icms-i18n:label name="res_l_device_pool"/>：</th><td width="30%"><label id="lab_sp"></label></td>
				<th><icms-i18n:label name="res_l_storage_scpname"/>：</th><td><label id="lab_scpname"></label></td>
				</tr>
				<tr>
				<th><icms-i18n:label name="res_l_device_model"/>：</th><td><label id="lab_scpdevtype"></label></td>
				<th><icms-i18n:label name="res_l_storage_scpapptype"/>：</th><td><label id="lab_scpapptype"></label></td>
				</tr>
				<tr>
				<th><icms-i18n:label name="res_l_comput_remark"/>：</th><td><label id="lab_scpremark" /></td>
				</tr>	
	  		</table>
	  			<p class="btnbar1" style="padding-left:27px;">
	  				<shiro:hasPermission name="storage:refDevice">
	  					<input type="button" class="btn btn_dd2" id="btn_refDev" value='<icms-i18n:label name="storage_btn_refDev"/>' onclick="refStorageDevice()" style="margin-right: 5px;"/>
	  				</shiro:hasPermission>
	  				<shiro:hasPermission name="storage:updateResChildPool">
	  					<input  class="btn btn_dd2"  type="button" value='<icms-i18n:label name="storage_btn_updateChPool"/>' onclick="modStorageChildPool()" style="margin-right: 5px; margin-left:0;" />
	  				</shiro:hasPermission>
	  				<shiro:hasPermission name="storage:deleteResChildPool">	
	  					<input type="button"  class="btn btn_dd2" value='<icms-i18n:label name="storage_btn_delChPool"/>' onclick="delStorageChildPool()" style="margin-right: 5px; margin-left:0;" />
	  				</shiro:hasPermission>
	  			</p>
			<div id="showRefDevice" style="display: none">
				<div id="showRefDeviceButtonDiv" class="panel clear" style="margin-left: 1px;margin-top: 10px;">
				<table id="gridTableRef"></table>
				<div id="gridPagerRef"></div>
				</div>
				<p id="showRefDeviceButtonDiv" class="btnbar btnbar1" style="text-align:right; margin-right:23px">
				<input type="button"  class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="commom_btn_cancel"/>' style="margin-left:0;" onclick="cancelRefStorageDevice()"/>
				<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="common_btn_save"/>' onclick="saveRefStorageDevice()" style="margin-right: 5px; margin-left:0;"/>
				</p>
			</div>
		<div id="showStorageDeviceList" style="display: none;margin-top: 20px;margin-left: 25px;">
			<div id="showStorageDeviceListDiv" class="panel clear" style="margin-left: 14px;margin-top: px;">
			<table id="gridTableDl"></table>
			<div id="gridPagerDl"></div>
			</div>
		</div>
  	</div>
  	
  	
  
  	
  	
  	
  	<div id="dev_div" style="display: none" class="short_btn">
  		<table border="0"  class="report" width="97%">
			<tr>
			<th ><icms-i18n:label name="res_l_device_name"/>：</th><td width="30%"><label id="lab_sdname"></label></td><th ><icms-i18n:label name="res_l_storage_deviceSn"/>：</th><td><label id="lab_sdsn"></label></td>
			</tr>		
			<tr>
			<th ><icms-i18n:label name="res_l_device_type"/>：</th><td><label id="lab_sddevtype"></label></td><th ><icms-i18n:label name="res_l_storage_manufacturer"/>：</th><td><label id="lab_sdcs"></label></td>
			</tr>	
			<tr>
			<th ><icms-i18n:label name="res_l_device_model"/>：</th><td><label id="lab_sdxh" /></td><th ><icms-i18n:label name="res_l_storage_sdwm"/>：</th><td><label id="lab_sdwm" /></td>
			</tr>	
			<tr>
			<th ><icms-i18n:label name="res_l_device_mgrIp"/>：</th><td><label id="lab_sdip" /></td><th ><icms-i18n:label name="res_l_device_cacheCapacity"/>：</th><td><label id="lab_sdhc" /></td>
			</tr>				
			<tr>
			<th ><icms-i18n:label name="res_l_device_diskCapacity"/>：</th><td><label id="lab_sdcprl" /></td><th ><icms-i18n:label name="res_l_storage_cpxh"/>：</th><td><label id="lab_cpxh" /></td>
			</tr>			
			<tr>
			<th ><icms-i18n:label name="res_l_storage_sdrpm"/>：</th><td><label id="lab_sdrpm" /></td><th ><icms-i18n:label name="res_l_device_portCount"/>：</th><td><label id="lab_sddksl" /></td>
			</tr>		
			<tr>
			<th ><icms-i18n:label name="res_l_device_portSpeed"/>：</th><td><label id="lab_sddksd" /></td><th ><icms-i18n:label name="res_l_storage_isActive"/>：</th><td><label id="lab_sdsfky" /></td>
			</tr>
  		</table>
  		<p class="btnbar1" style="margin-left:27px; padding-right:19px;">
  			<%--  <shiro:hasPermission name="storage:deleteResChildPool">
  				<input type="button" class="btn btn_dd2" id="btn_showDataStore" value="查看DataStore" onclick="showDataStore()" style="margin-left:0; margin-right: 5px;" />
  			</shiro:hasPermission>  --%>
  			<shiro:hasPermission name="storage:deleteResChildPool">
  				<input type="button" value='<icms-i18n:label name="storage_btn_unRefDev"/>' onclick="unRefStorageDevice()" class="btn btn_dd2"  style="margin-left:0; margin-right: 5px;" />
  			</shiro:hasPermission>		
  		</p>
		 
		<div id="showDataStoreList" style="display: none;margin-top: 13px;margin-left: 25px;width: 700px;">
			<div id="showDataStoreListDiv" class="panel clear" style="margin-left: 14px;margin-top: px;">
			<table id="gridTable"></table>
			<div id="gridPager"></div>
			</div>
		</div>
  	</div>
  	
  	<div id="add_sp_div" style="display:none;background-color:white; padding-top:18px;"  class="pageFormContent">
  		<form id="sp_form" action="" method="post">
				<p>
				<span class="updateDiv_list">
				<i><icms-i18n:label name="bm_l_belongDc"/>：</i><select id="sel_dc"   class="selInput"></select><input type="hidden" id="hid_dc" name="sp.datacenterId" />
				</span>
				<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_comput_poolName"/>：</i><input type="text" id="txt_name"  name="sp.poolName" class="textInput" /><input type="hidden" id="hid_poolName" />
				</span>
				</p>
				<p>
				<span class="updateDiv_list">
				<i><icms-i18n:label name="res_l_network_ename"/>：</i><input type="text" id="txt_ename" name="sp.ename"   class="textInput" />
				</span>
				</p>
				<p>
				<span class="updateDiv_list" style="width: 100%;">
				<i style="width: 110px;"><icms-i18n:label name="res_l_storage_pooltype"/>：</i><icms-ui:dic id="sp.poolType" name="sp.poolType" kind="STORAGE_TYPE" attr="style='width:85px;'" showType="flatSelect" />
				</span>
				</p>
				<p>
				<span class="updateDiv_list" style="width: 100%;">
				<i style="width:113px;"><icms-i18n:label name="res_l_storage_serlev"/>：</i><icms-ui:dic id="sp.serviceLevelCode" name="sp.serviceLevelCode" kind="SERVICE_LEVEL_CODE" attr="style='width:87px;'" showType="flatSelect" />
				</span>
				</p>
				<p>
				<span class="updateDiv_list">
				<i><icms-i18n:label name="res_l_comput_remark"/>：</i><textarea id="texta_remark" name="sp.remark" style="width:165px; height:40px; border: 1px solid #d5d5d5; box-shadow: 1px 1px 2px #ededed; background-color: #f5f5f5;"></textarea>
				</span>
				</p>
				<input type="hidden" name="sp.storageResPoolCode" value="NAS" />
				<input type="hidden" name="sp.status" value="1" />
				<input type="hidden" name="sp.id" id="hid_sp_id" />		
	  		<p class="winbtnbar btnbar1"><label id="sp_error_tip"></label>
		  		<input type="button" id="btnAddSp"    class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="commom_btn_cancel"/>' value='<icms-i18n:label name="commom_btn_cancel"/>' onclick="closeView('add_sp_div')" style="margin-right: 5px; margin-left:0;" />
		  		<input type="button" id="btn_mod_sp" class="btn btn_dd2 btn_dd3" onclick="saveOrModifyStoragePool('modify')" title='<icms-i18n:label name="common_btn_update"/>' value='<icms-i18n:label name="common_btn_update"/>' style="margin-right: 5px;margin-left: 5px; margin-left:0;" />
		  		<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="saveOrModifyStoragePool('save')" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' style="margin-right: 5px;margin-left: 5px; margin-left:0;" /> 
	  		</p>
  		</form>
  	</div>
  	
  	<div id="add_scp_div" style="display:none;background-color:white;" class="pageFormContent">
  		<form id="scp_form" action="" method="post">
				<p>
				<span class="updateDiv_list">
				<i><icms-i18n:label name="res_l_device_pool"/>：</i><select id="sel_sp" style="width:140px;" class="selInput"></select><input type="hidden" id="hid_sp" name="scp.resPoolId" />
				</span>
				<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_storage_scpname"/>：</i><input  class="textInput" type="text" id="txt_scpname" name="scp.name" style="width:140px;" /><input type="hidden" id="hid_childPoolName" />
				</span>
				</p>
				<p>
				<i><font color="red">*</font><icms-i18n:label name="res_l_device_manufacturer"/>：</i><icms-ui:dic id="sel_manufacturer" name="manuf" attr="style='width:80px;'" showType="flatSelect" sql="SELECT DISTINCT '1' as id,C.DEVICE_MANUFACTURER as value,C.DEVICE_MANUFACTURER as name,'1' as kind FROM CM_DEVICE_MODEL C WHERE 1 = 1 AND C.DEVICE_TYPE = 'STORAGE' AND C.IS_ACTIVE = 'Y' ORDER BY C.CREATE_DATETIME ASC" click="selectDevModelByManuf(this.getAttribute('value'));" />
				</p>
				<p>
				<i><icms-i18n:label name="res_l_device_model"/>：</i><span id="span_device_model" class="uc-radio"></span><input type="hidden" id="hid_storageDevModel" name="scp.storageDevModel" value=""/>
				</p>
				<p>
				<i><icms-i18n:label name="res_l_storage_scpapptype"/>：</i><icms-ui:dic id="scp.storageAppTypeCode" showType="flatSelect" name="scp.storageAppTypeCode" attr="style='width:80px;'" kind="STORAGE_DATA_TYPE" />
				</p>
				<p>
				<i><icms-i18n:label name="res_l_comput_remark"/>：</i><textarea id="texta_scpremark" class="textInput" name="scp.remark"  style="width:436px; height:50px; "></textarea>
				</p>				
	  		<p class="winbtnbar btnbar1" > 
	  		<input type="button"  class="btn btn_dd2 btn_dd3"  id="btnAddSp" title='<icms-i18n:label name="commom_btn_cancel"/>' value='<icms-i18n:label name="commom_btn_cancel"/>' onclick="closeView('add_scp_div')" style="margin-right: 0px; margin-left:5px; float:right;" />
	  		<input type="button" class="btn btn_dd2 btn_dd3" id="btn_add_scp" onclick="saveOrUpdateStorageChildPool('save')" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' style="margin-right: 0px;margin-left: 5px; float:right;" />
	  		<input type="button"  class="btn btn_dd2 btn_dd3"  id="btn_mod_scp" onclick="saveOrUpdateStorageChildPool('modify')" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' style="margin-right: 0px;margin-left: 5px; float:right;" />
	  		</p>
	  		<input type="hidden" name="scp.storageChildResPoolId" id="hid_scp_id" />		
  		</form>
  	</div>

 </div>
</body>
</html>