<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>云服务请求类型</title>
<%@ include file="/common/zTree_load.jsp"%>
<script type="text/javascript" src="${ctx}/pages/request/js/cloudRequesType.js"></script>
</head>

<body  class="main1">
	<div id="content" class="main">
		<div id="treeDiv" style="overflow: auto;min-height:400px;height:100%;width:29%;float:left;clear:right;border-right: solid #EEEEEE 1PX;">
			<input type="text" id="name" name="name" style="width:70%"/>
			<input type="button" class="btn"  id="selcet" name="selcet" value='<icms-i18n:label name="common_btn_save"/>' onclick="searchTreeByCname()"/>
			<ul id="treeRm" class="ztree"></ul>
		</div>
		<div id="rightTopDiv" style="width: 70%; height: 30px; float: left;"  >
				<div id="view_BmSrType_Div">
					<table class="pagelist">
						<tr>
							<th><icms-i18n:label name="bm_l_type_name"/></th>
							<td ><label id="lbl_BmSrType_srTypeName" style="font-size: 14px;"></label></td>
						</tr>
						<tr>
							<th><icms-i18n:label name="bm_l_type_standard"/></th>
							<td><label id="lbl_BmSrType_srTypeMark" style="font-size: 14px;"></label></td>
						</tr>
						<tr>
							<th><icms-i18n:label name="bm_l_describe"/></th>
							<td><label id="lbl_BmSrType_remark" style="font-size: 14px;"></label></td>
						</tr>
					</table>
					     <p class="btnbar"><input type="button"  class="btn" id="addBmSrType" name="addBmSrType" value='<icms-i18n:label name="common_btn_add_requestType"/>' onclick="addBmSrType('insert')" style="margin-right: 5px;" >		
					 <input type="button"  class="btn"  id="modifyBmSrType" name="modifyBmSrType" value='<icms-i18n:label name="common_btn_update_requestType"/>' onclick="modifyBmSrType('update')" style="margin-right: 5px;" >
					   <input type="button"   class="btn" id="deleteBmSrType" name="deleteBmSrType" value='<icms-i18n:label name="common_btn_delete_requestType"/>' onclick="deleteBmSrType()" style="margin-right: 5px;" ></p>
				</div>
				
				<div id="add_update_BmSrType_Div" style="display: none">
					<form action="" method="post" id="add_update_BmSrType_Form">
					<table class="pagelist">
						<tr>
							<td class="input_label"><icms-i18n:label name="bm_l_type_name"/><font color="red">*</font></td>
							<td><input type="text" id="srTypeName" name="srTypeName"
								style="width: 72%" /></td>
						</tr>
						<tr>
							<td class="input_label"><icms-i18n:label name="bm_l_type_standard"/><font color="red">*</font></td>
							<td><input type="text" id="srTypeMark" name="srTypeMark"
								style="width: 72%" /></td>
						</tr>
						<tr>
							<td class="input_label"><icms-i18n:label name="bm_l_type"/><font color="red">*</font></td>
							<td><icms-ui:dic id = "srTypeCode" name="srTypeCode"
									kind="REQUEST_TYPE" showType="select" attr="style='width: 72%;'" />
							</td>
						</tr>
						<tr>
							<td class="input_label"><icms-i18n:label name="bm_l_describe"/></td>
							<td><textarea id="remark" name="remark" rows="5" cols="40"></textarea></td>
						</tr>
					</table>
						<p class="btnbar">
							<input type="button"  class="btn"  id="cencelBmSrType" name="cencelBmSrType" value='<icms-i18n:label name="commom_btn_cancel"/>' onclick="closeView('add_update_BmSrType_Div')">
							<input type="button"  class="btn"  id="saveBmSrType" name="saveBmSrType" value='<icms-i18n:label name="common_btn_save"/>' onclick="saveBmSrTypeBtn()">
						</p>
					</form>
				</div>
				
		</div>
		<input type="hidden" id="parentId" name="parentId" value="">
		<input type="hidden" id="lbl_BmSrType_srTypeCode" name="lbl_BmSrType_srTypeCode" value="">
		<div id="div_tip"></div>
	</div>
	
</body>
</html>
