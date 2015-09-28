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
		var d = dialog({
			content : "登录超时，请重新登录",
			width : "100px",
			okValue : '确定',
			ok : function() {
				window.location.href = "op/login/toLogin";
			}
		});
		d.show();

	});
</script>
</head>
</html>