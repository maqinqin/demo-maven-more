//清空页面
function clearTab(tableId){
	 var tab = document.getElementById(tableId) ;
	 var inputs = tab.getElementsByTagName("input"); 
	 for(var k=0;k<inputs.length;k++) 
	 { 
		 if(inputs[k].type!='button'  && inputs[k].id != 'serviceStatus' && inputs[k].id != 'serviceType' && inputs[k].id != 'attrType' && inputs[k].id != 'attrClass'){
			 inputs[k].value=""; 
		 }
	 } 
	 var texts = tab.getElementsByTagName("textarea"); 
	  for(var k=0;k<texts.length;k++) 
	 { 
			 texts[k].value=""; 
	 } 
}
