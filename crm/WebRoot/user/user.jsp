<%@ page language="java" import="java.util.*" import="net.qingsoft.crm.vo.AccountInfo" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/inc/header.jsp"></jsp:include>
<br/><br/><br/>
<link href="<%=basePath%>css/user.css" rel="stylesheet">
<div class="container-fluid">
	<div class="row">
	  <div class="col-md-2">
	  	<jsp:include page="/inc/sidebar.jsp"></jsp:include>
	  	<embed src="<%=basePath%>images/clock.swf" width="200" height="200">
	  </div>
	  <div class="col-md-10">

		<!-- Tab切换 -->
		<ul class="nav nav-tabs" role="tablist">
		  <li class="active"><a href="#profile" role="tab" data-toggle="tab">所有用户</a></li>
		  <li><a href="#add" role="tab" data-toggle="tab">添加用户</a></li>
		</ul>
		
		<!-- Tab 面板 -->
		<div class="tab-content">

		  <div class="tab-pane active" id="profile">
				<!-- 表格 -->
				<table class="table table-striped table-hover text-center">
					<tr>
						<th class="text-center">用户ID</th>
						<th class="text-center">用户姓名</th>
						<th class="text-center">电话</th>
						<th class="text-center">用户名</th>
						<th class="text-center">权限</th>
						<th class="text-center">操作</th>
					</tr>
					<jsp:include page="/servlet/AccountInfoServlet?action=viewAll"></jsp:include>
					<%		
					 ArrayList<AccountInfo> list = (ArrayList<AccountInfo>)request.getAttribute("list");
					 for(int i=0;i<list.size();i++){
						 AccountInfo accountInfo=list.get(i);
						 %>
						 <tr>
							 <td><span class="label label-success"><%=accountInfo.getId()%></span></td>
							 <td><%=accountInfo.getName()%></td>
							 <td><%=accountInfo.getTel()%></td>
							 <td><%=accountInfo.getUsername()%></td>
							 <td>
								 <span class="lvl">
							 	<%
							 	if(accountInfo.getLvl()==0){
							 	%>管理员
							 	<%
							 	}
							 	else{
							 	%>业务员
							 	<%
							 	}
							 	%>
							 	</span>
							 </td>
							 <td>
							 <!-- start -->
							 <div class="dropdown">
								  <button  class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
								    用户功能<span class="caret"></span>
								  </button>
								  <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
								    <li role="presentation">
								    	<button id="viewForm<%=accountInfo.getId()%>" value="<%=accountInfo.getId()%>" class="btn btn-primary btnview" data-toggle="modal" >查看订单</button>
								    </li>
								    <li role="presentation">
								    	<button id="editForm<%=accountInfo.getId()%>" value="<%=accountInfo.getId()%>" class="btn btn-success btnedit" data-toggle="modal" >修改权限</button>
								    </li>
								    <li role="presentation">
								    	<button id="delForm<%=accountInfo.getId()%>" value ="<%=accountInfo.getId()%>" class="btn btn-warning btndel" data-toggle="modal" >删除用户</button>
								    </li>
								  </ul>
								</div>
							 <!-- end -->
							 </td>
						 </tr>
						 <%
						 	} 
						 %>
						 
					
				</table>
				<!-- 分页 -->
				<jsp:include page="/servlet/AccountInfoServlet?action=pageCount"></jsp:include>
				
				
					<ul class="pagination">
						<li><a>&laquo;</a></li>
							<li>
							<%	
								int count=(Integer)request.getAttribute("count");
								for(int i=1;i<=count;i++){
							%>
							
							<a href="<%=basePath%>user/user.jsp?pageNo=<%=i%>">
							<%=i%>
							</a>
							<%
								}
							 %>
							</li>
						<li><a>&raquo;</a></li>
					</ul>
				
				
		  </div>
		  
		<!-- Tab 面板 -->

		  <div class="tab-pane" id="add">
				<!-- 表格 -->
				<table class="table table-bordered table-striped table-hover">
					<tr>
						<td><span class="label label-success">请输入用户名</span></td>
						<td><input type="text" class="form-control" id="addUsername" placeholder="用户名"></td>
						<td><span id="spanusername" style = "display:block; width:300px;">请输入英文数字的组合，不区分大小写</span></td>
					</tr>
					<tr>	
						<td><span class="label label-success">请输入姓名</span></td>
						<td><input type="text" class="form-control" id="addName" placeholder="姓名"></td>
						<td><span id="spanname" style = "display:block; width:300px;">请输入真实姓名</span></td>
					</tr>
					<tr>	
						<td><span class="label label-success">请输入电话</span></td>
						<td><input type="text" class="form-control" id="addTel" placeholder="电话"></td>
						<td><span id="spantel" style = "display:block; width:300px;">请输入数字和-的组合</span></td>
					</tr>
					<tr>	
						<td><span class="label label-success">请选择权限</span></td>
						<td>
							<select name="level" id = "addLvl" >
								<option value=1 selected>业务员</option>
								<option value=0>管理员</option>
							</select>
						</td>
					</tr>
				</table>
				<center><button class="btn btn-warning" data-toggle="modal" id="adduser" disabled>添加用户</button></center>
		  </div>
		<!-- Tab切换 结束-->	  	
	  	
	  </div>
	</div>
</div>

