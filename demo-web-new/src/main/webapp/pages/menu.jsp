<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.git.cloud.sys.model.po.SysUserPo"%>
<%@ page import="org.apache.shiro.SecurityUtils"%>
<%
	String path = request.getContextPath();
%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>左边菜单导航</title>
<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css"></link>
<script>

//加载菜单数据;
$(function () { 
   var url = "<%=path%>/sys/menu/showSystemMenu.do";
    $.ajax({
        async: true,
        cache: false,
        type: 'POST',
        dataType: 'json',
        url: url,
        error: function () {
        	alert("请求失败", null, "red");
        },
        success: function (data) {
            createMenu(data);
        }
    });
});
/**
 * 创建左边导航菜单;
 */
function createMenu(data){
     var openflag = false;
     var openUrl = '';
	 var htmlStr = "<ul class=\"menu1\">";
	 $.each(data, function(i, menuPo) {
	     if(menuPo.menuUrl!=''){
	       if(i==0){
	          htmlStr=htmlStr+"<li onclick=\"goto(this,'<%=path%>"+menuPo.menuUrl+"?menuName="+menuPo.menuName+"')\" class=\"current1\"><a><span><i style=\"background:url(../"+menuPo.imageUrl+") no-repeat; background-size:90% 90%;\"></i>"+menuPo.menuName+"</span></a><ul></ul></li>";
	       }else{
	          htmlStr=htmlStr+"<li  onclick=\"goto(this,'<%=path%>"+menuPo.menuUrl+"?menuName="+menuPo.menuName+"')\"><a><span><i style=\"background:url(../"+menuPo.imageUrl+") no-repeat; background-size:90% 90%;\"></i>"+menuPo.menuName+"</span></a><ul></ul></li>";
	       }
	       
	       if(openUrl == ''){
	          openUrl = '<%=path%>'+menuPo.menuUrl;
	       }
	       
	     }else{
	        htmlStr=htmlStr+"<li class=\"submenu\"><a><span><i style=\"background:url(../"+menuPo.imageUrl+") no-repeat; background-size:90% 90%;\"></i>"+menuPo.menuName+"<em></em><ul></ul></span></a>";
	        htmlStr = htmlStr + "<ul class=\"menu2\">";
	        var len = menuPo.childMenu.length;
	        
	        $.each(menuPo.childMenu, function(k, childMenu) {
	          
	          if(openUrl == ''){
	              openUrl = '<%=path%>'+childMenu.menuUrl;
	          }
	          var curl = childMenu.menuUrl;
	          if(k != len-1){
	        	 
	        	  if(curl.indexOf('http') > -1){
	        		  htmlStr = htmlStr + "<li  onclick=\"goto(this,'"+childMenu.menuUrl+"?menuName="+menuPo.menuName+"&childMenuName="+childMenu.menuName+"')\"><a><i class=\"menu2-circle\" ></i>"+childMenu.menuName+"</a></li>";
	        	  }else{
		              htmlStr = htmlStr + "<li  onclick=\"goto(this,'<%=path%>"+childMenu.menuUrl+"?menuName="+menuPo.menuName+"&childMenuName="+childMenu.menuName+"')\"><a><i class=\"menu2-circle\" ></i>"+childMenu.menuName+"</a></li>";
	        	  }
	          }else{
	        	  if(curl.indexOf('http') > -1){
	        		  htmlStr = htmlStr + "<li id='last_li"+childMenu.id+"' onclick=\"goto(this,'"+childMenu.menuUrl+"?menuName="+menuPo.menuName+"&childMenuName="+childMenu.menuName+"')\"><a><i class=\"menu2-circle\" ></i>"+childMenu.menuName+"</a></li>";
	        	  }else{
	        		  htmlStr = htmlStr + "<li id='last_li"+childMenu.id+"' onclick=\"goto(this,'<%=path%>"+childMenu.menuUrl+"?menuName="+menuPo.menuName+"&childMenuName="+childMenu.menuName+"')\"><a><i class=\"menu2-circle\" ></i>"+childMenu.menuName+"</a></li>";
	        	  }
	        	  
	          }
			  
		    });
			htmlStr = htmlStr+"</ul></li>";
	     }
	 });
	 htmlStr = htmlStr+"</ul>";
	 document.getElementById("autoMenu").innerHTML = htmlStr;
	 
	 if(openUrl !=''){
        parent.frames["main"].location.href = openUrl;
	 }
	 //菜单事件绑定;
	addShowSubMenuEvent();
}


 function addShowSubMenuEvent(){
	    $('.submenu > a').click(function(e)
			{
				e.preventDefault();
				var submenu = $(this).siblings('ul');
				var li = $(this).parents('li');
				var submenus = $('.side li.submenu ul');
				var submenus_parents = $('.side li.submenu');
				if(li.hasClass('open'))
				{
					if(($(window).width() > 768) || ($(window).width() < 479)) {
						submenu.slideUp();
					} else {
						submenu.fadeOut(250);
					}
					li.removeClass('open');
				} else 
				{
					if(($(window).width() > 768) || ($(window).width() < 479)) {
						submenus.slideUp();			
						submenu.slideDown();
					} else {
						submenus.fadeOut(250);			
						submenu.fadeIn(250);
					}
					submenus_parents.removeClass('open');		
					li.addClass('open');
				}
			});
			
			$(".menu1 > li").each(function(i){
				$(this).click(function(){
					var _li = $('.menu1 > li');
					_li.removeClass('current1');
					var isSelectSub = false;
					$(this).find("li").each(function(i,n){
					     var obj = $(n);
					     isSelectSub = obj.hasClass("cur");
					     if (isSelectSub) {
					    	 return false;
					     }
					});
					var isOpen = $(this).hasClass("open");
					//alert(isOpen + "-" + isSelectSub);
					if(! isOpen || (isOpen && !isSelectSub)) {
						if(!this.classList.contains('submenu')==true){
							$(this).addClass('current1');
					 	}
					}
				});
			});
		
		    var last_id;
			$(".menu2 > li").each(function(i){
				$(this).click(function(){
					var _li = $('.menu2 > li');
					_li.removeClass('cur');
					/* if((this.id == '' || (this.id).indexOf("last_li") == -1 || this.id != last_id) && typeof(last_id)!='undefined'){
					   document.getElementById(last_id).style.background = "url(\"../images/menu/menu2_libgList.jpg\") no-repeat;";
					} */
					if(this.id != '' && (this.id).indexOf("last_li") != -1){
						$(this).addClass('cur');
					   this.style.backgroundColor="";
					   last_id = this.id;
					}else{
						$(this).addClass('cur');
						if(document.getElementById(last_id)!=null){
					   document.getElementById(last_id).style.backgroundImage = "";
						}
					}
				});
			});
	}


function goto(obj,url){
   var str = $('.menu2 > li');
   var urlStr = url.indexOf("&");
   if(urlStr == -1){
   	 str.removeClass('cur');
     var lastId;
	 $(".menu2 > li").each(function(i){
		if(this.id != '' && (this.id).indexOf("last_li") != -1){
			lastId = this.id;
			document.getElementById(lastId).style.backgroundImage = "";
		}
	 });
   }
   parent.frames["main"].location.href = encodeURI(url);
}
</script>
</head>
<!-- #3A99C7 -->
<!-- wmy，左边菜单的颜色 -->
<body style="background:#22222C;height:100%;overflow-x:hidden;">
	 <div class="side" id="autoMenu" >
    </div>
    <!-- div><a href="#" title="折叠" id="controller" onClick="javascript:ExpandContents(this);" class="side_btn1"></a></div> -->
</body>
</html>