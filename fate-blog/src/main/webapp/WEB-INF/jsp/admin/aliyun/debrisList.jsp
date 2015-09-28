<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://"+ request.getServerName() + path + "/";%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>碎片整理--${setting.appName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<c:import url="/op/base/admin/head"></c:import>
<link href="js/jquery-ui-1.11.1/jquery-ui.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<c:import url="/op/base/admin/header">
	    <c:param name="header_index" value="1" />
	</c:import>
	<div class="container-fluid">
	<div class="reinforce-main">
		<c:import url="navi.jsp">
			<c:param name="index" value="3" />
		</c:import>
		<div class="reinforce-content">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;" rel="nofollow">管理中心</a> ><a href="filelist/index">文件管理</a> > 碎片管理
					</h5>
				</div>
			</div>
			<div class="page">
			<input type="hidden" id="bucketName" value="${bucketName }" /> <input
				type="hidden" id="selectedFiles">
			<table class="table table-hover" cellpadding="0" cellspacing="0">
				<thead>
					<tr class="tableHeader">
						<th style="width:30%;">文件名</th>
						<th style="width:30%;">uploadId</th>
						<th style="width:20%;">上传时间</th>
						<th style="width:10%;">碎片</th>
						<th align="right" style="min-width:175px;width:10%;">操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="list" varStatus="i">
						<tr style="height:55px;">
							<td><span title="${list.fileName }">${list.fileName}</span></td>
							<td><span title="${list.uploadId}">${list.uploadId}</span></td>
							<td><span title="${list.uploadDate}">${list.uploadDate}</span></td>
							<td><span title="${list.debrisNum}个(${list.debrisSize})">${list.debrisNum}个(${list.debrisSize})</span></td>
							<td align="right"><a href=javascript:deleteDebris("${list.fileName}","${list.uploadId}") rel="nofollow" >删除</a></td>
						</tr>

					</c:forEach>
				</tbody>
			</table>
			</div>
			<div class="clear"></div>
			</div>
		</div>

	</div>
	<c:import url="/op/base/foot"></c:import>
</body>
</html>
<script type="text/javascript" src="js/ZeroClipboard/ZeroClipboard.js"></script>
<script type="text/javascript" src="js/cloud/upload.js"></script>
<script type="text/javascript" src="js/base/navi.js"></script>
<script type="text/javascript">
  $(function(){
    $(".fileList tr td span").poshytip();
  });
  
	function deleteDebris(key, uploadId) {
		$.ajax({
			url : "debris/deleteDebris",
			type : 'post',
			dataType : 'json',
			data : {
				bucketName : "${bucketName}",
				key : key,
				uploadId : uploadId
			},
			success : function(data) {
				if (data.success){
					toastr.success("删除成功");
					setTimeout(function() {
						location.reload();
					}, 2000);
				}else{
					toastr.error("删除失败，请稍后重试");
				}
				
			},
			error : function(data) {
				alert('网络连接异常');
			}
		});

	}
</script>