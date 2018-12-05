<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<html>
<head>
<title>云服务申请</title>

<%-- <script type="text/javascript" src="${ctx}/scripts/jquery.min.js" ></script> --%>
<script type="text/javascript" src="${ctx}/scripts/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/scripts/switch.js"></script>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/switch.css" />
		
<link rel="stylesheet" href="${ctx}/css/jquery.range.css">
<script src="${ctx}/scripts/jquery.range.js"></script>
<script type='text/javascript' src='${ctx}/scripts/json.js'></script>
<script type='text/javascript'
	src='${ctx}/scripts/My97DatePicker/WdatePicker.js'></script>
<script type='text/javascript'
	src='${ctx}/pages/request/js/cloudRequestInfo.js'></script>
<script type='text/javascript'
	src='${ctx}/pages/request/js/cloudVirtualSupply.js'></script>
<script type='text/javascript'
	src='${ctx}/pages/request/js/cloudVirtualRecycle.js'></script>
<script type='text/javascript'
	src='${ctx}/pages/request/js/cloudVirtualExtend.js'></script>
<script type='text/javascript'
	src='${ctx}/pages/request/js/serviceAutoAction.js'></script>
<script type="text/javascript" 
	src="${ctx}/jquery/js/jquery-jiajian.js"></script>