<!-- 模态对话框 -->
<div class="modal fade" id="editFormModal" tabindex="-1" role="dialog" aria-hidden="true">
<div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
        <h4 class="modal-title" id="4">修改权限</h4>
      </div>
      <div class="modal-body saleel">
			<form role="form">
			  <div class="form-group">
			    <label for="name" class="fbb">现权限:</label>
			    <br/>
			    <label>
			    	<%
						AccountInfo accountInfo = (AccountInfo)(session.getAttribute("account"));
			    		int id = accountInfo.getId();
						int lvl = accountInfo.getLvl();
						
						if(accountInfo.getLvl()==0){
							out.print("管理员");
						}
						else{
							out.print("业务员");
						}
					%>
				</label>
			  </div>
			  <div class="form-group">
			    <label for="tel" class="fbb">更改权限</label>
			    <input  class="form-control" id="tel" >
			  </div>
	       </form>
		<div class="modal-footer">
		  <button type="button" class="btn btn-primary " id="save">保存</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
      
    </div>
</div>
</div>
</div>



<jsp:include page="/inc/footer.jsp"></jsp:include>

	<script language="javascript" type = "text/javascript">
		/* 点击按钮事件 */
		$(document).ready(function(e) {
			var arrdel = $(".btndel");
			var arredit = $(".btnedit");
			var arrlvl = $(".lvl").text();
			for(var i = 0; i<arrdel.length;i++){
				if(arrdel[i].value == <%=id%>){
					arrdel[i].disabled=true;
				}
				arrdel[i].onclick = function(){
					if(window.confirm("你确定要删除该用户吗?")){
						window.location.href="<%=basePath%>servlet/AccountInfoServlet?action=delUser&id="+this.value;
						alert("即将删除"+this.value+"用户");
						return true;
					}
					else{
						return false;
					}
				}
			}
			
			for(var i = 0; i<arredit.length;i++){
				if(arredit[i].value == <%=id%>){
					arredit[i].disabled=true;
				}
				arredit[i].onclick = function(){
					if(window.confirm("你确定要修改该用户权限吗?")){
						window.location.href="<%=basePath%>servlet/AccountInfoServlet?action=changeLvl&id="+this.value;
						alert("即将修改"+this.value+"用户");
						return true;
					}
					else{
						return false;
					}
				}
				
			}
			
			
    		$("#adduser").click(function(e) {
    			var addUsername = $("#addUsername").val();
				var addName = $("#addName").val();
				var addTel = $("#addTel").val();
				var addLvl = $("#addLvl").val();
				addName = encodeURI(addName);
				addName = encodeURI(addName);
				
				window.location.href="<%=basePath%>servlet/AccountInfoServlet?action=addUser&username="+addUsername+"&name="+addName+"&tel="+addTel+"&lvl="+addLvl;	
				alert("添加用户成功,初始密码为1234");	
				return true;
			});
		});
		
		var arrview = $(".btnview");
		for(var i = 0; i<arrview.length;i++){
			arrview[i].onclick = function(){
				window.location.href="<%=basePath%>servlet/AccountInfoServlet?action=orderView&id="+this.value;
			}
		}
		
	</script>
	
	<script language="javascript" type="text/javascript">
	var u = 0;
	var n = 0;
	var t = 0;
		/* 判断录入是否可用 */
  		$("#addUsername").blur(function(){
  			var addUsername = $("#addUsername").val();
  			if(addUsername ==""){
  				$("#spanusername").html("<font color='Red'>请填写用户名</font>");
  				return;
  				u = 0;
  			}
  			$.ajax({
	  				type:"post",
	  				async:false,
	  				data:"action=isExist&username="+addUsername,
	  				url:"<%=basePath%>servlet/AccountInfoServlet",
	  				success:function(message){
	  					if(message=="false")
	  					{
	  						u = 0;
	  						$("#spanusername").html("<font color='Red'>用户名已被使用</font>");
	  					}
	  					else if(message == "true"){
	  						u = 1;
	  						$("#spanusername").html("<font color='Green'>√</font>");
	  						if(u+n+t == 3){
	  							$("#adduser").attr("disabled",false);
	  						}
	  					}
	  				}
	  			});
	  		})
  		/* 文本框获得焦点，按钮失效 */
  		$("#addUsername").focus(function(){
  			$("#adduser").attr("disabled",true);
  			u = 0;
  			})
  		$("#addName").focus(function(){
  			$("#adduser").attr("disabled",true);
  			n = 0;
  			})
  		$("#addTel").focus(function(){
  			$("#adduser").attr("disabled",true);
  			t = 0;
  			})
	  	/* 判断电话是否正确 */
  		$("#addTel").blur(function(){
  			var tel = $("#addTel").val();
  			var regtel = /^[\d|-]*$/;//12312-12323-
  			if(tel ==""){
  				t = 0;
  				$("#spantel").html("<font color='Red'>请输入数字和-组合</font>");
  			}
  			else if(!regtel.test(tel)){
  				t = 0;
  				$("#spantel").html("<font color='Red'>请输入数字和-组合</font>");
  			}
  			else{
  				t = 1;
  				$("#spantel").html("<font color='Green'>√</font>");
  				if(u+n+t == 3){
  					$("#adduser").attr("disabled",false);
  				}
  			}
  			})
	  	/* 判断是姓名是否正确 */
  		$("#addName").blur(function(){
  			var name = $("#addName").val();
  			var regname = /^[\u4e00-\u9fa5]*$/;//12312-12323-
  			if(name == ""){
  				n = 0;
  				$("#spanname").html("<font color='Red'>请输入正确的姓名</font>");
  			}
  			else if(!regname.test(name)){
  				n = 0;
  				$("#spanname").html("<font color='Red'>请输入正确的姓名</font>");
  			}
  			else{
  				n = 1;
  				$("#spanname").html("<font color='Green'>√</font>");
  				if(u+n+t == 3){
  					$("#adduser").attr("disabled",false);
  				}
  			}
  			})
  			
				
	</script>

	
	
	
	
	
	
	
	
	
	
