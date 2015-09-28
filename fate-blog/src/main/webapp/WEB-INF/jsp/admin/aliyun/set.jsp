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

<title>${setting.siteName}-文件系统-基本设置</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<c:import url="/op/base/admin/head"></c:import>

</head>

<body>
	<c:import url="/op/base/admin/header">
		<c:param name="header_index" value="1" />
	</c:import>
	<div class="container-fluid">
	<div class="reinforce-main">
		<c:import url="navi.jsp">
			<c:param name="index" value="1" />
		</c:import>
		<div class="reinforce-content">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;" rel="nofollow">管理中心</a> ><a
							href="javascript:;">阿里云</a> > 基础设置
					</h5>
				</div>
				<div class="site_right"></div>
			</div>
			<div class="page">
				<div class="page-header">
					<h1>
						基本设置 <small>请先配置本页的阿里云基本设置，否则其他阿里云配置页面会500</small>
					</h1>
				</div>
				<form id="setForm" onsubmit="return false;">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="accessKeyId">Access Key ID</label>
								<input type="text" class="form-control" id="accessKeyId" placeholder="Access Key ID" name="accessKeyId" value="${paccessKeyId}">
							</div>
						</div>
						<div class="col-md-6">
							<label class="help-block">阿里云API的Access Key ID</label>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="accessKeySecret">Access Key Secret</label>
								<input type="password" class="form-control" id="accessKeySecret" placeholder="Access Key Secret" name="accessKeySecret" value="${paccessKeySecret}">
							</div>
						</div>
						<div class="col-md-6">
							<label class="help-block">阿里云API的Access Key Secret</label>
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
				url : "admin/aliyun/updateSet",
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
