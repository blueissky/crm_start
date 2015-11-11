////////////////////////////////////////////普通js放方法

/**
 *取消submit提交 
 */
function thispage(){
		var x=$(".username").val(); 
		if(x==''){
			return false;
		}else{
			return true;
		}
	}
///////////////////////////////////////////////以下是jquery方法

$(function(){
	$(".delCustomer").click(function(){
		var customerId=$("#hiddenCustomerID").val();
		var judge=confirm("确认要删除？");
		if(judge==false){
			return;
		}
		$.ajax({
			type:"post",
			async:false,
			data:"action=delClient&customerId="+customerId,
			url:"servlet/ClientServlet",
			success:function(message){
				if(message=="true"){
					alert("删除成功");
					window.location.href="client/client.jsp";
				}else{
					alert("删除失败");
				}
			}
		});
	});
	
	/**
	 * 判断新增客户信息是否符合格式
	 */
	var regExptel=function(){
		var tel=$(".usertel").val();
		var regtel=/^[\d|-]*$/;//12312-12323-
		if(!regtel.test(tel)){
			$(".spantel").html("请输入正确的格式!");
			$(".subbtn").attr("disabled",true);
		}else{
			$(".spantel").html("数字和-组合");
			$(".subbtn").attr("disabled",false);
		}
	} 
	var regExpage=function(){
		var age=$(".userage").val();
		var regage=/^([0-9][0-9])?$/;//0到99之间或者没有
		if(!regage.test(age)){
			$(".spanage").html("请输入正确的格式!");
			$(".subbtn").attr("disabled",true);//设置提交按钮不能用
		}else{
			$(".spanage").html("0-99之间");
			$(".subbtn").attr("disabled",false);
		}
	}
	var regExpqq=function(){
		var qq=$(".userqq").val();
		var regqq=/^(\d{5,15})?$/;//5到15位QQ号
		if(!regqq.test(qq)){
			$(".spanqq").html("请输入正确的格式!");
			$(".subbtn").attr("disabled",true);
		}else{
			$(".spanqq").html("5到15位之间");
			$(".subbtn").attr("disabled",false);
		}
	}
	var regExpemail=function(){
		var email=$(".useremail").val();
		var regemail=/^((\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3}))?$/;//判断电子邮件
		if(!regemail.test(email)){
			$(".spanemail").html("请输入正确的格式!");
			$(".subbtn").attr("disabled",true);
		}else{
			$(".spanemail").html("xxx@xxx.xxx");
			$(".subbtn").attr("disabled",false);
		}
	}
	$(".usertel").blur(regExptel);
	$(".userage").blur(regExpage);
	$(".userqq").blur(regExpqq);
	$(".useremail").blur(regExpemail);
/**
 * 判断表单是否为空
 */	
	$(".subbtn").click(function(){
		var name=$(".username").val();
		if(name!=''){
			$(".newform").attr("action","servlet/ClientServlet?action=saveMessage");
			alert("增加客户成功!");
		}
	});
/**
 * 增加新的业务信息 
 */
	$("#newRequest").click(function(){
		var method=$("#requestMethod").val();
		var content=$("#requestContent").val();
		var customerID=$("#hiddenCustomerID").val();
		$.ajax({
			type:"post",
			async:false,
			data:"action=newRequest&method="+method+"&content="+content+"&customerID="+customerID,
			url:"servlet/BusinessServlet",
			success:function(message){
				if(message=="true"){
					alert("增加信息成功！");
				}else{
					alert("增加信息失败！");
				}
			}
		});
	});
});
//			为了避免是用 alert时刷新页面，因此使用以下二种方法
//			方法一 <form name="df">
//				  提交按钮该为button 
//					df.submit();
//			方法二<form onsubmit="return x()">(true/false)
//					function x(){
//						
//					}
//					方法放在普通JS中，
//				提交按钮是submit
//				newformname.submit();
//	$("#searchbutton").click(function(){
//		$("#customermessage").replaceWith("");
//		$("#customerpage").replaceWith("");
//		$("#hiddenarea").show();//显示隐藏区域，对应display none不显示
//	});
//		var customerId=$("#delCustomer").val();
//		$.ajax({
//			type:"post",
//			async:"false",
//			data:"action=delCustomer&customerId="+customerId,
//			url:"servlet/ClientServlet",
//			success:function(){
//				if(message=="true"){
//					alert("增加信息成功！");
//				}else{
//					alert("增加信息失败！");
//				}
//			}
//		});












