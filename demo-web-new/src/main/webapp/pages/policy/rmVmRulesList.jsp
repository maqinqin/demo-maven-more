<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx}/pages/policy/js/rmVmRulesList.js"></script>

</head>
<body class="main1">
	<div  class="content-main clear">
	<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sa_title_vRule"></icms-i18n:label></div>
				<div class="WorkSmallBtBg">
					<small>
					 <icms-i18n:label name="sa_title_vdescribe"></icms-i18n:label>
					</small>
				</div>	
					
			</h1>
		</div>
	<!-- <div id="topDiv" style="width: 100%">
		<table width="100%" >
					<tr width="100%" height="15px;">
						<td width="30%" >&emsp;</td><td width="30%" >&emsp;</td><td width="20%" >&emsp;</td><td width="20%" >&emsp;&emsp;</td>
					</tr>
					<tr width="100%" >
						<td width="30%" >&emsp;</td><td width="30%" >&emsp;</td><td width="20%" >&emsp;</td><td width="20%" align="center" >&emsp;&emsp;<input type="button" value="新建" onclick="createData()" />&emsp;<input type="button" value="删除" onclick="deleteData()" /></td>
					</tr>
		</table>
		</div> -->
		<shiro:hasPermission name="rmvmrules:update"> 
	        <input type="hidden" id="updateFlag" name="updateFlag" value="1" />
	    </shiro:hasPermission>
	    
	<div class="pageTableBg" style="padding-top:20px;">
		<div class="panel clear"  id="gridTableDiv">
			<table id="gridTable"></table>
			<div id="gridPager"></div>
	</div>
	</div>
	
	</div>
	<div id="updateDiv"  style="display: none;">
		<form action="" method="post" id="updateForm">
		<table  id="updateTab"  border="0" class="pageFormContent" style="float:left;">
			<input type="hidden" id=rulesId name="rulesId" />
			<input type="hidden" id="isActive" name="isActive" />
			<input type="hidden" id="method" name="method" />
			<tr>
				<th width="120px" ><font color="red">*</font>排序对象:</th>
				<td width="120px">
				<icms-ui:dic id="sortObject" name="sortObject" kind="CLOUD_SORT_OBJECT_TYPE"  showType="select" attr="style='width: 140px;'"/>
				</td>
			</tr>
			<tr>
				<th width="120px" ><font color="red">*</font>排序方式:</th><td width="120px">
				<icms-ui:dic id="sortType" name="sortType" kind="SORT_TYPE" showType="select" attr="style='width: 140px;'"/>
				</td>
			</tr>
		</table>
		<p class="btnbar"><input type="button" class="btn" onclick="saveOrUpdateBtn()" value="保存" style="margin-right: 5px;margin-left: 5px;" /><input type="button"  class="btn" value="取消" onclick="closeView()" style="margin-right: 5px;" /></p>
		</form>
		
		
	</div>
</body>
</html>
