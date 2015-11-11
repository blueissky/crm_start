<%@ page language="java" import="java.util.*" import="net.qingsoft.crm.vo.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/inc/header.jsp"></jsp:include>
	<%
     	AccountInfo accountInfo = (AccountInfo)(session.getAttribute("account"));
    	String correctOldPass = accountInfo.getPassword();
    	String name = accountInfo.getName();
    	String tel = accountInfo.getTel();
   	%>
<br/><br/><br/>
<div class="container-fluid">
	<div class="row">
	  <div class="col-md-2">
	  	<jsp:include page="/inc/sidebar.jsp"></jsp:include>
	  	<embed src="<%=basePath%>images/clock.swf" width="200" height="200">
	  </div>
	  
	  <div class="col-md-10">
	  	<!-- Tab切换 -->
		<ul class="nav nav-tabs" role="tablist">
		  <li class="active"><a href="#profile" role="tab" data-toggle="tab">档案</a></li>
		  <li><a href="#changepass" role="tab" data-toggle="tab">修改密码</a></li>
		</ul>
		
		<!-- Tab 面板 -->
		<div class="tab-content">

		  <div class="tab-pane active" id="profile">
				<!-- 表格 -->
	  		<table class="table">
					<tr>
						<th>类别</th><th>内容</th>
					</tr>
					<tr>
						<td>真实姓名</td><td><%out.println(accountInfo.getName()); %></td>
					</tr>
					<tr>	
						<td>联系电话</td><td><%out.println(accountInfo.getTel()); %></td>
					</tr>
					<tr>	
						<td>权限</td>
						<td>
						<%
						String lvl = null;
						if(accountInfo.getLvl()==0){
							lvl = "管理员";
						}
						else{
							lvl = "业务员";
						}
						out.println(lvl); 
						%>
						</td>
					</tr>
			</table>
			<center><button class="btn btn-warning" data-toggle="modal" data-target="#editFormModal">修改信息</button></center>
			
	  	  </div>
	  	  
	  	  <div class="tab-pane" id="changepass">
				<div class="row-fluid">
 					 <div class="span2">请输入原密码</div><div class="span4"><input type="password" class="form-control" id="oldpass" name="oldpass" placeholder="原密码"/></div>
 				</div>
 				<div class="row-fluid">
 				 	 <div class="span2">请输入新密码</div><div class="span4"><input type="password" class="form-control" id="newpass" name="newpass" placeholder="新密码"/></div>
 				</div>
 				<div class="row-fluid">
 					 <div class="span2">请确认新密码</div><div class="span4"><input type="password" class="form-control" id="newpassv" name="newpassv" placeholder="确认新密码"/></div>
				</div>
				<center><input type="button" class="btn btn-warning" value ="修改密码" onclick="return pass()"/></center>
		  </div>
		</div>
</div>


<!-- 模态对话框 -->
<div class="modal fade" id="editFormModal" tabindex="-1" role="dialog"aria-hidden="true">
<div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
        <h4 class="modal-title" id="4">修改信息</h4>
      </div>
      <div class="modal-body saleel">
			<form role="form">
			  <div class="form-group">
			    <label for="name" class="fbb">真实姓名</label>
			    <input  class="form-control" id="name" >
			  </div>
			  <div class="form-group">
			    <label for="tel" class="fbb">联系电话</label>
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
		function pass(){
			var oldPass = document.getElementById('oldpass').value;
			var newPass = document.getElementById('newpass').value;
			var newPassv = document.getElementById('newpassv').value;
			
			if(oldPass == "" || newPass == "" || newPassv ==""){
				alert("密码框不能为空");
				return false;
			}
			
			else if(newPass != newPassv){
				alert("新密码两次输入不一致");
				return false;
			}
			
			else if(oldPass != '<%=correctOldPass%>'){
				alert("原密码输入错误");
				return false;
			} 
			
			else if(oldPass == newPass){
				alert("请输入新的密码");
				return false;
			}
			
			window.location.href="<%=basePath%>servlet/AccountInfoServlet?action=updatePass&newpass="+newPass;	
			alert("提交成功");	
			return true;
		}
	</script>
	
	<script language="javascript" type = "text/javascript">
		$(document).ready(function(e) {
    		$("#save").click(function(e) {
    		
    			var name = $("#name").val();
				var tel = $("#tel").val();
				
				if(name == "" && tel ==""){
					alert("没有要修改的内容");
					return false;
				}
				
				if(name == ""){
					name = '<%=name%>';
				}
				
				if(tel == ""){
					tel = '<%=tel%>';
				}

				name = encodeURI(name);
				name = encodeURI(name);
				
				window.location.href="<%=basePath%>servlet/AccountInfoServlet?action=updateInfo&newname="+name+"&newtel="+tel;	
				alert("提交成功");	
				return true;
			});
		});
		
	</script>
	
	