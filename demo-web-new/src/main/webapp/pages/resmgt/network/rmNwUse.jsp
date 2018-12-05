<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx}/pages/resmgt/network/js/rmNwUse.js"></script>
<style type="text/css">
#updateTab label{display:block; padding-left:140px;}

#updateUseRelDiv i {text-align:left; padding-right:3px;}
#updateUseRelDiv p{width:600px; margin-bottom:5px; margin-left:18px;  }
#updateUseRelDiv p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#updateUseRelDiv label{padding-left:100px;}
#updateDiv label{padding-left:100px;}
</style>
</head>
<body class="main1">
<div class="content-main clear">
		<div class="page-header">
			<h1>
			<div class="WorkBtBg"><icms-i18n:label name="sa_title_ip"></icms-i18n:label></div>
				
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
					<td class="tabBt" style="width:55px;"><icms-i18n:label name="sa_l_ipPurCode"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input id="useCodeInput" class="textInput" type="text">
					</td>
					<td class="tabBt" style="width:90px;"><icms-i18n:label name="sa_l_ipPurName"></icms-i18n:label>：</td>
					<td class="tabCon">
					    <input id="useNameInput" class="textInput" type="text">
					</td>
					<td class="tabBt"></td>
					<td class="tabCon"></td>
				</tr>
				<tr>
					
				</tr>
			</table>
		</div>
	</div>
	<div class="searchBtn_right" style="width:31%; float:left; padding-left:2%;" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td  align="right"  width="25%" >
						<a href="javascript:search()" type="button" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' class="btn" onclick="search();return false;" >
							<span class="icon iconfont icon-icon-search"></span>
							<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
						</a><!-- style="vertical-align:middle" -->
						<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" >
							<span class="icon iconfont icon-icon-clear1"></span>
							<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
						</button>
						<!-- style="vertical-align:middle; text-indent:-999px;" -->
					<shiro:hasPermission name="use:add">
						<a href="javascript:void(0)" type="button" title='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' class="btn" onclick="createData()" >
							<span class="icon iconfont icon-icon-add"></span>
							<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
						</a><!-- style="vertical-align:middle" -->
					</shiro:hasPermission>
					<shiro:hasPermission name="use:update"> 
			            <input type="hidden" id="updateFlag" name="updateFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="use:del"> 
			            <input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="use:rel"> 
			        	<input type="hidden" id="useRelFlag" name="useRelFlag" value="1" />
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
		<form action="" method="post" id="updateUseForm">
		<div id="updateTab"  class="pageFormContent">
			<input type="hidden" id="method" name="method" />
			<input type="hidden" id="addIsActive" name="isActive" />
			<p style="width:300px; margin-bottom:0;">
				<i><font color="red">*</font><icms-i18n:label name="sa_l_ipPurCode"></icms-i18n:label>:</i><input class="textInput" type="text" id="addUseCode" name="useCode" />
			</p>
			<p style="width:300px;  margin-bottom:0;">
				<i><font color="red">*</font><icms-i18n:label name="sa_l_ipPurName"></icms-i18n:label>:</i>
				<input class="textInput" type="text" id="addUseName" name="useName" />
			</p>			
			<div class="nowrap right"></div>   
		</div>
		<div class="winbtnbar btnbar1" style="text-align:right; margin-right:29px; margin-bottom:15px;"><!-- <label id="sp_error_tip"></label> -->
		<input type="button" id="btnAddSp" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="closeView()" style="margin-right: 26px;margin-left: 0px;" />
		<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="submitForm()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;" />
		</div>
		</form>		
	</div> 
	
	<div id="rmNwUseRel" style="display: none;" > 
		<table width="100%">
			<tr width="100%">
				<td align="right"></td>
			</tr>
			<tr >
				<td align="right" >
				<shiro:hasPermission name="useRel:add">
						<a href="javascript:void(0)" type="button" class="btn" onclick="createRmNwUseRelData()"	title='<icms-i18n:label name="com_btn_create"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' style="margin-left:930px; color:#fff;">
							<span class="icon iconfont icon-icon-add"></span>
	  						<span>添加</span>
						</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="useRel:update"> 
						<input type="hidden" id="updateFlag2" name="updateFlag2" value="1" />
				</shiro:hasPermission> 
				<shiro:hasPermission name="useRel:del"> 
						<input type="hidden" id="deleteFlag2" name="deleteFlag2" value="1" />
				</shiro:hasPermission> 
				</td>
			</tr>
			<tr width="100%" height="10px">
			</tr>
		</table>
		
		<div class="panel clear" id="rmNwUseRelGridDiv">
			<input type="hidden" id="useCodeMark" />
			<input type="hidden" id="useIdMark" />
			<table id="rmNwUseRelGridTable">
			</table>
			<div id="rmNwUseRelGridPager"></div>
		</div>
	</div>
	
	<div id="updateUseRelDiv" style="display: none">
		<form action="" method="post" id="useRelForm">
		<div id="useRelTab"  class="pageFormContent">
			<input type="hidden" id="methodUseRel" name="methodUseRel" />
			<input type="hidden" id="addIsActive" name="isActive" />
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="sa_l_platform"></icms-i18n:label>:</i>
				<select id="platformSel" name="platformId" class="selInput"></select>
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_vmType"></icms-i18n:label>:</i>
				<select id="virtualTypeSel" name="virtualTypeId" class="selInput"></select>
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_hostType"></icms-i18n:label>:</i>
				<select id="hostTypeSel" name="hostTypeId" class="selInput"></select>
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="sa_l_relaCode"></icms-i18n:label>:</i>
				<input class="textInput" type="text" id="useRelCode" name="useRelCode" readonly="readonly"/>
			</span>
			<div class="nowrap right"></div>   
		</div>
		<div class="winbtnbar btnbar1" style="text-align:right; width:588px; margin-bottom:20px;"><label id="sp_error_tip"></label>
		<input type="button" id="btnAddSp" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="closeUseRelView()" style="margin-right: 5px; margin-left:0;" />
		<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="submitUseRelForm()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;" /></div>
		</form>		
	</div> 
</body>
</html>