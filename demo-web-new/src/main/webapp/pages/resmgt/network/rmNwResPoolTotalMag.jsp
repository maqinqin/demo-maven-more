<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<title>网络资源池管理</title>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> -->
<script type="text/javascript" src="${ctx}/plugins/jquery.form.js"></script>
<!-- <script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/resPoolMag.js"></script>  -->
<script type='text/javascript' src='${ctx}/pages/resmgt/network/js/rmNwResPoolTotalMag.js'></script>
<script type="text/javascript" src="${ctx}/common/echart/echarts.js"></script>
<script type="text/javascript" src="${ctx}/common/javascript/honeySwitch3.js"></script>
<link href="${ctx}/css/honeySwitch.css" rel="stylesheet" type='text/css'/>

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
<style type="text/css">
html,body{height:100%}
._selInput {
	border: 1px solid #9a9a9a;
	height: 30px;
	line-height: 30px;
	color:#858585;
	background-color: #fff;
	border: 1px solid #000000;
	padding: 3px 4px 6px;
	font-size: 14px;
	width: 210px;
}
._textInput {
	width: 200px;
	border: 1px solid #9a9a9a;
	height: 17px;
	line-height: 17px;
	color:#858585;
	background-color: #fff;
	border: 1px solid #d5d5d5;
	padding: 5px 4px 6px;
	font-size: 14px;
}
.gridBtn {
	margin-left: 10px;
	background-color: #3EAAE8;
	border-color: #3EAAE8;
	margin-right: 20px;
	color:#FFF;
	display: inline-block;
	border: 2px solid #3EAAE8;
	border-radius:2px;
	box-shadow: none;
	cursor: pointer;
	vertical-align: middle;
	position: relative;
	white-space: nowrap;
	padding: 1px 3px;
	font-size: 12px;
	font-weight:400;
	margin-right:6px;
	margin-bottom:2px;
}
#cdpUpdateDiv p{margin-bottom:0; width:350px;}
#cdpUpdateDiv i{width:70px;}
#cdpUpdateForm{text-align:center}
#updategridDiv p{margin-bottom:0; padding-left:20px; width:308px;}
#updategridDiv i{width:70px;}

#poolUpdateDiv {padding:28px 0 0 10px;}
#poolUpdateDiv p{width:624px; margin-bottom:0;height:25px}
#poolUpdateDiv p .updateDiv_list{overflow:hidden; display:inline-block; width:310px; float:left;}
#poolUpdateDiv i{width:80px;}
#poolUpdateDiv select{width:165px; height:24px; line-height:24px; border:1px solid #d5d5d5; background:#f5f5f5; box-shadow:1px 1px 2px #ededed; padding:0; }
#poolUpdateDiv label{display:block; padding-left:100px;}
#addCclassDiv p{width:300px; margin-bottom:0; padding-left:20px;}
label{display:block; padding-left:30px;}
#pl_div label{padding-left:10px;}
#holdCclassForm label{padding-left:0px;}


#showIpStatusDiv .btn{
	float: left;
	height: 24px;
	font-weight: normal;
	padding: 0 24px;
}

