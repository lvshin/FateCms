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

<title>添加公告--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="/op/base/admin/head"></c:import>
<link rel="stylesheet" href="http://open.reinforce.cn/Bootstrap/datetimepicker/bootstrap-datetimepicker.min.css">
<style type="text/css">
.input-group {
	width: 100%;
}

#contentEditer {
	width: 100%;
	height: 400px;
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
				<c:param name="index" value="4" />
			</c:import>
			<div class="reinforce-content">
				<div class="site">
					<div class="site_left">
						<h5>
							<a href="javascript:;" rel="nofollow">管理中心</a> ><a
								href="admin/announcement/list">公告发布</a> > ${operation}公告
						</h5>
					</div>
				</div>
				<div class="page">
					<form id="announcementForm" class="form-horizontal mg-t-10">
						<div class="form-group">
							<label for="title" class="col-sm-1 control-label"><span class="red">*</span>标题</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="title" placeholder="公告标题" name="title" value="${announcement.title}" maxlength="50">
								<input type="hidden" name="id" value="${announcement==null?0:announcement.id}">
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group">
							<label for="startTime" class="col-sm-1 control-label">开始时间</label>
							<div class="col-sm-9">
								<input type="text" class="form-control time-picker" id="startTime" name="startTime" value="${announcement.startTime}" placeholder="不填则不限日期">
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group">
							<label for="endTime" class="col-sm-1 control-label">结束时间</label>
							<div class="col-sm-9">
								<input type="text" class="form-control time-picker" id="endTime" name="endTime" value="${announcement.endTime}" placeholder="同上">
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group">
							<label for="displayOrder" class="col-sm-1 control-label"><span class="red">*</span>显示顺序</label>
							<div class="col-sm-9">
								<input type="text" class="form-control int-only" id="displayOrder" placeholder="显示顺序" name="displayOrder" value="${announcement==null?0:announcement.displayOrder}">
								<input type="hidden" name="id" value="${announcement==null?0:announcement.id}">
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group">
							<label for="endTime" class="col-sm-1 control-label"><span class="red">*</span>公告内容</label>
							<div class="col-sm-9">
								<textarea class="form-control" id="content" name="content" rows="8">${announcement.content}</textarea>
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-1 col-sm-11">
								<button type="button" class="btn btn-primary" onclick="addAnnouncement()">发布</button>
								<button type="button" class="btn btn-default" onclick="location.href='admin/announcement/list'">返回</button>
							</div>
						</div>
					</form>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<c:import url="/op/base/foot"></c:import>
	<script src="kindeditor/kindeditor-all-min.js"></script>
	<script src="kindeditor/lang/zh_CN.js"></script>

	<script src="http://open.reinforce.cn/Bootstrap/datetimepicker/bootstrap-datetimepicker.min.js"></script>
	<script src="http://open.reinforce.cn/Bootstrap/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"  charset="UTF-8"></script>
	<script type="text/javascript">
		$(function() {
			$(".time-picker").datetimepicker({
				format : "yyyy-mm-dd hh:ii:ss",
				autoclose : true,
				todayBtn : true,
				todayHighlight : true,
				language : 'zh-CN'
			});

			KindEditor
					.ready(function(K) {
						var editor = K
								.create(
										"#content",
										{
											uploadJson : '/file/uploadImg',
											fileManagerJson : 'file/filelist',
											allowFileManager : true,
											htmlTags : {
												font : [ 'color', 'size',
														'face',
														'.background-color' ],
												span : [ '.color',
														'.background-color',
														'.font-size',
														'.font-family',
														'.background',
														'.font-weight',
														'.font-style',
														'.text-decoration',
														'.vertical-align',
														'.line-height' ],
												div : [ 'align', '.border',
														'.margin', '.padding',
														'.text-align',
														'.color',
														'.background-color',
														'.font-size',
														'.font-family',
														'.font-weight',
														'.background',
														'.font-style',
														'.text-decoration',
														'.vertical-align',
														'.margin-left', 'id',
														'class', 'data-date',
														'data-date-format' ],
												table : [ 'border',
														'cellspacing',
														'cellpadding', 'width',
														'height', 'align',
														'bordercolor',
														'.padding', '.margin',
														'.border', 'bgcolor',
														'.text-align',
														'.color',
														'.background-color',
														'.font-size',
														'.font-family',
														'.font-weight',
														'.font-style',
														'.text-decoration',
														'.background',
														'.width', '.height',
														'.border-collapse' ],
												'td,th' : [ 'align', 'valign',
														'width', 'height',
														'colspan', 'rowspan',
														'bgcolor',
														'.text-align',
														'.color',
														'.background-color',
														'.font-size',
														'.font-family',
														'.font-weight',
														'.font-style',
														'.text-decoration',
														'.vertical-align',
														'.background',
														'.border' ],
												a : [ 'href', 'target', 'name',
														'id', 'data-*' ],
												embed : [ 'src', 'width',
														'height', 'type',
														'loop', 'autostart',
														'quality', '.width',
														'.height', 'align',
														'allowscriptaccess' ],
												img : [ 'src', 'width',
														'height', 'border',
														'alt', 'title',
														'align', '.width',
														'.height', '.border' ],
												'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : [
														'align', '.text-align',
														'.color',
														'.background-color',
														'.font-size',
														'.font-family',
														'.background',
														'.font-weight',
														'.font-style',
														'.text-decoration',
														'.vertical-align',
														'.text-indent',
														'.margin-left' ],
												'pre,span' : [ 'class' ],
												i : [ 'class' ],
												property : [ 'name', 'value',
														'ref' ],
												bean : [
														'id',
														'class',
														'p:connection-factory-ref',
														'/' ],
												hr : [ 'class',
														'.page-break-after' ],
												'script,iframe' : [ 'src',
														'style' ],
												'br,tbody,tr,strong,b,sub,sup,em,u,strike,s,del,code' : [],
												'dt,dd' : [ 'style' ],
												'audio,video' : [ 'src',
														'preload', 'loop',
														'controls', 'autoplay',
														'height', 'width' ],
												'button,input' : [ 'class',
														'id', 'size', 'type',
														'value', 'placeholder',
														'onclick' ],
												link : [ 'rel', 'href' ]
											},
											resizeType : 1,
											urlType : "domain",
											afterBlur : function(e) {
												$('#content')
														.val(editor.html());
											},
											afterChange : function() {
												$('.word_count1').html(
														this.count()); //字数统计包含HTML代码
											}

										});
						html = editor.html();

						// 同步数据后可以直接取得textarea的value
						editor.sync();
						html = $('#content').val(); // jQuery

						// 设置HTML内容
						//editor.html('HTML内容');
					});
		});

		/**
		 * 添加公告
		 */
		function addAnnouncement() {
			var options = {
				url : "admin/announcement/submit",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						toastr.success(data.msg);
						location.href = "admin/announcement/list";
					} else {
						toastr.error(data.msg);
					}
				},
				error : function() {
					dialog.alert("与服务器的通信异常，请稍后再试");
				}

			};
			$("#announcementForm").ajaxSubmit(options);
		}
	</script>
</body>
</html>
