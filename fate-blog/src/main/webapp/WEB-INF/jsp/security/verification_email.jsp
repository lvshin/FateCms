<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

<title>第三方帐号注册</title>

<c:import url="/op/head"></c:import>
<link rel="stylesheet" type="text/css" href="css/login.css" />


</head>

<body>
	<div class="login_logo">
		<div class="logo_left"></div>
		<div class="logo_right"></div>

	</div>
	<div class="login">
			<div class="form">
				<div class="item" style="width:300px;margin:10% auto;">
					<span id="res"></span>
				</div>
				<div class="item" style="width:45px;height:45px;margin:0 auto;">
					<c:import url="/op/base/loading?size=10"></c:import>
				</div>
			</div>
		</div>
		<c:import url="/op/footer"></c:import>
		<script type="text/javascript">

	$(function() {
	    $(".spinner").css("display","inline-block");
		$.ajax({
			type : "post",
			url : "op/security/verification/sendEmail",
			dataType : 'json',
			data : {
				uid : "${uid}",
				toMails : "${email}"
			},
			error : function() {
				alert("通信错误");
			},
			success : function(data) {
			  if(data.success){
			    var d = dialog({
	            		content: "验证邮件已经发送到您的邮箱，请在12小时内确认",
	            		okValue: '确定',
                        ok: function () {
                          $("#res").html("验证邮件已经发送到您的邮箱，请在12小时内确认");
                          $(".spinner").css("display","none");
                          return true;
                        }
	            			    });
	            d.showModal();
			  }
			  else{
			     if(data.error_code==0){
			        var d = dialog({
	            		content: "发生系统内部错误",
	            		okValue: '确定',
                        ok: function () {
                          return true;
                        }
	            			    });
	                d.show();
			     }
			     else if(data.error_code==1){
			        var d = dialog({
	            		content: "请勿重复发送",
	            		okValue: '确定',
                        ok: function () {
                          return true;
                        }
	            			    });
	                d.showModal();
			     }
			  }
			}
		});

	});
</script>
</body>
</html>