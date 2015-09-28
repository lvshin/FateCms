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

<title>${setting.siteName}-站点信息</title>
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
				<c:param name="index" value="7" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;" rel="nofollow">管理中心</a> ><a href="javascript:;">全局设置</a> > QQ登录
						</h5>
					</div>
					<div class="site_right"></div>
				</div>
				<div class="page">
					<div class="page-header">
						<h1>
							QQ登录 <small></small>
						</h1>
					</div>
					<form id="qqForm" onsubmit="return false;">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label>是否开启QQ登录</label><br>
									<label><input type="radio" name="on" value="false" ${!setting.qq?"checked":""}>&nbsp;&nbsp;否</label>&nbsp;&nbsp;&nbsp;&nbsp;
									<label><input type="radio" name="on" value="true" ${setting.qq?"checked":""}>&nbsp;&nbsp;是</label>
								</div>
							</div>
							<div class="col-md-6">
								<label class="help-block">是否开启QQ登录功能</label>
							</div>
						</div>
						<div class="qq" ${setting.qq?"style='display:block;'":""}>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="accessKey">accessKey</label> <input type="text" class="form-control" id="accessKey" name="accessKey" value="${qq.accessKey}" >
									</div>
								</div>
								<div class="col-md-6">
									<label class="help-block">QQ互联申请到的accessKey</label>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="accessSecret">accessSecret</label> <input type="text" class="form-control" id="accessSecret" name="accessSecret" value="${qq.accessSecret}" >
									</div>
								</div>
								<div class="col-md-6">
									<label class="help-block">QQ互联申请到的accessSecret</label>
								</div>
							</div>
						</div>
						<button class="btn btn-default" onclick="qqSubmit()">提交</button>
					</form>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript" src="js/admin/siteSet.js"></script>
	<script type="text/javascript">
		$(function(){
			$("input[name=on]").click(function(){
				if($(this).val()=='true')
					$(".qq").show();
				else
					$(".qq").hide();
			});
			
			$(".help").poshytip();
		});
	</script>
</body>
</html>
