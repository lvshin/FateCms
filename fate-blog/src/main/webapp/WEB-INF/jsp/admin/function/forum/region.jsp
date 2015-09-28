<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<title>分区设置--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="/op/base/admin/head"></c:import>
<style type="text/css">
.input-group {
	width: 100%;
}

.table thead tr td {
	font-weight: bold;
}

.del {
	height: 22px !important;
}
</style>
</head>

<body>
	<c:import url="/op/base/admin/header">
		<c:param name="header_index" value="2" />
	</c:import>
	<div class="container-fluid">
		<div class="reinforce-main region">
			<c:import url="../navi.jsp">
				<c:param name="index" value="1" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;" rel="nofollow">管理中心</a> ><a
								href="admin/forum/forumList">版块管理</a> > 分区设置
						</h5>
					</div>
					<div class="site_right"></div>
				</div>
				<div class="page">
					<c:if test="${success}">
						<form id="regionForm" >
						<div class="page-header">
							<h3>
								分区消息设置--${forum.forumName} <small>(fid:${forum.fid})<input type="hidden" name="fid" value="${forum.fid}"></small>
							</h3>
						</div>
						<div class="row">
							<div class="form-group col-md-6">
								<label for="regionName">分区名称</label> 
								<input type="text" class="form-control" id="regionName" placeholder="分区名称" name="regionName" value="${forum.forumName}">
							</div>
							<div class="col-md-6">
							</div>
						</div>
						<div class="alert alert-info" role="alert">SEO设置</div>
						<div class="row">
							<div class="form-group col-md-6">
								<label for="title">title</label> 
								<input type="text" class="form-control" id="title" placeholder="" name="title" value="${forum.title}">
							</div>
							<div class="col-md-6">
								<label class="help-block">显示在浏览器标题处的title</label>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-6">
								<label for="keywords">keywords</label> 
								<input type="text" class="form-control" id="keywords" placeholder="" name="keywords" value="${forum.keywords}">
							</div>
							<div class="col-md-6">
								<label class="help-block">keywords用于搜索引擎优化，放在 meta 的 keyword 标签中，多个关键字间请用半角逗号 "," 隔开</label>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-6">
								<label for="description">description</label> 
								<textarea class="form-control" id="description" name="description" >${forum.description}</textarea>
							</div>
							<div class="col-md-6">
								<label class="help-block">description用于搜索引擎优化，放在 meta 的 description 标签中</label>
							</div>
						</div>
						</form>
					</c:if>
					<c:if test="${!success}">
						${message}
					</c:if>
					<br>
					<button class="btn btn-success" onclick="regionSubmit()">提交</button>
				</div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript">
		//分区更新
		function editRegion() {
			var options = {
				url : "admin/forum/editRegion",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr.success(data.msg);
						setTimeout(function() {
							location.reload();
						}, 1000);
					} else {
						toastr.error(data.msg);
					}
				},
				error : function() {
					toastr.error("通信错误");
				}

			};
			$("#regionForm").ajaxSubmit(options);
		}
	</script>
</body>
</html>
