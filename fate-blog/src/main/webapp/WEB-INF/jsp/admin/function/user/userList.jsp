<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>用户列表--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="/op/base/admin/head"></c:import>
<style type="text/css">
.input-group {
	width: 100%;
}

.table thead tr td{font-weight: bold;}
.del{height:22px!important;}
</style>
</head>

<body>
	<c:import url="/op/base/admin/header">
	    <c:param name="header_index" value="2" />
	</c:import>
	<div class="container-fluid">
		<div class="reinforce-main region">
			<c:import url="../navi.jsp">
				<c:param name="index" value="3" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;"  rel="nofollow">用户管理</a> > 用户列表
						</h5>
					</div>
				</div>
				<div class="page">
					<table class="table table-hover">
						<thead>
							<tr>
								<th><input type="checkbox" id="selectedAll" value="" style="margin-left:20%"></th>
								<th>昵称</th>
								<th>注册时间</th>
								<th>最后登录时间</th>
								<th>最后登录IP</th>
								<th>状态</th>
								<th>登陆方式</th>
								<th align="right">操作&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${users}" var="user" varStatus="i">
								<tr>
								<td><input type="checkbox" value="" style="margin-left:20%"></td>
								<td>${user.nickName}</td>
								<td>${user.activateDate}</td>
								<td>${user.loginDate}</td>
								<td>${user.loginIp}</td>
								<td>${user.state==0?"正常":"小黑屋"}</td>
								<td>${user.headIconUsed==0?"本地":(user.headIconUsed==1?"QQ":"新浪微博")}</td>
								<td align="right">
									<div class="btn-group btn-group-xs" role="group">
										<button class="btn btn-danger del" onclick="dialog.alert('敬请期待');">禁止该IP访问</button>
									</div>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="page-num">
						<c:import url="/op/page/1/${curPage}/?pageSize=${pageSize}&count=${count}"></c:import>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
		<c:import url="/op/base/foot"></c:import>
<script type="text/javascript" src="js/select.js"></script>
<script type="text/javascript">
</script>
</body>
</html>
