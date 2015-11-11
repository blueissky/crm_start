$(function(){
	$(".delRequest").click(function(){
		var value=$("#hiddenArea").val();
		var judge=confirm("确认要删除？");
		if(judge==false){
			return;
		}
		$.ajax({
			type:"post",
			async:false,
			data:"action=delRequest&delRequest="+value,
			url:"servlet/BusinessServlet",
			success:function(message){
				if(message=="true")
					{
					location.href="servlet/BusinessServlet?action=findAllRequest";
					}
			}
		});
	});
});
