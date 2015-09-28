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
				<c:param name="index" value="5" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;" rel="nofollow">管理中心</a> ><a href="javascript:;">站点设置</a> > 注册控制
						</h5>
					</div>
					<div class="site_right"></div>
				</div>
				<div class="page">
					<div class="page-header">
						<h1>
							注册控制 <small></small>
						</h1>
					</div>
					<form id="regForm" onsubmit="return false;">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label>是否开启注册</label><br>
									<label><input type="radio" name="on" value="false" ${!setting.regAllow?"checked":""}>&nbsp;&nbsp;否</label>&nbsp;&nbsp;&nbsp;&nbsp;
									<label><input type="radio" name="on" value="true" ${setting.regAllow?"checked":""}>&nbsp;&nbsp;是</label>
								</div>
							</div>
							<div class="col-md-6">
								<label class="help-block">是否允许用户注册</label>
							</div>
						</div>
						<div class="reg" ${setting.regAllow?"style='display:block;'":""}>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="minLengthOfPassword">最小密码长度</label> <input type="text" class="form-control" id="minLengthOfPassword" name="minLengthOfPassword" value="${setting.minLengthOfPassword}" >
									</div>
								</div>
								<div class="col-md-6">
									<label class="help-block">新用户注册时密码最小长度，0或不填为不限制</label>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label>是否开启邮箱验证</label><br>
										<label><input type="radio" name="emailOn" value="false" ${setting.needEmailVerify?"":"checked"} >&nbsp;&nbsp;否</label>&nbsp;&nbsp;&nbsp;&nbsp;
										<label><input type="radio" name="emailOn" value="true" ${setting.needEmailVerify?"checked":""} >&nbsp;&nbsp;是</label>
									</div>
								</div>
								<div class="col-md-6">
									<label class="help-block">若开启，将向用户注册 Email 发送一封验证邮件以确认邮箱的有效性</label>
								</div>
							</div>
						</div>
						<button class="btn btn-default" onclick="regSubmit()">提交</button>
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
					$(".reg").show();
				else
					$(".reg").hide();
			});
			
			$(".help").poshytip();
		});
		
	</script>
</body>
</html>
