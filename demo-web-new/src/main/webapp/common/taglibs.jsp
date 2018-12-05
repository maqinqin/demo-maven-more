<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="icms-ui" uri="/WEB-INF/tld/icms-ui.tld"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script>
	var ctx = '${ctx}';
	var _language = '<%=request.getLocale()%>';
	pagin = null;
	function convertLocationSearchParamsToObject(locationParams) {
		var search = locationParams.replace(/^\s+/,'').replace(/\s+$/,'').match(/([^?#]*)(#.*)?$/);//提取location.search中'?'后面的部分
		if(!search){
			return {};
		}
		var searchStr = search[1];
		var searchHash = searchStr.split('&');

		var ret = {};
		for(var i = 0, len = searchHash.length; i < len; i++){ //这里可以调用each方法
			var pair = searchHash[i];
			if((pair = pair.split('='))[0]){
				var key = decodeURIComponent(pair.shift());
				var value = pair.length > 1 ? pair.join('=') : pair[0];
				console.log()
				if(value != undefined){
					value = decodeURIComponent(value);
				}
				if(key in ret){
					if(ret[key].constructor != Array){
						ret[key] = [ret[key]];
					}
					ret[key].push(value);
				}else{
					ret[key] = value;
				}
			}
		}
		return ret;
	}

	function getPaginationParamFromURLSearchPart() {
		if (window.location.toString().indexOf("backForward=true") != -1) {
			pagin = convertLocationSearchParamsToObject(location.search);
		}
	}

	function pushPaginationParamToHistory(data, page, rows) {
		var loc = window.location.toString().substring(0, window.location.toString().indexOf("backForward=true") == -1 ?
				window.location.toString().length : window.location.toString().indexOf("backForward=true"));
		var searchHead = (loc.indexOf("\\?") == -1 ? "?" : "&");

		var prePagination = searchHead + "backForward=true&page=" + eval("data." + page) + "&rows=" + eval("data." + rows);
		prePagination = prePagination.replace("\\?\\&","\\?");
		window.history.pushState(null, "", (loc + prePagination).toString().replace(/[?]+/, "?"));
	}
	getPaginationParamFromURLSearchPart()
</script>