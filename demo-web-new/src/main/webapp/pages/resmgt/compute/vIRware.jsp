<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="icms-ui" uri="/WEB-INF/tld/icms-ui.tld"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/vIRware.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/compute/js/vmController.js"></script>
<%-- <%@ include file="/pages/resmgt/compute/briefBase.jsp"%> --%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/count.css"></link>
<div class="content-main" id="content_vm_div">
	<form class="Form_tabMargin">
	<table width="95%" align="center">
		<tr>
			<td width="6.5%"><icms-i18n:label name="bm_l_vmName"/>：</td>
			<td width="19%" align="left">
				<input id="vmNameInput" class="text_Input" type="text"/>
			</td>
<!-- 			<td width="9%" >IP地址：</td> -->
<!-- 			<td width="19%" align="left"> -->
<!-- 				<input id="vmIpAdrInput" class="text_Input" type="text"/> -->
<!-- 			</td> -->
			<td  align="right"  width="25%">
				<a href="javascript:void(0)" type="button" value="<icms-i18n:label name="common_btn_query"/>" title='<icms-i18n:label name="common_btn_query"/>' class="btn" onclick="search_hvm();" style="vertical-align:middle">
					<span class="icon iconfont icon-icon-search"></span>
	  				<span>查询</span>
				</a>
				<button class="btnDel" type="reset" value="<icms-i18n:label name="common_btn_clear"/>" title='<icms-i18n:label name="common_btn_clear"/>'>
					<span class="icon iconfont icon-icon-clear1"></span>
	  				<span>清除</span>
				</button>
	        </td>
		</tr>
	</table>
	</form>
	<div id="gridTable_virware_div"  style="margin-top: 10px;border:1px solid #d5d5d5; width:95%; margin:0 auto;">
		    <input type="hidden" id="vmTypeCode" name="vmTypeCode"/>
		     <input type="hidden" id="platFormCode" name="platFormCode"/>
		     <input type="hidden" id="vmId" name="vmId"/>
		<table id="gridTable_virware"></table>
		<table id="gridPager_virware"></table>
	</div>
