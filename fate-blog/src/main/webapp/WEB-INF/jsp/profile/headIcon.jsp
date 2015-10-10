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

<title>${userSession.user.nickName}--头像修改</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="keywords" content="${userSession.user.nickName}的个人资料">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description"
	content="${userSession.user.nickName}--头像修改,${setting.appName}">
<link rel="stylesheet" type="text/css"
	href="css/Validform/datePicker-min.css">
<link rel="stylesheet" type="text/css" href="js/croppic/croppic.css">
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
					<c:param name="index" value="2" />
				</c:import>
				<div class="col-md-10">
					<div class="title">
						<span><strong>修改头像</strong></span>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div id="croppic"></div>
							<div class="btns">
								<button class="btn btn-success" onclick="updateHeadIcon()">保存</button>
								<button class="btn btn-default">重置</button>
							</div>
						</div>

						<div class="col-md-6 preview">
							<h4>头像预览</h4>
							<img src="images/default_headicon.png" class="img-rounded"
								width=140>
							<div class="help-block">140x140</div>
							<img src="images/default_headicon.png" class="img-rounded"
								width=80>
							<div class="help-block">80x80</div>
							<img src="images/default_headicon.png" class="img-rounded"
								width=40>
							<div class="help-block">40x40</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	<c:import url="/op/footer"></c:import>
	<script type="text/javascript" src="js/croppic/croppic.min.js"
			charset="utf-8"></script>
	<script type="text/javascript">
		$(function() {
			var croppicHeaderOptions = {
				uploadUrl : 'profile/uploadImg',
				cropUrl : 'profile/cropImg',
				modal : false,
				loaderHtml : '<div class="loader bubblingG"><span id="bubblingG_1"></span><span id="bubblingG_2"></span><span id="bubblingG_3"></span></div> ',
				onBeforeImgUpload : function() {
					console.log('onBeforeImgUpload')
				},
				onAfterImgUpload : function() {
					console.log('onAfterImgUpload')
				},
				onImgDrag : function() {
					console.log('onImgDrag')
				},
				onImgZoom : function() {
					console.log('onImgZoom')
				},
				onBeforeImgCrop : function() {
					console.log('onBeforeImgCrop')
				},
				onAfterImgCrop : function() {
					console.log('onAfterImgCrop');
					$(".preview").children("img").attr("src",$(".croppedImg").attr("src"));
				}
			}
			var croppic = new Croppic('croppic', croppicHeaderOptions);
		});

		function updateHeadIcon() {
			console.log($(".croppedImg").attr("src"));
			$.ajax({
				url : "profile/updateHeadIcon",
				type : 'post',
				dataType : 'json',
				data : {
					url : $(".croppedImg").attr("src")
				},
				success : function(data) {
					if (data.success) {
						location.reload();
					} else {
						var d2 = dialog({
							content : data.msg
						});
						d2.show();
						setTimeout(function() {
							d2.close().remove();
						}, 1000);
					}
				}
			});
		}
	</script>
</body>
</html>
