<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>${theme==null?"发布主题":theme.title}--${setting.siteName}</title>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="keywords" content="">
<meta name="description" content="">
<c:import url="/op/head"></c:import>
<link rel="stylesheet" href="http://open.reinforce.cn/Bootstrap/datetimepicker/bootstrap-datetimepicker.min.css">
<style type="text/css">
#contentEditer {
	width: 100%;
	height: 400px;
}
.medias{display: none;}
</style>

</head>

<body>
	<div class="container">
		<!-- 头部导航条 -->
		<c:import url="/op/header">
			<c:param name="header_index" value="1" />
		</c:import>
		<ol class="breadcrumb">
			<li><a href="javascript:;" rel="nofollow"><span
					class="glyphicon glyphicon-home"></span></a></li>
			<li><a href="/">首页</a></li>
			<li><a href="op/forum-${forum.fid}-1.html">${forum.forumName}</a></li>
			<li class="active">${theme.title}</li>
		</ol>
		<div class="main">
			<div class="panel panel-default">
				<div class="panel-body">
					<c:if test="${success}">
						<form id="themeForm">
							<div class="form-group">
								<label for="title">标题</label>
								<input type="text" class="form-control" id="title" name="title" value="${theme.title}" datatype="*" nullmsg="请填写标题" maxlength="50"> 
								<input type="hidden" name="guid" value="${theme.guid}">
								<input type="hidden" name="fid" value="${forum.fid}">
							</div>
							<div class="form-group">
								<label for="title">发布时间</label> 
								<input type="text" class="form-control time-picker" id="publishDate" name="publishDate" value="<fmt:formatDate value='${theme.publishDate}' pattern='yyyy-MM-dd HH:mm:ss' />" placeholder="提供可选择的发布日期">
							</div>
							<div class="form-group">
								<label for="active">主题类型</label> 
								<br>
								<input type="radio" id="typeNormal" name="type" value="0" ${theme==null?"checked":(theme.type==0?"checked":"")}><label for="typeNormal" style="font-weight: normal;">图文主题</label>
								<input type="radio" id="typeAudio" name="type" value="1" ${theme.type==1?"checked":""}><label for="typeAudio" style="font-weight: normal;">音频主题</label>
								<input type="radio" id="typeVideo" name="type" value="2" ${theme.type==2?"checked":""}><label for="typeVideo" style="font-weight: normal;">视频主题</label>
							</div>
							<div class="medias row">
								<c:if test="${theme.type==2}">
									<c:forEach items="${theme.medias}" var="media" >
										<div class="col-md-6">
											<input class="form-control url pull-left" type="text" name="files" value="${media.url}" />
										</div>
									</c:forEach>
								</c:if>
								<c:if test="${theme.type==1}">
									<c:forEach items="${theme.medias}" var="media" >
										<div class="col-lg-2">
											<input class='form-control atitle pull-left' type='text' name='titles' placeholder='文件名' value="${media.title}" datatype="*" nullmsg="请填写文件名"/>
										</div>
										<div class="col-lg-4">
											<input class='form-control url pull-left' type='text' name='files' placeholder='文件地址' value='${media.url}' datatype="*" nullmsg="请填写文件地址"/>
										</div>
										<div class="col-lg-2">
											<input class='form-control singer pull-left' type='text' name='singers' placeholder='歌手' value="${media.singer}" datatype="*" nullmsg="请填写歌手"/>
										</div>
										<div class="col-lg-3">
											<input class='form-control time pull-left' type='text' name='times'  placeholder='播放时间' value="${media.lastTime}" datatype="*" nullmsg="请填写播放时长"/>
										</div>
										<div class="col-lg-1">
										</div>
										<br>
									</c:forEach>
								</c:if>
								<div class="col-lg-12 mg-t-10">
								<a href='javascript:;' class='add newUrl' rel='nofollow' onclick="newVideoFile()">
										<span class='glyphicon glyphicon-plus'></span>
										&nbsp;添加媒体链接
								</a>
								</div>
							</div>
							<div class="form-group">
								<label for="contentEditer">内容</label>
								<textarea class="form-control" id="contentEditer" name="content">${theme.content}</textarea>
								<p>
									您当前输入了 <span class="word_count1">0</span> 个文字。（字数统计包含HTML代码。）
									<br />
								</p>
							</div>
							<div class="form-group">
								<label for="title">标签(以英文的,分隔)</label> 
								<input type="text"class="form-control" id="tags" name="tags" value="${theme.tags}" maxlength="50">
							</div>
							
							<button type="submit" class="hidden" id="submitBtn"></button>
							
							<div class="btns pull-right">
								<button type="button" class="btn btn-primary"
									onclick="submitTheme('2',this)" data-loading-text="提交中...">${theme.state!=2?"发布":"保存"}</button>
								<c:if test="${theme.state!=2}">
									<button type="button" class="btn btn-success"
										onclick="submitTheme('1',this)" data-loading-text="提交中...">草稿</button>
								</c:if>
								<button type="button" class="btn btn-default"
									onclick="location.href='admin/theme/themeList?fid=${theme.forum.fid}'">返回</button>
							</div>
						</form>
					</c:if>
					<c:if test="${!success}">
						${msg}
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<c:import url="/op/footer"></c:import>
	<script src="kindeditor/kindeditor-all-min.js"></script>
	<script src="kindeditor/lang/zh_CN.js"></script>

	<script src="http://open.reinforce.cn/Bootstrap/datetimepicker/bootstrap-datetimepicker.min.js"></script>
	<script src="http://open.reinforce.cn/Bootstrap/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"  charset="UTF-8"></script>
	<script>
		var $this;
		var $type;
		$(function() {
		
			$('[data-toggle="tooltip"]').tooltip();
		
			KindEditor
					.ready(function(K) {
						var editor = K
								.create(
										"#contentEditer",
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
														'.margin-left',
														'id','class','data-date','data-date-format' ],
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
												a : [ 'href', 'target', 'name', 'id' , 'data-*'],
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
												i : ['class'],
												property : ['name','value','ref'],
												bean : ['id','class','p:connection-factory-ref','/'],
												hr : [ 'class',
														'.page-break-after' ],
												'script,iframe' : ['src','style'],
												'br,tbody,tr,strong,b,sub,sup,em,u,strike,s,del,code' : [],
												'dt,dd' : [ 'style' ],
												'audio,video' : [ 'src',
														'preload', 'loop',
														'controls', 'autoplay',
														'height', 'width' ],
												'button,input' : ['class','id', 'size', 'type','value','placeholder','onclick'],
												link : ['rel','href']
											},
											resizeType : 1,
											urlType : "domain",
											afterBlur : function(e) {
												$('#contentEditer').val(
														editor.html());
											},
											afterChange : function() {
												$('.word_count1').html(
														this.count()); //字数统计包含HTML代码
											}

										});
						html = editor.html();

						// 同步数据后可以直接取得textarea的value
						editor.sync();
						html = $('#contentEditer').val(); // jQuery

						// 设置HTML内容
						//editor.html('HTML内容');
					});
					
			
		$("#themeForm")
					.Validform(
							{
								btnReset : "",
								tiptype : function(msg, o, cssctl) {
									switch (o.type) {
									case 1:
										$(o.obj).parent().removeClass(
												"has-error").removeClass(
												"has-success");
										break;
									case 2:
										$(o.obj).parent().removeClass(
												"has-error").addClass(
												"has-success");
										break;
									case 3:
										$(o.obj).attr("placeholder", msg).parent().removeClass(
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
									"*6-20" : /^[^\s]{6,20}$/,
									"z2-16" : /^[\u4E00-\u9FA5\uF900-\uFA2D\u3040-\u309F\u30A0-\u30FF\u3100-\u312F\u31F0-\u31FF\uAC00-\uD7AF\w]{2,16}$/,
									"pm" : /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z0-9]{5}$/,
									"ID" : /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/
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
									addTheme($type, $this);
									return false;
								}
							});

			$("#elementType").change(function() {
				alert($(this).val());
			});
			$("input[name=type]").click(function() {
				if ($(this).val() == 0)
					$(".medias").hide();
				else {
					$(".medias").show();
					if ($(this).val() == 1)
						$(".newUrl").attr("onclick", "newAudioFile()");
					else
						$(".newUrl").attr("onclick", "newVideoFile()");
				}

			});

			$(document).on("click", ".deleterow", function() {
				$(this).parent().remove();
			});
			$("input[name=type]:eq(${theme.type})").click();
			$('.time-picker').datetimepicker({
				format : 'yyyy-mm-dd hh:ii:ss',
				autoclose : true,
				todayBtn : true,
				todayHighlight : true,
				language : 'zh-CN'
			});
		});

		function submitTheme(type, btn){
			$this = btn;
			$type = type;
			$("#submitBtn").click();
		}

		function newVideoFile() {
			var content = "<input class='form-control url pull-left' type='text' name='files'  datatype='*' nullmsg='请填写视频地址'/><a class='pull-left close deleterow' aria-label='Close'><span aria-hidden='true'>&times;</span></a>";
			$(".newUrl").parent().before(content);
		}

		function newAudioFile() {
			var content = "<div class='col-lg-2'><input class='form-control atitle ' type='text' name='titles' placeholder='文件名' datatype='*' nullmsg='请填写文件名' /></div><div class='col-lg-4'><input class='form-control url pull-left' type='text' name='files' placeholder='文件地址'  datatype='*' nullmsg='请填写文件地址'/></div><div class='col-lg-2'><input class='form-control singer pull-left' type='text' name='singers' placeholder='歌手'  datatype='*' nullmsg='请填写歌手'/></div><div class='col-lg-3'><input class='form-control time pull-left' type='text' name='times'  placeholder='播放时间'  datatype='*' nullmsg='请填写播放时长'/></div><div class='col-lg-1'><a class='pull-left close deleterow' aria-label='Close'><span aria-hidden='true'>&times;</span></a></div>";
			$(".newUrl").parent().before(content);
		}

		function addTheme(state, btn) {
			$(btn).button('loading');
			var options = {
				url : "admin/theme/addTheme",
				type : 'post',
				dataType : 'json',
				data : {
					state : state
				},
				success : function(data) {
					$(btn).button('reset');
					if (data.success) {
						toastr.success(data.msg);
						setTimeout(function() {
							window.location.href = data.url;
						}, 1000);
					} else {
						toastr.error(data.msg);
						setTimeout(function() {
							d.close().remove();
							$(btn).button('reset');
						}, 1000);
					}
				},
				error : function(data) {
					if (data.status == 404)
						toastr.error("请求地址不存在");
					else if (data.status == 500)
						toastr.error("系统内部错误");
					else if (data.status == 200) {
						toastr.error("登录超时，为保证您填写的内容不丢失，请勿刷新页面，并在新开页面中重新登录");
					} else
						toastr.error("通信异常");
					$(btn).button('reset');
				}

			};
			$("#themeForm").ajaxSubmit(options);
		}
	</script>
</body>
</html>