#showIpStatusDiv .btn:hover{
	float: left;
	height: 24px;
	font-weight: normal;
	padding: 0 24px;
}
.sumTable2 td{
	padding-left:24px;
}
#tabcdp tr th,#tab_cinf tr th{
width:50px;
}
#holdCclassForm label{
	position:absolute;
	font-size:11px;
}
#bclass_Name-error,#bclass_scope-error{
padding-right: 40px;}
.gaugeDetail{position: absolute;left:-80px;top:52px;}
</style>
</head>
<body  class="main1">
<input type="hidden" id="hid_icon" /> 
<!-- rm_nw_bclass 中创建B段ip时存储的数据中心id -->
<input type="hidden" id="datacenterId" name="datacenterId" />
<!-- 点击新建C段地址时，从数据库中查出来的B段Ip对应的数据中心id -->
<input type="hidden" id="c_datacenterId" name="datacenterId" />
<!-- div_tip  作警告提示用 -->
<div id="div_tip"></div>
   
   <div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="res_title_network_resPool"/></div>
				<div class="WorkSmallBtBg">
					<small>
					<icms-i18n:label name="res_desc_network_resPool"/>
					</small>
				</div>
					
			</h1>
		</div>
  <div id="treeDiv" class="tree-div tree-div1">
  <a href="javascript:" class="tree-bar"><i class="fa fa-caret-left"></i></a>
	 <div class="pageFormContent tree-search">
  		<p style="margin: 0px;">
  			<select id="sel_type" class="selInput" style=" float: left;width: 100px">
	  	  		<option value="dc"><icms-i18n:label name="bm_l_data_center"/></option>
	  	  		<option value="bClass"><icms-i18n:label name="res_l_network_b"/></option>
	  	  		<option value="cClass"><icms-i18n:label name="res_l_network_c"/></option>
	  	  		<option value="pool"><icms-i18n:label name="info_vm_res_pool"/></option>
	  	  	</select>
  	  	<input type="text" id="name" size="30" class="textInput"  name="name" style="width: 43%;float: left;" />
  	  	<span class="input-group-btn"><a href="#" class="btn-default"><i class="fa fa-search" style="line-height:15px;width:auto;" onclick="searchTreeByCname()"></i></a></span>
		<!-- <input type="button" class="btn_search_s" id="search" style="margin-top: 3px;float: left; margin-left:5px;" name="search" title="查询" onclick="searchTreeByCname()"/> -->
	  </p>
	  </div>
	  <ul id="treeRm" class="ztree" style="padding:0 0 0 5px;overflow-y:auto;height:90%;"></ul>
  </div>
  
  <div id="rightContentDiv" class="right-div right-div1" style="width:74%">
    <div id="xd_div" style="display:none;font-size:18px;">
  		<table border="0" cellpadding="0" cellspacing="0" class="pagelist" style="float:left;width:40%" >
			<tr>
			<th><img src="../../../css/zTreeStyle/img/icons/datacenter.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="bm_l_data_center"/><icms-i18n:label name="res_l_comput_image"/></label></td>
			</tr>
			<tr>
			<th><img src="../../../css/zTreeStyle/img/icons/b_address.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="res_l_network_b"/><icms-i18n:label name="res_l_comput_image"/></label></td>
			</tr>
			<tr>
			<th><img src="../../../css/zTreeStyle/img/icons/c_address.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="res_l_network_c"/><icms-i18n:label name="res_l_comput_image"/></label></td>
			</tr>	
			<tr>
			<th><img src="../../../css/zTreeStyle/img/icons/respoolnet.png" /></th><td><label style="font-size: 14px; padding-left:0;"><icms-i18n:label name="res_l_network_resPool"/><icms-i18n:label name="res_l_comput_image"/></label></td>
			</tr>
						
			</table>
  	</div>
  	  	<div id="res_pro_div" class="sumCon_top">
  	  	<input type = "hidden" id="usableipNum">
  	  	<input type = "hidden" id="ipNum">
  	  	<input type = "hidden" id="usedipNum">
      	<p class="tip2"><icms-i18n:label name="res_l_comput_survey"/></p><span class="tip2b"></span>
          <table class="sumTable2" style="width:97%;">
          	<tr>
              	<th><icms-i18n:label name="res_l_comput_selected"/>:</th>
      			<td id="selection_current" style="color:#646964"></td>
                <th><icms-i18n:label name="res_l_comput_rmResPoolCount"/>:</th>
      			<td><input id="subPoolCount" disabled="true " style="width : 60px ;border-style:none;background-color:transparent"/></td>
                <th><icms-i18n:label name="res_l_network_usableipCount"/>:</th>
      			<td><input id="usableipCount" disabled="true " style="width : 60px ;border-style:none;background-color:transparent"/></td>
      			<th><icms-i18n:label name="res_l_network_usedipCount"/>:</th>
      			<td><input id="usedipCount" disabled="true " style="width : 60px ;border-style:none;background-color:transparent"/></td> 
      			<th><icms-i18n:label name="res_l_network_ipCount"/>:</th>
      			<td><input id="ipCount" disabled="true " style="width : 60px ;border-style:none;background-color:transparent"/></td>
            </tr>
          </table>
         <div id="echart_Network_div" style="width: 244px;height: 141px; margin-left : 80px;position:relative;margin-top: 15px;
