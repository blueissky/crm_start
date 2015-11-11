<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<%=basePath %>bootstrap/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%=basePath %>bootstrap/js/bootstrap.min.js"></script>
  </body>
</html>
