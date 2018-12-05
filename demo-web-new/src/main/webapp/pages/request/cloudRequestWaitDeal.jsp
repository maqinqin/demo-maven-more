<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<html>
<head>
<title>云平台管理系统</title>
<script type="text/javascript" src="${ctx}/pages/request/js/cloudRequestWaitDeal.js"></script>
<script type="text/javascript">
	function init() {
		$("#btn_reset").trigger("click");//触发reset按钮
		initCloudRequestList();
		//自适应宽度
		$("#gridTable").setGridWidth($("#div_list1").width());
		$(window).resize(function() {
			$("#gridTable").setGridWidth($("#div_list1").width());
			$("#gridTable").setGridHeight(heightTotal() + 50);
	    });
	}
</script>
</head>
<body onload="init();" class="main1">
	<div class="content-main clear" id="list">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bm_title_waitDeal"/></div>
				<div class="WorkSmallBtBg">
					<small>
				<icms-i18n:label name="bm_desc_waitDeal"/>
				</small>
				</div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg" style="overflow:hidden";>
		<form id="f" >
		  <div class="searchInWrap_top">
				<div class="searchIn_left" style="width:65%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="99%" align="center">
							<tr style=" height:55px; ">
								<td class="tabBt" style="width:70px;"><icms-i18n:label name="bm_l_service_number"/>：</td>
								<td class="tabCon">
									<input type="text" id="srCode" name="srCode" class="textInput"/>
								</td>
								<td class="tabBt"><icms-i18n:label name="bm_l_app_system"/>：</td>
								<td class="tabCon">
									<icms-ui:dic id="appId" name="appId" showType="select" attr="class='selInput'"
											sql="select APP_ID as value, CNAME as name from APP_INFO where IS_ACTIVE = 'Y' and FATHER_ID = '0'" />
								</td>
								<td class="tabBt"><icms-i18n:label name="bm_l_data_center"/>：</td>
								<td class="tabCon">
									<icms-ui:dic id="datacenterId" name="datacenterId" showType="select" attr="class='selInput'"
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
									<icms-ui:dic id="srTypeMark" name="srTypeMark" showType="select" attr="class='selInput'"
									sql="select SR_TYPE_MARK as value, SR_TYPE_NAME as name from BM_SR_TYPE where IS_ACTIVE = 'Y' and SR_TYPE_CODE = 'L'" />
								</td>
								<td class="tabBt"></td>
								<td class="tabCon"></td>
							</tr>
							
			</table>
			</div>
		</div>
		<div class="searchBtn_right" style="width:30%; float:left" >
			<table height="12%" width="99%" align="center"> 
				<tr style=" height:52px;">
					<td width="30%" align="left">
						<a href="javascript:search()"  type="button" title='<icms-i18n:label name="common_btn_query"/>' class="btn" onclick="search();return false;" value='<icms-i18n:label name="common_btn_query"/>'>
							<span class="icon iconfont icon-icon-search"></span>
	  						<span>查询</span>
						</a>
						<button  id="btn_reset" type="reset" title='<icms-i18n:label name="common_btn_clear"/>' class="btnDel"  value='<icms-i18n:label name="common_btn_clear"/>' >
							<span class="icon iconfont icon-icon-clear1"></span>
	  						<span>清空</span>
						</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
  </form>
		</div>
		<div class="pageTableBg">
			<div class="panel clear" id="div_list1">
			<table id="gridTable"></table>
			<div id="gridPager"></div>
		</div>
		</div>
		
	</div>
</body>
</html>