</div>
 	<!-- 新建快照信息 -->
	<div id="snapshotAddDiv" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="snapshotAddForm">
		    <input type="hidden" id="snapshot_vmId" name="snapshot_vmId"/>
			<input type="hidden" id="snapshot_vmName" name="snapshot_vmName"/>
               <p style="width:330px; padding-left:80px;">
				<i><font color="red">*</font><icms-i18n:label name="res_l_snapshot_Name"/>：</i><input type="text" id="snapshot_Name" name="snapshot_Name" class="textInput" style="width:188px;"></input>
				</p>
				<p style="width:330px; padding-left:80px;" id="memoryAndSilence">
				<i><input name="snapshot_type" type="checkbox" value="memory" id="memory"/><icms-i18n:label name="res_l_memory_snapshot"/></i>
				<i><input name="snapshot_type" type="checkbox" value="silence" id="silence"/><icms-i18n:label name="res_l_silence_snapshot"/></i>
				</p>
				<p style="width:330px; padding-left:80px;">
				<i><icms-i18n:label name="res_l_comput_remark"/>：</i><textarea id="snapshot_remark" rows="2" name="snapshot_remark" style="width:200px;"></textarea>
				</p>
			<p class="winbtnbar btnbar1" style="width:395px;">
				<label id="sp_error_tip"></label>
				<input type="button" id="btnAddSp" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="commom_btn_cancel"/>' title='<icms-i18n:label name="commom_btn_cancel"/>' onclick="cancelSnapshotDiv()" style="margin-left: 5px;" />
				<input type="button" id="btn_add_sp" value='<icms-i18n:label name="common_btn_save"/>' class="btn btn_dd2 btn_dd3" onclick="saveSnapshotBtn()" title='<icms-i18n:label name="common_btn_save"/>' style="margin-right: 5px;margin-left: 5px;" />
			</p>
		</form>
	</div>

	<div id="snapshotMagDiv" style="display: none">
			<input type="hidden" id="ss_vmId" name="ss_vmId"/>
			<input type="hidden" id="ss_vmName" name="ss_vmName"/>
			<div id="snapshotMagButtonDiv" class="panel clear" style="margin-left: 13px;margin-top: -5px;">
			<table id="snapshotGridTable"></table>
			<table id="snapshotgridPager"></table>
			</div>
		</div>
	
	
	
	
	
	
	<!-- 挂载卷 -->
	<div id="mountVolumeDiv" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="mountVolumeForm">
            <p style="width: 80%" id='yesOrNoId'>
			<icms-i18n:label name="res_l_yesOrNo"/>：<icms-ui:dic id="yesOrNo" name="yesOrNo" kind="YES_OR_NO" showType="select" attr="style='width:150px;'class='selInput'"/>
			</p>
			 <p style="width: 80%" id='newBuildId'>
			<icms-i18n:label name="res_l_newBuild"/>：<icms-ui:dic id="newBuild" name="newBuild" kind="YES_OR_NO" showType="select" attr="style='width:150px;'class='selInput'"/>
			</p>
			
			 <p style="width: 80%" id='volumeTypeP'>
			<%-- 卷类型：<icms-ui:dic id="_volumeType" name="_volumeType" showType="select" attr="class='selInput'"
 					sql="SELECT rt.VOLUME_TYPE AS value,rt.VOLUME_TYPE AS name 
 					FROM RM_VOLUME_TYPE rt LEFT JOIN RM_VM_MANAGE_SERVER rvms ON rt.VM_MS_ID = 
 					rvms.ID LEFT JOIN RM_DATACENTER rd ON rvms.DATACENTER_ID = rd.ID LEFT JOIN RM_AVAILABILITY_ZONE a 
 					ON a.VM_MS_ID = rvms.ID WHERE rvms.IS_ACTIVE = 'Y' AND rd.IS_ACTIVE = 'Y' AND a.IS_ACTIVE = 'Y' 
 					" /> --%>
 				卷类型：<select id="_volumeType" name="_volumeType" class="textInput" style="height:24px; line-height:24px; width:150px;" >
		  	       <option value="">请选择</option>
		  	    </select>
			</p>
			
			<p style="width: 80%" id='volumeSizeId'><icms-i18n:label name="res_l_volumeSize"/>：<input id="volumeSize" class="text_Input" type="text"/></p>
			<div class="panel clear" id="mountGridTable_div">
				<table id="mountGridTable"></table>
				<!-- <div id="mountGridPager"></div> -->
			</div>
			<p class="winbtnbar" style="text-align: right;">
				<label id="sp_error_tip"></label>
				<input type="button" class="btn_gray" value='<icms-i18n:label name="commom_btn_cancel"/>' title='<icms-i18n:label name="commom_btn_cancel"/>' onclick="closeViews('mountVolumeDiv')" style="margin-left: 5px;" />
				<input type="button" id="btn_add_mountVolume" value='<icms-i18n:label name="common_btn_save"/>' class="btn_gray" onclick="saveMountVolumeBtn()" title='<icms-i18n:label name="common_btn_save"/>' style="margin-right: 5px;margin-left: 5px;" />
			</p>
		</form>
	</div>
	
	<div id="unMountVolumeDiv" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="unMountVolumeForm">
		<div class="panel clear" id="unMountGridTable_div">
				<table id="unMountGridTable"></table>
			</div>
		</form>
	</div>
	<!-- 性能监控 -->
	<div id="monitorDiv" style="display:none;background-color:white;"  class="pageFormContent">
   		<div id="monitorChart" style="height:199px"></div> 
   		<p id="lastCpu"></p>
   		<p id="lastMem"></p>
   		<p id="lastIO"></p>
	</div>
	