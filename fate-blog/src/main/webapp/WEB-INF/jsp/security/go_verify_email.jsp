<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>${setting.appName}邮箱验证确认页</title>

<link rel="stylesheet" href="http://open.reinforce.cn/artdialog/6.0.4/css/ui-dialog.css">
</head>

<body>
<script src="http://open.reinforce.cn/jquery/1.11.3/jquery.min.js"></script>
<script src="http://open.reinforce.cn/artdialog/6.0.4/js/dialog-plus-min.js"></script>
<script type="text/javascript">
	$(function() {
		$.ajax({
			type : "post",
			url : "op/security/verification/verifyEmail",
			dataType : 'json',
			data : {
				uid : "${uid}",
				code : "${code}"
			},
			error : function() {
				var d = dialog({
					content : "通信错误",
					okValue : '确定',
					ok : function() {
						return true;
					}
				});
				d.showModal();
			},
			success : function(data) {
				if (data.success) {
					var d = dialog({
						content : "验证成功",
						okValue : '确定',
						ok : function() {
							window.close();
							return true;
						}
					});
					d.showModal();
				} else if (data.error_code == 1) {
                   var d = dialog({
						content : "验证链接已经超时，请重新申请邮箱验证",
						okValue : '确定',
						ok : function() {
							window.close();
							return true;
						}
					});
					d.showModal();
				}else if (data.error_code == 2||data.error_code == 3) {
                   var d = dialog({
						content : "无效链接",
						okValue : '确定',
						ok : function() {
							window.close();
							return true;
						}
					});
					d.showModal();
				}
			}
		});
	});
</script>
</body>
</html>
