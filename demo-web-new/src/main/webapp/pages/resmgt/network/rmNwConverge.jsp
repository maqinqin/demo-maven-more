<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx}/pages/resmgt/network/js/rmNwConverge.js"></script>
<style type="text/css">
.pageFormContent{padding-bottom:0;}
#updateTab p{width:280px; padding:0;}
#updateTab p i{width:90px; text-align:left;}
#updateTab label{display:block; padding-left:100px;}
</style>
</head>
<body class="main1">
<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_netCon"></icms-i18n:label></div>
					<div class="WorkSmallBtBg">
						<small>
						 <icms-i18n:label name="bim_title_netDesc"></icms-i18n:label>
						</small>
					</div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:63%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
				
					<td class="tabBt" style="width:50px;"><icms-i18n:label name="bim_l_netConName"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input id="convergeNameInput" class="textInput" type="text">
<!-- 						<select id="convergeIdSel" class="selInput" style="width: 310px;"> -->
<!-- 							<option  id="" value="">请选择...</option> -->
<!-- 						</select> -->
					</td>
					<td class="tabBt"><icms-i18n:label name="com_l_dataCenter"></icms-i18n:label>：</td>
					<td class="tabCon">
						<select id="datacenterIdSel" class="selInput">
							<option  id="" value=""><icms-i18n:label name="com_l_choose"></icms-i18n:label></option>
						</select>
					</td>
					<td class="tabBt" style="width:20px;"></td>
					<td class="tabCon" ></td>
					
				</tr>
			</table>
		</div>
	</div>
	<div class="searchBtn_right" style="width:32%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td>
						<a href="javascript:search()" type="button" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' class="btn" onclick="search();return false;">
							<span class="icon iconfont icon-icon-search"></span>
							<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
						</a>
						<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" >
							<span class="icon iconfont icon-icon-clear1"></span>
							<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
						</button>
					<shiro:hasPermission name="converge:add">
							<a href="javascript:void(0)" title='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' class="btn" onclick="createData()" >
								<span class="icon iconfont icon-icon-add"></span>
								<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
							</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="converge:update"> 
			            <input type="hidden" id="updateFlag" name="updateFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="converge:del"> 
			            <input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
			        </shiro:hasPermission>
			        </td>
						</tr>
					</table>
	</div>
</div>
			</form>
		</div>
		<div class="pageTableBg">
			<div class="panel clear" id="gridTableDiv">
			<table id="gridTable" ></table>
					<div id="gridPager"></div>
		</div>	
		</div>
			
</div>
	
	<div id="updateDiv" style="display: none">
		<form action="" method="post" id="updateForm">
		<div  id="updateTab"  class="pageFormContent">
			<input type="hidden" id="method" name="method" />
			<input type="hidden" id="addIsActive" name="isActive" />
			<p>
				<i><font color="red">*</font><icms-i18n:label name="bim_l_netConName"></icms-i18n:label>:</i><input class="textInput" type="text" id="addConvergeName" name="convergeName" />
			</p>
			<p>
				<i><font color="red">*</font><icms-i18n:label name="com_l_dataCenter"></icms-i18n:label>:</i>
				<select id="addDatacenterIdSel" name="datacenterId" class="selInput"></select>
			</p>			
			<div class="nowrap right"></div>   
		</div>
			<div class="winbtnbar btnbar1" style="text-align:right; margin-bottom:10px; margin-right:39px; "><label id="sp_error_tip"></label>
			<input type="button" id="btnAddSp" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="closeView()" style="margin-right: 5px; margin-left:0;" />
			<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="submitForm()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;" />
			</div>
		</form>		
	</div> 
</body>
</html>