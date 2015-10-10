<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<title>合作帐号绑定</title>
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
<style>
.thirdParty {
	width: 70%;
	min-height: 200px;
	margin: 40px auto;
}

.row {
	margin-bottom: 15px;
}

.row #code {
	width: 60%;
	display: inline-block;
}

.row #sendCode {
	margin-top: -5px;
}

.btns {
	width: 250px;
	margin: 0 auto;
}

#mobile {
	
}
</style>
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
					<c:param name="index" value="5" />
				</c:import>
				<div class=" col-md-10 col-xs-12">
					<div class="title">
						<span><strong>合作帐号绑定</strong></span>
					</div>
					<div class="thirdParty row">
						<div class="col-xs-4">
							<strong>QQ帐号</strong>
						</div>
						<div class="col-xs-8">
							<span class="help-block">${qq==null?"未绑定":"已绑定"}&nbsp;<a
								href="${qq==null?"profile/toBindQQ":"javascript:unbindQQ();"}">${qq==null?"点击绑定":"点击解绑"}</a></span>
							<img src="${qq==null?"
								images/default_headicon.png":qq.headIconBig}" class="img-thumbnail"
								width=100 />
						</div>
						<div class="col-xs-4">
							<strong>新浪微博帐号</strong>
						</div>
						<div class="col-xs-8">
							<span class="help-block">${weibo==null?"未绑定":"已绑定"}&nbsp;<a
								href="${weibo==null?"profile/toBindWeibo":"javascript:unbindWeibo();"}">${weibo==null?"点击绑定":"点击解绑"}</a></span>
							<img src="${weibo==null?"
								images/default_headicon.png":weibo.headIconBig}" class="img-thumbnail"
								width=100 />
						</div>
					</div>
				</div>

			</div>
		</div>
		<c:import url="/op/footer"></c:import>
	</div>
	<script type="text/javascript" src="js/Validform/Validform_v5.3.2.js"></script>
	<script type="text/javascript">
		function unbindQQ() {
			$.ajax({
				url : "profile/unbindQQ",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						location.reload();
					} else {
						var d2 = dialog({
							content : data.message
						});
						d2.show();
						setTimeout(function() {
							d2.close().remove();
						}, 1000);
					}
				}
			});
		}
		
		function unbindWeibo() {
			$.ajax({
				url : "profile/unbindWeibo",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						location.reload();
					} else {
						var d2 = dialog({
							content : data.message
						});
						d2.show();
						setTimeout(function() {
							d2.close().remove();
						}, 1000);
					}
				}
			});
		}
	</script>
</body>
</html>
