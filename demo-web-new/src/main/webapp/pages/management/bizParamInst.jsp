<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<html>
<head>
<title>云平台管理系统</title>
<script type="text/javascript" src="${ctx}/pages/management/js/bizParamInst.js"></script>
<style type="text/css">
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="biz_para_head"/></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:40px; ">
					<td class="tabBt" style="width:100px;"><icms-i18n:label name="biz_para_device_name"/>：</td>
					<td class="tabCon">
						<input type="text" id="sedeviceName" name="sedeviceName" class="textInput" />
					</td>
					<td class="tabBt" style="width:100px;">paramKey：</td>
					<td class="tabCon">
						<input type="text" id="paramKey" name="paramKey" class="textInput" />
					</td>
					<td class="tabBt" style="width:100px;"><icms-i18n:label name="biz_para_inst_name"/>：</td>
					<td class="tabCon">
						<input type="text" id="instanceName" name="instanceName" class="textInput" />
					</td>
				</tr>
				<tr style=" height:40px; ">
				 <td class="tabBt" style="width:120px;"><icms-i18n:label name="biz_para_inst_id"/>：</td>
					<td class="tabCon">
						<input type="text" id="flowInstId" name="flowInstId" class="textInput" />
					</td> 
				<!--  <td class="tabBt" style="width:100px;">节点ID：</td>
					<td class="tabCon">
						<input type="text" id="nodeId" name="nodeId" class="textInput" />
					</td> -->
					
				</tr>
			</table>
		</div>
	</div>
	<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:40px;">
							<td   width="10%" align="center" colspan="2">
					<!-- <input class="Btn_Add" onclick="addBizParamInst()" title="新增流程参数" style="vertical-align:middle" type="button"> -->
					<a href="javascript:void(0)" type="button" class="btn" onclick="searchList()" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' style="vertical-align:middle;">
						<span class="icon iconfont icon-icon-search"></span>
						<span>查询</span>
					</a>
					<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" onclick="clearAll()" >
						<span class="icon iconfont icon-icon-clear1"></span>
						<span>清空</span>
					</button>
					<shiro:hasPermission name="bizParamInst:update">
								<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
						</shiro:hasPermission>
					<shiro:hasPermission name="bizParamInst:add">
								<input type="hidden" id="addFlag" name="addFlag" value="1" />
						</shiro:hasPermission>
					</td>
						</tr>
				   </table>
   </div>
</div>
		</div>
		<div class="pageTableBg">
		<div class="panel clear" id="gridMain">
			<table id="gridTable" ></table>
			<div id="gridPager"></div>
		</div>
		</div>		
	   </div>
	<!-- 更改流程参数 -->	
	<div id="updateValueDiv"   style="display: none;">
	<form action="" method="post" id="updateValueForm">
		<div class="pageFormContent">
		<input type="hidden" name="hiddenId" id="hiddenId">
		<p>
			<i><icms-i18n:label name="biz_para_device_name"/>：</i>	
			<input type="text" id="deviceName" name="deviceName" class="textInput"  disabled="disabled"/>
		</p>
		<p>
			<i>paramKey：</i>
			<input type="text" id="upkey" name="upkey" class="textInput"  disabled="disabled"/>
		</p>
		<p>
			<i>paramValue：</i>
			<input type="text" id="upvalue" name="upvalue" class="textInput" />
		</p>
		<div class="winbtnbar btnbar1" style="width:270px;"> 
			 <input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeViews('updateValueDiv')" style=" margin-right: 5px;margin-left: 0px;" />
			 <input type="button" class="btn btn_dd2 btn_dd3" onclick="saveValue()" title='<icms-i18n:label name=""></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style=" margin-right: 5px;margin-left: 5px;"/>
		</div>
		</div>
	</form>
</div>


	<!--新增流程参数  -->
	<div id="addBizParamInstDiv"   style="display: none; height: 180px;">
	<form action="" method="post" id="addBizParamInstForm">
		<div class="pageFormContent">
		<input type="hidden" name="hidNodeId" id="hidNodeId">
		<input type="hidden" name="hidFlowInstId" id="hidFlowInstId">
		<input type="hidden" name="hidDeviceId" id="hidDeviceId">
		<p>
			<i><icms-i18n:label name="biz_para_inst_name"/>：</i>	
			<input type="text" id="addInstanceName" name="addInstanceName" class="textInput"  disabled="disabled"/>
		</p>
		<p>
			<i><font color="red">*</font>paramKey：</i>
			<input type="text" id="addkey" name="addkey" class="textInput"  />
		</p>
		<p>
			<i>paramValue：</i>
			<input type="text" id="addValue" name="addValue" class="textInput" />
		</p>
		<div class="winbtnbar btnbar1" style="width:270px;"> 
			 <input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeViews('addBizParamInstDiv')" style=" margin-right: 5px;margin-left: 0px;" />
			 <input type="button" class="btn btn_dd2 btn_dd3" onclick="addValueValidate()" title='<icms-i18n:label name=""></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style=" margin-right: 5px;margin-left: 5px;"/>
		</div>
		</div>
	</form>
</div>

</body>
</html>
	