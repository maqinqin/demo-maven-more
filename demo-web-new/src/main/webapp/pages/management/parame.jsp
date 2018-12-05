<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>云平台管理系统</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<script type="text/javascript" src="${ctx }/pages/management/js/parameQry.js"></script>
<style>

#addParameterDiv i {width:80px; text-align:left; padding-right:3px;}
#addParameterDiv p{width:600px; margin-bottom:5px; margin-left:18px;  }
#addParameterDiv p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#addParameterDiv label{display:block; padding-left:100px;}
</style>
</head>
<body onload="initTest();" class="main1">
<div>
   <div class="page-header"> 
     <h1>
      <div class="WorkBtBg"><icms-i18n:label name="sys_title_param"></icms-i18n:label></div>
     </h1>
   </div>
  <div id="topDiv" class="pageFormContent searchBg">
   <form action="">
      <div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
           <td class="tabBt" style="width:55px;"><icms-i18n:label name="com_l_paramName"></icms-i18n:label>：</td>
           <td class="tabCon"><input id="qryParamName"  name="qryParamName" class="textInput"></td>
           <td class="tabBt"></td>
           <td class="tabCon"></td>
           <td class="tabBt"></td>
           <td class="tabCon"></td>
        </tr>
      </table>
     </div>
    </div>
    <div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td>
             <a href="javascript:search()" type="button" class="btn" onclick="search();return false;"  title='<icms-i18n:label name="sys_btn_queryParam"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' style="vertical-align:middle">
             	<span class="icon iconfont icon-icon-search"></span>
				<span>查询</span>
             </a>
             <button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel"  onclick="clearAll()">
             	<span class="icon iconfont icon-icon-clear1"></span>
				<span>清空</span>
             </button>
             <a href="javascript:void(0)" type="button" class="btn"    onclick="addParameter()" title='<icms-i18n:label name="sys_btn_createParam"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' style="vertical-align:middle">
             	<span class="icon iconfont icon-icon-add"></span>
				<span>添加</span>
             </a>
           </td>
						</tr>
					</table>
	</div>
   </div>
   </form>
   
   </div>
   <div class="pageTableBg">
   	 <div class="panel clear" id="gridTableDiv">
			<div>
			<table id="gridTable" ></table>
			<div id="gridPager"></div></div>
		</div>	
   </div>
  	

</div>
<div id="addParameterDiv"  style="display:none;background-color: white;" class="pageFormContent">
<form action=""  method="post"   id="addParameterForm">

	<p>
	<span class="updateDiv_list">
    	<i><font color="red">*</font><icms-i18n:label name="param_name"></icms-i18n:label>：</i><input type="text"  id="paramName" name="paramName"  class="textInput"/>
    </span>
    
	<span class="updateDiv_list">
		<i><icms-i18n:label name="sys_l_isEncrypt"></icms-i18n:label>：</i>
		<input  type="radio" id="isEncryption" name="isEncryption" class="radioItem"  value="N" checked="checked"/><icms-i18n:label name="NNNS" />						
		<input  type="radio" id="isEncryption" name="isEncryption" class="radioItem"  value="Y"/><icms-i18n:label name="YYYS" />
	</span>
	</p>
	
	<p>
	<span class="updateDiv_list">
		<i><font color="red">*</font><icms-i18n:label name="param_value"></icms-i18n:label>：</i> <!-- <input type="text"	id="paramValue" name="paramValue" class="textInput" /> -->
		<textarea id="paramValue" name="paramValue" style="width:169px; height:40px; box-shadow: 1px 1px 2px #ededed; background-color: #f5f5f5;border: 1px solid #d5d5d5;"></textarea>
	</span>		 
	
	<span class="updateDiv_list">
		<i><icms-i18n:label name="com_l_remark"></icms-i18n:label>：</i>  <!-- <input  type="text" id="" name="" class="textInput" value="" style="width: 300px,height: 200px;"/> -->
		<textarea id="remark" name="remark" style="width:179px; height:40px; box-shadow: 1px 1px 2px #ededed; background-color: #f5f5f5;border: 1px solid #d5d5d5;"></textarea>			
	</span>
	</p>
	
		
	<p class="winbtnbar btnbar1" style="text-align:right; margin-bottom:20px; width:564px;">
				
				<input type="button" class="btn btn_dd2 btn_dd3" id="cancelParameter"
					name="cancelParameter" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('addParameterDiv')" style="margin-left:0; margin-right:5px;">
				<input type="button" class="btn btn_dd2 btn_dd3" id="saveParameter"
					name="saveParameter" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="updateOrSave()" style="margin-left:0; margin-right:5px;">
					<input type="hidden" id="updateFlag" name="updateFlag" value="1"/>
					<input type="hidden" id="deleteFlag" value="1"/>
					
	</p>
</form>


</div>
<input type="hidden" id="paramId" name="paramId">


</body> 
</html>