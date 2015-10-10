<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<title>${userSession.user.nickName}的个人资料</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="keywords" content="${userSession.user.nickName}的个人资料">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description"
	content="${userSession.user.nickName}的个人资料,${setting.appName}">
<link rel="stylesheet" type="text/css"
	href="css/Validform/datePicker-min.css">
<link rel="stylesheet" type="text/css" href="css/Validform/style.css">
<link rel="stylesheet" type="text/css" href="css/Validform.css">
<link rel="stylesheet" href="http://open.reinforce.cn/Bootstrap/datetimepicker/bootstrap-datetimepicker.min.css">
<c:import url="/op/head"></c:import>
</head>

<body>
	
		<!-- 头部导航条 -->
		<c:import url="/op/header">
			<c:param name="header_index" value="1" />
		</c:import>
		<div class="container">
		<div class="profile">
			<div class="row">
			<c:import url="navi.jsp">
				<c:param name="index" value="1" />
			</c:import>
			<div class=" col-md-10 col-xs-12">
			<div class="title">
				<span><strong>${userSession.user.nickName}的个人资料</strong></span>
			</div>
			<div class="info">
				<form class="form" id="infoForm" method="post">
					<div class="row">
						<div class="col-md-7 col-xs-10">
							<div class="input-group">
								<span class="input-group-addon">电子邮箱</span> <input type="text"
									class="form-control" placeholder="email"
									value="${userSession.user.email}" readonly />
							</div>
						</div>
						<div class="col-md-2 col-xs-2">
							<span class="help"><button type="button" class="btn btn-warning">修改</button></span>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-7 col-xs-10">
							<div class="input-group">
								<span class="input-group-addon">手机号</span> <input type="text"
									class="form-control" placeholder="mobile"
									value="${userSession.user.mobile}" readonly />
							</div>
						</div>
						<div class="col-md-2 col-xs-2">
							<span class="help"><button type="button" class="btn btn-warning">修改</button></span>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-5">
							<div class="input-group">
								<span class="input-group-addon" id="basic-addon1">性别</span> <select
									class="form-control" name="sex">
									<!-- 这里为什么是109和119呢，是因为char类型被当作Long处理了，而字符m的ascii码为109,w为119 -->
									<option value="m" ${userSession.user.sex==109?"selected":""}>男</option>
									<option value="w" ${userSession.user.sex==119?"selected":""}>女</option>
								</select>
							</div>
						</div>
						<div class="col-md-7">
							<span class="Validform_checktip help"></span>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-7">
							<div class="input-group">
								<span class="input-group-addon">生日</span> <input type="text"
									class="form-control time-picker" placeholder="birthday" name="birthday"
									datatype="date" errormsg="请输入正确的日期，格式：1900-01-01"
									sucmsg="&nbsp;" ignore="ignore" 
									 type="date" pattern="yyyy-MM-dd"/>
							</div>
						</div>
						<div class="col-md-5">
							<span class="Validform_checktip help"></span>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-7">
							<div class="input-group">
								<span class="input-group-addon">家庭地址</span> <input type="text"
									class="form-control" placeholder="address" name="address"
									value="${userSession.user.address}">
							</div>
						</div>
						<div class="col-md-5">
							<span class="Validform_checktip help"></span>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-5">
							<div class="input-group">
								<span class="input-group-addon">血型</span> <select
									class="form-control" name="blood">
									<option value="A" ${userSession.user.blood=="A"?"selected":""}>A</option>
									<option value="B" ${userSession.user.blood=="B"?"selected":""}>B</option>
									<option value="AB" ${userSession.user.blood=="AB"?"selected":""}>AB</option>
									<option value="O" ${userSession.user.blood=="O"?"selected":""}>O</option>
									<option value="other" ${userSession.user.blood=="other"?"selected":""}>其他</option>
								</select>
							</div>
						</div>
						<div class="col-md-7">
							<span class="Validform_checktip help"></span>
						</div>
					</div>
					<br>
					<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label for="headicon">头像</label>
							<div id="headicon">
								<img class="img-rounded" src="${userSession.user.headIconLocal==null||userSession.user.headIconLocal==''?"images/default_headicon.png":userSession.user.headIconLocal}" width="40"/>&nbsp;<input id="local" type="radio" name="headIcon" value="0" ${userSession.user.headIconUsed==0?"checked":""} /><label for="local">本地头像</label>
								<img class="img-rounded" src="${qq==''?"images/default_headicon.png":qq}" width="40" />&nbsp;<input id="qq" type="radio" name="headIcon" value="1" ${userSession.user.headIconUsed==1?"checked":""} /><label for="qq">QQ头像</label>
								<img class="img-rounded" src="${weibo==''?"images/default_headicon.png":weibo}" width="40" />&nbsp;<input id="weibo" type="radio" name="headIcon" value="2" ${userSession.user.headIconUsed==2?"checked":""} /><label for="weibo">微博头像</label>
							</div>
						</div>
					</div>
				</div>
					<br>
					<div class="btns">
						<button type="submit" class="btn btn-info" id="infoSubmit">保存</button>
					</div>
				</form>
			</div>
			</div>
			</div>
		</div>
		<c:import url="/op/footer"></c:import>
	</div>
	<script type="text/javascript"
			src="js/Validform/Validform_v5.3.2_min.js" charset="utf-8"></script>
		<script type="text/javascript"
			src="js/Validform/jquery.datePicker-min.js" charset="utf-8"></script>
	<script src="http://open.reinforce.cn/Bootstrap/datetimepicker/bootstrap-datetimepicker.min.js"></script>
	<script src="http://open.reinforce.cn/Bootstrap/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"  charset="UTF-8"></script>
		
	<script type="text/javascript">
		$(function() {
			var form = $("#infoForm").Validform(
					{
						btnSubmit : "#infoSubmit",
						btnReset : "",
						tiptype : function(msg, o, cssctl) {
							switch (o.type) {
							case 1:
								$(o.obj).parent().parent().next().children(
										".Validform_checktip").removeClass("Validform_right").removeClass("Validform_wrong").html(msg);
								$(o.obj).parent().removeClass("has-error").removeClass("has-success");
								break;
							case 2:
								$(o.obj).parent().parent().next().children(
										".Validform_checktip").addClass(
										"Validform_right").html(msg);
								$(o.obj).parent().removeClass("has-error").addClass("has-success");
								break;
							case 3:
								$(o.obj).parent().parent().next().children(
										".Validform_checktip").addClass(
										"Validform_wrong").html(msg);
								$(o.obj).parent().removeClass("has-success").addClass("has-error");
								break;

							default:
								break;
							}

						},
						ignoreHidden : true,
						dragonfly : true,
						tipSweep : false,
						label : ".label",
						showAllError : true,
						postonce : true,
						datatype : {
							"qq" : /^\d{5,10}$/,
							"date" : /^\d{4}-\d{2}-\d{2}$/

						},
						usePlugin : {

						},
						beforeCheck : function(curform) {
							//在表单提交执行验证之前执行的函数，curform参数是当前表单对象。
							//这里明确return false的话将不会继续执行验证操作;	
						},
						beforeSubmit : function(curform) {
							//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
							//这里明确return false的话表单将不会提交;	
						},
						callback : function(data) {
						console.log(11);
							//返回数据data是json对象，{"info":"demo info","status":"y"}
							//info: 输出提示信息;
							//status: 返回提交数据的状态,是否提交成功。如可以用"y"表示提交成功，"n"表示提交失败，在ajax_post.php文件返回数据里自定字符，主要用在callback函数里根据该值执行相应的回调操作;
							//你也可以在ajax_post.php文件返回更多信息在这里获取，进行相应操作；
							//ajax遇到服务端错误时也会执行回调，这时的data是{ status:**, statusText:**, readyState:**, responseText:** }；

							//这里执行回调操作;
							//注意：如果不是ajax方式提交表单，传入callback，这时data参数是当前表单对象，回调函数会在表单验证全部通过后执行，然后判断是否提交表单，如果callback里明确return false，则表单不会提交，如果return true或没有return，则会提交表单。
							submitInfo();
							return false;
						}
					});
			$('.time-picker').datetimepicker({
    			format: 'yyyy-mm-dd hh:ii:ss',
    			autoclose : true,
    			todayBtn : true,
    			todayHighlight : true,
    			language : 'zh-CN'
			});
		});
		function submitInfo() {
			var options = {
				url : "profile/updateBasicInfo",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					var d = dialog({
						content : data.message
					});
					d.showModal();
					if (data.success) {
						setTimeout(function() {
							location.reload();
						}, 1500);
					} else {
						setTimeout(function() {
							d.close().remove();
						}, 1500);
					}
				},
				error : function() {
					dialog.alert("通信异常");
				}
			};
			$("#infoForm").ajaxSubmit(options);
			return false;
		}
	</script>
</body>
</html>
