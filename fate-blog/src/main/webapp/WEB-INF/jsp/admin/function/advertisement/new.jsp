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

<title>添加广告--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="/op/base/admin/head"></c:import>
<style type="text/css">
.input-group {
	width: 100%;
}

#contentEditer {
	width: 100%;
	height: 400px;
}
</style>
</head>

<body>
	<c:import url="/op/base/admin/header">
		<c:param name="header_index" value="2" />
	</c:import>
	<div class="main row">
		<c:import url="navi.jsp">
			<c:param name="index" value="1" />
		</c:import>
		<div class="right col-md-10">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;" rel="nofollow">管理中心</a> ><a
							href="admin/advertisement/list">广告发布</a> > ${operation}广告
					</h5>
				</div>
			</div>
			<form id="advertisementForm" style="width:80%">
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label for="title">广告名称</label> <input type="text"
								class="form-control" id="name" name="name"
								value="${advertisement.name}" maxlength="50"> <input
								type="hidden" name="id" value="${advertisement==null?0:advertisement.id}">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label for="type">广告类型</label> 
							<select name="type" id="type" class="form-control">
								<option value="1" ${advertisement.type==1?"selected":""}>页面底部广告</option>
								<option value="2" ${advertisement.type==2?"selected":""}>主题页右侧导航广告</option>
								<option value="3" ${advertisement.type==3?"selected":""}>主题文内广告</option>
								<option value="4" ${advertisement.type==4?"selected":""}>页面两侧空隙的悬浮广告</option>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label for="active">是否激活</label> 
							<br>
							<input type="radio" id="activeOn" name="active" value="true" ${advertisement==null?"checked":(advertisement.active?"checked":"")}><label for="activeOn" style="font-weight: normal;">是</label>
							<input type="radio" id="activeOff" name="active" value="false" ${advertisement.active?"":"checked"}><label for="activeOff" style="font-weight: normal;">否</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label for="code">广告代码</label> 
							<textarea class="form-control" id="code" name="code" rows="10">${advertisement.code}</textarea>
						</div>
					</div>
				</div>
				<div class="btns">
					<button type="button" class="btn btn-primary"
						onclick="addadvertisement()">发布</button>
					<button type="button" class="btn btn-default"
						onclick="location.href='admin/advertisement/list'">返回</button>
				</div>
			</form>
			<div class="clear"></div>
		</div>
	</div>
	<c:import url="/op/base/footer"></c:import>
	<script type="text/javascript">
		$(function(){
			$("#typeSimple").click(function(){
				$("#urlDiv").hide();
			});
			$("#typeUrl").click(function(){
				$("#urlDiv").show();
			});
		});
		function addadvertisement() {

			var options = {
				url : "admin/advertisement/submit",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					var d = dialog({
						content : data.message
					});
					d.showModal();
					if (data.success) {
						setTimeout(function() {
							window.location.href = "admin/advertisement/list";
						}, 1000);
					} else {
						setTimeout(function() {
							d.close().remove();
						}, 1000);
					}
				},
				error : function() {
					alert("通信错误");
				}

			};
			$("#advertisementForm").ajaxSubmit(options);
		}
	</script>
</body>
</html>
