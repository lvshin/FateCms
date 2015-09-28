<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<title>${setting.siteName}-阿里云</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="/op/base/admin/head"></c:import>

</head>

<body>
	<c:import url="/op/base/admin/header">
		<c:param name="header_index" value="1" />
	</c:import>
	<div class="container-fluid">
	<div class="reinforce-main">
		<c:import url="navi.jsp">
			<c:param name="index" value="4" />
		</c:import>
		<div class="reinforce-content">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;" rel="nofollow">管理中心</a> ><a
							href="javascript:;">阿里云</a> > OpenSearch
					</h5>
				</div>
				<div class="site_right"></div>
			</div>
			<div class="page">
			<div class="page-header">
				<h1>
					阿里云OpenSearch <small></small>
				</h1>
			</div>
			<form id="setForm" onsubmit="return false;">

					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="openSearchEndpoint">应用节点</label> <input type="text"
									class="form-control" id="openSearchEndpoint" placeholder="" name="endpoint"
									value="${openSearchEndpoint}">
							</div>
						</div>
						<div class="col-md-6">
							<label class="help-block">在阿里云OpenSearch应用详情页中提供的的API入口</label>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="openSearchAppName">应用名称</label> <input type="text"
									class="form-control" id="openSearchAppName" placeholder="" name="appName"
									value="${openSearchAppName}">
							</div>
						</div>
						<div class="col-md-6">
							<label class="help-block">在阿里云OpenSearch应用的名称</label>
						</div>
					</div>
				<button class="btn btn-default" onclick="setSubmit()">提交</button>
			</form>
			</div>
			<div class="clear"></div>
		</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript">
		function setSubmit() {
			var options = {
				url : "admin/aliyun/updateOpenSearchSet",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr.success(data.msg);
						setTimeout(function() {
							window.location.reload();
						}, 1000);
					} else {
						toastr.error(data.msg);
					}
				},
				error : function() {
					toastr.error("通信错误");
				}

			};
			$("#setForm").ajaxSubmit(options);
		}

	</script>
</body>
</html>