<script type="text/javascript">
var actType = '<c:out value="${param.actType}"></c:out>';
var srId = '${srId}';
var srCode = '${srCode}';
//var srCode = '<c:out value="${param.srCode}"></c:out>';
var todoId='<c:out value="${param.todoId}"></c:out>';
</script>
<style type="text/css">
html,body {
	height: 90%;
}
.pageFormContent p{margin-bottom:0;}
.single-slider{
	/* float: right; */
	width: 40px;
	text-align: center;
	border: 1px solid #dbd0d0;
	border-radius: 3px;
	margin-right:30px;
}
.slider-container{
	width: 400px;
	display:inline-block;
	vertical-align: middle;
}
.slider-container .back-bar{
	height: 25px;
}
.slider-container .scale {
    top: -5px;
}
.theme-green .back-bar .pointer {
  width: 25px;
  height: 25px;
  top: 0px;
  cursor: pointer;
  }
  .theme-green .back-bar .selected-bar{
  	background-image: linear-gradient(to bottom, #18a689, #18a689);
  }
  .switch{
  	/* margin-left: 71px; */
	display: inline-block;
	/* float: left; */
	vertical-align: middle;
  }
  .has-switch span.switch-left{
    background-image: linear-gradient(to bottom, #18a689, #18a689);
  }
#right .content-main .pageFormContent div {
  
    text-align: left;
}
</style>
</head>
<body onload="init();" class="main1" id="right" style="padding-bottom: 50px;">
	<div class="content-main clear" id="list">
	<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bm_title_myRequest"/></div>
				<div class="WorkSmallBtBg">
					<small>
					<icms-i18n:label name="bm_desc_myRequest"/>
					</small>
				</div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg" style="overflow:hidden;">
		<form id="f" >
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
					<td class="tabBt" style="width:70px;"><icms-i18n:label name="bm_l_service_number"/>：</td>
					<td class="tabCon">
						<input type="text" id="srCode_list" name="srCode_list" class="textInput"/>
					</td>
					<td class="tabBt"><icms-i18n:label name="bm_l_app_system"/>：</td>
					<td class="tabCon">
<%-- 					<icms-ui:dic id="appId_list" name="appId_list" showType="select" attr="class='selInput'" /> --%>
						<select id="appId_list" name="appId_list" class='selInput' >
							<option value="" selected><icms-i18n:label name="res_l_comput_select"/>...</option>
						</select>
						
					</td>
					<td class="tabBt"><icms-i18n:label name="bm_l_data_center"/>：</td>
					<td class="tabCon">
						<icms-ui:dic id="datacenterId_list" name="datacenterId_list" showType="select" attr="class='selInput'"
								sql="select ID as value, DATACENTER_CNAME as name from RM_DATACENTER where IS_ACTIVE = 'Y'"/>
					</td>
					</tr>
					<tr>
					<td class="tabBt"><icms-i18n:label name="bm_l_request_status"/>：</td>
					<td class="tabCon">
						<icms-ui:dic id="srStatusCode" name="srStatusCode" kind="REQUEST_STATUS_TYPE" showType="select" attr="class='selInput'" />
					</td>
					<td class="tabBt"><icms-i18n:label name="bm_request_type"/>：</td>
					<td class="tabCon">
						<icms-ui:dic id="srTypeMark_list" name="srTypeMark_list" showType="select" attr="class='selInput'"
						sql="select SR_TYPE_MARK as value, SR_TYPE_NAME as name from BM_SR_TYPE where IS_ACTIVE = 'Y' and SR_TYPE_CODE = 'L'" />
					</td>
					<td class="tabBt"></td>
					<td class="tabCon"></td>
				</tr>
			</table>
					</div>
				</div>
				<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td>
								<a href="javascript:search()" type="button" title='<icms-i18n:label name="common_btn_request_query"/>' class="btn" onclick="search();return false;" value='<icms-i18n:label name="common_btn_query"/>'>
									<span class="icon iconfont icon-icon-search"></span>
	  								<span>查询</span>
								</a>
								<button id="btn_reset"  type="reset" title='<icms-i18n:label name="common_btn_clear"/>' class="btnDel"  value='<icms-i18n:label name="common_btn_clear"/>'>
									<span class="icon iconfont icon-icon-clear1"></span>
	  								<span>清空</span>
								</button>
								<a href="javascript:void(0)" type="button" title='<icms-i18n:label name="common_btn_request_add"/>' class="btn" onclick="add();" value='<icms-i18n:label name="common_btn_add"/>'>
									<span class="icon iconfont icon-icon-add"></span>
	  								<span>添加</span>
								</a>
								
					</td>
						</tr>
					</table>
				</div>
			</div>
			
			</form>
		</div>
		<div class="pageTableBg">
			<div class="panel clear" id="div_list1">
				<table id="gridTable" ></table>
				<div id="gridPager"></div>
			</div>
		</div>	
	</div>
	<div class="content-main clear" id="nav" style="height:0px;display: none">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bm_title_Request"/></div>
				<div class="WorkSmallBtBg">
					<small id="small_title">
					<icms-i18n:label name="bm_title_Request"/>
					</small>
					<small id="small_back" style="right:80px;">
					</small>
				</div>
					
			</h1>
		</div>
		<div class="pageFormContent searchBg" id="div_baseCase" style="height:270px; padding-top:10px;text-align:left;">
			<form action="" method="post" id="requestForm">
				<input type="hidden" id="srId" name="srId"/>
				<input type="hidden" id="srCode" name="srCode"/>
				<div>
					<p>
						<i><font color="#FF0000">*</font><icms-i18n:label name="bm_l_app_system"/>：</i>					
<%-- 							<icms-ui:dic id="appId" name="appId" showType="select" attr="class='selInput' onclick='appSelectChange();'" --%>
<%-- 								sql="select APP_ID as value, CNAME as name from APP_INFO where IS_ACTIVE = 'Y' and FATHER_ID = '0' order by CONVERT( CNAME USING gbk ) COLLATE gbk_chinese_ci ASC" /> --%>
								<select id="appId" name="appId" class='selInput'  >
									<option value="" selected><icms-i18n:label name="res_l_comput_select"/>...</option>
								</select>
						</p>
					<p>
						<i><font color="#FF0000">*</font><icms-i18n:label name="bm_l_data_center"/>：</i>
							<icms-ui:dic id="datacenterId" name="datacenterId" showType="select" attr="class='selInput'  onclick='datacenterSelectChange();'"
							 	sql="select ID as value, DATACENTER_CNAME as name from RM_DATACENTER where IS_ACTIVE = 'Y'"/>
					</p>
				</div>			
				<div>
					<p>
						<i><font color="#FF0000">*</font><icms-i18n:label name="bm_request_type"/>：</i>					
							<icms-ui:dic id="srTypeMark" name="srTypeMark" showType="select" attr="class='selInput'"
								sql="select SR_TYPE_MARK as value, SR_TYPE_NAME as name from BM_SR_TYPE where IS_ACTIVE = 'Y' and SR_TYPE_CODE = 'L'"/>
					</p>
					<!--<p style="width:435px;">
						<i>申请人：</i>					
							<input id="userName" name="userName" value='<shiro:user><shiro:principal /></shiro:user>' type="text" class="textInput" readonly="readonly"></input>
					</p>-->	
					<input type="hidden" id="userId" name="userId" value='<shiro:user><shiro:principal property="userId" /></shiro:user>'>	
					<p id="serviceSelectTime">
					   <i> <icms-i18n:label name="bm_request_term"/>：</i>					   
							<input id="serviceBeginTimes"  name="serviceBeginTimes"  onClick="WdatePicker()" style="width: 79px;" type="text" class="textInput" />
							<span margin: 0 5px;" >--</span>
							<input id="serviceEndtimes"  name="serviceEndtimes"  onClick="WdatePicker()" style="width: 79px;" type="text" class="textInput"  />
					</p>
					<p>
						<i><icms-i18n:label name="bm_request_outline"/>：</i>					
							<textarea id="summary" name="summary" style="width:622px;" rows="2" class="textInput"></textarea>
					</p>
					<p style="display: none" id="approveP">
						<i><icms-i18n:label name="bm_approval_result"/>：</i>					
						<input type="text" class="readonly textInput"  readonly id="approveResult" style="width:622px;"/>
					</p>
					<!-- <p class="nowrap" style="width:435px;">
						<i><font color="#FF0000">*</font>详细描述：</i>					
							<textarea id="describe" name="describe" cols="100" rows="2"  class="textInput"></textarea>
					</p>-->
				</div>	
					
			</form>
		</div>	
			
		<div class="step" id="step" >
		  <ul>
			  <li class="now" style="padding-left:0;" id="step1"><i>1</i><icms-i18n:label name="bm_select_du"/></li><b></b>
			  <li  id="step2"><i>2</i><icms-i18n:label name="bm_select_cs"/></li><b></b>
			  <li  id="step3"><i>3</i><icms-i18n:label name="bm_select_serverConfig"/></li><b></b>
			  <li id="step4"><i>4</i><icms-i18n:label name="bm_input_serviceParam"/></li>
		  </ul>
		</div>
		<div class="step" id="step_s">
		  <ul>
			  <li class="now" style="padding-left:0;" id="step_s1"><i>1</i><icms-i18n:label name="bm_select_du"/></li><b></b>
			  <li  id="step_s2"><i>2</i><icms-i18n:label name="bm_select_vm"/></li><b></b>
			  <li  id="step_s3"><i>3</i><icms-i18n:label name="bm_select_serverConfig"/></li><b></b>
		  </ul>
		</div>
		<div class="step" id="step_auto">
		  <ul>
			  <li class="now" style="padding-left:0;" id="step_auto1"><i>1</i><icms-i18n:label name="bm_select_vm"/></li><b></b>
			  <li  id="step_auto2"><i>2</i><icms-i18n:label name="bm_select_cs"/></li><b></b>
			  <li id="step_auto3"><i>3</i><icms-i18n:label name="bm_input_serviceParam"/></li>
		  </ul>
		</div>
	</div>
	
		<!-- 申请类型：供给Start -->
		<div id="supply_main_div" class="clear" style="display: none;">
			<div id="service-apply-div" class="btnbar1" style="padding:28px 3px 0px 0px; text-align:right; height:50px; width:99%;margin-top:285px;">
				<a href="javascript:void(0)" type="submit" id="service-add-btn" class="btn btn_dd2 btn_dd3"
					value='<icms-i18n:label name="common_btn_select"/>' style="margin-bottom:15px;margin-top:19px;visibility:visible; margin-right:5px; margin-left:0; float:right; padding:0px 24px; height:22px; line-height:22px;">
						<span>选择</span>
					</a>
			</div>
			<!--  <h2 class="content-h2"></h2>  -->
			<div class="pageTableBg">
				<div class="panel clear" id="div_list2">
				<table id="serviceAppliedTable"></table>
				<div id="pageNav_supply"></div>
			</div>
			</div>
			
			<div id="supply_btnbar_div" class="btnbar" style="visibility:visible; text-align:right; margin-bottom:20px; width:99%;">
				<input type="button" class="btn btn_dd" id="cancel_btn" value='<icms-i18n:label name="commom_btn_cancel"/>'  style="margin-right:0; margin-left:5px;"/>
				<input type="button" class="btn btn_dd" id="goback_btn" value='<icms-i18n:label name="common_btn_return"/>' onclick="javascript:history.go(-1);" style="display: none; margin-right:0; margin-left:5px;"/> 
				<input type="button" class="btn btn_dd" id="delete_btn" value='<icms-i18n:label name="common_btn_closeRequest"/>' style="display: none; margin-right:0; margin-left:5px;"/> 
				<input type="button" class="btn btn_dd" id="submit_btn" value='<icms-i18n:label name="common_btn_submit"/>' style="margin-right:0; margin-left:5px;" />
			</div>
		</div>

			<!-- 供给step1 -->
			<div id="select_du_div" class="pageFormContent" style="display: none;padding:0px;" >				
				<form id="duForm">		
					<div class="searchBg" style="margin-top:140px;">
						<table width="96%" id="duFilter" align="center" >
							<tr>
								<td width="10%"><icms-i18n:label name="bm_l_du_name"/>：</td>
								<td> <input type="text" id="duName" name="duName" class="textInput" /></td>
								<td width="10%"><icms-i18n:label name="bm_l_du_type"/>：</td>
								<td> <icms-ui:dic id="duType" name="duType"
									kind="DU_SEV_TYPE" showType="select" attr="class='selInput'" /></td>								
								<td width="20%" style="height: 70px;text-align: right;" class="btnbar1">
									<input type="button" id="du_reset" value='<icms-i18n:label name="common_btn_reset"/>' onclick="duReset();" class="btn btn_dd2 btn_dd3" style="margin-right:5px; margin-left:0;" />
									<input type="button" id="du_search" value='<icms-i18n:label name="common_btn_query"/>' class="btn btn_dd2 btn_dd3" onclick="duSearch();" style="margin-right:5px; margin-left:0;"/>
									<!-- <input class="Btn_Empty" type="reset" style="text-indent:-999px;" title="清空" "> -->
									</td>
							</tr>
						</table>
					</div>			
					
					
					<div class="panel clear" id="supply_div1">
						<table id="appdu_Table"></table>
						<div id="AppdupageNav"></div>
					</div>
					
					<table border="0" cellpadding="0" cellspacing="0" width="99%"
						align="left" >
						<tr>
							<td align="right" style="height: 50px" class="btnbar1">
								<input type="button"
								id="next_du" title='<icms-i18n:label name="common_btn_next_step"/>' value='<icms-i18n:label name="common_btn_next_step"/>' class="btn btn_dd2 btn_dd3" style="margin-left:5px; margin-right:0;" />
								<input type="button"
								id="goback" onclick="GoBackToMain();" title='<icms-i18n:label name="common_btn_return"/>' value='<icms-i18n:label name="common_btn_return"/>' class="btn btn_dd2 btn_dd3" style="margin-left:5px; margin-right:0;" />
								
								<input type="hidden" name="du_id" id="du_id"/>
								</td>
						</tr>
					</table>
				</form>
			</div>
			
			<!-- step2 -->
			<div id="select_catalog_div" class="pageFormContent" style="display: none;padding:0px;">
			
				<form id="catalogForm">
				<div class="searchBg" style="padding-bottom:5px;">
					<table width="97%" id="catalogFilter" align="center" style="margin-top:138px;">
						<tr>
							<td width="10%"><icms-i18n:label name="bm_l_platform_type"/>：</td>
							<td> <icms-ui:dic name="platformType" id="platformType"
						sql="SELECT '1' as id, PLAY.PLATFORM_ID AS value,PLAY.PLATFORM_NAME AS name,'1' AS kind  FROM RM_PLATFORM PLAY WHERE PLAY.IS_ACTIVE='Y'"
						showType="select" attr="class='selInput'" /></td>
							<td width="10%"><icms-i18n:label name="bm_l_highAvail_type"/>：</td>
							<td><icms-ui:dic id="haType" name="haType" kind="HA_TYPE" showType="select" attr="class='selInput'" /></td>								
							<td align="right" style="width:20%;height: 70px" class="btnbar1">
								<input
								type="button" id="catalog_reset" title='<icms-i18n:label name="common_btn_reset"/>' value='<icms-i18n:label name="common_btn_reset"/>' onclick="catalogReset();" class="btn btn_dd2 btn_dd3" style="margin-right:5px; margin-left:0;" />
								<input type="button"
								id="calalog_search" title='<icms-i18n:label name="common_btn_query"/>'  value='<icms-i18n:label name="common_btn_query"/>' class="btn btn_dd2 btn_dd3"onclick="catalogSearch();" style="margin-right:5px; margin-left:0;"/> </td>
						</tr>
						<tr id="spCs"><td align="left" style="color:red" colspan="5"><icms-i18n:label name="bm_ve_tip1"/></td></tr> 
					</table>
					</div>
					<%-- <table id="spCs" width="96%" align="center">
						<tr>
							<td align="left" style="color:red"><icms-i18n:label name="bm_ve_tip1"/></td>
						</tr>
					</table> --%>
					<div class="panel clear" id="supply_div2">
						<table id="catalogTable" ></table>
						<div id="pageNav"></div>
					</div>					
					<table border="0" cellpadding="0" cellspacing="0" width="100%"
						align="center">
						<tr>
							<td align="right" style="height: 50px; margin-right:40px;" class="btnbar1">
							<input
								type="button" id="next_catalog" title='<icms-i18n:label name="common_btn_next_step"/>' value='<icms-i18n:label name="common_btn_next_step"/>' class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;"/>
							<input type="button"
								id="pre_catalog" title='<icms-i18n:label name="common_btn_previous_step"/>' value='<icms-i18n:label name="common_btn_previous_step"/>' class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;"/> 
								</td>
						</tr>
					</table>
				</form>
			</div>
			
		    <!-- step3 -->
			<div id="select_set_div" class="pageFormContent" style="display: none;margin-top:144px;padding: 15px;background: #fff;">
			<input type="hidden" id=serviceIds name="serviceIds" />
			<input type="hidden" id="serviceIsAvtice" name="serviceIsAvtice">
			 <form id="setForm">
			 	<table id="newResTab" align="left" width="100%">
					<tr>
						<td><i><font color="red">*</font><icms-i18n:label name="bm_l_cs_set"/>:</i><input type="hidden" id="vmrowId" name="vmrowId" /> 
						    <span id="uc-number" class="uc-number">
					             <a  href="javascript:;" onclick="changeVmNum('reduce');" target="_self" rel="nofollow" class="uc-reduce" hidefocus="" >
					             <small class="icon-reduce">-</small>
					             </a><input type="text" id="service_num" class="ar-input" name="service_num" value="1" maxlength="4" onblur="changeVmNum('modify');">
					             <a href="javascript:;" onclick="changeVmNum('add');" target="_self" rel="nofollow" class="uc-add" hidefocus="">
					             <small class="icon-add">+</small></a>
					         </span>
							<span id="serviceNumErr" style="color:#FF0000;"></span></td>
					</tr>
					<tr>	
						<td><i><font color="red">*</font><icms-i18n:label name="bm_l_device_number"/>:</i><input type="text" id="vm_num" name="vm_num"
							class="readonly" readonly="readonly" style="margin-left: 6px;" />
						</td>
					</tr>
				</table>
				<table border="0" cellpadding="0" bordercolor="#0099ff" cellspacing="1" id="vmConfig" align="left" width="100%">
					<tr><td>
					
					<i style=""><font color="red">*</font>CPU：</i>
					<icms-ui:dic name="cpu" id="cpu" sql="SELECT CPU_MEM_REF_ID as id, PARAM_NAME AS name,PARAM_VALUE AS value ,'1' AS kind  FROM CLOUD_CPU_MEM_REF  WHERE PARAM_TYPE='CPU' AND IS_ACTIVE='Y' ORDER BY  PARAM_VALUE ASC"
								showType="flatSelect" attr="style='width: 85px;'"/>
					
					</td></tr>
					<tr><td>
					<i style=""><font color="red">*</font><icms-i18n:label name="bm_l_device_mem"/>：</i>
					<icms-ui:dic name="mem" id="mem" sql="SELECT CPU_MEM_REF_ID as id, PARAM_NAME AS name,PARAM_VALUE AS value ,'1' AS kind FROM CLOUD_CPU_MEM_REF  WHERE PARAM_TYPE='MEM' AND IS_ACTIVE='Y' ORDER BY  PARAM_VALUE ASC"
								showType="flatSelect" attr="style='width: 85px;'" />
					</td></tr>
					
					<tr id="sysDisk_Tr">
					<td colspan="2">
					<i><icms-i18n:label name="bm_l_sys_disk"/>：</i>
					 	<input  class="single-slider"  id="hardDisk" readonly />
					</td></tr>
					
					<tr id="dataDisk_Tr">
					<td>
					<i><icms-i18n:label name="bm_l_data_disk"/>：</i>
						<input  class="single-slider"  id="dataDisk" readonly />
					</td></tr>
					<!-- 共享 -->
					<tr id="share_Tr" style="display:none"><td>
					<i><icms-i18n:label name="bm_l_data_share"/>：</i>
						<div class="switch switch-small" id="shareDiv" data-on-label="是" data-off-label="否">
						    <input type="checkbox"   id="share" />
						</div>
					</td></tr>
					<!-- 浮动ip -->
					<tr id="float_Tr" style="display:none"><td>
					<i><icms-i18n:label name="bm_l_float_ip"/>：</i>
						<div class="switch switch-small"  id="floatIpDiv" data-on-label="是" data-off-label="否">
						    <input type="checkbox"   id="floatIp" />
						    
						</div>
					</td></tr>
				</table>
				<div id="showHostConfigList" style="display: none;margin-top: 13px;margin-left: 246px;width: 700px;align:center;">
					<div id="showHostConfigListDiv" class="panel clear" style="margin-left: 14px;margin-top: px;">
						<table id="hostConfigGridTable"></table>
						<div id="hostConfigGridPager"></div>
					</div>
				</div>
				
				<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
					<tr>
						<td align="right" style="height: 50px; margin-right:20px;" class="btnbar1">
							<input type="button" id="next_set" title='<icms-i18n:label name="common_btn_next_step"/>' value='<icms-i18n:label name="common_btn_next_step"/>' class="btn btn_dd2 btn_dd3" style="margin-right:5px; margin-left:0; background:#18a689; float:right;"/>
							<input type="button" id="pre_set" title='<icms-i18n:label name="common_btn_previous_step"/>' value='<icms-i18n:label name="common_btn_previous_step"/>' class="btn btn_dd2 btn_dd3" style="margin-right:5px; margin-left:0; "/>
						</td>
					</tr>
				</table>
			 </form>
			</div>
			
			<!-- step4 -->
			<div id="select_attr_div" class="pageFormContent" style="display: none">
				<input id="duId_attr" name="duId_attr" type="hidden"
					class="textInput" /> </br>
					<div class="panel clear" id="supply_div4" >						
						<table id="attrTable" style="margin-top:138px;"></table>
					</div>
				<table border="0" cellpadding="0" cellspacing="0" width="100%"
					align="center" >
					<tr>
						<td align="right" style="height: 50px;" class="btnbar1">
						<input type="button" id="save_service" title='<icms-i18n:label name="common_btn_ensure"/>' class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="common_btn_ensure"/>' style="margin-right:5px; margin-left:0;" />
						<input type="button" id="pre_attr" title='<icms-i18n:label name="common_btn_previous_step"/>' class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="common_btn_previous_step"/>' style="margin-right:5px; margin-left:0;"/> 
						<input type="button" id="cancel_attr_btn" value='<icms-i18n:label name="commom_btn_cancel"/>' class="btn btn_dd2 btn_dd3" style="margin-right:5px; margin-left:0;"/> 
						<input type="button" id="save_attr_btn" value='<icms-i18n:label name="common_btn_save"/>' class="btn btn_dd2 btn_dd3" style="margin-right:5px; margin-left:0;"/> 
						</td>
					</tr>
				</table>
				<!-- listSql查询结果显示和选择 -->
					<div id="attrListSqlDiv" class="div_center" style="display: none;">
						<div class="gridMain">
								<table id="gridTableListSql"></table>
								<table id="gridPagerListSql"></table>
						</div>
					</div>
			</div>
			<!-- 申请类型：供给End -->
			
			<!-- 申请类型：回收Start -->
			<div id="recycle_main_div" style="display: none"  class="clear">
					<div id="service-apply-div" align="left" class="btnbar1" style="padding-top:28px;text-align:right;margin-right:10px; clear:both; height:50px; width:98%; margin-top:285px;" >
							<input type="button" style=" margin-bottom:15px; visibility:visible; margin-right:5px; margin-left:0;"id="recycle_select_btn" class="btn btn_dd2 btn_dd3"
								value='<icms-i18n:label name="common_btn_select"/>'/>
					</div>	
					<div class="panel clear" id="div_list4">
						<table id="selectDeviceTable"></table>
						<div id="pageNav_recycle"></div>
					</div>
				<div id="recycle_btnbar_div" class="btnbar btnbar1" style="text-align:right; width:98%;">
					<input type="button" class="btn btn_dd2 btn_dd3" id="recycle_cancel_btn" value='<icms-i18n:label name="commom_btn_cancel"/>' style="margin-right:0; margin-left:5px;" onclick="cancelRequest();" />
					<input type="button" class="btn btn_dd2 btn_dd3" id="recycle_goback_btn" value='<icms-i18n:label name="common_btn_return"/>' onclick="javascript:history.go(-1);" style="display: none;"/> 
					<input type="button" 	class="btn btn_dd2 btn_dd3" id="recycle_delete_btn" value='<icms-i18n:label name="common_btn_closeRequest"/>' onclick="deleteRequest();" style="display: none;"/>
					<input type="button" class="btn btn_dd2 btn_dd3" id="recycle_submit_btn" value='<icms-i18n:label name="common_btn_submit"/>' style="margin-right:0; margin-left:5px;" /> 
				</div>
			</div>		
			<div id="recycle_select_div" class="panel"
				style="width: 700px; display: none; padding-top:10px; padding-bottom:10px;">
				<div class="choosecontent">
					<h2><icms-i18n:label name="bm_l_novr_device"/>:</h2>
					<h2 style="padding-left: 110px; width: 100px;"><icms-i18n:label name="bm_l_vr_device"/>:</h2>
					<div class="choose clear ztree" id="deviceTree1" style="width:260px;overflow-y:scroll;"></div>
					<div class="choosebtn">
						<input type="button" class="btn" value=">>"
							onclick="setSelectNode('deviceTree1', 'deviceTree2');" style=""/> <input
							type="button" class="btn" value="&lt;&lt;"
							onclick="setSelectNode('deviceTree2', 'deviceTree1');" />
					</div>
					<div class="choose ztree" id="deviceTree2" style="width:260px;overflow-y:scroll;"></div>
					<div class="btnbar clear btnbar1" style="margin-right:16px;">
						<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="commom_btn_confirm"/>'
							onclick="selectRecycleDevice();" />
					</div>
				</div>
			</div>	
			<!-- 申请类型：回收End -->

			<!-- 申请类型：扩容Start -->
			<div id="extend_main_div" style="display: none;" class="clear">
				<div id="service-extend-div" class="btnbar1"  align="left" style="padding-top:28px; padding-bottom:0px; text-align:right;  height:50px; width:98%; margin-top:285px;">
					<input type="submit" id="service-extend-btn" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="common_btn_select"/>' style="margin-bottom:15px;visibility:visible; margin-right:5px; margin-left:0;clear:both; "/>
				</div>
				<div class="panel clear" id="div_list3">
					<table id="serviceExtendedTable"></table>
					<div id="pageNav_extend"></div>
				</div>
				<div id="extend_btnbar_div" class="btnbar btnbar1" style="text-align:right; margin-right:10px">
					<input type="button" class="btn btn_dd2 btn_dd3" id="extend_goback_btn" value='<icms-i18n:label name="common_btn_return"/>' onclick="javascript:history.go(-1);" style="display: none;"/>
					<input type="button" class="btn btn_dd2 btn_dd3" id="extend_cancel_btn" value='<icms-i18n:label name="commom_btn_cancel"/>' style="margin-right:0; margin-left:5px;"/>
					<input type="button" class="btn btn_dd2 btn_dd3" id="extend_delete_btn" value='<icms-i18n:label name="common_btn_closeRequest"/>' style="display: none;"/>
					<input type="button" class="btn btn_dd2 btn_dd3" id="extend_submit_btn" value='<icms-i18n:label name="common_btn_submit"/>' style="margin-right:0; margin-left:5px;"/>
				</div>

			</div>		
			
			<!-- 扩容step1 -->
			<div id="select_extendDu_div" class="pageFormContent" style="display: none;padding:0px;">
				<form id="extendDuForm">					
					<div id="radio_extendDu_div" class="panel clear" style="margin-top:154px; padding-right:10px;">
						<table id="appExtendDu_Table"></table>
						<div id="AppExtendDupageNav"></div>
					</div>
					
					<div class="btnbar btnbar1" style="text-align:right;">
						<input type="button" id="next_extendDu" title='<icms-i18n:label name="common_btn_next_step"/>' value='<icms-i18n:label name="common_btn_next_step"/>' class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;" />
						<input type="button" title='<icms-i18n:label name="common_btn_return"/>' value='<icms-i18n:label name="common_btn_return"/>' class="btn btn_dd2 btn_dd3" onclick="GoBackToMain1()" style="margin-left:0; margin-right:5px;" />
					</div>
				</form>
			</div>
			
			<!-- 扩容step2 -->
			<div id="select_extendDev_div" class="pageFormContent"
				style="display: none; padding:0px;">
				<form id="extendDuForm">
					<div id="radio_extendDev_div" class="panel clear" style="margin-top:154px; padding-right:10px;">
						<table id="appExtendDev_Table"></table>
						<div id="AppExtendDevpageNav"></div>
					</div>
					<div class="btnbar btnbar1" style="text-align:right; margin-right:20px;">
						<input type="button" id="next_extendDev" title='<icms-i18n:label name="common_btn_next_step"/>' value='<icms-i18n:label name="common_btn_next_step"/>' class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;"/>
						<input type="button" id="pre_extendDev" title='<icms-i18n:label name="common_btn_previous_step"/>' value='<icms-i18n:label name="common_btn_previous_step"/>' class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;"/>
					</div>
				</form>
			</div>
			
			<!-- 扩容step3 -->
			<div id="select_extendSet_div" class="pageFormContent"
				style="display: none; margin-top:144px;padding:15px; background:#fff;">
				<form id="extendSetForm">
				<table border="0" cellpadding="0" bordercolor="#0099ff" cellspacing="1" width="100%">
					<tr><td>
					<i><font color="red">*</font>CPU：</i>
					<icms-ui:dic name="cpu" id="cpu" sql="SELECT CPU_MEM_REF_ID as id, PARAM_NAME AS name,PARAM_VALUE AS value ,'1' AS kind  FROM CLOUD_CPU_MEM_REF  WHERE PARAM_TYPE='CPU' AND IS_ACTIVE='Y' ORDER BY  PARAM_VALUE ASC"
								showType="flatSelect" attr="style='width: 85px;'"/>
					
					</td></tr>
					<tr><td>
					<i><font color="red">*</font><icms-i18n:label name="bm_l_device_mem"/>：</i>
					<icms-ui:dic name="mem" id="mem" sql="SELECT CPU_MEM_REF_ID as id, PARAM_NAME AS name,PARAM_VALUE AS value ,'1' AS kind FROM CLOUD_CPU_MEM_REF  WHERE PARAM_TYPE='MEM' AND IS_ACTIVE='Y' ORDER BY  PARAM_VALUE ASC"
								showType="flatSelect" attr="style='width: 85px;'" />
					</td></tr>
					<tr id="dataDisk_TrVe"><td>
					<i ><icms-i18n:label name="bm_l_data_disk"/>：</i>
						<input  class="single-slider"  id="dataDiskVe" readonly />
					</td></tr>
				</table>
				<div class="btnbar btnbar1" style="text-align:right; margin-right:20px;">
					<input type="button" id="save_extend_btn" title='<icms-i18n:label name="common_btn_ensure"/>' value='<icms-i18n:label name="common_btn_ensure"/>' class="btn btn_dd2 btn_dd3" style="margin-right:5px; margin-left:0;"/>
					<input type="button" id="pre_extendSet" title='<icms-i18n:label name="common_btn_previous_step"/>' value='<icms-i18n:label name="common_btn_previous_step"/>' class="btn btn_dd2 btn_dd3" style="margin-right:5px; margin-left:0;"/>
				</div>
				</form>
			</div>
			<!-- 申请类型：扩容End -->
						
		<!-- 申请类型：服务自动化Start -->
		<div id="serviceAuto_main_div" class="clear" style="display: none;">
			<div id="service-apply-div"  class="btnbar1" style="padding-top:28px; text-align:right; height:50px; width:98%; margin-top:285px;">
				<input type="submit" id="service-auto-btn" class="btn btn_dd2 btn_dd3"
					value="<icms-i18n:label name="selVM"/>" style="margin-bottom:15px;visibility:visible; margin-right:5px; margin-left:0;"/>
			</div>
			<!-- 显示最终信息 -->
			<div class="panel clear" id="serAuto_div1">
				<table id="serviceAutoTable"></table>
				<div id="pageNav_auto"></div>
			</div>
			<div id="supply_btnbar_div" class="btnbar" style="visibility:visible; text-align:right; margin-bottom:20px; width:99%;">
				<input type="button" class="btn btn_dd" id="cancel_btn_auto" value='<icms-i18n:label name="commom_btn_cancel"/>'  style="margin-right:0; margin-left:5px;"/>
				<input type="button" class="btn btn_dd" id="goback_btn_auto" value='<icms-i18n:label name="common_btn_return"/>' onclick="javascript:history.go(-1);" style="display: none; margin-right:0; margin-left:5px;"/> 
				<input type="button" class="btn btn_dd" id="delete_btn_auto" value='<icms-i18n:label name="common_btn_closeRequest"/>' style="display: none; margin-right:0; margin-left:5px;"/> 
				<input type="button" class="btn btn_dd" id="submit_btn_auto" value='<icms-i18n:label name="common_btn_submit"/>' style="margin-right:0; margin-left:5px;" />
				
			</div>
		</div>
		
			
		<!-- 服务自动化step1,选择虚拟机 -->
			<div id="vm_select_div" class="panel"
				style="width: 700px; display: none; padding-top:10px; padding-bottom:10px;">
				<div class="choosecontent">
					<h2><icms-i18n:label name="bm_select_device"/>:</h2>
					<h2 style="padding-left: 110px; width: 100px;"><icms-i18n:label name="bm_selected_device"/>:</h2>
					<div class="choose clear ztree" id="deviceTree1_auto" style="width:260px;overflow-y:scroll;"></div>
					<div class="choosebtn">
						<input type="button" class="btn" value=">>"
							onclick="setSelectNode('deviceTree1_auto', 'deviceTree2_auto');" /> <input
							type="button" class="btn" value="&lt;&lt;"
							onclick="setSelectNode('deviceTree2_auto', 'deviceTree1_auto');" />
					</div>
					<div class="choose ztree" id="deviceTree2_auto" style="width:260px;overflow-y:scroll;"></div>
					<div class="btnbar btnbar1 clear" style="margin-right:16px;">
						<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="commom_btn_confirm"/>'
							id ="next_vm" />
					</div>
				</div>
			</div>	
			
			<!-- 服务自动化step2,选择云服务 -->
		<div id="select_cloudSer_div" class="pageFormContent" style="display: none" >
			<!-- 显示云服务列表 -->
			<div class="panel clear" id="serAuto_div2" style="margin-top:130px;">
				<table id="cloudSerlistTable" ></table>
				<div id="pageNavList"></div>
			</div>
			<table border="0" cellpadding="0" cellspacing="0" width="99%" align="left" >
				<tr>
					<td align="right" style="height: 50px" class="btnbar1"><input type="button"
						id="dev_set" title='<icms-i18n:label name="common_btn_previous_step"/>' value='<icms-i18n:label name="common_btn_previous_step"/>' class="btn btn_dd2 btn_dd3" style="margin-left:5px; margin-right:0;" />
						<input type="button"
						id="next_setAttr" title='<icms-i18n:label name="common_btn_next_step"/>' value='<icms-i18n:label name="common_btn_next_step"/>' class="btn btn_dd2 btn_dd3" style="margin-left:5px; margin-right:0;" />
						<input type="hidden" name="cloudSer_id" id="cloudSer_id"/>
						</td>
				</tr>
			</table>
		</div>
			
			<!-- 服务自动化step3 ,云服务参数-->
			<div id="select_attrAuto_div" class="pageFormContent" style="display: none">
			<input id="hidDeviceId" type="hidden" name="hidDeviceId">
					<div class="panel clear" id="autoCloud_attr_div">						
						<table id="autoCloudattrTable" class="pageFormContent"></table>
					<div class="btnbar btnbar1">
					<input type="button" id="auto_cancel_attr" value='<icms-i18n:label name="commom_btn_cancel"/>' class="btn btn_dd2 btn_dd3" /> <br>
					<input type="button" id="auto_save_attr" value='<icms-i18n:label name="common_btn_save"/>' class="btn btn_dd2 btn_dd3" /> 
				</div>
					</div>
				<table border="0" cellpadding="0" cellspacing="0" width="99%" align="left" >
					<tr>
						 <td align="right" style="height: 50px;" class="btnbar btnbar1">
						 	<input type="button" id="pre_attr_auto" title='<icms-i18n:label name="common_btn_previous_step"/>' class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="common_btn_previous_step"/>' style="margin-right:0; margin-left:5px;"/>
							<input type="button" id="next_saveServiceAuto" title='<icms-i18n:label name="common_btn_ensure"/>' class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="common_btn_ensure"/>' style="margin-right:0; margin-left:5px;" />
						</td>
					</tr>
				</table>
			</div>
			
			<!-- 申请类型：负载均衡Start -->
			<div id="fzjh_main_div" style="display: none">
				待实现 负载均衡</div>
			<!-- 申请类型：负载均衡End -->
			<div id="cloud_attr_div" class="div_center" style="display: none">
				<div style="width: 500px; height: 330px">
					<table id="cloudattrTable"></table>
				</div>
				<div class="btnbar btnbar1">
					<input type="button" id="cancel_attr" value='<icms-i18n:label name="commom_btn_cancel"/>' class="btn btn_dd2 btn_dd3" /> 
					<input type="button" id="save_attr" value='<icms-i18n:label name="common_btn_save"/>' class="btn btn_dd2 btn_dd3" /> 
					<br>
				</div>
			</div>
	<script>
	</script>
			
</body>
</html>