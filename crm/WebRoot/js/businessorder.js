$(function(){
	$("#newRequest").click(function(){
		var judge=confirm("确定提交?");
		if(!judge){
			return;}
		var id=$("#hiddenCustomerID").val();
		var method=$("#requestMethod").val();
		var content=$("#requestContent").val();
		$.ajax({
			type:"post",
			data:"action=newRequest&customerID="+id+"&content="+content+"&method="+method,
			url:"servlet/BusinessServlet",
			success:function(message){
				$("#business").html(message);
			}
		});
	});
});