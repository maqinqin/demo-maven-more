<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title><icms-i18n:label name="bm_l_service_delivery_info"/></title>
		<script type="text/javascript">
        var baseUrl="<%=path%>";
        var ctx = "${ctx}";
        var srType = "${bmSrVo.srTypeMark}";
        var srId = "${bmSrVo.srId}";
        var actionType = "detail";
        var todoId = "${todoId}";

       </script>
       <script type="text/javascript" src="${ctx}/pages/request/js/cloudRequestParam.js"></script>
		<script type="text/javascript"
			src="<%=path%>/pages/request/js/cloudRequestAffirm.js"></script>
	   <style type="text/css">
	     .ip_table{
	        border-collapse:collapse;
			width:100%;
			border: 1px solid #abc6d1;
		  }
	     .ip_table td{
			border: 1px solid #abc6d1;
		  }
		  .widthStyle{
			width:100%;
		  }
	   </style>
	</head>
	<body class="main1">
		<div class="main">
			<jsp:include page="includeInfo.jsp" />
			<div class="panel" style="width: 96%;margin-top: 20px">
				<h1>
					<icms-i18n:label name="bm_l_service_delivery_info"/>（<label id="titlePage"></label>） 
				</h1>
			</div>
			<div id="gridMain" class="gridMain" style="width: 96%;">
				<table id="gridTable" style="width: 96%;"></table>
				<table id="gridPager"></table>
			</div>
			<table border="0" cellpadding="0" cellspacing="0" width="96%"
				align="center" style="margin-top: 5px">
				<tr>
					<td align="center" colspan="2" style="text-align:right; margin-bottom:20px;" class="btnbar1">
						<c:if test="${bmSrVo.srStatusCode == 'REQUEST_WAIT_CLOSE' && todoId !='' && todoId !=null  }">
							<input type="button" class="btn btn_dd2 btn_dd3" id="submit_btn" onclick="closeRequestSr();" value='<icms-i18n:label name="common_btn_closeRequest"/>' />
						</c:if>
						<input type="button" class="btn btn_dd2 btn_dd3" id="submit_btn" onclick="javascript:history.go(-1);" value='<icms-i18n:label name="common_btn_return"/>' />
					</td>
				</tr>
			</table>
			
			<div id="dialog-userParm" title="用户输入参数" class="form_main_div"
				style="display: none">
				<table id="srattTable"></table>
				<div id="uspageNav" style="padding-right: 10px;"></div>
			</div>
			<div id="catalog_detail_div" class="form_main_div"
				style="display: none; overflow-y: true" title="服务配置详细信息">
				<fieldset>
					<legend>
						<icms-i18n:label name="bm_l_service_directory"/>
					</legend>
					<form id="catalogdetailform" action="" method="post">
						<table class="form_table" " align="left" cellspacing="0"
							cellpadding="0" border="0">
							<tr>
								<td class="form_win_label">
									<icms-i18n:label name="bm_l_service_directoryName"/>
								</td>
								<td>
									<input type="text" id="serviceName" name="serviceName"
										class="form_input" style="width: 455px" />
								</td>
							</tr>
							<tr>
								<td class="form_win_label">
									<icms-i18n:label name="bm_t_business_function"/>
								</td>
								<td>
									<textarea rows="3" cols="75" name="funcRemark" id="funcRemark"
										class="form_area"></textarea>
								</td>
							</tr>
							<tr>
								<td class="form_win_label">
									<icms-i18n:label name="bm_t_nonbusiness_function"/>
								</td>
								<td>
									<textarea rows="3" cols="75" name="unfuncRemark"
										id="unfuncRemark" class="form_area"></textarea>
								</td>
							</tr>
							<tr>
								<td class="form_win_label">
									<icms-i18n:label name="bm_t_vmNum"/>
								</td>
								<td>
									<input type="text" id="vmBase" name="vmBase" class="form_input"
										style="width: 100px" />
								</td>
							</tr>
						</table>
					</form>
				</fieldset>
				<br></br>
				<fieldset>
					<legend>
						<icms-i18n:label name="bm_l_softWare_config"/>
					</legend>
					<div style="text-align: center;">
						<table align="center" id="software-config" width="95%">
							<thead>
								<tr>
									<th width="50%">
										<icms-i18n:label name="bm_l_softWare_name"/>
									</th>
									<th width="30%">
									<icms-i18n:label name="bm_l_softWare_type"/>
									</th>
									<th width="20%">
										<icms-i18n:label name="bm_l_softWare_ver"/>
									</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</fieldset>
				<br></br>
				<fieldset>
					<legend>
					<icms-i18n:label name="bm_l_server_config"/>
					</legend>
					<form id="catalogdetailform" action="" method="post">
						<table class="form_table" " align="left" cellspacing="0"
							cellpadding="0" border="0">
							<tr>
								<td class="form_win_label">
									<icms-i18n:label name="res_l_datastore_name"/>
								</td>
								<td colspan="3">
									<input type="text" id="serviceSetName" name="serviceSetName"
										class="form_input" style="width: 580px" />
								</td>
							</tr>
							<tr>
								<td class="form_win_label">
									CPU
								</td>
								<td>
									<input type="text" id="cpu" name="cpu" class="form_input" />
								</td>
								<td class="form_win_label"></td>
								<td></td>
							</tr>
							<tr>
								<td class="form_win_label">
									<icms-i18n:label name="bm_l_device_mem"/>(G)
								</td>
								<td>
									<input type="text" id="mem" name="mem" class="form_input" />
								</td>
								<td class="form_win_label">
									<icms-i18n:label name="bm_t_systemDisk"/>(G)
								</td>
								<td>
									<input type="text" id="sysDisk" name="sysDisk"
										class="form_input" />
								</td>
							</tr>
							<tr>
								<td class="form_win_label">
									<icms-i18n:label name="bm_t_dataDisk"/>(G)
								</td>
								<td>
									<input type="text" id="dataDiskCode" name="dataDiskCode"
										class="form_input" />
								</td>
								<td class="form_win_label">
									<icms-i18n:label name="bm_t_archiveDisk"/>(G)
								</td>
								<td>
									<input type="text" id="archiveDiskCode" name="archiveDiskCode"
										class="form_input" />
								</td>
							</tr>
							<tr>
								<td class="form_win_label">
									<icms-i18n:label name="bm_t_networkCard_Number"/>
								</td>
								<td>
									<input type="text" id="network" name="network"
										class="form_input" />
								</td>
								<td class="form_win_label">
									<icms-i18n:label name="bm_t_networkCard_type"/>
								</td>
								<td>
									<input type="text" id="networkTypeCode" name="networkTypeCode"
										class="form_input" />
								</td>
							</tr>
						</table>
					</form>
				</fieldset>
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
						<td align="center" style="height: 50px">
							<input type="button" id="cancel_attr_btn" value='<icms-i18n:label name="commom_btn_cancel"/>' class="btn" />
							<input type="button" id="save_attr_btn" value='<icms-i18n:label name="common_btn_save"/>' class="btn" /> 
							<input type="button" id="save_service" value='<icms-i18n:label name="common_btn_ensure"/>' class="btn" /> 
							<input type="button" id="pre_attr" value="<icms-i18n:label name="common_btn_previous_step"/>" class="btn" /> 
							<br>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>