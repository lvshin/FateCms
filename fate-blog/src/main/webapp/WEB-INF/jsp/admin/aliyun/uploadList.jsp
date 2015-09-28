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

<title>任务管理</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<c:import url="/op/base/admin/head"></c:import>
</head>

<body>
	<c:import url="/op/base/admin/header">
	    <c:param name="header_index" value="1" />
	</c:import>
	<link href="js/jquery-ui-1.11.1/jquery-ui.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="js/artDialog/css/ui-dialog.css">
	<div class="main row">
		<c:import url="navi.jsp">
			<c:param name="index" value="5" />
		</c:import>

		<div class="right col-md-10">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;" rel="nofollow">祝福之风后台</a> ><a href="filelist/index">文件管理</a> > 任务管理
					</h5>
				</div>
			</div>
			<input type="hidden" id="bucketName" value="${bucketName }" /> <input
				type="hidden" id="selectedFiles">
			<table class="fileList" cellpadding="0" cellspacing="0">
				<thead>
					<tr class="tableHeader">
						<td style=";max-width:60%;">文件名</td>
						<td style="width:75px;">大小</td>
						<td style="width:620px;">状态</td>
						<td align="right" style="min-width:175px;width:10%;">操作&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="list" varStatus="i">
						<tr>
							<td><span title="${list.fileName }">${list.fileName }</span></td>
							<td>${list.size}</td>
							<td>
							    <div><strong>读取中。。。</strong></div>
								<div class="progress">
									<div class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" aria-valuenow="${list.percentDone}"
										aria-valuemin="0" aria-valuemax="100" style="width: ${list.percentDone}%;">
										${list.percentDone}%</div>
								</div>
							</td>
							<td align="right">
							<c:if test="${list.state!=3 }">
							<span style="margin-left:6px;"><a href="javascript:	void(0)');" rel="nofollow">取消上传</a></span>
							</c:if>
							</td>
						</tr>

					</c:forEach>
				</tbody>
			</table>
			<div class="clear"></div>

		</div>

	</div>
	<c:import url="/op/base/footer"></c:import>
<script type="text/javascript" src="js/base/navi.js"></script>
<script type="text/javascript" src="js/cloud/uploadList.js"></script>
<script type="text/javascript" src="js/ZeroClipboard/ZeroClipboard.js"></script>
<script type="text/javascript" src="js/cloud/upload.js"></script>
</body>
</html>

