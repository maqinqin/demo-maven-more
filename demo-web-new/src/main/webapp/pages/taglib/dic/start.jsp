<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ include file="/common/taglibs.jsp"%>
<head>
<script type="text/javascript">
//扁平化选择按钮点击事件
function selectObj(obj,id){ 
     var parentNode = obj.parentNode;
     var childNodes = parentNode.childNodes;
	 for(var i=0;i<childNodes.length;i++)
	 {
	  if(childNodes[i].tagName == "A"){
	     childNodes[i].className="unit"; 
	  }
	 }
	 obj.className="unit current";
	 
	 if(typeof(id) != "undefined" && id != null && id.indexOf(".") != -1){
         id = id.replace(".","\\.");
    }
	$('#'+id).val(obj.getAttribute('value')); 
}
</script>
<style>

</style>
</head>
<c:choose>
	<c:when test="${dt_dic.onlyShow}">
		<c:forEach var="element" items="${dt_list}">
			<c:if test="${element['CODE']==dt_dic.value}">
				<input type="hidden" name="${dt_dic.name}" value="${dt_dic.value}" />
    			<span id="${dt_dic.name}_detail">${element['DETAIL']}</span>
			</c:if>
		</c:forEach>
	</c:when>
	<c:when test="${dt_dic.showType!=null}">
		<c:choose>
			<c:when test="${dt_dic.showType=='select'}" >
				<select name="${dt_dic.name }" ${dt_dic.attr } id="${dt_dic.id}">
					<option value="" ${dt_dic.value==null?"selected":""}><icms-i18n:label name="l_common_select"/>...</option>
					<c:forEach var="element" items="${dt_list}">
						<option value="${element['CODE']}" ${element['CODE']==dt_dic.value?"selected":""}>${element['DETAIL']}</option>
					</c:forEach>
				</select>
			</c:when>
			<%-- 扁平化选择 --%>
			<c:when test="${dt_dic.showType=='flatSelect'}" >
				<span id="${dt_dic.id}_span" class="uc-radio" >
				    <c:forEach var="element" items="${dt_list}" varStatus="status">
				          <c:choose>
				           <c:when test="${status.index==0}" >
				              <a href="javascript:;" id="selectObj" onclick="selectObj(this,'${dt_dic.id}');${dt_dic.click}" target="_self" value="${element['CODE']}" class="unit current" ${dt_dic.attr } >${element['DETAIL']}</a>
				              <input type="hidden" id="${dt_dic.id}" name="${dt_dic.name}" value="${element['CODE']}"/>
				              <script type="text/javascript">
				                $('#${dt_dic.id}').val("${element['CODE']}");
				              </script>
				              <c:if test="${dt_dic.cascade !=''}">
					              <script type="text/javascript">
					                eval("${dt_dic.cascade}"+"("+"'${element['CODE']}'"+")");
								  </script>
							  </c:if>
				           </c:when>
				           <c:otherwise>
					          <a href="javascript:;"  id="selectObj" onclick="selectObj(this,'${dt_dic.id}');${dt_dic.click}" target="_self" value="${element['CODE']}" class="unit" ${dt_dic.attr } >${element['DETAIL']}</a>
					        
					       </c:otherwise>
					      </c:choose>
					</c:forEach>
				</span>
				<span class="clear"></span>
			</c:when>
			<c:otherwise>
				<input type="hidden" class="synValue"  id="${dt_dic.name}" name="${dt_dic.name}" value="${dt_dic.value}" />
				<c:forEach var="element" items="${dt_list}">
					<input class="synValue" type="${dt_dic.showType }" name="_${dt_dic.name}" value="${element['CODE']}"  ${dt_dic.attr} />${element['DETAIL'] }
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		
	</c:otherwise>
</c:choose>