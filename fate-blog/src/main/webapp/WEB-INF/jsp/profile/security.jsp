<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://"+ request.getServerName() + path + "/";%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>${userSession.user.nickName}的个人资料</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="keywords" content="${userSession.user.nickName}的个人资料">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description"
	content="${userSession.user.nickName}的个人资料,${setting.appName}">
<link rel="stylesheet" type="text/css"
	href="css/Validform/datePicker-min.css">
<link rel="stylesheet" type="text/css" href="css/Validform/style.css">
<link rel="stylesheet" type="text/css" href="css/Validform.css">
<c:import url="/op/head"></c:import>
</head>

<body>
		<!-- 头部导航条 -->
		<c:import url="/op/header">
			<c:param name="header_index" value="1" />
		</c:import>
		<div class="container">
		<div class="profile">
			<div class="row">
				<c:import url="navi.jsp">
					<c:param name="index" value="3" />
				</c:import>
				<div class=" col-md-10 col-xs-12">
					<div class="title">
						<span><strong>安全设置</strong></span>
					</div>
					<div class="page-header">
						<h4>
							手机号
						</h4>
					</div>
					<div class="alert alert-info" role="alert">
						<c:if test="${userSession.user.mobile!=null}">
							${userSession.user.mobile}${userSession.user.mobileStatus?"":"（未验证）"}
						</c:if>
						<a href="profile/mobile?mobile=${userSession.user.mobileStatus?"":userSession.user.mobile}">${userSession.user.mobile==null?"立即绑定":"更改绑定"}</a>
					</div>
					<div class="page-header">
						<h4>
							邮箱
						</h4>
					</div>
					<div class="alert alert-info" role="alert">
						<c:if test="${userSession.user.email!=null}">
							${userSession.user.email}${userSession.user.emailStatus?"":"（未验证）"}
						</c:if>
						<a href="profile/email?email=${!userSession.user.emailStatus?userSession.user.email:""}">${userSession.user.email==null?"立即绑定":"更改绑定"}</a>
					</div>
				</div>
			</div>
		</div>
		<c:import url="/op/footer"></c:import>
	</div>
	<script type="text/javascript">
		$(function() {
		});
		function submitInfo() {
			var options = {
				url : "profile/updateBasicInfo",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					var d = dialog({
						content : data.message
					});
					d.showModal();
					if (data.success) {
						setTimeout(function() {
							location.reload();
						}, 1500);
					} else {
						setTimeout(function() {
							d.close().remove();
						}, 1500);
					}
				},
				error : function() {
					dialog.alert("通信异常");
				}
			};
			$("#infoForm").ajaxSubmit(options);
			return false;
		}
	</script>
</body>
</html>
