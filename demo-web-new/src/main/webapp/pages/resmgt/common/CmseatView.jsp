<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<script type="text/javascript" src="${ctx}/plugins/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/common/js/CmseatView.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/common/js/CmseatAllHostCanRelevanceInfo.js"></script>
<title>位置信息管理</title>
<style type="text/css">
html,body{height:100%}
</style>
</head>
<body  class="main1">
<input type="hidden" id="hid_icon" /> 
<div id="div_tip"></div>
<div id="content" class="content-main clear">
	<div class="page-header">
			<h1>
				系统管理
					<small>
						位置信息管理
					</small>
			</h1>
		</div>


  <div id="treeDiv" style="overflow: auto;min-height:406px;height:100%;width:29%;background-color:white;float:left;clear:right;border-right: solid #EEEEEE 1PX;">
	  <ul id="treeRm" class="ztree" ></ul>
  </div>
  <div id="rightContentDiv" style="width:70%;overflow: auto;min-height:406px;height:100%;float:left;clear:right; margin:0 auto;text-align:center;">
  	
  	<div id="xd_div" style="display:none;font-size:18px;">
  		<table border="0" cellpadding="0" cellspacing="0" class="pagelist" style="float:left;width:40%" >
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/datacenter.png" /></th><td><label style="font-size: 14px; ">数据中心图标</label></td>
			</tr>
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/room.png" /></th><td><label style="font-size: 14px;">机房图标</label></td>
			</tr>
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/cabinet.png" /></th><td><label style="font-size: 14px;">机柜图标</label></td>
			</tr>			
			<tr>
			<th><img src="../../css/zTreeStyle/img/icons/u.png" /></th><td><label style="font-size: 14px;">U位图标</label></td>
			</tr>
		</table>
  	</div>
  	
  	<div id="dc_div" style="display: none; top:136px;">
  		<table id="tab1" border="0" class="report" width="97%">
			<tr>
			<th>数据中心名称：</th><td><label id="lab_dcname"></label></td>
			</tr>
			<tr>
			<th>英文编码：</th><td><label id="lab_dcEname"></label></td>
			</tr>
			<tr>
			<th>数据中心地址：</th><td><label id="lab_address"></label></td>
			</tr>
			<tr>
			<th>是否激活：</th><td><label id="lab_isActive"></label></td>
			</tr>
  		</table>
   		<p class="btnbar"><input type="button" value="新建机房"  class="btn" onclick="saveOrupdateRoomInfo('save')" style="margin-right: 5px;" /></p>
 	</div>
  	
	<!-- 展示机房详细信息 -->
  	<div id="room_div" style="display: none">
  		<table id="tab" border="0" class="report" width="97%">
			<tr>
			<th>机房名称：</th><td><label id="room_seatName"></label></td>
			</tr>
			<tr>
			<th>机房编码：</th><td><label id="room_seatCode"></label></td>
			</tr>
			<tr>
			<th>位置描述：</th><td><label id="room_description"></label></td>
			</tr>
			<tr>
			<th>创建人：</th><td><label id="room_createUser"></label></td>
			</tr>
			<tr>
			<th>创建时间：</th><td><label id="room_createDatetime"></label></td>
			</tr>
			<tr>
			<th>更新人：</th><td><label id="room_updateUser" /></td>
			</tr>	
			<tr>
			<th>更新时间：</th><td><label id="room_updateDatetime" /></td>
			</tr>
		</table>
  		<p class="btnbar"><input type="button" value="新建机柜" class="btn" onclick="saveOrupdateCabinetInfo('save')" style="margin-right: 5px;" /><input type="button" class="btn"  value="修改机房" onclick="saveOrupdateRoomInfo('update')" style="margin-right: 5px;" /><input type="button" class="btn"  value="删除机房" onclick="delRoomInfo()" style="margin-right: 5px;" /></p>
  	</div>
  	
	<!-- 展示机柜详细信息 -->
	<div id="cabinet_div" style="display: none">
  		<table border="0" cellpadding="0" cellspacing="0" class="report" width="97%">
			<tr>
			<th>机柜名称：</th><td><label id="cabinet_seatName"></label></td>
			</tr>
			<tr>
			<th>机柜编码：</th><td><label id="cabinet_seatCode"></label></td>
			</tr>
			<tr>
			<th>位置描述：</th><td><label id="cabinet_description"></label></td>
			</tr>
			<tr>
			<th>创建人：</th><td><label id="cabinet_createUser"></label></td>
			</tr>
			<tr>
			<th>创建时间：</th><td><label id="cabinet_createDatetime"></label></td>
			</tr>
			<tr>
			<th>更新人：</th><td><label id="cabinet_updateUser" /></td>
			</tr>	
			<tr>
			<th>更新时间：</th><td><label id="cabinet_updateDatetime" /></td>
			</tr>
		</table>
 	  	<p class="btnbar"><input type="button" value="新建U位" class="btn" onclick="saveOrupdateUInfo('save')" style="margin-right: 5px;" /><input type="button" class="btn" value="修改机柜" onclick="saveOrupdateCabinetInfo('update')" style="margin-right: 5px;" /><input type="button" class="btn"  value="删除机柜" onclick="delCabinetInfo()" style="margin-right: 5px;" /></p>
   	</div>
  	
	<!-- 展示U位详细信息 -->
  	<div id="u_div" style="display: none">
  		<table border="0"  class="report" width="97%">
			<tr>
			<th>U位名称：</th><td><label id="u_seatName"></label></td>
			</tr>
			<tr>
			<th>U位编码：</th><td><label id="u_seatCode"></label></td>
			</tr>
			<tr>
			<th>U高：</th><td><label id="u_uheight"></label></td>
			</tr>
			<th>U位状态：</th><td><label id="u_status"></label></td>
			</tr>
			<tr>
			<th>位置描述：</th><td><label id="u_description"></label></td>
			</tr>
			<tr>
			<th>创建人：</th><td><label id="u_createUser"></label></td>
			</tr>
			<tr>
			<th>创建时间：</th><td><label id="u_createDatetime"></label></td>
			</tr>
			<tr>
			<th>更新人：</th><td><label id="u_updateUser" /></td>
			</tr>	
			<tr>
			<th>更新时间：</th><td><label id="u_updateDatetime" /></td>
			</tr>
  		</table>
		<p class="btnbar"><input type="button" class="btn" value="关联设备" onclick="initCanRelevanceInfo()" style="margin-right: 5px;" /><input type="button" class="btn"  value="修改U位" onclick="saveOrupdateUInfo('update')" style="margin-right: 5px;" /><input type="button" class="btn"  value="删除U位" onclick="delUInfo()" style="margin-right: 5px;" /></p>
  	</div>
  	
  	<div id="add_room_div" style="display:none;"  class="pageFormContent">
  		<form id="room_form" action="" method="post">
				<p>
				<i><font color="red">*</font>机房名称：</i><input type="text" id="room_seatName_inout" name="room_seatName_inout" class="textInput" />
				</p>
				<p>
				<i><font color="red">*</font>机房编码：</i><input type="text" id="room_seatCode_inout" name="room_seatCode_inout" class="textInput" />
				</p>
				<p>
				<i>位置描述：</i><textarea id="room_description_inout" rows="3" align="left" name="room_description_inout"  class="textInput"></textarea>
				</p>
	  		<p class="btnbar"> <input type="button" class="btn_ok" id="btn_add_room" onclick="saveOrUpdateBtnRoom('save')" title="新建" style="margin-right: 5px;margin-left: 5px;" /><input type="button" class="btn_edit" id="btn_mod_room" onclick="saveOrUpdateBtnRoom('update')" title="修改" style="margin-right: 5px;margin-left: 5px;" /><input type="button"  class="btn_cancel"  id="btnAddRoom" title="取消" onclick="closeView('add_room_div')" style="margin-right: 5px;" /></p>
  		</form>
  	</div>
  	
  	<div id="add_cabinet_div" style="display:none;"  class="pageFormContent">
  		<form id="cabinet_form" action="" method="post">
				<p>
				<i><font color="red">*</font>机柜名称：</i><input type="text" id="cabinet_seatName_inout" name="cabinet_seatName_inout" class="textInput" />
				</p>
				<p>
				<i><font color="red">*</font>机柜编码：</i><input type="text" id="cabinet_seatCode_inout" name="cabinet_seatCode_inout" class="textInput" />
				</p>
				<p>
				<i>位置描述：</i><textarea id="cabinet_description_inout" rows="3" align="left" name="cabinet_description_inout" class="textInput"></textarea>
				</p>
	  		<p class="btnbar"> <input type="button" class="btn_ok" id="btn_add_cabinet" onclick="saveOrUpdateBtnCabinet('save')" title="新建" style="margin-right: 5px;margin-left: 5px;" /><input type="button" class="btn_edit" id="btn_mod_cabinet" onclick="saveOrUpdateBtnCabinet('update')" title="修改" style="margin-right: 5px;margin-left: 5px;" /><input type="button"  class="btn_cancel"  id="btnAddCabinet" title="取消" onclick="closeView('add_cabinet_div')" style="margin-right: 5px;" /></p>
  		</form>
  	</div>
  	
  	<div id="add_u_div" style="display:none;"  class="pageFormContent">
  		<form id="u_form" action="" method="post">
				<p>
				<i><font color="red">*</font>U位名称：</i><input type="text" id="u_seatName_inout" name="u_seatName_inout" class="textInput" />
				</p>
				<p>
				<i><font color="red">*</font>U位编码：</i><input type="text" id="u_seatCode_inout" name="u_seatCode_inout"  class="textInput" />
				</p>
				<i><font color="red">*</font>U高：</i><input type="text" id="u_uheight_inout" name="u_uheight_inout"  class="textInput" />
				</p>
				<p>
				<i>位置描述：</i><textarea id="u_description_inout" rows="3" align="left" name="u_description_inout"  class="textInput"></textarea>
				</p>
	  		<p class="btnbar" style="text-align:right;"> <input type="button" class="btn_gray" id="btn_add_u" onclick="saveOrUpdateBtnU('save')" title="新建" style="margin-right: 5px;margin-left: 0px;" /><input type="button" class="btn_gray" id="btn_mod_u" onclick="saveOrUpdateBtnU('update')" title="修改" style="margin-right: 5px;margin-left: 0px;" /><input type="button"  class="btn_gray"  id="btnAddSp" title="取消" onclick="closeView('add_u_div')" style="margin-right: 5px; margin-left:0;" /></p>
  		</form>
  	</div>
  	
	<div id="deviceDiv" class="pageFormContent">		
  		<table id="tab" border="0">
			<tr>
				<td  align="right">物理机名称：</td>
				<td><input type="text" id="deviceName" class="textInput" /> </td>
				<td  align="right">序列号：</td>
				<td><input type="text" id="sn"  class="textInput" /></td>
				<td align="left">
				<table style="margin-top: 5px;">
				<td><input type="button" title="查询" onclick="search();" class="btn_search" style="margin-right: 5px;margin-left: 5px;margin-bottom: 5px;"></input></td>
				<td><input type="button" title="关联" onclick="relevance();" class="btn_link" style="margin-right: 5px;margin-bottom: 5px;"></input></td>
				<td><input type="button" title="返回" onclick="closegDiv()" class="btn_cancel" style="margin-right: 5px;margin-bottom: 5px;"/></td>
				</table>
				</td>
			</tr>
  		</table>
	 	<table id="deviceTable"></table>
  		<table id="devicePager"></table>
	</div>

 </div>
</div>
</body>
</html>