padding: 10px;"></div>
       </div>
  	<div id="dc_div" style="display: none;padding-top:0px;">
  		<p class="tip2"><icms-i18n:label name="res_l_comput_basic_info"/></p><span class="tip2b"></span>
  		<table id="tabdc" border="0" cellpadding="0" cellspacing="0" class="report" width="97%">
			<tr>
			<th><icms-i18n:label name="res_l_comput_dcName"/>：</th><td width="30%"><label id="lab_dcname" style="font-size: 14px;"></label>
			<input type="hidden" id="dcId" name="dcId"  ></input></td>
			<th><icms-i18n:label name="res_l_network_ename"/>：</th><td><label id="lab_dcEname" style="font-size: 14px;"></label></td>
			</tr>
			<tr>
			<th><icms-i18n:label name="res_l_comput_dcAddress"/>：</th><td><label id="lab_address" style="font-size: 14px;"></label></td>
			<th><icms-i18n:label name="res_l_comput_isActive"/>：</th><td><label id="lab_isActive" style="font-size: 14px;"></label></td>
			</tr>
		</table>
        <p class="btnbar1" style="margin-left:15px;">
        <shiro:hasPermission name="network:bclass_add">
					<input type="button" id="bclass_add" value='<icms-i18n:label name="networkRes_btn_addB"/>'  style="margin-right: 5px;" class="btn btn_dd2" />
					</shiro:hasPermission>
        </p>
  	</div>

	<div id="pl_div" style="display: none; background:#e4eaec; padding:0px 0;">
		
			<table id="tabpl" border="0" class="report" width="100%">
			<tr>
				<th><icms-i18n:label name="res_l_network_c"/>:</th><td  width="20%"><label id="ss_cclass"></label></td>
				<th><icms-i18n:label name="res_l_network_gateway"/>:</th><td><label id="ss_gateway"></label></td>
				<th><icms-i18n:label name="res_l_network_subnetMask"/>:</th><td><label id="ss_subnetmask" ></label></td>
			</tr>		
			<tr>
				<th><icms-i18n:label name="res_l_network_resPoolName"/>:</th><td  width="20%"><label id="s_poolName"></label></td>
				<th><icms-i18n:label name="res_l_network_ename"/>:</th><td><label id="s_ename"></label></td>
				<th><icms-i18n:label name="res_l_comput_remark"/>:</th><td><label id="s_remark" ></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_network_resPoolType"/>:</th>
				<td width="20%"><label id="s_nwPoolType"></label></td>
				<th><icms-i18n:label name="res_l_network_ipStart"/>:</th>
				<td><label id="s_ipStart"></label><input type="hidden" id="st" name="st"/></td>
				<th><icms-i18n:label name="res_l_network_ipEnd"/>:</th>
				<td><label id="s_ipEnd"></label><input type="hidden" id="en" name="en"/></td>
			</tr>
				<tr>
				<th><icms-i18n:label name="bm_l_platform_type"/>:</th>
				<td width="20%"><label id="s_platformName"></label></td>
				<th><icms-i18n:label name="res_l_network_virtualTypeName"/>:</th>
				<td><label id="s_virtualTypeName"></label></td>
				<th><icms-i18n:label name="res_l_network_hostType"/>:</th>
				<td><label id="s_hostTypeName"></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_network_purpose"/>:</th>
				<td width="20%"><label id="s_useName"></label></td>
				<th><icms-i18n:label name="res_l_comput_secureArea"/>:</th>
				<td><label id="s_secureAreaName" style="padding-left:10px"></label></td>
				<th><icms-i18n:label name="res_l_network_secureTierName"/>:</th>
				<td><label id="s_secureTierName"></label></td>
			</tr> 
			 <tr>
			    <th><icms-i18n:label name="res_l_comput_networkConvergence"/>:</th>
				<td width="20%"><label id="s_convergeName"></label></td>
				<th><icms-i18n:label name="res_l_network_usedNum"/>:</th>
					<td><label id="s_usedNum"></label></td>
				<th><icms-i18n:label name="res_l_network_canUsedNum"/>:</th>
					<td width="20%"><label id="s_canUsedNum"></label></td> 

				<!-- <th>是否启用：</th><td id='cclassStatus'></td> -->
			</tr>
			<tr id="opControl" >
			    <th><icms-i18n:label name="res_l_network_VmSm"/>:</th>
				<td width="20%"><label id="s_VmSmId"></label></td>
				<th><icms-i18n:label name="res_l_network_vpcName"/>:</th>
				<td><label id="s_vpcId"></label></td> 
				<th><icms-i18n:label name="res_l_network_virtualRouter"/>:</th>
				<td><label id="s_virtualRouterId"></label></td>
			</tr>
			<tr id="opControl1">
				<th><icms-i18n:label name="res_l_network_networkType"/>:</th>
				<td><label id="s_networkType"></label></td>
				<th>VLANID:</th>
				<td><label id="s_vlanId1"></label></td>
				<th><icms-i18n:label name="res_l_network_isEnable"/>:</th>
				<td><label id="s_isEnable1"></label></td>
			</tr>
			<tr id="opControl2">
				<th>VLANID:</th>
				<td><label id="s_vlanId2"></label></td>
				<th><icms-i18n:label name="res_l_network_isEnable"/>:</th>
				<td><label id="s_isEnable2"></label></td>
			</tr>
	  		</table>			
			<p class="btnbar1" style="padding-top:10px; padding-left:28px;" >
			<span style="float:left;"><input id="cancalRouter" class="btn" type="button"  value='<icms-i18n:label name="networkRes_btn_cancalRouter"/>' style="display:none; height:24px; font-weight:normal; margin-right:12px; margin-left:0;"></input></span> 
			<span style="float:left;"><input id="addRouter" class="btn" type="button"  value='<icms-i18n:label name="networkRes_btn_addRouter"/>' style="display: none; height:24px;font-weight:normal; margin-right:12px; margin-left:0;"></input></span>
			<shiro:hasPermission name="network:occupyBySeq">
				<input type="button" value='<icms-i18n:label name="networkRes_btn_occupy"/>' onclick="showOccupyBySeq();" id="holdCclass" class="btn" style="margin-right:12px; margin-left:0; height:24px; font-weight:normal;"></input>						
			</shiro:hasPermission>
			<shiro:hasPermission name="network:occupyBySeq">
				<input type="button" value='<icms-i18n:label name="networkRes_btn_ipupdate"/>' onclick="updateOccupyBySeq();" id="ipupdate" class="btn" style="margin-right:12px; margin-left:0; height:24px;font-weight:normal;"></input>						
			</shiro:hasPermission>
			<shiro:hasPermission name="network:occupyBySeq">
				<input type="button" value='<icms-i18n:label name="networkRes_btn_gate"/>' onclick="gatewayEdit();" id="gatewayEdit" class="btn" style="margin-right:12px; margin-left:0; height:24px;font-weight:normal;"></input>						
			</shiro:hasPermission>
			<shiro:hasPermission name="network:occupyBySeq">
				<input type="button" value='<icms-i18n:label name="networkRes_btn_ipdelete"/>' onclick="deleteOccupyBySeq();" id="ipdelete" class="btn" style="height:24px; font-weight:normal; margin-right:12px; margin-left:0;"></input>						
			</shiro:hasPermission>
		</p>
	
			<p class="btnbar1" style="text-align:right;">
				<shiro:hasPermission name="network:pool_modify">
					<input type="button" id="pool_modify" value='<icms-i18n:label name="networkRes_btn_updatePool"/>'
						style="margin-right: 5px; margin-left:0;" class="btn btn_dd2" />
				</shiro:hasPermission>
				<shiro:hasPermission name="network:c_valid">
					<input type="button" value='<icms-i18n:label name="networkRes_btn_enable"/>' onclick="saveCclassValid('Y');"
						id="c_valid" class="btn btn_dd2" style="margin-left:0; margin-right:5px;"></input>
				</shiro:hasPermission>
			 	<shiro:hasPermission name="network:pool_del">
		<input type="button" id="pool_del1" class="btn btn_dd2"  value='<icms-i18n:label name="networkRes_btn_delPool"/>' style="margin-left:0; margin-right:5px;"/>
   </shiro:hasPermission>
			</p>  		
  	</div>
  	<!-- wmy，新建资源池 -->
  		<div id="poolUpdateDiv" style="display:none; padding-left:30px; padding-top:18px;"  class="pageFormContent" >
		<form action="" method="post" id="poolUpdateForm">
			<input type="hidden" id="poolMethod" name="poolMethod" />
			<input type="hidden" id="pl_datacenter_id" name="pl_datacenter_id" />
			<input type="hidden" id="pl_checkName" name="pl_checkName" />
			<input type="hidden" id="pl_checkEname" name="pl_checkEname" />
			<input type="hidden" id="u_cclassId" name="u_cclassId" /><!--ip资源池表中C段id  -->
			<p>
				<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_comput_poolName"/>:</i>
				<input type="text" id="pl_pool_name" name="pl_pool_name"  class="textInput" />
			    </span>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="res_l_network_ename"/>:</i>
					<input type="text" id="pl_ename" name="pl_ename"  class="textInput"/>
				</span>
			</p>
			<p>
				<span class="updateDiv_list">
				<i><icms-i18n:label name="res_l_network_gateway"/>:</i>
				<input type="text" id="u_gateway" name="u_gateway"  class="textInput"/>
			</span>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="res_l_network_IPMaskBit"/>:</i>
				<input type="text" id="c_cclassName2" name="c_cclassName2" style='border:0px;align:center;width:70px;' readonly="readonly"/>
				<input type="text" id="c_end" name="c_end" class="textInput" onblur="getIPInfo()" style='width:20px;align:center;' />/
				<input type="text" id="c_mask" name="c_mask" class="textInput" onblur="getIPInfo()" style='width:20px;align:center;' />
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="res_l_network_c"/>:</i>
				<!-- <label id="u_cclassName"></label> -->
				<input type="text" id="u_cclassName" name="u_cclassName" style='border:0px;align:center;width:100px;' readonly="readonly"/>
			</span>
			<span class="updateDiv_list">
			    <i><icms-i18n:label name="res_l_network_subnetMask"/>:</i>
			    <input type="text" id="u_subnetmask" name="u_subnetmask"  class="textInput"/>
			    </span>
			<span class="updateDiv_list" style="display: none;">
				<i><font color="red">*</font><icms-i18n:label name="res_l_comput_hostType"/>:</i>
				<input type="hidden" id="pl_nwPoolType" name="pl_nwPoolType" value="IPPL"  class="textInput"/>
					<%-- <icms-ui:dic id="pl_nwPoolType" name="pl_nwPoolType" showType="select" attr="class='selInput'"
 					sql="SELECT DIC_CODE AS value, DIC_NAME AS name FROM ADMIN_DIC WHERE DIC_TYPE_CODE = 'NW_RES_POOL_TYPE'" />	 --%>			    
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_network_ipStart"/>:</i>
				<input type="hidden" id="inputIp" name="inputIp"  ></input>
				<input type="hidden" id="ips" name="ips"  ></input>
				<input type="text" id="c_ipStart" name="c_ipStart"  class="textInput"></input>
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_network_ipEnd"/>:</i>
				<input type="text" id="c_ipEnd" name="c_ipEnd"  class="textInput"></input>
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bm_l_platform_type"/>:</i>
				<select id="u_platformId" name="u_platformId" class="_selInput" onchange="changeVirtualTypeByPF();" ></select>
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_network_virtualTypeName"/>:</i>
				<select id="u_virtualTypeId" name="u_virtualTypeId"  class="_selInput" onchange="changeUseByVirtual();"></select>
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_network_hostType"/>:</i>
				<select id="u_hostTypeId" name="u_hostTypeId"  class="_selInput" onchange="changeUseByHost();"></select>
			</span>
			<span class="updateDiv_list">								
				<i><font color="red">*</font><icms-i18n:label name="res_l_network_purpose"/>:</i>
				<select id="u_useId" name="u_useId"  class="_selInput" ></select>
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_comput_secureArea"/> :</i>
				<select id="u_secureAreaId" name="u_secureAreaId"  class="_selInput" onchange="changeSecureTierBySA();"></select>
			</span>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="res_l_network_secureTierName"/>:</i>
				<select id="u_secureTierId" name="u_secureTierId"  class="_selInput" ></select>
			</span>
			</p>
			<p id="Openstack_message4">
			<span class="updateDiv_list">
			    <i><font color="red">*</font><icms-i18n:label name="res_l_comput_networkConvergence"/>:</i>	
				<select id="u_convergeId" name="u_convergeId"  class="_selInput"></select>
			</span>
			<span class="updateDiv_list">	
				<i><!-- <font color="red">*</font> -->VLANID:</i>
				<input type="text" id="u_vlanId" name="u_vlanId" style="width:165px; height:22px; line-height:22px; border:1px solid #d5d5d5; background:#f5f5f5; box-shadow:1px 1px 2px #ededed; padding:0; " class="_textInput" ></input> 								
			</span>
			</p>
			<p id="Openstack_message3">
			<span class="updateDiv_list">
			    <i><font color="red">*</font><icms-i18n:label name="res_l_comput_networkConvergence"/>:</i>	
				<select id="u_convergeId1" name="u_convergeId1"  class="_selInput"></select>
			</span>
			<span class="updateDiv_list">
			    <i><font color="red">*</font><icms-i18n:label name="res_l_network_VmSm"/>:</i>	
				<icms-ui:dic id="c_VmMsId" name="c_VmMsId" attr="class='selInput'" showType="select" 
					sql="SELECT ID AS value, SERVER_NAME AS name FROM RM_VM_MANAGE_SERVER WHERE PLATFORM_TYPE = '4' OR PLATFORM_TYPE = '5'" />
			</span>
			</p>
			<p id="Openstack_message1">
			<span class="updateDiv_list">	
				<i><font color="red">*</font><icms-i18n:label name="res_l_network_vpcName"/>:</i>
				<select id="c_vpcId" name="c_vpcId"  class="_selInput"></select>		
			</span>
			<span  class="updateDiv_list" id="switchLable">	
				<i><font color="red">*</font>直连网络:</i>
				<%-- <icms-ui:dic id="isExternalNet" name="isExternalNet" kind="IS_EXTERNAL" showType="select"
						attr="style='width: 166px;'class='selInput'" /> --%>
					<input type="hidden" id="isExternalNet" name="isExternalNet" value="N"/>
			</span>
			</p>
			<p id="Openstack_message2">
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="res_l_network_networkType"/>：</i>
				   <icms-ui:dic id="networkType" kind="NETWORK_TYPE_CODE" showType="select"
						attr="style='width: 166px;'class='selInput'" />
			</span>	
			<span class="updateDiv_list">	
				<i><font color="red">*</font>vlanID/vxlanID:</i>
				<input type="text" id="u_vlanId1" name="u_vlanId1" style="width:165px; height:22px; line-height:22px; border:1px solid #d5d5d5; background:#f5f5f5; box-shadow:1px 1px 2px #ededed; padding:0; " class="_textInput" ></input> 								
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="res_l_comput_remark"/>:</i>
				<textarea  id="pl_remark" rows="2" align="left"  class="textInput" style="height:25px"></textarea>
			</span>
			<span id="virtualRouterSpan" class="updateDiv_list"  style="display: none">	
				<i><!-- <font color="red">*</font> --><icms-i18n:label name="res_l_network_virtualRouteName"/>:</i>
				<select id="c_virtualRouterId" name="c_virtualRouterId"  class="_selInput"></select>		
			</span>
			</p>
			<p>
				<span id="physicalNetworkSpan" class="updateDiv_list" style="display: none">	
					<i><font color="red">*</font><icms-i18n:label name="res_l_network_physicalNetwork"/>:</i>
					<select id="physicalNetwork" class="selInput" name="physicalNetwork"></select>
				</span>
			</p>

			<p class="btnbar btnbar1" style="text-align:right; margin-right:50px; width:576px;" >
					<label id="sp_error_tip"></label>
					<input type="button" onclick="closeNwPoolView()"  class="btn btn_dd2 btn_dd3" id="nwResPoolcancelBtn" value='<icms-i18n:label name="commom_btn_cancel"/>' style="margin-left:0; margin-right:24px;"/>
					<input type="button"  onclick="saveOrUpdateNwPoolBtn()"  class="btn btn_dd2 btn_dd3" id="nwResPoolSaveBtn" value='<icms-i18n:label name="common_btn_save"/>' style="margin-left:0; margin-right:5px;"/>
			</p>
		</form>
	</div>
	
	  <div id="cdp_div" style="display: none;">
	  <p class="tip2"><icms-i18n:label name="res_l_comput_basic_info"/></p><span class="tip2b"></span>
			<table id="tabcdp" border="0" class="report" width="97%">
			<tr>
				<th><icms-i18n:label name="res_l_network_bName"/>:</th>
				<td  width="30%"><label id="s_bclass_Name"></label></td>
				<th><icms-i18n:label name="res_l_network_subnetMask"/>:</th>
				<td><label id="s_bclass_subnetmask"></label></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_network_bIp"/>:</th>
				<td><label id="s_bclass_scope"></label></td>
			    <th><icms-i18n:label name="res_l_comput_remark"/>:</th>
			    <td><label id="s_bclass_remark"></label></td>
			</tr>
			</table>
            <p class="btnbar1" style="padding-left:29px;">
                    <shiro:hasPermission name="network:bclass_modify">
					<input type="button" id="bclass_modify" value='<icms-i18n:label name="networkRes_btn_updateB"/>' style="margin-right: 12px;" class="btn btn_dd2" />
					</shiro:hasPermission>
					<shiro:hasPermission name="network:cclass_add">
					<input type="button" id="add_Cclass" value='<icms-i18n:label name="networkRes_btn_addC"/>' style="margin-right: 12px; margin-left:0;" class="btn btn_dd2"  />
					</shiro:hasPermission>
			</p>				
  	</div>
  	<!--wmy  新增C段地址-->
  	<div id="addCclassDiv" style="display:none;" class="pageFormContent">
		<form action="" method="post" id="addCclassForm">
		    <input type="hidden" id="addCclassMethod" name="addCclassMethod" />
		    <input type="hidden" id="bclassId" name="bclassId" />			
			<p>
				<i><font color="red">*</font><icms-i18n:label name="res_l_network_c"/>:</i>
				<input type="text" id="c_cclassName1" name="c_cclassName1" style='border:0px;align:center;width:50px;' readonly="readonly"></input>
				<input type="text" id="c_cclassName" name="c_cclassName" class="textInput" onkeyup="addCgateway();" style='width:35px;align:center;' ></input>.0
				<input type="hidden" id="c_cclassNameHidden" name="c_cclassNameHidden" ></input>
			</p>
			<p>
				<i>&nbsp;&nbsp;<icms-i18n:label name="res_l_network_subnetMask"/>:</i>
				<input type="text" id="c_subnetmask" name="c_subnetmask"  class="textInput" ></input>
			</p>
			<p>
				<i><icms-i18n:label name="res_l_network_gateway"/>:</i>
				<input type="text" id="c_gateway" name="c_gateway"  class="textInput" ></input>
			</p>
			<p>
				<i><icms-i18n:label name="res_l_comput_remark"/>:</i>
				<textarea id="c_remark" class="textInput" align="left" style="height:40px;" name="c_remark" rows="3"></textarea>
			</p>
			
		<p class="btnbar btnbar1" style="text-align:right; padding-left:3px;"><label id="sp_error_tip1"></label>
           <input type="button" onclick="closeAddCclassView()"  class="btn btn_dd2 btn_dd3" id="CclassSaveCancelBtn" value='<icms-i18n:label name="commom_btn_cancel"/>' style="margin-left:0; margin-right:24px;"/>
		   <input type="button"  onclick="saveOrUpdateCclassBtn()"   class="btn btn_dd2 btn_dd3" id="CclassSaveBtn" value='<icms-i18n:label name="common_btn_save"/>' style="margin-left:0; margin-right:5px;"/>
       </p>
		</form>
	</div>
	<!--wmy，修改C段地址  -->
	<div id="updategridDiv" style="display:none;"  class="pageFormContent" >
	<form action="" method="post" id="updateCclassForm">
	<input type="hidden" id="uu_cclassId" name="uu_cclassId" ></input>
	
			<p>
				<i><icms-i18n:label name="res_l_network_c"/>:</i>
				<input type="text" id="uu_cclassName" name="uu_cclassName"  class="textInput" disabled="disabled"></input>
			</p>
			<p>
			    <i><icms-i18n:label name="res_l_network_subnetMask"/>:</i>
			    <input type="text" id="uu_subnetmask" name="uu_subnetmask"  class="textInput" ></input>
			</p>
			<p>
				<i><icms-i18n:label name="res_l_network_gateway"/>:</i>
				<input type="text" id="uu_gateway" name="uu_gateway"  class="textInput" ></input>
			<p>
				<i><icms-i18n:label name="res_l_comput_remark"/>:</i>
				<textarea id="uu_remark" class="textInput" align="left" style="height:40px;" name="uu_remark" rows="3"></textarea>
			</p>
			<p class="btnbar btnbar1" style="text-align:right; margin-right:40px; width:254px;"><label id="sp_error_tip" ></label>
				<input type="button"  onclick="closeCclassView()"  id="c_close" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="commom_btn_cancel"/>'  style="margin-left:0; margin-right:24px;"/>
				<input type="button"  onclick="updateCclassBtn()" id="c_updateCclassBtn"  class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="common_btn_save"/>' style="margin-left:0; margin-right:5px;" />
			</p>
		</form>
	</div>
  	
	<div id="cdpUpdateDiv" style="display:none;" class="pageFormContent">
		<form action="" method="post" id="cdpUpdateForm">
		    <input type="hidden" id="cdpMethod" name="cdpMethod" />		
			<input type="hidden" id="bclass_resPoolId" name="bclass_resPoolId" />
			<p>
				<i><font color="red">*</font><icms-i18n:label name="res_l_network_bName"/>:</i>
				<input type="text" id="bclass_Name" name="bclass_Name" class="textInput" ></input>
			</p>
			<p>
				<i><font color="red">*</font><icms-i18n:label name="res_l_network_bIp"/>:</i>
				<input type="text" id="bclass_scope" name="bclass_scope"  class="textInput" ></input>
			</p>
			<p>
				<i><font color="red">*</font><icms-i18n:label name="res_l_network_subnetMask"/>:</i>
				<input type="text" id="bclass_subnetmask" name="bclass_subnetmask"  class="textInput" ></input>
			</p>
			<p>
			    <i>&nbsp;&nbsp;<icms-i18n:label name="res_l_comput_remark"/>:</i>
			    <textarea  id="bclass_remark" rows="3" align="left" name="bclass_remark" style="height:40px;"  class="textInput" ></textarea>
			</p>
		<p class="btnbar btnbar1" style="width:255px; margin-left: 58px; text-align:right;"><label id="sp_error_tip"></label>
           <input type="button" onclick="closeBclassView()"  class="btn btn_dd2 btn_dd3" id="bclassSaveCancelBtn" value='<icms-i18n:label name="commom_btn_cancel"/>' style="margin-left:0; margin-right:24px;"/>
		   <input type="button"  onclick="saveOrUpdateCdpBtn()"   class="btn btn_dd2 btn_dd3" id="bclassSaveBtn" value='<icms-i18n:label name="common_btn_save"/>' style="margin-left:0; margin-right:10px;"/>
        </p>
		</form>
	</div>
	
	<div id="gridDiv" style="display: none;  padding-top:0px;">
	<p class="tip2"><icms-i18n:label name="res_l_comput_basic_info"/></p><span class="tip2b"></span>
		<table id="tab_cinf"  border="0" class="report" width="97%">
			<tr>
				<th><icms-i18n:label name="res_l_network_c"/>:</th>
				<td width="30%"><label id="s_cclassName"></label></td>
				<th><icms-i18n:label name="res_l_network_gateway"/>:</th>
				<td><label id="s_gateway"></label></td>
			</tr>
			<tr>	
				<th><icms-i18n:label name="res_l_network_subnetMask"/>:</th>
				<td><label id="s_subnetmask"></label></td>
				<th><icms-i18n:label name="res_l_comput_remark"/>:</th>
				<td><label id="s_reamrk"></label></td>
			</tr>
		</table>
		<p class="btnbar1" style=" padding-top: 20px;padding-left:42px;">
			<%-- <shiro:hasPermission name="network:c_hold">
			<input type="button" value="占  位" onclick="saveCclassValid('H');" id="c_hold" class="btn"></input>
			</shiro:hasPermission>  --%>
			<shiro:hasPermission name="network:c_change">
			<input type="button" value='<icms-i18n:label name="networkRes_btn_updateC"/>' onclick="cclassChange();" id="c_change" class="btn btn_dd2" style="margin-right:12px; margin-left:0;"></input>
			</shiro:hasPermission>
			<shiro:hasPermission name="network:pool_add">
	        <input type="button" id="pool_add" value='<icms-i18n:label name="networkRes_btn_addPool"/>' style="margin-right: 12px; width:120px; margin-left:0; padding-left:0; padding-right:0;" class="btn btn_dd2" />
	        </shiro:hasPermission> 
			<shiro:hasPermission name="network:beAvailable">
				<input  id="beAvailableFlag" type="hidden" value="1"></input>		
			</shiro:hasPermission>
			<shiro:hasPermission name="network:occupy">
				<input type="hidden" id="occupyFlag" value="1"/>
			</shiro:hasPermission>
		</p>
	</div>
	<div id="showIpStatusDiv" style="display: none; background:#fff; padding:20px; padding-top:30px;">
			
			<span style="float:left; margin-right:10px;"><icms-i18n:label name="res_l_distribute_state"/>：</span>
			<icms-ui:dic id="allocated" name="allocated" kind="ALLOCED_STATUS_CODE" showType="select" attr="style='margin-bottom:10px; float:left; width:184px; height:24px; 'class='selInput'" />
			<input class="btn" type="button" onclick="search()" title='<icms-i18n:label name="common_btn_query"/>' value='<icms-i18n:label name="common_btn_query"/>' style="margin-left:10px;">
			
			</br>
			<span style="height:4px; background:#F1F4F6; display:block; clear:both; "></span>
			<span style="margin-left:20px; display:block; height:30px; line-height:30px; clear:both; backkgkround:#ccc;" ><font style="font-size:14px; font-weight: bold;"><icms-i18n:label name="res_l_network_ipDisTable"/></font></span>	
			<div class="panel clear" id="gridTableDiv" style="width:100%;">
				<table id="gridTable" ></table>
				<div id="pageGrid"></div>
			</div>	
		</div>
	<div id="querySeqDiv" class="pageFormContent" style="display: none">
		<form id="holdCclassForm">
			<table align="center">
				<tr>
					<td align="left"><font color="red">*</font><font style="font-size:14px;"><icms-i18n:label name="res_l_network_querySeqStart"/>：</font></td>
					<td width="100px" align="left">
						<input type="text" id="querySeqStart" name="querySeqStart"  class="textInput" style="width:154px;margin-left:0; margin-right:30px;"/>
					</td>						
					<td align="left"><font color="red">*</font><font style="font-size:14px;"><icms-i18n:label name="res_l_network_querySeqEnd"/>：</font></td>
					<td width="100px" align="left">
						<input type="text" id="querySeqEnd" name="querySeqEnd"  class="textInput" style="width:154px;margin-left:0;"/>
					</td>				
				</tr>
				<tr>			
					<td align="center" colspan="4" class="btnbar1" style="height:40px;">
						<input type="reset" title='<icms-i18n:label name="common_btn_clear"/>' class="btn btn_dd2 btn_dd3" style="vertical-align:middle; margin-right:0;" value='<icms-i18n:label name="common_btn_clear"/>'/>							
						<input type="button" class="btn btn_dd2 btn_dd3" onclick="occupyBySeqFun()" value='<icms-i18n:label name="networkRes_btn_place"/>' style="vertical-align:middle;"/>
					</td>	
				</tr>
			</table>
		</form>
	</div>
 </div><!-- 右边div结束 --> 
 <div id="ipupdateDiv" class="pageFormContent" style="display: none">
			<div>
				<i><font color="red">*</font><icms-i18n:label name="res_l_comput_poolName"/>:</i>
				<input type="text" id="being_pl_pool_name" name="pl_pool_name"  class="textInput" />
			 </div>
			 <div style="margin-top: 10px">
					<i><font color="red">*</font><icms-i18n:label name="res_l_network_ename"/>:</i>
					<input type="text" id="being_pl_ename" name="pl_ename"  class="textInput"/>
			</div>
							
			<%-- <div align="center" colspan="4"  class="btnbar1" style="padding-right:43px;">
				<input type="button"  onclick="beingSaveBtn()"  class="btn btn_dd2 btn_dd3" id="nwResNameBtn" value='<icms-i18n:label name="common_btn_save"/>' style="margin-left:0; margin-right:5px;"/> --%>
			<div align="center" colspan="4"  class="btnbar1" style="padding-right:43px; height:40px; padding-top:18px;">
				<input type="button" onclick="closeView('ipupdateDiv')"  class="btn btn_dd2 btn_dd3" id="nwResPoolcancelBtn" value='<icms-i18n:label name="commom_btn_cancel"/>' style="margin-left:0; margin-right:24px;"/>
				<input type="button"  onclick="beingSaveBtn()"  class="btn btn_dd2 btn_dd3" id="nwResNameBtn" value='<icms-i18n:label name="common_btn_save"/>' style="margin-left:0; margin-right:5px;"/>
			</div>	
	</div>
	
	<div id="gwUpdateDiv" class="pageFormContent" style="display: none">
			<div>
				<i><font color="red">*</font><icms-i18n:label name="com_l_gateway"/>:</i>
				<input type="text" id="being_pl_pool_wg" name="being_pl_pool_wg"  class="textInput" />
			 </div>
			<div align="center" colspan="4"  class="btnbar1" style="padding-right:43px; height:40px; padding-top:16px;">
				<input type="button" onclick="closeView('gwUpdateDiv')"  class="btn btn_dd2 btn_dd3" id="gateWayEditBtnCancel" value='<icms-i18n:label name="commom_btn_cancel"/>' style="margin-left:0; margin-right:24px;"/>
				<input type="button"  onclick="gateWayEditBtn()"  class="btn btn_dd2 btn_dd3" id="gateWayEditBtn" value='<icms-i18n:label name="common_btn_save"/>' style="margin-left:0; margin-right:5px;"/>
			</div>	
	</div>
</body>
</html>