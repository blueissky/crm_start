<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/inc/header.jsp"></jsp:include>
<br/><br/><br/>
<div class="container-fluid">
	<div class="row">
	  <div class="col-md-2">
	  	<jsp:include page="/inc/sidebar.jsp"></jsp:include>
	  	<embed src="<%=basePath%>images/clock.swf" width="200" height="200">
	  </div>
	  <div class="col-md-10">
	  	 <div><img src="images/loginPC2.jpg" alt="login" width="100%" height="600px"></div> 
	  </div>
	</div>
</div>



<jsp:include page="/inc/footer.jsp"></jsp:include>