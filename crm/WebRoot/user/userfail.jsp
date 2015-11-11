<%@ page language="java" import="java.util.*" import="net.qingsoft.crm.vo.AccountInfo" pageEncoding="UTF-8"%>
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
		
		<span><h2>对不起，您无权查看此页面。</h2></span>
	  	
	  </div>
	</div>
</div>
<jsp:include page="/inc/footer.jsp"></jsp:include>