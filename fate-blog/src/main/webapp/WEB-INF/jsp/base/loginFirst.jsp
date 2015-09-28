<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>祝福之风</title>
<link rel="stylesheet" href="http://open.reinforce.cn/artdialog/6.0.4/css/ui-dialog.css">
<script src="http://open.reinforce.cn/jquery/1.11.3/jquery.min.js"></script>
<script src="http://open.reinforce.cn/artdialog/6.0.4/js/dialog-plus-min.js"></script>
<script type="text/javascript">
	$(function() {
		setTimeout(function(){location.href = "op/login/goLogin";},1000);
	});
</script>
</head>
<body>
<strong>登陆超时，请重新登陆</strong>
</body>
</html>