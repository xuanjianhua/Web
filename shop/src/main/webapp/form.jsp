<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type= "text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
	$().ready(function() {
		$("#btn_login").on('click', function() {
			var username = $("#username").val();
			var password = $("#password").val();
			$.ajax({
				url:"${pageContext.request.contextPath}/user/show.do",
				type:"post",
				datatype:"json", 
				contentType:"application/json; charset=utf-8",
				data:{username:username,password:password}, 
				success:function(data){
					
					 $("#show").append("用户名："+data.username+"年龄："+data.age);	
				}
				
			});
		}); 
	});
</script>
</head>
<body>

		用户名：<input type="text" id="username" name="username" /><br>
		密码：<input type="password" id="password" name="password" /><br>
		<input type="button" value="登陆" id="btn_login"/>
</body>
</html>