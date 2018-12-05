<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ taglib prefix="icms-ui" uri="/WEB-INF/tld/icms-ui.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${ctx}/pages/service/js/softWareVerParamList.js"></script>
<script type="text/javascript">
	
	
</script>
</head>
<body class="main1">
	<div class="main">
		<div id="topDiv" style="width: 100%">
			<table width="100%" >
						<tr width="100%" height="15px;">
							<td width="30%" >&emsp;</td><td width="30%" >&emsp;</td><td width="20%" >&emsp;</td><td width="20%" >&emsp;&emsp;</td>
						</tr>
						<tr width="100%" >
							<td width="30%" >&emsp;</td><td width="30%" >&emsp;</td><td width="20%" >&emsp;</td><td width="20%" align="center" >&emsp;&emsp;<input type="button" value='<icms-i18n:label name="common_btn_add"/>' onclick="createData()" />&emsp;<input type="button" value="删除" onclick="deleteData()" /></td>
						</tr>
			</table>
			</div>
	
		<div class="gridMain">
			<table id="gridTable"></table>
			<div id="gridPager"></div>
		</div>
	</div>
	<div id="updateDiv" class="cmxform"   style="display: none;background-color: #DFDFDF">
		<form action="" method="post" id="updateForm">
		<table  id="updateTab">
			<input type="hidden" id="softwareVerId" name="softwareVerId" />
			<input type="hidden" id="isActive" name="isActive" />
			<input type="text" style="display: none" id="method" name="method" />
			<input type="text" style="display: none" id="softwareId" name="softwareId" value="${cloudSoftware.softwareId }" />
			
			<tr>
				<td class="input_label" width="120px" align="right">软件版本名称:</td><td width="260px"><input type="text" id="verName" name="verName" /></td>
			</tr>
			<tr>
				<td class="input_label" width="120px" align="right">软件版本描述:</td><td  ><input type="text" id="verRemark" name="verRemark" /></td>
			</tr>
			<tr>
				<td  colspan="2" align="center"><input type="button"  onclick="saveOrUpdateBtn()" value="保存"/>&emsp;&emsp;&emsp;<input type="button" onclick="closeView()" value="取消"/></td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
