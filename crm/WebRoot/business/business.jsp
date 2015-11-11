<%@ page language="java" import="java.util.*,net.qingsoft.crm.vo.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/inc/header.jsp"></jsp:include>
<br/><br/><br/>
<link href="<%=basePath%>css/business.css" rel="stylesheet">
<div  class="container-fluid">
	<div class="row">
		<div class="col-md-2">
			<jsp:include page="/inc/sidebar.jsp"></jsp:include>
			<embed src="<%=basePath%>images/clock.swf" width="200" height="200">
		</div>
		<div class="col-md-10">
			<!-- Tab切换标签 -->
			<ul class="nav nav-tabs" role="tablist">
				<li class="active"><a href="#message" role="tab" data-toggle="tab">业务查看</a></li>
			</ul>
			<!-- Tab切换面板 -->
			<script type="text/javascript">
		   	function hiddenValue(x){
				document.getElementById("hiddenArea").value=x;/**点击按钮时修改隐藏属性的value值**/
			}
			</script>
			<input id="hiddenArea" type="hidden" value="">
			<div class="tab-content">
				<!--业务查看表  -->
				<div class="tab-pane active" id="message">
					<table class="searchTable table table-bordered table-hover">
						<tr>
							<td>
							<form class="navbar-form navbar-left" method="post" action="<%=basePath%>servlet/BusinessServlet?action=findAllRequest">
							<span class="label label-info">姓名</span><input class="form-control" type="text" name="customerName">        
							<span class="label label-info">地点</span><input class="form-control" type="text" name="method">
							<input type="submit" value="搜索" class="btn btn-default" id="searchbutton">
							</form>
							</td>
						</tr>
					</table>
					<table class="searchView table table-bordered table-striped table-hover">
						<tr>
							<th>编号</th><th>客户姓名</th><th>会谈内容</th>
						</tr>
						<%
						ArrayList<RequestInfo> list=(ArrayList<RequestInfo>)request.getAttribute("findAllRequest");
						Integer num=(Integer)request.getAttribute("num");
						RequestInfo ri=null;
						for(int i=0;list!=null&&i<list.size();i++){
							ri=list.get(i);							
							
						%>
						<tr>
							<td><span class="label label-success"><%=ri.getNum()%></span></td>
							<td>
							姓名:<%=ri.getCustomerInfo().getName()%><br/>
							电话:<%=ri.getCustomerInfo().getTel()%><br/>
							方式:<%=ri.getMethod()%><br/>
							时间:<%=ri.getConndate()%><br/>
							<button class="btn btn-warning btn-sm delRequest" onclick=hiddenValue(<%=ri.getId()%>)>删除</button>
							</td>
							<td><textarea class="form-control" cols="90" rows="6"><%=ri.getCont()%></textarea></td>
						</tr>
						<%
						}
						%>
					</table>
					<!-- 分页列表开始 -->
					<ul class="pagination" id="customerpage">
						<li><a>&laquo;</a></li>
					<%for(int i=1;num!=null&&i<=num;i++){
						%>
						<li >
						<a href="<%=basePath%>servlet/BusinessServlet?action=findAllRequest&pageNo=<%=i%>"><%=i%><!-- 回调jsp文件并将pageNo参数传送到servlet -->
						</a>
						</li>
					<%
					} %>
						<li><a>&raquo;</a></li>
				</ul>
					<!-- 分页列表结束 -->
				</div>
			</div>
			<!-- Tab结束 -->
		</div>
	</div>
</div>
<script src="<%=basePath %>js/jquery-1.8.2.min.js"></script>
<script src="<%=basePath%>js/business.js"></script>
<jsp:include page="/inc/footer.jsp"></jsp:include>