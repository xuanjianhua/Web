<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
      String path = request.getContextPath();
      String basePath = request.getScheme() + "://"
                  + request.getServerName() + ":" + request.getServerPort()
                  + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
	$().ready(function() {
		$("#btn_login").on('click', function() {
			var username = $("#username").val();
			var password = $("#password").val();
			alert(username + password);
		});
	});
</script>
</head>
<body>
	<div>
		<span>username：</span><input type="text" id="username"name="username" /><br> 
		<span>password：</span><input type="password" id="password" name="password" /><br> 
		<input type="button" id="btn_login" value="login" />
	</div>
</body>
</html>