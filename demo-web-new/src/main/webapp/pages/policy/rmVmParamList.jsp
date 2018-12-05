<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx}/pages/policy/js/rmVmParamList.js"></script>

<script type="text/javascript" src="${ctx}/jquery/js/jquery-slider.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/slider.css"></link>
<style>
.pageFormContent i {width:90px;}
.current:link, .uc-radio .current:visited, .uc-radio .current:hover {background:url(../../pages/workflow/designer/images/btn_gray8Y.jpg) no-repeat left center;height:25px;cursor:pointer;color:#000;font-size:12px;	font-family:"微软雅黑";line-height:25px;text-align:center;border:none;}
.unit{ background:url(../../pages/workflow/designer/images/btn_gray8.jpg) no-repeat left center;height:25px;cursor:pointer;color:#000;font-size:12px;	font-family:"微软雅黑";line-height:25px;text-align:center;border:none;}
.unit:hover{background:url(../../pages/workflow/designer/images/btn_gray8Y.jpg) no-repeat left center;height:25px;cursor:pointer;color:#000;font-size:12px;font-family:"微软雅黑";line-height:25px;text-align:center;border:none;}
</style>
</head>
<body class="main1">
<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sa_title_vParam"></icms-i18n:label></div>
				<div class="WorkSmallBtBg">
					<small>
					 	<icms-i18n:label name="sa_title_vDescribe"></icms-i18n:label>
					</small>
				</div>
					
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left; padding-left:4px;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
					<td class="tabBt" style="width:95px;"><icms-i18n:label name="sa_l_paramObType"></icms-i18n:label>：</td>
					<td class="tabCon">
						<icms-ui:dic id="objectTypeSel" name="objectTypeSel" kind="CLOUD_OBJECT_TYPE_TYPE" showType="select" attr="class='selInput'" />
					</td>
					<td class="tabBt" style="width:70px;"><icms-i18n:label name="sa_l_paramOb"></icms-i18n:label>：</td>
					<td class="tabCon">
								<select id="objectIdSel" class="selInput">
									<option  id="" value=""><icms-i18n:label name="l_common_select"/>...</option>
								</select>
					</td>
					<td class="tabBt" style="width:70px;"><icms-i18n:label name="sa_l_paramSort"></icms-i18n:label>：</td>
					<td class="tabCon">
						<icms-ui:dic id="paramTypeSel" name="paramTypeSel" kind="CLOUD_PARAM_TYPE_TYPE" showType="select" attr="class='selInput'" />
					</td>
				</tr>
			</table>
			</div>
		</div>
		<div class="searchBtn_right" style="width:31%; float:left; padding-left:1%;" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
					<td  align="left" >
						<a href="javascript:search()" type="button" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' class="btn" onclick="search();return false;" >
							<span class="icon iconfont icon-icon-search"></span>
							<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
						</a>
						<!-- style="vertical-align:middle" -->
						
						<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="btn"></icms-i18n:label>' class="btnDel" >
							<span class="icon iconfont icon-icon-clear1"></span>
							<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
						</button>
						<!-- style="vertical-align:middle; text-indent:-999px;" -->
						
					<shiro:hasPermission name="rmvmparam:create">
							<a href="javascript:void(0)"  type="button" title='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' class="btn" onclick="createData()" >
								<span class="icon iconfont icon-icon-add"></span>
							   <span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
							</a>
							<!-- style="vertical-align:middle" -->
					</shiro:hasPermission>
					<shiro:hasPermission name="rmvmparam:update"> 
			            <input type="hidden" id="updateFlag" name="updateFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="rmvmparam:delete"> 
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
			<input type="hidden" id=paramId name="paramId" />
			<input type="hidden" id="isActive" name="isActive" />
			<input type="hidden" id="method" name="method" />
			<p style="width: 550px">
				<i><icms-i18n:label name="sa_l_paramObType"></icms-i18n:label>:</i>
				<icms-ui:dic id="objectType" name="objectType" cascade="initObjectTypeSelect" kind="CLOUD_OBJECT_TYPE_TYPE"  showType="flatSelect" attr="style='width: 80px;'" click="initObjectTypeSelect(this.getAttribute('value'));"/>
			</p>
			<p  style="width: 550px">
				<i><icms-i18n:label name="sa_l_paramOb"></icms-i18n:label>:</i>
				<select id="objectId" name="objectId" class="selInput" style="width: 200x;"></select>
				<%-- <td  width="260px" id="cdpTdId">	<icms-ui:dic name="objectId" id="objectId"  sql="SELECT CONCAT(POOL.POOL_NAME,'-',CDP_NAME) name,CDP.ID value,'1' kind FROM RM_CDP CDP,RM_RES_POOL POOL WHERE CDP.RES_POOL_ID=POOL.ID AND POOL.POOL_TYPE='H'  AND CDP.IS_ACTIVE='Y' AND POOL.IS_ACTIVE='Y'" showType="select" attr="style='width: 140px;'"/>	</td>
				<td  width="260px" id="poolTdId">	<icms-ui:dic name="objectId" id="objectId"  sql="SELECT POOL.POOL_NAME name,POOL.ID value,'1' kind FROM RM_RES_POOL POOL WHERE POOL.POOL_TYPE='H'  AND POOL.IS_ACTIVE='Y'" showType="select" attr="style='width: 140px;'"/>	</td> --%>
			</p>
			<p  style="width: 550px; margin-bottom:0;">
				<i style="display:block;" ><icms-i18n:label name="sa_l_paramSort"></icms-i18n:label>:</i>
				<div style="margin-bottom:20px;"><icms-ui:dic id="paramType" name="paramType" kind="CLOUD_PARAM_TYPE_TYPE"  showType="flatSelect" attr="style='width: 170px;'"  /></div>
			</p>
			<p  style="width: 550px">
				<i  ><font color="red">*</font><icms-i18n:label name="param_value"></icms-i18n:label>:</i><input class="textInput" type="text" id="value" name="value" />
			</p>
			<!--p style="width:400px">
                    <i>存储空间:</i>
				    <span>
				        <span id="uc-duration" class="uc-slider" style="width: 200px;">
				            <span class="range">
				                        <span class="sliderBlock" value="250" style="width: 50px;">
				                               <span>250GB</span>
				                        </span><span class="sliderBlock"  value="500" style="width: 50px;">
				                                <span>500GB</span>
				                        </span><span class="sliderBlock"  value="1000" style="width: 100px;">
				                               <span>1000GB</span>
				                        </span>  
				                <span class="sliderContainer">
				                    <span class="sliderCurrent">
				                        <span class="sliderUnit">
				                               <span>250GB</span>
				                        </span><span class="sliderUnit">
				                                <span>500GB</span>
				                        </span><span class="sliderUnit">
				                               <span>1000GB</span>
				                        </span>
				                    </span>
				                </span>
				                 
				                <span class="bar"></span>                                             
				                <a href="javascript:;" target="_self" rel="nofollow" class="sliderDrag" hidefocus>
				                    <%--<b></b>
				                    <b></b>
				                    <b></b>--%>
				                </a>
				            </span>
				           
				        </span>
				    </span>
				    <input class="uc-input" id="storage" name="storage" type="text" value="5" />
			</p-->
			<div class="nowrap right"></div>   
		</div>
		<div class="winbtnbar btnbar1" style="margin-right:20px;"><label id="sp_error_tip"></label>
		<input type="button" id="btnAddSp" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="closeView()" />
		<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3 " onclick="saveOrUpdateBtn()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' />
		</div>
		</form>		
	</div>
<!-- 存储空间 slider js -->
<script type="text/javascript">
   
var F = new Object;
F.DurationConfig = {};
F.DurationConfig.data = [];
var vm_duration_config = [{"max":1000,"min":5,"step":5,"unit":"GB"}];
(function(){
    
    var i = 0, l = 0, _i = 0, _l = 0,
        step = 0,
        min = 0,
        max = 0,
        type;
    
        for(_i = vm_duration_config[i].min - 0, _l = vm_duration_config[i].max - 0; _i <= _l; _i = _i + vm_duration_config[i].step){
            min = max;
            max = _i;
            step = max - min;
            F.DurationConfig.data.push({
                'unit': step,
                'min': min,
                'max': max
            });
        }

    F.DurationConfig.defaultValue = 5;
})();
 
var t = new UC.slider("#uc-duration",{
    data: F.DurationConfig.data,
    defaultValue: F.DurationConfig.defaultValue,
    min: vm_duration_config[0].min,
	max: vm_duration_config[0].max,
	step: vm_duration_config[0].step,
    bindInput: '.uc-input'
})

</script>   
</body>
</html>