$(function(){
})
function availableZoneShow(){
	var ap=$("#availablePartition");
	ap.css("display","none");
	
	$("#pl_availablePartition").val("");
	$("#pl_platformType").change(function(){
		var val=this.value;
		if(val=='4'||val=='5'){
			ap.css("display","block");
			selectTierByAreaAZ();
//			$("#pl_availablePartition").val("");
			$("#pl_hostType").val("");
		}else{
			ap.css("display","none");
		}
	});
}
