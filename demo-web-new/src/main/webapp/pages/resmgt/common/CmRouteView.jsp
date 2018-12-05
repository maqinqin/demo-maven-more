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
<script type="text/javascript" src="${ctx}/pages/resmgt/common/js/CmRouteView.js"></script>
<style type="text/css">
#add_update_Route_Div i {text-align:left; padding-right:3px;}
#add_update_Route_Div p{width:600px; margin-bottom:5px; margin-left:18px;  }
#add_update_Route_Div p .updateDiv_list{display:inline-block; width:296px; float:left;height: 55px;}
#add_update_Route_Div label{display:block; padding-left:110px; width:196px;}
</style>
</head>
<body  onload="initRoute()" class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_router"></icms-i18n:label></div>
				<div class="WorkSmallBtBg">	
					<small>
					    <icms-i18n:label name="bim_title_Rdescribe"></icms-i18n:label>
					</small>
				</div>	
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:62%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
				    <td class="tabBt" style="width:55px;"><icms-i18n:label name="bim_l_routName"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="route_name" name="route_name" class="textInput"/>
					</td>
				    <td class="tabBt"><icms-i18n:label name="com_l_ipAddr"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="route_ip" name="route_ip" class="textInput"/>
					</td>
					  <td class="tabBt" style="width:40px;"></td>
					  <td class="tabCon"></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="searchBtn_right" style="width:32%; float:left; padding-left:2%"  >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td >
					  <a href="javascript:search()" type="button" class="btn" onclick="search();return false;" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' >
					  	<span class="icon iconfont icon-icon-search"></span>
						<span>查询</span>
					  </a>
					 <button title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" onclick="cancel();" >
					 	<span class="icon iconfont icon-icon-clear1"></span>
						<span>清空</span>
					 </button><!-- style="text-indent:-999px;" -->
					 <shiro:hasPermission name="route_add">
					    <a href="javascript:void(0)" class="btn"  onclick="addRoute();" title='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>'>
					    	<span class="icon iconfont icon-icon-add"></span>
							<span>添加</span>
					    </a>
			        </shiro:hasPermission>
			           <shiro:hasPermission name="route_delete">
			            <input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
			           </shiro:hasPermission>
			            <shiro:hasPermission name="route_update">
			           <input type="hidden" id="updateFlag" name="updateFlag" value="1" />
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
		
		<div id="add_update_Route_Div" style="display: none" class="pageFormContent">
			<form action="" method="post" id="add_update_Route_Form">
			<div  id="updateRoute"  >
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_blogsDataCent"></icms-i18n:label>:</i>
				<icms-ui:dic id = "route_dataCenter_id" name="route_dataCenter_id"
									sql="SELECT DISTINCT r.ID AS value, r.DATACENTER_CNAME AS name,c.DATACENTER_ID AS kind FROM RM_DATACENTER r LEFT JOIN
			CM_ROUTE c ON r.ID=c.DATACENTER_ID WHERE r.IS_ACTIVE='Y' ;"
									showType="select" attr="class='selInput'" />
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_routName"></icms-i18n:label>:</i>
				<input class="textInput" type="text" id="name" name="name"/>
			 
			 </span>
			 
			 <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_ipAddr"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="ip" name="ip" />
			 </span>
			 <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_subnetMask"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="mask" name="mask" />
			 </span>
			
			<%-- <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_defauGate"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="gateway" name="gateway" />
			 </span> --%>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="com_l_remark"></icms-i18n:label>:</i>
				<textarea style="width:155px; height:40px; box-shadow: 1px 1px 2px #ededed; background-color: #f5f5f5;border: 1px solid #d5d5d5;"" id="remark" name="remark"></textarea>
			</span>
			</p>
			 <p class="winbtnbar btnbar1" style="text-align:right; margin-bottom:20px; width:570px; padding-right:30px; ">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="cencelRoute" name="cencelRoute" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>'  style="margin-right: 5px; margin-left:0;"  onclick="closeView('add_update_Route_Div')">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="saveRoute" name="saveRoute" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;"  onclick="saveRouteBtn()">
				</p>
				</div>
				</form>
			</div>
		
	
	<input type="hidden" id="id" name="id">

</body>
</html>
