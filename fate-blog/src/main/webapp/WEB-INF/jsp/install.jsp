<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">

<title>安装</title>
<!-- 使360之类的浏览器以极速模式打开网页 -->
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="http://open.reinforce.cn/Bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="http://open.reinforce.cn/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet" href="http://open.reinforce.cn/artdialog/6.0.4/css/ui-dialog.css">
<link rel="stylesheet" href="http://open.reinforce.cn/Validform/css/Validform.css">
<style type="text/css">
	.middle{width: 600px;margin: 100px auto;}
</style>

</head>

<body>
	<div class="container">
		<div class="middle">
			<div class="panel panel-default">
				<!-- Default panel contents -->
				<div class="panel-heading panel-title">
					<i class="fa fa-power-off"></i>&nbsp;&nbsp;初始化您的网站
				</div>
				<div class="panel-body">
					<form class="form-horizontal" id="installForm">
						<div class="form-group">
							<label for="appName" class="col-sm-3 control-label">网站名称</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="appName" name="appName" placeholder="您的网站名称" datatype="*" nullmsg="请填写您的网站名称" sucmsg="&nbsp;">
							</div>
							<div class="col-sm-4">
								<span class="Validform_checktip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="url" class="col-sm-3 control-label">网站URL</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="url" name="url" placeholder="您网站的域名"  datatype="*" nullmsg="请填写您的网站域名" sucmsg="&nbsp;">
							</div>
							<div class="col-sm-4">
								<span class="Validform_checktip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="nickName" class="col-sm-3 control-label">管理员昵称</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="nickName" name="nickName" placeholder="您的昵称"  datatype="*" nullmsg="请填写您的昵称" sucmsg="&nbsp;">
							</div>
							<div class="col-sm-4">
								<span class="Validform_checktip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="email" class="col-sm-3 control-label">管理员邮箱</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="email" name="email" placeholder="您的邮箱"  datatype="e" nullmsg="请填写您的管理员邮箱" errormsg="邮箱格式不正确" sucmsg="&nbsp;">
							</div>
							<div class="col-sm-4">
								<span class="Validform_checktip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-sm-3 control-label">管理员密码</label>
							<div class="col-sm-5">
								<input type="password" class="form-control" id="password" name="pwd" datatype="*" nullmsg="请填写您的管理员密码" sucmsg="&nbsp;">
							</div>
							<div class="col-sm-4">
								<span class="Validform_checktip"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-sm-3 control-label">再次输入密码</label>
							<div class="col-sm-5">
								<input type="password" class="form-control" id="cfmPassword" recheck="pwd" datatype="*" nullmsg="请填写您的管理员密码" sucmsg="&nbsp;">
							</div>
							<div class="col-sm-4">
								<span class="Validform_checktip"></span>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-9">
								<button type="submit" class="btn btn-default" id="submitBtn">保存</button>
							</div>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>
	<script type="text/javascript" src="http://open.reinforce.cn/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="http://open.reinforce.cn/Bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="http://open.reinforce.cn/artdialog/6.0.4/js/dialog-plus-min.js"></script>
<script type="text/javascript" src="http://open.reinforce.cn/jquery/jquery.md5.min.js"></script>
<script type="text/javascript" src="http://open.reinforce.cn/jquery/jquery.form.min.js"></script>
<script src="http://open.reinforce.cn/Validform/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
	function submitForm() {
		$("#submitBtn").button('loading');
			var options = {
				url : "install/update",
				type : 'post',
				dataType : 'json',
				data : {
					password : $.md5($("#password").val())
				},
				success : function(data) {
					if (data.success) {
						
					var d = dialog({
						title : '提示',
						content : "站点创建成功",
						width : '200px',
						okValue : '确定',
						ok : function() {
							location.href = "/"
						}
					});
					d.showModal();
				} else{
					$("#submitBtn").button('reset');
					dialog.alert(data.msg);
				}
					
			},
			error : function(data) {
				console.log(data);
			}

		};
		$("#installForm").ajaxSubmit(options);
	}
	$("#installForm")
					.Validform(
							{
								btnReset : "",
								tiptype : function(msg, o, cssctl) {
									switch (o.type) {
									case 1:
										$(o.obj).parent().next().children(
											".Validform_checktip").removeClass(
											"Validform_right").removeClass(
											"Validform_wrong").html(msg);
										$(o.obj).parent().parent().removeClass(
												"has-error").removeClass(
												"has-success");
										break;
									case 2:
										$(o.obj).parent().next().children(
											".Validform_checktip").addClass(
											"Validform_right").html(msg);
										$(o.obj).parent().parent().removeClass(
												"has-error").addClass(
												"has-success");
										break;
									case 3:
										$(o.obj).parent().next().children(
											".Validform_checktip").addClass(
											"Validform_wrong").html(msg);
										$(o.obj).attr("placeholder", msg).parent().parent().removeClass(
												"has-success").addClass(
												"has-error");
										break;

									default:
										break;
									}

								},
								ignoreHidden : true,
								dragonfly : false,
								tipSweep : false,
								label : ".label",
								showAllError : true,
								postonce : false,
								datatype : {
								},
								usePlugin : {},
								beforeCheck : function(curform) {
									//在表单提交执行验证之前执行的函数，curform参数是当前表单对象。
									//这里明确return false的话将不会继续执行验证操作;	
								},
								beforeSubmit : function(curform) {
									//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
									//这里明确return false的话表单将不会提交;	
								},
								callback : function(data) {
									//返回数据data是json对象，{"info":"demo info","status":"y"}
									//info: 输出提示信息;
									//status: 返回提交数据的状态,是否提交成功。如可以用"y"表示提交成功，"n"表示提交失败，在ajax_post.php文件返回数据里自定字符，主要用在callback函数里根据该值执行相应的回调操作;
									//你也可以在ajax_post.php文件返回更多信息在这里获取，进行相应操作；
									//ajax遇到服务端错误时也会执行回调，这时的data是{ status:**, statusText:**, readyState:**, responseText:** }；

									//这里执行回调操作;
									//注意：如果不是ajax方式提交表单，传入callback，这时data参数是当前表单对象，回调函数会在表单验证全部通过后执行，然后判断是否提交表单，如果callback里明确return false，则表单不会提交，如果return true或没有return，则会提交表单。
									submitForm();
									return false;
								}
							});
</script>
</body>
</html>
