<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<title>Ping记录--${setting.siteName}</title>
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
				<c:param name="index" value="2" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;"  rel="nofollow">管理中心</a> ><a href="javascript:;">主题管理</a> > SEO Ping 记录
						</h5>
					</div>
				</div>
				<div class="page">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>主题</th>
								<th>百度</th>
								<th>谷歌</th>
								<th>时间</th>
								<th align="right">操作&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pings}" var="ping" varStatus="i">
								<tr>
									<td><div class="widthLimit"><a href="${ping.theme.url}" title="${ping.theme.title}" target="_blank">${ping.theme.title}</a></div></td>
									<td>${ping.baidu?"成功":"失败"}</td>
									<td>${ping.google?"成功":"失败"}</td>
									<td><fmt:formatDate value="${ping.pingDate}" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
									<td align="right">
										<div class="btn-group btn-group-xs" role="group" aria-label="...">
											<button class="btn btn-success del ping" pid="${ping.id}">全部重ping</button>	
										</div>
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
		$(function() {
			//对主题重新发出ping
			$(".ping").click(function() {
				var pid = $(this).attr("pid");
				var $res = $(this).parent().parent().prev().prev().prev();
				$res.html("<i class='fa fa-spinner fa-pulse'></i>");
				$.ajax({
					url : "admin/theme/rePing",
					type : 'post',
					dataType : 'json',
					data : {
						id : pid
					},
					success : function(data) {
						$res.html(data.success ? "成功" : "失败");
					}
				});
			});

		});
	</script>
</body>
</html>
