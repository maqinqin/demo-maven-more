<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<html>
	<head>

		<title>详细信息</title>
		<script type="text/javascript">
         var baseUrl="${ctx}";
         var srType = "${bmSrVo.srTypeMark}";
         var srStatusCode = "${bmSrVo.srStatusCode}";
         var srId = "${bmSrVo.srId}";
         var todoId = "${todoId}";
         var actionType = "detail";
         var attrRrinfoIds = "${bmSrVo.attrRrinfoIds}";
         function history(){
        	 window.location.href=document.referrer;  
         }
       </script>
        <script type='text/javascript' src='${ctx}/scripts/json.js'></script>
        <script type="text/javascript" src="${ctx}/pages/request/js/cloudRequestParam.js"></script>
        <script type="text/javascript" src="${ctx}/pages/request/js/cloudRequestDetail.js"></script>

	</head>
	<body class="main1">
		<div class="main">
			<jsp:include page="includeInfo.jsp" />
			<div class="pageTableBg">
				<div id="title1" class="panel panel_long" style="margin-top:20px;">
					<h1>
						<icms-i18n:label name="bm_l_resApplication_info"/>（<label id="titlePage1"></label>）
					</h1>
				</div>
				
				<div id="gridMain1" class="gridMain" style="width:96%;">
					<table id="gridTable1"></table>
					<table id="gridPager1"></table>
				</div>
			</div>
			<div class="pageTableBg">
				<div id="title2" class="panel panel_long" style="display:none; margin-top:20px;">
				<h1>
					<icms-i18n:label name="bm_l_pre_distribution_info"/>（<label id="titlePage2"></label>）
				</h1>
				</div>
				<div id="gridMain2" class="gridMain" style="width:96%; display:none;" >
					<table id="gridTable2"></table>
					<table id="gridPager2"></table>
				</div>
			</div>
			<div class="pageTableBg">
				<div id="title3" class="panel panel_long" style="margin-top: 20px;display:none;">
				<h1>
					<icms-i18n:label name="bm_l_delivery_info"/>（<label id="titlePage3"></label>）
				</h1>
			</div>
			<div id="gridMain3" class="gridMain" style="width:96%;display:none;">
				<table id="gridTable3"></table>
				<table id="gridPager3"></table>
			</div>
			</div>
			
			<table border="0" cellpadding="0" cellspacing="0" width="96%" align="center" style="margin-top: 5px">
				<tr>
					<td align="center" colspan="2" style="text-align:right; margin-bottom:20px;" class="btnbar1">
						<input type="button" class="btn btn_dd2 btn_dd3" id="submit_btn" onclick="history();" value='<icms-i18n:label name="common_btn_return"/>' />
					</td>
				</tr>
			</table>
		</div>
		<!-- 属性列表 -->
		<div id="select_attr_div" class="div_center" style="display:none">
				<input id="duId_attr" name="duId_attr" type="hidden"
					class="textInput" /> </br>
					<div style="width: 500px; height: 330px" >
						<table >
							<tr>
								<td style="width: 100px"></td>
								<td align="center" style="height: 40px">
									<div style="margin: 0 30px 20px 35px;border: 1px solid #a6caca;">
										<table id="attrTable" ></table>
									</div>
								</td>
							</tr>
						</table>
					</div>
				<table border="0" cellpadding="0" cellspacing="0" width="100%"
					align="center">
					<tr>
						<td align="right" style="height: 50px" class="btnbar1">
						<input type="button" id="cancel_attr_btn" value='<icms-i18n:label name="commom_btn_cancel"/>' class="btn btn_dd2 btn_dd3" /> <br>
						<input type="button" id="save_attr_btn" value='<icms-i18n:label name="common_btn_save"/>' class="btn btn_dd2 btn_dd3" /> 
						<input type="button" id="save_service" value='<icms-i18n:label name="common_btn_ensure"/>' class="btn btn_dd2 btn_dd3" /> 
						<input type="button" id="pre_attr" value='<icms-i18n:label name="common_btn_previous_step"/>' class="btn btn_dd2 btn_dd3" /> 
						</td>
					</tr>
				</table>
			</div>
			<div id="showCategoryInfoDetail" style="width: 96%;">
				<table id="gridTablePriceApprovalDetail"></table>
				<!-- <table id="gridPagerPriceApproval"></table> -->
			</div>
	</body>
</html>
