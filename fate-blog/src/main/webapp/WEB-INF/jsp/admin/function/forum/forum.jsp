<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<title>分区设置--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="/op/base/admin/head"></c:import>
<style type="text/css">
.input-group {
	width: 100%;
}

.table thead tr td {
	font-weight: bold;
}

.del {
	height: 22px !important;
}
</style>
</head>

<body>
	<c:import url="/op/base/admin/header">
		<c:param name="header_index" value="2" />
	</c:import>
	<div class="container-fluid">
		<div class="reinforce-main region">
			<c:import url="../navi.jsp">
				<c:param name="index" value="1" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;" rel="nofollow">管理中心</a> ><a
								href="admin/forum/forumList">版块管理</a> > 版块设置
						</h5>
					</div>
					<div class="site_right"></div>
				</div>
				<div class="page">
					<c:if test="${success}">
						<form id="iconForum">
							<input type="file" name="icon" id="icon" style="display: none;"/>
						</form>
						<form id="regionForm" >
							<div class="page-header">
								<h3>
									编辑版块--${forum.forumName} <small>(fid:${forum.fid})<input type="hidden" name="fid" value="${forum.fid}"></small>
								</h3>
							</div>
							<div class="row">
								<div class="form-group col-md-6">
									<label for="forumName">版块名称</label> 
									<input type="text" class="form-control" id="forumName" placeholder="版块名称" name="forumName" value="${forum.forumName}">
								</div>
								<div class="col-md-6 help-block">
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-6">
									<label for="forumIcon">版块图标</label> 
									<input type="text" class="form-control" id="forumIcon" placeholder="版块图标" name="forumIcon" value="${forum.forumIcon}">
									<br>
									<button type="button" class="btn btn-info" onclick="uploadIcon()">选择文件</button>
									&nbsp;&nbsp;
									<img id="curIcon" src="${forum.forumIcon}" alt="...">
								</div>
								<div class="col-md-6">
									<label class="help-block">可以选择上传图片或是输入图片的URL地址</label>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-6">
									<label for="iconWidth">图标宽度（单位：px）</label> 
									<input type="text" class="form-control" id="iconWidth" placeholder="图标宽度" name="iconWidth" value="${forum.iconWidth}">
								</div>
								<div class="col-md-6">
									<label class="help-block">建议大小：174x70</label>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-6">
									<label for="parentFid">上级版块</label> 
									<select class="form-control" id="parentFid" name="parentFid">
										<c:forEach items="${forums}" var="f">
											<option value="${f.fid}" ${forum.parentForum.fid==f.fid?"selected":""}>${f.forumName}</option>
											<c:forEach items="${f.children}" var="c">
												<c:if test="${c.fid!=forum.fid}">
													<option value="${c.fid}" ${forum.parentForum.fid==c.fid?"selected":""}>--${c.forumName}</option>
												</c:if>
											</c:forEach>
										</c:forEach>
									</select>
								</div>
								<div class="col-md-8 col-xs-6 help-block">
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-6">
									<label for="forumDesc">版块描述</label> 
									<textarea class="form-control" id="forumDesc" name="forumDesc">${forum.forumDesc}</textarea>
								</div>
								<div class="col-md-8 col-xs-6 help-block">
								</div>
							</div>
							<div class="alert alert-info" role="alert">SEO设置</div>
							<div class="row">
								<div class="form-group col-md-6">
									<label for="title">title</label> 
									<input type="text" class="form-control" id="title" placeholder="" name="title" value="${forum.title}">
								</div>
								<div class="col-md-6">
									<label class="help-block">显示在浏览器标题处的title</label>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-6">
									<label for="keywords">keywords</label> 
									<input type="text" class="form-control" id="keywords" placeholder="" name="keywords" value="${forum.keywords}">
								</div>
								<div class="col-md-6">
									<label class="help-block">keywords用于搜索引擎优化，放在 meta 的 keyword 标签中，多个关键字间请用半角逗号 "," 隔开</label>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-6">
									<label for="description">description</label> 
									<textarea class="form-control" id="description" name="description" >${forum.description}</textarea>
								</div>
								<div class="col-md-6">
									<label class="help-block">description用于搜索引擎优化，放在 meta 的 description 标签中</label>
								</div>
							</div>
						</form>
					</c:if>
					<c:if test="${!success}">
						${message}
					</c:if>
					<br>
					<button class="btn btn-success" onclick="editForum()">提交</button>
				</div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script type="text/javascript">
		//版块更新
		function editForum() {
			var options = {
				url : "admin/forum/editForum",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr.success(data.msg);
						setTimeout(function() {
							location.reload();
						}, 1000);
					} else {
						toastr.error(data.msg);
					}
				},
				error : function() {
					toastr.error("通信错误");
				}
			};
			$("#regionForm").ajaxSubmit(options);
		}

		//点击上传版块图标按钮
		function uploadIcon() {
			$("#icon").click();
		}

		$(function(){
			//上传版块图
			$("#icon").change(function() {
				console.log($("#icon").val());
				var options = {
					url : "admin/forum/uploadIcon",
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							$("#forumIcon").val(data.url);
							$("#curIcon").attr("src", data.url);
						} else {
							toastr.error(data.msg);
						}
					},
					error : function() {
						toastr.error("通信错误");
					}
	
				};
				$("#iconForum").ajaxSubmit(options);
			});
		});
	</script>
</body>
</html>
