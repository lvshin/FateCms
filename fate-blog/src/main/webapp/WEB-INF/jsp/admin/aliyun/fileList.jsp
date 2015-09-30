<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://"+ request.getServerName() + path + "/";%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>云存储控制台</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<c:import url="/op/base/admin/head"></c:import>

<script type="text/javascript" src="js/cloud/upload.js"></script>
</head>

<body>
	<c:import url="/op/base/admin/header">
	    <c:param name="header_index" value="1" />
	</c:import>
	<div class="container-fluid">
	<div class="reinforce-main">
		<c:import url="navi.jsp">
			<c:param name="index" value="2" />
		</c:import>
		<div class="reinforce-content">
			<div class="site">
				<div class="site_left">
					<h5>
						<a href="javascript:;" rel="nofollow">管理中心</a> ><a href="admin/aliyun/filelist">文件管理</a> > 文件列表
					</h5>
					
				</div>
			</div>
			<div class="page">
				<ol class="breadcrumb">
					<li><span class="file-icon-small icon-small-home" style="margin-right:7px;"></span></li>
					<c:forEach items="${dirList}" var="list" varStatus="i">
						<c:if test="${i.first }">
							<c:if test="${!i.last||keyword!='' }">
									<li><a href="admin/aliyun/filelist">根目录</a></li>
							</c:if>
							<c:if test="${i.last&&keyword=='' }">
									<li class="active"><strong>根目录</strong></li>
							</c:if>
						</c:if>
						<c:if test="${!i.first }">
							<c:if test="${!i.last||keyword!='' }">
					  			<li><a href="admin/aliyun/filelist?dir=${pDirList[i.index]}">${list}</a></li>
							</c:if>
							<c:if test="${i.last&&keyword=='' }">
								<li class="active"><strong>${list}</strong></li>
							</c:if>
						</c:if>
					</c:forEach>
					<c:if test="${keyword!=''}">
						<li class="active"><strong>搜索“${keyword}”</strong></li>
					</c:if>
				</ol>
				<div class="form-inline btns">
				<button class="btn btn-success" onclick="$('#uploadButton').click();">
					<span class="fa fa-cloud-upload"> </span> 上传文件
				</button>
					<form id="multipartUploadForm" action="" method="post" enctype="multipart/form-data" style="display:none;">
						<input type="file" name="uploadFile" id="uploadButton" onchange="doMultipartUpload()" style="display:none;"/>
					</form>
					<button class="btn btn-primary" onclick="newDir();">
						<span class="fa fa-plus"> </span> 新建文件夹
					</button>
					<button class="btn btn-default" onclick="location.reload();">
						<span class="fa fa-refresh"> </span> 刷新
					</button>
					<input class="form-control" type="text" id="keyword" size="36" placeholder="搜索当前文件夹下的文件"  value="${keyword}" />
					<button class="btn btn-default" onclick="search()">
						<span class="glyphicon glyphicon-search"> </span> 搜索
					</button>
				</div>
			<input type="hidden" id="curFolder" value="${folder}" /> <input
				type="hidden" id="bucketName" value="${bucketName }" /> <input
				type="hidden" id="selectedFiles">
			<table class="table table-hover">
				<thead>
					<tr class="tableHeader">
						<th style="width:3%"><input type="checkbox" id="selectedAll" value="" style="margin-left:20%"></th>
						<th style=";max-width:60%;">文件名</th>
						<th style="width:75px;">大小</th>
						<th style="width:65px;">类型</th>
						<th style="width:151px;">上传时间</th>
						<th align="right" style="min-width:175px;width:20%;">操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="list" varStatus="i">
						<tr>
							<td><c:if test="${list.type!='文件夹'&&list.type!='back' }">
									<input type="checkbox" value="${list.fileName }"
										style="margin-left:20%">
								</c:if></td>
							<td><a href="${list.url }" title="${list.showName }" rel="nofollow" data-toggle="tooltip" data-placement="top" ><i class="file-icon-small icon-small-${list.type}"></i>&nbsp;&nbsp;${list.showName}</a></td>
							<td>${list.fileSize}</td>
							<td>${list.type=='back'?"":list.type}</td>
							<td><span title='${list.uploadDate}' data-toggle="tooltip" data-placement="top" >${list.uploadDate}</span></td>
							<td align="right"><c:if test="${list.type!='back'}">
									<c:if test="${list.type!='文件夹'}">
										<span><a
											href=javascript:getOutUrl('${bucketName}','${list.fileName}') rel="nofollow">获取外链</a></span>
										<span style="margin-left:6px;"><a
											href="file/getfile/${bucketName }/${list.fileName }?" rel="nofollow">下载</a></span>
									</c:if>
									<span style="margin-left:6px;"><a
										href="javascript:deleteObject('${bucketName }','${list.fileName }','${list.type=='文件夹'?1:0 }');" rel="nofollow">删除</a></span></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
			<div class="div-fixed">
				<div class="pull-left">
					<button onclick="batchDeleteObject('${bucketName }')"
						class="btn btn-default" disabled="disabled">批量删除</button>
					<button onclick="unSelectAll()" class="btn btn-default"
						disabled="disabled">取消选择</button>
				</div>
			</div>
			<div id='a1'></div>
			<div class="clear"></div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
</body>
</html>
<script type="text/javascript" src="js/ZeroClipboard/ZeroClipboard.js"></script>
<script type="text/javascript" src="js/cloud/cloud.js"></script>
<script type="text/javascript">
	function play(url) {
		var flashvars={
        f:url,
        c:0,
        loaded:'loadedHandler'
    };
    var video=[url];
    CKobject.embed('ckplayer/ckplayer.swf','a1','ckplayer_a1','600','400',false,flashvars,video);
	}
</script>